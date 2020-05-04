package com.example.StoreWarehouseMongo1.dao;

import com.example.StoreWarehouseMongo1.Exceptions.ProductFoundException;
import com.example.StoreWarehouseMongo1.helpers.CachingService;
import com.example.StoreWarehouseMongo1.helpers.Pagination;
import com.example.StoreWarehouseMongo1.model.History;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author Tasos
 */
@Component
public class ProductDAO {

    @Autowired
    private CachingService cachingService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private HistoryRepository historyrepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private Pagination pagination;

    @Autowired
    private MongoTemplate mongoTemplate;

    public ResponseEntity<?> get(String productId) {
        Product product = new Product();
        List<List<?>> response = new ArrayList();
        List<Product> prod = new ArrayList();
        product = productRepository.findById(productId).get();
        String color = product.getColor();
        String sku = product.getSku();
        List<Map<?, ?>> others = new ArrayList();
        List<List<Map<?, ?>>> responseList = new ArrayList();
        for (Product pr : productRepository.findByskuAndColor(sku, color)) {
            if (!pr.getAddress().equals(product.getAddress())) {
                Map<String, Map<?, ?>> jsonObjectAll = new HashMap();
                Map<String, String> jsonObject = new HashMap();
                jsonObject.put("address", pr.getAddress());
                jsonObject.put("quantity", String.valueOf(pr.getQuantity()));
                jsonObjectAll.put("other", jsonObject);
                others.add(jsonObjectAll);
            }
        }
        responseList.add(others);
        prod.add(product);
        response.add(prod);
        response.add(responseList);
        return new ResponseEntity<List>(response, HttpStatus.OK);
    }

    //@Transactional
    public void insert(Product product) throws Exception {
        Product pr1 = new Product();
        Product pr2 = new Product();
        Product pr3 = new Product();
        Product pr4 = new Product();
        try {
            pr1 = productRepository.findBysku(product.getSku() + "-Y").get(0);
            pr2 = productRepository.findBysku(product.getSku() + "-W").get(0);
            pr3 = productRepository.findBysku(product.getSku() + "-B").get(0);
            pr4 = productRepository.findBysku(product.getSku() + "-R").get(0);
        } catch (Exception e) {
            if (pr1.getSku() == null) {
                pr1 = new Product();
                pr1.setSku("");
            }
            if (pr2.getSku() == null) {
                pr2 = new Product();
                pr2.setSku("");
            }
            if (pr3.getSku() == null) {
                pr3 = new Product();
                pr3.setSku("");
            }
            if (pr4.getSku() == null) {
                pr4 = new Product();
                pr4.setSku("");
            }
        }
        if ((!pr1.getSku().contains(product.getSku()) && pr2.getSku().contains(product.getSku())
                && pr3.getSku().contains(product.getSku()) && pr4.getSku().contains(product.getSku()))
                || (pr1.getSku().equals("") || pr2.getSku().equals("")) || pr3.getSku().equals("")
                || pr4.getSku().equals("")) {
            if (product.getColor().equals("White")) {
                product.setSku(product.getSku() + "-W");
            }
            if (product.getColor().equals("Yellow")) {
                product.setSku(product.getSku() + "-Y");
            }
            if (product.getColor().equals("Black")) {
                product.setSku(product.getSku() + "-B");
            }
            if (product.getColor().equals("Rose")) {
                product.setSku(product.getSku() + "-R");
            }
            productRepository.save(product);
            saveHistory(product, product.getAddress());
            instantiateOtherProducts(product);
            instantiateOtherProductsToOtherStores(product);
        } else {
            throw new ProductFoundException("This product is already exist");
        }
    }

