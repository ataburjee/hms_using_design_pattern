package com.hms.service;

import com.hms.dto.AppointmentDTO;
import com.hms.dto.BookAppointmentRequestDTO;

public interface AppointmentService {
    AppointmentDTO bookAppointment(BookAppointmentRequestDTO request);
}
