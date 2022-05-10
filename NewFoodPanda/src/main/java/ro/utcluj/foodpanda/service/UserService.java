package ro.utcluj.foodpanda.service;

import ro.utcluj.foodpanda.model.User;

public interface UserService {

    void saveClientService(User clientUser);
    void updateClientUserService(User clientUser);

    User getAdminUserWithNameService(String adminUserName);
    User getClientUserWithNameService(String clientUser);

    boolean existsByUsernameService(String username);
    boolean existsByEmailService(String email);
}
