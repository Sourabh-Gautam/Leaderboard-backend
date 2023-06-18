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
import org.modelmapper.TypeToken;

import com.epam.programmanagement.dto.ContributorTypeDto;
import com.epam.programmanagement.exception.NoDataExistsException;
import com.epam.programmanagement.model.ContributorType;
import com.epam.programmanagement.repo.ContributorTypeRepository;

/**
 * The ContributorTypeServiceTest class tests the methods which are present in
 * ContributorTypeService class. The class is annotated by 1 annotation:
 * 
 * @ExtendWith annotation used to register extensions for the annotated test
 *             class.
 * @author Bhargavi_Velugonda
 *
 */
@ExtendWith(MockitoExtension.class)
class ContributorTypeServiceTest {
	
	@Mock
	private ContributorTypeRepository contributorTypeRepository;
	private ContributorTypeService contributorTypeService;

	private ModelMapper mapper = new ModelMapper();
	private List<ContributorType> contributorTypes = new ArrayList<>();
	private ContributorType contributorType = new ContributorType();

	/**
	 * The setup() method is basically executed before executing every single test
	 * case.
	 */
	@BeforeEach
	public void setup() {

		contributorTypeService = new ContributorTypeService(contributorTypeRepository);
		contributorType.setContributerType("Winner");
		contributorType.setId(1);
		contributorType.setPoints(50);
		contributorTypes.add(contributorType);

	}

	/**
	 * The addContributorTypeTest() method tests the functionality of adding a new ContributorType into the database.
	 */
	@Test
	void addContributorTypeTest() {

		when(contributorTypeRepository.save(any(ContributorType.class))).thenReturn(contributorType);
		ContributorTypeDto contributorTypeDto = mapper.map(contributorType, ContributorTypeDto.class);
		assertEquals(contributorTypeDto.getContributerType(), contributorTypeService.saveContributorType(contributorTypeDto).getContributerType());

	}
  
	/**
	 * The getContributorTypesTest() method tests the functionality of retrieving all ContributorTypes from the database.
	 */
	@Test
	void getContributorTypesTest() {

		when(contributorTypeRepository.findAll()).thenReturn(contributorTypes);
		List<ContributorTypeDto> contributorTypesDto = mapper.map(contributorTypes, new TypeToken<List<ContributorTypeDto>>() { 
		}.getType());
		assertEquals(contributorTypesDto.size(), contributorTypeService.getAllContributorTypes().size());

	}

	/**
	 * The getContributorTypesTestFail() method tests the functionality of retrieving all the contributorTypes when no contributorTypes are present in the database.
	 */
	@Test
	void getContributorTypesTestFail() {
		List<ContributorType> emptyContributorTypeList = new ArrayList<>();
		when(contributorTypeRepository.findAll()).thenReturn(emptyContributorTypeList);
		assertThrows(NoDataExistsException.class, () -> contributorTypeService.getAllContributorTypes());

	}

	/**
	 * The getContributorTypeByIdTest() method tests the functionality of retrieving a ContributorType based on contributorTypeId which present in the database.
	 */
	@Test
	void getContributorTypeByIdTest() {
		when(contributorTypeRepository.findById(anyInt())).thenReturn(Optional.of(contributorType));
		ContributorTypeDto contributorTypeDto = mapper.map(contributorType, ContributorTypeDto.class);
		assertEquals(contributorTypeDto.getContributerType(), contributorTypeService.getContributorType(1).getContributerType());
	}

	/**
	 * The getContributorTypeByIdTestFail() method tests the functionality of retrieving a ContributorType based on contributorTypeId which is not present in the database.
	 */
	@Test
	void getContributorTypeByIdTestFail() {
		when(contributorTypeRepository.findById(anyInt())).thenReturn(Optional.empty());
		assertThrows(NoDataExistsException.class, () -> contributorTypeService.getContributorType(10));

	}
	/**
	 * The updateContributorTypeTest() method tests the functionality of updating a ContributorType based on contributorTypeId in the database.
	 */
	@Test
	void updateContributorTypeTest() {
		when(contributorTypeRepository.findById(anyInt())).thenReturn(Optional.of(contributorType));
		ContributorTypeDto contributorTypeDto = mapper.map(contributorType, ContributorTypeDto.class);

		ContributorTypeDto updatedDto = new ContributorTypeDto();
		updatedDto.setId(1);
		updatedDto.setContributerType("Winner");
		updatedDto.setPoints(50);
		ContributorType updatedContributorType = mapper.map(updatedDto, ContributorType.class);

		when(contributorTypeRepository.save(any(ContributorType.class))).thenReturn(updatedContributorType);

		assertEquals(contributorTypeDto.getContributerType(), contributorTypeService.updateContributorType(2, updatedDto).getContributerType());
	}
   
	/**
	 * The updateContributorTypeFail() method tests the functionality of updating a ContributorType with the id which is not present in the  database.
	 */
	@Test
	void updateContributorTypeFail() {
		when(contributorTypeRepository.findById(100)).thenReturn(Optional.empty());
		ContributorTypeDto contributorTypeDto = mapper.map(contributorType, ContributorTypeDto.class);
		assertThrows(NoDataExistsException.class, () -> contributorTypeService.updateContributorType(100, contributorTypeDto));
	}
	/**
	 * The  deleteContributorTypeTest() method tests the functionality of deleting a  ContributorType based on the id from the  database.
	 */
	@Test
	void deleteContributorTypeTest() {
		when(contributorTypeRepository.findById(anyInt())).thenReturn(Optional.ofNullable(contributorType));
		ContributorTypeDto contributorTypeDto = mapper.map(contributorType, ContributorTypeDto.class);
		assertEquals(contributorTypeDto.getContributerType(), contributorTypeService.deleteContributorType(1).getContributerType());
	}
	/**
	 * The deleteBookFail() method tests the functionality of deleting a ContributorType with the id which is not present.
	 * It throws an exception called NoDataExists.
	 */
	@Test
	void deleteContributorTypeFail() {
		when(contributorTypeRepository.findById(anyInt())).thenReturn(Optional.empty());

		assertThrows(NoDataExistsException.class, () -> contributorTypeService.deleteContributorType(100));
	}

}
