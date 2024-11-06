package com.example.entrega1.services;
//---------------------------------[IMPORTS DE SERVICO]----------------------------------------//
import com.example.entrega1.entities.UsuarioEntity;
import com.example.entrega1.repositories.UsuarioRepository;

import com.example.entrega1.entities.AhorrosEntity;
import com.example.entrega1.repositories.AhorrosRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
@Service
//--------------------------------[FUNCIONES DE SERVICO]---------------------------------------//
public class AhorrosService {
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    AhorrosRepository ahorrosRepository;
    //---------------------------------------[CRUD]-----------------------------------------------//
    // * OBTENER TODOS LOS AHORROS -> ENTREGA UNA LISTA DE AHORROS
    public ArrayList<AhorrosEntity> getAhorros() {
        return (ArrayList<AhorrosEntity>) ahorrosRepository.findAll();
    }
    //------------------------------------------------------------------//
    // * OBTENER AHORRO POR ID -> ENTREGA UN AHORRO -> REQUIERE DE UN CICLO FOR PARA OBTENER LISTA AHORROS
    public AhorrosEntity getAhorrosById(Long id) {
        return ahorrosRepository.findById(id).get();
    }
    //------------------------------------------------------------------//
    // * GUARDAR AHORRO -> ENTREGA UN AHORRO
    public AhorrosEntity saveAhorros(AhorrosEntity ahorros) {
        return ahorrosRepository.save(ahorros);
    }
    //------------------------------------------------------------------//
    // * ACTUALIZAR AHORRO -> ENTREGA UN AHORRO
    public AhorrosEntity updateAhorros(AhorrosEntity ahorros) {
        return ahorrosRepository.save(ahorros);
    }
    //------------------------------------------------------------------//
    // * ELIMINAR AHORRO -> ENTREGA UN MAPA CON EL VALOR DE LA ELIMINACIÓN
    public Map<String, Boolean> deleteAhorros(Long id) {
        ahorrosRepository.deleteById(id);
        return Map.of("deleted", true);
    }
    //------------------------------------------------------------------//
}
