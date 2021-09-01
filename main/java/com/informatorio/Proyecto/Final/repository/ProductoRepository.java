package com.informatorio.Proyecto.Final.repository;

import com.informatorio.Proyecto.Final.domain.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByNombreContains(String palabra);
    List<Producto> findByPublicadoTrue();
}

