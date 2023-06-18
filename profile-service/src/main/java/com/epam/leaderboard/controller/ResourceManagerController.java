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

import com.epam.leaderboard.dto.ResourceManagerDTO;
import com.epam.leaderboard.exception.EntityNotFoundException;
import com.epam.leaderboard.service.ResourceManagerService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("${api.version.first}")
public class ResourceManagerController {

	
	@Autowired
	private ResourceManagerService resourceManagerService;
	private static final Logger LOGGER = LogManager.getLogger(ResourceManagerController.class);


	/**
	 * The getResourceManagers() method is to retrieve all the resourceManagers present in the database.
	 * It gives a status code of 200 i.e OK.
	 * @return List of ResourceManagers
	 * @throws EntityNotFoundException 
	 */
	@GetMapping("/Resource-Managers")
	public ResponseEntity<List<ResourceManagerDTO>> getResourceManagers() throws EntityNotFoundException {
         
		LOGGER.info("Display all the ResourceManagers");
		
		return new ResponseEntity<>(resourceManagerService.getAllResourceManagers(), HttpStatus.OK);

	}
}
