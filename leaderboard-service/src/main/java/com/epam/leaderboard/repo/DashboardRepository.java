package com.epam.leaderboard.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.epam.leaderboard.entity.Participant;

/**
 * Dashboard repository interface to perform various operation provides by
 * JpaRepository
 * 
 * @author Sourabh_Gautam
 *
 */

public interface DashboardRepository extends JpaRepository<Participant, Integer> {

	// contains

	@Query(nativeQuery = true, value = "SELECT DENSE_RANK() OVER(ORDER BY points DESC) rank, a.* FROM (SELECT email, designation, MAX(awarded_date) AS awarded_date, MIN(participant_id) AS participant_id, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit, SUM(points) AS points FROM participant WHERE participant_name LIKE CONCAT('%', :filter, '%') and awarded_date between :startOfYear and :endOfYear GROUP BY email, designation, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit) a")
	List<Participant> findAllByParticipantNameContains(LocalDate startOfYear, LocalDate endOfYear, String filter,
			Pageable pageable);

	@Query(nativeQuery = true, value = "SELECT DENSE_RANK() OVER(ORDER BY points DESC) rank, a.* FROM (SELECT email, designation, MAX(awarded_date) AS awarded_date, MIN(participant_id) AS participant_id, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit, SUM(points) AS points FROM participant WHERE designation LIKE CONCAT('%', :filter, '%') and awarded_date between :startOfYear and :endOfYear GROUP BY email, designation, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit) a")
	List<Participant> findAllByDesignationContains(LocalDate startOfYear, LocalDate endOfYear, String filter,
			Pageable pageable);

	@Query(nativeQuery = true, value = "SELECT DENSE_RANK() OVER(ORDER BY points DESC) rank, a.* FROM (SELECT email, designation, MAX(awarded_date) AS awarded_date, MIN(participant_id) AS participant_id, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit, SUM(points) AS points FROM participant WHERE business_unit LIKE CONCAT('%', :filter, '%') and awarded_date between :startOfYear and :endOfYear GROUP BY email, designation, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit) a")
	List<Participant> findAllByBusinessUnitContains(LocalDate startOfYear, LocalDate endOfYear, String filter,
			Pageable pageable);

	@Query(nativeQuery = true, value = "SELECT DENSE_RANK() OVER(ORDER BY points DESC) rank, a.* FROM (SELECT email, designation, MAX(awarded_date) AS awarded_date, MIN(participant_id) AS participant_id, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit, SUM(points) AS points FROM participant WHERE resource_manager LIKE CONCAT('%', :filter, '%') and awarded_date between :startOfYear and :endOfYear GROUP BY email, designation, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit) a")
	List<Participant> findAllByResourceManagerContains(LocalDate startOfYear, LocalDate endOfYear, String filter,
			Pageable pageable);

	@Query(nativeQuery = true, value = "SELECT DENSE_RANK() OVER(ORDER BY points DESC) rank, a.* FROM (SELECT email, designation, MAX(awarded_date) AS awarded_date, MIN(participant_id) AS participant_id, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit, SUM(points) AS points FROM participant WHERE primary_skill LIKE CONCAT('%', :filter, '%') and awarded_date between :startOfYear and :endOfYear GROUP BY email, designation, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit) a")
	List<Participant> findAllByPrimarySkillContains(LocalDate startOfYear, LocalDate endOfYear, String filter,
			Pageable pageable);

	@Query(nativeQuery = true, value = "SELECT DENSE_RANK() OVER(ORDER BY points DESC) rank, a.* FROM (SELECT email, designation, MAX(awarded_date) AS awarded_date, MIN(participant_id) AS participant_id, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit, SUM(points) AS points FROM participant WHERE sub_skill LIKE CONCAT('%', :filter, '%') and awarded_date between :startOfYear and :endOfYear GROUP BY email, designation, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit) a")
	List<Participant> findAllBySubSkillContains(LocalDate startOfYear, LocalDate endOfYear, String filter,
			Pageable pageable);

	// equals

