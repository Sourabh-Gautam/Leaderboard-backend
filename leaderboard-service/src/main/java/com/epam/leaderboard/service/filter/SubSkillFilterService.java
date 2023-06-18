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

public class SubSkillFilterService implements ColumnFilter {
	private static final Logger LOGGER = LoggerFactory.getLogger(SubSkillFilterService.class);

	private DashboardRepository dashboardRepository;

	public SubSkillFilterService(DashboardRepository dashboardRepository) {
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
			List<Participant> participants = dashboardRepository.findAllBySubSkillEquals(startOfYear, endOfYear,
					filter, pageable);
			int participantCount = dashboardRepository.getParticipantCountForEqualsSubSkill(startOfYear,
					endOfYear, filter);
			filterData.setParticipants(participants);
			filterData.setTotalElements(participantCount);
			yield filterData;
		}
		case FilterConstant.NOTEQUAL -> {
			List<Participant> participants = dashboardRepository.findAllBySubSkillIsNot(startOfYear, endOfYear,
					filter, pageable);
			int participantCount = dashboardRepository.getParticipantCountForNotEqualsSubSkill(startOfYear,
					endOfYear, filter);
			filterData.setParticipants(participants);
			filterData.setTotalElements(participantCount);
			yield filterData;
		}

		case FilterConstant.STARTSWITH -> {
			List<Participant> participants = dashboardRepository.findAllBySubSkillStartingWith(startOfYear,
					endOfYear, filter, pageable);
			int participantCount = dashboardRepository.getParticipantCountForStartsWithSubSkill(startOfYear,
					endOfYear, filter);
			filterData.setParticipants(participants);
			filterData.setTotalElements(participantCount);
			yield filterData;
		}
		case FilterConstant.ENDSWITH -> {
			List<Participant> participants = dashboardRepository.findAllBySubSkillEndingWith(startOfYear,
					endOfYear, filter, pageable);
			int participantCount = dashboardRepository.getParticipantCountForEndsWithSubSkill(startOfYear,
					endOfYear, filter);
			filterData.setParticipants(participants);
			filterData.setTotalElements(participantCount);
			yield filterData;
		}
		default -> {
			List<Participant> participants = dashboardRepository.findAllBySubSkillContains(startOfYear,
					endOfYear, filter, pageable);
			int participantCount = dashboardRepository.getParticipantCountForContainsSubSkill(startOfYear,
					endOfYear, filter);
			filterData.setParticipants(participants);
			filterData.setTotalElements(participantCount);
			yield filterData;
		}
		};
	}
}
