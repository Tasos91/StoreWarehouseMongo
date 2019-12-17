package com.example.StoreWarehouseMongo1.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.List;
import org.springframework.data.annotation.Id;
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
    private int goldQ;
    private int whiteQ;
    private int blackQ;
    private int bronzeQ;
    private String address;
    private String category;

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }

    public Product(String id, String productcode, String cost_usd, String cost_eu, String price, String producer_code, String gold_weight, String silver_weight, String descr, String karats, int goldQ, int whiteQ, int blackQ, int bronzeQ, String address, String category) {
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
        this.goldQ = goldQ;
        this.whiteQ = whiteQ;
        this.blackQ = blackQ;
        this.bronzeQ = bronzeQ;
        this.address = address;
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

    public int getGoldQ() {
        return goldQ;
    }

    public void setGoldQ(int goldQ) {
        this.goldQ = goldQ;
    }

    public int getWhiteQ() {
        return whiteQ;
    }

    public void setWhiteQ(int whiteQ) {
        this.whiteQ = whiteQ;
    }

    public int getBlackQ() {
        return blackQ;
    }

    public void setBlackQ(int blackQ) {
        this.blackQ = blackQ;
    }

    public int getBronzeQ() {
        return bronzeQ;
    }

    public void setBronzeQ(int bronzeQ) {
        this.bronzeQ = bronzeQ;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
