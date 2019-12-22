/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.StoreWarehouseMongo1.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Tasos
 */
@Document(collection = "stock")
public class Stock {

    @Id
    private String id;
    private Color color;
    private Store store;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Stock(Color color, Store store) {
        this.color = color;
        this.store = store;
    }

    public Stock(Color color) {
        this.color = color;
    }

}
