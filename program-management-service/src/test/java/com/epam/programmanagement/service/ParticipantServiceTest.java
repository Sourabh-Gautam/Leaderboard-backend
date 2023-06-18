package com.epam.programmanagement.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import com.epam.programmanagement.dto.ParticipantDto;
import com.epam.programmanagement.dto.ProgramDto;
import com.epam.programmanagement.exception.ParticipantNotFoundException;
import com.epam.programmanagement.model.ContributorType;
import com.epam.programmanagement.model.Participant;
import com.epam.programmanagement.model.Program;
import com.epam.programmanagement.repo.ContributorTypeRepository;
import com.epam.programmanagement.repo.ParticipantRepository;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * The ParticipantServiceTest class tests the methods which are present in
 * ParticipantService class.
 * 
 * 
 * 
 * @author Bhargavi_Velugonda
 *
 */

@ExtendWith(MockitoExtension.class)
class ParticipantServiceTest {

	@InjectMocks
	private ParticipantService participantService;
	@Mock
	private ProgramService programService;
	@Mock
	private ParticipantRepository participantRepository;
	@Mock
	private ContributorTypeRepository contributorTypeRepository;
	@Mock
	private ModelMapper modelMapper;

	private Participant participant;
	private ParticipantDto participantDto;
	private Program program = new Program();
	@Mock
	private ProgramDto programDto = new ProgramDto();
	private Set<ParticipantDto> setOfParticipant;
	private Set<Participant> setOfParticipants = new HashSet<>();
	private Set<Program> setOfProgram = new HashSet<>();
    private ContributorType contributorType = new ContributorType();
	/**
	 * The setup() method is basically executed before executing every single test
	 * case.
	 */

	@BeforeEach
	public void setUp() throws Exception {
		contributorType.setContributerType("winner");
		contributorType.setPoints(50);
		contributorType.setId(1);
		List<Integer> organizers = new ArrayList<>();
		organizers.add(1);
		organizers.add(2);
		organizers.add(3);
		program.setProgramId(1);
		program.setAddedBy("Vahini");
		program.setCreatedAt(LocalDateTime.now());
		program.setDescription("DataStructures");
		program.setEndDate(null);
		program.setStartDate(null);
		program.setParticipant(setOfParticipants);
		program.setProgramId(1);
		program.setTitle("DSA");
		program.setWeightage(15);
		programDto.setProgramId(1);
		programDto.setAddedBy("Vahini");
		programDto.setCreatedAt(LocalDateTime.now());
		programDto.setDescription("DataStructures");
		programDto.setEndDate(null);
		programDto.setStartDate(null);
		programDto.setProgramId(2);
		programDto.setTitle("DSA");
		programDto.setWeightage(15);
		setOfParticipants.add(participant);
		programDto.setParticipant(setOfParticipants);

		setOfProgram.add(program);
		participant = new Participant();
		participant.setParticipantId(1);
		participant.setParticipantName("sneka");
		participant.setContributorType("winner");
		
		participant.setProgram(setOfProgram);
		participant.setPoints(3);
		participant.setAddedBy("sneka");
		participant.setLastModifiedAt(null);
		participant.setAwardedDate(null);
		participantDto = new ParticipantDto();
		participantDto.setParticipantId(1);
		participantDto.setContributorType("winner");
		participantDto.setProgram(setOfProgram);
		participantDto.setPoints(3);
		participantDto.setParticipantName("sneka");
		participantDto.setAddedBy("sneka");
		participantDto.setLastModifiedAt(null);
		participantDto.setAwardedDate(null);
		setOfParticipant = new HashSet<>();
		setOfParticipant.add(participantDto);

	}

	/**
	 * The testAddParticipant() method tests the functionality of adding a new
	 * participant into the database.
	 * @throws JsonProcessingException 
	 */

	@Test
	void testAddParticipant() throws JsonProcessingException {
		when(contributorTypeRepository.findByContributerType("winner")).thenReturn(contributorType);
		when(participantRepository.save(any(Participant.class))).thenReturn(participant);
		when(modelMapper.map(participant, ParticipantDto.class)).thenReturn(participantDto);
		when(modelMapper.map(participantDto, Participant.class)).thenReturn(participant);
		assertEquals(participantDto, participantService.saveParticipant(participantDto));
	}

	/**
	 * The testGetParticipantsFail() method tests when data not present it give
	 * message.
	 */

	@Test
	void testGetParticipantsFail() {

		when(programService.getProgram(1)).thenReturn(programDto);
		Set<Participant> emptyParticipant = new HashSet<>();
		when(programDto.getParticipant()).thenReturn(emptyParticipant);
		assertThrows(ParticipantNotFoundException.class, () -> participantService.getAllParticipants(1));

	}

	/**
	 * The testGetParticipantById() method tests the functionality of retrieving a
	 * Participant based on programId which present in the database.
	 */

