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
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuario")
@CrossOrigin(origins = "*")

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
    public ResponseEntity<UsuarioEntity> updateUsuario(@RequestBody UsuarioEntity usuario) {
        return ResponseEntity.ok(usuarioService.updateUsuario(usuario));
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
    @GetMapping("/calcularMontoMensual")
    public ResponseEntity<Double> calcularMontoMensual(@RequestBody Map<String, Object> body) {
        String rut = (String) body.get("rut");
        double P = ((Number) body.get("P")).doubleValue();   // MONTO PRESTAMO
        double r = ((Number) body.get("r")).doubleValue();     // TASA DE INTERES ANUAL
        double n = ((Number) body.get("n")).doubleValue();    // INGRESE PLAZO EN AÑOS ->
        double V = ((Number) body.get("V")).doubleValue();    // VALOR DE LA PROPIEDAD
        if (rut == null || rut.isEmpty() || !usuarioRepository.existsByRut(rut)) {
            System.out.println("ERROR: EL RUT INGRESADO NO SE ENCUENTRA REGISTRADO EN EL SISTEMA, POR FAVOR INGRESAR UN RUT REGISTRADO O REGISTRARSE EN EL SISTEMA");
            return ResponseEntity.badRequest().body(null);
        }
        if (P <= 0 || r <= 0 || n <= 0 || V <= 0) {
            System.out.println("ERROR: LOS VALORES INGRESADOS NO PUEDEN SER NEGARIVOS O IGUAL A 0");
            return ResponseEntity.badRequest().body(null);
        }
        double Resultado = sistemaService.Credito_Hipotecario(rut, P, r, n, V);
        if (Resultado == 0) {
            System.out.println("ERROR: NO SE PUDO CALCULAR EL MONTO MENSUAL");
            return ResponseEntity.badRequest().body(null);
        }else {
            return ResponseEntity.ok(Resultado);
        }
    }
    /* EL JSON ES;
    {
      "rut": "12345678-9",
      "P": 50000,
      "r": 0.5,
      "n": 30,
      "V": 100000
    }
     */
    //-----------------------[P2]- FUNCIONES DE REGISTRO DE USUARIO-------------------------//
    @PostMapping("/register")
    public ResponseEntity<?> registerUsuario(@RequestBody UsuarioEntity usuario) {
        if(usuario.getRut() == null || usuario.getRut().isEmpty()) {
            return ResponseEntity.badRequest().body("POR FAVOR INGRESAR RUT");
        }
        if(usuarioRepository.existsByRut(usuario.getRut())) {
            return ResponseEntity.badRequest().body("EL RUT YA EXISTE");
        }
        if(usuario.getName() == null || usuario.getName().isEmpty()) {
            return ResponseEntity.badRequest().body("POR FAVOR INGRESAR NOMBRE");
        }
        if (usuario.getAge() == 0) {
            return ResponseEntity.badRequest().body("POR FAVOR INGRESAR VALOR DE EDAD");
        }
        if (usuario.getAge() < 0) {
            return ResponseEntity.badRequest().body("EL VALOR DE AÑOS DE TRABAJO TIENE QUE SER MAYOR O IGUAL QUE: 0");
        }
        if (usuario.getAge() <= 18) {
            return ResponseEntity.badRequest().body("EL VALOR DE LA EDAD TIENE QUE SER MAYOR QUE 18");
        }
        if(usuario.getDocuments() == null || usuario.getDocuments().isEmpty()) {
            return ResponseEntity.badRequest().body("POR FAVOR INGRESAR DOCUMENTO");
        }
        if(usuario.getIndependiente() == null || usuario.getIndependiente().isEmpty()) {
            return ResponseEntity.badRequest().body("POR FAVOR INGRESAR SI ES 'INDEPENDIENTE' O 'ASALARIADO'");
        }
        for (String document : usuario.getDocuments()) {
            if (!document.toLowerCase().endsWith(".pdf")) {
                return ResponseEntity.badRequest().body("TODOS LOS DOCUMENTOS DEBEN SER ARCHIVOS PDF");
            }
        }
        if (usuario.getHouses() < 0) {
            return ResponseEntity.badRequest().body("EL VALOR DE CANTIDAD DE CASAS TIENE QUE SER MAYOR O IGUAL QUE: 0");
        }
        if(usuario.getValorpropiedad() < 0) {
            return ResponseEntity.badRequest().body("EL VALOR DE LA PROPIEDAD TIENE QUE SER MAYOR O IGUAL QUE: 0");
        }
        if (usuario.getIngresos() < 0) {
            return ResponseEntity.badRequest().body("EL VALOR DE INGRESOS TIENE QUE SER MAYOR O IGUAL QUE: 0");
        }
        if (usuario.getSumadeuda() < 0){
            return ResponseEntity.badRequest().body("EL VALOR DE LAS DEUDA TOTALES TIENE QUE SER MAYOR O IGUAL QUE: 0");
        }
        try {
            UsuarioEntity registeredUsuario = sistemaService.registerUsuario(
                    usuario.getRut(),
                    usuario.getName(),
                    usuario.getAge(),
                    usuario.getWorkage(),
                    usuario.getDocuments(),
                    usuario.getHouses(),
                    usuario.getValorpropiedad(),
                    usuario.getIngresos(),
                    usuario.getSumadeuda(),
                    usuario.getObjective(), // Puede ser nulo
                    usuario.getIndependiente(),
                    usuario.getAhorros(),
                    usuario.getCreditos()
            );
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
    /*
    @PostMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<CreditEntity> applyForCredit(@PathVariable Long id,
    @RequestParam("proofOfIncome") MultipartFile file,
    @RequestParam(value = "appraisalCertificate", required = false) MultipartFile file2,
    @RequestParam(value = "creditHistory", required = false) MultipartFile file3,
    @RequestParam(value = "deedOfTheFirstHome", required = false) MultipartFile file4,
    @RequestParam(value = "financialStatusOfTheBusiness", required = false) MultipartFile file5,
    @RequestParam(value = "businessPlan", required = false) MultipartFile file6,
    @RequestParam(value = "remodelingBudget", required = false) MultipartFile file7,
    @RequestParam(value = "updatedAppraisalCertificate", required = false) MultipartFile file8,
    @RequestParam("monthlyIncome1") String monthlyIncome1,
    @RequestParam("monthlyIncome2") String monthlyIncome2,
    @RequestParam("monthlyIncome3") String monthlyIncome3,
    @RequestParam("monthlyIncome4") String monthlyIncome4,
    @RequestParam("monthlyIncome5") String monthlyIncome5,
    @RequestParam("monthlyIncome6") String monthlyIncome6,
    @RequestParam("monthlyIncome7") String monthlyIncome7,
    @RequestParam("monthlyIncome8") String monthlyIncome8,
    @RequestParam("monthlyIncome9") String monthlyIncome9,
    @RequestParam("monthlyIncome10") String monthlyIncome10,
    @RequestParam("monthlyIncome11") String monthlyIncome11,
    @RequestParam("monthlyIncome12") String monthlyIncome12,
    @RequestParam("requestedAmount") int requestedAmount,
    @RequestParam("loanTerm") int loanTerm,
    @RequestParam("annualInterestRate") double annualInterestRate,
    @RequestParam("typeOfLoan") String typeOfLoan,
    @RequestParam("creditsHistory") Boolean creditsHistory,
    @RequestParam("monthlyDebt") String monthlyDebt,
    @RequestParam("propertyAmount") int propertyAmount) {
        try {
            String monthlyIncome = monthlyIncome1 + "," + monthlyIncome2 + "," + monthlyIncome3 + "," + monthlyIncome4 + "," + monthlyIncome5 + "," + monthlyIncome6 + "," + monthlyIncome7 + "," + monthlyIncome8 + "," + monthlyIncome9 + "," + monthlyIncome10 + "," + monthlyIncome11 + "," + monthlyIncome12;
            CreditEntity credit = new CreditEntity(id, monthlyIncome, requestedAmount, loanTerm, annualInterestRate, typeOfLoan, creditsHistory, monthlyDebt, propertyAmount);
            byte[] pdfBytes = file.getBytes();
            credit.setProofOfIncome(pdfBytes);
            byte[] pdfBytes2 = null;
            if (file2 != null && !file2.isEmpty()) {
                pdfBytes2 = file2.getBytes();
            }
            credit.setAppraisalCertificate(pdfBytes2);
            byte[] pdfBytes3 = null;
            if (file3 != null && !file3.isEmpty()) {
                pdfBytes3 = file3.getBytes();
            }
            credit.setCreditHistory(pdfBytes3);
            byte[] pdfBytes4 = null;
            if (file4 != null && !file4.isEmpty()) {
                pdfBytes4 = file4.getBytes();
            }
            credit.setDeedOfTheFirstHome(pdfBytes4);
            byte[] pdfBytes5 = null;
            if (file5 != null && !file5.isEmpty()) {
                pdfBytes5 = file5.getBytes();
            }
            credit.setFinancialStatusOfTheBusiness(pdfBytes5);
            byte[] pdfBytes6 = null;
            if (file6 != null && !file6.isEmpty()) {
                pdfBytes6 = file6.getBytes();
            }
            credit.setBusinessPlan(pdfBytes6);
            byte[] pdfBytes7 = null;
            if (file7 != null && !file7.isEmpty()) {
                pdfBytes7 = file7.getBytes();
            }
            credit.setRemodelingBudget(pdfBytes7);
            byte[] pdfBytes8 = null;
            if (file8 != null && !file8.isEmpty()) {
                pdfBytes8 = file8.getBytes();
            }
            credit.setCreditsHistory(creditsHistory);
            credit.setUpdatedAppraisalCertificate(pdfBytes8);
            credit.setAppraisalCertificate(pdfBytes2);
            CreditEntity creditSaved = creditService.applicationStatus(credit);
            return ResponseEntity.ok(creditSaved);
        } catch (Exception e) {
            // If the user is not found, return null.
            return null;

     */





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
            return ResponseEntity.badRequest().body("ERROR: EL ID DE USUARIO INGRESADO NO SE ENCUENTRA REGISTRADO EN EL SISTEMA, POR FAVOR INGRESAR UN ID REGISTRADO O REGISTRARSE EN EL SISTEMA");
        }

        // Validar montos
        if (montop <= 0 || plazo <= 0 || intanu <= 0 || intmen <= 0 || segudesg < 0 || seguince < 0 || comiad < 0) {
            return ResponseEntity.badRequest().body("ERROR: LOS VALORES INGRESADOS NO PUEDEN SER NEGATIVOS O IGUAL A 0");
        }

        // Validar archivos obligatorios
        if (comprobanteIngresos == null || comprobanteIngresos.isEmpty() ||
                certificadoAvaluo == null || certificadoAvaluo.isEmpty() ||
                dicom == null || dicom.isEmpty()) {
            return ResponseEntity.badRequest().body("ERROR: LOS ARCHIVOS COMPROBANTE DE INGRESOS, CERTIFICADO DE AVALÚO Y DICOM SON OBLIGATORIOS");
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
    @PostMapping("/aprobarCredito")
    public ResponseEntity<?> aprobarCredito(@RequestBody Map<String, Object> body) {
        Long userId = ((Number) body.get("userId")).longValue();
        if (userId == null || !usuarioRepository.existsById(userId)) {
            return ResponseEntity.badRequest().body("ERROR: EL ID DE USUARIO INGRESADO NO SE ENCUENTRA REGISTRADO EN EL SISTEMA, POR FAVOR INGRESAR UN ID REGISTRADO O REGISTRARSE EN EL SISTEMA");
        }
        try {
            sistemaService.evaluateCredito(userId);
            return ResponseEntity.ok("REVISION DE CRÉDITO REALIZADA CORRECTAMENTE");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //----------------[P6]- FUNCIONES DE CALCULO DE COSTOS TOTALES-----------------//
    @PostMapping("/calcularCostosTotales")
    public ResponseEntity<?> calcularCostosTotales(@RequestBody Map<String, Object> body) {
        Long userId = ((Number) body.get("userId")).longValue();
        if (userId == null || !usuarioRepository.existsById(userId)) {
            return ResponseEntity.badRequest().body("ERROR: EL ID DE USUARIO INGRESADO NO SE ENCUENTRA REGISTRADO EN EL SISTEMA, POR FAVOR INGRESAR UN ID REGISTRADO O REGISTRARSE EN EL SISTEMA");
        }
        try {
            sistemaService.calcularCostosTotales(userId);
            return ResponseEntity.ok("COSTOS TOTALES CALCULADOS CORRECTAMENTE");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
