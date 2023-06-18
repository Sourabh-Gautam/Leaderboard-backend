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

import com.epam.programmanagement.dto.ProgramTemplateDto;
import com.epam.programmanagement.service.ProgramTemplateService;

import jakarta.validation.Valid;
/**
 * ProgramTemplateController does the operations which are related to Program Template Service such as 
 * creating , reading , updating and deleting a program.
 * 
 * ProgramTemplateController is annotated by two annotations:
 * @RestController - Combination of @Controller and @Responsebody Annotations.
 *                   Marks this class as a bean.
 * @RequestMapping - Class level request mapping.   
 * @author Sneka_P
 *
 */

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/programTemplates")
public class ProgramTemplateController {
	@Autowired
	private ProgramTemplateService programTemplateService;
	private static Logger logger = LogManager.getLogger(ProgramTemplateController.class);

	/**
	 * The getProgramTemplate() method is to retrieve all the ProgramTemplate present in the database.
	 * It gives a status code of 200 i.e OK.
	 * @return List of ProgramTemplates
	 */
	@GetMapping
	public ResponseEntity<List<ProgramTemplateDto>> getProgramTemplates() {
         
		logger.info("Display all the Programs");
		
		return new ResponseEntity<>(programTemplateService.getAllProgramTemplates(), HttpStatus.OK);

	}
	
	/**
	 * The getProgramTemplate() method returns a ProgramTemplate based on the ProgramTemplateId.
	 * It gives a status code of 200 i.e OK.
	 * @param ProgramTemplateId
	 * @return ProgramTemplate details
	 */

	@GetMapping("/{programTemplateId}")
	public ResponseEntity<ProgramTemplateDto> getProgram(@PathVariable int programTemplateId) {
		logger.info("programTemplate Details for the programTemplate id {}",programTemplateId);

		return new ResponseEntity<>(programTemplateService.getProgramTemplate(programTemplateId), HttpStatus.OK);

	}
  
	/**
	 * The createProgramTemplate() method creates a new ProgramTemplate in the database based on the given data.
	 * It gives a status code of 201 i.e CREATED.
	 * @param ProgramTemplate
	 * @return inserted ProgramTemplate data.
	 */
	@PostMapping
	public ResponseEntity<ProgramTemplateDto> createProgramTemplate(@Valid @RequestBody ProgramTemplateDto programTemplateDto) {
		
		logger.info("Creating a Program with the details {}",programTemplateDto);

		return new ResponseEntity<>(programTemplateService.saveProgramTemplate(programTemplateDto), HttpStatus.CREATED);

	}
 
	/**
	 * The updateProgramTemplate() method updates the ProgramTemplate present in the database with given update details.
	 * It gives a status code of 202 i.e ACCEPTED.
	 * @param programTemplateId
	 * @param ProgramTemplateDto
	 * @return Updated ProgramTemplate Data
	 *
	 */
	@PutMapping("/{programTemplateId}")
	public ResponseEntity<ProgramTemplateDto> updateProgramTemplate(@PathVariable int programTemplateId,
			@Valid @RequestBody ProgramTemplateDto programTemplateDto){
		
		logger.info("Update ProgramTemplate with programtemplate id {}{}",programTemplateId, programTemplateDto);
	
		return new ResponseEntity<>(programTemplateService.updateProgramTemplate(programTemplateId, programTemplateDto), HttpStatus.ACCEPTED);
	}
  /**
   * The deleteProgramTemplate() method delete the ProgramTemplate present in the database with given ProgramTemplateId.
   * It gives a status code of 2004 i.e NO_CONTENT.
   * @param ProgramTemplateId
   * @return void
   * 
   */
	
	@DeleteMapping("/{programTemplateId}")
	public ResponseEntity<ProgramTemplateDto> deleteProgramTemplate(@PathVariable int programTemplateId) {
		
		logger.info("Delete Program with program id {}",programTemplateId);

		return new ResponseEntity<>(programTemplateService.deleteProgramTemplate(programTemplateId), HttpStatus.NO_CONTENT);

	}
}
