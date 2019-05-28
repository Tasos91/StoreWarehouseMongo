/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.StoreWarehouseMongo1.controllers;

import com.example.StoreWarehouseMongo1.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Tasos
 */
@RestController
@RequestMapping("/save")
@CrossOrigin(origins = "*")
public class SaveProductController {
    
//    @Autowired
//    private ProductRepository productrepository;
//    
//        @GetMapping("/newproduct")
//	public void saveproduct() {
//                Product product= new Product();
//                productrepository.save(product);
//        }
}
