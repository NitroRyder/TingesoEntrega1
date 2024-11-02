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



public class CreditoServiceTest {
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private CreditoRepository creditoRepository;
    @InjectMocks
    private CreditoService creditoService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    //---------------------------------------------------------------------------//
    /*
    @Test
    void testGetCreditos(){
        CreditoEntity credito1 = new CreditoEntity();
        credito1.setId(1L);
        credito1.setMontop(1000000);
        credito1.setPlazo(12);
        credito1.setUsuario(null);

        CreditoEntity credito2 = new CreditoEntity();
        credito2.setId(2L);
        credito2.setMontop(500000);
        credito2.setPlazo(6);
        credito2.setUsuario(null);

        when(creditoRepository.findAll()).thenReturn(Arrays.asList(credito1, credito2));

        List<CreditoEntity> creditoList = creditoService.getCreditos();

        assertEquals(2, creditoList.size());
        assertEquals(1L, creditoList.get(0).getId());
        assertEquals(1000000, creditoList.get(0).getMontop());
        assertEquals(12, creditoList.get(0).getPlazo());
        assertEquals(2L, creditoList.get(1).getId());
        assertEquals(500000, creditoList.get(1).getMontop());
        assertEquals(6, creditoList.get(1).getPlazo());
    }
    */
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
}
