package ro.sd.foodpanda.model;

import java.util.List;

public interface CustomerObserver {

    void update(Food f);

    int getFinalPrice();

    List<Food> getFoodList();

    void setFinalPrice(int i);
}
