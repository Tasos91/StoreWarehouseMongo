package com.example.StoreWarehouseMongo1.controllers;

import com.example.StoreWarehouseMongo1.dao.ProducerDAO;
import com.example.StoreWarehouseMongo1.model.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Tasos
 */
@RestController
@RequestMapping("/api/producer")
@CrossOrigin(origins = "*")
public class ProducerControllerCRUD {

    @Autowired
    private ProducerDAO producerdao;

    @PostMapping(value = "/create")
    public ResponseEntity<?> createProducer(@RequestBody Producer producer) {
        return producerdao.create(producer);
//        return producerdao.createMany(producer);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getProducers() {
        return producerdao.getAll();
    }

    @GetMapping
    public ResponseEntity<?> getProducer(@RequestParam("producerId") String producerId) {
        return producerdao.get(producerId);
    }

    @PatchMapping(value = "/update")
    public ResponseEntity<?> updateProducer(@RequestBody Producer producer) {
        return producerdao.update(producer);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteProducer(@RequestParam("producerId") String producerId) {
        return producerdao.delete(producerId);
    }

    @DeleteMapping(value = "/deleteAll")
    public void deleteΑll() {
        producerdao.deleteAll();
    }
}
