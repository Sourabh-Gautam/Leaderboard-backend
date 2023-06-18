package com.epam.leaderboard.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.leaderboard.dto.BusinessUnitDTO;
import com.epam.leaderboard.entity.BusinessUnit;
import com.epam.leaderboard.exception.EntityNotFoundException;
import com.epam.leaderboard.repo.BusinessUnitRepo;

@Service
public class BusinessUnitService {
	
	@Autowired
	private BusinessUnitRepo businessUnitRepository;

	private ModelMapper mapper = new ModelMapper();
	
	private static final Logger LOGGER = LogManager.getLogger(BusinessUnitService.class);
	
	public BusinessUnitService(BusinessUnitRepo businessUnitRepository) {
		this.businessUnitRepository = businessUnitRepository;
	}
	
	public List<BusinessUnitDTO> getAllBusinessUnits() throws EntityNotFoundException {
		LOGGER.info("Display all the Business Units");
		List<BusinessUnit> businessUnits = businessUnitRepository.findAll();
		if (businessUnits.isEmpty()) {
			throw new EntityNotFoundException("No Business Units in the database");
		}

		return mapper.map(businessUnits, new TypeToken<List<BusinessUnitDTO>>() {
		}.getType());

	}

}
