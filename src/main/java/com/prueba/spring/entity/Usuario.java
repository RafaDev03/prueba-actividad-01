package com.prueba.spring.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Long usuarioId;

    private String password;
    @Column(unique = true)
    private String email;

    private String nombres;

    private String apellidos;

    @Column(name = "nombre_empresa")
    private String nombreEmpresa;

    private String telefono;

    @Column(name = "foto_perfil")
    private String fotoPerfil;

    @ManyToOne(targetEntity = Cargo.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "cargo_id")
    private Cargo cargo;

    @ManyToOne(targetEntity = Rol.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "rol_id")
    private Rol rol;
}
