package com.epam.programmanagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.epam.programmanagement.dto.ProgramTemplateDto;
import com.epam.programmanagement.exception.ProgramTemplateNotFound;
import com.epam.programmanagement.model.ProgramTemplate;
import com.epam.programmanagement.repo.ProgramTemplateRepo;

/**
 * The ProgramServiceTemplateTest class tests the methods which are present in
 * ProgramTemplateService class. The class is annotated by 1 annotation:
 * 
 * @ExtendWith annotation used to register extensions for the annotated test
 *             class.
 * @author Sneka_P
 *
 */
@ExtendWith(MockitoExtension.class)
class ProgramTemplateServiceTest {
	@Mock
	private ProgramTemplateRepo programTemplateRepository;
	
	private ProgramTemplateService programTemplateService ;
	private ModelMapper mapper = new ModelMapper();
	private List<ProgramTemplate> programTemplateList = new ArrayList<>();
	private List<ProgramTemplateDto> programTemplateListDto = new ArrayList<>();
	private ProgramTemplate programTemplate = new ProgramTemplate();
	private ProgramTemplateDto programTemplateDto = new ProgramTemplateDto();
	/**
	 * The setup() method is basically executed before executing every single test
	 * case.
	 */
	@BeforeEach
	public void setup() {
		programTemplateService = new ProgramTemplateService(programTemplateRepository);
		programTemplate.setCategory("dsa");
		programTemplate.setDescription("DataStructures");
		programTemplate.setProgramTemplateId(1);
		programTemplate.setWeightage(2.0f);
		programTemplateDto.setCategory("dsa");
		programTemplateDto.setDescription("DataStructures");
		programTemplateDto.setProgramTemplateId(1);
		programTemplateDto.setWeightage(2.0f);
		programTemplateList.add(programTemplate);
		programTemplateListDto.add(programTemplateDto);

	}

	/**
	 * The addProgramTemplateTest() method tests the functionality of adding a new
	 * Program into the database.
	 */
	@Test
	void addProgramTemplateTest() {

		when(programTemplateRepository.save(any(ProgramTemplate.class))).thenReturn(programTemplate);

		ProgramTemplateDto programTemplateDto = mapper.map(programTemplate, ProgramTemplateDto.class);

		assertEquals(programTemplateDto.getCategory(), programTemplateService.saveProgramTemplate(programTemplateDto).getCategory());

	}

	/**
	 * The getProgramTemplateTest() method tests the functionality of retrieving all
	 * Programs from the database.
	 */
	@Test
	void getProgramTemplateTest() {

		when(programTemplateRepository.findAll()).thenReturn(programTemplateList);
//		List<ProgramTemplateDto> programsDto = mapper.map(programTemplate, new TypeToken<List<ProgramTemplateDto>>() {
//		}.getType());
		assertEquals(programTemplateListDto.size(), programTemplateService.getAllProgramTemplates().size());

	}

	/**
	 * The getProgramTemplateTestFail() method tests the functionality of retrieving all
	 * the programTemplate when no programTemplate are present in the database.
	 */
	@Test
	void getProgramTemplateTestFail() {
		List<ProgramTemplate> emptyProgramTemplateList = new ArrayList<>();
		when(programTemplateRepository.findAll()).thenReturn(emptyProgramTemplateList);
		assertThrows(ProgramTemplateNotFound.class, () -> programTemplateService.getAllProgramTemplates());

	}

	/**
	 * The getProgramTemplateByIdTest() method tests the functionality of retrieving a
	 * Program based on programTemplateId which present in the database.
	 */
	@Test
	void getProgramTemplateByIdTest() {
		when(programTemplateRepository.findById(anyInt())).thenReturn(Optional.of(programTemplate));
		ProgramTemplateDto programTemplateDto = mapper.map(programTemplate, ProgramTemplateDto.class);
		assertEquals(programTemplateDto.getCategory(), programTemplateService.getProgramTemplate(1).getCategory());
	}

	/**
	 * The getProgramTemplateByIdTestFail() method tests the functionality of retrieving a
	 * Program based on programTemplateId which is not present in the database.
	 */
	@Test
	void getProgramTemplateByIdTestFail() {
		when(programTemplateRepository.findById(anyInt())).thenReturn(Optional.empty());
		assertThrows(ProgramTemplateNotFound.class, () -> programTemplateService.getProgramTemplate(10));

	}

	/**
	 * The updateProgramTemplateTest() method tests the functionality of updating a ProgramTemplate
	 * based on programTemplateId in the database.
	 */
	@Test
	void updateProgramTemplateTest() {
		when(programTemplateRepository.findById(anyInt())).thenReturn(Optional.of(programTemplate));
		ProgramTemplateDto programDto = mapper.map(programTemplate, ProgramTemplateDto.class);

		ProgramTemplateDto updatedDto = new ProgramTemplateDto();
		updatedDto.setCategory("techtalk");
		updatedDto.setDescription("DataStructures");
		updatedDto.setWeightage(2.0f);
		updatedDto.setProgramTemplateId(1);
		ProgramTemplate updatedProgramTemplate = mapper.map(updatedDto, ProgramTemplate.class);

		when(programTemplateRepository.save(any(ProgramTemplate.class))).thenReturn(updatedProgramTemplate);
		programTemplateDto.setCategory("techtalk");
		assertEquals(programTemplateDto.getCategory(), programTemplateService.updateProgramTemplate(2, updatedDto).getCategory());
	}

	/**
	 * The updateProgramTemplateFail() method tests the functionality of updating a ProgramTemplate
	 * with the id which is not present in the database.
	 */
	@Test
	void updateProgramTemplateFail() {
		when(programTemplateRepository.findById(100)).thenReturn(Optional.empty());
		ProgramTemplateDto programTemplateDto = mapper.map(programTemplate, ProgramTemplateDto.class);
		assertThrows(ProgramTemplateNotFound.class, () -> programTemplateService.updateProgramTemplate(100, programTemplateDto));
	}

	/**
	 * The deleteProgramTemplateTest() method tests the functionality of deleting a ProgramTemplate
	 * based on the id from the database.
	 */
	@Test
	void deleteProgramTemplateTest() {
		when(programTemplateRepository.findById(anyInt())).thenReturn(Optional.ofNullable(programTemplate));
		ProgramTemplateDto programTemplateDto = mapper.map(programTemplate, ProgramTemplateDto.class);
		assertEquals(programTemplateDto.getCategory(), programTemplateService.deleteProgramTemplate(1).getCategory());
	}

	/**
	 * The deleteprogramTemplateFail() method tests the functionality of deleting a ProgramTemplate
	 * with the id which is not present. It throws an exception called
	 * ProgramNotFound.
	 */
	@Test
	void deleteProgramTemplateFail() {
		when(programTemplateRepository.findById(anyInt())).thenReturn(Optional.empty());

		assertThrows(ProgramTemplateNotFound.class, () -> programTemplateService.deleteProgramTemplate(100));
	}
}
