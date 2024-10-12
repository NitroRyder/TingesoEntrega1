package com.example.entrega1.repositories;
//-------------------------------[IMPORTS DE REPOSITORIO]--------------------------------------//
import com.example.entrega1.entities.CreditoEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
//------------------------------[INTERFACE DE REPOSITORIO]-------------------------------------//
public interface CreditoRepository extends JpaRepository<CreditoEntity, Long> {

}
