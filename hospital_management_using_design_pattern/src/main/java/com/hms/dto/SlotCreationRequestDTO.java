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
public class SlotCreationRequestDTO {

    @NotNull
    private String doctorId;

    @NotNull
    private SlotType slotType;

    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;

    @NotNull
    private LocalTime startTime;
    @NotNull
    private LocalTime endTime;

    @NotNull
    private Long slotDuration;

    private List<DayOfWeek> daysOfWeek;
    private List<LocalDate> customDates;
}