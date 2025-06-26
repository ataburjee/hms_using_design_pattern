package com.hms.repository.opd;

import com.hms.model.ipd.OpdBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpdBillRepository extends JpaRepository<OpdBill, String> {
    boolean existsByAppointmentId(String appointmentId);
}