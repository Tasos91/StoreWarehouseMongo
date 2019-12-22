package com.example.StoreWarehouseMongo1.controllers;

import com.example.StoreWarehouseMongo1.model.Product;
import com.example.StoreWarehouseMongo1.model.Store;
import com.example.StoreWarehouseMongo1.repositories.ProductRepository;
import com.example.StoreWarehouseMongo1.repositories.StoreRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Tasos
 */
@RestController
@RequestMapping("/addProduct")
@CrossOrigin(origins = "*")
public class ProductControllerCRUD {

    @Autowired
    private ProductRepository productrepository;

    @Autowired
    private StoreRepository storerepository;

    @RequestMapping(value = "/add/{address}", method = PUT)
    public void addStoreController(@RequestBody Product product, @PathVariable("address") String addressToStored) {
        List<Store> stores = storerepository.findByaddress(addressToStored);
        Store store = stores.get(0);
        List<Product> listOfProductsToThisStore = store.getProducts(); //if not null..vale try catch gia na mh fame ArrayIndexOutOfBound
        listOfProductsToThisStore.add(product); //prosthetoume to product sth lista twn products tou store ;)
        store.setProducts(listOfProductsToThisStore);
        storerepository.save(store);
        productrepository.save(product);
    }

}
