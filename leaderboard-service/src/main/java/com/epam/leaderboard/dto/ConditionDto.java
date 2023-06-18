package com.epam.leaderboard.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ConditionDto {
	private String filter;
	private String filterType;
	private String type;
}
