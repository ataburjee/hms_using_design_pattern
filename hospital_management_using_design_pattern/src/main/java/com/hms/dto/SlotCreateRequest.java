package com.hms.dto;

import com.hms.enums.opd.SlotType;
import com.hms.enums.opd.DoctorType;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
public class SlotCreateRequest {
    private String doctorId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private int durationMinutes;
    private SlotType slotType;
    private DoctorType doctorType;
    private String tag;
    private String note;
    private String colorCode;
    private String recurrencePattern;
}