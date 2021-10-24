package car.hey.listing.error;

import lombok.Getter;

@Getter
public class ApiErrorResponse {
  private final int code;

  private final int status;

  private final String message;

  public ApiErrorResponse(final int code, final int status, final String message) {
    this.code = code;
    this.status = status;
    this.message = message;
  }
}
