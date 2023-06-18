package com.epam.leaderboard.dto;

import java.util.Map;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
//Don't remove ! It's required.
@EqualsAndHashCode
public class SortFilterDto {
	private Map<String, FilterDto> filterModel;
	private String colId;
	private String sort;
	private String data;
	private int year;
}