	@Query(nativeQuery = true, value = "SELECT DENSE_RANK() OVER(ORDER BY points DESC) rank, a.* FROM (SELECT email, designation, MAX(awarded_date) AS awarded_date, MIN(participant_id) AS participant_id, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit, SUM(points) AS points FROM participant WHERE participant_name = :filter and awarded_date between :startOfYear and :endOfYear GROUP BY email, designation, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit) a")
	List<Participant> findAllByParticipantNameEquals(LocalDate startOfYear, LocalDate endOfYear, String filter,
			Pageable pageable);

	@Query(nativeQuery = true, value = "SELECT DENSE_RANK() OVER(ORDER BY points DESC) rank, a.* FROM (SELECT email, designation, MAX(awarded_date) AS awarded_date, MIN(participant_id) AS participant_id, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit, SUM(points) AS points FROM participant WHERE designation = :filter and awarded_date between :startOfYear and :endOfYear GROUP BY email, designation, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit) a")
	List<Participant> findAllByDesignationEquals(LocalDate startOfYear, LocalDate endOfYear, String filter,
			Pageable pageable);

	@Query(nativeQuery = true, value = "SELECT DENSE_RANK() OVER(ORDER BY points DESC) rank, a.* FROM (SELECT email, designation, MAX(awarded_date) AS awarded_date, MIN(participant_id) AS participant_id, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit, SUM(points) AS points FROM participant WHERE business_unit = :filter and awarded_date between :startOfYear and :endOfYear GROUP BY email, designation, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit) a")
	List<Participant> findAllByBusinessUnitEquals(LocalDate startOfYear, LocalDate endOfYear, String filter,
			Pageable pageable);

	@Query(nativeQuery = true, value = "SELECT DENSE_RANK() OVER(ORDER BY points DESC) rank, a.* FROM (SELECT email, designation, MAX(awarded_date) AS awarded_date, MIN(participant_id) AS participant_id, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit, SUM(points) AS points FROM participant WHERE resource_manager = :filter and awarded_date between :startOfYear and :endOfYear GROUP BY email, designation, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit) a")
	List<Participant> findAllByResourceManagerEquals(LocalDate startOfYear, LocalDate endOfYear, String filter,
			Pageable pageable);

	@Query(nativeQuery = true, value = "SELECT DENSE_RANK() OVER(ORDER BY points DESC) rank, a.* FROM (SELECT email, designation, MAX(awarded_date) AS awarded_date, MIN(participant_id) AS participant_id, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit, SUM(points) AS points FROM participant WHERE primary_skill = :filter and awarded_date between :startOfYear and :endOfYear GROUP BY email, designation, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit) a")
	List<Participant> findAllByPrimarySkillEquals(LocalDate startOfYear, LocalDate endOfYear, String filter,
			Pageable pageable);

	@Query(nativeQuery = true, value = "SELECT DENSE_RANK() OVER(ORDER BY points DESC) rank, a.* FROM (SELECT email, designation, MAX(awarded_date) AS awarded_date, MIN(participant_id) AS participant_id, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit, SUM(points) AS points FROM participant WHERE sub_skill = :filter and awarded_date between :startOfYear and :endOfYear GROUP BY email, designation, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit) a")
	List<Participant> findAllBySubSkillEquals(LocalDate startOfYear, LocalDate endOfYear, String filter,
			Pageable pageable);

	// not equals

	@Query(nativeQuery = true, value = "SELECT DENSE_RANK() OVER(ORDER BY points DESC) rank, a.* FROM (SELECT email, designation, MAX(awarded_date) AS awarded_date, MIN(participant_id) AS participant_id, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit, SUM(points) AS points FROM participant WHERE participant_name != :filter and awarded_date between :startOfYear and :endOfYear GROUP BY email, designation, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit) a")
	List<Participant> findAllByParticipantNameIsNot(LocalDate startOfYear, LocalDate endOfYear, String filter,
			Pageable pageable);

