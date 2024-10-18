package com.example.entrega1.controllers;

import com.example.entrega1.entities.UsuarioEntity;
import com.example.entrega1.services.UsuarioService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {
        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private UsuarioService usuarioService;

        @Test
        public void listUsuarios_ShouldReturnUsuarios() throws Exception {
            List<String> documentos1 = new ArrayList<>();
            documentos1.add("dicom.pdf");
            // Arrange
            UsuarioEntity user1 = new UsuarioEntity(1L,
                    "12345678-9",
                    "John Doe",
                    30,
                    3,
                    new ArrayList<>(),
                    2,
                    1000000,
                    500000,
                    240000,
                    "Compra de vivienda",
                    "independiente",
                    new ArrayList<>(),
                    null,
                    new ArrayList<>(),
                    new ArrayList<>()
            );

            UsuarioEntity user2 = new UsuarioEntity(2L,
                    "98765432-1",
                    "Jane Smith",
                    30,
                    3,
                    new ArrayList<>(),
                    2,
                    1000000,
                    500000,
                    240000,
                    "Compra de vivienda",
                    "independiente",
                    new ArrayList<>(),
                    null,
                    new ArrayList<>(),
                    new ArrayList<>()
            );

            List<UsuarioEntity> usuarios = Arrays.asList(user1, user2);

            given(usuarioService.getAllUsuarios()).willReturn(usuarios);

            // Act & Assert
            mockMvc.perform(get("/usuarios")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(2)))
                    .andExpect(jsonPath("$[0].name", is("John Doe")))
                    .andExpect(jsonPath("$[1].name", is("Jane Smith")));
        }
}
