package com.sinsistema.soporte.controller;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sinsistema.soporte.exception.ResourceNotFoundException;
import com.sinsistema.soporte.model.*;
import com.sinsistema.soporte.repository.*;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class RolController {
	
	@Autowired
	private RolRepository rolRepository;
	
	@PostMapping("/rol/crear")
	public Rol crearRol(@Valid @RequestBody Rol rol) {
		return rolRepository.save(rol);
	}
	
	@GetMapping("/rol/getAll")
	public List<Rol> obtenerRoles() throws ResourceNotFoundException {
	   return rolRepository.findAll();
	}

	@DeleteMapping("/rol/deleteById/{id}")
	public Map<String, Boolean> deleteRol(@PathVariable(value = "id") Long rolId)
	        throws ResourceNotFoundException {
	    Rol rol = rolRepository.findById(rolId)
	            .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado para id :: " + rolId));

	    rolRepository.delete(rol);
	    Map<String, Boolean> response = new HashMap<>();
	    response.put("deleted", Boolean.TRUE);
	    return response;
	}

	@PutMapping("/rol/putRolById/{id}")
	public ResponseEntity<Rol> updateRol(@PathVariable(value = "id") Long rolId, @RequestBody Rol rolDetails)
	        throws ResourceNotFoundException {
	    Rol rol = rolRepository.findById(rolId)
	            .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado para id :: " + rolId));

	    rol.setDescripcion(rolDetails.getDescripcion());

	    final Rol updatedRol = rolRepository.save(rol);
	    return ResponseEntity.ok(updatedRol);
	}
}
