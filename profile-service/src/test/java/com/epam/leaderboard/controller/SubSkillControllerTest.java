package com.epam.leaderboard.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.epam.leaderboard.ProfileServiceApplication;
import com.epam.leaderboard.dto.SkillsDTO;
import com.epam.leaderboard.dto.SubSkillDTO;
import com.epam.leaderboard.service.SubSkillService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class is meant to test every functionality provided into
 * SubSkillController
 * 
 * @author Bhargavi_Velugonda
 *
 */
@WebMvcTest(controllers = { SubSkillController.class})
@ContextConfiguration(classes = { ProfileServiceApplication.class })
@ExtendWith({SpringExtension.class,MockitoExtension.class})
class SubSkillControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@InjectMocks
	private SubSkillController SkillController;
	@MockBean
	private SubSkillService SkillService;
	
	private SubSkillDTO SkillDTO = new SubSkillDTO();
	
	private List<SubSkillDTO> SkillsDTO = new ArrayList<>();
	
	@Autowired
	private ObjectMapper mapper;
	
	/**
	 * The setup() method is basically executed before executing every single test case.
	 */
	@BeforeEach
	public void setup() {
		
		
		SkillDTO.setSubSkillName("AWS");
		SkillDTO.setId(1);
		SkillsDTO.add(SkillDTO);
	
	}
	
	/**
	 * The getSkillsTest() tests the getSkills method of the SkillController.
	 * It will return a status code of 200 i.e OK if it retrieves all the Skills from the database.
	 * @throws Exception
	 */
	@Test
	void getSkillsTest() throws Exception {

		
		when(SkillService.getAllSkillss()).thenReturn(SkillsDTO);

		MvcResult result = mockMvc.perform(
				get("/api/v1/profiles/SubSkills").contentType(MediaType.APPLICATION_JSON).
				content(this.mapper.writeValueAsString(SkillDTO)))
				.andDo(print()).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertThat(response.getStatus(), is(HttpStatus.OK.value()));

	}

}
