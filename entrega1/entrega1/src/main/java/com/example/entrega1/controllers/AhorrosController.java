package com.example.entrega1.controllers;
//------------------------------[IMPORTS DE SERVICO]-------------------------------------//

import com.example.entrega1.entities.AhorrosEntity;
import com.example.entrega1.repositories.AhorrosRepository;
import com.example.entrega1.services.AhorrosService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//---------------------------------[IMPORTS DE SERVICO]----------------------------------------//
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ahorros")
@CrossOrigin("*")
//------------------------------[IMPORTS DE SERVICO]-------------------------------------//
public class AhorrosController {
    @Autowired
    AhorrosRepository ahorrosRepository;
    @Autowired
    AhorrosService ahorrosService;
    //-------------------------------------------------------------------------------------------//
    //-----------------------------------[GETERS]----------------------------------------------//
    // * OBTENER TODOS LOS AHORROS
    @GetMapping("/all")
    public ResponseEntity<List<AhorrosEntity>> listAllAhorros() {
        List<AhorrosEntity> ahorros = ahorrosService.getAhorros();
        return ResponseEntity.ok(ahorros);
    }
    //-------------------------------------------------------------------------------------------//
    // * OBTENER AHORRO POR ID
    @GetMapping("/find/{id}")
    public ResponseEntity<AhorrosEntity> getAhorrosById(@PathVariable Long id) {
        AhorrosEntity valorid = ahorrosService.getAhorrosById(id);
        return ResponseEntity.ok(valorid);
    }
    //-------------------------------------------------------------------------------------------//
    //------------------------------------[POST]-----------------------------------------------//
    // * GUARDADO DE AHORROS
    @PostMapping("/save")
    public ResponseEntity<AhorrosEntity> saveAhorros(@RequestBody AhorrosEntity ahorros) {
        try {
            return ResponseEntity.ok(ahorrosService.saveAhorros(ahorros));
        }
        catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    //-------------------------------------------------------------------------------------------//
    //------------------------------------[PUT]-----------------------------------------------//
    // * ACTUALIZAR AHORRO
    @PutMapping("/update")
    public ResponseEntity<AhorrosEntity> updateAhorros(@RequestBody AhorrosEntity ahorros) {
        try {
            return ResponseEntity.ok(ahorrosService.updateAhorros(ahorros));
        }
        catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    //-------------------------------------------------------------------------------------------//
    //-----------------------------------[DELETE]----------------------------------------------//
    // * ELIMINAR AHORRO
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteAhorros(@PathVariable Long id) {
        return ResponseEntity.ok(ahorrosService.deleteAhorros(id));
    }
    //-------------------------------------------------------------------------------------------//
}
