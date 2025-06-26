package com.hms.model.ipd;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "prescription_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrescriptionItem {

    @Id
    private String id;

    private String medicineName;
    private String dosage;
    private String frequency;
    private int durationInDays;

    @ManyToOne
    @JoinColumn(name = "prescription_id")
    private Prescription prescription;
}
