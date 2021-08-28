package com.informatorio.Proyecto.Final.repository;

import com.informatorio.Proyecto.Final.domain.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long> {
}

