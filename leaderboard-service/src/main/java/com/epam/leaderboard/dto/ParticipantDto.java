package com.epam.leaderboard.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ParticipantDto {

	private int participantId;
	private String participantName;
	private String contributorType;
	private String designation;
	private int points;
	private String addedBy;
	private LocalDate awardedDate;
	private String resourceManager;
	private String businessUnit;
	private String primarySkill;
	private List<String> subSkill;
	private String email;
	private int rank;

}
