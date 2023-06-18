package com.epam.leaderboard.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.leaderboard.dto.ResourceManagerDTO;
import com.epam.leaderboard.entity.ResourceManager;
import com.epam.leaderboard.exception.EntityNotFoundException;
import com.epam.leaderboard.repo.ResourceManagerRepo;
@Service
public class ResourceManagerService {

	@Autowired
	private ResourceManagerRepo resourceManagerRepository;

	private ModelMapper mapper = new ModelMapper();
	
	private static final Logger LOGGER = LogManager.getLogger(ResourceManagerService.class);
	
	public ResourceManagerService(ResourceManagerRepo resourceManagerRepository) {
		this.resourceManagerRepository = resourceManagerRepository;
	}
	
	public List<ResourceManagerDTO> getAllResourceManagers() throws EntityNotFoundException {
		LOGGER.info("Display all the Resource Managers");
		List<ResourceManager> resourceManagers = resourceManagerRepository.findAll();
		if (resourceManagers.isEmpty()) {
			throw new EntityNotFoundException("No Business Units in the resource managers");
		}

		return mapper.map(resourceManagers, new TypeToken<List<ResourceManagerDTO>>() {
		}.getType());

	}
}
