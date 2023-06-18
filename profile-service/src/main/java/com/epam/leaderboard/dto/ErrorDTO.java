package com.epam.leaderboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This is a Data Transfer Object (DTO) class named "ErrorDTO" used to hold
 * information about an error.
 * 
 * The class is annotated with "@Data" from the lombok library which generates
 * the boilerplate code for getters, setters, toString, equals and hashcode
 * methods.
 * 
 * It also has a "@NoArgsConstructor" annotation, which generates a no-argument
 * constructor for the class.
 * 
 * The class has three properties:
 * 
 * message: a string representation of the error message. 
 * description: a string representation of the error description.
 * timestamp: a string representation of the time when the error occurred.
 * 
 * @author Sourabh_Gautam
 *
 */

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDTO {

	private String message;
	private String description;
	private String timestamp;

}
