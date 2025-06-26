package com.hms.strategy.opd;

import com.hms.dto.SlotCreationRequestDTO;
import com.hms.model.Doctor;
import com.hms.model.TimeSlot;

import java.util.List;

public interface SlotCreationStrategy {
    List<TimeSlot> generateSlots(SlotCreationRequestDTO request, Doctor doctor);
}

