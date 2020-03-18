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
    private String color;
    private String productId;
    private Integer quantity;
    private String gold_weight;
    private String stoneWeight;

    public String getStoneWeight() {
        return stoneWeight;
    }

    public void setStoneWeight(String stoneWeight) {
        this.stoneWeight = stoneWeight;
    }
    private String karats;

    public String getKarats() {
        return karats;
    }

    public void setKarats(String karats) {
        this.karats = karats;
    }

    public Stock(String color, String productId, Integer quantity, String gold_weight, String silver_weight, String imageUrl, String karats, String stoneWeight) {
        this.color = color;
        this.productId = productId;
        this.quantity = quantity;
        this.gold_weight = gold_weight;
        this.silver_weight = silver_weight;
        this.imageUrl = imageUrl;
        this.karats = karats;
        this.stoneWeight = stoneWeight;
    }
    private String silver_weight;

    public String getGold_weight() {
        return gold_weight;
    }

    public void setGold_weight(String gold_weight) {
        this.gold_weight = gold_weight;
    }

    public String getSilver_weight() {
        return silver_weight;
    }

    public void setSilver_weight(String silver_weight) {
        this.silver_weight = silver_weight;
    }
    private String imageUrl;

    public Stock(String color, String productId, Integer quantity, String imageUrl) {
        this.color = color;
        this.productId = productId;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Stock() {
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Stock(String color, String productId, Integer quantity) {
        this.color = color;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Stock(String color) {
        this.color = color;
    }

}
