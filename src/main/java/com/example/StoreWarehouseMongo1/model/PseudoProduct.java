package com.example.StoreWarehouseMongo1.model;

import com.example.StoreWarehouseMongo1.model.Product;
import com.example.StoreWarehouseMongo1.model.Stock;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Tasos
 */

@Document(collection = "pseudoproducts")
public class PseudoProduct {
    
    private Product product;
    private Stock stock;

    public PseudoProduct(Product product, Stock stock) {
        this.product = product;
        this.stock = stock;
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
