package com.target.myretail.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(Include.NON_NULL)
public class ProductDetails {
	
    @NotNull
	private Long productId;        
	private String productName;
	@NotNull
    @Valid
	private ProductPrice productPrice;	
	@ApiModelProperty(hidden=true)
    private List<Message> errors;
	
	public ProductDetails() { 
		
	}
	public ProductDetails(@NotNull Long productId, String productName, @NotNull @Valid ProductPrice productPrice) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.productPrice = productPrice;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public ProductPrice getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(ProductPrice productPrice) {
		this.productPrice = productPrice;
	}
	public List<Message> getErrors() {
		return errors;
	}
	public void setErrors(List<Message> errors) {
		this.errors = errors;
	}
	
	public void addError(Message message) {
        if (errors == null) {
            errors = new ArrayList<>();
        }
        this.errors.add(message);
    }

    public void addError(String code, String description) {
        addError(new Message(code, description));
    }      
}
