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
    private Product product;
    private String productId;
    private String storeId;
    private String producerId;
    private String categoryId;

    public History(String timestamp, Product product, String productId, String storeId, String producerId, String categoryId) {
        this.timestamp = timestamp;
        this.product = product;
        this.productId = productId;
        this.storeId = storeId;
        this.producerId = producerId;
        this.categoryId = categoryId;
    }

    public History(String timestamp, Product product, String productId, String storeId) {
        this.timestamp = timestamp;
        this.product = product;
        this.productId = productId;
        this.storeId = storeId;
    }

    public History() {
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getProducerId() {
        return producerId;
    }

    public void setProducerId(String producerId) {
        this.producerId = producerId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
    
    

}
