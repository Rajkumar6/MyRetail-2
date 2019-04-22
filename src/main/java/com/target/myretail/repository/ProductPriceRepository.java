package com.target.myretail.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.target.myretail.model.ProductPrice;
@Repository
public interface ProductPriceRepository extends MongoRepository<ProductPrice, Long> {}