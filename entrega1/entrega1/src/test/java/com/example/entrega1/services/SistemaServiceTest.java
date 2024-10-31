package com.example.entrega1.services;

import com.example.entrega1.entities.UsuarioEntity;
import com.example.entrega1.repositories.UsuarioRepository;
import com.example.entrega1.entities.CreditoEntity;
import com.example.entrega1.repositories.CreditoRepository;
import com.example.entrega1.entities.AhorrosEntity;
import com.example.entrega1.repositories.AhorrosRepository;

import com.example.entrega1.services.SistemaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class SistemaServiceTest {
    @InjectMocks
    private SistemaService sistemaService;

    @Mock
    private UsuarioRepository usuarioRepository;

    private UsuarioEntity usuario;
    private CreditoEntity credito;
    private AhorrosEntity ahorros;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuario = new UsuarioEntity();
        credito = new CreditoEntity();
        ahorros = new AhorrosEntity();

        // Mock the save method to return the savedUsuario object

    }
    /*
    @Test
    void whenGetMontoCredito_thenCorrect() {
        // Given
        UsuarioEntity savedUsuario = new UsuarioEntity();
        usuario.setId(1L);
        usuario.setRut("12.345.678-2");
        usuario.setName("Raul");
         usuario.setAge(30);
        usuario.setWorkage(2);
        usuario.setDocuments(new ArrayList<>());
        usuario.setHouses(2);
        usuario.setValorpropiedad(1000000);
        usuario.setIngresos(500000);
        usuario.setSumadeuda(240000);
        usuario.setObjective("Compra de vivienda");
        usuario.setIndependiente("independiente");
        usuario.setAhorros(new ArrayList<>());
        usuario.setCreditos(new ArrayList<>());
        usuario.setNotifications(new ArrayList<>());

        when(usuarioRepository.save(usuario)).thenReturn(savedUsuario);

        // When
        // registrar usuario
        //usuario = sistemaService.registerUsuario(usuario);
        usuario = sistemaService.registerUsuario(usuario.getRut(), usuario.getName(), usuario.getAge(), usuario.getWorkage(), usuario.getDocuments(), usuario.getHouses(), usuario.getValorpropiedad(), usuario.getIngresos(), usuario.getSumadeuda(), usuario.getObjective(), usuario.getIndependiente(), usuario.getAhorros(), usuario.getCreditos());
        // Then
        assertThat(usuario).isNotNull();
    }
    */
}
