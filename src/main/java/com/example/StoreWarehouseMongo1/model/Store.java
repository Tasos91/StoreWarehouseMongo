package com.example.StoreWarehouseMongo1.model;

import java.util.List;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Tasos
 */
@Document(collection = "stores")
public class Store {

    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    private String address;
    private String username;
    private String password;
    private Boolean enabledUser;
    @DBRef
    private List<User> users;
    @DBRef
    private List<Product> products;

    public Store() {
    }

    public Store(String address, String username, String password, Boolean enabledUser, List<User> users, List<Product> products) {
        this.address = address;
        this.username = username;
        this.password = password;
        this.enabledUser = enabledUser;
        this.users = users;
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Boolean getEnabledUser() {
        return enabledUser;
    }

    public void setEnabledUser(Boolean enabledUser) {
        this.enabledUser = enabledUser;
    }

//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

}
