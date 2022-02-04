package com.lime.mediscreenpatient.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lime.mediscreenpatient.MediscreenPatientApplication;
import com.lime.mediscreenpatient.model.Gender;
import com.lime.mediscreenpatient.model.Patient;
import com.lime.mediscreenpatient.service.PatientService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.MOCK, classes={ MediscreenPatientApplication.class })
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private PatientService patientServiceMock;

    @BeforeAll
    public void setUp() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void test_Add_Patient_Should_Return_OK()  throws Exception {
        String addBirth = "1998-10-29";
        Date birthDate = parseDate(addBirth);
        Patient addPatient = new Patient();
        addPatient.setId(888L);
        addPatient.setFirstName("bbb");
        addPatient.setLastName("bbb");
        addPatient.setBirthDate(birthDate);
        addPatient.setSex(Gender.F);
        when(patientServiceMock.savePatient(any(Patient.class))).thenReturn(addPatient);
        mockMvc.perform(post("/api/patient/add").contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(addPatient))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("bbb"));
    }

    @Test
    public void test_Get_Patient_By_Id_Should_Return_OK() throws Exception {
        String getBirth = "1997-10-29";
        Date birthDate = parseDate(getBirth);
        Patient patient = new Patient(999L, "ccc", "ccc", birthDate, Gender.M);
        when(patientServiceMock.findPatientById(999L)).thenReturn(patient);
        mockMvc.perform(get("/api/patients/999")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("ccc"));
    }

    @Test
    public void test_Get_Null_Patient_By_Id_Should_Return_OK() throws Exception {
        when(patientServiceMock.findPatientById(41L)).thenReturn(null);
        mockMvc.perform(get("/api/patients/41"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_Update_Patient_By_Id_Should_Return_OK() throws Exception {
        String getBirth = "1967-10-29";
        Date birthDate = parseDate(getBirth);
        Patient updatedPatient = new Patient(777L, "updatedDDD", "ddd", birthDate, Gender.M);
        when(patientServiceMock.updatePatient(777L, updatedPatient)).thenReturn(updatedPatient);
        mockMvc.perform(put("/api/patients/update/777")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedPatient))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("updatedDDD"));

    }

    @Test
    public void test_Get_BindingResult_When_Input_Is_Null() throws Exception {
        String getBirth = "1968-10-29";
        Date birthDate = parseDate(getBirth);
        Patient updatedPatient = new Patient(33L, "", "ddd", birthDate, Gender.M);
        when(patientServiceMock.updatePatient(33L, updatedPatient)).thenReturn(updatedPatient);
        mockMvc.perform(put("/api/patients/update/33")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedPatient))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_Delete_Patient_By_Id_Should_Return_OK() throws Exception {
        when(patientServiceMock.deletePatientById(111L)).thenReturn(true);
        mockMvc.perform(delete("/api/patients/111")
                ).andExpect(status().isNoContent());

    }

    @Test
    public void test_Get_All_Patients_Should_Return_OK() throws Exception {
        String getBirth = "1967-10-29";
        Date birthDate = parseDate(getBirth);
        Patient patientNum1 = new Patient(333L, "iii", "iii", birthDate, Gender.M);
        Patient patientNum2 = new Patient(555L, "jjj", "jjj", birthDate, Gender.M);
        List<Patient> patientList = new ArrayList<>();
        patientList.add(patientNum1);
        patientList.add(patientNum2);
        when(patientServiceMock.findAll()).thenReturn(patientList);
        mockMvc.perform(get("/api/patients")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$..*").isNotEmpty())
                .andExpect(jsonPath("$.[0].firstName").value("iii"))
                .andExpect(jsonPath("$.[1].firstName").value("jjj"));
    }

    @Test
    public void test_Get_Patient_By_LastName_Should_Return_OK() throws Exception {
        String getBirth = "1967-10-29";
        Date birthDate = parseDate(getBirth);
        Patient foundPatient1 = new Patient(22L, "vicky", "vvv", birthDate, Gender.M);
        Patient foundPatient2 = new Patient(11L, "vivian", "vvv", birthDate, Gender.M);
        List<Patient> patientList = new ArrayList<>();
        patientList.add(foundPatient1);
        patientList.add(foundPatient2);
        when(patientServiceMock.findPatientByLastName("vvv")).thenReturn(patientList);
        mockMvc.perform(get("/api/patients/family")
                .param("lastName", "vvv")
                .contentType(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$..*").isNotEmpty())
                .andExpect(jsonPath("$.[0].firstName").value("vicky"))
                .andExpect(jsonPath("$.[1].firstName").value("vivian"));
    }

    @Test
    public void test_Get_Empty_List_Patients_Should_Return_OK() throws Exception {
        when(patientServiceMock.findPatientByLastName("ooo")).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/api/patients/family")
                .param("lastName", "ooo")
                .contentType(MediaType.ALL))
                .andExpect(status().isNoContent());
    }

    @Test
    public void test_Get_Patient_By_Id_Should_Return_404() throws Exception {
        when(patientServiceMock.findPatientById(444L)).thenReturn(null);
        mockMvc.perform(get("/api/patient/444")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void test_Get_Null_Patients_By_lastName_Should_Return_OK() throws Exception {
        when(patientServiceMock.findPatientByLastName("nnn")).thenReturn(null);
        mockMvc.perform(get("/api/patients")
                .param("lastName", "nnn")
                .contentType(MediaType.ALL))
                .andExpect(status().isNoContent());
    }

    @Test
    public void test_Delete_Null_Patient_Should_Return_404() throws Exception {
        when(patientServiceMock.deletePatientById(1111L)).thenReturn(false);
        mockMvc.perform(delete("/api/patients/1111")
                ).andExpect(status().isNotFound());
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Date parseDate(String myDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(myDate);
        long timeInMillis = date.getTime();
        return new Date(timeInMillis);
    }
}
