package car.hey.listing.service;

import car.hey.listing.model.Listing;
import car.hey.listing.request.ListingSearchRequest;
import java.util.List;

public interface ListingService {

  /**
   * Search for Listing based on search parameters passed.
   *
   * @param parameters
   * @return
   */
  List<Listing> search(final ListingSearchRequest parameters);
}
