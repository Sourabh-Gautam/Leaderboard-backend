package com.epam.leaderboard.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The class represents a profile of a user in the system.
 * It is annotated with JPA Entity annotation, which signifies that
 * it maps to a database table named "USER_PROFILES".
 * The class is also annotated with Lombok's @Data annotation, which
 * generates getters, setters, toString, hashCode, and equals methods
 * based on the fields of the class.
 * It also has the @NoArgsConstructor and @AllArgsConstructor annotations
 * from Lombok, which generate a no-arg constructor and a constructor
 * with all fields as arguments, respectively.
 * The class has five fields:
 * id - a unique identifier for the profile, generated by JPA.
 * name - the name of the user.
 * designation - the designation of the user.
 * isAdmin - a boolean indicating whether the user has admin privileges.
 * practiceTeam - the practice team the user belongs to.
 * 
 * @author Divyam_Sethi
 *
 */

@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "USER_PROFILES")
public class Profile {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String name;
	private String designation;
	private boolean isAdmin;
	private String createdDate;
	private String primarySkill;
	private List<String> subSkill;
	private String businessUnit;
	private String rmName;
	@Column(unique = true)
	private String email;

}
