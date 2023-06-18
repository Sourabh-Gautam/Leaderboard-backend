package com.epam.leaderboard.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.epam.leaderboard.dto.PaginatedParticipantResponse;
import com.epam.leaderboard.dto.SortFilterDto;
import com.epam.leaderboard.service.DashboardService;

/**
 * This controller class is meant for handle only one request i.e to get all
 * participants
 * 
 * @author Sourabh_Gautam
 *
 */

@RestController
@CrossOrigin(origins = "${leaderboard.origin}")
@RequestMapping("${api.version.first}")
public class DashboardController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DashboardController.class);

	@Autowired
	private DashboardService dashboardService;

	/**
	 * Method to handle get all participant request
	 * 
	 * @return List of participants
	 * @throws Exception
	 */

	@PostMapping
	public ResponseEntity<PaginatedParticipantResponse> participants(@RequestParam int pageNo,
			@RequestParam int recordCount, @RequestBody SortFilterDto sortFilterDto) throws Exception {
		LOGGER.info("Page No {}", pageNo);
		LOGGER.info("Record Count {}", recordCount);
		LOGGER.info("Sort Filter Dto {}", sortFilterDto);
		PaginatedParticipantResponse participantDtoList = dashboardService.getAllParticipants(pageNo, recordCount,
				sortFilterDto);

		return ResponseEntity.status(HttpStatus.OK).body(participantDtoList);
	}

}
