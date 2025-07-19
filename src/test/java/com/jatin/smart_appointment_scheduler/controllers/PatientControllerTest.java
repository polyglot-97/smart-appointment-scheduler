package com.jatin.smart_appointment_scheduler.controllers;

import com.jatin.smart_appointment_scheduler.dtos.PatientRequestDTO;
import com.jatin.smart_appointment_scheduler.dtos.PatientResponseDTO;
import com.jatin.smart_appointment_scheduler.services.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Collections;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(PatientController.class)
class PatientControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PatientService patientService;

    @BeforeEach
    void setup() {}

    @org.junit.jupiter.api.Test
    void create_shouldReturnCreatedPatient() throws Exception {
        PatientResponseDTO responseDTO = new PatientResponseDTO();
        when(patientService.create(any())).thenReturn(responseDTO);
        mockMvc.perform(post("/api/patient")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk());
    }

    @org.junit.jupiter.api.Test
    void getAll_shouldReturnList() throws Exception {
        when(patientService.getAll()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/patient"))
                .andExpect(status().isOk());
    }

    @org.junit.jupiter.api.Test
    void getById_shouldReturnPatient() throws Exception {
        PatientResponseDTO responseDTO = new PatientResponseDTO();
        when(patientService.getById(any())).thenReturn(responseDTO);
        mockMvc.perform(get("/api/patient/1"))
                .andExpect(status().isOk());
    }

    @org.junit.jupiter.api.Test
    void update_shouldReturnUpdatedPatient() throws Exception {
        PatientResponseDTO responseDTO = new PatientResponseDTO();
        when(patientService.update(any(), any())).thenReturn(responseDTO);
        mockMvc.perform(put("/api/patient/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk());
    }

    @org.junit.jupiter.api.Test
    void delete_shouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/patient/1"))
                .andExpect(status().isNoContent());
    }
} 