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

public class SistemaServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private CreditoRepository creditoRepository;
    @Mock
    private AhorrosRepository ahorrosRepository;
    @Mock
    private UsuarioService usuarioService;
    @InjectMocks
    private SistemaService sistemaService; // EL QUE VOY A EVALUAR

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    //---------------------------------------------------------------------------//
    @Test
    void testCredito_Hipotecario() {
        double result = sistemaService.Credito_Hipotecario("12.345.678-2", 100000, 0.04, 20, 150000);
        assertThat(result).isGreaterThan(0);
    }
    @Test
    public void testCreditoHipotecarioPrimeraVivienda() {
        SistemaService sistemaService = new SistemaService();
        double result = sistemaService.Credito_Hipotecario("12345678-9", 160000, 0.04, 30, 200000);
        assertEquals(763.8644727447262, result, 0.01);
    }

    @Test
    public void testCreditoHipotecarioSegundaVivienda() {
        SistemaService sistemaService = new SistemaService();
        double result = sistemaService.Credito_Hipotecario("12345678-9", 100000, 0.06, 20, 150000);
        assertEquals(716.4310584781729, result, 0.01);
    }

    @Test
    public void testCreditoHipotecarioPropiedadesComerciales() {
        SistemaService sistemaService = new SistemaService();
        double result = sistemaService.Credito_Hipotecario("12345678-9", 120000, 0.06, 25, 200000);
        assertEquals(773.1616817826173, result, 0.01);
    }

    @Test
    public void testCreditoHipotecarioRemodelacion() {
        SistemaService sistemaService = new SistemaService();
        double result = sistemaService.Credito_Hipotecario("12345678-9", 100000, 0.06, 15, 200000);
        assertEquals(843.8568280484624, result, 0.01);
    }

    @Test
    public void testCreditoHipotecarioRemodelacion2() {
        SistemaService sistemaService = new SistemaService();

        String rut = "12345678-9";
        double P = 50000; // Monto del préstamo
        double r = 0.05; // Tasa de interés anual
        double n = 10; // Plazo del préstamo en años
        double V = 100000; // Valor actual de la propiedad

        double expectedMonthlyPayment = P * (r / 12 * Math.pow(1 + r / 12, n * 12)) / (Math.pow(1 + r / 12, n * 12) - 1);

        double result = sistemaService.Credito_Hipotecario(rut, P, r, n, V);

        assertEquals(expectedMonthlyPayment, result, 0.01);
    }

    @Test
    public void testCreditoHipotecarioInvalid() {
        SistemaService sistemaService = new SistemaService();
        double result = sistemaService.Credito_Hipotecario("12345678-9", 300000, 0.07, 40, 200000);
        assertEquals(0, result, 0.01);
    }

    //---------------------------------------------------------------------------//
    //---------------------------------------------------------------------------//
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
    public void testRegisterUsuario2() {
        // Arrange
        List<AhorrosEntity> ahorros = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            AhorrosEntity ahorro = new AhorrosEntity();
            ahorro.setTransaccion(50000 * i);
            ahorro.setTipo("DEPOSITO");
            ahorros.add(ahorro);
        }

        List<CreditoEntity> creditos = new ArrayList<>();

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
    public void testRegisterUsuario3() {
        // Arrange
        List<AhorrosEntity> ahorros = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            AhorrosEntity ahorro = new AhorrosEntity();
            ahorro.setTransaccion(50000 * i);
            ahorro.setTipo("DEPOSITO");
            ahorros.add(ahorro);
        }

        List<CreditoEntity> creditos = null;

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
    public void testRegisterUsuario4() {
        // Arrange
        String rut = "12345678-9";
        String name = "John Doe";
        int age = 30;
        int workage = 5;
        int houses = 0;
        int valorpropiedad = 200000;
        int ingresos = 5000;
        int sumadeuda = 2000;
        String objective = "PRIMERA VIVIENDA";
        String independiente = "ASALARIADO";
        List<AhorrosEntity> ahorros = new ArrayList<>();
        List<CreditoEntity> creditos = new ArrayList<>();

        AhorrosEntity ahorro = new AhorrosEntity();
        ahorro.setTransaccion(20000);
        ahorro.setTipo("DEPOSITO");
        ahorros.add(ahorro);

        CreditoEntity credito = new CreditoEntity();
        credito.setMontop(100000);
        credito.setState("APROBADO");
        creditos.add(credito);

        UsuarioEntity expectedUsuario = new UsuarioEntity();
        expectedUsuario.setRut(rut);
        expectedUsuario.setName(name);
        expectedUsuario.setAge(age);
        expectedUsuario.setWorkage(workage);
        expectedUsuario.setHouses(houses);
        expectedUsuario.setValorpropiedad(valorpropiedad);
        expectedUsuario.setIngresos(ingresos);
        expectedUsuario.setSumadeuda(sumadeuda);
        expectedUsuario.setObjective(objective);
        expectedUsuario.setIndependiente(independiente);
        expectedUsuario.setAhorros(ahorros);
        expectedUsuario.setCreditos(creditos);

        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(expectedUsuario);

        // Act
        UsuarioEntity result = sistemaService.registerUsuario(rut, name, age, workage, houses, valorpropiedad, ingresos, sumadeuda, objective, independiente, ahorros, creditos);

        // Assert
        assertNotNull(result);
        assertEquals(rut, result.getRut());
        assertEquals(name, result.getName());
        assertEquals(age, result.getAge());
        assertEquals(workage, result.getWorkage());
        assertEquals(houses, result.getHouses());
        assertEquals(valorpropiedad, result.getValorpropiedad());
        assertEquals(ingresos, result.getIngresos());
        assertEquals(sumadeuda, result.getSumadeuda());
        assertEquals(objective, result.getObjective());
        assertEquals(independiente, result.getIndependiente());
        assertEquals(ahorros, result.getAhorros());
        assertEquals(creditos, result.getCreditos());
        verify(usuarioRepository, times(1)).save(any(UsuarioEntity.class));
    }
    //---------------------------------------------------------------------------//
    //---------------------------------------------------------------------------//
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
    public void testCreateSolicitudWithExistingSolicitud() {
        // Arrange
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setId(userId);

        CreditoEntity existingSolicitud = new CreditoEntity();
        existingSolicitud.setId(1L);
        usuario.setSolicitud(existingSolicitud);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));

        CreditoEntity newSolicitud = new CreditoEntity();
        newSolicitud.setMontop(10000);
        newSolicitud.setPlazo(12);
        newSolicitud.setIntanu(5.0);
        newSolicitud.setIntmen(0.4);
        newSolicitud.setSegudesg(0.1);
        newSolicitud.setSeguince(0.1);
        newSolicitud.setComiad(0.1);
        newSolicitud.setComprobanteIngresos(new byte[]{1, 2, 3});
        newSolicitud.setCertificadoAvaluo(new byte[]{1, 2, 3});
        newSolicitud.setHistorialCrediticio(new byte[]{1, 2, 3});
        newSolicitud.setEscrituraPrimeraVivienda(new byte[]{1, 2, 3});
        newSolicitud.setPlanNegocios(new byte[]{1, 2, 3});
        newSolicitud.setEstadosFinancieros(new byte[]{1, 2, 3});
        newSolicitud.setPresupuestoRemodelacion(new byte[]{1, 2, 3});
        newSolicitud.setDicom(new byte[]{1, 2, 3});
        newSolicitud.setState("PENDIENTE");

        when(creditoRepository.save(any(CreditoEntity.class))).thenReturn(newSolicitud);

        // Act
        CreditoEntity result = sistemaService.createSolicitud(userId, 10000, 12, 5.0, 0.4, 0.1, 0.1, 0.1, new byte[]{1, 2, 3}, new byte[]{1, 2, 3}, new byte[]{1, 2, 3}, new byte[]{1, 2, 3}, new byte[]{1, 2, 3}, new byte[]{1, 2, 3}, new byte[]{1, 2, 3}, new byte[]{1, 2, 3});

        // Assert
        assertNotNull(result);
        assertEquals("PENDIENTE", result.getState());
        verify(creditoRepository, times(1)).delete(existingSolicitud);
        verify(creditoRepository, times(1)).save(newSolicitud);
    }

    @Test
    public void testEvaluateCredito_SolicitudNoEncontrada() {
        // Configurar mocks y datos de prueba
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setId(userId);
        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));

        // Llamar al método a probar
        Map<String, Object> response = sistemaService.evaluateCredito(userId);

        // Verificar resultados
        assertNull(response);
        verify(usuarioRepository, times(1)).save(any(UsuarioEntity.class));
    }
    //---------------------------------------------------------------------------//
    //---------------------------------------------------------------------------//
    @Test
    public void testEvaluateCreditoPrimeraVivienda() {
        // Arrange
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        // Arrange
        List<AhorrosEntity> ahorros = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            AhorrosEntity ahorro = new AhorrosEntity();
            ahorro.setTransaccion(500000 * i);
            ahorro.setTipo("DEPOSITO");
            ahorros.add(ahorro);
        }
        List<CreditoEntity> creditos = null;

        usuario.setRut("12345678-9");
        usuario.setName("John Doe");
        usuario.setAge(30);
        usuario.setWorkage(5);
        usuario.setHouses(0);
        usuario.setValorpropiedad(200000);
        usuario.setIngresos(5000);
        usuario.setSumadeuda(2000);
        usuario.setObjective("PRIMERA VIVIENDA");
        usuario.setIndependiente("ASALARIADO");
        usuario.setAhorros(ahorros);
        usuario.setCreditos(creditos);

        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        CreditoEntity newSolicitud = new CreditoEntity();
        newSolicitud.setMontop(150000);
        newSolicitud.setPlazo(20);
        newSolicitud.setIntanu(0.04); // Tasa de interés ajustada
        newSolicitud.setIntmen(0.0033);
        newSolicitud.setSegudesg(200);
        newSolicitud.setSeguince(150);
        newSolicitud.setComiad(50);
        newSolicitud.setComprobanteIngresos(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setCertificadoAvaluo(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setHistorialCrediticio(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEscrituraPrimeraVivienda(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPlanNegocios(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEstadosFinancieros(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPresupuestoRemodelacion(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setDicom(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setState("PENDIENTE");

        usuario.setSolicitud(newSolicitud);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(creditoRepository.save(any(CreditoEntity.class))).thenReturn(newSolicitud);

        // Act
        Map<String, Object> result = sistemaService.evaluateCredito(userId);
        // Assert
        assertNotNull(result);
        assertEquals(150000, usuario.getSolicitud().getMontop(), 0);
        assertEquals(20, usuario.getSolicitud().getPlazo());
        assertEquals(0.04, usuario.getSolicitud().getIntanu(), 0);
        assertEquals(0.0033, usuario.getSolicitud().getIntmen(), 0);
        assertEquals(200, usuario.getSolicitud().getSegudesg(), 0);
        assertEquals(150, usuario.getSolicitud().getSeguince(), 0);
        assertEquals(50, usuario.getSolicitud().getComiad(), 0);
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getComprobanteIngresos());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getCertificadoAvaluo());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getHistorialCrediticio());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getEscrituraPrimeraVivienda());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getPlanNegocios());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getEstadosFinancieros());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getPresupuestoRemodelacion());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getDicom());
    } //ERROR-1

    @Test
    public void testEvaluateCreditoPrimeraVivienda1() {
        // Create a user without a solicitud
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setId(1L);
        usuario.setSolicitud(null); // Ensure no solicitud is set

        // Mock the repository to return the user
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        // Call the method under test
        Map<String, Object> result = sistemaService.evaluateCredito(1L);

        // Assert that the result is null
        assertNull(result);
    }// SIN SOLICITUD

    @Test
    public void testEvaluateCreditoPrimeraVivienda11() {
        // Configurar el entorno de prueba
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setId(userId);
        usuario.setIngresos(5000);
        usuario.setWorkage(3);
        usuario.setIndependiente("ASALARIADO");
        usuario.setSumadeuda(2000);
        usuario.setValorpropiedad(100000);
        usuario.setHouses(0);
        usuario.setObjective("PRIMERA VIVIENDA");
        usuario.setAge(30);

        CreditoEntity solicitud = new CreditoEntity();
        solicitud.setMontop(80000.0);
        solicitud.setPlazo(20);
        solicitud.setIntanu(0.04);
        solicitud.setIntmen(0.0033);
        solicitud.setSegudesg(0.001);
        solicitud.setSeguince(0.002);
        solicitud.setComiad(0.001);
        solicitud.setState("APROBADO");

        usuario.setSolicitud(solicitud);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));

        // Llamar al método evaluateCredito
        Map<String, Object> result = sistemaService.evaluateCredito(userId);

        // Verificar que el resultado sea null
        assertNull(result);
    } // SOLICITUD NO PENDIENTE

    @Test
    public void testEvaluateCreditoPrimeraVivienda111() {
        // Arrange
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        // Arrange
        List<AhorrosEntity> ahorros = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            AhorrosEntity ahorro = new AhorrosEntity();
            ahorro.setTransaccion(500000 * i);
            ahorro.setTipo("DEPOSITO");
            ahorros.add(ahorro);
        }
        List<CreditoEntity> creditos = null;

        usuario.setRut("12345678-9");
        usuario.setName("John Doe");
        usuario.setAge(30);
        usuario.setWorkage(5);
        usuario.setHouses(0);
        usuario.setValorpropiedad(200000);
        usuario.setIngresos(5000);
        usuario.setSumadeuda(2000);
        usuario.setObjective("PRIMERA VIVIENDA");
        usuario.setIndependiente("ASALARIADO");
        usuario.setAhorros(ahorros);
        usuario.setCreditos(creditos);

        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        CreditoEntity newSolicitud = new CreditoEntity();
        newSolicitud.setMontop(150000);
        newSolicitud.setPlazo(20);
        newSolicitud.setIntanu(2.04); // Tasa de interés ajustada
        newSolicitud.setIntmen(0.0033);
        newSolicitud.setSegudesg(200);
        newSolicitud.setSeguince(150);
        newSolicitud.setComiad(50);
        newSolicitud.setComprobanteIngresos(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setCertificadoAvaluo(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setHistorialCrediticio(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEscrituraPrimeraVivienda(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPlanNegocios(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEstadosFinancieros(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPresupuestoRemodelacion(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setDicom(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setState("PENDIENTE");

        usuario.setSolicitud(newSolicitud);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(creditoRepository.save(any(CreditoEntity.class))).thenReturn(newSolicitud);

        // Act
        Map<String, Object> result = sistemaService.evaluateCredito(userId);

        // Assert
        assertNull(result);
    } // TASA DE INTERÉS MENSUAL ES INCORRECTA

    @Test
    public void testEvaluateCreditoPrimeraVivienda1111() {
        // Arrange
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        // Arrange
        List<AhorrosEntity> ahorros = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            AhorrosEntity ahorro = new AhorrosEntity();
            ahorro.setTransaccion(500000 * i);
            ahorro.setTipo("DEPOSITO");
            ahorros.add(ahorro);
        }
        List<CreditoEntity> creditos = null;

        usuario.setRut("12345678-9");
        usuario.setName("John Doe");
        usuario.setAge(30);
        usuario.setWorkage(5);
        usuario.setHouses(0);
        usuario.setValorpropiedad(200000);
        usuario.setIngresos(5000);
        usuario.setSumadeuda(2000);
        usuario.setObjective("PRIMERA VIVIENDA");
        usuario.setIndependiente("ASALARIADO");
        usuario.setAhorros(ahorros);
        usuario.setCreditos(creditos);

        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        CreditoEntity newSolicitud = new CreditoEntity();
        newSolicitud.setMontop(1500000000);
        newSolicitud.setPlazo(20);
        newSolicitud.setIntanu(0.04); // Tasa de interés ajustada
        newSolicitud.setIntmen(0.0033);
        newSolicitud.setSegudesg(200);
        newSolicitud.setSeguince(150);
        newSolicitud.setComiad(50);
        newSolicitud.setComprobanteIngresos(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setCertificadoAvaluo(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setHistorialCrediticio(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEscrituraPrimeraVivienda(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPlanNegocios(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEstadosFinancieros(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPresupuestoRemodelacion(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setDicom(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setState("PENDIENTE");

        usuario.setSolicitud(newSolicitud);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(creditoRepository.save(any(CreditoEntity.class))).thenReturn(newSolicitud);

        // Act
        Map<String, Object> result = sistemaService.evaluateCredito(userId);
        // Assert
        assertNull(result);
    } // RELACIÓN CUOTA/INGRESO RECHAZADA:

    @Test
    public void testEvaluateCreditoPrimeraVivienda11111() {
        // Arrange
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        // Arrange
        List<AhorrosEntity> ahorros = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            AhorrosEntity ahorro = new AhorrosEntity();
            ahorro.setTransaccion(500000 * i);
            ahorro.setTipo("DEPOSITO");
            ahorros.add(ahorro);
        }
        List<CreditoEntity> creditos = null;

        usuario.setRut("12345678-9");
        usuario.setName("John Doe");
        usuario.setAge(30);
        usuario.setWorkage(5);
        usuario.setHouses(0);
        usuario.setValorpropiedad(200000);
        usuario.setIngresos(5000);
        usuario.setSumadeuda(2000);
        usuario.setObjective("PRIMERA VIVIENDA");
        usuario.setIndependiente("ASALARIADO");
        usuario.setAhorros(ahorros);
        usuario.setCreditos(creditos);

        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        CreditoEntity newSolicitud = new CreditoEntity();
        newSolicitud.setMontop(150000);
        newSolicitud.setPlazo(20);
        newSolicitud.setIntanu(0.04); // Tasa de interés ajustada
        newSolicitud.setIntmen(0.0033);
        newSolicitud.setSegudesg(200);
        newSolicitud.setSeguince(150);
        newSolicitud.setComiad(50);
        newSolicitud.setComprobanteIngresos(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setCertificadoAvaluo(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setHistorialCrediticio(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEscrituraPrimeraVivienda(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPlanNegocios(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEstadosFinancieros(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPresupuestoRemodelacion(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setDicom(new byte[]{2,3,4});
        newSolicitud.setState("PENDIENTE");

        usuario.setSolicitud(newSolicitud);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(creditoRepository.save(any(CreditoEntity.class))).thenReturn(newSolicitud);

        // Act
        Map<String, Object> result = sistemaService.evaluateCredito(userId);
        // Assert
        assertNull(result);
    } //DICOM MALO, longitud < 4

    @Test
    public void testEvaluateCreditoPrimeraVivienda11112() {
        // Arrange
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        // Arrange
        List<AhorrosEntity> ahorros = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            AhorrosEntity ahorro = new AhorrosEntity();
            ahorro.setTransaccion(500000 * i);
            ahorro.setTipo("DEPOSITO");
            ahorros.add(ahorro);
        }
        List<CreditoEntity> creditos = null;

        usuario.setRut("12345678-9");
        usuario.setName("John Doe");
        usuario.setAge(30);
        usuario.setWorkage(5);
        usuario.setHouses(0);
        usuario.setValorpropiedad(200000);
        usuario.setIngresos(5000);
        usuario.setSumadeuda(2000);
        usuario.setObjective("PRIMERA VIVIENDA");
        usuario.setIndependiente("ASALARIADO");
        usuario.setAhorros(ahorros);
        usuario.setCreditos(creditos);

        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        CreditoEntity newSolicitud = new CreditoEntity();
        newSolicitud.setMontop(150000);
        newSolicitud.setPlazo(20);
        newSolicitud.setIntanu(0.04); // Tasa de interés ajustada
        newSolicitud.setIntmen(0.0033);
        newSolicitud.setSegudesg(200);
        newSolicitud.setSeguince(150);
        newSolicitud.setComiad(50);
        newSolicitud.setComprobanteIngresos(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setCertificadoAvaluo(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setHistorialCrediticio(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEscrituraPrimeraVivienda(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPlanNegocios(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEstadosFinancieros(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPresupuestoRemodelacion(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setDicom(new byte[]{'%', 'P', 'D',1,2,3,4});
        newSolicitud.setState("PENDIENTE");

        usuario.setSolicitud(newSolicitud);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(creditoRepository.save(any(CreditoEntity.class))).thenReturn(newSolicitud);

        // Act
        Map<String, Object> result = sistemaService.evaluateCredito(userId);
        // Assert
        assertNull(result);
    } //DICOM MALO, [4] no es F

    @Test
    public void testEvaluateCreditoPrimeraVivienda11113() {
        // Arrange
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        // Arrange
        List<AhorrosEntity> ahorros = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            AhorrosEntity ahorro = new AhorrosEntity();
            ahorro.setTransaccion(500000 * i);
            ahorro.setTipo("DEPOSITO");
            ahorros.add(ahorro);
        }
        List<CreditoEntity> creditos = null;

        usuario.setRut("12345678-9");
        usuario.setName("John Doe");
        usuario.setAge(30);
        usuario.setWorkage(5);
        usuario.setHouses(0);
        usuario.setValorpropiedad(200000);
        usuario.setIngresos(5000);
        usuario.setSumadeuda(2000);
        usuario.setObjective("PRIMERA VIVIENDA");
        usuario.setIndependiente("ASALARIADO");
        usuario.setAhorros(ahorros);
        usuario.setCreditos(creditos);

        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        CreditoEntity newSolicitud = new CreditoEntity();
        newSolicitud.setMontop(150000);
        newSolicitud.setPlazo(20);
        newSolicitud.setIntanu(0.04); // Tasa de interés ajustada
        newSolicitud.setIntmen(0.0033);
        newSolicitud.setSegudesg(200);
        newSolicitud.setSeguince(150);
        newSolicitud.setComiad(50);
        newSolicitud.setComprobanteIngresos(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setCertificadoAvaluo(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setHistorialCrediticio(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEscrituraPrimeraVivienda(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPlanNegocios(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEstadosFinancieros(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPresupuestoRemodelacion(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setDicom(new byte[]{'%', 'P', 1 ,'F', 1, 2,3,4});
        newSolicitud.setState("PENDIENTE");

        usuario.setSolicitud(newSolicitud);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(creditoRepository.save(any(CreditoEntity.class))).thenReturn(newSolicitud);

        // Act
        Map<String, Object> result = sistemaService.evaluateCredito(userId);
        // Assert
        assertNull(result);
    } //DICOM MALO, [2] no es D

    @Test
    public void testEvaluateCreditoPrimeraVivienda11114() {
        // Arrange
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        // Arrange
        List<AhorrosEntity> ahorros = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            AhorrosEntity ahorro = new AhorrosEntity();
            ahorro.setTransaccion(500000 * i);
            ahorro.setTipo("DEPOSITO");
            ahorros.add(ahorro);
        }
        List<CreditoEntity> creditos = null;

        usuario.setRut("12345678-9");
        usuario.setName("John Doe");
        usuario.setAge(30);
        usuario.setWorkage(5);
        usuario.setHouses(0);
        usuario.setValorpropiedad(200000);
        usuario.setIngresos(5000);
        usuario.setSumadeuda(2000);
        usuario.setObjective("PRIMERA VIVIENDA");
        usuario.setIndependiente("ASALARIADO");
        usuario.setAhorros(ahorros);
        usuario.setCreditos(creditos);

        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        CreditoEntity newSolicitud = new CreditoEntity();
        newSolicitud.setMontop(150000);
        newSolicitud.setPlazo(20);
        newSolicitud.setIntanu(0.04); // Tasa de interés ajustada
        newSolicitud.setIntmen(0.0033);
        newSolicitud.setSegudesg(200);
        newSolicitud.setSeguince(150);
        newSolicitud.setComiad(50);
        newSolicitud.setComprobanteIngresos(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setCertificadoAvaluo(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setHistorialCrediticio(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEscrituraPrimeraVivienda(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPlanNegocios(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEstadosFinancieros(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPresupuestoRemodelacion(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setDicom(new byte[]{'%', 1, 'D', 'F', 1, 2,3,4});
        newSolicitud.setState("PENDIENTE");

        usuario.setSolicitud(newSolicitud);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(creditoRepository.save(any(CreditoEntity.class))).thenReturn(newSolicitud);

        // Act
        Map<String, Object> result = sistemaService.evaluateCredito(userId);
        // Assert
        assertNull(result);
    } //DICOM MALO, [1] no es P

    @Test
    public void testEvaluateCreditoPrimeraVivienda11115() {
        // Arrange
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        // Arrange
        List<AhorrosEntity> ahorros = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            AhorrosEntity ahorro = new AhorrosEntity();
            ahorro.setTransaccion(500000 * i);
            ahorro.setTipo("DEPOSITO");
            ahorros.add(ahorro);
        }
        List<CreditoEntity> creditos = null;

        usuario.setRut("12345678-9");
        usuario.setName("John Doe");
        usuario.setAge(30);
        usuario.setWorkage(5);
        usuario.setHouses(0);
        usuario.setValorpropiedad(200000);
        usuario.setIngresos(5000);
        usuario.setSumadeuda(2000);
        usuario.setObjective("PRIMERA VIVIENDA");
        usuario.setIndependiente("ASALARIADO");
        usuario.setAhorros(ahorros);
        usuario.setCreditos(creditos);

        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        CreditoEntity newSolicitud = new CreditoEntity();
        newSolicitud.setMontop(150000);
        newSolicitud.setPlazo(20);
        newSolicitud.setIntanu(0.04); // Tasa de interés ajustada
        newSolicitud.setIntmen(0.0033);
        newSolicitud.setSegudesg(200);
        newSolicitud.setSeguince(150);
        newSolicitud.setComiad(50);
        newSolicitud.setComprobanteIngresos(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setCertificadoAvaluo(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setHistorialCrediticio(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEscrituraPrimeraVivienda(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPlanNegocios(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEstadosFinancieros(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPresupuestoRemodelacion(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setDicom(new byte[]{1, 'P', 'D', 'F', 1, 2,3,4});
        newSolicitud.setState("PENDIENTE");

        usuario.setSolicitud(newSolicitud);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(creditoRepository.save(any(CreditoEntity.class))).thenReturn(newSolicitud);

        // Act
        Map<String, Object> result = sistemaService.evaluateCredito(userId);
        // Assert
        assertNull(result);
    } //DICOM MALO, [0] no es %

    @Test
    public void testEvaluateCreditoPrimeraVivienda111111() {
        // Arrange
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        // Arrange
        List<AhorrosEntity> ahorros = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            AhorrosEntity ahorro = new AhorrosEntity();
            ahorro.setTransaccion(500000 * i);
            ahorro.setTipo("DEPOSITO");
            ahorros.add(ahorro);
        }
        List<CreditoEntity> creditos = null;

        usuario.setRut("12345678-9");
        usuario.setName("John Doe");
        usuario.setAge(30);
        usuario.setWorkage(5);
        usuario.setHouses(0);
        usuario.setValorpropiedad(200000);
        usuario.setIngresos(5000);
        usuario.setSumadeuda(2000);
        usuario.setObjective("PRIMERA VIVIENDA");
        usuario.setIndependiente("ASALARIADO");
        usuario.setAhorros(ahorros);
        usuario.setCreditos(creditos);

        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        CreditoEntity newSolicitud = new CreditoEntity();
        newSolicitud.setMontop(150000);
        newSolicitud.setPlazo(20);
        newSolicitud.setIntanu(0.04); // Tasa de interés ajustada
        newSolicitud.setIntmen(0.0033);
        newSolicitud.setSegudesg(200);
        newSolicitud.setSeguince(150);
        newSolicitud.setComiad(50);
        newSolicitud.setComprobanteIngresos(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setCertificadoAvaluo(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setHistorialCrediticio(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEscrituraPrimeraVivienda(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPlanNegocios(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEstadosFinancieros(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPresupuestoRemodelacion(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setDicom(null);
        newSolicitud.setState("PENDIENTE");

        usuario.setSolicitud(newSolicitud);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(creditoRepository.save(any(CreditoEntity.class))).thenReturn(newSolicitud);

        // Act
        Map<String, Object> result = sistemaService.evaluateCredito(userId);
        // Assert
        assertNull(result);
    } //DICOM NO INGRESADO

    @Test
    public void testEvaluateCreditoPrimeraVivienda1111111() {
        // Arrange
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        // Arrange
        List<AhorrosEntity> ahorros = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            AhorrosEntity ahorro = new AhorrosEntity();
            ahorro.setTransaccion(500000 * i);
            ahorro.setTipo("DEPOSITO");
            ahorros.add(ahorro);
        }
        List<CreditoEntity> creditos = null;

        usuario.setRut("12345678-9");
        usuario.setName("John Doe");
        usuario.setAge(30);
        usuario.setWorkage(0);
        usuario.setHouses(0);
        usuario.setValorpropiedad(200000);
        usuario.setIngresos(5000);
        usuario.setSumadeuda(2000);
        usuario.setObjective("PRIMERA VIVIENDA");
        usuario.setIndependiente("ASALARIADO");
        usuario.setAhorros(ahorros);
        usuario.setCreditos(creditos);

        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        CreditoEntity newSolicitud = new CreditoEntity();
        newSolicitud.setMontop(150000);
        newSolicitud.setPlazo(20);
        newSolicitud.setIntanu(0.04); // Tasa de interés ajustada
        newSolicitud.setIntmen(0.0033);
        newSolicitud.setSegudesg(200);
        newSolicitud.setSeguince(150);
        newSolicitud.setComiad(50);
        newSolicitud.setComprobanteIngresos(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setCertificadoAvaluo(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setHistorialCrediticio(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEscrituraPrimeraVivienda(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPlanNegocios(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEstadosFinancieros(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPresupuestoRemodelacion(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setDicom(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setState("PENDIENTE");

        usuario.setSolicitud(newSolicitud);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(creditoRepository.save(any(CreditoEntity.class))).thenReturn(newSolicitud);

        // Act
        Map<String, Object> result = sistemaService.evaluateCredito(userId);
        // Assert
        assertNull(result);
    } // ASALARIADO CON MENOS DE 1 AÑO

    @Test
    public void testEvaluateCreditoPrimeraVivienda11111111() {
        // Arrange
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        // Arrange
        List<AhorrosEntity> ahorros = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            AhorrosEntity ahorro = new AhorrosEntity();
            ahorro.setTransaccion(500000 * i);
            ahorro.setTipo("DEPOSITO");
            ahorros.add(ahorro);
        }
        List<CreditoEntity> creditos = null;

        usuario.setRut("12345678-9");
        usuario.setName("John Doe");
        usuario.setAge(30);
        usuario.setWorkage(5);
        usuario.setHouses(0);
        usuario.setValorpropiedad(200000);
        usuario.setIngresos(5000);
        usuario.setSumadeuda(2000);
        usuario.setObjective("PRIMERA VIVIENDA");
        usuario.setIndependiente("OTRO");
        usuario.setAhorros(ahorros);
        usuario.setCreditos(creditos);

        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        CreditoEntity newSolicitud = new CreditoEntity();
        newSolicitud.setMontop(150000);
        newSolicitud.setPlazo(20);
        newSolicitud.setIntanu(0.04); // Tasa de interés ajustada
        newSolicitud.setIntmen(0.0033);
        newSolicitud.setSegudesg(200);
        newSolicitud.setSeguince(150);
        newSolicitud.setComiad(50);
        newSolicitud.setComprobanteIngresos(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setCertificadoAvaluo(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setHistorialCrediticio(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEscrituraPrimeraVivienda(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPlanNegocios(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEstadosFinancieros(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPresupuestoRemodelacion(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setDicom(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setState("PENDIENTE");

        usuario.setSolicitud(newSolicitud);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(creditoRepository.save(any(CreditoEntity.class))).thenReturn(newSolicitud);

        // Act
        Map<String, Object> result = sistemaService.evaluateCredito(userId);
        // Assert
        assertNull(result);
    } // NI INDEPENDIENTE NI ASALARIADO

    @Test
    public void testEvaluateCreditoPrimeraVivienda111111111() {
        // Arrange
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        // Arrange
        List<AhorrosEntity> ahorros = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            AhorrosEntity ahorro = new AhorrosEntity();
            ahorro.setTransaccion(500000 * i);
            ahorro.setTipo("DEPOSITO");
            ahorros.add(ahorro);
        }
        List<CreditoEntity> creditos = null;

        usuario.setRut("12345678-9");
        usuario.setName("John Doe");
        usuario.setAge(30);
        usuario.setWorkage(1);
        usuario.setHouses(0);
        usuario.setValorpropiedad(200000);
        usuario.setIngresos(5000);
        usuario.setSumadeuda(2000);
        usuario.setObjective("PRIMERA VIVIENDA");
        usuario.setIndependiente("INDEPENDIENTE");
        usuario.setAhorros(ahorros);
        usuario.setCreditos(creditos);

        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        CreditoEntity newSolicitud = new CreditoEntity();
        newSolicitud.setMontop(150000);
        newSolicitud.setPlazo(20);
        newSolicitud.setIntanu(0.04); // Tasa de interés ajustada
        newSolicitud.setIntmen(0.0033);
        newSolicitud.setSegudesg(200);
        newSolicitud.setSeguince(150);
        newSolicitud.setComiad(50);
        newSolicitud.setComprobanteIngresos(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setCertificadoAvaluo(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setHistorialCrediticio(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEscrituraPrimeraVivienda(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPlanNegocios(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEstadosFinancieros(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPresupuestoRemodelacion(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setDicom(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setState("PENDIENTE");

        usuario.setSolicitud(newSolicitud);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(creditoRepository.save(any(CreditoEntity.class))).thenReturn(newSolicitud);

        // Act
        Map<String, Object> result = sistemaService.evaluateCredito(userId);
        // Assert
        assertNotNull(result);
        assertEquals("INDEPENDIENTE", usuario.getIndependiente());
    } // INDEPENDIENTE CON MENOS DE 1 AÑO
    @Test
    public void testEvaluateCreditoPrimeraVivienda12() {
        // Arrange
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        // Arrange
        List<AhorrosEntity> ahorros = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            AhorrosEntity ahorro = new AhorrosEntity();
            ahorro.setTransaccion(500000 * i);
            ahorro.setTipo("DEPOSITO");
            ahorros.add(ahorro);
        }
        List<CreditoEntity> creditos = null;

        usuario.setRut("12345678-9");
        usuario.setName("John Doe");
        usuario.setAge(30);
        usuario.setWorkage(5);
        usuario.setHouses(0);
        usuario.setValorpropiedad(200000);
        usuario.setIngresos(5000);
        usuario.setSumadeuda(300000);
        usuario.setObjective("PRIMERA VIVIENDA");
        usuario.setIndependiente("ASALARIADO");
        usuario.setAhorros(ahorros);
        usuario.setCreditos(creditos);

        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        CreditoEntity newSolicitud = new CreditoEntity();
        newSolicitud.setMontop(150000);
        newSolicitud.setPlazo(20);
        newSolicitud.setIntanu(0.04); // Tasa de interés ajustada
        newSolicitud.setIntmen(0.0033);
        newSolicitud.setSegudesg(200);
        newSolicitud.setSeguince(150);
        newSolicitud.setComiad(50);
        newSolicitud.setComprobanteIngresos(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setCertificadoAvaluo(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setHistorialCrediticio(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEscrituraPrimeraVivienda(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPlanNegocios(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEstadosFinancieros(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPresupuestoRemodelacion(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setDicom(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setState("PENDIENTE");

        usuario.setSolicitud(newSolicitud);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(creditoRepository.save(any(CreditoEntity.class))).thenReturn(newSolicitud);

        // Act
        Map<String, Object> result = sistemaService.evaluateCredito(userId);
        // Assert
        assertNull(result);
    } //LA SUMA DE DEUDAS NO PUEDE SER MAYOR AL 50% DE LOS INGRESOS

    @Test
    public void testEvaluateCreditoPrimeraVivienda13() {
        // Arrange
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        // Arrange
        List<AhorrosEntity> ahorros = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            AhorrosEntity ahorro = new AhorrosEntity();
            ahorro.setTransaccion(500000 * i);
            ahorro.setTipo("DEPOSITO");
            ahorros.add(ahorro);
        }
        List<CreditoEntity> creditos = null;

        usuario.setRut("12345678-9");
        usuario.setName("John Doe");
        usuario.setAge(30);
        usuario.setWorkage(5);
        usuario.setHouses(0);
        usuario.setValorpropiedad(200000);
        usuario.setIngresos(5000);
        usuario.setSumadeuda(2000);
        usuario.setObjective("PRIMERA VIVIENDA");
        usuario.setIndependiente("ASALARIADO");
        usuario.setAhorros(ahorros);
        usuario.setCreditos(creditos);

        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        CreditoEntity newSolicitud = new CreditoEntity();
        newSolicitud.setMontop(150000);
        newSolicitud.setPlazo(20);
        newSolicitud.setIntanu(0.04); // Tasa de interés ajustada
        newSolicitud.setIntmen(0.0033);
        newSolicitud.setSegudesg(200);
        newSolicitud.setSeguince(150);
        newSolicitud.setComiad(50);
        newSolicitud.setComprobanteIngresos(null);
        newSolicitud.setCertificadoAvaluo(null);
        newSolicitud.setHistorialCrediticio(null);
        newSolicitud.setEscrituraPrimeraVivienda(null);
        newSolicitud.setPlanNegocios(null);
        newSolicitud.setEstadosFinancieros(null);
        newSolicitud.setPresupuestoRemodelacion(null);
        newSolicitud.setDicom(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setState("PENDIENTE");

        usuario.setSolicitud(newSolicitud);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(creditoRepository.save(any(CreditoEntity.class))).thenReturn(newSolicitud);

        // Act
        Map<String, Object> result = sistemaService.evaluateCredito(userId);
        // Assert
        assertNull(result);
    } // SIN ARCHIVOS

    @Test
    public void testEvaluateCreditoPrimeraVivienda14() {
        // Arrange
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        // Arrange
        List<AhorrosEntity> ahorros = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            AhorrosEntity ahorro = new AhorrosEntity();
            ahorro.setTransaccion(500000 * i);
            ahorro.setTipo("DEPOSITO");
            ahorros.add(ahorro);
        }
        List<CreditoEntity> creditos = null;

        usuario.setRut("12345678-9");
        usuario.setName("John Doe");
        usuario.setAge(75);
        usuario.setWorkage(5);
        usuario.setHouses(0);
        usuario.setValorpropiedad(200000);
        usuario.setIngresos(5000);
        usuario.setSumadeuda(2000);
        usuario.setObjective("PRIMERA VIVIENDA");
        usuario.setIndependiente("ASALARIADO");
        usuario.setAhorros(ahorros);
        usuario.setCreditos(creditos);

        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        CreditoEntity newSolicitud = new CreditoEntity();
        newSolicitud.setMontop(150000);
        newSolicitud.setPlazo(20);
        newSolicitud.setIntanu(0.04); // Tasa de interés ajustada
        newSolicitud.setIntmen(0.0033);
        newSolicitud.setSegudesg(200);
        newSolicitud.setSeguince(150);
        newSolicitud.setComiad(50);
        newSolicitud.setComprobanteIngresos(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setCertificadoAvaluo(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setHistorialCrediticio(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEscrituraPrimeraVivienda(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPlanNegocios(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEstadosFinancieros(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPresupuestoRemodelacion(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setDicom(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setState("PENDIENTE");

        usuario.setSolicitud(newSolicitud);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(creditoRepository.save(any(CreditoEntity.class))).thenReturn(newSolicitud);

        // Act
        Map<String, Object> result = sistemaService.evaluateCredito(userId);
        // Assert
        assertNull(result);
    } // EDAD MAYOR A 75

    @Test
    public void testEvaluateCreditoPrimeraVivienda15() {
        // Arrange
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        // Arrange
        List<AhorrosEntity> ahorros = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            AhorrosEntity ahorro = new AhorrosEntity();
            ahorro.setTransaccion(500000 * i);
            ahorro.setTipo("DEPOSITO");
            ahorros.add(ahorro);
        }
        List<CreditoEntity> creditos = null;

        usuario.setRut("12345678-9");
        usuario.setName("John Doe");
        usuario.setAge(30);
        usuario.setWorkage(5);
        usuario.setHouses(0);
        usuario.setValorpropiedad(200000);
        usuario.setIngresos(5000);
        usuario.setSumadeuda(2000);
        usuario.setObjective("PRIMERA VIVIENDA");
        usuario.setIndependiente("ASALARIADO");
        usuario.setAhorros(ahorros);
        usuario.setCreditos(creditos);

        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        CreditoEntity newSolicitud = new CreditoEntity();
        newSolicitud.setMontop(150000);
        newSolicitud.setPlazo(20);
        newSolicitud.setIntanu(0.04); // Tasa de interés ajustada
        newSolicitud.setIntmen(0.0033);
        newSolicitud.setSegudesg(200);
        newSolicitud.setSeguince(150);
        newSolicitud.setComiad(50);
        newSolicitud.setComprobanteIngresos(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setCertificadoAvaluo(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setHistorialCrediticio(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEscrituraPrimeraVivienda(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPlanNegocios(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEstadosFinancieros(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPresupuestoRemodelacion(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setDicom(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setState("PENDIENTE");

        usuario.setSolicitud(newSolicitud);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(creditoRepository.save(any(CreditoEntity.class))).thenReturn(newSolicitud);

        // Act
        when(usuarioService.obtenerValorPositivoMasPequeno(ahorros)).thenReturn(500000); // SIEMPRE Y CUANDO LA FUNCIÓN RETORNE
        Map<String, Object> result = sistemaService.evaluateCredito(userId);
        // Assert
        assertNotNull(result);
        assertEquals(150000, usuario.getSolicitud().getMontop(), 0);
        assertEquals(20, usuario.getSolicitud().getPlazo());
        assertEquals(0.04, usuario.getSolicitud().getIntanu(), 0);
        assertEquals(0.0033, usuario.getSolicitud().getIntmen(), 0);
        assertEquals(200, usuario.getSolicitud().getSegudesg(), 0);
        assertEquals(150, usuario.getSolicitud().getSeguince(), 0);
        assertEquals(50, usuario.getSolicitud().getComiad(), 0);
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getComprobanteIngresos());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getCertificadoAvaluo());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getHistorialCrediticio());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getEscrituraPrimeraVivienda());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getPlanNegocios());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getEstadosFinancieros());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getPresupuestoRemodelacion());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getDicom());
    } // PASA R71 SOLAMENTE

    @Test
    public void testEvaluateCreditoPrimeraVivienda16() {
        // Arrange
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        // Arrange
        List<AhorrosEntity> ahorros = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            AhorrosEntity ahorro = new AhorrosEntity();
            ahorro.setTransaccion(0 * i);
            ahorro.setTipo("DEPOSITO");
            ahorros.add(ahorro);
        }
        List<CreditoEntity> creditos = null;

        usuario.setRut("12345678-9");
        usuario.setName("John Doe");
        usuario.setAge(30);
        usuario.setWorkage(5);
        usuario.setHouses(0);
        usuario.setValorpropiedad(200000);
        usuario.setIngresos(5000);
        usuario.setSumadeuda(2000);
        usuario.setObjective("PRIMERA VIVIENDA");
        usuario.setIndependiente("ASALARIADO");
        usuario.setAhorros(ahorros);
        usuario.setCreditos(creditos);

        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        CreditoEntity newSolicitud = new CreditoEntity();
        newSolicitud.setMontop(150000);
        newSolicitud.setPlazo(20);
        newSolicitud.setIntanu(0.04); // Tasa de interés ajustada
        newSolicitud.setIntmen(0.0033);
        newSolicitud.setSegudesg(200);
        newSolicitud.setSeguince(150);
        newSolicitud.setComiad(50);
        newSolicitud.setComprobanteIngresos(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setCertificadoAvaluo(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setHistorialCrediticio(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEscrituraPrimeraVivienda(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPlanNegocios(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEstadosFinancieros(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPresupuestoRemodelacion(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setDicom(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setState("PENDIENTE");

        usuario.setSolicitud(newSolicitud);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(creditoRepository.save(any(CreditoEntity.class))).thenReturn(newSolicitud);

        // Act
        when(usuarioService.obtenerValorPositivoMasPequeno(ahorros)).thenReturn(500000); // SIEMPRE Y CUANDO LA FUNCIÓN RETORNE
        Map<String, Object> result = sistemaService.evaluateCredito(userId);
        // Assert
        assertNotNull(result);
        assertEquals(150000, usuario.getSolicitud().getMontop(), 0);
        assertEquals(20, usuario.getSolicitud().getPlazo());
        assertEquals(0.04, usuario.getSolicitud().getIntanu(), 0);
        assertEquals(0.0033, usuario.getSolicitud().getIntmen(), 0);
        assertEquals(200, usuario.getSolicitud().getSegudesg(), 0);
        assertEquals(150, usuario.getSolicitud().getSeguince(), 0);
        assertEquals(50, usuario.getSolicitud().getComiad(), 0);
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getComprobanteIngresos());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getCertificadoAvaluo());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getHistorialCrediticio());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getEscrituraPrimeraVivienda());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getPlanNegocios());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getEstadosFinancieros());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getPresupuestoRemodelacion());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getDicom());
    } // PASA R71 Y R72

    @Test
    public void testEvaluateCreditoPrimeraVivienda17() {
        // Arrange
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        // Arrange
        List<AhorrosEntity> ahorros = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            AhorrosEntity ahorro = new AhorrosEntity();
            ahorro.setTransaccion(500000 * i);
            ahorro.setTipo("DEPOSITO");
            ahorros.add(ahorro);
        }
        // Agregar un nuevo ahorro con otro tipo
        AhorrosEntity nuevoAhorro = new AhorrosEntity();
        nuevoAhorro.setTransaccion(-1000000);
        nuevoAhorro.setTipo("RETIRO");
        ahorros.add(nuevoAhorro);

        List<CreditoEntity> creditos = null;

        usuario.setRut("12345678-9");
        usuario.setName("John Doe");
        usuario.setAge(30);
        usuario.setWorkage(5);
        usuario.setHouses(0);
        usuario.setValorpropiedad(200000);
        usuario.setIngresos(5000);
        usuario.setSumadeuda(2000);
        usuario.setObjective("PRIMERA VIVIENDA");
        usuario.setIndependiente("ASALARIADO");
        usuario.setAhorros(ahorros);
        usuario.setCreditos(creditos);

        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        CreditoEntity newSolicitud = new CreditoEntity();
        newSolicitud.setMontop(150000);
        newSolicitud.setPlazo(20);
        newSolicitud.setIntanu(0.04); // Tasa de interés ajustada
        newSolicitud.setIntmen(0.0033);
        newSolicitud.setSegudesg(200);
        newSolicitud.setSeguince(150);
        newSolicitud.setComiad(50);
        newSolicitud.setComprobanteIngresos(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setCertificadoAvaluo(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setHistorialCrediticio(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEscrituraPrimeraVivienda(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPlanNegocios(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEstadosFinancieros(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPresupuestoRemodelacion(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setDicom(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setState("PENDIENTE");

        usuario.setSolicitud(newSolicitud);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(creditoRepository.save(any(CreditoEntity.class))).thenReturn(newSolicitud);

        // Act
        when(usuarioService.obtenerValorPositivoMasPequeno(ahorros)).thenReturn(500000); // SIEMPRE Y CUANDO LA FUNCIÓN RETORNE
        Map<String, Object> result = sistemaService.evaluateCredito(userId);
        // Assert
        assertNotNull(result);
        assertEquals(150000, usuario.getSolicitud().getMontop(), 0);
        assertEquals(20, usuario.getSolicitud().getPlazo());
        assertEquals(0.04, usuario.getSolicitud().getIntanu(), 0);
        assertEquals(0.0033, usuario.getSolicitud().getIntmen(), 0);
        assertEquals(200, usuario.getSolicitud().getSegudesg(), 0);
        assertEquals(150, usuario.getSolicitud().getSeguince(), 0);
        assertEquals(50, usuario.getSolicitud().getComiad(), 0);
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getComprobanteIngresos());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getCertificadoAvaluo());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getHistorialCrediticio());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getEscrituraPrimeraVivienda());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getPlanNegocios());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getEstadosFinancieros());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getPresupuestoRemodelacion());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getDicom());
    } // ERROR-3

    @Test
    public void testEvaluateCreditoPrimeraVivienda171() {
        // Arrange
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        // Arrange
        List<AhorrosEntity> ahorros = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            AhorrosEntity ahorro = new AhorrosEntity();
            ahorro.setTransaccion(5000 * i);
            ahorro.setTipo("DEPOSITO");
            ahorros.add(ahorro);
        }
        // Agregar un nuevo ahorro con otro tipo
        AhorrosEntity nuevoAhorro = new AhorrosEntity();
        nuevoAhorro.setTransaccion(-1000000);
        nuevoAhorro.setTipo("RETIRO");
        ahorros.add(nuevoAhorro);

        List<CreditoEntity> creditos = null;

        usuario.setRut("12345678-9");
        usuario.setName("John Doe");
        usuario.setAge(30);
        usuario.setWorkage(5);
        usuario.setHouses(0);
        usuario.setValorpropiedad(200000);
        usuario.setIngresos(5000);
        usuario.setSumadeuda(2000);
        usuario.setObjective("PRIMERA VIVIENDA");
        usuario.setIndependiente("ASALARIADO");
        usuario.setAhorros(ahorros);
        usuario.setCreditos(creditos);

        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        CreditoEntity newSolicitud = new CreditoEntity();
        newSolicitud.setMontop(150000);
        newSolicitud.setPlazo(20);
        newSolicitud.setIntanu(0.04); // Tasa de interés ajustada
        newSolicitud.setIntmen(0.0033);
        newSolicitud.setSegudesg(200);
        newSolicitud.setSeguince(150);
        newSolicitud.setComiad(50);
        newSolicitud.setComprobanteIngresos(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setCertificadoAvaluo(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setHistorialCrediticio(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEscrituraPrimeraVivienda(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPlanNegocios(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEstadosFinancieros(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPresupuestoRemodelacion(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setDicom(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setState("PENDIENTE");

        usuario.setSolicitud(newSolicitud);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(creditoRepository.save(any(CreditoEntity.class))).thenReturn(newSolicitud);

        // Act
        when(usuarioService.obtenerValorPositivoMasPequeno(ahorros)).thenReturn(500000); // SIEMPRE Y CUANDO LA FUNCIÓN RETORNE
        Map<String, Object> result = sistemaService.evaluateCredito(userId);
        // Assert
        assertNotNull(result);
        assertEquals(150000, usuario.getSolicitud().getMontop(), 0);
        assertEquals(20, usuario.getSolicitud().getPlazo());
        assertEquals(0.04, usuario.getSolicitud().getIntanu(), 0);
        assertEquals(0.0033, usuario.getSolicitud().getIntmen(), 0);
        assertEquals(200, usuario.getSolicitud().getSegudesg(), 0);
        assertEquals(150, usuario.getSolicitud().getSeguince(), 0);
        assertEquals(50, usuario.getSolicitud().getComiad(), 0);
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getComprobanteIngresos());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getCertificadoAvaluo());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getHistorialCrediticio());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getEscrituraPrimeraVivienda());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getPlanNegocios());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getEstadosFinancieros());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getPresupuestoRemodelacion());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getDicom());
        assertEquals("RECHAZADA", newSolicitud.getState());
    } // ERROR-2,3,4,5

    @Test
    public void testEvaluateCreditoPrimeraVivienda18() {
        // Arrange
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        // Arrange
        List<AhorrosEntity> ahorros = new ArrayList<>();
        for (int i = 1; i <= 24; i++) {
            AhorrosEntity ahorro = new AhorrosEntity();
            ahorro.setTransaccion(5 * i);
            ahorro.setTipo("DEPOSITO");
            ahorros.add(ahorro);
        }
        List<CreditoEntity> creditos = null;

        usuario.setRut("12345678-9");
        usuario.setName("John Doe");
        usuario.setAge(30);
        usuario.setWorkage(5);
        usuario.setHouses(0);
        usuario.setValorpropiedad(200000);
        usuario.setIngresos(5000);
        usuario.setSumadeuda(2000);
        usuario.setObjective("PRIMERA VIVIENDA");
        usuario.setIndependiente("ASALARIADO");
        usuario.setAhorros(ahorros);
        usuario.setCreditos(creditos);

        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        CreditoEntity newSolicitud = new CreditoEntity();
        newSolicitud.setMontop(150000);
        newSolicitud.setPlazo(20);
        newSolicitud.setIntanu(0.04); // Tasa de interés ajustada
        newSolicitud.setIntmen(0.0033);
        newSolicitud.setSegudesg(200);
        newSolicitud.setSeguince(150);
        newSolicitud.setComiad(50);
        newSolicitud.setComprobanteIngresos(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setCertificadoAvaluo(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setHistorialCrediticio(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEscrituraPrimeraVivienda(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPlanNegocios(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEstadosFinancieros(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPresupuestoRemodelacion(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setDicom(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setState("PENDIENTE");

        usuario.setSolicitud(newSolicitud);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(creditoRepository.save(any(CreditoEntity.class))).thenReturn(newSolicitud);

        // Act
        when(usuarioService.obtenerValorPositivoMasPequeno(ahorros)).thenReturn(500000); // SIEMPRE Y CUANDO LA FUNCIÓN RETORNE
        Map<String, Object> result = sistemaService.evaluateCredito(userId);
        // Assert
        assertNotNull(result);
        assertEquals(150000, usuario.getSolicitud().getMontop(), 0);
        assertEquals(20, usuario.getSolicitud().getPlazo());
        assertEquals(0.04, usuario.getSolicitud().getIntanu(), 0);
        assertEquals(0.0033, usuario.getSolicitud().getIntmen(), 0);
        assertEquals(200, usuario.getSolicitud().getSegudesg(), 0);
        assertEquals(150, usuario.getSolicitud().getSeguince(), 0);
        assertEquals(50, usuario.getSolicitud().getComiad(), 0);
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getComprobanteIngresos());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getCertificadoAvaluo());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getHistorialCrediticio());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getEscrituraPrimeraVivienda());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getPlanNegocios());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getEstadosFinancieros());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getPresupuestoRemodelacion());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getDicom());
    } // CASO 2 DE ERROR ANTES 4

    @Test
    public void testEvaluateCreditoPrimeraVivienda19() {
        // Arrange
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        // Arrange
        List<AhorrosEntity> ahorros = new ArrayList<>();
        for (int i = 1; i <= 24; i++) {
            AhorrosEntity ahorro = new AhorrosEntity();
            ahorro.setTransaccion(5 * i);
            ahorro.setTipo("DEPOSITO");
            ahorros.add(ahorro);
        }
        List<CreditoEntity> creditos = null;

        usuario.setRut("12345678-9");
        usuario.setName("John Doe");
        usuario.setAge(30);
        usuario.setWorkage(5);
        usuario.setHouses(0);
        usuario.setValorpropiedad(200000);
        usuario.setIngresos(5000);
        usuario.setSumadeuda(2000);
        usuario.setObjective("PRIMERA VIVIENDA");
        usuario.setIndependiente("ASALARIADO");
        usuario.setAhorros(ahorros);
        usuario.setCreditos(creditos);

        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        CreditoEntity newSolicitud = new CreditoEntity();
        newSolicitud.setMontop(150000);
        newSolicitud.setPlazo(20);
        newSolicitud.setIntanu(0.04); // Tasa de interés ajustada
        newSolicitud.setIntmen(0.0033);
        newSolicitud.setSegudesg(200);
        newSolicitud.setSeguince(150);
        newSolicitud.setComiad(50);
        newSolicitud.setComprobanteIngresos(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setCertificadoAvaluo(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setHistorialCrediticio(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEscrituraPrimeraVivienda(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPlanNegocios(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEstadosFinancieros(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPresupuestoRemodelacion(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setDicom(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setState("PENDIENTE");

        usuario.setSolicitud(newSolicitud);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(creditoRepository.save(any(CreditoEntity.class))).thenReturn(newSolicitud);

        // Act
        when(usuarioService.obtenerValorPositivoMasPequeno(ahorros)).thenReturn(500000); // SIEMPRE Y CUANDO LA FUNCIÓN RETORNE
        Map<String, Object> result = sistemaService.evaluateCredito(userId);
        // Assert
        assertNotNull(result);
        assertEquals(150000, usuario.getSolicitud().getMontop(), 0);
        assertEquals(20, usuario.getSolicitud().getPlazo());
        assertEquals(0.04, usuario.getSolicitud().getIntanu(), 0);
        assertEquals(0.0033, usuario.getSolicitud().getIntmen(), 0);
        assertEquals(200, usuario.getSolicitud().getSegudesg(), 0);
        assertEquals(150, usuario.getSolicitud().getSeguince(), 0);
        assertEquals(50, usuario.getSolicitud().getComiad(), 0);
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getComprobanteIngresos());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getCertificadoAvaluo());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getHistorialCrediticio());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getEscrituraPrimeraVivienda());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getPlanNegocios());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getEstadosFinancieros());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getPresupuestoRemodelacion());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getDicom());
    } // ERROR 4
    //---------------------------------------------------------------------------//
    @Test
    public void testEvaluateCreditoPrimeraVivienda2() {
        // Arrange
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        // Arrange
        List<AhorrosEntity> ahorros = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            AhorrosEntity ahorro = new AhorrosEntity();
            ahorro.setTransaccion(500000 * i);
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

        usuario.setRut("12345678-9");
        usuario.setName("John Doe");
        usuario.setAge(30);
        usuario.setWorkage(5);
        usuario.setHouses(0);
        usuario.setValorpropiedad(200000);
        usuario.setIngresos(5000);
        usuario.setSumadeuda(2000);
        usuario.setObjective("PRIMERA VIVIENDA");
        usuario.setIndependiente("ASALARIADO");
        usuario.setAhorros(ahorros);
        usuario.setCreditos(creditos);

        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        CreditoEntity newSolicitud = new CreditoEntity();
        newSolicitud.setMontop(150000);
        newSolicitud.setPlazo(20);
        newSolicitud.setIntanu(0.04); // Tasa de interés ajustada
        newSolicitud.setIntmen(0.0033);
        newSolicitud.setSegudesg(200);
        newSolicitud.setSeguince(150);
        newSolicitud.setComiad(50);
        newSolicitud.setComprobanteIngresos(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setCertificadoAvaluo(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setHistorialCrediticio(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEscrituraPrimeraVivienda(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPlanNegocios(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEstadosFinancieros(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPresupuestoRemodelacion(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setDicom(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setState("PENDIENTE");

        usuario.setSolicitud(newSolicitud);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(creditoRepository.save(any(CreditoEntity.class))).thenReturn(newSolicitud);

        // Act
        Map<String, Object> result = sistemaService.evaluateCredito(userId);
        // Assert
        assertNotNull(result);
        assertEquals(150000, usuario.getSolicitud().getMontop(), 0);
        assertEquals(20, usuario.getSolicitud().getPlazo());
        assertEquals(0.04, usuario.getSolicitud().getIntanu(), 0);
        assertEquals(0.0033, usuario.getSolicitud().getIntmen(), 0);
        assertEquals(200, usuario.getSolicitud().getSegudesg(), 0);
        assertEquals(150, usuario.getSolicitud().getSeguince(), 0);
        assertEquals(50, usuario.getSolicitud().getComiad(), 0);
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getComprobanteIngresos());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getCertificadoAvaluo());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getHistorialCrediticio());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getEscrituraPrimeraVivienda());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getPlanNegocios());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getEstadosFinancieros());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getPresupuestoRemodelacion());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getDicom());
    }

    @Test
    public void testEvaluateCreditoPrimeraVivienda3() {
        // Arrange
        UsuarioEntity usuario = new UsuarioEntity();
        Long userId = 1L;

        // Arrange
        List<AhorrosEntity> ahorros = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            AhorrosEntity ahorro = new AhorrosEntity();
            //ahorro.setId((long) i + 1); // Asignar un ID único a cada ahorro
            ahorro.setTransaccion(20000 * i); // Asegúrate de que estos valores sean positivos y mayores a 15,000
            ahorro.setTipo("DEPOSITO");
            ahorros.add(ahorro);
        }
        List<CreditoEntity> creditos = null;
        usuario.setId(userId);
        usuario.setRut("12345678-9");
        usuario.setName("John Doe");
        usuario.setAge(30);
        usuario.setWorkage(5);
        usuario.setHouses(0);
        usuario.setValorpropiedad(200000);
        usuario.setIngresos(200000);
        usuario.setSumadeuda(2000);
        usuario.setObjective("PRIMERA VIVIENDA");
        usuario.setIndependiente("ASALARIADO");
        usuario.setAhorros(ahorros);
        usuario.setCreditos(creditos);



        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        CreditoEntity newSolicitud = new CreditoEntity();
        newSolicitud.setMontop(150000);
        newSolicitud.setPlazo(20);
        newSolicitud.setIntanu(0.04); // Tasa de interés ajustada
        newSolicitud.setIntmen(0.0033);
        newSolicitud.setSegudesg(200);
        newSolicitud.setSeguince(150);
        newSolicitud.setComiad(50);
        newSolicitud.setComprobanteIngresos(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setCertificadoAvaluo(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setHistorialCrediticio(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEscrituraPrimeraVivienda(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPlanNegocios(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEstadosFinancieros(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPresupuestoRemodelacion(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setDicom(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setState("PENDIENTE");

        usuario.setSolicitud(newSolicitud);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(creditoRepository.save(any(CreditoEntity.class))).thenReturn(newSolicitud);
        // Act
        when(usuarioService.obtenerValorPositivoMasPequeno(ahorros)).thenReturn(20000); // SIEMPRE Y CUANDO LA FUNCIÓN RETORNE
        Map<String, Object> result = sistemaService.evaluateCredito(userId);
        // Assert
        assertNotNull(result);
        assertEquals(150000, usuario.getSolicitud().getMontop(), 0);
        assertEquals(20, usuario.getSolicitud().getPlazo());
        assertEquals(0.04, usuario.getSolicitud().getIntanu(), 0);
        assertEquals(0.0033, usuario.getSolicitud().getIntmen(), 0);
        assertEquals(200, usuario.getSolicitud().getSegudesg(), 0);
        assertEquals(150, usuario.getSolicitud().getSeguince(), 0);
        assertEquals(50, usuario.getSolicitud().getComiad(), 0);
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getComprobanteIngresos());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getCertificadoAvaluo());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getHistorialCrediticio());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getEscrituraPrimeraVivienda());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getPlanNegocios());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getEstadosFinancieros());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getPresupuestoRemodelacion());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getDicom());
        assertEquals("APROBADO", newSolicitud.getState());
    } // APROBADO

    @Test
    public void testEvaluateCreditoPrimeraVivienda4() {
        // Arrange
        UsuarioEntity usuario = new UsuarioEntity();
        Long userId = 1L;

        // Arrange
        List<AhorrosEntity> ahorros = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            AhorrosEntity ahorro = new AhorrosEntity();
            //ahorro.setId((long) i + 1); // Asignar un ID único a cada ahorro
            ahorro.setTransaccion(20000 * i); // Asegúrate de que estos valores sean positivos y mayores a 15,000
            ahorro.setTipo("DEPOSITO");
            ahorros.add(ahorro);
        }
        List<CreditoEntity> creditos = null;
        usuario.setId(userId);
        usuario.setRut("12345678-9");
        usuario.setName("John Doe");
        usuario.setAge(30);
        usuario.setWorkage(5);
        usuario.setHouses(0);
        usuario.setValorpropiedad(200000);
        usuario.setIngresos(200000);
        usuario.setSumadeuda(2000);
        usuario.setObjective("PRIMERA VIVIENDA");
        usuario.setIndependiente("ASALARIADO");
        usuario.setAhorros(ahorros);
        usuario.setCreditos(creditos);



        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        CreditoEntity newSolicitud = new CreditoEntity();
        newSolicitud.setMontop(150000);
        newSolicitud.setPlazo(20);
        newSolicitud.setIntanu(0.04); // Tasa de interés ajustada
        newSolicitud.setIntmen(0.0033);
        newSolicitud.setSegudesg(200);
        newSolicitud.setSeguince(150);
        newSolicitud.setComiad(50);
        newSolicitud.setComprobanteIngresos(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setCertificadoAvaluo(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setHistorialCrediticio(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setDicom(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setState("PENDIENTE");

        usuario.setSolicitud(newSolicitud);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(creditoRepository.save(any(CreditoEntity.class))).thenReturn(newSolicitud);
        // Act
        when(usuarioService.obtenerValorPositivoMasPequeno(ahorros)).thenReturn(20000); // SIEMPRE Y CUANDO LA FUNCIÓN RETORNE
        Map<String, Object> result = sistemaService.evaluateCredito(userId);
        // Assert
        assertNotNull(result);
        assertEquals(150000, usuario.getSolicitud().getMontop(), 0);
        assertEquals(20, usuario.getSolicitud().getPlazo());
        assertEquals(0.04, usuario.getSolicitud().getIntanu(), 0);
        assertEquals(0.0033, usuario.getSolicitud().getIntmen(), 0);
        assertEquals(200, usuario.getSolicitud().getSegudesg(), 0);
        assertEquals(150, usuario.getSolicitud().getSeguince(), 0);
        assertEquals(50, usuario.getSolicitud().getComiad(), 0);
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getComprobanteIngresos());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getCertificadoAvaluo());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getHistorialCrediticio());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getDicom());
        assertEquals("APROBADO", newSolicitud.getState());
    } // APROBADO CON LOS DOCUMENTOS JUSTOS

    @Test
    public void testEvaluateCreditoPrimeraVivienda41() {
        // Arrange
        UsuarioEntity usuario = new UsuarioEntity();
        Long userId = 1L;

        // Arrange
        List<AhorrosEntity> ahorros = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            AhorrosEntity ahorro = new AhorrosEntity();
            //ahorro.setId((long) i + 1); // Asignar un ID único a cada ahorro
            ahorro.setTransaccion(20000 * i); // Asegúrate de que estos valores sean positivos y mayores a 15,000
            ahorro.setTipo("DEPOSITO");
            ahorros.add(ahorro);
        }
        List<CreditoEntity> creditos = null;
        usuario.setId(userId);
        usuario.setRut("12345678-9");
        usuario.setName("John Doe");
        usuario.setAge(30);
        usuario.setWorkage(5);
        usuario.setHouses(0);
        usuario.setValorpropiedad(200000);
        usuario.setIngresos(200000);
        usuario.setSumadeuda(2000);
        usuario.setObjective("PRIMERA VIVIENDA");
        usuario.setIndependiente("ASALARIADO");
        usuario.setAhorros(ahorros);
        usuario.setCreditos(creditos);



        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        CreditoEntity newSolicitud = new CreditoEntity();
        newSolicitud.setMontop(150000);
        newSolicitud.setPlazo(20);
        newSolicitud.setIntanu(0.04); // Tasa de interés ajustada
        newSolicitud.setIntmen(0.0033);
        newSolicitud.setSegudesg(200);
        newSolicitud.setSeguince(150);
        newSolicitud.setComiad(50);
        newSolicitud.setComprobanteIngresos(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setCertificadoAvaluo(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setDicom(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setState("PENDIENTE");

        usuario.setSolicitud(newSolicitud);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(creditoRepository.save(any(CreditoEntity.class))).thenReturn(newSolicitud);

        // Act
        when(usuarioService.obtenerValorPositivoMasPequeno(ahorros)).thenReturn(20000); // SIEMPRE Y CUANDO LA FUNCIÓN RETORNE
        Map<String, Object> result = sistemaService.evaluateCredito(userId);
        // Assert
        assertNull(result);
    } // FALTA DE ARCHIVOS

    @Test
    public void testEvaluateCreditoPrimeraVivienda31() {
        // Arrange
        UsuarioEntity usuario = new UsuarioEntity();
        Long userId = 1L;

        // Arrange
        List<AhorrosEntity> ahorros = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            AhorrosEntity ahorro = new AhorrosEntity();
            //ahorro.setId((long) i + 1); // Asignar un ID único a cada ahorro
            ahorro.setTransaccion(0 * i); // Asegúrate de que estos valores sean positivos y mayores a 15,000
            ahorro.setTipo("DEPOSITO");
            ahorros.add(ahorro);
        }
        List<CreditoEntity> creditos = null;
        usuario.setId(userId);
        usuario.setRut("12345678-9");
        usuario.setName("John Doe");
        usuario.setAge(30);
        usuario.setWorkage(5);
        usuario.setHouses(0);
        usuario.setValorpropiedad(200000);
        usuario.setIngresos(200000);
        usuario.setSumadeuda(2000);
        usuario.setObjective("PRIMERA VIVIENDA");
        usuario.setIndependiente("ASALARIADO");
        usuario.setAhorros(ahorros);
        usuario.setCreditos(creditos);



        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        CreditoEntity newSolicitud = new CreditoEntity();
        newSolicitud.setMontop(150000);
        newSolicitud.setPlazo(20);
        newSolicitud.setIntanu(0.04); // Tasa de interés ajustada
        newSolicitud.setIntmen(0.0033);
        newSolicitud.setSegudesg(200);
        newSolicitud.setSeguince(150);
        newSolicitud.setComiad(50);
        newSolicitud.setComprobanteIngresos(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setCertificadoAvaluo(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setHistorialCrediticio(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEscrituraPrimeraVivienda(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPlanNegocios(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEstadosFinancieros(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPresupuestoRemodelacion(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setDicom(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setState("PENDIENTE");

        usuario.setSolicitud(newSolicitud);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(creditoRepository.save(any(CreditoEntity.class))).thenReturn(newSolicitud);

        // Act
        when(usuarioService.obtenerValorPositivoMasPequeno(ahorros)).thenReturn(20000); // SIEMPRE Y CUANDO LA FUNCIÓN RETORNE
        Map<String, Object> result = sistemaService.evaluateCredito(userId);
        // Assert
        assertNotNull(result);
        assertEquals(150000, usuario.getSolicitud().getMontop(), 0);
        assertEquals(20, usuario.getSolicitud().getPlazo());
        assertEquals(0.04, usuario.getSolicitud().getIntanu(), 0);
        assertEquals(0.0033, usuario.getSolicitud().getIntmen(), 0);
        assertEquals(200, usuario.getSolicitud().getSegudesg(), 0);
        assertEquals(150, usuario.getSolicitud().getSeguince(), 0);
        assertEquals(50, usuario.getSolicitud().getComiad(), 0);
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getComprobanteIngresos());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getCertificadoAvaluo());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getHistorialCrediticio());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getEscrituraPrimeraVivienda());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getPlanNegocios());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getEstadosFinancieros());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getPresupuestoRemodelacion());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getDicom());
        assertEquals("REVISION ADICIONAL", newSolicitud.getState());
    } // PASA R71 Y R72

    @Test
    public void testEvaluateCreditoPrimeraVivienda32() {
        // Arrange
        UsuarioEntity usuario = new UsuarioEntity();
        Long userId = 1L;

        // Arrange
        List<AhorrosEntity> ahorros = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            AhorrosEntity ahorro = new AhorrosEntity();
            //ahorro.setId((long) i + 1); // Asignar un ID único a cada ahorro
            ahorro.setTransaccion(20000 * i); // Asegúrate de que estos valores sean positivos y mayores a 15,000
            ahorro.setTipo("DEPOSITO");
            ahorros.add(ahorro);
        }
        // Agregar un nuevo ahorro con otro tipo
        AhorrosEntity nuevoAhorro = new AhorrosEntity();
        nuevoAhorro.setTransaccion(-1000000);
        nuevoAhorro.setTipo("RETIRO");
        ahorros.add(nuevoAhorro);

        List<CreditoEntity> creditos = null;
        usuario.setId(userId);
        usuario.setRut("12345678-9");
        usuario.setName("John Doe");
        usuario.setAge(30);
        usuario.setWorkage(5);
        usuario.setHouses(0);
        usuario.setValorpropiedad(200000);
        usuario.setIngresos(200000);
        usuario.setSumadeuda(2000);
        usuario.setObjective("PRIMERA VIVIENDA");
        usuario.setIndependiente("ASALARIADO");
        usuario.setAhorros(ahorros);
        usuario.setCreditos(creditos);



        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        CreditoEntity newSolicitud = new CreditoEntity();
        newSolicitud.setMontop(150000);
        newSolicitud.setPlazo(20);
        newSolicitud.setIntanu(0.04); // Tasa de interés ajustada
        newSolicitud.setIntmen(0.0033);
        newSolicitud.setSegudesg(200);
        newSolicitud.setSeguince(150);
        newSolicitud.setComiad(50);
        newSolicitud.setComprobanteIngresos(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setCertificadoAvaluo(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setHistorialCrediticio(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEscrituraPrimeraVivienda(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPlanNegocios(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEstadosFinancieros(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPresupuestoRemodelacion(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setDicom(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setState("PENDIENTE");

        usuario.setSolicitud(newSolicitud);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(creditoRepository.save(any(CreditoEntity.class))).thenReturn(newSolicitud);
        // Act
        when(usuarioService.obtenerValorPositivoMasPequeno(ahorros)).thenReturn(20000); // SIEMPRE Y CUANDO LA FUNCIÓN RETORNE
        Map<String, Object> result = sistemaService.evaluateCredito(userId);
        // Assert
        assertNotNull(result);
        assertEquals(150000, usuario.getSolicitud().getMontop(), 0);
        assertEquals(20, usuario.getSolicitud().getPlazo());
        assertEquals(0.04, usuario.getSolicitud().getIntanu(), 0);
        assertEquals(0.0033, usuario.getSolicitud().getIntmen(), 0);
        assertEquals(200, usuario.getSolicitud().getSegudesg(), 0);
        assertEquals(150, usuario.getSolicitud().getSeguince(), 0);
        assertEquals(50, usuario.getSolicitud().getComiad(), 0);
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getComprobanteIngresos());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getCertificadoAvaluo());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getHistorialCrediticio());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getEscrituraPrimeraVivienda());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getPlanNegocios());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getEstadosFinancieros());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getPresupuestoRemodelacion());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getDicom());
    } // PASA R71 Y R74

    @Test
    public void testEvaluateCreditoPrimeraVivienda321() {
        // Arrange
        UsuarioEntity usuario = new UsuarioEntity();
        Long userId = 1L;

        // Arrange
        List<AhorrosEntity> ahorros = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            AhorrosEntity ahorro = new AhorrosEntity();
            //ahorro.setId((long) i + 1); // Asignar un ID único a cada ahorro
            ahorro.setTransaccion(2000000 * i); // Asegúrate de que estos valores sean positivos y mayores a 15,000
            ahorro.setTipo("DEPOSITO");
            ahorros.add(ahorro);
        }
        // Agregar un nuevo ahorro con otro tipo
        AhorrosEntity nuevoAhorro = new AhorrosEntity();
        nuevoAhorro.setTransaccion(-1000000);
        nuevoAhorro.setTipo("RETIRO");
        ahorros.add(nuevoAhorro);

        List<CreditoEntity> creditos = null;
        usuario.setId(userId);
        usuario.setRut("12345678-9");
        usuario.setName("John Doe");
        usuario.setAge(30);
        usuario.setWorkage(5);
        usuario.setHouses(0);
        usuario.setValorpropiedad(200000);
        usuario.setIngresos(200000);
        usuario.setSumadeuda(2000);
        usuario.setObjective("PRIMERA VIVIENDA");
        usuario.setIndependiente("ASALARIADO");
        usuario.setAhorros(ahorros);
        usuario.setCreditos(creditos);



        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        CreditoEntity newSolicitud = new CreditoEntity();
        newSolicitud.setMontop(150000);
        newSolicitud.setPlazo(20);
        newSolicitud.setIntanu(0.04); // Tasa de interés ajustada
        newSolicitud.setIntmen(0.0033);
        newSolicitud.setSegudesg(200);
        newSolicitud.setSeguince(150);
        newSolicitud.setComiad(50);
        newSolicitud.setComprobanteIngresos(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setCertificadoAvaluo(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setHistorialCrediticio(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEscrituraPrimeraVivienda(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPlanNegocios(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEstadosFinancieros(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPresupuestoRemodelacion(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setDicom(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setState("PENDIENTE");

        usuario.setSolicitud(newSolicitud);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(creditoRepository.save(any(CreditoEntity.class))).thenReturn(newSolicitud);

        // Act
        when(usuarioService.obtenerValorPositivoMasPequeno(ahorros)).thenReturn(20000); // SIEMPRE Y CUANDO LA FUNCIÓN RETORNE
        Map<String, Object> result = sistemaService.evaluateCredito(userId);
        // Assert
        assertNotNull(result);
        assertEquals(150000, usuario.getSolicitud().getMontop(), 0);
        assertEquals(20, usuario.getSolicitud().getPlazo());
        assertEquals(0.04, usuario.getSolicitud().getIntanu(), 0);
        assertEquals(0.0033, usuario.getSolicitud().getIntmen(), 0);
        assertEquals(200, usuario.getSolicitud().getSegudesg(), 0);
        assertEquals(150, usuario.getSolicitud().getSeguince(), 0);
        assertEquals(50, usuario.getSolicitud().getComiad(), 0);
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getComprobanteIngresos());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getCertificadoAvaluo());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getHistorialCrediticio());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getEscrituraPrimeraVivienda());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getPlanNegocios());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getEstadosFinancieros());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getPresupuestoRemodelacion());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getDicom());
    } //ERROR-3

    @Test
    public void testEvaluateCreditoPrimeraVivienda322() {
        // Arrange
        UsuarioEntity usuario = new UsuarioEntity();
        Long userId = 1L;

        // Arrange
        List<AhorrosEntity> ahorros = new ArrayList<>();
        for (int i = 1; i <= 24; i++) {
            AhorrosEntity ahorro = new AhorrosEntity();
            //ahorro.setId((long) i + 1); // Asignar un ID único a cada ahorro
            ahorro.setTransaccion(20000 * i); // Asegúrate de que estos valores sean positivos y mayores a 15,000
            ahorro.setTipo("DEPOSITO");
            ahorros.add(ahorro);
        }

        List<CreditoEntity> creditos = null;
        usuario.setId(userId);
        usuario.setRut("12345678-9");
        usuario.setName("John Doe");
        usuario.setAge(30);
        usuario.setWorkage(5);
        usuario.setHouses(0);
        usuario.setValorpropiedad(200000);
        usuario.setIngresos(200000);
        usuario.setSumadeuda(2000);
        usuario.setObjective("PRIMERA VIVIENDA");
        usuario.setIndependiente("ASALARIADO");
        usuario.setAhorros(ahorros);
        usuario.setCreditos(creditos);



        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        CreditoEntity newSolicitud = new CreditoEntity();
        newSolicitud.setMontop(150000);
        newSolicitud.setPlazo(20);
        newSolicitud.setIntanu(0.04); // Tasa de interés ajustada
        newSolicitud.setIntmen(0.0033);
        newSolicitud.setSegudesg(200);
        newSolicitud.setSeguince(150);
        newSolicitud.setComiad(50);
        newSolicitud.setComprobanteIngresos(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setCertificadoAvaluo(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setHistorialCrediticio(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEscrituraPrimeraVivienda(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPlanNegocios(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEstadosFinancieros(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPresupuestoRemodelacion(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setDicom(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setState("PENDIENTE");

        usuario.setSolicitud(newSolicitud);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(creditoRepository.save(any(CreditoEntity.class))).thenReturn(newSolicitud);

        // Act
        when(usuarioService.obtenerValorPositivoMasPequeno(ahorros)).thenReturn(20000); // SIEMPRE Y CUANDO LA FUNCIÓN RETORNE
        Map<String, Object> result = sistemaService.evaluateCredito(userId);
        // Assert
        assertNotNull(result);
        assertEquals(150000, usuario.getSolicitud().getMontop(), 0);
        assertEquals(20, usuario.getSolicitud().getPlazo());
        assertEquals(0.04, usuario.getSolicitud().getIntanu(), 0);
        assertEquals(0.0033, usuario.getSolicitud().getIntmen(), 0);
        assertEquals(200, usuario.getSolicitud().getSegudesg(), 0);
        assertEquals(150, usuario.getSolicitud().getSeguince(), 0);
        assertEquals(50, usuario.getSolicitud().getComiad(), 0);
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getComprobanteIngresos());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getCertificadoAvaluo());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getHistorialCrediticio());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getEscrituraPrimeraVivienda());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getPlanNegocios());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getEstadosFinancieros());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getPresupuestoRemodelacion());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getDicom());
    } // APROBADO

    @Test
    public void testEvaluateCreditoPrimeraVivienda323() {
        // Arrange
        UsuarioEntity usuario = new UsuarioEntity();
        Long userId = 1L;

        // Arrange
        List<AhorrosEntity> ahorros = new ArrayList<>();
        for (int i = 1; i <= 24; i++) {
            AhorrosEntity ahorro = new AhorrosEntity();
            //ahorro.setId((long) i + 1); // Asignar un ID único a cada ahorro
            ahorro.setTransaccion(20000 * i); // Asegúrate de que estos valores sean positivos y mayores a 15,000
            ahorro.setTipo("DEPOSITO");
            ahorros.add(ahorro);
        }

        List<CreditoEntity> creditos = null;
        usuario.setId(userId);
        usuario.setRut("12345678-9");
        usuario.setName("John Doe");
        usuario.setAge(30);
        usuario.setWorkage(5);
        usuario.setHouses(0);
        usuario.setValorpropiedad(200000);
        usuario.setIngresos(200000);
        usuario.setSumadeuda(2000);
        usuario.setObjective("PRIMERA VIVIENDA");
        usuario.setIndependiente("ASALARIADO");
        usuario.setAhorros(ahorros);
        usuario.setCreditos(creditos);



        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        CreditoEntity newSolicitud = new CreditoEntity();
        newSolicitud.setMontop(150000);
        newSolicitud.setPlazo(20);
        newSolicitud.setIntanu(0.04); // Tasa de interés ajustada
        newSolicitud.setIntmen(0.0033);
        newSolicitud.setSegudesg(200);
        newSolicitud.setSeguince(150);
        newSolicitud.setComiad(50);
        newSolicitud.setComprobanteIngresos(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setCertificadoAvaluo(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setHistorialCrediticio(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEscrituraPrimeraVivienda(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPlanNegocios(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEstadosFinancieros(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPresupuestoRemodelacion(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setDicom(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setState("PENDIENTE");

        usuario.setSolicitud(newSolicitud);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(creditoRepository.save(any(CreditoEntity.class))).thenReturn(newSolicitud);
        // imprimir ahorros del ususario
        // Act
        when(usuarioService.obtenerValorPositivoMasPequeno(ahorros)).thenReturn(20000); // SIEMPRE Y CUANDO LA FUNCIÓN RETORNE
        Map<String, Object> result = sistemaService.evaluateCredito(userId);

        // Assert
        assertNotNull(result);
        assertEquals(150000, usuario.getSolicitud().getMontop(), 0);
        assertEquals(20, usuario.getSolicitud().getPlazo());
        assertEquals(0.04, usuario.getSolicitud().getIntanu(), 0);
        assertEquals(0.0033, usuario.getSolicitud().getIntmen(), 0);
        assertEquals(200, usuario.getSolicitud().getSegudesg(), 0);
        assertEquals(150, usuario.getSolicitud().getSeguince(), 0);
        assertEquals(50, usuario.getSolicitud().getComiad(), 0);
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getComprobanteIngresos());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getCertificadoAvaluo());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getHistorialCrediticio());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getEscrituraPrimeraVivienda());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getPlanNegocios());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getEstadosFinancieros());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getPresupuestoRemodelacion());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getDicom());
    } // ERROR-4
    //---------------------------------------------------------------------------//
    @Test
    public void testEvaluateCreditoSegundaVivienda() {
        // Arrange
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        List<AhorrosEntity> ahorros = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            AhorrosEntity ahorro = new AhorrosEntity();
            ahorro.setTransaccion(500000 * i);
            ahorro.setTipo("DEPOSITO");
            ahorros.add(ahorro);
        }
        List<CreditoEntity> creditos = null;

        usuario.setRut("12345678-9");
        usuario.setName("John Doe");
        usuario.setAge(30);
        usuario.setWorkage(5);
        usuario.setHouses(1);
        usuario.setValorpropiedad(200000);
        usuario.setIngresos(5000);
        usuario.setSumadeuda(2000);
        usuario.setObjective("SEGUNDA VIVIENDA");
        usuario.setIndependiente("ASALARIADO");
        usuario.setAhorros(ahorros);
        usuario.setCreditos(creditos);

        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        CreditoEntity newSolicitud = new CreditoEntity();
        newSolicitud.setMontop(130000);
        newSolicitud.setPlazo(20);
        newSolicitud.setIntanu(0.04); // Tasa de interés ajustada
        newSolicitud.setIntmen(0.0033);
        newSolicitud.setSegudesg(200);
        newSolicitud.setSeguince(150);
        newSolicitud.setComiad(50);
        newSolicitud.setComprobanteIngresos(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setCertificadoAvaluo(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setHistorialCrediticio(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEscrituraPrimeraVivienda(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPlanNegocios(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEstadosFinancieros(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPresupuestoRemodelacion(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setDicom(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setState("PENDIENTE");

        usuario.setSolicitud(newSolicitud);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(creditoRepository.save(any(CreditoEntity.class))).thenReturn(newSolicitud);

        // Act
        Map<String, Object> result = sistemaService.evaluateCredito(userId);
        // Assert
        assertNotNull(result);
        //assertEquals("RECHAZADA", newSolicitud.getState());
    } //ERROR-1

    @Test
    public void testEvaluateCreditoSegundaVivienda1() {
        // Arrange
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        List<AhorrosEntity> ahorros = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            AhorrosEntity ahorro = new AhorrosEntity();
            ahorro.setTransaccion(500000 * i);
            ahorro.setTipo("DEPOSITO");
            ahorros.add(ahorro);
        }
        List<CreditoEntity> creditos = null;

        usuario.setRut("12345678-9");
        usuario.setName("John Doe");
        usuario.setAge(30);
        usuario.setWorkage(5);
        usuario.setHouses(1);
        usuario.setValorpropiedad(200000);
        usuario.setIngresos(5000);
        usuario.setSumadeuda(2000);
        usuario.setObjective("SEGUNDA VIVIENDA");
        usuario.setIndependiente("ASALARIADO");
        usuario.setAhorros(ahorros);
        usuario.setCreditos(creditos);

        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        CreditoEntity newSolicitud = new CreditoEntity();
        newSolicitud.setMontop(130000);
        newSolicitud.setPlazo(20);
        newSolicitud.setIntanu(0.04); // Tasa de interés ajustada
        newSolicitud.setIntmen(0.0033);
        newSolicitud.setSegudesg(200);
        newSolicitud.setSeguince(150);
        newSolicitud.setComiad(50);
        newSolicitud.setComprobanteIngresos(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setCertificadoAvaluo(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setHistorialCrediticio(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEscrituraPrimeraVivienda(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPlanNegocios(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEstadosFinancieros(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPresupuestoRemodelacion(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setDicom(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setState("PENDIENTE");

        usuario.setSolicitud(newSolicitud);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(creditoRepository.save(any(CreditoEntity.class))).thenReturn(newSolicitud);

        // Act
        Map<String, Object> result = sistemaService.evaluateCredito(userId);
        // Assert
        assertNotNull(result);
        assertEquals(130000, usuario.getSolicitud().getMontop(), 0);
        assertEquals(20, usuario.getSolicitud().getPlazo());
        assertEquals(0.04, usuario.getSolicitud().getIntanu(), 0);
        assertEquals(0.0033, usuario.getSolicitud().getIntmen(), 0);
        assertEquals(200, usuario.getSolicitud().getSegudesg(), 0);
        assertEquals(150, usuario.getSolicitud().getSeguince(), 0);
        assertEquals(50, usuario.getSolicitud().getComiad(), 0);
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getComprobanteIngresos());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getCertificadoAvaluo());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getHistorialCrediticio());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getEscrituraPrimeraVivienda());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getPlanNegocios());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getEstadosFinancieros());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getPresupuestoRemodelacion());
        assertArrayEquals(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4}, usuario.getSolicitud().getDicom());
        //assertEquals("RECHAZADA", newSolicitud.getState());
    }

    @Test
    public void testEvaluateCreditoSegundaVivienda2() {
        // Arrange
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        List<AhorrosEntity> ahorros = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            AhorrosEntity ahorro = new AhorrosEntity();
            ahorro.setTransaccion(500000 * i);
            ahorro.setTipo("DEPOSITO");
            ahorros.add(ahorro);
        }
        List<CreditoEntity> creditos = null;

        usuario.setRut("12345678-9");
        usuario.setName("John Doe");
        usuario.setAge(30);
        usuario.setWorkage(5);
        usuario.setHouses(1);
        usuario.setValorpropiedad(200000);
        usuario.setIngresos(5000);
        usuario.setSumadeuda(2000);
        usuario.setObjective("SEGUNDA VIVIENDA");
        usuario.setIndependiente("ASALARIADO");
        usuario.setAhorros(ahorros);
        usuario.setCreditos(creditos);

        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        CreditoEntity newSolicitud = new CreditoEntity();
        newSolicitud.setMontop(130000);
        newSolicitud.setPlazo(20);
        newSolicitud.setIntanu(0.04); // Tasa de interés ajustada
        newSolicitud.setIntmen(0.0033);
        newSolicitud.setSegudesg(200);
        newSolicitud.setSeguince(150);
        newSolicitud.setComiad(50);
        newSolicitud.setComprobanteIngresos(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setCertificadoAvaluo(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setHistorialCrediticio(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEscrituraPrimeraVivienda(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPlanNegocios(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEstadosFinancieros(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPresupuestoRemodelacion(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setDicom(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setState("PENDIENTE");

        usuario.setSolicitud(newSolicitud);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(creditoRepository.save(any(CreditoEntity.class))).thenReturn(newSolicitud);

        // Act
        when(usuarioService.obtenerValorPositivoMasPequeno(ahorros)).thenReturn(500000); // SIEMPRE Y CUANDO LA FUNCIÓN RETORNE
        Map<String, Object> result = sistemaService.evaluateCredito(userId);
        // Assert
        assertNotNull(result);
        //assertEquals("RECHAZADA", newSolicitud.getState());
    } // APROBADO

    @Test
    public void testEvaluateCreditoSegundaVivienda22() {
        // Arrange
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        List<AhorrosEntity> ahorros = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            AhorrosEntity ahorro = new AhorrosEntity();
            ahorro.setTransaccion(500000 * i);
            ahorro.setTipo("DEPOSITO");
            ahorros.add(ahorro);
        }
        List<CreditoEntity> creditos = null;

        usuario.setRut("12345678-9");
        usuario.setName("John Doe");
        usuario.setAge(30);
        usuario.setWorkage(5);
        usuario.setHouses(1);
        usuario.setValorpropiedad(200000);
        usuario.setIngresos(5000);
        usuario.setSumadeuda(2000);
        usuario.setObjective("SEGUNDA VIVIENDA");
        usuario.setIndependiente("ASALARIADO");
        usuario.setAhorros(ahorros);
        usuario.setCreditos(creditos);

        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        CreditoEntity newSolicitud = new CreditoEntity();
        newSolicitud.setMontop(130000);
        newSolicitud.setPlazo(20);
        newSolicitud.setIntanu(0.04); // Tasa de interés ajustada
        newSolicitud.setIntmen(0.0033);
        newSolicitud.setSegudesg(200);
        newSolicitud.setSeguince(150);
        newSolicitud.setComiad(50);
        newSolicitud.setComprobanteIngresos(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setCertificadoAvaluo(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setHistorialCrediticio(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEscrituraPrimeraVivienda(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setDicom(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setState("PENDIENTE");

        usuario.setSolicitud(newSolicitud);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(creditoRepository.save(any(CreditoEntity.class))).thenReturn(newSolicitud);

        // Act
        when(usuarioService.obtenerValorPositivoMasPequeno(ahorros)).thenReturn(500000); // SIEMPRE Y CUANDO LA FUNCIÓN RETORNE
        Map<String, Object> result = sistemaService.evaluateCredito(userId);
        // Assert
        assertNotNull(result);
    } // APROBADO CON DOCUMENTOS JUSTOS

    @Test
    public void testEvaluateCreditoSegundaVivienda222() {
        // Arrange
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        List<AhorrosEntity> ahorros = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            AhorrosEntity ahorro = new AhorrosEntity();
            ahorro.setTransaccion(500000 * i);
            ahorro.setTipo("DEPOSITO");
            ahorros.add(ahorro);
        }
        List<CreditoEntity> creditos = null;

        usuario.setRut("12345678-9");
        usuario.setName("John Doe");
        usuario.setAge(30);
        usuario.setWorkage(5);
        usuario.setHouses(1);
        usuario.setValorpropiedad(200000);
        usuario.setIngresos(5000);
        usuario.setSumadeuda(2000);
        usuario.setObjective("SEGUNDA VIVIENDA");
        usuario.setIndependiente("ASALARIADO");
        usuario.setAhorros(ahorros);
        usuario.setCreditos(creditos);

        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        CreditoEntity newSolicitud = new CreditoEntity();
        newSolicitud.setMontop(130000);
        newSolicitud.setPlazo(20);
        newSolicitud.setIntanu(0.04); // Tasa de interés ajustada
        newSolicitud.setIntmen(0.0033);
        newSolicitud.setSegudesg(200);
        newSolicitud.setSeguince(150);
        newSolicitud.setComiad(50);
        newSolicitud.setComprobanteIngresos(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setCertificadoAvaluo(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setHistorialCrediticio(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setDicom(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setState("PENDIENTE");

        usuario.setSolicitud(newSolicitud);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(creditoRepository.save(any(CreditoEntity.class))).thenReturn(newSolicitud);

        // Act
        when(usuarioService.obtenerValorPositivoMasPequeno(ahorros)).thenReturn(500000); // SIEMPRE Y CUANDO LA FUNCIÓN RETORNE
        Map<String, Object> result = sistemaService.evaluateCredito(userId);
        // Assert
        assertNull(result);
    } // FALTA DE ARCHIVOS

    @Test
    public void testEvaluateCreditoSegundaVivienda21() {
        // Arrange
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        List<AhorrosEntity> ahorros = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            AhorrosEntity ahorro = new AhorrosEntity();
            ahorro.setTransaccion(500000 * i);
            ahorro.setTipo("DEPOSITO");
            ahorros.add(ahorro);
        }
        List<CreditoEntity> creditos = null;

        usuario.setRut("12345678-9");
        usuario.setName("John Doe");
        usuario.setAge(75);
        usuario.setWorkage(5);
        usuario.setHouses(1);
        usuario.setValorpropiedad(200000);
        usuario.setIngresos(5000);
        usuario.setSumadeuda(2000);
        usuario.setObjective("SEGUNDA VIVIENDA");
        usuario.setIndependiente("ASALARIADO");
        usuario.setAhorros(ahorros);
        usuario.setCreditos(creditos);

        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        CreditoEntity newSolicitud = new CreditoEntity();
        newSolicitud.setMontop(130000);
        newSolicitud.setPlazo(20);
        newSolicitud.setIntanu(0.04); // Tasa de interés ajustada
        newSolicitud.setIntmen(0.0033);
        newSolicitud.setSegudesg(200);
        newSolicitud.setSeguince(150);
        newSolicitud.setComiad(50);
        newSolicitud.setComprobanteIngresos(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setCertificadoAvaluo(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setHistorialCrediticio(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEscrituraPrimeraVivienda(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPlanNegocios(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEstadosFinancieros(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPresupuestoRemodelacion(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setDicom(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setState("PENDIENTE");

        usuario.setSolicitud(newSolicitud);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(creditoRepository.save(any(CreditoEntity.class))).thenReturn(newSolicitud);

        // Act
        when(usuarioService.obtenerValorPositivoMasPequeno(ahorros)).thenReturn(500000); // SIEMPRE Y CUANDO LA FUNCIÓN RETORNE
        Map<String, Object> result = sistemaService.evaluateCredito(userId);
        // Assert
        assertNull(result);
        //assertEquals("RECHAZADA", newSolicitud.getState());
    } // EDAD NO APROBADA

    @Test
    public void testEvaluateCreditoSegundaVivienda211() {
        // Arrange
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        List<AhorrosEntity> ahorros = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            AhorrosEntity ahorro = new AhorrosEntity();
            ahorro.setTransaccion(50000000 * i);
            ahorro.setTipo("DEPOSITO");
            ahorros.add(ahorro);
        }
        // Agregar un nuevo ahorro con otro tipo
        AhorrosEntity nuevoAhorro = new AhorrosEntity();
        nuevoAhorro.setTransaccion(-1000000);
        nuevoAhorro.setTipo("RETIRO");
        ahorros.add(nuevoAhorro);

        List<CreditoEntity> creditos = null;

        usuario.setRut("12345678-9");
        usuario.setName("John Doe");
        usuario.setAge(30);
        usuario.setWorkage(5);
        usuario.setHouses(1);
        usuario.setValorpropiedad(200000);
        usuario.setIngresos(5000);
        usuario.setSumadeuda(2000);
        usuario.setObjective("SEGUNDA VIVIENDA");
        usuario.setIndependiente("ASALARIADO");
        usuario.setAhorros(ahorros);
        usuario.setCreditos(creditos);

        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        CreditoEntity newSolicitud = new CreditoEntity();
        newSolicitud.setMontop(130000);
        newSolicitud.setPlazo(20);
        newSolicitud.setIntanu(0.04); // Tasa de interés ajustada
        newSolicitud.setIntmen(0.0033);
        newSolicitud.setSegudesg(200);
        newSolicitud.setSeguince(150);
        newSolicitud.setComiad(50);
        newSolicitud.setComprobanteIngresos(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setCertificadoAvaluo(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setHistorialCrediticio(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEscrituraPrimeraVivienda(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPlanNegocios(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEstadosFinancieros(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPresupuestoRemodelacion(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setDicom(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setState("PENDIENTE");

        usuario.setSolicitud(newSolicitud);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(creditoRepository.save(any(CreditoEntity.class))).thenReturn(newSolicitud);

        // Act
        when(usuarioService.obtenerValorPositivoMasPequeno(ahorros)).thenReturn(500000); // SIEMPRE Y CUANDO LA FUNCIÓN RETORNE
        Map<String, Object> result = sistemaService.evaluateCredito(userId);
        // Assert
        assertNotNull(result);
        //assertEquals("RECHAZADA", newSolicitud.getState());
    } // LOS 2 ERRORES-3

    @Test
    public void testEvaluateCreditoSegundaVivienda2111() {
        // Arrange
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        List<AhorrosEntity> ahorros = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            AhorrosEntity ahorro = new AhorrosEntity();
            ahorro.setTransaccion(500000 * i);
            ahorro.setTipo("DEPOSITO");
            ahorros.add(ahorro);
        }
        // Agregar un nuevo ahorro con otro tipo
        AhorrosEntity nuevoAhorro = new AhorrosEntity();
        nuevoAhorro.setTransaccion(-1000000);
        nuevoAhorro.setTipo("RETIRO");
        ahorros.add(nuevoAhorro);

        List<CreditoEntity> creditos = null;

        usuario.setRut("12345678-9");
        usuario.setName("John Doe");
        usuario.setAge(30);
        usuario.setWorkage(5);
        usuario.setHouses(1);
        usuario.setValorpropiedad(200000);
        usuario.setIngresos(5000);
        usuario.setSumadeuda(2000);
        usuario.setObjective("SEGUNDA VIVIENDA");
        usuario.setIndependiente("ASALARIADO");
        usuario.setAhorros(ahorros);
        usuario.setCreditos(creditos);

        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        CreditoEntity newSolicitud = new CreditoEntity();
        newSolicitud.setMontop(130000);
        newSolicitud.setPlazo(20);
        newSolicitud.setIntanu(0.04); // Tasa de interés ajustada
        newSolicitud.setIntmen(0.0033);
        newSolicitud.setSegudesg(200);
        newSolicitud.setSeguince(150);
        newSolicitud.setComiad(50);
        newSolicitud.setComprobanteIngresos(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setCertificadoAvaluo(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setHistorialCrediticio(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEscrituraPrimeraVivienda(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPlanNegocios(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEstadosFinancieros(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPresupuestoRemodelacion(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setDicom(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setState("PENDIENTE");

        usuario.setSolicitud(newSolicitud);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(creditoRepository.save(any(CreditoEntity.class))).thenReturn(newSolicitud);

        // Act
        when(usuarioService.obtenerValorPositivoMasPequeno(ahorros)).thenReturn(500000); // SIEMPRE Y CUANDO LA FUNCIÓN RETORNE
        Map<String, Object> result = sistemaService.evaluateCredito(userId);
        // Assert
        assertNotNull(result);
        //assertEquals("RECHAZADA", newSolicitud.getState());
    } // ERROR-3
    //---------------------------------------------------------------------------//
    @Test
    public void testEvaluateCreditoPropiedadComercial() {
        // Arrange
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        List<AhorrosEntity> ahorros = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            AhorrosEntity ahorro = new AhorrosEntity();
            ahorro.setTransaccion(500000 * i);
            ahorro.setTipo("DEPOSITO");
            ahorros.add(ahorro);
        }
        List<CreditoEntity> creditos = null;

        usuario.setRut("12345678-9");
        usuario.setName("John Doe");
        usuario.setAge(30);
        usuario.setWorkage(5);
        usuario.setHouses(1);
        usuario.setValorpropiedad(200000);
        usuario.setIngresos(5000);
        usuario.setSumadeuda(2000);
        usuario.setObjective("PROPIEDAD COMERCIAL");
        usuario.setIndependiente("ASALARIADO");
        usuario.setAhorros(ahorros);
        usuario.setCreditos(creditos);

        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        CreditoEntity newSolicitud = new CreditoEntity();
        newSolicitud.setMontop(110000);
        newSolicitud.setPlazo(20);
        newSolicitud.setIntanu(0.06); // Tasa de interés ajustada
        newSolicitud.setIntmen(0.0005);
        newSolicitud.setSegudesg(200);
        newSolicitud.setSeguince(150);
        newSolicitud.setComiad(50);
        newSolicitud.setComprobanteIngresos(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setCertificadoAvaluo(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setHistorialCrediticio(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEscrituraPrimeraVivienda(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPlanNegocios(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEstadosFinancieros(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPresupuestoRemodelacion(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setDicom(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setState("PENDIENTE");

        usuario.setSolicitud(newSolicitud);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(creditoRepository.save(any(CreditoEntity.class))).thenReturn(newSolicitud);

        // Act
        Map<String, Object> result = sistemaService.evaluateCredito(userId);
        // Assert
        assertNotNull(result);
    } //ERROR-1

    @Test
    public void testEvaluateCreditoPropiedadComercial2() {
        // Arrange
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        List<AhorrosEntity> ahorros = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            AhorrosEntity ahorro = new AhorrosEntity();
            ahorro.setTransaccion(500000 * i);
            ahorro.setTipo("DEPOSITO");
            ahorros.add(ahorro);
        }
        List<CreditoEntity> creditos = null;

        usuario.setRut("12345678-9");
        usuario.setName("John Doe");
        usuario.setAge(30);
        usuario.setWorkage(5);
        usuario.setHouses(1);
        usuario.setValorpropiedad(200000);
        usuario.setIngresos(5000);
        usuario.setSumadeuda(2000);
        usuario.setObjective("PROPIEDAD COMERCIAL");
        usuario.setIndependiente("ASALARIADO");
        usuario.setAhorros(ahorros);
        usuario.setCreditos(creditos);

        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        CreditoEntity newSolicitud = new CreditoEntity();
        newSolicitud.setMontop(110000);
        newSolicitud.setPlazo(20);
        newSolicitud.setIntanu(0.06); // Tasa de interés ajustada
        newSolicitud.setIntmen(0.0005);
        newSolicitud.setSegudesg(200);
        newSolicitud.setSeguince(150);
        newSolicitud.setComiad(50);
        newSolicitud.setComprobanteIngresos(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setCertificadoAvaluo(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setHistorialCrediticio(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEscrituraPrimeraVivienda(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPlanNegocios(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEstadosFinancieros(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPresupuestoRemodelacion(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setDicom(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setState("PENDIENTE");

        usuario.setSolicitud(newSolicitud);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(creditoRepository.save(any(CreditoEntity.class))).thenReturn(newSolicitud);

        // Act
        when(usuarioService.obtenerValorPositivoMasPequeno(ahorros)).thenReturn(500000); // SIEMPRE Y CUANDO LA FUNCIÓN RETORNE
        Map<String, Object> result = sistemaService.evaluateCredito(userId);
        // Assert
        assertNotNull(result);
    } // APROBADO

    @Test
    public void testEvaluateCreditoPropiedadComercial3() {
        // Arrange
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        List<AhorrosEntity> ahorros = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            AhorrosEntity ahorro = new AhorrosEntity();
            ahorro.setTransaccion(500000 * i);
            ahorro.setTipo("DEPOSITO");
            ahorros.add(ahorro);
        }
        List<CreditoEntity> creditos = null;

        usuario.setRut("12345678-9");
        usuario.setName("John Doe");
        usuario.setAge(30);
        usuario.setWorkage(5);
        usuario.setHouses(1);
        usuario.setValorpropiedad(200000);
        usuario.setIngresos(5000);
        usuario.setSumadeuda(2000);
        usuario.setObjective("PROPIEDAD COMERCIAL");
        usuario.setIndependiente("ASALARIADO");
        usuario.setAhorros(ahorros);
        usuario.setCreditos(creditos);

        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        CreditoEntity newSolicitud = new CreditoEntity();
        newSolicitud.setMontop(110000);
        newSolicitud.setPlazo(20);
        newSolicitud.setIntanu(0.06); // Tasa de interés ajustada
        newSolicitud.setIntmen(0.0005);
        newSolicitud.setSegudesg(200);
        newSolicitud.setSeguince(150);
        newSolicitud.setComiad(50);
        newSolicitud.setComprobanteIngresos(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setCertificadoAvaluo(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPlanNegocios(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEstadosFinancieros(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setDicom(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setState("PENDIENTE");

        usuario.setSolicitud(newSolicitud);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(creditoRepository.save(any(CreditoEntity.class))).thenReturn(newSolicitud);

        // Act
        when(usuarioService.obtenerValorPositivoMasPequeno(ahorros)).thenReturn(500000); // SIEMPRE Y CUANDO LA FUNCIÓN RETORNE
        Map<String, Object> result = sistemaService.evaluateCredito(userId);
        // Assert
        assertNotNull(result);
    } // APROBADO CON DOCUMENTOS JUSTOS

    @Test
    public void testEvaluateCreditoPropiedadComercial31() {
        // Arrange
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        List<AhorrosEntity> ahorros = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            AhorrosEntity ahorro = new AhorrosEntity();
            ahorro.setTransaccion(500000 * i);
            ahorro.setTipo("DEPOSITO");
            ahorros.add(ahorro);
        }
        List<CreditoEntity> creditos = null;

        usuario.setRut("12345678-9");
        usuario.setName("John Doe");
        usuario.setAge(30);
        usuario.setWorkage(5);
        usuario.setHouses(1);
        usuario.setValorpropiedad(200000);
        usuario.setIngresos(5000);
        usuario.setSumadeuda(2000);
        usuario.setObjective("PROPIEDAD COMERCIAL");
        usuario.setIndependiente("ASALARIADO");
        usuario.setAhorros(ahorros);
        usuario.setCreditos(creditos);

        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        CreditoEntity newSolicitud = new CreditoEntity();
        newSolicitud.setMontop(110000);
        newSolicitud.setPlazo(20);
        newSolicitud.setIntanu(0.06); // Tasa de interés ajustada
        newSolicitud.setIntmen(0.0005);
        newSolicitud.setSegudesg(200);
        newSolicitud.setSeguince(150);
        newSolicitud.setComiad(50);
        newSolicitud.setComprobanteIngresos(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setCertificadoAvaluo(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPlanNegocios(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setDicom(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setState("PENDIENTE");

        usuario.setSolicitud(newSolicitud);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(creditoRepository.save(any(CreditoEntity.class))).thenReturn(newSolicitud);

        // Act
        when(usuarioService.obtenerValorPositivoMasPequeno(ahorros)).thenReturn(500000); // SIEMPRE Y CUANDO LA FUNCIÓN RETORNE
        Map<String, Object> result = sistemaService.evaluateCredito(userId);
        // Assert
        assertNull(result);
    } // FALTA DE ARCHIVOS

    @Test
    public void testEvaluateCreditoPropiedadComercial21() {
        // Arrange
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        List<AhorrosEntity> ahorros = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            AhorrosEntity ahorro = new AhorrosEntity();
            ahorro.setTransaccion(500000 * i);
            ahorro.setTipo("DEPOSITO");
            ahorros.add(ahorro);
        }
        List<CreditoEntity> creditos = null;

        usuario.setRut("12345678-9");
        usuario.setName("John Doe");
        usuario.setAge(75);
        usuario.setWorkage(5);
        usuario.setHouses(1);
        usuario.setValorpropiedad(200000);
        usuario.setIngresos(5000);
        usuario.setSumadeuda(2000);
        usuario.setObjective("PROPIEDAD COMERCIAL");
        usuario.setIndependiente("ASALARIADO");
        usuario.setAhorros(ahorros);
        usuario.setCreditos(creditos);

        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        CreditoEntity newSolicitud = new CreditoEntity();
        newSolicitud.setMontop(110000);
        newSolicitud.setPlazo(20);
        newSolicitud.setIntanu(0.06); // Tasa de interés ajustada
        newSolicitud.setIntmen(0.0005);
        newSolicitud.setSegudesg(200);
        newSolicitud.setSeguince(150);
        newSolicitud.setComiad(50);
        newSolicitud.setComprobanteIngresos(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setCertificadoAvaluo(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setHistorialCrediticio(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEscrituraPrimeraVivienda(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPlanNegocios(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEstadosFinancieros(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPresupuestoRemodelacion(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setDicom(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setState("PENDIENTE");

        usuario.setSolicitud(newSolicitud);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(creditoRepository.save(any(CreditoEntity.class))).thenReturn(newSolicitud);

        // Act
        when(usuarioService.obtenerValorPositivoMasPequeno(ahorros)).thenReturn(500000); // SIEMPRE Y CUANDO LA FUNCIÓN RETORNE
        Map<String, Object> result = sistemaService.evaluateCredito(userId);
        // Assert
        assertNull(result);
    } // EDAD = 75

    @Test
    public void testEvaluateCreditoPropiedadComercial22() {
        // Arrange
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        List<AhorrosEntity> ahorros = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            AhorrosEntity ahorro = new AhorrosEntity();
            ahorro.setTransaccion(500000 * i);
            ahorro.setTipo("DEPOSITO");
            ahorros.add(ahorro);
        }
        // Agregar un nuevo ahorro con otro tipo
        AhorrosEntity nuevoAhorro = new AhorrosEntity();
        nuevoAhorro.setTransaccion(-1000000);
        nuevoAhorro.setTipo("RETIRO");
        ahorros.add(nuevoAhorro);

        List<CreditoEntity> creditos = null;

        usuario.setRut("12345678-9");
        usuario.setName("John Doe");
        usuario.setAge(30);
        usuario.setWorkage(5);
        usuario.setHouses(1);
        usuario.setValorpropiedad(200000);
        usuario.setIngresos(5000);
        usuario.setSumadeuda(2000);
        usuario.setObjective("PROPIEDAD COMERCIAL");
        usuario.setIndependiente("ASALARIADO");
        usuario.setAhorros(ahorros);
        usuario.setCreditos(creditos);

        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        CreditoEntity newSolicitud = new CreditoEntity();
        newSolicitud.setMontop(110000);
        newSolicitud.setPlazo(20);
        newSolicitud.setIntanu(0.06); // Tasa de interés ajustada
        newSolicitud.setIntmen(0.0005);
        newSolicitud.setSegudesg(200);
        newSolicitud.setSeguince(150);
        newSolicitud.setComiad(50);
        newSolicitud.setComprobanteIngresos(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setCertificadoAvaluo(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setHistorialCrediticio(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEscrituraPrimeraVivienda(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPlanNegocios(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEstadosFinancieros(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPresupuestoRemodelacion(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setDicom(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setState("PENDIENTE");

        usuario.setSolicitud(newSolicitud);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(creditoRepository.save(any(CreditoEntity.class))).thenReturn(newSolicitud);

        // Act
        when(usuarioService.obtenerValorPositivoMasPequeno(ahorros)).thenReturn(500000); // SIEMPRE Y CUANDO LA FUNCIÓN RETORNE
        Map<String, Object> result = sistemaService.evaluateCredito(userId);
        // Assert
        assertNotNull(result);
    } // RETIRO AL FINAL

    @Test
    public void testEvaluateCreditoPropiedadComercial23() {
        // Arrange
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        List<AhorrosEntity> ahorros = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            AhorrosEntity ahorro = new AhorrosEntity();
            ahorro.setTransaccion(500000 * i);
            ahorro.setTipo("DEPOSITO");
            ahorros.add(ahorro);
        }
        List<CreditoEntity> creditos = null;

        usuario.setRut("12345678-9");
        usuario.setName("John Doe");
        usuario.setAge(30);
        usuario.setWorkage(5);
        usuario.setHouses(1);
        usuario.setValorpropiedad(200000);
        usuario.setIngresos(5000);
        usuario.setSumadeuda(2000);
        usuario.setObjective("PROPIEDAD COMERCIAL");
        usuario.setIndependiente("ASALARIADO");
        usuario.setAhorros(ahorros);
        usuario.setCreditos(creditos);

        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        CreditoEntity newSolicitud = new CreditoEntity();
        newSolicitud.setMontop(110000);
        newSolicitud.setPlazo(20);
        newSolicitud.setIntanu(0.06); // Tasa de interés ajustada
        newSolicitud.setIntmen(0.0005);
        newSolicitud.setSegudesg(200);
        newSolicitud.setSeguince(150);
        newSolicitud.setComiad(50);
        newSolicitud.setComprobanteIngresos(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setCertificadoAvaluo(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setHistorialCrediticio(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEscrituraPrimeraVivienda(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPlanNegocios(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEstadosFinancieros(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPresupuestoRemodelacion(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setDicom(null);
        newSolicitud.setState("PENDIENTE");

        usuario.setSolicitud(newSolicitud);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(creditoRepository.save(any(CreditoEntity.class))).thenReturn(newSolicitud);

        // Act
        when(usuarioService.obtenerValorPositivoMasPequeno(ahorros)).thenReturn(500000); // SIEMPRE Y CUANDO LA FUNCIÓN RETORNE
        Map<String, Object> result = sistemaService.evaluateCredito(userId);
        // Assert
        assertNull(result);
    } // DICOM == NULL

    @Test
    public void testEvaluateCreditoPropiedadComercial24() {
        // Arrange
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        List<AhorrosEntity> ahorros = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            AhorrosEntity ahorro = new AhorrosEntity();
            ahorro.setTransaccion(500000 * i);
            ahorro.setTipo("DEPOSITO");
            ahorros.add(ahorro);
        }
        List<CreditoEntity> creditos = null;

        usuario.setRut("12345678-9");
        usuario.setName("John Doe");
        usuario.setAge(30);
        usuario.setWorkage(5);
        usuario.setHouses(1);
        usuario.setValorpropiedad(200000);
        usuario.setIngresos(5000);
        usuario.setSumadeuda(2000);
        usuario.setObjective("PROPIEDAD COMERCIAL");
        usuario.setIndependiente("ASALARIADO");
        usuario.setAhorros(ahorros);
        usuario.setCreditos(creditos);

        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        CreditoEntity newSolicitud = new CreditoEntity();
        newSolicitud.setMontop(110000);
        newSolicitud.setPlazo(20);
        newSolicitud.setIntanu(0.06); // Tasa de interés ajustada
        newSolicitud.setIntmen(0.0005);
        newSolicitud.setSegudesg(200);
        newSolicitud.setSeguince(150);
        newSolicitud.setComiad(50);
        newSolicitud.setComprobanteIngresos(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setCertificadoAvaluo(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setHistorialCrediticio(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEscrituraPrimeraVivienda(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPlanNegocios(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEstadosFinancieros(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPresupuestoRemodelacion(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setDicom(new byte[]{});
        newSolicitud.setState("PENDIENTE");

        usuario.setSolicitud(newSolicitud);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(creditoRepository.save(any(CreditoEntity.class))).thenReturn(newSolicitud);

        // Act
        when(usuarioService.obtenerValorPositivoMasPequeno(ahorros)).thenReturn(500000); // SIEMPRE Y CUANDO LA FUNCIÓN RETORNE
        Map<String, Object> result = sistemaService.evaluateCredito(userId);
        // Assert
        assertNull(result);
    } // DICOM == LONGITUD = 0
    //---------------------------------------------------------------------------//
    @Test
    public void testEvaluateCreditoRemodelacion() {
        // Arrange
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        List<AhorrosEntity> ahorros = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            AhorrosEntity ahorro = new AhorrosEntity();
            ahorro.setTransaccion(500000 * i);
            ahorro.setTipo("DEPOSITO");
            ahorros.add(ahorro);
        }
        List<CreditoEntity> creditos = null;

        usuario.setRut("12345678-9");
        usuario.setName("John Doe");
        usuario.setAge(30);
        usuario.setWorkage(5);
        usuario.setHouses(1);
        usuario.setValorpropiedad(200000);
        usuario.setIngresos(5000);
        usuario.setSumadeuda(2000);
        usuario.setObjective("REMODELACION");
        usuario.setIndependiente("ASALARIADO");
        usuario.setAhorros(ahorros);
        usuario.setCreditos(creditos);

        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        CreditoEntity newSolicitud = new CreditoEntity();
        newSolicitud.setMontop(90000);
        newSolicitud.setPlazo(15);
        newSolicitud.setIntanu(0.05); // Tasa de interés ajustada
        newSolicitud.setIntmen(0.0004);
        newSolicitud.setSegudesg(200);
        newSolicitud.setSeguince(150);
        newSolicitud.setComiad(50);
        newSolicitud.setComprobanteIngresos(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setCertificadoAvaluo(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setHistorialCrediticio(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEscrituraPrimeraVivienda(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPlanNegocios(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEstadosFinancieros(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPresupuestoRemodelacion(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setDicom(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setState("PENDIENTE");

        usuario.setSolicitud(newSolicitud);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(creditoRepository.save(any(CreditoEntity.class))).thenReturn(newSolicitud);

        // Act
        Map<String, Object> result = sistemaService.evaluateCredito(userId);
        // Assert
        assertNotNull(result);
    } // ERROR-1

    @Test
    public void testEvaluateCreditoRemodelacion2() {
        // Arrange
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        List<AhorrosEntity> ahorros = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            AhorrosEntity ahorro = new AhorrosEntity();
            ahorro.setTransaccion(500000 * i);
            ahorro.setTipo("DEPOSITO");
            ahorros.add(ahorro);
        }
        List<CreditoEntity> creditos = null;

        usuario.setRut("12345678-9");
        usuario.setName("John Doe");
        usuario.setAge(30);
        usuario.setWorkage(5);
        usuario.setHouses(1);
        usuario.setValorpropiedad(200000);
        usuario.setIngresos(5000);
        usuario.setSumadeuda(2000);
        usuario.setObjective("REMODELACION");
        usuario.setIndependiente("ASALARIADO");
        usuario.setAhorros(ahorros);
        usuario.setCreditos(creditos);

        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        CreditoEntity newSolicitud = new CreditoEntity();
        newSolicitud.setMontop(90000);
        newSolicitud.setPlazo(15);
        newSolicitud.setIntanu(0.05); // Tasa de interés ajustada
        newSolicitud.setIntmen(0.0004);
        newSolicitud.setSegudesg(200);
        newSolicitud.setSeguince(150);
        newSolicitud.setComiad(50);
        newSolicitud.setComprobanteIngresos(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setCertificadoAvaluo(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setHistorialCrediticio(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEscrituraPrimeraVivienda(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPlanNegocios(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEstadosFinancieros(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPresupuestoRemodelacion(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setDicom(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setState("PENDIENTE");

        usuario.setSolicitud(newSolicitud);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(creditoRepository.save(any(CreditoEntity.class))).thenReturn(newSolicitud);

        // Act
        Map<String, Object> result = sistemaService.evaluateCredito(userId);
        // Assert
        assertNotNull(result);
    } // ERROR-1, 2, 3.1 Y 3.2

    @Test
    public void testEvaluateCreditoRemodelacion3() {
        // Arrange
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        List<AhorrosEntity> ahorros = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            AhorrosEntity ahorro = new AhorrosEntity();
            ahorro.setTransaccion(500000 * i);
            ahorro.setTipo("DEPOSITO");
            ahorros.add(ahorro);
        }
        List<CreditoEntity> creditos = null;

        usuario.setRut("12345678-9");
        usuario.setName("John Doe");
        usuario.setAge(30);
        usuario.setWorkage(5);
        usuario.setHouses(1);
        usuario.setValorpropiedad(200000);
        usuario.setIngresos(5000);
        usuario.setSumadeuda(2000);
        usuario.setObjective("REMODELACION");
        usuario.setIndependiente("ASALARIADO");
        usuario.setAhorros(ahorros);
        usuario.setCreditos(creditos);

        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        CreditoEntity newSolicitud = new CreditoEntity();
        newSolicitud.setMontop(90000);
        newSolicitud.setPlazo(15);
        newSolicitud.setIntanu(0.05); // Tasa de interés ajustada
        newSolicitud.setIntmen(0.0004);
        newSolicitud.setSegudesg(200);
        newSolicitud.setSeguince(150);
        newSolicitud.setComiad(50);
        newSolicitud.setComprobanteIngresos(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setCertificadoAvaluo(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setHistorialCrediticio(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEscrituraPrimeraVivienda(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPlanNegocios(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setEstadosFinancieros(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPresupuestoRemodelacion(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setDicom(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setState("PENDIENTE");

        usuario.setSolicitud(newSolicitud);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(creditoRepository.save(any(CreditoEntity.class))).thenReturn(newSolicitud);

        // Act
        when(usuarioService.obtenerValorPositivoMasPequeno(ahorros)).thenReturn(500000); // SIEMPRE Y CUANDO LA FUNCIÓN RETORNE
        Map<String, Object> result = sistemaService.evaluateCredito(userId);
        // Assert
        assertNotNull(result);
    } // APROBADO

    @Test
    public void testEvaluateCreditoRemodelacion31() {
        // Arrange
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        List<AhorrosEntity> ahorros = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            AhorrosEntity ahorro = new AhorrosEntity();
            ahorro.setTransaccion(500000 * i);
            ahorro.setTipo("DEPOSITO");
            ahorros.add(ahorro);
        }
        List<CreditoEntity> creditos = null;

        usuario.setRut("12345678-9");
        usuario.setName("John Doe");
        usuario.setAge(30);
        usuario.setWorkage(5);
        usuario.setHouses(1);
        usuario.setValorpropiedad(200000);
        usuario.setIngresos(5000);
        usuario.setSumadeuda(2000);
        usuario.setObjective("REMODELACION");
        usuario.setIndependiente("ASALARIADO");
        usuario.setAhorros(ahorros);
        usuario.setCreditos(creditos);

        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        CreditoEntity newSolicitud = new CreditoEntity();
        newSolicitud.setMontop(90000);
        newSolicitud.setPlazo(15);
        newSolicitud.setIntanu(0.05); // Tasa de interés ajustada
        newSolicitud.setIntmen(0.0004);
        newSolicitud.setSegudesg(200);
        newSolicitud.setSeguince(150);
        newSolicitud.setComiad(50);
        newSolicitud.setComprobanteIngresos(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setCertificadoAvaluo(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setPresupuestoRemodelacion(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setDicom(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setState("PENDIENTE");

        usuario.setSolicitud(newSolicitud);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(creditoRepository.save(any(CreditoEntity.class))).thenReturn(newSolicitud);

        // Act
        when(usuarioService.obtenerValorPositivoMasPequeno(ahorros)).thenReturn(500000); // SIEMPRE Y CUANDO LA FUNCIÓN RETORNE
        Map<String, Object> result = sistemaService.evaluateCredito(userId);
        // Assert
        assertNotNull(result);
    } // APROBADO CON DOCUMENTOS JUSTOS

    @Test
    public void testEvaluateCreditoRemodelacion32() {
        // Arrange
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        List<AhorrosEntity> ahorros = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            AhorrosEntity ahorro = new AhorrosEntity();
            ahorro.setTransaccion(500000 * i);
            ahorro.setTipo("DEPOSITO");
            ahorros.add(ahorro);
        }
        List<CreditoEntity> creditos = null;

        usuario.setRut("12345678-9");
        usuario.setName("John Doe");
        usuario.setAge(30);
        usuario.setWorkage(5);
        usuario.setHouses(1);
        usuario.setValorpropiedad(200000);
        usuario.setIngresos(5000);
        usuario.setSumadeuda(2000);
        usuario.setObjective("REMODELACION");
        usuario.setIndependiente("ASALARIADO");
        usuario.setAhorros(ahorros);
        usuario.setCreditos(creditos);

        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        CreditoEntity newSolicitud = new CreditoEntity();
        newSolicitud.setMontop(90000);
        newSolicitud.setPlazo(15);
        newSolicitud.setIntanu(0.05); // Tasa de interés ajustada
        newSolicitud.setIntmen(0.0004);
        newSolicitud.setSegudesg(200);
        newSolicitud.setSeguince(150);
        newSolicitud.setComiad(50);
        newSolicitud.setComprobanteIngresos(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setCertificadoAvaluo(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setDicom(new byte[]{'%', 'P', 'D', 'F', 1, 2, 3, 4});
        newSolicitud.setState("PENDIENTE");

        usuario.setSolicitud(newSolicitud);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(creditoRepository.save(any(CreditoEntity.class))).thenReturn(newSolicitud);

        // Act
        when(usuarioService.obtenerValorPositivoMasPequeno(ahorros)).thenReturn(500000); // SIEMPRE Y CUANDO LA FUNCIÓN RETORNE
        Map<String, Object> result = sistemaService.evaluateCredito(userId);
        // Assert
        assertNull(result);
    } // FALTA DE DOCUMENTOS
    //---------------------------------------------------------------------------//
    //---------------------------------------------------------------------------//
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
    public void testFollowCredito2() {
        // Configurar mocks y datos de prueba
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setId(userId);
        CreditoEntity solicitud = new CreditoEntity();
        usuario.setSolicitud(solicitud);
        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));

        // Llamar al método a probar
        CreditoEntity result = sistemaService.followCredito(userId);

        // Verificar resultados
        assertNotNull(result);
        assertEquals(solicitud, result);
    }
    @Test
    public void testFollowCreditoSolicitudNula() {
        // Arrange
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setId(userId);
        usuario.setSolicitud(null);
        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));

        // Act
        CreditoEntity result = sistemaService.followCredito(userId);

        // Assert
        assertNull(result);
    }

    @Test
    public void testFollowCreditoUsuarioNoEncontrado() {
        // Arrange
        Long userId = 1L;
        when(usuarioRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            sistemaService.followCredito(userId);
        });
    }

    @Test
    public void testFollowCreditoNoSolicitud() {
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setSolicitud(null);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));

        CreditoEntity result = sistemaService.followCredito(userId);

        assertNull(result);
    }
    //---------------------------------------------------------------------------//
    //---------------------------------------------------------------------------//
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
    public void testCalcularCostosTotales2() {
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setId(userId);
        CreditoEntity solicitud = new CreditoEntity();
        solicitud.setMontop(100000);
        solicitud.setPlazo(10);
        solicitud.setIntanu(5.0);
        solicitud.setIntmen(0.004);
        solicitud.setSegudesg(0.01);
        solicitud.setSeguince(0.02);
        solicitud.setComiad(0.03);
        usuario.setSolicitud(solicitud);
        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));

        List<Double> costosTotales = sistemaService.calcularCostosTotales(userId);

        assertNotNull(costosTotales);
        assertEquals(6, costosTotales.size());
    }

    @Test
    public void testCalcularCostosTotales3() {
        // Configurar mocks y datos de prueba
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setId(userId);
        CreditoEntity solicitud = new CreditoEntity();
        solicitud.setMontop(100000);
        solicitud.setPlazo(10);
        solicitud.setIntanu(0.05);
        solicitud.setIntmen(0.004);
        solicitud.setSegudesg(0.001);
        solicitud.setSeguince(0.002);
        solicitud.setComiad(0.003);
        usuario.setSolicitud(solicitud);
        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));

        // Llamar al método a probar
        List<Double> resultados = sistemaService.calcularCostosTotales(userId);

        // Verificar resultados
        assertNotNull(resultados);
        assertEquals(6, resultados.size());
    }

    @Test
    public void testCalcularCostosTotales4() {
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setId(userId);
        CreditoEntity solicitud = null;
        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));

        List<Double> costosTotales = sistemaService.calcularCostosTotales(userId);

        assertNull(costosTotales);
    }

    @Test
    public void testCalcularCostosTotales5() {
        // Configurar mocks y datos de prueba
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setId(userId);
        CreditoEntity solicitud = new CreditoEntity();
        solicitud.setMontop(100000);
        solicitud.setPlazo(10);
        solicitud.setIntanu(0.05); // Tasa de interés anual
        solicitud.setIntmen(11.004); // Tasa de interés mensual
        solicitud.setSegudesg(0.001);
        solicitud.setSeguince(0.002);
        solicitud.setComiad(0.003);
        usuario.setSolicitud(solicitud);
        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));

        // Llamar al método a probar
        List<Double> resultados = sistemaService.calcularCostosTotales(userId);

        // Verificar resultados
        assertNull(resultados);
    }

    @Test
    public void testCalcularCostosTotalesUsuarioNoEncontrado() {
        Long userId = 1L;
        when(usuarioRepository.findById(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            sistemaService.calcularCostosTotales(userId);
        });

        assertEquals("ERROR: USUARIO NO ENCONTRADO", exception.getMessage());
    }


    //---------------------------------------------------------------------------//
    //---------------------------------------------------------------------------//
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
    public void testGetNotifications2() {
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        List<String> notifications = Arrays.asList("Notification 1", "Notification 2");
        usuario.setNotifications(notifications);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));

        List<String> result = sistemaService.getNotifications(userId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Notification 1", result.get(0));
        assertEquals("Notification 2", result.get(1));

        verify(usuarioRepository, times(1)).findById(userId);
    }
    //---------------------------------------------------------------------------//
    //---------------------------------------------------------------------------//
    @Test
    void testUpdateState() {
        UsuarioEntity usuario = new UsuarioEntity();
        CreditoEntity credito = new CreditoEntity();
        usuario.setSolicitud(credito);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        int result = sistemaService.updateState(1L, 1);
        assertThat(result).isEqualTo(0);
    }

    @Test
    public void testUpdateState2() {
        // Configurar mocks y datos de prueba
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setId(userId);
        CreditoEntity solicitud = new CreditoEntity();
        usuario.setSolicitud(solicitud);
        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));

        // Llamar al método a probar
        int result = sistemaService.updateState(userId, 1);

        // Verificar resultados
        assertEquals(0, result);
        assertEquals("EN REVISION INICIAL", solicitud.getState());
        verify(creditoRepository, times(1)).save(any(CreditoEntity.class));
        verify(usuarioRepository, times(1)).save(any(UsuarioEntity.class));
    }
    @Test
    public void testUpdateState3() {
        // Configurar mocks y datos de prueba
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setId(userId);
        CreditoEntity solicitud = null;
        usuario.setSolicitud(solicitud);
        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));

        // Llamar al método a probar
        int result = sistemaService.updateState(userId, 1);

        // Verificar resultados
        assertEquals(-1, result);

    }

    @Test
    public void testUpdateStateCaso1() {
        // Arrange
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        CreditoEntity solicitud = new CreditoEntity();
        usuario.setSolicitud(solicitud);
        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));

        // Act
        int result = sistemaService.updateState(userId, 1);

        // Assert
        assertEquals(0, result);
        assertEquals("EN REVISION INICIAL", solicitud.getState());
        assertTrue(usuario.getNotifications().contains("ESTADO DE SOLICITUD ACTUALIZADO: SU SOLICITUD SE ENCUENTRA EN EL ESTADO: EN REVISIÓN INICIAL"));
        verify(creditoRepository, times(1)).save(any(CreditoEntity.class));
        verify(usuarioRepository, times(1)).save(any(UsuarioEntity.class));
    }

    @Test
    public void testUpdateStateCaso2() {
        // Arrange
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        CreditoEntity solicitud = new CreditoEntity();
        usuario.setSolicitud(solicitud);
        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));

        // Act
        int result = sistemaService.updateState(userId, 2);

        // Assert
        assertEquals(0, result);
        assertEquals("PENDIENTE DE DOCUMENTACION", solicitud.getState());
        assertTrue(usuario.getNotifications().contains("ESTADO DE SOLICITUD ACTUALIZADO: SU SOLICITUD SE ENCUENTRA EN EL ESTADO: PENDIENTE DE DOCUMENTACIÓN"));
        verify(creditoRepository, times(1)).save(any(CreditoEntity.class));
        verify(usuarioRepository, times(1)).save(any(UsuarioEntity.class));
    }

    @Test
    public void testUpdateStateCaso3() {
        // Arrange
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        CreditoEntity solicitud = new CreditoEntity();
        usuario.setSolicitud(solicitud);
        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));

        // Act
        int result = sistemaService.updateState(userId, 3);

        // Assert
        assertEquals(0, result);
        assertEquals("EN EVALUACION", solicitud.getState());
        assertTrue(usuario.getNotifications().contains("ESTADO DE SOLICITUD ACTUALIZADO: SU SOLICITUD SE ENCUENTRA EN EL ESTADO: EN EVALUACIÓN"));
        verify(creditoRepository, times(1)).save(any(CreditoEntity.class));
        verify(usuarioRepository, times(1)).save(any(UsuarioEntity.class));
    }

    @Test
    public void testUpdateStateCaso4() {
        // Arrange
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        CreditoEntity solicitud = new CreditoEntity();
        usuario.setSolicitud(solicitud);
        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));

        // Act
        int result = sistemaService.updateState(userId, 4);

        // Assert
        assertEquals(0, result);
        assertEquals("PRE-APROBADA", solicitud.getState());
        assertTrue(usuario.getNotifications().contains("ESTADO DE SOLICITUD ACTUALIZADO: SU SOLICITUD SE ENCUENTRA EN EL ESTADO: PRE-APROBADA"));
        verify(creditoRepository, times(1)).save(any(CreditoEntity.class));
        verify(usuarioRepository, times(1)).save(any(UsuarioEntity.class));
    }

    @Test
    public void testUpdateStateCaso5() {
        // Arrange
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        CreditoEntity solicitud = new CreditoEntity();
        usuario.setSolicitud(solicitud);
        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));

        // Act
        int result = sistemaService.updateState(userId, 5);

        // Assert
        assertEquals(0, result);
        assertEquals("EN APROBACION FINAL", solicitud.getState());
        assertTrue(usuario.getNotifications().contains("ESTADO DE SOLICITUD ACTUALIZADO: SU SOLICITUD SE ENCUENTRA EN EL ESTADO: EN APROBACIÓN FINAL"));
        verify(creditoRepository, times(1)).save(any(CreditoEntity.class));
        verify(usuarioRepository, times(1)).save(any(UsuarioEntity.class));
    }

    @Test
    public void testUpdateStateCaso6() {
        // Arrange
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        CreditoEntity solicitud = new CreditoEntity();
        usuario.setSolicitud(solicitud);
        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));

        // Act
        int result = sistemaService.updateState(userId, 6);

        // Assert
        assertEquals(0, result);
        assertEquals("APROBADA", solicitud.getState());
        assertTrue(usuario.getNotifications().contains("ESTADO DE SOLICITUD ACTUALIZADO: SU SOLICITUD SE ENCUENTRA EN EL ESTADO: APROBADA"));
        verify(creditoRepository, times(1)).save(any(CreditoEntity.class));
        verify(usuarioRepository, times(1)).save(any(UsuarioEntity.class));
    }

    @Test
    public void testUpdateStateCaso7() {
        // Arrange
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        CreditoEntity solicitud = new CreditoEntity();
        usuario.setSolicitud(solicitud);
        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));

        // Act
        int result = sistemaService.updateState(userId, 7);

        // Assert
        assertEquals(0, result);
        assertEquals("RECHAZADA", solicitud.getState());
        assertTrue(usuario.getNotifications().contains("ESTADO DE SOLICITUD ACTUALIZADO: SU SOLICITUD SE ENCUENTRA EN EL ESTADO: RECHAZADA"));
        verify(creditoRepository, times(1)).save(any(CreditoEntity.class));
        verify(usuarioRepository, times(1)).save(any(UsuarioEntity.class));
    }

    @Test
    public void testUpdateStateCaso8() {
        // Arrange
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        CreditoEntity solicitud = new CreditoEntity();
        usuario.setSolicitud(solicitud);
        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));

        // Act
        int result = sistemaService.updateState(userId, 8);

        // Assert
        assertEquals(0, result);
        assertEquals("CANCELADA POR EL CLIENTE", solicitud.getState());
        assertTrue(usuario.getNotifications().contains("ESTADO DE SOLICITUD ACTUALIZADO: SU SOLICITUD SE ENCUENTRA EN EL ESTADO: CANCELADA POR EL CLIENTE"));
        verify(creditoRepository, times(1)).save(any(CreditoEntity.class));
        verify(usuarioRepository, times(1)).save(any(UsuarioEntity.class));
    }
    /*
    @Test
    public void testUpdateStateToDesemboloso() {
        // Configurar el usuario y la solicitud
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setId(userId);
        CreditoEntity solicitud = new CreditoEntity();
        solicitud.setState("PENDIENTE");
        usuario.setSolicitud(solicitud);

        // Configurar los mocks
        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(creditoRepository.save(any(CreditoEntity.class))).thenReturn(solicitud);

        // Llamar al método a probar
        sistemaService.updateState(userId, 9);

        // Verificar que se llamaron a los métodos correctos
        verify(usuarioRepository, times(1)).findById(userId);
        verify(creditoRepository, times(2)).save(any(CreditoEntity.class));
        verify(usuarioRepository, times(1)).save(any(UsuarioEntity.class));

        // Verificar el estado de la solicitud
        assertEquals("EN DESEMBOLOSO", solicitud.getState());
    }
    */
    @Test
    public void testUpdateStateCaso10() {
        // Arrange
        Long userId = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        CreditoEntity solicitud = new CreditoEntity();
        usuario.setSolicitud(solicitud);
        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));

        // Act
        int result = sistemaService.updateState(userId, 10);

        // Assert
        assertEquals(0, result);
        assertEquals("PENDIENTE", solicitud.getState());
        assertTrue(usuario.getNotifications().contains("ESTADO DE SOLICITUD ACTUALIZADO: SU SOLICITUD SE ENCUENTRA EN EL ESTADO: PENDIENTE"));
        verify(creditoRepository, times(1)).save(any(CreditoEntity.class));
        verify(usuarioRepository, times(1)).save(any(UsuarioEntity.class));
    }
}