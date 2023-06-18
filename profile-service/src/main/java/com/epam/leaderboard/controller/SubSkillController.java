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

import com.epam.leaderboard.dto.SubSkillDTO;
import com.epam.leaderboard.exception.EntityNotFoundException;
import com.epam.leaderboard.service.SubSkillService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("${api.version.first}")
public class SubSkillController {

	@Autowired
	private SubSkillService skillService;
	private static final Logger LOGGER = LogManager.getLogger(SubSkillController.class);

	/**
	 * The getSkillss() method is to retrieve all the skills present in the database.
	 * It gives a status code of 200 i.e OK.
	 * @return List of Skills
	 * @throws EntityNotFoundException 
	 */
	@GetMapping("/SubSkills")
	public ResponseEntity<List<SubSkillDTO>> getSkills() throws EntityNotFoundException {
         
		LOGGER.info("Display all the Skills");
		
		return new ResponseEntity<>(skillService.getAllSkillss(), HttpStatus.OK);

	}
}
