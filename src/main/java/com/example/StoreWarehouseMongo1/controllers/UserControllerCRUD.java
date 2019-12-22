package com.example.StoreWarehouseMongo1.controllers;

import com.example.StoreWarehouseMongo1.model.Product;
import com.example.StoreWarehouseMongo1.model.Store;
import com.example.StoreWarehouseMongo1.model.User;
import com.example.StoreWarehouseMongo1.repositories.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Tasos
 */
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserControllerCRUD {

    @Autowired
    private UserRepository userrepository;

    @RequestMapping(value = "/show/{username}", method = POST)
    public User showUserController(@PathVariable("username") String username) {
        return showSpecificUser(username);
    }

    @RequestMapping(value = "/createUser/{address}", method = POST)
    public User createUserController(@RequestBody User user, @PathVariable("address") String address) {
        return createUser(address, user);
    }

    @RequestMapping(value = "/update/{usernameOld}", method = POST)//update
    public void updateUserController(@RequestBody User updateUserFromUi, @PathVariable("usernameOld") String usernameOld) {
        userrepository.save(updateUser(updateUserFromUi, usernameOld));
    }

    @RequestMapping(value = "/delete/{address}", method = POST)
    public void deleteUserController(@PathVariable("username") String username) {
        userrepository.delete(deleteUser(username));
    }

    public User deleteUser(String username) {
        User userForDelete = null;
        try {
            List<User> users = userrepository.findByusername(username);
            userForDelete = users.get(0);
        } catch (Exception e) {
        }
        return userForDelete;
    }

    public User showSpecificUser(String username) {
        User user = null;
        try {
            List<User> users = userrepository.findByusername(username);
            user = users.get(0);
        } catch (Exception e) {
        }
        return user;
    }

    public User updateUser(User updatedUserFromUi, String username) { //tha erxetai oloklhro to json apo to ui
        List<User> users = userrepository.findByusername(username);  //to opoio tha einai oi kainourgies times gia ta pedia tou user
        User userToBeUpdated = users.get(0);                         //kai epishs tha erxetai kai to pallio username!   
        userToBeUpdated.setUsername(updatedUserFromUi.getUsername());
        userToBeUpdated.setFullname(updatedUserFromUi.getFullname());
        userToBeUpdated.setPassword(updatedUserFromUi.getPassword());
        userToBeUpdated.setEmail(updatedUserFromUi.getEmail());
        userToBeUpdated.setEnabled(updatedUserFromUi.isEnabled());
        return userToBeUpdated;
    }

    public User createUser(String address, User user) {
        Store store = new Store();
        List<User> users = new ArrayList<User>();
        List<Product> products = new ArrayList<Product>();
        store.setAddress(address);
        store.setUsers(users);
        store.setProducts(products);
        user.setStore(store);
        userrepository.save(user);
        return user;
    }

}
