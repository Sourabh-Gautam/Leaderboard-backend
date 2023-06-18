package com.epam.programmanagement.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.epam.programmanagement.ProgramManagement;
import com.epam.programmanagement.dto.ParticipantDto;
import com.epam.programmanagement.model.Program;
import com.epam.programmanagement.service.ParticipantService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The ProgramControllerTest class contains test cases related to
 * ProgramController. The class is annotated by 3 annotations.
 * 
 * @WebMvcTest annotation is used for unit testing of Spring MVC components.
 * @ContextConfiguration annotation loads an ApplicationContext.
 * @ExtendWith annotation  used to register extensions for the annotated test
 *             class.
 *
 * @author Sneka_P
 *
 */
@WebMvcTest(controllers = { ParticipantsController.class })
@ContextConfiguration(classes = { ProgramManagement.class })
@ExtendWith({ SpringExtension.class, MockitoExtension.class })
class ParticipantControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@InjectMocks
	private ParticipantsController participantController;
	@MockBean
	private ParticipantService participantService;
	@Autowired
	private ObjectMapper mapper;
	private ParticipantDto participantDto = new ParticipantDto();
	private Set<Program> programs = new HashSet<>();
	private Program program = new Program();
	List<Integer> organizers = new ArrayList<>();
	private List<ParticipantDto> participantDtos = new ArrayList<>();

	@BeforeEach()
	void setup() {
		organizers.add(1);
		organizers.add(2);
		organizers.add(3);

		programs.add(program);
		participantDto.setAddedBy("Alex");
		participantDto.setPoints(14);
		participantDto.setParticipantName("sneka");
		participantDto.setProgram(programs);
		participantDto.setParticipantId(2);
		program.setAddedBy("Vahini");
		program.setCreatedAt(LocalDateTime.now());
		program.setDescription("DataStructures");
		program.setEndDate(null);
		program.setStartDate(null);
		program.setProgramId(2);
		program.setTitle("DSA");
		program.setWeightage(15);
		participantDtos.add(participantDto);
	}

	/**
	 * createParticipantTest() method test the create method of participant
	 * controller
	 * 
	 * @throws Exception
	 */

	@Test
	void createParticipantTest() throws Exception

	{

		when(participantService.saveParticipant(any(ParticipantDto.class))).thenReturn(participantDto);
		MvcResult result = mockMvc.perform(post("/api/v1/programs/2/participants")
				.contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(participantDto)))
				.andDo(print()).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertThat(response.getStatus(), is(HttpStatus.CREATED.value()));
	}

	/**
	 * getParticipantByIdTest() method test getting the value of participant
	 * controller method
	 * 
	 * @throws Exception
	 */
	@Test
	void getParticipantByIdTest() throws Exception {
		when(participantService.getParticipant(anyInt(), anyInt())).thenReturn(participantDto);
		MvcResult result = mockMvc.perform(get("/api/v1/programs/2/participants/12")
				.contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(participantDto)))
				.andDo(print()).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertThat(response.getStatus(), is(HttpStatus.OK.value()));
	}

	/**
	 * getAllParticipantsTest() method test the getting the value of participant
	 * controller method
	 * 
	 * @throws Exception
	 */
	@Test
	void getAllParticipantsTest() throws Exception {
		when(participantService.getAllParticipants(anyInt())).thenReturn(participantDtos);
		MvcResult result = mockMvc.perform(get("/api/v1/programs/2/participants")
				.contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(participantDto)))
				.andDo(print()).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertThat(response.getStatus(), is(HttpStatus.OK.value()));
	}

	/**
	 * updateParticipantTest() method test the updating the value of participant
	 * controller method
	 * 
	 * @throws Exception
	 */
	@Test
	void updateParticipantTest() throws Exception {
		when(participantService.updateParticipant(2, 12, participantDto)).thenReturn(participantDto);
		MvcResult result = mockMvc.perform(put("/api/v1/programs/2/participants/12")
				.contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(participantDto)))
				.andDo(print()).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertThat(response.getStatus(), is(HttpStatus.ACCEPTED.value()));
	}
	/**
	 * The deleteProgramTest() tests the deleteProgram method of the ProgramController.
	 * It will return a status code of 204 i.e NO_CONTENT if the Program is deleted from the database.
	 * @throws Exception
	 */
	@Test
	void deleteParticipantTest() throws Exception {
		doNothing().when(participantService).deleteParticipant(1,1);
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/api/v1/programs/2/participants/12")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(mockRequest).andExpect(status().isNoContent());
		
	}
	
}
