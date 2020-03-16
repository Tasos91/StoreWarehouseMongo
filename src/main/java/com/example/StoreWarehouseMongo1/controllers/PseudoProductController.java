package com.example.StoreWarehouseMongo1.controllers;

import com.example.StoreWarehouseMongo1.model.History;
import com.example.StoreWarehouseMongo1.model.PseudoProduct;
import com.example.StoreWarehouseMongo1.model.Product;
import com.example.StoreWarehouseMongo1.model.Stock;
import com.example.StoreWarehouseMongo1.model.Store;
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

    //UPDATE MONO GIA TO XRHSTH AFOU O XRHSTHS
    //THA MPOREI NA KANEI UPDATE MONO STH QUANTITY
    @PatchMapping(value = "/update/{productcode}/{quantity}")
    public ResponseEntity<?> updatePseudoproduct(@PathVariable("productcode") String productcode,
            @PathVariable("quantity") Integer quantity) {
        Stock stock = new Stock();
        try {
            stock = stockrepository.findByproductId(productcode).get(0);
        } catch (Exception e) {
            return new ResponseEntity(new CustomErrorType("Product not found", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        }
        stock.setQuantity(quantity);
        stockrepository.save(stock);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String timestamp = dtf.format(now);
        History productHistory = new History();
        productHistory.setTimestamp(timestamp);
        productHistory.setStock(stock);
        historyrepository.save(productHistory);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