	@Query(nativeQuery = true, value = "SELECT DENSE_RANK() OVER(ORDER BY points DESC) rank, a.* FROM (SELECT email, designation, MAX(awarded_date) AS awarded_date, MIN(participant_id) AS participant_id, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit, SUM(points) AS points FROM participant WHERE designation != :filter and awarded_date between :startOfYear and :endOfYear GROUP BY email, designation, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit) a")
	List<Participant> findAllByDesignationIsNot(LocalDate startOfYear, LocalDate endOfYear, String filter,
			Pageable pageable);

	@Query(nativeQuery = true, value = "SELECT DENSE_RANK() OVER(ORDER BY points DESC) rank, a.* FROM (SELECT email, designation, MAX(awarded_date) AS awarded_date, MIN(participant_id) AS participant_id, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit, SUM(points) AS points FROM participant WHERE business_unit != :filter and awarded_date between :startOfYear and :endOfYear GROUP BY email, designation, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit) a")
	List<Participant> findAllByBusinessUnitIsNot(LocalDate startOfYear, LocalDate endOfYear, String filter,
			Pageable pageable);

	@Query(nativeQuery = true, value = "SELECT DENSE_RANK() OVER(ORDER BY points DESC) rank, a.* FROM (SELECT email, designation, MAX(awarded_date) AS awarded_date, MIN(participant_id) AS participant_id, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit, SUM(points) AS points FROM participant WHERE resource_manager != :filter and awarded_date between :startOfYear and :endOfYear GROUP BY email, designation, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit) a")
	List<Participant> findAllByResourceManagerIsNot(LocalDate startOfYear, LocalDate endOfYear, String filter,
			Pageable pageable);

	@Query(nativeQuery = true, value = "SELECT DENSE_RANK() OVER(ORDER BY points DESC) rank, a.* FROM (SELECT email, designation, MAX(awarded_date) AS awarded_date, MIN(participant_id) AS participant_id, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit, SUM(points) AS points FROM participant WHERE primary_skill != :filter and awarded_date between :startOfYear and :endOfYear GROUP BY email, designation, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit) a")
	List<Participant> findAllByPrimarySkillIsNot(LocalDate startOfYear, LocalDate endOfYear, String filter,
			Pageable pageable);

	@Query(nativeQuery = true, value = "SELECT DENSE_RANK() OVER(ORDER BY points DESC) rank, a.* FROM (SELECT email, designation, MAX(awarded_date) AS awarded_date, MIN(participant_id) AS participant_id, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit, SUM(points) AS points FROM participant WHERE sub_skill != :filter and awarded_date between :startOfYear and :endOfYear GROUP BY email, designation, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit) a")
	List<Participant> findAllBySubSkillIsNot(LocalDate startOfYear, LocalDate endOfYear, String filter,
			Pageable pageable);

	// startingwith

	@Query(nativeQuery = true, value = "SELECT DENSE_RANK() OVER(ORDER BY points DESC) rank, a.* FROM (SELECT email, designation, MAX(awarded_date) AS awarded_date, MIN(participant_id) AS participant_id, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit, SUM(points) AS points FROM participant WHERE participant_name LIKE CONCAT(:filter, '%') and awarded_date between :startOfYear and :endOfYear GROUP BY email, designation, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit) a")
	List<Participant> findAllByParticipantNameStartingWith(LocalDate startOfYear, LocalDate endOfYear, String filter,
			Pageable pageable);

	@Query(nativeQuery = true, value = "SELECT DENSE_RANK() OVER(ORDER BY points DESC) rank, a.* FROM (SELECT email, designation, MAX(awarded_date) AS awarded_date, MIN(participant_id) AS participant_id, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit, SUM(points) AS points FROM participant WHERE designation LIKE CONCAT(:filter, '%') and awarded_date between :startOfYear and :endOfYear GROUP BY email, designation, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit) a")
	List<Participant> findAllByDesignationStartingWith(LocalDate startOfYear, LocalDate endOfYear, String filter,
			Pageable pageable);

