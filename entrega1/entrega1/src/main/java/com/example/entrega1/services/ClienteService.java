package com.example.entrega1.services;
//---------------------------------[IMPORTS DE SERVICO]----------------------------------------//
import com.example.entrega1.entities.ClienteEntity;
import com.example.entrega1.repositories.ClienteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
@Service
//--------------------------------[FUNCIONES DE SERVICO]---------------------------------------//
public class ClienteService {
    @Autowired
    ClienteRepository clienteRepository;
    //-------------------------------------[CRUD]----------------------------------------------//
    // * OBTENER TODOS LOS CLIENTE -> ENTREGA UNA LISTA DE CLIENTES
    public ArrayList<ClienteEntity> getCliente() {
        return (ArrayList<ClienteEntity>) clienteRepository.findAll();
    }
    //------------------------------------------------------------------//
    // * GUARDAR CLIENTE -> RECIVE UN CLIENTE Y LO GUARDA
    public ClienteEntity save(ClienteEntity cliente) {
        return clienteRepository.save(cliente);
    }
    //------------------------------------------------------------------//
    // * ACTUALIZAR CLIENTE
    public ClienteEntity update(ClienteEntity cliente) {
        return clienteRepository.save(cliente);
    }
    //------------------------------------------------------------------//
    // * ELIMINAR CLIENTE -> SI HAY ALGÚN FALLO, ENTREGA EXCEPCIÓN
    public void deleteCliente(Long id) throws Exception {
        try {
            clienteRepository.deleteById(id);
        }
        catch (Exception e) {
            throw new Exception("NO SE PUDO ELIMINAR EL CLIENTE CON id: " + id);
        }
    }
    //------------------------------[OPERACIONES CLIENTE]-------------------------------------//
    //--------------------------------------[FIND-BY]--------------------------------------------//
    public ClienteEntity findById(Long id) {
        return clienteRepository.findById(id).orElse(null);
    }
    //------------------------------------------------------------------//
    public List<ClienteEntity> findByRut(String rut) {
        return clienteRepository.findByRut(rut);
    }
    //-----------------------------------[ALL-FINDERS]-----------------------------------------//
    // + OBTENER CLIENTE POR ID -> REGRESA NULL SI NO EXISTE
    /*
     public ClienteEntity findClienteById(Long id) {
        return clienteRepository.findById(id).orElse(null);
    }
     */
    // + OBTENER CLIENTE POR ID -> REGRESA EXCEPCIÓN SI NO EXISTE
    public ClienteEntity findClienteById(Long id) {
        return clienteRepository.findById(id).get();
    }
    //------------------------------------------------------------------//
    // + OBTENER TODOS LOS RUT´S
    public List<String> findAllRuts() {
        return clienteRepository.findAllRuts();
    }
    //------------------------------------------------------------------//
    // + OBTENER TODOS LOS NOMBRES -> |||||||| ESTE PUEDE TENER PROBLEMAS ||||||||
    public List<String> findAllNames() {
        return clienteRepository.findAllNames();
    }
    //------------------------------------------------------------------//
    // + OBTENER TODOS LOS EMAILS -> |||||||| ESTE PUEDE TENER PROBLEMAS ||||||||
    public List<String> findAllEmails() {
        return clienteRepository.findAllEmails();
    }
    //------------------------------------------------------------------//
    // + OBTENER TODOS LOS DOCUMENTOS -> |||||||| ESTE PUEDE TENER PROBLEMAS ||||||||
    public List<String> findAllDocuments() {
        return clienteRepository.findAllDocuments();
    }
    //------------------------------------------------------------------//
    // + OBTENER TODOS LOS INGRESOS
    public List<Integer> findAllIngresos() {
        return clienteRepository.findAllIngresos();
    }
    //------------------------------------------------------------------//
}
