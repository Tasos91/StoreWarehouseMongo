package com.example.StoreWarehouseMongo1.controllers;

import com.example.StoreWarehouseMongo1.model.Category;
import com.example.StoreWarehouseMongo1.model.History;
import com.example.StoreWarehouseMongo1.model.Producer;
import com.example.StoreWarehouseMongo1.model.PseudoProduct;
import com.example.StoreWarehouseMongo1.model.Product;
import com.example.StoreWarehouseMongo1.model.Stock;
import com.example.StoreWarehouseMongo1.model.Store;
import com.example.StoreWarehouseMongo1.repositories.CategoryRepository;
import com.example.StoreWarehouseMongo1.repositories.HistoryRepository;
import com.example.StoreWarehouseMongo1.repositories.ProducerRepository;
import com.example.StoreWarehouseMongo1.repositories.ProductRepository;
import com.example.StoreWarehouseMongo1.repositories.PseudoProductRepository;
import com.example.StoreWarehouseMongo1.repositories.StockRepository;
import com.example.StoreWarehouseMongo1.repositories.StoreRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/api/pseudo")
@CrossOrigin(origins = "*")
public class PseudoProductController {

    @Autowired
    private CategoryRepository categoryrepository;

    @Autowired
    private PseudoProductRepository pseudoproductrepository;

    @Autowired
    private ProductRepository productrepository;

    @Autowired
    private StoreRepository storerepository;

    @Autowired
    private StockRepository stockrepository;

    @Autowired
    private HistoryRepository historyrepository;
    
    @Autowired
    private ProducerRepository producerRepository;

