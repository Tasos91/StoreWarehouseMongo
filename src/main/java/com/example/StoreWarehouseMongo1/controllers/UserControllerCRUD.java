package com.example.StoreWarehouseMongo1.controllers;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.example.StoreWarehouseMongo1.Exceptions.ProductNotFoundException;
import com.example.StoreWarehouseMongo1.model.Store;
import com.example.StoreWarehouseMongo1.model.User;
import com.example.StoreWarehouseMongo1.repositories.StoreRepository;
import com.example.StoreWarehouseMongo1.repositories.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 *
 * @author Tasos
 */
@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserControllerCRUD {

    @Autowired
    private UserRepository userrepository;

    @Autowired
    private StoreRepository storeRepository;

    @RequestMapping(value = "/get", method = POST)
    public User get(@RequestParam("username") String username) {
        return getUser(username);
    }

    @GetMapping
    public List<User> getUsers() {
        return userrepository.findAll();
    }

    @PostMapping(value = "/addUserToStore/{address}")
    public ResponseEntity<?> addUserToStore(@RequestBody User user, @PathVariable("address") String address) {
        Store store = new Store();
        try {
            store = storeRepository.findByaddress(address).get(0);
        } catch (Exception e) {
            return new ResponseEntity(new CustomErrorType("This store doesn't exist", HttpStatus.CONFLICT.value()),
                    HttpStatus.CONFLICT);
        }
        List<User> users = store.getUsers();
        if (!users.isEmpty()) {
            for (User user1 : users) {
                String username = user.getUsername();
                String username1 = user1.getUsername();
                if (username.equals(username1)) {
                    return new ResponseEntity(new CustomErrorType("This user already exist in this store", HttpStatus.CONFLICT.value()),
                            HttpStatus.CONFLICT);
                }
            }
        }
        users.add(user);
        store.setUsers(users);
        storeRepository.save(store);
        return new ResponseEntity(new CustomErrorType("The user added into store = " + address, HttpStatus.OK.value()),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/create/{address}", method = POST)
    public User createUserController(@Valid @RequestBody User user, @PathVariable("address") String address) {
        try {
           // return createUser(address, user);
        } catch (Exception e) {
            //return createUser(address, user);
        }
        return new User();
    }

    @PostMapping(value = "/update")//update,reset password
    public void updateUserController(@RequestBody User user
    ) {
        user.setPassword(getHashedPassword(user.getPassword()));
        userrepository.save(user);
    }

    @DeleteMapping(value = "/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable("username") String username
    ) {
        User user = new User();
        user = null;
        try {
            user = userrepository.findByUsername(username).get(0);
        } catch (Exception userNotFound) {
        }
        if (user == null) {
            return new ResponseEntity(new CustomErrorType("Unable to delete. User with username " + username + " not found.", HttpStatus.NOT_FOUND.value()),
                    HttpStatus.NOT_FOUND);
        }
        userrepository.delete(user);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }

    public User getUser(String username) {
        try {
            return userrepository.findByUsername(username).get(0);
        } catch (Exception e) {
            throw new ProductNotFoundException(e.getMessage());
        }
    }

    public User updateUser(User updatedUserFromUi, String username) { //tha erxetai oloklhro to json apo to ui
        List<User> users = userrepository.findByUsername(username);  //to opoio tha einai oi kainourgies times gia ta pedia tou user
        User userToBeUpdated = users.get(0);                         //kai epishs tha erxetai kai to pallio username!   
        userToBeUpdated.setUsername(updatedUserFromUi.getUsername());
        userToBeUpdated.setFullname(updatedUserFromUi.getFullname());
        userToBeUpdated.setPassword(updatedUserFromUi.getPassword());
        userToBeUpdated.setEmail(updatedUserFromUi.getEmail());
        userToBeUpdated.setEnabled(updatedUserFromUi.isEnabled());
        return userToBeUpdated;
    }

    public User createUser(String address, User user) {
        user.setPassword(getHashedPassword(user.getPassword()));
        List<Store> stores = storeRepository.findByaddress(address);
        Store store = stores.get(0);
        List<User> users = store.getUsers();
        users.add(user);
        store.setUsers(users);
        userrepository.save(user);
        storeRepository.save(store);
        return user;
    }

    private String getHashedPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
