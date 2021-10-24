package car.hey.listing.mapper;

import car.hey.listing.model.Listing;
import car.hey.listing.repository.ListingEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface ListingMapper {
    ListingMapper INSTANCE = Mappers.getMapper(ListingMapper.class);

    Listing map(ListingEntity listingEntity);

    ListingEntity map(Listing listing);

    List<Listing> map(List<ListingEntity> listingEntities);
}
