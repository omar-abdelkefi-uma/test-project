package io.glide.boot.exception;

import org.hibernate.TypeMismatchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;


@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetails> resourceNotFoundException(ResourceNotFoundException ex) {
		ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ResourceNotSavedException.class)
	public ResponseEntity<ErrorDetails> handleException(ResourceNotSavedException e) {
		ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_ACCEPTABLE, e.getLocalizedMessage());
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_ACCEPTABLE);
	}


	@ExceptionHandler(TypeMismatchException.class)
	public ResponseEntity<ErrorDetails> handleTypeMismatchException(TypeMismatchException e) {
		ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST, "Invalid value '${e.value}'", e);
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(WebExchangeBindException.class)
	public ResponseEntity<ErrorDetails> handleWebExchangeBindException(WebExchangeBindException e) {
		ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST, "Invalid value(s)");
		errorDetails.addValidationErrors(e.getBindingResult().getFieldErrors());
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

}
