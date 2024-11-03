package com.example.entrega1.services;

import com.example.entrega1.entities.AhorrosEntity;
import com.example.entrega1.entities.UsuarioEntity;
import com.example.entrega1.repositories.AhorrosRepository;
import com.example.entrega1.repositories.CreditoRepository;
import com.example.entrega1.repositories.UsuarioRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doThrow;
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
    //---------------------------------------------------------------------------//
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
    //---------------------------------------------------------------------------//
    @Test
    void testSaveUsuario(){
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setId(1L);
        usuario.setRut("12.345.678-2");
        usuario.setName("Raul");
        usuario.setAge(30);
        usuario.setWorkage(2);
        usuario.setHouses(2);
        usuario.setValorpropiedad(1000000);
        usuario.setIngresos(500000);
        usuario.setSumadeuda(240000);
        usuario.setObjective("Compra de vivienda");
        usuario.setIndependiente("independiente");
        usuario.setAhorros(null);
        usuario.setCreditos(null);
        usuario.setNotifications(null);

        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        UsuarioEntity result = usuarioService.saveUsuario(usuario);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getRut()).isEqualTo("12345678-2");
    }
    //---------------------------------------------------------------------------//
    @Test
    void testUpdateUsuario(){
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setId(1L);
        usuario.setRut("12.345.678-2");
        usuario.setName("Raul");
        usuario.setAge(30);
        usuario.setWorkage(2);
        usuario.setHouses(2);
        usuario.setValorpropiedad(1000000);
        usuario.setIngresos(500000);
        usuario.setSumadeuda(240000);
        usuario.setObjective("Compra de vivienda");
        usuario.setIndependiente("independiente");
        usuario.setAhorros(null);
        usuario.setCreditos(null);
        usuario.setNotifications(null);

        when(usuarioRepository.findById(1L)).thenReturn(java.util.Optional.of(usuario));
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        UsuarioEntity result = usuarioService.updateUsuario(1L, 1000000, 500000, 240000, "Compra de vivienda");

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getValorpropiedad()).isEqualTo(1000000);
    }
    @Test
    public void testUpdateUsuarioReturnsNullWhenUserNotFound() {
        UsuarioRepository usuarioRepository = Mockito.mock(UsuarioRepository.class);
        UsuarioService usuarioService = new UsuarioService();
        usuarioService.usuarioRepository = usuarioRepository;

        Long id = 1L;
        int valorpropiedad = 100000;
        int ingresos = 50000;
        int sumadeuda = 20000;
        String objective = "Compra";

        // Simular que el usuario no es encontrado
        Mockito.when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        UsuarioEntity result = usuarioService.updateUsuario(id, valorpropiedad, ingresos, sumadeuda, objective);

        assertNull(result);
    }
    //---------------------------------------------------------------------------//
    @Test
    void testDeleteUsuario(){
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setId(1L);
        usuario.setRut("12.345.678-2");
        usuario.setName("Raul");
        usuario.setAge(30);
        usuario.setWorkage(2);
        usuario.setHouses(2);
        usuario.setValorpropiedad(1000000);
        usuario.setIngresos(500000);
        usuario.setSumadeuda(240000);
        usuario.setObjective("Compra de vivienda");
        usuario.setIndependiente("independiente");
        usuario.setAhorros(null);
        usuario.setCreditos(null);
        usuario.setNotifications(null);

        when(usuarioRepository.findById(1L)).thenReturn(java.util.Optional.of(usuario));

        try {
            usuarioService.deleteUsuario(1L);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //---------------------------------------------------------------------------//
    @Test
    void testDeleteAllUsuarios(){
        usuarioService.deleteAllUsuarios();
    }

    @Test
    void testDeleteAllUsuarios2() {
        // Call the service method
        usuarioService.deleteAllUsuarios();

        // Verify that the repository method was called
        verify(usuarioRepository, times(1)).deleteAll();
    }
    //---------------------------------------------------------------------------//
    @Test
    void testDeleteUsuarioWithException() {
        Long id = 1L;

        // Mock the repository behavior to throw an exception
        doThrow(new RuntimeException("Exception")).when(usuarioRepository).deleteById(id);

        // Verify that the exception is thrown
        Exception exception = assertThrows(Exception.class, () -> {
            usuarioService.deleteUsuario(id);
        });

        assertThat(exception.getMessage()).isEqualTo("NO SE PUDO ELIMINAR EL CLIENTE CON id: " + id);
    }
    //---------------------------------------------------------------------------//
    @Test
    void testGetUsuarioById(){
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setId(1L);
        usuario.setRut("12.345.678-2");
        usuario.setName("Raul");
        usuario.setAge(30);
        usuario.setWorkage(2);
        usuario.setHouses(2);
        usuario.setValorpropiedad(1000000);
        usuario.setIngresos(500000);
        usuario.setSumadeuda(240000);
        usuario.setObjective("Compra de vivienda");
        usuario.setIndependiente("independiente");
        usuario.setAhorros(null);
        usuario.setCreditos(null);
        usuario.setNotifications(null);

        when(usuarioRepository.findById(1L)).thenReturn(java.util.Optional.of(usuario));

        UsuarioEntity result = usuarioService.getUsuarioById(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getRut()).isEqualTo("12345678-2");
    }
    //---------------------------------------------------------------------------//
    @Test
    void testGetUsuarioByRut() {
        // Crear usuarios de prueba
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
        usuario2.setRut("12.345.678-2");
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

        // Simular el comportamiento del repositorio
        when(usuarioRepository.findByRut("12.345.678-2")).thenReturn(usuarios);

        // Llamar al método del servicio
        List<UsuarioEntity> result = usuarioService.getUsuarioByRut("12.345.678-2");

        // Verificar los resultados
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getId()).isEqualTo(1L);
        assertThat(result.get(0).getRut()).isEqualTo("12345678-2");
        assertThat(result.get(1).getId()).isEqualTo(2L);
        assertThat(result.get(1).getRut()).isEqualTo("12345678-2");
    }
    //---------------------------------------------------------------------------//
    @Test
    void testGetUsuarioByObjective() {
        // Crear usuarios de prueba
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

        // Simular el comportamiento del repositorio
        when(usuarioRepository.findByObjective("Compra de vivienda")).thenReturn(usuarios);

        // Llamar al método del servicio
        List<UsuarioEntity> result = usuarioService.getUsuarioByObjective("Compra de vivienda");

        // Verificar los resultados
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getId()).isEqualTo(1L);
        assertThat(result.get(0).getRut()).isEqualTo("12345678-2");
        assertThat(result.get(1).getId()).isEqualTo(2L);
        assertThat(result.get(1).getRut()).isEqualTo("12345678-3");
    }
    //---------------------------------------------------------------------------//
    @Test
    void testGetAllUsuarios(){
        Object result = usuarioService.getAllUsuarios();
        assertThat(result).isEqualTo(null);
    }

    @Test
    void testGetAllUsuarios2() {
        // Call the service method
        Object result = usuarioService.getAllUsuarios();

        // Verify the results
        assertThat(result).isNull();
    }

    @Test
    void testGetAllUsuarios3() {
        // Call the service method
        usuarioService.deleteAllUsuarios();

        // Verify that the repository method was called
        verify(usuarioRepository, times(1)).deleteAll();
    }
    //---------------------------------------------------------------------------//
    @Test
    void testObtenerValorPositivoMasPequeno() {
        // Set up test data
        AhorrosEntity ahorro1 = new AhorrosEntity();
        ahorro1.setTransaccion(100);
        AhorrosEntity ahorro2 = new AhorrosEntity();
        ahorro2.setTransaccion(50);
        AhorrosEntity ahorro3 = new AhorrosEntity();
        ahorro3.setTransaccion(200);
        AhorrosEntity ahorro4 = new AhorrosEntity();
        ahorro4.setTransaccion(-10); // Negative value should be ignored
        AhorrosEntity ahorro5 = new AhorrosEntity();
        ahorro5.setTransaccion(0); // Zero value should be ignored

        List<AhorrosEntity> ahorros = new ArrayList<>();
        ahorros.add(ahorro1);
        ahorros.add(ahorro2);
        ahorros.add(ahorro3);
        ahorros.add(ahorro4);
        ahorros.add(ahorro5);

        // Call the service method
        int result = usuarioService.obtenerValorPositivoMasPequeno(ahorros);

        // Verify the results
        Assertions.assertThat(result).isEqualTo(50);
    }

    @Test
    void testObtenerValorPositivoMasPequenoSinValoresPositivos() {
        // Set up test data
        AhorrosEntity ahorro1 = new AhorrosEntity();
        ahorro1.setTransaccion(-100);
        AhorrosEntity ahorro2 = new AhorrosEntity();
        ahorro2.setTransaccion(0);

        List<AhorrosEntity> ahorros = new ArrayList<>();
        ahorros.add(ahorro1);
        ahorros.add(ahorro2);

        // Call the service method
        int result = usuarioService.obtenerValorPositivoMasPequeno(ahorros);

        // Verify the results
        Assertions.assertThat(result).isEqualTo(-1);
    }
    //---------------------------------------------------------------------------//
    @Test
    void testGetUsuarioByIdNotFound() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            usuarioService.getUsuarioById(1L);
        });

        assertThat(exception.getMessage()).isEqualTo("No value present");
    }

    @Test
    void testGetUsuarioByIdNotFound2() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            usuarioService.getUsuarioById(1L);
        });

        assertThat(exception.getMessage()).isEqualTo("No value present");
    }
    //---------------------------------------------------------------------------//
}
