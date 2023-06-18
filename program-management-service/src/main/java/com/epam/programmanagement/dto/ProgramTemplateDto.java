package com.epam.programmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProgramTemplateDto {
	private int programTemplateId;
	@NotBlank(message = "category is required")
	private String category;
	private String description;
	@NotNull(message = "weightage is required")
	private float weightage;

}
