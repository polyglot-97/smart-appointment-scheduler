package com.jatin.smart_appointment_scheduler.controllers;

import com.jatin.smart_appointment_scheduler.dtos.AppointmentRequestDTO;
import com.jatin.smart_appointment_scheduler.dtos.AppointmentResponseDTO;
import com.jatin.smart_appointment_scheduler.services.AppointmentService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
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

@WebMvcTest(AppointmentController.class)
class AppointmentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AppointmentService appointmentService;

    @BeforeEach
    void setup() {}

    @org.junit.jupiter.api.Test
    void create_shouldReturnCreatedAppointment() throws Exception {
        AppointmentResponseDTO responseDTO = new AppointmentResponseDTO();
        when(appointmentService.create(any())).thenReturn(responseDTO);
        mockMvc.perform(post("/api/appointment")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk());
    }

    @org.junit.jupiter.api.Test
    void getAll_shouldReturnList() throws Exception {
        when(appointmentService.getAll()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/appointment"))
                .andExpect(status().isOk());
    }
} 