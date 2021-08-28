package com.informatorio.Proyecto.Final.repository;

import com.informatorio.Proyecto.Final.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}

