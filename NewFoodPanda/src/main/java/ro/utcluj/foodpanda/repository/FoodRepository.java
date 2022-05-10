package ro.utcluj.foodpanda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.utcluj.foodpanda.model.Food;
import ro.utcluj.foodpanda.model.Restaurant;

import java.util.List;

/**
 * The food repository connected to the database
 */
public interface FoodRepository extends JpaRepository<Food,Long> {

    List<Food> findByRestaurant(Restaurant restaurant);

    Food findByName(String food);
}
