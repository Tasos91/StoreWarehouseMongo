package com.example.StoreWarehouseMongo1.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Tasos
 */
@Document(collection = "products")
public class Product implements Comparable<Product> {

    @Id
    private String id;
    private String sku;
    private String costUsd;
    private String costEu;
    private String price;
    private String description;
    private String karats;
    private String goldWeight;
    private String silverWeight;
    private String color;
    private Integer quantity;
    private String imageUrl;
    private boolean nonProduce;
    private String otherStone;
    private String otherStoneWeight;
    private String diamondWeight;
    private String categoryId;
    private String producerId;
    private String address;

    public Product() {
    }

    public Product(String sku, String costUsd, String costEu, String price, String description, String karats, String goldWeight, String silverWeight, String color, Integer quantity, String imageUrl, boolean nonProduce, String otherStone, String otherStoneWeight, String diamondWeight, String categoryId, String producerId, String address) {
        this.sku = sku;
        this.costUsd = costUsd;
        this.costEu = costEu;
        this.price = price;
        this.description = description;
        this.karats = karats;
        this.goldWeight = goldWeight;
        this.silverWeight = silverWeight;
        this.color = color;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
        this.nonProduce = nonProduce;
        this.otherStone = otherStone;
        this.otherStoneWeight = otherStoneWeight;
        this.diamondWeight = diamondWeight;
        this.categoryId = categoryId;
        this.producerId = producerId;
        this.address = address;
    }
    
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getsku() {
        return sku;
    }

    public void setsku(String sku) {
        this.sku = sku;
    }

    public String getcostUsd() {
        return costUsd;
    }

    public void setcostUsd(String costUsd) {
        this.costUsd = costUsd;
    }

    public String getcostEu() {
        return costEu;
    }

    public void setcostEu(String costEu) {
        this.costEu = costEu;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getdescription() {
        return description;
    }

    public void setdescription(String description) {
        this.description = description;
    }

    public String getKarats() {
        return karats;
    }

    public void setKarats(String karats) {
        this.karats = karats;
    }

    public String getGoldWeight() {
        return goldWeight;
    }

    public void setGoldWeight(String goldWeight) {
        this.goldWeight = goldWeight;
    }

    public String getSilverWeight() {
        return silverWeight;
    }

    public void setSilverWeight(String silverWeight) {
        this.silverWeight = silverWeight;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isnonProduce() {
        return nonProduce;
    }

    public void setnonProduce(boolean nonProduce) {
        this.nonProduce = nonProduce;
    }

    public String getOtherStone() {
        return otherStone;
    }

    public void setOtherStone(String otherStone) {
        this.otherStone = otherStone;
    }

    public String getOtherStoneWeight() {
        return otherStoneWeight;
    }

    public void setOtherStoneWeight(String otherStoneWeight) {
        this.otherStoneWeight = otherStoneWeight;
    }

    public String getDiamondWeight() {
        return diamondWeight;
    }

    public void setDiamondWeight(String diamondWeight) {
        this.diamondWeight = diamondWeight;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getProducerId() {
        return producerId;
    }

    public void setProducerId(String producerId) {
        this.producerId = producerId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();

        String jsonString = "";
        try {
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            jsonString = mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonString;
    }

    @Override
    public int compareTo(Product product) {
        return this.getsku().compareTo(product.getsku());
    }

}
