package com.target.myretail.exception;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

import com.target.myretail.model.ProductDetails;

@Component
@ControllerAdvice
public class ProductDetailExceptionHandler{
	
	private final org.slf4j.Logger logger = LoggerFactory.getLogger(ProductDetailExceptionHandler.class);

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public final ResponseEntity<ProductDetails> handleArgTypeMiExceptionHandler(MethodArgumentTypeMismatchException ex){
		logger.error("ProductDetailExceptionHandler::handleArgTypeMiExceptionHandler {}", ex.getMessage());
        ProductDetails productDetails = new ProductDetails();
        productDetails.addError(HttpStatus.BAD_REQUEST.toString(),"Invalid Product Id");
		return new ResponseEntity<>(productDetails,new HttpHeaders(),HttpStatus.BAD_REQUEST); 
	}
	@ExceptionHandler(BadRequestException.class)
	public final ResponseEntity<ProductDetails> handleBadRequestException(BadRequestException ex){
		logger.error("ProductDetailExceptionHandler::handleBadRequestException {}", ex.getMessage());
		ProductDetails productDetails = new ProductDetails();		
        productDetails.addError(HttpStatus.BAD_REQUEST.toString(),ex.getMessage());
		return new ResponseEntity<>(productDetails,new HttpHeaders(),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<ProductDetails> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
		logger.error("ProductDetailExceptionHandler::handleMethodArgumentNotValid {}", ex.getMessage());
		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();
        ProductDetails productDetails = new ProductDetails();
        for (FieldError fieldError : fieldErrors) {
            productDetails.addError(fieldError.getField() , fieldError.getDefaultMessage());
        }
        for (ObjectError objectError : globalErrors) {
            productDetails.addError(objectError.getObjectName() , objectError.getDefaultMessage());
        }      
		return new ResponseEntity<>(productDetails, HttpStatus.BAD_REQUEST);		    
	} 	
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	protected ResponseEntity<ProductDetails> handleMethodArgumentNotReadable(HttpMessageNotReadableException ex) {
		logger.error("ProductDetailExceptionHandler::handleMethodArgumentNotReadable {}", ex.getMessage());
	        ProductDetails productDetails = new ProductDetails();  
		    Throwable mostSpecificCause = ex.getMostSpecificCause();
	       	productDetails.addError( mostSpecificCause.getClass().getName(), mostSpecificCause.getMessage());
	       	return new ResponseEntity<>(productDetails, HttpStatus.BAD_REQUEST);		
	}

	@ExceptionHandler(ResponseStatusException.class)
	public final ResponseEntity<ProductDetails> handleResponseStatusException(ResponseStatusException ex){		
		logger.error("ProductDetailExceptionHandler::handleResponseStatusException {}", ex.getMessage());
		ProductDetails productDetails = new ProductDetails();
        productDetails.addError(ex.getStatus().toString(),ex.getMessage());
		return new ResponseEntity<>(productDetails,new HttpHeaders(),ex.getStatus());
	}
	
	@ExceptionHandler(RuntimeException.class)
	public final ResponseEntity<ProductDetails> handleRuntimeException(RuntimeException ex){
		logger.error("ProductDetailExceptionHandler::handleRuntimeException {}", ex.getMessage());
		ProductDetails productDetails = new ProductDetails();
        productDetails.addError(HttpStatus.INTERNAL_SERVER_ERROR.toString(),ex.getMessage());
		return new ResponseEntity<>(productDetails,new HttpHeaders(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ProductDetails> handleGeneralException(Exception ex){
		logger.error("ProductDetailExceptionHandler::handleGeneralException {}", ex.getMessage());
		ProductDetails productDetails = new ProductDetails();
        productDetails.addError(HttpStatus.INTERNAL_SERVER_ERROR.toString(),ex.getMessage());
		return new ResponseEntity<>(productDetails,new HttpHeaders(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
