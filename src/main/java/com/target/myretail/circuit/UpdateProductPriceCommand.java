package com.target.myretail.circuit;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.target.myretail.dao.UpdateProductPriceDAO;
import com.target.myretail.model.ProductPrice;

public class UpdateProductPriceCommand extends HystrixCommand<ProductPrice>{	

	private final UpdateProductPriceDAO updateProductPriceDAO;
	
	private final ProductPrice productPrice;

    public UpdateProductPriceCommand(ProductPrice productPrice, UpdateProductPriceDAO updateProductPriceDAO) {
        super(HystrixCommandGroupKey.Factory.asKey("UpdateProductPriceCommand"));
        this.productPrice = productPrice;
        this.updateProductPriceDAO=updateProductPriceDAO;
    }

    @Override
    protected ProductPrice run() throws Exception {  
        return updateProductPriceDAO.updateProductPrice(productPrice); 
    }
    
   
  }
