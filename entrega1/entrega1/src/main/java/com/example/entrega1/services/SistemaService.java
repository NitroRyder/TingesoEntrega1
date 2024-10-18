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

import java.util.List;

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
    // P = MONTO DEL PRESTAMO
    // r = TASA DE INTERES ANUAL
    // n = PLAZO DEL PRESTAMO EN AÑOS
    // V = VALOR ACTUAL DE LA PROPIEDAD
    public double Credito_Hipotecario(String rut, double P, double r, double n, double V) {
        if (rut == null || rut.isEmpty() || !usuarioRepository.existsByRut(rut)) {
            throw new IllegalArgumentException("ERROR: EL  RUT INGRESADO NO SE ENCUENTRA REGISTRADO EN EL SISTEMA, POR FAVOR INGRESAR UN RUT REGISTRADO O REGISTRARSE EN EL SISTEMA");
        }
        if(n == 30 && 0.035<= r && r <=0.05 && P <= V*0.8){ // Primera Vivienda
            System.out.println("PRÉSTAMO REALIZADO: Primera Vivienda");
            System.out.println("LOS DOCUMENTOS A INGRESAR PARA ESTE TIPO DE PRÉSTAMO SON:");
            System.out.println("1.- Comprobante de ingresos");
            System.out.println("2.- Certificado de avalúo");
            System.out.println("3.- Historial crediticio");
            r = r / 12 / 100; // PASA DE TASA ANUAL A MENSUAL
            n = n * 12;        // PASA DE AÑOS A CANTIDAD DE PAGOS
            double M = P * (r * Math.pow(1 + r, n)) / (Math.pow(1 + r, n) - 1);
            return M;
        } else if(n == 20 && 0.04<= r && r <=0.06 && P <= V*0.7){ //Segunda Vivienda
            System.out.println("PRESTAMO REALIZADO: Segunda Vivienda");
            System.out.println("LOS DOCUMENTOS A INGRESAR PARA ESTE TIPO DE PRÉSTAMO SON:");
            System.out.println("1.- Comprobante de ingresos");
            System.out.println("2.- Certificado de avalúo");
            System.out.println("3.- Escritura de la primera vivienda");
            System.out.println("4.- Historial crediticio");
            r = r / 12 / 100;
            n = n * 12;
            double M = P * (r * Math.pow(1 + r, n)) / (Math.pow(1 + r, n) - 1);
            return M;
        } else if(n == 25 && 0.05<= r && r <=0.07 && P <= V*0.6){ //Propiedades Comerciales
            System.out.println("PRESTAMO REALIZADO: Propiedades Comerciales");
            System.out.println("LOS DOCUMENTOS A INGRESAR PARA ESTE TIPO DE PRÉSTAMO SON:");
            System.out.println("1.-Estado financiero del negocio");
            System.out.println("2.- Comprobante de ingresos");
            System.out.println("3.- Certificado de avalúo");
            System.out.println("4.- Plan de negocios");
            r = r / 12 / 100;
            n = n * 12;
            double M = P * (r * Math.pow(1 + r, n)) / (Math.pow(1 + r, n) - 1);
            return M;
        } else if(n ==15 && 0.045<= r && r <=0.06 && P<= V*0.5){ //Remodelación
            System.out.println("PRESTAMO REALIZADO: Remodelación");
            System.out.println("LOS DOCUMENTOS A INGRESAR PARA ESTE TIPO DE PRÉSTAMO SON:");
            System.out.println("1.- Comprobante de ingresos");
            System.out.println("2.- Presupuesto de la remodelación\n");
            System.out.println("3.- Certificado de avalúo actualizado");
            r = r / 12 / 100;
            n = n * 12;
            double M = P * (r * Math.pow(1 + r, n)) / (Math.pow(1 + r, n) - 1);
            return M;
        }else{
            System.out.println("TOMA ESTE 0");
            return 0;
        }
    }
    //-----------------------[P2]- FUNCIONES DE REGISTRO DE USUARIO-------------------------//
    public UsuarioEntity registerUsuario(String rut, String name, int age, int workage, List<String> documents, int houses, int valorpropiedad, int ingresos, int sumadeuda, String objective, String independiente, List<AhorrosEntity> ahorros, List<CreditoEntity> creditos) {
        // Eliminar los puntos del RUT
        String cleanedRut = rut.replace(".", "");
        if (rut == null || rut.isEmpty() || !cleanedRut.matches("^[0-9]{7,8}-[0-9Kk]$")) {
            throw new IllegalArgumentException("EL RUT DEBE SER VÁLIDO");
        }
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("POR FAVOR INGRESAR NOMBRE");
        }
        if (independiente == null || (!independiente.toUpperCase().equalsIgnoreCase("INDEPENDIENTE") && !independiente.toUpperCase().equalsIgnoreCase("ASALARIADO"))) {
            throw new IllegalArgumentException("POR FAVOR INGRESAR 'INDEPENDIENTE' O 'ASALARIADO'");
        }
        if (documents != null) {
            for (String document : documents) {
                if (document == null) {
                    throw new IllegalArgumentException("NO SE HA INGRESADO UN ARCHIVO POR FAVOR INGRESAR DOCUMENTO DE TIPO pdf");
                }
                if (!document.toLowerCase().endsWith(".pdf")) {
                    throw new IllegalArgumentException("EL DOCUMENTO NO ES DEL TIPO PDF. POR FAVOR INGRESAR UN DOCUMENTO DE TIPO PDF");
                }
            }
            documents.replaceAll(String::toUpperCase);
        }
        //-------------------------------------------------------------------------//
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setRut(cleanedRut.toUpperCase());
        usuario.setName(name.toUpperCase());
        usuario.setAge(age);
        usuario.setWorkage(workage);
        usuario.setDocuments(documents);
        usuario.setHouses(houses);
        usuario.setValorpropiedad(valorpropiedad);
        usuario.setIngresos(ingresos);
        usuario.setSumadeuda(sumadeuda);
        usuario.setObjective(objective.toUpperCase());
        usuario.setIndependiente(independiente.toUpperCase());
        //-------------------------------------------------------------------------//
        // SETEO DE AHORROS // SI NO HAY, NO INGRESA NADA
        if (ahorros != null) {
            for (AhorrosEntity ahorro : ahorros) {
                ahorro.setUsuario(usuario);
                String tipo = ahorro.getTipo().toUpperCase();
                int transaccion = ahorro.getTransaccion();
                if (!tipo.equals("DEPOSITO") && !tipo.equals("PAGO") && !tipo.equals("TRANSFERENCIA") && !tipo.equals("RETIRO")) {
                    throw new IllegalArgumentException("TIPO DE AHORRO NO VÁLIDO. LOS TIPOS VÁLIDOS SON: DEPOSITO, PAGO, TRANSFERENCIA, RETIRO");
                }
                if (tipo.equals("DEPOSITO") && transaccion <= 0) {
                    throw new IllegalArgumentException("LA TRANSACCIÓN DE UN DEPOSITO DEBE SER MAYOR A 0");
                }
                if ((tipo.equals("PAGO") || tipo.equals("TRANSFERENCIA") || tipo.equals("RETIRO")) && transaccion >= 0) {
                    throw new IllegalArgumentException("LA TRANSACCIÓN DE UN PAGO, TRANSFERENCIA O RETIRO DEBE SER MENOR A 0");
                }
            }
        }
        usuario.setAhorros(ahorros);
        //-------------------------------------------------------------------------//
        usuario.setSolicitud(null);
        //-------------------------------------------------------------------------//
        // SETEO DE HISTORIAL DE CREDITOS // SI NO HAY, NO INGRESA NADA // DÁ LO MISMO SI LOS ARCHIVOS FUERON INGRESADOS O NO EN EL HISTORIAL
        if (creditos != null) {
            for (CreditoEntity credito : creditos) {
                if (!"APROBADO".equalsIgnoreCase(credito.getState())) {
                    throw new IllegalArgumentException("TODOS LOS CRÉDITOS DEBEN TENER EL ESTADO 'APROBADO'");
                }
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
        return usuarioRepository.findById(savedUsuario.getId()).orElse(null);
    }
    //-----------------------[P3]- FUNCIONES DE CREACIÓN  DE SOLICITUD DE CRÉDITO-------------------------//
    public CreditoEntity createSolicitud(Long userId, double montop, int plazo, double intanu, double intmen, double segudesg, double seguince, double comiad, byte[] comprobanteIngresos, byte[] certificadoAvaluo, byte[] historialCrediticio, byte[] escrituraPrimeraVivienda, byte[] planNegocios, byte[] estadosFinancieros, byte[] presupuestoRemodelacion, byte[] dicom) {
        if (userId == null || !usuarioRepository.existsById(userId)) {
            throw new IllegalArgumentException("ERROR: EL ID DE USUARIO INGRESADO NO SE ENCUENTRA REGISTRADO EN EL SISTEMA, POR FAVOR INGRESAR UN ID REGISTRADO O REGISTRARSE EN EL SISTEMA");
        }
        if (montop <= 0) {
            throw new IllegalArgumentException("ERROR: EL MONTO DEL PRÉSTAMO DEBE SER MAYOR A 0");
        }
        if (plazo <= 0) {
            throw new IllegalArgumentException("ERROR: EL PLAZO DEL PRÉSTAMO DEBE SER MAYOR A 0");
        }
        if (intanu <= 0) {
            throw new IllegalArgumentException("ERROR: LA TASA DE INTERÉS ANUAL DEBE SER MAYOR A 0");
        }
        if (intmen <= 0) {
            throw new IllegalArgumentException("ERROR: LA TASA DE INTERÉS MENSUAL DEBE SER MAYOR A 0");
        }
        if (segudesg <= 0) {
            throw new IllegalArgumentException("ERROR: EL SEGURO DE DESGRAVAMEN DEBE SER MAYOR A 0");
        }
        if (seguince <= 0) {
            throw new IllegalArgumentException("ERROR: EL SEGURO DE INCENDIO DEBE SER MAYOR A 0");
        }
        if (comiad <= 0) {
            throw new IllegalArgumentException("ERROR: LA COMISIÓN ADMINISTRATIVA DEBE SER MAYOR A 0");
        }

        UsuarioEntity usuario = usuarioRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("ERROR: USUARIO NO ENCONTRADO"));

        // Eliminar la solicitud existente si hay una
        if (usuario.getSolicitud() != null) {
            CreditoEntity solicitudExistente = usuario.getSolicitud();
            usuario.setSolicitud(null);
            creditoRepository.delete(solicitudExistente);
        }

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

        return creditoRepository.findById(savedSolicitud.getId()).orElse(null);
    }
    //-----------------------[P4]- EVALUACIÓN DE CRÉDITO-------------------------//
    // VER QUE EL RUT ESTÉ GUARDADO EN LA BASE DE DATOS Y QUE TENGA UNA "solicitud" EN ESTADO "PENDIENTE"
    public CreditoEntity evaluateCredito(Long userId) {
        if (userId == null || !usuarioRepository.existsById(userId)) {
            throw new IllegalArgumentException("ERROR: EL ID DE USUARIO INGRESADO NO SE ENCUENTRA REGISTRADO EN EL SISTEMA, POR FAVOR INGRESAR UN ID REGISTRADO O REGISTRARSE EN EL SISTEMA");
        }
        UsuarioEntity usuario = usuarioRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("ERROR: USUARIO NO ENCONTRADO"));
        CreditoEntity solicitud = usuario.getSolicitud(); // OBTENGO LA SOLICITUD DEL USUARIO
        //-------------------------------------------------------------------------//
        if (solicitud == null || !"PENDIENTE".equalsIgnoreCase(solicitud.getState())) {
            throw new IllegalArgumentException("ERROR: EL USUARIO NO TIENE UNA SOLICITUD DE CRÉDITO RECHAZADA");
        }
        //-------------------------------------------------------------------------//
        // Verificar los campos byte[]
        if (solicitud.getComprobanteIngresos() == null || solicitud.getComprobanteIngresos().length == 0) {
            System.out.println("Comprobante de ingresos no está guardado.");
        }
        if (solicitud.getCertificadoAvaluo() == null || solicitud.getCertificadoAvaluo().length == 0) {
            System.out.println("Certificado de avalúo no está guardado.");
        }
        if (solicitud.getHistorialCrediticio() == null || solicitud.getHistorialCrediticio().length == 0) {
            System.out.println("Historial crediticio no está guardado.");
        }
        if (solicitud.getEscrituraPrimeraVivienda() == null || solicitud.getEscrituraPrimeraVivienda().length == 0) {
            System.out.println("Escritura de la primera vivienda no está guardada.");
        }
        if (solicitud.getPlanNegocios() == null || solicitud.getPlanNegocios().length == 0) {
            System.out.println("Plan de negocios no está guardado.");
        }
        if (solicitud.getEstadosFinancieros() == null || solicitud.getEstadosFinancieros().length == 0) {
            System.out.println("Estados financieros no están guardados.");
        }
        if (solicitud.getPresupuestoRemodelacion() == null || solicitud.getPresupuestoRemodelacion().length == 0) {
            System.out.println("Presupuesto de remodelación no está guardado.");
        }
        if (solicitud.getDicom() == null || solicitud.getDicom().length == 0) {
            System.out.println("DICOM no está guardado.");
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
            System.out.println("TASA DE INTERÉS MENSUAL CORRECTA");
        } else {
            // MODIFICAR EL ESTADO DE LA SOLICITUD A "RECHAZADA"
            solicitud.setState("RECHAZADA");
            //-------------------------------------------------------------------------//-------------------------------------------------------------------------//
            // ACTUALIZACIÓN DE solicitud EN EL ESPACIO DE solicitud DEL USUARIO
            usuario.setSolicitud(solicitud);
            //-------------------------------------------------------------------------//
            CreditoEntity savedSolicitud = creditoRepository.save(solicitud);
            //-------------------------------------------------------------------------//
            usuario.addNotification("TASA DE INTERÉS MENSUAL ES INCORRECTA");
            usuarioRepository.save(usuario);
            //-------------------------------------------------------------------------//
            return creditoRepository.findById(savedSolicitud.getId()).orElse(null);
        }
        //-------------------------------------------------------------------------//-------------------------------------------------------------------------//
        // [R1]- RELACIÓN CUOTA/INGRESO------------------------------------//
        //double cuota = montop * (Math.round(intanu / 12 / 100) * Math.pow(1 + Math.round(intanu / 12 / 100), meses)) / (Math.pow(1 + Math.round(intanu / 12 / 100), meses) - 1);
        double cuota = montop * (intmen * Math.pow(1 + intmen, meses)) / (Math.pow(1 + intmen, meses) - 1);
        double cuotaing = (Math.round(cuota) / Math.round(usuario.getIngresos())) * 100;
        if (cuotaing <= 0.35) {
            // TIENE QUE SER MENOR O IGUAL QUE EL UMBRAL ESTABLECIDO POR EL BANCO
            System.out.println("RELACIÓN CUOTA/INGRESO ACEPTADA");
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
            return creditoRepository.findById(savedSolicitud.getId()).orElse(null);
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
            return creditoRepository.findById(savedSolicitud.getId()).orElse(null);
            //-------------------------------------------------------------------------//-------------------------------------------------------------------------//
        } else if (dicom.length < 4 || dicom[0] != '%' || dicom[1] != 'P' || dicom[2] != 'D' || dicom[3] != 'F') {
            System.out.println("DICOM no es un archivo PDF.");
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
            return creditoRepository.findById(savedSolicitud.getId()).orElse(null);
            //-------------------------------------------------------------------------//-------------------------------------------------------------------------//
        } else {
            System.out.println("DICOM GUARDADO EN FORMATO PDF.");
        }
        //-------------------------------------------------------------------------//-------------------------------------------------------------------------//
        // [R3]- -------------------------------------------------------------------//
        // VER SI DENTRO AÑOS DE TRABAJO ES MAYOR O IGUAL A 2
        if (usuario.getWorkage() >= 1 && usuario.getIndependiente().equalsIgnoreCase("ASALARIADO")) {
            System.out.println("AÑOS DE TRABAJO ACTUAL ACEPTADOS");
        } else if (usuario.getIndependiente().equalsIgnoreCase("INDEPENDIENTE")) {
            System.out.println("TRABAJADOR INDEPENDIENTE");
            // OBTENCIÓN DE LOS AHORROS DEL USUARIO
            List<AhorrosEntity> ahorros = usuario.getAhorros(); // OBTENGO LOS AHORROS DEL USUARIO -> PARA REALIZAR EVALUACIÓN -> ||||||||||||||||||||||||||||VER QUE HACER||||||||||||||||||||||||||||
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
            return creditoRepository.findById(savedSolicitud.getId()).orElse(null);
        }
        // [R4]--------------------------------------------------------------------//
        // RECHAZO DE LA SOLICITUD SI LA SUMA DE LAS DEUDAS ES MAYOR AL 50% DE LOS INGRESOS
        if (usuario.getSumadeuda() > usuario.getIngresos() * 0.5) {
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
            return creditoRepository.findById(savedSolicitud.getId()).orElse(null);
        } else {
            System.out.println("LA SUMA DE DEUDAS ES MENOR AL 50% DE LOS INGRESOS");
        }
        // [R5]--------------------------------------------------------------------// INFRESAR VALOR DE PROPIEDAD
        //System.out.println("MONTO DEL PRÉSTAMO: " + montop);
        //System.out.println("VALOR DE LA PROPIEDAD * 0.7: " + usuario.getValorpropiedad() * 0.7);
        //System.out.print("PLAZO: " + plazo);
        //System.out.print("TASA DE INTERÉS ANUAL: " + intanu);
        //System.out.print("CANTIDAD DE PROPIEDADES: " + usuario.getHouses());
        //-------------------------------------------------------------------------//
        if (plazo == 30 && 0.035 <= intanu && intanu <= 0.05 && montop <= usuario.getValorpropiedad() * 0.8 &&
                solicitud.getComprobanteIngresos() != null && solicitud.getComprobanteIngresos().length > 0 &&
                solicitud.getCertificadoAvaluo() != null && solicitud.getCertificadoAvaluo().length > 0 &&
                solicitud.getHistorialCrediticio() != null && solicitud.getHistorialCrediticio().length > 0 && usuario.getHouses() == 1) {
            System.out.println("CRÉDITO APROBADO: PRIMERA VIVIENDA");
            //-------------------------------------------------------------------------//
        } else if (plazo == 20 && 0.04 <= intanu && intanu <= 0.06 && montop <= usuario.getValorpropiedad() * 0.7 &&
                solicitud.getComprobanteIngresos() != null && solicitud.getComprobanteIngresos().length > 0 &&
                solicitud.getCertificadoAvaluo() != null && solicitud.getCertificadoAvaluo().length > 0 &&
                solicitud.getEscrituraPrimeraVivienda() != null && solicitud.getEscrituraPrimeraVivienda().length > 0 &&
                solicitud.getHistorialCrediticio() != null && solicitud.getHistorialCrediticio().length > 0 && usuario.getHouses() > 1) {
            System.out.println("CRÉDITO APROBADO: SEGUNDA VIVIENDA");
            //-------------------------------------------------------------------------//
        } else if (plazo == 25 && 0.05 <= intanu && intanu <= 0.07 && montop <= usuario.getValorpropiedad() * 0.6 &&
                solicitud.getEstadosFinancieros() != null && solicitud.getEstadosFinancieros().length > 0 &&
                solicitud.getComprobanteIngresos() != null && solicitud.getComprobanteIngresos().length > 0 &&
                solicitud.getCertificadoAvaluo() != null && solicitud.getCertificadoAvaluo().length > 0 &&
                solicitud.getPlanNegocios() != null && solicitud.getPlanNegocios().length > 0 && usuario.getObjective().equalsIgnoreCase("PROPIEDAD COMERCIAL")) {
            System.out.println("CRÉDITO APROBADO: PROPIEDADES COMERCIALES");
            //-------------------------------------------------------------------------//
        } else if (plazo == 15 && 0.045 <= intanu && intanu <= 0.06 && montop <= usuario.getValorpropiedad() * 0.5 &&
                solicitud.getComprobanteIngresos() != null && solicitud.getComprobanteIngresos().length > 0 &&
                solicitud.getPresupuestoRemodelacion() != null && solicitud.getPresupuestoRemodelacion().length > 0 &&
                solicitud.getCertificadoAvaluo() != null && solicitud.getCertificadoAvaluo().length > 0 && usuario.getObjective().equalsIgnoreCase("REMODELACIÓN")) {
            System.out.println("CRÉDITO APROBADO: REMODELACIÓN");
        } else {
            // MODIFICAR EL ESTADO DE LA SOLICITUD A "RECHAZADA"
            solicitud.setState("RECHAZADA");
            //-------------------------------------------------------------------------//-------------------------------------------------------------------------//
            // ACTUALIZACIÓN DE solicitud EN EL ESPACIO DE solicitud DEL USUARIO
            usuario.setSolicitud(solicitud);
            //-------------------------------------------------------------------------//
            CreditoEntity savedSolicitud = creditoRepository.save(solicitud);
            //-------------------------------------------------------------------------//
            usuario.addNotification("CREDITO RECHAZADO: NO CUMPLE CON LOS REQUISITOS");
            usuarioRepository.save(usuario);
            //-------------------------------------------------------------------------//
            return creditoRepository.findById(savedSolicitud.getId()).orElse(null);
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
            return creditoRepository.findById(savedSolicitud.getId()).orElse(null);
        } else {
            System.out.println("EDAD DENTRO DE RANGO ACEPTABLE");
        }
        // [R7]--------------------------------------------------------------------//
        int errores = 5; // CANTIDAD DE ERRORES PERMITIDOS PARA OTORGAR UN ESTADO A LA SOLICITUD
        // [R7]-----[R71]---------------------------------------------------------//--------------------------------------------------------------------//
        // SALARIO MÍNIMO REQUERIDO
        List<AhorrosEntity> ahorros = usuario.getAhorros();
        int valorPositivoMasPequeno = usuarioService.obtenerValorPositivoMasPequeno(ahorros);
        if(valorPositivoMasPequeno >= Math.round(usuario.getSolicitud().getMontop()*0.1)){
            System.out.println("SALDO POSITIVO MÁS PEQUEÑO MAYOR O IGUAL AL 10% DEL MONTO DEL PRÉSTAMO");
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
        System.out.println("CANTIDAD DE MESES DE AHORRO: " + cantidad);
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
            System.out.println("ERROR-2: MENOS DE 12 MESES DE AHORRO");
            errores--;// CHEQUEO NEGATIVO
        }else if (saldo < 0) { // SI EL SALDO ES NEGATIVO MARCO MAL EL ANALISIS
            //-------------------------------------------------------------------------//  -------------------------------------------------------------------------------------------------> REVIZAR |||||||||||||||||||||||||||||||||||||||||
            usuario.addNotification("ERROR-2: SALDO TOTAL ES NEGATIVO");
            usuarioRepository.save(usuario);
            System.out.println("ERROR-2: SALDO TOTAL ES NEGATIVO");
            errores--;// CHEQUEO NEGATIVO
        }else if (bandera == 1) { // SI HAY UN RETIRO MAYOR A 50% DEL SALDO DENTRO DE LOS ULTIMOS 12 MESES
            //-------------------------------------------------------------------------//  -------------------------------------------------------------------------------------------------> REVIZAR |||||||||||||||||||||||||||||||||||||||||
            usuario.addNotification("ERROR-2: RETIRO MAYOR A 50% DEL SALDO");
            usuarioRepository.save(usuario);
            System.out.println("ERROR-2: RETIRO MAYOR A 50% DEL SALDO");
            errores--;// CHEQUEO NEGATIVO
        }else{
            System.out.println("HISTORIAL DE AHORRO CONSISTENTE");
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
            System.out.println("ERROR-3: HAY MENOS DE 12 DEPOSITOS MENSUALES O MENOS DE 4 DEPOSITOS TRIMESTRALES");
            bandera = 1; // SI ES 1, NO NECESITO SEGUIR RESTANDO ERRORES, PARA LA COMPARACIÓN DIGUIENTE
        }
        // MONTO MINIMO: LOS DEPOSITOS DEBEN SUMAR AL MENOS EL 5% DE LOS INGRESOS MENSUALES
        if (sumdepos < usuario.getIngresos() * 0.05) {
            //-------------------------------------------------------------------------//  -------------------------------------------------------------------------------------------------> REVIZAR |||||||||||||||||||||||||||||||||||||||||
            usuario.addNotification("ERROR-3: LOS DEPOSITOS NO SUMAN AL MENOS EL 5% DE LOS INGRESOS MENSUALES");
            usuarioRepository.save(usuario);
            System.out.println("ERROR-3: LOS DEPOSITOS NO SUMAN AL MENOS EL 5% DE LOS INGRESOS MENSUALES");
            bandera = 1; // SI ES 1, NO NECESITO SEGUIR RESTANDO ERRORES, PARA LA COMPARACIÓN DIGUIENTE
        }
        // ------------------------------------------------------------------------//
        if (bandera > 0){
            errores--; // CHEQUEO NEGATIVO
        }else{
            System.out.println("DEPOSITOS MENSUALES Y TRIMESTRALES ACEPTADOS Y SUMAN AL MENOS EL 5% DE LOS INGRESOS MENSUALES");
        }
        // [R7]-----[R74]---------------------------------------------------------//--------------------------------------------------------------------//
        if (cantidad < 24 && acumulado >= usuario.getSolicitud().getMontop()*0.2 ){
            System.out.println("CUENTA DE AHORROS MENOR A 2 AÑOS Y CON UN SALDO ACUMULADO AL MENOS DE 20% DEL PRESTAMO");
        }else if(cantidad >= 24 && acumulado >= usuario.getSolicitud().getMontop()*0.1){
            System.out.println("CUENTA DE AHORROS MAYOR O IGUAL A 2 AÑOS Y CON UN SALDO ACUMULADO AL MENOS DE 10% DEL PRESTAMO");
        }else{
            //-------------------------------------------------------------------------//  -------------------------------------------------------------------------------------------------> REVIZAR |||||||||||||||||||||||||||||||||||||||||
            usuario.addNotification("ERROR-4: CUENTA DE AHORROS NO CUMPLE CON RELACIÓN ENTRE AÑOS DE ANTIUGEDAD Y SALDO ACUMULADO RESPECTO AL MONTO DEL PRÉSTAMO");
            usuarioRepository.save(usuario);
            //-------------------------------------------------------------------------//
            System.out.println("ERROR-4: CUENTA DE AHORROS NO CUMPLE CON RELACIÓN ENTRE AÑOS DE ANTIUGEDAD Y SALDO ACUMULADO RESPECTO AL MONTO DEL PRÉSTAMO");
            errores--; // CHEQUEO NEGATIVO
        }
        // [R7]-----[R75]---------------------------------------------------------//--------------------------------------------------------------------//
        bandera = 0;  // REUTILIZACIÓN, AHORA SI ES 1 O MAYOR, ES PORQUE HAY UN RETIRO MAYOR AL 30% DEL SALDO EN LOS ULTIMOS 6 MESES
        for (AhorrosEntity ahorro : ahorros) {
            if(ahorro.getTipo().equalsIgnoreCase("RETIRO") && Math.abs(ahorro.getTransaccion()) > saldo*0.3 && cantidad <= 6){
                bandera = 1;
            }
            cantidad = cantidad - 1;
        }
        // ------------------------------------------------------------------------//
        if (bandera != 1) {
            System.out.println("RETIROS MAYORES AL 30% DEL SALDO EN LOS ULTIMOS 6 MESES");
            errores--; // CHEQUEO NEGATIVO
        }
        // [R7]-----[CHEQUEO DE EVALUACIONES]----------------------------//--------------------------------------------------------------------//
        System.out.println("FINALIZACIÓN DE LA EVALUACIÓN DE CRÉDITO");
        System.out.println("ERRORES ENCONTRADOS: " + errores);
        if (errores == 5) {
            System.out.println("SOLICITUD DE CRÉDITO APROBADO");
            // MODIFICAR EL ESTADO DE LA SOLICITUD A "APROBADO"
            solicitud.setState("APROBADO");
            //-------------------------------------------------------------------------//-------------------------------------------------------------------------//
            // ACTUALIZACIÓN DE solicitud EN EL ESPACIO DE solicitud DEL USUARIO
            usuario.setSolicitud(solicitud);
            //-------------------------------------------------------------------------//
            CreditoEntity savedSolicitud = creditoRepository.save(solicitud);
            //-------------------------------------------------------------------------//
            return creditoRepository.findById(savedSolicitud.getId()).orElse(null);
        } else if (errores >= 3 && errores < 5) {
            System.out.println("SOLICITUD DE CRÉDITO EN REVISIÓN ADICIONAL");
            // MODIFICAR EL ESTADO DE LA SOLICITUD A "RECHAZADA"
            solicitud.setState("REVISIÓN ADICIONAL");
            //-------------------------------------------------------------------------//-------------------------------------------------------------------------//
            // ACTUALIZACIÓN DE solicitud EN EL ESPACIO DE solicitud DEL USUARIO
            usuario.setSolicitud(solicitud);
            //-------------------------------------------------------------------------//
            CreditoEntity savedSolicitud = creditoRepository.save(solicitud);
            //-------------------------------------------------------------------------//
            System.out.println("CRÉDITO RECHAZADO: EL SOLICITANTE ESTÁ MUY CERCANO A LA EDAD MÁXIMA PERMITIDA");
            return creditoRepository.findById(savedSolicitud.getId()).orElse(null);
        } else {
            System.out.println("SOLICITUD DE CRÉDITO RECHAZADO");
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
            System.out.println("CRÉDITO RECHAZADO: EL SOLICITANTE ESTÁ MUY CERCANO A LA EDAD MÁXIMA PERMITIDA");
            return creditoRepository.findById(savedSolicitud.getId()).orElse(null);
        }
    }
    //-----------------------[P6]- FUNCIONES DE CALCULO DE COSTOS TOTALES-------------------------//
    public CreditoEntity calcularCostosTotales(Long userId){
        if (userId == null || !usuarioRepository.existsById(userId)) {
            throw new IllegalArgumentException("ERROR: EL ID DE USUARIO INGRESADO NO SE ENCUENTRA REGISTRADO EN EL SISTEMA, POR FAVOR INGRESAR UN ID REGISTRADO O REGISTRARSE EN EL SISTEMA");
        }
        UsuarioEntity usuario = usuarioRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("ERROR: USUARIO NO ENCONTRADO"));
        CreditoEntity solicitud = usuario.getSolicitud(); // OBTENGO LA SOLICITUD DEL USUARIO
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
            System.out.println("TASA DE INTERÉS MENSUAL CORRECTA");
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
            System.out.println("TASA DE INTERÉS MENSUAL INCORRECTA");
            return creditoRepository.findById(savedSolicitud.getId()).orElse(null);
        }
        //-------------------------------------------------------------------------//-------------------------------------------------------------------------//
        // [PASO 1]- CALCULO DE CUOTA MENSUAL DEL PRESTAMO------------------------------------//
        double cuota = montop * (intmen * Math.pow(1 + intmen, meses)) / (Math.pow(1 + intmen, meses) - 1);

        System.out.println("CUOTA MENSUAL DEL PRÉSTAMO: "+Math.round(cuota));
        // [PASO 2]- CALCULO DE LOS SEGUROS------------------------------------//
        double segudesgtotal = montop * segudesg;
        System.out.println("SEGURO DE DESGRAVAMEN: "+Math.round(segudesgtotal));
        // [PASO 3]- CALCULO DE COMISION POR ADMINISTRACIÓN--------------//
        double comiadtotal = montop * comiad;
        System.out.println("COMISIÓN ADMINISTRATIVA: "+Math.round(comiadtotal));
        // [PASO 4]- CALCULO DE COSTO TOTAL DE PRESTAMO------------------//
        double costomensual = cuota + segudesgtotal + comiadtotal;
        double costototal = (costomensual * meses*12) + montop;
        System.out.println("COSTO MENSUAL DEL PRÉSTAMO: "+Math.round(costomensual));
        // [PASO 5]- REVICIÓN Y ENTREGA DE SOLICITUD-------------------------//
        System.out.println("COSTO TOTAL DEL PRÉSTAMO: "+Math.round(costototal));
        return null;
    }
}
