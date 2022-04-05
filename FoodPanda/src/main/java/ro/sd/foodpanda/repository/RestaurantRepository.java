package ro.sd.foodpanda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.sd.foodpanda.model.Administrator;
import ro.sd.foodpanda.model.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {

    Restaurant findByAdministrator(Administrator administrator);
    Restaurant findByName(String name);
}
