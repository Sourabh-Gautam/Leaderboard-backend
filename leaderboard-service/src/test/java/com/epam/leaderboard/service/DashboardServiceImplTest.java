package com.epam.leaderboard.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.epam.leaderboard.dto.ConditionDto;
import com.epam.leaderboard.dto.FilterDto;
import com.epam.leaderboard.dto.PaginatedParticipantResponse;
import com.epam.leaderboard.dto.SortFilterDto;
import com.epam.leaderboard.entity.Participant;
import com.epam.leaderboard.repo.DashboardRepository;

@ExtendWith(MockitoExtension.class)
class DashboardServiceImplTest {

	@InjectMocks
	private DashboardServiceImpl dashboardService;

	@Mock
	private DashboardRepository dashboardRepository;

	List<Participant> participants;

	int pageNo = 0;
	int recordCount = 5;

	private static final String DATE_FORMAT = "yyyy-MM-dd";
	LocalDate startOfCurrentYear = LocalDate.parse("%s-01-01".formatted(Year.now().getValue()),
			DateTimeFormatter.ofPattern(DATE_FORMAT));
	LocalDate endOfCurrentYear = startOfCurrentYear.plusYears(1).minusDays(1);
	String startDateStr;
	String endDateStr;
	String year = "2023";
	String[] quarter = { "q1", "q2", "q3", "q4" };

	HashMap<String, FilterDto> map;
	SortFilterDto sortFilterDto;

	FilterDto filterDto;
	FilterDto startDateFilterDto;
	FilterDto endDateFilterDto;
	FilterDto yearFilterDto;
	FilterDto quarterFilterDto;

	ConditionDto conditionDto1;
	ConditionDto conditionDto2;

	@BeforeEach
	void setup() {
		
		startDateStr = "%d-01-01".formatted(Year.now().getValue());
		endDateStr = "%d-12-31".formatted(Year.now().getValue());
		
		participants = new ArrayList<>();

		Participant participant = new Participant();
		participant.setParticipantName("Sourabh");
		participant.setParticipantId(1);
		participant.setAddedBy("sourabh");
		participant.setAwardedDate(null);
		participant.setBusinessUnit("SCL Delivery");
		participant.setDesignation("JSE");
		participant.setPrimarySkill("Java");
		participant.setSubSkill("[\"Angular\"]");
		participant.setResourceManager("Shakti Vyas");

		Participant participant2 = new Participant();
		participant2.setParticipantName("Divyam");
		participant2.setParticipantId(2);
		participant2.setAddedBy("sourabh");
		participant2.setAwardedDate(null);
		participant2.setBusinessUnit("SCL Delivery");
		participant2.setDesignation("JSE");
		participant2.setPrimarySkill("Java");
		participant2.setSubSkill("[\"Angular\"]");
		participant2.setResourceManager("Shakti Vyas");

		participants.add(participant);
		participants.add(participant2);

		map = new HashMap<>();

		sortFilterDto = new SortFilterDto();
		sortFilterDto.setColId("points");
		sortFilterDto.setData(null);
		sortFilterDto.setSort("asc");
		sortFilterDto.setFilterModel(map);
		sortFilterDto.setYear(Year.now().getValue());

		filterDto = new FilterDto();

		filterDto.setFilter("test");
		filterDto.setFilterType("text");
		filterDto.setOperator(null);
		filterDto.setCondition1(null);
		filterDto.setCondition2(null);

		startDateFilterDto = new FilterDto();
		startDateFilterDto.setFilterType("date");
		startDateFilterDto.setType("greaterThan");

		endDateFilterDto = new FilterDto();
		endDateFilterDto.setFilterType("date");
		endDateFilterDto.setType("lessThan");

		yearFilterDto = new FilterDto();
		yearFilterDto.setFilterType("date");
		yearFilterDto.setType("lessThan");

		quarterFilterDto = new FilterDto();
		quarterFilterDto.setFilterType("date");
		quarterFilterDto.setType("lessThan");

		conditionDto1 = new ConditionDto();
		conditionDto1.setFilterType("text");
		conditionDto1.setType("contains");

		conditionDto2 = new ConditionDto();
		conditionDto2.setFilterType("text");
		conditionDto2.setType("contains");

	}

