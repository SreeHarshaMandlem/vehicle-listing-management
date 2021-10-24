package car.hey.listing.extractor;

import car.hey.listing.error.ApiException;
import car.hey.listing.error.ErrorEnum;
import car.hey.listing.repository.DealerEntity;
import car.hey.listing.repository.ListingEntity;
import car.hey.listing.repository.ListingRepository;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Log
@AllArgsConstructor
public class CsvListingExtractor implements ListingExtractorService<MultipartFile> {

  private final ListingRepository listingRepository;

  @Override
  public List<ListingEntity> extractListings(final DealerEntity dealerEntity,
      final MultipartFile file) {
    try (BufferedReader reader = new BufferedReader(
        new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
        CSVParser csvParser = new CSVParser(reader,
            CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

      List<ListingEntity> listings = new ArrayList<>();

      for (CSVRecord csvRecord : csvParser.getRecords()) {

        if (csvRecord.size() != 6) {
          log.severe("Record is faulty: " + csvRecord.toString());
          continue;
        }

        final UUID id = getListingId(dealerEntity, csvRecord.get("code"));
        ListingEntity listing = new ListingEntity(
            id,
            dealerEntity,
            csvRecord.get("code"),
            csvRecord.get("make/model").split("/")[0],
            csvRecord.get("make/model").split("/")[1],
            Integer.parseInt(csvRecord.get("power-in-ps")),
            Integer.parseInt(csvRecord.get("year")),
            csvRecord.get("color"),
            Double.parseDouble(csvRecord.get("price"))
        );

        listings.add(listing);
      }

      return listings;
    } catch (IOException e) {
      log.severe("Unexpected exception. Failed to parse CSV file: " + e.getMessage());
      throw new ApiException(ErrorEnum.GENERIC_EXCEPTION);
    }
  }

  private UUID getListingId(final DealerEntity dealer, final String code) {
    final Optional<ListingEntity> listing = listingRepository.findByDealerAndCode(dealer, code);
    return listing.isPresent() ? listing.get().getId() : UUID.randomUUID();
  }
}
