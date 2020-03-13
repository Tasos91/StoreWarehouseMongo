package com.example.StoreWarehouseMongo1.controllers;

import com.example.StoreWarehouseMongo1.model.History;
import com.example.StoreWarehouseMongo1.model.PseudoProduct;
import com.example.StoreWarehouseMongo1.model.Product;
import com.example.StoreWarehouseMongo1.model.Stock;
import com.example.StoreWarehouseMongo1.model.Store;
import com.example.StoreWarehouseMongo1.repositories.HistoryRepository;
import com.example.StoreWarehouseMongo1.repositories.ProductRepository;
import com.example.StoreWarehouseMongo1.repositories.PseudoProductRepository;
import com.example.StoreWarehouseMongo1.repositories.StockRepository;
import com.example.StoreWarehouseMongo1.repositories.StoreRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Tasos
 */
@RestController
@RequestMapping("/api/history")
@CrossOrigin(origins = "*")
public class HistoryController {

    @Autowired
    private HistoryRepository historyrepository;

    @GetMapping
    public ResponseEntity<?> getHistory() {
        try {
            return new ResponseEntity<List<History>>(historyrepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new CustomErrorType("Product not found", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        }
    }

}
