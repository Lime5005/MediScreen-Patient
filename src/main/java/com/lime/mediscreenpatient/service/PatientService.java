package com.lime.mediscreenpatient.service;

import com.lime.mediscreenpatient.model.Patient;

import java.util.List;

public interface PatientService {
    List<Patient> findAll();
    List<Patient> findPatientByLastName(String lastName);
    Patient findPatientById(Long id);
    Patient updatePatient(Long id, Patient patientDetails);
    Boolean deletePatientById(Long id);
    Patient savePatient(Patient patient);
}
