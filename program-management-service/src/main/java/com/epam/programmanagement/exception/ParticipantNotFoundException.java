package com.epam.programmanagement.exception;

public class ParticipantNotFoundException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ParticipantNotFoundException(String message)
	{
		super(message);
	}
}
