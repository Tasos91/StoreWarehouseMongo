package com.example.StoreWarehouseMongo1.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import software.amazon.ion.Decimal;

import javax.validation.constraints.*;

/**
 *
 * @author Tasos
 */
@Document(collection = "products")
public class Product implements Comparable<Product> {

    @Id
    private String id;

    @Size(min=5, max=5)
    @NotBlank
    private String sku;

    @DecimalMin(value = "10.0", inclusive = true)
    @DecimalMax(value = "99999.99", inclusive = true)
    private Decimal costUsd;

    @DecimalMin(value = "10.0", inclusive = true)
    @DecimalMax(value = "99999.99", inclusive = true)
    private Decimal costEu;

    @DecimalMin(value = "10.0", inclusive = true)
    @DecimalMax(value = "999999.99", inclusive = true)
    private Decimal price;

    @Size(min=0, max=500)
    private String description;

    @Min(value = 9)
    @Max(value = 24)
    private Integer karats;

    @DecimalMin(value = "0.01", inclusive = true)
    @DecimalMax(value = "99.99", inclusive = true)
    private Decimal goldWeight;

    private String color;

    @Min(value = 0)
    @Max(value = 1000)
    private Integer quantity;

    private String imageUrl;

    private boolean nonProduce;

    @Size(min=0, max=100)
    private String otherStone;

    @DecimalMin(value = "0.001", inclusive = true)
    @DecimalMax(value = "99.99", inclusive = true)
    private Decimal otherStoneWeight;

    @DecimalMin(value = "0.001", inclusive = true)
    @DecimalMax(value = "99.99", inclusive = true)
    private Decimal diamondWeight;

    private String categoryId;

    private String producerId;

    private String address;



    public Product() {
    }

    public Product(String sku, Decimal costUsd, Decimal costEu, Decimal price, String description, Integer karats, Decimal goldWeight, String color, Integer quantity, String imageUrl, boolean nonProduce, String otherStone, Decimal otherStoneWeight, Decimal diamondWeight, String categoryId, String producerId, String address) {
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

    public Decimal getCostUsd() {
        return costUsd;
    }

    public void setCostUsd(Decimal costUsd) {
        this.costUsd = costUsd;
    }

    public Decimal getCostEu() {
        return costEu;
    }

    public void setCostEu(Decimal costEu) {
        this.costEu = costEu;
    }

    public Decimal getPrice() {
        return price;
    }

    public void setPrice(Decimal price) {
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

    public Decimal getGoldWeight() {
        return goldWeight;
    }

    public void setGoldWeight(Decimal goldWeight) {
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

    public Decimal getOtherStoneWeight() {
        return otherStoneWeight;
    }

    public void setOtherStoneWeight(Decimal otherStoneWeight) {
        this.otherStoneWeight = otherStoneWeight;
    }

    public Decimal getDiamondWeight() {
        return diamondWeight;
    }

    public void setDiamondWeight(Decimal diamondWeight) {
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
