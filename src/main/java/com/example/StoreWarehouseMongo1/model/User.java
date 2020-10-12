package com.example.StoreWarehouseMongo1.model;

import java.util.List;
import javax.validation.constraints.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 *
 * @author Tasos
 */
@Document(collection = "users")
public class User {

    @Id
    private String id;
    @Indexed(unique = true, direction = IndexDirection.DESCENDING, dropDups = true)
    @NotNull(message = "Username cannot be null")
    private String username;
    @Indexed(unique = true, direction = IndexDirection.DESCENDING, dropDups = true)
    @NotNull(message = "Email cannot be null")
    @Email
    private String email;
    @NotNull(message = "Password cannot be null")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[-@#$%^&+*()!_=])(?=\\S+$).{6,10}$",
    message = "The password length must be from 6 to 10 characters and has the following restrictions: must contain at least 1 number, a lower case letter must occur at least once, " +
            "an upper case letter must occur at least once, a special character must occur at least once eg. (!@#$%^&*())," +
            " no whitespace allowed in the entire string ")
    private String password;
    @NotNull(message = "Fullname cannot be null")
    @Size(min=0, max=20)
    @NotBlank
    private String fullname;
    private boolean enabled;
    private boolean admin;
    private String[] roles;

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public User(String username, String password, String email, String fullname, String... roles) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullname = fullname;
        this.roles = roles;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

}