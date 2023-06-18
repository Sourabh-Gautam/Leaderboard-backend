package com.epam.programmanagement.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.programmanagement.dto.ProgramTemplateDto;
import com.epam.programmanagement.exception.ProgramNotFoundException;
import com.epam.programmanagement.exception.ProgramTemplateNotFound;
import com.epam.programmanagement.model.ProgramTemplate;
import com.epam.programmanagement.repo.ProgramTemplateRepo;
/**
 * ProgramTemplateService class has all the methods such as creating , reading ,
 * updating and deleting a programTemplate.
 * 
 * It is annotated by the @Service annotation marking it as a bean.
 * @author Sneka_P
 *
 */
@Service
public class ProgramTemplateService {
	@Autowired
	private ProgramTemplateRepo programTemplateRepo;
	private ModelMapper mapper = new ModelMapper();
	private String programTemplateException = "ProgramTemplate with given id not present";
	private static Logger logger = LogManager.getLogger(ProgramTemplateService.class);
	public ProgramTemplateService(ProgramTemplateRepo programTemplateRepo) {
		this.programTemplateRepo = programTemplateRepo;
	}

	/**
	 * The getAllProgramTemplates() method will retrieve all the programsTemplate
	 * present in the database.
	 * 
	 * @return List of programsTemplate
	 * @throws ProgramTemplateNotFound if there are no programsTemplate in the
	 *                                 database.
	 */

	public List<ProgramTemplateDto> getAllProgramTemplates() {
		logger.info("Display all the programTemplate");
		List<ProgramTemplate> programTemplate = programTemplateRepo.findAll();
		if (programTemplate.isEmpty()) {
			throw new ProgramTemplateNotFound("No ProgramTemplate Exists");
		}

		return mapper.map(programTemplate, new TypeToken<List<ProgramTemplateDto>>() {
		}.getType());

	}

	/**
	 * The getProgramTemplate() method will retrieve the ProgramTemplate based on
	 * the given programTemplateId present in the database.
	 * 
	 * @param programTemplateId
	 * @return ProgramTemplate data
	 * @throws ProgramTemplateNotFound if the programTemplateId is not present in
	 *                                 the database.
	 */

	public ProgramTemplateDto getProgramTemplate(int programTemplateId) {
		logger.info("ProgramTemplate Details for the programTemplate id {}", programTemplateId);

		return mapper.map(programTemplateRepo.findById(programTemplateId)
				.orElseThrow(() -> new ProgramTemplateNotFound(programTemplateException)), ProgramTemplateDto.class);

	}

	/**
	 * The saveProgramTemplate() method will create the new ProgramTemplate in the
	 * database based on the given data.
	 * 
	 * @param ProgramTemplateDto
	 * @return inserted ProgramTemplate data
	 */

	public ProgramTemplateDto saveProgramTemplate(ProgramTemplateDto programTemplateDto) {
		logger.info("Creating a ProgramTemplate with the details {}", programTemplateDto);

		ProgramTemplate programTemplate = mapper.map(programTemplateDto, ProgramTemplate.class);

		return mapper.map(programTemplateRepo.save(programTemplate), ProgramTemplateDto.class);

	}

	/**
	 * The updateProgramTemplate() method updates the ProgramTemplate present in the database with
	 * given update details.
	 * 
	 * @param programTemplateId
	 * @param programTemplateDto
	 * @return Updated programTemplate Data
	 * @throws ProgramTemplateNotFound if the ProgramTemplateId doesn't exist.
	 */
	public ProgramTemplateDto updateProgramTemplate(int programTemplateId, ProgramTemplateDto programTemplateDto) {

		logger.info("Update Program with program id {}{}", programTemplateId, programTemplateDto);

		ProgramTemplate programTemplate = mapper.map(programTemplateDto, ProgramTemplate.class);
		ProgramTemplate existingprogramTemplate = programTemplateRepo.findById(programTemplateId)
				.orElseThrow(() -> new ProgramTemplateNotFound(programTemplateException));

		existingprogramTemplate.setCategory(programTemplate.getCategory());
		existingprogramTemplate.setDescription(programTemplate.getDescription());

		existingprogramTemplate.setWeightage(programTemplate.getWeightage());
		return mapper.map(programTemplateRepo.save(existingprogramTemplate), ProgramTemplateDto.class);
	}

	/**
	 * The deleteProgramTemplate() method delete the Program present in the database
	 * with given programId.
	 * 
	 * @param programId
	 * @return nothing
	 * @throws ProgramNotFoundException if the programId doesn't exist.
	 */
	public ProgramTemplateDto deleteProgramTemplate(int programTemplateId) {

		logger.info("Delete Program with program id {}", programTemplateId);
		ProgramTemplate programTemplate = programTemplateRepo.findById(programTemplateId)
				.orElseThrow(() -> new ProgramTemplateNotFound(programTemplateException));

		programTemplateRepo.deleteById(programTemplateId);

		return mapper.map(programTemplate, ProgramTemplateDto.class);

	}
}
