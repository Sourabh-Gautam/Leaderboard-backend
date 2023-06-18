package com.epam.leaderboard.exception;

/**
 * This class represents a custom exception for the case where a profile is not
 * found. It extends the `Exception` class and provides multiple constructors to
 * handle different scenarios.
 * 
 * @author Sourabh_Gautam
 */
public class EntityNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new `ProfileNotFoundException` with the specified detail
	 * message.
	 * 
	 * @param message The detail message
	 */
	public EntityNotFoundException(String message) {
		super(message);
	}

}
