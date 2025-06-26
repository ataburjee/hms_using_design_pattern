package com.hms.repository.ipd;

import com.hms.model.opd.Bed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BedRepository extends JpaRepository<Bed, Long> {
    Optional<Bed> findFirstByWardIdAndOccupiedFalse(Long wardId);
}