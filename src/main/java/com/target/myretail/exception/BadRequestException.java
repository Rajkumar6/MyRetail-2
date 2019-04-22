package com.target.myretail.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException{
	
	private static final long serialVersionUID = -3870438735534544650L;
	public BadRequestException() { 
		
	}
    public BadRequestException(String exception) {
        super(exception);

	}
}
