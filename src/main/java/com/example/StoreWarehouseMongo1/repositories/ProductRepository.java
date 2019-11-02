/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.StoreWarehouseMongo1.repositories;

import com.example.StoreWarehouseMongo1.model.Product;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 *
 * @author Tasos
 */

@CrossOrigin(origins = "*")
@Repository
public interface ProductRepository extends MongoRepository<Product,String>, PagingAndSortingRepository<Product,String> {
    
    Page<Product> findAll(Pageable pageable);
    List<Product> findByproductcode(String productCode);
    List<Product> findByUsernameAndPassword(String username,String password);
    
    @Cacheable
    List<Product> findByAddress(String address);
    
}
