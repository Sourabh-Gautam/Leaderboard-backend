package com.epam.leaderboard.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epam.leaderboard.entity.SubSkill;

@Repository
public interface SubSkillRepo extends JpaRepository<SubSkill, Long>{

}
