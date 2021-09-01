package com.informatorio.Proyecto.Final.controller;

import com.informatorio.Proyecto.Final.domain.Producto;
import com.informatorio.Proyecto.Final.domain.Usuario;
import com.informatorio.Proyecto.Final.repository.ProductoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/producto")
public class ProductoController {

    private ProductoRepository productoRepository;

    public ProductoController(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @PostMapping/*ALTA PRODUCTO*/
    public ResponseEntity<?> createProducto(@Valid @RequestBody Producto producto) {
        return new ResponseEntity<>(productoRepository.save(producto), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/producto/{id}")/*BAJA PRODUCTO*/
    public ResponseEntity<?> borrarUsuario(@PathVariable Long id){
        productoRepository.deleteById(id);
        return new ResponseEntity<>("Producto "+id+ " eliminado",HttpStatus.OK);
    }

    @PutMapping("/{id}")/*MODIFICAR PRODUCTO*/
    public ResponseEntity<?>  modificarProducto(@PathVariable Long id,@RequestBody Producto producto){
        Producto prodAmodificar=productoRepository.findById(id).get();

        prodAmodificar.setNombre(producto.getNombre());
        prodAmodificar.setDescripcion(producto.getDescripcion());
        prodAmodificar.setPrecioUnitario(producto.getPrecioUnitario());
        prodAmodificar.setContenido(producto.getContenido());
        prodAmodificar.setPublicado(producto.getPublicado());

        productoRepository.save(prodAmodificar);

        return new ResponseEntity<>("Producto " +id+" Modificado",HttpStatus.OK);

    }

    @GetMapping/*TRAER TODOS LOS PRODUCTOS*/
    public ResponseEntity<?> traerTodos(){

        return new ResponseEntity<>(productoRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/palabra")/*TRAER PRODUCTOS CON NOMBRES QUE INCLUYE LA PALABRA INGRESADA*/
    public ResponseEntity<?> nameIgualA(@RequestParam("word") String word){
        return new ResponseEntity<>(productoRepository.findByNombreContains(word),HttpStatus.OK);

    }

    @GetMapping("/publicados")
    public ResponseEntity<?> traerPublicados(){
        return new ResponseEntity<>(productoRepository.findByPublicadoTrue(),HttpStatus.OK);
    }
}

