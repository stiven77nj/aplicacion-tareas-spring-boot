package com.example.tareas.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table( name = "USUARIOS", schema = "app_tareas")
public class Usuario implements Serializable {

  @Id
  @GeneratedValue( strategy = GenerationType.IDENTITY )
  @Column( name = "ID")
  private Long id;

  @Column( name = "NOMBRE")
  private String nombre;

  @Column( name = "APELLIDO")
  private String apellido;

  @Column( name = "CEDULA")
  private Long cedula;

  @Column( name = "TELEFONO")
  private Long telefono;

  @Column( name = "NOMBRE_USUARIO")
  private String nombreUsuario;

  @Column( name = "CONTRASEÑA")
  private String password;

  @Column( name = "FECHA_CREACION")
  @Temporal( TemporalType.TIMESTAMP )
  private Date fechaCreacion;
}