	@Query(nativeQuery = true, value = "SELECT DENSE_RANK() OVER(ORDER BY points DESC) rank, a.* FROM (SELECT email, designation, MAX(awarded_date) AS awarded_date, MIN(participant_id) AS participant_id, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit, SUM(points) AS points FROM participant WHERE business_unit LIKE CONCAT(:filter, '%') and awarded_date between :startOfYear and :endOfYear GROUP BY email, designation, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit) a")
	List<Participant> findAllByBusinessUnitStartingWith(LocalDate startOfYear, LocalDate endOfYear, String filter,
			Pageable pageable);

	@Query(nativeQuery = true, value = "SELECT DENSE_RANK() OVER(ORDER BY points DESC) rank, a.* FROM (SELECT email, designation, MAX(awarded_date) AS awarded_date, MIN(participant_id) AS participant_id, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit, SUM(points) AS points FROM participant WHERE resource_manager LIKE CONCAT(:filter, '%') and awarded_date between :startOfYear and :endOfYear GROUP BY email, designation, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit) a")
	List<Participant> findAllByResourceManagerStartingWith(LocalDate startOfYear, LocalDate endOfYear, String filter,
			Pageable pageable);

	@Query(nativeQuery = true, value = "SELECT DENSE_RANK() OVER(ORDER BY points DESC) rank, a.* FROM (SELECT email, designation, MAX(awarded_date) AS awarded_date, MIN(participant_id) AS participant_id, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit, SUM(points) AS points FROM participant WHERE primary_skill LIKE CONCAT(:filter, '%') and awarded_date between :startOfYear and :endOfYear GROUP BY email, designation, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit) a")
	List<Participant> findAllByPrimarySkillStartingWith(LocalDate startOfYear, LocalDate endOfYear, String filter,
			Pageable pageable);

	@Query(nativeQuery = true, value = "SELECT DENSE_RANK() OVER(ORDER BY points DESC) rank, a.* FROM (SELECT email, designation, MAX(awarded_date) AS awarded_date, MIN(participant_id) AS participant_id, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit, SUM(points) AS points FROM participant WHERE sub_skill LIKE CONCAT(:filter, '%') and awarded_date between :startOfYear and :endOfYear GROUP BY email, designation, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit) a")
	List<Participant> findAllBySubSkillStartingWith(LocalDate startOfYear, LocalDate endOfYear, String filter,
			Pageable pageable);

	// endingwith

	@Query(nativeQuery = true, value = "SELECT DENSE_RANK() OVER(ORDER BY points DESC) rank, a.* FROM (SELECT email, designation, MAX(awarded_date) AS awarded_date, MIN(participant_id) AS participant_id, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit, SUM(points) AS points FROM participant WHERE participant_name LIKE CONCAT('%', :filter) and awarded_date between :startOfYear and :endOfYear GROUP BY email, designation, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit) a")
	List<Participant> findAllByParticipantNameEndingWith(LocalDate startOfYear, LocalDate endOfYear, String filter,
			Pageable pageable);

	@Query(nativeQuery = true, value = "SELECT DENSE_RANK() OVER(ORDER BY points DESC) rank, a.* FROM (SELECT email, designation, MAX(awarded_date) AS awarded_date, MIN(participant_id) AS participant_id, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit, SUM(points) AS points FROM participant WHERE designation LIKE CONCAT('%', :filter) and awarded_date between :startOfYear and :endOfYear GROUP BY email, designation, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit) a")
	List<Participant> findAllByDesignationEndingWith(LocalDate startOfYear, LocalDate endOfYear, String filter,
			Pageable pageable);

	@Query(nativeQuery = true, value = "SELECT DENSE_RANK() OVER(ORDER BY points DESC) rank, a.* FROM (SELECT email, designation, MAX(awarded_date) AS awarded_date, MIN(participant_id) AS participant_id, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit, SUM(points) AS points FROM participant WHERE business_unit LIKE CONCAT('%', :filter) and awarded_date between :startOfYear and :endOfYear GROUP BY email, designation, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit) a")
	List<Participant> findAllByBusinessUnitEndingWith(LocalDate startOfYear, LocalDate endOfYear, String filter,
			Pageable pageable);

