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
public class CategoriaController {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@PostMapping("/categoria/crear")
	public Categoria crearCategoria(@Valid @RequestBody Categoria categoria) {
		return categoriaRepository.save(categoria);
	}
	
	@GetMapping("/categoria/getAll")
	public List<Categoria> obtenerCategorias() throws ResourceNotFoundException {
	   return categoriaRepository.findAll();
	}

	@DeleteMapping("/categoria/deleteById/{id}")
	public Map<String, Boolean> deleteCategoria(@PathVariable(value = "id") Long categoriaId)
	        throws ResourceNotFoundException {
	    Categoria categoria = categoriaRepository.findById(categoriaId)
	            .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada para id :: " + categoriaId));

	    categoriaRepository.delete(categoria);
	    Map<String, Boolean> response = new HashMap<>();
	    response.put("deleted", Boolean.TRUE);
	    return response;
	}

	@PutMapping("/categoria/putCategoriaById/{id}")
	public ResponseEntity<Categoria> updateCategoria(@PathVariable(value = "id") Long categoriaId, @RequestBody Categoria categoriaDetails)
	        throws ResourceNotFoundException {
	    Categoria categoria = categoriaRepository.findById(categoriaId)
	            .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada para id :: " + categoriaId));

	    categoria.setDescripcion(categoriaDetails.getDescripcion());

	    final Categoria updatedCategoria = categoriaRepository.save(categoria);
	    return ResponseEntity.ok(updatedCategoria);
	}
}

