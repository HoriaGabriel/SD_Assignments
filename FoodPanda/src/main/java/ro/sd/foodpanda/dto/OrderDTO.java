package ro.sd.foodpanda.dto;

import org.springframework.lang.NonNull;

public class OrderDTO {

    @NonNull
    private String client;

    @NonNull
    private String food;

    @NonNull
    private String restaurant;

    public OrderDTO() { }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(@NonNull String restaurant) {
        this.restaurant = restaurant;
    }
}
