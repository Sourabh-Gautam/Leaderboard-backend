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

import com.epam.leaderboard.dto.BusinessUnitDTO;
import com.epam.leaderboard.exception.EntityNotFoundException;
import com.epam.leaderboard.service.BusinessUnitService;


@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("${api.version.first}")
public class BusinessUnitController {

	@Autowired
	private BusinessUnitService businessUnitService;
	private static final Logger LOGGER = LogManager.getLogger(BusinessUnitController.class);

	/**
	 * The getBusinessUnits() method is to retrieve all the businessUnits present in the database.
	 * It gives a status code of 200 i.e OK.
	 * @return List of BusinessUnits
	 * @throws EntityNotFoundException 
	 */
	@GetMapping("/Business-Units")
	public ResponseEntity<List<BusinessUnitDTO>> getBusinessUnits() throws EntityNotFoundException {
         
		LOGGER.info("Display all the BusinessUnits");
		
		return new ResponseEntity<>(businessUnitService.getAllBusinessUnits(), HttpStatus.OK);

	}
	
}
