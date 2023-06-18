package com.epam.programmanagement.controller;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.programmanagement.dto.ParticipantDto;
import com.epam.programmanagement.service.ParticipantService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/participants-details")
public class ParticipantContributionController {
	@Autowired
	private ParticipantService participantService;
	private static Logger logger = LogManager.getLogger(ParticipantContributionController.class);

	@GetMapping("email/{email}/{selectedYear}")
	public ResponseEntity<Set<ParticipantDto>> getParticipantByEmail(@PathVariable String email, @PathVariable int selectedYear) {
		logger.info("email: {}", email);
		logger.info("year: {}", selectedYear);
		return new ResponseEntity<>(participantService.getParticipantByEmail(email, selectedYear), HttpStatus.OK);
	}

	@GetMapping("designation/{designation}")
	public ResponseEntity<Set<ParticipantDto>> getParticipantByDesignation(@PathVariable String designation) {
		logger.info("designation: {}", designation);
		return new ResponseEntity<>(participantService.getParticipantByDesignation(designation), HttpStatus.OK);
	}

	@GetMapping("bu/{bu}")
	public ResponseEntity<Set<ParticipantDto>> getParticipantByBU(@PathVariable String bu) {
		logger.info("Business Unit: {}", bu);
		return new ResponseEntity<>(participantService.getParticipantByBusinessUnit(bu), HttpStatus.OK);
	}

	@GetMapping("ps/{ps}")
	public ResponseEntity<Set<ParticipantDto>> getParticipantByPS(@PathVariable String ps) {
		logger.info("Primary Skill: {}", ps);
		return new ResponseEntity<>(participantService.getParticipantByyPrimarySkill(ps), HttpStatus.OK);
	}

	@GetMapping("rm/{rm}")
	public ResponseEntity<Set<ParticipantDto>> getParticipantByRM(@PathVariable String rm) {
		logger.info("Resource Managar: {}", rm);
		return new ResponseEntity<>(participantService.getParticipantByResourceManager(rm), HttpStatus.OK);
	}
}
