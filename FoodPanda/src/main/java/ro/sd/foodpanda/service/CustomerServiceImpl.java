package ro.sd.foodpanda.service;

import org.springframework.stereotype.Service;
import ro.sd.foodpanda.model.Client;
import ro.sd.foodpanda.repository.CustomerRepository;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService{

    CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Client findCustomer(String name, String password) {

        List<Client> list = customerRepository.findAll();
        for(Client i: list)
        {
            if(i.getUsername().equals(name) && i.getPassword().equals(password))
                return i;
        }
        return null;
    }

    @Override
    public void saveClient(Client client) {

        customerRepository.save(client);
    }

    @Override
    public boolean existsByUsername(String username) {

        Client c = customerRepository.findByUsername(username);

        if(c==null)
            return false;

        return true;
    }

    @Override
    public Client findCustomerWithName(String client) {

        Client c = customerRepository.findByUsername(client);

        if(c==null)
            return null;

        return c;
    }

    @Override
    public void updateCustomer(Client client1) {

        customerRepository.save(client1);
    }
}
