package ro.sd.foodpanda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.sd.foodpanda.dto.LoginDTO;
import ro.sd.foodpanda.dto.MessageResponse;
import ro.sd.foodpanda.dto.OrderDTO;
import ro.sd.foodpanda.mapper.OrderMapper;
import ro.sd.foodpanda.model.Administrator;
import ro.sd.foodpanda.model.Client;
import ro.sd.foodpanda.model.Food;
import ro.sd.foodpanda.model.Restaurant;
import ro.sd.foodpanda.service.AdministratorService;

import java.util.ArrayList;
import java.util.List;


@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class AdministratorController {

    @Autowired
    AdministratorService administratorService;

    @GetMapping("/home")
    public String allAccess() {
        return "Welcome To Food Panda";
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateAdmin(@RequestBody LoginDTO loginDTO) {

        Administrator administrator = administratorService.findAdmin(loginDTO.getName(), loginDTO.getPassword());

        if(administrator==null){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Admin not found!"));
        }else{
            return ResponseEntity.ok(administrator);
        }
    }

    public Administrator getAdminForRestaurant(String name) {

        Administrator admin = administratorService.findAdminWithName(name);

        if(admin==null)
            return null;

        return admin;
    }

    @GetMapping("/get-admin-orders")
    public ResponseEntity<List<OrderDTO>> getCustomerOrders(@RequestParam String name) {

        Administrator administrator = administratorService.findAdminWithName(name);

        Restaurant restaurant = administrator.getRestaurant();

        List<Food> foodList = restaurant.getFood();

        List<OrderDTO> orderDTOList = new ArrayList<>();

        for(Food f: foodList){

            List<Client> clientList =f.getClient();

            for(Client c: clientList){

                OrderMapper orderMapper = OrderMapper.getInstance();

                OrderDTO orderDTO = orderMapper.convertToDTO(f,restaurant,c);

                orderDTOList.add(orderDTO);
            }
        }

        return new ResponseEntity<List<OrderDTO>>(orderDTOList,HttpStatus.OK);
    }

    @GetMapping("/get-restaurant")
    public ResponseEntity<String> getRestaurantName(@RequestParam String name) {

        Administrator admin = administratorService.findAdminWithName(name);

        if(admin==null){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Restaurant restaurant = admin.getRestaurant();

        if(restaurant==null) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(restaurant.getName(),HttpStatus.OK);
    }

}
