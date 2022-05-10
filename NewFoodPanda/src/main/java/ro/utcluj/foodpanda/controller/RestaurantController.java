package ro.utcluj.foodpanda.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.utcluj.foodpanda.dto.FoodDTO;
import ro.utcluj.foodpanda.dto.MessageResponse;
import ro.utcluj.foodpanda.dto.RestaurantDTO;
import ro.utcluj.foodpanda.mapper.FoodMapper;
import ro.utcluj.foodpanda.mapper.RestaurantMapper;
import ro.utcluj.foodpanda.mapper.RestaurantMapperProxy;
import ro.utcluj.foodpanda.model.Food;
import ro.utcluj.foodpanda.model.Restaurant;
import ro.utcluj.foodpanda.model.User;
import ro.utcluj.foodpanda.service.RestaurantService;

import java.util.ArrayList;
import java.util.List;


@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api3")
public class RestaurantController{

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    UserController userController;

    private static final Logger logger = LoggerFactory.getLogger(RestaurantController.class);


    /**
     * Method returns all existing restaurants
     * @return the list of restaurants.
     */
    @GetMapping("/get-all-restaurants")
    public ResponseEntity<List<RestaurantDTO>> returnAllRestaurants() {

        List<Restaurant> restaurantList = restaurantService.getRestaurantsService();

        RestaurantMapper restaurantMapper = new RestaurantMapperProxy();

        List<RestaurantDTO> restaurantDTOList = restaurantMapper.convertToDTO(restaurantList);

        if(restaurantDTOList == null) {
            logger.error("The list of restaurants is empty");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.info("Restaurant list successfully retrieved");
        return new ResponseEntity<>(restaurantDTOList,HttpStatus.OK);

    }

    /**
     * Method gets the restaurant with the name given as parameter.
     * @param name the string used to search for the restaurant
     * @return the restaurant found.
     */
    @GetMapping("/get-full-restaurant")
    public ResponseEntity<List<RestaurantDTO>> returnRestaurant(@RequestParam String name) {

        if(name.isEmpty() || name==null){
            logger.warn("No name was given, all restaurants will be retrieved");
           return returnAllRestaurants();
        }

        Restaurant restaurant = restaurantService.getRestaurantWithNameService(name);

        RestaurantMapper restaurantMapper = new RestaurantMapperProxy();

        RestaurantDTO restaurantDTO = restaurantMapper.convertToDTOOneRestaurant(restaurant);

        if(restaurantDTO==null){
            logger.error("The list of restaurants is empty");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        List<RestaurantDTO> restaurantDTOList = new ArrayList<>();
        restaurantDTOList.add(restaurantDTO);

        logger.error("Restaurant has been found {}",restaurantDTOList);

        return new ResponseEntity<>(restaurantDTOList,HttpStatus.OK);

    }

    /**
     * A new restaurant is created using the restaurant dto to get the necessary data from the frontend to
     * the backend
     * @param restaurantDTO the object carrying all the information needed to create a restaurant
     * @return a success/error message
     */
    @PostMapping("/add-restaurant")
    public ResponseEntity<?> createRestaurant(@RequestBody RestaurantDTO restaurantDTO) {


        if(restaurantDTO.getAdministrator().isEmpty() || restaurantDTO.getLocation().isEmpty()
                || restaurantDTO.getName().isEmpty() || restaurantDTO.getDelivery_zones().isEmpty()){
            logger.error("Cannot create Restaurant, not all spaces are filled in");
            return ResponseEntity.badRequest().body(new MessageResponse("Please fill in all the blanks"));
        }

        User adminUser = userController.findAdminUserForRestaurant(restaurantDTO.getAdministrator());

        if(adminUser==null){
            logger.error("Cannot create Restaurant, no admin was found with the name {}",restaurantDTO.getAdministrator());
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Admin not found!"));
        }

        logger.info("Admin User has been found");

        Restaurant restaurant = restaurantService.getRestaurantService(adminUser);

        if(restaurant!=null){
            logger.error("Cannot create Restaurant, admin has a restaurant {}",restaurant.getName());
            return ResponseEntity.badRequest().body(new MessageResponse("This Admin already has a restaurant!"));
        }

        RestaurantMapper restaurantMapper = new RestaurantMapperProxy();

        Restaurant convertedRestaurant = restaurantMapper.convertFromDTO(restaurantDTO);
        convertedRestaurant.setUser(adminUser);

        try{
            restaurantService.saveRestaurantService(convertedRestaurant);
            logger.info("Restaurant created");
            return new ResponseEntity<>(convertedRestaurant, HttpStatus.CREATED);
        }catch(Exception e){
            logger.error("There was a problem creating the restaurant {}",e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * The method gets all the food available at the given restaurant.
     * @param restaurant name of the restaurant used to find the restaurant and then its food.
     * @return the food list of the restaurant.
     */
    @GetMapping("/get-foods")
    public ResponseEntity<List<FoodDTO>> getFood(@RequestParam String restaurant) {

        Restaurant theRestaurant = restaurantService.getRestaurantWithNameService(restaurant);

        if(theRestaurant==null){
            logger.error("Restaurant has not been found with the name provided {}",restaurant);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        List<Food> foodList = theRestaurant.getFood();

        if(foodList==null){
            logger.error("Restaurant has no food in it");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        FoodMapper foodMapper = FoodMapper.getInstance();

        List<FoodDTO> foodDTOList = foodMapper.convertToDTO(foodList);

        logger.info("Food list has been retreived {}",foodDTOList);

        return new ResponseEntity<>(foodDTOList,HttpStatus.OK);

    }


    /**
     * The method is used by the foodController to get its restaurant based on the name.
     * @param restaurantName the name of the restaurant.
     * @return the restaurant.
     */
    public Restaurant getRestaurantForFood(String restaurantName) {

        Restaurant restaurant = restaurantService.getRestaurantWithNameService(restaurantName);

        if(restaurant==null)
            return null;

        return restaurant;
    }

    /**
     * Method updates the information of a restaurant
     * @param restaurant the restaurant.
     */
    public void updateForFood(Restaurant restaurant) {

        restaurantService.updateRestaurantService(restaurant);
    }
}
