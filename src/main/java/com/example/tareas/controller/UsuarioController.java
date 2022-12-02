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
import java.util.TreeMap;

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
                response.put("Valor", usuarios);
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            } else {
                response.put("Mensaje", "Usuarios listados de manera de exitosa");
                response.put("Valor", usuarios);

                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
            }
            
        } catch (DataAccessException e) {
            
            response.put("Mensaje", "Ocurrio un error al listar todos los empleados");
            response.put("Error", e.getMostSpecificCause().getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }


    @PostMapping()
    public ResponseEntity<?> agregarUsuario(@RequestBody Usuario usuario){
        Map<String, Object> response = new HashMap<>();
        Usuario usuarioNuevo = null;
        try{
            usuarioNuevo=usuarioService.agregarUsuario(usuario);

        } catch (DataAccessException e){
            response.put("Mensaje", "Ocurrio un error al ingresar el usuario");
            response.put("Error", e.getMostSpecificCause().getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        response.put("Mensaje: ","Se ingresó el usuario correctamente");
        response.put("Valor: ",usuarioNuevo);
        return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);

    }


    @GetMapping("/id/{id}")

    public ResponseEntity<?> buscarUsuario(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();

        try{
            Usuario usuario = usuarioService.buscarUsuario(id);
            if (usuario == null){
                response.put("Valor: ",usuario);
                response.put("Mensaje: ","No se encontró el usuario con ID "+id);

                return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);

            }else{
                response.put("Mensaje: ","Se encontró el usuario con ID: "+id);
                response.put("Valor: ",usuario);
                return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);

            }
        } catch (DataAccessException e){
            response.put("_Mensaje: ","Ocurrió un error interno del servidor");
            response.put("Error:",e.getMostSpecificCause().getMessage());
            return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }


    @GetMapping("/{user}")
    public ResponseEntity<?> buscarNombreUsuario(@PathVariable String user){
        Map<String, Object> response = new HashMap<>();
        try{
            Usuario usuario=usuarioService.buscarNombreUsuario(user);
            if (usuario == null){
                response.put("Mensaje: ","No se encontró el usuario con Nombre de Usuario: "+user);
                response.put("Valor",usuario);
                return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);

            }else{
                response.put("Mensaje: ","Se encontró el usuario con Nombre de Usuario: "+user);
                response.put("Valor: ",usuario);
                return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
            }

        }catch (DataAccessException e){
            response.put("_Mensaje: ","Ocurrió un error interno en el servidor");
            response.put("Error: ",e.getMostSpecificCause().getMessage());
            return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

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
    public ResponseEntity<?> editarUsuario(@PathVariable Long id, @RequestBody Usuario usuario){
        Map<String, Object> response = new HashMap<>();


        try{
            Usuario usuarioNuevo = usuarioService.buscarUsuario(id);
            if (usuarioNuevo == null){
                response.put("Valor: ",usuarioNuevo);
                response.put("Mensaje: ","No se encontró el usuario con ID "+id);

                return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);

            }else{
                response.put("Mensaje: ","Se encontró el usuario con ID: "+id);
                usuarioNuevo.setNombre(usuario.getNombre());
                usuarioNuevo.setApellido(usuario.getApellido());
                usuarioNuevo.setCedula(usuario.getCedula());
                usuarioNuevo.setNombreUsuario(usuario.getNombreUsuario());
                usuarioService.agregarUsuario(usuarioNuevo);
                response.put("Valor: ",usuarioNuevo);
                return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);

            }
        } catch (DataAccessException e){
            response.put("_Mensaje: ","Ocurrió un error interno del servidor");
            response.put("Error:",e.getMostSpecificCause().getMessage());
            return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);

        }


    }
}
