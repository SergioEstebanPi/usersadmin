package com.example.usersadmin.exceptions;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.usersadmin.models.ErrorMessageModel;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class AppExceptionsHandler extends ResponseEntityExceptionHandler {
	public AppExceptionsHandler() {
	}

	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request) {
		String message = ex.getLocalizedMessage();
		System.out.println(ex.toString());
		HttpStatus statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
		if (message == null)
			message = ex.toString();
		if (ex instanceof DataIntegrityViolationException) {
			message = "Email already exists";
			statusCode = HttpStatus.BAD_REQUEST;
		}
		if (ex instanceof EmailInvalidException) {
			message = ex.getMessage();
			statusCode = HttpStatus.BAD_REQUEST;
		}
		ErrorMessageModel errorMessageModel = new ErrorMessageModel(message);
		return new ResponseEntity<>(errorMessageModel, new HttpHeaders(), statusCode);
	}
}
