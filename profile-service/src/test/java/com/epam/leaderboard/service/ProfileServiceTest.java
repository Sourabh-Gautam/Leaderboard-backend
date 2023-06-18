
package com.epam.leaderboard.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.leaderboard.entity.Profile;
import com.epam.leaderboard.exception.EntityNotFoundException;
import com.epam.leaderboard.repo.ProfileRepository;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

	@Mock
	private ProfileRepository profileRepository;

	@InjectMocks
	private ProfileServiceImpl profileService;

	private Profile profile;

	private Profile profile2;

	@BeforeEach
	public void setup() {
		profile = new Profile();

		profile.setName("John Doe");
		profile.setId(1L);
		profile.setDesignation("Manager");
		profile.setBusinessUnit("Business Unit A");
		profile.setPrimarySkill("Java");
		profile.setRmName("Jane Smith");
		profile.setAdmin(true);

		profile2 = new Profile();
		profile2.setName("Jane Smith");
		profile2.setId(2L);
		profile2.setDesignation("Developer");
		profile2.setBusinessUnit("Business Unit B");
		profile2.setPrimarySkill("Python");
		profile2.setRmName("John Doe");
		profile2.setAdmin(false);

		MockitoAnnotations.openMocks(this);

	}

	@Test
	void testInsertNewProfile() {
		Profile profile = new Profile();
		profile.setName("John Doe");
		profile.setAdmin(true);
		Mockito.when(profileRepository.save(Mockito.any(Profile.class))).thenReturn(profile);
		Profile savedProfile = profileService.insertNewProfile(profile);
		assertThat(savedProfile.getName()).isEqualTo("John Doe");
		assertThat(savedProfile.isAdmin()).isTrue();
	}

	@Test
	void testGetAllProfiles() {
		List<Profile> profiles = new ArrayList<>();
		profiles.add(profile);
		profiles.add(profile2);
		Mockito.when(profileRepository.findAll()).thenReturn(profiles);
		List<Profile> actualProfiles = profileService.getAllProfiles();
		assertEquals(profiles, actualProfiles);
	}

	@Test
	void testGetProfileById() throws EntityNotFoundException {
		long profileId = 1L;
		Mockito.when(profileRepository.findById(profileId)).thenReturn(Optional.of(profile));
		Profile actualProfile = profileService.getProfileById(profileId);
		assertEquals(profile, actualProfile);
	}

	@Test
	void testGetProfileById_EntityNotFoundException() {
		long profileId = 1L;
		Mockito.when(profileRepository.findById(profileId)).thenReturn(Optional.empty());
		assertThrows(EntityNotFoundException.class, () -> profileService.getProfileById(profileId));
	}

	@Test
	void testUpdateProfileById() throws EntityNotFoundException {
		long profileId = 1L;

		Profile existingProfile = profile;
		List<String> subSkills = new ArrayList<>();
		subSkills.add("AWS");
		subSkills.add("Microservices");

		Profile updatedProfile = new Profile();
		updatedProfile.setName("Jane Smith");
		updatedProfile.setId(1L);
		updatedProfile.setDesignation("Developer");
		updatedProfile.setBusinessUnit("Business Unit B");
		updatedProfile.setPrimarySkill("Python");
		updatedProfile.setSubSkill(subSkills);
		updatedProfile.setRmName("John Doe");
		updatedProfile.setAdmin(false);
		Mockito.when(profileRepository.findById(profileId)).thenReturn(Optional.of(existingProfile));
		Profile actualProfile = profileService.updateProfileById(profileId, updatedProfile);

		assertEquals(updatedProfile, actualProfile);
	}

	@Test
	void testDeleteById() throws EntityNotFoundException {
		long profileId = 1L;

		Mockito.when(profileRepository.findById(profileId)).thenReturn(Optional.of(profile));
		profileService.deleteById(profileId);
		Mockito.verify(profileRepository, Mockito.times(1)).deleteById(profileId);
	}

	@Test
	void testDeleteById_EntityNotFoundException() {
		long profileId = 1L;
		Mockito.when(profileRepository.findById(profileId)).thenReturn(Optional.empty());
		assertThrows(EntityNotFoundException.class, () -> profileService.deleteById(profileId));
	}
}