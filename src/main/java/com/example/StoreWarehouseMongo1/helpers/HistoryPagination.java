package com.example.StoreWarehouseMongo1.helpers;

import com.example.StoreWarehouseMongo1.model.History;
import com.example.StoreWarehouseMongo1.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Tasos
 */
@Component
public class HistoryPagination {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<History> getHistoryPaginated(String page, String address, String productId, String limitString, String startDate, String endDate) {
        int paGe = Integer.parseInt(page);
        paGe = paGe - 1;
        int limit = Integer.valueOf(limitString);
        final Pageable pageableRequest = PageRequest.of(paGe, limit);
        Query query = new Query();
        query.addCriteria(Criteria.where("storeId").is(address));
        query.addCriteria(Criteria.where("productId").is(productId));
        query.addCriteria(Criteria.where("timestamp").gte(startDate).lt(endDate));
        query.with(pageableRequest);
        List<History> history = mongoTemplate.find(query, History.class);
        return history;
    }

    public Map<String, Integer> getMaxSize(String productId, String address, String startDate, String endDate) {
        Map<String, Integer> maxSizeMap = new HashMap<>();
        Query query = new Query();
        query.with(new Sort(Sort.Direction.ASC, "timestamp"));
        query.addCriteria(Criteria.where("storeId").is(address)
                .and("productId").is(productId));
        query.addCriteria(Criteria.where("timestamp").gte(startDate).lt(endDate));
        long maxSizelong = mongoTemplate.count(query, History.class);
        Integer maxSize = (int) (long) maxSizelong;
        maxSizeMap.put("maxSize", maxSize);
        return maxSizeMap;
    }

}
