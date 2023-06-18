package com.epam.programmanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ContributorType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	private String contributerType;
	int points;
	@Override
	public String toString() {
		return "ContributorType [id=" + id + ", contributerType=" + contributerType + ", points=" + points + "]";
	}

}
