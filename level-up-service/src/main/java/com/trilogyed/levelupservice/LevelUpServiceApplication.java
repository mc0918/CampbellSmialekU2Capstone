package com.trilogyed.levelupservice;

import com.trilogyed.levelupservice.model.LevelUp;
import com.trilogyed.levelupservice.repository.LevelUpRepository;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

@SpringBootApplication
@EnableDiscoveryClient
public class LevelUpServiceApplication {
	private LevelUpRepository repo;

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
		SpringApplication.run(LevelUpServiceApplication.class, args);
	}

	@RequestMapping(value = "/levelup/customer/{id}", method = RequestMethod.GET)
	public LevelUp getLevelUpPointsByCustomerId(Integer id) {
		return repo.findByCustomerId(id);
	}
}
