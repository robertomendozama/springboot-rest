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
public class EstadoController {
	
	@Autowired
	private EstadoRepository estadoRepository;
	private TicketRepository ticketRepository;
	
	
	
	@PostMapping("/estado/crear")
	public Estado crearEstado(@Valid @RequestBody Estado estado) {
		return estadoRepository.save(estado);
	}
	
	@GetMapping("/estado/getAll")
	public List<Estado> ObtenerEstados()throws ResourceNotFoundException {
	   return  estadoRepository.findAll();
	}

	@DeleteMapping("/estado/deleteById/{id}")
	public Map<String, Boolean> deleteEstado(@PathVariable(value = "id") Long estadoid)
	        throws ResourceNotFoundException {
	    Estado estado = estadoRepository.findById(estadoid)
	            .orElseThrow(() -> new ResourceNotFoundException("Estado no encontrado para id :: " + estadoid));

	    estadoRepository.delete(estado);
	    Map<String, Boolean> response = new HashMap<>();
	    response.put("deleted", Boolean.TRUE);
	    return response;
	}

	@PutMapping("/estado/putEstadoById/{id}")
	public ResponseEntity<Estado> updateEstado(@PathVariable(value = "id") Long estadoid, @RequestBody Estado estadodetails)
	        throws ResourceNotFoundException {
	    Estado estado = estadoRepository.findById(estadoid)
	            .orElseThrow(() -> new ResourceNotFoundException("Estado no encontrado para id :: " + estadoid));

	    estado.setDescripcion(estadodetails.getDescripcion());

	    final Estado updatedEstado = estadoRepository.save(estado);
	    return ResponseEntity.ok(updatedEstado);
	}

}
