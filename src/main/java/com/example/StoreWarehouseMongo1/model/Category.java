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
@Document(collection = "category")
public class Category {

    @Id
    private String id;
    private String kindOfCategory;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKindOfCategory() {
        return kindOfCategory;
    }

    public void setKindOfCategory(String kindOfCategory) {
        this.kindOfCategory = kindOfCategory;
    }

    public Category(String id, String kindOfCategory) {
        this.id = id;
        this.kindOfCategory = kindOfCategory;
    }
 
}
