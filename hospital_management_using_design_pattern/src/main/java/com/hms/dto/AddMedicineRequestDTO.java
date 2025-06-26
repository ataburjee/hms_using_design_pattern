package com.hms.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddMedicineRequestDTO {
    private String name;
    private String brand;
    private String dosageForm;
    private String strength;
    private int stock;
    private double pricePerUnit;
}