    public void instantiateOtherProducts(Product product) throws Exception {
        if (product.getColor().equals("White")) {
            Product pr1 = new Product();
            pr1.setColor("Yellow");
            pr1.setCategoryId(product.getCategoryId());
            pr1.setCostEu(product.getCostEu());
            pr1.setCostUsd(product.getCostUsd());
            pr1.setDescription(product.getDescription());
            pr1.setDiamondWeight(product.getDiamondWeight());
            pr1.setGoldWeight(product.getGoldWeight());
            pr1.setImageUrl("");
            pr1.setKarats(product.getKarats());
            pr1.setNonProduce(false);
            pr1.setOtherStone(product.getOtherStone());
            pr1.setOtherStoneWeight(product.getOtherStoneWeight());
            pr1.setPrice(product.getPrice());
            pr1.setProducerId(product.getProducerId());
            pr1.setAddress(product.getAddress());
            pr1.setSilverWeight(product.getSilverWeight());
            pr1.setSku(product.getSku().replaceAll("-W", "-Y"));
            pr1.setQuantity(0);
            productRepository.save(pr1);
            saveHistory(pr1, product.getAddress());
            Product pr2 = new Product();
            pr2.setColor("Black");
            pr2.setCategoryId(product.getCategoryId());
            pr2.setCostEu(product.getCostEu());
            pr2.setCostUsd(product.getCostUsd());
            pr2.setDescription(product.getDescription());
            pr2.setDiamondWeight(product.getDiamondWeight());
            pr2.setGoldWeight(product.getGoldWeight());
            pr2.setImageUrl("");
            pr2.setKarats(product.getKarats());
            pr2.setNonProduce(false);
            pr2.setOtherStone(product.getOtherStone());
            pr2.setOtherStoneWeight(product.getOtherStoneWeight());
            pr2.setPrice(product.getPrice());
            pr2.setProducerId(product.getProducerId());
            pr2.setAddress(product.getAddress());
            pr2.setSilverWeight(product.getSilverWeight());
            pr2.setSku(product.getSku().replaceAll("-W", "-B"));
            pr2.setQuantity(0);
            productRepository.save(pr2);
            saveHistory(pr2, product.getAddress());
            Product pr3 = new Product();
            pr3.setColor("Rose");
            pr3.setCategoryId(product.getCategoryId());
            pr3.setCostEu(product.getCostEu());
            pr3.setCostUsd(product.getCostUsd());
            pr3.setDescription(product.getDescription());
            pr3.setDiamondWeight(product.getDiamondWeight());
            pr3.setGoldWeight(product.getGoldWeight());
            pr3.setImageUrl("");
            pr3.setKarats(product.getKarats());
            pr3.setNonProduce(false);
            pr3.setOtherStone(product.getOtherStone());
            pr3.setOtherStoneWeight(product.getOtherStoneWeight());
            pr3.setPrice(product.getPrice());
            pr3.setProducerId(product.getProducerId());
            pr3.setAddress(product.getAddress());
            pr3.setSilverWeight(product.getSilverWeight());
            pr3.setSku(product.getSku().replaceAll("-W", "-R"));
            pr3.setQuantity(0);
            productRepository.save(pr3);
            saveHistory(pr3, product.getAddress());
        }
        if (product.getColor().equals("Black")) {
            Product pr1 = new Product();
            pr1.setColor("Yellow");
            pr1.setCategoryId(product.getCategoryId());
            pr1.setCostEu(product.getCostEu());
            pr1.setCostUsd(product.getCostUsd());
            pr1.setDescription(product.getDescription());
            pr1.setDiamondWeight(product.getDiamondWeight());
            pr1.setGoldWeight(product.getGoldWeight());
            pr1.setImageUrl("");
            pr1.setKarats(product.getKarats());
            pr1.setNonProduce(false);
            pr1.setOtherStone(product.getOtherStone());
            pr1.setOtherStoneWeight(product.getOtherStoneWeight());
            pr1.setPrice(product.getPrice());
            pr1.setProducerId(product.getProducerId());
            pr1.setAddress(product.getAddress());
            pr1.setSilverWeight(product.getSilverWeight());
            pr1.setSku(product.getSku().replaceAll("-B", "-Y"));
            pr1.setQuantity(0);
            productRepository.save(pr1);
            saveHistory(pr1, product.getAddress());
            Product pr2 = new Product();
            pr2.setColor("White");
            pr2.setCategoryId(product.getCategoryId());
            pr2.setCostEu(product.getCostEu());
            pr2.setCostUsd(product.getCostUsd());
            pr2.setDescription(product.getDescription());
            pr2.setDiamondWeight(product.getDiamondWeight());
            pr2.setGoldWeight(product.getGoldWeight());
            pr2.setImageUrl("");
            pr2.setKarats(product.getKarats());
            pr2.setNonProduce(false);
            pr2.setOtherStone(product.getOtherStone());
            pr2.setOtherStoneWeight(product.getOtherStoneWeight());
            pr2.setPrice(product.getPrice());
            pr2.setProducerId(product.getProducerId());
            pr2.setAddress(product.getAddress());
            pr2.setSilverWeight(product.getSilverWeight());
            pr2.setSku(product.getSku().replaceAll("-B", "-W"));
            pr2.setQuantity(0);
            productRepository.save(pr2);
            saveHistory(pr2, product.getAddress());
            Product pr3 = new Product();
            pr3.setColor("Rose");
            pr3.setCategoryId(product.getCategoryId());
            pr3.setCostEu(product.getCostEu());
            pr3.setCostUsd(product.getCostUsd());
            pr3.setDescription(product.getDescription());
            pr3.setDiamondWeight(product.getDiamondWeight());
            pr3.setGoldWeight(product.getGoldWeight());
            pr3.setImageUrl("");
            pr3.setKarats(product.getKarats());
            pr3.setNonProduce(false);
            pr3.setOtherStone(product.getOtherStone());
            pr3.setOtherStoneWeight(product.getOtherStoneWeight());
            pr3.setPrice(product.getPrice());
            pr3.setProducerId(product.getProducerId());
            pr3.setAddress(product.getAddress());
            pr3.setSilverWeight(product.getSilverWeight());
            pr3.setSku(product.getSku().replaceAll("-B", "-R"));
            pr3.setQuantity(0);
            productRepository.save(pr3);
            saveHistory(pr3, product.getAddress());
        }
        if (product.getColor().equals("Yellow")) {
            Product pr1 = new Product();
            pr1.setColor("White");
            pr1.setCategoryId(product.getCategoryId());
            pr1.setCostEu(product.getCostEu());
            pr1.setCostUsd(product.getCostUsd());
            pr1.setDescription(product.getDescription());
            pr1.setDiamondWeight(product.getDiamondWeight());
            pr1.setGoldWeight(product.getGoldWeight());
            pr1.setImageUrl("");
            pr1.setKarats(product.getKarats());
            pr1.setNonProduce(false);
            pr1.setOtherStone(product.getOtherStone());
            pr1.setOtherStoneWeight(product.getOtherStoneWeight());
            pr1.setPrice(product.getPrice());
            pr1.setProducerId(product.getProducerId());
            pr1.setAddress(product.getAddress());
            pr1.setSilverWeight(product.getSilverWeight());
            pr1.setSku(product.getSku().replaceAll("-Y", "-W"));
            pr1.setQuantity(0);
            productRepository.save(pr1);
            saveHistory(pr1, product.getAddress());
            Product pr2 = new Product();
            pr2.setColor("Black");
            pr2.setCategoryId(product.getCategoryId());
            pr2.setCostEu(product.getCostEu());
            pr2.setCostUsd(product.getCostUsd());
            pr2.setDescription(product.getDescription());
            pr2.setDiamondWeight(product.getDiamondWeight());
            pr2.setGoldWeight(product.getGoldWeight());
            pr2.setImageUrl("");
            pr2.setKarats(product.getKarats());
            pr2.setNonProduce(false);
            pr2.setOtherStone(product.getOtherStone());
            pr2.setOtherStoneWeight(product.getOtherStoneWeight());
            pr2.setPrice(product.getPrice());
            pr2.setProducerId(product.getProducerId());
            pr2.setAddress(product.getAddress());
            pr2.setSilverWeight(product.getSilverWeight());
            pr2.setSku(product.getSku().replaceAll("-Y", "-B"));
            pr2.setQuantity(0);
            productRepository.save(pr2);
            saveHistory(pr2, product.getAddress());
            Product pr3 = new Product();
            pr3.setColor("Rose");
            pr3.setCategoryId(product.getCategoryId());
            pr3.setCostEu(product.getCostEu());
            pr3.setCostUsd(product.getCostUsd());
            pr3.setDescription(product.getDescription());
            pr3.setDiamondWeight(product.getDiamondWeight());
            pr3.setGoldWeight(product.getGoldWeight());
            pr3.setImageUrl("");
            pr3.setKarats(product.getKarats());
            pr3.setNonProduce(false);
            pr3.setOtherStone(product.getOtherStone());
            pr3.setOtherStoneWeight(product.getOtherStoneWeight());
            pr3.setPrice(product.getPrice());
            pr3.setProducerId(product.getProducerId());
            pr3.setAddress(product.getAddress());
            pr3.setSilverWeight(product.getSilverWeight());
            pr3.setSku(product.getSku().replaceAll("-Y", "-R"));
            pr3.setQuantity(0);
            productRepository.save(pr3);
            saveHistory(pr3, product.getAddress());
        }
        if (product.getColor().equals("Rose")) {
            Product pr1 = new Product();
            pr1.setColor("Yellow");
            pr1.setCategoryId(product.getCategoryId());
            pr1.setCostEu(product.getCostEu());
            pr1.setCostUsd(product.getCostUsd());
            pr1.setDescription(product.getDescription());
            pr1.setDiamondWeight(product.getDiamondWeight());
            pr1.setGoldWeight(product.getGoldWeight());
            pr1.setImageUrl("");
            pr1.setKarats(product.getKarats());
            pr1.setNonProduce(false);
            pr1.setOtherStone(product.getOtherStone());
            pr1.setOtherStoneWeight(product.getOtherStoneWeight());
            pr1.setPrice(product.getPrice());
            pr1.setProducerId(product.getProducerId());
            pr1.setAddress(product.getAddress());
            pr1.setSilverWeight(product.getSilverWeight());
            pr1.setSku(product.getSku().replaceAll("-R", "-Y"));
            pr1.setQuantity(0);
            productRepository.save(pr1);
            saveHistory(pr1, product.getAddress());
            Product pr2 = new Product();
            pr2.setColor("Black");
            pr2.setCategoryId(product.getCategoryId());
            pr2.setCostEu(product.getCostEu());
            pr2.setCostUsd(product.getCostUsd());
            pr2.setDescription(product.getDescription());
            pr2.setDiamondWeight(product.getDiamondWeight());
            pr2.setGoldWeight(product.getGoldWeight());
            pr2.setImageUrl("");
            pr2.setKarats(product.getKarats());
            pr2.setNonProduce(false);
            pr2.setOtherStone(product.getOtherStone());
            pr2.setOtherStoneWeight(product.getOtherStoneWeight());
            pr2.setPrice(product.getPrice());
            pr2.setProducerId(product.getProducerId());
            pr2.setAddress(product.getAddress());
            pr2.setSilverWeight(product.getSilverWeight());
            pr2.setSku(product.getSku().replaceAll("-R", "-B"));
            pr2.setQuantity(0);
            productRepository.save(pr2);
            saveHistory(pr2, product.getAddress());
            Product pr3 = new Product();
            pr3.setColor("White");
            pr3.setCategoryId(product.getCategoryId());
            pr3.setCostEu(product.getCostEu());
            pr3.setCostUsd(product.getCostUsd());
            pr3.setDescription(product.getDescription());
            pr3.setDiamondWeight(product.getDiamondWeight());
            pr3.setGoldWeight(product.getGoldWeight());
            pr3.setImageUrl("");
            pr3.setKarats(product.getKarats());
            pr3.setNonProduce(false);
            pr3.setOtherStone(product.getOtherStone());
            pr3.setOtherStoneWeight(product.getOtherStoneWeight());
            pr3.setPrice(product.getPrice());
            pr3.setProducerId(product.getProducerId());
            pr3.setAddress(product.getAddress());
            pr3.setSilverWeight(product.getSilverWeight());
            pr3.setSku(product.getSku().replaceAll("-R", "-W"));
            pr3.setQuantity(0);
            productRepository.save(pr3);
            saveHistory(pr3, product.getAddress());
        }
    }

