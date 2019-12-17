package com.example.StoreWarehouseMongo1.repositories;

import com.example.StoreWarehouseMongo1.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
/**
 *
 * @author Tasos
 */
public interface UserRepository extends MongoRepository<User, String> {

    User findByEmail(String email);

}
