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

/**
 * Entity implementation class for Entity: Departamento
 *
 */
@Entity
@Table(name = "lab_examen_indicaciones", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = {"id","id_examen","id_indicacion"}))
public class LabExamenIndicaciones implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_examen", nullable = false)
	private LabExamen examen;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_indicacion", nullable = false)
	private LabIndicaciones indicaciones;

	@Column(name = "fecha_modificacion", nullable = false)
	private Date fechaModificacion;
	@Column(name = "fecha_registro", nullable = false)
	private Date fechaRegistro;
	@Column(name = "usuario_registro", nullable = false)
	private String usuarioRegistro;
	private static final long serialVersionUID = 1L;

	public LabExamenIndicaciones() {
		super();
		this.id = 0;
		this.indicaciones= new LabIndicaciones();
		this.examen= new LabExamen();
		this.usuarioRegistro = "";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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
		return indicaciones.getDescripcion();
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
			if (!(obj instanceof LabExamenIndicaciones)) {
				return false;
			} else {
				if (((LabExamenIndicaciones) obj).id == this.id &&((LabExamenIndicaciones) obj).indicaciones.getId() == this.indicaciones.getId()) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

	/**
	 * @return the examen
	 */
	public LabExamen getExamen() {
		return examen;
	}

	/**
	 * @param examen
	 *            the examen to set
	 */
	public void setExamen(LabExamen examen) {
		this.examen = examen;
	}

	/**
	 * @return the indicaciones
	 */
	public LabIndicaciones getIndicaciones() {
		return indicaciones;
	}

	/**
	 * @param indicaciones
	 *            the indicaciones to set
	 */
	public void setIndicaciones(LabIndicaciones indicaciones) {
		this.indicaciones = indicaciones;
	}

}
