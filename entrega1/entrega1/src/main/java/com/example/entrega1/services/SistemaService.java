package com.example.entrega1.services;

// ELABORAR FUNCIONES A SOLICTADAS POR ACÁ -> CORE DE LA LOGICA DE NEGOCIOS
// -> PROCURAR DE QUE ESTÉ BIEN REALIZADO
// -> ESTE SERVICIO SOLO USA GETS PARA CALCULAR LAS COSAS
import com.example.entrega1.entities.AhorrosEntity;
import com.example.entrega1.entities.UsuarioEntity;
import com.example.entrega1.repositories.UsuarioRepository;
import com.example.entrega1.entities.CreditoEntity;
import com.example.entrega1.repositories.CreditoRepository;

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
        if(n == 30 && 0.35<= r && r <=0.5 && P <= V*0.8){ // Primera Vivienda
            System.out.println("PRÉSTAMO REALIZADO: Primera Vivienda");
            System.out.println("LOS DOCUMENTOS A INGRESAR PARA ESTE TIPO DE PRÉSTAMO SON:");
            System.out.println("1.- Comprobante de ingresos");
            System.out.println("2.- Certificado de avalúo");
            System.out.println("3.- Historial crediticio");
            r = r / 12 / 100; // PASA DE TASA ANUAL A MENSUAL
            n = n * 12;        // PASA DE AÑOS A CANTIDAD DE PAGOS
            double M = P * (r * Math.pow(1 + r, n)) / (Math.pow(1 + r, n) - 1);
            return M;
        } else if(n == 20 && 0.4<= r && r <=0.6 && P <= V*0.7){ //Segunda Vivienda
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
        } else if(n == 25 && 0.5<= r && r <=0.7 && P <= V*0.6){ //Propiedades Comerciales
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
        } else if(n ==15 && 0.45<= r && r <=0.6 && P<= V*0.5){ //Remodelación
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
    public UsuarioEntity registerUsuario(String rut, String name, int age, int workage, List<String> documents, int houses, int ingresos, String objective, List<AhorrosEntity> ahorros, List<CreditoEntity> creditos) {
        if (rut == null || rut.isEmpty() || !rut.matches("^[0-9]{7,8}-[0-9Kk]$")) {
            throw new IllegalArgumentException("EL RUT DEBE SER VÁLIDO");
        }
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("POR FAVOR INGRESAR NOMBRE");
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
        }
        //-------------------------------------------------------------------------//
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setRut(rut.toUpperCase());
        usuario.setName(name.toUpperCase());
        usuario.setAge(age);
        usuario.setWorkage(workage);
        usuario.setDocuments(documents);
        usuario.setHouses(houses);
        usuario.setIngresos(ingresos);
        usuario.setObjective(objective.toUpperCase());
        //-------------------------------------------------------------------------//
        // SETEO DE AHORROS // SI NO HAY, NO INGRESA NADA
        if (ahorros != null) {
            for (AhorrosEntity ahorro : ahorros) {
                ahorro.setUsuario(usuario);
            }
        }
        usuario.setAhorros(ahorros);
        //-------------------------------------------------------------------------//
        usuario.setSolicitud(null);
        //-------------------------------------------------------------------------//
        // SETEO DE HISTORIAL DE CREDITOS // SI NO HAY, NO INGRESA NADA
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
    public CreditoEntity createSolicitud(String rut, double montop, int plazo, double intanu, double intmen, double segudesg, double seguince, double comiad) {
        if (rut == null || rut.isEmpty() || !usuarioRepository.existsByRut(rut)) {
            throw new IllegalArgumentException("ERROR: EL RUT INGRESADO NO SE ENCUENTRA REGISTRADO EN EL SISTEMA, POR FAVOR INGRESAR UN RUT REGISTRADO O REGISTRARSE EN EL SISTEMA");
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
        //-------------------------------------------------------------------------//
        CreditoEntity solicitud = new CreditoEntity();
        solicitud.setMontop(montop);
        solicitud.setPlazo(plazo);
        solicitud.setIntanu(intanu);
        solicitud.setIntmen(intmen);
        solicitud.setSegudesg(segudesg);
        solicitud.setSeguince(seguince);
        solicitud.setComiad(comiad);
        solicitud.setState("PENDIENTE"); // SIEMPRE GUARDADO EN MAYUSCULA
        //-------------------------------------------------------------------------//
        // GUARDADO DE solicitud EN EL ESPACIO DE solicitud DEL USUARIO
        UsuarioEntity usuario = usuarioRepository.findByRut(rut).get(0);
        usuario.setSolicitud(solicitud);
        //-------------------------------------------------------------------------//
        CreditoEntity savedSolicitud = creditoRepository.save(solicitud);
        //-------------------------------------------------------------------------//
        return creditoRepository.findById(savedSolicitud.getId()).orElse(null);
    }
    //-----------------------[P4]- EVALUACIÓN DE CRÉDITO-------------------------//
    // VER QUE EL RUT ESTÉ GUARDADO EN LA BASE DE DATOS Y QUE TENGA UNA "solicitud" EN ESTADO "PENDIENTE"
    public CreditoEntity evaluateCredito(String rut){
        if(rut == null || rut.isEmpty() || !usuarioRepository.existsByRut(rut)){
            throw new IllegalArgumentException("ERROR: EL RUT INGRESADO NO SE ENCUENTRA REGISTRADO EN EL SISTEMA, POR FAVOR INGRESAR UN RUT REGISTRADO O REGISTRARSE EN EL SISTEMA");
        }
        UsuarioEntity usuario = usuarioRepository.findByRut(rut).get(0); // OBTENGO AL PRIMER USUARIO CON ESE RUT (DE IGUAL FORMA SÉ QUE NO SE REPITEN)
        CreditoEntity solicitud = usuario.getSolicitud(); // OBTENGO LA SOLICITUD DEL USUARIO
        //-------------------------------------------------------------------------//
        if(solicitud == null || !"PENDIENTE".equalsIgnoreCase(solicitud.getState())){
            throw new IllegalArgumentException("ERROR: EL USUARIO NO TIENE UNA SOLICITUD DE CRÉDITO RECHAZADA");
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
            System.out.println("TASA DE INTERÉS MENSUAL INCORRECTA");
            return creditoRepository.findById(savedSolicitud.getId()).orElse(null);
        }
        //-------------------------------------------------------------------------//-------------------------------------------------------------------------//
        // [R1]- RELACIÓN CUOTA/INGRESO------------------------------------//
        double cuota = montop * (intmen * Math.pow(1 + intmen, meses)) / (Math.pow(1 + intmen, meses) - 1);
        double cuotaing = (cuota / usuario.getIngresos()) * 100;
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
            System.out.println("RELACIÓN CUOTA/INGRESO RECHAZADA: CUOTA/INGRESO TIENE QUE SER MENOR O IGUAL QUE EL UMBRAL ESTABLECIDO POR EL BANCO");
            return creditoRepository.findById(savedSolicitud.getId()).orElse(null);
        }
        //-------------------------------------------------------------------------//-------------------------------------------------------------------------//
        // [R2]- HISTORIAL DE  CREDITOS--------------------------------------//
        // OBRENCIÓN DE documents DE USUARIO
        List<String> documents = usuario.getDocuments();
        if (documents != null) {
            for (String document : documents) {
                if (document == null) {
                    // MODIFICAR EL ESTADO DE LA SOLICITUD A "RECHAZADA"
                    solicitud.setState("RECHAZADA");
                    //-------------------------------------------------------------------------//-------------------------------------------------------------------------//
                    // ACTUALIZACIÓN DE solicitud EN EL ESPACIO DE solicitud DEL USUARIO
                    usuario.setSolicitud(solicitud);
                    //-------------------------------------------------------------------------//
                    CreditoEntity savedSolicitud = creditoRepository.save(solicitud);
                    //-------------------------------------------------------------------------//
                    System.out.println("NO SE HA INGRESADO UN ARCHIVO POR FAVOR INGRESAR DOCUMENTO DE TIPO pdf");
                    return creditoRepository.findById(savedSolicitud.getId()).orElse(null);
                    //-------------------------------------------------------------------------//-------------------------------------------------------------------------//
                }
                if (!document.toLowerCase().endsWith(".pdf")) {
                    // MODIFICAR EL ESTADO DE LA SOLICITUD A "RECHAZADA"
                    solicitud.setState("RECHAZADA");
                    //-------------------------------------------------------------------------//-------------------------------------------------------------------------//
                    // ACTUALIZACIÓN DE solicitud EN EL ESPACIO DE solicitud DEL USUARIO
                    usuario.setSolicitud(solicitud);
                    //-------------------------------------------------------------------------//
                    CreditoEntity savedSolicitud = creditoRepository.save(solicitud);
                    //-------------------------------------------------------------------------//
                    System.out.println("EL DOCUMENTO NO ES DEL TIPO PDF. POR FAVOR INGRESAR UN DOCUMENTO DE TIPO PDF");
                    return creditoRepository.findById(savedSolicitud.getId()).orElse(null);
                    //-------------------------------------------------------------------------//-------------------------------------------------------------------------//
                }
            }
        }
        // [R3]- -------------------------------------------------------------------//

        // [R4]--------------------------------------------------------------------//

        // [R5]--------------------------------------------------------------------//

        // [R6]--------------------------------------------------------------------//

        // [R7]--------------------------------------------------------------------//

        //-------------------------------------------------------------------------//
        System.out.println("EL DOCUMENTO HA PASADO LA EVALUACIÓN");
        return solicitud; // REVIZAR CIERRE DE EL ANALISIS
    }
}
