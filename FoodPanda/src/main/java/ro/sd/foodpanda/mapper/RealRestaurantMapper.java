package ro.sd.foodpanda.mapper;

import ro.sd.foodpanda.dto.RestaurantDTO;
import ro.sd.foodpanda.model.Restaurant;


import java.util.ArrayList;
import java.util.List;

public class RealRestaurantMapper implements RestaurantMapper{

    public Restaurant convertFromDTO(RestaurantDTO restaurantDTO){

        Restaurant res = new Restaurant();
        res.setName(restaurantDTO.getName());
        res.setLocation(restaurantDTO.getLocation());
        res.setDeliveryZones(Integer.valueOf(restaurantDTO.getDelivery_zones()));

        return res;
    }

    public List<RestaurantDTO> convertToDTO(List<Restaurant> restaurantList) {

        List<RestaurantDTO> restaurantDTOList = new ArrayList<>();

        for(Restaurant r: restaurantList){

            RestaurantDTO restaurantDTO = new RestaurantDTO();
            restaurantDTO.setId(r.getId().toString());
            restaurantDTO.setName(r.getName());
            restaurantDTO.setLocation(r.getLocation());
            restaurantDTO.setDelivery_zones(r.getDeliveryZones().toString());
            restaurantDTO.setAdministrator(r.getAdministrator().getUsername());

            restaurantDTOList.add(restaurantDTO);
        }

        return restaurantDTOList;

    }

    public RestaurantDTO convertToDTOOneRestaurant(Restaurant r) {

        RestaurantDTO restaurantDTO = new RestaurantDTO();
        restaurantDTO.setId(r.getId().toString());
        restaurantDTO.setName(r.getName());
        restaurantDTO.setLocation(r.getLocation());
        restaurantDTO.setDelivery_zones(r.getDeliveryZones().toString());
        restaurantDTO.setAdministrator(r.getAdministrator().getUsername());

        return restaurantDTO;
    }
}