	@Test
	void testGetAllParticipantsWithNoFilterAsc() throws Exception {

		PageRequest pageable = PageRequest.of(pageNo, recordCount, Sort.Direction.ASC, "points");

		when(dashboardRepository.findAllCustom(startOfCurrentYear, endOfCurrentYear, pageable)).thenReturn(this.participants);

		PaginatedParticipantResponse result = dashboardService.getAllParticipants(pageNo, recordCount, sortFilterDto);

		assertThat(result.getParticipantDtoList().size()).isEqualTo(2);
	}

	@Test
	void testGetAllParticipantsWithDesc() throws Exception {

		this.sortFilterDto.setSort("desc");
		PageRequest pageable = PageRequest.of(pageNo, recordCount, Sort.Direction.DESC, "points");

		when(dashboardRepository.findAllCustom(startOfCurrentYear, endOfCurrentYear, pageable)).thenReturn(this.participants);

		PaginatedParticipantResponse result = dashboardService.getAllParticipants(pageNo, recordCount, sortFilterDto);
		assertThat(result.getParticipantDtoList().size()).isEqualTo(2);
	}

	
	@Test
	void testGetAllParticipantsOnParticipantsNameWithContainsFilter() throws Exception {
		
		filterDto.setType("contains");
		map.put("participantsName", filterDto);
		sortFilterDto.setFilterModel(map);
		
		PageRequest pageable = PageRequest.of(pageNo, recordCount, Sort.Direction.ASC, "points");
		
		when(dashboardRepository.findAllByParticipantNameContains(startOfCurrentYear, endOfCurrentYear, "test", pageable)).thenReturn(this.participants);
		
		PaginatedParticipantResponse result = dashboardService.getAllParticipants(pageNo, recordCount, sortFilterDto);
		
		assertThat(result.getParticipantDtoList().size()).isEqualTo(2);
	}
	
	@Test
	void testGetAllParticipantsOnParticipantsNameWithEqualsFilter() throws Exception {
		
		filterDto.setType("equals");
		map.put("participantsName", filterDto);
		sortFilterDto.setFilterModel(map);
		
		PageRequest pageable = PageRequest.of(pageNo, recordCount, Sort.Direction.ASC, "points");
		
		when(dashboardRepository.findAllByParticipantNameEquals(startOfCurrentYear, endOfCurrentYear, "test", pageable)).thenReturn(this.participants);
		
		PaginatedParticipantResponse result = dashboardService.getAllParticipants(pageNo, recordCount, sortFilterDto);
		
		assertThat(result.getParticipantDtoList().size()).isEqualTo(2);
	}
	
	@Test
	void testGetAllParticipantsOnParticipantsNameWithNotEqualsFilter() throws Exception {
		
		filterDto.setType("notEqual");
		map.put("participantsName", filterDto);
		sortFilterDto.setFilterModel(map);
		
		PageRequest pageable = PageRequest.of(pageNo, recordCount, Sort.Direction.ASC, "points");
		
		when(dashboardRepository.findAllByParticipantNameIsNot(startOfCurrentYear, endOfCurrentYear, "test", pageable)).thenReturn(this.participants);
		
		PaginatedParticipantResponse result = dashboardService.getAllParticipants(pageNo, recordCount, sortFilterDto);
		
		assertThat(result.getParticipantDtoList().size()).isEqualTo(2);
	}
	
	@Test
	void testGetAllParticipantsOnParticipantsNameWithStartsWithFilter() throws Exception {
		
		filterDto.setType("startsWith");
		map.put("participantsName", filterDto);
		sortFilterDto.setFilterModel(map);
		
		PageRequest pageable = PageRequest.of(pageNo, recordCount, Sort.Direction.ASC, "points");
		
		when(dashboardRepository.findAllByParticipantNameStartingWith(startOfCurrentYear, endOfCurrentYear, "test", pageable)).thenReturn(this.participants);
		
		PaginatedParticipantResponse result = dashboardService.getAllParticipants(pageNo, recordCount, sortFilterDto);
		
		assertThat(result.getParticipantDtoList().size()).isEqualTo(2);
	}

	@Test
	void testGetAllParticipantsOnParticipantsNameWithEndsWithFilter() throws Exception {
		
		filterDto.setType("endsWith");
		map.put("participantsName", filterDto);
		sortFilterDto.setFilterModel(map);
		
		PageRequest pageable = PageRequest.of(pageNo, recordCount, Sort.Direction.ASC, "points");
		
		when(dashboardRepository.findAllByParticipantNameEndingWith(startOfCurrentYear, endOfCurrentYear, "test", pageable)).thenReturn(this.participants);
		
		PaginatedParticipantResponse result = dashboardService.getAllParticipants(pageNo, recordCount, sortFilterDto);
		
		assertThat(result.getParticipantDtoList().size()).isEqualTo(2);
	}
	
