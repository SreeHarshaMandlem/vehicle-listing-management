package car.hey.listing.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import car.hey.listing.error.ApiControllerAdvice;
import car.hey.listing.error.ApiException;
import car.hey.listing.error.ErrorEnum;
import car.hey.listing.model.Listing;
import car.hey.listing.service.ListingImportService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest
@ContextConfiguration(classes = {ListingImportController.class, ApiControllerAdvice.class})
class ListingImportControllerTest {

  public static final UUID DEALER_ID = UUID.randomUUID();
  private static final String CODE = "code";
  private static final String MAKE = "renault";
  private static final String MODEL = "megane";
  private static final int POWER = 132;
  private static final int YEAR = 2014;
  private static final String COLOR = "red";
  private static final double PRICE = 13990;

  @MockBean
  private ListingImportService listingImportService;

  @Autowired
  private MockMvc mockMvc;

  @Test
  void importCsv_ok() throws Exception {
    MockMultipartFile file = new MockMultipartFile(
        "file",
        "listings.csv",
        ListingImportService.CSV_TYPE,
        Files.readAllBytes(new File("src/test/resources/listings.csv").toPath())
    );

    doNothing().when(listingImportService).importListingsFromCsv(DEALER_ID, file);

    mockMvc.perform(multipart("/upload_csv/" + DEALER_ID).file(file))
        .andExpect(status().isOk());
  }

  @Test
  void importCsv_dealerNotFound_404() throws Exception {
    MockMultipartFile file = new MockMultipartFile(
        "file",
        "listings.csv",
        ListingImportService.CSV_TYPE,
        Files.readAllBytes(new File("src/test/resources/listings.csv").toPath())
    );

    doThrow(new ApiException(ErrorEnum.DEALER_ID_NOT_FOUND)).when(listingImportService)
        .importListingsFromCsv(DEALER_ID, file);

    mockMvc.perform(multipart("/upload_csv/" + DEALER_ID).file(file))
        .andExpect(status().isNotFound());
  }

  @Test
  void importCsv_fileTypeNotSupported_400() throws Exception {
    MockMultipartFile file = new MockMultipartFile(
        "file",
        "listings.txt",
        MediaType.TEXT_PLAIN_VALUE,
        "Simple Text".getBytes()
    );

    doThrow(new ApiException(ErrorEnum.FILE_FORMAT_NOT_SUPPORTED)).when(listingImportService)
        .importListingsFromCsv(DEALER_ID, file);

    mockMvc.perform(multipart("/upload_csv/" + DEALER_ID).file(file))
        .andExpect(status().isBadRequest());
  }

  @Test
  void importJson_ok() throws Exception {

    doNothing().when(listingImportService).importListingsFromJson(any(UUID.class), anyList());

    final List<Listing> listings = new ArrayList<>();
    listings.add(createListing());
    listings.add(createListing());
    listings.add(createListing());

    final String json = new ObjectMapper().writeValueAsString(listings);

    final ResultActions resultActions = mockMvc.perform(post("/vehicle_listings")
        .header("DEALER_ID", DEALER_ID)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isOk());

    System.out.println("json = " + json);
  }

  @Test
  void importJson_dealerNotFound_404() throws Exception {
    doThrow(new ApiException(ErrorEnum.DEALER_ID_NOT_FOUND)).when(listingImportService)
        .importListingsFromJson(any(UUID.class), anyList());

    final String json = new ObjectMapper().writeValueAsString(Collections.emptyList());
    mockMvc.perform(post("/vehicle_listings")
        .header("DEALER_ID", DEALER_ID)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isNotFound());
  }

  private Listing createListing() {
    return new Listing(null, CODE, MAKE, MODEL, POWER, YEAR, COLOR, PRICE);
  }
}