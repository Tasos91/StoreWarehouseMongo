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
    private String productcode;
    private String cost_usd;
    private String cost_eu;
    private String price;
    private String descr;
    private String karats;
    private String goldWeight;
    private String silverWeight;
    private String color;
    private Integer quantity;
    private String imageUrl;
    private boolean non_produce;
    private String otherStone;
    private String otherStoneWeight;
    private String diamondWeight;
    private String categoryId;
    private String producerId;
    private Category category;
    private Producer producer;
    private String address;

    public Product() {
    }

    public Product(String productcode, String cost_usd, String cost_eu, String price, String descr, String karats, String goldWeight, String silverWeight, String color, Integer quantity, String imageUrl, boolean non_produce, String otherStone, String otherStoneWeight, String diamondWeight, String categoryId, String producerId, Category category, Producer producer, String address) {
        this.productcode = productcode;
        this.cost_usd = cost_usd;
        this.cost_eu = cost_eu;
        this.price = price;
        this.descr = descr;
        this.karats = karats;
        this.goldWeight = goldWeight;
        this.silverWeight = silverWeight;
        this.color = color;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
        this.non_produce = non_produce;
        this.otherStone = otherStone;
        this.otherStoneWeight = otherStoneWeight;
        this.diamondWeight = diamondWeight;
        this.categoryId = categoryId;
        this.producerId = producerId;
        this.category = category;
        this.producer = producer;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }

    public String getCost_usd() {
        return cost_usd;
    }

    public void setCost_usd(String cost_usd) {
        this.cost_usd = cost_usd;
    }

    public String getCost_eu() {
        return cost_eu;
    }

    public void setCost_eu(String cost_eu) {
        this.cost_eu = cost_eu;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
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

    public boolean isNon_produce() {
        return non_produce;
    }

    public void setNon_produce(boolean non_produce) {
        this.non_produce = non_produce;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
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
        return this.getProductcode().compareTo(product.getProductcode());
    }

}
