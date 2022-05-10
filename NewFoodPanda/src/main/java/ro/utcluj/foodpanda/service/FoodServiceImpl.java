package ro.utcluj.foodpanda.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ro.utcluj.foodpanda.model.Food;
import ro.utcluj.foodpanda.model.Restaurant;
import ro.utcluj.foodpanda.repository.FoodRepository;
import java.util.List;


@Service
public class FoodServiceImpl implements FoodService{

    FoodRepository foodRepository;

    private static final Logger logger = LoggerFactory.getLogger(FoodServiceImpl.class);

    public FoodServiceImpl(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    /**
     * The method saves a new food into the database
     * @param food the food to be saved
     */
    @Override
    public void saveFoodService(Food food) {

        logger.info("Food will be saved {}",food.getName());

        foodRepository.save(food);

        logger.info("Food has been saved {}",food.getName());
    }

    /**
     * The method returns a list of foods who belong to the given restaurant
     * @param restaurant the restaurant with the foods
     * @return the list of foods
     */
    @Override
    public List<Food> getFoodsService(Restaurant restaurant) {

        logger.info("Searching for Food with given restaurant {}",restaurant.getName());
        List<Food> foods = foodRepository.findByRestaurant(restaurant);

        if(foods == null){
            logger.error("List of foods is null");
            return null;
        }

        logger.info("Food List has been found {}",foods);

        return foods;
    }

    /**
     * The method searches for the food using the name
     * @param foodName name of the food
     * @return the found food or null if there is none
     */
    @Override
    public Food getFoodWithNameService(String foodName) {

        logger.info("Searching for Food with given name {}",foodName);

        Food food = foodRepository.findByName(foodName);

        if(food==null){
            logger.error("Food not found");
            return null;
        }

        logger.info("Food has been found {}",food);

        return food;
    }

    /**
     * The method updates the given food
     * @param food the food to be updated
     */
    @Override
    public void updateFoodService(Food food) {

        logger.info("Food will be updated {}",food.getName());

        foodRepository.save(food);

        logger.info("Food has been updated {}",food.getName());
    }
}
