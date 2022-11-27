package com.example.tareas.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UsuarioController {
    @RequestMapping("/saludo")
    public ResponseEntity<String> mensaje(){
        return new ResponseEntity<>("Wenas tardes", HttpStatus.OK);
    }
}
