package com.example.StoreWarehouseMongo1.controllers;

import com.example.StoreWarehouseMongo1.helpers.Filter;
import com.example.StoreWarehouseMongo1.helpers.Pagination;
import com.example.StoreWarehouseMongo1.model.Product;
import com.example.StoreWarehouseMongo1.model.Store;
import com.example.StoreWarehouseMongo1.model.Token;
import com.example.StoreWarehouseMongo1.model.User;
import com.example.StoreWarehouseMongo1.repositories.ProductRepository;
import com.example.StoreWarehouseMongo1.repositories.StoreRepository;
import com.example.StoreWarehouseMongo1.repositories.TokenRepository;
import com.example.StoreWarehouseMongo1.repositories.UserRepository;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
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
    private TokenRepository tokenRepository;

    @Autowired
    private ProductRepository productrepository;

    @Autowired
    private Pagination pagination;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/add", method = GET)
    public void addStoreAToMongoTest() {
        List<Product> products1 = new ArrayList<Product>();
        Product product1 = new Product("123456789", "BR122", "100", "120", "200",
                "test", "test2", "test3", "test3", "test3", 10, 5, 5, 5, "kifisia", "RING");
        Product product2 = new Product("987654321", "BR999", "200", "220", "300",
                "test22", "test222", "test322", "test3333", "test3222", 5, 66, 888, 8, "Golden Hall", "BRACELLETE");
        products1.add(product1);
        products1.add(product2);
        productrepository.save(product1);
        productrepository.save(product2);
        storerepository.save(addStore(products1));

    }

    public Store addStore(List<Product> products1) {

        Store store = new Store();
        User user1 = new User("tasos","aisera321", "tasos.sot@hotmail.com", "Sotiriou", true, store);
        User user2 = new User("sakhs","aisera100", "anastasios.sotiriou@aisera.com", "Kalepanagos", true, store);
        List<User> users1 = new ArrayList<User>();
        users1.add(user1);
        addUser(user1);
        addUser(user2);
        users1.add(user2);
        Store store1 = new Store("kifisia", "kokkinogenhs",
                "aisera123", true, users1, products1);
        return store1;
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    @RequestMapping(value = "/showSpecificProducts/{username}", method = POST)
    public List<Product> findUserInsideStore(@PathVariable("username") String username) {
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
