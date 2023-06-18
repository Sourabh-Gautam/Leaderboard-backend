package com.epam.leaderboard.exception;

/**
 * The class represents a custom runtime exception for invalid profile input.
 * 
 * @author Sourabh_Gautam
 *
 */

public class WrongProfileInputException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new `WrongProfileInputException` with the specified detail
	 * message.
	 * 
	 * @param message The detail message
	 */

	public WrongProfileInputException(String message) {
		super(message);
	}

}
