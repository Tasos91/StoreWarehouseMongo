/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.StoreWarehouseMongo1.dao;

import com.example.StoreWarehouseMongo1.controllers.CustomErrorType;
import com.example.StoreWarehouseMongo1.helpers.Pagination;
import com.example.StoreWarehouseMongo1.model.Category;
import com.example.StoreWarehouseMongo1.model.History;
import com.example.StoreWarehouseMongo1.model.Producer;
import com.example.StoreWarehouseMongo1.model.Product;
import com.example.StoreWarehouseMongo1.model.Store;
import com.example.StoreWarehouseMongo1.repositories.CategoryRepository;
import com.example.StoreWarehouseMongo1.repositories.HistoryRepository;
import com.example.StoreWarehouseMongo1.repositories.ProducerRepository;
import com.example.StoreWarehouseMongo1.repositories.ProductRepository;
import com.example.StoreWarehouseMongo1.repositories.StoreRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Tasos
 */
@Component
public class ProductDAO {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private HistoryRepository historyrepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProducerRepository producerRepository;

    @Autowired
    private Pagination pagination;

    //@Transactional
    public void insert(Product product) throws Exception {
        if (!productRepository.findByproductcode(product.getProductcode()).equals(product.getProductcode())) {
            Category category = categoryRepository.findById(product.getCategoryId()).get();
            Producer producer = producerRepository.findById(product.getProducerId()).get();
            product.setCategory(category);
            product.setProducer(producer);
            productRepository.save(product);
            saveHistory(product, product.getAddress());
//            int l = 0;
//            if (l == 0) {
//                throw new Exception();
//            }
            instantiateOtherProducts(product);
            instantiateOtherProductsToOtherStores(product);
        }
    }

