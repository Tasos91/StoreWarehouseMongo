package com.example.StoreWarehouseMongo1.controllers;

import com.example.StoreWarehouseMongo1.dao.ProductDAO;
import com.example.StoreWarehouseMongo1.helpers.S3Client;
import com.example.StoreWarehouseMongo1.model.Product;
import com.example.StoreWarehouseMongo1.repositories.ProductRepository;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

/**
 * @author Tasos
 */
@RestController
@RequestMapping("/api/product")
@CrossOrigin(origins = "*")
public class ProductControllerCRUD {

    @Autowired
    private S3Client s3Client;

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
        return new ResponseEntity<List<List<?>>>(productDao.getProductsPerFilterCase(page, categoryId, producerId, address, limit), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public void create(@Valid @RequestBody Product product) throws Exception {
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
    public ResponseEntity<Product> disableProduct(@Valid @RequestBody Product product) {
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
    public void changeQuantityOfProduct(@RequestParam("quantity") int quantity,
                                        @RequestParam("id") String productId) {
        productDao.updateQuantity(productId, quantity);
    }

    @PatchMapping(value = "/update")//!!!!!THA PREPEI NA GINEI VALIDATION GIA TO PRODUCT BY HAND!!!den exei testaristei apo to UI oute apo kwdika...
    public ResponseEntity updateProduct(@RequestPart(value = "file") MultipartFile file, @RequestParam("text") String productString) {
        Product product = productDao.convertJsonToProduct(productString); // updated product
        String id = product.getId();
        System.out.println("First");
        JSONObject errors = productDao.customErrorsFromValidation(product);
        if (!errors.isEmpty()) {
            System.out.println("Second");
            return new ResponseEntity<JSONObject>(errors, HttpStatus.BAD_REQUEST);
        }
        Product pr = productrepository.findById(id).get(); //apo db prin to update
        if (!file.getName().equals("") && file.getName() != null) {
            System.out.println("Third");
            s3Client.deleteFileFromS3Bucket(pr.getImageUrl());
            product.setImageUrl(s3Client.returnFilePath(file));
            try {
                System.out.println("Fourth");
                s3Client.uploadFile(file);
            } catch (IOException e) {
                System.out.println("Fifth");
                e.printStackTrace();
            }
        }
        return productDao.edit(product);
    }

    @DeleteMapping(value = "/delete")
    public void deleteProduct(@RequestParam("sku") String sku) {
        productDao.deleteProduct(sku);
    }

//    @PostMapping(value = "/createTest")
//    public void testCreate(@RequestBody Product product) {
//        productDao.testCreate(product);
//    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
