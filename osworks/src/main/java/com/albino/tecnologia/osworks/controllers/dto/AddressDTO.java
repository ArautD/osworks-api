package com.albino.tecnologia.osworks.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    @NotBlank(message = "Fill in the Zip Code Field Correctly")
    @Size(min = 2, max = 10, message = "'${validatedValue}' needs to be between {min} characters")
    private String postalCode;

    @NotBlank(message = "Fill in the Backyard Field Correctly")
    @Size(min = 2, max = 50, message = "'${validatedValue}' needs to be between {min} characters")
    private String backyard;

    @NotBlank(message = "Fill in the Number Field Correctly")
    @Size(min = 2, max = 10, message = "'${validatedValue}' needs to be between {min} characters")
    private String number;

    @Size(min = 2, max = 25, message = "'${validatedValue}' needs to be between {min} characters")
    private String complement;

    @NotBlank(message = "Fill in the Neighborhood Field Correctly")
    @Size(min = 2, max = 25, message = "'${validatedValue}' needs to be between {min} characters")
    private String neighborhood;

    @NotBlank(message = "Fill in the City Field Correctly")
    @Size(min = 2, max = 25, message = "'${validatedValue}' needs to be between {min} characters")
    private String city;

    @NotBlank(message = "Fill in the UF Field Correctly")
    @Size(min = 1, max = 2, message = "'${validatedValue}' needs to be between {min} characters")
    private String uf;

}
