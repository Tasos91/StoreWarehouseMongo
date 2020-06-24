package com.example.StoreWarehouseMongo1.serviceauth;

import com.example.StoreWarehouseMongo1.model.User;
import com.example.StoreWarehouseMongo1.repositories.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class MongoUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).get(0);
        UserBuilder builder = null;
        if (user != null) {
         //   if (user.isEnabled()) {
                builder = org.springframework.security.core.userdetails.User.withUsername(username);
                builder.roles(user.getRoles());
                builder.password(user.getPassword());
           // }
        } else {
            throw new UsernameNotFoundException("User not found.");
        }

        return builder.build();
    }

}
