package com.hms.service.impl;

import com.hms.dto.AvailableDoctorTimeSlotDTO;
import com.hms.dto.SlotCreationRequestDTO;
import com.hms.model.Doctor;
import com.hms.model.TimeSlot;
import com.hms.repository.opd.DoctorRepository;
import com.hms.repository.opd.TimeSlotRepository;
import com.hms.service.SlotService;
import com.hms.service.factory.SlotStrategyFactory;
import com.hms.strategy.opd.SlotCreationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SlotServiceImpl implements SlotService {

    private final TimeSlotRepository slotRepository;
    private final DoctorRepository doctorRepository;
    private final SlotStrategyFactory slotStrategyFactory;

    @Override
    public List<LocalDate> getUnavailableDates(String doctorId) {
        LocalDate from = LocalDate.now();
        LocalDate to = from.plusMonths(3);
        return slotRepository.findDistinctDatesByDoctorIdBetween(doctorId, from, to);
    }

    @Override
    public List<TimeSlot> createSlots(SlotCreationRequestDTO request) {
        Doctor doctor = doctorRepository
                .findById(request.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor doesn't exists...!"));

        SlotCreationStrategy strategy = slotStrategyFactory.getStrategy(doctor.getDoctorType());
        List<TimeSlot> slots = strategy.generateSlots(request, doctor);
        return slotRepository.saveAll(slots);
    }

    @Override
    public List<TimeSlot> getSlotsForDoctor(String doctorId, LocalDate date) {
        return slotRepository.findByDoctorIdAndDate(doctorId, date);
    }

    @Override
    public List<AvailableDoctorTimeSlotDTO> listDoctorHavingSlotsByDate(LocalDate date) {
        List<TimeSlot> timeSlotsByDate = slotRepository.findByDateOrderByStartTime(date);
        if (timeSlotsByDate.isEmpty()) {
            return Collections.emptyList();
        }
        List<AvailableDoctorTimeSlotDTO> list = new ArrayList<>();

        Map<String, List<TimeSlot>> doctorTimeSlotList = timeSlotsByDate.stream().collect(Collectors.groupingBy(TimeSlot::getDoctorId));
        doctorTimeSlotList.forEach(
                (doctorId, timeSlotList) -> {
                    Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();
                    list.add(AvailableDoctorTimeSlotDTO.builder()
                            .doctorId(doctorId)
                            .doctorName(doctor.getName())
                            .doctorType(doctor.getDoctorType())
                            .department(doctor.getDepartment())
                            .timeSlots(timeSlotList)
                            .build());
                });
        return list;
    }

}
