package com.example.StoreWarehouseMongo1.repositories;

import com.example.StoreWarehouseMongo1.model.Stock;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author Tasos
 */
public interface StockRepository extends MongoRepository<Stock, String> {
        
    
}