	@Test
	void testGetAllParticipantsOnDesignationWithContainsFilter() throws Exception {
		
		filterDto.setType("contains");
		map.put("designation", filterDto);
		sortFilterDto.setFilterModel(map);
		
		PageRequest pageable = PageRequest.of(pageNo, recordCount, Sort.Direction.ASC, "points");
		
		when(dashboardRepository.findAllByDesignationContains(startOfCurrentYear, endOfCurrentYear, "test", pageable)).thenReturn(this.participants);
		
		PaginatedParticipantResponse result = dashboardService.getAllParticipants(pageNo, recordCount, sortFilterDto);
		
		assertThat(result.getParticipantDtoList().size()).isEqualTo(2);
	}
	
	@Test
	void testGetAllParticipantsOnDesignationWithEqualsFilter() throws Exception {
		
		filterDto.setType("equals");
		map.put("designation", filterDto);
		sortFilterDto.setFilterModel(map);
		
		PageRequest pageable = PageRequest.of(pageNo, recordCount, Sort.Direction.ASC, "points");
		
		when(dashboardRepository.findAllByDesignationEquals(startOfCurrentYear, endOfCurrentYear, "test", pageable)).thenReturn(this.participants);
		
		PaginatedParticipantResponse result = dashboardService.getAllParticipants(pageNo, recordCount, sortFilterDto);
		
		assertThat(result.getParticipantDtoList().size()).isEqualTo(2);
	}
	
	@Test
	void testGetAllParticipantsOnDesignationWithNotEqualsFilter() throws Exception {
		
		filterDto.setType("notEqual");
		map.put("designation", filterDto);
		sortFilterDto.setFilterModel(map);
		
		PageRequest pageable = PageRequest.of(pageNo, recordCount, Sort.Direction.ASC, "points");
		
		when(dashboardRepository.findAllByDesignationIsNot(startOfCurrentYear, endOfCurrentYear, "test", pageable)).thenReturn(this.participants);
		
		PaginatedParticipantResponse result = dashboardService.getAllParticipants(pageNo, recordCount, sortFilterDto);
		
		assertThat(result.getParticipantDtoList().size()).isEqualTo(2);
	}
	
	@Test
	void testGetAllParticipantsOnDesignationWithStartsWithFilter() throws Exception {
		
		filterDto.setType("startsWith");
		map.put("designation", filterDto);
		sortFilterDto.setFilterModel(map);
		
		PageRequest pageable = PageRequest.of(pageNo, recordCount, Sort.Direction.ASC, "points");
		
		when(dashboardRepository.findAllByDesignationStartingWith(startOfCurrentYear, endOfCurrentYear, "test", pageable)).thenReturn(this.participants);
		
		PaginatedParticipantResponse result = dashboardService.getAllParticipants(pageNo, recordCount, sortFilterDto);
		
		assertThat(result.getParticipantDtoList().size()).isEqualTo(2);
	}

	@Test
	void testGetAllParticipantsOnDesignationWithEndsWithFilter() throws Exception {
		
		filterDto.setType("endsWith");
		map.put("designation", filterDto);
		sortFilterDto.setFilterModel(map);
		
		PageRequest pageable = PageRequest.of(pageNo, recordCount, Sort.Direction.ASC, "points");
		
		when(dashboardRepository.findAllByDesignationEndingWith(startOfCurrentYear, endOfCurrentYear, "test", pageable)).thenReturn(this.participants);
		
		PaginatedParticipantResponse result = dashboardService.getAllParticipants(pageNo, recordCount, sortFilterDto);
		
		assertThat(result.getParticipantDtoList().size()).isEqualTo(2);
	}
	
	@Test
	void testGetAllParticipantsOnBusinessUnitWithContainsFilter() throws Exception {
		
		filterDto.setType("contains");
		map.put("businessUnit", filterDto);
		sortFilterDto.setFilterModel(map);
		
		PageRequest pageable = PageRequest.of(pageNo, recordCount, Sort.Direction.ASC, "points");
		
		when(dashboardRepository.findAllByBusinessUnitContains(startOfCurrentYear, endOfCurrentYear, "test", pageable)).thenReturn(this.participants);
		
		PaginatedParticipantResponse result = dashboardService.getAllParticipants(pageNo, recordCount, sortFilterDto);
		
		assertThat(result.getParticipantDtoList().size()).isEqualTo(2);
	}
	
