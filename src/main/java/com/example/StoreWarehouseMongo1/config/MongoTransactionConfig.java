///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.example.StoreWarehouseMongo1.config;
//
//import com.mongodb.MongoClient;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.mongodb.MongoDbFactory;
//import org.springframework.data.mongodb.MongoTransactionManager;
//import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
//import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
//
///**
// *
// * @author Tasos
// */
//@Configuration
//@EnableMongoRepositories(basePackages = "com.example.StoreWarehouseMongo1.repositories")
//public class MongoTransactionConfig extends AbstractMongoConfiguration {
//
//    @Override
//    public MongoClient mongoClient() {
//        return new MongoClient("127.0.0.1", 27017);
//    }
//
//    @Override
//    protected String getDatabaseName() {
//        return "warehouse";
//    }
//
//    @Bean   
//    MongoTransactionManager transactionManager(MongoDbFactory dbFactory) {
//        return new MongoTransactionManager(dbFactory);
//    }
//}
