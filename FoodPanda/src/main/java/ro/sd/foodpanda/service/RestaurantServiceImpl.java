package ro.sd.foodpanda.service;
import org.springframework.stereotype.Service;
import ro.sd.foodpanda.model.Administrator;
import ro.sd.foodpanda.model.Restaurant;
import ro.sd.foodpanda.repository.RestaurantRepository;

import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService{

    RestaurantRepository restaurantRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Restaurant getRestaurant(Administrator administrator) {

        Restaurant restaurant = restaurantRepository.findByAdministrator(administrator);

        if(restaurant == null)
           return null;

        return restaurant;
    }

    @Override
    public Restaurant getRestaurantWithName(String restaurant) {

        Restaurant restaurant1 = restaurantRepository.findByName(restaurant);

        if(restaurant == null)
            return null;

        return restaurant1;
    }

    @Override
    public void updateRestaurant(Restaurant res) {
        restaurantRepository.save(res);
    }

    @Override
    public List<Restaurant> getRestaurants() {

        List<Restaurant> restaurantList = restaurantRepository.findAll();
        if(restaurantList.isEmpty())
           return null;

        return restaurantList;
    }

    @Override
    public Restaurant findRestaurant(Administrator administrator) {

        Restaurant restaurant = restaurantRepository.findByAdministrator(administrator);

        if(restaurant==null)
            return null;
        else
            return restaurant;
    }

    @Override
    public void saveRestaurant(Restaurant restaurant1) {
        restaurantRepository.save(restaurant1);
    }
}
