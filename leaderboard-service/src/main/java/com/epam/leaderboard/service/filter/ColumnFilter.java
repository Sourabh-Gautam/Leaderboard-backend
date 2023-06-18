package com.epam.leaderboard.service.filter;

import org.springframework.data.domain.Pageable;

import com.epam.leaderboard.dto.FilterData;
import com.epam.leaderboard.dto.SortFilterDto;

public interface ColumnFilter {
	FilterData filterWithColumnName(Pageable pageable, SortFilterDto sortFilterDto);
}