	@Test
	void testGetAllParticipantsOnBusinessUnitWithEqualsFilter() throws Exception {
		
		filterDto.setType("equals");
		map.put("businessUnit", filterDto);
		sortFilterDto.setFilterModel(map);
		
		PageRequest pageable = PageRequest.of(pageNo, recordCount, Sort.Direction.ASC, "points");
		
		when(dashboardRepository.findAllByBusinessUnitEquals(startOfCurrentYear, endOfCurrentYear, "test", pageable)).thenReturn(this.participants);
		
		PaginatedParticipantResponse result = dashboardService.getAllParticipants(pageNo, recordCount, sortFilterDto);
		
		assertThat(result.getParticipantDtoList().size()).isEqualTo(2);
	}
	
	@Test
	void testGetAllParticipantsOnBusinessUnitWithNotEqualsFilter() throws Exception {
		
		filterDto.setType("notEqual");
		map.put("businessUnit", filterDto);
		sortFilterDto.setFilterModel(map);
		
		PageRequest pageable = PageRequest.of(pageNo, recordCount, Sort.Direction.ASC, "points");
		
		when(dashboardRepository.findAllByBusinessUnitIsNot(startOfCurrentYear, endOfCurrentYear, "test", pageable)).thenReturn(this.participants);
		
		PaginatedParticipantResponse result = dashboardService.getAllParticipants(pageNo, recordCount, sortFilterDto);
		
		assertThat(result.getParticipantDtoList().size()).isEqualTo(2);
	}
	
	@Test
	void testGetAllParticipantsOnBusinessUnitWithStartsWithFilter() throws Exception {
		
		filterDto.setType("startsWith");
		map.put("businessUnit", filterDto);
		sortFilterDto.setFilterModel(map);
		
		PageRequest pageable = PageRequest.of(pageNo, recordCount, Sort.Direction.ASC, "points");
		
		when(dashboardRepository.findAllByBusinessUnitStartingWith(startOfCurrentYear, endOfCurrentYear, "test", pageable)).thenReturn(this.participants);
		
		PaginatedParticipantResponse result = dashboardService.getAllParticipants(pageNo, recordCount, sortFilterDto);
		
		assertThat(result.getParticipantDtoList().size()).isEqualTo(2);
	}

	@Test
	void testGetAllParticipantsOnBusinessUnitWithEndsWithFilter() throws Exception {
		
		filterDto.setType("endsWith");
		map.put("businessUnit", filterDto);
		sortFilterDto.setFilterModel(map);
		
		PageRequest pageable = PageRequest.of(pageNo, recordCount, Sort.Direction.ASC, "points");
		
		when(dashboardRepository.findAllByBusinessUnitEndingWith(startOfCurrentYear, endOfCurrentYear, "test", pageable)).thenReturn(this.participants);
		
		PaginatedParticipantResponse result = dashboardService.getAllParticipants(pageNo, recordCount, sortFilterDto);
		
		assertThat(result.getParticipantDtoList().size()).isEqualTo(2);
	}
	
	@Test
	void testGetAllParticipantsOnPrimarySkillWithContainsFilter() throws Exception {
		
		filterDto.setType("contains");
		map.put("primarySkill", filterDto);
		sortFilterDto.setFilterModel(map);
		
		PageRequest pageable = PageRequest.of(pageNo, recordCount, Sort.Direction.ASC, "points");
		
		when(dashboardRepository.findAllByPrimarySkillContains(startOfCurrentYear, endOfCurrentYear, "test", pageable)).thenReturn(this.participants);
		
		PaginatedParticipantResponse result = dashboardService.getAllParticipants(pageNo, recordCount, sortFilterDto);
		
		assertThat(result.getParticipantDtoList().size()).isEqualTo(2);
	}
	
	@Test
	void testGetAllParticipantsOnPrimarySkillWithEqualsFilter() throws Exception {
		
		filterDto.setType("equals");
		map.put("primarySkill", filterDto);
		sortFilterDto.setFilterModel(map);
		
		PageRequest pageable = PageRequest.of(pageNo, recordCount, Sort.Direction.ASC, "points");
		
		when(dashboardRepository.findAllByPrimarySkillEquals(startOfCurrentYear, endOfCurrentYear, "test", pageable)).thenReturn(this.participants);
		
		PaginatedParticipantResponse result = dashboardService.getAllParticipants(pageNo, recordCount, sortFilterDto);
		
		assertThat(result.getParticipantDtoList().size()).isEqualTo(2);
	}
	
