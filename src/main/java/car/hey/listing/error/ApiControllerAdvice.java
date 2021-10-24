package car.hey.listing.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiControllerAdvice {

  /**
   * Advice for exception of type ApiException.
   *
   * @param ex ApiException to send as ResponseEntity
   * @return The ResponseEntity with error details
   */
  @ExceptionHandler(value = ApiException.class)
  protected ResponseEntity<ApiErrorResponse> handleApiException(ApiException ex) {
    final ApiErrorResponse response = new ApiErrorResponse(
        ex.getCode(),
        ex.getStatus().value(),
        ex.getMessage());
    return new ResponseEntity<>(response, ex.getStatus());
  }
}