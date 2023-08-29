package com.web.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDTO {
    private Long id;
    @NotEmpty(message ="User name should not be empty!")
    private String userName;
    @NotEmpty(message ="Email should not be empty!")
    @Email(message = "Please input a valid email!")
    private String email;
    @NotEmpty(message ="Password should not be empty!")
    private String password;

}
