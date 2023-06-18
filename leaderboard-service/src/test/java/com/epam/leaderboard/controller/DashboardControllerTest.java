package com.epam.leaderboard.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.epam.leaderboard.LeaderboardApplication;
import com.epam.leaderboard.dto.PaginatedParticipantResponse;
import com.epam.leaderboard.dto.ParticipantDto;
import com.epam.leaderboard.dto.SortFilterDto;
import com.epam.leaderboard.service.DashboardService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@WebMvcTest(controllers = DashboardController.class)
@ContextConfiguration(classes = { LeaderboardApplication.class })
@ExtendWith({SpringExtension.class, MockitoExtension.class})
class DashboardControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	private DashboardController dashboardController;
	
	@MockBean
	private DashboardService dashboardService;

	

	

	@Test
	void testGetParticipantsReturnsListOfParticipants() throws Exception {
		// Arrange
		List<ParticipantDto> participantList = new ArrayList<>();
		ParticipantDto participant1 = new ParticipantDto();
		participant1.setParticipantId(1);
		participant1.setParticipantName("John Doe");
		participantList.add(participant1);
		
//		FilterDto filterDto = new FilterDto();
//		filterDto.setFilter("f");
//		filterDto.setFilterType("text");
//		filterDto.setOperator("and");
//		filterDto.setType("equals");
//		filterDto.setCondition1(null);
//		filterDto.setCondition2(null);
		
//		HashMap<String, FilterDto> map = new HashMap<>();
//		map.put("participantsName", filterDto);

		SortFilterDto sortFilterDto = new SortFilterDto();
		sortFilterDto.setColId("points");
		sortFilterDto.setSort("asc");
		sortFilterDto.setData(null);
		sortFilterDto.setFilterModel(null);

		String json = this.mapToJson(sortFilterDto);

		PaginatedParticipantResponse paginatedParticipantResponse = new PaginatedParticipantResponse();
		paginatedParticipantResponse.setNumberOfPages(0);
		paginatedParticipantResponse.setNumberOfParticipants(0);
		paginatedParticipantResponse.setParticipantDtoList(participantList);
		
		when(dashboardService.getAllParticipants(0, 1, sortFilterDto)).thenReturn(paginatedParticipantResponse);
		// Act
		MvcResult mvcResult = mockMvc
				.perform(post("/api/v1/dashboard/0/1").contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
				.andReturn();

		// Assert
		String jsonOutput = mvcResult.getResponse().getContentAsString();
		
		PaginatedParticipantResponse response = this.mapToDto(jsonOutput);
		assertThat(response.getNumberOfPages()).isZero();
		assertThat(response.getNumberOfParticipants()).isZero();
	}

	private String mapToJson(SortFilterDto dto) throws JsonProcessingException {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		return ow.writeValueAsString(dto);
	}
	
	PaginatedParticipantResponse mapToDto(String json) throws JsonProcessingException {
		ObjectMapper ow = new ObjectMapper();
		return ow.readValue(json, PaginatedParticipantResponse.class);
	}

}
