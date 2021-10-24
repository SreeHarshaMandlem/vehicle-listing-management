package car.hey.listing.service;

import car.hey.listing.repository.DealerEntity;
import java.util.UUID;

public interface DealerService {

  DealerEntity getDealerEntity(UUID dealerId);
}
