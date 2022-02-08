package com.lime.mediscreenpatient.controller;

import com.lime.mediscreenpatient.model.Patient;
import com.lime.mediscreenpatient.service.PatientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")//it's running on 8081, but for configuring allowed origins: ui running on 8080.
@RestController
@Api(tags = "Patient's data API")
@RequestMapping("/api")
public class PatientController {
    Logger logger = LoggerFactory.getLogger(PatientController.class);

    private final PatientService patientService;


    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/patients")
    @ApiOperation("Get all Patients")
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = patientService.findAll();
        if (patients.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @GetMapping("/patients/{id}")
    @ApiOperation("Find one Patient by patient's ID")
    public ResponseEntity<Object> getPatientById(@PathVariable(value = "id") Long patientId) {
        Patient patient = patientService.findPatientById(patientId);
        if (patient == null ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No patient with id: " + patientId);
        }
        logger.info("Patient is queried with id: " + patientId);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @PutMapping("/patients/update/{id}")
    @ApiOperation("Update Patient's info by patient's ID")
    public ResponseEntity<Object> updatePatientById(@PathVariable(value = "id") Long patientId, @Valid @RequestBody Patient patientDetails, BindingResult result) {
        ResponseEntity<Object> messages = getBindingResultErrors(result);
        if (messages != null) return messages;
        Patient patient = patientService.updatePatient(patientId, patientDetails);
        logger.info("Patient info updated with id " + patientId);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @PostMapping("/patient/add")
    @ApiOperation("Create a new Patient")
    public ResponseEntity<Object> createPatient(@Valid @RequestBody Patient patient, BindingResult result) {
        ResponseEntity<Object> messages = getBindingResultErrors(result);
        if (messages != null) return messages;
        Patient newPatient = patientService.savePatient(patient);
        logger.info("New Patient created with id: " + newPatient.getId());
        return new ResponseEntity<>(newPatient, HttpStatus.CREATED);
    }

    @DeleteMapping("/patients/{id}")
    @ApiOperation("Delete a Patient by patient's ID")
    public ResponseEntity<Object> deletePatient(@PathVariable(value = "id") Long patientId) {
        Boolean deleted = patientService.deletePatientById(patientId);
        if (!deleted) {
            logger.error("Failed to delete Patient with id: " + patientId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient not found with id: " + patientId);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/patients/family")
    @ApiOperation("Find one Patient by patient's Lastname")
    public ResponseEntity<List<Patient>> getPatientsByLastName(@RequestParam(value = "lastName") String lastName) {
        List<Patient> patients = patientService.findPatientByLastName(lastName);
        if (patients.isEmpty()) {
            logger.error("Failed to find Patient(s) with Lastname: " + lastName);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        logger.info("Query to find Patient(s) with Lastname: " + lastName);
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    private ResponseEntity<Object> getBindingResultErrors(BindingResult result) {
        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            List<String> messages = new ArrayList<>();
            for (FieldError error : fieldErrors) {
                messages.add(error.getDefaultMessage());
            }
            logger.error("Error message: " + messages);
            return new ResponseEntity<>(messages, HttpStatus.BAD_REQUEST);
        }
        return null;
    }

}
