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
import com.epam.programmanagement.dto.ProgramTemplateDto;
import com.epam.programmanagement.service.ProgramTemplateService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The ProgramTemplateControllerTest class contains test cases related to ProgramTemplateController.
 * The class is annotated by 3 annotations.
 * @WebMvcTest annotation is used for unit testing of Spring MVC components.
 * @ContextConfiguration annotation loads an ApplicationContext.
 * @ExtendWith annotation  used to register extensions for the annotated test class.
 * @author Sneka_P
 *
 */
@WebMvcTest(controllers = { ProgramTemplateController.class})
@ContextConfiguration(classes = { ProgramManagement.class })
@ExtendWith({SpringExtension.class,MockitoExtension.class})
class ProgramTemplateTest {
	@Autowired
	private MockMvc mockMvc;
	@InjectMocks
	private ProgramTemplateController programTemplateController;
	@MockBean
	private ProgramTemplateService programTemplateService;
	
	private ProgramTemplateDto programTemplateDto = new ProgramTemplateDto();
	
	private List<ProgramTemplateDto> listProgramTemplateDto = new ArrayList<>();
	
	@Autowired
	private ObjectMapper mapper;
	
	/**
	 * The setup() method is basically executed before executing every single test case.
	 */
	@BeforeEach
	public void setup() {
		
		programTemplateDto.setCategory("Dsa");
		programTemplateDto.setDescription("jfksae");
		programTemplateDto.setProgramTemplateId(1);
		programTemplateDto.setWeightage(6.5f);
		listProgramTemplateDto.add(programTemplateDto);
	}
	
	/**
	 * The createProgramTemplateTest() tests the createProgramTemplate method of the ProgramTemplateController.
	 * It will return a status code of 201 i.e CREATED if the ProgramTemplate is inserted in the database.
	 * @throws Exception
	 */
	@Test
	void createProgramTemplateTest() throws Exception {

		
		when(programTemplateService.saveProgramTemplate(any(ProgramTemplateDto.class))).thenReturn(programTemplateDto);

		MvcResult result = mockMvc.perform(
				post("/api/v1/programTemplates").contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(programTemplateDto)))
				.andDo(print()).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertThat(response.getStatus(), is(HttpStatus.CREATED.value()));
		

	}
	
	/**
	 * The getProgramTemplateTest() tests the getProgramTemplate method of the ProgramTemplateController.
	 * It will return a status code of 200 i.e OK if it retrieves all the programTemplate from the database.
	 * @throws Exception
	 */
	@Test
	void getProgramTemplateTest() throws Exception {

		
		when(programTemplateService.getAllProgramTemplates()).thenReturn(listProgramTemplateDto);

		MvcResult result = mockMvc.perform(
				get("/api/v1/programTemplates").contentType(MediaType.APPLICATION_JSON).
				content(this.mapper.writeValueAsString(programTemplateDto)))
				.andDo(print()).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertThat(response.getStatus(), is(HttpStatus.OK.value()));

	}
	
	/**
	 * The getProgramTemplateByIdTest() tests the getProgramTemplate method of the ProgramTemplateController.
	 * It will return a status code of 200 i.e OK if the ProgramTemplate is retrieved from the database.
	 * @throws Exception
	 */
	@Test
	void getProgramTemplateByIdTest() throws Exception {

		
		when(programTemplateService.getProgramTemplate(anyInt())).thenReturn(programTemplateDto);

		MvcResult result = mockMvc.perform(
				get("/api/v1/programTemplates/1").contentType(MediaType.APPLICATION_JSON).
				content(this.mapper.writeValueAsString(programTemplateDto)))
				.andDo(print()).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertThat(response.getStatus(), is(HttpStatus.OK.value()));

	}
	/**
	 * The updateProgramTemplateTest() tests the updateProgramTemplate method of the ProgramTemplateController.
	 * It will return a status code of 202 i.e ACCEPTED if the Program is updated in the database.
	 * @throws Exception
	 */
	@Test
	void updateProgramTemplateTest() throws Exception {

		
		when(programTemplateService.updateProgramTemplate(anyInt(),any(ProgramTemplateDto.class))).thenReturn(programTemplateDto);

		MvcResult result = mockMvc.perform(
				put("/api/v1/programTemplates/1").contentType(MediaType.APPLICATION_JSON).
				content(this.mapper.writeValueAsString(programTemplateDto)))
				.andDo(print()).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertThat(response.getStatus(), is(HttpStatus.ACCEPTED.value()));

	}
	/**
	 * The deleteProgramTemplateTest() tests the deleteProgramTemplate method of the ProgramTemplateController.
	 * It will return a status code of 204 i.e NO_CONTENT if the ProgramTemplate is deleted from the database.
	 * @throws Exception
	 */
	@Test
	void deleteProgramTest() throws Exception {

		
		when(programTemplateService.deleteProgramTemplate(anyInt())).thenReturn(programTemplateDto);

		MvcResult result = mockMvc.perform(
				delete("/api/v1/programTemplates/1").contentType(MediaType.APPLICATION_JSON).
				content(this.mapper.writeValueAsString(programTemplateDto)))
				.andDo(print()).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertThat(response.getStatus(), is(HttpStatus.NO_CONTENT.value()));

	}
}
