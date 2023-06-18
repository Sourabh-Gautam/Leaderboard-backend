package com.epam.programmanagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyInt;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import com.epam.programmanagement.dto.ProgramDto;
import com.epam.programmanagement.exception.NoDataExistsException;
import com.epam.programmanagement.exception.ProgramNotFoundException;
import com.epam.programmanagement.model.Program;
import com.epam.programmanagement.repo.ProgramRepository;

/**
 * The ProgramServiceTest class tests the methods which are present in
 * ProgramService class. The class is annotated by 1 annotation:
 * 
 * @ExtendWith annotation used to register extensions for the annotated test
 *             class.
 * @author Vahini_Nune
 *
 */
@ExtendWith(MockitoExtension.class)
class ProgramServiceTest {
	@Mock
	private ProgramRepository programRepository;
	private ProgramService programService;

	private ModelMapper mapper = new ModelMapper();
	private List<Program> programs = new ArrayList<>();
	private Program program = new Program();
	private List<Integer> organizers = new ArrayList<>();

	/**
	 * The setup() method is basically executed before executing every single test
	 * case.
	 */
	@BeforeEach
	public void setup() {
		organizers.add(1);
		organizers.add(2);
		organizers.add(3);

		programService = new ProgramService(programRepository);
		program.setAddedBy("Vahini");
		program.setDescription("DataStructures");
		program.setProgramId(2);
		program.setWeightage(15);
		program.setCategory("Incubation");
		programs.add(program);

	}

	/**
	 * The addProgramTest() method tests the functionality of adding a new Program into the database.
	 */
	@Test
	void addProgramTest() {

		when(programRepository.save(any(Program.class))).thenReturn(program);
		ProgramDto programDto = mapper.map(program, ProgramDto.class);
		assertEquals(programDto.getTitle(), programService.saveProgram(programDto).getTitle());

	}
  
	/**
	 * The getProgramsTest() method tests the functionality of retrieving all Programs from the database.
	 */
	@Test
	void getProgramsTest() {

		when(programRepository.findAll()).thenReturn(programs);
		List<ProgramDto> programsDto = mapper.map(programs, new TypeToken<List<ProgramDto>>() { 
		}.getType());
		assertEquals(programsDto.size(), programService.getAllPrograms().size());

	}

	/**
	 * The getProgramsTestFail() method tests the functionality of retrieving all the programs when no programs are present in the database.
	 */
	@Test
	void getProgramsTestFail() {
		List<Program> emptyProgramList = new ArrayList<>();
		when(programRepository.findAll()).thenReturn(emptyProgramList);
		assertThrows(NoDataExistsException.class, () -> programService.getAllPrograms());

	}

	/**
	 * The getProgramByIdTest() method tests the functionality of retrieving a Program based on programId which present in the database.
	 */
	@Test
	void getProgramByIdTest() {
		when(programRepository.findById(anyInt())).thenReturn(Optional.of(program));
		ProgramDto programDto = mapper.map(program, ProgramDto.class);
		assertEquals(programDto.getTitle(), programService.getProgram(1).getTitle());
	}

	/**
	 * The getProgramByIdTestFail() method tests the functionality of retrieving a Program based on programId which is not present in the database.
	 */
	@Test
	void getProgramByIdTestFail() {
		when(programRepository.findById(anyInt())).thenReturn(Optional.empty());
		assertThrows(ProgramNotFoundException.class, () -> programService.getProgram(10));

	}
	/**
	 * The updateProgramTest() method tests the functionality of updating a Program based on programId in the database.
	 */
	@Test
	void updateProgramTest() {
		when(programRepository.findById(anyInt())).thenReturn(Optional.of(program));
		ProgramDto programDto = mapper.map(program, ProgramDto.class);

		ProgramDto updatedDto = new ProgramDto();
		updatedDto.setAddedBy("Alex");
		updatedDto.setDescription("DataStructures");
		updatedDto.setProgramId(2);
		updatedDto.setWeightage(15);
		updatedDto.setCategory("Incubation");
		programDto.setAddedBy("Alex");
		Program updatedProgram = mapper.map(updatedDto, Program.class);

		when(programRepository.save(any(Program.class))).thenReturn(updatedProgram);

		assertEquals(programDto.getTitle(), programService.updateProgram(2, updatedDto).getTitle());
	}
   
	/**
	 * The updateProgramFail() method tests the functionality of updating a Program with the id which is not present in the  database.
	 */
	@Test
	void updateProgramFail() {
		when(programRepository.findById(100)).thenReturn(Optional.empty());
		ProgramDto programDto = mapper.map(program, ProgramDto.class);
		assertThrows(ProgramNotFoundException.class, () -> programService.updateProgram(100, programDto));
	}
	/**
	 * The  deleteProgramTest() method tests the functionality of deleting a  Program based on the id from the  database.
	 */
	@Test
	void deleteProgramTest() {
		when(programRepository.findById(anyInt())).thenReturn(Optional.ofNullable(program));
		ProgramDto programDto = mapper.map(program, ProgramDto.class);
		assertEquals(programDto.getTitle(), programService.deleteProgram(1).getTitle());
	}
	/**
	 * The deleteBookFail() method tests the functionality of deleting a Program with the id which is not present.
	 * It throws an exception called ProgramNotFound.
	 */
	@Test
	void deleteProgramFail() {
		when(programRepository.findById(anyInt())).thenReturn(Optional.empty());

		assertThrows(ProgramNotFoundException.class, () -> programService.deleteProgram(100));
	}
}
