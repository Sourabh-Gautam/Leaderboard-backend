package com.epam.programmanagement.controller;

import java.util.List;

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

import com.epam.programmanagement.dto.ProgramDto;
import com.epam.programmanagement.exception.ProgramNotFoundException;
import com.epam.programmanagement.service.ProgramService;

import jakarta.validation.Valid;

/**
 * Program Controller does the operations which are related to Program Service such as 
 * creating , reading , updating and deleting a program.
 * 
 * Program Controller is annotated by two annotations:
 * @RestController - Combination of @Controller and @Responsebody Annotations.
 *                   Marks this class as a bean.
 * @RequestMapping - Class level request mapping.                  
 * 
 * @author Vahini_Nune
 *
 */


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/programs")
public class ProgramController {
	@Autowired
	private ProgramService programService;
	private static Logger logger = LogManager.getLogger(ProgramController.class);

	/**
	 * The getPrograms() method is to retrieve all the programs present in the database.
	 * It gives a status code of 200 i.e OK.
	 * @return List of Programs
	 */
	@GetMapping
	public ResponseEntity<List<ProgramDto>> getPrograms() {
         
		logger.info("Display all the Programs");
		
		return new ResponseEntity<>(programService.getAllPrograms(), HttpStatus.OK);

	}
	
	/**
	 * The getProgram() method returns a program based on the programId.
	 * It gives a status code of 200 i.e OK.
	 * @param programId
	 * @return Program details
	 */

	@GetMapping("/{programId}")
	public ResponseEntity<ProgramDto> getProgram(@PathVariable int programId) {
		logger.info("Program Details for the program id {}",programId);

		return new ResponseEntity<>(programService.getProgram(programId), HttpStatus.OK);

	}
  
	/**
	 * The createProgram() method creates a new program in the database based on the given data.
	 * It gives a status code of 201 i.e CREATED.
	 * @param program
	 * @return inserted Program data.
	 */
	@PostMapping
	public ResponseEntity<ProgramDto> createProgram(@Valid @RequestBody ProgramDto program) {
		
		logger.info("Creating a Program with the details {}",program);

		return new ResponseEntity<>(programService.saveProgram(program), HttpStatus.CREATED);

	}
 
	/**
	 * The updateProgram() method updates the Program present in the database with given update details.
	 * It gives a status code of 202 i.e ACCEPTED.
	 * @param programId
	 * @param programDto
	 * @return Updated Program Data
	 * @throws ProgramNotFoundException if the programId doesn't exist.
	 */
	@PutMapping("/{programId}")
	public ResponseEntity<ProgramDto> updateProgram(@PathVariable int programId,
			@Valid @RequestBody ProgramDto programDto) throws ProgramNotFoundException{
		
		logger.info("Update Program with program id {}{}",programId, programDto);
	
		return new ResponseEntity<>(programService.updateProgram(programId, programDto), HttpStatus.ACCEPTED);
	}
  /**
   * The deleteProgram() method delete the Program present in the database with given programId.
   * It gives a status code of 2004 i.e NO_CONTENT.
   * @param programId
   * @return void
   * @throws ProgramNotFoundException if the programId doesn't exist.
   */
	
	@DeleteMapping("/{programId}")
	public ResponseEntity<ProgramDto> deleteProgram(@PathVariable int programId) throws ProgramNotFoundException {
		
		logger.info("Delete Program with program id {}",programId);

		return new ResponseEntity<>(programService.deleteProgram(programId), HttpStatus.NO_CONTENT);

	}

}
