/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.StoreWarehouseMongo1.controllers;

import com.example.StoreWarehouseMongo1.model.Product;
import com.example.StoreWarehouseMongo1.repositories.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/showProducts")
@CrossOrigin(origins = "*")
public class ShowProductController {

    @Autowired
    private ProductRepository productrepository;

    @PostMapping("/{address}/{pageNum}") //this controller shows all products for the specified store for the first page
    public List<Product> showProductsByPage(@PathVariable("address") String address, @PathVariable("pageNum") int page) {

        Pageable pageable = PageRequest.of(page, 5); //pagination sto 0  mpainei to page
        Page<Product> pageproduct = productrepository.findAll(pageable); //pagination
        List<Product> productsPerPage = pageproduct.getContent(); //pagination
        List<Product> productsPerPageForThisStore = new ArrayList();
        for (Product pr : productsPerPage) {
            if (pr.getAddress().equals(address)) {
                productsPerPageForThisStore.add(pr);
            }
        }

        return productsPerPageForThisStore;
    }
}
