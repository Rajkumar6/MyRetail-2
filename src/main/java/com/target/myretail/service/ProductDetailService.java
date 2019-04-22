package com.target.myretail.service;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.target.myretail.circuit.GetProductNameCommand;
import com.target.myretail.circuit.GetProductPriceCommand;
import com.target.myretail.circuit.UpdateProductPriceCommand;
import com.target.myretail.dao.GetProductNameDAO;
import com.target.myretail.dao.GetProductPriceDAO;
import com.target.myretail.dao.UpdateProductPriceDAO;
import com.target.myretail.model.ProductDetails;
import com.target.myretail.model.ProductPrice;

import rx.Observable;

@Service
public class ProductDetailService {

	private final org.slf4j.Logger logger = LoggerFactory.getLogger(ProductDetailService.class); 
	
	@Autowired
	public GetProductNameDAO productNameDAO;

	@Autowired
	public GetProductPriceDAO productPriceDAO;

	@Autowired
	public UpdateProductPriceDAO updateProductPriceDAO;

	public Observable<ProductDetails> getProductDetails(Long productId) {
 
		ProductDetails productDetails = new ProductDetails();
		return Observable
				.zip(new GetProductNameCommand(productId, productNameDAO).observe().onErrorReturn(exception -> {
					logger.error("Error during get product name API {}", exception.getMessage()); 
					productDetails.addError("404", "Product Name not available");
					return null;
				}), new GetProductPriceCommand(productId, productPriceDAO).observe().onErrorReturn(exception -> {
					logger.error("Error during price details lookup from DB {}", exception.getMessage());
					return null;
				}), ((productName, priceDetails) -> getConsolidatedProductDetails(productId, productName, priceDetails,
						productDetails)));
	}

	public Observable<ProductDetails> updateProductPrice(Long productId, ProductDetails productDetails) {

		return new GetProductPriceCommand(productId, productPriceDAO).observe().onErrorReturn(exception -> {
			logger.error("Error during price details lookup from DB {}", exception.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Hystrix error occured during Product lookup");
		}).flatMap(availablePrice -> {
			if (availablePrice != null) {
				ProductPrice updatedPrice = productDetails.getProductPrice();
				updatedPrice.setId(productId);
				return new UpdateProductPriceCommand(updatedPrice, updateProductPriceDAO).observe()
						.onErrorReturn(exception -> {
							logger.error("Error during update Price Details to DB {}", exception.getMessage());
							throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Hystrix error occured during Price Update");
						});
			} else {
				logger.error("Error Price details not available in DB");
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Available");
			}
		}).map(newProductPrice -> {
			productDetails.setProductPrice(newProductPrice);
			return productDetails; 
		});
	}

	public ProductDetails getConsolidatedProductDetails(Long productId, String productName, ProductPrice priceDetails,
			ProductDetails productDetails) {
		if(productName ==null && priceDetails == null) {			
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Available");
		}
		productDetails.setProductId(productId);
		if (priceDetails != null) {
			productDetails.setProductPrice(priceDetails);
		} else {
			productDetails.addError("404", "Product Price not available");
		}
		if (productName != null) {
			productDetails.setProductName(productName);
		}		 
		return productDetails;
	}



}
