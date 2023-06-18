package com.epam.programmanagement.exception;

public class NoDataExistsException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public NoDataExistsException(String message)
	{
		super(message);
	}

}
