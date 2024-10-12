package com.example.entrega1.entities;
//---------------------------------[IMPORTS DE ENTIDAD]----------------------------------------//
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "prestamo")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//----------------------------------[CLASE DE ENTIDAD]-----------------------------------------//
public class PrestamoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    //-----------------------------------------------------------------------------------------//
    @Column(unique = true, nullable = false)
    private Long id;        // VALOR DE id -> RELACIONAR CON CLIENTE
    //-----------------------------------------------------------------------------------------//
    @Column(name = "tipo", nullable = false)
    private String tipo;    // VALOR DE TIPO DE PRÉSTAMO -> EXISTEN 4 TIPOS
    //-----------------------------------------------------------------------------------------//
    @Column(name = "plazo", nullable = false)
    private int plazo;     // VALOR DE PLAZO (años)     -> VALORES SEGUN TIPO
    //-----------------------------------------------------------------------------------------//
    @Column(name = "interes", nullable = false)
    private double interes;    // VALOR DE INTERÉS      -> RANGO SEGUN EL TIPO
    //-----------------------------------------------------------------------------------------//
    @Column(name = "montomax", nullable = false)
    private double montomax;   // VALOR DE MONTO   -> VALORES SEGUN TIPO
    //-----------------------------------------------------------------------------------------//
    @Column(name = "requisitos", nullable = false)
    private String requisitos; // VALOR DE REQUISITOS -> IDEAL QUE SEA LISTA
    //-----------------------------------------------------------------------------------------//
}
