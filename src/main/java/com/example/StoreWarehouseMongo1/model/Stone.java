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

@Document(collection="stones")
public class Stone {
    
    @Id
    private String id;
    private String weight;
    private String stone_desc;
    private String quantity;
    
    public Stone(){
    }

    public Stone(String id, String weight, String stone_desc, String quantity) {
        this.id = id;
        this.weight = weight;
        this.stone_desc = stone_desc;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getStone_desc() {
        return stone_desc;
    }

    public void setStone_desc(String stone_desc) {
        this.stone_desc = stone_desc;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
