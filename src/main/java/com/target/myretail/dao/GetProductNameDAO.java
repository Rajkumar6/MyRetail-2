package com.target.myretail.dao;

import java.io.IOException;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class GetProductNameDAO {
	
	private final org.slf4j.Logger logger = LoggerFactory.getLogger(GetProductNameDAO.class); 

	@Autowired
	private RestTemplateBuilder builder;
	
	@Autowired
	private Environment env;
	
	public String getProductName(Long productId){		 
		String url = env.getProperty("target.urlprefix") + productId + env.getProperty("target.urlsuffix"); 
		ResponseEntity<String> response = builder.build().getForEntity(url, String.class);
		return getProductNameFrmJson(response.getBody());
	}
		
	public String getProductNameFrmJson(String jsonString) {
		
		logger.info("GetProductNameDAO::getProductName Start");		
		ObjectMapper mapper = new ObjectMapper();
		String productName = null;
		JsonNode root = null;		
		try {
			
			if (jsonString != null) {
				root = mapper.readTree(jsonString);
				String[] nodesArray = { "product", "item", "product_description", "title" };
				for (String node : nodesArray) {
					if (root != null) {
						root = root.findValue(node);
					} else {
						break;
					}
				}
				if (root != null) {
					productName = root.asText(); 
				}
			}
		} catch (IOException exception) {
			logger.error("Error while parsing the response {}", exception.getMessage());
		}
		
		logger.info("GetProductNameDAO::getProductNameFromApi::productName : {}" , productName);
		return productName;
	}
	
		
}
