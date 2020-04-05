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
    private String gold_weight;
    private String silver_weight;
    private String color;
    private Integer quantity;
    private String imageUrl;
    private boolean non_produce;
    private String other_stone;
    private String other_stoneWeight;
    private String diamond_weight;
    private String categoryId;
    private String producerId;
    private String storeId;
    private Category category;
    private Producer producer;

    public Product(String productcode, String cost_usd, String cost_eu, String price, String descr, String karats, String gold_weight, String silver_weight, String color, Integer quantity, String imageUrl, boolean non_produce, String other_stone, String other_stoneWeight, String diamond_weight, String categoryId, String producerId, String storeId, Category category, Producer producer) {
        this.productcode = productcode;
        this.cost_usd = cost_usd;
        this.cost_eu = cost_eu;
        this.price = price;
        this.descr = descr;
        this.karats = karats;
        this.gold_weight = gold_weight;
        this.silver_weight = silver_weight;
        this.color = color;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
        this.non_produce = non_produce;
        this.other_stone = other_stone;
        this.other_stoneWeight = other_stoneWeight;
        this.diamond_weight = diamond_weight;
        this.categoryId = categoryId;
        this.producerId = producerId;
        this.storeId = storeId;
        this.category = category;
        this.producer = producer;
    }

    public Product(String productcode, String cost_usd, String cost_eu, String price, String descr, String karats, String gold_weight, String silver_weight, String color, Integer quantity, String imageUrl, boolean non_produce, String other_stone, String other_stoneWeight, String diamond_weight, String categoryId, String producerId, String storeId) {
        this.productcode = productcode;
        this.cost_usd = cost_usd;
        this.cost_eu = cost_eu;
        this.price = price;
        this.descr = descr;
        this.karats = karats;
        this.gold_weight = gold_weight;
        this.silver_weight = silver_weight;
        this.color = color;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
        this.non_produce = non_produce;
        this.other_stone = other_stone;
        this.other_stoneWeight = other_stoneWeight;
        this.diamond_weight = diamond_weight;
        this.categoryId = categoryId;
        this.producerId = producerId;
        this.storeId = storeId;
    }
    
    

    public String getKarats() {
        return karats;
    }

    public void setKarats(String karats) {
        this.karats = karats;
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

    public Product(String productcode, String cost_usd, String cost_eu, String price, String descr, String karats, String gold_weight, String silver_weight) {
        this.productcode = productcode;
        this.cost_usd = cost_usd;
        this.cost_eu = cost_eu;
        this.price = price;
        this.descr = descr;
        this.karats = karats;
        this.gold_weight = gold_weight;
        this.silver_weight = silver_weight;
    }

    public Product(String productcode, Category category, String cost_usd, String cost_eu, String price, String producer_code, String descr) {
        this.cost_usd = cost_usd;
        this.cost_eu = cost_eu;
        this.price = price;
        this.descr = descr;

    }

    public Product(String productcode) {
        this.productcode = productcode;
    }

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }

    public Product(String productcode, String cost_usd, String cost_eu, String price, String gold_weight, String silver_weight, String descr) {
        this.productcode = productcode;
        this.cost_usd = cost_usd;
        this.cost_eu = cost_eu;
        this.price = price;
        this.descr = descr;
    }

    public String getCost_usd() {
        return cost_usd;
    }

    public void setCost_usd(String cost_usd) {
        this.cost_usd = cost_usd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Product() {
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

    public String getOther_stone() {
        return other_stone;
    }

    public void setOther_stone(String other_stone) {
        this.other_stone = other_stone;
    }

    public String getOther_stoneWeight() {
        return other_stoneWeight;
    }

    public void setOther_stoneWeight(String other_stoneWeight) {
        this.other_stoneWeight = other_stoneWeight;
    }

    public String getDiamond_weight() {
        return diamond_weight;
    }

    public void setDiamond_weight(String diamond_weight) {
        this.diamond_weight = diamond_weight;
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

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
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
