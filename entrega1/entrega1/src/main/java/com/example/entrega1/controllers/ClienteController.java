package com.example.entrega1.controllers;
//---------------------------------[IMPORTS DE SERVICO]----------------------------------------//
import com.example.entrega1.entities.ClienteEntity;
import com.example.entrega1.repositories.ClienteRepository;
import com.example.entrega1.services.ClienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//---------------------------------[IMPORTS DE SERVICO]----------------------------------------//

import java.util.List;


@RestController
@RequestMapping("/api/cliente")
@CrossOrigin(origins = "*")
//---------------------------------[IMPORTS DE SERVICO]----------------------------------------//
public class ClienteController {
    @Autowired
    ClienteService clienteService;
    @Autowired
    ClienteRepository clienteRepository;
    //-----------------------------------------------------------------------------------------//
    //----------------------------------[GETERS]----------------------------------------------//
    // * OBTENER TODOS LOS CLIENTES
    @GetMapping("/all") // -> VER SI NECESITA CAMBIO, ->  "/"
    /*
    public ResponseEntity<List<ClienteEntity>> listAllCliente() {
        return ResponseEntity.ok(clienteService.getCliente());
    }
    */
    public ResponseEntity<List<ClienteEntity>> listAllCliente() {
        List<ClienteEntity> clientes = clienteService.getCliente();
        return ResponseEntity.ok(clientes);
    }
    //-----------------------------------------------------------------------------------------//
    // * OBTENER CLIENTE POR ID
    @GetMapping("/find/{id}")
    public ResponseEntity<ClienteEntity> findClienteById(@PathVariable Long id) {
        return ResponseEntity.ok(clienteRepository.findById(id).get());
    }
    //-----------------------------------------------------------------------------------------//
    // * OBTENER CLIENTE POR RUT
    @GetMapping("/find/rut/{rut}")
    public ResponseEntity<List<ClienteEntity>> findClienteByRut(@PathVariable String rut) {
        return ResponseEntity.ok(clienteRepository.findByRut(rut));
    }
    //-----------------------------------------------------------------------------------------//
    // * OBTENER CLIENTE POR DOCUMENTO
    @GetMapping("/find/document/{document}")
    public ResponseEntity<List<ClienteEntity>> findClienteByDocument(@PathVariable String document) {
        return ResponseEntity.ok(clienteRepository.findByDocument(document));
    }
    //-----------------------------------------------------------------------------------------//
    // * OBTENER CLIENTE POR NÚMERO DE CASAS
    @GetMapping("/find/houses/{houses}")
    public ResponseEntity<List<ClienteEntity>> findClienteByHouses(@PathVariable int houses) {
        return ResponseEntity.ok(clienteRepository.findByHouses(houses));
    }
    //-----------------------------------------------------------------------------------------//
    //------------------------------------[POST]-----------------------------------------------//
    // * GUARDADO DE CLIENTES
    /*@PostMapping("/save")
    public ResponseEntity<ClienteEntity> saveCliente(@RequestBody ClienteEntity cliente) {
       return ResponseEntity.ok(clienteService.save(cliente));
    }*/
    // LIMITACIÓN DE PUBLICACIÓN; SOLO SE ACEPTAN MAYOR O IGUAL A 0
    @PostMapping("/save")
    public ResponseEntity<?> saveCliente(@RequestBody ClienteEntity cliente) {
        if (cliente.getHouses() < 0) {
            return ResponseEntity.badRequest().body("EL VALOR DE CANTIDAD DE CASAS TIENE QUE SER MAYOR O IGUAL QUE: 0");
        }
        if (cliente.getIngresos() < 0) {
            return ResponseEntity.badRequest().body("EL VALOR DE INGRESOS TIENE QUE SER MAYOR O IGUAL QUE: 0");
        }
        return ResponseEntity.ok(clienteService.save(cliente));
    }
    //-------------------------------------------------------------------------------------------//
    //------------------------------------[PUTS]-----------------------------------------------//
    // * ACTUALIZAR CLIENTES
    @PutMapping("/update")
    public ResponseEntity<ClienteEntity> updateCliente(@RequestBody ClienteEntity cliente) {
        return ResponseEntity.ok(clienteService.update(cliente));
    }
    //-------------------------------------------------------------------------------------------//
    // * ACTUALIZAR CLIENTE POR ID //                               |||||||||VER SI SE PUEDE HACER POR RUT|||||||||
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCliente(@PathVariable Long id, @RequestBody ClienteEntity updatedCliente) {
        ClienteEntity existingCliente = clienteService.findById(id);
        if (existingCliente == null) {
            return ResponseEntity.notFound().build();
        }
        if (updatedCliente.getHouses() < 0) {
            return ResponseEntity.badRequest().body("EL VALOR DE CANTIDAD DE CASAS TIENE QUE SER MAYOR O IGUAL QUE: 0");
        }
        if (updatedCliente.getIngresos() < 0) {
            return ResponseEntity.badRequest().body("EL VALOR DE INGRESOS TIENE QUE SER MAYOR O IGUAL QUE: 0");
        }
        existingCliente.setHouses(updatedCliente.getHouses());
        existingCliente.setIngresos(updatedCliente.getIngresos());
        return ResponseEntity.ok(clienteService.update(existingCliente));
    }
    //-------------------------------------------------------------------------------------------//
    //------------------------------------[DELETERS]------------------------------------------//
    // * ELIMINAR CLIENTE POR ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCliente(@PathVariable Long id) {
        try {
            clienteService.deleteCliente(id);
            return ResponseEntity.ok("CLIENTE ELIMINADO CON id: " + id);
        }
        catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }
    //-----------------------------------------------------------------------------------------//
}
