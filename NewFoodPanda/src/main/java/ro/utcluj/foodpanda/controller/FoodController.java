package ro.utcluj.foodpanda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.utcluj.foodpanda.dto.FoodDTO;
import ro.utcluj.foodpanda.dto.MessageResponse;
import ro.utcluj.foodpanda.dto.OrderDTO;
import ro.utcluj.foodpanda.mapper.FoodMapper;
import ro.utcluj.foodpanda.model.*;
import ro.utcluj.foodpanda.service.FoodService;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api4")
public class FoodController {

    @Autowired
    FoodService foodService;

    @Autowired
    UserController userController;

    @Autowired
    RestaurantController restaurantController;

    EmailSender emailSender = new EmailSender();

    private List<CustomerObserver> observers = new ArrayList<>();

    int firstItemInCart=0;

    String restaurant = null;

    private static final Logger logger = LoggerFactory.getLogger(FoodController.class);

    /**
     * The Method is used to create a  new Food and add it to a restaurants menu.
     * @param foodDTO the needed information for creating a new food item in dto form
     * @return a message of success or error depending on the result of the operation
     */
    @PostMapping("/add-food")
    public ResponseEntity<?> addFood(@RequestBody FoodDTO foodDTO) {

        if(foodDTO.getCategory().isEmpty() || foodDTO.getName().isEmpty() || foodDTO.getDescription().isEmpty()
           || foodDTO.getPrice().isEmpty()){
            logger.error("Cannot add Food, not all spaces are filled in");
            return ResponseEntity.badRequest().body(new MessageResponse("Please fill in all the blanks"));
        }

        if(Integer.valueOf(foodDTO.getPrice())<=0){
            logger.error("Cannot add Food, the price is negative");
            return ResponseEntity.badRequest().body(new MessageResponse("Negative Price!"));
        }

        FoodMapper foodMapper = FoodMapper.getInstance();

        Food food = foodMapper.convertFromDTO(foodDTO);

        if(food==null){
            logger.warn("The foods category is incorrect");
            return ResponseEntity.badRequest().body(new MessageResponse("Incorrect Category"));
        }

        logger.info("New food item is here and has name {}",food.getName());

        Restaurant restaurant= restaurantController.getRestaurantForFood(foodDTO.getRestaurant());

        if(restaurant==null){
            logger.error("Cannot add Food, the restaurant was not found");
            return ResponseEntity.badRequest().body(new MessageResponse("Restaurant not found"));
        }

        logger.info("Restaurant has been found and has name {}",restaurant.getName());

        food.setRestaurant(restaurant);

        foodService.saveFoodService(food);

        logger.info("Food has been saved");

        restaurant.getFood().add(food);

        restaurantController.updateForFood(restaurant);

        logger.info("Restaurant has been saved");

        return new ResponseEntity<>(food,HttpStatus.CREATED);

    }