    public void instantiateOtherProducts(Product product) throws Exception {
        Category category = categoryRepository.findById(product.getCategoryId()).get();
        Producer producer = producerRepository.findById(product.getProducerId()).get();
        if (product.getColor().equals("White")) {
            Product pr1 = new Product();
            pr1.setColor("Yellow");
            pr1.setCategoryId(product.getCategoryId());
            pr1.setCost_eu(product.getCost_eu());
            pr1.setCost_usd(product.getCost_usd());
            pr1.setDescr(product.getDescr());
            pr1.setDiamond_weight(product.getDiamond_weight());
            pr1.setGold_weight(product.getGold_weight());
            pr1.setImageUrl("");
            pr1.setProducer(producer);
            pr1.setCategory(category);
            pr1.setKarats(product.getKarats());
            pr1.setNon_produce(false);
            pr1.setOther_stone(product.getOther_stone());
            pr1.setOther_stoneWeight(product.getOther_stoneWeight());
            pr1.setPrice(product.getPrice());
            pr1.setProducerId(product.getProducerId());
            pr1.setAddress(product.getAddress());
            pr1.setSilver_weight(product.getSilver_weight());
            pr1.setProductcode(product.getProductcode());
            pr1.setQuantity(0);
            productRepository.save(pr1);
            saveHistory(pr1, product.getAddress());
            Product pr2 = new Product();
            pr2.setColor("Black");
            pr2.setCategoryId(product.getCategoryId());
            pr2.setCost_eu(product.getCost_eu());
            pr2.setCost_usd(product.getCost_usd());
            pr2.setDescr(product.getDescr());
            pr2.setProducer(producer);
            pr2.setCategory(category);
            pr2.setDiamond_weight(product.getDiamond_weight());
            pr2.setGold_weight(product.getGold_weight());
            pr2.setImageUrl("");
            pr2.setKarats(product.getKarats());
            pr2.setNon_produce(false);
            pr2.setOther_stone(product.getOther_stone());
            pr2.setOther_stoneWeight(product.getOther_stoneWeight());
            pr2.setPrice(product.getPrice());
            pr2.setProducerId(product.getProducerId());
            pr2.setAddress(product.getAddress());
            pr2.setSilver_weight(product.getSilver_weight());
            pr2.setProductcode(product.getProductcode());
            pr2.setQuantity(0);
            productRepository.save(pr2);
            saveHistory(pr2, product.getAddress());
            Product pr3 = new Product();
            pr3.setColor("Rose");
            pr3.setCategoryId(product.getCategoryId());
            pr3.setCost_eu(product.getCost_eu());
            pr3.setCost_usd(product.getCost_usd());
            pr3.setDescr(product.getDescr());
            pr3.setDiamond_weight(product.getDiamond_weight());
            pr3.setGold_weight(product.getGold_weight());
            pr3.setProducer(producer);
            pr3.setCategory(category);
            pr3.setImageUrl("");
            pr3.setKarats(product.getKarats());
            pr3.setNon_produce(false);
            pr3.setOther_stone(product.getOther_stone());
            pr3.setOther_stoneWeight(product.getOther_stoneWeight());
            pr3.setPrice(product.getPrice());
            pr3.setProducerId(product.getProducerId());
            pr3.setAddress(product.getAddress());
            pr3.setSilver_weight(product.getSilver_weight());
            pr3.setProductcode(product.getProductcode());
            pr3.setQuantity(0);
            productRepository.save(pr3);
            saveHistory(pr3, product.getAddress());
        }
        if (product.getColor().equals("Black")) {
            Product pr1 = new Product();
            pr1.setColor("Yellow");
            pr1.setCategoryId(product.getCategoryId());
            pr1.setCost_eu(product.getCost_eu());
            pr1.setCost_usd(product.getCost_usd());
            pr1.setDescr(product.getDescr());
            pr1.setProducer(producer);
            pr1.setCategory(category);
            pr1.setDiamond_weight(product.getDiamond_weight());
            pr1.setGold_weight(product.getGold_weight());
            pr1.setImageUrl("");
            pr1.setKarats(product.getKarats());
            pr1.setNon_produce(false);
            pr1.setOther_stone(product.getOther_stone());
            pr1.setOther_stoneWeight(product.getOther_stoneWeight());
            pr1.setPrice(product.getPrice());
            pr1.setProducerId(product.getProducerId());
            pr1.setAddress(product.getAddress());
            pr1.setSilver_weight(product.getSilver_weight());
            pr1.setProductcode(product.getProductcode());
            pr1.setQuantity(0);
            productRepository.save(pr1);
            saveHistory(pr1, product.getAddress());
            Product pr2 = new Product();
            pr2.setColor("White");
            pr2.setCategoryId(product.getCategoryId());
            pr2.setCost_eu(product.getCost_eu());
            pr2.setCost_usd(product.getCost_usd());
            pr2.setDescr(product.getDescr());
            pr2.setDiamond_weight(product.getDiamond_weight());
            pr2.setGold_weight(product.getGold_weight());
            pr2.setProducer(producer);
            pr2.setCategory(category);
            pr2.setImageUrl("");
            pr2.setKarats(product.getKarats());
            pr2.setNon_produce(false);
            pr2.setOther_stone(product.getOther_stone());
            pr2.setOther_stoneWeight(product.getOther_stoneWeight());
            pr2.setPrice(product.getPrice());
            pr2.setProducerId(product.getProducerId());
            pr2.setAddress(product.getAddress());
            pr2.setSilver_weight(product.getSilver_weight());
            pr2.setProductcode(product.getProductcode());
            pr2.setQuantity(0);
            productRepository.save(pr2);
            saveHistory(pr2, product.getAddress());
            Product pr3 = new Product();
            pr3.setColor("Rose");
            pr3.setCategoryId(product.getCategoryId());
            pr3.setCost_eu(product.getCost_eu());
            pr3.setCost_usd(product.getCost_usd());
            pr3.setDescr(product.getDescr());
            pr3.setDiamond_weight(product.getDiamond_weight());
            pr3.setGold_weight(product.getGold_weight());
            pr3.setImageUrl("");
            pr3.setProducer(producer);
            pr3.setCategory(category);
            pr3.setKarats(product.getKarats());
            pr3.setNon_produce(false);
            pr3.setOther_stone(product.getOther_stone());
            pr3.setOther_stoneWeight(product.getOther_stoneWeight());
            pr3.setPrice(product.getPrice());
            pr3.setProducerId(product.getProducerId());
            pr3.setAddress(product.getAddress());
            pr3.setSilver_weight(product.getSilver_weight());
            pr3.setProductcode(product.getProductcode());
            pr3.setQuantity(0);
            productRepository.save(pr3);
            saveHistory(pr3, product.getAddress());
        }
        if (product.getColor().equals("Yellow")) {
            Product pr1 = new Product();
            pr1.setColor("White");
            pr1.setCategoryId(product.getCategoryId());
            pr1.setCost_eu(product.getCost_eu());
            pr1.setCost_usd(product.getCost_usd());
            pr1.setDescr(product.getDescr());
            pr1.setDiamond_weight(product.getDiamond_weight());
            pr1.setGold_weight(product.getGold_weight());
            pr1.setImageUrl("");
            pr1.setProducer(producer);
            pr1.setCategory(category);
            pr1.setKarats(product.getKarats());
            pr1.setNon_produce(false);
            pr1.setOther_stone(product.getOther_stone());
            pr1.setOther_stoneWeight(product.getOther_stoneWeight());
            pr1.setPrice(product.getPrice());
            pr1.setProducerId(product.getProducerId());
            pr1.setAddress(product.getAddress());
            pr1.setSilver_weight(product.getSilver_weight());
            pr1.setProductcode(product.getProductcode());
            pr1.setQuantity(0);
            productRepository.save(pr1);
            saveHistory(pr1, product.getAddress());
            Product pr2 = new Product();
            pr2.setColor("Black");
            pr2.setCategoryId(product.getCategoryId());
            pr2.setCost_eu(product.getCost_eu());
            pr2.setCost_usd(product.getCost_usd());
            pr2.setDescr(product.getDescr());
            pr2.setDiamond_weight(product.getDiamond_weight());
            pr2.setGold_weight(product.getGold_weight());
            pr2.setImageUrl("");
            pr2.setProducer(producer);
            pr2.setCategory(category);
            pr2.setKarats(product.getKarats());
            pr2.setNon_produce(false);
            pr2.setOther_stone(product.getOther_stone());
            pr2.setOther_stoneWeight(product.getOther_stoneWeight());
            pr2.setPrice(product.getPrice());
            pr2.setProducerId(product.getProducerId());
            pr2.setAddress(product.getAddress());
            pr2.setSilver_weight(product.getSilver_weight());
            pr2.setProductcode(product.getProductcode());
            pr2.setQuantity(0);
            productRepository.save(pr2);
            saveHistory(pr2, product.getAddress());
            Product pr3 = new Product();
            pr3.setColor("Rose");
            pr3.setCategoryId(product.getCategoryId());
            pr3.setCost_eu(product.getCost_eu());
            pr3.setCost_usd(product.getCost_usd());
            pr3.setDescr(product.getDescr());
            pr3.setProducer(producer);
            pr3.setCategory(category);
            pr3.setDiamond_weight(product.getDiamond_weight());
            pr3.setGold_weight(product.getGold_weight());
            pr3.setImageUrl("");
            pr3.setKarats(product.getKarats());
            pr3.setNon_produce(false);
            pr3.setOther_stone(product.getOther_stone());
            pr3.setOther_stoneWeight(product.getOther_stoneWeight());
            pr3.setPrice(product.getPrice());
            pr3.setProducerId(product.getProducerId());
            pr3.setAddress(product.getAddress());
            pr3.setSilver_weight(product.getSilver_weight());
            pr3.setProductcode(product.getProductcode());
            pr3.setQuantity(0);
            productRepository.save(pr3);
            saveHistory(pr3, product.getAddress());
        }
        if (product.getColor().equals("Rose")) {
            Product pr1 = new Product();
            pr1.setColor("Yellow");
            pr1.setCategoryId(product.getCategoryId());
            pr1.setCost_eu(product.getCost_eu());
            pr1.setCost_usd(product.getCost_usd());
            pr1.setDescr(product.getDescr());
            pr1.setDiamond_weight(product.getDiamond_weight());
            pr1.setGold_weight(product.getGold_weight());
            pr1.setImageUrl("");
            pr1.setProducer(producer);
            pr1.setCategory(category);
            pr1.setKarats(product.getKarats());
            pr1.setNon_produce(false);
            pr1.setOther_stone(product.getOther_stone());
            pr1.setOther_stoneWeight(product.getOther_stoneWeight());
            pr1.setPrice(product.getPrice());
            pr1.setProducerId(product.getProducerId());
            pr1.setAddress(product.getAddress());
            pr1.setSilver_weight(product.getSilver_weight());
            pr1.setProductcode(product.getProductcode());
            pr1.setQuantity(0);
            productRepository.save(pr1);
            saveHistory(pr1, product.getAddress());
            Product pr2 = new Product();
            pr2.setColor("Black");
            pr2.setCategoryId(product.getCategoryId());
            pr2.setCost_eu(product.getCost_eu());
            pr2.setProducer(producer);
            pr2.setCategory(category);
            pr2.setCost_usd(product.getCost_usd());
            pr2.setDescr(product.getDescr());
            pr2.setDiamond_weight(product.getDiamond_weight());
            pr2.setGold_weight(product.getGold_weight());
            pr2.setImageUrl("");
            pr2.setKarats(product.getKarats());
            pr2.setNon_produce(false);
            pr2.setOther_stone(product.getOther_stone());
            pr2.setOther_stoneWeight(product.getOther_stoneWeight());
            pr2.setPrice(product.getPrice());
            pr2.setProducerId(product.getProducerId());
            pr2.setAddress(product.getAddress());
            pr2.setSilver_weight(product.getSilver_weight());
            pr2.setProductcode(product.getProductcode());
            pr2.setQuantity(0);
            productRepository.save(pr2);
            saveHistory(pr2, product.getAddress());
            Product pr3 = new Product();
            pr3.setColor("White");
            pr3.setCategoryId(product.getCategoryId());
            pr3.setCost_eu(product.getCost_eu());
            pr3.setCost_usd(product.getCost_usd());
            pr3.setDescr(product.getDescr());
            pr3.setProducer(producer);
            pr3.setCategory(category);
            pr3.setDiamond_weight(product.getDiamond_weight());
            pr3.setGold_weight(product.getGold_weight());
            pr3.setImageUrl("");
            pr3.setKarats(product.getKarats());
            pr3.setNon_produce(false);
            pr3.setOther_stone(product.getOther_stone());
            pr3.setOther_stoneWeight(product.getOther_stoneWeight());
            pr3.setPrice(product.getPrice());
            pr3.setProducerId(product.getProducerId());
            pr3.setAddress(product.getAddress());
            pr3.setSilver_weight(product.getSilver_weight());
            pr3.setProductcode(product.getProductcode());
            pr3.setQuantity(0);
            productRepository.save(pr3);
            saveHistory(pr3, product.getAddress());
        }
    }

