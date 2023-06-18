package com.epam.leaderboard.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.epam.leaderboard.dto.FilterData;
import com.epam.leaderboard.dto.PaginatedParticipantResponse;
import com.epam.leaderboard.dto.ParticipantDto;
import com.epam.leaderboard.dto.SortFilterDto;
import com.epam.leaderboard.entity.Participant;
import com.epam.leaderboard.enums.SortType;
import com.epam.leaderboard.repo.DashboardRepository;
import com.epam.leaderboard.service.filter.ColumnFilterServiceFactory;
import com.epam.leaderboard.service.filter.DateFilterService;
import com.epam.leaderboard.service.filter.YearFilterService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This is the implementation class of DashboardService
 * 
 * @author Sourabh_Gautam
 *
 */

@Service
public class DashboardServiceImpl implements DashboardService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DashboardServiceImpl.class);

	private static final String DATE_FORMAT = "yyyy-MM-dd";
	private static Map<String, String> columnName = Map.of("points", "points", "participantName", "participant_name",
			"designation", "designation", "businessUnit", "business_unit", "resourceManager", "resource_manager",
			"primarySkill", "primary_skill", "subSkill", "sub_skill");

	private ModelMapper modelMapper = new ModelMapper();

	private DashboardRepository dashboardRepository;

	public DashboardServiceImpl(DashboardRepository dashboardRepository) {
		super();
		this.dashboardRepository = dashboardRepository;
	}

	/**
	 * This method is use to get all the participants sorted on the basis of
	 * calculatedWeightage in descending order
	 * 
	 * @return List of participants
	 * @throws JsonProcessingException
	 * @throws JsonMappingException
	 */
	@Override
	public PaginatedParticipantResponse getAllParticipants(int pageNo, int recordCount, SortFilterDto sortFilterDto)
			throws Exception {
	
		String colName = sortFilterDto.getColId();

		var startOfYear = LocalDate.parse("%s-01-01".formatted(sortFilterDto.getYear()),
				DateTimeFormatter.ofPattern(DATE_FORMAT));

		LocalDate endOfYear = startOfYear.plusYears(1).minusDays(1);

		PageRequest pageable;

		if (SortType.DESC.toString().equalsIgnoreCase(sortFilterDto.getSort())) {
			pageable = PageRequest.of(pageNo, recordCount, Sort.Direction.DESC, columnName.get(colName));
		} else {
			pageable = PageRequest.of(pageNo, recordCount, Sort.Direction.ASC, columnName.get(colName));
		}

		LOGGER.info("Record Count {}", recordCount);
		LOGGER.info("Page No {}", pageNo);
		LOGGER.info("Sort Filter DTO {}", sortFilterDto);

		var filterData = queryResponse(sortFilterDto, startOfYear, endOfYear, pageable);

		List<ParticipantDto> participantDtos = prepareResponse(filterData);

		LOGGER.info("Total Elements {}", filterData.getTotalElements());

		var paginatedParticipantResponse = new PaginatedParticipantResponse();
		paginatedParticipantResponse.setParticipantDtoList(participantDtos);
		paginatedParticipantResponse.setNumberOfParticipants(filterData.getTotalElements());
		return paginatedParticipantResponse;
	}

	public FilterData queryResponse(SortFilterDto sortFilterDto, LocalDate startOfYear, LocalDate endOfYear,
			PageRequest pageable) {
		var filterData = new FilterData();
		var totalElements = 0;
		if (sortFilterDto.getFilterModel().isEmpty()) {
			List<Participant> participants = dashboardRepository.findAllCustom(startOfYear, endOfYear, pageable);
			totalElements = dashboardRepository.getParticipantCountGrouping(startOfYear, endOfYear);
			filterData.setParticipants(participants);
			filterData.setTotalElements(totalElements);
		} else if ("dateFilter".equals(sortFilterDto.getData())) {
			filterData = new DateFilterService(dashboardRepository).getParticipantsByAwardedDate(pageable,
					sortFilterDto);
		} else if ("yearFilter".equals(sortFilterDto.getData())) {
			filterData = new YearFilterService(dashboardRepository).getParticipantsByAwardedDate(pageable,
					sortFilterDto);
		} else {
			var columnFilter = new ColumnFilterServiceFactory(dashboardRepository)
					.getFilteredParticipant(sortFilterDto);
			filterData = columnFilter.filterWithColumnName(pageable, sortFilterDto);
		}
		return filterData;
	}

	public List<ParticipantDto> prepareResponse(FilterData filterData)
			throws JsonProcessingException, JsonMappingException {
		List<ParticipantDto> participantDtos = new ArrayList<>();
		for (Participant p : filterData.getParticipants()) {
			ParticipantDto obj = modelMapper.map(p, ParticipantDto.class);
			var mapper = new ObjectMapper();
			List<String> subSkills = mapper.readValue(p.getSubSkill(), new TypeReference<List<String>>() {
			});
			obj.setSubSkill(subSkills);
			participantDtos.add(obj);
		}
		return participantDtos;
	}

}
