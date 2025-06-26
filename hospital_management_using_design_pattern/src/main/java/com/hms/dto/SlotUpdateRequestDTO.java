package com.hms.dto;

import com.hms.enums.opd.SlotType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SlotUpdateRequestDTO {

    @NotNull
    private String doctorId;

    @NotNull
    private SlotType slotType;

    @NotNull
    private LocalTime startTime;
    @NotNull
    private LocalTime endTime;

    @NotNull
    private Duration slotDuration;
}