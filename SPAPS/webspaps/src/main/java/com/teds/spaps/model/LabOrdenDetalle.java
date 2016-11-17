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
@Table(name = "lab_orden_detalle", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class LabOrdenDetalle implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Size(max = 255)
	@Column(name = "descripcion", nullable = false)
	private String descripcion;

	@Column(name = "observacion", nullable = true)
	private String observacion;

	@Size(max = 255)
	@Column(name = "paramentros", nullable = false)
	private String parametros;

	private int porcentaje = 0;

	@Size(max = 2)
	@Column(name = "estado", nullable = false)
	/*
	 * pendiente = PE aprobado = AP vigente = VI atrasado = AT procesado = PR
	 */
	private String estado;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_compania", nullable = false)
	private Compania compania;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_sucursal", nullable = false)
	private Sucursal sucursal;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_examen", nullable = false)
	private LabExamen labExamen;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_orden", nullable = false)
	private LabOrden orden;

	@Column(name = "fecha_modificacion", nullable = true)
	private Date fechaModificacion;
	@Column(name = "fecha_registro", nullable = false)
	private Date fechaRegistro;
	@Column(name = "usuario_registro", nullable = false)
	private String usuarioRegistro;
	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "ordenDetalle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<LabOrdenSubDetalle> listOrdenSubDetalle = new ArrayList<LabOrdenSubDetalle>();

	public LabOrdenDetalle() {
		super();
		this.id = 0;
		this.descripcion = "";
		this.estado = "AC";
		this.usuarioRegistro = "";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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
		return "LabOrdenDetalle [id=" + id + ", descripcion=" + descripcion
				+ ", labExamen=" + labExamen + ", orden=" + orden + "]";
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
			if (!(obj instanceof LabOrdenDetalle)) {
				return false;
			} else {
				if (((LabOrdenDetalle) obj).id == this.id) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

	/**
	 * @return the parametros
	 */
	public String getParametros() {
		return parametros;
	}

	/**
	 * @param parametros
	 *            the parametros to set
	 */
	public void setParametros(String parametros) {
		this.parametros = parametros;
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
	 * @return the labExamen
	 */
	public LabExamen getLabExamen() {
		return labExamen;
	}

	/**
	 * @param labExamen
	 *            the labExamen to set
	 */
	public void setLabExamen(LabExamen labExamen) {
		this.labExamen = labExamen;
	}

	/**
	 * @return the listOrdenSubDetalle
	 */
	public List<LabOrdenSubDetalle> getListOrdenSubDetalle() {
		return listOrdenSubDetalle;
	}

	/**
	 * @param listOrdenSubDetalle
	 *            the listOrdenSubDetalle to set
	 */
	public void setListOrdenSubDetalle(
			List<LabOrdenSubDetalle> listOrdenSubDetalle) {
		this.listOrdenSubDetalle = listOrdenSubDetalle;
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
	 * @return the porcentaje
	 */
	public int getPorcentaje() {
		int cant = 0;
		for (LabOrdenSubDetalle labOrdenSubDetalle : listOrdenSubDetalle) {
			if (labOrdenSubDetalle.isCompletado()) {
				cant++;
			}
		}
		if (cant>0) {
			porcentaje = (cant * 100) / listOrdenSubDetalle.size();			
		}else{
			porcentaje=0;
		}
		return porcentaje;
	}

	/**
	 * @param porcentaje
	 *            the porcentaje to set
	 */
	public void setPorcentaje(int porcentaje) {
		this.porcentaje = porcentaje;
	}

}
