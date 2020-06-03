package com.example.StoreWarehouseMongo1.controllers;

import com.example.StoreWarehouseMongo1.helpers.HistoryPagination;
import com.example.StoreWarehouseMongo1.model.History;
import com.example.StoreWarehouseMongo1.repositories.HistoryRepository;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @Autowired
    private HistoryPagination pagination;

    @GetMapping
    public ResponseEntity<?> getHistory() {
        try {
            return new ResponseEntity<List<History>>(historyrepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new CustomErrorType("Product not found", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteHistory() {
        try {
            historyrepository.deleteAll();
            return new ResponseEntity<>("History deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new CustomErrorType("History is not deleted", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping(value = "/product")
    public ResponseEntity<?> getHistoryByDate(@RequestParam("storeId") String storeId,
            @RequestParam("productId") String productId) {
        try {
            return new ResponseEntity<List<History>>(historyrepository.findByProductIdAndStoreId(productId, storeId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new CustomErrorType("Product not found", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/test")
    public void getHistoryByDatse() {
        pagination.getHistoryPaginated("1","Kifisia","5e8e0dcf46a89e3fac413074","12","2020/04/08 17:45:51", "2020/04/08 17:45:51");
    }
    
    private void dateFromString(History history) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        Date dt = (Date) dtf.parse(history.getTimestamp());
//        String timestamp = dtf.format(now);
        History productHistory = new History();
    }
    
    

}
