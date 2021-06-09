package io.glide.boot.exception;

import org.hibernate.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;


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
//		Map<String, String> errorResponse = new HashMap<>();
//		errorResponse.put("message", e.getLocalizedMessage());
//		errorResponse.put("status", HttpStatus.BAD_REQUEST.toString());

		ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_ACCEPTABLE, e.getLocalizedMessage());
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_ACCEPTABLE);
	}

	@ResponseStatus(
			value = HttpStatus.BAD_REQUEST,
			reason = "Received Invalid Input Parameters")
	@ExceptionHandler(TypeMismatchException.class)
	public void handleException(TypeMismatchException e) {
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid value '${e.value}'", e);
	}

	/*@ExceptionHandler({
			MethodArgumentTypeMismatchException.class,
			TypeMismatchException.class
	})
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status) {
		List<String> details = new ArrayList<>();
		for(ObjectError error : ex.getBindingResult().getAllErrors()) {
			details.add(error.getDefaultMessage());
		}
		ErrorDetails error = new ErrorDetails(HttpStatus.BAD_REQUEST, "Validation Failed \n" +details);
		return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
	}*/

}
