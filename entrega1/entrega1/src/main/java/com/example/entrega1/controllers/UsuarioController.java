package com.example.entrega1.controllers;
//------------------------------[IMPORTS DE SERVICO]-------------------------------------//
import com.example.entrega1.entities.CreditoEntity;
import com.example.entrega1.entities.UsuarioEntity;
import com.example.entrega1.repositories.UsuarioRepository;
import com.example.entrega1.services.UsuarioService;

import com.example.entrega1.services.SistemaService;

import com.example.entrega1.repositories.CreditoRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
//---------------------------------[IMPORTS DE SERVICO]----------------------------------------//

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuario")
@CrossOrigin("*")

//------------------------------[IMPORTS DE SERVICO]-------------------------------------//
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    CreditoRepository creditoRepository;
    @Autowired
    private SistemaService sistemaService;
    //-------------------------------------------------------------------------------------------//
    //-----------------------------------[GETERS]----------------------------------------------//
    // * OBTENER TODOS LOS CLIENTES
    @GetMapping("/all") // -> VER SI NECESITA CAMBIO, ->  "/"
    public ResponseEntity<List<UsuarioEntity>> listAllUsuario() {
        List<UsuarioEntity> usuarios = usuarioService.getUsuarios();
        return ResponseEntity.ok(usuarios);
    }
    //-------------------------------------------------------------------------------------------//
    // * OBTENER CLIENTE POR ID
    @GetMapping("/find/{id}")
    public ResponseEntity<UsuarioEntity> getUsuarioById(@PathVariable Long id) {
        UsuarioEntity valorid = usuarioService.getUsuarioById(id);
        return ResponseEntity.ok(valorid);
    }
    //-------------------------------------------------------------------------------------------//
    // * OBTENER CLIENTE POR RUT
    @GetMapping("/find/rut/{rut}")
    public ResponseEntity<List<UsuarioEntity>> getUsuarioByRut(@PathVariable String rut) {
        List<UsuarioEntity> ruts = usuarioService.getUsuarioByRut(rut);
        return ResponseEntity.ok(ruts);
    }
    //-------------------------------------------------------------------------------------------//
    // * OBTENER CLIENTE POR OBJETIVO
    @GetMapping("/find/objective/{objective}")
    public ResponseEntity<List<UsuarioEntity>> getUsuarioByObjective(@PathVariable String objective) {
        List<UsuarioEntity> objectives = usuarioService.getUsuarioByObjective(objective);
        return ResponseEntity.ok(objectives);
    }
    //-------------------------------------------------------------------------------------------//
    //------------------------------------[POST]-----------------------------------------------//
    // * GUARDADO DE USUARIOS
    @PostMapping("/save")
    public ResponseEntity<?> saveUsuario(@RequestBody UsuarioEntity usuario) {
        if (usuario.getHouses() < 0) {
            return ResponseEntity.badRequest().body("EL VALOR DE CANTIDAD DE CASAS TIENE QUE SER MAYOR O IGUAL QUE: 0");
        }
        if (usuario.getIngresos() < 0) {
            return ResponseEntity.badRequest().body("EL VALOR DE INGRESOS TIENE QUE SER MAYOR O IGUAL QUE: 0");
        }
        return ResponseEntity.ok(usuarioService.saveUsuario(usuario));
    }
    //------------------------------------------------------------------------------------------//
    //------------------------------------[PUT]------------------------------------------------//
    // * ACTUALIZACIÓN DE USUARIOS
    @PutMapping("/update")
    public ResponseEntity<UsuarioEntity> updateUsuario(
            @RequestParam Long id,
            @RequestParam int valorpropiedad,
            @RequestParam int ingresos,
            @RequestParam int sumadeuda,
            @RequestParam String objective) {
        try {
            UsuarioEntity updatedUsuario = usuarioService.updateUsuario(id, valorpropiedad, ingresos, sumadeuda, objective);
            return ResponseEntity.ok(updatedUsuario);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
    //-------------------------------------------------------------------------------------------//
    //------------------------------------[DELETE]---------------------------------------------//
    // * ELIMINACIÓN DE USUARIOS
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable Long id) {
        try {
            usuarioService.deleteUsuario(id);
            return ResponseEntity.ok("USUARIO ELIMINADO CON id: " + id);
        }
        catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }
    // * ELIMINAR TODO CLIENTE
    @DeleteMapping("/delete/all")
    public ResponseEntity<?> deleteAllUsuario() {
        try {
            usuarioRepository.deleteAll();
            return ResponseEntity.ok("TODOS LOS USUARIOS HAN SIDO ELIMINADOS");
        }
        catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }
    //------------------------------------------------------------------------------------------//
    //----------------[P1]- FUNCIONES DE CALCULO DE CRÉDITO HIPOTECARIO-----------------//
    // + SIMULACIÓN DEL CALCULO DE MONTO MENSUAL:
    @GetMapping("/calcularMontoMensual")
    public ResponseEntity<Double> calcularMontoMensual(@RequestParam("rut") String rut,
        @RequestParam("P") double P,
        @RequestParam("r") double r,
        @RequestParam("n") double n,
        @RequestParam("V") double V) {
        /*
        P // MONTO PRESTAMO
        r // TASA DE INTERES ANUAL
        n // INGRESE PLAZO EN AÑOS 
        V // VALOR DE LA PROPIEDAD
        */
        if (rut == null || rut.isEmpty() || !usuarioRepository.existsByRut(rut)) {
            System.out.println("ERROR: EL RUT INGRESADO NO SE ENCUENTRA REGISTRADO EN EL SISTEMA, POR FAVOR INGRESAR UN RUT REGISTRADO O REGISTRARSE EN EL SISTEMA");
            double Er = -2;
            return ResponseEntity.ok(Er);
        }
        double Resultado = sistemaService.Credito_Hipotecario(rut, P, r, n, V);
        if (Resultado == 0) {
            System.out.println("ERROR: NO SE PUDO CALCULAR EL MONTO MENSUAL");
            return ResponseEntity.ok(Resultado = -1);
        }else {
            return ResponseEntity.ok(Resultado);
        }
    }
    //-----------------------[P2]- FUNCIONES DE REGISTRO DE USUARIO-------------------------//
    // + REGISTRO DE USUARIOS EN BASE DE DATOS:
    @PostMapping("/register")
    public ResponseEntity<?> registerUsuario(@RequestBody UsuarioEntity usuario) {
        if(usuarioRepository.existsByRut(usuario.getRut())) {
            double Er = -2;
            //UsuarioEntity Resultado = null;
            return ResponseEntity.ok(Er);
            //return ResponseEntity.badRequest().body("EL RUT YA EXISTE");
        }
        try {
            UsuarioEntity registeredUsuario = sistemaService.registerUsuario(
                    usuario.getRut(),
                    usuario.getName(),
                    usuario.getAge(),
                    usuario.getWorkage(),
                    usuario.getHouses(),
                    usuario.getValorpropiedad(),
                    usuario.getIngresos(),
                    usuario.getSumadeuda(),
                    usuario.getObjective(), // Puede ser nulo
                    usuario.getIndependiente(),
                    usuario.getAhorros(),
                    usuario.getCreditos()
            );
            // SI EXISTE UN CREDITO QUE NO SE ENCUENTRE APROBADO, LA SOLICITUD SERÁ RECHAZADA EN EL FRONT-END
            for (CreditoEntity credito : registeredUsuario.getCreditos()) {
                if (!credito.getState().equals("APROBADO")) {
                    return ResponseEntity.ok(-1);
                }
            }
            return ResponseEntity.ok(registeredUsuario);
        }
        catch (DataIntegrityViolationException e) {
            e.printStackTrace(); // Log the exception
            return ResponseEntity.badRequest().body("ERROR DE INTEGRIDAD DE DATOS");
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception
            return ResponseEntity.status(500).body("ERROR INTERNO DEL SERVIDOR");
        }
    }
    //----------------[P3]- FUNCIONES DE SOLICITÚD DE CRÉDITO-----------------//
    // + SOLICITUD DE CRÉDITO HIPOTECARIO PARA UN USUARIO POR ID:
    @PostMapping("/solicitarCredito")
    public ResponseEntity<?> createSolicitud(
            @RequestParam("userId") Long userId,
            @RequestParam("montop") double montop,
            @RequestParam("plazo") int plazo,
            @RequestParam("intanu") double intanu,
            @RequestParam("intmen") double intmen,
            @RequestParam("segudesg") double segudesg,
            @RequestParam("seguince") double seguince,
            @RequestParam("comiad") double comiad,
            @RequestParam("comprobanteIngresos") MultipartFile comprobanteIngresos,
            @RequestParam("certificadoAvaluo") MultipartFile certificadoAvaluo,
            @RequestParam(value = "historialCrediticio", required = false) MultipartFile historialCrediticio,
            @RequestParam(value = "escrituraPrimeraVivienda", required = false) MultipartFile escrituraPrimeraVivienda,
            @RequestParam(value = "planNegocios", required = false) MultipartFile planNegocios,
            @RequestParam(value = "estadosFinancieros", required = false) MultipartFile estadosFinancieros,
            @RequestParam(value = "presupuestoRemodelacion", required = false) MultipartFile presupuestoRemodelacion,
            @RequestParam("dicom") MultipartFile dicom) {
        // Validar RUT
        if (userId == null || !usuarioRepository.existsById(userId)) {
            //return ResponseEntity.badRequest().body("ERROR: EL ID DE USUARIO INGRESADO NO SE ENCUENTRA REGISTRADO EN EL SISTEMA, POR FAVOR INGRESAR UN ID REGISTRADO O REGISTRARSE EN EL SISTEMA");
            return ResponseEntity.ok(-2);
        }
        try {
            // Llamar a la función createSolicitud de SistemaService
            sistemaService.createSolicitud(
                    userId, montop, plazo, intanu, intmen, segudesg, seguince, comiad,
                    comprobanteIngresos.getBytes(), certificadoAvaluo.getBytes(),
                    historialCrediticio != null ? historialCrediticio.getBytes() : null,
                    escrituraPrimeraVivienda != null ? escrituraPrimeraVivienda.getBytes() : null,
                    planNegocios != null ? planNegocios.getBytes() : null,
                    estadosFinancieros != null ? estadosFinancieros.getBytes() : null,
                    presupuestoRemodelacion != null ? presupuestoRemodelacion.getBytes() : null,
                    dicom.getBytes());

            return ResponseEntity.ok("SOLICITUD DE CRÉDITO CREADA O ACTUALIZADA CORRECTAMENTE");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("ERROR INTERNO DEL SERVIDOR: " + e.getMessage());
        }
    }
    //----------------[P4]- FUNCIONES DE APROBACIÓN DE CRÉDITO-----------------//
    // + APROBACIÓN DE CRÉDITO HIPOTECARIO PARA UN USUARIO POR ID:
    @PostMapping("/aprobarCredito")
    public ResponseEntity<?> aprobarCredito(@RequestBody Map<String, Object> body) {
        Long userId = ((Number) body.get("userId")).longValue();
        if (userId == null || !usuarioRepository.existsById(userId)) {
            return ResponseEntity.ok(-2);
        }
        try {
            Map<String, Object> resultado = sistemaService.evaluateCredito(userId);
            if (resultado == null) {
                return ResponseEntity.ok(-1);
            }else{
                CreditoEntity solicitud = (CreditoEntity) resultado.get("solicitud");

                List<Map<String, String>> files = (List<Map<String, String>>) resultado.get("files");
                Map<String, Object> response = new HashMap<>();
                response.put("solicitud", solicitud);
                response.put("files", files);
                return ResponseEntity.ok(response);
            }

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //-----------------------[P5]- FUNCIONES DE SEGUIMENTO ---------------------------------------//
    // + SEGUIMIENTO DE CRÉDITO HIPOTECARIO PARA UN USUARIO POR ID:
    @PostMapping("/seguimiento")
    public ResponseEntity<?> seguimiento(@RequestBody Map<String, Object> body) {
        Long userId = ((Number) body.get("userId")).longValue();
        if (userId == null || !usuarioRepository.existsById(userId)) {
            return ResponseEntity.ok(-2);
        }
        try {
            return ResponseEntity.ok(sistemaService.followCredito(userId));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //----------------[P6]- FUNCIONES DE CALCULO DE COSTOS TOTALES-----------------//
    // + CALCULO DE COSTOS TOTALES PARA UN USUARIO POR ID:
    @PostMapping("/calcularCostosTotales")
    public ResponseEntity<?> calcularCostosTotales(@RequestBody Map<String, Object> body) {
        Long userId = ((Number) body.get("userId")).longValue();
        if (userId == null || !usuarioRepository.existsById(userId)) {
            return ResponseEntity.ok(-2);
        }
        try {
            return ResponseEntity.ok(sistemaService.calcularCostosTotales(userId));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //-----------------------[EXTRA]- FUNCIONES DE NOTIFICACIONES-------------------------------//
    // + OBTENER NOTIFICACIONES PARA UN USUARIO POR ID:
    @PostMapping("/notificaciones")
    public ResponseEntity<?> getNotificaciones(@RequestBody Map<String, Object> body) {
        Long userId = ((Number) body.get("userId")).longValue();
        if (userId == null || !usuarioRepository.existsById(userId)) {
            return ResponseEntity.ok(-2);
        }
        try {
            return ResponseEntity.ok(sistemaService.getNotifications(userId));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //-----------------------[EXTRA]- FUNCIONES DE ACTUALIZACIÓN DE ESTADOS-----------------//
    // + ACTUALIZAR ESTADO DE USUARIO POR ID:
    @PostMapping("/updateState")
    //updateState ingresa id y un int state
    public ResponseEntity<?> updateState(@RequestBody Map<String, Object> body) {
        Long userId = ((Number) body.get("userId")).longValue();
        int state = ((Number) body.get("state")).intValue();
        if (userId == null || !usuarioRepository.existsById(userId)) {
            return ResponseEntity.ok(-2);
        }
        try {
            return ResponseEntity.ok(sistemaService.updateState(userId, state));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
