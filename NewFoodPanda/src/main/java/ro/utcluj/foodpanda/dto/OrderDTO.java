package ro.utcluj.foodpanda.dto;

import org.springframework.lang.NonNull;

/**
 * Class used to get the order information from the frontend such as the name of the client, the name
 * of the food and the name of the restaurant.
 */
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
