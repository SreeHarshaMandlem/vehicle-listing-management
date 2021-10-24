package car.hey.listing.repository;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "LISTING")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ListingEntity {
  @Id
  @GeneratedValue(generator = "uuid-generator")
  @GenericGenerator(name = "uuid-generator", strategy = "uuid2")
  @Type(type="uuid-char")
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "dealer_id")
  private DealerEntity dealer;

  private String code;

  private String make;

  private String model;

  private int power;

  private int year;

  private String color;

  private double price;
}
