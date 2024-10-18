package com.example.entrega1.entities;
//---------------------------------[IMPORTS DE ENTIDAD]----------------------------------------//
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//----------------------------------[CLASE DE ENTIDAD]-----------------------------------------//
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(unique = true, nullable = false)
    private Long id;
    //-----------------------------------------------------------------------------------------//
    @Column(name = "rut")
    private String rut;     // VALOR DE RUT -> IRREPETIBLE
    //-----------------------------------------------------------------------------------------//
    @Column(name = "name")
    private String name;    // VALOR DE NOMBRE
    //-----------------------------------------------------------------------------------------//
    @Column(name = "age", nullable = false)
    private int age = 0;   // VALOR DE EDAD
    //-----------------------------------------------------------------------------------------//
    @Column(name = "workage", nullable = false)
    private int workage = 0;   // VALOR DE AÑOS DE TRABAJO
    //-----------------------------------------------------------------------------------------//
    @ElementCollection
    @CollectionTable(name = "usuario_documents", joinColumns = @JoinColumn(name = "usuario_id"))
    @Column(name = "documents")
    private List<String> documents;  // List of PDF file paths  -> // VALOR DE DOCUMENTOS ----> ES UNA LISTA DE PDF
    //-----------------------------------------------------------------------------------------//
    @Column(name = "houses", nullable = false)
    private int houses = 0;   // VALOR DE NÚMERO DE CASAS
    //-----------------------------------------------------------------------------------------//
    @Column(name = "valorpropiedad", nullable = true)
    private int valorpropiedad = 0;   // VALOR DE PROPIEDAD
    //-----------------------------------------------------------------------------------------//
    @Column(name = "ingresos", nullable = false)
    private int ingresos = 0;   // VALOR DE INGRESOS MENSUALES DEL CLIENTE
    //-----------------------------------------------------------------------------------------//
    @Column(name = "sumadeuda", nullable = true)
    private int sumadeuda = 0;   // VALOR DE DEUDA
    //-----------------------------------------------------------------------------------------//
    @Column(name = "objective", nullable = true)
    private String objective; // VALOR DE OBJETIVO -> ES TEXTO
    //-----------------------------------------------------------------------------------------//
    @Column(name = "independiente", nullable = true)
    private String independiente; // VALOR DE SI EL TRABAJADOR ES  INDEPENDIENTE O NO -> ES BOOLEANO
    //-----------------------------------------------------------------------------------------//
    // LISTA DE AHORROS (HISTORIAL)
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario", orphanRemoval = true)
    private List<AhorrosEntity> ahorros = new ArrayList<>(); // LISTA DE AHORROS -> HISTORIAL DE AHORROS
    //-----------------------------------------------------------------------------------------//
    // UN CLIENTE TIENE UNA SOLICITUD DE CRÉDITO
    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "solicitud_id", referencedColumnName = "id")
    private CreditoEntity solicitud; // SOLICITUD DE CRÉDITO
    //-----------------------------------------------------------------------------------------//
    // UN CLIENTE TIENE UNA LISTA DE CREDITOS (HISTORIAL)
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario", orphanRemoval = true)
    private List<CreditoEntity> creditos = new ArrayList<>(); // LISTA DE CRÉDITOS -> HISTORIAL DE CREDITOS
    //-----------------------------------------------------------------------------------------//
    // LISTA DE NOTIFICACIONES
    @ElementCollection
    @CollectionTable(name = "usuario_notifications", joinColumns = @JoinColumn(name = "usuario_id"))
    @Column(name = "notifications")
    private List<String> notifications = new ArrayList<>();  // List of notifications -> // VALOR DE NOTIFICACIONES ----> ES UNA LISTA DE NOTIFICACIONES
    //-----------------------------------------------------------------------------------------//
    // ----------------------------[CONSTRUCTOR DE USUARIO]---------------------------// -> OPCIÓN POR CREACIÓN POR POSTMAN
    //-----------------------------------------------------------------------------------------//
    public <E> UsuarioEntity(long id, String rut, String name, int age, int workage, List<String> documents, int houses, int valorpropiedad, int ingresos, int sumadeuda, String objective, String independiente, ArrayList<E> ahorros, Object solicitud, ArrayList<E> creditos, ArrayList<E> notifications) {
    }
    //-----------------------------------------------------------------------------------------//
    // -------------------------[CONDICIONES DE LIMITACIONES]-------------------------// -> OPCIÓN POR CREACIÓN POR POSTMAN
    //-----------------------------------------------------------------------------------------//
    // LIMITACIÓN DE VALOR; SOLO SE ACEPTAN RUTS VÁLIDOS
    public void setRut(String rut) {
        if (rut == null || rut.isEmpty()) {
            throw new IllegalArgumentException("EL RUT DEBE SER VÁLIDO");
        }
        // Remove dots from the RUT
        String cleanedRut = rut.replace(".", "");
        // Validate the cleaned RUT
        if (!cleanedRut.matches("^[0-9]{7,8}-[0-9Kk]$")) {
            throw new IllegalArgumentException("EL RUT DEBE SER VÁLIDO");
        }
        this.rut = cleanedRut;
    }
    //-----------------------------------------------------------------------------------------//
    // LIMITACIÓN DE VALOR; SOLO SE ACEPTAN NOMBRES NO NULOS
    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("EL NOMBRE NO PUEDE SER NULO");
        }
        this.name = name;
    }
    //-----------------------------------------------------------------------------------------//
    // LIMITACIÓN DE VALOR; SOLO SE ACEPTAN MAYOR O IGUAL A 0
    public void setWorkage(int workage) {
        if (workage < 0) {
            throw new IllegalArgumentException("EL VALOR DE AÑOS DE TRABAJO TIENE QUE SER MAYOR O IGUAL QUE: 0");
        }
        this.workage = workage;
    }
    //-----------------------------------------------------------------------------------------//
    // LIMITACIÓN DE VALOR; SOLO SE ACEPTAN MAYOR O IGUAL A 18
    public void setAge(int age) {
        if (age <= 18) {
            throw new IllegalArgumentException("LA EDAD TIENE QUE SER MAYOR QUE 18");
        }
        this.age = age;
    }
    //-----------------------------------------------------------------------------------------//
    // LIMITACIÓN DE VALOR; LA LISTA DE DOCUMENTOS SOLO POSEE TIPO PFD
    public void setDocuments(List<String> documents) {
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
        this.documents = documents;
    }
    //-----------------------------------------------------------------------------------------//
    // LIMITACIÓN DE VALOR; SOLO SE ACEPTAN MAYOR O IGUAL A 0
    public void setHouses(int nHouses) {
        if (nHouses < 0) {
            throw new IllegalArgumentException("EL VALOR DE CANTIDAD DE CASAS TIENE QUE SER MAYOR O IGUAL QUE: 0");
        }
        this.houses = nHouses;
    }
    //-----------------------------------------------------------------------------------------//
    // LIMITACIÓN DE VALOR; SOLO SE ACEPTAN MAYOR O IGUAL A 0
    public void setIngresos(int nIngresos) {
        if (nIngresos < 0) {
            throw new IllegalArgumentException("EL VALOR DE INGRESOS TIENE QUE SER MAYOR O IGUAL QUE: 0");
        }
        this.ingresos = nIngresos;
    }
    //-----------------------------------------------------------------------------------------//
    // LIMITACIÓN DE VALOR; SOLO SE ACEPTAN MAYOR O IGUAL A 0
    public void setValorpropiedad(int nValorpropiedad) {
        if (nValorpropiedad < 0) {
            throw new IllegalArgumentException("EL VALOR DE PROPIEDAD TIENE QUE SER MAYOR O IGUAL QUE: 0");
        }
        this.valorpropiedad = nValorpropiedad;
    }
    //-----------------------------------------------------------------------------------------//
    public void setSolicitud() {
        this.solicitud = new CreditoEntity();
    }
    public void setNotifications(List<String> notifications) {
        this.notifications = notifications;
    }
    //-----------------------------------------------------------------------------------------//
    public void addNotification(String notification) {
        this.notifications.add(notification);
    }
    //-----------------------------------------------------------------------------------------//
    // -------------------------------[GETTERS-SELECTIVOS]------------------------------//
    //-----------------------------------------------------------------------------------------//
    // OBTENER VALOR DE RUT
    public String getRut() {return rut;
    }
    //-----------------------------------------------------------------------------------------//
    // OBTENER VALOR DE NOMBRE
    public String getName() {return name;
    }
    //-----------------------------------------------------------------------------------------//
    // OBTENER VALOR DE EDAD
    public int getAge() {return age;
    }
    //-----------------------------------------------------------------------------------------//
    // OBTENER VALOR DE AÑOS DE TRABAJO
    public int getWorkage() {return workage;}
    //-----------------------------------------------------------------------------------------//
    // OBTENER VALOR DE DOCUMENTOS
    public List<String> getDocuments() {return documents;
    }
    //-----------------------------------------------------------------------------------------//
    // OBTENER VALOR DE NÚMERO DE CASAS
    public int getHouses() {return houses;
    }
    //-----------------------------------------------------------------------------------------//
    // OBTENER VALOR DE INGRESOS
    public int getIngresos() {return ingresos;
    }
    //-----------------------------------------------------------------------------------------//
    // OBTENER VALOR DE CRÉDITOS
    public List<CreditoEntity> getCreditos() {return creditos;
    }
    //-----------------------------------------------------------------------------------------//
    public List<String> getNotifications() {
        return notifications;
    }
    //-----------------------------------------------------------------------------------------//

}
