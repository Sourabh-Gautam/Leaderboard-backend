package com.epam.leaderboard.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.leaderboard.dto.DesignationDTO;
import com.epam.leaderboard.exception.EntityNotFoundException;
import com.epam.leaderboard.service.DesignationService;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("${api.version.first}")
public class DesignationController {

	@Autowired
	private DesignationService designationService;
	private static final Logger LOGGER = LogManager.getLogger(DesignationController.class);

	/**
	 * The getDesignations() method is to retrieve all the designations present in the database.
	 * It gives a status code of 200 i.e OK.
	 * It gives some input message
	 * @return List of Designations
	 * @throws EntityNotFoundException 
	 */
	@GetMapping("/designations")
	public ResponseEntity<List<DesignationDTO>> getDesignations() throws EntityNotFoundException {
         
		LOGGER.info("Display all the Designations");
		
		return new ResponseEntity<>(designationService.getAllDesignations(), HttpStatus.OK);

	}
}
