package ro.utcluj.foodpanda.mapper;

import ro.utcluj.foodpanda.dto.OrderDTO;
import ro.utcluj.foodpanda.model.Food;
import ro.utcluj.foodpanda.model.Restaurant;
import ro.utcluj.foodpanda.model.User;

public class OrderMapper {

    private static OrderMapper orderMapper = new OrderMapper();

    private OrderMapper() { }

    public static OrderMapper getInstance(){
        return orderMapper;
    }

    /**
     * Method uses the given data to create a new order dto
     * @param f the food whose name is needed
     * @param r the restaurant whose name is needed
     * @param c the client whose name is needed
     * @return the order dto created
     */
    public OrderDTO convertToDTO(Food f, Restaurant r, User c) {

        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setClient(c.getUsername());
        orderDTO.setFood(f.getName());
        orderDTO.setRestaurant(r.getName());

        return orderDTO;
    }


}
