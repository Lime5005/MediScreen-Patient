package com.lime.mediscreen.service;

import com.lime.mediscreen.model.Patient;

import java.util.List;

public interface PatientService {
    String greet();
    List<Patient> findAll();
    Patient findPatientByFirstName(String firstName);
    List<Patient> findPatientByLastName(String lastName);
    Patient findPatientById(Long id);
    Patient updatePatient(Long id, Patient patientDetails);
    Boolean deletePatientById(Long id);
    Patient savePatient(Patient patient);
}
