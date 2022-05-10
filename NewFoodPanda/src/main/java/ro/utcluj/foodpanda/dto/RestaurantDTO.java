package ro.utcluj.foodpanda.dto;

import com.sun.istack.NotNull;

/**
 * Class used to get the restaurant information from the frontend and then convert it to a real restaurant
 */
public class RestaurantDTO {

    @NotNull
    private String name;

    @NotNull
    private String id;

    @NotNull
    private String location;

    @NotNull
    private String delivery_zones;

    @NotNull
    private String administrator;

    public RestaurantDTO() {
    }

    public RestaurantDTO(String name, String location, String delivery_zones, String administrator) {
        this.name = name;
        this.location = location;
        this.delivery_zones = delivery_zones;
        this.administrator = administrator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDelivery_zones() {
        return delivery_zones;
    }

    public void setDelivery_zones(String delivery_zones) {
        this.delivery_zones = delivery_zones;
    }

    public String getAdministrator() {
        return administrator;
    }

    public void setAdministrator(String administrator) {
        this.administrator = administrator;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
