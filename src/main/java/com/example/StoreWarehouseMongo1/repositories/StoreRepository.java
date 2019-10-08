/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.StoreWarehouseMongo1.repositories;

import com.example.StoreWarehouseMongo1.model.Store;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 *
 * @author Tasos
 */
@Repository
@CrossOrigin(origins="*")
public interface StoreRepository extends MongoRepository<Store,String>{
    
    List<Store> findByUsernameAndPassword(String username,String password);
    
}
