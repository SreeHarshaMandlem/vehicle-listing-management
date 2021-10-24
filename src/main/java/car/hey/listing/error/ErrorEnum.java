package car.hey.listing.error;

import java.util.Objects;
import org.springframework.http.HttpStatus;

public enum ErrorEnum {
  DEALER_ID_NOT_FOUND(4000, "Dealer ID not found", HttpStatus.NOT_FOUND),
  FILE_FORMAT_NOT_SUPPORTED(4001, "Only CSV file format is supported.", HttpStatus.BAD_REQUEST),
  GENERIC_EXCEPTION(4002, "Unexpected exception.", HttpStatus.INTERNAL_SERVER_ERROR),
  MIN_SEARCH_PARAMETERS_REQUIRED(4003,
      "Atleast one parameter from make, model, year and color is required", HttpStatus.BAD_REQUEST);

  private final HttpStatus status;
  private final int code;
  private final String description;

  public HttpStatus getStatus() {
    return this.status;
  }

  public int getCode() {
    return this.code;
  }

  public String getDescription() {
    return this.description;
  }

  ErrorEnum(int code, String description, HttpStatus status) {
    Objects.requireNonNull(description, "Description is required");
    Objects.requireNonNull(status, "Status is required");
    this.status = status;
    this.code = code;
    this.description = description;
  }
}
