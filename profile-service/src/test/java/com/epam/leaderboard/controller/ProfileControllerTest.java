package com.epam.leaderboard.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.epam.leaderboard.dto.ErrorDTO;
import com.epam.leaderboard.dto.ProfileDTO;
import com.epam.leaderboard.entity.Profile;
import com.epam.leaderboard.service.ProfileService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * This class is meant to test every functionality provided into
 * ProfileController
 * 
 * @author Sourabh_Gautam
 *
 */

@WebMvcTest(controllers = ProfileController.class)
class ProfileControllerTest {

	@MockBean
	private ProfileService profileService;

	@Autowired
	private MockMvc mockMvc;

	ProfileDTO profileDTO;
	ProfileDTO profileFailedDTO;
	Profile profile;
	Profile profileFailed;
	ErrorDTO errorDTO;
	List<String> subSkills = new ArrayList<>();

	@BeforeEach
	void init() {
		subSkills.add("AWS");
		subSkills.add("Microservices");
		profileDTO = new ProfileDTO(1, "Sourabh", "SDE", false, "2023-03-16","Java",subSkills,"HCLS Delivery","Shakti Vyas", "sourabhgoutam@gmail.com");
		profileFailedDTO = new ProfileDTO(1, "", "SDE", false, "2023-03-16","Java",subSkills,"HCLS Delivery","Shakti Vyas", "sourabhgoutam@gmail.com");
		profile = new Profile(1, "Sourabh", "SDE", false,"2023-03-16","Java",subSkills,"HCLS Delivery","Shakti Vyas", "sourabhgoutam@gmail.com");
		profileFailed = new Profile(1, "", "SDE", false,"2023-03-16","Java",subSkills,"HCLS Delivery","Shakti Vyas", "sourabhgoutam@gmail.com");
		errorDTO = new ErrorDTO("message", "description", "timestamp");
	}

	/**
	 * Method to test insertion of profile into db
	 * 
	 * @throws Exception when unable to convert ProfileDTO to JSON string
	 */

	@Test
	void testAddProfile() throws Exception {

		when(profileService.insertNewProfile(profile)).thenReturn(profile);

		String uri = "/api/v1/profiles";
		String json = mapToJson(profileDTO);

		MvcResult mvcResult = mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
				.andReturn();

		MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
		String jsonOutput = mockHttpServletResponse.getContentAsString();

		assertThat(jsonOutput).isNotNull().isNotBlank();
		assertEquals(HttpStatusCode.valueOf(201).value(), mockHttpServletResponse.getStatus());
	}
	
	@Test
	void testAddProfileFailed() throws Exception {

		when(profileService.insertNewProfile(profileFailed)).thenReturn(profileFailed);

		String uri = "/api/v1/profiles";
		String json = mapToJson(profileFailedDTO);

		MvcResult mvcResult = mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
				.andReturn();

		MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
		String jsonOutput = mockHttpServletResponse.getContentAsString();
		ErrorDTO errorDto = this.mapToErrorDto(jsonOutput);
		assertThat(errorDto.getMessage()).isNotBlank();
		assertEquals(HttpStatusCode.valueOf(404).value(), mockHttpServletResponse.getStatus());
		
		
	}

	/**
	 * Method to test updation of profile into db
	 * 
	 * @throws Exception when unable to convert ProfileDTO to JSON string and when
	 *                   given profile id doesn't exist
	 */

