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
    private List<Stock> stock;

    public Store() {
    }

    public Store(String address, List<User> users, List<Stock> stock) {
        this.address = address;
        this.users = users;
        this.stock = stock;
    }

    public List<Stock> getStock() {
        return stock;
    }

    public void setStock(List<Stock> stock) {
        this.stock = stock;
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
