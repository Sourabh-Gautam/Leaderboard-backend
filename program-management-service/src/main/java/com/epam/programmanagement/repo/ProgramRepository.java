package com.epam.programmanagement.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.programmanagement.model.Program;

public interface ProgramRepository extends JpaRepository<Program, Integer>{

}
