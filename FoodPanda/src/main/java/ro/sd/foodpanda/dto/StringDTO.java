package ro.sd.foodpanda.dto;

import org.springframework.lang.NonNull;

public class StringDTO {

    @NonNull
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
