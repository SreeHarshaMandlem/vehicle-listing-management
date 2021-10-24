package car.hey.listing.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import car.hey.listing.error.ApiException;
import car.hey.listing.repository.DealerEntity;
import car.hey.listing.repository.DealerRepository;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DealerServiceTest {

  public static final UUID DEALER_ID = UUID.randomUUID();
  @Mock
  private DealerRepository dealerRepository;

  private DealerService dealerService;

  @BeforeEach
  void setup() {
    dealerService = new DealerServiceImpl(dealerRepository);
  }

  @Test
  void getDealerEntity_success() {
    final DealerEntity dealerEntity = getDealerEntity();

    when(dealerRepository.findById(DEALER_ID)).thenReturn(Optional.of(dealerEntity));

    assertEquals(dealerEntity, dealerService.getDealerEntity(DEALER_ID));
  }

  @Test
  void getDealerEntity_whenDealerIdDoesNotExist_ApiException() {
    when(dealerRepository.findById(DEALER_ID)).thenReturn(Optional.empty());

    assertThrows(ApiException.class, () -> dealerService.getDealerEntity(DEALER_ID));
  }


  private DealerEntity getDealerEntity() {
    return new DealerEntity(DEALER_ID, "");
  }
}