package ro.utcluj.foodpanda.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ro.utcluj.foodpanda.dto.LoginDTO;
import ro.utcluj.foodpanda.dto.MessageResponse;
import ro.utcluj.foodpanda.dto.OrderDTO;
import ro.utcluj.foodpanda.dto.SetUpDTO;
import ro.utcluj.foodpanda.mapper.OrderMapper;
import ro.utcluj.foodpanda.model.*;

import ro.utcluj.foodpanda.security.jwt.JwtUtils;
import ro.utcluj.foodpanda.security.services.UserDetailsImpl;
import ro.utcluj.foodpanda.service.RoleService;
import ro.utcluj.foodpanda.service.UserService;
import ro.utcluj.foodpanda.security.jwt.JwtResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class UserController{

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/home")
    public String allAccess() {
        return "Welcome To Food Panda";
    }

    /**
     * Method used to authenticate client, by checking if a client exists in the database with the
     * given information
     * @param loginDTO the information obtained from the front-end in dto form
     * @return the response, all the user details of the client
     */
    @PostMapping("/login-customer")
    public ResponseEntity<?> authenticateClientUser(@RequestBody LoginDTO loginDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getName(), loginDTO.getPassword()));

        logger.info("User is authenticated");

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        logger.info("User details are extracted like username {}", userDetails.getUsername());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }


    /**
     * The Method is used to authenticate the Administrator by using the service to look if the admin
     * exists.
     * @param loginDTO is the data coming from the frontend and has the given name and password.
     * @return an error if the admin is not found and an ok message if the admin exists, along with the administrator.
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateAdminUser(@RequestBody LoginDTO loginDTO) {


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getName(), loginDTO.getPassword()));

        logger.info("User is authenticated");

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        logger.info("User details are extracted like username {}", userDetails.getUsername());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    /**
     * Method used to create an account for a new client.
     * @param setUpDTO the needed data for set-up in dto form
     * @return an error if the username is already taken and a success message, if it works
     */
    @PostMapping("/set-up")
    public ResponseEntity<?> registerClientUser(@RequestBody SetUpDTO setUpDTO) {

        if (userService.existsByUsernameService(setUpDTO.getUsername())) {
            logger.error("Cannot register client, username already taken");
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userService.existsByEmailService(setUpDTO.getEmail())) {
            logger.error("Cannot register client, email already taken");
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        User clientUser = new User(setUpDTO.getUsername(),
                                   setUpDTO.getEmail(),
                                   encoder.encode(setUpDTO.getPassword()));

        logger.info("User has been created {}",clientUser.getUsername());

        Role userRole = roleService.getByNameService(ERole.ROLE_CLIENT);

        clientUser.setRole(userRole);
        userService.saveClientService(clientUser);

        logger.info("User has been saved");

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    /**
     * The method returns a list with wll the orders of the given client.
     * @param name the name used to find a customer
     * @return the list of all the orders
     */
    @GetMapping("/get-client-orders")
    public ResponseEntity<List<OrderDTO>> findClientUserOrders(@RequestParam String name) {

        User clientUser = userService.getClientUserWithNameService(name);

        logger.info("User has been found {}",clientUser.getUsername());

        List<OrderDTO> orderDTOList = new ArrayList<>();
        List<Food> clientUserFood = clientUser.getFood();

        for(Food food: clientUserFood){

            Restaurant restaurant = food.getRestaurant();

            logger.info("Restaurant has been found {}",restaurant.getName());

            OrderMapper orderMapper = OrderMapper.getInstance();

            OrderDTO orderDTO = orderMapper.convertToDTO(food,restaurant,clientUser);

            orderDTOList.add(orderDTO);

            logger.info("Order has been added to the DTO");
        }

        return new ResponseEntity<List<OrderDTO>>(orderDTOList,HttpStatus.OK);
    }


    /**
     * The method takes all the food that has been ordered so far and returns it to the frontend
     * @param name the name of the administrator used to get the food
     * @return the list of food returned as dtos
     */
    @GetMapping("/get-admin-orders")
    public ResponseEntity<List<OrderDTO>> findClientUserOrdersForAdmin(@RequestParam String name) {

        User adminUser = userService.getAdminUserWithNameService(name);

        logger.info("Admin whose orders to find has been found {}",adminUser.getUsername());

        Restaurant restaurant = adminUser.getRestaurant();

        logger.info("Restaurant whose orders to find has been found {}",restaurant.getName());

        List<Food> foodList = restaurant.getFood();

        List<OrderDTO> orderDTOList = new ArrayList<>();

        for(Food f: foodList){

            List<User> clientList = f.getClient();

            for(User c: clientList){

                OrderMapper orderMapper = OrderMapper.getInstance();

                OrderDTO orderDTO = orderMapper.convertToDTO(f,restaurant,c);

                orderDTOList.add(orderDTO);

                logger.info("Order has been added to the DTO");
            }
        }

        return new ResponseEntity<List<OrderDTO>>(orderDTOList,HttpStatus.OK);
    }


    /**
     * The method is used to find the name of the restaurant using the administrators name
     * @param name the name of the administrator
     * @return the name of the restaurant
     */
    @GetMapping("/get-restaurant")
    public ResponseEntity<String> findRestaurantName(@RequestParam String name) {


        User adminUser = userService.getAdminUserWithNameService(name);

        if(adminUser==null){
            logger.error("Cannot find Restaurant, admin does not exist");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.info("Admin whose restaurant to find has been found {}",adminUser.getUsername());

        Restaurant restaurant = adminUser.getRestaurant();

        if(restaurant==null) {
            logger.error("Cannot find Restaurant, it does not exist");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.info("Restaurant has been found {}",restaurant.getName());

        return new ResponseEntity<>(restaurant.getName(),HttpStatus.OK);
    }


    /**
     * Method used by the foodController to get the client using the given string
     * @param clientUserName the  name of the client
     * @return the client itself
     */
    public User findClientUserForFood(String clientUserName) {

        User clientUser = userService.getClientUserWithNameService(clientUserName);

        if(clientUser==null){
            logger.error("Cannot find client, does not exist");
            return null;
        }

        logger.info("Client has been found {}",clientUser.getUsername());

        return clientUser;
    }

    /**
     * Method used to update the customer after changing some variables
     * @param clientUser the client to be updated
     */
    public void updateClientUserForFood(User clientUser) {

        userService.updateClientUserService(clientUser);
    }


    /**
     * The method returns the administrator of a restaurant. It is used by the restaurant controller
     * to get the administrator, needed for some operation.
     * @param adminUserName the name taken from the front end
     * @return the administrator found with the name
     */
    public User findAdminUserForRestaurant(String adminUserName) {

        User adminUser = userService.getAdminUserWithNameService(adminUserName);

        if(adminUser==null){
            logger.error("Cannot find admin, does not exist");
            return null;
        }

        logger.info("Admin has been found {}",adminUser.getUsername());
        return adminUser;
    }

}
