package com.target.myretail.circuit;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.target.myretail.dao.GetProductPriceDAO;
import com.target.myretail.model.ProductPrice;

public class GetProductPriceCommand extends HystrixCommand<ProductPrice>{	

	private final GetProductPriceDAO productPriceDAO;
	
	private final Long productId;

    public GetProductPriceCommand(Long productId, GetProductPriceDAO productPriceDAO) {
        super(HystrixCommandGroupKey.Factory.asKey("GetProductPriceCommand"));
        this.productId = productId;
        this.productPriceDAO=productPriceDAO; 
    }

    @Override
    protected ProductPrice run() throws Exception {  
        return productPriceDAO.getProductPrice(productId);
    }    
       
  }
