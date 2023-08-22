package com.sinsistema.soporte.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sinsistema.soporte.exception.ResourceNotFoundException;
import com.sinsistema.soporte.model.*;
import com.sinsistema.soporte.repository.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class UsuarioController {
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private CargoRepository cargoRepository;
	@Autowired
	private DepartamentoRepository departamentoRepository;
	@Autowired
	private RolRepository rolRepository;

	@GetMapping("/usuario")
	public List<Usuario> getAllusuario() {
		return usuarioRepository.findAll();
	}

//	@GetMapping("/usuario/{rut}")
//	public ResponseEntity<Usuario> getusuarioById(@PathVariable(value = "rut") Long rut)
//			throws ResourceNotFoundException {
//		Usuario usuario = usuarioRepository.findById(rut)
//				.orElseThrow(() -> new ResourceNotFoundException("usuario not found for this rut :: " + rut));
//		return ResponseEntity.ok().body(usuario);
//	}
//	
	
	@GetMapping("/usuario/{rut}")
	public ResponseEntity<Usuario> getUsuarioById(@PathVariable(value = "rut") Long rut)
	        throws ResourceNotFoundException {
	    Usuario usuario = usuarioRepository.findById(rut)
	            .orElseThrow(() -> new ResourceNotFoundException("Usuario not found for this rut: " + rut));

	    usuario.getIdCargo(); 
	    usuario.getIdDepartamento(); 
	    usuario.getIdRol(); 

	    return ResponseEntity.ok().body(usuario);
	}

	@PostMapping("/usuario")
	public Usuario createusuario(@Valid @RequestBody Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

	@PutMapping("/usuario/{rut}")
	public ResponseEntity<Usuario> updateusuario(@PathVariable(value = "rut") Long rut,
			@Valid @RequestBody Usuario usuarioDetails) throws ResourceNotFoundException {
		Usuario usuario = usuarioRepository.findById(rut)
				.orElseThrow(() -> new ResourceNotFoundException("usuario not found for this rut : " + rut));

		usuario.setCorreo(usuarioDetails.getCorreo());
		usuario.setIdCargo(usuarioDetails.getIdCargo());
		usuario.setActivo(usuarioDetails.isActivo());
		usuario.setContrasena(usuarioDetails.getContrasena());
		usuario.setCorreo(usuarioDetails.getCorreo());
		usuario.setDv(usuarioDetails.getDv());
		usuario.setFechaCreacion(usuarioDetails.getFechaCreacion());
		usuario.setIdCargo(usuarioDetails.getIdCargo());
		usuario.setIdDepartamento(usuarioDetails.getIdDepartamento());
		usuario.setIdRol(usuarioDetails.getIdRol());
		usuario.setNombre(usuarioDetails.getNombre());
		usuario.setTelefono(usuarioDetails.getTelefono());

		final Usuario updatedusuario = usuarioRepository.save(usuario);
		return ResponseEntity.ok(updatedusuario);
	}

	@DeleteMapping("/usuario/{rut}")
	public Map<String, Boolean> deleteusuario(@PathVariable(value = "rut") Long rut)
			throws ResourceNotFoundException {
		Usuario usuario = usuarioRepository.findById(rut)
				.orElseThrow(() -> new ResourceNotFoundException("usuario not found for this rut :: " + rut));

		usuarioRepository.delete(usuario);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	
	
	@GetMapping("/login")
	public ResponseEntity<?> login(@RequestParam String correo, @RequestParam String contrasena)
	        throws ResourceNotFoundException {
	    Usuario usuario = usuarioRepository.findByCorreoAndContrasena(correo, contrasena);
	    
	    if (usuario == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
	    } else {
	    	usuario.getRut();
	        usuario.getIdCargo(); 
	        usuario.getIdDepartamento(); 
	        usuario.getIdRol(); 

	        return ResponseEntity.ok(usuario);
	    }
	}





}