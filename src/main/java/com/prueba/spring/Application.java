package com.prueba.spring;

import java.util.List;
import java.time.LocalDate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.prueba.spring.entity.Cargo;
import com.prueba.spring.entity.Categoria;
import com.prueba.spring.entity.ECargo;
import com.prueba.spring.entity.EEstado;
import com.prueba.spring.entity.ERole;
import com.prueba.spring.entity.EstadoProyecto;
import com.prueba.spring.entity.Proyecto;
import com.prueba.spring.entity.Rol;
import com.prueba.spring.entity.Usuario;
import com.prueba.spring.repository.CargoRepository;
import com.prueba.spring.repository.CategoriaRepository;
import com.prueba.spring.repository.EstadoProyectoRepository;
import com.prueba.spring.repository.ProyectoRepository;
import com.prueba.spring.repository.RolRepository;
import com.prueba.spring.repository.UsuarioRepository;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner init(EstadoProyectoRepository estadoProyectoRepository,
			ProyectoRepository proyectoRepository,
			CargoRepository cargoRepository, RolRepository rolRepository,
			UsuarioRepository usuarioRepository, CategoriaRepository categoriaRepository) {
		return args -> {
			Rol rolAdmin = Rol.builder()
					.rol(ERole.ADMIN)
					.build();

			Rol rolGerente = Rol.builder()
					.rol(ERole.GERENTE)
					.build();

			Rol rolUser = Rol.builder()
					.rol(ERole.USER)
					.build();

			Cargo cargoCEO = Cargo.builder()
					.nombreCargo(ECargo.CEO)
					.build();

			Cargo cargoDesarrollador = Cargo.builder()
					.nombreCargo(ECargo.DESARROLLADOR)
					.build();

			Cargo cargoGerente = Cargo.builder()
					.nombreCargo(ECargo.GERENTE)
					.build();

			EstadoProyecto estadoEnCurso = EstadoProyecto.builder()
					.nombreEstado(EEstado.EN_CURSO)
					.build();
			EstadoProyecto estadoEnRevision = EstadoProyecto.builder()
					.nombreEstado(EEstado.EN_REVISION)
					.build();
			EstadoProyecto estadoFinalizado = EstadoProyecto.builder()
					.nombreEstado(EEstado.FINALIZADO)
					.build();
			EstadoProyecto estadoPlanificacion = EstadoProyecto.builder()
					.nombreEstado(EEstado.PLANIFICACION)
					.build();

			rolRepository.saveAll(List.of(rolAdmin, rolGerente, rolUser));
			cargoRepository.saveAll(List.of(cargoCEO, cargoDesarrollador, cargoGerente));
			estadoProyectoRepository
					.saveAll(List.of(estadoEnCurso, estadoEnRevision, estadoFinalizado,
							estadoPlanificacion));

			Categoria categoria1 = Categoria.builder()
					.nombreCategoria("Aplicaciones Web")
					.build();

			Categoria categoria2 = Categoria.builder()
					.nombreCategoria("Aplicaciones Móviles")
					.build();

			Categoria categoria3 = Categoria.builder()
					.nombreCategoria("Aplicaciones Web")
					.build();

			Categoria categoria4 = Categoria.builder()
					.nombreCategoria("Software Empresarial")
					.build();

			Categoria categoria5 = Categoria.builder()
					.nombreCategoria("Automatización de Procesos")
					.build();

			categoriaRepository.saveAll(List.of(categoria1, categoria2, categoria3, categoria4, categoria5));

			Usuario usuario1 = Usuario.builder()
					.password("$2a$10$24TvapBOsKLRxAlxjSmBGOKLYr4zhV.VJ77RsX5j/L7VViwcf1sqC")
					.email("rafael@gmail.com")
					.nombres("Rafael Adonis")
					.apellidos("Flores Rivera")
					.nombreEmpresa("LVL Consulting")
					.telefono("989874474")
					.fotoPerfil("---")
					.cargo(cargoDesarrollador)
					.rol(rolGerente)
					.build();

			Usuario usuario2 = Usuario.builder()
					.password("$2a$10$24TvapBOsKLRxAlxjSmBGOKLYr4zhV.VJ77RsX5j/L7VViwcf1sqC")
					.email("estrella@gmail.com")
					.nombres("Estrella Susana")
					.apellidos("Flores Rivera")
					.nombreEmpresa("LVL Consulting")
					.telefono("989874474")
					.fotoPerfil("---")
					.cargo(cargoCEO)
					.rol(rolUser)
					.build();

			Usuario usuario3 = Usuario.builder()
					.password("$2a$10$24TvapBOsKLRxAlxjSmBGOKLYr4zhV.VJ77RsX5j/L7VViwcf1sqC")
					.email("milene@gmail.com")
					.nombres("Milene")
					.apellidos("Peña Espinoza")
					.nombreEmpresa("LVL Consulting")
					.telefono("989874474")
					.fotoPerfil("---")
					.cargo(cargoDesarrollador)
					.rol(rolUser)
					.build();

			usuarioRepository.saveAll(List.of(usuario1, usuario2, usuario3));

			Proyecto proyecto1 = Proyecto.builder()
					.codigoProyecto("ATA-1")
					.nombre("Proyecto de App")
					.descripcion("-------------")
					.fechaInicio(LocalDate.of(2024, 8, 24))
					.fechaFinal(LocalDate.of(2024, 9, 26))
					.logo("---")
					.estado(estadoPlanificacion)
					.categoria(categoria5)
					.usuario(usuario1)
					.build();

			Proyecto proyecto2 = Proyecto.builder()
					.codigoProyecto("PA-21")
					.nombre("Diseño de RR.SS")
					.descripcion("-------------")
					.fechaInicio(LocalDate.of(2024, 8, 15))
					.fechaFinal(LocalDate.of(2024, 9, 14))
					.logo("---")
					.estado(estadoEnCurso)
					.usuario(usuario1)
					.categoria(categoria1)
					.build();

			Proyecto proyecto3 = Proyecto.builder()
					.codigoProyecto("ATA-2")
					.nombre("Control de calidad")
					.descripcion("-------------")
					.fechaInicio(LocalDate.of(2024, 8, 24))
					.fechaFinal(LocalDate.of(2024, 9, 26))
					.logo("---")
					.estado(estadoFinalizado)
					.usuario(usuario1)
					.categoria(categoria3)
					.build();
			proyectoRepository.saveAll(List.of(proyecto1, proyecto2, proyecto3));
		};
	}

}
