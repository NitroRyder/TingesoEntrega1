package com.example.entrega1.services;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.entrega1.controllers.CreditoController;
import com.example.entrega1.entities.AhorrosEntity;
import com.example.entrega1.entities.CreditoEntity;
import com.example.entrega1.entities.UsuarioEntity;
import com.example.entrega1.repositories.AhorrosRepository;
import com.example.entrega1.repositories.CreditoRepository;
import com.example.entrega1.repositories.UsuarioRepository;
import com.example.entrega1.services.CreditoService;
import com.example.entrega1.services.SistemaService;
import com.example.entrega1.services.UsuarioService;
import com.example.entrega1.controllers.UsuarioController;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;



public class CreditoServiceTest {
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private CreditoRepository creditoRepository;
    @Mock
    private CreditoController creditoController;
    @InjectMocks
    private CreditoService creditoService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    //---------------------------------------------------------------------------//
    @Test
    void testGetCreditos() {
        // Arrange
        CreditoEntity credito1 = new CreditoEntity();
        credito1.setId(1L);
        CreditoEntity credito2 = new CreditoEntity();
        credito2.setId(2L);

        List<CreditoEntity> creditos = new ArrayList<>(Arrays.asList(credito1, credito2));

        when(creditoRepository.findAll()).thenReturn(creditos);

        // Act
        ArrayList<CreditoEntity> result = creditoService.getCreditos();

        // Assert
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getId()).isEqualTo(1L);
        assertThat(result.get(1).getId()).isEqualTo(2L);
    }
    //---------------------------------------------------------------------------//
    @Test
    void testSaveCredito(){
        CreditoEntity credito = new CreditoEntity();
        credito.setId(1L);
        credito.setMontop(1000000);
        credito.setPlazo(12);
        credito.setUsuario(null);

        when(creditoRepository.save(credito)).thenReturn(credito);

        CreditoEntity savedCredito = creditoService.saveCredito(credito);

        assertEquals(1L, savedCredito.getId());
        assertEquals(1000000, savedCredito.getMontop());
        assertEquals(12, savedCredito.getPlazo());
    }
    //---------------------------------------------------------------------------//
    @Test
    void testUpdateCredito() {
        CreditoEntity credito = new CreditoEntity();
        credito.setId(1L);
        credito.setMontop(1000000);
        credito.setPlazo(12);
        credito.setUsuario(null);

        when(creditoRepository.findById(1L)).thenReturn(Optional.of(credito));
        when(creditoRepository.save(credito)).thenReturn(credito);

    }

    @Test
    void testUpdateCredito2() {
        // Arrange
        CreditoEntity credito = new CreditoEntity();
        credito.setId(1L);
        credito.setMontop(1000000);
        credito.setPlazo(12);
        credito.setUsuario(null);

        when(creditoRepository.findById(1L)).thenReturn(Optional.of(credito));
        when(creditoRepository.save(credito)).thenReturn(credito);

        // Act
        CreditoEntity updatedCredito = creditoService.updateCredito(credito);

        // Assert
        assertNotNull(updatedCredito);
        assertEquals(1L, updatedCredito.getId());
        assertEquals(1000000, updatedCredito.getMontop());
        assertEquals(12, updatedCredito.getPlazo());
        verify(creditoRepository, times(1)).save(credito);
    }
    //---------------------------------------------------------------------------//
    @Test
    void testDeleteCredito() throws Exception {
        Long creditoId = 1L;

        // Act
        creditoService.deleteCredito(creditoId);

        // Assert
        verify(creditoRepository).deleteById(creditoId);
    }

    @Test
    void testDeleteCreditoWithException() {
        Long creditoId = 1L;

        // Arrange
        doThrow(new RuntimeException("Exception")).when(creditoRepository).deleteById(creditoId);

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> {
            creditoService.deleteCredito(creditoId);
        });

        assertThrows(Exception.class, () -> creditoService.deleteCredito(creditoId));
    }
    //---------------------------------------------------------------------------//
    @Test
    void testDeleteAllCreditos() {
        // Act
        creditoService.deleteAllCreditos();

        // Assert
        verify(creditoRepository, times(1)).deleteAll();
    }
    //---------------------------------------------------------------------------//
    @Test
    void testGetCreditoById() {
        // Arrange
        Long creditoId = 1L;
        CreditoEntity credito = new CreditoEntity();
        credito.setId(creditoId);
        credito.setMontop(1000000);
        credito.setPlazo(12);

        when(creditoRepository.findById(creditoId)).thenReturn(Optional.of(credito));

        // Act
        CreditoEntity result = creditoService.getCreditoById(creditoId);

        // Assert
        assertNotNull(result);
        assertEquals(creditoId, result.getId());
        assertEquals(1000000, result.getMontop());
        assertEquals(12, result.getPlazo());
    }
    //---------------------------------------------------------------------------//
    @Test
    void testAddCreditoToUsuario() {
        // Arrange
        CreditoEntity credito = new CreditoEntity();
        credito.setId(1L);
        credito.setMontop(1000000);
        credito.setPlazo(12);
        credito.setUsuario(null);

        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setRut("12345678-9");
        usuario.setName("Juan");
        usuario.setAge(25);
        usuario.setSumadeuda(1000000);

        when(usuarioRepository.existsByRut("12345678-9")).thenReturn(true);
        when(usuarioRepository.findByRut("12345678-9")).thenReturn(Arrays.asList(usuario));
        when(creditoRepository.save(credito)).thenReturn(credito);

        // Act
        CreditoEntity result = creditoService.addCreditoToUsuario(credito, "12345678-9");

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(1000000, result.getMontop());
        assertEquals(12, result.getPlazo());
        assertEquals(usuario, result.getUsuario());
    }

    @Test
    void testAddCreditoToUsuario2() {
        // Arrange
        CreditoEntity credito = new CreditoEntity();
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setRut("12345678-9");

        when(usuarioRepository.existsByRut("12345678-9")).thenReturn(true);
        when(usuarioRepository.findByRut("12345678-9")).thenReturn(Arrays.asList(usuario));
        when(creditoRepository.save(credito)).thenReturn(credito);

        // Act
        CreditoEntity result = creditoService.addCreditoToUsuario(credito, "12345678-9");

        // Assert
        assertNotNull(result);
        assertEquals(usuario, result.getUsuario());
        verify(creditoRepository, times(1)).save(credito);
    }

    @Test
    void testAddCreditoToUsuarioWithNullRut() {
        // Arrange
        CreditoEntity credito = new CreditoEntity();

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            creditoService.addCreditoToUsuario(credito, null);
        });

        assertEquals("RUT NO PUEDE SER NULO", exception.getMessage());
    }

    @Test
    void testAddCreditoToUsuarioWithNonExistentRut() {
        // Arrange
        CreditoEntity credito = new CreditoEntity();

        when(usuarioRepository.existsByRut("nonexistent-rut")).thenReturn(false);

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            creditoService.addCreditoToUsuario(credito, "nonexistent-rut");
        });

        assertEquals("USUARIO NO ENCONTRADO POR RUT", exception.getMessage());
    }
    //---------------------------------------------------------------------------//
}
