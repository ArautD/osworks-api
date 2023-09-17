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
public class UserByUsernameDTO {

    @NotBlank(message = "Fill the Username Field Correctly")
    @Size(min = 3, max = 15, message = "'${validatedValue}' needs to be between {min} characters")
    private String username;

}
