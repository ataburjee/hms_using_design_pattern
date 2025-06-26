package com.hms.controller;

import com.hms.dto.AvailableDoctorTimeSlotDTO;
import com.hms.dto.SlotCreationRequestDTO;
import com.hms.dto.SlotUpdateRequestDTO;
import com.hms.model.TimeSlot;
import com.hms.service.SlotService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/slots")
public class SlotController {

    @Autowired
    private SlotService slotService;

    @PostMapping()
    public ResponseEntity<List<TimeSlot>> generateSlots(@RequestBody SlotCreationRequestDTO request) {
        return ResponseEntity.ok(slotService.createSlots(request));
    }

//    @PutMapping()
//    public ResponseEntity<List<TimeSlot>> updateSlot(@RequestBody SlotUpdateRequestDTO request) {
//        return ResponseEntity.ok(slotService.updateSlot(request));
//    }

//    @GetMapping()
//    public ResponseEntity<List<TimeSlot>> getSlots(@RequestBody SlotCreationRequestDTO request) {
//        return ResponseEntity.ok(slotService.listSlots(request));
//    }

    @PostMapping("/book")
    public ResponseEntity<List<TimeSlot>> bookSlot(@RequestBody SlotCreationRequestDTO request) {
        return ResponseEntity.ok(slotService.createSlots(request));
    }

    @GetMapping
    public ResponseEntity<List<AvailableDoctorTimeSlotDTO>> listDoctorHavingSlotsByDate(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(slotService.listDoctorHavingSlotsByDate(date));
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<TimeSlot>> getSlotsForDoctor(
            @PathVariable String doctorId,
            @RequestParam LocalDate date
    ) {
        return ResponseEntity.ok(slotService.getSlotsForDoctor(doctorId, date));
    }
}
