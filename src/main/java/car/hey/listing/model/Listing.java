package car.hey.listing.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Listing {
  private UUID id;

  private String code;

  private String make;

  private String model;

  private int power;

  private int year;

  private String color;

  private double price;
}
