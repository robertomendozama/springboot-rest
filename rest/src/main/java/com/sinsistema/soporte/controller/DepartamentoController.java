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
public class DepartamentoController {
	
	@Autowired
	private DepartamentoRepository departamentoRepository;
	
	@PostMapping("/departamento/crear")
	public Departamento crearDepartamento(@Valid @RequestBody Departamento departamento) {
		return departamentoRepository.save(departamento);
	}
	
	@GetMapping("/departamento/getAll")
	public List<Departamento> obtenerDepartamentos() throws ResourceNotFoundException {
	   return departamentoRepository.findAll();
	}

	@DeleteMapping("/departamento/deleteById/{id}")
	public Map<String, Boolean> deleteDepartamento(@PathVariable(value = "id") Long departamentoId)
	        throws ResourceNotFoundException {
	    Departamento departamento = departamentoRepository.findById(departamentoId)
	            .orElseThrow(() -> new ResourceNotFoundException("Departamento no encontrado para id :: " + departamentoId));

	    departamentoRepository.delete(departamento);
	    Map<String, Boolean> response = new HashMap<>();
	    response.put("deleted", Boolean.TRUE);
	    return response;
	}

	@PutMapping("/departamento/putDepartamentoById/{id}")
	public ResponseEntity<Departamento> updateDepartamento(@PathVariable(value = "id") Long departamentoId, @RequestBody Departamento departamentoDetails)
	        throws ResourceNotFoundException {
	    Departamento departamento = departamentoRepository.findById(departamentoId)
	            .orElseThrow(() -> new ResourceNotFoundException("Departamento no encontrado para id :: " + departamentoId));

	    departamento.setDescripcion(departamentoDetails.getDescripcion());

	    final Departamento updatedDepartamento = departamentoRepository.save(departamento);
	    return ResponseEntity.ok(updatedDepartamento);
	}
}
