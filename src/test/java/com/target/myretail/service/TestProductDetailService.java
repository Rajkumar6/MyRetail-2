package com.target.myretail.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.server.ResponseStatusException;

import com.target.myretail.config.SpringConfiguration;
import com.target.myretail.model.ProductDetails;
import com.target.myretail.model.ProductPrice;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfiguration.class)
public class TestProductDetailService {
	
	public static final String PRODUCT_NAME="The Big Lebowski (Blu-ray)";
	
	@InjectMocks
	public ProductDetailService productDetailService;
	
	@Rule
    public ExpectedException exceptionRule = ExpectedException.none();
	
	@Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test(expected = ResponseStatusException.class)
    public void getConsolidatedProductDetailsTest() {
		
		ProductPrice productPrice=new ProductPrice(13860428L, BigDecimal.valueOf(199.99),"USD");
		
		ProductDetails productDetails=new ProductDetails(13860428L,PRODUCT_NAME,
				productPrice); 
		productDetailService.getConsolidatedProductDetails(13860428L,null,null,productDetails);
	}
	
	@Test
    public void getConsolidatedProductDetailsInvalidPriceTest() {
		
		ProductDetails productDetails=new ProductDetails(13860428L,PRODUCT_NAME,null); 
		ProductDetails newproductDetails = productDetailService.getConsolidatedProductDetails(13860428L,PRODUCT_NAME,null,productDetails);
		assertEquals(PRODUCT_NAME,newproductDetails.getProductName());
		assertNull(newproductDetails.getProductPrice());
		assertEquals("Product Price not available",productDetails.getErrors().stream().findFirst().get().getDescription());

	}
	
	
	@Test
    public void getConsolidatedProductDetailsValidPriceTest() {
		
		ProductPrice productPrice=new ProductPrice(13860428L, BigDecimal.valueOf(199.99),"USD");
		
		ProductDetails productDetails=new ProductDetails(13860428L,PRODUCT_NAME,
				productPrice); 
		ProductDetails newproductDetails = productDetailService.getConsolidatedProductDetails(13860428L,PRODUCT_NAME,productPrice,productDetails);
		assertEquals(BigDecimal.valueOf(199.99),newproductDetails.getProductPrice().getPrice());
		
	}
}
