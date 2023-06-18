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

import com.epam.leaderboard.dto.BusinessUnitDTO;
import com.epam.leaderboard.entity.BusinessUnit;
import com.epam.leaderboard.exception.EntityNotFoundException;
import com.epam.leaderboard.repo.BusinessUnitRepo;

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
class BusinessUnitServiceTest {
	
	@Mock
	private BusinessUnitRepo businessUnitRepository;
	
	private BusinessUnitService businessUnitService;

	private ModelMapper mapper = new ModelMapper();
	private List<BusinessUnit> businessUnits = new ArrayList<>();
	private List<BusinessUnitDTO> businessUnitsDto = new ArrayList<>();
	private BusinessUnit businessUnit = new BusinessUnit();
	private BusinessUnitDTO businessUnitdto = new BusinessUnitDTO();

	/**
	 * The setup() method is basically executed before executing every single test
	 * case.
	 */
	@BeforeEach
	public void setup() {

		businessUnitService = new BusinessUnitService(businessUnitRepository);
		businessUnit.setBusinessUnitName("HCLS Delivery");
		businessUnit.setId(1);
		businessUnits.add(businessUnit);
		businessUnitdto.setBusinessUnitName("Shakti Vyas");
		businessUnitdto.setId(1);
		businessUnitsDto.add(businessUnitdto);

	}

	/**
	 * The getBusinessUnitsTest() method tests the functionality of retrieving
	 * all BusinessUnits from the database.
	 * @throws Exception 
	 */
	@Test
	void getBusinessUnitsTest() throws Exception {

		when(businessUnitRepository.findAll()).thenReturn(businessUnits);
		businessUnitsDto = mapper.map(businessUnits,
				new TypeToken<List<BusinessUnitDTO>>() {
				}.getType());
		assertEquals(businessUnitsDto.size(), businessUnitService.getAllBusinessUnits().size());

	}

	/**
	 * The getBusinessUnitsTestFail() method tests the functionality of
	 * retrieving all the businessUnits when no businessUnits are present in
	 * the database.
	 */
	@Test
	void getBusinessUnitsTestFail() {
		List<BusinessUnit> emptyBusinessUnitList = new ArrayList<>();
		when(businessUnitRepository.findAll()).thenReturn(emptyBusinessUnitList);
		assertThrows(EntityNotFoundException.class, () -> businessUnitService.getAllBusinessUnits());

	}

}
