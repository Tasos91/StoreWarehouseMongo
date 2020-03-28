package com.example.StoreWarehouseMongo1.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Tasos
 */
@Document(collection = "stock")
public class Stock implements Comparable<Stock> {

    @Id
    private String id;
    private String color;
    private String productId;
    private Integer quantity;
    private String gold_weight; 
    private String other_stoneWeight;
    private String imageUrl;
    private String karats;
    private String silver_weight;
    private boolean non_produce;
    private String other_stone;
    private String diamond_weight;
    private String categoryId;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Stock(String id, String color, String productId, Integer quantity, String gold_weight, String other_stoneWeight, String imageUrl, String karats, String silver_weight, boolean non_produce, String other_stone, String diamond_weight, String categoryId) {
        this.id = id;
        this.color = color;
        this.productId = productId;
        this.quantity = quantity;
        this.gold_weight = gold_weight;
        this.other_stoneWeight = other_stoneWeight;
        this.imageUrl = imageUrl;
        this.karats = karats;
        this.silver_weight = silver_weight;
        this.non_produce = non_produce;
        this.other_stone = other_stone;
        this.diamond_weight = diamond_weight;
        this.categoryId = categoryId;
    }

    public Stock(String color, String productId, Integer quantity, String gold_weight, boolean non_produce, String other_stone, String diamond_weight, String karats, String silver_weight, String imageUrl, String other_stoneWeight) {
        this.color = color;
        this.productId = productId;
        this.quantity = quantity;
        this.gold_weight = gold_weight;
        this.non_produce = non_produce;
        this.other_stone = other_stone;
        this.diamond_weight = diamond_weight;
        this.karats = karats;
        this.silver_weight = silver_weight;
        this.imageUrl = imageUrl;
        this.other_stoneWeight = other_stoneWeight;
    }

    public Stock(String color, String productId, Integer quantity, String gold_weight, String other_stoneWeight, boolean non_produce, String other_stone, String diamond_weight, String karats, String silver_weight, String imageUrl) {
        this.color = color;
        this.productId = productId;
        this.quantity = quantity;
        this.gold_weight = gold_weight;
        this.other_stoneWeight = other_stoneWeight;
        this.non_produce = non_produce;
        this.other_stone = other_stone;
        this.diamond_weight = diamond_weight;
        this.karats = karats;
        this.silver_weight = silver_weight;
        this.imageUrl = imageUrl;
    }

    public String getOther_stone() {
        return other_stone;
    }

    public String getOther_stoneWeight() {
        return other_stoneWeight;
    }

    public void setOther_stoneWeight(String other_stoneWeight) {
        this.other_stoneWeight = other_stoneWeight;
    }

    public void setOther_stone(String other_stone) {
        this.other_stone = other_stone;
    }

    public String getDiamond_weight() {
        return diamond_weight;
    }

    public void setDiamond_weight(String diamond_weight) {
        this.diamond_weight = diamond_weight;
    }

    public boolean isNon_produce() {
        return non_produce;
    }

    public void setNon_produce(boolean non_produce) {
        this.non_produce = non_produce;
    }

    public String getKarats() {
        return karats;
    }

    public void setKarats(String karats) {
        this.karats = karats;
    }

    public Stock(String color, String productId, Integer quantity, String gold_weight, String silver_weight, String imageUrl, String karats, String other_stoneWeight, boolean non_produce) {
        this.color = color;
        this.productId = productId;
        this.quantity = quantity;
        this.gold_weight = gold_weight;
        this.silver_weight = silver_weight;
        this.imageUrl = imageUrl;
        this.karats = karats;
        this.other_stoneWeight = other_stoneWeight;
        this.non_produce = non_produce;
    }

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

    @Override
    public int compareTo(Stock stock) {
        return this.getProductId().compareTo(stock.getProductId());
    }
    
     @Override
    public String toString() {
        return "productId: " + productId +"";
    }

}
