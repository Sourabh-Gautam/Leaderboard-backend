package com.epam.leaderboard.service;

import java.util.List;

import com.epam.leaderboard.entity.Profile;
import com.epam.leaderboard.exception.EntityNotFoundException;

/**
 * ProfileService interface provides the method who communicate with database
 * and performs CRUD operations
 * @author Divyam_Sethi
 *
 */

public interface ProfileService {
	
	/**
	 * Insert the given profile object into database
	 * @param profile the profile entity
	 * @return the profile entity
	 */

	Profile insertNewProfile(Profile profile);
	
	/**
	 * This method will fetch all the profile object available inside database
	 * @return list of profile objects
	 */

	List<Profile> getAllProfiles();
	
	/**
	 * Get profile by passing a profile id
	 * @param profileId the profile id
	 * @return the profile object
	 * @throws EntityNotFoundException when the given profile id doesn't exists
	 */

	Profile getProfileById(long profileId) throws EntityNotFoundException;
	
	/**
	 * Delete profile by passing a profile id
	 * @param profileId the profile id
	 * @throws EntityNotFoundException when the given profile id doesn't exists
	 */

	void deleteById(long profileId) throws EntityNotFoundException;
	
	/**
	 * Update profile by passing a profile id
	 * @param profileId the profile id
	 * @param profile the new profile object
	 * @return the updated profile object
	 * @throws EntityNotFoundException when the given profile id doesn't exists
	 */

	Profile updateProfileById(long profileId, Profile profile) throws EntityNotFoundException;

	Profile getProfileByEmail(String email) throws EntityNotFoundException;

}
