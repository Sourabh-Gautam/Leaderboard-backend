package com.epam.leaderboard.service.filter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;

import com.epam.leaderboard.dto.FilterData;
import com.epam.leaderboard.dto.SortFilterDto;
import com.epam.leaderboard.repo.DashboardRepository;

public class YearFilterService {

	private DashboardRepository dashboardRepository;
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	private static final Logger LOGGER = LoggerFactory.getLogger(YearFilterService.class);

	public YearFilterService(DashboardRepository dashboardRepository) {
		this.dashboardRepository = dashboardRepository;
	}

	public FilterData getParticipantsByAwardedDate(Pageable pageable, SortFilterDto sortFilterDto) {

		String year = sortFilterDto.getFilterModel().get("yearFilter").getFilter();
		String quarterFilter = sortFilterDto.getFilterModel().get("quarterFilter").getFilter();

		LOGGER.info("Year - {}", year);
		LOGGER.info("Year filter - {}", quarterFilter);

		var startDate = LocalDate.parse("%s-01-01".formatted(year), DateTimeFormatter.ofPattern(DATE_FORMAT));
		var endDate = startDate;
		
		switch (quarterFilter) {
		case "q1" -> endDate = endDate.plusMonths(3).minusDays(1);
		case "q2" -> {
			startDate = startDate.plusMonths(3);
			endDate = endDate.plusMonths(6).minusDays(1);
		}
		case "q3" -> {
			startDate = startDate.plusMonths(6);
			endDate = endDate.plusMonths(9).minusDays(1);
		}
		case "q4" -> {
			startDate = startDate.plusMonths(9);
			endDate = endDate.plusMonths(12).minusDays(1);
		}
		default -> endDate = endDate.plusYears(1).minusDays(1);
		}

		LOGGER.info("Start date - {}", startDate);
		LOGGER.info("End date - {}", endDate);
		
		var filterData = new FilterData();
		filterData.setParticipants(dashboardRepository.findAllByAwardedDateBetween(startDate, endDate, pageable));
		filterData.setTotalElements(dashboardRepository.getParticipantCountGrouping(startDate, endDate));
		return filterData;

	}
}
