package com.example.StoreWarehouseMongo1.helpers;

import com.example.StoreWarehouseMongo1.model.Product;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

/**
 *
 * @author Tasos
 */
@Component
public class Pagination {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Product> getProductsPaginated(int page, String categoryId, String storeId, String producerId) {
        final Pageable pageableRequest = PageRequest.of(page, 28);
        Query query = new Query();
        query.with(new Sort(Sort.Direction.ASC, "productcode"));
        query.addCriteria(Criteria.where("storeId").is(storeId));
        if (!categoryId.isEmpty()) {
            query.addCriteria(Criteria.where("categoryId").is(categoryId));
        }
        if (!producerId.isEmpty()) {
            query.addCriteria(Criteria.where("producerId").is(producerId));
        }
        query.with(pageableRequest);
        List<Product> products = mongoTemplate.find(query, Product.class);
        return products;
    }

    public Integer getMaxSize(String storeId) {
        Query query = new Query();
        query.with(new Sort(Sort.Direction.ASC, "productcode"));
        query.addCriteria(Criteria.where("storeId").is(storeId));
        long maxSizelong = mongoTemplate.count(query, Product.class);
        Integer maxSize = (int) (long) maxSizelong;
        return maxSize;
    }

    public Integer getMaxSizeForCategory(String categoryId, String storeId) {
        Query query = new Query();
        query.with(new Sort(Sort.Direction.ASC, "productcode"));
        query.addCriteria(Criteria.where("storeId").is("storeId")
                .and("categoryId").is(categoryId));
        long maxSizelong = mongoTemplate.count(query, Product.class);
        Integer maxSize = (int) (long) maxSizelong;
        return maxSize;
    }

    public Integer getMaxSizeForProducer(String producerId, String storeId) {
        Query query = new Query();
        query.with(new Sort(Sort.Direction.ASC, "productcode"));
        query.addCriteria(Criteria.where("storeId").is("storeId")
                .and("producerId").is(producerId));
        long maxSizelong = mongoTemplate.count(query, Product.class);
        Integer maxSize = (int) (long) maxSizelong;
        return maxSize;
    }

    public Map<String, Integer> getMaxSize(String storeId, String producerId, String categoryId) {
        Map<String, Integer> maxSize = new HashMap<>();
        if (!producerId.isEmpty() && producerId != null) {
            maxSize.put("maxSize", getMaxSizeForProducer(producerId, storeId));
        }
        if (!categoryId.isEmpty() && categoryId != null) {
            maxSize.put("maxSize", getMaxSizeForProducer(categoryId, storeId));
        }
        if (categoryId.isEmpty() && producerId.isEmpty()) {
            maxSize.put("maxSize", getMaxSize(storeId));
        }
        return maxSize;
    }

}