	@Test
	void testUpdateProfile() throws Exception {

		when(profileService.updateProfileById(1, profile)).thenReturn(profile);

		String uri = "/api/v1/profiles/1";
		String json = mapToJson(profileDTO);

		MvcResult mvcResult = mockMvc.perform(put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
				.andReturn();

		MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
		String jsonOutput = mockHttpServletResponse.getContentAsString();

		ProfileDTO responseDTO = mapToDto(jsonOutput);
		assertThat(profileDTO.getName()).isEqualTo(responseDTO.getName());
		assertEquals(HttpStatusCode.valueOf(200).value(), mockHttpServletResponse.getStatus());
	}
	
	@Test
	void testUpdateProfileFailed() throws Exception {

		when(profileService.updateProfileById(1, profileFailed)).thenReturn(profileFailed);

		String uri = "/api/v1/profiles/1";
		String json = mapToJson(profileFailedDTO);

		MvcResult mvcResult = mockMvc.perform(put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
				.andReturn();

		MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
		String jsonOutput = mockHttpServletResponse.getContentAsString();
		
		ErrorDTO errorDto = this.mapToErrorDto(jsonOutput);
		assertThat(errorDto.getMessage()).isNotBlank();
		assertEquals(HttpStatusCode.valueOf(404).value(), mockHttpServletResponse.getStatus());

	}

	/**
	 * Method to test `get an profile object using profile id`
	 * 
	 * @throws Exception when unable to convert ProfileDTO to JSON string and when
	 *                   given profile id doesn't exist
	 */

	@Test
	void testGetOneProfile() throws Exception {

		when(profileService.getProfileById(1)).thenReturn(profile);

		String uri = "/api/v1/profiles/1";
		String json = mapToJson(profileDTO);

		MvcResult mvcResult = mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
				.andReturn();

		MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
		String jsonOutput = mockHttpServletResponse.getContentAsString();

		ProfileDTO responseDTO = mapToDto(jsonOutput);
		assertThat(responseDTO.getName()).isEqualTo(profileDTO.getName());
		assertEquals(HttpStatusCode.valueOf(200).value(), mockHttpServletResponse.getStatus());
	}

	/**
	 * Method to test `get all profile object`
	 * 
	 * @throws Exception when unable to convert ProfileDTO to JSON string and when
	 *                   given profile id doesn't exist
	 */

	@Test
	void testGetAllProfile() throws Exception {

		when(profileService.getAllProfiles()).thenReturn(List.of(profile));

		String uri = "/api/v1/profiles";
		String json = mapToJson(profileDTO);

		MvcResult mvcResult = mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
				.andReturn();

		MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
		String jsonOutput = mockHttpServletResponse.getContentAsString();

		assertThat(jsonOutput).isNotBlank();
		assertEquals(HttpStatusCode.valueOf(200).value(), mockHttpServletResponse.getStatus());
	}

	/**
	 * Method to test `delete an profile object using profile id`
	 * 
	 * @throws Exception when unable to convert ProfileDTO to JSON string and when
	 *                   given profile id doesn't exist
	 */

	@Test
	void testDeleteProfile() throws Exception {

		doNothing().when(profileService).deleteById(1);
		String uri = "/api/v1/profiles/1";

		MvcResult mvcResult = mockMvc.perform(delete(uri).contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();

		assertEquals(HttpStatusCode.valueOf(204).value(), mockHttpServletResponse.getStatus());
	}

	/**
	 * Method to convert a ProfileDTO object to JSON object
	 * 
	 * @param profileDTO the ProfileDTO object
	 * @return the JSON string
	 * @throws JsonProcessingException when unable to convert the given ProfileDTO
	 *                                 object to JSON
	 */

	private String mapToJson(ProfileDTO profileDTO) throws JsonProcessingException {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(profileDTO);
		return json;
	}

	/**
	 * Method to convert a JSON object to ProfileDTO object
	 * 
	 * @param profileJSON the JSON string
	 * @return the ProfileDTO object
	 * @throws JsonProcessingException when unable to convert the given JSON string
	 *                                 to ProfileDTO object
	 */

	ProfileDTO mapToDto(String profileJSON) throws JsonProcessingException {
		ObjectMapper ow = new ObjectMapper();
		ProfileDTO profileDTO = ow.readValue(profileJSON, ProfileDTO.class);
		return profileDTO;
	}
	
	ErrorDTO mapToErrorDto(String errorJSON) throws JsonProcessingException {
		ObjectMapper ow = new ObjectMapper();
		ErrorDTO errorDTO = ow.readValue(errorJSON, ErrorDTO.class);
		return errorDTO;
	}
	

}
