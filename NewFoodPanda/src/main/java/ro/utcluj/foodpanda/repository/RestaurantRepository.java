package ro.utcluj.foodpanda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.utcluj.foodpanda.model.User;
import ro.utcluj.foodpanda.model.Restaurant;

/**
 * The restaurant repository connected to the database
 */
public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {

    Restaurant findByUser(User user);
    Restaurant findByName(String name);
}
