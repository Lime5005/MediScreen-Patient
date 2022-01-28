package com.lime.mediscreen.proxy;

import com.lime.mediscreen.model.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "mediscreen-patient", url = "${patient.serviceUrl}")
public interface PatientFeignProxy {

    @GetMapping("/api/patients/{id}")
    ResponseEntity<Patient> getPatientById(@PathVariable(value = "id") Long patientId);

    @GetMapping("/api/patients/family")
    ResponseEntity<List<Patient>> getPatientsByLastName(@RequestParam(value = "lastName") String lastName);
}
