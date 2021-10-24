package car.hey.listing.service;

import car.hey.listing.error.ApiException;
import car.hey.listing.error.ErrorEnum;
import car.hey.listing.repository.DealerEntity;
import car.hey.listing.repository.DealerRepository;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class DealerServiceImpl implements DealerService {

  private final DealerRepository dealerRepository;

  @Override
  public final DealerEntity getDealerEntity(final UUID dealerId) {
    log.info("Retrieving dealer information with id: {}", dealerId);
    return dealerRepository.findById(dealerId)
        .orElseThrow(() -> new ApiException(ErrorEnum.DEALER_ID_NOT_FOUND));
  }
}
