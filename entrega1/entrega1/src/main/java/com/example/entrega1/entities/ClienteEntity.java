package com.example.entrega1.entities;
//---------------------------------[IMPORTS DE ENTIDAD]----------------------------------------//
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "clientes")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//----------------------------------[CLASE DE ENTIDAD]-----------------------------------------//
public class ClienteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    //-----------------------------------------------------------------------------------------//
    @Column(unique = true, nullable = false)
    private Long id;        // VALOR DE id
    //-----------------------------------------------------------------------------------------//
    @Column(name = "rut", nullable = false)
    private String rut;     // VALOR DE RUT
    //-----------------------------------------------------------------------------------------//
    @Column(name = "name")
    private String name;    // VALOR DE NOMBRE
    //-----------------------------------------------------------------------------------------//
    @Column(name = "email")
    private String email;   // VALOR DE CORREO ELECTRÓNICO
    //-----------------------------------------------------------------------------------------//
    @Column(name = "document", nullable = false) // ||||||||||||||REVIZAR A QUE SE REFIERE CON ESTE VALOR||||||||||||||
    private String document; // VALOR DE DOCUMENTO
    //-----------------------------------------------------------------------------------------//
    @Column(name = "houses", nullable = false)
    private int houses = 0;   // VALOR DE NÚMERO DE CASAS
    // LIMITACIÓN DE VALOR; SOLO SE ACEPTAN MAYOR O IGUAL A 0
    public void setHouses(int nHouses) {
        if (nHouses < 0) {
            throw new IllegalArgumentException("EL VALOR DE CANTIDAD DE CASAS TIENE QUE SER MAYOR O IGUAL QUE: 0");
        }
        this.houses = nHouses;
    }
    // OBTENER VALOR DE NÚMERO DE CASAS
    public int getHouses() {
        return houses;
    }
    //-----------------------------------------------------------------------------------------//
    @Column(name = "ingresos")
    private int ingresos = 0;   // VALOR DE INGRESOS
    // LIMITACIÓN DE VALOR; SOLO SE ACEPTAN MAYOR O IGUAL A 0
    public void setIngresos(int nIngresos) {
        if (nIngresos < 0) {
            throw new IllegalArgumentException("EL VALOR DE INGRESOS TIENE QUE SER MAYOR O IGUAL QUE: 0");
        }
        this.ingresos = nIngresos;
    }
    // OBTENER VALOR DE INGRESOS
    public int getIngresos() {
        return ingresos;
    }
    //-----------------------------------------------------------------------------------------//
}
