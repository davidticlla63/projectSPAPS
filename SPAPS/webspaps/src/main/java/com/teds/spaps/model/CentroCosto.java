package com.teds.spaps.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * 
 * @author mauriciobejaranorivera
 *
 */
@Entity
@Table(name = "centro_costo", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = {"nombre","id_empresa"}))
public class CentroCosto implements Serializable {

	private static final long serialVersionUID = -8698947209563190803L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String nombre;
	
	@Column(name="descripcion",nullable=true )
	private String descripcion;
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_empresa", nullable=false)
	private Empresa empresa;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_centro_costo", nullable=true)
	private CentroCosto centroCosto;

	@Size(max = 2) //AC , IN
	private String estado;

	@Column(name="usuario_registro",nullable=false )
	private String usuarioRegistro;

	@Column(name="fecha_registro",nullable=false )
	private Date fechaRegistro;

	@Column(name="fecha_modificacion",nullable=true )
	private Date fechaModificacion;

	public CentroCosto() {
		super();
		this.id = 0;
	}

	@Override
	public String toString() {
		return nombre;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj==null){
			return false;
		}else{
			if(!(obj instanceof CentroCosto)){
				return false;
			}else{
				if(((CentroCosto)obj).id==this.id){
					return true;
				}else{
					return false;
				}
			}
		}
	}
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getUsuarioRegistro() {
		return usuarioRegistro;
	}

	public void setUsuarioRegistro(String usuarioRegistro) {
		this.usuarioRegistro = usuarioRegistro;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public CentroCosto getCentroCosto() {
		return centroCosto;
	}

	public void setCentroCosto(CentroCosto centroCosto) {
		this.centroCosto = centroCosto;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}


