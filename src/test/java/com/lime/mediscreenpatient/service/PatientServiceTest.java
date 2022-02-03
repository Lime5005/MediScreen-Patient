package com.lime.mediscreenpatient.service;

import com.lime.mediscreenpatient.MediscreenPatientApplication;
import com.lime.mediscreenpatient.model.Gender;
import com.lime.mediscreenpatient.model.Patient;
import com.lime.mediscreenpatient.repository.PatientRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes={ MediscreenPatientApplication.class })
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PatientServiceTest {

    private Patient patient = new Patient();
    private Patient patientToDelete = new Patient();

    private Long id = null;
    private Long idToDelete = null;

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientRepository patientRepository;

    @BeforeAll
    public void initPatient() throws ParseException {
        String birthDate = "1967-01-01";
        patient.setFirstName("Tim");
        patient.setLastName("Bits");
        patient.setBirthDate(parseDate(birthDate));
        patient.setSex(Gender.M);
        patientService.savePatient(patient);
        id = patient.getId();

        patientToDelete.setFirstName("Sally");
        patientToDelete.setLastName("Cats");
        patientToDelete.setBirthDate(parseDate(birthDate));
        patientToDelete.setSex(Gender.F);
        patientService.savePatient(patientToDelete);
        idToDelete = patientToDelete.getId();
    }

    @AfterAll
    public void cleanPatient() {
        System.out.println("id == " + id);
        patientRepository.deleteById(id);
    }

    @Test
    @Order(1)
    public void test_Find_All_Should_Exist() {
        List<Patient> patients = patientService.findAll();
        assertTrue(patients.size() > 0);
    }

    @Test
    @Order(2)
    public void test_Find_Patient_By_Id_Should_Exist() {
        Patient patientById = patientService.findPatientById(id);
        assertEquals("Tim", patientById.getFirstName());
        assertEquals("Bits", patientById.getLastName());
        assertEquals(Gender.M, patientById.getSex());
    }

    @Test
    @Order(3)
    public void test_Find_Patient_By_Lastname_Should_OK() {
        List<Patient> bits = patientService.findPatientByLastName("Bits");
        assertTrue(bits.size() > 0);
    }

    @Test
    @Order(4)
    public void test_Update_Patient_By_Id_Should_OK() {
        Long id = patient.getId();
        Patient foundPatient = patientService.findPatientById(id);
        foundPatient.setLastName("Jones");
        foundPatient.setFirstName("Ken");
        Patient updatePatient = patientService.updatePatient(id, foundPatient);
        assertEquals("Jones", updatePatient.getLastName());
        assertEquals("Ken", updatePatient.getFirstName());
    }

    @Test
    @Order(5)
    public void test_Delete_Patient_By_Id_Should_OK() {
        patientService.deletePatientById(idToDelete);
        Patient patientById = patientService.findPatientById(idToDelete);
        assertNull(patientById);
    }

    @Test
    @Order(6)
    public void test_Update_Null_Patient_Should_Return_Null() {
        Patient patient = patientService.updatePatient(idToDelete, patientToDelete);
        assertNull(patient);
    }

    public static Date parseDate(String myDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(myDate);
        long timeInMillis = date.getTime();
        return new Date(timeInMillis);
    }

}
