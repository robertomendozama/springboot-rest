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
public class PrioridadController {
	
	@Autowired
	private PrioridadRepository prioridadRepository;
	
	@PostMapping("/prioridad/crear")
	public Prioridad crearPrioridad(@Valid @RequestBody Prioridad prioridad) {
		return prioridadRepository.save(prioridad);
	}
	
	@GetMapping("/prioridad/getAll")
	public List<Prioridad> obtenerPrioridades() throws ResourceNotFoundException {
	   return prioridadRepository.findAll();
	}

	@DeleteMapping("/prioridad/deleteById/{id}")
	public Map<String, Boolean> deletePrioridad(@PathVariable(value = "id") Long prioridadId)
	        throws ResourceNotFoundException {
	    Prioridad prioridad = prioridadRepository.findById(prioridadId)
	            .orElseThrow(() -> new ResourceNotFoundException("Prioridad no encontrada para id :: " + prioridadId));

	    prioridadRepository.delete(prioridad);
	    Map<String, Boolean> response = new HashMap<>();
	    response.put("deleted", Boolean.TRUE);
	    return response;
	}

	@PutMapping("/prioridad/putPrioridadById/{id}")
	public ResponseEntity<Prioridad> updatePrioridad(@PathVariable(value = "id") Long prioridadId, @RequestBody Prioridad prioridadDetails)
	        throws ResourceNotFoundException {
	    Prioridad prioridad = prioridadRepository.findById(prioridadId)
	            .orElseThrow(() -> new ResourceNotFoundException("Prioridad no encontrada para id :: " + prioridadId));

	    prioridad.setDescripcion(prioridadDetails.getDescripcion());

	    final Prioridad updatedPrioridad = prioridadRepository.save(prioridad);
	    return ResponseEntity.ok(updatedPrioridad);
	}
}
