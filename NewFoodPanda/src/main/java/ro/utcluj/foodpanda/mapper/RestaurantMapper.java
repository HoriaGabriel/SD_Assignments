package ro.utcluj.foodpanda.mapper;

import ro.utcluj.foodpanda.dto.RestaurantDTO;
import ro.utcluj.foodpanda.model.Restaurant;

import java.util.List;

public interface RestaurantMapper {
    
    List<RestaurantDTO> convertToDTO(List<Restaurant> restaurantList);

    RestaurantDTO convertToDTOOneRestaurant(Restaurant restaurant);

    Restaurant convertFromDTO(RestaurantDTO restaurantDTO);
}
