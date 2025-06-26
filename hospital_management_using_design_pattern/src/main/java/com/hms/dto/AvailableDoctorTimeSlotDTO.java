package com.hms.dto;

import com.hms.enums.opd.DoctorType;
import com.hms.enums.opd.SlotStatus;
import com.hms.model.TimeSlot;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
public class AvailableDoctorTimeSlotDTO {
    private String doctorId;
    private String doctorName;
    private String specialization;
    private DoctorType doctorType;
    private String department;
    private List<TimeSlot> timeSlots;
}
