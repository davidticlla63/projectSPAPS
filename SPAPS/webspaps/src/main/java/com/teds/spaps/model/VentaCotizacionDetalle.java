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
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

/**
 * Entity implementation class for Entity: Cargo
 *
 */
@Entity
@Table(name = "venta_cotizacion_detalle", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class VentaCotizacionDetalle implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Size(max = 2)
	@Column(name = "estado", nullable = true)
	/*
	 * pendiente = PE aprobado = AP vigente = VI atrasado = AT procesado = PR
	 */
	private String estado;

	private double cantidad = 1;

	@Column(name = "precio_unitario", nullable = false)
	private double precioUnitario = 0;

	@Column(name = "precio_sub_total", nullable = false)
	private double precioSubTotal = 0;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cotizacion")
	private VentaCotizacion cotizacion;

	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_servicio", nullable = false)
	private VentaServicios servicio;
	
	@Column(name = "fecha_modificacion", nullable = true)
	private Date fechaModificacion;
	@Column(name = "fecha_registro", nullable = true)
	private Date fechaRegistro;
	@Column(name = "usuario_registro", nullable = true)
	private String usuarioRegistro;
	private static final long serialVersionUID = 1L;
	@Transient
	private Integer idcorrelativo;
	public VentaCotizacionDetalle() {
		super();
		this.id = 0;
		this.estado = "AC";
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
		return "VentaCotizacionDetalle [id=" + id + ", estado=" + estado
				+ ", cantidad=" + cantidad + ", precioUnitario="
				+ precioUnitario + ", precioSubTotal=" + precioSubTotal
				+ ", cotizacion=" + cotizacion + ", servicio=" + servicio
				+ ", fechaModificacion=" + fechaModificacion
				+ ", fechaRegistro=" + fechaRegistro + ", usuarioRegistro="
				+ usuarioRegistro + "]";
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
			if (!(obj instanceof VentaCotizacionDetalle)) {
				return false;
			} else {
				if (((VentaCotizacionDetalle) obj).id == this.id) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

	/**
	 * @return the precioSubTotal
	 */
	public double getPrecioSubTotal() {
		this.precioSubTotal = this.cantidad * this.precioUnitario;
		return precioSubTotal;
	}

	/**
	 * @param precioSubTotal
	 *            the precioSubTotal to set
	 */
	public void setPrecioSubTotal(double precioSubTotal) {
		this.precioSubTotal = precioSubTotal;
	}

	/**
	 * @return the precioUnitario
	 */
	public double getPrecioUnitario() {

		return precioUnitario;
	}

	/**
	 * @param precioUnitario
	 *            the precioUnitario to set
	 */
	public void setPrecioUnitario(double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	/**
	 * @return the cotizacion
	 */
	public VentaCotizacion getCotizacion() {
		return cotizacion;
	}

	/**
	 * @param cotizacion
	 *            the cotizacion to set
	 */
	public void setCotizacion(VentaCotizacion cotizacion) {
		this.cotizacion = cotizacion;
	}

	/**
	 * @return the concepto
	 */
	

	/**
	 * @return the cantidad
	 */
	public double getCantidad() {
		return cantidad;
	}

	/**
	 * @param cantidad
	 *            the cantidad to set
	 */
	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	public VentaServicios getServicio() {
		return servicio;
	}

	public void setServicio(VentaServicios servicio) {
		this.servicio = servicio;
	}

	public Integer getIdcorrelativo() {
		return idcorrelativo;
	}

	public void setIdcorrelativo(Integer idcorrelativo) {
		this.idcorrelativo = idcorrelativo;
	}

}
