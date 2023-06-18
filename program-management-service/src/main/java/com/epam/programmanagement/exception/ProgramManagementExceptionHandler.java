package com.epam.programmanagement.exception;

import java.time.LocalDate;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.epam.programmanagement.model.ErrorResponse;

/**
 * The ProgramManagementExceptionHandler class manages the global exceptions and validations.
 * It handles two exceptions i.e ProgramNotFoundException and NoProgramExistsException.
 * It is annotated with @RestControllerAdvice which is the combination of @ResponseBody and @ControllerAdvice.
 * @author Vahini_Nune
 *
 */


@RestControllerAdvice
public class ProgramManagementExceptionHandler {
/**
 * This method handles ProgramNotFoundException and NoProgramExistsException.
 * @param request
 * @param exception
 * @return
 */
	@ExceptionHandler(value = { ProgramNotFoundException.class ,NoDataExistsException.class,ProgramTemplateNotFound.class,ParticipantNotFoundException.class})
	public ResponseEntity<ErrorResponse> handlerForException(WebRequest request, Exception exception) {
		
		ErrorResponse response = new ErrorResponse();
		response.setError(exception.getMessage());
		response.setStatus(HttpStatus.BAD_REQUEST.name()); 
		response.setTimestamp(LocalDate.now());
		response.setPath(request.getDescription(false));
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);  
	}
	
	/**
	 * This method handles validations for input fields in the request body.
	 * @param exception
	 * @param request
	 * @return
	 */
	@ExceptionHandler(value = { MethodArgumentNotValidException.class })
	protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
			WebRequest request) {
		
		ErrorResponse response = new ErrorResponse();
		response.setError(exception.getBindingResult().getFieldErrors().stream()
				.map(DefaultMessageSourceResolvable::getDefaultMessage)
				.collect(java.util.stream.Collectors.joining(", ")));
		response.setStatus(HttpStatus.BAD_REQUEST.name());
		response.setTimestamp(LocalDate.now());
		response.setPath(request.getDescription(false));
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); 
	}
}
