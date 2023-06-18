package com.epam.programmanagement.repo;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.epam.programmanagement.model.Participant;
import com.epam.programmanagement.model.Program;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Program> {

	Optional<Participant> findByProgramAndParticipantId(Program program, int participant);

	@Query(nativeQuery = true, value = "select * from participant where email = :email and awarded_date between :startOfCurrentYear and :endOfCurrentYear")
	Set<Participant> findAllByEmail(String email, LocalDate startOfCurrentYear, LocalDate endOfCurrentYear);

	Set<Participant> findAllByDesignation(String designation);

	Set<Participant> findAllByBusinessUnit(String bu);

	Set<Participant> findAllByPrimarySkill(String ps);

	Set<Participant> findAllByResourceManager(String rm);

}
