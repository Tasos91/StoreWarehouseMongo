/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.StoreWarehouseMongo1.dao;

import com.example.StoreWarehouseMongo1.Exceptions.ProductIdWrongException;
import com.example.StoreWarehouseMongo1.Exceptions.ProductNotFoundException;
import com.example.StoreWarehouseMongo1.helpers.HistoryPagination;
import com.example.StoreWarehouseMongo1.model.History;
import com.example.StoreWarehouseMongo1.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Tasos
 */
@Component
public class HistoryDAO {

    @Autowired
    private HistoryPagination historyPagination;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<List<?>> get(String productId, String address, String startDate, String endDate, String limitString, String page) {
        List<History> productHistory = historyPagination.getHistoryPaginated(page, address, productId, limitString, startDate, endDate);
        List<Map<String, Integer>> listMaxSize = new ArrayList();
        listMaxSize.add(historyPagination.getMaxSize(productId, address, startDate, endDate));
        List<List<?>> productsWithMaxSize = new ArrayList();
        productsWithMaxSize.add(productHistory);
        productsWithMaxSize.add(listMaxSize);
        return productsWithMaxSize;
    }

}
