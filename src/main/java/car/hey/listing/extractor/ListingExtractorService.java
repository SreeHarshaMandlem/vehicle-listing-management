package car.hey.listing.extractor;

import car.hey.listing.repository.DealerEntity;
import car.hey.listing.repository.ListingEntity;
import java.util.List;

public interface ListingExtractorService<T> {

  List<ListingEntity> extractListings(DealerEntity dealerEntity, T data);
}
