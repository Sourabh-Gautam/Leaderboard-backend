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

import com.epam.leaderboard.dto.ResourceManagerDTO;
import com.epam.leaderboard.entity.ResourceManager;
import com.epam.leaderboard.exception.EntityNotFoundException;
import com.epam.leaderboard.repo.ResourceManagerRepo;

/**
 * The ResourceManagerServiceTest class tests the methods which are present in
 * ResourceManagerService class. The class is annotated by 1 annotation:
 * 
 * @ExtendWith annotation used to register extensions for the annotated test
 *             class.
 * @author Bhargavi_Velugonda
 *
 */
@ExtendWith(MockitoExtension.class)
class ResourceManagerServiceTest {

	@Mock
	private ResourceManagerRepo resourceManagerRepository;
	
	
	private ResourceManagerService resourceManagerService;

	private ModelMapper mapper = new ModelMapper();
	private List<ResourceManager> resourceManagers = new ArrayList<>();
	private List<ResourceManagerDTO> resourceManagersDto = new ArrayList<>();
	private ResourceManager resourceManager = new ResourceManager();
	private ResourceManagerDTO resourceManagerdto = new ResourceManagerDTO();

	/**
	 * The setup() method is basically executed before executing every single test
	 * case.
	 */
	@BeforeEach
	public void setup() {

		resourceManagerService = new ResourceManagerService(resourceManagerRepository);
		resourceManager.setResourceManagerName("Shakti Vyas");
		resourceManager.setId(1);
		resourceManagers.add(resourceManager);
		resourceManagerdto.setResourceManagerName("Shakti Vyas");
		resourceManagerdto.setId(1);
		resourceManagersDto.add(resourceManagerdto);

	}

	/**
	 * The getResourceManagersTest() method tests the functionality of retrieving
	 * all ResourceManagers from the database.
	 * @throws Exception 
	 */
	@Test
	void getResourceManagersTest() throws Exception {

		when(resourceManagerRepository.findAll()).thenReturn(resourceManagers);
		resourceManagersDto = mapper.map(resourceManagers,
				new TypeToken<List<ResourceManagerDTO>>() {
				}.getType());
		assertEquals(resourceManagersDto.size(), resourceManagerService.getAllResourceManagers().size());

	}

	/**
	 * The getResourceManagersTestFail() method tests the functionality of
	 * retrieving all the resourceManagers when no resourceManagers are present in
	 * the database.
	 */
	@Test
	void getResourceManagersTestFail() {
		List<ResourceManager> emptyResourceManagerList = new ArrayList<>();
		when(resourceManagerRepository.findAll()).thenReturn(emptyResourceManagerList);
		assertThrows(EntityNotFoundException.class, () -> resourceManagerService.getAllResourceManagers());

	}
}
