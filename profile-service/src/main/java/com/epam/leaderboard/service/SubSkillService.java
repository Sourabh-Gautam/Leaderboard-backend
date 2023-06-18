package com.epam.leaderboard.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.leaderboard.dto.SubSkillDTO;
import com.epam.leaderboard.entity.SubSkill;
import com.epam.leaderboard.exception.EntityNotFoundException;
import com.epam.leaderboard.repo.SubSkillRepo;

@Service
public class SubSkillService {

	@Autowired
	private SubSkillRepo subSkillRepository;

	private ModelMapper mapper = new ModelMapper();
	
	private static final Logger LOGGER = LogManager.getLogger(SubSkillService.class);
	
	public SubSkillService(SubSkillRepo subSkillRepository) {
		this.subSkillRepository = subSkillRepository;
	}
	
	public List<SubSkillDTO> getAllSkillss() throws EntityNotFoundException {
		LOGGER.info("Display all the skills");
		List<SubSkill> skills = subSkillRepository.findAll();
		if (skills.isEmpty()) {
			throw new EntityNotFoundException("No Skills in the database");
		}

		return mapper.map(skills, new TypeToken<List<SubSkillDTO>>() {
		}.getType());

	}
}