	@Test
	void testGetAllParticipantsOnPrimarySkillWithNotEqualsFilter() throws Exception {
		
		filterDto.setType("notEqual");
		map.put("primarySkill", filterDto);
		sortFilterDto.setFilterModel(map);
		
		PageRequest pageable = PageRequest.of(pageNo, recordCount, Sort.Direction.ASC, "points");
		
		when(dashboardRepository.findAllByPrimarySkillIsNot(startOfCurrentYear, endOfCurrentYear, "test", pageable)).thenReturn(this.participants);
		
		PaginatedParticipantResponse result = dashboardService.getAllParticipants(pageNo, recordCount, sortFilterDto);
		
		assertThat(result.getParticipantDtoList().size()).isEqualTo(2);
	}
	
	@Test
	void testGetAllParticipantsOnPrimarySkillWithStartsWithFilter() throws Exception {
		
		filterDto.setType("startsWith");
		map.put("primarySkill", filterDto);
		sortFilterDto.setFilterModel(map);
		
		PageRequest pageable = PageRequest.of(pageNo, recordCount, Sort.Direction.ASC, "points");
		
		when(dashboardRepository.findAllByPrimarySkillStartingWith(startOfCurrentYear, endOfCurrentYear, "test", pageable)).thenReturn(this.participants);
		
		PaginatedParticipantResponse result = dashboardService.getAllParticipants(pageNo, recordCount, sortFilterDto);
		
		assertThat(result.getParticipantDtoList().size()).isEqualTo(2);
	}

	@Test
	void testGetAllParticipantsOnPrimarySkillWithEndsWithFilter() throws Exception {
		
		filterDto.setType("endsWith");
		map.put("primarySkill", filterDto);
		sortFilterDto.setFilterModel(map);
		
		PageRequest pageable = PageRequest.of(pageNo, recordCount, Sort.Direction.ASC, "points");
		
		when(dashboardRepository.findAllByPrimarySkillEndingWith(startOfCurrentYear, endOfCurrentYear, "test", pageable)).thenReturn(this.participants);
		
		PaginatedParticipantResponse result = dashboardService.getAllParticipants(pageNo, recordCount, sortFilterDto);
		
		assertThat(result.getParticipantDtoList().size()).isEqualTo(2);
	}
	
	@Test
	void testGetAllParticipantsOnSubSkillWithContainsFilter() throws Exception {
		
		filterDto.setType("contains");
		map.put("subSkill", filterDto);
		sortFilterDto.setFilterModel(map);
		
		PageRequest pageable = PageRequest.of(pageNo, recordCount, Sort.Direction.ASC, "points");
		
		when(dashboardRepository.findAllBySubSkillContains(startOfCurrentYear, endOfCurrentYear, "test", pageable)).thenReturn(this.participants);
		
		PaginatedParticipantResponse result = dashboardService.getAllParticipants(pageNo, recordCount, sortFilterDto);
		
		assertThat(result.getParticipantDtoList().size()).isEqualTo(2);
	}
	
	@Test
	void testGetAllParticipantsOnSubSkillWithEqualsFilter() throws Exception {
		
		filterDto.setType("equals");
		map.put("subSkill", filterDto);
		sortFilterDto.setFilterModel(map);
		
		PageRequest pageable = PageRequest.of(pageNo, recordCount, Sort.Direction.ASC, "points");
		
		when(dashboardRepository.findAllBySubSkillEquals(startOfCurrentYear, endOfCurrentYear, "test", pageable)).thenReturn(this.participants);
		
		PaginatedParticipantResponse result = dashboardService.getAllParticipants(pageNo, recordCount, sortFilterDto);
		
		assertThat(result.getParticipantDtoList().size()).isEqualTo(2);
	}
	
	@Test
	void testGetAllParticipantsOnSubSkillWithNotEqualsFilter() throws Exception {
		
		filterDto.setType("notEqual");
		map.put("subSkill", filterDto);
		sortFilterDto.setFilterModel(map);
		
		PageRequest pageable = PageRequest.of(pageNo, recordCount, Sort.Direction.ASC, "points");
		
		when(dashboardRepository.findAllBySubSkillIsNot(startOfCurrentYear, endOfCurrentYear, "test", pageable)).thenReturn(this.participants);
		
		PaginatedParticipantResponse result = dashboardService.getAllParticipants(pageNo, recordCount, sortFilterDto);
		
		assertThat(result.getParticipantDtoList().size()).isEqualTo(2);
	}
	
