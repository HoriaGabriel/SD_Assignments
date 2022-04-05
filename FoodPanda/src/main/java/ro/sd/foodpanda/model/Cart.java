package ro.sd.foodpanda.model;

import java.util.ArrayList;
import java.util.List;

public class Cart implements CustomerObserver{

    private List<Food> foodList = new ArrayList<>();
    private Integer finalPrice;

    public Cart() {
        this.finalPrice = 0;
    }

    public List<Food> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<Food> foodList) {
        this.foodList = foodList;
    }

    public int getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(int finalPrice) {
        this.finalPrice = finalPrice;
    }

    @Override
    public void update(Food f) {
        foodList.add(f);
        finalPrice=finalPrice+f.getPrice();
    }
}
