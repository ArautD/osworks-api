package com.albino.tecnologia.osworks.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotBlank(message = "Fill the Name Field Correctly")
    @Size(min = 3, max = 50, message = "'${validatedValue}' needs to be between {min} characters")
    private String name;

    @NotBlank(message = "Fill the Name Field Correctly")
    @Size(min = 3, max = 25, message = "'${validatedValue}' needs to be between {min} characters")
    private String username;

    @NotBlank(message = "Fill the Password Field Correctly")
    @Size(min = 1, max = 8, message = "'${validatedValue}' needs to be between {min} characters")
    private String password;

    @NotBlank(message = "Fill the E-mail Field Correctly")
    @Size(min = 5, max = 25, message = "'${validatedValue}' needs to be between {min} characters")
    private String email;

    @NotNull(message = "Fill the Permissions Field Correctly")
    private List<Long> roleIds;

}
