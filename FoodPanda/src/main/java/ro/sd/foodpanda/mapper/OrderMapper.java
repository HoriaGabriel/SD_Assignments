package ro.sd.foodpanda.mapper;

import ro.sd.foodpanda.dto.FoodDTO;
import ro.sd.foodpanda.dto.OrderDTO;
import ro.sd.foodpanda.model.Client;
import ro.sd.foodpanda.model.Food;
import ro.sd.foodpanda.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class OrderMapper {

    private static OrderMapper orderMapper = new OrderMapper();

    private OrderMapper() { }

    public static OrderMapper getInstance(){
        return orderMapper;
    }

    public OrderDTO convertToDTO(Food f, Restaurant r, Client c) {

        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setClient(c.getUsername());
        orderDTO.setFood(f.getName());
        orderDTO.setRestaurant(r.getName());

        return orderDTO;
    }


}
