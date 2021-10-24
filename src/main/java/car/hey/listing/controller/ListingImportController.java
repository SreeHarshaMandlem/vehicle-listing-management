package car.hey.listing.controller;

import static org.springframework.http.HttpStatus.OK;

import car.hey.listing.model.Listing;
import car.hey.listing.service.ListingImportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@Api(tags="Listing Controller")
public class ListingImportController {

  private ListingImportService listingImportService;

  @PostMapping(value = "/upload_csv/{dealerId}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Import Listings from providers from uploaded CSV file")
  @ApiResponses({
      @ApiResponse(code = 200, message = "Success"),
      @ApiResponse(code = 400, message = "Bad Request"),
      @ApiResponse(code = 404, message = "Not Found"),
      @ApiResponse(code = 500, message = "Technical error during elaboration")})
  @ResponseStatus(code = OK)
  public void importListingsFromCsv(
      @PathVariable final UUID dealerId, @RequestParam("file") final MultipartFile file) {
    listingImportService.importListingsFromCsv(dealerId, file);
  }

  @PostMapping(value = "/vehicle_listings", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Import Listings from providers from JSON")
  @ApiResponses({
      @ApiResponse(code = 200, message = "Success"),
      @ApiResponse(code = 400, message = "Bad Request"),
      @ApiResponse(code = 404, message = "Not Found"),
      @ApiResponse(code = 500, message = "Technical error during elaboration")})
  @ResponseStatus(code = OK)
  public void importListingsFromJson(
      @RequestHeader("DEALER_ID") final UUID dealerId, @RequestBody final List<Listing> listings) {
    listingImportService.importListingsFromJson(dealerId, listings);
  }

}
