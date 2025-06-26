package com.hms.repository.opd;

import com.hms.model.ipd.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, String> {
    List<Appointment> findByDoctorIdAndDate(String doctorId, java.time.LocalDate date);
}
