package ro.sd.foodpanda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.sd.foodpanda.model.Food;
import ro.sd.foodpanda.model.Restaurant;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food,Long> {


    List<Food> getByRestaurant(Restaurant restaurant);

    Food getByName(String food);
}
