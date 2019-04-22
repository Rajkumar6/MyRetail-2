package com.target.myretail.model;


import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection="productprice")
public class ProductPrice implements Serializable{	


	private static final long serialVersionUID = -356125091541048933L;
	
	@Id
	@JsonProperty("id")
	@JsonIgnore
	private Long id;
	
	@NotNull
	@Size(min=2, max=3,message="CurrencyCode should have atleast 2 characters and maximum 3 characters")
	private String currencyCode;
	
	@NotNull(message = "Please provide a valid price")
    @DecimalMin("0.00")
	private BigDecimal price;	
	
	public ProductPrice(){ 
	}

	public ProductPrice(Long id, BigDecimal price, String currencyCode) {
		super();
		this.id = id;
		this.price = price;
		this.currencyCode = currencyCode;
	}
	@JsonIgnore
	@JsonProperty(value = "id")
	public Long getId() {
		return id;
	}		
	
	@JsonIgnore
	@JsonProperty(value = "id")
	public void setId(Long id) {
		this.id = id;
	}
	
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	@Override
	public String toString() {
		return "ProductPrice {"
			+ "price=" + price + ","
			+ "currencyCode =" + currencyCode + "}";
	}	
}