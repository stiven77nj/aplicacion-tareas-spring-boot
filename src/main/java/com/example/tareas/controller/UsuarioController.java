package com.example.tareas.controller;

import com.example.tareas.model.Usuario;
import com.example.tareas.service.interfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("/saludo")
    public ResponseEntity<String> mensaje(){
        return new ResponseEntity<>("Wenas tardes", HttpStatus.OK);
    }

    @GetMapping()
    public List<Usuario> mostrar(){
        return usuarioService.mostrarUsuario();
    }

    @PostMapping()
    public Usuario agregarUsuario(@RequestBody Usuario usuario){
        return usuarioService.agregarUsuario(usuario);
    }

    @GetMapping("/{id}")
    public Usuario buscarUsuario(@PathVariable Long id){
        return usuarioService.buscarUsuario(id);
    }

    @GetMapping("/{user}")
    public Usuario buscarUsuarioUser(@PathVariable String user){
        return usuarioService.buscarUsuarioUser(user);
    }

    @DeleteMapping("/{id}")
    public void borrarUsuario(@PathVariable Long id){
        usuarioService.borrarUsuario(id);
    }

    @PutMapping("/{id}")
    public Usuario editarUsuario(@PathVariable Long id, @RequestBody Usuario usuario){
        Usuario usuarioNuevo = usuarioService.buscarUsuario(id);
        usuarioNuevo.setNombre(usuario.getNombre());
        usuarioNuevo.setApellido(usuario.getApellido());
        usuarioNuevo.setCedula(usuario.getCedula());
        usuarioNuevo.setNombreUsuario(usuario.getNombreUsuario());
        return usuarioService.agregarUsuario(usuarioNuevo);
    }
}
