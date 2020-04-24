package com.example.StoreWarehouseMongo1.controllers;

import com.example.StoreWarehouseMongo1.dao.ProductDAO;
import com.example.StoreWarehouseMongo1.model.Product;
import com.example.StoreWarehouseMongo1.repositories.ProductRepository;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Tasos
 */
@RestController
@RequestMapping("/api/product")
@CrossOrigin(origins = "*")
public class ProductControllerCRUD {

    @Autowired
    private ProductRepository productrepository;

    @Autowired
    private ProductDAO productDao;

    @GetMapping
    public List<Product> getAll() {
        return productrepository.findAll();
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> getProductsPaginatedAndFiltered(@RequestParam("page") String page,
            @RequestParam("categoryId") String categoryId, @RequestParam("producerId") String producerId,
            @RequestParam("address") String address, @RequestParam("limit") String limit) {
        if (page == null || page.equals("")) {
            return new ResponseEntity(new CustomErrorType("Error: Page cannot be null"
                    + " ", 666), HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity<List<List<?>>>(productDao.getProductsPerFilterCase(page, categoryId, producerId, address, limit), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new CustomErrorType("Error: " + e.getMessage()
                    + " ", HttpStatus.GATEWAY_TIMEOUT.value()), HttpStatus.GATEWAY_TIMEOUT);
        }
    }

    @PostMapping(value = "/create")
    public void create(@RequestBody Product product) throws Exception {
        productDao.insert(product);
    }

    @GetMapping(value = "/get")
    public ResponseEntity<?> getProduct(@RequestParam("productId") String productId) {
        return productDao.get(productId);
    }

    @DeleteMapping(value = "/deleteAll")
    public void deleteProducts() {
        productrepository.deleteAll();
    }

    @PatchMapping(value = "/disable")
    public ResponseEntity<Product> disableProduct(@RequestBody Product product) {
        try {
            productDao.makeItDisable(product);
            return new ResponseEntity(new CustomErrorType("The product is not produced anymore", HttpStatus.OK.value()),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new CustomErrorType("", HttpStatus.INTERNAL_SERVER_ERROR.value()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping(value = "/change")
    public ResponseEntity<Product> changeQuantityOfProduct(@RequestParam("quantity") int quantity,
            @RequestParam("id") String productId) {
        try {
            productDao.updateQuantity(productId, quantity);
            return new ResponseEntity(new CustomErrorType("The quantity is changed succesfully", HttpStatus.OK.value()),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new CustomErrorType(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping(value = "/update")
    public void updateProduct(@RequestBody Product product) {
        productDao.updateProduct(product);
    }

}
