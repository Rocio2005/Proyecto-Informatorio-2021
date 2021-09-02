package com.informatorio.Proyecto.Final.controller;

import com.informatorio.Proyecto.Final.domain.Carrito;
import com.informatorio.Proyecto.Final.domain.Usuario;
import com.informatorio.Proyecto.Final.repository.CarritoRepository;
import com.informatorio.Proyecto.Final.repository.ProductoRepository;
import com.informatorio.Proyecto.Final.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class CarritoController {

    private final UsuarioRepository usuarioRepository;
    private final CarritoRepository carritoRepository;
    private final ProductoRepository productoRepository;

    public CarritoController(UsuarioRepository usuarioRepository, CarritoRepository carritoRepository, ProductoRepository
            productoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.carritoRepository = carritoRepository;
        this.productoRepository = productoRepository;

    }

    @PostMapping("/usuario/{id}/carrito")/*CREAR CARRITO*/ //le creo un carrito al usuario que le paso en la url
    public ResponseEntity<?> crearCarrito(@PathVariable("id") Long userId, @Valid @RequestBody Carrito carrito) {

        Usuario usuario = usuarioRepository.getById(userId);
        carrito.setUsuario(usuario);
        return new ResponseEntity<>(carritoRepository.save(carrito), HttpStatus.CREATED);

    }

    @DeleteMapping("/usuario/{id}/carrito/{idCarrito}")/*BORRAR CARRITO DE UN USUARIO POR SU ID*/
    public ResponseEntity<?> borrarCarrito(@PathVariable("id") Long userId,
                                           @PathVariable("idCarrito") Long carritoId) {

        Usuario usuario = usuarioRepository.getById(userId);
        Carrito carrito = carritoRepository.getById(carritoId);
        if (usuario == carrito.getUsuario()) {
            carritoRepository.delete(carrito);
            return new ResponseEntity<>("CARRITO " + carritoId + " DEL " + " USUARIO " + userId + " BORRADO", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("EL CARRITO " + carritoId + " NO LE PERTENECE AL USUARIO " + userId, HttpStatus.OK);
        }
    }

}


    /*@PutMapping("/usuario/{id}/carrito/{idCarrito}/=?")//agregar indumentaria al carrito del usuario tal a su carrito tanto
    public ResponseEntity<?> agregarIndumentaria(@PathVariable("id") Long userId,@PathVariable("idCarrito") Long idCarrito,
                                                 @Valid @RequestBody OperacionCarrito operacionCarrito) {
        //la operacion carrito me envia un producto
        Carrito carrito=carritoRepository.getById(idCarrito);

        /*for (LineaDeCarrito lineaDeCarrito:carrito.getLineasDeCarrito()){
            boolean exist=operacionCarrito.getIndumentariaId().equals(lineaDeCarrito.getIndumentaria().getId());
            //si existe no hago nada,sobrescribo el valor de la cantidad o lo piso¿?
            lineaDeCarrito.setCantidad(operacionCarrito.getCantidad());

            // o sino acumulo
            lineaDeCarrito.setCantidad(lineaDeCarrito.getCantidad()+ operacionCarrito.getCantidad());
        }*///no se porque se repite en POSTMAN

       /* Indumentaria indumentaria= indumentariaRepository.getById(operacionCarrito.getIndumentariaId());//busco el producto que pase en la operación carrito
        LineaDeCarrito lineaDeCarrito=new LineaDeCarrito();//creo el detalle del producto
        lineaDeCarrito.setCarrito(carrito);
        lineaDeCarrito.setIndumentaria(indumentaria);
        lineaDeCarrito.setCantidad(operacionCarrito.getCantidad());
        carrito.agregarLineaDeCarrito(lineaDeCarrito);
        return new ResponseEntity<>(carritoRepository.save(carrito), HttpStatus.CREATED);
    }

    @DeleteMapping("/usuario/{id}/carrito/{idCarrito}/linea/{idLinea}")//linea o descripcion o detalle o producto
    public ResponseEntity<?> borrarLinea(@PathVariable("id") Long userId,@PathVariable("idCarrito") Long idCarrito,
                                         @PathVariable("idLinea") Long id,
                                         @PathVariable("idIndumentaria")
                                         @Valid @RequestBody OperacionCarrito operacionCarrito){
        Carrito carrito=carritoRepository.getById(idCarrito);//busca mi carrito(el que le pedi en la request) en la base de datos
        Indumentaria indumentaria=indumentariaRepository.getById(operacionCarrito.getIndumentariaId());//obtengo mi producto por la id que le pase
        LineaDeCarrito lineaDeCarrito=new LineaDeCarrito();//creo una linea para llamar al detalle del producto anterior
        //le paso a linea de carrito los datos necesarios para crear el detalle de esa indumentaria de mi carrito
        lineaDeCarrito.setCarrito(carrito);
        lineaDeCarrito.setIndumentaria(indumentaria);
        lineaDeCarrito.setCantidad(operacionCarrito.getCantidad());
        carrito.removerLineaDeCarrito(lineaDeCarrito);
        return new ResponseEntity<>(carritoRepository.save(carrito),HttpStatus.ACCEPTED);

    }

    @PutMapping("/{id}")/*MODIFICAR USUARIO*/
   /* public ResponseEntity<?>  modificarUsuario(@PathVariable Long id,@RequestBody Usuario usuario){
        Usuario usuarioAmodificar=usuarioRepository.findById(id).get();

        usuarioAmodificar.setNombre(usuario.getNombre());
        usuarioAmodificar.setApellido(usuario.getApellido());


        usuarioRepository.save(usuarioAmodificar);

        return new ResponseEntity<>("usuario modificado",HttpStatus.OK);

    }*/
