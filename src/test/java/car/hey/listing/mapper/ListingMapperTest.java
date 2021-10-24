package car.hey.listing.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import car.hey.listing.model.Listing;
import car.hey.listing.repository.DealerEntity;
import car.hey.listing.repository.ListingEntity;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class ListingMapperTest {

  private static final UUID ID = UUID.randomUUID();
  private static final UUID DEALER_ID = UUID.randomUUID();
  private static final String DEALER_NAME = "Erikson";
  private static final String CODE = "1234";
  private static final String MAKE = "renault";
  private static final String MODEL = "megane";
  private static final int POWER = 132;
  private static final int YEAR = 2014;
  private static final String COLOR = "red";
  private static final double PRICE = 13990;

  @Test
  void mapFromEntityToDTO() {
    Listing dto = ListingMapper.INSTANCE.map(createListingEntity());
    assertListing(dto);
  }

  @Test
  void mapFromDTOToEntity() {
    ListingEntity entity = ListingMapper.INSTANCE.map(createListing());
    assertListingEntity(entity);
  }

  private ListingEntity createListingEntity() {
    return new ListingEntity(ID, createDealerEntity(), CODE, MAKE, MODEL, POWER, YEAR, COLOR, PRICE);
  }

  private DealerEntity createDealerEntity() {
    return new DealerEntity(DEALER_ID, DEALER_NAME);
  }

  private Listing createListing() {
    return new Listing(ID, CODE, MAKE, MODEL, POWER, YEAR, COLOR, PRICE);
  }

  private void assertListingEntity(final ListingEntity listing) {
    assertEquals(ID, listing.getId());
    assertNull(listing.getDealer());
    assertEquals(CODE, listing.getCode());
    assertEquals(MAKE, listing.getMake());
    assertEquals(MODEL, listing.getModel());
    assertEquals(POWER, listing.getPower());
    assertEquals(YEAR, listing.getYear());
    assertEquals(COLOR, listing.getColor());
    assertEquals(PRICE, listing.getPrice());
  }

  private void assertListing(final Listing listing) {
    assertEquals(ID, listing.getId());
    assertEquals(CODE, listing.getCode());
    assertEquals(MAKE, listing.getMake());
    assertEquals(MODEL, listing.getModel());
    assertEquals(POWER, listing.getPower());
    assertEquals(YEAR, listing.getYear());
    assertEquals(COLOR, listing.getColor());
    assertEquals(PRICE, listing.getPrice());
  }
}