    public void instantiateOtherProductsToOtherStores(Product product) {
        Category category = categoryRepository.findById(product.getCategoryId()).get();
        Producer producer = producerRepository.findById(product.getProducerId()).get();
        for (Store store : storeRepository.findAll()) {
            if (!product.getAddress().equals(store.getAddress())) {
                if (product.getColor().equals("White")) {
                    Product pr1 = new Product();
                    pr1.setColor("Yellow");
                    pr1.setCategoryId(product.getCategoryId());
                    pr1.setCost_eu(product.getCost_eu());
                    pr1.setCost_usd(product.getCost_usd());
                    pr1.setDescr(product.getDescr());
                    pr1.setDiamond_weight(product.getDiamond_weight());
                    pr1.setGold_weight(product.getGold_weight());
                    pr1.setImageUrl("");
                    pr1.setProducer(producer);
                    pr1.setCategory(category);
                    pr1.setKarats(product.getKarats());
                    pr1.setNon_produce(false);
                    pr1.setOther_stone(product.getOther_stone());
                    pr1.setOther_stoneWeight(product.getOther_stoneWeight());
                    pr1.setPrice(product.getPrice());
                    pr1.setProducerId(product.getProducerId());
                    pr1.setAddress(store.getAddress());
                    pr1.setSilver_weight(product.getSilver_weight());
                    pr1.setProductcode(product.getProductcode());
                    pr1.setQuantity(0);
                    productRepository.save(pr1);
                    saveHistory(pr1, store.getAddress());
                    Product pr2 = new Product();
                    pr2.setColor("Black");
                    pr2.setCategoryId(product.getCategoryId());
                    pr2.setCost_eu(product.getCost_eu());
                    pr2.setCost_usd(product.getCost_usd());
                    pr2.setDescr(product.getDescr());
                    pr2.setDiamond_weight(product.getDiamond_weight());
                    pr2.setGold_weight(product.getGold_weight());
                    pr2.setImageUrl("");
                    pr2.setProducer(producer);
                    pr2.setCategory(category);
                    pr2.setKarats(product.getKarats());
                    pr2.setNon_produce(false);
                    pr2.setOther_stone(product.getOther_stone());
                    pr2.setOther_stoneWeight(product.getOther_stoneWeight());
                    pr2.setPrice(product.getPrice());
                    pr2.setProducerId(product.getProducerId());
                    pr2.setAddress(store.getAddress());
                    pr2.setSilver_weight(product.getSilver_weight());
                    pr2.setProductcode(product.getProductcode());
                    pr2.setQuantity(0);
                    productRepository.save(pr2);
                    saveHistory(pr2, store.getAddress());
                    Product pr3 = new Product();
                    pr3.setColor("Rose");
                    pr3.setCategoryId(product.getCategoryId());
                    pr3.setCost_eu(product.getCost_eu());
                    pr3.setCost_usd(product.getCost_usd());
                    pr3.setDescr(product.getDescr());
                    pr3.setDiamond_weight(product.getDiamond_weight());
                    pr3.setGold_weight(product.getGold_weight());
                    pr3.setImageUrl("");
                    pr3.setProducer(producer);
                    pr3.setCategory(category);
                    pr3.setKarats(product.getKarats());
                    pr3.setNon_produce(false);
                    pr3.setOther_stone(product.getOther_stone());
                    pr3.setOther_stoneWeight(product.getOther_stoneWeight());
                    pr3.setPrice(product.getPrice());
                    pr3.setProducerId(product.getProducerId());
                    pr3.setAddress(store.getAddress());
                    pr3.setSilver_weight(product.getSilver_weight());
                    pr3.setProductcode(product.getProductcode());
                    pr3.setQuantity(0);
                    productRepository.save(pr3);
                    saveHistory(pr3, store.getAddress());
                    Product pr4 = new Product();
                    pr4.setColor("White");
                    pr4.setCategoryId(product.getCategoryId());
                    pr4.setCost_eu(product.getCost_eu());
                    pr4.setCost_usd(product.getCost_usd());
                    pr4.setDescr(product.getDescr());
                    pr4.setDiamond_weight(product.getDiamond_weight());
                    pr4.setGold_weight(product.getGold_weight());
                    pr4.setImageUrl("");
                    pr4.setProducer(producer);
                    pr4.setCategory(category);
                    pr4.setKarats(product.getKarats());
                    pr4.setNon_produce(false);
                    pr4.setOther_stone(product.getOther_stone());
                    pr4.setOther_stoneWeight(product.getOther_stoneWeight());
                    pr4.setPrice(product.getPrice());
                    pr4.setProducerId(product.getProducerId());
                    pr4.setAddress(store.getAddress());
                    pr4.setSilver_weight(product.getSilver_weight());
                    pr4.setProductcode(product.getProductcode());
                    pr4.setQuantity(0);
                    productRepository.save(pr4);
                    saveHistory(pr4, store.getAddress());
                }
                if (product.getColor().equals("Black")) {
                    Product pr1 = new Product();
                    pr1.setColor("Yellow");
                    pr1.setCategoryId(product.getCategoryId());
                    pr1.setCost_eu(product.getCost_eu());
                    pr1.setCost_usd(product.getCost_usd());
                    pr1.setDescr(product.getDescr());
                    pr1.setDiamond_weight(product.getDiamond_weight());
                    pr1.setGold_weight(product.getGold_weight());
                    pr1.setImageUrl("");
                    pr1.setProducer(producer);
                    pr1.setCategory(category);
                    pr1.setKarats(product.getKarats());
                    pr1.setNon_produce(false);
                    pr1.setOther_stone(product.getOther_stone());
                    pr1.setOther_stoneWeight(product.getOther_stoneWeight());
                    pr1.setPrice(product.getPrice());
                    pr1.setProducerId(product.getProducerId());
                    pr1.setAddress(store.getAddress());
                    pr1.setSilver_weight(product.getSilver_weight());
                    pr1.setProductcode(product.getProductcode());
                    pr1.setQuantity(0);
                    productRepository.save(pr1);
                    saveHistory(pr1, store.getAddress());
                    Product pr2 = new Product();
                    pr2.setColor("White");
                    pr2.setCategoryId(product.getCategoryId());
                    pr2.setCost_eu(product.getCost_eu());
                    pr2.setCost_usd(product.getCost_usd());
                    pr2.setDescr(product.getDescr());
                    pr2.setDiamond_weight(product.getDiamond_weight());
                    pr2.setGold_weight(product.getGold_weight());
                    pr2.setImageUrl("");
                    pr2.setProducer(producer);
                    pr2.setCategory(category);
                    pr2.setKarats(product.getKarats());
                    pr2.setNon_produce(false);
                    pr2.setOther_stone(product.getOther_stone());
                    pr2.setOther_stoneWeight(product.getOther_stoneWeight());
                    pr2.setPrice(product.getPrice());
                    pr2.setProducerId(product.getProducerId());
                    pr2.setAddress(store.getAddress());
                    pr2.setSilver_weight(product.getSilver_weight());
                    pr2.setProductcode(product.getProductcode());
                    pr2.setQuantity(0);
                    productRepository.save(pr2);
                    saveHistory(pr2, store.getAddress());
                    Product pr3 = new Product();
                    pr3.setColor("Rose");
                    pr3.setCategoryId(product.getCategoryId());
                    pr3.setCost_eu(product.getCost_eu());
                    pr3.setCost_usd(product.getCost_usd());
                    pr3.setDescr(product.getDescr());
                    pr3.setDiamond_weight(product.getDiamond_weight());
                    pr3.setGold_weight(product.getGold_weight());
                    pr3.setImageUrl("");
                    pr3.setProducer(producer);
                    pr3.setCategory(category);
                    pr3.setKarats(product.getKarats());
                    pr3.setNon_produce(false);
                    pr3.setOther_stone(product.getOther_stone());
                    pr3.setOther_stoneWeight(product.getOther_stoneWeight());
                    pr3.setPrice(product.getPrice());
                    pr3.setProducerId(product.getProducerId());
                    pr3.setAddress(store.getAddress());
                    pr3.setSilver_weight(product.getSilver_weight());
                    pr3.setProductcode(product.getProductcode());
                    pr3.setQuantity(0);
                    productRepository.save(pr3);
                    saveHistory(pr3, store.getAddress());
                    Product pr4 = new Product();
                    pr4.setColor("Black");
                    pr4.setCategoryId(product.getCategoryId());
                    pr4.setCost_eu(product.getCost_eu());
                    pr4.setCost_usd(product.getCost_usd());
                    pr4.setDescr(product.getDescr());
                    pr4.setDiamond_weight(product.getDiamond_weight());
                    pr4.setGold_weight(product.getGold_weight());
                    pr4.setImageUrl("");
                    pr4.setProducer(producer);
                    pr4.setCategory(category);
                    pr4.setKarats(product.getKarats());
                    pr4.setNon_produce(false);
                    pr4.setOther_stone(product.getOther_stone());
                    pr4.setOther_stoneWeight(product.getOther_stoneWeight());
                    pr4.setPrice(product.getPrice());
                    pr4.setProducerId(product.getProducerId());
                    pr4.setAddress(store.getAddress());
                    pr4.setSilver_weight(product.getSilver_weight());
                    pr4.setProductcode(product.getProductcode());
                    pr4.setQuantity(0);
                    productRepository.save(pr4);
                    saveHistory(pr4, store.getAddress());
                }
                if (product.getColor().equals("Yellow")) {
                    Product pr1 = new Product();
                    pr1.setColor("White");
                    pr1.setCategoryId(product.getCategoryId());
                    pr1.setCost_eu(product.getCost_eu());
                    pr1.setCost_usd(product.getCost_usd());
                    pr1.setDescr(product.getDescr());
                    pr1.setDiamond_weight(product.getDiamond_weight());
                    pr1.setGold_weight(product.getGold_weight());
                    pr1.setImageUrl("");
                    pr1.setProducer(producer);
                    pr1.setCategory(category);
                    pr1.setKarats(product.getKarats());
                    pr1.setNon_produce(false);
                    pr1.setOther_stone(product.getOther_stone());
                    pr1.setOther_stoneWeight(product.getOther_stoneWeight());
                    pr1.setPrice(product.getPrice());
                    pr1.setProducerId(product.getProducerId());
                    pr1.setAddress(store.getAddress());
                    pr1.setSilver_weight(product.getSilver_weight());
                    pr1.setProductcode(product.getProductcode());
                    pr1.setQuantity(0);
                    productRepository.save(pr1);
                    saveHistory(pr1, store.getAddress());
                    Product pr2 = new Product();
                    pr2.setColor("Black");
                    pr2.setCategoryId(product.getCategoryId());
                    pr2.setCost_eu(product.getCost_eu());
                    pr2.setCost_usd(product.getCost_usd());
                    pr2.setDescr(product.getDescr());
                    pr2.setDiamond_weight(product.getDiamond_weight());
                    pr2.setGold_weight(product.getGold_weight());
                    pr2.setImageUrl("");
                    pr2.setProducer(producer);
                    pr2.setCategory(category);
                    pr2.setKarats(product.getKarats());
                    pr2.setNon_produce(false);
                    pr2.setOther_stone(product.getOther_stone());
                    pr2.setOther_stoneWeight(product.getOther_stoneWeight());
                    pr2.setPrice(product.getPrice());
                    pr2.setProducerId(product.getProducerId());
                    pr2.setAddress(store.getAddress());
                    pr2.setSilver_weight(product.getSilver_weight());
                    pr2.setProductcode(product.getProductcode());
                    pr2.setQuantity(0);
                    productRepository.save(pr2);
                    saveHistory(pr2, store.getAddress());
                    Product pr3 = new Product();
                    pr3.setColor("Rose");
                    pr3.setCategoryId(product.getCategoryId());
                    pr3.setCost_eu(product.getCost_eu());
                    pr3.setCost_usd(product.getCost_usd());
                    pr3.setDescr(product.getDescr());
                    pr3.setDiamond_weight(product.getDiamond_weight());
                    pr3.setGold_weight(product.getGold_weight());
                    pr3.setImageUrl("");
                    pr3.setProducer(producer);
                    pr3.setCategory(category);
                    pr3.setKarats(product.getKarats());
                    pr3.setNon_produce(false);
                    pr3.setOther_stone(product.getOther_stone());
                    pr3.setOther_stoneWeight(product.getOther_stoneWeight());
                    pr3.setPrice(product.getPrice());
                    pr3.setProducerId(product.getProducerId());
                    pr3.setAddress(store.getAddress());
                    pr3.setSilver_weight(product.getSilver_weight());
                    pr3.setProductcode(product.getProductcode());
                    pr3.setQuantity(0);
                    productRepository.save(pr3);
                    saveHistory(pr3, store.getAddress());
                    Product pr4 = new Product();
                    pr4.setColor("Yellow");
                    pr4.setCategoryId(product.getCategoryId());
                    pr4.setCost_eu(product.getCost_eu());
                    pr4.setCost_usd(product.getCost_usd());
                    pr4.setDescr(product.getDescr());
                    pr4.setDiamond_weight(product.getDiamond_weight());
                    pr4.setGold_weight(product.getGold_weight());
                    pr4.setImageUrl("");
                    pr4.setProducer(producer);
                    pr4.setCategory(category);
                    pr4.setKarats(product.getKarats());
                    pr4.setNon_produce(false);
                    pr4.setOther_stone(product.getOther_stone());
                    pr4.setOther_stoneWeight(product.getOther_stoneWeight());
                    pr4.setPrice(product.getPrice());
                    pr4.setProducerId(product.getProducerId());
                    pr4.setAddress(store.getAddress());
                    pr4.setSilver_weight(product.getSilver_weight());
                    pr4.setProductcode(product.getProductcode());
                    pr4.setQuantity(0);
                    productRepository.save(pr4);
                    saveHistory(pr4, store.getAddress());
                }
                if (product.getColor().equals("Rose")) {
                    Product pr1 = new Product();
                    pr1.setColor("Yellow");
                    pr1.setCategoryId(product.getCategoryId());
                    pr1.setCost_eu(product.getCost_eu());
                    pr1.setCost_usd(product.getCost_usd());
                    pr1.setDescr(product.getDescr());
                    pr1.setDiamond_weight(product.getDiamond_weight());
                    pr1.setGold_weight(product.getGold_weight());
                    pr1.setImageUrl("");
                    pr1.setProducer(producer);
                    pr1.setCategory(category);
                    pr1.setKarats(product.getKarats());
                    pr1.setNon_produce(false);
                    pr1.setOther_stone(product.getOther_stone());
                    pr1.setOther_stoneWeight(product.getOther_stoneWeight());
                    pr1.setPrice(product.getPrice());
                    pr1.setProducerId(product.getProducerId());
                    pr1.setAddress(store.getAddress());
                    pr1.setSilver_weight(product.getSilver_weight());
                    pr1.setProductcode(product.getProductcode());
                    pr1.setQuantity(0);
                    productRepository.save(pr1);
                    saveHistory(pr1, store.getAddress());
                    Product pr2 = new Product();
                    pr2.setColor("Black");
                    pr2.setProducer(producer);
                    pr2.setCategory(category);
                    pr2.setCategoryId(product.getCategoryId());
                    pr2.setCost_eu(product.getCost_eu());
                    pr2.setCost_usd(product.getCost_usd());
                    pr2.setDescr(product.getDescr());
                    pr2.setDiamond_weight(product.getDiamond_weight());
                    pr2.setGold_weight(product.getGold_weight());
                    pr2.setImageUrl("");
                    pr2.setKarats(product.getKarats());
                    pr2.setNon_produce(false);
                    pr2.setOther_stone(product.getOther_stone());
                    pr2.setOther_stoneWeight(product.getOther_stoneWeight());
                    pr2.setPrice(product.getPrice());
                    pr2.setProducerId(product.getProducerId());
                    pr2.setAddress(store.getAddress());
                    pr2.setSilver_weight(product.getSilver_weight());
                    pr2.setProductcode(product.getProductcode());
                    pr2.setQuantity(0);
                    productRepository.save(pr2);
                    saveHistory(pr2, store.getAddress());
                    Product pr3 = new Product();
                    pr3.setColor("White");
                    pr3.setCategoryId(product.getCategoryId());
                    pr3.setCost_eu(product.getCost_eu());
                    pr3.setCost_usd(product.getCost_usd());
                    pr3.setDescr(product.getDescr());
                    pr3.setDiamond_weight(product.getDiamond_weight());
                    pr3.setGold_weight(product.getGold_weight());
                    pr3.setImageUrl("");
                    pr3.setProducer(producer);
                    pr3.setCategory(category);
                    pr3.setKarats(product.getKarats());
                    pr3.setNon_produce(false);
                    pr3.setOther_stone(product.getOther_stone());
                    pr3.setOther_stoneWeight(product.getOther_stoneWeight());
                    pr3.setPrice(product.getPrice());
                    pr3.setProducerId(product.getProducerId());
                    pr3.setAddress(store.getAddress());
                    pr3.setSilver_weight(product.getSilver_weight());
                    pr3.setProductcode(product.getProductcode());
                    pr3.setQuantity(0);
                    productRepository.save(pr3);
                    saveHistory(pr3, store.getAddress());
                    Product pr4 = new Product();
                    pr4.setColor("Rose");
                    pr4.setCategoryId(product.getCategoryId());
                    pr4.setCost_eu(product.getCost_eu());
                    pr4.setCost_usd(product.getCost_usd());
                    pr4.setDescr(product.getDescr());
                    pr4.setDiamond_weight(product.getDiamond_weight());
                    pr4.setGold_weight(product.getGold_weight());
                    pr4.setImageUrl("");
                    pr4.setProducer(producer);
                    pr4.setCategory(category);
                    pr4.setKarats(product.getKarats());
                    pr4.setNon_produce(false);
                    pr4.setOther_stone(product.getOther_stone());
                    pr4.setOther_stoneWeight(product.getOther_stoneWeight());
                    pr4.setPrice(product.getPrice());
                    pr4.setProducerId(product.getProducerId());
                    pr4.setAddress(store.getAddress());
                    pr4.setSilver_weight(product.getSilver_weight());
                    pr4.setProductcode(product.getProductcode());
                    pr4.setQuantity(0);
                    productRepository.save(pr4);
                    saveHistory(pr4, store.getAddress());
                }
            }
        }
    }

