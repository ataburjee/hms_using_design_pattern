package com.hms.model.opd;

import com.hms.enums.ipd.WardType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private WardType type;

    private int totalBeds;

    @OneToMany(mappedBy = "ward", cascade = CascadeType.ALL)
    private List<Bed> beds;
}
