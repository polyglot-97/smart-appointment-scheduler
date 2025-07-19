package com.jatin.smart_appointment_scheduler.controllers;

import com.jatin.smart_appointment_scheduler.dtos.ClinicRequestDTO;
import com.jatin.smart_appointment_scheduler.dtos.ClinicResponseDTO;
import com.jatin.smart_appointment_scheduler.services.ClinicService;
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

@WebMvcTest(ClinicController.class)
class ClinicControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ClinicService clinicService;

    @BeforeEach
    void setup() {}

    @org.junit.jupiter.api.Test
    void create_shouldReturnCreatedClinic() throws Exception {
        ClinicResponseDTO responseDTO = new ClinicResponseDTO();
        when(clinicService.create(any())).thenReturn(responseDTO);
        mockMvc.perform(post("/api/clinic")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk());
    }

    @org.junit.jupiter.api.Test
    void getAll_shouldReturnList() throws Exception {
        when(clinicService.getAll()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/clinic"))
                .andExpect(status().isOk());
    }

    @org.junit.jupiter.api.Test
    void getById_shouldReturnClinic() throws Exception {
        ClinicResponseDTO responseDTO = new ClinicResponseDTO();
        when(clinicService.getById(any())).thenReturn(responseDTO);
        mockMvc.perform(get("/api/clinic/1"))
                .andExpect(status().isOk());
    }

    @org.junit.jupiter.api.Test
    void update_shouldReturnUpdatedClinic() throws Exception {
        ClinicResponseDTO responseDTO = new ClinicResponseDTO();
        when(clinicService.update(any(), any())).thenReturn(responseDTO);
        mockMvc.perform(put("/api/clinic/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk());
    }

    @org.junit.jupiter.api.Test
    void delete_shouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/clinic/1"))
                .andExpect(status().isNoContent());
    }
} 