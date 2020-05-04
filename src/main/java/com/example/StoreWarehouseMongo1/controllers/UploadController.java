/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.StoreWarehouseMongo1.controllers;

import com.example.StoreWarehouseMongo1.dao.ProductDAO;
import com.example.StoreWarehouseMongo1.helpers.AmazonClient;
import com.example.StoreWarehouseMongo1.model.Product;
import com.example.StoreWarehouseMongo1.repositories.ProductRepository;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Tasos
 */
@RestController
@RequestMapping("/api/image")
@CrossOrigin(origins = "*")
public class UploadController {

    @Autowired
    private AmazonClient amazonClient;

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/upload")
    public String uploadFile(@RequestPart(value = "file") MultipartFile file) {
        return amazonClient.uploadFile(file);
    }

    @DeleteMapping("/delete")
    public String deleteFile(@RequestPart(value = "url") String fileUrl,
            @RequestParam("sku") String sku) {
        for (Product pr : productRepository.findBysku(sku)) {
            productRepository.delete(pr);
        }
        return amazonClient.deleteFileFromS3Bucket(fileUrl);
    }
}
