package ro.sd.foodpanda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.sd.foodpanda.dto.FoodDTO;
import ro.sd.foodpanda.dto.MessageResponse;
import ro.sd.foodpanda.dto.RestaurantDTO;
import ro.sd.foodpanda.mapper.FoodMapper;
import ro.sd.foodpanda.mapper.RestaurantMapper;
import ro.sd.foodpanda.mapper.RestaurantMapperProxy;
import ro.sd.foodpanda.model.Administrator;
import ro.sd.foodpanda.model.Food;
import ro.sd.foodpanda.model.Restaurant;
import ro.sd.foodpanda.service.RestaurantService;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api3")
public class RestaurantController {

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    AdministratorController administratorController;

    @GetMapping("/get-all-restaurants")
    public ResponseEntity<List<RestaurantDTO>> getAllRestaurants() {

        List<Restaurant> restaurantList = restaurantService.getRestaurants();

        RestaurantMapper restaurantMapper = new RestaurantMapperProxy();

        List<RestaurantDTO> restaurantDTOList = restaurantMapper.convertToDTO(restaurantList);

        if(restaurantDTOList == null)
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(restaurantDTOList,HttpStatus.OK);

    }

    @GetMapping("/get-full-restaurant")
    public ResponseEntity<List<RestaurantDTO>> getRestaurant(@RequestParam String name) {

        if(name.isEmpty() || name==null){
           return getAllRestaurants();
        }

        Restaurant restaurant = restaurantService.getRestaurantWithName(name);

        RestaurantMapper restaurantMapper = new RestaurantMapperProxy();

        RestaurantDTO restaurantDTO = restaurantMapper.convertToDTOOneRestaurant(restaurant);

        if(restaurantDTO==null)
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        List<RestaurantDTO> restaurantDTOList = new ArrayList<>();
        restaurantDTOList.add(restaurantDTO);

        return new ResponseEntity<>(restaurantDTOList,HttpStatus.OK);

    }

    @PostMapping("/add-restaurant")
    public ResponseEntity<?> createRestaurant(@RequestBody RestaurantDTO restaurantDTO) {

        if(restaurantDTO.getAdministrator().isEmpty() || restaurantDTO.getLocation().isEmpty()
                || restaurantDTO.getName().isEmpty() || restaurantDTO.getDelivery_zones().isEmpty())
            return ResponseEntity.badRequest().body(new MessageResponse("Please fill in all the blanks"));

        Administrator administrator = administratorController.getAdminForRestaurant(restaurantDTO.getAdministrator());

        if(administrator==null){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Admin not found!"));
        }

        Restaurant restaurant = restaurantService.findRestaurant(administrator);

        if(restaurant!=null){
            return ResponseEntity.badRequest().body(new MessageResponse("This Admin already has a restaurant!"));
        }

        RestaurantMapper restaurantMapper = new RestaurantMapperProxy();

        Restaurant restaurant1 = restaurantMapper.convertFromDTO(restaurantDTO);
        restaurant1.setAdministrator(administrator);

        try{
            restaurantService.saveRestaurant(restaurant1);
            return new ResponseEntity<>(restaurant1, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-foods")
    public ResponseEntity<List<FoodDTO>> getFood(@RequestParam String restaurant) {

        Restaurant restaurant1= restaurantService.getRestaurantWithName(restaurant);

        if(restaurant1==null){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        List<Food> foodList = restaurant1.getFood();

        if(foodList==null){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        FoodMapper foodMapper = FoodMapper.getInstance();

        List<FoodDTO> foodDTOList = foodMapper.convertToDTO(foodList);

        return new ResponseEntity<>(foodDTOList,HttpStatus.OK);

    }



    public Restaurant getRestaurantForFood(String restaurant) {

        Restaurant res = restaurantService.getRestaurantWithName(restaurant);

        if(res==null)
            return null;

        return res;
    }

    public void updateForFood(Restaurant res) {

        restaurantService.updateRestaurant(res);
    }
}
