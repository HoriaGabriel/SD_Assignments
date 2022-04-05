package ro.sd.foodpanda.mapper;

import ro.sd.foodpanda.dto.FoodDTO;
import ro.sd.foodpanda.model.Category;
import ro.sd.foodpanda.model.Food;

import java.util.ArrayList;
import java.util.List;

public class FoodMapper {

    private static FoodMapper foodMapper = new FoodMapper();

    private FoodMapper() { }

    public static FoodMapper getInstance(){
        return foodMapper;
    }

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
