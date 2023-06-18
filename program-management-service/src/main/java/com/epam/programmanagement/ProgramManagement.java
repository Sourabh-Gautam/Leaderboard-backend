package com.epam.programmanagement;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Program Management API", version = "2.0",description = "Program Management API"))
public class ProgramManagement {

	public static void main(String[] args) {
		SpringApplication.run(ProgramManagement.class, args);

	} 
} 

