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
public class TicketController {
	
	private TicketRepository ticketRepository;
	private PrioridadRepository prioridadRepository;
	private EstadoRepository estadoRepository;
	private UsuarioRepository usuarioRepository;

	
	@Autowired
	public TicketController(TicketRepository ticketRepository,
	                         PrioridadRepository prioridadRepository,
	                         EstadoRepository estadoRepository,
	                         UsuarioRepository usuarioRepository) {
	    this.ticketRepository = ticketRepository;
	    this.prioridadRepository = prioridadRepository;
	    this.estadoRepository = estadoRepository;
	    this.usuarioRepository = usuarioRepository;
	}

	
	
	
	
	
	@PostMapping("/ticket")
	public Ticket crearTicket(@Valid @RequestBody Ticket ticket) {
		return ticketRepository.save(ticket);
	}
	
	@GetMapping("/ticket/getAll")
	public List<Ticket> ObtenerAllTicket() {
		return ticketRepository.findAll();
	}
	
	@GetMapping("/ticket/getById/{id}")
	public ResponseEntity<Ticket> ObtenerTicketporID(@PathVariable(value= "id") Long ticketId)
			throws ResourceNotFoundException{
		Ticket ticket = ticketRepository.findById(ticketId)
				.orElseThrow(() -> new ResourceNotFoundException("Ticket no encontrado para el id:: " + ticketId));
		ticket.getUsuarioAsignado();
		ticket.getUsuarioSolicitud();
		return ResponseEntity.ok().body(ticket);
	}
	
	@Transactional
	@GetMapping("/buscarTicket")
	public ResponseEntity<List<Ticket>> searchTickets(	@RequestParam(required = false) Integer id,
														@RequestParam(required = false) Integer prioridad,
														@RequestParam(required = false) Integer id_estado,
														@RequestParam(required = false) Integer id_usuario_solicitud,
														@RequestParam(required = false) Integer id_Usuario_Asignado,
														@RequestParam(required = false) String fecha) {
	    List<Ticket> tickets = ticketRepository.buscarTickets(id, prioridad, id_estado, id_usuario_solicitud, id_Usuario_Asignado, fecha);
		if (tickets.isEmpty()) {
	        return ResponseEntity.notFound().build();
	    } else {
	        return ResponseEntity.ok(tickets);
	    }
	}

	@DeleteMapping("/ticket/deleteById/{id}")
	public Map<String, Boolean> deleteTicket(@PathVariable(value = "id") Long ticketId)
	        throws ResourceNotFoundException {
	    Ticket ticket = ticketRepository.findById(ticketId)
	            .orElseThrow(() -> new ResourceNotFoundException("Ticket no encontrado para id :: " + ticketId));

	    ticketRepository.delete(ticket);
	    Map<String, Boolean> response = new HashMap<>();
	    response.put("deleted", Boolean.TRUE);
	    return response;
	}

	@PutMapping("/ticket/putById/{id}")
	public ResponseEntity<Ticket> updateTicket(@PathVariable(value = "id") Long ticketId, @RequestBody Ticket ticketDetails)
	        throws ResourceNotFoundException {
	    Ticket ticket = ticketRepository.findById(ticketId)
	            .orElseThrow(() -> new ResourceNotFoundException("Ticket no encontrado para id :: " + ticketId));

	    ticket.setAsunto(ticketDetails.getAsunto());
	    ticket.setDescripcion(ticketDetails.getDescripcion());
	    ticket.setFechaSolicitud(ticketDetails.getFechaSolicitud());
	    ticket.setArchivo(ticketDetails.getArchivo());
	    ticket.setPrioridad(ticketDetails.getPrioridad());
	    ticket.setCategoria(ticketDetails.getCategoria());
	    ticket.setEstado(ticketDetails.getEstado());
	    ticket.setUsuarioSolicitud(ticketDetails.getUsuarioSolicitud());
	    ticket.setUsuarioAsignado(ticketDetails.getUsuarioAsignado());
	    ticket.setMensajes(ticketDetails.getMensajes());

	    final Ticket updatedTicket = ticketRepository.save(ticket);
	    return ResponseEntity.ok(updatedTicket);
	}

//	@GetMapping("/ticket/getById/{id}")
//	public ResponseEntity<Ticket> obtenerTicketPorId(@PathVariable(value = "id") Long ticketId)
//	        throws ResourceNotFoundException {
//	    Ticket ticket = ticketRepository.findById(ticketId)
//	            .orElseThrow(() -> new ResourceNotFoundException("Ticket no encontrado para el id:: " + ticketId));
//	    return ResponseEntity.ok().body(ticket);
//	}

	@GetMapping("/ticket/prioridad/{prioridadId}")
	public ResponseEntity<List<Ticket>> obtenerTicketsPorPrioridad(@PathVariable(value = "prioridadId") Long prioridadId) throws ResourceNotFoundException {
	    Prioridad prioridad = prioridadRepository.findById(prioridadId)
	            .orElseThrow(() -> new ResourceNotFoundException("Prioridad no encontrada para el id:: " + prioridadId));
	    List<Ticket> tickets = ticketRepository.findByPrioridad(prioridad);
	    return ResponseEntity.ok().body(tickets);
	}

	@GetMapping("/ticket/estado/{estadoId}")
	public ResponseEntity<List<Ticket>> obtenerTicketsPorEstado(@PathVariable(value = "estadoId") Long estadoId) throws ResourceNotFoundException {
	    Estado estado = estadoRepository.findById(estadoId)
	            .orElseThrow(() -> new ResourceNotFoundException("Estado no encontrado para el id:: " + estadoId));
	    List<Ticket> tickets = ticketRepository.findByEstado(estado);
	    return ResponseEntity.ok().body(tickets);
	}

	@GetMapping("/ticket/usuario-asignado/{usuarioId}")
	public ResponseEntity<List<Ticket>> obtenerTicketsPorUsuarioAsignado(@PathVariable(value = "usuarioId") Long usuarioId)throws ResourceNotFoundException {
	    Usuario usuarioAsignado = usuarioRepository.findById(usuarioId)
	            .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado para el id:: " + usuarioId));
	    List<Ticket> tickets = ticketRepository.findByUsuarioAsignado(usuarioAsignado);
	    return ResponseEntity.ok().body(tickets);
	}

	@GetMapping("/ticket/usuario-solicitud/{usuarioId}")
	public ResponseEntity<List<Ticket>> obtenerTicketsPorUsuarioSolicitud(@PathVariable(value = "usuarioId") Long usuarioId)throws ResourceNotFoundException {
	    Usuario usuarioSolicitud = usuarioRepository.findById(usuarioId)
	            .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado para el id:: " + usuarioId));
	    List<Ticket> tickets = ticketRepository.findByUsuarioSolicitud(usuarioSolicitud);
	    return ResponseEntity.ok().body(tickets);
	}

	
	
}
