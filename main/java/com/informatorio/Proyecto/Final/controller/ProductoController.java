package com.informatorio.Proyecto.Final.controller;

import com.informatorio.Proyecto.Final.domain.Producto;
import com.informatorio.Proyecto.Final.repository.ProductoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/producto")
public class ProductoController {

    private ProductoRepository productoRepository;

    public ProductoController(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @PostMapping
    public ResponseEntity<?> createProducto(@Valid @RequestBody Producto producto) {
        return new ResponseEntity<>(productoRepository.save(producto), HttpStatus.CREATED);
    }
}

