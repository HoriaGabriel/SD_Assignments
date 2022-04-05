package ro.sd.foodpanda.service;

import ro.sd.foodpanda.model.Restaurant;
import ro.sd.foodpanda.model.Administrator;

import java.util.List;

public interface RestaurantService {
    Restaurant getRestaurant(Administrator admin);

    Restaurant getRestaurantWithName(String restaurant);

    void updateRestaurant(Restaurant res);

    List<Restaurant> getRestaurants();

    Restaurant findRestaurant(Administrator administrator);

    void saveRestaurant(Restaurant restaurant1);
}
