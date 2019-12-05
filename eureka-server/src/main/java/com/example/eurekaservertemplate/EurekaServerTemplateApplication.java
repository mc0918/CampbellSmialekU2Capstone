package com.example.eurekaservertemplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerTemplateApplication {

	/*
	THIS APP IS GOOD TO GO, JUST RUN IT (or change default port to what you want)
	 */

	public static void main(String[] args) {
		SpringApplication.run(EurekaServerTemplateApplication.class, args);
	}

}
