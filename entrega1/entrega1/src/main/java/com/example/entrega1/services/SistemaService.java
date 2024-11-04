package com.example.entrega1.services;

// ELABORAR FUNCIONES A SOLICTADAS POR ACÁ -> CORE DE LA LOGICA DE NEGOCIOS
// -> PROCURAR DE QUE ESTÉ BIEN REALIZADO
// -> ESTE SERVICIO SOLO USA GETS PARA CALCULAR LAS COSAS
import com.example.entrega1.entities.AhorrosEntity;
import com.example.entrega1.entities.UsuarioEntity;
import com.example.entrega1.entities.CreditoEntity;
import com.example.entrega1.repositories.UsuarioRepository;
import com.example.entrega1.repositories.CreditoRepository;
import com.example.entrega1.repositories.AhorrosRepository;
import com.example.entrega1.services.AhorrosService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

//-------------------------------[IMPORTS DE REPOSITORIO]--------------------------------------//
@Service
//------------------------------[INTERFACE DE REPOSITORIO]-------------------------------------//
public class SistemaService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private CreditoRepository creditoRepository;
    @Autowired
    private AhorrosService ahorrosService;
    //----------------[P1]- FUNCIONES DE CALCULO DE CRÉDITO HIPOTECARIO-----------------// -------------> REVIZAR
    public double Credito_Hipotecario(String rut, double P, double r, double n, double V) {
        // P = MONTO DEL PRESTAMO
        // r = TASA DE INTERES ANUAL
        // n = PLAZO DEL PRESTAMO EN AÑOS
        // V = VALOR ACTUAL DE LA PROPIEDAD
        //-------------------------------------------------------------------------//
        if(n <= 30 && 0.035<= r && r <=0.05 && P <= V*0.8){ // Primera Vivienda
            System.out.println("PRÉSTAMO REALIZADO: Primera Vivienda");
            //System.out.println("LOS DOCUMENTOS A INGRESAR PARA ESTE TIPO DE PRÉSTAMO SON:");
            //System.out.println("1.- Comprobante de ingresos");
            //System.out.println("2.- Certificado de avalúo");
            //System.out.println("3.- Historial crediticio");
            r = r / 12 / 100; // PASA DE TASA ANUAL A MENSUAL
            n = n * 12;        // PASA DE AÑOS A CANTIDAD DE PAGOS
            double M = P * (r * Math.pow(1 + r, n)) / (Math.pow(1 + r, n) - 1);
            return M;
        //-------------------------------------------------------------------------//
        } else if(n <= 20 && 0.04<= r && r <=0.06 && P <= V*0.7){ //Segunda Vivienda
            System.out.println("PRESTAMO REALIZADO: Segunda Vivienda");
            //System.out.println("LOS DOCUMENTOS A INGRESAR PARA ESTE TIPO DE PRÉSTAMO SON:");
            //System.out.println("1.- Comprobante de ingresos");
            //System.out.println("2.- Certificado de avalúo");
            //System.out.println("3.- Escritura de la primera vivienda");
            //System.out.println("4.- Historial crediticio");
            r = r / 12 / 100;
            n = n * 12;
            double M = P * (r * Math.pow(1 + r, n)) / (Math.pow(1 + r, n) - 1);
            return M;
        //-------------------------------------------------------------------------//
        } else if(n <= 25 && 0.05<= r && r <=0.07 && P <= V*0.6){ //Propiedades Comerciales
            System.out.println("PRESTAMO REALIZADO: Propiedades Comerciales");
            //System.out.println("LOS DOCUMENTOS A INGRESAR PARA ESTE TIPO DE PRÉSTAMO SON:");
            //System.out.println("1.-Estado financiero del negocio");
            //System.out.println("2.- Comprobante de ingresos");
            //System.out.println("3.- Certificado de avalúo");
            //System.out.println("4.- Plan de negocios");
            r = r / 12 / 100;
            n = n * 12;
            double M = P * (r * Math.pow(1 + r, n)) / (Math.pow(1 + r, n) - 1);
            return M;
        //-------------------------------------------------------------------------//
        } else if(n <=15 && 0.045<= r && r <=0.06 && P<= V*0.5){ //Remodelación
            System.out.println("PRESTAMO REALIZADO: Remodelación");
            //System.out.println("LOS DOCUMENTOS A INGRESAR PARA ESTE TIPO DE PRÉSTAMO SON:");
            //System.out.println("1.- Comprobante de ingresos");
            //System.out.println("2.- Presupuesto de la remodelación\n");
            //System.out.println("3.- Certificado de avalúo actualizado");
            r = r / 12 / 100;
            n = n * 12;
            double M = P * (r * Math.pow(1 + r, n)) / (Math.pow(1 + r, n) - 1);
            return M;
        //-------------------------------------------------------------------------//
        }else{
            //System.out.println("TOMA ESTE 0");
            return 0;
        }
    }
    //-----------------------[P2]- FUNCIONES DE REGISTRO DE USUARIO-------------------------//
    public UsuarioEntity registerUsuario(String rut, String name, int age, int workage, int houses, int valorpropiedad, int ingresos, int sumadeuda, String objective, String independiente, List<AhorrosEntity> ahorros, List<CreditoEntity> creditos) {
        // Eliminar los puntos del RUT
        String cleanedRut = rut.replace(".", "");
        //-------------------------------------------------------------------------//
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setRut(cleanedRut.toUpperCase());
        usuario.setName(name.toUpperCase());
        usuario.setAge(age);
        usuario.setWorkage(workage);
        usuario.setHouses(houses);
        usuario.setValorpropiedad(valorpropiedad);
        usuario.setIngresos(ingresos);
        usuario.setSumadeuda(sumadeuda);
        usuario.setObjective(objective.toUpperCase());
        usuario.setIndependiente(independiente.toUpperCase());
        //-------------------------------------------------------------------------//
        // SETEO DE AHORROS // SI NO HAY, NO INGRESA NADA
        for (AhorrosEntity ahorro : ahorros) {
            ahorro.setUsuario(usuario);
        }
        usuario.setAhorros(ahorros);
        usuario.setSolicitud(null);
        //-------------------------------------------------------------------------//
        // SETEO DE HISTORIAL DE CREDITOS // SI NO HAY, NO INGRESA NADA // DÁ LO MISMO SI LOS ARCHIVOS FUERON INGRESADOS O NO EN EL HISTORIAL
        if (creditos != null) {
            for (CreditoEntity credito : creditos) {
                credito.setUsuario(usuario);
                credito.setState(credito.getState().toUpperCase());
            }
            usuario.setCreditos(creditos);
        }
        //-------------------------------------------------------------------------//
        UsuarioEntity savedUsuario = usuarioRepository.save(usuario);
        //-------------------------------------------------------------------------//
        // GUARDADO DE HISTORIAL EN CASO DE EXISTIR
        if (creditos != null) {
            for (CreditoEntity credito : creditos) {
                creditoRepository.save(credito);
            }
        }
        //-------------------------------------------------------------------------//
        return savedUsuario;
        //return usuarioRepository.findById(savedUsuario.getId()).orElse(null);
    }
    //-----------------------[P3]- FUNCIONES DE CREACIÓN  DE SOLICITUD DE CRÉDITO-------------------------//
    public CreditoEntity createSolicitud(Long userId, double montop, int plazo, double intanu, double intmen, double segudesg, double seguince, double comiad, byte[] comprobanteIngresos, byte[] certificadoAvaluo, byte[] historialCrediticio, byte[] escrituraPrimeraVivienda, byte[] planNegocios, byte[] estadosFinancieros, byte[] presupuestoRemodelacion, byte[] dicom) {

        UsuarioEntity usuario = usuarioRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("ERROR: USUARIO NO ENCONTRADO"));
        //-------------------------------------------------------------------------//
        // Eliminar la solicitud existente si hay una
        if (usuario.getSolicitud() != null) {
            CreditoEntity solicitudExistente = usuario.getSolicitud();
            usuario.setSolicitud(null);
            creditoRepository.delete(solicitudExistente);
        }
        //-------------------------------------------------------------------------//
        // Crear la nueva solicitud
        CreditoEntity solicitud = new CreditoEntity();
        solicitud.setMontop(montop);
        solicitud.setPlazo(plazo);
        solicitud.setIntanu(intanu);
        solicitud.setIntmen(intmen);
        solicitud.setSegudesg(segudesg);
        solicitud.setSeguince(seguince);
        solicitud.setComiad(comiad);
        solicitud.setComprobanteIngresos(comprobanteIngresos);
        solicitud.setCertificadoAvaluo(certificadoAvaluo);
        solicitud.setHistorialCrediticio(historialCrediticio);
        solicitud.setEscrituraPrimeraVivienda(escrituraPrimeraVivienda);
        solicitud.setPlanNegocios(planNegocios);
        solicitud.setEstadosFinancieros(estadosFinancieros);
        solicitud.setPresupuestoRemodelacion(presupuestoRemodelacion);
        solicitud.setDicom(dicom);
        solicitud.setState("PENDIENTE");

        // Asociar la nueva solicitud al usuario
        usuario.setSolicitud(solicitud);

        // Guardar la nueva solicitud
        CreditoEntity savedSolicitud = creditoRepository.save(solicitud);

        return savedSolicitud;
        //return creditoRepository.findById(savedSolicitud.getId()).orElse(null);
    }
    //-----------------------[P4]- EVALUACIÓN DE CRÉDITO-------------------------//
    // VER QUE EL RUT ESTÉ GUARDADO EN LA BASE DE DATOS Y QUE TENGA UNA "solicitud" EN ESTADO "PENDIENTE"
    public Map<String, Object> evaluateCredito(Long userId) {

        UsuarioEntity usuario = usuarioRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("ERROR: USUARIO NO ENCONTRADO"));
        CreditoEntity ayuda = usuario.getSolicitud(); // OBTENGO LA SOLICITUD DEL USUARIO
        CreditoEntity solicitud = usuario.getSolicitud(); // OBTENGO LA SOLICITUD DEL USUARIO
        //-------------------------------------------------------------------------//
        if(solicitud == null) {
            System.out.println("SOLICITUD NO ENCONTRADA");
            usuario.addNotification("ERROR: EL USUARIO NO TIENE UNA SOLICITUD DE CRÉDITO CREADA");
            usuarioRepository.save(usuario);
            return null;
        }
        //-------------------------------------------------------------------------//
        // LIMPIEZA DE BANDEJA DE NOTIFICACIONES:
        usuario.setNotifications(new ArrayList<>());
        usuarioRepository.save(usuario);

        Map<String, Object> response = new HashMap<>();
        response.put("ayuda", ayuda);
        List<Map<String, String>> files = new ArrayList<>();
        files.add(Map.of("name", "Comprobante de Ingresos", "data", ayuda.getComprobanteIngresos() != null ? Base64.getEncoder().encodeToString(ayuda.getComprobanteIngresos()) : ""));
        files.add(Map.of("name", "Certificado de Avaluo", "data", ayuda.getCertificadoAvaluo() != null ? Base64.getEncoder().encodeToString(ayuda.getCertificadoAvaluo()) : ""));
        files.add(Map.of("name", "Historial Crediticio", "data", ayuda.getHistorialCrediticio() != null ? Base64.getEncoder().encodeToString(ayuda.getHistorialCrediticio()) : ""));
        files.add(Map.of("name", "Escritura Primera Vivienda", "data", ayuda.getEscrituraPrimeraVivienda() != null ? Base64.getEncoder().encodeToString(ayuda.getEscrituraPrimeraVivienda()) : ""));
        files.add(Map.of("name", "Plan de Negocios", "data", ayuda.getPlanNegocios() != null ? Base64.getEncoder().encodeToString(ayuda.getPlanNegocios()) : ""));
        files.add(Map.of("name", "Estados Financieros", "data", ayuda.getEstadosFinancieros() != null ? Base64.getEncoder().encodeToString(ayuda.getEstadosFinancieros()) : ""));
        files.add(Map.of("name", "Presupuesto de Remodelación", "data", ayuda.getPresupuestoRemodelacion() != null ? Base64.getEncoder().encodeToString(ayuda.getPresupuestoRemodelacion()) : ""));
        files.add(Map.of("name", "Dicom", "data", ayuda.getDicom() != null ? Base64.getEncoder().encodeToString(ayuda.getDicom()) : ""));
        response.put("files", files);
        //-------------------------------------------------------------------------//
        if ( !"PENDIENTE".equalsIgnoreCase(solicitud.getState())) {
            System.out.println("SOLICITUD NO ESTÁ EN ESTADO PENDIENTE");
            usuario.addNotification("ERROR: LA SOLICITUD DE CRÉDITO NO ESTÁ EN ESTADO PENDIENTE");
            usuarioRepository.save(usuario);
            return null;
        }
        //-------------------------------------------------------------------------//
        double montop = solicitud.getMontop();        // MONTO DEL PRÉSTAMO
        int plazo = solicitud.getPlazo();                      // PLAZO DEL PRÉSTAMO EN AÑOS
        double intanu = solicitud.getIntanu();            // TASA DE INTERES ANUAL
        double intmen = solicitud.getIntmen();          // TASA DE INTERES MENSUAL
        double segudesg = solicitud.getSegudesg();// SEGURO DE DESGRAVAMEN
        double seguince = solicitud.getSeguince();  // SEGURO DE INCENDIO
        double comiad = solicitud.getComiad();       // COMISIÓN ADMINISTRATIVA
        double meses = plazo * 12;                           // PASA DE AÑOS A MESES
        //-------------------------------------------------------------------------//
        // ANÁLISIS DE QUE LOS DATOS SOLICITADOS DEL CRÉDITO SEAN CORRECTOS// VALORES REDONDEADOS PARA COMPARAR
        if (Math.round(intmen) == Math.round(intanu / 12.0 / 100.0)) {
            //System.out.println("TASA DE INTERÉS MENSUAL CORRECTA");
        } else {
            // MODIFICAR EL ESTADO DE LA SOLICITUD A "RECHAZADA"
            solicitud.setState("RECHAZADA");
            //----------------------------------------------------------------------//-------------------------------------------------------------------------//
            // ACTUALIZACIÓN DE solicitud EN EL ESPACIO DE solicitud DEL USUARIO
            usuario.setSolicitud(solicitud);
            //----------------------------------------------------------------------//
            CreditoEntity savedSolicitud = creditoRepository.save(solicitud);
            //----------------------------------------------------------------------//
            usuario.addNotification("TASA DE INTERÉS MENSUAL ES INCORRECTA");
            usuarioRepository.save(usuario);
            //----------------------------------------------------------------------//
            return null;
        }
        //-------------------------------------------------------------------------//-------------------------------------------------------------------------//
        // [R1]- RELACIÓN CUOTA/INGRESO------------------------------------//
        double cuota = montop * (intmen * Math.pow(1 + intmen, meses)) / (Math.pow(1 + intmen, meses) - 1);
        double cuotaing = (Math.round(cuota) / Math.round(usuario.getIngresos())) * 100;
        if (cuotaing <= 0.35) {
            // TIENE QUE SER MENOR O IGUAL QUE EL UMBRAL ESTABLECIDO POR EL BANCO
            //System.out.println("RELACIÓN CUOTA/INGRESO ACEPTADA");
        } else {
            // MODIFICAR EL ESTADO DE LA SOLICITUD A "RECHAZADA"
            solicitud.setState("RECHAZADA");
            //-------------------------------------------------------------------------//-------------------------------------------------------------------------//
            // ACTUALIZACIÓN DE solicitud EN EL ESPACIO DE solicitud DEL USUARIO
            usuario.setSolicitud(solicitud);
            //-------------------------------------------------------------------------//
            CreditoEntity savedSolicitud = creditoRepository.save(solicitud);
            //-------------------------------------------------------------------------//
            usuario.addNotification("RELACIÓN CUOTA/INGRESO RECHAZADA: CUOTA/INGRESO TIENE QUE SER MENOR O IGUAL QUE EL UMBRAL ESTABLECIDO POR EL BANCO");
            usuarioRepository.save(usuario);
            //-------------------------------------------------------------------------//
            return null;
        }
        //-------------------------------------------------------------------------//-------------------------------------------------------------------------//
        // [R2]- HISTORIAL DE  CREDITOS--------------------------------------//
        // OBRENCIÓN DE documents DE USUARIO
        // VERIFICACIÓN DE QUE HAYA DOCUMENTO REFERENTE A DICOM.PDF
        byte[] dicom = solicitud.getDicom();
        if (dicom == null || dicom.length == 0) {
            // MODIFICAR EL ESTADO DE LA SOLICITUD A "RECHAZADA"
            solicitud.setState("RECHAZADA");
            // ACTUALIZACIÓN DE solicitud EN EL ESPACIO DE solicitud DEL USUARIO
            usuario.setSolicitud(solicitud);
            //-------------------------------------------------------------------------//
            CreditoEntity savedSolicitud = creditoRepository.save(solicitud);
            //-------------------------------------------------------------------------//
            usuario.addNotification("NO SE HA INGRESADO UN ARCHIVO DICOM");
            usuarioRepository.save(usuario);
            //-------------------------------------------------------------------------//
            return null;
            //-------------------------------------------------------------------------//-------------------------------------------------------------------------//
        } else if (dicom.length < 4 || dicom[0] != '%' || dicom[1] != 'P' || dicom[2] != 'D' || dicom[3] != 'F') {
            //System.out.println("DICOM no es un archivo PDF.");
            // MODIFICAR EL ESTADO DE LA SOLICITUD A "RECHAZADA"
            solicitud.setState("RECHAZADA");
            // ACTUALIZACIÓN DE solicitud EN EL ESPACIO DE solicitud DEL USUARIO
            usuario.setSolicitud(solicitud);
            //-------------------------------------------------------------------------//
            CreditoEntity savedSolicitud = creditoRepository.save(solicitud);
            //-------------------------------------------------------------------------//
            usuario.addNotification("NO SE HA INGRESADO UN ARCHIVO DICOM DEL TIPO PDF");
            usuarioRepository.save(usuario);
            //-------------------------------------------------------------------------//
            return null;
            //-------------------------------------------------------------------------//-------------------------------------------------------------------------//
        } else {
            //System.out.println("DICOM GUARDADO EN FORMATO PDF.");
        }
        //-------------------------------------------------------------------------//-------------------------------------------------------------------------//
        // [R3]- -------------------------------------------------------------------//
        // VER SI DENTRO AÑOS DE TRABAJO ES MAYOR O IGUAL A 2
        if (usuario.getWorkage() >= 1 && usuario.getIndependiente().equalsIgnoreCase("ASALARIADO")) {
            //System.out.println("AÑOS DE TRABAJO ACTUAL ACEPTADOS");
        } else if (usuario.getIndependiente().equalsIgnoreCase("INDEPENDIENTE")) {
            //System.out.println("TRABAJADOR INDEPENDIENTE");
            // OBTENCIÓN DE LOS AHORROS DEL USUARIO
            List<AhorrosEntity> ahorros = usuario.getAhorros(); // OBTENGO LOS AHORROS DEL USUARIO -> PARA REALIZAR EVALUACIÓN
        } else {
            // MODIFICAR EL ESTADO DE LA SOLICITUD A "RECHAZADA"
            solicitud.setState("RECHAZADA");
            //-------------------------------------------------------------------------//-------------------------------------------------------------------------//
            // ACTUALIZACIÓN DE solicitud EN EL ESPACIO DE solicitud DEL USUARIO
            usuario.setSolicitud(solicitud);
            //-------------------------------------------------------------------------//
            CreditoEntity savedSolicitud = creditoRepository.save(solicitud);
            //-------------------------------------------------------------------------//
            usuario.addNotification("POR FAVOR, SI NO ERES UN TRABAJADOR INDEPENDIENTE, DEBES TENER MÁS DE 1 AÑO DE TRABAJO");
            usuarioRepository.save(usuario);
            //-------------------------------------------------------------------------//
            return null;
        }
        // [R4]--------------------------------------------------------------------//
        // RECHAZO DE LA SOLICITUD SI LA SUMA DE LAS DEUDAS ES MAYOR AL 50% DE LOS INGRESOS
        if (Double.valueOf(usuario.getSumadeuda()) > usuario.getIngresos() * 0.5) {
            // MODIFICAR EL ESTADO DE LA SOLICITUD A "RECHAZADA"
            solicitud.setState("RECHAZADA");
            //-------------------------------------------------------------------------//-------------------------------------------------------------------------//
            // ACTUALIZACIÓN DE solicitud EN EL ESPACIO DE solicitud DEL USUARIO
            usuario.setSolicitud(solicitud);
            //-------------------------------------------------------------------------//
            CreditoEntity savedSolicitud = creditoRepository.save(solicitud);
            //-------------------------------------------------------------------------//
            usuario.addNotification("LA SUMA DE DEUDAS NO PUEDE SER MAYOR AL 50% DE LOS INGRESOS");
            usuarioRepository.save(usuario);
            //-------------------------------------------------------------------------//
            return null;
        } else {
            //System.out.println("LA SUMA DE DEUDAS ES MENOR AL 50% DE LOS INGRESOS");
        }
        // [R5]--------------------------------------------------------------------// INFRESAR VALOR DE PROPIEDAD
        //-------------------------------------------------------------------------//
        if (plazo <= 30 && 0.035 <= intanu && intanu <= 0.05 && montop <= usuario.getValorpropiedad() * 0.8 &&
                solicitud.getComprobanteIngresos() != null && solicitud.getComprobanteIngresos().length > 0 &&
                solicitud.getCertificadoAvaluo() != null && solicitud.getCertificadoAvaluo().length > 0 &&
                solicitud.getHistorialCrediticio() != null && solicitud.getHistorialCrediticio().length > 0 && usuario.getHouses() == 0 && usuario.getObjective().equalsIgnoreCase("PRIMERA VIVIENDA")) {
            //System.out.println("CRÉDITO APROBADO: PRIMERA VIVIENDA");
            //-------------------------------------------------------------------------//
        } else if (plazo <= 20 && 0.04 <= intanu && intanu <= 0.06 && montop <= usuario.getValorpropiedad() * 0.7 &&
                solicitud.getComprobanteIngresos() != null && solicitud.getComprobanteIngresos().length > 0 &&
                solicitud.getCertificadoAvaluo() != null  && solicitud.getCertificadoAvaluo().length > 0 &&
                solicitud.getEscrituraPrimeraVivienda() != null && solicitud.getEscrituraPrimeraVivienda().length > 0 &&
                solicitud.getHistorialCrediticio() != null  && solicitud.getHistorialCrediticio().length > 0 && usuario.getHouses() > 0 && usuario.getObjective().equalsIgnoreCase("SEGUNDA VIVIENDA")) {
            //System.out.println("CRÉDITO APROBADO: SEGUNDA VIVIENDA");
            //-------------------------------------------------------------------------//
        } else if (plazo <= 25 && 0.05 <= intanu && intanu <= 0.07 && montop <= usuario.getValorpropiedad() * 0.6 &&
                solicitud.getEstadosFinancieros() != null && solicitud.getEstadosFinancieros().length > 0 &&
                solicitud.getComprobanteIngresos() != null && solicitud.getComprobanteIngresos().length > 0 &&
                solicitud.getCertificadoAvaluo() != null && solicitud.getCertificadoAvaluo().length > 0 &&
                solicitud.getPlanNegocios() != null && solicitud.getPlanNegocios().length > 0 && usuario.getObjective().equalsIgnoreCase("PROPIEDAD COMERCIAL")) {
            //System.out.println("CRÉDITO APROBADO: PROPIEDADES COMERCIALES");
            //-------------------------------------------------------------------------//
        } else if (plazo <= 15 && 0.045 <= intanu && intanu <= 0.06 && montop <= usuario.getValorpropiedad() * 0.5 &&
                solicitud.getComprobanteIngresos() != null && solicitud.getComprobanteIngresos().length > 0 &&
                solicitud.getPresupuestoRemodelacion() != null && solicitud.getPresupuestoRemodelacion().length > 0 &&
                solicitud.getCertificadoAvaluo() != null && solicitud.getCertificadoAvaluo().length > 0 && usuario.getObjective().equalsIgnoreCase("REMODELACION")) {
            //System.out.println("CRÉDITO APROBADO: REMODELACIÓN");
        } else {
            // MODIFICAR EL ESTADO DE LA SOLICITUD A "RECHAZADA"
            //System.out.println("CRÉDITO RECHAZADO: NO CUMPLE CON LOS REQUISITOS, PORFAVOR REVIZAR LOS VALORES INGRESADOS Y LOS ARCHIVOS");
            solicitud.setState("RECHAZADA");
            //-------------------------------------------------------------------------//-------------------------------------------------------------------------//
            // ACTUALIZACIÓN DE solicitud EN EL ESPACIO DE solicitud DEL USUARIO
            usuario.setSolicitud(solicitud);
            //-------------------------------------------------------------------------//
            CreditoEntity savedSolicitud = creditoRepository.save(solicitud);
            //-------------------------------------------------------------------------//
            usuario.addNotification("CREDITO RECHAZADO: NO CUMPLE CON LOS REQUISITOS, PORFAVOR REVIZAR LOS VALORES INGRESADOS Y LOS ARCHIVOS");
            usuarioRepository.save(usuario);
            //-------------------------------------------------------------------------//
            return null;
        }
        // [R6]--------------------------------------------------------------------//
        if (usuario.getAge() + plazo > 75 || (usuario.getAge() + plazo >= 70 && usuario.getAge() + plazo <= 75)) {
            // MODIFICAR EL ESTADO DE LA SOLICITUD A "RECHAZADA"
            solicitud.setState("RECHAZADA");
            //-------------------------------------------------------------------------//-------------------------------------------------------------------------//
            // ACTUALIZACIÓN DE solicitud EN EL ESPACIO DE solicitud DEL USUARIO
            usuario.setSolicitud(solicitud);
            //-------------------------------------------------------------------------//
            CreditoEntity savedSolicitud = creditoRepository.save(solicitud);
            //-------------------------------------------------------------------------//
            usuario.addNotification("CREDITO RECHAZADO: EL SOLICITANTE ESTÁ MUY CERCANO A LA EDAD MÁXIMA PERMITIDA");
            usuarioRepository.save(usuario);
            //-------------------------------------------------------------------------//
            return null;
        } else {
            //System.out.println("EDAD DENTRO DE RANGO ACEPTABLE");
        }
        // [R7]--------------------------------------------------------------------//
        int errores = 5; // CANTIDAD DE ERRORES PERMITIDOS PARA OTORGAR UN ESTADO A LA SOLICITUD
        // [R7]-----[R71]---------------------------------------------------------//--------------------------------------------------------------------//
        // SALARIO MÍNIMO REQUERIDO
        List<AhorrosEntity> ahorros = usuario.getAhorros();
        int valorPositivoMasPequeno = usuarioService.obtenerValorPositivoMasPequeno(ahorros);
        if(valorPositivoMasPequeno >= Math.round(usuario.getSolicitud().getMontop()*0.1)){
            //System.out.println("SALDO POSITIVO MÁS PEQUEÑO MAYOR O IGUAL AL 10% DEL MONTO DEL PRÉSTAMO");
        }else{
            //-------------------------------------------------------------------------//
            usuario.addNotification("ERROR-1: SALDO POSITIVO MÁS PEQUEÑO MENOR AL 10% DEL MONTO DEL PRÉSTAMO");
            usuarioRepository.save(usuario);
            //-------------------------------------------------------------------------//
            errores--; // CHEQUEO NEGATIVO
        }
        // [R7]-----[R72]---------------------------------------------------------//--------------------------------------------------------------------//
        // HISTORIAL DE AHORRO CONSISTENTE
        //El cliente debe haber mantenido un saldo positivo en su cuenta de ahorros por lo menos durante los últimos 12 meses, sin realizar retiros significativos (> 50% del saldo).
        int bandera = 0;            // VERIFICA SI HAY ALGÚN RETIRO MAYOR A 50% DEL SALDO
        double saldo = 0;          // SALDO DE LA CUENTA DE AHORROS
        double acumulado = 0; // SALDO ACUMUADO DE LA CUENTA DE AHORROS
        double cantidad = 0;    // CANTIDAD DE MESES DE AHORRO / CADA TRANSACCIÓN ES UN MES
        double analisis = 0;      // REPRESENTA LOS ULTIMOS 12 MESES
        // ------------------------------------------------------------------------//
        // OBTENCIÓN DE LA CANTIDAD DE MESES DE AHORRO DEL USUARIO
        for (AhorrosEntity ahorro : ahorros) {
            cantidad = cantidad + 1;
        }
        //System.out.println("CANTIDAD DE MESES DE AHORRO: " + cantidad);
        analisis = cantidad; // REPRESENTA LOS ULTIMOS 12 MESES
        // ------------------------------------------------------------------------//
        // OBTENCIÓN DE LOS AHORROS DEL USUARIO TOTALES EN LOS ULTIMOS 12 MESES
        for (AhorrosEntity ahorro : ahorros) {
            acumulado = acumulado + ahorro.getTransaccion();
            if (ahorro.getTipo().equalsIgnoreCase("RETIRO") && Math.abs(ahorro.getTransaccion()) > saldo * 0.5 && analisis <= 12) {
                bandera = 1; // SI ES 1, HAY UN RETIRO MAYOR A 50% DEL SALDO DENTRO DE LOS ULTIMOS 12 MESES
            }
            saldo = ahorro.getTransaccion() + saldo;
            analisis = analisis - 1;
        }
        // ------------------------------------------------------------------------//
        if (cantidad < 12) {   // SI NO HAY 12 MESES DE AHORRO MARCO MAL EL ANALISIS
            //-------------------------------------------------------------------------//  -------------------------------------------------------------------------------------------------> REVIZAR |||||||||||||||||||||||||||||||||||||||||
            usuario.addNotification("ERROR-2: MENOS DE 12 MESES DE AHORRO");
            usuarioRepository.save(usuario);
            //System.out.println("ERROR-2: MENOS DE 12 MESES DE AHORRO");
            errores--;// CHEQUEO NEGATIVO
        }else if (saldo < 0) { // SI EL SALDO ES NEGATIVO MARCO MAL EL ANALISIS
            //-------------------------------------------------------------------------//  -------------------------------------------------------------------------------------------------> REVIZAR |||||||||||||||||||||||||||||||||||||||||
            usuario.addNotification("ERROR-2: SALDO TOTAL ES NEGATIVO");
            usuarioRepository.save(usuario);
            //System.out.println("ERROR-2: SALDO TOTAL ES NEGATIVO");
            errores--;// CHEQUEO NEGATIVO
        }else if (bandera == 1) { // SI HAY UN RETIRO MAYOR A 50% DEL SALDO DENTRO DE LOS ULTIMOS 12 MESES
            //-------------------------------------------------------------------------//  -------------------------------------------------------------------------------------------------> REVIZAR |||||||||||||||||||||||||||||||||||||||||
            usuario.addNotification("ERROR-2: RETIRO MAYOR A 50% DEL SALDO");
            usuarioRepository.save(usuario);
            //System.out.println("ERROR-2: RETIRO MAYOR A 50% DEL SALDO");
            errores--;// CHEQUEO NEGATIVO
        }else{
            //System.out.println("HISTORIAL DE AHORRO CONSISTENTE");
        }
        // [R7]-----[R73]---------------------------------------------------------//--------------------------------------------------------------------//
        bandera = 0;            // REUTILIZACIÓN, AHORA SI ES 1, ES PORQUE LOS DEPOSITOS NO RESPETAN EL SER MENSUALES O TRIMESTRALES
        int mensual = 0;      // VERIFICADOR DE DEPOSITOS MENSUALES
        int trimestral = 0;    // VERIFICADOR DE DEPOSITOS TRIMESTRALES
        int sumdepos = 0;   // SUMA DE DEPOSITOS TOTALES EN EL HISTORIAL
        analisis = cantidad; // REPRESENTA LOS ULTIMOS 12 MESES
        // ------------------------------------------------------------------------//
        // OBTENCIÓN DE LOS DEPOSITOS MENSUALES Y TRIMESTRALES DEL USUARIO SIEMPRE Y CUANDO LA CUENTA DE AHORRO POSEA 12 O MAS MESES DE HISTORIAL
        for (AhorrosEntity ahorro : ahorros) {
            if (ahorro.getTipo().equalsIgnoreCase("DEPOSITO") && analisis <= 12 && cantidad >= 12) {
                sumdepos = sumdepos + ahorro.getTransaccion();
                mensual = mensual + 1;
                if (mensual % 3 == 0) {
                    trimestral = trimestral + 1;
                }
            }
            analisis = analisis - 1;
        }
        // ------------------------------------------------------------------------//
        // SI NO HAY DEPOSITOS MENSUALES O TRIMESTRALES MARCO MAL EL ANALISIS DENTRO DE LOS ULTIMOS 12 MESES
        if (mensual < 12 && trimestral < 4) {
            //-------------------------------------------------------------------------//  -------------------------------------------------------------------------------------------------> REVIZAR |||||||||||||||||||||||||||||||||||||||||
            usuario.addNotification("ERROR-3: HAY MENOS DE 12 DEPOSITOS MENSUALES O MENOS DE 4 DEPOSITOS TRIMESTRALES");
            usuarioRepository.save(usuario);
            //System.out.println("ERROR-3: HAY MENOS DE 12 DEPOSITOS MENSUALES O MENOS DE 4 DEPOSITOS TRIMESTRALES");
            bandera = 1; // SI ES 1, NO NECESITO SEGUIR RESTANDO ERRORES, PARA LA COMPARACIÓN DIGUIENTE
        }
        // MONTO MINIMO: LOS DEPOSITOS DEBEN SUMAR AL MENOS EL 5% DE LOS INGRESOS MENSUALES
        if (sumdepos < usuario.getIngresos() * 0.05) {
            //-------------------------------------------------------------------------//  -------------------------------------------------------------------------------------------------> REVIZAR |||||||||||||||||||||||||||||||||||||||||
            usuario.addNotification("ERROR-3: LOS DEPOSITOS NO SUMAN AL MENOS EL 5% DE LOS INGRESOS MENSUALES");
            usuarioRepository.save(usuario);
            //System.out.println("ERROR-3: LOS DEPOSITOS NO SUMAN AL MENOS EL 5% DE LOS INGRESOS MENSUALES");
            bandera = 1; // SI ES 1, NO NECESITO SEGUIR RESTANDO ERRORES, PARA LA COMPARACIÓN DIGUIENTE
        }
        // ------------------------------------------------------------------------//
        if (bandera > 0){
            errores--; // CHEQUEO NEGATIVO
        }else{
            //System.out.println("DEPOSITOS MENSUALES Y TRIMESTRALES ACEPTADOS Y SUMAN AL MENOS EL 5% DE LOS INGRESOS MENSUALES");
        }
        // [R7]-----[R74]---------------------------------------------------------//--------------------------------------------------------------------//
        if (cantidad < 24 && acumulado >= usuario.getSolicitud().getMontop()*0.2 ){
            //System.out.println("CUENTA DE AHORROS MENOR A 2 AÑOS Y CON UN SALDO ACUMULADO AL MENOS DE 20% DEL PRESTAMO");
        }else if(cantidad >= 24 && acumulado >= usuario.getSolicitud().getMontop()*0.1){
            //System.out.println("CUENTA DE AHORROS MAYOR O IGUAL A 2 AÑOS Y CON UN SALDO ACUMULADO AL MENOS DE 10% DEL PRESTAMO");
        }else{
            //-------------------------------------------------------------------------//  -------------------------------------------------------------------------------------------------> REVIZAR |||||||||||||||||||||||||||||||||||||||||
            usuario.addNotification("ERROR-4: CUENTA DE AHORROS NO CUMPLE CON RELACIÓN ENTRE AÑOS DE ANTIUGEDAD Y SALDO ACUMULADO RESPECTO AL MONTO DEL PRÉSTAMO");
            usuarioRepository.save(usuario);
            //-------------------------------------------------------------------------//
            //System.out.println("ERROR-4: CUENTA DE AHORROS NO CUMPLE CON RELACIÓN ENTRE AÑOS DE ANTIUGEDAD Y SALDO ACUMULADO RESPECTO AL MONTO DEL PRÉSTAMO");
            errores--; // CHEQUEO NEGATIVO
        }
        // [R7]-----[R75]---------------------------------------------------------//--------------------------------------------------------------------//
        bandera = 0;  // REUTILIZACIÓN, AHORA SI ES 1 O MAYOR, ES PORQUE HAY UN RETIRO MAYOR AL 30% DEL SALDO EN LOS ULTIMOS 6 MESES
        saldo = 0;       // REUTILIZACIÓN, SALDO DE LA CUENTA DE AHORROS DE LOS ULTIMOS 6 MESES
        analisis = cantidad; // REPRESENTA LOS ULTIMOS 12 MESES
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        for(AhorrosEntity ahorro : ahorros){
            if(analisis <= 6){
                saldo = saldo + ahorro.getTransaccion();
            }
            analisis = analisis - 1;
        }

        for (AhorrosEntity ahorro : ahorros) {
            if(ahorro.getTipo().equalsIgnoreCase("RETIRO") && Math.abs(ahorro.getTransaccion()) > saldo*0.3){
                //System.out.println("tipo:" + ahorro.getTipo() + " transaccion:" + ahorro.getTransaccion() + " saldo:" + saldo);
                bandera = 1;
            }
        }
        // ------------------------------------------------------------------------//
        if (bandera == 1) {
            //System.out.println("RETIROS MAYORES AL 30% DEL SALDO EN LOS ULTIMOS 6 MESES");
            usuario.addNotification("ERROR-5: RETIROS MAYORES AL 30% DEL SALDO EN LOS ULTIMOS 6 MESES");
            usuarioRepository.save(usuario);
            errores--; // CHEQUEO NEGATIVO
        }
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // [R7]-----[CHEQUEO DE EVALUACIONES]----------------------------//--------------------------------------------------------------------//
        //System.out.println("FINALIZACIÓN DE LA EVALUACIÓN DE CRÉDITO");
        //System.out.println("ERRORES ENCONTRADOS: " + errores);
        if (errores == 5) {
            //System.out.println("SOLICITUD DE CRÉDITO APROBADO");
            // MODIFICAR EL ESTADO DE LA SOLICITUD A "APROBADO"
            solicitud.setState("APROBADO");
            usuario.addNotification("CRÉDITO APROBADO");
            //-------------------------------------------------------------------------//-------------------------------------------------------------------------//
            // ACTUALIZACIÓN DE solicitud EN EL ESPACIO DE solicitud DEL USUARIO
            usuario.setSolicitud(solicitud);
            //-------------------------------------------------------------------------//
            CreditoEntity savedSolicitud = creditoRepository.save(solicitud);
            //-------------------------------------------------------------------------//
            return response;
        } else if (errores >= 3 && errores < 5) {
            //System.out.println("SOLICITUD DE CRÉDITO EN REVISIÓN ADICIONAL");
            // MODIFICAR EL ESTADO DE LA SOLICITUD A "RECHAZADA"
            solicitud.setState("REVISION ADICIONAL");
            //-------------------------------------------------------------------------//-------------------------------------------------------------------------//
            // ACTUALIZACIÓN DE solicitud EN EL ESPACIO DE solicitud DEL USUARIO
            usuario.setSolicitud(solicitud);
            //-------------------------------------------------------------------------//
            CreditoEntity savedSolicitud = creditoRepository.save(solicitud);
            //-------------------------------------------------------------------------//
            //System.out.println("CRÉDITO RECHAZADO: EL SOLICITANTE ESTÁ MUY CERCANO A LA EDAD MÁXIMA PERMITIDA");
            return response;
        } else {
            //System.out.println("SOLICITUD DE CRÉDITO RECHAZADO");
            // MODIFICAR EL ESTADO DE LA SOLICITUD A "RECHAZADA"
            solicitud.setState("RECHAZADA");
            //-------------------------------------------------------------------------//-------------------------------------------------------------------------//
            // ACTUALIZACIÓN DE solicitud EN EL ESPACIO DE solicitud DEL USUARIO
            usuario.setSolicitud(solicitud);
            //-------------------------------------------------------------------------//
            CreditoEntity savedSolicitud = creditoRepository.save(solicitud);
            //-------------------------------------------------------------------------//  -------------------------------------------------------------------------------------------------> REVIZAR |||||||||||||||||||||||||||||||||||||||||
            usuario.addNotification("CRÉDITO RECHAZADO: EL SOLICITANTE NO CUMPLE CON LOS REQUISITOS");
            usuarioRepository.save(usuario);
            //-------------------------------------------------------------------------//
            //System.out.println("CRÉDITO RECHAZADO: EL SOLICITANTE ESTÁ MUY CERCANO A LA EDAD MÁXIMA PERMITIDA");
            return response;
            //return creditoRepository.findById(savedSolicitud.getId()).orElse(null);
        }
    }
    //-----------------------[P5]- FUNCIONES DE SEGUIMENTO ---------------------------------------//
    public CreditoEntity followCredito(Long userId) {
        UsuarioEntity usuario = usuarioRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("ERROR: USUARIO NO ENCONTRADO"));
        CreditoEntity solicitud = usuario.getSolicitud(); // OBTENGO LA SOLICITUD DEL USUARIO
        //System.out.println("respuesta solicitud: " + solicitud);
        //-------------------------------------------------------------------------//
        return solicitud;
    }
    //-----------------------[P6]- FUNCIONES DE CALCULO DE COSTOS TOTALES-------------------//
    public List<Double> calcularCostosTotales(Long userId){
        UsuarioEntity usuario = usuarioRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("ERROR: USUARIO NO ENCONTRADO"));
        CreditoEntity solicitud = usuario.getSolicitud(); // OBTENGO LA SOLICITUD DEL USUARIO
        if (solicitud == null){
            //System.out.println("SOLICITUD NO ENCONTRADA");
            usuario.addNotification("SOLICITUD NO EXISTENTE EN EL USUARIO.");
            return null;
        }
        //-------------------------------------------------------------------------//
        double montop = solicitud.getMontop();        // MONTO DEL PRÉSTAMO
        int plazo = solicitud.getPlazo();                      // PLAZO DEL PRÉSTAMO EN AÑOS
        double intanu = solicitud.getIntanu();            // TASA DE INTERES ANUAL
        double intmen = solicitud.getIntmen();          // TASA DE INTERES MENSUAL
        double segudesg = solicitud.getSegudesg();// SEGURO DE DESGRAVAMEN
        double seguince = solicitud.getSeguince();  // SEGURO DE INCENDIO
        double comiad = solicitud.getComiad();       // COMISIÓN ADMINISTRATIVA
        double meses = plazo * 12; // PASA DE AÑOS A MESES
        //-------------------------------------------------------------------------//
        // ANÁLISIS DE QUE LOS DATOS SOLICITADOS DEL CRÉDITO SEAN CORRECTOS// VALORES REDONDEADOS PARA COMPARAR
        if (Math.round(intmen) == Math.round(intanu / 12.0 / 100.0)) {
            //System.out.println("TASA DE INTERÉS MENSUAL CORRECTA");
        } else {
            // MODIFICAR EL ESTADO DE LA SOLICITUD A "RECHAZADA"
            solicitud.setState("RECHAZADA");
            //-------------------------------------------------------------------------//-------------------------------------------------------------------------//
            // ACTUALIZACIÓN DE solicitud EN EL ESPACIO DE solicitud DEL USUARIO
            usuario.setSolicitud(solicitud);
            //-------------------------------------------------------------------------//
            CreditoEntity savedSolicitud = creditoRepository.save(solicitud);
            //-------------------------------------------------------------------------//  -------------------------------------------------------------------------------------------------> REVIZAR |||||||||||||||||||||||||||||||||||||||||
            usuario.addNotification("TASA DE INTERÉS MENSUAL ES INCORRECTA");
            usuarioRepository.save(usuario);
            //-------------------------------------------------------------------------//
            //System.out.println("TASA DE INTERÉS MENSUAL INCORRECTA");
            return null;
        }
        //-------------------------------------------------------------------------//-------------------------------------------------------------------------//
        List<Double> resultados = new ArrayList<>();
        //-------------------------------------------------------------------------//-------------------------------------------------------------------------//
        // [PASO 1]- CALCULO DE CUOTA MENSUAL DEL PRESTAMO------------------------------------//
        double cuota = montop * (intmen * Math.pow(1 + intmen, meses)) / (Math.pow(1 + intmen, meses) - 1);
        resultados.add(cuota);
        //System.out.println("CUOTA MENSUAL DEL PRÉSTAMO: "+Math.round(cuota));
        // [PASO 2]- CALCULO DE LOS SEGUROS------------------------------------//
        double segudesgtotal = montop * segudesg;
        resultados.add(segudesgtotal);
        resultados.add(seguince);
        //System.out.println("SEGURO DE DESGRAVAMEN: "+Math.round(segudesgtotal));
        // [PASO 3]- CALCULO DE COMISION POR ADMINISTRACIÓN--------------//
        double comiadtotal = montop * comiad;
        resultados.add(comiadtotal);
        //System.out.println("COMISIÓN ADMINISTRATIVA: "+Math.round(comiadtotal));
        // [PASO 4]- CALCULO DE COSTO TOTAL DE PRESTAMO------------------//
        double costomensual = cuota + segudesgtotal + comiadtotal;
        double costototal = (costomensual * meses*12) + montop;
        resultados.add(costomensual);
        resultados.add(costototal);
        //System.out.println("COSTO MENSUAL DEL PRÉSTAMO: "+Math.round(costomensual));
        // [PASO 5]- REVICIÓN Y ENTREGA DE SOLICITUD-------------------------//
        //System.out.println("COSTO TOTAL DEL PRÉSTAMO: "+Math.round(costototal));

        return resultados;
    }
    //-----------------------[EXTRA]- FUNCIONES DE NOTIFICACIONES-------------------------------//
    public List<String> getNotifications(Long userId) {
        UsuarioEntity usuario = usuarioRepository.findById(userId).orElseThrow(null);
        List<String> notifications = usuario.getNotifications();
        return notifications;
    }
    //-----------------------[EXTRA]- FUNCIONES DE ACTUALIZACIÓN DE ESTADOS-----------------//
    public int updateState(Long userId, int state) {
        UsuarioEntity usuario = usuarioRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("ERROR: USUARIO NO ENCONTRADO"));
        CreditoEntity solicitud = usuario.getSolicitud(); // OBTENGO LA SOLICITUD DEL USUARIO
        if (solicitud == null){
            //System.out.println("SOLICITUD NO ENCONTRADA");
            usuario.addNotification("SOLICITUD NO EXISTENTE EN EL USUARIO.");
            return -1;
        }
        //-------------------------------------------------------------------------//
        if (state ==1) {
            solicitud.setState("EN REVISION INICIAL");
            usuario.addNotification("ESTADO DE SOLICITUD ACTUALIZADO: SU SOLICITUD SE ENCUENTRA EN EL ESTADO: EN REVISIÓN INICIAL");

        }else if (state ==2) {
            solicitud.setState("PENDIENTE DE DOCUMENTACION");
            usuario.addNotification("ESTADO DE SOLICITUD ACTUALIZADO: SU SOLICITUD SE ENCUENTRA EN EL ESTADO: PENDIENTE DE DOCUMENTACIÓN");

        }else if (state ==3) {
            solicitud.setState("EN EVALUACION");
            usuario.addNotification("ESTADO DE SOLICITUD ACTUALIZADO: SU SOLICITUD SE ENCUENTRA EN EL ESTADO: EN EVALUACIÓN");

        }else if (state ==4) {
            solicitud.setState("PRE-APROBADA");
            usuario.addNotification("ESTADO DE SOLICITUD ACTUALIZADO: SU SOLICITUD SE ENCUENTRA EN EL ESTADO: PRE-APROBADA");

        }else if (state ==5) {
            solicitud.setState("EN APROBACION FINAL");
            usuario.addNotification("ESTADO DE SOLICITUD ACTUALIZADO: SU SOLICITUD SE ENCUENTRA EN EL ESTADO: EN APROBACIÓN FINAL");

        }else if (state ==6) {
            solicitud.setState("APROBADA");
            usuario.addNotification("ESTADO DE SOLICITUD ACTUALIZADO: SU SOLICITUD SE ENCUENTRA EN EL ESTADO: APROBADA");

        }else if (state ==7) {
            solicitud.setState("RECHAZADA");
            usuario.addNotification("ESTADO DE SOLICITUD ACTUALIZADO: SU SOLICITUD SE ENCUENTRA EN EL ESTADO: RECHAZADA");

        }else if (state ==8) {
            solicitud.setState("CANCELADA POR EL CLIENTE");
            usuario.addNotification("ESTADO DE SOLICITUD ACTUALIZADO: SU SOLICITUD SE ENCUENTRA EN EL ESTADO: CANCELADA POR EL CLIENTE");

        }else if (state ==9) {
            solicitud.setState("EN DESEMBOLOSO");
            usuario.addNotification("ESTADO DE SOLICITUD ACTUALIZADO: SU SOLICITUD SE ENCUENTRA EN EL ESTADO: EN DESEMBOLOSO");

            // Crear un nuevo crédito basado en la solicitud
            CreditoEntity nuevoCredito = new CreditoEntity();
            nuevoCredito.setMontop(solicitud.getMontop());
            nuevoCredito.setPlazo(solicitud.getPlazo());
            nuevoCredito.setIntanu(solicitud.getIntanu());
            nuevoCredito.setIntmen(solicitud.getIntmen());
            nuevoCredito.setSegudesg(solicitud.getSegudesg());
            nuevoCredito.setSeguince(solicitud.getSeguince());
            nuevoCredito.setComiad(solicitud.getComiad());
            nuevoCredito.setState("DESEMBOLOSO");
            nuevoCredito.setUsuario(usuario);

            // Asegurarse de que la lista de créditos del usuario esté inicializada
            List<CreditoEntity> creditos = usuario.getCreditos();
            if (creditos == null) {
                creditos = new ArrayList<>();
            }

            // Agregar el nuevo crédito a la lista de créditos del usuario
            creditos.add(nuevoCredito);
            usuario.setCreditos(creditos);

            // Guardar el nuevo crédito y el usuario en el repositorio
            creditoRepository.save(nuevoCredito);
            usuarioRepository.save(usuario);

            // Actualización de solicitud en el espacio de solicitud del usuario
            usuario.setSolicitud(solicitud);
        } else if (state == 10) {
            solicitud.setState("PENDIENTE");
            usuario.addNotification("ESTADO DE SOLICITUD ACTUALIZADO: SU SOLICITUD SE ENCUENTRA EN EL ESTADO: PENDIENTE");
        }
        //solicitud.setState(state);
        //-------------------------------------------------------------------------//-------------------------------------------------------------------------//
        // ACTUALIZACIÓN DE solicitud EN EL ESPACIO DE solicitud DEL USUARIO
        usuario.setSolicitud(solicitud);
        //-------------------------------------------------------------------------//
        creditoRepository.save(solicitud);
        usuarioRepository.save(usuario);
        //-------------------------------------------------------------------------//
        return 0;
    }
}

