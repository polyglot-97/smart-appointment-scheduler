package com.jatin.smart_appointment_scheduler.controllers;

import com.jatin.smart_appointment_scheduler.dtos.EncounterRequestDTO;
import com.jatin.smart_appointment_scheduler.dtos.EncounterResponseDTO;
import com.jatin.smart_appointment_scheduler.services.EncounterService;
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

@WebMvcTest(EncounterController.class)
class EncounterControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EncounterService encounterService;

    @BeforeEach
    void setup() {}

    @org.junit.jupiter.api.Test
    void create_shouldReturnCreatedEncounter() throws Exception {
        EncounterResponseDTO responseDTO = new EncounterResponseDTO();
        when(encounterService.create(any())).thenReturn(responseDTO);
        mockMvc.perform(post("/api/encounter")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk());
    }

    @org.junit.jupiter.api.Test
    void getAll_shouldReturnList() throws Exception {
        when(encounterService.getAll()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/encounter"))
                .andExpect(status().isOk());
    }

    @org.junit.jupiter.api.Test
    void getById_shouldReturnEncounter() throws Exception {
        EncounterResponseDTO responseDTO = new EncounterResponseDTO();
        when(encounterService.getById(any())).thenReturn(responseDTO);
        mockMvc.perform(get("/api/encounter/1"))
                .andExpect(status().isOk());
    }

    @org.junit.jupiter.api.Test
    void update_shouldReturnUpdatedEncounter() throws Exception {
        EncounterResponseDTO responseDTO = new EncounterResponseDTO();
        when(encounterService.update(any(), any())).thenReturn(responseDTO);
        mockMvc.perform(put("/api/encounter/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk());
    }

    @org.junit.jupiter.api.Test
    void delete_shouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/encounter/1"))
                .andExpect(status().isNoContent());
    }
} 