package com.lime.mediscreen.controller;

import com.lime.mediscreen.exception.ErrorMessage;
import com.lime.mediscreen.exception.ResourceNotFoundException;
import com.lime.mediscreen.model.Patient;
import com.lime.mediscreen.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")//it's running on 8080, but for configuring allowed origins: ui running on 8081.
@RestController
@RequestMapping("/api")
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/patients")
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = patientService.findAll();
        if (patients.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @GetMapping("/patients/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable(value = "id") Long patientId) {
        Patient patient = patientService.findPatientById(patientId);
        if (patient == null ) {
            throw new ResourceNotFoundException("Patient Not Found With id: " + patientId);
        }
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @PutMapping("/patients/update/{id}")
    public ResponseEntity<Object> updatePatientById(@PathVariable(value = "id") Long patientId, @Valid @RequestBody Patient patientDetails, BindingResult result) {
        Patient patient = patientService.updatePatient(patientId, patientDetails);
        if (result.hasFieldErrors()) {
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @PostMapping("/patient/add")
    public ResponseEntity<Object> createPatient(@Valid @RequestBody Patient patient, BindingResult result) {
        if (result.hasFieldErrors()) {
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        Patient newPatient = patientService.savePatient(patient);
        return new ResponseEntity<>(newPatient, HttpStatus.CREATED);
    }

    @DeleteMapping("/patients/{id}")
    public ResponseEntity<Object> deletePatient(@PathVariable(value = "id") Long patientId) {
        Boolean deleted = patientService.deletePatientById(patientId);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient not found with id: " + patientId);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/patients/family")
    public ResponseEntity<List<Patient>> getPatientsByLastName(@RequestParam(value = "lastName") String lastName) {
        List<Patient> patients = patientService.findPatientByLastName(lastName);
        if (patients.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

}
