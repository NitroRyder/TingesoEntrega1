package com.example.entrega1.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;

public class UsuarioRepositoryTest {

    private UsuarioRepository usuarioRepository;
    private TestEntityManager entityManager;

    @Test
    public void whenFindById_thenReturnUsuario() {

    }
}