	@Test
	void testGetAllParticipantsOnSubSkillWithStartsWithFilter() throws Exception {
		
		filterDto.setType("startsWith");
		map.put("subSkill", filterDto);
		sortFilterDto.setFilterModel(map);
		
		PageRequest pageable = PageRequest.of(pageNo, recordCount, Sort.Direction.ASC, "points");
		
		when(dashboardRepository.findAllBySubSkillStartingWith(startOfCurrentYear, endOfCurrentYear, "test", pageable)).thenReturn(this.participants);
		
		PaginatedParticipantResponse result = dashboardService.getAllParticipants(pageNo, recordCount, sortFilterDto);
		
		assertThat(result.getParticipantDtoList().size()).isEqualTo(2);
	}
	
	@Test
	void testGetAllParticipantsOnSubSkillWithEndsWithFilter() throws Exception {
		
		filterDto.setType("endsWith");
		map.put("subSkill", filterDto);
		sortFilterDto.setFilterModel(map);
		
		PageRequest pageable = PageRequest.of(pageNo, recordCount, Sort.Direction.ASC, "points");
		
		when(dashboardRepository.findAllBySubSkillEndingWith(startOfCurrentYear, endOfCurrentYear, "test", pageable)).thenReturn(this.participants);
		
		PaginatedParticipantResponse result = dashboardService.getAllParticipants(pageNo, recordCount, sortFilterDto);
		
		assertThat(result.getParticipantDtoList().size()).isEqualTo(2);
	}
	
	@Test
	void testGetAllParticipantsOnResourceManagerWithContainsFilter() throws Exception {
		
		filterDto.setType("contains");
		map.put("resourceManager", filterDto);
		sortFilterDto.setFilterModel(map);
		
		PageRequest pageable = PageRequest.of(pageNo, recordCount, Sort.Direction.ASC, "points");
		
		when(dashboardRepository.findAllByResourceManagerContains(startOfCurrentYear, endOfCurrentYear, "test", pageable)).thenReturn(this.participants);
		
		PaginatedParticipantResponse result = dashboardService.getAllParticipants(pageNo, recordCount, sortFilterDto);
		
		assertThat(result.getParticipantDtoList().size()).isEqualTo(2);
	}
	
	@Test
	void testGetAllParticipantsOnResourceManagerWithEqualsFilter() throws Exception {
		
		filterDto.setType("equals");
		map.put("resourceManager", filterDto);
		sortFilterDto.setFilterModel(map);
		
		PageRequest pageable = PageRequest.of(pageNo, recordCount, Sort.Direction.ASC, "points");
		
		when(dashboardRepository.findAllByResourceManagerEquals(startOfCurrentYear, endOfCurrentYear, "test", pageable)).thenReturn(this.participants);
		
		PaginatedParticipantResponse result = dashboardService.getAllParticipants(pageNo, recordCount, sortFilterDto);
		
		assertThat(result.getParticipantDtoList().size()).isEqualTo(2);
	}
	
	@Test
	void testGetAllParticipantsOnResourceManagerWithNotEqualsFilter() throws Exception {
		
		filterDto.setType("notEqual");
		map.put("resourceManager", filterDto);
		sortFilterDto.setFilterModel(map);
		
		PageRequest pageable = PageRequest.of(pageNo, recordCount, Sort.Direction.ASC, "points");
		
		when(dashboardRepository.findAllByResourceManagerIsNot(startOfCurrentYear, endOfCurrentYear, "test", pageable)).thenReturn(this.participants);
		
		PaginatedParticipantResponse result = dashboardService.getAllParticipants(pageNo, recordCount, sortFilterDto);
		
		assertThat(result.getParticipantDtoList().size()).isEqualTo(2);
	}
	
	@Test
	void testGetAllParticipantsOnResourceManagerWithStartsWithFilter() throws Exception {
		
		filterDto.setType("startsWith");
		map.put("resourceManager", filterDto);
		sortFilterDto.setFilterModel(map);
		
		PageRequest pageable = PageRequest.of(pageNo, recordCount, Sort.Direction.ASC, "points");
		
		when(dashboardRepository.findAllByResourceManagerStartingWith(startOfCurrentYear, endOfCurrentYear, "test", pageable)).thenReturn(this.participants);
		
		PaginatedParticipantResponse result = dashboardService.getAllParticipants(pageNo, recordCount, sortFilterDto);
		
		assertThat(result.getParticipantDtoList().size()).isEqualTo(2);
	}

