package car.hey.listing.extractor;

import static car.hey.listing.error.ErrorEnum.GENERIC_EXCEPTION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import car.hey.listing.error.ApiException;
import car.hey.listing.repository.DealerEntity;
import car.hey.listing.repository.DealerRepository;
import car.hey.listing.repository.ListingEntity;
import car.hey.listing.repository.ListingRepository;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
class CsvListingExtractorTest {

  private static final UUID DEALER_ID = UUID.randomUUID();
  private static final String DEALER_NAME = "Erikson";

  @Mock
  private ListingRepository listingRepository;

  private CsvListingExtractor csvListingsImportService;

  @BeforeEach
  void setup() {
    csvListingsImportService = new CsvListingExtractor(listingRepository);
  }

  @Test
  void extractListings_success() throws IOException {
    final MultipartFile csv = Mockito.mock(MultipartFile.class);
    when(csv.getInputStream()).thenReturn(new FileInputStream("src/test/resources/listings.csv"));

    final List<ListingEntity> listingEntities = csvListingsImportService
        .extractListings(getDealerEntity(), csv);

    Assertions.assertEquals(9, listingEntities.size());
  }

  @Test
  void importListings_whenIOError_thenApiException() throws IOException {
    final MultipartFile file = Mockito.mock(MultipartFile.class);
    when(file.getInputStream()).thenThrow(new IOException());
    final ApiException apiException = Assertions.assertThrows(ApiException.class,
        () -> csvListingsImportService.extractListings(getDealerEntity(), file));

    assertEquals(GENERIC_EXCEPTION.getCode(), apiException.getCode());
    assertEquals(GENERIC_EXCEPTION.getStatus(), apiException.getStatus());
    assertEquals(GENERIC_EXCEPTION.getDescription(), apiException.getMessage());
  }

  private DealerEntity getDealerEntity() {
    return new DealerEntity(DEALER_ID, DEALER_NAME);
  }
}