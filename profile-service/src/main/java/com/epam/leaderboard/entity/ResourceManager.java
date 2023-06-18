package com.epam.leaderboard.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@NoArgsConstructor

@Entity(name = "RESOURCE_MANAGER")
public class ResourceManager {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String resourceManagerName;

}
