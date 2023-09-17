package com.albino.tecnologia.osworks.controllers.dto;

import com.albino.tecnologia.osworks.infra.jackson.deserializer.LocalDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {

    @NotBlank(message = "Fill the Project Name Field Correctly")
    @Size(min = 5, max = 25, message = "'${validatedValue}' needs to be between {min} characters")
    private String name;

    @NotBlank(message = "Fill the Description Name Field Correctly")
    @Size(min = 5, max = 100, message = "'${validatedValue}' needs to be between {min} characters")
    private String description;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate beginDate;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate endDate;

    @Positive(message = "Company Id is must be Positive")
    private Long serviceOrderId;

}
