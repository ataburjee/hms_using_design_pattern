package com.hms.strategy.opd;

import com.hms.dto.SlotCreationRequestDTO;
import com.hms.enums.opd.SlotStatus;
import com.hms.model.Doctor;
import com.hms.model.TimeSlot;
import com.hms.util.Utility;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component("SURGERY")
public class SurgerySlotStrategy implements SlotCreationStrategy {

    @Override
    public List<TimeSlot> generateSlots(SlotCreationRequestDTO request, Doctor doctor) {
        List<TimeSlot> slots = new ArrayList<>();

        Duration duration = Duration.ofMinutes(request.getSlotDuration());
        LocalTime startTime = request.getStartTime();
        LocalTime endTime = request.getEndTime();

        switch (request.getSlotType()) {
            case DAILY -> {
                for (LocalDate date = request.getStartDate(); !date.isAfter(request.getEndDate()); date = date.plusDays(1)) {
                    slots.addAll(generateSurgerySlots(doctor.getId(), date, startTime, endTime, duration));
                }
            }
            case WEEKLY -> {
                for (LocalDate date = request.getStartDate(); !date.isAfter(request.getEndDate()); date = date.plusDays(1)) {
                    if (request.getDaysOfWeek().contains(date.getDayOfWeek())) {
                        slots.addAll(generateSurgerySlots(doctor.getId(), date, startTime, endTime, duration));
                    }
                }
            }
            case CUSTOM_DAYS -> {
                for (LocalDate date : request.getCustomDates()) {
                    slots.addAll(generateSurgerySlots(doctor.getId(), date, startTime, endTime, duration));
                }
            }
        }

        return slots;
    }

    private List<TimeSlot> generateSurgerySlots(String doctorId, LocalDate date,
                                                LocalTime start, LocalTime end, Duration duration) {
        List<TimeSlot> slots = new ArrayList<>();
        LocalTime current = start;

        while (!current.plus(duration).isAfter(end)) {
            slots.add(TimeSlot.builder()
                    .id(Utility.generateId(Utility.TIME_SLOT))
                    .doctorId(doctorId)
                    .date(date)
                    .startTime(current)
                    .endTime(current.plus(duration))
                    .status(SlotStatus.AVAILABLE)
                    .isBooked(false)
                    .build());
            current = current.plus(duration);
        }
        return slots;
    }
}
