package com.example.StoreWarehouseMongo1.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Tasos
 */

@Document(collection="stores")
public class Store {
    
    @Id
    private String id;
    private String address;
    private String username;
    private String password;
    private Boolean enabledUser;

    public Store(String address, String username, String password, Boolean enabledUser) {
        this.address = address;
        this.username = username;
        this.password = password;
        this.enabledUser = enabledUser;
    }

    public Boolean getEnabledUser() {
        return enabledUser;
    }

    public void setEnabledUser(Boolean enabledUser) {
        this.enabledUser = enabledUser;
    }



    public Store(){
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
