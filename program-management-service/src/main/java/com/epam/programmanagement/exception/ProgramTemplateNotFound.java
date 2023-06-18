package com.epam.programmanagement.exception;

public class ProgramTemplateNotFound extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProgramTemplateNotFound(String message)
	{
		super(message);
	}

}
