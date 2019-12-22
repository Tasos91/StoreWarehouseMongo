package com.example.StoreWarehouseMongo1.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Tasos
 */
@Document(collection = "products")
public class Product {

    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    private String productcode;
    private String cost_usd;
    private String cost_eu;
    private String price;
    private String producer_code;
    private String gold_weight;
    private String silver_weight;
    private String descr;
    private String karats;
    private List<Stone> stones; //proswrina vgazw ta stones apo to constructor
    private Category category;
    @DBRef
    private List<Stock> stock;

    public Product(String productcode, Category category, List<Stock> stock) {
        this.productcode = productcode;
        this.category = category;
        this.stock = stock;
    }

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }

    public Product(String id, String productcode, String cost_usd, String cost_eu, String price, String producer_code, String gold_weight, String silver_weight, String descr, String karats, Category category, List<Stock> stock) {
        this.id = id;
        this.productcode = productcode;
        this.cost_usd = cost_usd;
        this.cost_eu = cost_eu;
        this.price = price;
        this.producer_code = producer_code;
        this.gold_weight = gold_weight;
        this.silver_weight = silver_weight;
        this.descr = descr;
        this.karats = karats;
        this.category = category;
        this.stock = stock;
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

    public String getProducer_code() {
        return producer_code;
    }

    public void setProducer_code(String producer_code) {
        this.producer_code = producer_code;
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

    public List<Stone> getStones() {
        return stones;
    }

    public void setStones(List<Stone> stones) {
        this.stones = stones;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Stock> getStock() {
        return stock;
    }

    public void setStock(List<Stock> stock) {
        this.stock = stock;
    }

    public Product() {
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

}
