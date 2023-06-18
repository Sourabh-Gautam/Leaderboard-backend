package com.epam.leaderboard.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class FilterDto {

	private String filter;
	private String filterType;
	private String type;
	private String operator;
	private ConditionDto condition1;
	private ConditionDto condition2;
	
}