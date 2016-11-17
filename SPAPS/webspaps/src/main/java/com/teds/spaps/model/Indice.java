package com.teds.spaps.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

/**
 * Entity implementation class for Entity: Indice
 *
 */
@Entity
@Table(name = "indice", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Indice implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7164019509389154356L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "paciente", nullable = true, insertable = true, updatable = true)
	private Integer paciente;
	@Column(name = "historia_clinica", nullable = true, insertable = true, updatable = true)
	private Integer historiaClinica;
	@Column(name = "receta", nullable = true, insertable = true, updatable = true)
	private Integer receta;
	@Column(name = "orden", nullable = true, insertable = true, updatable = true)
	private Integer orden;
	@Size(max = 2)
	@Column(name = "estado", nullable = false)
	// AC , IN , RM
	private String estado;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_compania", nullable = false)
	private Compania compania;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_sucursal", nullable = false)
	private Sucursal sucursal;
	@Column(name = "fecha_modificacion", nullable = false)
	private Date fechaModificacion;
	@Column(name = "fecha_registro", nullable = false)
	private Date fechaRegistro;
	@Column(name = "usuario_registro", nullable = false)
	private String usuarioRegistro;

	public Indice() {
		super();
		this.id = 0;
		this.paciente = 0;
		this.historiaClinica = 0;
		this.receta = 0;
		this.orden = 0;
		this.estado = "AC";
		this.compania = new Compania();
		this.sucursal = new Sucursal();
		this.usuarioRegistro = "";
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Compania getCompania() {
		return compania;
	}

	public void setCompania(Compania compania) {
		this.compania = compania;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getUsuarioRegistro() {
		return usuarioRegistro;
	}

	public void setUsuarioRegistro(String usuarioRegistro) {
		this.usuarioRegistro = usuarioRegistro;
	}

	/**
	 * @return the paciente
	 */
	public Integer getPaciente() {
		return paciente;
	}

	/**
	 * @param paciente
	 *            the paciente to set
	 */
	public void setPaciente(Integer paciente) {
		this.paciente = paciente;
	}

	/**
	 * @return the historiaClinica
	 */
	public Integer getHistoriaClinica() {
		return historiaClinica;
	}

	/**
	 * @param historiaClinica
	 *            the historiaClinica to set
	 */
	public void setHistoriaClinica(Integer historiaClinica) {
		this.historiaClinica = historiaClinica;
	}

	/**
	 * @return the receta
	 */
	public Integer getReceta() {
		return receta;
	}

	/**
	 * @param receta
	 *            the receta to set
	 */
	public void setReceta(Integer receta) {
		this.receta = receta;
	}

	/**
	 * @return the orden
	 */
	public Integer getOrden() {
		return orden;
	}

	/**
	 * @param orden
	 *            the orden to set
	 */
	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	@Override
	public String toString() {
		return this.historiaClinica.toString();
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
			if (!(obj instanceof Indice)) {
				return false;
			} else {
				if (((Indice) obj).id == this.id) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

}
