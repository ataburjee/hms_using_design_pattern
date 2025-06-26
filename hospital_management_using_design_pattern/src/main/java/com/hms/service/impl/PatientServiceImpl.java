package com.hms.service.impl;

import com.hms.dto.PatientDTO;
import com.hms.model.Patient;
import com.hms.repository.opd.PatientRepository;
import com.hms.service.PatientService;

import com.hms.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    private PatientDTO mapToDTO(Patient patient) {
        return PatientDTO.builder()
                .id(patient.getId())
                .fullName(patient.getFullName())
                .gender(patient.getGender())
                .age(patient.getAge())
                .contactNumber(patient.getContactNumber())
                .email(patient.getEmail())
                .address(patient.getAddress())
                .build();
    }

    private Patient mapToEntity(PatientDTO dto) {
        return Patient.builder()
                .id(Utility.generateId(Utility.PATIENT))
                .fullName(dto.getFullName())
                .gender(dto.getGender())
                .age(dto.getAge())
                .contactNumber(dto.getContactNumber())
                .email(dto.getEmail())
                .address(dto.getAddress())
                .registrationDate(LocalDate.now())
                .build();
    }

    @Override
    public PatientDTO createPatient(PatientDTO dto) {
        Patient patient = mapToEntity(dto);
        return mapToDTO(patientRepository.save(patient));
    }

    @Override
    public PatientDTO getPatientById(String id) {
        return patientRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
    }

    @Override
    public List<PatientDTO> getAllPatients() {
        return patientRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PatientDTO updatePatient(String id, PatientDTO dto) {
        Patient existing = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        existing.setFullName(dto.getFullName());
        existing.setGender(dto.getGender());
        existing.setAge(dto.getAge());
        existing.setContactNumber(dto.getContactNumber());
        existing.setEmail(dto.getEmail());
        existing.setAddress(dto.getAddress());

        return mapToDTO(patientRepository.save(existing));
    }

    @Override
    public void deletePatient(String id) {
        patientRepository.deleteById(id);
    }

    @Override
    public List<Patient> searchPatients(String q) {
        List<Patient> searchedPatients = patientRepository.findByFullNameContainingIgnoreCaseOrContactNumberContaining(q, q);
        System.out.println("searchedPatients = " + searchedPatients);
        return searchedPatients;
    }
}
