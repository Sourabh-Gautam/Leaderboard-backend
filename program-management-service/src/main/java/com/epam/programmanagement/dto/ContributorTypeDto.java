package com.epam.programmanagement.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ContributorTypeDto {
	private int id;
	private String contributerType;
	private int points;
}
