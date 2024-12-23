package com.example.entrega1.services;
//---------------------------------[IMPORTS DE SERVICO]----------------------------------------//
import com.example.entrega1.entities.CreditoEntity;
import com.example.entrega1.entities.UsuarioEntity;
import com.example.entrega1.entities.AhorrosEntity;
import com.example.entrega1.repositories.CreditoRepository;
import com.example.entrega1.repositories.UsuarioRepository;
import com.example.entrega1.repositories.AhorrosRepository;

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
    @Autowired
    AhorrosRepository ahorrosRepository;
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
    // * ACTUALIZAR CLIENTE -> RECIVE UN CLIENTE Y LO ACTUALIZA
    public UsuarioEntity updateUsuario(Long id, int valorpropiedad, int ingresos, int sumadeuda, String objective) {
        UsuarioEntity usuario = usuarioRepository.findById(id).orElse(null);
        if(usuario == null) {
            return null;
        }
        // SETEO DE VALORES INGRESADOS:
        usuario.setValorpropiedad(valorpropiedad);
        usuario.setIngresos(ingresos);
        usuario.setSumadeuda(sumadeuda);
        usuario.setObjective(objective.toUpperCase());

        return usuarioRepository.save(usuario);
    }
    //------------------------------------------------------------------//
    // * ELIMINAR CLIENTE POR ID -> SI HAY ALGÚN FALLO, ENTREGA EXCEPCIÓN
    public void deleteUsuario(Long id) throws Exception {
        try {
            usuarioRepository.deleteById(id);
        }
        catch (Exception e) {
            throw new Exception("NO SE PUDO ELIMINAR EL CLIENTE CON id: " + id);
        }
    }
    //------------------------------------------------------------------//
    // * ELIMINAR TODOS LOS CLIENTES -> ELIMINA TODOS LOS CLIENTES
    public void deleteAllUsuarios() {
        usuarioRepository.deleteAll();
    }
    //------------------------------[OPERACIONES CLIENTE]------------------------------------//
    //--------------------------------------[GETTERS]--------------------------------------------//
    // + OBTENER TODOS LOS CLIENTES -> ENTREGA UNA LISTA DE CLIENTES
    public Object getAllUsuarios() {
        return null;
    }
    //------------------------------------------------------------------//
    // + OBTENER CLIENTE POR ID -> REGRESA EXCEPCIÓN SI NO EXISTE
    public UsuarioEntity getUsuarioById(Long id) {
        return usuarioRepository.findById(id).get();
    }
    //------------------------------------------------------------------//
    // + OBTENER CLIENTE POR RUT -> REGRESA UNA LISTA DE CLIENTES -> [BUSCO EL VALOR 0 DE LA LISTA]
    public List<UsuarioEntity> getUsuarioByRut(String rut) {
        return usuarioRepository.findByRut(rut);
    }
    //------------------------------------------------------------------//
    // + OBTENER CLIENTE POR OBJETIVO -> REGRESA UNA LISTA DE CLIENTES -> [BUSCO EL VALOR 0 DE LA LISTA]
    public List<UsuarioEntity>  getUsuarioByObjective(String objective) {
        return usuarioRepository.findByObjective(objective);
    }
    //------------------------------[OPERACIONES AHORRO]------------------------------------//
    // + OBTENER EL VALOR POSITIVO MAS CHICO DENTRO DE LA LISTA DE AHORROS DEL USUARIO
    public int obtenerValorPositivoMasPequeno(List<AhorrosEntity> ahorros) {
        int valorPositivoMasPequeno = Integer.MAX_VALUE;
        boolean foundPositive = false;

        for (AhorrosEntity ahorro : ahorros) {
            int transaccion = ahorro.getTransaccion();
            if (transaccion > 0 && transaccion < valorPositivoMasPequeno) {
                valorPositivoMasPequeno = transaccion;
                foundPositive = true;
            }
        }
        return foundPositive ? valorPositivoMasPequeno : -1; // Devuelve -1 si no se encuentra ningún valor positivo
    }
}
