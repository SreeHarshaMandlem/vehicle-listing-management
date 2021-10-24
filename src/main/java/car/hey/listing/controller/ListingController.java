package car.hey.listing.controller;

import static org.springframework.http.ResponseEntity.ok;

import car.hey.listing.model.Listing;
import car.hey.listing.request.ListingSearchRequest;
import car.hey.listing.service.ListingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Api(tags="Listing Controller")
public class ListingController {

  private final ListingService listingService;

  @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Search for listings as based on search criteria")
  @ApiResponses({
      @ApiResponse(code = 200, message = "Success"),
      @ApiResponse(code = 400, message = "Bad Request"),
      @ApiResponse(code = 500, message = "Technical error")})
  public ResponseEntity<List<Listing>> searchListing(
      @RequestBody final ListingSearchRequest listingSearchRequest) {
    return ok(listingService.search(listingSearchRequest));
  }

}
