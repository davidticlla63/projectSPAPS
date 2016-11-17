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
 * Entity implementation class for Entity: Consulta
 *
 */
@Entity
@Table(name = "consulta", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Consulta implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3055370135929927764L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Size(max = 2)
	@Column(name = "tipo_consulta", nullable = false)
	private String tipoConsulta;
	@Column(name = "reconsulta", nullable = false)
	private Integer reconsulta;
	@Size(max = 2)
	@Column(name = "estado", nullable = false)
	/*
	 * activa actual = AT activa = AC inactiva = IN eliminada = RM
	 */
	private String estado;
	@Size(max = 1)
	// particular = P, seguro = S, empresa = E, desgravamen = D
	@Column(name = "tipo_atencion", nullable = false)
	private String tipoAtencion;
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "id_seguro", nullable = true, insertable = true, updatable = true)
	private PlanSeguro planSeguro;
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "id_empresa", nullable = true, insertable = true, updatable = true)
	private Compania empresa;
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "id_orden_servicio", nullable = true, insertable = true, updatable = true)
	private DesgOrdenServicio ordenServicio;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_paciente", nullable = false)
	private Paciente paciente;
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

	public Consulta() {
		super();
		this.id = 0;
		this.tipoConsulta = "";
		this.paciente = new Paciente();
		this.estado = "AC";
		this.compania = new Compania();
		this.sucursal = new Sucursal();
		this.usuarioRegistro = "";
		this.reconsulta = 0;
		this.planSeguro = null;
		this.empresa = null;
		this.ordenServicio = null;
		this.tipoAtencion = "";
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
	 * @return the reconsulta
	 */
	public Integer getReconsulta() {
		return reconsulta;
	}

	/**
	 * @param reconsulta
	 *            the reconsulta to set
	 */
	public void setReconsulta(Integer reconsulta) {
		this.reconsulta = reconsulta;
	}

	/**
	 * @return the paciente
	 */
	public Paciente getPaciente() {
		return paciente;
	}

	/**
	 * @param paciente
	 *            the paciente to set
	 */
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	/**
	 * @return the tipoConsulta
	 */
	public String getTipoConsulta() {
		return tipoConsulta;
	}

	/**
	 * @param tipoConsulta
	 *            the tipoConsulta to set
	 */
	public void setTipoConsulta(String tipoConsulta) {
		this.tipoConsulta = tipoConsulta;
	}

	public String getTipoAtencion() {
		return tipoAtencion;
	}

	public PlanSeguro getPlanSeguro() {
		return planSeguro;
	}

	public void setTipoAtencion(String tipoAtencion) {
		this.tipoAtencion = tipoAtencion;
	}

	public void setPlanSeguro(PlanSeguro planSeguro) {
		this.planSeguro = planSeguro;
	}

	public Compania getEmpresa() {
		return empresa;
	}

	public DesgOrdenServicio getOrdenServicio() {
		return ordenServicio;
	}

	public void setEmpresa(Compania empresa) {
		this.empresa = empresa;
	}

	public void setOrdenServicio(DesgOrdenServicio ordenServicio) {
		this.ordenServicio = ordenServicio;
	}

	@Override
	public String toString() {
		return this.id.toString();
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
			if (!(obj instanceof Consulta)) {
				return false;
			} else {
				if (((Consulta) obj).id == this.id) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

}
