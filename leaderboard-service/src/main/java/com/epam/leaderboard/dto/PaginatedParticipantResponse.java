package com.epam.leaderboard.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaginatedParticipantResponse {

	private List<ParticipantDto> participantDtoList;
	private int numberOfParticipants;
	private int numberOfPages;
	
}
