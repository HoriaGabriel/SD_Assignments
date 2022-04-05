package ro.sd.foodpanda.service;

import org.springframework.stereotype.Service;
import ro.sd.foodpanda.model.Food;
import ro.sd.foodpanda.model.Restaurant;
import ro.sd.foodpanda.repository.FoodRepository;

import java.util.List;

@Service
public class FoodServiceImpl implements FoodService{

    FoodRepository foodRepository;

    public FoodServiceImpl(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @Override
    public void saveFood(Food f) {

        foodRepository.save(f);
    }

    @Override
    public List<Food> getFoods(Restaurant restaurant) {

        List<Food> foods = foodRepository.getByRestaurant(restaurant);

        if(foods.isEmpty())
            return null;

        return foods;
    }

    @Override
    public Food findFoodWithName(String food) {

        Food f = foodRepository.getByName(food);

        if(f==null)
           return null;

        return f;
    }

    @Override
    public void updateFood(Food f) {

        foodRepository.save(f);
    }
}
