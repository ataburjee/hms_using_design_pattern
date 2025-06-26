//package com.hms.config;
//
//import com.hms.model.Patient;
//import com.hms.repository.opd.DoctorRepository;
//import com.hms.repository.opd.PatientRepository;
//import com.hms.util.Utility;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//public class DataInitializer implements CommandLineRunner {
//
//    private final DoctorRepository doctorRepository;
//    private final PatientRepository patientRepository;
//
//    @Override
//    public void run(String... args) throws Exception {
////        List<Doctor> doctors = List.of(
////                new Doctor(getId(), "Dr. A", "", DoctorType.CONSULTATION ,"General Physician"),
////                new Doctor(getId(), "Dr. B", "", DoctorType.SURGERY ,"General Physician"),
////                new Doctor(getId(), "Dr. C", "", DoctorType.PHYSIOTHERAPY ,"General Physician")
////
////        );
//
////        LocalTime[] startTimes = { LocalTime.of(9, 0), LocalTime.of(10, 0), LocalTime.of(11, 0) };
//
////        for (int i = 0; i < doctors.size(); i++) {
////            Doctor doctor = doctors.get(i);
////            doctorRepository.save(doctor);
////            System.out.println("doctor.getId() = " + doctor.getId());
////            for (int j = 0; j < 4; j++) {
////                PatientTimeSlot ts = new PatientTimeSlot();
////                ts.setId(Utility.generateId());
////                ts.setQueueNumber(i+1);
////                ts.setStartTime(startTimes[i].plusMinutes(j * 15));
////                ts.setEndTime(startTimes[i].plusMinutes((j + 1) * 15));
////                timeSlotRepository.save(ts);
////            }
//
//        String id = getId();
//        System.out.println("id = " + id);
//        List<Patient> patients = List.of(
//                new Patient(id, "p1", "male", 23, "123456", "p1@gmail.com", "N/A", LocalDate.now()),
//                new Patient(getId(), "p2", "female", 33, "123456", "p2@gmail.com", "N/A", LocalDate.now())
//        );
//        patientRepository.saveAll(patients);
//    }
//
//    String getId() {
//        return Utility.generateId(Utility.PATIENT);
//    }
//}
