package com.example.entrega1.controllers;
//------------------------------[IMPORTS DE SERVICO]-------------------------------------//
import com.example.entrega1.entities.UsuarioEntity;
import com.example.entrega1.repositories.UsuarioRepository;
import com.example.entrega1.services.UsuarioService;

import com.example.entrega1.entities.CreditoEntity;
import com.example.entrega1.repositories.CreditoRepository;
import com.example.entrega1.services.CreditoService;
import com.example.entrega1.services.SistemaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//---------------------------------[IMPORTS DE SERVICO]----------------------------------------//
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/credito")
@CrossOrigin(origins = "*")
//------------------------------[IMPORTS DE SERVICO]-------------------------------------//
public class CreditoController {
    @Autowired
    CreditoRepository creditoRepository;
    @Autowired
    CreditoService creditoService;
    //-------------------------------------------------------------------------------------------//
    //-----------------------------------[GETERS]----------------------------------------------//
    // * OBTENER TODOS LOS CRÉDITOS
    @GetMapping("/all") // -> VER SI NECESITA CAMBIO, ->  "/"
    public ResponseEntity<List<CreditoEntity>> listAllCredito() {
        List<CreditoEntity> creditos = creditoService.getCreditos();
        return ResponseEntity.ok(creditos);
    }
    //-------------------------------------------------------------------------------------------//
    // * OBTENER CRÉDITO POR ID
    @GetMapping("/find/{id}")
    public ResponseEntity<CreditoEntity> getCreditoById(@PathVariable Long id) {
        CreditoEntity valorid = creditoService.getCreditoById(id);
        return ResponseEntity.ok(valorid);
    }
    //-------------------------------------------------------------------------------------------//
    //------------------------------------[POST]-----------------------------------------------//
    // * GUARDADO DE CRÉDITOS
    @PostMapping("/save")
    public ResponseEntity<CreditoEntity> saveCredito(@RequestBody CreditoEntity credito) {
        try {
            return ResponseEntity.ok(creditoService.saveCredito(credito));
        }
        catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    //-------------------------------------------------------------------------------------------//
    //------------------------------------[PUT]-----------------------------------------------//
    // * ACTUALIZAR CRÉDITO
    @PutMapping("/update")
    public ResponseEntity<CreditoEntity> updateCredito(@RequestBody CreditoEntity credito) {
        return ResponseEntity.ok(creditoService.updateCredito(credito));
    }
    //-------------------------------------------------------------------------------------------//
    //------------------------------------[DELETE]-----------------------------------------------//
    // * ELIMINAR CRÉDITO POR ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCredito(@PathVariable Long id) {
        try {
            creditoService.deleteCredito(id);
            return ResponseEntity.ok(null);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    //-------------------------------------------------------------------------------------------//
    // * ELIMINAR TODOS LOS CRÉDITOS
    @DeleteMapping("/delete/all")
    public ResponseEntity<?> deleteAllCreditos() {
        creditoService.deleteAllCreditos();
        return ResponseEntity.ok(null);
    }
    //-------------------------------------------------------------------------------------------//
    // AGREGAR CRÉDITO POR RUT
    @PostMapping("/add/{rut}")
    public ResponseEntity<?> addCreditoToUsuario(@RequestBody CreditoEntity credito, @PathVariable String rut) {
        try {
            return ResponseEntity.ok(creditoService.addCreditoToUsuario(credito, rut));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
