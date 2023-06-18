package com.epam.programmanagement.model;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Program {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int programId;
	private String category;
	private String title;
	private String description;
	private String addedBy;
	private Date startDate;
	@JsonIgnore
    @ManyToMany(mappedBy = "program",cascade = CascadeType.ALL)
	private Set<Participant> participant;
	private Date endDate;
	private int weightage;
	private LocalDateTime createdAt;
	@Override
	public String toString() {
		return "Program [programId=" + programId + ", category=" + category + ", title=" + title + ", description="
				+ description + ", addedBy=" + addedBy + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", weightage=" + weightage + ", createdAt=" + createdAt + "]";
	}
	
	


	
	

}
