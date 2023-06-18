package com.epam.programmanagement.service;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.programmanagement.dto.ProgramDto;
import com.epam.programmanagement.exception.NoDataExistsException;
import com.epam.programmanagement.exception.ProgramNotFoundException;
import com.epam.programmanagement.model.Program;
import com.epam.programmanagement.repo.ProgramRepository;

/**
 * ProgramService class has all the methods such as creating , reading ,
 * updating and deleting a program.
 * 
 * It is annotated by the @Service annotation marking it as a bean.
 * 
 * @author Vahini_Nune
 *
 */
@Service
public class ProgramService {

	@Autowired
	private ProgramRepository programRepository;
	private ModelMapper mapper = new ModelMapper();
	private String programException = "Program with given id not present";
	private static Logger logger = LogManager.getLogger(ProgramService.class);

	public ProgramService(ProgramRepository programRepository) {
		this.programRepository = programRepository;
	}

	/**
	 * The getAllPrograms() method will retrieve all the programs present in the
	 * database.
	 * 
	 * @return List of Programs
	 * @throws NoDataExistsException if there are no programs in the database.
	 */

	public List<ProgramDto> getAllPrograms() {
		logger.info("Display all the programs");
		List<Program> programs = programRepository.findAll();
		if (programs.isEmpty()) {
			throw new NoDataExistsException("No Program Exists");
		}

		return mapper.map(programs, new TypeToken<List<ProgramDto>>() {
		}.getType());

	}

	/**
	 * The getProgram() method will retrieve the program based on the given
	 * programId present in the database.
	 * 
	 * @param programId
	 * @return Program data
	 * @throws ProgramNotFoundException if the programId is not present in the
	 *                                  database.
	 */

	public ProgramDto getProgram(int programId) {
		logger.info("Program Details for the program id {}",programId);

		return mapper.map(
				programRepository.findById(programId).orElseThrow(() -> new ProgramNotFoundException(programException)),
				ProgramDto.class);

	}

	/**
	 * The saveProgram() method will create the new Program in the database based on
	 * the given data.
	 * 
	 * @param programDto
	 * @return inserted Program data
	 */

	public ProgramDto saveProgram(ProgramDto programDto) {
		logger.info("Creating a Program with the details {}",programDto);

		Program program = mapper.map(programDto, Program.class);
		LocalDateTime currentDateTime = LocalDateTime.now();
		program.setCreatedAt(currentDateTime);
		return mapper.map(programRepository.save(program), ProgramDto.class);

	}

	/**
	 * The updateProgram() method updates the Program present in the database with
	 * given update details.
	 * 
	 * @param programId
	 * @param programDto
	 * @return Updated Program Data
	 * @throws ProgramNotFoundException if the programId doesn't exist.
	 */
	public ProgramDto updateProgram(int programId, ProgramDto programDto) {
		
		logger.info("Update Program with program id {}{}",programId, programDto);
		
		Program program = mapper.map(programDto, Program.class);
		Program existingProgram = programRepository.findById(programId)
				.orElseThrow(() -> new ProgramNotFoundException(programException));
		existingProgram.setTitle(program.getTitle());
		existingProgram.setDescription(program.getDescription());
		existingProgram.setAddedBy(program.getAddedBy());
		existingProgram.setStartDate(program.getStartDate());
		existingProgram.setEndDate(program.getEndDate());
		existingProgram.setWeightage(program.getWeightage()); 
		existingProgram.setCategory(program.getCategory());
		return mapper.map(programRepository.save(existingProgram), ProgramDto.class);
	}

	/**
	 * The deleteProgram() method delete the Program present in the database with
	 * given programId.
	 * 
	 * @param programId
	 * @return nothing
	 * @throws ProgramNotFoundException if the programId doesn't exist.
	 */
	public ProgramDto deleteProgram(int programId) {

		logger.info("Delete Program with program id {}",programId);
		Program program = programRepository.findById(programId)
				.orElseThrow(() -> new ProgramNotFoundException(programException));

		programRepository.deleteById(programId);

		return mapper.map(program, ProgramDto.class);

	}

}
