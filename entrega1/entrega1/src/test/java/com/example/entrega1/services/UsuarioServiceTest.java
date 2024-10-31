package com.example.entrega1.services;

import com.example.entrega1.entities.UsuarioEntity;
import com.example.entrega1.repositories.AhorrosRepository;
import com.example.entrega1.repositories.CreditoRepository;
import com.example.entrega1.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;

public class UsuarioServiceTest {
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private CreditoRepository creditoRepository;
    @Mock
    private AhorrosRepository ahorrosRepository;
    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUsuarios(){
        UsuarioEntity usuario1 = new UsuarioEntity();
        usuario1.setId(1L);
        usuario1.setRut("12.345.678-2");
        usuario1.setName("Raul");
        usuario1.setAge(30);
        usuario1.setWorkage(2);
        usuario1.setHouses(2);
        usuario1.setValorpropiedad(1000000);
        usuario1.setIngresos(500000);
        usuario1.setSumadeuda(240000);
        usuario1.setObjective("Compra de vivienda");
        usuario1.setIndependiente("independiente");
        usuario1.setAhorros(null);
        usuario1.setCreditos(null);
        usuario1.setNotifications(null);

        UsuarioEntity usuario2 = new UsuarioEntity();
        usuario2.setId(2L);
        usuario2.setRut("12.345.678-3");
        usuario2.setName("Juan");
        usuario2.setAge(43);
        usuario2.setWorkage(5);
        usuario2.setHouses(1);
        usuario2.setValorpropiedad(500000);
        usuario2.setIngresos(300000);
        usuario2.setSumadeuda(100000);
        usuario2.setObjective("Compra de auto");
        usuario2.setIndependiente("dependiente");
        usuario2.setAhorros(null);
        usuario2.setCreditos(null);
        usuario2.setNotifications(null);

        List<UsuarioEntity> usuarios = new ArrayList<>(Arrays.asList(usuario1, usuario2));

        when(usuarioRepository.findAll()).thenReturn(usuarios);

        List<UsuarioEntity> result = usuarioService.getUsuarios();

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getId()).isEqualTo(1L);
        assertThat(result.get(0).getRut()).isEqualTo("12345678-2");
    }

}
