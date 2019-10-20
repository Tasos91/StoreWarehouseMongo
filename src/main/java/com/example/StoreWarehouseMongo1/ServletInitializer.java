package com.example.StoreWarehouseMongo1;

import com.springauth.springsecurityauth.domain.Role;
import com.springauth.springsecurityauth.domain.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(StoreWarehouseMongo1Application.class);
	}
        

}
