package ro.utcluj.foodpanda.service;

import ro.utcluj.foodpanda.model.User;
import ro.utcluj.foodpanda.model.Restaurant;

import java.util.List;

public interface RestaurantService {

    Restaurant getRestaurantService(User adminUser);

    Restaurant getRestaurantWithNameService(String restaurantName);

    List<Restaurant> getRestaurantsService();

    void updateRestaurantService(Restaurant restaurant);

    void saveRestaurantService(Restaurant restaurant);
}
