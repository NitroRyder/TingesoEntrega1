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
    // * GUARDAR AHORRO -> RECIVE UN AHORRO Y LO GUARDA
    public AhorrosEntity saveAhorro(AhorrosEntity ahorro) {
        return ahorrosRepository.save(ahorro);
    }
    //------------------------------------------------------------------//
    // * ACTUALIZAR AHORRO
    public AhorrosEntity updateAhorro(AhorrosEntity ahorro) {
        return ahorrosRepository.save(ahorro);
    }
    //------------------------------------------------------------------//
    // * ELIMINAR AHORRO -> SI HAY ALGÚN FALLO, ENTREGA EXCEPCIÓN
    public void deleteAhorro(Long id) throws Exception {
        try {
            ahorrosRepository.deleteById(id);
        }
        catch (Exception e) {
            throw new Exception("NO SE PUDO ELIMINAR EL AHORRO CON id: " + id);
        }
    }
    //------------------------------------------------------------------//
    // * ELIMINAR TODOS LOS AHORROS
    public void deleteAllAhorros() {
        ahorrosRepository.deleteAll();
    }
    //------------------------------[OPERACIONES AHORRO]------------------------------------//
    //--------------------------------------[GETTERS]--------------------------------------------//
    // + OBTENER AHORRO POR ID -> REGRESA EXCEPCIÓN SI NO EXISTE
    public AhorrosEntity getAhorroById(Long id) {
        return ahorrosRepository.findById(id).get();
    }
    //--------------------------------------[AGREGAR]--------------------------------------------//
    // AGREGAR AHORRO A USUARIO POR SU RUT
    public AhorrosEntity addAhorroToUsuario(AhorrosEntity ahorro, String rut) {
        UsuarioEntity usuario = usuarioRepository.findByRut(rut).get(0);
        ahorro.setUsuario(usuario);
        return ahorrosRepository.save(ahorro);
    }
    //------------------------------------------------------------------//
    public AhorrosEntity getAhorrosById(Long id) {
        return ahorrosRepository.findById(id).get();
    }
    //------------------------------------------------------------------//
    public AhorrosEntity saveAhorros(AhorrosEntity ahorros) {
        return ahorrosRepository.save(ahorros);
    }
    //------------------------------------------------------------------//
    public AhorrosEntity updateAhorros(AhorrosEntity ahorros) {
        return ahorrosRepository.save(ahorros);
    }

    public Map<String, Boolean> deleteAhorros(Long id) {
        ahorrosRepository.deleteById(id);
        return Map.of("deleted", true);
    }
    //------------------------------------------------------------------//
}
