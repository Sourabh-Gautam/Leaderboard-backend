//package com.epam.leaderboard.service.filter;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.when;
//
//import java.time.LocalDate;
//import java.time.Year;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//
//import com.epam.leaderboard.dto.ConditionDto;
//import com.epam.leaderboard.dto.FilterDto;
//import com.epam.leaderboard.dto.PaginatedParticipantResponse;
//import com.epam.leaderboard.dto.SortFilterDto;
//import com.epam.leaderboard.entity.Participant;
//import com.epam.leaderboard.repo.DashboardRepository;
//import com.epam.leaderboard.service.DashboardServiceImpl;
//
//public class ParticipantNameFilterServiceTest {
//	@InjectMocks
//	private DashboardServiceImpl dashboardService;
//
//	@Mock
//	private DashboardRepository dashboardRepository;
//
//	List<Participant> participants;
//	
//	int pageNo = 0;
//	int recordCount = 5;
//	
//	private static final String DATE_FORMAT = "yyyy-MM-dd";
//	LocalDate startOfCurrentYear = LocalDate.parse("%s-01-01".formatted(Year.now().getValue()),
//			DateTimeFormatter.ofPattern(DATE_FORMAT));
//	LocalDate endOfCurrentYear = startOfCurrentYear.plusYears(1).minusDays(1);
//	String startDateStr = "2023-01-01";
//	String endDateStr = "2023-12-31";
//	String year = "2023";
//	String[] quarter = {"q1", "q2", "q3", "q4"}; 
//	
//	HashMap<String, FilterDto> map;
//	SortFilterDto sortFilterDto;
//	
//	FilterDto filterDto;
//	FilterDto startDateFilterDto;
//	FilterDto endDateFilterDto;
//	FilterDto yearFilterDto;
//	FilterDto quarterFilterDto;
//	
//	ConditionDto conditionDto1;
//	ConditionDto conditionDto2;
//	
//	@BeforeEach
//	void setup() {
//		participants = new ArrayList<>();
//		
//		Participant participant = new Participant();
//		participant.setParticipantName("Sourabh");
//		participant.setParticipantId(1);
//		participant.setAddedBy("sourabh");
//		participant.setAwardedDate(null);
//		participant.setBusinessUnit("SCL Delivery");
//		participant.setDesignation("JSE");
//		participant.setPrimarySkill("Java");
//		participant.setSubSkill("[\"Angular\"]");
//		participant.setResourceManager("Shakti Vyas");
//		
//		Participant participant2 = new Participant();
//		participant2.setParticipantName("Divyam");
//		participant2.setParticipantId(2);
//		participant2.setAddedBy("sourabh");
//		participant2.setAwardedDate(null);
//		participant2.setBusinessUnit("SCL Delivery");
//		participant2.setDesignation("JSE");
//		participant2.setPrimarySkill("Java");
//		participant2.setSubSkill("[\"Angular\"]");
//		participant2.setResourceManager("Shakti Vyas");
//		
//		participants.add(participant);
//		participants.add(participant2);
//		
//		map = new HashMap<>();
//		
//		sortFilterDto = new SortFilterDto();
//		sortFilterDto.setColId("points");
//		sortFilterDto.setData(null);
//		sortFilterDto.setSort("asc");
//		sortFilterDto.setFilterModel(map);
//		
//		filterDto = new FilterDto();
//		
//		filterDto.setFilter("test");
//		filterDto.setFilterType("text");
//		filterDto.setOperator(null);
//		filterDto.setCondition1(null);
//		filterDto.setCondition2(null);
//		
//		startDateFilterDto = new FilterDto();
//		startDateFilterDto.setFilterType("date");
//		startDateFilterDto.setType("greaterThan");
//		
//		endDateFilterDto = new FilterDto();
//		endDateFilterDto.setFilterType("date");
//		endDateFilterDto.setType("lessThan");
//		
//		yearFilterDto = new FilterDto();
//		yearFilterDto.setFilterType("date");
//		yearFilterDto.setType("lessThan");
//		
//		quarterFilterDto = new FilterDto();
//		quarterFilterDto.setFilterType("date");
//		quarterFilterDto.setType("lessThan");
//		
//		conditionDto1 = new ConditionDto();
//		conditionDto1.setFilterType("text");
//		conditionDto1.setType("contains");
//		z
//		conditionDto2 = new ConditionDto();
//		conditionDto2.setFilterType("text");
//		conditionDto2.setType("contains");
//		
//	}
//	

//}
