package com.hms.service;

import com.hms.model.Doctor;
import org.springframework.stereotype.Service;

public interface DoctorService {
    Doctor findDoctor(String doctorId);
}
