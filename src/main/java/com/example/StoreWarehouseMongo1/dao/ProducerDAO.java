/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.StoreWarehouseMongo1.dao;

import com.example.StoreWarehouseMongo1.model.Producer;
import com.example.StoreWarehouseMongo1.repositories.ProducerRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 *
 * @author Tasos
 */
@Component
public class ProducerDAO {

    @Autowired
    private ProducerRepository producerRepository;

    public ResponseEntity<?> getAll() {
        try {
            List<Producer> producers = producerRepository.findAll();
            return new ResponseEntity<>(producers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("ERROR: " + e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    public ResponseEntity<?> get(String producerId) {
        try {
            return new ResponseEntity<>(producerRepository.findById(producerId).get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Producer not found", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> update(Producer producer) {
        try {
            String producerCode = producer.getProducerCode();
            String producerName = producer.getValue();
            Producer producerTosave = producerRepository.findById(producer.getId()).get();
            producerTosave.setProducerCode(producerCode);
            producerTosave.setValue(producerName);
            producerRepository.save(producerTosave);
            return new ResponseEntity<>("Producer updated", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("", HttpStatus.CONFLICT);
        }

    }

    public ResponseEntity<?> delete(String producerId) {
        try {
            Producer producer = producerRepository.findById(producerId).get();
            producerRepository.delete(producer);
            return new ResponseEntity<>("Producer with producer code " + producer.getProducerCode() + " is succesfully deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("This producer not found", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> create(Producer producer) {
        try {
            String producerCode = producer.getProducerCode();
            for (Producer produ : producerRepository.findAll()) {
                if (produ.getProducerCode().equals(producerCode)) {
                    return new ResponseEntity<>("This producer already exists", HttpStatus.CONFLICT);
                }
            }
            producerRepository.save(producer);
            return new ResponseEntity<>("This producer succesfully created", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("", HttpStatus.CONFLICT);
        }
    }

    public ResponseEntity<?> createMany(Producer producer) {
        try {
            for (int i = 0; i < 1; i++) {
                Producer producer1 = new Producer();
                producer1.setId("5e8237f8216bc20ca4c48aef");
                producer1.setProducerCode("B0005287A06");
                producer1.setValue("SILVEROS");
                producerRepository.save(producer1);
            }
            return new ResponseEntity<>("This producer succesfully created", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("", HttpStatus.CONFLICT);
        }

    }

    public void deleteAll() {
        producerRepository.deleteAll();
    }

}
