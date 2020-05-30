package com.example.StoreWarehouseMongo1.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import javax.validation.constraints.*;

/**
 *
 * @author Tasos
 */
@Document(collection = "products")
public class Product implements Comparable<Product> {

    @Id
    private String id;

    @Pattern(regexp = "^[a-zA-Z0-9]{5,5}$",
            message = "Address must be contained of 2-24 characters (letters only)")
    @NotBlank
    private String sku;

    @Size(min=3, max=8)
    @Pattern(regexp = "^(\\d{2,5}\\.)\\d+$",
            message = "You must include dot")
    private String costUsd;

    @Size(min=3, max=8)
    @Pattern(regexp = "^(\\d{1,5}\\.)\\d+$",
            message = "You must include dot")
    private String costEu;

    @Size(min=3, max=9)
    @Pattern(regexp = "^(\\d{1,6}\\.)\\d+$",
            message = "You must include dot")
    private String price;

    @Size(min=0, max=500)
    private String description;

    @Min(value = 9)
    @Max(value = 24)
    private Integer karats;

    @Size(min=3, max=5)
    @Pattern(regexp = "^(\\d{1,2}\\.)\\d+$",
            message = "You must include dot")
    private String goldWeight;

    private String color;

    @Min(value = 0)
    @Max(value = 1000)
    private Integer quantity;

    private String imageUrl;

    private boolean nonProduce;

    @Size(min=0, max=100)
    private String otherStone;

    @Size(min=3, max=5)
    @Pattern(regexp = "^(\\d{1,2}\\.)\\d+$",
            message = "You must include dot")
    private String otherStoneWeight;

    @Size(min=3, max=5)
    @Pattern(regexp = "^(\\d{1,2}\\.)\\d+$",
            message = "You must include dot")
    private String diamondWeight;

    @Pattern(regexp = "^[a-zA-Z0-9]{24,24}$",
            message = "Category Id must be contained of 24 characters (numbers and letters only)")
    private String categoryId;

    @Pattern(regexp = "^[a-zA-Z0-9]{24,24}$",
            message = "Producer Id must be contained of 24 characters (numbers and letters only)")
    private String producerId;

    @Pattern(regexp = "^[a-zA-Z]{2,20}$",
            message = "Address must be contained of 2-24 characters (letters only)")
    private String address;

    public Product() {
    }

    public Product(String sku, String costUsd, String costEu, String price, String description, Integer karats, String goldWeight, String color, Integer quantity, String imageUrl, boolean nonProduce, String otherStone, String otherStoneWeight, String diamondWeight, String categoryId, String producerId, String address) {
        this.sku = sku;
        this.costUsd = costUsd;
        this.costEu = costEu;
        this.price = price;
        this.description = description;
        this.karats = karats;
        this.goldWeight = goldWeight;
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

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getCostUsd() {
        return costUsd;
    }

    public void setCostUsd(String costUsd) {
        this.costUsd = costUsd;
    }

    public String getCostEu() {
        return costEu;
    }

    public void setCostEu(String costEu) {
        this.costEu = costEu;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getKarats() {
        return karats;
    }

    public void setKarats(Integer karats) {
        this.karats = karats;
    }

    public String getGoldWeight() {
        return goldWeight;
    }

    public void setGoldWeight(String goldWeight) {
        this.goldWeight = goldWeight;
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

    public boolean isNonProduce() {
        return nonProduce;
    }

    public void setNonProduce(boolean nonProduce) {
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
        return this.getSku().compareTo(product.getSku());
    }

}
