package ro.utcluj.foodpanda.mapper;

import ro.utcluj.foodpanda.dto.FoodDTO;
import ro.utcluj.foodpanda.model.Category;
import ro.utcluj.foodpanda.model.Food;

import java.util.ArrayList;
import java.util.List;

public class FoodMapper {

    private static FoodMapper foodMapper = new FoodMapper();

    private FoodMapper() { }

    public static FoodMapper getInstance(){
        return foodMapper;
    }

    /**
     * The method transforms the food dto given as parameter into a new food item
     * @param foodDTO the dto taken from the front-end
     * @return the new food
     */
    public static Food convertFromDTO(FoodDTO foodDTO){

        Food f = new Food();
        f.setName(foodDTO.getName());
        f.setDescription(foodDTO.getDescription());
        f.setPrice(Integer.valueOf(foodDTO.getPrice()));

        try{
            Category c = Category.valueOf(foodDTO.getCategory());
            f.setCategory(c);
        } catch(IllegalArgumentException e){
            return null;
        }

        return f;
    }

    /**
     * The method converts a list of food items into dtos
     * @param foodList the food items
     * @return the list of dtos
     */
    public static List<FoodDTO> convertToDTO(List<Food> foodList) {

        List<FoodDTO> foodDTOList = new ArrayList<>();

        for(Food f: foodList){

            FoodDTO foodDTO = new FoodDTO();
            foodDTO.setId(f.getFood_id().toString());
            foodDTO.setName(f.getName());
            foodDTO.setDescription(f.getDescription());
            foodDTO.setPrice(f.getPrice().toString());
            foodDTO.setCategory(f.getCategory().toString());

            foodDTOList.add(foodDTO);
        }

        return foodDTOList;
    }
}
