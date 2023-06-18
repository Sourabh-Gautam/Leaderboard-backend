package com.epam.leaderboard.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.leaderboard.entity.Profile;

/**
 * This interface represents a repository for accessing and managing `Profile` entities in the database.
 * It extends the `JpaRepository` interface, providing a convenient and efficient way to interact with the database.
 * 
 * @author Divyam_Sethi
 */

public interface ProfileRepository extends JpaRepository<Profile, Long> {

	Optional<Profile> findByEmail(String email);

}
