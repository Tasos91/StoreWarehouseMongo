package com.example.StoreWarehouseMongo1.repositories;

import com.example.StoreWarehouseMongo1.model.Store;
import com.example.StoreWarehouseMongo1.model.User;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
/**
 *
 * @author Tasos
 */
public interface UserRepository extends MongoRepository<User, String> {

    User findByEmail(String email);
    List<User> findByusername(String address);

}
