package com.hms.model.ipd;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "medicines")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Medicine {

    @Id
    private String id;

    private String name;
    private String brand;
    private String dosageForm; // e.g. Tablet, Syrup
    private String strength; // e.g. 500mg

    private int stock;
    private double pricePerUnit;
}
