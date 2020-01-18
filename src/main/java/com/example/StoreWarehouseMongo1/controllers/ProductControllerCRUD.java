package com.example.StoreWarehouseMongo1.controllers;

import com.example.StoreWarehouseMongo1.model.Product;
import com.example.StoreWarehouseMongo1.repositories.ProductRepository;
import com.example.StoreWarehouseMongo1.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Tasos
 */
@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "*")
public class ProductControllerCRUD {

    @Autowired
    private ProductRepository productrepository;

    @Autowired
    private StoreRepository storerepository;

    @RequestMapping(value = "/create", method = POST)
    public void addProductController(@RequestBody Product product) {
        productrepository.save(product);
    }

        
        
        //        List<Store> stores = storerepository.findByaddress(addressToStored);
//        Store store = stores.get(0);
//        List<Product> listOfProductsToThisStore = store.getProducts(); //if not null..vale try catch gia na mh fame ArrayIndexOutOfBound
//        productrepository.save(product);
//        String productCode = product.getProductcode();
//        List<Product> products = productrepository.findByproductcode(productCode);
//        listOfProductsToThisStore.add(products.get(0)); //prosthetoume to product sth lista twn products tou store ;)
//        store.setProducts(listOfProductsToThisStore);
//        storerepository.save(store);//update to store me ta products pou exei
}
