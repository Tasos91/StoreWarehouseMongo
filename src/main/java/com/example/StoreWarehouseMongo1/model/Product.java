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
    private String productcode;
    private Category category;
    private String cost_usd;
    private String cost_eu;
    private String price;
    private String producer_code;
    private String descr;
    private List<Stone> stones; //proswrina vgazw ta stones apo to constructor

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Product(String productcode, Category category) {
        this.productcode = productcode;
        this.category = category;
    }

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }

    public Product(String productcode, String cost_usd, String cost_eu, String price, String producer_code, String gold_weight, String silver_weight, String descr, Category category) {
        this.productcode = productcode;
        this.cost_usd = cost_usd;
        this.cost_eu = cost_eu;
        this.price = price;
        this.producer_code = producer_code;
        this.descr = descr;
        this.category = category;
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

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public List<Stone> getStones() {
        return stones;
    }

    public void setStones(List<Stone> stones) {
        this.stones = stones;
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
