package com.epam.programmanagement.service;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.programmanagement.dto.ContributorTypeDto;
import com.epam.programmanagement.exception.NoDataExistsException;
import com.epam.programmanagement.model.ContributorType;
import com.epam.programmanagement.repo.ContributorTypeRepository;
@Service
public class ContributorTypeService {

	@Autowired
	private ContributorTypeRepository contributorTypeRepository;

	private ModelMapper mapper = new ModelMapper();
	
	private static Logger logger = LogManager.getLogger(ContributorTypeService.class);
	
	private String contributorException = "Contributor with given id not present";
	
	public ContributorTypeService(ContributorTypeRepository contributorTypeRepository) {
		this.contributorTypeRepository = contributorTypeRepository;
	}

	
	/**
	 * The getAllContributors() method will retrieve all the contributors present in the
	 * database.
	 * 
	 * @return List of Contributors
	 * @throws NoDataExistsException if there are no contributors in the database.
	 */
	public List<ContributorTypeDto> getAllContributorTypes() {
		logger.info("Display all the programs");
		List<ContributorType> contributors = contributorTypeRepository.findAll();
		if (contributors.isEmpty()) {
			throw new NoDataExistsException("No Program Exists");
		}

		return mapper.map(contributors, new TypeToken<List<ContributorTypeDto>>() {
		}.getType());

	}
	
	/**
	 * The getContributorType() method will retrieve the contributor based on the given
	 * contributorId present in the database.
	 * 
	 * @param contributorId
	 * @return ContributorType data
	 * @throws NoDataExistsException if the contributorId is not present in the
	 *                                  database.
	 */

	public ContributorTypeDto getContributorType(int contributorId) {
		logger.info("ContributorType Details for the contributor id {}",contributorId);

		return mapper.map(
				contributorTypeRepository.findById(contributorId).orElseThrow(() -> new NoDataExistsException(contributorException)),
				ContributorTypeDto.class);

	}

	/**
	 * The saveContributorType() method will create the new ContributorType in the database based on
	 * the given data.
	 * 
	 * @param contributorDto
	 * @return inserted ContributorType data
	 */

	public ContributorTypeDto saveContributorType(ContributorTypeDto contributorDto) {
		logger.info("Creating a ContributorType with the details {}",contributorDto);

		ContributorType contributor = mapper.map(contributorDto, ContributorType.class);
		contributor.setContributerType(contributor.getContributerType().toLowerCase());
		return mapper.map(contributorTypeRepository.save(contributor), ContributorTypeDto.class);

	}

	/**
	 * The updateContributorType() method updates the ContributorType present in the database with
	 * given update details.
	 * 
	 * @param contributorId
	 * @param contributorDto
	 * @return Updated ContributorType Data
	 * @throws NoDataExistsException if the contributorId doesn't exist.
	 */
	public ContributorTypeDto updateContributorType(int contributorId, ContributorTypeDto contributorDto) {
		
		logger.info("Update ContributorType with contributor id {}{}",contributorId, contributorDto);
		
		ContributorType contributor = mapper.map(contributorDto, ContributorType.class);
		ContributorType existingContributorType = contributorTypeRepository.findById(contributorId)
				.orElseThrow(() -> new NoDataExistsException(contributorException));
		existingContributorType.setContributerType(contributor.getContributerType());
		existingContributorType.setPoints(contributor.getPoints());
		return mapper.map(contributorTypeRepository.save(existingContributorType), ContributorTypeDto.class);
	}

	/**
	 * The deleteContributorType() method delete the ContributorType present in the database with
	 * given contributorId.
	 * 
	 * @param contributorId
	 * @return nothing
	 * @throws NoDataExistsException if the contributorId doesn't exist.
	 */
	public ContributorTypeDto deleteContributorType(int contributorId) {

		logger.info("Delete ContributorType with contributor id {}",contributorId);
		ContributorType contributor = contributorTypeRepository.findById(contributorId)
				.orElseThrow(() -> new NoDataExistsException(contributorException));

		contributorTypeRepository.deleteById(contributorId);

		return mapper.map(contributor, ContributorTypeDto.class);

	}
}
