/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.StoreWarehouseMongo1.controllers;

import com.example.StoreWarehouseMongo1.model.Product;
import com.example.StoreWarehouseMongo1.model.PseudoProduct;
import com.example.StoreWarehouseMongo1.model.Stock;
import com.example.StoreWarehouseMongo1.model.Store;
import com.example.StoreWarehouseMongo1.repositories.ProductRepository;
import com.example.StoreWarehouseMongo1.repositories.PseudoProductRepository;
import com.example.StoreWarehouseMongo1.repositories.StockRepository;
import com.example.StoreWarehouseMongo1.repositories.StoreRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Tasos
 */
@RestController
@RequestMapping("/api/stock")
@CrossOrigin(origins = "*")
public class StockControllerCRUD {

    @Autowired
    private StockRepository stockrepository;

    @Autowired
    private StoreRepository storerepository;

    @Autowired
    private ProductRepository productrepository;

    @Autowired
    private PseudoProductRepository pseudoproductrepository;

    @RequestMapping(value = "/add/{address}", method = POST) //prwta tha dhmiourgeitai sth vash to product kai meta to stock!
    public ResponseEntity<?> addStockController(@RequestBody Stock stock, @PathVariable("address") String addressToStored) {
        try {
            productrepository.findByproductcode(stock.getProductId()).get(0);
            if (stock.getImageUrl() == null) {
                stock.setImageUrl("");
            }
            stockrepository.save(stock);
            Store store = storerepository.findByaddress(addressToStored).get(0);
            List<Stock> stock1 = null;
            try {
                stock1 = store.getStock(); //kanw update th lista twn stock tou store
                stock1.add(stock);//edw prepei na ginetai prwth fora save kai to stock sto history
            } catch (Exception thereIsNotPreviousStock) {
                if (stock1 == null || stock1.isEmpty()) {
                    stock1.add(stock);
                }
            }
            store.setStock(stock1);
            storerepository.save(store);
        } catch (Exception e) {
            return new ResponseEntity<>("There is not product code", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Stock created succesfully", HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteStock(@PathVariable("id") String id) {
        try {
            Stock stock = new Stock();
            try {
                stock = stockrepository.findById(id).get();
                stockrepository.delete(stock);
            } catch (Exception e) {
                return new ResponseEntity(new CustomErrorType("Cannot delete this stock", HttpStatus.NOT_FOUND.value()),
                        HttpStatus.NOT_FOUND);
            }
            List<Store> stores = storerepository.findAll();
            for (Store store : stores) {
                List<Stock> stock1 = store.getStock();
                for (Stock stock2 : stock1) {
                    if (stock2.getId().equals(stock.getId())) {
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

    @PostMapping(value = "/nonProduce")
    public ResponseEntity<?> makeItUnUsable(@RequestParam("id") String id) {
        try {
            Stock stock = new Stock();
            try {
                stock = stockrepository.findById(id).get();
                stock.setNon_produce(true);
                stockrepository.save(stock);
            } catch (Exception e) {
                return new ResponseEntity(new CustomErrorType("Cannot find this product", HttpStatus.NOT_FOUND.value()),
                        HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new CustomErrorType(e.getMessage(), HttpStatus.NOT_FOUND.value()),
                    HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/deleteAll")
    public void deleteStock() {
        stockrepository.deleteAll();
    }

}
