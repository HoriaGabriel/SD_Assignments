package ro.sd.foodpanda.mapper;

import ro.sd.foodpanda.dto.RestaurantDTO;
import ro.sd.foodpanda.model.Restaurant;

import java.util.List;

public interface RestaurantMapper {
    
    List<RestaurantDTO> convertToDTO(List<Restaurant> restaurantList);

    RestaurantDTO convertToDTOOneRestaurant(Restaurant restaurant);

    Restaurant convertFromDTO(RestaurantDTO restaurantDTO);
}
