package ro.utcluj.foodpanda.mapper;

import ro.utcluj.foodpanda.dto.RestaurantDTO;
import ro.utcluj.foodpanda.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class RestaurantMapperProxy implements RestaurantMapper{

    private RestaurantMapper restaurantMapper = new RealRestaurantMapper();

    /**
     * Proxy method used to check the data before sending it to the actual mapper to create a
     * list of restaurant dtos
     * @param restaurantList the list of restaurants
     * @return the list of dtos
     */
    @Override
    public List<RestaurantDTO> convertToDTO(List<Restaurant> restaurantList) {

        if(restaurantList==null)
           return null;

        List<RestaurantDTO> restaurantDTOList = new ArrayList<>();
        restaurantDTOList = restaurantMapper.convertToDTO(restaurantList);

        return restaurantDTOList;
    }

    /**
     * The method is used to convert the restaurant to a dto
     * @param restaurant the restaurant to be converted
     * @return the dto
     */
    @Override
    public RestaurantDTO convertToDTOOneRestaurant(Restaurant restaurant) {

        if(restaurant==null)
           return null;

        RestaurantDTO res = new RestaurantDTO();
        res= restaurantMapper.convertToDTOOneRestaurant(restaurant);

        return res;
    }

    /**
     * The method checks the data before using the restaurant mapper to convert the restaurant to a dto
     * @param restaurantDTO the dto to be converted
     * @return the restaurant
     */
    @Override
    public Restaurant convertFromDTO(RestaurantDTO restaurantDTO) {

        if(restaurantDTO==null)
            return null;

        Restaurant restaurant = new Restaurant();
        restaurant=restaurantMapper.convertFromDTO(restaurantDTO);

        return  restaurant;
    }
}
