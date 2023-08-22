package com.sinsistema.soporte.model;

import com.fasterxml.jackson.annotation.*;

import jakarta.persistence.*;

@Entity
@Table(name = "mensaje")
public class Mensaje {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
		
	@Column(name="descripcion")
	private String descripcion;
	
	@Column(name="fecha")
	private String fecha;

	 @JsonIgnore
	@ManyToOne
    @JoinColumn(name = "id_ticket")
    private Ticket ticket;
	

	@Column(name="id_usuario")
	 @JsonProperty("usuarioId")
	private int usuarioId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public Ticket getTicket() {
	    return ticket;
	}

	public void setTicket(Ticket ticket) {
	    this.ticket = ticket;
	}


	public int getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	}
