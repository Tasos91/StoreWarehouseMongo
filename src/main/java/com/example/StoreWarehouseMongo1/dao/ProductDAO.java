package com.example.StoreWarehouseMongo1.dao;

import com.example.StoreWarehouseMongo1.Exceptions.ProductFoundException;
import com.example.StoreWarehouseMongo1.helpers.Pagination;
import com.example.StoreWarehouseMongo1.model.History;
import com.example.StoreWarehouseMongo1.model.Product;
import com.example.StoreWarehouseMongo1.model.Store;
import com.example.StoreWarehouseMongo1.repositories.HistoryRepository;
import com.example.StoreWarehouseMongo1.repositories.ProductRepository;
import com.example.StoreWarehouseMongo1.repositories.StoreRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

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
    private Pagination pagination;

    public ResponseEntity<?> get(String productId) {
        Product product = new Product();
        List<List<?>> response = new ArrayList();
        List<Product> prod = new ArrayList();
        product = productRepository.findById(productId).get();
        String color = product.getColor();
        String sku = product.getsku();
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
            pr1 = productRepository.findBysku(product.getsku() + "-Y").get(0);
            pr2 = productRepository.findBysku(product.getsku() + "-W").get(0);
            pr3 = productRepository.findBysku(product.getsku() + "-B").get(0);
            pr4 = productRepository.findBysku(product.getsku() + "-R").get(0);
        } catch (Exception e) {
            if (pr1.getsku() == null) {
                pr1 = new Product();
                pr1.setsku("");
            }
            if (pr2.getsku() == null) {
                pr2 = new Product();
                pr2.setsku("");
            }
            if (pr3.getsku() == null) {
                pr3 = new Product();
                pr3.setsku("");
            }
            if (pr4.getsku() == null) {
                pr4 = new Product();
                pr4.setsku("");
            }
        }
        if ((!pr1.getsku().contains(product.getsku()) && pr2.getsku().contains(product.getsku())
                && pr3.getsku().contains(product.getsku()) && pr4.getsku().contains(product.getsku()))
                || (pr1.getsku().equals("") || pr2.getsku().equals("")) || pr3.getsku().equals("")
                || pr4.getsku().equals("")) {
            if (product.getColor().equals("White")) {
                product.setsku(product.getsku() + "-W");
            }
            if (product.getColor().equals("Yellow")) {
                product.setsku(product.getsku() + "-Y");
            }
            if (product.getColor().equals("Black")) {
                product.setsku(product.getsku() + "-B");
            }
            if (product.getColor().equals("Rose")) {
                product.setsku(product.getsku() + "-R");
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
            pr1.setcostEu(product.getcostEu());
            pr1.setcostUsd(product.getcostUsd());
            pr1.setdescription(product.getdescription());
            pr1.setDiamondWeight(product.getDiamondWeight());
            pr1.setGoldWeight(product.getGoldWeight());
            pr1.setImageUrl("");
            pr1.setKarats(product.getKarats());
            pr1.setnonProduce(false);
            pr1.setOtherStone(product.getOtherStone());
            pr1.setOtherStoneWeight(product.getOtherStoneWeight());
            pr1.setPrice(product.getPrice());
            pr1.setProducerId(product.getProducerId());
            pr1.setAddress(product.getAddress());
            pr1.setSilverWeight(product.getSilverWeight());
            pr1.setsku(product.getsku().replaceAll("-W", "-Y"));
            pr1.setQuantity(0);
            productRepository.save(pr1);
            saveHistory(pr1, product.getAddress());
            Product pr2 = new Product();
            pr2.setColor("Black");
            pr2.setCategoryId(product.getCategoryId());
            pr2.setcostEu(product.getcostEu());
            pr2.setcostUsd(product.getcostUsd());
            pr2.setdescription(product.getdescription());
            pr2.setDiamondWeight(product.getDiamondWeight());
            pr2.setGoldWeight(product.getGoldWeight());
            pr2.setImageUrl("");
            pr2.setKarats(product.getKarats());
            pr2.setnonProduce(false);
            pr2.setOtherStone(product.getOtherStone());
            pr2.setOtherStoneWeight(product.getOtherStoneWeight());
            pr2.setPrice(product.getPrice());
            pr2.setProducerId(product.getProducerId());
            pr2.setAddress(product.getAddress());
            pr2.setSilverWeight(product.getSilverWeight());
            pr2.setsku(product.getsku().replaceAll("-W", "-B"));
            pr2.setQuantity(0);
            productRepository.save(pr2);
            saveHistory(pr2, product.getAddress());
            Product pr3 = new Product();
            pr3.setColor("Rose");
            pr3.setCategoryId(product.getCategoryId());
            pr3.setcostEu(product.getcostEu());
            pr3.setcostUsd(product.getcostUsd());
            pr3.setdescription(product.getdescription());
            pr3.setDiamondWeight(product.getDiamondWeight());
            pr3.setGoldWeight(product.getGoldWeight());
            pr3.setImageUrl("");
            pr3.setKarats(product.getKarats());
            pr3.setnonProduce(false);
            pr3.setOtherStone(product.getOtherStone());
            pr3.setOtherStoneWeight(product.getOtherStoneWeight());
            pr3.setPrice(product.getPrice());
            pr3.setProducerId(product.getProducerId());
            pr3.setAddress(product.getAddress());
            pr3.setSilverWeight(product.getSilverWeight());
            pr3.setsku(product.getsku().replaceAll("-W", "-R"));
            pr3.setQuantity(0);
            productRepository.save(pr3);
            saveHistory(pr3, product.getAddress());
        }
        if (product.getColor().equals("Black")) {
            Product pr1 = new Product();
            pr1.setColor("Yellow");
            pr1.setCategoryId(product.getCategoryId());
            pr1.setcostEu(product.getcostEu());
            pr1.setcostUsd(product.getcostUsd());
            pr1.setdescription(product.getdescription());
            pr1.setDiamondWeight(product.getDiamondWeight());
            pr1.setGoldWeight(product.getGoldWeight());
            pr1.setImageUrl("");
            pr1.setKarats(product.getKarats());
            pr1.setnonProduce(false);
            pr1.setOtherStone(product.getOtherStone());
            pr1.setOtherStoneWeight(product.getOtherStoneWeight());
            pr1.setPrice(product.getPrice());
            pr1.setProducerId(product.getProducerId());
            pr1.setAddress(product.getAddress());
            pr1.setSilverWeight(product.getSilverWeight());
            pr1.setsku(product.getsku().replaceAll("-B", "-Y"));
            pr1.setQuantity(0);
            productRepository.save(pr1);
            saveHistory(pr1, product.getAddress());
            Product pr2 = new Product();
            pr2.setColor("White");
            pr2.setCategoryId(product.getCategoryId());
            pr2.setcostEu(product.getcostEu());
            pr2.setcostUsd(product.getcostUsd());
            pr2.setdescription(product.getdescription());
            pr2.setDiamondWeight(product.getDiamondWeight());
            pr2.setGoldWeight(product.getGoldWeight());
            pr2.setImageUrl("");
            pr2.setKarats(product.getKarats());
            pr2.setnonProduce(false);
            pr2.setOtherStone(product.getOtherStone());
            pr2.setOtherStoneWeight(product.getOtherStoneWeight());
            pr2.setPrice(product.getPrice());
            pr2.setProducerId(product.getProducerId());
            pr2.setAddress(product.getAddress());
            pr2.setSilverWeight(product.getSilverWeight());
            pr2.setsku(product.getsku().replaceAll("-B", "-W"));
            pr2.setQuantity(0);
            productRepository.save(pr2);
            saveHistory(pr2, product.getAddress());
            Product pr3 = new Product();
            pr3.setColor("Rose");
            pr3.setCategoryId(product.getCategoryId());
            pr3.setcostEu(product.getcostEu());
            pr3.setcostUsd(product.getcostUsd());
            pr3.setdescription(product.getdescription());
            pr3.setDiamondWeight(product.getDiamondWeight());
            pr3.setGoldWeight(product.getGoldWeight());
            pr3.setImageUrl("");
            pr3.setKarats(product.getKarats());
            pr3.setnonProduce(false);
            pr3.setOtherStone(product.getOtherStone());
            pr3.setOtherStoneWeight(product.getOtherStoneWeight());
            pr3.setPrice(product.getPrice());
            pr3.setProducerId(product.getProducerId());
            pr3.setAddress(product.getAddress());
            pr3.setSilverWeight(product.getSilverWeight());
            pr3.setsku(product.getsku().replaceAll("-B", "-R"));
            pr3.setQuantity(0);
            productRepository.save(pr3);
            saveHistory(pr3, product.getAddress());
        }
        if (product.getColor().equals("Yellow")) {
            Product pr1 = new Product();
            pr1.setColor("White");
            pr1.setCategoryId(product.getCategoryId());
            pr1.setcostEu(product.getcostEu());
            pr1.setcostUsd(product.getcostUsd());
            pr1.setdescription(product.getdescription());
            pr1.setDiamondWeight(product.getDiamondWeight());
            pr1.setGoldWeight(product.getGoldWeight());
            pr1.setImageUrl("");
            pr1.setKarats(product.getKarats());
            pr1.setnonProduce(false);
            pr1.setOtherStone(product.getOtherStone());
            pr1.setOtherStoneWeight(product.getOtherStoneWeight());
            pr1.setPrice(product.getPrice());
            pr1.setProducerId(product.getProducerId());
            pr1.setAddress(product.getAddress());
            pr1.setSilverWeight(product.getSilverWeight());
            pr1.setsku(product.getsku().replaceAll("-Y", "-W"));
            pr1.setQuantity(0);
            productRepository.save(pr1);
            saveHistory(pr1, product.getAddress());
            Product pr2 = new Product();
            pr2.setColor("Black");
            pr2.setCategoryId(product.getCategoryId());
            pr2.setcostEu(product.getcostEu());
            pr2.setcostUsd(product.getcostUsd());
            pr2.setdescription(product.getdescription());
            pr2.setDiamondWeight(product.getDiamondWeight());
            pr2.setGoldWeight(product.getGoldWeight());
            pr2.setImageUrl("");
            pr2.setKarats(product.getKarats());
            pr2.setnonProduce(false);
            pr2.setOtherStone(product.getOtherStone());
            pr2.setOtherStoneWeight(product.getOtherStoneWeight());
            pr2.setPrice(product.getPrice());
            pr2.setProducerId(product.getProducerId());
            pr2.setAddress(product.getAddress());
            pr2.setSilverWeight(product.getSilverWeight());
            pr2.setsku(product.getsku().replaceAll("-Y", "-B"));
            pr2.setQuantity(0);
            productRepository.save(pr2);
            saveHistory(pr2, product.getAddress());
            Product pr3 = new Product();
            pr3.setColor("Rose");
            pr3.setCategoryId(product.getCategoryId());
            pr3.setcostEu(product.getcostEu());
            pr3.setcostUsd(product.getcostUsd());
            pr3.setdescription(product.getdescription());
            pr3.setDiamondWeight(product.getDiamondWeight());
            pr3.setGoldWeight(product.getGoldWeight());
            pr3.setImageUrl("");
            pr3.setKarats(product.getKarats());
            pr3.setnonProduce(false);
            pr3.setOtherStone(product.getOtherStone());
            pr3.setOtherStoneWeight(product.getOtherStoneWeight());
            pr3.setPrice(product.getPrice());
            pr3.setProducerId(product.getProducerId());
            pr3.setAddress(product.getAddress());
            pr3.setSilverWeight(product.getSilverWeight());
            pr3.setsku(product.getsku().replaceAll("-Y", "-R"));
            pr3.setQuantity(0);
            productRepository.save(pr3);
            saveHistory(pr3, product.getAddress());
        }
        if (product.getColor().equals("Rose")) {
            Product pr1 = new Product();
            pr1.setColor("Yellow");
            pr1.setCategoryId(product.getCategoryId());
            pr1.setcostEu(product.getcostEu());
            pr1.setcostUsd(product.getcostUsd());
            pr1.setdescription(product.getdescription());
            pr1.setDiamondWeight(product.getDiamondWeight());
            pr1.setGoldWeight(product.getGoldWeight());
            pr1.setImageUrl("");
            pr1.setKarats(product.getKarats());
            pr1.setnonProduce(false);
            pr1.setOtherStone(product.getOtherStone());
            pr1.setOtherStoneWeight(product.getOtherStoneWeight());
            pr1.setPrice(product.getPrice());
            pr1.setProducerId(product.getProducerId());
            pr1.setAddress(product.getAddress());
            pr1.setSilverWeight(product.getSilverWeight());
            pr1.setsku(product.getsku().replaceAll("-R", "-Y"));
            pr1.setQuantity(0);
            productRepository.save(pr1);
            saveHistory(pr1, product.getAddress());
            Product pr2 = new Product();
            pr2.setColor("Black");
            pr2.setCategoryId(product.getCategoryId());
            pr2.setcostEu(product.getcostEu());
            pr2.setcostUsd(product.getcostUsd());
            pr2.setdescription(product.getdescription());
            pr2.setDiamondWeight(product.getDiamondWeight());
            pr2.setGoldWeight(product.getGoldWeight());
            pr2.setImageUrl("");
            pr2.setKarats(product.getKarats());
            pr2.setnonProduce(false);
            pr2.setOtherStone(product.getOtherStone());
            pr2.setOtherStoneWeight(product.getOtherStoneWeight());
            pr2.setPrice(product.getPrice());
            pr2.setProducerId(product.getProducerId());
            pr2.setAddress(product.getAddress());
            pr2.setSilverWeight(product.getSilverWeight());
            pr2.setsku(product.getsku().replaceAll("-R", "-B"));
            pr2.setQuantity(0);
            productRepository.save(pr2);
            saveHistory(pr2, product.getAddress());
            Product pr3 = new Product();
            pr3.setColor("White");
            pr3.setCategoryId(product.getCategoryId());
            pr3.setcostEu(product.getcostEu());
            pr3.setcostUsd(product.getcostUsd());
            pr3.setdescription(product.getdescription());
            pr3.setDiamondWeight(product.getDiamondWeight());
            pr3.setGoldWeight(product.getGoldWeight());
            pr3.setImageUrl("");
            pr3.setKarats(product.getKarats());
            pr3.setnonProduce(false);
            pr3.setOtherStone(product.getOtherStone());
            pr3.setOtherStoneWeight(product.getOtherStoneWeight());
            pr3.setPrice(product.getPrice());
            pr3.setProducerId(product.getProducerId());
            pr3.setAddress(product.getAddress());
            pr3.setSilverWeight(product.getSilverWeight());
            pr3.setsku(product.getsku().replaceAll("-R", "-W"));
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
                    pr1.setcostEu(product.getcostEu());
                    pr1.setcostUsd(product.getcostUsd());
                    pr1.setdescription(product.getdescription());
                    pr1.setDiamondWeight(product.getDiamondWeight());
                    pr1.setGoldWeight(product.getGoldWeight());
                    pr1.setImageUrl("");
                    pr1.setKarats(product.getKarats());
                    pr1.setnonProduce(false);
                    pr1.setOtherStone(product.getOtherStone());
                    pr1.setOtherStoneWeight(product.getOtherStoneWeight());
                    pr1.setPrice(product.getPrice());
                    pr1.setProducerId(product.getProducerId());
                    pr1.setAddress(store.getAddress());
                    pr1.setSilverWeight(product.getSilverWeight());
                    pr1.setsku(product.getsku().replaceAll("-W", "-Y"));
                    pr1.setQuantity(0);
                    productRepository.save(pr1);
                    saveHistory(pr1, store.getAddress());
                    Product pr2 = new Product();
                    pr2.setColor("Black");
                    pr2.setCategoryId(product.getCategoryId());
                    pr2.setcostEu(product.getcostEu());
                    pr2.setcostUsd(product.getcostUsd());
                    pr2.setdescription(product.getdescription());
                    pr2.setDiamondWeight(product.getDiamondWeight());
                    pr2.setGoldWeight(product.getGoldWeight());
                    pr2.setImageUrl("");
                    pr2.setKarats(product.getKarats());
                    pr2.setnonProduce(false);
                    pr2.setOtherStone(product.getOtherStone());
                    pr2.setOtherStoneWeight(product.getOtherStoneWeight());
                    pr2.setPrice(product.getPrice());
                    pr2.setProducerId(product.getProducerId());
                    pr2.setAddress(store.getAddress());
                    pr2.setSilverWeight(product.getSilverWeight());
                    pr2.setsku(product.getsku().replaceAll("-W", "-B"));
                    pr2.setQuantity(0);
                    productRepository.save(pr2);
                    saveHistory(pr2, store.getAddress());
                    Product pr3 = new Product();
                    pr3.setColor("Rose");
                    pr3.setCategoryId(product.getCategoryId());
                    pr3.setcostEu(product.getcostEu());
                    pr3.setcostUsd(product.getcostUsd());
                    pr3.setdescription(product.getdescription());
                    pr3.setDiamondWeight(product.getDiamondWeight());
                    pr3.setGoldWeight(product.getGoldWeight());
                    pr3.setImageUrl("");
                    pr3.setKarats(product.getKarats());
                    pr3.setnonProduce(false);
                    pr3.setOtherStone(product.getOtherStone());
                    pr3.setOtherStoneWeight(product.getOtherStoneWeight());
                    pr3.setPrice(product.getPrice());
                    pr3.setProducerId(product.getProducerId());
                    pr3.setAddress(store.getAddress());
                    pr3.setSilverWeight(product.getSilverWeight());
                    pr3.setsku(product.getsku().replaceAll("-W", "-R"));
                    pr3.setQuantity(0);
                    productRepository.save(pr3);
                    saveHistory(pr3, store.getAddress());
                    Product pr4 = new Product();
                    pr4.setColor("White");
                    pr4.setCategoryId(product.getCategoryId());
                    pr4.setcostEu(product.getcostEu());
                    pr4.setcostUsd(product.getcostUsd());
                    pr4.setdescription(product.getdescription());
                    pr4.setDiamondWeight(product.getDiamondWeight());
                    pr4.setGoldWeight(product.getGoldWeight());
                    pr4.setImageUrl("");
                    pr4.setKarats(product.getKarats());
                    pr4.setnonProduce(false);
                    pr4.setOtherStone(product.getOtherStone());
                    pr4.setOtherStoneWeight(product.getOtherStoneWeight());
                    pr4.setPrice(product.getPrice());
                    pr4.setProducerId(product.getProducerId());
                    pr4.setAddress(store.getAddress());
                    pr4.setSilverWeight(product.getSilverWeight());
                    pr4.setsku(product.getsku().replaceAll("-W", "-W"));
                    pr4.setQuantity(0);
                    productRepository.save(pr4);
                    saveHistory(pr4, store.getAddress());
                }
                if (product.getColor().equals("Black")) {
                    Product pr1 = new Product();
                    pr1.setColor("Yellow");
                    pr1.setCategoryId(product.getCategoryId());
                    pr1.setcostEu(product.getcostEu());
                    pr1.setcostUsd(product.getcostUsd());
                    pr1.setdescription(product.getdescription());
                    pr1.setDiamondWeight(product.getDiamondWeight());
                    pr1.setGoldWeight(product.getGoldWeight());
                    pr1.setImageUrl("");
                    pr1.setKarats(product.getKarats());
                    pr1.setnonProduce(false);
                    pr1.setOtherStone(product.getOtherStone());
                    pr1.setOtherStoneWeight(product.getOtherStoneWeight());
                    pr1.setPrice(product.getPrice());
                    pr1.setProducerId(product.getProducerId());
                    pr1.setAddress(store.getAddress());
                    pr1.setSilverWeight(product.getSilverWeight());
                    pr1.setsku(product.getsku().replaceAll("-B", "-Y"));
                    pr1.setQuantity(0);
                    productRepository.save(pr1);
                    saveHistory(pr1, store.getAddress());
                    Product pr2 = new Product();
                    pr2.setColor("White");
                    pr2.setCategoryId(product.getCategoryId());
                    pr2.setcostEu(product.getcostEu());
                    pr2.setcostUsd(product.getcostUsd());
                    pr2.setdescription(product.getdescription());
                    pr2.setDiamondWeight(product.getDiamondWeight());
                    pr2.setGoldWeight(product.getGoldWeight());
                    pr2.setImageUrl("");
                    pr2.setKarats(product.getKarats());
                    pr2.setnonProduce(false);
                    pr2.setOtherStone(product.getOtherStone());
                    pr2.setOtherStoneWeight(product.getOtherStoneWeight());
                    pr2.setPrice(product.getPrice());
                    pr2.setProducerId(product.getProducerId());
                    pr2.setAddress(store.getAddress());
                    pr2.setSilverWeight(product.getSilverWeight());
                    pr2.setsku(product.getsku().replaceAll("-B", "-W"));
                    pr2.setQuantity(0);
                    productRepository.save(pr2);
                    saveHistory(pr2, store.getAddress());
                    Product pr3 = new Product();
                    pr3.setColor("Rose");
                    pr3.setCategoryId(product.getCategoryId());
                    pr3.setcostEu(product.getcostEu());
                    pr3.setcostUsd(product.getcostUsd());
                    pr3.setdescription(product.getdescription());
                    pr3.setDiamondWeight(product.getDiamondWeight());
                    pr3.setGoldWeight(product.getGoldWeight());
                    pr3.setImageUrl("");
                    pr3.setKarats(product.getKarats());
                    pr3.setnonProduce(false);
                    pr3.setOtherStone(product.getOtherStone());
                    pr3.setOtherStoneWeight(product.getOtherStoneWeight());
                    pr3.setPrice(product.getPrice());
                    pr3.setProducerId(product.getProducerId());
                    pr3.setAddress(store.getAddress());
                    pr3.setSilverWeight(product.getSilverWeight());
                    pr3.setsku(product.getsku().replaceAll("-B", "-R"));
                    pr3.setQuantity(0);
                    productRepository.save(pr3);
                    saveHistory(pr3, store.getAddress());
                    Product pr4 = new Product();
                    pr4.setColor("Black");
                    pr4.setCategoryId(product.getCategoryId());
                    pr4.setcostEu(product.getcostEu());
                    pr4.setcostUsd(product.getcostUsd());
                    pr4.setdescription(product.getdescription());
                    pr4.setDiamondWeight(product.getDiamondWeight());
                    pr4.setGoldWeight(product.getGoldWeight());
                    pr4.setImageUrl("");
                    pr4.setKarats(product.getKarats());
                    pr4.setnonProduce(false);
                    pr4.setOtherStone(product.getOtherStone());
                    pr4.setOtherStoneWeight(product.getOtherStoneWeight());
                    pr4.setPrice(product.getPrice());
                    pr4.setProducerId(product.getProducerId());
                    pr4.setAddress(store.getAddress());
                    pr4.setSilverWeight(product.getSilverWeight());
                    pr4.setsku(product.getsku().replaceAll("-B", "-B"));
                    pr4.setQuantity(0);
                    productRepository.save(pr4);
                    saveHistory(pr4, store.getAddress());
                }
                if (product.getColor().equals("Yellow")) {
                    Product pr1 = new Product();
                    pr1.setColor("White");
                    pr1.setCategoryId(product.getCategoryId());
                    pr1.setcostEu(product.getcostEu());
                    pr1.setcostUsd(product.getcostUsd());
                    pr1.setdescription(product.getdescription());
                    pr1.setDiamondWeight(product.getDiamondWeight());
                    pr1.setGoldWeight(product.getGoldWeight());
                    pr1.setImageUrl("");
                    pr1.setKarats(product.getKarats());
                    pr1.setnonProduce(false);
                    pr1.setOtherStone(product.getOtherStone());
                    pr1.setOtherStoneWeight(product.getOtherStoneWeight());
                    pr1.setPrice(product.getPrice());
                    pr1.setProducerId(product.getProducerId());
                    pr1.setAddress(store.getAddress());
                    pr1.setSilverWeight(product.getSilverWeight());
                    pr1.setsku(product.getsku().replaceAll("-Y", "-W"));
                    pr1.setQuantity(0);
                    productRepository.save(pr1);
                    saveHistory(pr1, store.getAddress());
                    Product pr2 = new Product();
                    pr2.setColor("Black");
                    pr2.setCategoryId(product.getCategoryId());
                    pr2.setcostEu(product.getcostEu());
                    pr2.setcostUsd(product.getcostUsd());
                    pr2.setdescription(product.getdescription());
                    pr2.setDiamondWeight(product.getDiamondWeight());
                    pr2.setGoldWeight(product.getGoldWeight());
                    pr2.setImageUrl("");
                    pr2.setKarats(product.getKarats());
                    pr2.setnonProduce(false);
                    pr2.setOtherStone(product.getOtherStone());
                    pr2.setOtherStoneWeight(product.getOtherStoneWeight());
                    pr2.setPrice(product.getPrice());
                    pr2.setProducerId(product.getProducerId());
                    pr2.setAddress(store.getAddress());
                    pr2.setSilverWeight(product.getSilverWeight());
                    pr2.setsku(product.getsku().replaceAll("-Y", "-B"));
                    pr2.setQuantity(0);
                    productRepository.save(pr2);
                    saveHistory(pr2, store.getAddress());
                    Product pr3 = new Product();
                    pr3.setColor("Rose");
                    pr3.setCategoryId(product.getCategoryId());
                    pr3.setcostEu(product.getcostEu());
                    pr3.setcostUsd(product.getcostUsd());
                    pr3.setdescription(product.getdescription());
                    pr3.setDiamondWeight(product.getDiamondWeight());
                    pr3.setGoldWeight(product.getGoldWeight());
                    pr3.setImageUrl("");
                    pr3.setKarats(product.getKarats());
                    pr3.setnonProduce(false);
                    pr3.setOtherStone(product.getOtherStone());
                    pr3.setOtherStoneWeight(product.getOtherStoneWeight());
                    pr3.setPrice(product.getPrice());
                    pr3.setProducerId(product.getProducerId());
                    pr3.setAddress(store.getAddress());
                    pr3.setSilverWeight(product.getSilverWeight());
                    pr3.setsku(product.getsku().replaceAll("-Y", "-R"));
                    pr3.setQuantity(0);
                    productRepository.save(pr3);
                    saveHistory(pr3, store.getAddress());
                    Product pr4 = new Product();
                    pr4.setColor("Yellow");
                    pr4.setCategoryId(product.getCategoryId());
                    pr4.setcostEu(product.getcostEu());
                    pr4.setcostUsd(product.getcostUsd());
                    pr4.setdescription(product.getdescription());
                    pr4.setDiamondWeight(product.getDiamondWeight());
                    pr4.setGoldWeight(product.getGoldWeight());
                    pr4.setImageUrl("");
                    pr4.setKarats(product.getKarats());
                    pr4.setnonProduce(false);
                    pr4.setOtherStone(product.getOtherStone());
                    pr4.setOtherStoneWeight(product.getOtherStoneWeight());
                    pr4.setPrice(product.getPrice());
                    pr4.setProducerId(product.getProducerId());
                    pr4.setAddress(store.getAddress());
                    pr4.setSilverWeight(product.getSilverWeight());
                    pr4.setsku(product.getsku().replaceAll("-Y", "-Y"));
                    pr4.setQuantity(0);
                    productRepository.save(pr4);
                    saveHistory(pr4, store.getAddress());
                }
                if (product.getColor().equals("Rose")) {
                    Product pr1 = new Product();
                    pr1.setColor("Yellow");
                    pr1.setCategoryId(product.getCategoryId());
                    pr1.setcostEu(product.getcostEu());
                    pr1.setcostUsd(product.getcostUsd());
                    pr1.setdescription(product.getdescription());
                    pr1.setDiamondWeight(product.getDiamondWeight());
                    pr1.setGoldWeight(product.getGoldWeight());
                    pr1.setImageUrl("");
                    pr1.setKarats(product.getKarats());
                    pr1.setnonProduce(false);
                    pr1.setOtherStone(product.getOtherStone());
                    pr1.setOtherStoneWeight(product.getOtherStoneWeight());
                    pr1.setPrice(product.getPrice());
                    pr1.setProducerId(product.getProducerId());
                    pr1.setAddress(store.getAddress());
                    pr1.setSilverWeight(product.getSilverWeight());
                    pr1.setsku(product.getsku().replaceAll("-R", "-Y"));
                    pr1.setQuantity(0);
                    productRepository.save(pr1);
                    saveHistory(pr1, store.getAddress());
                    Product pr2 = new Product();
                    pr2.setColor("Black");
                    pr2.setCategoryId(product.getCategoryId());
                    pr2.setcostEu(product.getcostEu());
                    pr2.setcostUsd(product.getcostUsd());
                    pr2.setdescription(product.getdescription());
                    pr2.setDiamondWeight(product.getDiamondWeight());
                    pr2.setGoldWeight(product.getGoldWeight());
                    pr2.setImageUrl("");
                    pr2.setKarats(product.getKarats());
                    pr2.setnonProduce(false);
                    pr2.setOtherStone(product.getOtherStone());
                    pr2.setOtherStoneWeight(product.getOtherStoneWeight());
                    pr2.setPrice(product.getPrice());
                    pr2.setProducerId(product.getProducerId());
                    pr2.setAddress(store.getAddress());
                    pr2.setSilverWeight(product.getSilverWeight());
                    pr2.setsku(product.getsku().replaceAll("-R", "-B"));
                    pr2.setQuantity(0);
                    productRepository.save(pr2);
                    saveHistory(pr2, store.getAddress());
                    Product pr3 = new Product();
                    pr3.setColor("White");
                    pr3.setCategoryId(product.getCategoryId());
                    pr3.setcostEu(product.getcostEu());
                    pr3.setcostUsd(product.getcostUsd());
                    pr3.setdescription(product.getdescription());
                    pr3.setDiamondWeight(product.getDiamondWeight());
                    pr3.setGoldWeight(product.getGoldWeight());
                    pr3.setImageUrl("");
                    pr3.setKarats(product.getKarats());
                    pr3.setnonProduce(false);
                    pr3.setOtherStone(product.getOtherStone());
                    pr3.setOtherStoneWeight(product.getOtherStoneWeight());
                    pr3.setPrice(product.getPrice());
                    pr3.setProducerId(product.getProducerId());
                    pr3.setAddress(store.getAddress());
                    pr3.setSilverWeight(product.getSilverWeight());
                    pr3.setsku(product.getsku().replaceAll("-R", "-W"));
                    pr3.setQuantity(0);
                    productRepository.save(pr3);
                    saveHistory(pr3, store.getAddress());
                    Product pr4 = new Product();
                    pr4.setColor("Rose");
                    pr4.setCategoryId(product.getCategoryId());
                    pr4.setcostEu(product.getcostEu());
                    pr4.setcostUsd(product.getcostUsd());
                    pr4.setdescription(product.getdescription());
                    pr4.setDiamondWeight(product.getDiamondWeight());
                    pr4.setGoldWeight(product.getGoldWeight());
                    pr4.setImageUrl("");
                    pr4.setKarats(product.getKarats());
                    pr4.setnonProduce(false);
                    pr4.setOtherStone(product.getOtherStone());
                    pr4.setOtherStoneWeight(product.getOtherStoneWeight());
                    pr4.setPrice(product.getPrice());
                    pr4.setProducerId(product.getProducerId());
                    pr4.setAddress(store.getAddress());
                    pr4.setSilverWeight(product.getSilverWeight());
                    pr4.setsku(product.getsku().replaceAll("-R", "-R"));
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
        product.setnonProduce(true);
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

    public List<List<?>> getProductsPerFilterCase(String page, String categoryId, String producerId, String storeId, String limit) {
        List<Product> products = pagination.getProductsPaginated(page, categoryId, storeId, producerId, limit);
        List<Map<String, Integer>> listMaxSize = new ArrayList();
        listMaxSize.add(pagination.getMaxSize(storeId, producerId, categoryId));
        List<List<?>> productsWithMaxSize = new ArrayList();
        productsWithMaxSize.add(products);
        productsWithMaxSize.add(listMaxSize);
        return productsWithMaxSize;
    }

    public void deleteProduct(String sku) {
        for (Product product : productRepository.findBysku(sku)) {
            productRepository.delete(product);
        }
    }

    public void updateProduct(Product product) {
        Product pr = productRepository.findById(product.getId()).get();
        if (!pr.getsku().equals(product.getsku()) || !pr.getdescription().equals(product.getdescription())
                || !pr.getcostEu().equals(product.getcostEu()) || !pr.getcostUsd().equals(product.getcostUsd())
                || !pr.getPrice().equals(product.getPrice()) || !pr.getCategoryId().equals(product.getCategoryId())
                || !pr.getProducerId().equals(product.getProducerId()) || !pr.getKarats().equals(product.getKarats())) {
            productRepository.save(product);
            for (Product prod : productRepository.findBysku(pr.getsku())) {
                if (!prod.getId().equals(pr.getId())) {
                    prod.setsku(product.getsku());
                    prod.setCategoryId(product.getCategoryId());
                    prod.setProducerId(product.getProducerId());
                    prod.setcostEu(product.getcostEu());
                    prod.setcostUsd(product.getcostUsd());
                    prod.setPrice(product.getPrice());
                    prod.setKarats(product.getKarats());
                    productRepository.save(prod);
                }
            }
        }
        for (Product prod : productRepository.findByskuAndColor(pr.getsku(), pr.getColor())) {
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

}
