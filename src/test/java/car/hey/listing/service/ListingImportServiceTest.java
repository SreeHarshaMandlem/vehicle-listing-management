package car.hey.listing.service;

import static car.hey.listing.error.ErrorEnum.FILE_FORMAT_NOT_SUPPORTED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import car.hey.listing.error.ApiException;
import car.hey.listing.extractor.CsvListingExtractor;
import car.hey.listing.extractor.JsonListingExtractor;
import car.hey.listing.model.Listing;
import car.hey.listing.repository.DealerEntity;
import car.hey.listing.repository.ListingEntity;
import car.hey.listing.repository.ListingRepository;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
class ListingImportServiceTest {

  private static String PLAIN_TEXT_TYPE = "text/plain";

  private static final UUID DEALER_ID = UUID.randomUUID();
  private static final String DEALER_NAME = "Erikson";

  private static final UUID ID = UUID.randomUUID();
  private static final String CODE = "1234";
  private static final String MAKE = "renault";
  private static final String MODEL = "megane";
  private static final int POWER = 132;
  private static final int YEAR = 2014;
  private static final String COLOR = "red";
  private static final double PRICE = 13990;

  @Mock
  private CsvListingExtractor csvListingExtractor;

  @Mock
  private JsonListingExtractor jsonListingExtractor;

  @Mock
  private ListingRepository listingRepository;

  @Mock
  private DealerService dealerService;

  private ListingImportService listingImportService;

  @BeforeEach
  void setup() {
    listingImportService = new ListingImportServiceImpl(csvListingExtractor, jsonListingExtractor,
        listingRepository, dealerService);
  }

  @Test
  void importListingsFromJson_success() {
    final DealerEntity dealerEntity = new DealerEntity(DEALER_ID, DEALER_NAME);
    when(dealerService.getDealerEntity(DEALER_ID)).thenReturn(dealerEntity);

    final List<ListingEntity> listingEntities = Collections.singletonList(createListingEntity());
    final List<Listing> listings = Collections.emptyList();
    when(jsonListingExtractor.extractListings(dealerEntity, listings)).thenReturn(listingEntities);

    listingImportService.importListingsFromJson(DEALER_ID, listings);

    verify(listingRepository).saveAll(listingEntities);
  }

  @Test
  void importListingsFromCsv_success() {
    final DealerEntity dealerEntity = new DealerEntity(DEALER_ID, DEALER_NAME);
    when(dealerService.getDealerEntity(DEALER_ID)).thenReturn(dealerEntity);

    final MultipartFile csv = Mockito.mock(MultipartFile.class);
    when(csv.getContentType()).thenReturn(ListingImportService.CSV_TYPE);
    final List<ListingEntity> listingEntities = Collections.singletonList(createListingEntity());
    when(csvListingExtractor.extractListings(dealerEntity, csv)).thenReturn(listingEntities);

    listingImportService.importListingsFromCsv(DEALER_ID, csv);

    verify(listingRepository).saveAll(listingEntities);
  }

  @Test
  void importListingsFromCsv_fileTypeNotSupportedType_ApiException() {
    final MultipartFile file = Mockito.mock(MultipartFile.class);

    when(file.getContentType()).thenReturn(PLAIN_TEXT_TYPE);

    final ApiException apiException = assertThrows(ApiException.class,
        () -> listingImportService.importListingsFromCsv(DEALER_ID, file));

    assertEquals(FILE_FORMAT_NOT_SUPPORTED.getCode(), apiException.getCode());
    assertEquals(FILE_FORMAT_NOT_SUPPORTED.getStatus(), apiException.getStatus());
    assertEquals(FILE_FORMAT_NOT_SUPPORTED.getDescription(), apiException.getMessage());
  }

  private ListingEntity createListingEntity() {
    return new ListingEntity(ID, createDealerEntity(), CODE, MAKE, MODEL, POWER, YEAR, COLOR, PRICE);
  }

  private DealerEntity createDealerEntity() {
    return new DealerEntity(DEALER_ID, DEALER_NAME);
  }
}