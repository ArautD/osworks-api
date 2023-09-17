package com.albino.tecnologia.osworks.controllers.dto;

import com.albino.tecnologia.osworks.enums.CompanyType;
import com.albino.tecnologia.osworks.infra.jackson.deserializer.LocalDateDeserializer;
import com.albino.tecnologia.osworks.models.Address;
import com.albino.tecnologia.osworks.models.Responsible;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CNPJ;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO {

    @CNPJ
    @NotBlank(message = "Fill in the CNPJ Field Correctly")
    @Size(min = 2, max = 50, message = "'${validatedValue}' must be between {min} characters")
    private String cnpj;

    @NotBlank(message = "Fill in the Company Name Field Correctly")
    @Size(min = 5, max = 50, message = "'${validatedValue}' must be between {min} characters")
    private String companyName;

    private CompanyType companyType;

    @NotBlank(message = "Fill the State Registration Field Correctly")
    @Size(min = 5, max = 50, message = "'${validatedValue}' must be between {min} characters")
    private String stateRegistration;

    @NotBlank(message = "Fill the Phone Number Field Correctly")
    @Size(min = 5, max = 50, message = "'${validatedValue}' must be between {min} characters")
    private String phoneNumber;

    @Size(min = 5, max = 50, message = "'${validatedValue}' must be between {min} characters")
    @Email(message = "Fill the E-mail Field Correctly")
    private String email;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate bornDate;

    @NotNull(message = "Some responsible field is null")
    private Responsible responsible;

    @NotNull(message = "Some Address field is null")
    private Address address;
}
