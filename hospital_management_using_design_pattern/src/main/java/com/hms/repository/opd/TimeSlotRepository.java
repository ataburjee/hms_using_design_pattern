package com.hms.repository.opd;

import com.hms.model.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, String> {
    List<TimeSlot> findByDoctorIdAndDate(String doctorId, LocalDate date);
    List<TimeSlot> findByDateOrderByStartTime(LocalDate date);

    @Query("SELECT DISTINCT ts.date FROM TimeSlot ts WHERE ts.doctorId = :doctorId AND ts.date BETWEEN :from AND :to")
    List<LocalDate> findDistinctDatesByDoctorIdBetween(@Param("doctorId") String doctorId,
                                                       @Param("from") LocalDate from,
                                                       @Param("to") LocalDate to);

}
