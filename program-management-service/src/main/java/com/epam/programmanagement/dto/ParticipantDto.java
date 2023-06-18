package com.epam.programmanagement.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import com.epam.programmanagement.model.Program;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ParticipantDto {

	private int participantId;
	private String participantName;
	private String contributorType;
	private String designation;
	
	private Set<Program> program;
	private int points;
	private String addedBy;
	private LocalDate awardedDate;
	private LocalDate lastModifiedAt;
	private String resourceManager;
	private String businessUnit;
	private String primarySkill;
	private List<String> subSkill;
	private String email;
	
}
