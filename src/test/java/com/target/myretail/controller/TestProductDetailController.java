package com.target.myretail.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.target.myretail.model.ProductDetails;
import com.target.myretail.model.ProductPrice;
import com.target.myretail.service.ProductDetailService;

import rx.Observable;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductDetailController.class)
public class TestProductDetailController {

	public static final String CLIENT_ID_HEADER = "X-CLIENT-ID";

	public static final String CLIENT_ID_HEADER_VALUE = "TARGET";

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProductDetailService productDetailService;

	@Test
	public void testGetProductDetails() throws Exception {

		mockService(13860428L);
		mockMvc.perform(get("/myretail/products/13860428").contentType(MediaType.APPLICATION_JSON)
				.header(CLIENT_ID_HEADER, CLIENT_ID_HEADER_VALUE)).andExpect(status().isOk());
	}

	@Test
	public void testGetProductDetailsInvalidProduct() throws Exception {
		mockService(1386042811L);
		mockMvc.perform(get("/myretail/products/13860428111").contentType(MediaType.APPLICATION_JSON)
				.header(CLIENT_ID_HEADER, CLIENT_ID_HEADER_VALUE)).andExpect(status().isBadRequest());
	}

	@Test
	public void testGetProductDetailsInvalidClientID() throws Exception {
		mockService(13860428L);
		mockMvc.perform(get("/myretail/products/13860428").contentType(MediaType.APPLICATION_JSON)
				.header(CLIENT_ID_HEADER, "TARGETTest")).andExpect(status().isBadRequest());
	}

	@Test
	public void testUpdateProductDetails() throws Exception {

		ProductDetails productDetails = new ProductDetails(13860428L, "The Big Lebowski (Blu-ray)",
				new ProductPrice(13860428L, BigDecimal.valueOf(199.99), "USD"));

		when(productDetailService.updateProductPrice(Mockito.anyLong(), Mockito.any(ProductDetails.class)))
				.thenReturn(Observable.just(productDetails));

		mockMvc.perform(put("/myretail/product/13860428").contentType(MediaType.APPLICATION_JSON)
				.content(getProductInJson(productDetails.getProductId()))
				.header(CLIENT_ID_HEADER, CLIENT_ID_HEADER_VALUE)).andExpect(status().isOk());

	}

	private void mockService(Long productId) {
		ProductDetails productDetails = new ProductDetails(productId, "The Big Lebowski (Blu-ray)",
				new ProductPrice(productId, BigDecimal.valueOf(199.99), "USD"));

		when(productDetailService.getProductDetails(productId)).thenReturn(Observable.just(productDetails));
	}
	
	private String getProductInJson(long id) {

		return "{\r\n" + "    \"productId\":" + id + ",\r\n"
				+ "    \"productName\": \"The Big Lebowski (Blu-ray)\",\r\n" + "    \"productPrice\": {\r\n"
				+ "        \"currencyCode\": \"USD\",\r\n" + "        \"price\": 299.99\r\n" + "    }\r\n" + "}";

	}

}