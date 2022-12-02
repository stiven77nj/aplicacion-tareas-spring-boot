package com.example.tareas.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table( name = "USUARIOS", schema = "app_tareas")
public class Usuario implements Serializable {

  private static final long serialVersionUID = -8775757554770303683L;

  @Id
  @GeneratedValue( strategy = GenerationType.IDENTITY )
  @Column( name = "ID")
  private Long id;

  @Column(nullable = false, name = "NOMBRE")
  private String nombre;

  @Column( name = "APELLIDO")
  private String apellido;

  @Column( name = "CEDULA",nullable = false)
  private Long cedula;

  @Column( name = "TELEFONO")
  private Long telefono;

  @Column( name = "NOMBRE_USUARIO", unique = true,nullable = false)
  private String nombreUsuario;

  @Column( name = "CONTRASEÑA",nullable = false)
  private String password;

  @Column( name = "FECHA_CREACION")
  @Temporal( TemporalType.TIMESTAMP )
  private Date fechaCreacion;
}
