package com.springauth.springsecurityauth.domain.repository;

import com.springauth.springsecurityauth.domain.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author Tasos
 */
public interface RoleRepository extends MongoRepository<Role, String> {

    Role findByRole(String role);
}
