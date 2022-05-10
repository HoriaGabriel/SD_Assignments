package ro.utcluj.foodpanda.mapper;

import ro.utcluj.foodpanda.dto.RestaurantDTO;
import ro.utcluj.foodpanda.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class RealRestaurantMapper implements RestaurantMapper{

    /**
     * The method converts the dto to a restaurant
     * @param restaurantDTO the dto to be converted
     * @return the resulted restaurant
     */
    public Restaurant convertFromDTO(RestaurantDTO restaurantDTO){

        Restaurant res = new Restaurant();
        res.setName(restaurantDTO.getName());
        res.setLocation(restaurantDTO.getLocation());
        res.setDeliveryZones(Integer.valueOf(restaurantDTO.getDelivery_zones()));

        return res;
    }

    /**
     * Method to convert a list of restaurants to dto
     * @param restaurantList the restaurant list
     * @return the dto list
     */
    public List<RestaurantDTO> convertToDTO(List<Restaurant> restaurantList) {

        List<RestaurantDTO> restaurantDTOList = new ArrayList<>();

        for(Restaurant r: restaurantList){

            RestaurantDTO restaurantDTO = new RestaurantDTO();
            restaurantDTO.setId(r.getId().toString());
            restaurantDTO.setName(r.getName());
            restaurantDTO.setLocation(r.getLocation());
            restaurantDTO.setDelivery_zones(r.getDeliveryZones().toString());
            restaurantDTO.setAdministrator(r.getUser().getUsername());

            restaurantDTOList.add(restaurantDTO);
        }

        return restaurantDTOList;

    }

    /**
     * The Method converts a restaurant to a dto
     * @param r the restaurant to be converted
     * @return the created dto
     */
    public RestaurantDTO convertToDTOOneRestaurant(Restaurant r) {

        RestaurantDTO restaurantDTO = new RestaurantDTO();
        restaurantDTO.setId(r.getId().toString());
        restaurantDTO.setName(r.getName());
        restaurantDTO.setLocation(r.getLocation());
        restaurantDTO.setDelivery_zones(r.getDeliveryZones().toString());
        restaurantDTO.setAdministrator(r.getUser().getUsername());

        return restaurantDTO;
    }
}
