package com.epam.leaderboard.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epam.leaderboard.entity.RoleDesignation;
@Repository
public interface DesignationRepo extends JpaRepository<RoleDesignation, Long> {

}
