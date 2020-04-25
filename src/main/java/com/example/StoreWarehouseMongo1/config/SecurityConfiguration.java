/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.StoreWarehouseMongo1.config;

import com.example.StoreWarehouseMongo1.serviceauth.MongoUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author Tasos
 */
@Configuration
@EnableConfigurationProperties
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    MongoUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers("/api/product/**").hasAnyRole("ADMIN")
                .antMatchers("/api/store/**").hasAnyRole("ADMIN")
                .antMatchers("/api/category/**").hasAnyRole("ADMIN")
                .antMatchers("/api/producer/**").hasAnyRole("ADMIN")
                .antMatchers("/api/user/**").hasAnyRole("ADMIN")
                .antMatchers("/health").hasAnyRole("ADMIN", "USER")
                .antMatchers("/info").hasAnyRole("ADMIN", "USER")
                .anyRequest().authenticated()
                .and().httpBasic()
                .and().sessionManagement().disable();
//                .csrf().disable()
//                .cors()
//                .and()
//                .authorizeRequests()
//                .antMatchers("/product/api/**").hasAnyRole("ADMIN")
//                .antMatchers("/store/api/**").hasAnyRole("ADMIN")
//                .antMatchers("/user/api/**").hasAnyRole("ADMIN")
//                .antMatchers("/stock/api/**").hasAnyRole("ADMIN", "USER")
//                .antMatchers("/pseudo/api/get/pseudoProducts/{address}").hasAnyRole("ADMIN", "USER")
//                .antMatchers("/health").permitAll()
//                .antMatchers("/info").permitAll()
//                .anyRequest().authenticated()
//                .and().httpBasic()
//                .and().sessionManagement().disable();
    }

    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService);
    }
}
