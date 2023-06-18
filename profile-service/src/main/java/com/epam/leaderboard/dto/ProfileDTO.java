package com.epam.leaderboard.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This is a Data Transfer Object (DTO) class named "ProfileDTO" used to hold
 * information about an profile.
 * 
 * The class is annotated with "@Data" from the lombok library which generates
 * the boilerplate code for getters, setters, toString, equals and hashcode
 * methods.
 * 
 * It also has a "@NoArgsConstructor" annotation, which generates a no-argument
 * constructor for the class.
 * 
 * The class has six properties:
 * 
 * id: identity for a profile. name: name of employee whose profile is this.
 * designation: designation of employee. isAdmin: specify that whether this
 * profile is an admin profile. practiceTeam: number of employee in the team
 * createdDate: when the profile had been created
 * 
 * @author Sourabh_Gautam
 *
 */

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {

	private long id;

	@NotBlank(message = "Name cannot be blank")
	@NotNull(message = "Name cannot be null")
	@Pattern(regexp = "^[a-zA-Z ]+$", message = "Name can only contains alphabets")
	private String name;

	@Pattern(regexp = "^[a-zA-Z ]+$", message = "Designation can only contains alphabets")
	@NotBlank(message = "Designation cannot be blank")
	@NotNull(message = "Designation cannot be null")
	private String designation;

	private boolean isAdmin;

	private String createdDate = LocalDate.now().toString();

	private String primarySkill;
	private List<String> subSkill;
	private String businessUnit;
	private String rmName;

	@NotBlank(message = "Email cannot be blank")
	private String email;

}
