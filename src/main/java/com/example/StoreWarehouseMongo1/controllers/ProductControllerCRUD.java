package com.example.StoreWarehouseMongo1.controllers;

import com.example.StoreWarehouseMongo1.dao.ProductDAO;
import com.example.StoreWarehouseMongo1.model.Product;
import com.example.StoreWarehouseMongo1.repositories.ProductRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

    @GetMapping
    public List<Product> getAll() {
        return productrepository.findAll();
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> getProductsPaginatedAndFiltered(@RequestParam("page") int page,
            @RequestParam("categoryId") String categoryId, @RequestParam("producerId") String producerId,
            @RequestParam("address") String address, @RequestParam("limit") String limit) {
        try {
            return new ResponseEntity<List<List<?>>>(productDao.getProductsPerFilterCase(page, categoryId, producerId, address, limit), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new CustomErrorType("Error: " + e.getMessage()
                    + " ", HttpStatus.GATEWAY_TIMEOUT.value()), HttpStatus.GATEWAY_TIMEOUT);
        }
    }

    @PostMapping(value = "/create")
    public void create(@RequestBody Product product) throws Exception {
        productDao.insert(product);
    }

    @GetMapping(value = "/get")
    public ResponseEntity<?> getProduct(@RequestParam("productId") String productId) {
        Product product = new Product();
        List<List<?>> response = new ArrayList();
        List<Product> prod = new ArrayList();
        try {
            product = productrepository.findById(productId).get();
            String color = product.getColor();
            String sku = product.getsku();
            List<Map<?, ?>> others = new ArrayList();
            List<List<Map<?, ?>>> responseList = new ArrayList();
            for (Product pr : productrepository.findByskuAndColor(sku, color)) {
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
        } catch (Exception IndexOutOfBoundsException) {
        }
        if (product.getsku() == null) {
            return new ResponseEntity(new CustomErrorType("Product with sku " + product.getsku()
                    + " not found", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteAll")
    public void deleteProducts() {
        productrepository.deleteAll();
    }

    @PatchMapping(value = "/disable")
    public ResponseEntity<Product> disableProduct(@RequestBody Product product) {
        try {
            productDao.makeItDisable(product);
            return new ResponseEntity(new CustomErrorType("The product is not produced anymore", HttpStatus.OK.value()),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new CustomErrorType("", HttpStatus.INTERNAL_SERVER_ERROR.value()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping(value = "/change")
    public ResponseEntity<Product> changeQuantityOfProduct(@RequestParam("quantity") int quantity,
            @RequestParam("id") String productId) {
        try {
            productDao.updateQuantity(productId, quantity);
            return new ResponseEntity(new CustomErrorType("The quantity is changed succesfully", HttpStatus.OK.value()),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new CustomErrorType(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping(value = "/update")
    public void updateProduct(@RequestBody Product product) {
      productDao.updateProduct(product);
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
//        if (!stock.getOtherStone().equals(st.getOtherStone()) //GINETAI UPDATE SE OLA TA KATASTHMATA
//                || !stock.getOtherStoneWeight().equals(st.getOtherStoneWeight())
//                || !stock.getDiamondWeight().equals(st.getDiamondWeight())) {
//            List<Stock> stockToUpdate = stockrepository.findByproductIdAndColor(stock.getProductId(), stock.getColor());
//            for (Stock s : stockToUpdate) {
//                s.setOtherStone(stock.getOtherStone());
//                s.setOtherStoneWeight(stock.getOtherStoneWeight());
//                s.setOtherStoneWeight(stock.getOtherStoneWeight());
//                stockrepository.save(s);
//            }
//        }
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
