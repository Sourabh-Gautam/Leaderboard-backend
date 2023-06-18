package com.epam.programmanagement.dto;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Set;

import com.epam.programmanagement.model.Participant;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProgramDto {
	private int programId;
	private String category;
	private String title;
	private String description;
	private String addedBy;
	private Date startDate;
	private Date endDate;
	@Min(value = 1,message = "weightage is required")
	private int weightage;
	private LocalDateTime createdAt;
//	@JsonIgnore
	private Set<Participant> participant;
}
