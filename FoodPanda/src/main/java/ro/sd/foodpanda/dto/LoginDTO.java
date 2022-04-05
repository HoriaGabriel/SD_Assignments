package ro.sd.foodpanda.dto;

import com.sun.istack.NotNull;

public class LoginDTO {

    @NotNull
    private String username;

    @NotNull
    private String password;

    public LoginDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getName() {
        return username;
    }

    public void setName(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
