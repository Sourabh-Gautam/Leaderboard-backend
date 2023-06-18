package com.epam.leaderboard.service.filter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.data.domain.Pageable;

import com.epam.leaderboard.dto.FilterData;
import com.epam.leaderboard.dto.SortFilterDto;
import com.epam.leaderboard.repo.DashboardRepository;

public class DateFilterService {

	private DashboardRepository dashboardRepository;
	private static final String DATE_FORMAT = "yyyy-MM-dd";

	public DateFilterService(DashboardRepository dashboardRepository) {
		this.dashboardRepository = dashboardRepository;
	}

	public FilterData getParticipantsByAwardedDate(Pageable pageable, SortFilterDto sortFilterDto) {
		String startDateStr = sortFilterDto.getFilterModel().get("startDateFilter").getFilter();
		String endDateStr = sortFilterDto.getFilterModel().get("endDateFilter").getFilter();

		LocalDate startDate;
		LocalDate endDate;

		if (startDateStr.isBlank()) {
			endDate = LocalDate.parse(endDateStr, DateTimeFormatter.ofPattern(DATE_FORMAT));
			startDate = LocalDate.parse("%s-01-01".formatted(endDate.getYear()),
					DateTimeFormatter.ofPattern(DATE_FORMAT));
		} else if (endDateStr.isBlank()) {
			startDate = LocalDate.parse(startDateStr, DateTimeFormatter.ofPattern(DATE_FORMAT));
			endDate = LocalDate.parse("%s-12-31".formatted(startDate.getYear()),
					DateTimeFormatter.ofPattern(DATE_FORMAT));
		} else {
			endDate = LocalDate.parse(endDateStr, DateTimeFormatter.ofPattern(DATE_FORMAT));
			startDate = LocalDate.parse(startDateStr, DateTimeFormatter.ofPattern(DATE_FORMAT));
		}	
		
		var filterData = new FilterData();
		filterData.setParticipants(dashboardRepository.findAllByAwardedDateBetween(startDate, endDate, pageable));
		filterData.setTotalElements(dashboardRepository.getParticipantCountGrouping(startDate, endDate));
		return filterData;
	}

}
