package car.hey.listing.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Wrapper exception for exceptions that will be sent back from API.
 * Each such exception is described in ErrorEnum.
 */
@Getter
public class ApiException extends RuntimeException {

  private final HttpStatus status;

  private final int code;

  public ApiException(final ErrorEnum error) {
    super(error.getDescription());
    this.status = error.getStatus();
    this.code = error.getCode();
  }
}