	@Query(nativeQuery = true, value = "SELECT DENSE_RANK() OVER(ORDER BY points DESC) rank, a.* FROM (SELECT email, designation, MAX(awarded_date) AS awarded_date, MIN(participant_id) AS participant_id, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit, SUM(points) AS points FROM participant WHERE resource_manager LIKE CONCAT('%', :filter) and awarded_date between :startOfYear and :endOfYear GROUP BY email, designation, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit) a")
	List<Participant> findAllByResourceManagerEndingWith(LocalDate startOfYear, LocalDate endOfYear, String filter,
			Pageable pageable);

	@Query(nativeQuery = true, value = "SELECT DENSE_RANK() OVER(ORDER BY points DESC) rank, a.* FROM (SELECT email, designation, MAX(awarded_date) AS awarded_date, MIN(participant_id) AS participant_id, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit, SUM(points) AS points FROM participant WHERE primary_skill LIKE CONCAT('%', :filter) and awarded_date between :startOfYear and :endOfYear GROUP BY email, designation, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit) a")
	List<Participant> findAllByPrimarySkillEndingWith(LocalDate startOfYear, LocalDate endOfYear, String filter,
			Pageable pageable);

	@Query(nativeQuery = true, value = "SELECT DENSE_RANK() OVER(ORDER BY points DESC) rank, a.* FROM (SELECT email, designation, MAX(awarded_date) AS awarded_date, MIN(participant_id) AS participant_id, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit, SUM(points) AS points FROM participant WHERE sub_skill LIKE CONCAT('%', :filter) and awarded_date between :startOfYear and :endOfYear GROUP BY email, designation, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit) a")
	List<Participant> findAllBySubSkillEndingWith(LocalDate startOfYear, LocalDate endOfYear, String filter,
			Pageable pageable);

	// between

	@Query(nativeQuery = true, value = "SELECT DENSE_RANK() OVER(ORDER BY points DESC) rank, a.* FROM (SELECT email, designation, MAX(awarded_date) AS awarded_date, MIN(participant_id) AS participant_id, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit, SUM(points) AS points FROM participant WHERE awarded_date between :startDate and :endDate GROUP BY email, designation, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit) a")
	List<Participant> findAllByAwardedDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

	// General participant counter

	@Query(nativeQuery = true, value = "select count(distinct email) from participant where awarded_date between :startDate and :endDate")
	int getParticipantCountGrouping(LocalDate startDate, LocalDate endDate);

	// total count for participant name

	@Query(nativeQuery = true, value = "select count(distinct email) from participant where awarded_date between :startDate and :endDate and participant_name LIKE CONCAT('%', :filter, '%')")
	int getParticipantCountForContainsParticipantName(LocalDate startDate, LocalDate endDate, String filter);

	@Query(nativeQuery = true, value = "select count(distinct email) from participant where awarded_date between :startDate and :endDate and participant_name = :filter")
	int getParticipantCountForEqualsParticipantName(LocalDate startDate, LocalDate endDate, String filter);

	@Query(nativeQuery = true, value = "select count(distinct email) from participant where awarded_date between :startDate and :endDate and participant_name != :filter")
	int getParticipantCountForNotEqualsParticipantName(LocalDate startDate, LocalDate endDate, String filter);

	@Query(nativeQuery = true, value = "select count(distinct email) from participant where awarded_date between :startDate and :endDate and participant_name LIKE CONCAT(:filter, '%')")
	int getParticipantCountForStartsWithParticipantName(LocalDate startDate, LocalDate endDate, String filter);

	@Query(nativeQuery = true, value = "select count(distinct email) from participant where awarded_date between :startDate and :endDate and participant_name LIKE CONCAT('%', :filter)")
	int getParticipantCountForEndsWithParticipantName(LocalDate startDate, LocalDate endDate, String filter);

	// total count for designation

