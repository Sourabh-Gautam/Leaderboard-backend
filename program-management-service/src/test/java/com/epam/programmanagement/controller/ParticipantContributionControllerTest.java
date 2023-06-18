package com.epam.programmanagement.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.HashSet;
import java.util.Set;

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

import com.epam.programmanagement.ProgramManagement;
import com.epam.programmanagement.dto.ParticipantDto;
import com.epam.programmanagement.service.ParticipantService;

/**
 * The ParticipantContributionControllerTest class contains test cases related to
 * ParticipantContributionController. The class is annotated by 3 annotations.
 * 
 * @WebMvcTest annotation is used for unit testing of Spring MVC components.
 * @ContextConfiguration annotation loads an ApplicationContext.
 * @ExtendWith annotation  used to register extensions for the annotated test
 *             class.
 *
 * @author Bhargavi_Velugonda
 *
 */
@WebMvcTest(controllers = { ParticipantContributionController.class })
@ContextConfiguration(classes = { ProgramManagement.class })
@ExtendWith({ SpringExtension.class, MockitoExtension.class })
class ParticipantContributionControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@InjectMocks
	private ParticipantContributionController participantController;
	@MockBean
	private ParticipantService participantService;
	
	private ParticipantDto participantDto = new ParticipantDto();
	private Set<ParticipantDto> participantDtos = new HashSet<>();

	@BeforeEach()
	void setup() {

		participantDto.setAddedBy("Alex");
		participantDto.setPoints(14);
		participantDto.setBusinessUnit("India");
		participantDto.setEmail("sneka@epam.com");
		participantDto.setContributorType("Winner");
		participantDto.setDesignation("Software Engineer");
		participantDto.setPrimarySkill("Java");
		participantDto.setResourceManager("Shakti Vyas");
		participantDto.setParticipantName("sneka");
		participantDto.setParticipantId(2);
		participantDtos.add(participantDto);
	}

	
	/**
	 * getParticipantByEmailTest() method test getting the value of participantContribution
	 * controller method
	 * @throws Exception 
	 * 
	 */
	@Test
	void getParticipantByEmailTest() throws Exception{
		when(participantService.getParticipantByEmail(any(), anyInt())).thenReturn(participantDtos);
		MvcResult result = mockMvc.perform(get("/api/v1/participants-details/email/sneka/2023")
				.contentType(MediaType.APPLICATION_JSON))
				. andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertThat(response.getStatus(), is(HttpStatus.OK.value()));
	}
	
	/**
	 * getParticipantByDesignationTest() method test getting the value of participantContribution
	 * controller method
	 * @throws Exception 
	 * 
	 */
	@Test
	void getParticipantByDesignationTest() throws Exception{
		when(participantService.getParticipantByDesignation(any())).thenReturn(participantDtos);
		MvcResult result = mockMvc.perform(get("/api/v1/participants-details/designation/Junior Software Engineer")
				.contentType(MediaType.APPLICATION_JSON))
				. andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertThat(response.getStatus(), is(HttpStatus.OK.value()));
	}
	
	/**
	 * getParticipantByBUTest() method test getting the value of participantContribution
	 * controller method
	 * @throws Exception 
	 * 
	 */
	@Test
	void getParticipantByBUTest() throws Exception{
		when(participantService.getParticipantByBusinessUnit(any())).thenReturn(participantDtos);
		MvcResult result = mockMvc.perform(get("/api/v1/participants-details/bu/HCLS Delivery")
				.contentType(MediaType.APPLICATION_JSON))
				. andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertThat(response.getStatus(), is(HttpStatus.OK.value()));
	}
	
	/**
	 * getParticipantByPSTest() method test getting the value of participantContribution
	 * controller method
	 * @throws Exception 
	 * 
	 */
	@Test
	void getParticipantByPSTest() throws Exception{
		when(participantService.getParticipantByyPrimarySkill(any())).thenReturn(participantDtos);
		MvcResult result = mockMvc.perform(get("/api/v1/participants-details/ps/Java")
				.contentType(MediaType.APPLICATION_JSON))
				. andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertThat(response.getStatus(), is(HttpStatus.OK.value()));
	}
	
	/**
	 * getParticipantByRMTest() method test getting the value of participantContribution
	 * controller method
	 * @throws Exception 
	 * 
	 */
	@Test
	void getParticipantByRMTest() throws Exception{
		when(participantService.getParticipantByResourceManager(any())).thenReturn(participantDtos);
		MvcResult result = mockMvc.perform(get("/api/v1/participants-details/rm/Shakti Vyas")
				.contentType(MediaType.APPLICATION_JSON))
				. andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertThat(response.getStatus(), is(HttpStatus.OK.value()));
	}


	


}
