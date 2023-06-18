package com.epam.leaderboard.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import com.epam.leaderboard.dto.SkillsDTO;
import com.epam.leaderboard.entity.Skill;
import com.epam.leaderboard.exception.EntityNotFoundException;
import com.epam.leaderboard.repo.SkillsRepo;

/**
 * The BusinessUnitServiceTest class tests the methods which are present in
 * BusinessUnitService class. The class is annotated by 1 annotation:
 * 
 * @ExtendWith annotation used to register extensions for the annotated test
 *             class.
 * @author Bhargavi_Velugonda
 *
 */
@ExtendWith(MockitoExtension.class)
class SkillsServiceTest {
	
	@Mock
	private SkillsRepo skillRepository;
	
	private SkillsService skillService;

	private ModelMapper mapper = new ModelMapper();
	private List<Skill> skills = new ArrayList<>();
	private List<SkillsDTO> skillsDto = new ArrayList<>();
	private Skill skill = new Skill();
	private SkillsDTO skilldto = new SkillsDTO();

	/**
	 * The setup() method is basically executed before executing every single test
	 * case.
	 */
	@BeforeEach
	public void setup() {

		skillService = new SkillsService(skillRepository);
		skill.setSkillName("Java");
		skill.setId(1);
		skills.add(skill);
		skilldto.setSkillName("Java");
		skilldto.setId(1);
		skillsDto.add(skilldto);

	}

	/**
	 * The getSkillssTest() method tests the functionality of retrieving
	 * all Skillss from the database.
	 * @throws Exception 
	 */
	@Test
	void getSkillssTest() throws Exception {

		when(skillRepository.findAll()).thenReturn(skills);
		skillsDto = mapper.map(skills,
				new TypeToken<List<SkillsDTO>>() {
				}.getType());
		assertEquals(skillsDto.size(), skillService.getAllSkillss().size());

	}

	/**
	 * The getSkillssTestFail() method tests the functionality of
	 * retrieving all the skills when no skills are present in
	 * the database.
	 */
	@Test
	void getSkillssTestFail() {
		List<Skill> emptySkillsList = new ArrayList<>();
		when(skillRepository.findAll()).thenReturn(emptySkillsList);
		assertThrows(EntityNotFoundException.class, () -> skillService.getAllSkillss());

	}

}
