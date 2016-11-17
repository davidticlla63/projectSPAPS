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
@Table(name = "venta_cotizacion", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class VentaCotizacion implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String codigo;

	@Size(max = 255)
	@Column(name = "observacion", nullable = true)
	private String observacion;
	
	@Size(max = 255)
	@Column(name = "persona", nullable = true)
	private String persona;

	@Size(max = 2)
	@Column(name = "estado", nullable = true)
	/*
	 * pendiente = PE aprobado = AP vigente = VI atrasado = AT procesado = PR
	 */
	private String estado;
	
	@Column(name = "precio_total_bs", nullable = true)
	private double precioTotalBs=0;
	
	@Column(name = "precio_total_us", nullable = true)
	private double precioTotalUs=0;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_compania", nullable = false)
	private Compania compania;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "id_paciente", nullable = true)
	private Paciente paciente;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "id_sucursal", nullable = true)
	private Sucursal sucursal;

	@Column(name = "fecha_modificacion", nullable = true)
	private Date fechaModificacion;

	@Column(name = "fecha_registro", nullable = false)
	private Date fechaRegistro;

	@Column(name = "usuario_registro", nullable = false)
	private String usuarioRegistro;

	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "cotizacion", orphanRemoval=true,cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<VentaCotizacionDetalle> listCotizacionDetalles = new ArrayList<>();
	
	

	public VentaCotizacion() {
		super();
		this.id = 0;
		this.observacion = "ninguna";
		this.estado = "AC";
		this.usuarioRegistro = "";
		this.codigo="Generado por el Sistema";
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


	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	

	
	
	@Override
	public String toString() {
		return "VentaCotizacion [id=" + id + ", codigo=" + codigo
				+ ", observacion=" + observacion + ", persona=" + persona
				+ ", estado=" + estado + ", precioTotalBs=" + precioTotalBs
				+ ", precioTotalUs=" + precioTotalUs + ", compania=" + compania
				+ ", paciente=" + paciente + ", sucursal=" + sucursal
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
			if (!(obj instanceof VentaCotizacion)) {
				return false;
			} else {
				if (((VentaCotizacion) obj).id == this.id) {
					return true;
				} else {
					return false;
				}
			}
		}
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
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the listCotizacionDetalles
	 */
	public List<VentaCotizacionDetalle> getListCotizacionDetalles() {
		return listCotizacionDetalles;
	}

	/**
	 * @param listCotizacionDetalles
	 *            the listCotizacionDetalles to set
	 */
	public void setListCotizacionDetalles(
			List<VentaCotizacionDetalle> listCotizacionDetalles) {
		this.listCotizacionDetalles = listCotizacionDetalles;
	}
	public void addDetalle(VentaCotizacionDetalle detalle){
		listCotizacionDetalles.add(detalle);
		detalle.setCotizacion(this);
	}
	/**
	 * @return the precioTotalBs
	 */
	public double getPrecioTotalBs() {
		return precioTotalBs;
	}

	/**
	 * @param precioTotalBs
	 *            the precioTotalBs to set
	 */
	public void setPrecioTotalBs(double precioTotalBs) {
		this.precioTotalBs = precioTotalBs;
	}

	public String getPersona() {
		return persona;
	}

	public void setPersona(String persona) {
		this.persona = persona;
	}

	public double getPrecioTotalUs() {
		return precioTotalUs;
	}

	public void setPrecioTotalUs(double precioTotalUs) {
		this.precioTotalUs = precioTotalUs;
	}

	/**
	 * @return the precioVentaUs
	 */
	
}
