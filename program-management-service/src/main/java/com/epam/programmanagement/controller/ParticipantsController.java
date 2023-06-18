package com.epam.programmanagement.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.programmanagement.dto.ParticipantDto;
import com.epam.programmanagement.model.Program;
import com.epam.programmanagement.service.ParticipantService;
import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.validation.Valid;

/**
 * Participant Controller does the operations which are related to Program
 * Service such as Creating , reading , updating and deleting a program Program
 * 
 * Controller is annotated by two annotations:
 * 
 * @RestController - Combination of @Controller and @Responsebody Annotations.
 *                                    Marks this class as a bean.
 * @RequestMapping - Class level request mapping. 
 * 
 * @author Sneka_P
 *
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/programs/{programId}/participants")
public class ParticipantsController {
	@Autowired
	private ParticipantService participantService;
	private static Logger logger = LogManager.getLogger(ParticipantsController.class);
	/**
	 * The getParticipants() method its getting all the participant based on program
	 * id
	 * 
	 * @param programId is the foreign key of program table
	 * @return Set Of Participants
	 * @throws Exception 
	 */
	@GetMapping
	public ResponseEntity<List<ParticipantDto>> getParticipants(@PathVariable int programId) throws Exception {
		logger.info("Display all the programs {}",programId);
		return new ResponseEntity<>(participantService.getAllParticipants(programId), HttpStatus.OK);

	}

	/**
	 * The getParticipant() method getting the data based on programId and
	 * participant id
	 * 
	 * @param programId     is the foreign key of program table
	 * @param participantId is the foreign key of user table
	 * @return participantDto
	 * 
	 */
	@GetMapping("/{participantId}")
	public ResponseEntity<ParticipantDto> getParticipant(@PathVariable int programId, @PathVariable int participantId) {
		logger.info("Display all the Participant baesd on id {}",programId);
		return new ResponseEntity<>(participantService.getParticipant(programId, participantId), HttpStatus.OK);

	}

	/**
	 * The createParticipant() method create the new record in database
	 * 
	 * 
	 * @param participant details
	 * @param programId   is the foreign key of program table
	 * @return participant data
	 * @throws JsonProcessingException 
	 */
	@PostMapping
	public ResponseEntity<ParticipantDto> createParticipant(@Valid @RequestBody ParticipantDto participant,
			@PathVariable int programId) throws JsonProcessingException {
		logger.info("create the participant {} {}",programId,participant);
		Program program = participantService.getProgramById(programId);
		Set<Program> programs = new HashSet<>();
		programs.add(program);
		participant.setProgram(programs);
		return new ResponseEntity<>(participantService.saveParticipant(participant), HttpStatus.CREATED);

	}

	/**
	 * The deleteParticipant is deleting the participant based on programId and
	 * participantId
	 * 
	 * @param programId     is the foreign key of program table
	 * @param participantId is the foreign key of user table
	 * 
	 */

	@DeleteMapping("/{participantId}")
	public ResponseEntity<String> deleteParticipant(@PathVariable int programId,
			@PathVariable int participantId) {
		logger.info("delete the participant {} {}",programId,participantId);
		participantService.deleteParticipant(programId, participantId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

	}

	/**
	 * The updateParticipant() method update all the details of participant
	 * 
	 * @param programId      is the foreign key of program table
	 * @param participantId  is the foreign key of user table
	 * @param participantDto details
	 * 
	 */
	@PutMapping("/{participantId}")
	public ResponseEntity<ParticipantDto> updateParticipant(@Valid @PathVariable int programId,
			@PathVariable int participantId, @RequestBody ParticipantDto participantDto) {
		logger.info("update the participant {} {}",programId,participantId);
		logger.info("My Dto {}", participantDto);
		Program program = participantService.getProgramById(programId);
		Set<Program> programs = new HashSet<>();
		programs.add(program);
		participantDto.setProgram(programs);
		return new ResponseEntity<>(participantService.updateParticipant(programId, participantId, participantDto),
				HttpStatus.ACCEPTED);
	}
}
