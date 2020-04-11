package com.example.StoreWarehouseMongo1.repositories;

import com.example.StoreWarehouseMongo1.model.Store;
import com.example.StoreWarehouseMongo1.model.User;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
/**
 *
 * @author Tasos
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {

    List<User> findByEmail(String email);
    List<User> findByUsername(String username);

}
