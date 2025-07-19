package com.jatin.smart_appointment_scheduler.controllers;

import com.jatin.smart_appointment_scheduler.dtos.ProviderRequestDTO;
import com.jatin.smart_appointment_scheduler.dtos.ProviderResponseDTO;
import com.jatin.smart_appointment_scheduler.services.ProviderService;
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

@WebMvcTest(ProviderController.class)
class ProviderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProviderService providerService;

    @BeforeEach
    void setup() {}

    @org.junit.jupiter.api.Test
    void create_shouldReturnCreatedProvider() throws Exception {
        ProviderResponseDTO responseDTO = new ProviderResponseDTO();
        when(providerService.create(any())).thenReturn(responseDTO);
        mockMvc.perform(post("/api/provider")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk());
    }

    @org.junit.jupiter.api.Test
    void getAll_shouldReturnList() throws Exception {
        when(providerService.getAll()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/provider"))
                .andExpect(status().isOk());
    }

    @org.junit.jupiter.api.Test
    void getById_shouldReturnProvider() throws Exception {
        ProviderResponseDTO responseDTO = new ProviderResponseDTO();
        when(providerService.getById(any())).thenReturn(responseDTO);
        mockMvc.perform(get("/api/provider/1"))
                .andExpect(status().isOk());
    }

    @org.junit.jupiter.api.Test
    void update_shouldReturnUpdatedProvider() throws Exception {
        ProviderResponseDTO responseDTO = new ProviderResponseDTO();
        when(providerService.update(any(), any())).thenReturn(responseDTO);
        mockMvc.perform(put("/api/provider/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk());
    }

    @org.junit.jupiter.api.Test
    void delete_shouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/provider/1"))
                .andExpect(status().isNoContent());
    }
} 