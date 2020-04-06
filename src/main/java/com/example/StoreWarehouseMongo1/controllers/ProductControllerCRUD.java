package com.example.StoreWarehouseMongo1.controllers;

import com.example.StoreWarehouseMongo1.dao.ProductDAO;
import com.example.StoreWarehouseMongo1.helpers.Pagination;
import com.example.StoreWarehouseMongo1.model.History;
import com.example.StoreWarehouseMongo1.model.Product;
import com.example.StoreWarehouseMongo1.repositories.ProductRepository;
import com.example.StoreWarehouseMongo1.repositories.StoreRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/api/product")
@CrossOrigin(origins = "*")
public class ProductControllerCRUD {

    @Autowired
    private ProductRepository productrepository;

    @Autowired
    private ProductDAO productDao;

    @Autowired
    private Pagination pagination;

    @Autowired
    private StoreRepository storerepository;

    @GetMapping
    public List<Product> getAll() {
        return productrepository.findAll();
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> getProductsPaginatedAndFiltered(@RequestParam("page") int page,
            @RequestParam("categoryId") String categoryId, @RequestParam("producerId") String producerId,
            @RequestParam("storeId") String storeId) {
        List<Product> products = pagination.getProductsPaginated(page, categoryId, storeId, producerId);
        List<Map<String, Integer>> listMaxSize = new ArrayList();
        listMaxSize.add(pagination.getMaxSize(storeId, producerId, categoryId));
        List<List<?>> productsWithMaxSize = new ArrayList();
        productsWithMaxSize.add(products);
        productsWithMaxSize.add(listMaxSize);
        return new ResponseEntity<List<List<?>>>(productsWithMaxSize, HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public void create(@RequestBody Product product) {
        productDao.insert(product);
    }

    @GetMapping(value = "/get")
    public ResponseEntity<?> getProduct(@RequestParam("productId") String productId,
            @RequestParam("storeId") String storeId) {
        Product product = new Product();
        try {
            product = productrepository.findById(productId).get();
        } catch (Exception IndexOutOfBoundsException) {
        }
        if (product.getProductcode() == null) {
            return new ResponseEntity(new CustomErrorType("Product with productCode " + product.getProductcode()
                    + " not found", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }

//    @PatchMapping(value = "/update")
//    public ResponseEntity<?> updatePseudoproduct(@RequestBody PseudoProduct pseudoProduct) {
//        Product pr = new Product();
//        Stock st = new Stock();
//        try {
//            //pr = productrepository.findById(pseudoProduct.getProduct().getId()).get();
//            st = stockrepository.findById(pseudoProduct.getStock().getId()).get();
//        } catch (Exception e) {
//            return new ResponseEntity(new CustomErrorType("Product not found", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
//        }
//        Product product = pseudoProduct.getProduct();
//
//        product.setId(pr.getId());
//
//        Stock stock = pseudoProduct.getStock();
//        stock.setId(st.getId());
//
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
//        LocalDateTime now = LocalDateTime.now();
//        String timestamp = dtf.format(now);
//        String quantity = st.getQuantity().toString(); //apo vash
//        String quant = stock.getQuantity().toString(); //apo ui
//        if (!quantity.equals(quant)) {
//            History productHistory = new History();
//            productHistory.setTimestamp(timestamp);
//            // productHistory.setStock(stock);
//            historyrepository.save(productHistory);
//        }
//        productrepository.save(product);
//        stockrepository.save(stock);
//        if (!stock.getOther_stone().equals(st.getOther_stone()) //GINETAI UPDATE SE OLA TA KATASTHMATA
//                || !stock.getOther_stoneWeight().equals(st.getOther_stoneWeight())
//                || !stock.getDiamond_weight().equals(st.getDiamond_weight())) {
//            List<Stock> stockToUpdate = stockrepository.findByproductIdAndColor(stock.getProductId(), stock.getColor());
//            for (Stock s : stockToUpdate) {
//                s.setOther_stone(stock.getOther_stone());
//                s.setOther_stoneWeight(stock.getOther_stoneWeight());
//                s.setOther_stoneWeight(stock.getOther_stoneWeight());
//                stockrepository.save(s);
//            }
//        }
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
    @DeleteMapping(value = "/deleteAll")
    public void deleteProducts() {
        productrepository.deleteAll();
    }
}
