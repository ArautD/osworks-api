package com.albino.tecnologia.osworks.controllers.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.br.CPF;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponsibleDTO {

    @NotBlank(message = "Fill in the CPF Field Correctly")
    @CPF(message = "${validatedValue}' is invalid")
    private String cpf;

    @NotBlank(message = "Fill the Name Field Correctly")
    @Size(min = 2, max = 25, message = "'${validatedValue}' needs to be between {min} characters")
    private String name;

    @NotBlank(message = "Fill the RG Field Correctly")
    @Size(min = 2, max = 15, message = "'${validatedValue}' needs to be between {min} characters")
    private String rg;

    @NotBlank(message = "Fill the RG Field Correctly")
    @Size(min = 2, max = 13, message = "'${validatedValue}' needs to be between {min} characters")
    private String phoneNumber;

    @NotBlank(message = "Fill the E-mail Field Correctly")
    @Size(min = 2, max = 20, message = "'${validatedValue}' needs to be between {min} characters")
    private String email;

    @NotBlank(message = "Fill the Department Field Correctly")
    @Size(min = 2, max = 15, message = "'${validatedValue}' needs to be between {min} characters")
    private String department;

    @NotBlank(message = "Fill the Post Field Correctly")
    @Size(min = 2, max = 15, message = "'${validatedValue}' needs to be between {min} characters")
    private String post;

}
