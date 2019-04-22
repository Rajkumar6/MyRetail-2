package com.target.myretail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableCircuitBreaker
@EnableHystrixDashboard
@EnableCaching
@ComponentScan({ "com.target.myretail.circuit", "com.target.myretail.controller", "com.target.myretail.dao", "com.target.myretail.model",
	"com.target.myretail.repository", "com.target.myretail.service","com.target.myretail.exception","com.target.myretail.config"})
public class MyretailApplication {
	public static void main(String[] args) {
		SpringApplication.run(MyretailApplication.class, args); 
	}
	
}
