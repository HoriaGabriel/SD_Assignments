package ro.utcluj.foodpanda.dto;

import com.sun.istack.NotNull;

/**
 * Class used to get the login information from the frontend
 */
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
