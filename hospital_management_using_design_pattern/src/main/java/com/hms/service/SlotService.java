package com.hms.service;

import com.hms.dto.AvailableDoctorTimeSlotDTO;
import com.hms.dto.SlotCreationRequestDTO;
import com.hms.model.TimeSlot;

import java.time.LocalDate;
import java.util.List;

public interface SlotService {
    List<TimeSlot> createSlots(SlotCreationRequestDTO request);
    List<TimeSlot> getSlotsForDoctor(String doctorId, LocalDate date);
    List<AvailableDoctorTimeSlotDTO> listDoctorHavingSlotsByDate(LocalDate date);
}