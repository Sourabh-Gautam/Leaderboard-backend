package com.epam.leaderboard.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epam.leaderboard.entity.ResourceManager;

@Repository
public interface ResourceManagerRepo extends JpaRepository<ResourceManager, Long>{

}
