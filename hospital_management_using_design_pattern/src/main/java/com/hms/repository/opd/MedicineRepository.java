package com.hms.repository.opd;

import com.hms.model.ipd.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicineRepository extends JpaRepository<Medicine, String> {
    Optional<Medicine> findByNameIgnoreCase(String name);
}
