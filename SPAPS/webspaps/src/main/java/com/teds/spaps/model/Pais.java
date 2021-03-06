package com.teds.spaps.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

/**
 * Entity implementation class for Entity: Pais
 *
 */
@Entity
@Table(name = "pais", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Pais implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Size(max = 255)
	@Column(name = "nombre", nullable = false)
	private String nombre;
	@Size(max = 2)
	@Column(name = "estado", nullable = false)
	// AC , IN , RM
	private String estado;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_compania", nullable = false)
	private Compania compania;

	@Column(name = "fecha_modificacion", nullable = false)
	private Date fechaModificacion;

	@Column(name = "fecha_registro", nullable = false)
	private Date fechaRegistro;

	@Column(name = "usuario_registro", nullable = false)
	private String usuarioRegistro;
	private static final long serialVersionUID = 1L;
	
	@OneToMany(mappedBy = "pais", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Ciudad> listCiudads= new ArrayList<Ciudad>();

	public Pais() {
		super();
		this.id = 0;
		this.nombre = "";
		this.estado = "AC";
//		this.compania = new Compania();
		this.usuarioRegistro = "";
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

//	public Compania getCompania() {
//		return compania;
//	}
//
//	public void setCompania(Compania compania) {
//		this.compania = compania;
//	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fecha_modificacion) {
		this.fechaModificacion = fecha_modificacion;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fecha_registro) {
		this.fechaRegistro = fecha_registro;
	}

	public String getUsuarioRegistro() {
		return usuarioRegistro;
	}

	public void setUsuarioRegistro(String usuario_registro) {
		this.usuarioRegistro = usuario_registro;
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
		if (obj == null) {
			return false;
		} else {
			if (!(obj instanceof Pais)) {
				return false;
			} else {
				if (((Pais) obj).id == this.id) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

	/**
	 * @return the listCiudads
	 */
	public List<Ciudad> getListCiudads() {
		return listCiudads;
	}

	/**
	 * @param listCiudads the listCiudads to set
	 */
	public void setListCiudads(List<Ciudad> listCiudads) {
		this.listCiudads = listCiudads;
	}

	/**
	 * @return the compania
	 */
	public Compania getCompania() {
		return compania;
	}

	/**
	 * @param compania the compania to set
	 */
	public void setCompania(Compania compania) {
		this.compania = compania;
	}

}
