package com.epam.programmanagement.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.programmanagement.dto.ParticipantDto;
import com.epam.programmanagement.dto.ProgramDto;
import com.epam.programmanagement.exception.ParticipantNotFoundException;
import com.epam.programmanagement.model.ContributorType;
import com.epam.programmanagement.model.Participant;
import com.epam.programmanagement.model.Program;
import com.epam.programmanagement.repo.ContributorTypeRepository;
import com.epam.programmanagement.repo.ParticipantRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

/**
 * ParticipantService class has ability to perform various task like add,
 * delete, update, get participant.
 * 
 * @service - Mark this class as a bean
 * 
 * @author Sneka_P
 *
 */
@Service
public class ParticipantService {
	@Autowired
	private ParticipantRepository participantRepository;
	@Autowired
	private ContributorTypeRepository contributorTypeRepository;
	@Autowired
	ProgramService programService;
	private ModelMapper modelMapper = new ModelMapper();
	private String participantException = "Participant with given  id not present";
	public static final LocalDateTime currentDateTime = LocalDateTime.now();
	private static Logger logger = LogManager.getLogger(ParticipantService.class);

	/**
	 * The getAllParticipants() method its getting all the participant based on
	 * program id
	 * 
	 * @param programId is the foreign key of program table
	 * @throws if participant is empty then it will throw
	 *            ParticipantNotFoundException
	 * @return Set of participants
	 */
	public List<ParticipantDto> getAllParticipants(int programId) throws Exception {
		logger.info("Display all the Participants {}", programId);
		ProgramDto program = programService.getProgram(programId);

		Set<Participant> participants = program.getParticipant();
		List<ParticipantDto> participantDto = new ArrayList<>();
		
		for (Participant p : participants) {
			ParticipantDto obj = modelMapper.map(p, ParticipantDto.class);
			ObjectMapper mapper = new ObjectMapper();
			Gson gson = new Gson();
			String[] stringArray = gson.fromJson(p.getSubSkill(), String[].class);
	        List<String> list = Arrays.asList(stringArray);
			obj.setSubSkill(list);
			participantDto.add(obj);
		}

		if (participants.isEmpty()) {
			throw new ParticipantNotFoundException("given id =" + programId + " not present in the participant table");
		}
		return participantDto;
	}

	/**
	 * The saveParticipant() method create the new record in database
	 * 
	 * @param participantDto
	 * @return Participant details
	 * @throws JsonProcessingException
	 */
	public ParticipantDto saveParticipant(ParticipantDto participantDto) throws JsonProcessingException {
		logger.info("Create the participant {} ", participantDto);
		logger.info("Contributor type {}", participantDto.getContributorType());

		ContributorType contributorType = contributorTypeRepository
				.findByContributerType(participantDto.getContributorType());
		List<Program> program = participantDto.getProgram().stream().toList();
		int programWeight = program.get(0).getWeightage();
		int points = programWeight * contributorType.getPoints();
		participantDto.setPoints(points);
		participantDto.setAwardedDate(LocalDate.now());
		Participant participant = modelMapper.map(participantDto, Participant.class);
		Gson gson = new Gson();
        String jsonSubSkill = gson.toJson(participantDto.getSubSkill());
		participant.setSubSkill(jsonSubSkill);
		Participant participant2 = participantRepository.save(participant);
		return modelMapper.map(participant2, ParticipantDto.class);
	}

	/**
	 * The getParticipant() method getting the data based on programId and
	 * participant id
	 * 
	 * @param programId     is the foreign key of program table
	 * @param participantId is the foreign key of user table
	 * @return participantDto
	 */
	public ParticipantDto getParticipant(int programId, int participantId) {
		logger.info("dispaly participant based on id {} {}", programId, participantId);
		Program program = getProgramById(programId);
		Participant participantById = participantRepository.findByProgramAndParticipantId(program, participantId)
				.orElseThrow(() -> new ParticipantNotFoundException(participantException));
		return modelMapper.map(participantById, ParticipantDto.class);

	}

