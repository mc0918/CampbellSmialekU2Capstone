package com.trilogyed.retailapi;

import com.trilogyed.retailapi.model.LevelUp;
import com.trilogyed.retailapi.service.ServiceLayer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestTemplate;

@EnableCircuitBreaker
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class RetailApiApplication {
	@Autowired
	private ServiceLayer serviceLayer;

	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
		return rabbitTemplate;
	}

	@Bean
	public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	public static void main(String[] args) {
		SpringApplication.run(RetailApiApplication.class, args);
	}

	@Bean
	public RestTemplate rest(RestTemplateBuilder builder){
		return builder.build();
	}

	@RequestMapping(value = "/levelup/customer/{id}", method = RequestMethod.GET)
	public LevelUp getLevelUpPointsByCustomerId(@PathVariable int id) {
		return serviceLayer.getLevelUpPointsByCustomerId(id);
	}
}
