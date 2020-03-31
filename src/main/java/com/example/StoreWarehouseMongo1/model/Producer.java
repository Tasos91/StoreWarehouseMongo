/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.StoreWarehouseMongo1.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Tasos
 */
@Document(collection = "producer")
public class Producer {

    @Id
    private String id;
    private String producerCode;
    private String producerName;

    public Producer(String producerCode, String producerName) {
        this.producerCode = producerCode;
        this.producerName = producerName;
    }

    public Producer(String id, String producerCode, String producerName) {
        this.id = id;
        this.producerCode = producerCode;
        this.producerName = producerName;
    }

    public Producer() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProducerCode() {
        return producerCode;
    }

    public void setProducerCode(String producerCode) {
        this.producerCode = producerCode;
    }

    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }
    
    

}
