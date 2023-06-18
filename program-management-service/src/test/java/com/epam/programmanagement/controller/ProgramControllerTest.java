package com.epam.programmanagement.controller;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.time.LocalDateTime;
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

import com.epam.programmanagement.ProgramManagement;
import com.epam.programmanagement.dto.ProgramDto;
import com.epam.programmanagement.service.ProgramService;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * The ProgramControllerTest class contains test cases related to ProgramController.
 * The class is annotated by 3 annotations.
 * @WebMvcTest annotation is used for unit testing of Spring MVC components.
 * @ContextConfiguration annotation loads an ApplicationContext.
 * @ExtendWith annotation  used to register extensions for the annotated test class.
 * 
 * @author Vahini_Nune
 *
 */
@WebMvcTest(controllers = { ProgramController.class})
@ContextConfiguration(classes = { ProgramManagement.class })
@ExtendWith({SpringExtension.class,MockitoExtension.class})
class ProgramControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@InjectMocks
	private ProgramController programController;
	@MockBean
	private ProgramService programService;
	
	private ProgramDto programDto = new ProgramDto();
	
	private List<ProgramDto> programsDto = new ArrayList<>();
	private List<Integer> organizers = new ArrayList<>();
	
	@Autowired
	private ObjectMapper mapper;
	
	/**
	 * The setup() method is basically executed before executing every single test case.
	 */
	@BeforeEach
	public void setup() {
		organizers.add(1);
		 organizers.add(2);
		organizers.add(3);
		
		
		programDto.setAddedBy("Vahini");
		programDto.setCreatedAt(LocalDateTime.now());
		programDto.setDescription("DataStructures");
		programDto.setEndDate(null);
		programDto.setStartDate(null);
		programDto.setProgramId(2);
		programDto.setTitle("DSA");
		programDto.setWeightage(15);
		programDto.setCategory("Incubation");
		programsDto.add(programDto);
	
	}
	
	/**
	 * The createProgramTest() tests the createProgram method of the ProgramController.
	 * It will return a status code of 201 i.e CREATED if the Program is inserted in the database.
	 * @throws Exception
	 */
	@Test
	void createProgramTest() throws Exception {

		
		when(programService.saveProgram(any(ProgramDto.class))).thenReturn(programDto);

		MvcResult result = mockMvc.perform(
				post("/api/v1/programs").contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(programDto)))
				.andDo(print()).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertThat(response.getStatus(), is(HttpStatus.CREATED.value()));
		

	}
	
	/**
	 * The getProgramsTest() tests the getPrograms method of the ProgramController.
	 * It will return a status code of 200 i.e OK if it retrieves all the programs from the database.
	 * @throws Exception
	 */
	@Test
	void getProgramsTest() throws Exception {

		
		when(programService.getAllPrograms()).thenReturn(programsDto);

		MvcResult result = mockMvc.perform(
				get("/api/v1/programs").contentType(MediaType.APPLICATION_JSON).
				content(this.mapper.writeValueAsString(programDto)))
				.andDo(print()).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertThat(response.getStatus(), is(HttpStatus.OK.value()));

	}
	
	/**
	 * The getProgramByIdTest() tests the getProgram method of the ProgramController.
	 * It will return a status code of 200 i.e OK if the Program is retrieved from the database.
	 * @throws Exception
	 */
	@Test
	void getProgramByIdTest() throws Exception {

		
		when(programService.getProgram(anyInt())).thenReturn(programDto);

		MvcResult result = mockMvc.perform(
				get("/api/v1/programs/1").contentType(MediaType.APPLICATION_JSON).
				content(this.mapper.writeValueAsString(programDto)))
				.andDo(print()).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertThat(response.getStatus(), is(HttpStatus.OK.value()));

	}
	/**
	 * The updateProgramTest() tests the updateProgram method of the ProgramController.
	 * It will return a status code of 202 i.e ACCEPTED if the Program is updated in the database.
	 * @throws Exception
	 */
	@Test
	void updateProgramTest() throws Exception {

		
		when(programService.updateProgram(anyInt(),any(ProgramDto.class))).thenReturn(programDto);

		MvcResult result = mockMvc.perform(
				put("/api/v1/programs/1").contentType(MediaType.APPLICATION_JSON).
				content(this.mapper.writeValueAsString(programDto)))
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
	void deleteProgramTest() throws Exception {

		
		when(programService.deleteProgram(anyInt())).thenReturn(programDto);

		MvcResult result = mockMvc.perform(
				delete("/api/v1/programs/1").contentType(MediaType.APPLICATION_JSON).
				content(this.mapper.writeValueAsString(programDto)))
				.andDo(print()).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertThat(response.getStatus(), is(HttpStatus.NO_CONTENT.value()));

	}
	
}
