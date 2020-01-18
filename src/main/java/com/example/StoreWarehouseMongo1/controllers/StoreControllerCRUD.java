package com.example.StoreWarehouseMongo1.controllers;

import com.example.StoreWarehouseMongo1.model.Product;
import com.example.StoreWarehouseMongo1.model.Stock;
import com.example.StoreWarehouseMongo1.model.Store;
import com.example.StoreWarehouseMongo1.model.User;
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
@RequestMapping("/store")
@CrossOrigin(origins = "*")
public class StoreControllerCRUD {

    @Autowired
    private StoreRepository storerepository;

    @RequestMapping(value = "/show/{address}", method = POST)
    public Store showStoreController(@PathVariable("address") String address) {
        return showSpecificStore(address);
    }

    @RequestMapping(value = "/add/{address}", method = POST)
    public void createStoreController(@PathVariable("address") String address) {
        storerepository.save(createStore(address));
    }

    public Store createStore(String address) {
        List<User> users = new ArrayList<User>();
        List<Stock> stock = new ArrayList<Stock>();
        return new Store(address, users, stock);
    }

    @RequestMapping(value = "/update/{addressOld}/{addressNew}", method = POST)
    public void updateStoreController(@PathVariable("addressOld") String addressOld,
            @PathVariable("addressNew") String addressNew) {
        storerepository.save(updateStore(addressOld, addressNew));
    }

    @RequestMapping(value = "/delete/{address}", method = POST)
    public void updateStoreController(@PathVariable("address") String address) {
        storerepository.delete(deleteStore(address));
    }

    public Store updateStore(String addressOld, String addressNew) {
        Store updatedStore = null;
        try {
            List<Store> stores = storerepository.findByaddress(addressOld);
            updatedStore = stores.get(0);
            updatedStore.setAddress(addressNew);
        } catch (Exception e) {
        }
        return updatedStore;
    }

    public Store deleteStore(String address) {
        Store storeForDelete = null;
        try {
            List<Store> stores = storerepository.findByaddress(address);
            storeForDelete = stores.get(0);
        } catch (Exception e) {
        }
        return storeForDelete;
    }

    public Store showSpecificStore(String address) {
        Store store = null;
        try {
            List<Store> stores = storerepository.findByaddress(address);
            store = stores.get(0);
        } catch (Exception e) {
        }
        return store;
    }

}
