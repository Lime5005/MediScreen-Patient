package com.lime.mediscreenpatient.repository;

import com.lime.mediscreenpatient.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient findPatientByFirstName(String firstName);
    List<Patient> findPatientByLastName(String lastName);
}
