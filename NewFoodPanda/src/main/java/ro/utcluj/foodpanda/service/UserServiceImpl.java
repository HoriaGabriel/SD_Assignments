package ro.utcluj.foodpanda.service;

import static ro.utcluj.foodpanda.model.ERole.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ro.utcluj.foodpanda.model.Role;
import ro.utcluj.foodpanda.model.User;
import ro.utcluj.foodpanda.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository) { this.userRepository = userRepository; }

    /**
     * The method searches for the administrator using its name
     * @param adminUserName the name of the administrator
     * @return the found administrator or null if there is none
     */
    @Override
    public User getAdminUserWithNameService(String adminUserName) {

        logger.info("Searching for Admin with given name {}",adminUserName);

        User adminUser = userRepository.findByUsername(adminUserName);

        if(adminUser==null){
            logger.error("Admin not found");
            return null;
        }

        Role role = adminUser.getRole();

        if(role.getName().equals(ROLE_ADMIN)) {
            logger.info("Admin has been found and has proper role {}",adminUser);
            return adminUser;
        }

        logger.error("Admin does not have proper role");
        return null;
    }


    /**
     * The method saves a new client into the database
     * @param clientUser the client to be saved
     */
    @Override
    public void saveClientService(User clientUser) {

        logger.info("Client will be saved {}",clientUser.getUsername());

        userRepository.save(clientUser);

        logger.info("Client has been saved {}",clientUser.getUsername());
    }

    /**
     * The method searches for the customer using the name
     * @param username of the customer
     * @return true or false if there is none
     */
    @Override
    public boolean existsByUsernameService(String username) {

        logger.info("We will see if the client exists by name {}",username);

        User clientUser = userRepository.findByUsername(username);

        if(clientUser==null){
            logger.error("Client not found");
            return false;
        }

        logger.info("Client has been found {}",clientUser.getUsername());

        return true;
    }

    /**
     * The method searches for the customer using the name
     * @param clientUserName name of the customer
     * @return the found customer or null if there is none
     */
    @Override
    public User getClientUserWithNameService(String clientUserName) {

        logger.info("We will get the client by name {}",clientUserName);

        User clientUser = userRepository.findByUsername(clientUserName);

        if(clientUser==null){
            logger.error("Client not found");
            return null;
        }

        Role role = clientUser.getRole();

        if(role.getName().equals(ROLE_CLIENT)) {
            logger.info("Client has been found and has proper role {}",clientUser);
            return clientUser;
        }

        logger.error("Client does not have proper role");
        return null;
    }

    /**
     * The method updates the given customer
     * @param clientUser the client to be updated
     */
    @Override
    public void updateClientUserService(User clientUser) {

        logger.info("Client will be updated {}",clientUser.getUsername());

        userRepository.save(clientUser);

        logger.info("Client has been updated {}",clientUser.getUsername());
    }

    /**
     * The method searches for the client using the email
     * @param email of the client
     * @return true or false if there is none
     */
    @Override
    public boolean existsByEmailService(String email) {

        logger.info("We will see if the client exists by email {}",email);

        User clientUser = userRepository.findByEmail(email);

        if(clientUser==null){
            logger.error("Client not found");
            return false;
        }

        logger.info("Client has been found {}",clientUser.getUsername());

        return true;
    }
}
