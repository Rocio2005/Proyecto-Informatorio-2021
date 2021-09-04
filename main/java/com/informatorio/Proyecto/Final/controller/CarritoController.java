package com.informatorio.Proyecto.Final.controller;

import com.informatorio.Proyecto.Final.domain.Carrito;
import com.informatorio.Proyecto.Final.domain.LineaDeCarrito;
import com.informatorio.Proyecto.Final.domain.Producto;
import com.informatorio.Proyecto.Final.domain.Usuario;
import com.informatorio.Proyecto.Final.dto.OperacionCarrito;
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
    /*si quiero borrar todos los carritos del usuario necesito traer el metodo getcarritos
     * y recorrer esa lista para eliminar todos */


    @PutMapping("/usuario/{id}/carrito/{idCarrito}/productos")
    //agregar producto al carrito del usuario tal a su carrito tanto
    public ResponseEntity<?> agregarProducto(@PathVariable("id") Long userId, @PathVariable("idCarrito") Long idCarrito,
                                             @Valid @RequestBody OperacionCarrito operacionCarrito) {
        //la operacion carrito me envia el producto que solicito en el body del Postman
        Carrito carrito = carritoRepository.getById(idCarrito);//Traigo el carrito que quiero cargar

        //for (LineaDeCarrito lineaDeCarrito : carrito.getLineasDeCarrito()) {
            //boolean exist = operacionCarrito.getProductoId().equals(lineaDeCarrito.getProducto().getId());
            //si existe no hago nada,sobrescribo el valor de la cantidad o lo piso¿?
            //lineaDeCarrito.setCantidad(operacionCarrito.getCantidad());

            // o sino acumulo
            //lineaDeCarrito.setCantidad(lineaDeCarrito.getCantidad() + operacionCarrito.getCantidad());
        //} //no se porque se repite en POSTMAN

        Producto producto = productoRepository.getById(operacionCarrito.getProductoId());//busco el producto que pase en la operación carrito
        LineaDeCarrito lineaDeCarrito = new LineaDeCarrito();
        //creo el detalle del producto(seteando cantidad,producto y el carrito correspondiente)
        lineaDeCarrito.setCarrito(carrito);//le agrego esta linea al carrito creado antes
        lineaDeCarrito.setProducto(producto);//le agrego el producto que traje del repositorio
        lineaDeCarrito.setCantidad(operacionCarrito.getCantidad());//le agrego la cantidad
        carrito.agregarLineaDeCarrito(lineaDeCarrito);
        return new ResponseEntity<>(carritoRepository.save(carrito), HttpStatus.OK);
    }


    @DeleteMapping("/usuario/{id}/carrito/{idCarrito}/linea/{idLinea}")//Borrar linea o descripcion o detalle o producto a borrar
    public ResponseEntity<?> borrarLinea(@PathVariable("id") Long userId, @PathVariable("idCarrito") Long idCarrito,
                                         @PathVariable("idLinea") Long idLinea) {
        Carrito carrito = carritoRepository.getById(idCarrito);//busca mi carrito(el que le pedi en la request) en la base de datos
        for (LineaDeCarrito linea:carrito.getLineasDeCarrito()){
            if (linea.getId().equals(idLinea)){
                carrito.removerLineaDeCarrito(linea);
            }
        }
        return new ResponseEntity<>("linea "+idLinea+" borrada "+"del carrito "+idCarrito, HttpStatus.OK);

    }

    @GetMapping("/usuario/{id}/carritos")//MOSTRAR CARRITOS DE UN USUARIO
    public ResponseEntity <?> mostrarTodosCarritos(@PathVariable("id") Long userId){
        Usuario usuario=usuarioRepository.getById(userId);
        return new ResponseEntity<>(usuario.getCarritos(),HttpStatus.OK);
    }

    @GetMapping("/usuario/{id}/carritoactual/{idCarrito}")//Mostrar detalle del carrito actual
    public ResponseEntity<?> detalleCarrito(@PathVariable("id") Long userId,@PathVariable("idCarrito") Long idCarrito){
        Usuario usuario=usuarioRepository.getById(userId);
        Carrito carrito=carritoRepository.getById(idCarrito);
        if (carrito.isEstado()){

            return new ResponseEntity<>(carrito.getLineasDeCarrito(),HttpStatus.OK);
        }
        else return new ResponseEntity<>("EL CARRITO NO ES EL ACTUAL",HttpStatus.OK);

    }

}
