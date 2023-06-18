package com.epam.programmanagement.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.programmanagement.dto.ContributorTypeDto;
import com.epam.programmanagement.exception.NoDataExistsException;
import com.epam.programmanagement.service.ContributorTypeService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/contributorTypes")
public class ContributorTypeController {
	
	@Autowired
	private ContributorTypeService contributorTypeService;
	private static Logger logger = LogManager.getLogger(ContributorTypeController.class);

	/**
	 * The getContributorTypes() method is to retrieve all the contributorTypes present in the database.
	 * It gives a status code of 200 i.e OK.
	 * @return List of ContributorTypes
	 */
	@GetMapping
	public ResponseEntity<List<ContributorTypeDto>> getContributorTypes() {
         
		logger.info("Display all the ContributorTypes");
		
		return new ResponseEntity<>(contributorTypeService.getAllContributorTypes(), HttpStatus.OK);

	}
	
	/**
	 * The getContributorType() method returns a contributorType based on the contributorTypeId.
	 * It gives a status code of 200 i.e OK.
	 * @param contributorTypeId
	 * @return ContributorType details
	 */

	@GetMapping("/{contributorTypeId}")
	public ResponseEntity<ContributorTypeDto> getContributorType(@PathVariable int contributorTypeId) {
		logger.info("ContributorType Details for the contributorType id {}",contributorTypeId);

		return new ResponseEntity<>(contributorTypeService.getContributorType(contributorTypeId), HttpStatus.OK);

	}
  
	/**
	 * The createContributorType() method creates a new contributorType in the database based on the given data.
	 * It gives a status code of 201 i.e CREATED.
	 * @param contributorType
	 * @return inserted ContributorType data.
	 */
	@PostMapping
	public ResponseEntity<ContributorTypeDto> createContributorType(@Valid @RequestBody ContributorTypeDto contributorType) {
		
		logger.info("Creating a ContributorType with the details {}",contributorType);

		return new ResponseEntity<>(contributorTypeService.saveContributorType(contributorType), HttpStatus.CREATED);

	}
 
	/**
	 * The updateContributorType() method updates the ContributorType present in the database with given update details.
	 * It gives a status code of 202 i.e ACCEPTED.
	 * @param contributorTypeId
	 * @param contributorTypeDto
	 * @return Updated ContributorType Data
	 * @throws NoDataExistsException if the contributorTypeId doesn't exist.
	 */
	@PutMapping("/{contributorTypeId}")
	public ResponseEntity<ContributorTypeDto> updateContributorType(@PathVariable int contributorTypeId,
			@Valid @RequestBody ContributorTypeDto contributorTypeDto) throws NoDataExistsException{
		
		logger.info("Update ContributorType with contributorType id {}{}",contributorTypeId, contributorTypeDto);
	
		return new ResponseEntity<>(contributorTypeService.updateContributorType(contributorTypeId, contributorTypeDto), HttpStatus.ACCEPTED);
	}
  /**
   * The deleteContributorType() method delete the ContributorType present in the database with given contributorTypeId.
   * It gives a status code of 2004 i.e NO_CONTENT.
   * @param contributorTypeId
   * @return void
   * @throws NoDataExistsException if the contributorTypeId doesn't exist.
   */
	
	@DeleteMapping("/{contributorTypeId}")
	public ResponseEntity<ContributorTypeDto> deleteContributorType(@PathVariable int contributorTypeId) throws NoDataExistsException {
		
		logger.info("Delete ContributorType with contributorType id {}",contributorTypeId);

		return new ResponseEntity<>(contributorTypeService.deleteContributorType(contributorTypeId), HttpStatus.NO_CONTENT);

	}

}
