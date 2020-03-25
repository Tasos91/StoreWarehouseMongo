/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.StoreWarehouseMongo1.controllers;

import com.example.StoreWarehouseMongo1.model.Category;
import com.example.StoreWarehouseMongo1.model.Product;
import com.example.StoreWarehouseMongo1.repositories.CategoryRepository;
import com.example.StoreWarehouseMongo1.repositories.ProductRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Tasos
 */
@RestController
@RequestMapping("/api/category")
@CrossOrigin(origins = "*")
public class CategoryControllerCRUD {
  
    
    @Autowired
    private ProductRepository productrepository;

    @Autowired
    private CategoryRepository categoryrepository;

    @PostMapping(value = "/create")
    public ResponseEntity<?> createCategory(@RequestBody Category category) {
        try {
            String kind = category.getKindOfCategory();
            for (Category cat : categoryrepository.findAll()) {
                if (cat.getKindOfCategory().equals(kind)) {
                    return new ResponseEntity<>("This type of category already exists", HttpStatus.CONFLICT);
                } else {
                    categoryrepository.save(category);
                }
            }
        } catch (Exception e) {
            return new ResponseEntity<>("", HttpStatus.CONFLICT);
        }
        return null;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<Category> categories = categoryrepository.findAll();
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("ERROR: " + e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping(value = "/{categoryId}")
    public ResponseEntity<?> get(@PathVariable("categoryId") String categoryId) {
        try {
            return new ResponseEntity<>(categoryrepository.findById(categoryId).get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Category not found", HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping(value = "/update/{categoryId}")
    public ResponseEntity<?> updateCategory(@RequestBody Category category, @PathVariable("categoryId") String categoryId) {
        try {
            String kind = category.getKindOfCategory();
            for (Category cat : categoryrepository.findAll()) {
                if ((cat.getId().equals(categoryId))) {
                    Category categoryTosave = categoryrepository.findById(categoryId).get();
                    categoryTosave.setKindOfCategory(kind);
                    categoryrepository.save(categoryTosave);
                    for(Product product : productrepository.findAll()){
                        if(product.getCategory().getId().equals(categoryId)){
                            product.setCategory(categoryTosave);
                            String id = productrepository.findById(product.getId()).get().getId();
                            product.setId(id);
                            productrepository.save(product);
                        }
                    }
                    return new ResponseEntity<>("Category updated", HttpStatus.OK);
                }
            }
            return new ResponseEntity<>("This kind of category not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("", HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping(value = "/{categoryId}")
    public ResponseEntity<?> delete(@PathVariable("categoryId") String categoryId) {
        try {
            Category category = categoryrepository.findById(categoryId).get();
            categoryrepository.delete(category);
            return new ResponseEntity<>("Category " + category.getKindOfCategory() + " is succesfully deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("This kind of category not found", HttpStatus.NOT_FOUND);
        }
    }
}
