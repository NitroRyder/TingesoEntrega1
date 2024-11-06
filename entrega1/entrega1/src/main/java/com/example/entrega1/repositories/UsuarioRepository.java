package com.example.entrega1.repositories;
//-------------------------------[IMPORTS DE REPOSITORIO]--------------------------------------//
import com.example.entrega1.entities.UsuarioEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
//------------------------------[INTERFACE DE REPOSITORIO]-------------------------------------//
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> { // VALOR DE REPOSITORIO DE CLIENTES
    //----------------------------------[BUSQUEDA POR VALOR]-----------------------------------//
    // -> ENTREGA LA TABLA COMPLETA DE CLIENTE SOLICITADO
    // * ENCONTRAR POR RUT
    List<UsuarioEntity> findByRut(String rut);
    //------------------------------------------------------------------//
    // * ENCONTRAR POR OBJETIVO
    List<UsuarioEntity> findByObjective(String objective);
    //--------------------------------[BUSQUEDA POR CONDICIÓN]---------------------------------//
    // -> RECORTA LA TABLA SEGÚN EL DATO QUE ESTOY BUSCANDO, ME DÁ MAS DE UN CLIENTE
    // + OBTENER TODOS LOS RUT´S
    @Query("SELECT c.rut FROM UsuarioEntity c WHERE c.rut IS NOT NULL")
    List<String> findAllRuts();
    //------------------------------------------------------------------//
    // + OBTENER TODOS LOS OBJETIVOS
    @Query("SELECT c.objective FROM UsuarioEntity c WHERE c.objective IS NOT NULL")
    List<String> findAllObjectives();
    boolean existsByRut(String rut);
    //-----------------------------------------------------------------------------------------//
}