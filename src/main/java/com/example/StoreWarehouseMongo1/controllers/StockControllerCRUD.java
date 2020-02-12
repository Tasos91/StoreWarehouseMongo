/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.StoreWarehouseMongo1.controllers;

import com.example.StoreWarehouseMongo1.model.Stock;
import com.example.StoreWarehouseMongo1.model.Store;
import com.example.StoreWarehouseMongo1.repositories.StockRepository;
import com.example.StoreWarehouseMongo1.repositories.StoreRepository;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Tasos
 */
@RestController
@RequestMapping("/stock/api")
@CrossOrigin(origins = "*")
public class StockControllerCRUD {

    @Autowired
    private StockRepository stockrepository;

    @Autowired
    private StoreRepository storerepository;

    @RequestMapping(value = "/add/{address}", method = POST) //prwta tha dhmiourgeitai sth vash to product kai meta to stock!
    public void addStockController(@RequestBody Stock stock, @PathVariable("address") String addressToStored) {
        stockrepository.save(stock);
        Store store = storerepository.findByaddress(addressToStored).get(0);
        List<Stock> stock1 = null;
        try {
            stock1 = store.getStock(); //kanw update th lista twn stock tou store
            stock1.add(stock);//edw prepei na ginetai prwth fora save kai to stock sto history
        } catch (Exception thereIsNotPreviousStock) {

        }
        store.setStock(stock1);
        storerepository.save(store);
    }


    @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("stockId") String stockId) throws IOException {
        String uploadUrl = "C:\\Tomcat\\images\\"+ file.getOriginalFilename();
        File convertFile = new File(uploadUrl);
        convertFile.createNewFile();
        FileOutputStream fout = new FileOutputStream(convertFile);
        fout.write(file.getBytes());
        fout.close();
        Optional<Stock> stock = stockrepository.findById(stockId);
        Stock st = stock.get();
        st.setImageUrl(uploadUrl);
        stockrepository.save(st);
        return new ResponseEntity<>("File is uploaded successfully", HttpStatus.OK);
    }

}   
