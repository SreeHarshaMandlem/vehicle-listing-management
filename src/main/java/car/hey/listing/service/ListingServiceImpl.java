package car.hey.listing.service;

import car.hey.listing.mapper.ListingMapper;
import car.hey.listing.model.Listing;
import car.hey.listing.request.ListingSearchRequest;
import car.hey.listing.repository.ListingRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class ListingServiceImpl implements ListingService {

  private ListingRepository listingRepository;

  private ListingMapper listingMapper;

  @Override
  public List<Listing> search(final ListingSearchRequest parameters) {
    log.info("Retrieving Listings with criteria: {}", parameters);
    return listingMapper.map(listingRepository.findAllByListingSearchParameters(parameters));
  }
}
