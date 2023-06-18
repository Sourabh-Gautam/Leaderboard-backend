package com.epam.leaderboard.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epam.leaderboard.entity.Skill;

@Repository
public interface SkillsRepo extends JpaRepository<Skill, Long>{
	

}
