package com.epam.leaderboard.service.filter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;

import com.epam.leaderboard.dto.FilterData;
import com.epam.leaderboard.dto.SortFilterDto;
import com.epam.leaderboard.entity.Participant;
import com.epam.leaderboard.repo.DashboardRepository;

public class BusinessUnitFilterService implements ColumnFilter {
	private static final Logger LOGGER = LoggerFactory.getLogger(ParticipantNameFilterService.class);

	private DashboardRepository dashboardRepository;

	public BusinessUnitFilterService(DashboardRepository dashboardRepository) {
		this.dashboardRepository = dashboardRepository;
	}

	public FilterData filterWithColumnName(Pageable pageable, SortFilterDto sortFilterDto) {

		LOGGER.info("Filter model - {}", sortFilterDto.getFilterModel());
		String colName = sortFilterDto.getFilterModel().keySet().stream().findFirst().orElse("participantName");
		String filter = sortFilterDto.getFilterModel().get(colName).getFilter();
		String type = sortFilterDto.getFilterModel().get(colName).getType();
		int year = sortFilterDto.getYear();
		LocalDate startOfYear = LocalDate.parse("%s-01-01".formatted(year), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalDate endOfYear = startOfYear.plusYears(1).minusDays(1);
		LOGGER.info("Column Name - {}, Filter - {}, Type - {}", colName, filter, type);

		FilterData filterData = new FilterData();

		return switch (type) {
		case FilterConstant.EQUALS -> {
			List<Participant> participants = dashboardRepository.findAllByBusinessUnitEquals(startOfYear, endOfYear,
					filter, pageable);
			int participantCount = dashboardRepository.getParticipantCountForEqualsBusinessUnit(startOfYear,
					endOfYear, filter);
			filterData.setParticipants(participants);
			filterData.setTotalElements(participantCount);
			yield filterData;
		}
		case FilterConstant.NOTEQUAL -> {
			List<Participant> participants = dashboardRepository.findAllByBusinessUnitIsNot(startOfYear, endOfYear,
					filter, pageable);
			int participantCount = dashboardRepository.getParticipantCountForNotEqualsBusinessUnit(startOfYear,
					endOfYear, filter);
			filterData.setParticipants(participants);
			filterData.setTotalElements(participantCount);
			yield filterData;
		}

		case FilterConstant.STARTSWITH -> {
			List<Participant> participants = dashboardRepository.findAllByBusinessUnitStartingWith(startOfYear,
					endOfYear, filter, pageable);
			int participantCount = dashboardRepository.getParticipantCountForStartsWithBusinessUnit(startOfYear,
					endOfYear, filter);
			filterData.setParticipants(participants);
			filterData.setTotalElements(participantCount);
			yield filterData;
		}
		case FilterConstant.ENDSWITH -> {
			List<Participant> participants = dashboardRepository.findAllByBusinessUnitEndingWith(startOfYear,
					endOfYear, filter, pageable);
			int participantCount = dashboardRepository.getParticipantCountForEndsWithBusinessUnit(startOfYear,
					endOfYear, filter);
			filterData.setParticipants(participants);
			filterData.setTotalElements(participantCount);
			yield filterData;
		}
		default -> {
			List<Participant> participants = dashboardRepository.findAllByBusinessUnitContains(startOfYear,
					endOfYear, filter, pageable);
			int participantCount = dashboardRepository.getParticipantCountForContainsBusinessUnit(startOfYear,
					endOfYear, filter);
			filterData.setParticipants(participants);
			filterData.setTotalElements(participantCount);
			yield filterData;
		}
		};
	}
}
