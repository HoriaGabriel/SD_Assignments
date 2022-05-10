package ro.utcluj.foodpanda;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import ro.utcluj.foodpanda.model.Food;
import ro.utcluj.foodpanda.model.Restaurant;
import ro.utcluj.foodpanda.repository.FoodRepository;
import ro.utcluj.foodpanda.service.FoodServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FoodServiceImplTest {

    @InjectMocks
    private FoodServiceImpl foodServiceImpl;

    private FoodRepository foodRepository;

    @Before
    public void setup(){

        foodRepository = mock(FoodRepository.class);

        foodServiceImpl = new FoodServiceImpl(foodRepository);

    }

    @Test
    public void getFoodWithNameServiceScenario() {

        Food sampleFood = new Food();
        sampleFood.setName("Salad");

        when(foodRepository.findByName("Salad")).thenReturn(sampleFood);

        Food testFood = foodServiceImpl.getFoodWithNameService("Salad");

        assertEquals(sampleFood,testFood);
    }

    @Test
    public void getFoodWithNameServiceFailScenario() {

        when(foodRepository.findByName("Salad")).thenReturn(null);

        Food testFood = foodServiceImpl.getFoodWithNameService("Salad");

        assertEquals(null,testFood);
    }


    @Test
    public void getFoodsServiceScenario() {

        Food sampleFood1 = new Food();
        sampleFood1.setName("Salad");

        Food sampleFood2 = new Food();
        sampleFood2.setName("OtherSalad");

        List<Food> foodList = new ArrayList<>();

        foodList.add(sampleFood1);
        foodList.add(sampleFood2);

        Restaurant restaurant = new Restaurant();
        restaurant.setFood(foodList);

        when(foodRepository.findByRestaurant(restaurant)).thenReturn(foodList);

        List<Food> testFoodList = foodServiceImpl.getFoodsService(restaurant);

        assertEquals(foodList,testFoodList);
    }

    @Test
    public void getFoodsServiceFailScenario() {

        Food sampleFood1 = new Food();
        sampleFood1.setName("Salad");

        Food sampleFood2 = new Food();
        sampleFood2.setName("OtherSalad");

        List<Food> foodList = new ArrayList<>();

        foodList.add(sampleFood1);
        foodList.add(sampleFood2);

        Restaurant restaurant = new Restaurant();
        restaurant.setFood(foodList);

        when(foodRepository.findByRestaurant(restaurant)).thenReturn(null);

        List<Food> testFoodList = foodServiceImpl.getFoodsService(restaurant);

        assertEquals(null,testFoodList);
    }

}
