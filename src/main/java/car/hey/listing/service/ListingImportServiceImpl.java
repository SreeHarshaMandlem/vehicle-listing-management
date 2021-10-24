package car.hey.listing.service;

import car.hey.listing.error.ApiException;
import car.hey.listing.error.ErrorEnum;
import car.hey.listing.extractor.CsvListingExtractor;
import car.hey.listing.extractor.JsonListingExtractor;
import car.hey.listing.model.Listing;
import car.hey.listing.repository.ListingEntity;
import car.hey.listing.repository.ListingRepository;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@AllArgsConstructor
public class ListingImportServiceImpl implements ListingImportService {

  private final CsvListingExtractor csvListingExtractor;

  private final JsonListingExtractor jsonListingExtractor;

  private final ListingRepository listingRepository;

  private final DealerService dealerService;

  /**
   * Imports the Listings from CSV file, upon validating the file. Any faulty entries will be
   * skipped and logged for auditing.
   *
   * @param dealerId The Dealer ID
   * @param file     The CSV file to import the Listings from
   */
  public void importListingsFromCsv(final UUID dealerId, final MultipartFile file) {
    log.info("Importing Lisings for Dealer with id {} from CSV file: {}", dealerId, file.getName());
    validateFile(file);
    final List<ListingEntity> listingEntities = csvListingExtractor
        .extractListings(dealerService.getDealerEntity(dealerId), file);
    listingRepository.saveAll(listingEntities);
  }

  /**
   * Imports the Listings from JSON.
   *
   * @param dealerId The Dealer ID
   * @param listings The Listings from JSON
   */
  public void importListingsFromJson(final UUID dealerId, final List<Listing> listings) {
    log.info("Importing Lisings for Dealer with id {} from JSON", dealerId);
    final List<ListingEntity> listingEntities = jsonListingExtractor
        .extractListings(dealerService.getDealerEntity(dealerId), listings);
    listingRepository.saveAll(listingEntities);
  }

  private void validateFile(final MultipartFile file) {
    log.debug("Validating uploaded file");
    if (!CSV_TYPE.equals(file.getContentType())) {
      log.error("Uploaded file is not a CSV file.");
      throw new ApiException(ErrorEnum.FILE_FORMAT_NOT_SUPPORTED);
    }
  }
}
