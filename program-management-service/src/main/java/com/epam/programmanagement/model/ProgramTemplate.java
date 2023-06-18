package com.epam.programmanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

@Entity
public class ProgramTemplate {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int programTemplateId;
	private String category;
	private String description;
	private float weightage;
}
