package com.target.myretail.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.target.myretail.model.ProductPrice;
import com.target.myretail.repository.ProductPriceRepository;

@Repository
public class GetProductPriceDAO {
	
	@Autowired
	private ProductPriceRepository productPriceRepository;	 
 
	@Cacheable(value = "productPriceCache", key = "#id")
	public ProductPrice getProductPrice(Long id){
		return productPriceRepository.findById(id).orElse(null); 
	}	
}
