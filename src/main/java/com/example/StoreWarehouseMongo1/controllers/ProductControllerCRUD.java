package com.example.StoreWarehouseMongo1.controllers;

import com.example.StoreWarehouseMongo1.model.Product;
import com.example.StoreWarehouseMongo1.model.Store;
import com.example.StoreWarehouseMongo1.repositories.ProductRepository;
import com.example.StoreWarehouseMongo1.repositories.StoreRepository;
import com.example.StoreWarehouseMongo1.repositories.UserRepository;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author Tasos
 */
@RestController
@RequestMapping("/product/api")
@CrossOrigin(origins = "*")
public class ProductControllerCRUD {

    private MongoTemplate mongoTemplate;

    @Autowired
    private ProductRepository productrepository;

    @Autowired
    private StoreRepository storerepository;

    @Autowired
    private UserRepository userepository;

    public ProductControllerCRUD(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
    

    @RequestMapping(value = "/create", method = POST)
    public ResponseEntity<?> createProductController(@RequestBody Product product, @PathVariable("isAdmin") boolean isAdmin, UriComponentsBuilder ucBuilder) {
        if (!isAdmin) {
            return new ResponseEntity(new CustomErrorType("The specific user has not the privilege to create a product", HttpStatus.FORBIDDEN.value()),
                    HttpStatus.FORBIDDEN);
        } else {
            Product pr = new Product();
            pr = null;
            try {
                pr = productrepository.findByproductcode(product.getProductcode()).get(0);
            } catch (Exception e) {
                productrepository.save(product);
                HttpHeaders headers = new HttpHeaders();
                headers.setLocation(ucBuilder.path("/create/{productcode}").buildAndExpand(product.getProductcode()).toUri());
                return new ResponseEntity(new CustomErrorType("The product " + product.getProductcode() + " was succesfully created", HttpStatus.OK.value()),
                        HttpStatus.OK);
            }
            if (pr != null) {
                return new ResponseEntity(new CustomErrorType("A product with productcode " + product.getProductcode() + " already exist", HttpStatus.CONFLICT.value()),
                        HttpStatus.CONFLICT);
            }
        }
        return null;
    }

    @GetMapping(value = "/{productCode}")
    public ResponseEntity<?> getProduct(@PathVariable("productCode") String productCode) {
        Product product = new Product();
        product = null;
        try {
            product = productrepository.findByproductcode(productCode).get(0);
        } catch (Exception IndexOutOfBoundsException) {
        }
        if (product == null) {
            return new ResponseEntity(new CustomErrorType("User with productCode " + productCode
                    + " not found",HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }

    @PutMapping
        public void update(@RequestBody Product product) {
        this.mongoTemplate.save(product);
    }

    @RequestMapping(value = "/delete/{productCode}", method = DELETE)
    public void deleteProduct(@PathVariable("productCode") String productCode) {
        Product pr = productrepository.findByproductcode(productCode).get(0);
        productrepository.delete(pr);
    }

    @RequestMapping(value = "/showAll", method = GET)
    public List<Product> getAll() {
        return this.productrepository.findAll();
    }

}
