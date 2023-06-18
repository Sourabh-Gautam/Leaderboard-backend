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

import com.epam.leaderboard.dto.DesignationDTO;
import com.epam.leaderboard.entity.RoleDesignation;
import com.epam.leaderboard.exception.EntityNotFoundException;
import com.epam.leaderboard.repo.DesignationRepo;

/**
 * The DesignationServiceTest class tests the methods which are present in
 * DesignationService class. The class is annotated by 1 annotation:
 * 
 * @ExtendWith annotation used to register extensions for the annotated test
 *             class.
 * @author Bhargavi_Velugonda
 *
 */
@ExtendWith(MockitoExtension.class)
class DesignationServiceTest {

	@Mock
	private DesignationRepo designationRepository;
	
	private DesignationService designationService;

	private ModelMapper mapper = new ModelMapper();
	private List<RoleDesignation> designations = new ArrayList<>();
	private List<DesignationDTO> designationsDto = new ArrayList<>();
	private RoleDesignation designation = new RoleDesignation();
	private DesignationDTO designationdto = new DesignationDTO();

	/**
	 * The setup() method is basically executed before executing every single test
	 * case.
	 */
	@BeforeEach
	public void setup() {

		designationService = new DesignationService(designationRepository);
		designation.setDesignation("Software Engineer");
		designation.setId(1);
		designations.add(designation);
		designationdto.setDesignation("Software Engineer");
		designationdto.setId(1);
		designationsDto.add(designationdto);

	}

	/**
	 * The getDesignationsTest() method tests the functionality of retrieving
	 * all Designations from the database.
	 * @throws Exception 
	 */
	@Test
	void getDesignationsTest() throws Exception {

		when(designationRepository.findAll()).thenReturn(designations);
		designationsDto = mapper.map(designations,
				new TypeToken<List<DesignationDTO>>() {
				}.getType());
		assertEquals(designationsDto.size(), designationService.getAllDesignations().size());

	}

	/**
	 * The getDesignationsTestFail() method tests the functionality of
	 * retrieving all the designations when no designations are present in
	 * the database.
	 */
	@Test
	void getDesignationsTestFail() {
		List<RoleDesignation> emptyDesignationList = new ArrayList<>();
		when(designationRepository.findAll()).thenReturn(emptyDesignationList);
		assertThrows(EntityNotFoundException.class, () -> designationService.getAllDesignations());

	}
}
