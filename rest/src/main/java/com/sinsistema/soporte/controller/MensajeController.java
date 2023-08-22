package com.sinsistema.soporte.controller;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sinsistema.soporte.exception.ResourceNotFoundException;
import com.sinsistema.soporte.model.Mensaje;
import com.sinsistema.soporte.model.Ticket;
import com.sinsistema.soporte.repository.MensajeRepository;
import com.sinsistema.soporte.repository.TicketRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class MensajeController {
	
	@Autowired
	private MensajeRepository mensajeRepository;
	@Autowired
	private TicketRepository ticketRepository;
	
	
	
//	@PostMapping("/mensaje")
//	public Mensaje crearMensaje(@Valid @RequestBody Mensaje mensaje) {
//		return mensajeRepository.save(mensaje);
//	}
	
	@PostMapping("/mensaje/{ticketId}")
	public Mensaje crearMensaje(@PathVariable("ticketId") long ticketId, @Valid @RequestBody Mensaje mensaje) {

	    Ticket ticket = ticketRepository.findById(ticketId).orElseThrow();

	    mensaje.setTicket(ticket);
	    ticket.getMensajes().add(mensaje);

	    mensajeRepository.save(mensaje);
	    ticketRepository.save(ticket);

	    return mensaje;
	}


	
	@GetMapping("/mensajesTicket/{id}")
	public ResponseEntity<List<Mensaje>> ObtenerMensajesPorTicket(@PathVariable(value = "id") Long ticketId)
	        throws ResourceNotFoundException {
	    List<Mensaje> mensajes = mensajeRepository.findByTicketId(ticketId);

	    if (mensajes.isEmpty()) {
	        throw new ResourceNotFoundException("No hay mensajes para el ticket con ID: " + ticketId);
	    }

	    return ResponseEntity.ok().body(mensajes);
	}

	@DeleteMapping("/mensaje/{id}")
	public Map<String, Boolean> deleteMensaje(@PathVariable(value = "id") Long mensajeId)
	        throws ResourceNotFoundException {
	    Mensaje mensaje = mensajeRepository.findById(mensajeId)
	            .orElseThrow(() -> new ResourceNotFoundException("Mensaje no encontrado para id :: " + mensajeId));

	    mensajeRepository.delete(mensaje);
	    Map<String, Boolean> response = new HashMap<>();
	    response.put("deleted", Boolean.TRUE);
	    return response;
	}

	@PutMapping("/mensaje/{id}")
	public ResponseEntity<Mensaje> updateMensaje(@PathVariable(value = "id") Long mensajeId, @RequestBody Mensaje mensajeDetails)
	        throws ResourceNotFoundException {
	    Mensaje mensaje = mensajeRepository.findById(mensajeId)
	            .orElseThrow(() -> new ResourceNotFoundException("Mensaje no encontrado para id :: " + mensajeId));

	    mensaje.setDescripcion(mensajeDetails.getDescripcion());
	    mensaje.setFecha(mensajeDetails.getFecha());
	    mensaje.setTicket(mensajeDetails.getTicket());
	    mensaje.setUsuarioId(mensajeDetails.getUsuarioId());

	    final Mensaje updatedMensaje = mensajeRepository.save(mensaje);
	    return ResponseEntity.ok(updatedMensaje);
	}

}