	@Test
	void testGetAllParticipantsOnResourceManagerWithEndsWithFilter() throws Exception {
		
		filterDto.setType("endsWith");
		map.put("resourceManager", filterDto);
		sortFilterDto.setFilterModel(map);
		
		PageRequest pageable = PageRequest.of(pageNo, recordCount, Sort.Direction.ASC, "points");
		
		when(dashboardRepository.findAllByResourceManagerEndingWith(startOfCurrentYear, endOfCurrentYear, "test", pageable)).thenReturn(this.participants);
		
		PaginatedParticipantResponse result = dashboardService.getAllParticipants(pageNo, recordCount, sortFilterDto);
		
		assertThat(result.getParticipantDtoList().size()).isEqualTo(2);
	}
	

	@Test
	void testGetAllParticipantsWithDateFilter() throws Exception {

		startDateFilterDto.setFilter(startDateStr);
		endDateFilterDto.setFilter("");

		map.put("startDateFilter", startDateFilterDto);
		map.put("endDateFilter", endDateFilterDto);
		sortFilterDto.setFilterModel(map);
		sortFilterDto.setData("dateFilter");

		PageRequest pageable = PageRequest.of(pageNo, recordCount, Sort.Direction.ASC, "points");

		when(dashboardRepository.findAllByAwardedDateBetween(startOfCurrentYear, endOfCurrentYear, pageable))
				.thenReturn(this.participants);

		PaginatedParticipantResponse result = dashboardService.getAllParticipants(pageNo, recordCount, sortFilterDto);

		assertThat(result.getParticipantDtoList().size()).isEqualTo(2);
	}

	@Test
	void testGetAllParticipantsWithEndDateFilter() throws Exception {

		startDateFilterDto.setFilter("");
		endDateFilterDto.setFilter(endDateStr);

		map.put("startDateFilter", startDateFilterDto);
		map.put("endDateFilter", endDateFilterDto);
		sortFilterDto.setFilterModel(map);
		sortFilterDto.setData("dateFilter");

		PageRequest pageable = PageRequest.of(pageNo, recordCount, Sort.Direction.ASC, "points");

		when(dashboardRepository.findAllByAwardedDateBetween(startOfCurrentYear, endOfCurrentYear, pageable))
				.thenReturn(this.participants);

		PaginatedParticipantResponse result = dashboardService.getAllParticipants(pageNo, recordCount, sortFilterDto);

		assertThat(result.getParticipantDtoList().size()).isEqualTo(2);
	}

	@Test
	void testGetAllParticipantsWithStartAndEndDateFilter() throws Exception {

		startDateFilterDto.setFilter(startDateStr);
		endDateFilterDto.setFilter(endDateStr);

		map.put("startDateFilter", startDateFilterDto);
		map.put("endDateFilter", endDateFilterDto);
		sortFilterDto.setFilterModel(map);
		sortFilterDto.setData("dateFilter");

		PageRequest pageable = PageRequest.of(pageNo, recordCount, Sort.Direction.ASC, "points");

		when(dashboardRepository.findAllByAwardedDateBetween(startOfCurrentYear, endOfCurrentYear, pageable))
				.thenReturn(this.participants);

		PaginatedParticipantResponse result = dashboardService.getAllParticipants(pageNo, recordCount, sortFilterDto);

		assertThat(result.getParticipantDtoList().size()).isEqualTo(2);
	}

	@Test
	void testGetAllParticipantsWithFullYearFilter() throws Exception {
		yearFilterDto.setFilter(year);
		quarterFilterDto.setFilter("Full");

		map.put("yearFilter", yearFilterDto);
		map.put("quarterFilter", quarterFilterDto);
		sortFilterDto.setFilterModel(map);
		sortFilterDto.setData("yearFilter");

		PageRequest pageable = PageRequest.of(pageNo, recordCount, Sort.Direction.ASC, "points");
		when(dashboardRepository.findAllByAwardedDateBetween(startOfCurrentYear, endOfCurrentYear, pageable))
				.thenReturn(this.participants);

		PaginatedParticipantResponse result = dashboardService.getAllParticipants(pageNo, recordCount, sortFilterDto);
		assertThat(result.getParticipantDtoList().size()).isEqualTo(2);
	}

