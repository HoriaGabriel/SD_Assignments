package ro.sd.foodpanda.mapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ro.sd.foodpanda.dto.RestaurantDTO;
import ro.sd.foodpanda.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class RestaurantMapperProxy implements RestaurantMapper{

    private RestaurantMapper restaurantMapper = new RealRestaurantMapper();

    @Override
    public List<RestaurantDTO> convertToDTO(List<Restaurant> restaurantList) {

        if(restaurantList==null)
           return null;

        List<RestaurantDTO> restaurantDTOList = new ArrayList<>();
        restaurantDTOList = restaurantMapper.convertToDTO(restaurantList);

        return restaurantDTOList;
    }

    @Override
    public RestaurantDTO convertToDTOOneRestaurant(Restaurant restaurant) {

        if(restaurant==null)
           return null;

        RestaurantDTO res = new RestaurantDTO();
        res= restaurantMapper.convertToDTOOneRestaurant(restaurant);

        return res;
    }

    @Override
    public Restaurant convertFromDTO(RestaurantDTO restaurantDTO) {

        if(restaurantDTO==null)
            return null;

        Restaurant restaurant = new Restaurant();
        restaurant=restaurantMapper.convertFromDTO(restaurantDTO);

        return  restaurant;
    }
}
