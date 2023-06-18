package com.epam.leaderboard.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.leaderboard.dto.SkillsDTO;
import com.epam.leaderboard.entity.Skill;
import com.epam.leaderboard.exception.EntityNotFoundException;
import com.epam.leaderboard.repo.SkillsRepo;
@Service
public class SkillsService {

	@Autowired
	private SkillsRepo skillsRepository;

	private ModelMapper mapper = new ModelMapper();
	
	private static final Logger LOGGER = LogManager.getLogger(SkillsService.class);
	
	public SkillsService(SkillsRepo skillsRepository) {
		this.skillsRepository = skillsRepository;
	}
	
	public List<SkillsDTO> getAllSkillss() throws EntityNotFoundException {
		LOGGER.info("Display all the skills");
		List<Skill> skills = skillsRepository.findAll();
		if (skills.isEmpty()) {
			throw new EntityNotFoundException("No Skills in the database");
		}

		return mapper.map(skills, new TypeToken<List<SkillsDTO>>() {
		}.getType());

	}
	
	

}
