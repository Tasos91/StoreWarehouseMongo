package com.example.StoreWarehouseMongo1.helpers;

import com.example.StoreWarehouseMongo1.model.Product;
import com.example.StoreWarehouseMongo1.repositories.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Tasos
 */
@Component
public class Filter {

    @Autowired
    ProductRepository productrepository;

    public static List<Product> getProductsForThisStore(List<Product> productsPerPage, String address) {
        List<Product> productsPerPageForThisStore = new ArrayList();
        for (Product product : productsPerPage) {
//            if (product.getAddress().equals(address)) { // na dw ta object mesa sto compass na einai idia kai oxi object mesa se object
//                productsPerPageForThisStore.add(product);
//            }
        }
        return productsPerPageForThisStore;
    }

    public static List<Product> getProductsPerStoreAndCategory(List<Product> productsPerPage, String address, String category) {
        List<Product> productsFilteredByStore = getProductsForThisStore(productsPerPage, address);
        List<Product> products = new ArrayList();
        for (Product product : productsFilteredByStore) {
//            if (product.getCategory().equals(category)) {
//                products.add(product);
//            }
        }
        return products;
    }

    public List<Product> getProduct(String productCode) {
        return productrepository.findByproductcode(productCode);
    }

//    public static List<Product> getProductsPerStoreAndProducerCode(List<Product> productsPerPage, String address, String producerCode) {
//        List<Product> productsPerPageForThisStore = getProductsForThisStore(productsPerPage, address);
//        List<Product> products = new ArrayList();
//        for (Product product : productsPerPageForThisStore) {
//            if (product.getProducer_code().equals(producerCode)) {
//                products.add(product);
//            }
//        }
//        return products;
//    }

}
