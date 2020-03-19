package com.example.StoreWarehouseMongo1.controllers;

import com.example.StoreWarehouseMongo1.model.Category;
import com.example.StoreWarehouseMongo1.model.History;
import com.example.StoreWarehouseMongo1.model.PseudoProduct;
import com.example.StoreWarehouseMongo1.model.Product;
import com.example.StoreWarehouseMongo1.model.Stock;
import com.example.StoreWarehouseMongo1.model.Store;
import com.example.StoreWarehouseMongo1.repositories.CategoryRepository;
import com.example.StoreWarehouseMongo1.repositories.HistoryRepository;
import com.example.StoreWarehouseMongo1.repositories.ProductRepository;
import com.example.StoreWarehouseMongo1.repositories.PseudoProductRepository;
import com.example.StoreWarehouseMongo1.repositories.StockRepository;
import com.example.StoreWarehouseMongo1.repositories.StoreRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

    //EDW TAUTOXRONA GINETAI KAI CREATE PSEUDOPRODUCT STH VASH
    //GINETAI O ELEGXOS KAI AN YPARXEI TO SUGKEKRIMENO PSEUDOPRODUCT DEN APOTHIKEUETAI
    //KAI APLA GINETAI RETURN OLA TA PSEUDOPRODUCTS POU PERIMENEI NA DEI O XRHSTHS
    @GetMapping(value = "/get/pseudoProducts/{address}")
    public ResponseEntity<?> getPseudoProducts(@PathVariable("address") String shop) { //fernei ta pseudoproducts kai kanei kai insert to kathena an den uparxei hdh
        Store store = new Store();
        try {
            store = storerepository.findByaddress(shop).get(0);
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
        int size = stock.size();
        Integer i = 0;
        for (Stock st : stock) {
            Product pr = new Product();
            try {
                pr = productrepository.findByproductcode(st.getProductId()).get(0);
            } catch (Exception e) {
            }
            PseudoProduct pspr = new PseudoProduct(pr, pr.getProductcode(), st);
            if (i < size) {
                String s = i.toString();
                pspr.setId(s);
                i++;
            }
            PseudoProduct pseudoproduct = new PseudoProduct();
            try {
                pseudoproduct = pseudoproductrepository.findByproductcode(pspr.getProductcode()).get(0);
            } catch (Exception e) { //an den uparxei to pseudoproduct mpainei sth catch kai ginetai save
                pseudoproductrepository.save(pspr);
            }
            pseudoproducts.add(pspr);
        }
        return new ResponseEntity<List>(pseudoproducts, HttpStatus.OK);
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

    @PostMapping(value = "/create/{address}")
    public ResponseEntity<?> createPseudoproduct(@RequestBody PseudoProduct pseudoProduct, @PathVariable("address") String addressToStored) {
        Product product = new Product();
        Stock stock = new Stock();
        try {
            Store store = storerepository.findByaddress(addressToStored).get(0);
            List<Stock> stockList = null;
            stockList = store.getStock();
            product = pseudoProduct.getProduct();
            try {
                String kindOfCategory = product.getCategory().getKindOfCategory();
                Category category = categoryrepository.findBykindOfCategory(kindOfCategory).get(0);
                product.setCategory(category);
            } catch (Exception ex) {
            }
            productrepository.save(product);
            stock = pseudoProduct.getStock();
            stockrepository.save(stock);
            stockList.add(stock);
            store.setStock(stockList);
            storerepository.save(store);
            saveHistory(stock);
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
                stockList.add(st1);
                store.setStock(stockList);
                stockrepository.save(st1);
                storerepository.save(store);
                saveHistory(st1);
                Stock st2 = new Stock();
                st2.setGold_weight(goldW);
                st2.setSilver_weight(silverW);
                st2.setKarats(karats);
                st2.setColor("Black");
                st2.setProductId(productId);
                st2.setQuantity(0);
                st2.setImageUrl("");
                stockrepository.save(st2);
                stockList.add(st2);
                store.setStock(stockList);
                storerepository.save(store);
                saveHistory(st2);
                Stock st3 = new Stock();
                st3.setGold_weight(goldW);
                st3.setSilver_weight(silverW);
                st3.setKarats(karats);
                st3.setColor("Rose");
                st3.setProductId(productId);
                st3.setQuantity(0);
                st3.setImageUrl("");
                stockrepository.save(st3);
                stockList.add(st3);
                store.setStock(stockList);
                storerepository.save(store);
                saveHistory(st3);
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
                stockrepository.save(st1);
                storerepository.save(store);
                saveHistory(st1);
                Stock st2 = new Stock();
                st2.setGold_weight(goldW);
                st2.setSilver_weight(silverW);
                st2.setKarats(karats);
                st2.setColor("Black");
                st2.setProductId(productId);
                st2.setQuantity(0);
                st2.setImageUrl("");
                stockrepository.save(st2);
                stockList.add(st2);
                store.setStock(stockList);
                storerepository.save(store);
                saveHistory(st2);
                Stock st3 = new Stock();
                st3.setGold_weight(goldW);
                st3.setSilver_weight(silverW);
                st3.setKarats(karats);
                st3.setColor("Rose");
                st3.setProductId(productId);
                st3.setQuantity(0);
                st3.setImageUrl("");
                stockrepository.save(st3);
                stockList.add(st3);
                store.setStock(stockList);
                storerepository.save(store);
                saveHistory(st3);
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
                stockrepository.save(st1);
                storerepository.save(store);
                saveHistory(st1);
                Stock st2 = new Stock();
                st2.setGold_weight(goldW);
                st2.setSilver_weight(silverW);
                st2.setKarats(karats);
                st2.setColor("Black");
                st2.setProductId(productId);
                st2.setQuantity(0);
                st2.setImageUrl("");
                stockrepository.save(st2);
                stockList.add(st2);
                store.setStock(stockList);
                storerepository.save(store);
                saveHistory(st2);
                Stock st3 = new Stock();
                st3.setGold_weight(goldW);
                st3.setSilver_weight(silverW);
                st3.setKarats(karats);
                st3.setColor("Rose");
                st3.setProductId(productId);
                st3.setQuantity(0);
                st3.setImageUrl("");
                stockrepository.save(st3);
                stockList.add(st3);
                store.setStock(stockList);
                storerepository.save(store);
                saveHistory(st3);
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

    private void saveHistory(Stock stock) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String timestamp = dtf.format(now);
        History productHistory = new History();
        productHistory.setTimestamp(timestamp);
        productHistory.setStock(stock);
        historyrepository.save(productHistory);
    }
}
