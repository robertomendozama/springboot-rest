package com.sinsistema.soporte.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sinsistema.soporte.model.Usuario;

public interface UsuarioRepository  extends  JpaRepository<Usuario, Long>  {
	Usuario findByCorreoAndContrasena(String correo, String contrasena);
	Usuario findByCorreo(String correo);
}
