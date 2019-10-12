package com.djamware.springbootmongodbsecurity.repository;

import com.springauth.springsecurityauth.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author Tasos
 */


public interface UserRepository extends MongoRepository<User, String> {

    User findByEmail(String email);

}
