package com.epam.leaderboard.service.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.epam.leaderboard.dto.SortFilterDto;
import com.epam.leaderboard.repo.DashboardRepository;
import com.epam.leaderboard.service.DashboardServiceImpl;

@Service
public class ColumnFilterServiceFactory {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DashboardServiceImpl.class);
	private DashboardRepository dashboardRepository;
	
	public ColumnFilterServiceFactory(DashboardRepository dashboardRepository) {
		super();
		this.dashboardRepository = dashboardRepository;
	}
	
	public ColumnFilter getFilteredParticipant(SortFilterDto sortFilterDto) {
		LOGGER.info("Filter model - {}", sortFilterDto.getFilterModel());
		String colName = sortFilterDto.getFilterModel().keySet().stream().findFirst().orElse("participantName");
		
		return switch (colName) {
		case "designation" -> new DesignationFilterService(dashboardRepository);
		case "businessUnit" -> new BusinessUnitFilterService(dashboardRepository);
		case "primarySkill" -> new PrimarySkillFilterService(dashboardRepository);
		case "subSkill" -> new SubSkillFilterService(dashboardRepository);
		case "resourceManager" -> new ResourceManagerFilterService(dashboardRepository);
		default -> new ParticipantNameFilterService(dashboardRepository);
		};
	}
	
}
