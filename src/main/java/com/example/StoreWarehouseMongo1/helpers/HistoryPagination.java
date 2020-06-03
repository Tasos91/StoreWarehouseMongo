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
//        query.addCriteria(Criteria.where("timestamp").gte(startDate).lt(endDate));
        query.with(pageableRequest);
        List<History> history = mongoTemplate.find(query, History.class);
        return history;
    }

    public Integer getMaxSize(String address) {
        Query query = new Query();
        query.with(new Sort(Sort.Direction.ASC, "sku"));
        query.addCriteria(Criteria.where("address").is(address));
        long maxSizelong = mongoTemplate.count(query, Product.class);
        Integer maxSize = (int) (long) maxSizelong;
        return maxSize;
    }

    public Integer getMaxSizeForCategory(String categoryId, String address) {
        Query query = new Query();
        query.with(new Sort(Sort.Direction.ASC, "sku"));
        query.addCriteria(Criteria.where("address").is(address)
                .and("categoryId").is(categoryId));
        long maxSizelong = mongoTemplate.count(query, Product.class);
        Integer maxSize = (int) (long) maxSizelong;
        return maxSize;
    }

    public Integer getMaxSizeForProducer(String producerId, String address) {
        Query query = new Query();
        query.with(new Sort(Sort.Direction.ASC, "sku"));
        query.addCriteria(Criteria.where("address").is(address)
                .and("producerId").is(producerId));
        long maxSizelong = mongoTemplate.count(query, Product.class);
        Integer maxSize = (int) (long) maxSizelong;
        return maxSize;
    }

    public Integer getMaxSizeForCategoryAndProducer(String producerId, String categoryId, String address) {
        Query query = new Query();
        query.with(new Sort(Sort.Direction.ASC, "sku"));
        query.addCriteria(Criteria.where("address").is(address)
                .and("producerId").is(producerId))
                .addCriteria(Criteria.where("categoryId").is(categoryId));
        long maxSizelong = mongoTemplate.count(query, Product.class);
        Integer maxSize = (int) (long) maxSizelong;
        return maxSize;
    }

    public Map<String, Integer> getMaxSize(String address, String producerId, String categoryId) {
        Map<String, Integer> maxSize = new HashMap<>();
        if (!categoryId.isEmpty() && producerId.isEmpty()) {
            maxSize.put("maxSize", getMaxSizeForCategory(categoryId, address));
        }
        if (categoryId.isEmpty() && !producerId.isEmpty()) {
            maxSize.put("maxSize", getMaxSizeForProducer(producerId, address));
        }
        if (categoryId.isEmpty() && producerId.isEmpty()) {
            maxSize.put("maxSize", getMaxSize(address));
        }
        if (!categoryId.isEmpty() && !producerId.isEmpty()) {
            maxSize.put("maxSize", getMaxSizeForCategoryAndProducer(producerId, categoryId, address));
        }
        return maxSize;
    }

}
