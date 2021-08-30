package com.informatorio.Proyecto.Final.controller;

import com.informatorio.Proyecto.Final.domain.Usuario;
import com.informatorio.Proyecto.Final.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository){
        this.usuarioRepository=usuarioRepository;
    }


    @PostMapping/*ALTA USUARIO*/
    public ResponseEntity<?> createUsuario(@Valid @RequestBody Usuario usuario){
        return new ResponseEntity<>(usuarioRepository.save(usuario), HttpStatus.CREATED);/*codigo 201*/
    }

    /*@RequestMapping(value = "/usuario/{id}", method = RequestMethod.DELETE)
    public void borrarPorId(@PathVariable("id") Long id) {
        usuarioRepository.deleteById(id);
    }*/

    @DeleteMapping(value = "/usuario/{id}")/*BAJA USUARIO*/
    public ResponseEntity<?> borrarUsuario(@PathVariable Long id){
        usuarioRepository.deleteById(id);
        return new ResponseEntity<>(id,HttpStatus.OK);
    }

    @PutMapping("/{id}")/*MODIFICAR USUARIO*/
    public ResponseEntity<?>  modificarUsuario(@PathVariable Long id,@RequestBody Usuario usuario){
        Usuario usuarioAmodificar=usuarioRepository.findById(id).get();

        usuarioAmodificar.setNombre(usuario.getNombre());
        usuarioAmodificar.setApellido(usuario.getApellido());
        usuarioAmodificar.setEmail(usuario.getEmail());
        usuarioAmodificar.setCiudad(usuario.getCiudad());
        usuarioAmodificar.setProvincia(usuario.getProvincia());
        usuarioAmodificar.setPais(usuario.getPais());

        usuarioRepository.save(usuarioAmodificar);

        return new ResponseEntity<>("usuario modificado",HttpStatus.OK);

    }

    /*@RequestMapping(value = "/usuario", method = RequestMethod.GET)
    public ResponseEntity<?> getAll() {
        return new ResponseEntity(usuarioRepository.findAll(), HttpStatus.OK);
    }*/

    @GetMapping/*TRAER TODOS LOS USUARIOS*/
    public ResponseEntity<?> traerTodos(){

        return new ResponseEntity<>(usuarioRepository.findAll(), HttpStatus.OK);
    }

    //@GetMapping("/{ciudad}")/*CONSULTAR  USUARIOS POR CIUDAD*/
    /*public  ResponseEntity<?> traerPorCiudad(@PathVariable String ciudad){

        return new ResponseEntity<>(usuarioRepository.findByCiudadContaining(ciudad),HttpStatus.OK);
    }*/

    @GetMapping("/{fechaDeCreacion}")
    public ResponseEntity<?> afterFechaDeCreacion(@PathVariable CharSequence fechaDeCreacion){

        LocalDateTime fechaIngresada=LocalDateTime.parse(fechaDeCreacion);

        List<Usuario> usuarios=usuarioRepository.findAll();
        List<Usuario> usuarioAfter=new ArrayList<>();
        for (Usuario usuario:usuarios){

            LocalDateTime fecha=usuario.getFechaDeCreacion();
            if (fecha.isAfter(fechaIngresada)){
                usuarioAfter.add(usuario);
            }
        }

        return new ResponseEntity<>(usuarioAfter,HttpStatus.OK);


    }



}

/*LocalDate fech=LocalDate.of(fecha.getYear(),fecha.getMonth(),fecha.getDayOfMonth());*/
/*LocalDate fechaIn=LocalDate.of(fechaIngresada.getYear(),fechaIngresada.getMonth(),fechaIngresada.getDayOfMonth());*/