package car.hey.listing.service;

import static car.hey.listing.error.ErrorEnum.MIN_SEARCH_PARAMETERS_REQUIRED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import car.hey.listing.error.ApiException;
import car.hey.listing.mapper.ListingMapper;
import car.hey.listing.repository.ListingRepository;
import car.hey.listing.request.ListingSearchRequest;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ListingServiceTest {

  private static String PLAIN_TEXT_TYPE = "text/plain";
  private static final String MAKE = "renault";
  private static final String MODEL = "megane";
  private static final int YEAR = 2014;
  private static final String COLOR = "red";

  private static final UUID DEALER_ID = UUID.randomUUID();

  @Mock
  private ListingRepository listingRepository;

  @Mock
  private ListingMapper listingMapper;

  private ListingService listingService;

  @BeforeEach
  void setup() {
    listingService = new ListingServiceImpl(listingRepository, listingMapper);
  }

  @Test
  void search_emptySearchParameters_ApiException() {
    final ListingSearchRequest searchParameters = new ListingSearchRequest();

    when(listingRepository.findAllByListingSearchParameters(searchParameters))
        .thenThrow(new ApiException(MIN_SEARCH_PARAMETERS_REQUIRED));

    final ApiException apiException = assertThrows(ApiException.class,
        () -> listingService.search(searchParameters));

    assertEquals(MIN_SEARCH_PARAMETERS_REQUIRED.getCode(), apiException.getCode());
    assertEquals(MIN_SEARCH_PARAMETERS_REQUIRED.getStatus(), apiException.getStatus());
    assertEquals(MIN_SEARCH_PARAMETERS_REQUIRED.getDescription(), apiException.getMessage());
  }

  @Test
  void search_byMake_success() {
    final ListingSearchRequest searchParameters = new ListingSearchRequest();
    searchParameters.setMake(MAKE);
    listingService.search(searchParameters);
    verify(listingRepository).findAllByListingSearchParameters(any(ListingSearchRequest.class));
  }

  @Test
  void search_byModel_success() {
    final ListingSearchRequest searchParameters = new ListingSearchRequest();
    searchParameters.setModel(MODEL);
    listingService.search(searchParameters);
    verify(listingRepository).findAllByListingSearchParameters(any(ListingSearchRequest.class));
  }

  @Test
  void search_byYear_success() {
    final ListingSearchRequest searchParameters = new ListingSearchRequest();
    searchParameters.setYear(YEAR);
    listingService.search(searchParameters);
    verify(listingRepository).findAllByListingSearchParameters(any(ListingSearchRequest.class));
  }

  @Test
  void search_byColor_success() {
    final ListingSearchRequest searchParameters = new ListingSearchRequest();
    searchParameters.setColor(COLOR);
    listingService.search(searchParameters);
    verify(listingRepository).findAllByListingSearchParameters(any(ListingSearchRequest.class));
  }

  @Test
  void search_byMakeModelYearColor_success() {
    final ListingSearchRequest searchParameters = new ListingSearchRequest();
    searchParameters.setMake(MAKE);
    searchParameters.setModel(MODEL);
    searchParameters.setYear(YEAR);
    searchParameters.setColor(COLOR);
    listingService.search(searchParameters);
    verify(listingRepository).findAllByListingSearchParameters(any(ListingSearchRequest.class));
  }

}