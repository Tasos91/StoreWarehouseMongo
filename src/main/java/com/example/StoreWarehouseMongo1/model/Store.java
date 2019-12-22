package com.example.StoreWarehouseMongo1.model;

import java.util.List;
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
    private String address;
    @DBRef
    private List<User> users;
    @DBRef
    private List<Product> products;

    public Store() {
    }

    public Store(String address, List<User> users, List<Product> products) {
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
