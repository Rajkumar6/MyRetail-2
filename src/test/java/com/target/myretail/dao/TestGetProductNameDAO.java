package com.target.myretail.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.target.myretail.config.SpringConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfiguration.class)
public class TestGetProductNameDAO { 
			
	@InjectMocks
	private GetProductNameDAO getProductNameDAO;	
		
	@Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
    public void productNameTest() {
		String productName = getProductNameDAO.getProductNameFrmJson(getProductInJson());
   		assertEquals("The Big Lebowski (Blu-ray)", productName);
	}
	
	private String getProductInJson() {
		
	      return "{\r\n" + 
	      		"  \"product\": {  \r\n" + 
	      		"   \"item\": {      \r\n" + 
	      		"      \"product_description\": {\r\n" + 
	      		"        \"title\": \"The Big Lebowski (Blu-ray)\"        \r\n" + 
	      		"      }\r\n" + 
	      		"    }\r\n" + 
	      		"  }\r\n" + 
	      		"}";
	}
	    
}
