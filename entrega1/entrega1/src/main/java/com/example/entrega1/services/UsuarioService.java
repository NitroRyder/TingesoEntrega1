package com.example.entrega1.services;
//---------------------------------[IMPORTS DE SERVICO]----------------------------------------//
import com.example.entrega1.entities.CreditoEntity;
import com.example.entrega1.entities.UsuarioEntity;
import com.example.entrega1.repositories.CreditoRepository;
import com.example.entrega1.repositories.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
@Service
//--------------------------------[FUNCIONES DE SERVICO]---------------------------------------//
public class UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    CreditoRepository creditoRepository;
    //---------------------------------------[CRUD]-----------------------------------------------//
    // * OBTENER TODOS LOS CLIENTE -> ENTREGA UNA LISTA DE CLIENTES
    public ArrayList<UsuarioEntity> getUsuarios() {
        return (ArrayList<UsuarioEntity>) usuarioRepository.findAll();
    }
    //------------------------------------------------------------------//
    // * GUARDAR CLIENTE -> RECIVE UN CLIENTE Y LO GUARDA
    public UsuarioEntity saveUsuario(UsuarioEntity usuario) {
        return usuarioRepository.save(usuario);
    }
    //------------------------------------------------------------------//
    // * ACTUALIZAR CLIENTE
    public UsuarioEntity updateUsuario(UsuarioEntity usuario) {
        return usuarioRepository.save(usuario);
    }
    //------------------------------------------------------------------//
    // * ELIMINAR CLIENTE -> SI HAY ALGÚN FALLO, ENTREGA EXCEPCIÓN
    public void deleteUsuario(Long id) throws Exception {
        try {
            usuarioRepository.deleteById(id);
        }
        catch (Exception e) {
            throw new Exception("NO SE PUDO ELIMINAR EL CLIENTE CON id: " + id);
        }
    }
    //------------------------------------------------------------------//
    // * ELIMINAR TODOS LOS CLIENTES
    public void deleteAllUsuarios() {
        usuarioRepository.deleteAll();
    }
    //------------------------------[OPERACIONES CLIENTE]------------------------------------//
    //--------------------------------------[GETTERS]--------------------------------------------//
    // + OBTENER CLIENTE POR ID -> REGRESA EXCEPCIÓN SI NO EXISTE
    public UsuarioEntity getUsuarioById(Long id) {
        return usuarioRepository.findById(id).get();
    }
    //------------------------------------------------------------------//
    // + OBTENER CLIENTE POR RUT
    public List<UsuarioEntity> getUsuarioByRut(String rut) {
        return usuarioRepository.findByRut(rut);
    }
    //------------------------------------------------------------------//
    // + OBTENER CLIENTE POR OBJETIVO
    public List<UsuarioEntity>  getUsuarioByObjective(String objective) {
        return usuarioRepository.findByObjective(objective);
    }
    //------------------------------------------------------------------//
    // + OBTENER TODOS LOS RUT´S
    public List<String> findAllRuts() {
        return usuarioRepository.findAllRuts();
    }
    //----------------------------------------------------------------------------------------------//
    //------------------------------[OPERACIONES CREDITO]-----------------------------------//
    /*
    // + OBTENER HISTORIAL DE CREDITO DE UN USUARIO POR RUT
    public  List<CreditoEntity> getCreditosByRut(String rut){
        if(rut == null){
            throw new RuntimeException("RUT NO PUEDE SER NULO");
        } else if (!usuarioRepository.existsByRut(rut)){
            throw new RuntimeException("USUARIO NO ENCONTRADO POR RUT");
        }else {
            UsuarioEntity usuario = usuarioRepository.findByRut(rut).get(0); // COMO LOS RUTS NO RE PEPITEN, OBTENEMOS AL PRIMERO
            return usuario.getCreditos();
        }
    }
    public CreditoEntity addCreditoToUsuario(String usariorut, CreditoEntity credito) {
        if(usariorut == null){
            throw new RuntimeException("RUT NO PUEDE SER NULO");
        } else if (!usuarioRepository.existsByRut(usariorut)){
            throw new RuntimeException("USUARIO NO ENCONTRADO POR RUT");
        }
        UsuarioEntity usuario = usuarioRepository.findByRut(usariorut).get(0); // COMO LOS RUTS NO RE PEPITEN, OBTENEMOS AL PRIMERO
        credito.setUsuario(usuario);
        return creditoRepository.save(credito);
    }
     */
}
