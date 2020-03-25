/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.StoreWarehouseMongo1.repositories;

import com.example.StoreWarehouseMongo1.model.Category;
import com.example.StoreWarehouseMongo1.model.Product;
import java.util.List;
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
public interface CategoryRepository extends MongoRepository<Category, String>, PagingAndSortingRepository<Category, String> {

    
    List<Category> findBykindOfCategory(String kind);

}
