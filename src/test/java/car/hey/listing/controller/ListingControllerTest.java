package car.hey.listing.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import car.hey.listing.error.ApiControllerAdvice;
import car.hey.listing.error.ApiException;
import car.hey.listing.error.ErrorEnum;
import car.hey.listing.model.Listing;
import car.hey.listing.request.ListingSearchRequest;
import car.hey.listing.service.ListingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
@ContextConfiguration(classes = {ListingController.class, ApiControllerAdvice.class})
class ListingControllerTest {

  public static final UUID DEALER_ID = UUID.randomUUID();
  private static final String CODE = "code";
  private static final String MAKE = "renault";
  private static final String MODEL = "megane";
  private static final int POWER = 132;
  private static final int YEAR = 2014;
  private static final String COLOR = "red";
  private static final double PRICE = 13990;

  @MockBean
  private ListingService listingService;

  @Autowired
  private MockMvc mockMvc;

  @Test
  void search_noSearchParametersPassed_400() throws Exception {
    when(listingService.search(any(ListingSearchRequest.class)))
        .thenThrow(new ApiException(ErrorEnum.MIN_SEARCH_PARAMETERS_REQUIRED));

    final String json = new ObjectMapper().writeValueAsString(new ListingSearchRequest());
    mockMvc.perform(get("/search/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isBadRequest());
  }

  @Test
  void search_ok() throws Exception {
    when(listingService.search(any(ListingSearchRequest.class)))
        .thenReturn(Collections.singletonList(createListing()));

    final String json = new ObjectMapper().writeValueAsString(new ListingSearchRequest());
    mockMvc.perform(get("/search/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isOk());
  }

  private Listing createListing() {
    return new Listing(null, CODE, MAKE, MODEL, POWER, YEAR, COLOR, PRICE);
  }
}