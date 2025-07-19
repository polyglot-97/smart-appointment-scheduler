package com.jatin.smart_appointment_scheduler.services;

import com.jatin.smart_appointment_scheduler.dtos.ProviderRequestDTO;
import com.jatin.smart_appointment_scheduler.dtos.ProviderResponseDTO;
import com.jatin.smart_appointment_scheduler.entities.Provider;
import com.jatin.smart_appointment_scheduler.enums.Role;
import com.jatin.smart_appointment_scheduler.repositories.ProviderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import java.util.Collections;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProviderServiceTest {
    @Mock private ProviderRepository providerRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @InjectMocks private ProviderService providerService;

    // Mock dependencies here

    @Test
    void create_shouldSaveProvider_whenValid() {
        ProviderRequestDTO dto = Mockito.mock(ProviderRequestDTO.class);
        Provider provider = Mockito.mock(Provider.class);
        Provider savedProvider = Mockito.mock(Provider.class);
        Mockito.when(dto.getEmail()).thenReturn("test@example.com");
        Mockito.when(dto.getPhone()).thenReturn("1234567890");
        Mockito.when(dto.getPassword()).thenReturn("password");
        Mockito.when(providerRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        Mockito.when(providerRepository.findByPhone("1234567890")).thenReturn(Optional.empty());
        Mockito.when(passwordEncoder.encode("password")).thenReturn("encoded");
        Mockito.when(providerRepository.save(Mockito.any())).thenReturn(savedProvider);
        ProviderResponseDTO response = providerService.create(dto);
        Mockito.verify(providerRepository).save(Mockito.any());
        assertNotNull(response);
    }

    @Test
    void create_shouldThrow_whenEmailExists() {
        ProviderRequestDTO dto = Mockito.mock(ProviderRequestDTO.class);
        Mockito.when(dto.getEmail()).thenReturn("test@example.com");
        Mockito.when(providerRepository.findByEmail("test@example.com")).thenReturn(Optional.of(Mockito.mock(Provider.class)));
        assertThrows(IllegalArgumentException.class, () -> providerService.create(dto));
    }

    @Test
    void create_shouldThrow_whenPhoneExists() {
        ProviderRequestDTO dto = Mockito.mock(ProviderRequestDTO.class);
        Mockito.when(dto.getEmail()).thenReturn("test@example.com");
        Mockito.when(dto.getPhone()).thenReturn("1234567890");
        Mockito.when(providerRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        Mockito.when(providerRepository.findByPhone("1234567890")).thenReturn(Optional.of(Mockito.mock(Provider.class)));
        assertThrows(IllegalArgumentException.class, () -> providerService.create(dto));
    }

    @Test
    void getAll_shouldReturnList() {
        Mockito.when(providerRepository.findAll()).thenReturn(Collections.emptyList());
        assertNotNull(providerService.getAll());
    }

    @Test
    void getById_shouldReturnProvider_whenFound() {
        Provider provider = Mockito.mock(Provider.class);
        Mockito.when(providerRepository.findById(1L)).thenReturn(Optional.of(provider));
        assertNotNull(providerService.getById(1L));
    }

    @Test
    void getById_shouldThrow_whenNotFound() {
        Mockito.when(providerRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> providerService.getById(1L));
    }

    @Test
    void update_shouldUpdateProvider_whenFound() {
        Provider provider = Mockito.mock(Provider.class);
        ProviderRequestDTO dto = Mockito.mock(ProviderRequestDTO.class);
        Mockito.when(providerRepository.findById(1L)).thenReturn(Optional.of(provider));
        Mockito.when(passwordEncoder.encode(Mockito.any())).thenReturn("encoded");
        Mockito.when(providerRepository.save(Mockito.any())).thenReturn(provider);
        assertNotNull(providerService.update(1L, dto));
    }

    @Test
    void update_shouldThrow_whenNotFound() {
        ProviderRequestDTO dto = Mockito.mock(ProviderRequestDTO.class);
        Mockito.when(providerRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> providerService.update(1L, dto));
    }

    @Test
    void delete_shouldDelete_whenFound() {
        Mockito.when(providerRepository.existsById(1L)).thenReturn(true);
        assertDoesNotThrow(() -> providerService.delete(1L));
    }

    @Test
    void delete_shouldThrow_whenNotFound() {
        Mockito.when(providerRepository.existsById(1L)).thenReturn(false);
        assertThrows(NoSuchElementException.class, () -> providerService.delete(1L));
    }

    @Test
    void sampleTest() {
        // TODO: implement test
    }
} 