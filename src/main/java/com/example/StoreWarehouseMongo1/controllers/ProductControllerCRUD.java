package com.example.StoreWarehouseMongo1.controllers;

import com.example.StoreWarehouseMongo1.model.Category;
import com.example.StoreWarehouseMongo1.model.Product;
import com.example.StoreWarehouseMongo1.repositories.CategoryRepository;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @Autowired
    private ProductRepository productrepository;

    @Autowired
    private CategoryRepository categoryrepository;

    @GetMapping
    public List<Product> getAll() {
        return productrepository.findAll();
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        Product pr = new Product();
        Product pere = product;
        pr = null;
        try {
            pr = productrepository.findByproductcode(product.getProductcode()).get(0);
        } catch (Exception e) {
            String kindOfCategory = product.getCategory().getKindOfCategory();
            Category category = categoryrepository.findBykindOfCategory(kindOfCategory).get(0);
            product.setCategory(category);
            productrepository.save(product);
            return new ResponseEntity(new CustomErrorType("The product " + product.getProductcode() + " was succesfully created", HttpStatus.OK.value()),
                    HttpStatus.OK);
        }
        if (pr != null) {
            return new ResponseEntity(new CustomErrorType("A product with productcode " + product.getProductcode() + " already exist", HttpStatus.CONFLICT.value()),
                    HttpStatus.CONFLICT);
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
            return new ResponseEntity(new CustomErrorType("Product with productCode " + productCode
                    + " not found", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }

    @PatchMapping(value = "/update")
    public void update(@RequestBody Product product) {
        productrepository.save(product);
    }

    @DeleteMapping(value = "/delete/{productCode}")
    public void deleteProduct(@PathVariable("productCode") String productCode) {
        Product pr = productrepository.findByproductcode(productCode).get(0);
        productrepository.delete(pr);
    }

}
