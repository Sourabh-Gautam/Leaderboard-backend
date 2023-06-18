package com.epam.programmanagement.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.programmanagement.model.ProgramTemplate;

public interface ProgramTemplateRepo extends JpaRepository<ProgramTemplate,Integer>{

}
