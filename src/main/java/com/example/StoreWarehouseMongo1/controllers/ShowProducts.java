package com.example.StoreWarehouseMongo1.controllers;

import com.example.StoreWarehouseMongo1.model.Product;
import com.example.StoreWarehouseMongo1.model.Store;
import com.example.StoreWarehouseMongo1.model.User;
import com.example.StoreWarehouseMongo1.repositories.StoreRepository;
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

    @RequestMapping(value = "/showSpecificProducts/{username}", method = POST)
    public List<Product> findUserInsideStoreAndReturnProducts(@PathVariable("username") String username) { //show products if you have the username of the user that he is in the secific store
        List<Store> stores = storerepository.findAll();
        List<Product> products = null;
        for (Store store : stores) {
            for (User user : store.getUsers()) {
                if (user.getUsername().equals(username)) {
                    products = store.getProducts();
                    return products;
                }
            }
        }
        return products;
    }

}
