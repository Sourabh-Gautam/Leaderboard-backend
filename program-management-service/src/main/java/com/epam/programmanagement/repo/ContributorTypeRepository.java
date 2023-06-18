package com.epam.programmanagement.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epam.programmanagement.model.ContributorType;
@Repository
public interface ContributorTypeRepository  extends JpaRepository<ContributorType, Integer> {
	ContributorType findByContributerType(String s);
}
