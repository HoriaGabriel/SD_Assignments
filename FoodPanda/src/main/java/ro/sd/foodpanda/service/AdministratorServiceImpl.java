package ro.sd.foodpanda.service;

import org.springframework.stereotype.Service;
import ro.sd.foodpanda.model.Administrator;
import ro.sd.foodpanda.repository.AdministratorRepository;
import ro.sd.foodpanda.repository.RestaurantRepository;

import java.util.List;

@Service
public class AdministratorServiceImpl implements  AdministratorService{

    private AdministratorRepository administratorRepository;

    private RestaurantRepository restaurantRepository;

    public AdministratorServiceImpl(AdministratorRepository administratorRepository,RestaurantRepository restaurantRepository) {
        this.administratorRepository = administratorRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Administrator findAdmin(String name, String password) {

        List<Administrator> list = administratorRepository.findAll();
        for(Administrator i: list)
        {
            if(i.getUsername().equals(name) && i.getPassword().equals(password))
                return i;
        }
        return null;
    }

    @Override
    public Administrator findAdminWithName(String administrator) {

        Administrator admin = administratorRepository.findByUsername(administrator);

        if(admin==null)
            return null;
        else
            return admin;
    }
}
