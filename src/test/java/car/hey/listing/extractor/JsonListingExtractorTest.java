package car.hey.listing.extractor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import car.hey.listing.error.ApiException;
import car.hey.listing.mapper.ListingMapper;
import car.hey.listing.model.Listing;
import car.hey.listing.repository.DealerEntity;
import car.hey.listing.repository.DealerRepository;
import car.hey.listing.repository.ListingEntity;
import car.hey.listing.repository.ListingRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class JsonListingExtractorTest {

  private static final UUID DEALER_ID = UUID.randomUUID();
  private static final String DEALER_NAME = "Erikson";
  private static final String CODE = "code";
  private static final String MAKE = "renault";
  private static final String MODEL = "megane";
  private static final int POWER = 132;
  private static final int YEAR = 2014;
  private static final String COLOR = "red";
  private static final double PRICE = 13990;

  @Mock
  private ListingRepository listingRepository;

  @Mock
  private DealerRepository dealerRepository;

  private ListingMapper listingMapper = ListingMapper.INSTANCE;

  private JsonListingExtractor jsonListingExtractor;

  @BeforeEach
  void setup() {
    jsonListingExtractor = new JsonListingExtractor(listingMapper, listingRepository);
  }

  @Test
  void extractListings_success() {
    List<Listing> listings = new ArrayList<>();
    listings.add(createListing());
    listings.add(createListing());
    listings.add(createListing());

    final List<ListingEntity> listingEntities = jsonListingExtractor
        .extractListings(getDealerEntity(), listings);

    assertEquals(3, listingEntities.size());
  }

  private Listing createListing() {
    return new Listing(null, CODE, MAKE, MODEL, POWER, YEAR, COLOR, PRICE);
  }

  private DealerEntity getDealerEntity() {
    return new DealerEntity(DEALER_ID, DEALER_NAME);
  }
}