	/**
	 * The updateParticipant() method update all the details of participant
	 * 
	 * @param programId      is the foreign key of program table
	 * @param participantId  is the foreign key of user table
	 * @param participantDto details
	 * @return participantDto
	 */
	public ParticipantDto updateParticipant(int programId, int participantId, ParticipantDto participantDto) {
		logger.info("update participant based on id {} {}", programId, participantId);
		logger.info(participantDto);
		Program program = getProgramById(programId);
		Participant existingParticipant = participantRepository.findByProgramAndParticipantId(program, participantId)
				.orElseThrow(() -> new ParticipantNotFoundException(participantException));
		existingParticipant.setAddedBy(participantDto.getAddedBy());
		existingParticipant.setPoints(participantDto.getPoints());
		existingParticipant.setContributorType(participantDto.getContributorType());
		existingParticipant.setLastModifiedAt(LocalDate.now());
		existingParticipant.setParticipantId(participantId);
		existingParticipant.setProgram(participantDto.getProgram());
		existingParticipant.setPoints(participantDto.getPoints());
		existingParticipant.setParticipantName(participantDto.getParticipantName());
		return modelMapper.map(participantRepository.save(existingParticipant), ParticipantDto.class);

	}

	/**
	 * The deleteParticipant is deleting the participant based on programId and
	 * participantId
	 * 
	 * @param programId     is the foreign key of program table
	 * @param participantId is the foreign key of user table
	 */
	public void deleteParticipant(int programId, int participantId) {
		logger.info("delete participant based on id {} {}", programId, participantId);
		Program program = getProgramById(programId);
		Participant participant = participantRepository.findByProgramAndParticipantId(program, participantId)
				.orElseThrow(() -> new ParticipantNotFoundException(participantException));
		participant.getProgram().clear();
		participantRepository.delete(participant);

	}

	/**
	 * the getProgramById() method get the program based on id in the programService
	 * 
	 * @param programId is the foreign key of program table
	 * @return Program data
	 */
	public Program getProgramById(int programId) {
		logger.info("get the program {}", programId);
		ProgramDto programDto = programService.getProgram(programId);
		return (modelMapper.map(programDto, Program.class));
	}

	public Set<ParticipantDto> getParticipantByEmail(String email, int selectedYear) {
		LocalDate startOfCurrentYear = LocalDate.parse("%s-01-01".formatted(selectedYear),
				DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalDate endOfCurrentYear = startOfCurrentYear.plusYears(1);
		Set<Participant> participantById = participantRepository.findAllByEmail(email, startOfCurrentYear,
				endOfCurrentYear);
		return modelMapper.map(participantById, new TypeToken<Set<ParticipantDto>>() {
		}.getType());
	}

	public Set<ParticipantDto> getParticipantByDesignation(String designation) {
		logger.info("designation {}", designation);
		Set<Participant> participantById = participantRepository.findAllByDesignation(designation);
		return modelMapper.map(participantById, new TypeToken<Set<ParticipantDto>>() {
		}.getType());
	}

	public Set<ParticipantDto> getParticipantByBusinessUnit(String bu) {
		logger.info("Business unit {}", bu);
		Set<Participant> participantById = participantRepository.findAllByBusinessUnit(bu);
		return modelMapper.map(participantById, new TypeToken<Set<ParticipantDto>>() {
		}.getType());
	}

	public Set<ParticipantDto> getParticipantByyPrimarySkill(String ps) {
		logger.info("Primary skill {}", ps);
		Set<Participant> participantById = participantRepository.findAllByPrimarySkill(ps);
		return modelMapper.map(participantById, new TypeToken<Set<ParticipantDto>>() {
		}.getType());
	}

	public Set<ParticipantDto> getParticipantByResourceManager(String rm) {
		logger.info("Resource manager {}", rm);
		Set<Participant> participantById = participantRepository.findAllByResourceManager(rm);
		return modelMapper.map(participantById, new TypeToken<Set<ParticipantDto>>() {
		}.getType());
	}

}
