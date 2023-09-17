package com.albino.tecnologia.osworks.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceOrderDTO {

    @NotBlank(message = "Fill the Description Field Correctly")
    @Size(min = 5, max = 100, message = "'${validatedValue}' needs to be between {min} characters")
    private String description;

    @NotBlank(message = "Fill the Hours Number Field Correctly")
    @Size(min = 1, max = 10, message = "'${validatedValue}' needs to be between {min} characters")
    private String hoursNumber;

    @Positive(message = "Service Order Function Points Need to Be Positive")
    private Integer numberFunctionPoints;

    @Positive(message = "Responsible Id is must be Positive")
    private Long responsibleId;

    @Positive(message = "Company Id is must be Positive")
    private Long companyId;

    @Positive(message = "Contract Id is must be Positive")
    private Long contractId;


}
