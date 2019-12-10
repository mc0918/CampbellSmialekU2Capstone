package com.trilogyed.adminapi;

import com.trilogyed.adminapi.controller.*;
import com.trilogyed.adminapi.model.*;
import com.trilogyed.adminapi.service.ServiceLayer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCaching
//@EnableSwagger2
//@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class AdminApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminApiApplication.class, args);
	}

}
