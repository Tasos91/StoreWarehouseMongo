package com.example.StoreWarehouseMongo1.controllers;

import com.example.StoreWarehouseMongo1.model.PseudoProduct;
import com.example.StoreWarehouseMongo1.model.Product;
import com.example.StoreWarehouseMongo1.model.Stock;
import com.example.StoreWarehouseMongo1.model.Store;
import com.example.StoreWarehouseMongo1.repositories.ProductRepository;
import com.example.StoreWarehouseMongo1.repositories.PseudoProductRepository;
import com.example.StoreWarehouseMongo1.repositories.StoreRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin; 
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;    
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Tasos
 */

@RestController
@RequestMapping("/get")
@CrossOrigin(origins = "*")
public class PseudoProductController {
    
    @Autowired
    private PseudoProductRepository pseudoproductrepository;
    
    @Autowired
    private ProductRepository productrepository;
    
    @Autowired
    private StoreRepository storerepository;
    
    @RequestMapping(value = "/pseudoProducts/{address}", method = GET)
    public List<PseudoProduct> addStockController(@PathVariable("address") String shop) {
        Store store = storerepository.findByaddress(shop).get(0);
        List<PseudoProduct> pseudoProducts = new ArrayList();
        List<Stock> stock = store.getStock();
        for(Stock st : stock){
            Product pr = productrepository.findByproductcode(st.getProductId()).get(0);
            PseudoProduct psprod = new PseudoProduct(pr, st);
            pseudoproductrepository.save(psprod);
            pseudoProducts.add(psprod);
        }
        return pseudoProducts;
    }
}
