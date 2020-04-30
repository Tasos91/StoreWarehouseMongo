package com.example.StoreWarehouseMongo1.model;

import java.util.List;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @Size(min = 6, max = 10, message = "Password must equal or grater than 6 characters and less than 10 characters")
    private String password;
    @NotNull(message = "Fullname cannot be null")
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
