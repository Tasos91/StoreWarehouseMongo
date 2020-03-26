package com.example.StoreWarehouseMongo1.controllers;

import com.example.StoreWarehouseMongo1.model.Product;
import com.example.StoreWarehouseMongo1.model.Stock;
import com.example.StoreWarehouseMongo1.model.Store;
import com.example.StoreWarehouseMongo1.model.User;
import com.example.StoreWarehouseMongo1.repositories.StoreRepository;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Tasos
 */
@RestController
@RequestMapping("/api/store")
@CrossOrigin(origins = "*")
public class StoreControllerCRUD {

    @Autowired
    private StoreRepository storerepository;

    @GetMapping
    public List<Store> getAll() {
        return storerepository.findAll();
    }

    @RequestMapping(value = "/show/{address}", method = GET)
    public Store getStore(@PathVariable("address") String address) {
        return showSpecificStore(address);
    }

    @GetMapping(value = "/specific")
    public ResponseEntity<?> getStoreByUser(@RequestBody User user) {
        List<Store> stores = storerepository.findAll();
        for (Store store : stores) {
            List<User> users = store.getUsers();
            for (User user1 : users) {
                if (user1.getUsername().equals(user.getUsername())) {
                    return new ResponseEntity<Store>(store, HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity(new CustomErrorType("There is not user in this store",
                HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
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
    public void deleteStoreController(@PathVariable("address") String address) {
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

    @DeleteMapping(value = "/deleteAll/{address}")
    public void deleteProducts(@PathVariable("address") String address) {
        Store store = storerepository.findByaddress(address).get(0);
        store.getStock().clear();
        storerepository.save(store);
    }

    @RequestMapping(value = "/pages/{address}", method = GET)
    public ResponseEntity<?> getPages(@PathVariable("address") String address) {
        Store store = new Store();
        try {
            store = storerepository.findByaddress(address).get(0);
        } catch (Exception e) {
            return new ResponseEntity(new CustomErrorType(e.getMessage(), HttpStatus.NOT_FOUND.value()),
                    HttpStatus.NOT_FOUND);
        }
        List<Stock> stock = store.getStock();
        int size = stock.size();
        int pages = 0;
        int j = 8;
        for (int i = 0; i <= size; i++) {
            if (i >= j && i <= j) {
                j = 8 + j;
                pages++;
            }
        }
        int result = j - size;
        if (result < 8) {
            pages++;
        }
        return new ResponseEntity<>(pages, HttpStatus.OK);
    }

}
