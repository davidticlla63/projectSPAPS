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
 * Entity implementation class for Entity: Cita
 *
 */
@Entity
@Table(name = "cita", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = {
		"id", "id_sucursal" }))
public class Cita implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Size(max = 2)
	@Column(name = "clase", nullable = false)
	private String clase;

	@Size(max = 2)
	@Column(name = "estado", nullable = false)
	private String estado;

	@Column(name = "fecha_atencion", nullable = true)
	private Date fechaAtencion;

	@Column(name = "hora", nullable = false)
	private Date hora;

	@Column(name = "hora_espera", nullable = false)
	private Date horaEspera;

	@Size(max = 255)
	@Column(name = "motivo", nullable = true)
	private String motivo;

	@Size(max = 2)
	@Column(name = "estado_atencion", nullable = false)
	private String estadoAtencion; // AC=NUEVO,PR=ATENDIDO, NA=NO ATENDIDO

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_individuo", nullable = false)
	private Paciente paciente;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_personal", nullable = false)
	private Personal personal;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "id_orden_servicio", nullable = true, insertable = true, updatable = true)
	private DesgOrdenServicio ordenServicio;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_sucursal", nullable = false)
	private Sucursal sucursal;

	@Column(name = "fecha_modificacion", nullable = true)
	private Date fechaModificacion;

	@Column(name = "fecha_registro", nullable = false)
	private Date fechaRegistro;
	@Column(name = "usuario_registro", nullable = false)
	private String usuarioRegistro;
	private static final long serialVersionUID = 1L;

	public Cita() {
		super();
		this.id = 0;
		this.clase = "";
		this.motivo = "";
		this.estadoAtencion = "AC";
		this.clase = "";
		this.estado = "AC";
		this.paciente = new Paciente();
		this.personal = new Personal();
		this.sucursal = new Sucursal();
		this.usuarioRegistro = "";
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getClase() {
		return this.clase;
	}

	public void setClase(String clase) {
		this.clase = clase;
	}

	public Date getFechaAtencion() {
		return this.fechaAtencion;
	}

	public void setFechaAtencion(Date fechaAtencion) {
		this.fechaAtencion = fechaAtencion;
	}

	public Date getHora() {
		return this.hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	public String getMotivo() {
		return this.motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public Paciente getIndividuo() {
		return paciente;
	}

	public void setIndividuo(Paciente paciente) {
		this.paciente = paciente;
	}

	public Personal getPersonal() {
		return personal;
	}

	public void setPersonal(Personal personal) {
		this.personal = personal;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public Date getFechaModificacion() {
		return fechaAtencion;
	}

	public void setFechaModificacion(Date fecha_modificacion) {
		this.fechaAtencion = fecha_modificacion;
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

	public DesgOrdenServicio getOrdenServicio() {
		return ordenServicio;
	}

	public void setOrdenServicio(DesgOrdenServicio ordenServicio) {
		this.ordenServicio = ordenServicio;
	}

	@Override
	public String toString() {
		return getFechaAtencion().toString();
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
			if (!(obj instanceof Cita)) {
				return false;
			} else {
				if (((Cita) obj).id == this.id) {
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
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado
	 *            the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * @return the estadoAtencion
	 */
	public String getEstadoAtencion() {
		return estadoAtencion;
	}

	/**
	 * @param estadoAtencion
	 *            the estadoAtencion to set
	 */
	public void setEstadoAtencion(String estadoAtencion) {
		this.estadoAtencion = estadoAtencion;
	}

	/**
	 * @return the horaEspera
	 */
	public Date getHoraEspera() {
		return horaEspera;
	}

	/**
	 * @param horaEspera
	 *            the horaEspera to set
	 */
	public void setHoraEspera(Date horaEspera) {
		this.horaEspera = horaEspera;
	}

}
