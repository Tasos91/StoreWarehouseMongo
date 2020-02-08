package com.example.StoreWarehouseMongo1.controllers;

import com.example.StoreWarehouseMongo1.model.Store;
import com.example.StoreWarehouseMongo1.model.User;
import com.example.StoreWarehouseMongo1.repositories.StoreRepository;
import com.example.StoreWarehouseMongo1.repositories.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/user/api")
@CrossOrigin(origins = "*")
public class UserControllerCRUD {

    @Autowired
    private UserRepository userrepository;

    @Autowired
    private StoreRepository storeRepository;

    @RequestMapping(value = "/show/{username}", method = POST)
    public User showUserController(@PathVariable("username") String username) {
        return showSpecificUser(username);
    }

    @GetMapping
    public List<User> getUsers() {
        return userrepository.findAll();
    }

    @RequestMapping(value = "/createUser/{address}", method = POST)
    public User createUserController(@RequestBody User user, @PathVariable("address") String address) {
        return createUser(address, user);
    }

    @RequestMapping(value = "/update/{usernameOld}", method = POST)//update
    public void updateUserController(@RequestBody User updateUserFromUi, @PathVariable("usernameOld") String usernameOld) {
        userrepository.save(updateUser(updateUserFromUi, usernameOld));
    }

    @DeleteMapping(value = "/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable("username") String username) {
        User user = new User();
        user = null;
        try {
            user = userrepository.findByusername(username).get(0);
        } catch (Exception userNotFound) {
        }
        if (user == null) {
            return new ResponseEntity(new CustomErrorType("Unable to delete. User with username " + username + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        userrepository.delete(user);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
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
        List<Store> stores = storeRepository.findByaddress(address);
        Store store = stores.get(0);
        List<User> users = store.getUsers();
        users.add(user);
        store.setUsers(users);
        storeRepository.save(store);
        userrepository.save(user);
        return user;
    }

}
