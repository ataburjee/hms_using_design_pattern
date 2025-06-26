package com.hms.service.impl;

import com.hms.model.Doctor;
import com.hms.repository.opd.DoctorRepository;
import com.hms.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    @Override
    public Doctor findDoctor(String doctorId) {
        return doctorRepository.findById(doctorId).orElseThrow(
                () -> new RuntimeException("Doctor not found")
        );
    }
}
