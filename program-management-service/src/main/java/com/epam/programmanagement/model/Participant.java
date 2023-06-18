package com.epam.programmanagement.model;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Participant {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int participantId;
	private String participantName;
	private String contributorType;
	private String designation;

	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinTable(name = "program_participant", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "programId"))
	private Set<Program> program;
	private int points;
	private String addedBy;
	private LocalDate awardedDate;
	private LocalDate lastModifiedAt;
	private String resourceManager;
	private String businessUnit;
	private String primarySkill;
	private String subSkill;
	private String email;

}
