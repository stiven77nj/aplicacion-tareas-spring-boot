package com.example.tareas.controller;

import com.example.tareas.model.Usuario;
import com.example.tareas.service.interfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("/saludo")
    public ResponseEntity<String> mensaje(){
        return new ResponseEntity<>("Wenas tardes", HttpStatus.OK);
    }


    // Obtener listado de clientes
    @GetMapping()
    public ResponseEntity<?> mostrar(){

        Map<String, Object> response = new HashMap<>();

        try {

            List<Usuario> usuarios = usuarioService.mostrarUsuario();

            if ( usuarios.isEmpty() ) {
                response.put("Mensaje", "No hay usuarios registrados en el sistema");
                response.put("Data", usuarios);
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            } else {
                response.put("Mensaje", "Usuarios listados de manera de exitosa");
                response.put("Data", usuarios);
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
            }
            
        } catch (DataAccessException e) {
            
            response.put("Mensaje", "Ocurrio un error al listar todos los empleados");
            response.put("Error", e.getMostSpecificCause().getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }


    @PostMapping()
    public Usuario agregarUsuario(@RequestBody Usuario usuario){
        return usuarioService.agregarUsuario(usuario);
    }


    @GetMapping("/id/{id}")
    public Usuario buscarUsuario(@PathVariable Long id){
        return usuarioService.buscarUsuario(id);
    }


    @GetMapping("/{user}")
    public Usuario buscarNombreUsuario(@PathVariable String user){
        return usuarioService.buscarNombreUsuario(user);
    }


    // Eliminar usuarios
    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrarUsuario(@PathVariable Long id){

        Map<String, Object> response = new HashMap<>();

        try {

            Usuario usuario = usuarioService.buscarUsuario(id);

            if ( usuario == null ) {
                response.put("Mensaje", "No se encuentra registrado un cliente con el id ".concat(id.toString()));
                response.put("Data", usuario);
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            } else {
                
                usuarioService.borrarUsuario(id); // Se elimina el usuario
    
                // Se busca el usuario recien eliminado para saber si se elimino de manera exitosa
                Usuario usuarioEliminado = usuarioService.buscarUsuario(id); 
    
                if ( usuarioEliminado == null ) { // Si el usuario es null, se elimino correctamente
                    response.put("Mensaje", "Usuario eliminado de manera correcta con el id ".concat(id.toString()));
                    response.put("Data", usuario);
                    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
                } else {
                    response.put("Mensaje", "No se pudo eliminar el usuario con el id ".concat(id.toString()));
                    response.put("Data", usuarioEliminado);
                    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
                }

            }
        } catch (DataAccessException e) {

            response.put("Mensaje", "Ocurrio un error al intentar eliminar el usuario");
            response.put("Error", e.getMostSpecificCause().getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
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
