package com.example.StoreWarehouseMongo1.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Tasos
 */
@Document(collection = "history")
public class History {
    
    @Id
    private String id;
    private String timestamp;
    private Stock stock;
    private String stockId;

    
    public String getStockId() {
        return stockId;
    }

    public History(String timestamp, String stockId) {
        this.timestamp = timestamp;
        this.stockId = stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public History(String timestamp, Stock stock, String stockId) {
        this.timestamp = timestamp;
        this.stock = stock;
        this.stockId = stockId;
    }
    
    public History(){
    }

    public History(String timestamp, Stock stock) {
        this.timestamp = timestamp;
        this.stock = stock;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }
    
}
