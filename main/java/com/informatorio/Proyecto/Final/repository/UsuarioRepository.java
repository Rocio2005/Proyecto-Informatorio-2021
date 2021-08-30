package com.informatorio.Proyecto.Final.repository;

import com.informatorio.Proyecto.Final.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    //List<Usuario> findByCiudadContaining(String ciudad);

    Usuario getByFechaDeCreacion(LocalDateTime fecha);

}

