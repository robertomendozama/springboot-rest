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
public class CargoController {
	
	@Autowired
	private CargoRepository cargoRepository;
	
	@PostMapping("/cargo/crear")
	public Cargo crearCargo(@Valid @RequestBody Cargo cargo) {
		return cargoRepository.save(cargo);
	}
	
	@GetMapping("/cargo/getAll")
	public List<Cargo> obtenerCargos() throws ResourceNotFoundException {
	   return cargoRepository.findAll();
	}

	@DeleteMapping("/cargo/deleteById/{id}")
	public Map<String, Boolean> deleteCargo(@PathVariable(value = "id") Long cargoId)
	        throws ResourceNotFoundException {
	    Cargo cargo = cargoRepository.findById(cargoId)
	            .orElseThrow(() -> new ResourceNotFoundException("Cargo no encontrado para id :: " + cargoId));

	    cargoRepository.delete(cargo);
	    Map<String, Boolean> response = new HashMap<>();
	    response.put("deleted", Boolean.TRUE);
	    return response;
	}

	@PutMapping("/cargo/putCargoById/{id}")
	public ResponseEntity<Cargo> updateCargo(@PathVariable(value = "id") Long cargoId, @RequestBody Cargo cargoDetails)
	        throws ResourceNotFoundException {
	    Cargo cargo = cargoRepository.findById(cargoId)
	            .orElseThrow(() -> new ResourceNotFoundException("Cargo no encontrado para id :: " + cargoId));

	    cargo.setDescripcion(cargoDetails.getDescripcion());

	    final Cargo updatedCargo = cargoRepository.save(cargo);
	    return ResponseEntity.ok(updatedCargo);
	}
}

