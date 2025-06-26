package com.hms.model;

import com.hms.enums.opd.SlotStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.*;
@Entity
@Table(name = "time_slots")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeSlot {

    @Id
    private String id;

    private String doctorId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    private SlotStatus status;

    private boolean isBooked;
}
