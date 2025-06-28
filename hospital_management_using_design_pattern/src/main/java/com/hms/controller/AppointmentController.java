package com.hms.controller;

import com.hms.dto.AppointmentDTO;
import com.hms.dto.BookAppointmentRequestDTO;
import com.hms.service.AppointmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/book")
    public ResponseEntity<AppointmentDTO> bookAppointment(@RequestBody BookAppointmentRequestDTO request) {
        return ResponseEntity.ok(appointmentService.bookAppointment(request));
    }
}
