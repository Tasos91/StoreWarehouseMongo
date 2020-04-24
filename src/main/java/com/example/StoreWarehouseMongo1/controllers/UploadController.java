/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.StoreWarehouseMongo1.controllers;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

//    @Autowired
//    private StockRepository stockrepository;
//
//    @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("stockId") String stockId) throws IOException {
//        String uploadUrl = "C:\\Tomcat\\images\\" + file.getOriginalFilename();
//        File convertFile = new File(uploadUrl);
//        convertFile.createNewFile();
//        FileOutputStream fout = new FileOutputStream(convertFile);
//        fout.write(file.getBytes());
//        fout.close();
//        Optional<Stock> stock = stockrepository.findById(stockId);
//        Stock st = stock.get();
//        st.setImageUrl(uploadUrl);
//        stockrepository.save(st);
//        return new ResponseEntity<>("File is uploaded successfully", HttpStatus.OK);
//    }
//
//    @PatchMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<Object> updateUploadFile(@RequestParam("file") MultipartFile fileNew, @RequestParam("stockId") String stockId) throws IOException {
//        Stock oldStock = stockrepository.findById(stockId).get();
//        String oldURL = oldStock.getImageUrl();
//        File fileOld = new File(oldURL);
//        fileOld.delete();
//        String uploadUrl = "C:\\Tomcat\\images\\" + fileNew.getOriginalFilename();
//        File convertFile = new File(uploadUrl);
//        convertFile.createNewFile();
//        FileOutputStream fout = new FileOutputStream(convertFile);
//        fout.write(fileNew.getBytes());
//        fout.close();
//        Stock stock = stockrepository.findById(stockId).get();
//        stock.setImageUrl(uploadUrl);
//        stockrepository.save(stock);
//        return new ResponseEntity<>("File is uploaded successfully", HttpStatus.OK);
//    }
    @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        try {
            File convFile = new File(file.getOriginalFilename());
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
            BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAT343VQJAC7ANC3WK", "wDf0dGRjISmU4kwjdMPMPNovf29ZeVEO7ZgZR0TH");
            AmazonS3 s3client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                    .withRegion("eu-central-1")
                    .build();
            String bucketName = "myanatolimages";
            s3client.putObject(bucketName, convFile.getName(), convFile);
        } catch (AmazonServiceException e) {
            System.out.println("ERROR: " + e.getErrorMessage());
        }
        return new ResponseEntity<>("File is uploaded successfully", HttpStatus.OK);
    }

}
