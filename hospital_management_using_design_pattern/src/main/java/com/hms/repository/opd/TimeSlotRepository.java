package com.hms.repository.opd;

import com.hms.model.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, String> {
    List<TimeSlot> findByDoctorIdAndDate(String doctorId, LocalDate date);

    List<TimeSlot> findByDate(LocalDate date);
}
