package com.example.StoreWarehouseMongo1.controllers;

import com.example.StoreWarehouseMongo1.model.Product;
import com.example.StoreWarehouseMongo1.model.Stock;
import com.example.StoreWarehouseMongo1.model.Store;
import com.example.StoreWarehouseMongo1.model.User;
import com.example.StoreWarehouseMongo1.repositories.ProductRepository;
import com.example.StoreWarehouseMongo1.repositories.StoreRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Tasos
 */
@RestController
@RequestMapping("/showProducts")
@CrossOrigin(origins = "*")
public class ShowProducts {

    @Autowired
    private StoreRepository storerepository;

    @Autowired
    private ProductRepository productrepository;

    @RequestMapping(value = "/showSpecificProducts/{address}", method = POST)
    public List<Product> findUserInsideStoreAndReturnProducts(@PathVariable("address") String address) { //show products if you have the username of the user that he is in the secific store

        List<Stock> stockList = storerepository.findByaddress(address).get(0).getStock();
        List<Product> products = new ArrayList<>();
        for (Stock s : stockList) {
            String productCode = s.getProductId();
            List<Product> prs = productrepository.findByproductcode(productCode);
            Product prd = prs.get(0);
            products.add(prd);
        }
        return products;
    }

}
