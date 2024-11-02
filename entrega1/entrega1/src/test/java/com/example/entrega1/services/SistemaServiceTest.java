package com.example.entrega1.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.entrega1.entities.AhorrosEntity;
import com.example.entrega1.entities.CreditoEntity;
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
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class SistemaServiceTest {
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private CreditoRepository creditoRepository;
    @Mock
    private AhorrosRepository ahorrosRepository;
    @InjectMocks
    private SistemaService sistemaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCredito_Hipotecario() {
        double result = sistemaService.Credito_Hipotecario("12.345.678-2", 100000, 0.04, 20, 150000);
        assertThat(result).isGreaterThan(0);
    }
    /*
    @Test
    public void testRegisterUsuario() {
        // Arrange
        List<AhorrosEntity> ahorros = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            AhorrosEntity ahorro = new AhorrosEntity();
            ahorro.setTransaccion(50000 * i);
            ahorro.setTipo("DEPOSITO");
            ahorros.add(ahorro);
        }

        List<CreditoEntity> creditos = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            CreditoEntity credito = new CreditoEntity();
            credito.setMontop(100000 * i);
            credito.setState("APROBADO");
            creditos.add(credito);
        }

        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setRut("12345678-9");
        usuario.setName("John Doe");
        usuario.setAge(30);
        usuario.setWorkage(5);
        usuario.setHouses(1);
        usuario.setValorpropiedad(100000);
        usuario.setIngresos(5000);
        usuario.setSumadeuda(2000);
        usuario.setObjective("PRIMERA VIVIENDA");
        usuario.setIndependiente("ASALARIADO");
        usuario.setAhorros(ahorros);
        usuario.setCreditos(creditos);

        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        // Act
        UsuarioEntity savedUsuario = sistemaService.registerUsuario(
                "12345678-9", "John Doe", 30, 5, 1, 100000, 5000, 2000,
                "PRIMERA VIVIENDA", "ASALARIADO", ahorros, creditos
        );

        // Assert
        assertNotNull(savedUsuario);
        assertEquals("12345678-9", savedUsuario.getRut());
        assertEquals("John Doe", savedUsuario.getName());
        assertEquals(30, savedUsuario.getAge());
        assertEquals(5, savedUsuario.getWorkage());
        assertEquals(1, savedUsuario.getHouses());
        assertEquals(100000, savedUsuario.getValorpropiedad());
        assertEquals(5000, savedUsuario.getIngresos());
        assertEquals(2000, savedUsuario.getSumadeuda());
        assertEquals("PRIMERA VIVIENDA", savedUsuario.getObjective());
        assertEquals("ASALARIADO", savedUsuario.getIndependiente());
        assertEquals(ahorros, savedUsuario.getAhorros());
        assertEquals(creditos, savedUsuario.getCreditos());
    }

    @Test
    void testCreateSolicitud() {
        UsuarioEntity usuario = new UsuarioEntity();
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        CreditoEntity credito = new CreditoEntity();
        when(creditoRepository.save(any(CreditoEntity.class))).thenReturn(credito);
        CreditoEntity result = sistemaService.createSolicitud(1L, 100000, 20, 0.04, 0.0033, 0.01, 0.01, 0.01, null, null, null, null, null, null, null, null);
        assertThat(result).isNotNull();
    }

    @Test
    void testEvaluateCredito() {
        UsuarioEntity usuario = new UsuarioEntity();
        CreditoEntity credito = new CreditoEntity();
        usuario.setSolicitud(credito);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        Map<String, Object> result = sistemaService.evaluateCredito(1L);
        if(result == null){
            System.out.println("result is null");
        }
        assertThat(result).isNotNull();
    }
    */
    @Test
    void testFollowCredito() {
        UsuarioEntity usuario = new UsuarioEntity();
        CreditoEntity credito = new CreditoEntity();
        usuario.setSolicitud(credito);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        CreditoEntity result = sistemaService.followCredito(1L);
        assertThat(result).isNotNull();
    }

    @Test
    void testCalcularCostosTotales() {
        UsuarioEntity usuario = new UsuarioEntity();
        CreditoEntity credito = new CreditoEntity();
        usuario.setSolicitud(credito);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        List<Double> result = sistemaService.calcularCostosTotales(1L);
        assertThat(result).isNotNull();
    }

    @Test
    void testGetNotifications() {
        UsuarioEntity usuario = new UsuarioEntity();
        List<String> notifications = new ArrayList<>();
        usuario.setNotifications(notifications);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        List<String> result = sistemaService.getNotifications(1L);
        assertThat(result).isNotNull();
    }

    @Test
    void testUpdateState() {
        UsuarioEntity usuario = new UsuarioEntity();
        CreditoEntity credito = new CreditoEntity();
        usuario.setSolicitud(credito);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        int result = sistemaService.updateState(1L, 1);
        assertThat(result).isEqualTo(0);
    }
}