package com.lime.mediscreen.service;

import com.lime.mediscreen.model.Patient;
import com.lime.mediscreen.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService{


    @Autowired
    private PatientRepository patientRepository;

    @Override
    public String greet() {
        return "Hello, World";
    }

    @Override
    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    @Override
    public Patient findPatientById(Long id) {
        Optional<Patient> optionalPatient = patientRepository.findById(id);
        return optionalPatient.orElse(null);
    }

    @Override
    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public Patient findPatientByFirstName(String firstName) {
        return patientRepository.findPatientByFirstName(firstName);
    }

    @Override
    public List<Patient> findPatientByLastName(String lastName) {
        return patientRepository.findPatientByLastName(lastName);
    }

    @Override
    public Patient updatePatient(Long id, Patient patientDetails) {
        Optional<Patient> optionalPatient = patientRepository.findById(id);
        if (!optionalPatient.isPresent()) {
            return null;
        }
        Patient patient = optionalPatient.get();
        patient.setFirstName(patientDetails.getFirstName());
        patient.setLastName(patientDetails.getLastName());
        patient.setBirthDate(patientDetails.getBirthDate());
        patient.setSex(patientDetails.getSex());
        patient.setAddress(patientDetails.getAddress());
        patient.setPhone(patientDetails.getPhone());
        final Patient updatedPatient = patientRepository.save(patient);
        return updatedPatient;
    }

    @Override
    public Boolean deletePatientById(Long id) {
        Boolean deleted = false;
        Optional<Patient> optionalPatient = patientRepository.findById(id);
        if (optionalPatient.isPresent()) {
            patientRepository.deleteById(id);
            deleted = true;
        }
        return deleted;
    }

}
