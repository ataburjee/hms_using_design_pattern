//package com.hms.service.impl;
//
//import com.hms.dto.QueueStatusResponse;
//import com.hms.model.AppointmentSlot;
//import com.hms.enums.opd.SlotStatus;
//import com.hms.model.PatientTimeSlot;
//import com.hms.repository.AppointmentSlotRepository;
//import com.hms.service.opd.QueueService;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class QueueServiceImpl implements QueueService {
//
//    private final AppointmentSlotRepository appointmentSlotRepository;
//
//
//    @Override
//    @Transactional
//    public String markQueueStarted(String appointmentSlotId) {
//        AppointmentSlot slot = appointmentSlotRepository.findById(appointmentSlotId)
//                .orElseThrow(() -> new RuntimeException("AppointmentSlot not found"));
//
//        Optional<PatientTimeSlot> firstBooked = timeSlotRepository
//                .findFirstByAppointmentSlotAndSlotStatusOrderByQueueNumberAsc(slot, SlotStatus.BOOKED);
//
//        if (firstBooked.isPresent()) {
//            PatientTimeSlot slotToMark = firstBooked.get();
//            slotToMark.setSlotStatus(SlotStatus.CURRENT);
//            slot.setCurrentQueueNumber(slotToMark.getQueueNumber());
//
//            appointmentSlotRepository.save(slot);
//            timeSlotRepository.save(slotToMark);
//
//            return "Queue started. Patient #" + slotToMark.getQueueNumber() + " is now CURRENT.";
//        }
//
//        return "No booked patients to mark as CURRENT.";
//    }
//
//    @Override
//    @Transactional
//    public String markCurrentAsMissed(String appointmentSlotId) {
//        AppointmentSlot slot = appointmentSlotRepository.findById(appointmentSlotId)
//                .orElseThrow(() -> new RuntimeException("AppointmentSlot not found"));
//
//        int current = slot.getCurrentQueueNumber();
//
//        PatientTimeSlot currentSlot = timeSlotRepository.findByAppointmentSlotAndQueueNumber(slot, current)
//                .orElseThrow(() -> new RuntimeException("Current time slot not found"));
//
//        currentSlot.setSlotStatus(SlotStatus.MISSED);
//        timeSlotRepository.save(currentSlot);
//
//        Optional<PatientTimeSlot> nextSlotOpt = timeSlotRepository
//                .findFirstByAppointmentSlotAndQueueNumberGreaterThanAndSlotStatusOrderByQueueNumberAsc(
//                        slot, current, SlotStatus.BOOKED
//                );
//
//        if (nextSlotOpt.isPresent()) {
//            PatientTimeSlot next = nextSlotOpt.get();
//            next.setSlotStatus(SlotStatus.CURRENT);
//            slot.setCurrentQueueNumber(next.getQueueNumber());
//            appointmentSlotRepository.save(slot);
//            timeSlotRepository.save(next);
//            return "Marked queue #" + current + " as MISSED. Promoted queue #" + next.getQueueNumber() + " to CURRENT.";
//        }
//
//        return "No booked patients left to promote.";
//    }
//
//
//    @Override
//    @Transactional
//    public String markLateArrival(String appointmentSlotId, int missedQueueNumber) {
//        AppointmentSlot slot = appointmentSlotRepository.findById(appointmentSlotId)
//                .orElseThrow(() -> new RuntimeException("Slot not found"));
//
//        int current = slot.getCurrentQueueNumber();
//
//        PatientTimeSlot missedSlot = timeSlotRepository.findByAppointmentSlotAndQueueNumber(slot, missedQueueNumber)
//                .orElseThrow(() -> new RuntimeException("Invalid missed queue number"));
//
//        if (missedSlot.getSlotStatus() != SlotStatus.MISSED) {
//            return "This slot was not marked as MISSED.";
//        }
//
//        missedSlot.setSlotStatus(SlotStatus.BOOKED);
//        timeSlotRepository.save(missedSlot);
//
//        return "Late patient (queue #" + missedQueueNumber + ") re-added. Will be called after current patient (#" + current + ").";
//    }
//
//    @Override
//    public List<QueueStatusResponse> getQueueStatus(String appointmentSlotId) {
//        AppointmentSlot slot = appointmentSlotRepository.findById(appointmentSlotId)
//                .orElseThrow(() -> new RuntimeException("Slot not found"));
//
//        List<PatientTimeSlot> patientTimeSlots = timeSlotRepository.findByAppointmentSlotOrderByQueueNumberAsc(slot);
//
//        return patientTimeSlots.stream().map(ts -> {
//            String name = ts.getPatient() != null ? ts.getPatient().getName() : "N/A";
//            return new QueueStatusResponse(ts.getQueueNumber(), name, ts.getSlotStatus());
//        }).toList();
//    }
//
//    @Override
//    @Transactional
//    public String completeCurrentAndPromoteNext(String appointmentSlotId) {
//        AppointmentSlot slot = appointmentSlotRepository.findById(appointmentSlotId)
//                .orElseThrow(() -> new RuntimeException("AppointmentSlot not found"));
//
//        int currentQueue = slot.getCurrentQueueNumber();
//
//        PatientTimeSlot currentSlot = timeSlotRepository.findByAppointmentSlotAndQueueNumber(slot, currentQueue)
//                .orElseThrow(() -> new RuntimeException("Current slot not found"));
//
//        if (currentSlot.getSlotStatus() != SlotStatus.CURRENT) {
//            return "No patient is currently active.";
//        }
//
//        // Mark current slot patient as completed(done)
//        currentSlot.setSlotStatus(SlotStatus.COMPLETED);
//        timeSlotRepository.save(currentSlot);
//
//        // Promote next booked patient
//        Optional<PatientTimeSlot> next = timeSlotRepository
//                .findFirstByAppointmentSlotAndQueueNumberGreaterThanAndSlotStatusOrderByQueueNumberAsc(
//                        slot, currentQueue, SlotStatus.BOOKED
//                );
//
//        if (next.isPresent()) {
//            PatientTimeSlot nextSlot = next.get();
//            nextSlot.setSlotStatus(SlotStatus.CURRENT);
//            slot.setCurrentQueueNumber(nextSlot.getQueueNumber());
//            timeSlotRepository.save(nextSlot);
//            appointmentSlotRepository.save(slot);
//            return "Patient #" + currentQueue + " marked as COMPLETED. Patient #" + nextSlot.getQueueNumber() + " is now CURRENT.";
//        } else {
//            slot.setCurrentQueueNumber(-1);
//            appointmentSlotRepository.save(slot);
//            return "Patient #" + currentQueue + " marked as COMPLETED. No more booked patients.";
//        }
//    }
//
//}