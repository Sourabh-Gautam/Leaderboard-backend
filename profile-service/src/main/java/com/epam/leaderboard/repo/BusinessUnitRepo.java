package com.epam.leaderboard.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epam.leaderboard.entity.BusinessUnit;
@Repository
public interface BusinessUnitRepo extends JpaRepository<BusinessUnit, Long>{

}
