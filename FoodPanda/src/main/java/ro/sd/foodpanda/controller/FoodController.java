package ro.sd.foodpanda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.sd.foodpanda.dto.FoodDTO;
import ro.sd.foodpanda.dto.MessageResponse;
import ro.sd.foodpanda.dto.OrderDTO;
import ro.sd.foodpanda.mapper.FoodMapper;
import ro.sd.foodpanda.model.*;
import ro.sd.foodpanda.service.FoodService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api4")
public class FoodController {

    @Autowired
    FoodService foodService;

    @Autowired
    CustomerController customerController;

    @Autowired
    RestaurantController restaurantController;

    private List<CustomerObserver> observers = new ArrayList<>();

    int aux=0;

    String restaurant = null;

    @PostMapping("/add-food")
    public ResponseEntity<?> addFood(@RequestBody FoodDTO foodDTO) {

        if(foodDTO.getCategory().isEmpty() || foodDTO.getName().isEmpty() || foodDTO.getDescription().isEmpty()
           || foodDTO.getPrice().isEmpty())
            return ResponseEntity.badRequest().body(new MessageResponse("Please fill in all the blanks"));

        if(Integer.valueOf(foodDTO.getPrice())<=0)
            return ResponseEntity.badRequest().body(new MessageResponse("Negative Price!"));

        FoodMapper foodMapper = FoodMapper.getInstance();

        Food f = foodMapper.convertFromDTO(foodDTO);

        if(f==null){
            return ResponseEntity.badRequest().body(new MessageResponse("Incorrect Category"));
        }

        Restaurant res= restaurantController.getRestaurantForFood(foodDTO.getRestaurant());

        if(res==null){
            return ResponseEntity.badRequest().body(new MessageResponse("Restaurant not found"));
        }

        f.setRestaurant(res);

        foodService.saveFood(f);

        res.getFood().add(f);

        restaurantController.updateForFood(res);

        return new ResponseEntity<>(f,HttpStatus.CREATED);

    }

    @PostMapping("/add-to-cart")
    public ResponseEntity<?> addToCart(@RequestBody OrderDTO orderDTO) {

        if(aux==0){
            aux=1;
            if(orderDTO.getFood().isEmpty() || orderDTO.getClient().isEmpty())
                return ResponseEntity.badRequest().body(new MessageResponse("Please fill in all the gaps!"));
            else{
                Food f = foodService.findFoodWithName(orderDTO.getFood());
                restaurant=f.getRestaurant().getName();
                addObserver(new Cart());
            }
        }

        if(aux==1){

            if(orderDTO.getFood().isEmpty() || orderDTO.getClient().isEmpty())
                return ResponseEntity.badRequest().body(new MessageResponse("Please fill in all the gaps!"));

            Food food = foodService.findFoodWithName(orderDTO.getFood());

            if(food==null)
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

            if(food.getRestaurant().getName().compareTo(restaurant)!=0){
                return ResponseEntity.badRequest().body(new MessageResponse("Order Food from same restaurant!"));
            }

            notifyCart(food);
            return ResponseEntity.ok(new MessageResponse("Food added successfully!"));

        }

        return ResponseEntity.badRequest().body(new MessageResponse("Something went wrong!"));

    }

    private void addObserver(Cart cart) {
        observers.add(cart);
    }

    private void notifyCart(Food f) {

        for(CustomerObserver c: observers){
            c.update(f);
        }

    }


    @GetMapping("/get-price")
    public ResponseEntity<String> getPrice() {

        for(CustomerObserver c: observers){

            Integer aux = c.getFinalPrice();

            String s = aux.toString();

            return new ResponseEntity<String>(s,HttpStatus.OK);

        }

        return new ResponseEntity<String>((String) null, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @GetMapping("/get-order")
    public ResponseEntity<List<String>> getOrder() {

        for(CustomerObserver c: observers){

            List<Food> aux = c.getFoodList();

            List<String> s = new ArrayList<>();

            for(Food f: aux){
                s.add(f.getName());
            }

            return new ResponseEntity<List<String>>(s,HttpStatus.OK);

        }

        return new ResponseEntity<List<String>>(Collections.singletonList((String) null), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/make-order")
    public ResponseEntity<?> makeOrder(@RequestParam String client) {

        Client client1 = customerController.findCustomerForFood(client);

        for(CustomerObserver c: observers){

            for(Food f: c.getFoodList()){

                f.getClient().add(client1);

                foodService.updateFood(f);

                client1.getFood().add(f);
            }

            c.getFoodList().clear();
            c.setFinalPrice(0);

            aux=0;
            restaurant=null;

            customerController.updateCustomerForFood(client1);

        }

        return ResponseEntity.ok(new MessageResponse("Order made successfully!"));
    }
}
