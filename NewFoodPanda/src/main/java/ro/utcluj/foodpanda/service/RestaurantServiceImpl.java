package ro.utcluj.foodpanda.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ro.utcluj.foodpanda.model.Restaurant;
import ro.utcluj.foodpanda.model.User;
import ro.utcluj.foodpanda.repository.RestaurantRepository;
import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService{

    RestaurantRepository restaurantRepository;

    private static final Logger logger = LoggerFactory.getLogger(RestaurantServiceImpl.class);

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    /**
     * The method returns a restaurant who belong to the administrator
     * @param user the restaurants administrator
     * @return the restaurant
     */
    @Override
    public Restaurant getRestaurantService(User user) {

        logger.info("Searching for Restaurant with given user {}",user.getUsername());

        Restaurant restaurant = restaurantRepository.findByUser(user);

        if(restaurant == null){
            logger.error("Restaurant is null");
            return null;
        }

        logger.info("Restaurant has been found {}",restaurant.getName());
        return restaurant;
    }

    /**
     * The method searches for the restaurant using the name
     * @param restaurantName name of the restaurant
     * @return the found restaurant or null if there is none
     */
    @Override
    public Restaurant getRestaurantWithNameService(String restaurantName) {

        logger.info("Searching for Restaurant with given name {}",restaurantName);

        Restaurant restaurant= restaurantRepository.findByName(restaurantName);

        if(restaurant == null){
            logger.error("Restaurant is null");
            return null;
        }

        logger.info("Restaurant has been found {}",restaurant.getName());

        return restaurant;
    }

    /**
     * The method updates the given restaurant
     * @param restaurant the restaurant to be updated
     */
    @Override
    public void updateRestaurantService(Restaurant restaurant) {

        logger.info("Restaurant will be updated {}",restaurant.getName());

        restaurantRepository.save(restaurant);

        logger.info("Restaurant has been updated {}",restaurant.getName());
    }

    /**
     * The method returns all the restaurants from the database
     * @return the restaurants
     */
    @Override
    public List<Restaurant> getRestaurantsService() {

        logger.info("Searching for Restaurants");

        List<Restaurant> restaurantList = restaurantRepository.findAll();
        if(restaurantList.isEmpty() || restaurantList==null){
            logger.error("Restaurants are null");
            return null;
        }
        logger.info("Restaurants have been found {}",restaurantList);
        return restaurantList;
    }

    /**
     * The method saves a new restaurant into the database
     * @param restaurant the restaurant to be saved
     */
    @Override
    public void saveRestaurantService(Restaurant restaurant) {

        logger.info("Restaurant will be saved {}",restaurant.getName());

        restaurantRepository.save(restaurant);

        logger.info("Restaurant has been saved {}",restaurant.getName());
    }
}
