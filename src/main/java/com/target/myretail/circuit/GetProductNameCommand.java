package com.target.myretail.circuit;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.target.myretail.dao.GetProductNameDAO;

public class GetProductNameCommand extends HystrixCommand<String>{	

	private final GetProductNameDAO productNameDAO;
	
	private final Long productId;

    public GetProductNameCommand(Long productId, GetProductNameDAO productNameDAO) {
        super(HystrixCommandGroupKey.Factory.asKey("GetProductNameCommand"));
        this.productId = productId;
        this.productNameDAO=productNameDAO; 
    }

    @Override
    protected String run() throws Exception {  
        return productNameDAO.getProductName(productId);
    }   
    
   
}
