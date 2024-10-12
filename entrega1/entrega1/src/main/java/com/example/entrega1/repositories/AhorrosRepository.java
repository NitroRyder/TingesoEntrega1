package com.example.entrega1.repositories;
//-------------------------------[IMPORTS DE REPOSITORIO]--------------------------------------//
import com.example.entrega1.entities.AhorrosEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
//------------------------------[INTERFACE DE REPOSITORIO]-------------------------------------//
public interface AhorrosRepository extends JpaRepository<AhorrosEntity, Long> { // VALOR DE REPOSITORIO DE AHORROS

}
