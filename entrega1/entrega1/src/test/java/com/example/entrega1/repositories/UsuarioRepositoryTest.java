package com.example.entrega1.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;

public class UsuarioRepositoryTest {
    @Autowired
    private UsuarioRepository usuarioRepository;
    private TestEntityManager entityManager;

    @Test
    public void whenFindById_thenReturnUsuario() {
        // Given
        UsuarioRepository usuario = new UsuarioRepository(
                null,
                "21063959-4",
                "Benjamin Valenzuela",
                20,
                "ALGO",
                1,
                1000000
        );
        entityManager.persistAndFlush(usuario);
        // When
        UsuarioRepository found = usuarioRepository.findById(usuario.getId()).get();
        // Then
        assertThat(found.getId()).isEqualTo(usuario.getId());
    }
}
