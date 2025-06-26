package com.hms.model;

import com.hms.enums.opd.DoctorType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "doctors")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Doctor {

    @Id
    private String id;

    private String name;

    private String specialization;

    @Enumerated(EnumType.STRING)
    private DoctorType doctorType;

    private String department;
}
