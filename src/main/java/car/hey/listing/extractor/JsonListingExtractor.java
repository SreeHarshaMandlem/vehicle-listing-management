package car.hey.listing.extractor;

import car.hey.listing.mapper.ListingMapper;
import car.hey.listing.model.Listing;
import car.hey.listing.repository.DealerEntity;
import car.hey.listing.repository.ListingEntity;
import car.hey.listing.repository.ListingRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class JsonListingExtractor implements ListingExtractorService<List<Listing>> {

  private final ListingMapper listingMapper;

  private final ListingRepository listingRepository;

  @Override
  public List<ListingEntity> extractListings(DealerEntity dealerEntity, List<Listing> listings) {
    log.debug("Extracting listings from: {}", listings);
    return listings.stream().map(listing -> {
      final UUID listingId = getListingId(dealerEntity, listing.getCode());
      final ListingEntity listingEntity = listingMapper.map(listing);
      listingEntity.setId(listingId);
      listingEntity.setDealer(dealerEntity);
      return listingEntity;
    }).collect(Collectors.toList());
  }

  private UUID getListingId(final DealerEntity dealer, final String code) {
    final Optional<ListingEntity> listing = listingRepository.findByDealerAndCode(dealer, code);
    log.debug("Listing already exists with ({}, {}) combination {}", dealer.getId(), code,
        listing.isPresent() ? "exists, updating Listing." : "does not exist, creating new Listing");
    return listing.isPresent() ? listing.get().getId() : UUID.randomUUID();
  }
}
