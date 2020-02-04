package com.example.StoreWarehouseMongo1.model;

import com.example.StoreWarehouseMongo1.model.Product;
import com.example.StoreWarehouseMongo1.model.Stock;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Tasos
 */
@Document(collection = "pseudoproducts")
public class PseudoProduct {

    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PseudoProduct(String id, Product product, String productcode, Stock stock) {
        this.id = id;
        this.product = product;
        this.productcode = productcode;
        this.stock = stock;
    }
    private Product product;
    private String productcode;
    private Stock stock;

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }

    public PseudoProduct(Product product, String productcode, Stock stock) {
        this.product = product;
        this.productcode = productcode;
        this.stock = stock;
    }

    public PseudoProduct(Product product, Stock stock) {
        this.product = product;
        this.stock = stock;
    }

    public PseudoProduct() {
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

}
