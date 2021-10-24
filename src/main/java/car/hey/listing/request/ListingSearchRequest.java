package car.hey.listing.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ListingSearchRequest {
  private String color;

  private String make;

  private String model;

  private int year;
}
