package com.hms.service.opd.impl;

import com.hms.dto.AppointmentDTO;
import com.hms.dto.BookAppointmentRequestDTO;
import com.hms.enums.opd.AppointmentStatus;
import com.hms.model.ipd.Appointment;
import com.hms.repository.opd.AppointmentRepository;
import com.hms.enums.opd.SlotStatus;
import com.hms.model.TimeSlot;
import com.hms.repository.opd.TimeSlotRepository;
import com.hms.service.opd.AppointmentService;

import com.hms.util.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final TimeSlotRepository timeSlotRepository;

    @Override
    public AppointmentDTO bookAppointment(BookAppointmentRequestDTO request) {

        // 1. Check if slot exists
        TimeSlot slot = timeSlotRepository.findById(request.getTimeSlotId())
                .orElseThrow(() -> new RuntimeException("Slot not found"));

        // 2. Check if already booked
        if (slot.isBooked()) {
            throw new RuntimeException("Slot already booked");
        }

        // 3. Create appointment
        Appointment appointment = Appointment.builder()
                .id(Utility.generateId(Utility.appointment))
                .patientId(request.getPatientId())
                .doctorId(request.getDoctorId())
                .timeSlotId(slot.getId())
                .date(slot.getDate())
                .startTime(slot.getStartTime())
                .endTime(slot.getEndTime())
                .status(AppointmentStatus.BOOKED)
                .isOnline(request.isOnline())
                .build();

        // 4. Save appointment
        Appointment saved = appointmentRepository.save(appointment);

        // 5. Mark slot as booked
        slot.setBooked(true);
        slot.setStatus(SlotStatus.BOOKED);
        timeSlotRepository.save(slot);

        // 6. Return DTO
        return AppointmentDTO.builder()
                .id(saved.getId())
                .doctorId(saved.getDoctorId())
                .patientId(saved.getPatientId())
                .timeSlotId(saved.getTimeSlotId())
                .date(saved.getDate())
                .startTime(saved.getStartTime())
                .endTime(saved.getEndTime())
                .status(saved.getStatus())
                .isOnline(saved.isOnline())
                .build();
    }
}
