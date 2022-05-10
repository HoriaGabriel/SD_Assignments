package ro.utcluj.foodpanda;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import ro.utcluj.foodpanda.model.User;
import ro.utcluj.foodpanda.model.Restaurant;
import ro.utcluj.foodpanda.repository.RestaurantRepository;
import ro.utcluj.foodpanda.service.RestaurantServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RestaurantServiceImplTest {

    @InjectMocks
    private RestaurantServiceImpl restaurantServiceImpl;

    private RestaurantRepository restaurantRepository;

    @Before
    public void setup(){

        restaurantRepository = mock(RestaurantRepository.class);

        restaurantServiceImpl = new RestaurantServiceImpl(restaurantRepository);
    }

    @Test
    public void getRestaurantWithNameServiceScenario() {

        Restaurant sampleRestaurant = new Restaurant();
        sampleRestaurant.setName("Yummy");

        when(restaurantRepository.findByName("Yummy")).thenReturn(sampleRestaurant);

        Restaurant testRestaurant = restaurantServiceImpl.getRestaurantWithNameService("Yummy");

        assertEquals(sampleRestaurant,testRestaurant);
    }

    @Test
    public void getRestaurantWithNameServiceFailScenario() {

        when(restaurantRepository.findByName("Yummy")).thenReturn(null);

        Restaurant testRestaurant = restaurantServiceImpl.getRestaurantWithNameService("Yummy");

        assertEquals(null,testRestaurant);
    }


    @Test
    public void getRestaurantServiceScenario() {

        Restaurant sampleRestaurant = new Restaurant();
        sampleRestaurant.setName("Yummy");

        User adminUser = new User();
        adminUser.setRestaurant(sampleRestaurant);

        sampleRestaurant.setUser(adminUser);

        when(restaurantRepository.findByUser(adminUser)).thenReturn(sampleRestaurant);

        Restaurant testRestaurant = restaurantServiceImpl.getRestaurantService(adminUser);

        assertEquals(sampleRestaurant,testRestaurant);
    }

    @Test
    public void getRestaurantServiceFailScenario() {

        User adminUser = new User();

        when(restaurantRepository.findByUser(adminUser)).thenReturn(null);

        Restaurant testRestaurant = restaurantServiceImpl.getRestaurantService(adminUser);

        assertEquals(null,testRestaurant);
    }


    @Test
    public void getRestaurantsServiceScenario() {

        Restaurant sampleRestaurant = new Restaurant();
        sampleRestaurant.setName("Yummy");

        Restaurant sampleRestaurant1 = new Restaurant();
        sampleRestaurant1.setName("Yummy1");

        List<Restaurant> restaurantList = new ArrayList<>();
        restaurantList.add(sampleRestaurant);
        restaurantList.add(sampleRestaurant1);

        when(restaurantRepository.findAll()).thenReturn(restaurantList);

        List<Restaurant> testRestaurantList = restaurantServiceImpl.getRestaurantsService();

        assertEquals(restaurantList,testRestaurantList);
    }
}