	@Query(nativeQuery = true, value = "select count(distinct email) from participant where awarded_date between :startDate and :endDate and designation LIKE CONCAT('%', :filter, '%')")
	int getParticipantCountForContainsDesignation(LocalDate startDate, LocalDate endDate, String filter);

	@Query(nativeQuery = true, value = "select count(distinct email) from participant where awarded_date between :startDate and :endDate and designation = :filter")
	int getParticipantCountForEqualsDesignation(LocalDate startDate, LocalDate endDate, String filter);

	@Query(nativeQuery = true, value = "select count(distinct email) from participant where awarded_date between :startDate and :endDate and designation != :filter")
	int getParticipantCountForNotEqualsDesignation(LocalDate startDate, LocalDate endDate, String filter);

	@Query(nativeQuery = true, value = "select count(distinct email) from participant where awarded_date between :startDate and :endDate and designation LIKE CONCAT(:filter, '%')")
	int getParticipantCountForStartsWithDesignation(LocalDate startDate, LocalDate endDate, String filter);

	@Query(nativeQuery = true, value = "select count(distinct email) from participant where awarded_date between :startDate and :endDate and designation LIKE CONCAT('%', :filter)")
	int getParticipantCountForEndsWithDesignation(LocalDate startDate, LocalDate endDate, String filter);

	// total count for business unit

	@Query(nativeQuery = true, value = "select count(distinct email) from participant where awarded_date between :startDate and :endDate and business_unit LIKE CONCAT('%', :filter, '%')")
	int getParticipantCountForContainsBusinessUnit(LocalDate startDate, LocalDate endDate, String filter);

	@Query(nativeQuery = true, value = "select count(distinct email) from participant where awarded_date between :startDate and :endDate and business_unit = :filter")
	int getParticipantCountForEqualsBusinessUnit(LocalDate startDate, LocalDate endDate, String filter);

	@Query(nativeQuery = true, value = "select count(distinct email) from participant where awarded_date between :startDate and :endDate and business_unit != :filter")
	int getParticipantCountForNotEqualsBusinessUnit(LocalDate startDate, LocalDate endDate, String filter);

	@Query(nativeQuery = true, value = "select count(distinct email) from participant where awarded_date between :startDate and :endDate and business_unit LIKE CONCAT(:filter, '%')")
	int getParticipantCountForStartsWithBusinessUnit(LocalDate startDate, LocalDate endDate, String filter);

	@Query(nativeQuery = true, value = "select count(distinct email) from participant where awarded_date between :startDate and :endDate and business_unit LIKE CONCAT('%', :filter)")
	int getParticipantCountForEndsWithBusinessUnit(LocalDate startDate, LocalDate endDate, String filter);

	// total count for resource manager

	@Query(nativeQuery = true, value = "select count(distinct email) from participant where awarded_date between :startDate and :endDate and resource_manager LIKE CONCAT('%', :filter, '%')")
	int getParticipantCountForContainsResourceManager(LocalDate startDate, LocalDate endDate, String filter);

	@Query(nativeQuery = true, value = "select count(distinct email) from participant where awarded_date between :startDate and :endDate and resource_manager = :filter")
	int getParticipantCountForEqualsResourceManager(LocalDate startDate, LocalDate endDate, String filter);

	@Query(nativeQuery = true, value = "select count(distinct email) from participant where awarded_date between :startDate and :endDate and resource_manager != :filter")
	int getParticipantCountForNotEqualsResourceManager(LocalDate startDate, LocalDate endDate, String filter);

	@Query(nativeQuery = true, value = "select count(distinct email) from participant where awarded_date between :startDate and :endDate and resource_manager LIKE CONCAT(:filter, '%')")
	int getParticipantCountForStartsWithResourceManager(LocalDate startDate, LocalDate endDate, String filter);

	@Query(nativeQuery = true, value = "select count(distinct email) from participant where awarded_date between :startDate and :endDate and resource_manager LIKE CONCAT('%', :filter)")
	int getParticipantCountForEndsWithResourceManager(LocalDate startDate, LocalDate endDate, String filter);

