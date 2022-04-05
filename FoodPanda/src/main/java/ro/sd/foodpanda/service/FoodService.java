package ro.sd.foodpanda.service;

import ro.sd.foodpanda.model.Food;
import ro.sd.foodpanda.model.Restaurant;

import java.util.List;

public interface FoodService {
    void saveFood(Food f);

    List<Food> getFoods(Restaurant restaurant);

    Food findFoodWithName(String food);

    void updateFood(Food f);
}
