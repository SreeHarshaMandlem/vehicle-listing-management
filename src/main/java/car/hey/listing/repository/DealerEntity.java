package car.hey.listing.repository;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "DEALER")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DealerEntity {
  @Id
  @GeneratedValue(generator = "uuid-generator")
  @GenericGenerator(name = "uuid-generator", strategy = "uuid2")
  @Type(type="uuid-char")
  private UUID id;

  private String name;
}
