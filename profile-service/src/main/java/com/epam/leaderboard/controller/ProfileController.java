package com.epam.leaderboard.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.leaderboard.dto.ProfileDTO;
import com.epam.leaderboard.entity.Profile;
import com.epam.leaderboard.exception.EntityNotFoundException;
import com.epam.leaderboard.exception.WrongProfileInputException;
import com.epam.leaderboard.service.ProfileService;

import jakarta.validation.Valid;

/**
 * ProfileController class has ability to perform various task related to
 * profile service like add, delete, update, get profile.
 * 
 * This class is annotated by two annotations
 * 
 * @RestController - Mark this class as a bean
 * @RequestMapping - Providing class level request mapping
 * 
 * @author Sourabh_Gautam
 */

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("${api.version.first}")
public class ProfileController {

	private ModelMapper mapper = new ModelMapper();

	private static final Logger LOGGER = LoggerFactory.getLogger(ProfileController.class);

	@Autowired
	private ProfileService profileService;

	/**
	 * The add() method is used to add new profile
	 * 
	 * @param profileDTO the profile data
	 * @param errors     the bean validation errors
	 * @return the inserted profile data
	 */

	@PostMapping
	public ResponseEntity<ProfileDTO> add(@Valid @RequestBody ProfileDTO profileDTO, Errors errors) {
		LOGGER.info("addProfile is access");
		LOGGER.info("Profile DTO- {}", profileDTO);

		if (errors.hasErrors()) {
			var err = errors.getAllErrors().stream().map(ObjectError::getDefaultMessage).toList().toString();
			LOGGER.error(err);
			throw new WrongProfileInputException(err);
		}
		Profile profile = mapper.map(profileDTO, Profile.class);
		var result = profileService.insertNewProfile(profile);
		return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(result, ProfileDTO.class));
	}

	/**
	 * Method to update the existing profile object
	 * 
	 * @param profileId  the profile id
	 * @param profileDTO the profile data
	 * @param errors     the bean validation errors
	 * @return the updated profile data
	 * @throws EntityNotFoundException when no profile exists with the given id
	 */

	@PutMapping("/{profileId}")
	public ResponseEntity<ProfileDTO> updateOne(@PathVariable long profileId, @Valid @RequestBody ProfileDTO profileDTO,
			Errors errors) throws EntityNotFoundException {
		LOGGER.info("updateOne is access");
		LOGGER.info("Profile DTO - {}", profileDTO);

		LOGGER.info("Profile Id {}", profileId);

		if (errors.hasErrors()) {
			errors.getAllErrors().stream().forEach(e -> LOGGER.error(e.getDefaultMessage()));
			throw new WrongProfileInputException("Fields cannot be null or blank.");
		}

		Profile mappedProfile = mapper.map(profileDTO, Profile.class);
		LOGGER.info("mappedProfile - {}", mappedProfile);
		var result = profileService.updateProfileById(profileId, mappedProfile);

		LOGGER.info("result - {}", result);

		return ResponseEntity.status(HttpStatus.OK).body(mapper.map(result, ProfileDTO.class));

	}

	/**
	 * Method to retrieve single profile based on profile id
	 * 
	 * @param profileId the profile id
	 * @return the profile object
	 * @throws EntityNotFoundException when passing profile id doesn't exist
	 */

	@GetMapping("/{profileId}")
	public ResponseEntity<ProfileDTO> getOne(@PathVariable long profileId) throws EntityNotFoundException {

		LOGGER.info("Profile Id : {}", profileId);

		LOGGER.info("getOne is access");

		var result = profileService.getProfileById(profileId);

		return ResponseEntity.status(HttpStatus.OK).body(mapper.map(result, ProfileDTO.class));

	}
	
	/**
	 * Method to retrieve single profile based on profile email
	 * 
	 * @param profileId the profile id
	 * @return the profile object
	 * @throws EntityNotFoundException when passing profile id doesn't exist
	 */

	@GetMapping("/email/{email}")
	public ResponseEntity<ProfileDTO> profileByEmail(@PathVariable String email) throws EntityNotFoundException {

		LOGGER.info("Profile Email - {}", email);
		var result = profileService.getProfileByEmail(email);
		return ResponseEntity.status(HttpStatus.OK).body(mapper.map(result, ProfileDTO.class));

	}

	/**
	 * Method to get all profile available in the database
	 * 
	 * @return the list of profiles
	 */

	@GetMapping
	public ResponseEntity<List<ProfileDTO>> getAll() {

		LOGGER.info("getAll is access");
		List<Profile> profileList = profileService.getAllProfiles();
		List<ProfileDTO> result = profileList.stream().map(e -> mapper.map(e, ProfileDTO.class)).toList();
		return ResponseEntity.status(HttpStatus.OK).body(result);

	}

	/**
	 * Delete a profile object based on profile id
	 * 
	 * @param profileId the profile id
	 * @return nothing
	 * @throws EntityNotFoundException when given id doesn't exists
	 */

	@DeleteMapping("/{profileId}")
	public ResponseEntity<List<ProfileDTO>> delete(@PathVariable long profileId) throws EntityNotFoundException {
		LOGGER.info("Delete is access");
		LOGGER.info("Profile Id = {}", profileId);

		profileService.deleteById(profileId);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

	}

}