    /**
     * The method adds the item in dto form to the cart if this product is the first to be added the cart is
     * created first. The observer pattern is used to update/create the cart.
     * @param orderDTO the dto of the order
     * @return a message of error or success
     */
    @PostMapping("/add-to-cart")
    public ResponseEntity<?> addToCart(@RequestBody OrderDTO orderDTO) {

        if(firstItemInCart==0){

            logger.info("The first item is to be added to the cart.");

            firstItemInCart=1;
            if(orderDTO.getFood().isEmpty() || orderDTO.getClient().isEmpty()){
                logger.error("Not all order gaps have been filled in.");
                return ResponseEntity.badRequest().body(new MessageResponse("Please fill in all the gaps!"));
            }
            else{
                logger.info("The order has the food {}",orderDTO.getFood());
                logger.info("The order has the client {}",orderDTO.getClient());
                Food food = foodService.getFoodWithNameService(orderDTO.getFood());
                if(food==null)
                    return ResponseEntity.badRequest().body(new MessageResponse("No food"));
                restaurant=food.getRestaurant().getName();
                addObserver(new Cart());
                logger.info("Observer cart has been added");
            }
        }

        if(firstItemInCart==1){

            logger.info("The first item has been added, this is a subsequent one");

            if(orderDTO.getFood().isEmpty() || orderDTO.getClient().isEmpty()){
                logger.error("Not all order gaps have been filled in.");
                return ResponseEntity.badRequest().body(new MessageResponse("Please fill in all the gaps!"));
            }

            Food food = foodService.getFoodWithNameService(orderDTO.getFood());

            if(food==null){
                logger.error("Food was not found");
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            logger.info("Food has been found {}",food.getName());

            if(food.getRestaurant().getName().compareTo(restaurant)!=0){
                logger.error("Food was ordered from a different restaurant");
                return ResponseEntity.badRequest().body(new MessageResponse("Order Food from same restaurant!"));
            }

            notifyCart(food);

            logger.info("Cart has been notified");

            return ResponseEntity.ok(new MessageResponse("Food added successfully!"));

        }

        return ResponseEntity.badRequest().body(new MessageResponse("Something went wrong!"));

    }

    /**
     * Method used to create a new Cart when shopping starts
     * @param cart the cart to be created
     */
    private void addObserver(Cart cart) {

        logger.info("New observer is added {}");
        observers.add(cart);
    }

    /**
     * The cart is notified wheneever a new product is added.
     * @param food the food to pe added to the cart.
     */
    private void notifyCart(Food food) {

        logger.info("New food is added",food.getName());
        for(CustomerObserver c: observers){
            c.update(food);
        }

    }

    /**
     * Method gets the price of the products added to the cart.
     * @return the final price
     */
    @GetMapping("/get-price")
    public ResponseEntity<String> returnPrice() {

        for(CustomerObserver customerObserver: observers){

            Integer aux = customerObserver.getFinalPrice();

            logger.info("Carts final price has been calculated {}",aux);

            String s = aux.toString();

            return new ResponseEntity<String>(s,HttpStatus.OK);

        }

        return new ResponseEntity<String>((String) null, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /**
     * The method returns the product of the order from the cart
     * @return the list of orders.
     */
    @GetMapping("/get-order")
    public ResponseEntity<List<String>> returnOrder() {

        for(CustomerObserver customerObserver: observers){

            List<Food> foodList = customerObserver.getFoodList();

            logger.info("Carts list of products is here {}",foodList);

            List<String> auxList = new ArrayList<>();

            for(Food food: foodList){
                auxList.add(food.getName());
            }

            return new ResponseEntity<List<String>>(auxList,HttpStatus.OK);

        }
        return new ResponseEntity<List<String>>(Collections.singletonList((String) null), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Method makes the order by adding the food from the cart to the clients list and vice-versa.
     * At the end the cart is reset. The email class is called to send an email to the administrator.
     * @param client the client who made the order
     * @return a succes/error message
     * @throws IOException
     * @throws MessagingException
     */
    @PutMapping("/make-order")
    public ResponseEntity<?> makeOrder(@RequestParam String client) throws IOException, MessagingException {

        User clientUser = userController.findClientUserForFood(client);

        logger.info("Client has been identified {}",clientUser.getUsername());

        List<Food> foodList = new ArrayList<>();

        for(CustomerObserver customerObserver: observers){

            for(Food food: customerObserver.getFoodList()){

                food.getClient().add(clientUser);

                foodService.updateFoodService(food);

                clientUser.getFood().add(food);
                foodList.add(food);

                logger.info("Order is being made");
            }

            emailSender.sendmail(foodList,customerObserver.getFinalPrice());

            logger.info("Email has been sent with final price {}",customerObserver.getFinalPrice());

            customerObserver.getFoodList().clear();
            customerObserver.setFinalPrice(0);

            firstItemInCart=0;
            restaurant=null;

            logger.info("Cart has been reset");

            userController.updateClientUserForFood(clientUser);

        }
        return ResponseEntity.ok(new MessageResponse("Order made successfully!"));
    }
}
