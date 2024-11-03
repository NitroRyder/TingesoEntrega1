package com.example.entrega1.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.entrega1.entities.AhorrosEntity;
import com.example.entrega1.entities.CreditoEntity;
import com.example.entrega1.entities.UsuarioEntity;
import com.example.entrega1.repositories.AhorrosRepository;
import com.example.entrega1.repositories.CreditoRepository;
import com.example.entrega1.repositories.UsuarioRepository;
import com.example.entrega1.services.SistemaService;
import com.example.entrega1.services.UsuarioService;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class AhorrosServiceTest {
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private CreditoRepository creditoRepository;
    @Mock
    private AhorrosRepository ahorrosRepository;
    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private AhorrosService ahorrosService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    //---------------------------------------------------------------------------//
    @Test
    void testGetAhorros() {
        AhorrosEntity ahorros1 = new AhorrosEntity();
        ahorros1.setId(1L);
        ahorros1.setTransaccion(1000000);
        ahorros1.setUsuario(null);

        AhorrosEntity ahorros2 = new AhorrosEntity();
        ahorros2.setId(2L);
        ahorros2.setTransaccion(500000);
        ahorros2.setUsuario(null);

        when(ahorrosRepository.findAll()).thenReturn(Arrays.asList(ahorros1, ahorros2));
    }

    @Test
    void testGetAhorros2() {
        AhorrosEntity ahorros1 = new AhorrosEntity();
        ahorros1.setId(1L);
        ahorros1.setTransaccion(1000000);
        ahorros1.setUsuario(null);

        AhorrosEntity ahorros2 = new AhorrosEntity();
        ahorros2.setId(2L);
        ahorros2.setTransaccion(500000);
        ahorros2.setUsuario(null);

        List<AhorrosEntity> ahorrosList = new ArrayList<>(Arrays.asList(ahorros1, ahorros2));
        when(ahorrosRepository.findAll()).thenReturn(ahorrosList);

        List<AhorrosEntity> result = ahorrosService.getAhorros();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(ahorros1, result.get(0));
        assertEquals(ahorros2, result.get(1));
    }
    //---------------------------------------------------------------------------//
    @Test
    void testGetAhorrosById() {
        AhorrosEntity ahorros = new AhorrosEntity();
        ahorros.setId(1L);
        ahorros.setTransaccion(1000000);
        ahorros.setUsuario(null);

        when(ahorrosRepository.findById(1L)).thenReturn(Optional.of(ahorros));
    }

    @Test
    void testGetAhorrosById2() {
        AhorrosEntity ahorros = new AhorrosEntity();
        ahorros.setId(1L);
        ahorros.setTransaccion(1000000);
        ahorros.setUsuario(null);

        when(ahorrosRepository.findById(1L)).thenReturn(Optional.of(ahorros));

        AhorrosEntity result = ahorrosService.getAhorrosById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(1000000, result.getTransaccion());
    }
    //---------------------------------------------------------------------------//
    @Test
    void testSaveAhorros() {
        AhorrosEntity ahorros = new AhorrosEntity();
        ahorros.setId(1L);
        ahorros.setTransaccion(1000000);
        ahorros.setUsuario(null);

        when(ahorrosRepository.save(ahorros)).thenReturn(ahorros);

        AhorrosEntity result = ahorrosService.saveAhorros(ahorros);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(1000000, result.getTransaccion());
    }
    //---------------------------------------------------------------------------//
    @Test
    void testUpdateAhorros() {
        AhorrosEntity ahorros = new AhorrosEntity();
        ahorros.setId(1L);
        ahorros.setTransaccion(1000000);
        ahorros.setUsuario(null);

        when(ahorrosRepository.findById(1L)).thenReturn(Optional.of(ahorros));
        when(ahorrosRepository.save(ahorros)).thenReturn(ahorros);
    }

    @Test
    void testUpdateAhorros2() {
        AhorrosEntity ahorros = new AhorrosEntity();
        ahorros.setId(1L);
        ahorros.setTransaccion(1000000);
        ahorros.setUsuario(null);

        when(ahorrosRepository.save(ahorros)).thenReturn(ahorros);

        AhorrosEntity result = ahorrosService.updateAhorros(ahorros);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(1000000, result.getTransaccion());
    }
    //---------------------------------------------------------------------------//
    @Test
    void testDeleteAhorros() {
        Long id = 1L;

        doNothing().when(ahorrosRepository).deleteById(id);

        Map<String, Boolean> result = ahorrosService.deleteAhorros(id);

        assertNotNull(result);
        assertTrue(result.get("deleted"));
        verify(ahorrosRepository, times(1)).deleteById(id);
    }
    //---------------------------------------------------------------------------//
}
