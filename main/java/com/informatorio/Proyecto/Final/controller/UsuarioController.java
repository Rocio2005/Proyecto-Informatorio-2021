package com.informatorio.Proyecto.Final.controller;

import com.informatorio.Proyecto.Final.domain.Usuario;
import com.informatorio.Proyecto.Final.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @GetMapping/*Traer Todos*/
    public ResponseEntity<?> traerTodos(){

        return new ResponseEntity<>(usuarioRepository.findAll(), HttpStatus.OK);
    }
}

