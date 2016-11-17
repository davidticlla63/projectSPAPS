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
@Table(name = "venta_servicios", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class VentaServicios implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Size(max = 255)
	@Column(name = "descripcion", nullable = false)
	private String descripcion;

	@Column(name = "precio_venta", nullable = false)
	private double precioVenta=0;
	
	@Column(name = "precio_venta_2", nullable = true)
	private double precioVenta2;
	
	@Column(name = "precio_venta_3", nullable = true)
	private double precioVenta3;

	@Column(name = "visualizar", nullable = true)
	private boolean visualizar = false;

	private boolean checker = false;

	@Size(max = 2)
	@Column(name = "estado", nullable = true)
	// AC , IN , RM
	private String estado;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_compania", nullable = false)
	private Compania compania;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_sucursal", nullable = false)
	private Sucursal sucursal;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "id_grupo_servicio", nullable = true)
	private VentaGrupoServicios grupoServicios;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "id_tipo_servicio", nullable = true)
	private VentaTipoServicios tipoServicios;

	@Column(name = "fecha_modificacion", nullable = true)
	private Date fechaModificacion;

	@Column(name = "fecha_registro", nullable = true)
	private Date fechaRegistro;
	@Column(name = "usuario_registro", nullable = false)
	private String usuarioRegistro;
	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "servicios", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<VentaServiciosSeguros> listServiciosSeguros = new ArrayList<VentaServiciosSeguros>();

	@OneToMany(mappedBy = "servicios", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<VentaServiciosEmpresa> listServiciosEmpresas = new ArrayList<VentaServiciosEmpresa>();

	@OneToMany(mappedBy = "servicio", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ServicioExamen> listServicioExamen = new ArrayList<>();
	public VentaServicios() {
		super();
		this.id = 0;
		this.descripcion = "";
		this.estado = "AC";		
		this.usuarioRegistro = "";
	}
	
	public VentaServicios(Integer id, String descripcion,Double precio){
		
		this.id=id;
		this.descripcion=descripcion;
		this.precioVenta=precio;
		
	}
	
	public VentaServicios(Integer id, String descripcion,Double precio,VentaGrupoServicios ventaGrupoServicios){
		
		this.id=id;
		this.descripcion=descripcion;
		this.precioVenta=precio;
		this.grupoServicios  = ventaGrupoServicios;
		
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
		return "VentaServicios [id=" + id + ", descripcion=" + descripcion
				+ ", precioVenta=" + precioVenta + ", precioVenta2="
				+ precioVenta2 + ", precioVenta3=" + precioVenta3
				+ ", visualizar=" + visualizar + ", checker=" + checker
				+ ", estado=" + estado + ", fechaModificacion="
				+ fechaModificacion + ", fechaRegistro=" + fechaRegistro
				+ ", usuarioRegistro=" + usuarioRegistro + ", grupoServicios="+grupoServicios+"]";
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
			if (!(obj instanceof VentaServicios)) {
				return false;
			} else {
				if (((VentaServicios) obj).id == this.id) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

	/**
	 * @return the visualizar
	 */
	public boolean isVisualizar() {
		return visualizar;
	}

	/**
	 * @param visualizar
	 *            the visualizar to set
	 */
	public void setVisualizar(boolean visualizar) {
		this.visualizar = visualizar;
	}

	/**
	 * @return the checker
	 */
	public boolean isChecker() {
		return checker;
	}

	/**
	 * @param checker
	 *            the checker to set
	 */
	public void setChecker(boolean checker) {
		this.checker = checker;
	}

	/**
	 * @return the grupoServicios
	 */
	public VentaGrupoServicios getGrupoServicios() {
		return grupoServicios;
	}

	/**
	 * @param grupoServicios
	 *            the grupoServicios to set
	 */
	public void setGrupoServicios(VentaGrupoServicios grupoServicios) {
		this.grupoServicios = grupoServicios;
	}

	/**
	 * @return the tipoServicios
	 */
	public VentaTipoServicios getTipoServicios() {
		return tipoServicios;
	}

	/**
	 * @param tipoServicios
	 *            the tipoServicios to set
	 */
	public void setTipoServicios(VentaTipoServicios tipoServicios) {
		this.tipoServicios = tipoServicios;
	}

	/**
	 * @return the precioVenta
	 */
	public double getPrecioVenta() {
		return precioVenta;
	}

	/**
	 * @param precioVenta
	 *            the precioVenta to set
	 */
	public void setPrecioVenta(double precioVenta) {
		this.precioVenta = precioVenta;
	}

	/**
	 * @return the precioVenta2
	 */
	public double getPrecioVenta2() {
		return precioVenta2;
	}

	/**
	 * @param precioVenta2
	 *            the precioVenta2 to set
	 */
	public void setPrecioVenta2(double precioVenta2) {
		this.precioVenta2 = precioVenta2;
	}

	/**
	 * @return the precioVenta3
	 */
	public double getPrecioVenta3() {
		return precioVenta3;
	}

	/**
	 * @param precioVenta3
	 *            the precioVenta3 to set
	 */
	public void setPrecioVenta3(double precioVenta3) {
		this.precioVenta3 = precioVenta3;
	}

	/**
	 * @return the listServiciosSeguros
	 */
	public List<VentaServiciosSeguros> getListServiciosSeguros() {
		return listServiciosSeguros;
	}

	/**
	 * @param listServiciosSeguros
	 *            the listServiciosSeguros to set
	 */
	public void setListServiciosSeguros(
			List<VentaServiciosSeguros> listServiciosSeguros) {
		this.listServiciosSeguros = listServiciosSeguros;
	}

	/**
	 * @return the listServiciosEmpresas
	 */
	public List<VentaServiciosEmpresa> getListServiciosEmpresas() {
		return listServiciosEmpresas;
	}

	/**
	 * @param listServiciosEmpresas
	 *            the listServiciosEmpresas to set
	 */
	public void setListServiciosEmpresas(
			List<VentaServiciosEmpresa> listServiciosEmpresas) {
		this.listServiciosEmpresas = listServiciosEmpresas;
	}

}
