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
    @InjectMocks
    private UsuarioService usuarioService;

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
    //---------------------------------------------------------------------------//
    /*
    @Test
    void testSaveAhorros() {
        AhorrosEntity ahorros = new AhorrosEntity();
        ahorros.setId(1L);
        ahorros.setTransaccion(1000000);
        ahorros.setUsuario(null);

        when(ahorrosRepository.save(ahorros)).thenReturn(ahorros);

        AhorrosEntity ahorrosGuardado = usuarioService.saveAhorros(ahorros);

        assertEquals(1L, ahorrosGuardado.getId());
        assertEquals(1000000, ahorrosGuardado.getTransaccion());
        assertEquals(null, ahorrosGuardado.getUsuario());
    }
    */
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
}
