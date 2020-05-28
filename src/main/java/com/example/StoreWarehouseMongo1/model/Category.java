/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.StoreWarehouseMongo1.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 *
 * @author Tasos
 */
@Document(collection = "category")
public class Category {

    
    @Id
    private String id;

    @Size(min=0, max=30)
    @NotBlank
    private String value;

    public Category() {
    }

    public Category(String id, String value) {
        this.id = id;
        this.value = value;
    }

    public Category(String value) {
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
