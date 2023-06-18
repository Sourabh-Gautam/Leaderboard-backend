package com.epam.leaderboard.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Setter
@Getter
public class Participant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int participantId;
	private String participantName;
	private String designation;
	private int points;
	private String addedBy;
	private LocalDate awardedDate;
	private String resourceManager;
	private String businessUnit;
	private String primarySkill;
	private String subSkill;
	private String email;
	private int rank;

}
