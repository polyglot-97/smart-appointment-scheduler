package com.jatin.smart_appointment_scheduler.controllers;

import com.jatin.smart_appointment_scheduler.dtos.ProviderAvailabilityRequestDTO;
import com.jatin.smart_appointment_scheduler.dtos.ProviderAvailabilityResponseDTO;
import com.jatin.smart_appointment_scheduler.services.ProviderAvailabilityService;
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
import static org.mockito.Mockito.mock;
import com.jatin.smart_appointment_scheduler.entities.ProviderAvailability;

@WebMvcTest(ProviderAvailabilityController.class)
class ProviderAvailabilityControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProviderAvailabilityService providerAvailabilityService;
    @MockBean
    private com.jatin.smart_appointment_scheduler.repositories.ProviderRepository providerRepository;
    @MockBean
    private com.jatin.smart_appointment_scheduler.repositories.ClinicRepository clinicRepository;

    @BeforeEach
    void setup() {}

    @org.junit.jupiter.api.Test
    void create_shouldReturnCreatedProviderAvailability() throws Exception {
        ProviderAvailability entity = mock(ProviderAvailability.class);
        when(providerAvailabilityService.create(any())).thenReturn(entity);
        mockMvc.perform(post("/api/provider-availability")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk());
    }

    @org.junit.jupiter.api.Test
    void getAll_shouldReturnList() throws Exception {
        when(providerAvailabilityService.getAll()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/provider-availability"))
                .andExpect(status().isOk());
    }

    @org.junit.jupiter.api.Test
    void getById_shouldReturnProviderAvailability() throws Exception {
        ProviderAvailability entity = mock(ProviderAvailability.class);
        when(providerAvailabilityService.getById(any())).thenReturn(entity);
        mockMvc.perform(get("/api/provider-availability/1"))
                .andExpect(status().isOk());
    }

    @org.junit.jupiter.api.Test
    void update_shouldReturnUpdatedProviderAvailability() throws Exception {
        ProviderAvailability entity = mock(ProviderAvailability.class);
        when(providerAvailabilityService.update(any(), any())).thenReturn(entity);
        mockMvc.perform(put("/api/provider-availability/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk());
    }

    @org.junit.jupiter.api.Test
    void delete_shouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/provider-availability/1"))
                .andExpect(status().isNoContent());
    }
} 