    private void saveHistory(Product product, String storeId) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String timestamp = dtf.format(now);
        History productHistory = new History();
        productHistory.setTimestamp(timestamp);
        productHistory.setProduct(product);
        String productId = String.valueOf(product.getId());
        productHistory.setProductId(productId);
        productHistory.setStoreId(storeId);
        productHistory.setProducerId(product.getProducerId());
        productHistory.setProducerId(product.getCategoryId());
        historyrepository.save(productHistory);
    }

    public void makeItDisable(Product product) {
        product.setNon_produce(true);
        productRepository.save(product);
    }

    public void updateQuantity(String productId, int quantity) {
        Product product = new Product();
        try {
            product = productRepository.findById(productId).get();
        } catch (Exception e) {
            throw e;
        }
        product.setQuantity(quantity);
        try {
            productRepository.save(product);
        } catch (Exception e) {
            throw e;
        }
    }

    public List<List<?>> getProductsPerFilterCase(int page, String categoryId, String producerId, String storeId, String limit) {
        List<Product> products = pagination.getProductsPaginated(page, categoryId, storeId, producerId, limit);
        List<Map<String, Integer>> listMaxSize = new ArrayList();
        listMaxSize.add(pagination.getMaxSize(storeId, producerId, categoryId));
        List<List<?>> productsWithMaxSize = new ArrayList();
        productsWithMaxSize.add(products);
        productsWithMaxSize.add(listMaxSize);
        return productsWithMaxSize;
    }

    public void deleteProduct(String productCode) {
        for (Product product : productRepository.findByproductcode(productCode)) {
            productRepository.delete(product);
        }
    }
    
    public void updateProduct(Product product) {
        Product pr = productRepository.findById(product.getId()).get();
        if (!pr.getProductcode().equals(product.getProductcode()) || !pr.getDescr().equals(product.getDescr())
                || !pr.getCost_eu().equals(product.getCost_eu()) || !pr.getCost_usd().equals(product.getCost_usd())
                || !pr.getPrice().equals(product.getPrice()) || !pr.getCategoryId().equals(product.getCategoryId())
                || !pr.getProducerId().equals(product.getProducerId()) || !pr.getKarats().equals(product.getKarats())) {
            productRepository.save(product);
            Category category = categoryRepository.findById(product.getCategoryId()).get();
            Producer producer = producerRepository.findById(product.getProducerId()).get();
            for (Product prod : productRepository.findByproductcode(pr.getProductcode())) {
                if (!prod.getId().equals(pr.getId())) {
                    prod.setProductcode(product.getProductcode());
                    prod.setDescr(product.getDescr());
                    prod.setCategoryId(product.getCategoryId());
                    prod.setProducerId(product.getProducerId());
                    prod.setCost_eu(product.getCost_eu());
                    prod.setCost_usd(product.getCost_usd());
                    prod.setPrice(product.getPrice());
                    prod.setCategory(category);
                    prod.setProducer(producer);
                    prod.setKarats(product.getKarats());
                    productRepository.save(prod);
                }
            }
            for (Product prod : productRepository.findByproductcodeAndColor(pr.getProductcode(), pr.getColor())) {
                if (!prod.getId().equals(pr.getId())) {
                    prod.setOther_stone(product.getOther_stone());
                    prod.setDiamond_weight(product.getDiamond_weight());
                    prod.setGold_weight(product.getGold_weight());
                    prod.setSilver_weight(product.getSilver_weight());
                    productRepository.save(prod);
                }
            }
        }
    }

}
