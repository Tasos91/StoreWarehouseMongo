package com.example.StoreWarehouseMongo1.dao;

import com.example.StoreWarehouseMongo1.Exceptions.ProductsNotFoundException;
import com.example.StoreWarehouseMongo1.helpers.HistoryPagination;
import com.example.StoreWarehouseMongo1.helpers.Validator;
import com.example.StoreWarehouseMongo1.model.History;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
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
    private Validator validator;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<List<?>> get(String productId, String address, String startDate, String endDate, String limitString, String page) {
        validator.validateParamsOfGetHistory(productId, address, startDate, endDate, limitString, page);
        List<History> productHistory = historyPagination.getHistoryPaginated(page, address, productId, limitString, startDate, endDate);
        if (productHistory.isEmpty()) {
            throw new ProductsNotFoundException("There are not products with these filters");
        }
        List<Map<String, Integer>> listMaxSize = new ArrayList();
        listMaxSize.add(historyPagination.getMaxSize(productId, address, startDate, endDate));
        List<List<?>> productsWithMaxSize = new ArrayList();
        productsWithMaxSize.add(productHistory);
        productsWithMaxSize.add(listMaxSize);
        return productsWithMaxSize;
    }

}
