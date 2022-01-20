package com.lime.mediscreen.controller;

import com.lime.mediscreen.model.Patient;
import com.lime.mediscreen.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/patients")
    public List<Patient> getAllPatients() {
        return patientService.findAll();
    }

    @GetMapping("/patients/{id}")
    public ResponseEntity<Object> getPatientById(@PathVariable(value = "id") Long patientId) {
        Patient patient = patientService.findPatientById(patientId);
        if (patient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(patient);
    }

    @PutMapping("/patients/update/{id}")
    public ResponseEntity<Object> updatePatientById(@PathVariable(value = "id") Long patientId, @Valid @RequestBody Patient patientDetails) {
        Patient patient = patientService.updatePatient(patientId, patientDetails);
        if (patient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(patient);
    }

    @PostMapping("/patient/add")
    public ResponseEntity<Object> createPatient(@Valid @RequestBody Patient patient) {
        Patient newPatient = patientService.savePatient(patient);
        return new ResponseEntity<>(newPatient, HttpStatus.CREATED);
    }

    @DeleteMapping("/patients/{id}")
    public ResponseEntity<Object> deletePatient(@PathVariable(value = "id") Long patientId) {
        Boolean deleted = patientService.deletePatientById(patientId);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

}
