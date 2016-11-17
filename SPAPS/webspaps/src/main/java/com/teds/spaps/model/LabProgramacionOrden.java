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
 * Entity implementation class for Entity: Cargo
 *
 */
@Entity
@Table(name = "lab_programacion_orden", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = {
		"id", "id_sucursal" }))
public class LabProgramacionOrden implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Size(max = 255)
	@Column(name = "observacion", nullable = true)
	private String observacion;

	@Column(name = "especialidad", nullable = false, length = 100)
	private String especialidad;

	@Column(name = "fecha", nullable = false)
	private Date fecha;

	@Size(max = 2)
	@Column(name = "estado", nullable = false, insertable = true, updatable = true)
	/*
	 * pendiente = PE aprobado = AP vigente = VI atrasado = AT procesado = PR
	 */
	private String estado;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "id_medico", nullable = true, insertable = true, updatable = true)
	private Personal medico;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "id_orden", nullable = true)
	private LabOrden orden;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "id_orden_img", nullable = true)
	private LabOrdenImag ordenImag;

	@Column(name = "tipo_orden", nullable = false)
	private String tipoOrden;// LABORATORIO,IMAGENOLOGIA

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

	@OneToMany(mappedBy = "programacionOrden", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<LabMuestras> listMuestras = new ArrayList<LabMuestras>();

	public LabProgramacionOrden() {
		super();
		this.id = 0;
		this.observacion = "";
		this.estado = "AC";
		this.sucursal = new Sucursal();
		this.fecha = new Date();
		this.usuarioRegistro = "";
	}

	public Integer getId() {
		return id;
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

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
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
			if (!(obj instanceof LabProgramacionOrden)) {
				return false;
			} else {
				if (((LabProgramacionOrden) obj).id == this.id) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

	/**
	 * @return the medico
	 */
	public Personal getMedico() {
		return medico;
	}

	/**
	 * @param medico
	 *            the medico to set
	 */
	public void setMedico(Personal medico) {
		this.medico = medico;
	}

	/**
	 * @return the especialidad
	 */
	public String getEspecialidad() {
		return especialidad;
	}

	/**
	 * @param especialidad
	 *            the especialidad to set
	 */
	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}

	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha
	 *            the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the observacion
	 */
	public String getObservacion() {
		return observacion;
	}

	/**
	 * @param observacion
	 *            the observacion to set
	 */
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	/**
	 * @return the orden
	 */
	public LabOrden getOrden() {
		return orden;
	}

	/**
	 * @param orden
	 *            the orden to set
	 */
	public void setOrden(LabOrden orden) {
		this.orden = orden;
	}

	/**
	 * @return the listMuestras
	 */
	public List<LabMuestras> getListMuestras() {
		return listMuestras;
	}

	/**
	 * @param listMuestras
	 *            the listMuestras to set
	 */
	public void setListMuestras(List<LabMuestras> listMuestras) {
		this.listMuestras = listMuestras;
	}

	/**
	 * @return the tipoOrden
	 */
	public String getTipoOrden() {
		return tipoOrden;
	}

	/**
	 * @param tipoOrden
	 *            the tipoOrden to set
	 */
	public void setTipoOrden(String tipoOrden) {
		this.tipoOrden = tipoOrden;
	}

	/**
	 * @return the ordenImag
	 */
	public LabOrdenImag getOrdenImag() {
		return ordenImag;
	}

	/**
	 * @param ordenImag
	 *            the ordenImag to set
	 */
	public void setOrdenImag(LabOrdenImag ordenImag) {
		this.ordenImag = ordenImag;
	}

}
