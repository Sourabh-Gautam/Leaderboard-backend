package com.epam.programmanagement.exception;

public class ProgramNotFoundException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProgramNotFoundException(String message)
	{
		super(message);
	}

}
