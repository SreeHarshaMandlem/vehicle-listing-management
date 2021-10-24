package car.hey.listing.service;

import car.hey.listing.model.Listing;
import java.util.List;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

public interface ListingImportService {

  String CSV_TYPE = "text/csv";

  /**
   * Imports the Listings from CSV file, upon validating the file. Any faulty entries will be
   * skipped and logged for auditing.
   *
   * @param dealerId The Dealer ID
   * @param file     The CSV file to import the Listings from
   */
  void importListingsFromCsv(final UUID dealerId, final MultipartFile file);

  /**
   * Imports the Listings from JSON.
   *
   * @param dealerId The Dealer ID
   * @param listings The Listings from JSON
   */
  void importListingsFromJson(final UUID dealerId, final List<Listing> listings);

}
