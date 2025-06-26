package com.hms.dto;

import lombok.*;

import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientDTO {

    private String id;

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Gender is required")
    private String gender;

    @Min(0)
    private Integer age;

    @NotBlank(message = "Contact number is required")
    private String contactNumber;

    @Email(message = "Email should be valid")
    private String email;

    private String address;
}
