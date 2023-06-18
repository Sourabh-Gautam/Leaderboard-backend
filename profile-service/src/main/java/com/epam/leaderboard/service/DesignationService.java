package com.epam.leaderboard.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.leaderboard.dto.DesignationDTO;
import com.epam.leaderboard.entity.RoleDesignation;
import com.epam.leaderboard.exception.EntityNotFoundException;
import com.epam.leaderboard.repo.DesignationRepo;

@Service
public class DesignationService {

	@Autowired
	private DesignationRepo designationRepository;

	private ModelMapper mapper = new ModelMapper();

	private static final Logger LOGGER = LogManager.getLogger(DesignationService.class);

	public DesignationService(DesignationRepo designationRepository) {
		this.designationRepository = designationRepository;
	}

	public List<DesignationDTO> getAllDesignations() throws EntityNotFoundException {
		LOGGER.info("Display all the Resource Managers");
		List<RoleDesignation> designations = designationRepository.findAll();
		if (designations.isEmpty()) {
			throw new EntityNotFoundException("No Designations in the database");
		}

		return mapper.map(designations, new TypeToken<List<DesignationDTO>>() {
		}.getType());

	}

}
