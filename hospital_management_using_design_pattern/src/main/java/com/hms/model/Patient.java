package com.hms.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "patients")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {

    @Id
    private String id;
    private String fullName;
    private String gender;
    private Integer age;
    private String contactNumber;
    private String email;
    private String address;
    private LocalDate registrationDate;

}