	@Test
	void testGetParticipantById() {
		when(programService.getProgram(1)).thenReturn(programDto);
		when(modelMapper.map(programDto, Program.class)).thenReturn(program);
		when(participantRepository.findByProgramAndParticipantId(program, 1)).thenReturn(Optional.of(participant));
		when(modelMapper.map(participant, ParticipantDto.class)).thenReturn(participantDto);
		ParticipantDto participantDto = modelMapper.map(participant, ParticipantDto.class);
		assertEquals(participantDto, participantService.getParticipant(1, 1));
		assertThrows(ParticipantNotFoundException.class, () -> participantService.getParticipant(2, 2));

	}

	/**
	 * The testUpdateParticipant() method tests the functionality of updating a
	 * Participant based on programId and participantId in the database.
	 */

	@Test
	void testUpdateParticipant() {
		when(programService.getProgram(1)).thenReturn(programDto);
		when(modelMapper.map(programDto, Program.class)).thenReturn(program);
		when(participantRepository.findByProgramAndParticipantId(program, 1)).thenReturn(Optional.of(participant));
		when(participantRepository.save(any(Participant.class))).thenReturn(participant);
		when(modelMapper.map(participant, ParticipantDto.class)).thenReturn(participantDto);
		assertEquals(participantDto, participantService.updateParticipant(1, 1, participantDto));
		assertThrows(ParticipantNotFoundException.class,
				() -> participantService.updateParticipant(2, 2, participantDto));
	}

	/**
	 * The testDeleteParticipant() method tests the functionality of deleting a
	 * participant based on the id from the database.
	 */

	@Test
	void testDeleteParticipant() {

		when(participantService.getProgramById(1)).thenReturn(program);
		when(participantRepository.findByProgramAndParticipantId(program, 1)).thenReturn(Optional.of(participant));
		doNothing().when(participantRepository).delete(participant);
		participantService.deleteParticipant(1, 1);
		verify(participantRepository, times(1)).delete(participant);
		assertThrows(ParticipantNotFoundException.class, () -> participantService.deleteParticipant(2, 2));

	}

	/**
	 * testGetProgram() method test the functionality of get the program based on id
	 */
	@Test
	void testGetProgram() {
		when(programService.getProgram(anyInt())).thenReturn(programDto);
		when(modelMapper.map(programDto, Program.class)).thenReturn(program);
		assertEquals(program, participantService.getProgramById(1));
	}
	
	/**
	 * The testGetParticipantsByEmail() method tests the functionality of retrieving all
	 * Participants based on email from the database.
	 */

	@Test
	void testGetParticipantsByEmail() {

		when(participantRepository.findAllByEmail(any(), any(), any())).thenReturn(setOfParticipants);

		Set<ParticipantDto> listOfParticipantDto = modelMapper.map(setOfParticipant,
				new TypeToken<Set<ParticipantDto>>() {
				}.getType());

		assertEquals(listOfParticipantDto, participantService.getParticipantByEmail("bhanu@epam.com", 2023));
	}

	/**
	 * The testGetParticipantsByDesignationTest() method tests the functionality of retrieving all
	 * Participants based on designation from the database.
	 */

	@Test
	void testGetParticipantsByDesignationTest() {

		when(participantRepository.findAllByDesignation(any())).thenReturn(setOfParticipants);

		Set<ParticipantDto> listOfParticipantDto = modelMapper.map(setOfParticipant,
				new TypeToken<Set<ParticipantDto>>() {
				}.getType());

		assertEquals(listOfParticipantDto, participantService.getParticipantByDesignation("Junior Software Engineer"));
	}

	/**
	 * The testGetParticipantsByRMTest() method tests the functionality of retrieving all
	 * Participants based on resource manager from the database.
	 */

	@Test
	void testGetParticipantsByRMTest() {

		when(participantRepository.findAllByResourceManager(any())).thenReturn(setOfParticipants);

		Set<ParticipantDto> listOfParticipantDto = modelMapper.map(setOfParticipant,
				new TypeToken<Set<ParticipantDto>>() {
				}.getType());

		assertEquals(listOfParticipantDto, participantService.getParticipantByResourceManager("Shakti Vyas"));
	}

	/**
	 * The testGetParticipantsByBUTest() method tests the functionality of retrieving all
	 * Participants based on Business Unit from the database.
	 */

	@Test
	void testGetParticipantsByBUTest() {

		when(participantRepository.findAllByBusinessUnit(any())).thenReturn(setOfParticipants);

		Set<ParticipantDto> listOfParticipantDto = modelMapper.map(setOfParticipant,
				new TypeToken<Set<ParticipantDto>>() {
				}.getType());

		assertEquals(listOfParticipantDto, participantService.getParticipantByBusinessUnit("HCLS DElivery"));
	}

	/**
	 * The testGetParticipantsByPSTest() method tests the functionality of retrieving all
	 * Participants based on Primary Skill from the database.
	 */

	@Test
	void testGetParticipantsByPSTest() {

		when(participantRepository.findAllByPrimarySkill(any())).thenReturn(setOfParticipants);

		Set<ParticipantDto> listOfParticipantDto = modelMapper.map(setOfParticipant,
				new TypeToken<Set<ParticipantDto>>() {
				}.getType());

		assertEquals(listOfParticipantDto, participantService.getParticipantByyPrimarySkill("Java"));
	}

}
