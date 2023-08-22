package com.sinsistema.soporte.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name = "asunto")
    private String asunto;
    
    @Column(name = "descripcion")
    private String descripcion;
    
    @Column(name = "fecha_Solicitud")
    private String fechaSolicitud;
    
    @Column(name = "archivo")
    private byte[] archivo;
    
    @ManyToOne
    @JoinColumn(name = "id_Prioridad")
    private Prioridad prioridad;

    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_Categoria")
    private Categoria categoria;
    
    @ManyToOne
    @JoinColumn(name = "id_Estado")
    private Estado estado;
    
   
    @ManyToOne
    @JoinColumn(name = "id_Usuario_Solicitud")
    private Usuario usuarioSolicitud;

   
    @ManyToOne
    @JoinColumn(name = "id_Usuario_Asignado")
    private Usuario usuarioAsignado;
    
    
    
    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    private List<Mensaje> mensajes;

    // Getter and Setter methods

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(String fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public byte[] getArchivo() {
        return archivo;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Usuario getUsuarioSolicitud() {
        return usuarioSolicitud;
    }

    public void setUsuarioSolicitud(Usuario usuarioSolicitud) {
        this.usuarioSolicitud = usuarioSolicitud;
    }

    public Usuario getUsuarioAsignado() {
        return usuarioAsignado;
    }

    public void setUsuarioAsignado(Usuario usuarioAsignado) {
        this.usuarioAsignado = usuarioAsignado;
    }

    public List<Mensaje> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }

}
