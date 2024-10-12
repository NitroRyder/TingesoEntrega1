package com.example.entrega1.entities;
//---------------------------------[IMPORTS DE ENTIDAD]----------------------------------------//
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ahorros")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//----------------------------------[CLASE DE ENTIDAD]-----------------------------------------//
public class AhorrosEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(unique = true, nullable = false)
    private Long id;
    //-----------------------------------------------------------------------------------------//
    @Column(name = "fecha", nullable = false)
    private Date fecha;     // VALOR DE FECHA -> IRREPETIBLE
    //-----------------------------------------------------------------------------------------//
    @Column(name = "saldo", nullable = false)
    private double saldo = 0;   // VALOR DE SALDO
    //-----------------------------------------------------------------------------------------//
    @Column(name = "retiros", nullable = false)
    private double retiros = 0;   // VALOR DE RETIROS
    //-----------------------------------------------------------------------------------------//
    @Column(name = "depositos", nullable = false)
    private double depositos = 0;   // VALOR DE DEPOSITOS
    //-----------------------------------------------------------------------------------------//
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private UsuarioEntity usuario; // USUARIO ASOCIADO A LOS AHORROS
    //-----------------------------------------------------------------------------------------//
}
