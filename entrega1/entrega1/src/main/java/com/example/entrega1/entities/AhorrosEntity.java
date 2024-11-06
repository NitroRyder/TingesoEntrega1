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
    @Column(name = "transaccion", nullable = false)
    private int transaccion;    // VALOR DE TRANSACCIÓN
    //-----------------------------------------------------------------------------------------//
    @Column(name = "tipo", nullable = false)
    private String tipo;    // TIPO DE TRANSACCIÓN: DEPOSITO | PAGO | TRANSFERENCIA | RETIRO
    //-----------------------------------------------------------------------------------------//
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private UsuarioEntity usuario; // USUARIO ASOCIADO A LOS AHORROS
    //-----------------------------------------------------------------------------------------//
}
