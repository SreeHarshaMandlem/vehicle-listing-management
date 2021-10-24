package car.hey.listing.repository;

import static org.springframework.data.jpa.domain.Specification.where;

import car.hey.listing.error.ApiException;
import car.hey.listing.error.ErrorEnum;
import car.hey.listing.request.ListingSearchRequest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListingRepository extends PagingAndSortingRepository<ListingEntity, UUID>,
    JpaSpecificationExecutor<ListingEntity> {

  Optional<ListingEntity> findByDealerAndCode(DealerEntity dealer, String code);

  /**
   * Search for Listings based on make, model, year, color search parameters.
   *
   * @param searchParameters the search criteria
   * @return List of ListingEntities
   */
  default List<ListingEntity> findAllByListingSearchParameters(
      final ListingSearchRequest searchParameters) {
    Specification<ListingEntity> searchClause = null;

    if (searchParameters.getMake() != null) {
      searchClause = where(ListingRepository.hasMake(searchParameters.getMake()));
    }

    if (searchParameters.getModel() != null) {
      if (searchClause == null) {
        searchClause = where(ListingRepository.hasModel(searchParameters.getModel()));
      } else {
        searchClause = searchClause.and(ListingRepository.hasModel(searchParameters.getModel()));
      }
    }

    if (searchParameters.getYear() != 0) {
      if (searchClause == null) {
        searchClause = where(ListingRepository.hasYear(searchParameters.getYear()));
      } else {
        searchClause = searchClause.and(ListingRepository.hasYear(searchParameters.getYear()));
      }
    }

    if (searchParameters.getColor() != null) {
      if (searchClause == null) {
        searchClause = where(ListingRepository.hasColor(searchParameters.getColor()));
      } else {
        searchClause = searchClause.and(ListingRepository.hasColor(searchParameters.getColor()));
      }
    }

    if (searchClause == null) {
      throw new ApiException(ErrorEnum.MIN_SEARCH_PARAMETERS_REQUIRED);
    } else {

      return this.findAll(searchClause);
    }
  }

  static Specification<ListingEntity> hasMake(final String make) {
    return (entity, cq, cb) -> cb.equal(entity.get("make"), make);
  }

  static Specification<ListingEntity> hasModel(final String model) {
    return (entity, cq, cb) -> cb.equal(entity.get("model"), model);
  }

  static Specification<ListingEntity> hasYear(final int year) {
    return (entity, cq, cb) -> cb.equal(entity.get("year"), year);
  }

  static Specification<ListingEntity> hasColor(final String color) {
    return (entity, cq, cb) -> cb.equal(entity.get("color"), color);
  }

}
