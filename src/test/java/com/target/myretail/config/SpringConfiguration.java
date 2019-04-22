package com.target.myretail.config;

import static org.mockito.Mockito.mock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.target.myretail.repository.ProductPriceRepository;

@Configuration
public class SpringConfiguration {
	@Bean
	public ProductPriceRepository planetDataRepository() {
		return mock(ProductPriceRepository.class); 
	}	
}
