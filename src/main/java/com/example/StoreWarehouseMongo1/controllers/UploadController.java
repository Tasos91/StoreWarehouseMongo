/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.StoreWarehouseMongo1.controllers;

import com.example.StoreWarehouseMongo1.helpers.S3Client;
import com.example.StoreWarehouseMongo1.model.Product;
import com.example.StoreWarehouseMongo1.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static com.example.StoreWarehouseMongo1.helpers.RegexUtils.filePathRegex;

/**
 * @author Tasos
 */
@RestController
@RequestMapping("/api/image")
@CrossOrigin(origins = "*")
public class UploadController {

    @Autowired
    private S3Client s3Client;

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/upload")
    public String uploadFile(@RequestPart(value = "file") MultipartFile file) {
        return s3Client.uploadFile(file);
    }

    @DeleteMapping("/delete")
    public String deleteFile(@RequestPart(value = "url") String fileUrl,
                             @RequestParam("sku") String sku) {
        for (Product pr : productRepository.findBysku(sku)) {
            productRepository.delete(pr);
        }
        if (filePathRegex(fileUrl)) {
            return s3Client.deleteFileFromS3Bucket(fileUrl);
        } else {
            return null;
        }
    }
}
