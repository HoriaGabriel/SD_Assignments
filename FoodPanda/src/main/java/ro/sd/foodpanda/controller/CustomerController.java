package ro.sd.foodpanda.controller;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.sd.foodpanda.dto.LoginDTO;
import ro.sd.foodpanda.dto.MessageResponse;
import ro.sd.foodpanda.dto.OrderDTO;
import ro.sd.foodpanda.dto.SetUpDTO;
import ro.sd.foodpanda.mapper.OrderMapper;
import ro.sd.foodpanda.model.*;
import ro.sd.foodpanda.service.CustomerService;

import java.util.ArrayList;
import java.util.List;


@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api2")
public class CustomerController{

    @Autowired
    CustomerService customerService;

    @PostMapping("/login-customer")
    public ResponseEntity<?> authenticateClient(@RequestBody LoginDTO loginDTO) {

        String encodedString = Base64.encodeBase64String(loginDTO.getPassword().getBytes());

        Client customer = customerService.findCustomer(loginDTO.getName(), encodedString);

        if(customer==null){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Customer not found!"));
        }else{
            return ResponseEntity.ok(customer);
        }
    }

    @PostMapping("/set-up")
    public ResponseEntity<?> registerClient(@RequestBody SetUpDTO setUpDTO) {

        if (customerService.existsByUsername(setUpDTO.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        String encodedString = Base64.encodeBase64String(setUpDTO.getPassword().getBytes());

        Client client = new Client(setUpDTO.getUsername(), encodedString, setUpDTO.getEmail());

        customerService.saveClient(client);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    public Client findCustomerForFood(String client) {

        Client client1 = customerService.findCustomerWithName(client);

        if(client1==null)
            return null;

        return client1;
    }

    public void updateCustomerForFood(Client client1) {

        customerService.updateCustomer(client1);
    }

    @GetMapping("/get-client-orders")
    public ResponseEntity<List<OrderDTO>> getCustomerOrders(@RequestParam String name) {

        Client client1 = customerService.findCustomerWithName(name);

        List<OrderDTO> orderDTOList = new ArrayList<>();
        List<Food> client1Food = client1.getFood();

        for(Food f: client1Food){
            Restaurant restaurant = f.getRestaurant();

            OrderMapper orderMapper = OrderMapper.getInstance();

            OrderDTO orderDTO = orderMapper.convertToDTO(f,restaurant,client1);

            orderDTOList.add(orderDTO);
        }

        return new ResponseEntity<List<OrderDTO>>(orderDTOList,HttpStatus.OK);
    }
}