	// total count for primary skill

	@Query(nativeQuery = true, value = "select count(distinct email) from participant where awarded_date between :startDate and :endDate and primary_skill LIKE CONCAT('%', :filter, '%')")
	int getParticipantCountForContainsPrimarySkill(LocalDate startDate, LocalDate endDate, String filter);

	@Query(nativeQuery = true, value = "select count(distinct email) from participant where awarded_date between :startDate and :endDate and primary_skill = :filter")
	int getParticipantCountForEqualsPrimarySkill(LocalDate startDate, LocalDate endDate, String filter);

	@Query(nativeQuery = true, value = "select count(distinct email) from participant where awarded_date between :startDate and :endDate and primary_skill != :filter")
	int getParticipantCountForNotEqualsPrimarySkill(LocalDate startDate, LocalDate endDate, String filter);

	@Query(nativeQuery = true, value = "select count(distinct email) from participant where awarded_date between :startDate and :endDate and primary_skill LIKE CONCAT(:filter, '%')")
	int getParticipantCountForStartsWithPrimarySkill(LocalDate startDate, LocalDate endDate, String filter);

	@Query(nativeQuery = true, value = "select count(distinct email) from participant where awarded_date between :startDate and :endDate and primary_skill LIKE CONCAT('%', :filter)")
	int getParticipantCountForEndsWithPrimarySkill(LocalDate startDate, LocalDate endDate, String filter);

	// total count for primary skill

	@Query(nativeQuery = true, value = "select count(distinct email) from participant where awarded_date between :startDate and :endDate and sub_skill LIKE CONCAT('%', :filter, '%')")
	int getParticipantCountForContainsSubSkill(LocalDate startDate, LocalDate endDate, String filter);

	@Query(nativeQuery = true, value = "select count(distinct email) from participant where awarded_date between :startDate and :endDate and sub_skill = :filter")
	int getParticipantCountForEqualsSubSkill(LocalDate startDate, LocalDate endDate, String filter);

	@Query(nativeQuery = true, value = "select count(distinct email) from participant where awarded_date between :startDate and :endDate and sub_skill != :filter")
	int getParticipantCountForNotEqualsSubSkill(LocalDate startDate, LocalDate endDate, String filter);

	@Query(nativeQuery = true, value = "select count(distinct email) from participant where awarded_date between :startDate and :endDate and sub_skill LIKE CONCAT(:filter, '%')")
	int getParticipantCountForStartsWithSubSkill(LocalDate startDate, LocalDate endDate, String filter);

	@Query(nativeQuery = true, value = "select count(distinct email) from participant where awarded_date between :startDate and :endDate and sub_skill LIKE CONCAT('%', :filter)")
	int getParticipantCountForEndsWithSubSkill(LocalDate startDate, LocalDate endDate, String filter);

	// find all

	@Query(nativeQuery = true, value = "SELECT DENSE_RANK() OVER(ORDER BY points DESC) rank, a.* FROM (SELECT email, designation, MAX(awarded_date) AS awarded_date, MIN(participant_id) AS participant_id, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit, SUM(points) AS points FROM participant WHERE awarded_date between :startDate and :endDate GROUP BY email, designation, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit) a")
	List<Participant> findAllCustom(LocalDate startDate, LocalDate endDate, Pageable pageable);

//	@Query(nativeQuery = true, value = "SELECT DENSE_RANK() OVER(ORDER BY points DESC) rank, a.* FROM (SELECT email, designation, MAX(awarded_date) AS awarded_date, MIN(participant_id) AS participant_id, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit, SUM(points) AS points FROM participant WHERE awarded_date >= :currentYear GROUP BY email, designation, participant_name, primary_skill, sub_skill, resource_manager, added_by, business_unit) a ORDER BY :colName asc LIMIT :recordCount OFFSET :startFrom")
//	List<Participant> findAllCustom(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate,
//			int recordCount, int startFrom, String colName, String sortOrder);

}
