package com.example.tareas.service.impl;

import com.example.tareas.model.Usuario;
import com.example.tareas.repository.IUsuarioRepository;
import com.example.tareas.service.interfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements IUsuarioService {
    @Autowired
    private IUsuarioRepository usuarioRepository;
    @Override
    public List<Usuario> mostrarUsuario() {
        return (List<Usuario>) usuarioRepository.findAll();
    }

    @Override
    public Usuario agregarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario buscarUsuario(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public Usuario buscarUsuarioUser(String user) {
        return usuarioRepository.findTopByNombreUsuario(user).orElse(null);
    }

    @Override
    public void borrarUsuario(Long id) {
        usuarioRepository.deleteById(id);

    }
}
