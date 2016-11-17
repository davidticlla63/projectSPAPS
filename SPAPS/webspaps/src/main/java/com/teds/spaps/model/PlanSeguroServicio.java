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
 * Entity implementation class for Entity: DetalleCobertura
 *
 */
@Entity
@Table(name = "planseguro_servicio", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class PlanSeguroServicio implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6545893163483962953L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_cobertura", nullable = false)
	private Servicio servicio;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_plan_seguro", nullable = false)
	private PlanSeguro planSeguro;
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

	public PlanSeguroServicio() {
		super();
		this.id = 0;
		this.servicio = new Servicio();
		this.planSeguro = new PlanSeguro();
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

	public Servicio getCobertura() {
		return servicio;
	}

	public void setCobertura(Servicio servicio) {
		this.servicio = servicio;
	}

	public PlanSeguro getPlanSeguro() {
		return planSeguro;
	}

	public void setPlanSeguro(PlanSeguro planSeguro) {
		this.planSeguro = planSeguro;
	}

	@Override
	public String toString() {
		return "Servicio = " + this.servicio.getDescripcion()
				+ ", Plan de Seguro = " + this.planSeguro.getDescripcion();
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
			if (!(obj instanceof PlanSeguroServicio)) {
				return false;
			} else {
				if (((PlanSeguroServicio) obj).id == this.id
						&& ((PlanSeguroServicio) obj).servicio
								.equals(this.servicio)
						&& ((PlanSeguroServicio) obj).planSeguro
								.equals(this.planSeguro)) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

}