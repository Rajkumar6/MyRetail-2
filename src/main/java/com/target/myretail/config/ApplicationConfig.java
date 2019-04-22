package com.target.myretail.config;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.target.myretail.model.ProductPrice;
import com.target.myretail.repository.ProductPriceRepository;

@Configuration
public class ApplicationConfig {
	@Bean
	CommandLineRunner commandLineRunner(ProductPriceRepository productPriceRepository) {
		return strings -> {
			productPriceRepository.deleteAll();
			productPriceRepository.save(new ProductPrice(13860428L, new BigDecimal("299.99"), "USD"));
			productPriceRepository.save(new ProductPrice(15117729L, new BigDecimal("399.99"), "USD"));
			productPriceRepository.save(new ProductPrice(16483589L, new BigDecimal("99.99"), "USD"));
			productPriceRepository.save(new ProductPrice(16696652L, new BigDecimal("399.99"), "USD"));
			productPriceRepository.save(new ProductPrice(16752456L, new BigDecimal("99.99"), "USD"));
			productPriceRepository.save(new ProductPrice(15643793L, new BigDecimal("99.99"), "USD"));		

		};
	}

	@Bean
	public CacheManager cacheManager() {
		return new EhCacheCacheManager(ehCacheCacheManager().getObject());
	}

	@Bean
	public EhCacheManagerFactoryBean ehCacheCacheManager() {
		EhCacheManagerFactoryBean cmfb = new EhCacheManagerFactoryBean();
		cmfb.setConfigLocation(new ClassPathResource("ehcache.xml"));
		cmfb.setShared(true);
		return cmfb;
	}

	@Bean
	public RestTemplateBuilder restTemplate() {
		return new RestTemplateBuilder();
	}
}
