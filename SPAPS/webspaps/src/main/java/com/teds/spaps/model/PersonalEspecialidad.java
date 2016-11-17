package com.teds.spaps.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

/**
 * Entity implementation class for Entity: DetallePersonal
 *
 */
@Entity
@Table(name = "rr_hh_personal_especialidad", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = {
		"id", "id_personal", "id_especialidad" }))
public class PersonalEspecialidad implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5953570195609026124L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_personal", nullable = false)
	private Personal personal;

	private Integer correlativo = 0;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_especialidad", nullable = false)
	private Especialidad especialidad;

	private boolean estado;

	@Column(name = "fecha_modificacion", nullable = false)
	private Date fechaModificacion;
	@Column(name = "fecha_registro", nullable = false)
	private Date fechaRegistro;
	@Column(name = "usuario_registro", nullable = false)
	private String usuarioRegistro;

	@OneToMany(mappedBy = "personalEspecialidad", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	List<RrHhDiaTurno> listDiaTurnos = new ArrayList<RrHhDiaTurno>();

	public PersonalEspecialidad() {
		super();
		this.id = 0;
		this.personal = new Personal();
		this.especialidad = new Especialidad();
		this.estado = false;
		this.usuarioRegistro = "";
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Personal getPersonal() {
		return personal;
	}

	public void setPersonal(Personal personal) {
		this.personal = personal;
	}

	public Especialidad getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(Especialidad especialidad) {
		this.especialidad = especialidad;
	}

	@Override
	public String toString() {
		return "Grupo = " + this.personal.toString() + ", Historia Medica = "
				+ this.especialidad.getCodigo();
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
			if (!(obj instanceof PersonalEspecialidad)) {
				return false;
			} else {
				if (((PersonalEspecialidad) obj).correlativo.intValue() == this.correlativo.intValue()) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

	/**
	 * @return the estado
	 */
	public boolean isEstado() {
		return estado;
	}

	/**
	 * @param estado
	 *            the estado to set
	 */
	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	/**
	 * @return the listDiaTurnos
	 */
	public List<RrHhDiaTurno> getListDiaTurnos() {
		return listDiaTurnos;
	}

	/**
	 * @param listDiaTurnos
	 *            the listDiaTurnos to set
	 */
	public void setListDiaTurnos(List<RrHhDiaTurno> listDiaTurnos) {
		this.listDiaTurnos = listDiaTurnos;
	}

	/**
	 * @return the correlativo
	 */
	public int getCorrelativo() {
		return correlativo;
	}

	/**
	 * @param correlativo
	 *            the correlativo to set
	 */
	public void setCorrelativo(int correlativo) {
		this.correlativo = correlativo;
	}

}
