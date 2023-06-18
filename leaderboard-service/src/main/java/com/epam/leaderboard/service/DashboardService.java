package com.epam.leaderboard.service;

import com.epam.leaderboard.dto.PaginatedParticipantResponse;
import com.epam.leaderboard.dto.SortFilterDto;

/**
 * This interface defines the dashboard service methods
 * 
 * @author Sourabh_Gautam
 *
 */
public interface DashboardService {

	/**
	 * This method is use to get all the participants sorted on the basis of
	 * calculatedWeightage in descending order
	 * 
	 * @return List of participants
	 */
	PaginatedParticipantResponse getAllParticipants(int pageNo, int recordCount, SortFilterDto filterDto)  throws Exception;

}
