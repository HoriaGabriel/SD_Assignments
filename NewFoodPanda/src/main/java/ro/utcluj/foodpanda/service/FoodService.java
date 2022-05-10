package ro.utcluj.foodpanda.service;

import ro.utcluj.foodpanda.model.Food;
import ro.utcluj.foodpanda.model.Restaurant;

import java.util.List;

public interface FoodService {

    void saveFoodService(Food food);
    void updateFoodService(Food food);

    List<Food> getFoodsService(Restaurant restaurant);
    Food getFoodWithNameService(String foodName);
}