	@Test
	void testGetAllParticipantsWithQ1YearFilter() throws Exception {
		var endDate = LocalDate.parse("%s-03-31".formatted(year), DateTimeFormatter.ofPattern(DATE_FORMAT));

		yearFilterDto.setFilter(year);
		quarterFilterDto.setFilter("q1");

		map.put("yearFilter", yearFilterDto);
		map.put("quarterFilter", quarterFilterDto);
		sortFilterDto.setFilterModel(map);
		sortFilterDto.setData("yearFilter");

		PageRequest pageable = PageRequest.of(pageNo, recordCount, Sort.Direction.ASC, "points");
		when(dashboardRepository.findAllByAwardedDateBetween(startOfCurrentYear, endDate, pageable))
				.thenReturn(this.participants);

		PaginatedParticipantResponse result = dashboardService.getAllParticipants(pageNo, recordCount, sortFilterDto);
		assertThat(result.getParticipantDtoList().size()).isEqualTo(2);
	}

	@Test
	void testGetAllParticipantsWithQ2YearFilter() throws Exception {
		var startDate = LocalDate.parse("%s-04-01".formatted(year), DateTimeFormatter.ofPattern(DATE_FORMAT));
		var endDate = LocalDate.parse("%s-06-31".formatted(year), DateTimeFormatter.ofPattern(DATE_FORMAT));

		yearFilterDto.setFilter(year);
		quarterFilterDto.setFilter("q2");

		map.put("yearFilter", yearFilterDto);
		map.put("quarterFilter", quarterFilterDto);
		sortFilterDto.setFilterModel(map);
		sortFilterDto.setData("yearFilter");

		PageRequest pageable = PageRequest.of(pageNo, recordCount, Sort.Direction.ASC, "points");
		when(dashboardRepository.findAllByAwardedDateBetween(startDate, endDate, pageable))
				.thenReturn(this.participants);

		PaginatedParticipantResponse result = dashboardService.getAllParticipants(pageNo, recordCount, sortFilterDto);
		assertThat(result.getParticipantDtoList().size()).isEqualTo(2);
	}
	
	@Test
	void testGetAllParticipantsWithQ3YearFilter() throws Exception {
		var startDate = LocalDate.parse("%s-07-01".formatted(year), DateTimeFormatter.ofPattern(DATE_FORMAT));
		var endDate = LocalDate.parse("%s-09-30".formatted(year), DateTimeFormatter.ofPattern(DATE_FORMAT));
		
		yearFilterDto.setFilter(year);
		quarterFilterDto.setFilter("q3");

		map.put("yearFilter", yearFilterDto);
		map.put("quarterFilter", quarterFilterDto);
		sortFilterDto.setFilterModel(map);
		sortFilterDto.setData("yearFilter");

		PageRequest pageable = PageRequest.of(pageNo, recordCount, Sort.Direction.ASC, "points");
		when(dashboardRepository.findAllByAwardedDateBetween(startDate, endDate, pageable))
				.thenReturn(this.participants);

		PaginatedParticipantResponse result = dashboardService.getAllParticipants(pageNo, recordCount, sortFilterDto);
		assertThat(result.getParticipantDtoList().size()).isEqualTo(2);
	}
	
	@Test
	void testGetAllParticipantsWithQ4YearFilter() throws Exception {
		
		
		var startDate = LocalDate.parse("%s-10-01".formatted(year), DateTimeFormatter.ofPattern(DATE_FORMAT));
		var endDate = LocalDate.parse("%s-12-31".formatted(year), DateTimeFormatter.ofPattern(DATE_FORMAT));
		
		yearFilterDto.setFilter(year);
		quarterFilterDto.setFilter("q4");

		map.put("yearFilter", yearFilterDto);
		map.put("quarterFilter", quarterFilterDto);
		sortFilterDto.setFilterModel(map);
		sortFilterDto.setData("yearFilter");

		PageRequest pageable = PageRequest.of(pageNo, recordCount, Sort.Direction.ASC, "points");
		when(dashboardRepository.findAllByAwardedDateBetween(startDate, endDate, pageable))
				.thenReturn(this.participants);

		PaginatedParticipantResponse result = dashboardService.getAllParticipants(pageNo, recordCount, sortFilterDto);
		assertThat(result.getParticipantDtoList().size()).isEqualTo(2);
	}
	
}