    public void instantiateOtherProductsToOtherStores(Product product) {
        for (Store store : storeRepository.findAll()) {
            if (!product.getAddress().equals(store.getAddress())) {
                if (product.getColor().equals("White")) {
                    Product pr1 = new Product();
                    pr1.setColor("Yellow");
                    pr1.setCategoryId(product.getCategoryId());
                    pr1.setCostEu(product.getCostEu());
                    pr1.setCostUsd(product.getCostUsd());
                    pr1.setDescription(product.getDescription());
                    pr1.setDiamondWeight(product.getDiamondWeight());
                    pr1.setGoldWeight(product.getGoldWeight());
                    pr1.setImageUrl("");
                    pr1.setKarats(product.getKarats());
                    pr1.setNonProduce(false);
                    pr1.setOtherStone(product.getOtherStone());
                    pr1.setOtherStoneWeight(product.getOtherStoneWeight());
                    pr1.setPrice(product.getPrice());
                    pr1.setProducerId(product.getProducerId());
                    pr1.setAddress(store.getAddress());
                    pr1.setSilverWeight(product.getSilverWeight());
                    pr1.setSku(product.getSku().replaceAll("-W", "-Y"));
                    pr1.setQuantity(0);
                    productRepository.save(pr1);
                    saveHistory(pr1, store.getAddress());
                    Product pr2 = new Product();
                    pr2.setColor("Black");
                    pr2.setCategoryId(product.getCategoryId());
                    pr2.setCostEu(product.getCostEu());
                    pr2.setCostUsd(product.getCostUsd());
                    pr2.setDescription(product.getDescription());
                    pr2.setDiamondWeight(product.getDiamondWeight());
                    pr2.setGoldWeight(product.getGoldWeight());
                    pr2.setImageUrl("");
                    pr2.setKarats(product.getKarats());
                    pr2.setNonProduce(false);
                    pr2.setOtherStone(product.getOtherStone());
                    pr2.setOtherStoneWeight(product.getOtherStoneWeight());
                    pr2.setPrice(product.getPrice());
                    pr2.setProducerId(product.getProducerId());
                    pr2.setAddress(store.getAddress());
                    pr2.setSilverWeight(product.getSilverWeight());
                    pr2.setSku(product.getSku().replaceAll("-W", "-B"));
                    pr2.setQuantity(0);
                    productRepository.save(pr2);
                    saveHistory(pr2, store.getAddress());
                    Product pr3 = new Product();
                    pr3.setColor("Rose");
                    pr3.setCategoryId(product.getCategoryId());
                    pr3.setCostEu(product.getCostEu());
                    pr3.setCostUsd(product.getCostUsd());
                    pr3.setDescription(product.getDescription());
                    pr3.setDiamondWeight(product.getDiamondWeight());
                    pr3.setGoldWeight(product.getGoldWeight());
                    pr3.setImageUrl("");
                    pr3.setKarats(product.getKarats());
                    pr3.setNonProduce(false);
                    pr3.setOtherStone(product.getOtherStone());
                    pr3.setOtherStoneWeight(product.getOtherStoneWeight());
                    pr3.setPrice(product.getPrice());
                    pr3.setProducerId(product.getProducerId());
                    pr3.setAddress(store.getAddress());
                    pr3.setSilverWeight(product.getSilverWeight());
                    pr3.setSku(product.getSku().replaceAll("-W", "-R"));
                    pr3.setQuantity(0);
                    productRepository.save(pr3);
                    saveHistory(pr3, store.getAddress());
                    Product pr4 = new Product();
                    pr4.setColor("White");
                    pr4.setCategoryId(product.getCategoryId());
                    pr4.setCostEu(product.getCostEu());
                    pr4.setCostUsd(product.getCostUsd());
                    pr4.setDescription(product.getDescription());
                    pr4.setDiamondWeight(product.getDiamondWeight());
                    pr4.setGoldWeight(product.getGoldWeight());
                    pr4.setImageUrl("");
                    pr4.setKarats(product.getKarats());
                    pr4.setNonProduce(false);
                    pr4.setOtherStone(product.getOtherStone());
                    pr4.setOtherStoneWeight(product.getOtherStoneWeight());
                    pr4.setPrice(product.getPrice());
                    pr4.setProducerId(product.getProducerId());
                    pr4.setAddress(store.getAddress());
                    pr4.setSilverWeight(product.getSilverWeight());
                    pr4.setSku(product.getSku().replaceAll("-W", "-W"));
                    pr4.setQuantity(0);
                    productRepository.save(pr4);
                    saveHistory(pr4, store.getAddress());
                }
                if (product.getColor().equals("Black")) {
                    Product pr1 = new Product();
                    pr1.setColor("Yellow");
                    pr1.setCategoryId(product.getCategoryId());
                    pr1.setCostEu(product.getCostEu());
                    pr1.setCostUsd(product.getCostUsd());
                    pr1.setDescription(product.getDescription());
                    pr1.setDiamondWeight(product.getDiamondWeight());
                    pr1.setGoldWeight(product.getGoldWeight());
                    pr1.setImageUrl("");
                    pr1.setKarats(product.getKarats());
                    pr1.setNonProduce(false);
                    pr1.setOtherStone(product.getOtherStone());
                    pr1.setOtherStoneWeight(product.getOtherStoneWeight());
                    pr1.setPrice(product.getPrice());
                    pr1.setProducerId(product.getProducerId());
                    pr1.setAddress(store.getAddress());
                    pr1.setSilverWeight(product.getSilverWeight());
                    pr1.setSku(product.getSku().replaceAll("-B", "-Y"));
                    pr1.setQuantity(0);
                    productRepository.save(pr1);
                    saveHistory(pr1, store.getAddress());
                    Product pr2 = new Product();
                    pr2.setColor("White");
                    pr2.setCategoryId(product.getCategoryId());
                    pr2.setCostEu(product.getCostEu());
                    pr2.setCostUsd(product.getCostUsd());
                    pr2.setDescription(product.getDescription());
                    pr2.setDiamondWeight(product.getDiamondWeight());
                    pr2.setGoldWeight(product.getGoldWeight());
                    pr2.setImageUrl("");
                    pr2.setKarats(product.getKarats());
                    pr2.setNonProduce(false);
                    pr2.setOtherStone(product.getOtherStone());
                    pr2.setOtherStoneWeight(product.getOtherStoneWeight());
                    pr2.setPrice(product.getPrice());
                    pr2.setProducerId(product.getProducerId());
                    pr2.setAddress(store.getAddress());
                    pr2.setSilverWeight(product.getSilverWeight());
                    pr2.setSku(product.getSku().replaceAll("-B", "-W"));
                    pr2.setQuantity(0);
                    productRepository.save(pr2);
                    saveHistory(pr2, store.getAddress());
                    Product pr3 = new Product();
                    pr3.setColor("Rose");
                    pr3.setCategoryId(product.getCategoryId());
                    pr3.setCostEu(product.getCostEu());
                    pr3.setCostUsd(product.getCostUsd());
                    pr3.setDescription(product.getDescription());
                    pr3.setDiamondWeight(product.getDiamondWeight());
                    pr3.setGoldWeight(product.getGoldWeight());
                    pr3.setImageUrl("");
                    pr3.setKarats(product.getKarats());
                    pr3.setNonProduce(false);
                    pr3.setOtherStone(product.getOtherStone());
                    pr3.setOtherStoneWeight(product.getOtherStoneWeight());
                    pr3.setPrice(product.getPrice());
                    pr3.setProducerId(product.getProducerId());
                    pr3.setAddress(store.getAddress());
                    pr3.setSilverWeight(product.getSilverWeight());
                    pr3.setSku(product.getSku().replaceAll("-B", "-R"));
                    pr3.setQuantity(0);
                    productRepository.save(pr3);
                    saveHistory(pr3, store.getAddress());
                    Product pr4 = new Product();
                    pr4.setColor("Black");
                    pr4.setCategoryId(product.getCategoryId());
                    pr4.setCostEu(product.getCostEu());
                    pr4.setCostUsd(product.getCostUsd());
                    pr4.setDescription(product.getDescription());
                    pr4.setDiamondWeight(product.getDiamondWeight());
                    pr4.setGoldWeight(product.getGoldWeight());
                    pr4.setImageUrl("");
                    pr4.setKarats(product.getKarats());
                    pr4.setNonProduce(false);
                    pr4.setOtherStone(product.getOtherStone());
                    pr4.setOtherStoneWeight(product.getOtherStoneWeight());
                    pr4.setPrice(product.getPrice());
                    pr4.setProducerId(product.getProducerId());
                    pr4.setAddress(store.getAddress());
                    pr4.setSilverWeight(product.getSilverWeight());
                    pr4.setSku(product.getSku().replaceAll("-B", "-B"));
                    pr4.setQuantity(0);
                    productRepository.save(pr4);
                    saveHistory(pr4, store.getAddress());
                }
                if (product.getColor().equals("Yellow")) {
                    Product pr1 = new Product();
                    pr1.setColor("White");
                    pr1.setCategoryId(product.getCategoryId());
                    pr1.setCostEu(product.getCostEu());
                    pr1.setCostUsd(product.getCostUsd());
                    pr1.setDescription(product.getDescription());
                    pr1.setDiamondWeight(product.getDiamondWeight());
                    pr1.setGoldWeight(product.getGoldWeight());
                    pr1.setImageUrl("");
                    pr1.setKarats(product.getKarats());
                    pr1.setNonProduce(false);
                    pr1.setOtherStone(product.getOtherStone());
                    pr1.setOtherStoneWeight(product.getOtherStoneWeight());
                    pr1.setPrice(product.getPrice());
                    pr1.setProducerId(product.getProducerId());
                    pr1.setAddress(store.getAddress());
                    pr1.setSilverWeight(product.getSilverWeight());
                    pr1.setSku(product.getSku().replaceAll("-Y", "-W"));
                    pr1.setQuantity(0);
                    productRepository.save(pr1);
                    saveHistory(pr1, store.getAddress());
                    Product pr2 = new Product();
                    pr2.setColor("Black");
                    pr2.setCategoryId(product.getCategoryId());
                    pr2.setCostEu(product.getCostEu());
                    pr2.setCostUsd(product.getCostUsd());
                    pr2.setDescription(product.getDescription());
                    pr2.setDiamondWeight(product.getDiamondWeight());
                    pr2.setGoldWeight(product.getGoldWeight());
                    pr2.setImageUrl("");
                    pr2.setKarats(product.getKarats());
                    pr2.setNonProduce(false);
                    pr2.setOtherStone(product.getOtherStone());
                    pr2.setOtherStoneWeight(product.getOtherStoneWeight());
                    pr2.setPrice(product.getPrice());
                    pr2.setProducerId(product.getProducerId());
                    pr2.setAddress(store.getAddress());
                    pr2.setSilverWeight(product.getSilverWeight());
                    pr2.setSku(product.getSku().replaceAll("-Y", "-B"));
                    pr2.setQuantity(0);
                    productRepository.save(pr2);
                    saveHistory(pr2, store.getAddress());
                    Product pr3 = new Product();
                    pr3.setColor("Rose");
                    pr3.setCategoryId(product.getCategoryId());
                    pr3.setCostEu(product.getCostEu());
                    pr3.setCostUsd(product.getCostUsd());
                    pr3.setDescription(product.getDescription());
                    pr3.setDiamondWeight(product.getDiamondWeight());
                    pr3.setGoldWeight(product.getGoldWeight());
                    pr3.setImageUrl("");
                    pr3.setKarats(product.getKarats());
                    pr3.setNonProduce(false);
                    pr3.setOtherStone(product.getOtherStone());
                    pr3.setOtherStoneWeight(product.getOtherStoneWeight());
                    pr3.setPrice(product.getPrice());
                    pr3.setProducerId(product.getProducerId());
                    pr3.setAddress(store.getAddress());
                    pr3.setSilverWeight(product.getSilverWeight());
                    pr3.setSku(product.getSku().replaceAll("-Y", "-R"));
                    pr3.setQuantity(0);
                    productRepository.save(pr3);
                    saveHistory(pr3, store.getAddress());
                    Product pr4 = new Product();
                    pr4.setColor("Yellow");
                    pr4.setCategoryId(product.getCategoryId());
                    pr4.setCostEu(product.getCostEu());
                    pr4.setCostUsd(product.getCostUsd());
                    pr4.setDescription(product.getDescription());
                    pr4.setDiamondWeight(product.getDiamondWeight());
                    pr4.setGoldWeight(product.getGoldWeight());
                    pr4.setImageUrl("");
                    pr4.setKarats(product.getKarats());
                    pr4.setNonProduce(false);
                    pr4.setOtherStone(product.getOtherStone());
                    pr4.setOtherStoneWeight(product.getOtherStoneWeight());
                    pr4.setPrice(product.getPrice());
                    pr4.setProducerId(product.getProducerId());
                    pr4.setAddress(store.getAddress());
                    pr4.setSilverWeight(product.getSilverWeight());
                    pr4.setSku(product.getSku().replaceAll("-Y", "-Y"));
                    pr4.setQuantity(0);
                    productRepository.save(pr4);
                    saveHistory(pr4, store.getAddress());
                }
                if (product.getColor().equals("Rose")) {
                    Product pr1 = new Product();
                    pr1.setColor("Yellow");
                    pr1.setCategoryId(product.getCategoryId());
                    pr1.setCostEu(product.getCostEu());
                    pr1.setCostUsd(product.getCostUsd());
                    pr1.setDescription(product.getDescription());
                    pr1.setDiamondWeight(product.getDiamondWeight());
                    pr1.setGoldWeight(product.getGoldWeight());
                    pr1.setImageUrl("");
                    pr1.setKarats(product.getKarats());
                    pr1.setNonProduce(false);
                    pr1.setOtherStone(product.getOtherStone());
                    pr1.setOtherStoneWeight(product.getOtherStoneWeight());
                    pr1.setPrice(product.getPrice());
                    pr1.setProducerId(product.getProducerId());
                    pr1.setAddress(store.getAddress());
                    pr1.setSilverWeight(product.getSilverWeight());
                    pr1.setSku(product.getSku().replaceAll("-R", "-Y"));
                    pr1.setQuantity(0);
                    productRepository.save(pr1);
                    saveHistory(pr1, store.getAddress());
                    Product pr2 = new Product();
                    pr2.setColor("Black");
                    pr2.setCategoryId(product.getCategoryId());
                    pr2.setCostEu(product.getCostEu());
                    pr2.setCostUsd(product.getCostUsd());
                    pr2.setDescription(product.getDescription());
                    pr2.setDiamondWeight(product.getDiamondWeight());
                    pr2.setGoldWeight(product.getGoldWeight());
                    pr2.setImageUrl("");
                    pr2.setKarats(product.getKarats());
                    pr2.setNonProduce(false);
                    pr2.setOtherStone(product.getOtherStone());
                    pr2.setOtherStoneWeight(product.getOtherStoneWeight());
                    pr2.setPrice(product.getPrice());
                    pr2.setProducerId(product.getProducerId());
                    pr2.setAddress(store.getAddress());
                    pr2.setSilverWeight(product.getSilverWeight());
                    pr2.setSku(product.getSku().replaceAll("-R", "-B"));
                    pr2.setQuantity(0);
                    productRepository.save(pr2);
                    saveHistory(pr2, store.getAddress());
                    Product pr3 = new Product();
                    pr3.setColor("White");
                    pr3.setCategoryId(product.getCategoryId());
                    pr3.setCostEu(product.getCostEu());
                    pr3.setCostUsd(product.getCostUsd());
                    pr3.setDescription(product.getDescription());
                    pr3.setDiamondWeight(product.getDiamondWeight());
                    pr3.setGoldWeight(product.getGoldWeight());
                    pr3.setImageUrl("");
                    pr3.setKarats(product.getKarats());
                    pr3.setNonProduce(false);
                    pr3.setOtherStone(product.getOtherStone());
                    pr3.setOtherStoneWeight(product.getOtherStoneWeight());
                    pr3.setPrice(product.getPrice());
                    pr3.setProducerId(product.getProducerId());
                    pr3.setAddress(store.getAddress());
                    pr3.setSilverWeight(product.getSilverWeight());
                    pr3.setSku(product.getSku().replaceAll("-R", "-W"));
                    pr3.setQuantity(0);
                    productRepository.save(pr3);
                    saveHistory(pr3, store.getAddress());
                    Product pr4 = new Product();
                    pr4.setColor("Rose");
                    pr4.setCategoryId(product.getCategoryId());
                    pr4.setCostEu(product.getCostEu());
                    pr4.setCostUsd(product.getCostUsd());
                    pr4.setDescription(product.getDescription());
                    pr4.setDiamondWeight(product.getDiamondWeight());
                    pr4.setGoldWeight(product.getGoldWeight());
                    pr4.setImageUrl("");
                    pr4.setKarats(product.getKarats());
                    pr4.setNonProduce(false);
                    pr4.setOtherStone(product.getOtherStone());
                    pr4.setOtherStoneWeight(product.getOtherStoneWeight());
                    pr4.setPrice(product.getPrice());
                    pr4.setProducerId(product.getProducerId());
                    pr4.setAddress(store.getAddress());
                    pr4.setSilverWeight(product.getSilverWeight());
                    pr4.setSku(product.getSku().replaceAll("-R", "-R"));
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
        product.setNonProduce(true);
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

    @Cacheable(value = "productsWithMaxSize")
    @CacheEvict("productsWithMaxSize")
    public List<List<?>> getProductsPerFilterCase(String page, String categoryId, String producerId, String storeId, String limit) throws InterruptedException {
        List<Product> products = pagination.getProductsPaginated(page, categoryId, storeId, producerId, limit);
        List<Map<String, Integer>> listMaxSize = new ArrayList();
        listMaxSize.add(pagination.getMaxSize(storeId, producerId, categoryId));
        List<List<?>> productsWithMaxSize = new ArrayList();
        productsWithMaxSize.add(products);
        productsWithMaxSize.add(listMaxSize);
//        cachingService.evictAllcachesAtIntervals();
        return productsWithMaxSize;
    }

    public void deleteProduct(String sku) {
        sku = sku.substring(0, (sku.length() - 2));
        Query query = new Query();
        query.addCriteria(Criteria.where("sku").regex(sku));
        for (Product product : mongoTemplate.find(query, Product.class)) {
            productRepository.delete(product);
        }
    }

    public void updateProduct(Product product) {
        Product pr = productRepository.findById(product.getId()).get();
        if (!pr.getSku().equals(product.getSku()) || !pr.getDescription().equals(product.getDescription())
                || !pr.getCostEu().equals(product.getCostEu()) || !pr.getCostUsd().equals(product.getCostUsd())
                || !pr.getPrice().equals(product.getPrice()) || !pr.getCategoryId().equals(product.getCategoryId())
                || !pr.getProducerId().equals(product.getProducerId()) || !pr.getKarats().equals(product.getKarats())) {
            productRepository.save(product);
            for (Product prod : productRepository.findBysku(pr.getSku())) {
                if (!prod.getId().equals(pr.getId())) {
                    prod.setSku(product.getSku());
                    prod.setCategoryId(product.getCategoryId());
                    prod.setProducerId(product.getProducerId());
                    prod.setCostEu(product.getCostEu());
                    prod.setCostUsd(product.getCostUsd());
                    prod.setPrice(product.getPrice());
                    prod.setKarats(product.getKarats());
                    productRepository.save(prod);
                }
            }
        }
        for (Product prod : productRepository.findByskuAndColor(pr.getSku(), pr.getColor())) {
            prod.setOtherStone(product.getOtherStone());
            prod.setDiamondWeight(product.getDiamondWeight());
            prod.setGoldWeight(product.getGoldWeight());
            prod.setSilverWeight(product.getSilverWeight());
            prod.setOtherStoneWeight(product.getOtherStoneWeight());
            productRepository.save(prod);
        }

    }

    public void throwE() {
        List<String> kk = new ArrayList();
        kk.get(5);
    }

//    public void testCreate(Product product) {
//        Category cat = categoryRepository.findById(product.getCategoryId()).get();
//        List<Category> cats = product.getCategory();
//        cats.add(cat);
//        product.setCategory(cats);
//        productRepository.save(product);
//    }
}
