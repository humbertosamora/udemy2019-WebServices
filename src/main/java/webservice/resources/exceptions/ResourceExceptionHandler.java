package webservice.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import webservice.services.exceptions.DatabaseException;
import webservice.services.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) { 
		String errorMessage = "Resource not found.";
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError error = new StandardError(Instant.now(), status.value(), errorMessage, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(error);
	}
	
	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> database(DatabaseException e, HttpServletRequest request) {
		String errorMessage = "Database error.";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError error = new StandardError(Instant.now(), status.value(), errorMessage, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(error);
	}
	
}
