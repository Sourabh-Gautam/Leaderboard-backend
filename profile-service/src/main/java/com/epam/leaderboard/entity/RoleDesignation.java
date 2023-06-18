package com.epam.leaderboard.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@Entity(name = "DESIGNATION")
public class RoleDesignation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String  designation;

}
