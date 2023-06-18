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
import com.epam.programmanagement.dto.ContributorTypeDto;
import com.epam.programmanagement.service.ContributorTypeService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The ContributorTypeControllerTest class contains test cases related to ContributorTypeController.
 * The class is annotated by 3 annotations.
 * @WebMvcTest annotation is used for unit testing of Spring MVC components.
 * @ContextConfiguration annotation loads an ApplicationContext.
 * @ExtendWith annotation  used to register extensions for the annotated test class.
 * 
 * @author Bhargavi_Velugonda
 *
 */
@WebMvcTest(controllers = { ContributorTypeController.class})
@ContextConfiguration(classes = { ProgramManagement.class })
@ExtendWith({SpringExtension.class,MockitoExtension.class})
class ContributorTypeControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	@InjectMocks
	private ContributorTypeController contributorTypeController;
	@MockBean
	private ContributorTypeService contributorTypeService;
	
	private ContributorTypeDto contributorTypeDto = new ContributorTypeDto();
	
	private List<ContributorTypeDto> contributorTypesDto = new ArrayList<>();
	
	@Autowired
	private ObjectMapper mapper;
	
	/**
	 * The setup() method is basically executed before executing every single test case.
	 */
	@BeforeEach
	public void setup() {
		
		
		contributorTypeDto.setContributerType("Winner");
		contributorTypeDto.setId(1);
		contributorTypeDto.setPoints(50);
		contributorTypesDto.add(contributorTypeDto);
	
	}
	
	/**
	 * The createContributorTypeTest() tests the createContributorType method of the ContributorTypeController.
	 * It will return a status code of 201 i.e CREATED if the ContributorType is inserted in the database.
	 * @throws Exception
	 */
	@Test
	void createContributorTypeTest() throws Exception {

		
		when(contributorTypeService.saveContributorType(any(ContributorTypeDto.class))).thenReturn(contributorTypeDto);

		MvcResult result = mockMvc.perform(
				post("/api/v1/contributorTypes").contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(contributorTypeDto)))
				.andDo(print()).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertThat(response.getStatus(), is(HttpStatus.CREATED.value()));
		

	}
	
	/**
	 * The getContributorTypesTest() tests the getContributorTypes method of the ContributorTypeController.
	 * It will return a status code of 200 i.e OK if it retrieves all the contributorTypes from the database.
	 * @throws Exception
	 */
	@Test
	void getContributorTypesTest() throws Exception {

		
		when(contributorTypeService.getAllContributorTypes()).thenReturn(contributorTypesDto);

		MvcResult result = mockMvc.perform(
				get("/api/v1/contributorTypes").contentType(MediaType.APPLICATION_JSON).
				content(this.mapper.writeValueAsString(contributorTypeDto)))
				.andDo(print()).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertThat(response.getStatus(), is(HttpStatus.OK.value()));

	}
	
	/**
	 * The getContributorTypeByIdTest() tests the getContributorType method of the ContributorTypeController.
	 * It will return a status code of 200 i.e OK if the ContributorType is retrieved from the database.
	 * @throws Exception
	 */
	@Test
	void getContributorTypeByIdTest() throws Exception {

		
		when(contributorTypeService.getContributorType(anyInt())).thenReturn(contributorTypeDto);

		MvcResult result = mockMvc.perform(
				get("/api/v1/contributorTypes/1").contentType(MediaType.APPLICATION_JSON).
				content(this.mapper.writeValueAsString(contributorTypeDto)))
				.andDo(print()).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertThat(response.getStatus(), is(HttpStatus.OK.value()));

	}
	/**
	 * The updateContributorTypeTest() tests the updateContributorType method of the ContributorTypeController.
	 * It will return a status code of 202 i.e ACCEPTED if the ContributorType is updated in the database.
	 * @throws Exception
	 */
	@Test
	void updateContributorTypeTest() throws Exception {

		
		when(contributorTypeService.updateContributorType(anyInt(),any(ContributorTypeDto.class))).thenReturn(contributorTypeDto);

		MvcResult result = mockMvc.perform(
				put("/api/v1/contributorTypes/1").contentType(MediaType.APPLICATION_JSON).
				content(this.mapper.writeValueAsString(contributorTypeDto)))
				.andDo(print()).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertThat(response.getStatus(), is(HttpStatus.ACCEPTED.value()));

	}
	/**
	 * The deleteContributorTypeTest() tests the deleteContributorType method of the ContributorTypeController.
	 * It will return a status code of 204 i.e NO_CONTENT if the ContributorType is deleted from the database.
	 * @throws Exception
	 */
	@Test
	void deleteContributorTypeTest() throws Exception {

		
		when(contributorTypeService.deleteContributorType(anyInt())).thenReturn(contributorTypeDto);

		MvcResult result = mockMvc.perform(
				delete("/api/v1/contributorTypes/1").contentType(MediaType.APPLICATION_JSON).
				content(this.mapper.writeValueAsString(contributorTypeDto)))
				.andDo(print()).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertThat(response.getStatus(), is(HttpStatus.NO_CONTENT.value()));

	}

}
