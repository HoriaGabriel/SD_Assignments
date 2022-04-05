package ro.sd.foodpanda.service;

import ro.sd.foodpanda.model.Client;

public interface CustomerService {

    Client findCustomer(String name, String password);

    void saveClient(Client client);

    boolean existsByUsername(String username);

    Client findCustomerWithName(String client);

    void updateCustomer(Client client1);
}
