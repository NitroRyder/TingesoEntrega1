package com.example.entrega1.services;
//---------------------------------[IMPORTS DE SERVICO]----------------------------------------//
import com.example.entrega1.entities.UsuarioEntity;
import com.example.entrega1.repositories.UsuarioRepository;

import com.example.entrega1.entities.CreditoEntity;
import com.example.entrega1.repositories.CreditoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
@Service
//--------------------------------[FUNCIONES DE SERVICO]---------------------------------------//
public class CreditoService {
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    CreditoRepository creditoRepository;
    //---------------------------------------[CRUD]-----------------------------------------------//
    // * OBTENER TODOS LOS CRÉDITOS -> ENTREGA UNA LISTA DE CRÉDITOS
    public ArrayList<CreditoEntity> getCreditos() {
        return (ArrayList<CreditoEntity>) creditoRepository.findAll();
    }
    //------------------------------------------------------------------//
    // * GUARDAR CRÉDITO -> RECIVE UN CRÉDITO Y LO GUARDA
    public CreditoEntity saveCredito(CreditoEntity credito) {
        return creditoRepository.save(credito);
    }
    //------------------------------------------------------------------//
    // * ACTUALIZAR CRÉDITO
    public CreditoEntity updateCredito(CreditoEntity credito) {
        return creditoRepository.save(credito);
    }
    //------------------------------------------------------------------//
    // * ELIMINAR CRÉDITO -> SI HAY ALGÚN FALLO, ENTREGA EXCEPCIÓN
    public void deleteCredito(Long id) throws Exception {
        try {
            creditoRepository.deleteById(id);
        }
        catch (Exception e) {
            throw new Exception("NO SE PUDO ELIMINAR EL CRÉDITO CON id: " + id);
        }
    }
    //------------------------------------------------------------------//
    // * ELIMINAR TODOS LOS CRÉDITOS
    public void deleteAllCreditos() {
        creditoRepository.deleteAll();
    }
    //------------------------------[OPERACIONES CRÉDITO]------------------------------------//
    //--------------------------------------[GETTERS]--------------------------------------------//
    // + OBTENER CRÉDITO POR ID -> REGRESA EXCEPCIÓN SI NO EXISTE
    public CreditoEntity getCreditoById(Long id) {
        return creditoRepository.findById(id).get();
    }
    //--------------------------------------[AGREGAR]--------------------------------------------//
    // AGREGAR CRÉDITO A USUARIO POR SU RUT
    public CreditoEntity addCreditoToUsuario(CreditoEntity credito, String rut) {
        if (rut == null) {
            throw new RuntimeException("RUT NO PUEDE SER NULO");
        } else if (!usuarioRepository.existsByRut(rut)) {
            throw new RuntimeException("USUARIO NO ENCONTRADO POR RUT");
        }
        UsuarioEntity usuario = usuarioRepository.findByRut(rut).get(0);
        credito.setUsuario(usuario);
        return creditoRepository.save(credito);
    }
}
