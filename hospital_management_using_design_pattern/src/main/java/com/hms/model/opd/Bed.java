package com.hms.model.opd;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bedNumber;
    private boolean occupied;

    @ManyToOne
    @JoinColumn(name = "ward_id")
    private Ward ward;
}

