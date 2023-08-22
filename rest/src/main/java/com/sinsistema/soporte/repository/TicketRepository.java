package com.sinsistema.soporte.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import com.sinsistema.soporte.model.*;

public interface TicketRepository extends  JpaRepository<Ticket, Long> {
	
	
	@Procedure(name = "BuscarTickets")
    List<Ticket> buscarTickets(	@Param("Id") Integer Id,
					    		@Param("prioridad") Integer prioridad,
					    		@Param("id_estado") Integer id_estado,
					    		@Param("id_usuario_solicitud") Integer id_usuario_solicitud,
					    		@Param("id_Usuario_Asignado") Integer id_Usuario_Asignado,
					    		@Param("fecha_Solicitud") String fecha_Solicitud);
	
	
    List<Ticket> findByPrioridad(Prioridad prioridad);

    List<Ticket> findByEstado(Estado estado);

    List<Ticket> findByUsuarioAsignado(Usuario usuarioAsignado);

    List<Ticket> findByUsuarioSolicitud(Usuario usuarioSolicitud);
	
}
