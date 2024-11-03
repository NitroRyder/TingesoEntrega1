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

        for(int i = 0; i < usuario.getNotifications().size(); i++){
            System.out.println("NOTIFICATIONS: " + usuario.getNotifications().get(i));
        }
        System.out.println("-------------------------------------------------------------------------------------------------");
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

        for(int i = 0; i < usuario.getNotifications().size(); i++){
            System.out.println("NOTIFICATIONS: " + usuario.getNotifications().get(i));
        }
        System.out.println("-------------------------------------------------------------------------------------------------");
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
        // imprimir ahorros del ususario
        for (AhorrosEntity ahorro : ahorros) {
            System.out.println("Tipo: " + ahorro.getTipo() + ", Transacción: " + ahorro.getTransaccion());
        }

        // Act
        when(usuarioService.obtenerValorPositivoMasPequeno(ahorros)).thenReturn(20000); // SIEMPRE Y CUANDO LA FUNCIÓN RETORNE
        Map<String, Object> result = sistemaService.evaluateCredito(userId);

        for(int i = 0; i < usuario.getNotifications().size(); i++){
            System.out.println("NOTIFICATIONS: " + usuario.getNotifications().get(i));
        }
        System.out.println("-------------------------------------------------------------------------------------------------");
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
    }

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

        for (int i = 0; i < usuario.getNotifications().size(); i++) {
            System.out.println("NOTIFICATIONS: " + usuario.getNotifications().get(i));
        }
        System.out.println("-------------------------------------------------------------------------------------------------");
        // Assert
        assertNotNull(result);
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

        for (int i = 0; i < usuario.getNotifications().size(); i++) {
            System.out.println("NOTIFICATIONS: " + usuario.getNotifications().get(i));
        }
        System.out.println("-------------------------------------------------------------------------------------------------");
        // Assert
        assertNotNull(result);
        //assertEquals("RECHAZADA", newSolicitud.getState());
    }

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

        for (int i = 0; i < usuario.getNotifications().size(); i++) {
            System.out.println("NOTIFICATIONS: " + usuario.getNotifications().get(i));
        }
        System.out.println("-------------------------------------------------------------------------------------------------");
        // Assert
        assertNotNull(result);
    }

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

        for (int i = 0; i < usuario.getNotifications().size(); i++) {
            System.out.println("NOTIFICATIONS: " + usuario.getNotifications().get(i));
        }
        System.out.println("-------------------------------------------------------------------------------------------------");
        // Assert
        assertNotNull(result);
    }

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

        for (int i = 0; i < usuario.getNotifications().size(); i++) {
            System.out.println("NOTIFICATIONS: " + usuario.getNotifications().get(i));
        }
        System.out.println("-------------------------------------------------------------------------------------------------");
        // Assert
        assertNotNull(result);
    }

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

        for (int i = 0; i < usuario.getNotifications().size(); i++) {
            System.out.println("NOTIFICATIONS: " + usuario.getNotifications().get(i));
        }
        System.out.println("-------------------------------------------------------------------------------------------------");
        // Assert
        assertNotNull(result);
    }

    @Test
    public void testEvaluateCreditoRemodelacion3() {
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
        when(usuarioService.obtenerValorPositivoMasPequeno(ahorros)).thenReturn(500000); // SIEMPRE Y CUANDO LA FUNCIÓN RETORNE
        Map<String, Object> result = sistemaService.evaluateCredito(userId);

        for (int i = 0; i < usuario.getNotifications().size(); i++) {
            System.out.println("NOTIFICATIONS: " + usuario.getNotifications().get(i));
        }
        System.out.println("-------------------------------------------------------------------------------------------------");
        // Assert
        assertNotNull(result);
    }

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
    void testGetNotifications() {
        UsuarioEntity usuario = new UsuarioEntity();
        List<String> notifications = new ArrayList<>();
        usuario.setNotifications(notifications);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        List<String> result = sistemaService.getNotifications(1L);
        assertThat(result).isNotNull();
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
        assertEquals("EN REVISIÓN INICIAL", solicitud.getState());
        verify(creditoRepository, times(1)).save(any(CreditoEntity.class));
        verify(usuarioRepository, times(1)).save(any(UsuarioEntity.class));
    }
}