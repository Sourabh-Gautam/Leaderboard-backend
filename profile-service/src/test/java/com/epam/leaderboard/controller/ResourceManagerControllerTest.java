package com.epam.leaderboard.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.epam.leaderboard.ProfileServiceApplication;
import com.epam.leaderboard.dto.ResourceManagerDTO;
import com.epam.leaderboard.exception.EntityNotFoundException;
import com.epam.leaderboard.service.ResourceManagerService;

@WebMvcTest(controllers = { ResourceManagerController.class})
@ContextConfiguration(classes = { ProfileServiceApplication.class })
@ExtendWith({SpringExtension.class,MockitoExtension.class})
class ResourceManagerControllerTest {

    @MockBean
    private ResourceManagerService resourceManagerService;

    @InjectMocks
    private ResourceManagerController resourceManagerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetResourceManagers() throws EntityNotFoundException {
        ResourceManagerDTO resourceManagerDTO1 = new ResourceManagerDTO(1, "John Doe");
        ResourceManagerDTO resourceManagerDTO2 = new ResourceManagerDTO(2,"Jane Smith");
        List<ResourceManagerDTO> expectedResourceManagers = Arrays.asList(resourceManagerDTO1, resourceManagerDTO2);

        when(resourceManagerService.getAllResourceManagers()).thenReturn(expectedResourceManagers);

        ResponseEntity<List<ResourceManagerDTO>> actualResponse = resourceManagerController.getResourceManagers();

        assertEquals(expectedResourceManagers, actualResponse.getBody());
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
    }

}
