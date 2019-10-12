package com.springauth.springsecurityauth.domain.repository;

import com.springauth.springsecurityauth.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tasos
 */
public interface UserRepository extends MongoRepository<User, String> {

    User findByEmail(String email);

}
