package com.epam.leaderboard.exception;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.epam.leaderboard.dto.ErrorDTO;

/**
 * This class represents a global exception handler for the application. It uses
 * the `@ControllerAdvice` annotation to indicate that this class should be used
 * to handle exceptions globally.
 * 
 * @author Sourabh_Gautam
 */

@ControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * This method handles exceptions of type `ProfileNotFoundException` and returns
	 * a `ResponseEntity` with an error message.
	 * 
	 * @param exception  The exception to be handled
	 * @param webRequest The web request that triggered the exception
	 * @return A `ResponseEntity` with the error message and a `404 NOT FOUND`
	 *         status code
	 */

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ErrorDTO> profileNotFoundException(Throwable exception, WebRequest webRequest) {

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(this.errorTemplate(exception));
	}

	/**
	 * This method handles exceptions of type `WrongProfileInputException` and
	 * returns a `ResponseEntity` with an error message.
	 * 
	 * @param exception  The exception to be handled
	 * @param webRequest The web request that triggered the exception
	 * @return A `ResponseEntity` with the error message and a `404 NOT FOUND`
	 *         status code
	 */

	@ExceptionHandler(WrongProfileInputException.class)
	public ResponseEntity<ErrorDTO> wrongProfileInputException(Throwable exception, WebRequest webRequest) {
		 
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(this.errorTemplate(exception));
	}

	/**
	 * Exception handler method for handling MethodArgumentNotValidException.
	 *
	 * @param exception The MethodArgumentNotValidException that was thrown
	 * @return A ResponseEntity with an ErrorDTO and a Bad Request status code
	 */

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorDTO> handleValidationExceptions(MethodArgumentNotValidException exception) {
		List<String> validationErrors = exception.getBindingResult().getFieldErrors().stream()
				.map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage()).toList();
		var error = new ErrorDTO("Data validation failed", validationErrors.toString(),
				Timestamp.valueOf(LocalDateTime.now()).toString());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
   
	public ErrorDTO errorTemplate(Throwable exception)   {
		var errorDTO = new ErrorDTO();
		errorDTO.setMessage("Data validation failed");
		errorDTO.setDescription(exception.getLocalizedMessage());
		errorDTO.setTimestamp(Timestamp.valueOf(LocalDateTime.now()).toString());
		return errorDTO;
	}
}
