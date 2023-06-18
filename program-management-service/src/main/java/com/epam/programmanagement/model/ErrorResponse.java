package com.epam.programmanagement.model;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorResponse {

	private String status;
	private String error;
	private LocalDate timestamp;
	private String path;

}