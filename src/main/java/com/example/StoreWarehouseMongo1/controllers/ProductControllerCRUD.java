package com.example.StoreWarehouseMongo1.controllers;

import com.example.StoreWarehouseMongo1.model.Product;
import com.example.StoreWarehouseMongo1.model.Store;
import com.example.StoreWarehouseMongo1.repositories.ProductRepository;
import com.example.StoreWarehouseMongo1.repositories.StoreRepository;
import com.example.StoreWarehouseMongo1.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Tasos
 */
@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "*")
public class ProductControllerCRUD {

    @Autowired
    private ProductRepository productrepository;

    @Autowired
    private StoreRepository storerepository;

    @Autowired
    private UserRepository userepository;

    @RequestMapping(value = "/create/{isAdmin}", method = POST)
    public void createProductController(@RequestBody Product product, @PathVariable("isAdmin") boolean isAdmin) {

        if (isAdmin) {
            productrepository.save(product);
        }

    }

    @RequestMapping(value = "/show/{productCode}", method = POST)
    public Product findProductController(@PathVariable("productCode") String productCode,
            @PathVariable("address") String address) {
        Store store = storerepository.findByaddress(address).get(0);
        return productrepository.findByproductcode(productCode).get(0);
    }

    @RequestMapping(value = "/update/{productCode}", method = PUT)
    public void updateProductController(@RequestBody Product updatedProduct, @PathVariable("productCode") String productCode) {
        Product product1 = productrepository.findByproductcode(productCode).get(0);
        product1.setCategory(updatedProduct.getCategory());
        product1.setCost_eu(updatedProduct.getCost_eu());
        product1.setCost_usd(updatedProduct.getCost_usd());
        product1.setDescr(updatedProduct.getDescr());
        product1.setGold_weight(updatedProduct.getGold_weight());
        product1.setKarats(updatedProduct.getKarats());
        product1.setPrice(updatedProduct.getPrice());
        product1.setProducer_code(updatedProduct.getProducer_code());
        product1.setProductcode(updatedProduct.getProductcode());
        product1.setSilver_weight(updatedProduct.getSilver_weight());
        product1.setStones(null);
        productrepository.save(product1);
    }

    @RequestMapping(value = "/delete/{productCode}", method = DELETE)
    public void deleteProduct(@PathVariable("productCode") String productCode) {
        Product pr = productrepository.findByproductcode(productCode).get(0);
        productrepository.delete(pr);
    }

}
