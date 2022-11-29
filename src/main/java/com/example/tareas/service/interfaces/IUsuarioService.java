package com.example.tareas.service.interfaces;

import com.example.tareas.model.Usuario;
import com.example.tareas.service.impl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IUsuarioService {

    public List<Usuario> mostrarUsuario();
    
    public Usuario agregarUsuario(Usuario usuario);
    
    public Usuario buscarUsuario(Long id);

    public Usuario buscarNombreUsuario(String user);

    public void borrarUsuario(Long id);
}