    //EDW TAUTOXRONA GINETAI KAI CREATE PSEUDOPRODUCT STH VASH
    //GINETAI O ELEGXOS KAI AN YPARXEI TO SUGKEKRIMENO PSEUDOPRODUCT DEN APOTHIKEUETAI
    //KAI APLA GINETAI RETURN OLA TA PSEUDOPRODUCTS POU PERIMENEI NA DEI O XRHSTHS
    @GetMapping(value = "/get/pseudoProducts")
    public ResponseEntity<?> getPseudoProducts(@RequestParam("address") String address,
            @RequestParam("page") Integer page) { //fernei ta pseudoproducts kai kanei kai insert to kathena an den uparxei hdh
        if (page > 0) {
            Store store = new Store();
            long start = System.currentTimeMillis();
            try {
                long start1 = System.currentTimeMillis();
                store = storerepository.findByaddress(address).get(0);
                long end = System.currentTimeMillis();
                System.out.println("DATABASE QUERY FOR STORE: " + (end - start1));

            } catch (Exception e) {
                return new ResponseEntity(new CustomErrorType("The specific store not found", HttpStatus.NOT_FOUND.value()),
                        HttpStatus.NOT_FOUND);
            }
            List<PseudoProduct> pseudoproducts = new ArrayList();
            List<Stock> stock = new ArrayList();
            try {
                stock = store.getStock(); //an uparxei stock uparxei sigoura kai product
            } catch (Exception e) {
                return new ResponseEntity(new CustomErrorType("This store doesn't have stock", HttpStatus.NOT_FOUND.value()),
                        HttpStatus.NOT_FOUND);
            }
            if (!stock.isEmpty() && stock != null && stock.size() > 0) {
                int size = stock.size();
                Integer i = 0;
                if (page == 1) {
                    page = 0;
                }
                int index = 32 * page; // 87 products kai 
                int result = (index - size); // 16 - 4 = 4
                if (result >= 32) {
                    return new ResponseEntity(new CustomErrorType("This page number exceed the number of real pages", HttpStatus.NOT_FOUND.value()),
                            HttpStatus.NOT_FOUND);
                }
                Stock st = new Stock();
                int lastOfStock = (page - 1) * 32;
                int whenSizeIsSmallerThanTheObjectsWeWantForAPage = 0;
                for (int j = 1; j <= 32; j++) {
                    if (size > 32) {
                        if (index > size) {
                            if (lastOfStock < size) {
                                st = stock.get(lastOfStock);
                                lastOfStock++;
                            }
                        }
                        if (index == size) {
                            index = index - 32;
                            st = stock.get(index);
                        }
                        if (index < size) {
                            st = stock.get(index);
                        }
                    }
                    if (size < 32) { //otan einai to size mikrotero apo to poso pou theloume na exei mia selida
                        st = stock.get(j - 1);
                        whenSizeIsSmallerThanTheObjectsWeWantForAPage++;
                    }
                    if (size == 32) {
                        st = stock.get(j - 1);
                    }
                    Product pr = new Product();
                    try {
                        long start1 = System.currentTimeMillis();
                        pr = productrepository.findByproductcode(st.getProductId()).get(0);
                        long end = System.currentTimeMillis();
                        System.out.println("DATABASE QUERY FOR PRODUCT: " + (end - start1));
                    } catch (Exception e) {
                    }
                    PseudoProduct pspr = new PseudoProduct(pr, pr.getProductcode(), st);
                    if (i < size) { //auto ginetai na gia na exoun ola ta pseudoproduct id apo 0 ews ...
                        String s = i.toString();
                        pspr.setId(s);
                        i++;
                    }
                    PseudoProduct pseudoproduct = new PseudoProduct();
                    try {
                        pseudoproduct = pseudoproductrepository.findByproductcode(pspr.getProductcode()).get(0);
                    } catch (Exception e) { //an den uparxei to pseudoproduct mpainei sth catch kai ginetai save
                        //pseudoproductrepository.save(pspr);
                    }
                    pseudoproducts.add(pspr);
                    index++;
                    if (lastOfStock == size) {
                        break;
                    }
                    if (whenSizeIsSmallerThanTheObjectsWeWantForAPage == size) {
                        break;
                    }
                }
                long end = System.currentTimeMillis();
                List<List<?>> testList = new ArrayList();
                List<Integer> pagesTest = new ArrayList();
                pagesTest.add(2);
                testList.add(pseudoproducts);
                testList.add(pagesTest);
                System.out.println("FINAL TIME FOR THIS API CALL:  " + (end - start));
                return new ResponseEntity<List>(testList, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("There is not content", HttpStatus.NO_CONTENT);
            }
        } else {
            return new ResponseEntity<>("Page must be bigger than zero", HttpStatus.CONFLICT);
        }
    }

    @GetMapping(value = "/category")
    public ResponseEntity<?> getProductsByCategory(@RequestParam("categoryId") String categoryId,
            @RequestParam("address") String address, @RequestParam("page") Integer page) {
        if (page > 0) {
            List<Stock> stock = new ArrayList();
            Store store = new Store();
            try {
                store = storerepository.findByaddress(address).get(0);
            } catch (Exception e) {
                return new ResponseEntity(new CustomErrorType("Wrong address. This address does not exist", HttpStatus.NOT_FOUND.value()),
                        HttpStatus.NOT_FOUND);
            }
            try {
                stock = store.getStock(); //an uparxei stock uparxei sigoura kai product
            } catch (Exception e) {
                return new ResponseEntity(new CustomErrorType("This store doesn't have stock", HttpStatus.NOT_FOUND.value()),
                        HttpStatus.NOT_FOUND);
            }
            if (!stock.isEmpty() && stock != null && stock.size() > 0) {
                Comparator<Stock> compareProductId = (Stock stock1, Stock stock2) //sort by productId
                        -> stock1.getProductId().compareTo(stock2.getProductId());
                Collections.sort(stock, compareProductId);
                List<Stock> stockByCategory = new ArrayList();
                for (Stock st : stock) {
                    if (st.getCategoryId().equals(categoryId)) {
                        stockByCategory.add(st);
                    }
                }
                if (stockByCategory.isEmpty() || stockByCategory == null) {
                    return new ResponseEntity(new CustomErrorType("This category doesn't exist", HttpStatus.NOT_FOUND.value()),
                            HttpStatus.NOT_FOUND);
                }
                int size = stockByCategory.size();
                List<PseudoProduct> pseudoproducts = new ArrayList();
                Integer i = 0;
                if (page == 1) {
                    page = 0;
                }
                int index = 32 * page; // 87 products kai 
                int result = (index - size); // 16 - 4 = 4
                if (result >= 32) {
                    return new ResponseEntity(new CustomErrorType("This page number exceed the number of real pages", HttpStatus.NOT_FOUND.value()),
                            HttpStatus.NOT_FOUND);
                }
                Stock st = new Stock();
                int lastOfStock = (page - 1) * 32;
                int whenSizeIsSmallerThanTheObjectsWeWantForAPage = 0;
                for (int j = 1; j <= 32; j++) {
                    if (size > 32) {
                        if (index > size) {
                            if (lastOfStock < size) {
                                st = stockByCategory.get(lastOfStock);
                                lastOfStock++;
                            }
                        }
                        if (index == size) {
                            index = index - 32;
                            st = stockByCategory.get(index);
                        }
                        if (index < size) {
                            st = stockByCategory.get(index);
                        }
                    }
                    if (size < 32) { //otan einai to size mikrotero apo to poso pou theloume na exei mia selida
                        st = stockByCategory.get(j - 1);
                        whenSizeIsSmallerThanTheObjectsWeWantForAPage++;
                    }
                    if (size == 32) {
                        st = stockByCategory.get(j - 1);
                    }
                    Product pr = new Product();
                    try {
                        long start1 = System.currentTimeMillis();
                        pr = productrepository.findByproductcode(st.getProductId()).get(0);
                        long end = System.currentTimeMillis();
                        System.out.println("DATABASE QUERY FOR PRODUCT: " + (end - start1));
                    } catch (Exception e) {
                    }
                    PseudoProduct pspr = new PseudoProduct(pr, pr.getProductcode(), st);
                    if (i < size) { //auto ginetai na gia na exoun ola ta pseudoproduct id apo 0 ews ...
                        String s = i.toString();
                        pspr.setId(s);
                        i++;
                    }
                    PseudoProduct pseudoproduct = new PseudoProduct();
                    try {
                        pseudoproduct = pseudoproductrepository.findByproductcode(pspr.getProductcode()).get(0);
                    } catch (Exception e) { //an den uparxei to pseudoproduct mpainei sth catch kai ginetai save
                        //pseudoproductrepository.save(pspr);
                    }
                    pseudoproducts.add(pspr);
                    index++;
                    if (lastOfStock == size) {
                        break;
                    }
                    if (whenSizeIsSmallerThanTheObjectsWeWantForAPage == size) {
                        break;
                    }
                }
                return new ResponseEntity<List<PseudoProduct>>(pseudoproducts, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("There is not content", HttpStatus.NO_CONTENT);
            }
        } else {
            return new ResponseEntity<>("Page must be bigger than zero", HttpStatus.CONFLICT);
        }
    }

    @GetMapping(value = "/get/{stockId}")
    public ResponseEntity<?> getPseudoproduct(@PathVariable("stockId") String id) {
        try {
            Stock stock = stockrepository.findById(id).get();
            String productCode = stock.getProductId();
            Product pr = productrepository.findByproductcode(productCode).get(0);
            PseudoProduct pspr = new PseudoProduct(pr, productCode, stock);
            pspr.setId("1");
            return new ResponseEntity<PseudoProduct>(pspr, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new CustomErrorType("Product not found", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/producer")
    public ResponseEntity<?> getProductsByProducerCode(@RequestParam("producerId") String producerId,
            @RequestParam("address") String address, @RequestParam("page") Integer page) {
        if (page > 0) {
            List<Stock> stock = new ArrayList();
            Store store = new Store();
            try {
                store = storerepository.findByaddress(address).get(0);
            } catch (Exception e) {
                return new ResponseEntity(new CustomErrorType("Wrong address. This address does not exist", HttpStatus.NOT_FOUND.value()),
                        HttpStatus.NOT_FOUND);
            }
            try {
                stock = store.getStock(); //an uparxei stock uparxei sigoura kai product
            } catch (Exception e) {
                return new ResponseEntity(new CustomErrorType("This store doesn't have stock", HttpStatus.NOT_FOUND.value()),
                        HttpStatus.NOT_FOUND);
            }
            if (!stock.isEmpty() && stock != null && stock.size() > 0) {
                Comparator<Stock> compareProductId = (Stock stock1, Stock stock2) //sort by productId
                        -> stock1.getProductId().compareTo(stock2.getProductId());
                Collections.sort(stock, compareProductId);
                List<Stock> stockByProducerCode = new ArrayList();
                for (Stock st : stock) {
                    if (st.getProducerId().equals(producerId)) {
                        stockByProducerCode.add(st);
                    }
                }
                if (stockByProducerCode.isEmpty() || stockByProducerCode == null) {
                    return new ResponseEntity(new CustomErrorType("This producer code doesn't exist", HttpStatus.NOT_FOUND.value()),
                            HttpStatus.NOT_FOUND);
                }
                int size = stockByProducerCode.size();
                List<PseudoProduct> pseudoproducts = new ArrayList();
                Integer i = 0;
                if (page == 1) {
                    page = 0;
                }
                int index = 32 * page; // 87 products kai 
                int result = (index - size); // 16 - 4 = 4
                if (result >= 32) {
                    return new ResponseEntity(new CustomErrorType("This page number exceed the number of real pages", HttpStatus.NOT_FOUND.value()),
                            HttpStatus.NOT_FOUND);
                }
                Stock st = new Stock();
                int lastOfStock = (page - 1) * 32;
                int whenSizeIsSmallerThanTheObjectsWeWantForAPage = 0;
                for (int j = 1; j <= 32; j++) {
                    if (size > 32) {
                        if (index > size) {
                            if (lastOfStock < size) {
                                st = stockByProducerCode.get(lastOfStock);
                                lastOfStock++;
                            }
                        }
                        if (index == size) {
                            index = index - 32;
                            st = stockByProducerCode.get(index);
                        }
                        if (index < size) {
                            st = stockByProducerCode.get(index);
                        }
                    }
                    if (size < 32) { //otan einai to size mikrotero apo to poso pou theloume na exei mia selida
                        st = stockByProducerCode.get(j - 1);
                        whenSizeIsSmallerThanTheObjectsWeWantForAPage++;
                    }
                    if (size == 32) {
                        st = stockByProducerCode.get(j - 1);
                    }
                    Product pr = new Product();
                    try {
                        long start1 = System.currentTimeMillis();
                        pr = productrepository.findByproductcode(st.getProductId()).get(0);
                        long end = System.currentTimeMillis();
                        System.out.println("DATABASE QUERY FOR PRODUCT: " + (end - start1));
                    } catch (Exception e) {
                    }
                    PseudoProduct pspr = new PseudoProduct(pr, pr.getProductcode(), st);
                    if (i < size) { //auto ginetai na gia na exoun ola ta pseudoproduct id apo 0 ews ...
                        String s = i.toString();
                        pspr.setId(s);
                        i++;
                    }
                    PseudoProduct pseudoproduct = new PseudoProduct();
                    try {
                        pseudoproduct = pseudoproductrepository.findByproductcode(pspr.getProductcode()).get(0);
                    } catch (Exception e) { //an den uparxei to pseudoproduct mpainei sth catch kai ginetai save
                        //pseudoproductrepository.save(pspr);
                    }
                    pseudoproducts.add(pspr);
                    index++;
                    if (lastOfStock == size) {
                        break;
                    }
                    if (whenSizeIsSmallerThanTheObjectsWeWantForAPage == size) {
                        break;
                    }
                }
                return new ResponseEntity<List<PseudoProduct>>(pseudoproducts, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("There is not content", HttpStatus.NO_CONTENT);
            }
        } else {
            return new ResponseEntity<>("Page must be bigger than zero", HttpStatus.CONFLICT);
        }
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> createPseudoproduct(@RequestBody PseudoProduct pseudoProduct, 
            @RequestParam("address") String addressToStored) {
        Product product = new Product();
        Stock stock = new Stock();
        try {
            List<Product> products = productrepository.findByproductcode(pseudoProduct.getProduct().getProductcode());
            if (products.size() > 0 && products != null) {
                return new ResponseEntity(new CustomErrorType("This product is already exist", HttpStatus.CONFLICT.value()), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
        }
        try {
            Store store = new Store();
            try {
                store = storerepository.findByaddress(addressToStored).get(0);
            } catch (Exception e) {
                return new ResponseEntity(new CustomErrorType("Store not found", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
            }
            List<Stock> stockList = null;
            stockList = store.getStock();
            product = pseudoProduct.getProduct();
            Category category = new Category();
            try {
                String kindOfCategory = product.getCategory().getKindOfCategory();
                category = categoryrepository.findBykindOfCategory(kindOfCategory).get(0);
                product.setCategory(category);
            } catch (Exception ex) {
                return new ResponseEntity(new CustomErrorType("This category not found", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
            }
            Producer producer = new Producer();
            try {
                String producerCode = product.getProducer().getProducerCode();
                producer = producerRepository.findByproducerCode(producerCode).get(0);
                product.setProducer(producer);
            } catch (Exception ex) {
                return new ResponseEntity(new CustomErrorType("This category not found", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
            }
            productrepository.save(product);
            stock = pseudoProduct.getStock();
            stock.setCategoryId(category.getId());
            stock.setProducerId(producer.getId());
            stockrepository.save(stock);
            stockList.add(stock);
            store.setStock(stockList);
            storerepository.save(store);
            saveHistory(stock, store);
            String goldW = stock.getGold_weight();
            String silverW = stock.getSilver_weight();
            String karats = stock.getKarats();
            String productId = stock.getProductId();
            if (stock.getColor().equals("White")) {
                Stock st1 = new Stock();
                st1.setGold_weight(goldW);
                st1.setSilver_weight(silverW);
                st1.setKarats(karats);
                st1.setColor("Yellow");
                st1.setImageUrl("");
                st1.setProductId(productId);
                st1.setQuantity(0);
                st1.setProducerId(producer.getId());
                stockList.add(st1);
                store.setStock(stockList);
                st1.setCategoryId(category.getId());
                stockrepository.save(st1);
                storerepository.save(store);
                saveHistory(st1, store);
                Stock st2 = new Stock();
                st2.setGold_weight(goldW);
                st2.setSilver_weight(silverW);
                st2.setKarats(karats);
                st2.setColor("Black");
                st2.setProductId(productId);
                st2.setQuantity(0);
                st2.setImageUrl("");
                st2.setProducerId(producer.getId());
                st2.setCategoryId(category.getId());
                stockrepository.save(st2);
                stockList.add(st2);
                store.setStock(stockList);
                storerepository.save(store);
                saveHistory(st2, store);
                Stock st3 = new Stock();
                st3.setGold_weight(goldW);
                st3.setSilver_weight(silverW);
                st3.setKarats(karats);
                st3.setColor("Rose");
                st3.setProductId(productId);
                st3.setQuantity(0);
                st3.setImageUrl("");
                st3.setCategoryId(category.getId());
                st3.setProducerId(product.getProducer().getId());
                stockrepository.save(st3);
                stockList.add(st3);
                store.setStock(stockList);
                storerepository.save(store);
                saveHistory(st3, store);
            }
            if (stock.getColor().equals("Yellow")) {
                Stock st1 = new Stock();
                st1.setGold_weight(goldW);
                st1.setSilver_weight(silverW);
                st1.setKarats(karats);
                st1.setColor("White");
                st1.setImageUrl("");
                st1.setProductId(productId);
                st1.setQuantity(0);
                st1.setCategoryId(category.getId());
                st1.setProducerId(product.getProducer().getId());
                stockList.add(st1);
                store.setStock(stockList);
                stockrepository.save(st1);
                storerepository.save(store);
                saveHistory(st1, store);
                Stock st2 = new Stock();
                st2.setGold_weight(goldW);
                st2.setSilver_weight(silverW);
                st2.setKarats(karats);
                st2.setColor("Black");
                st2.setProductId(productId);
                st2.setQuantity(0);
                st2.setImageUrl("");
                st2.setCategoryId(category.getId());
                st2.setProducerId(product.getProducer().getId());
                stockrepository.save(st2);
                stockList.add(st2);
                store.setStock(stockList);
                storerepository.save(store);
                saveHistory(st2, store);
                Stock st3 = new Stock();
                st3.setGold_weight(goldW);
                st3.setSilver_weight(silverW);
                st3.setKarats(karats);
                st3.setColor("Rose");
                st3.setProductId(productId);
                st3.setQuantity(0);
                st3.setImageUrl("");
                st3.setCategoryId(category.getId());
                st3.setProducerId(product.getProducer().getId());
                stockrepository.save(st3);
                stockList.add(st3);
                store.setStock(stockList);
                storerepository.save(store);
                saveHistory(st3, store);
            }
            if (stock.getColor().equals("Rose")) {
                Stock st1 = new Stock();
                st1.setGold_weight(goldW);
                st1.setSilver_weight(silverW);
                st1.setKarats(karats);
                st1.setColor("Yellow");
                st1.setImageUrl("");
                st1.setProductId(productId);
                st1.setQuantity(0);
                st1.setProducerId(producer.getId());
                stockList.add(st1);
                store.setStock(stockList);
                st1.setCategoryId(category.getId());
                stockrepository.save(st1);
                storerepository.save(store);
                saveHistory(st1, store);
                Stock st2 = new Stock();
                st2.setGold_weight(goldW);
                st2.setSilver_weight(silverW);
                st2.setKarats(karats);
                st2.setColor("Black");
                st2.setProductId(productId);
                st2.setQuantity(0);
                st2.setImageUrl("");
                st2.setProducerId(producer.getId());
                st2.setCategoryId(category.getId());
                stockrepository.save(st2);
                stockList.add(st2);
                store.setStock(stockList);
                storerepository.save(store);
                saveHistory(st2, store);
                Stock st3 = new Stock();
                st3.setGold_weight(goldW);
                st3.setSilver_weight(silverW);
                st3.setKarats(karats);
                st3.setColor("Rose");
                st3.setProductId(productId);
                st3.setQuantity(0);
                st3.setImageUrl("");
                st3.setCategoryId(category.getId());
                st3.setProducerId(producer.getId());
                stockrepository.save(st3);
                stockList.add(st3);
                store.setStock(stockList);
                storerepository.save(store);
                saveHistory(st3, store);
            }
            if (stock.getColor().equals("Black")) {
                Stock st1 = new Stock();
                st1.setGold_weight(goldW);
                st1.setSilver_weight(silverW);
                st1.setKarats(karats);
                st1.setColor("Yellow");
                st1.setImageUrl("");
                st1.setProductId(productId);
                st1.setQuantity(0);
                st1.setCategoryId(category.getId());
                st1.setProducerId(producer.getId());
                stockList.add(st1);
                store.setStock(stockList);
                stockrepository.save(st1);
                storerepository.save(store);
                saveHistory(st1, store);
                Stock st2 = new Stock();
                st2.setGold_weight(goldW);
                st2.setSilver_weight(silverW);
                st2.setKarats(karats);
                st2.setColor("White");
                st2.setProductId(productId);
                st2.setQuantity(0);
                st2.setImageUrl("");
                st2.setCategoryId(category.getId());
                st2.setProducerId(producer.getId());
                stockrepository.save(st2);
                stockList.add(st2);
                store.setStock(stockList);
                storerepository.save(store);
                saveHistory(st2, store);
                Stock st3 = new Stock();
                st3.setGold_weight(goldW);
                st3.setSilver_weight(silverW);
                st3.setKarats(karats);
                st3.setColor("Rose");
                st3.setProductId(productId);
                st3.setQuantity(0);
                st3.setImageUrl("");
                st3.setCategoryId(category.getId());
                st3.setProducerId(producer.getId());
                stockrepository.save(st3);
                stockList.add(st3);
                store.setStock(stockList);
                storerepository.save(store);
                saveHistory(st3, store);
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            return new ResponseEntity(new CustomErrorType("Product not found", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //UPDATE MONO GIA TO XRHSTH AFOU O XRHSTHS
    //O ADMIN THA MPOREI NA KANEI UPDATE PANTOU ENW O USER MONO STH QUANTITY
    @PatchMapping(value = "/update")
    public ResponseEntity<?> updatePseudoproduct(@RequestBody PseudoProduct pseudoProduct) {
        Product pr = new Product();
        Stock st = new Stock();
        try {
            pr = productrepository.findById(pseudoProduct.getProduct().getId()).get();
            st = stockrepository.findById(pseudoProduct.getStock().getId()).get();
        } catch (Exception e) {
            return new ResponseEntity(new CustomErrorType("Product not found", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        }
        Product product = pseudoProduct.getProduct();
        product.setId(pr.getId());

        Stock stock = pseudoProduct.getStock();
        stock.setId(st.getId());

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String timestamp = dtf.format(now);
        String quantity = st.getQuantity().toString(); //apo vash
        String quant = stock.getQuantity().toString(); //apo ui
        if (!quantity.equals(quant)) {
            History productHistory = new History();
            productHistory.setTimestamp(timestamp);
            productHistory.setStock(stock);
            historyrepository.save(productHistory);
        }
        productrepository.save(product);
        stockrepository.save(stock);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void saveHistory(Stock stock, Store store) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String timestamp = dtf.format(now);
        History productHistory = new History();
        productHistory.setTimestamp(timestamp);
        productHistory.setStock(stock);
        productHistory.setStockId(stock.getId());
        productHistory.setStoreId(store.getId());
        historyrepository.save(productHistory);
    }

    @PostMapping(value = "/GOD/create")
    public void developerCreate(@RequestBody PseudoProduct pseudoProduct) {
        Product prooduct = new Product();
        int prCounter = 0;
        int j = 0;
        for (int i = 0; i <= 100; i++) {
            String prCode = "B2";
            prCounter = (100 + j);
            String productCode = prCode + prCounter;
            try {
                Store store = new Store();
                Stock stock = new Stock();
                store = storerepository.findByaddress("Kifisia").get(0);
                List<Stock> stockList = null;
                stockList = store.getStock();
                prooduct = pseudoProduct.getProduct(); // product 
                Product product = new Product(productCode, prooduct.getCategory());
                try {
                    String kindOfCategory = product.getCategory().getKindOfCategory();
                    Category category = categoryrepository.findBykindOfCategory(kindOfCategory).get(0);
                    product.setCategory(category);
                } catch (Exception ex) {
                }
                productrepository.save(product);
                stock.setGold_weight("0.06");
                stock.setSilver_weight("0.05");
                stock.setKarats("9");
                stock.setColor("White");
                stock.setImageUrl("");
                stock.setQuantity(25);
                stock.setProductId(productCode);
                stock.setProducerId(product.getProducer().getId());
                stockrepository.save(stock);
                stockList.add(stock);
                store.setStock(stockList);
                storerepository.save(store);
                saveHistory(stock, store);
                String goldW = stock.getGold_weight();
                String silverW = stock.getSilver_weight();
                String karats = stock.getKarats();
                String productId = stock.getProductId();
                if (stock.getColor().equals("White")) {
                    Stock st1 = new Stock();
                    st1.setGold_weight(goldW);
                    st1.setSilver_weight(silverW);
                    st1.setKarats(karats);
                    st1.setColor("Yellow");
                    st1.setImageUrl("");
                    st1.setProductId(productId);
                    st1.setQuantity(0);
                    st1.setProductId(productCode);
                    st1.setProducerId(product.getProducer().getId());
                    stockList.add(st1);
                    store.setStock(stockList);
                    stockrepository.save(st1);
                    storerepository.save(store);
                    saveHistory(st1, store);
                    Stock st2 = new Stock();
                    st2.setGold_weight(goldW);
                    st2.setSilver_weight(silverW);
                    st2.setKarats(karats);
                    st2.setColor("Black");
                    st2.setProductId(productId);
                    st2.setQuantity(0);
                    st2.setImageUrl("");
                    st2.setProductId(productCode);
                    st2.setProducerId(product.getProducer().getId());
                    stockrepository.save(st2);
                    stockList.add(st2);
                    store.setStock(stockList);
                    storerepository.save(store);
                    saveHistory(st2, store);
                    Stock st3 = new Stock();
                    st3.setGold_weight(goldW);
                    st3.setSilver_weight(silverW);
                    st3.setKarats(karats);
                    st3.setColor("Rose");
                    st3.setProductId(productId);
                    st3.setQuantity(0);
                    st3.setImageUrl("");
                    st3.setProductId(productCode);
                    st3.setProducerId(product.getProducer().getId());
                    stockrepository.save(st3);
                    stockList.add(st3);
                    store.setStock(stockList);
                    storerepository.save(store);
                    saveHistory(st3, store);
                }
                if (stock.getColor().equals("Yellow")) {
                    Stock st1 = new Stock();
                    st1.setGold_weight(goldW);
                    st1.setSilver_weight(silverW);
                    st1.setKarats(karats);
                    st1.setColor("White");
                    st1.setImageUrl("");
                    st1.setProductId(productId);
                    st1.setQuantity(0);
                    stockList.add(st1);
                    store.setStock(stockList);
                    st1.setProductId(productCode);
                    st1.setProducerId(product.getProducer().getId());
                    stockrepository.save(st1);
                    storerepository.save(store);
                    saveHistory(st1, store);
                    Stock st2 = new Stock();
                    st2.setGold_weight(goldW);
                    st2.setSilver_weight(silverW);
                    st2.setKarats(karats);
                    st2.setColor("Black");
                    st2.setProductId(productId);
                    st2.setQuantity(0);
                    st2.setImageUrl("");
                    st2.setProductId(productCode);
                    st2.setProducerId(product.getProducer().getId());
                    stockrepository.save(st2);
                    stockList.add(st2);
                    store.setStock(stockList);
                    storerepository.save(store);
                    saveHistory(st2, store);
                    Stock st3 = new Stock();
                    st3.setGold_weight(goldW);
                    st3.setSilver_weight(silverW);
                    st3.setKarats(karats);
                    st3.setColor("Rose");
                    st3.setProductId(productId);
                    st3.setQuantity(0);
                    st3.setImageUrl("");
                    st3.setProductId(productCode);
                    st3.setProducerId(product.getProducer().getId());
                    stockrepository.save(st3);
                    stockList.add(st3);
                    store.setStock(stockList);
                    storerepository.save(store);
                    saveHistory(st3, store);
                }
                if (stock.getColor().equals("Rose")) {
                    Stock st1 = new Stock();
                    st1.setGold_weight(goldW);
                    st1.setSilver_weight(silverW);
                    st1.setKarats(karats);
                    st1.setColor("Yellow");
                    st1.setImageUrl("");
                    st1.setProductId(productId);
                    st1.setQuantity(0);
                    stockList.add(st1);
                    store.setStock(stockList);
                    st1.setProductId(productCode);
                    st1.setProducerId(product.getProducer().getId());
                    stockrepository.save(st1);
                    storerepository.save(store);
                    saveHistory(st1, store);
                    Stock st2 = new Stock();
                    st2.setGold_weight(goldW);
                    st2.setSilver_weight(silverW);
                    st2.setKarats(karats);
                    st2.setColor("Black");
                    st2.setProductId(productId);
                    st2.setQuantity(0);
                    st2.setImageUrl("");
                    st2.setProductId(productCode);
                    st2.setProducerId(product.getProducer().getId());
                    stockrepository.save(st2);
                    stockList.add(st2);
                    store.setStock(stockList);
                    storerepository.save(store);
                    saveHistory(st2, store);
                    Stock st3 = new Stock();
                    st3.setGold_weight(goldW);
                    st3.setSilver_weight(silverW);
                    st3.setKarats(karats);
                    st3.setColor("Rose");
                    st3.setProductId(productId);
                    st3.setQuantity(0);
                    st3.setImageUrl("");
                    st3.setProductId(productCode);
                    st3.setProducerId(product.getProducer().getId());
                    stockrepository.save(st3);
                    stockList.add(st3);
                    store.setStock(stockList);
                    storerepository.save(store);
                    saveHistory(st3, store);
                }
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
            j++;
        }

    }

}
