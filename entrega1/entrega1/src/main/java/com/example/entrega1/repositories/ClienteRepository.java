package com.example.entrega1.repositories;
//-------------------------------[IMPORTS DE REPOSITORIO]--------------------------------------//
import com.example.entrega1.entities.ClienteEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
//------------------------------[INTERFACE DE REPOSITORIO]-------------------------------------//
public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> { // VALOR DE REPOSITORIO DE CLIENTES
    //----------------------------------[BUSQUEDA POR VALOR]-----------------------------------//
    // -> ENTREGA LA TABLA COMPLETA DE CLIENTE SOLICITADO
    // * ENCONTRAR POR RUT
    List<ClienteEntity> findByRut(String rut);
    // * ENCONTRAR POR NOMBRE
    List<ClienteEntity> findByName(String name);
    // * ENCONTRAR POR EMAIL
    List<ClienteEntity> findByEmail(String email);
    // * ENCONTRAR POR DOCUMENTO
    List<ClienteEntity> findByDocument(String document);
    // * ENCONTRAR POR NÚMERO DE CASAS
    List<ClienteEntity> findByHouses(int houses);
    // * ENCONTRAR POR INGRESOS
    List<ClienteEntity> findByIngresos(int ingresos);
    //--------------------------------[BUSQUEDA POR CONDICIÓN]---------------------------------//
    // -> RECORTA LA TABLA SEGÚN EL DATO QUE ESTOY BUSCANDO, ME DÁ MAS DE UN CLIENTE
    // + OBTENER TODOS LOS RUT´S
    @Query("SELECT c.rut FROM ClienteEntity c WHERE c.rut IS NOT NULL")
    List<String> findAllRuts();
    // + OBTENER TODOS LOS NOMBRES
    @Query("SELECT c.name FROM ClienteEntity c WHERE c.name IS NOT NULL")
    List<String> findAllNames();
    // + OBTENER TODOS LOS EMAILS
    @Query("SELECT c.email FROM ClienteEntity c WHERE c.email IS NOT NULL")
    List<String> findAllEmails();
    // + OBTENER TODOS LOS DOCUMENTOS
    @Query("SELECT c.document FROM ClienteEntity c WHERE c.document IS NOT NULL")
    List<String> findAllDocuments();
    // + OBTENER TODOS LOS NÚMEROS DE CASAS
    @Query("SELECT c.houses FROM ClienteEntity c WHERE c.houses IS NOT NULL")
    List<Integer> findAllHouses();
    // + OBTENER TODOS LOS INGRESOS
    @Query("SELECT c.ingresos FROM ClienteEntity c WHERE c.ingresos IS NOT NULL")
    List<Integer> findAllIngresos();
    //-----------------------------------------------------------------------------------------//
}
