package com.teds.spaps.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.teds.spaps.enums.Dias;

/**
 * Entity implementation class for Entity: Cargo
 *
 */
@Entity
@Table(name = "rr_hh_dia_semana", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = { "id","id_personal_especialidad","id_turno" }))
public class RrHhDiaTurno implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Enumerated(EnumType.STRING)
	private Dias dias;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_personal", nullable = false)
	private Personal personal;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_personal_especialidad", nullable = false)
	private PersonalEspecialidad personalEspecialidad;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_turno", nullable = false)
	private RrHhTurno turno;
	
	@Column(name = "fecha_modificacion", nullable = true)
	private Date fechaModificacion;
	@Column(name = "fecha_registro", nullable = false)
	private Date fechaRegistro;
	@Column(name = "usuario_registro", nullable = false)
	private String usuarioRegistro;
	private static final long serialVersionUID = 1L;
	
	

	public RrHhDiaTurno() {
		super();
		this.id = 0;
		this.usuarioRegistro = "";
	}
	
	

	/**
	 * @param dias
	 * @param personalEspecialidad
	 * @param turno
	 * @param fechaModificacion
	 * @param fechaRegistro
	 * @param usuarioRegistro
	 */
	public RrHhDiaTurno(Dias dias, PersonalEspecialidad personalEspecialidad,
			RrHhTurno turno, Date fechaModificacion, Date fechaRegistro,
			String usuarioRegistro) {
		super();
		this.dias = dias;
		this.personalEspecialidad = personalEspecialidad;
		this.turno = turno;
		this.fechaModificacion = fechaModificacion;
		this.fechaRegistro = fechaRegistro;
		this.usuarioRegistro = usuarioRegistro;
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
		return getDias().getLabel();
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
			if (!(obj instanceof RrHhDiaTurno)) {
				return false;
			} else {
				if (((RrHhDiaTurno) obj).id == this.id) {
					return true;
				} else {
					return false;
				}
			}
		}
	}



	/**
	 * @return the turno
	 */
	public RrHhTurno getTurno() {
		return turno;
	}

	/**
	 * @param turno the turno to set
	 */
	public void setTurno(RrHhTurno turno) {
		this.turno = turno;
	}

	/**
	 * @return the dias
	 */
	public Dias getDias() {
		return dias;
	}

	/**
	 * @param dias the dias to set
	 */
	public void setDias(Dias dias) {
		this.dias = dias;
	}

	/**
	 * @return the personalEspecialidad
	 */
	public PersonalEspecialidad getPersonalEspecialidad() {
		return personalEspecialidad;
	}

	/**
	 * @param personalEspecialidad the personalEspecialidad to set
	 */
	public void setPersonalEspecialidad(PersonalEspecialidad personalEspecialidad) {
		this.personalEspecialidad = personalEspecialidad;
	}



	/**
	 * @return the personal
	 */
	public Personal getPersonal() {
		return personal;
	}



	/**
	 * @param personal the personal to set
	 */
	public void setPersonal(Personal personal) {
		this.personal = personal;
	}

}
