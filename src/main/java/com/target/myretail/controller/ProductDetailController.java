package com.target.myretail.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.target.myretail.exception.BadRequestException;
import com.target.myretail.model.ProductDetails;
import com.target.myretail.service.ProductDetailService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import rx.Observable;

@RestController
@RequestMapping("/myretail")
@Api(tags = "Product Detail Resource")
public class ProductDetailController {

	@Autowired
	private ProductDetailService productDetailService;

	@ApiOperation(value = "Display Product Details")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Product Details. Ex ProductID.13860428"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping(value = "/products/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public DeferredResult<ProductDetails> getProductDetails(@RequestHeader("X-CLIENT-ID") String clientId,
			@PathVariable(value = "id") Long productId) {
		validateRequest(clientId, productId, null);
		Observable<ProductDetails> o = productDetailService.getProductDetails(productId);
		DeferredResult<ProductDetails> deffered = new DeferredResult<>();
		o.subscribe(response -> deffered.setResult(response), e -> deffered.setErrorResult(e));
		return deffered;
	}

	@ApiOperation(value = "Update Product Details")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Updated Product Details. Ex ProductID.13860428"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@PutMapping(value = "/product/{id}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public DeferredResult<ProductDetails> updateProductDetails(@RequestHeader("X-CLIENT-ID") String clientId,
			@PathVariable(value = "id") Long productId, @Valid @RequestBody ProductDetails productDetails) {
		validateRequest(clientId, productId, productDetails);
		Observable<ProductDetails> o = productDetailService.updateProductPrice(productId, productDetails);
		DeferredResult<ProductDetails> deffered = new DeferredResult<>();
		o.subscribe(response -> deffered.setResult(response), e -> deffered.setErrorResult(e));
		return deffered;
	}

	private void validateRequest(String clientId, Long productId, ProductDetails productDetails) {
		if (!("TARGET").equals(clientId)) {
			throw new BadRequestException("Invalid Client ID.");
		} else if (productId != null && productId.toString().length() > 10) {
			throw new BadRequestException("Product Id in URI is too long.");
		} else if (productId != null && productDetails != null && !productId.equals(productDetails.getProductId())) {
			throw new BadRequestException("Bad Request: ProductId in URI and body doesn't match.");
		}
	}

}
