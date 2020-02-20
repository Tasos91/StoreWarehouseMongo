package com.example.StoreWarehouseMongo1.controllers;

import com.example.StoreWarehouseMongo1.model.Category;
import com.example.StoreWarehouseMongo1.model.Product;
import com.example.StoreWarehouseMongo1.model.PseudoProduct;
import com.example.StoreWarehouseMongo1.model.Stock;
import com.example.StoreWarehouseMongo1.model.Store;
import com.example.StoreWarehouseMongo1.repositories.CategoryRepository;
import com.example.StoreWarehouseMongo1.repositories.ProductRepository;
import com.example.StoreWarehouseMongo1.repositories.PseudoProductRepository;
import com.example.StoreWarehouseMongo1.repositories.StockRepository;
import com.example.StoreWarehouseMongo1.repositories.StoreRepository;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Autowired
    private PseudoProductRepository pseudoproductrepository;

    @Autowired
    private StockRepository stockrepository;

    @Autowired
    private StoreRepository storerepository;

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
            try {
                Category category = categoryrepository.findBykindOfCategory(kindOfCategory).get(0);
                product.setCategory(category);
            } catch (Exception ex) {
            }
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

    //GINETAI KAI UPDATE TO PSEUDOPRODUCT KAI TO STOCK AN UPARXEI
    @PatchMapping(value = "/update")
    public ResponseEntity<?> update(@RequestBody Product product) {
        String pcode = product.getProductcode();
        if (!pcode.isEmpty() || pcode != null) {
            productrepository.save(product);
            try {
                PseudoProduct pspr = pseudoproductrepository.findByproductcode(pcode).get(0);
                pspr.setProduct(product);
                pseudoproductrepository.save(pspr);
            } catch (Exception e) {
            }
            try {
                Stock stock = stockrepository.findByproductId(pcode).get(0);
                stock.setProductId(pcode);
                stockrepository.save(stock);
            } catch (Exception e) {
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity(new CustomErrorType("A product must have a product code", HttpStatus.CONFLICT.value()),
                HttpStatus.CONFLICT);
    }

    @DeleteMapping(value = "/delete/{productCode}")
    public ResponseEntity<?> deleteProduct(@PathVariable("productCode") String productCode) {
        try {
            Product pr = productrepository.findByproductcode(productCode).get(0);
            productrepository.delete(pr);
            try {
                Stock stock = stockrepository.findByproductId(pr.getProductcode()).get(0);
                stockrepository.delete(stock);
            } catch (Exception e) {
            }
            try {
                PseudoProduct pspr = pseudoproductrepository.findByproductcode(pr.getProductcode()).get(0);
                pseudoproductrepository.delete(pspr);
            } catch (Exception e) {
            }
            List<Store> stores = storerepository.findAll();
            for (Store store : stores) {
                List<Stock> stock1 = store.getStock();
                for (Stock stock2 : stock1) {
                    if (stock2.getProductId().equals(pr.getProductcode())) {
                        stock1.remove(stock2);
                        store.setStock(stock1);
                        storerepository.save(store);
                    }
                }
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new CustomErrorType(e.getMessage(), HttpStatus.NOT_FOUND.value()),
                    HttpStatus.NOT_FOUND);
        }
    }

}
