package com.epam.leaderboard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.leaderboard.entity.Profile;
import com.epam.leaderboard.exception.EntityNotFoundException;
import com.epam.leaderboard.repo.ProfileRepository;

import jakarta.transaction.Transactional;

/**
 * ProfileServiceImpl class is implementation of ProfileService interface it
 * provides the method who communicate with database and performs CRUD
 * operations
 * 
 * This class is annotated by two annotations
 * 
 * @Transactional - To make available the JPA session within this class as it is required by updateProfileById() method
 * @Service - Mark it as a bean
 * 
 * @author Divyam_Sethi
 *
 */

@Service
@Transactional
public class ProfileServiceImpl implements ProfileService {

	@Autowired
	private ProfileRepository profileRepository;

	/**
	 * Insert the given profile object into database using save()
	 * 
	 * @param profile the profile entity
	 * @return the profile entity
	 */

	@Override
	public Profile insertNewProfile(Profile profile) {
		return profileRepository.save(profile);
	}
	
	/**
	 * Update profile by passing a profile id
	 * @param profileId the profile id
	 * @param profile the new profile object
	 * @return the updated profile object
	 * @throws EntityNotFoundException when the given profile id doesn't exists
	 */

	@Override
	public Profile updateProfileById(long profileId, Profile profile) throws EntityNotFoundException {
		var matchedProfile = getProfileById(profileId);
		matchedProfile.setAdmin(profile.isAdmin());
		matchedProfile.setDesignation(profile.getDesignation());
		matchedProfile.setName(profile.getName());
		matchedProfile.setBusinessUnit(profile.getBusinessUnit());
		matchedProfile.setPrimarySkill(profile.getPrimarySkill());
		matchedProfile.setRmName(profile.getRmName());
		matchedProfile.setEmail(profile.getEmail());
		matchedProfile.setSubSkill(profile.getSubSkill());
		return matchedProfile;
	}

	/**
	 * This method will fetch all the profile object available inside database using
	 * findAll() method
	 * 
	 * @return list of profile objects
	 */

	@Override
	public List<Profile> getAllProfiles() {
		return profileRepository.findAll();
	}

	/**
	 * Get profile by passing a profile id using findById() method
	 * @param profileId the profile id
	 * @return the profile object
	 * @throws EntityNotFoundException when the given profile id doesn't exists
	 */

	@Override
	public Profile getProfileById(long profileId) throws EntityNotFoundException {
		return profileRepository.findById(profileId)
				.orElseThrow(() -> new EntityNotFoundException("No profile exist with id: %d".formatted(profileId)));
	}
	
	/**
	 * Get profile by passing a profile email
	 * @param email the profile email
	 * @return the profile object
	 * @throws EntityNotFoundException when the given profile email doesn't exists
	 */
	
	@Override
	public Profile getProfileByEmail(String email) throws EntityNotFoundException {
		return profileRepository.findByEmail(email)
				.orElseThrow(() -> new EntityNotFoundException("No profile exist with email: %s".formatted(email)));
	}

	/**
	 * Delete profile by passing a profile id using deleteById() method
	 * @param profileId the profile id
	 * @throws EntityNotFoundException when the given profile id doesn't exists
	 */
	
	@Override
	public void deleteById(long profileId) throws EntityNotFoundException {
		getProfileById(profileId);
		profileRepository.deleteById(profileId);
	}

}
