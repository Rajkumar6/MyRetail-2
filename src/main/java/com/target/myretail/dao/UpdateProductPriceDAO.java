package com.target.myretail.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Repository;

import com.target.myretail.model.ProductPrice;
import com.target.myretail.repository.ProductPriceRepository;

@Repository
public class UpdateProductPriceDAO {
	
	@Autowired
	private ProductPriceRepository productPriceRepository;	 

	@CachePut(value = "productPriceCache", key = "#productPrice.id")
	public ProductPrice updateProductPrice(ProductPrice productPrice){
		return productPriceRepository.save(productPrice); 
	}
}
