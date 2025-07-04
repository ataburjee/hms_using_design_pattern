package com.hms.dto;

import com.hms.enums.opd.SlotType;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecurringSlotRequestDTO {

    private String doctorId;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer slotDurationInMinutes;
//    private List<DayOfWeek> repeatOn;
//    private SlotType slotType;
//    private String tag;
//    private String note;
//    private String colorCode;

}

