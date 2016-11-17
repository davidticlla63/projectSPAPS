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
 * Entity implementation class for Entity: Cargo
 *
 */
@Entity
@Table(name = "lab_orden_sub_detalle_imag", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class LabOrdenSubDetalleImag implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Size(max = 255)
	@Column(name = "descripcion", nullable = false)
	private String descripcion;

	@Size(max = 255)
	@Column(name = "parametros", nullable = false)
	private String parametros;

	@Column(name = "resultado", nullable = true, length = 255)
	private String resultado;
	
	private boolean completado=false;

	@Column(name = "observacion", nullable = true, length = 255)
	private String observacion;

	@Column(name = "label", nullable = false)
	private boolean label = false;

	@Column(name = "unidad_medida", nullable = false)
	private String unidaMedida;

	@Size(max = 2)
	@Column(name = "estado", nullable = false)
	// AC , IN , RM
	private String estado;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_orden", nullable = false)
	private LabOrdenImag orden;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_compania", nullable = false)
	private Compania compania;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_sucursal", nullable = false)
	private Sucursal sucursal;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_orden_detalle", nullable = false)
	private LabOrdenDetalleImag ordenDetalle;

	@Column(name = "fecha_modificacion", nullable = true)
	private Date fechaModificacion;
	@Column(name = "fecha_registro", nullable = false)
	private Date fechaRegistro;
	@Column(name = "usuario_registro", nullable = false)
	private String usuarioRegistro;
	private static final long serialVersionUID = 1L;

	public LabOrdenSubDetalleImag() {
		super();
		this.id = 0;
		this.descripcion = "";
		this.estado = "AC";
		this.usuarioRegistro = "";
		this.fechaRegistro = new Date();
		this.label = false;
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
		return getDescripcion();
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
			if (!(obj instanceof LabOrdenSubDetalleImag)) {
				return false;
			} else {
				if (((LabOrdenSubDetalleImag) obj).id == this.id) {
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
	 * @return the ordenDetalle
	 */
	public LabOrdenDetalleImag getOrdenDetalle() {
		return ordenDetalle;
	}

	/**
	 * @param ordenDetalle
	 *            the ordenDetalle to set
	 */
	public void setOrdenDetalle(LabOrdenDetalleImag ordenDetalle) {
		this.ordenDetalle = ordenDetalle;
	}

	/**
	 * @return the resultado
	 */
	public String getResultado() {
		return resultado;
	}

	/**
	 * @param resultado
	 *            the resultado to set
	 */
	public void setResultado(String resultado) {
		this.resultado = resultado;
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
	 * @return the label
	 */
	public boolean isLabel() {
		return label;
	}

	/**
	 * @param label
	 *            the label to set
	 */
	public void setLabel(boolean label) {
		this.label = label;
	}

	/**
	 * @return the unidaMedida
	 */
	public String getUnidaMedida() {
		return unidaMedida;
	}

	/**
	 * @param unidaMedida
	 *            the unidaMedida to set
	 */
	public void setUnidaMedida(String unidaMedida) {
		this.unidaMedida = unidaMedida;
	}

	/**
	 * @return the orden
	 */
	public LabOrdenImag getOrden() {
		return orden;
	}

	/**
	 * @param orden
	 *            the orden to set
	 */
	public void setOrden(LabOrdenImag orden) {
		this.orden = orden;
	}
	
	/**
	 * @return the completado
	 */
	public boolean isCompletado() {
		if (this.resultado==null) {
			return false;
		}
		this.completado = this.resultado.trim().length() > 0;
		return completado;
	}

	/**
	 * @param completado the completado to set
	 */
	public void setCompletado(boolean completado) {
		this.completado = completado;
	}

}
