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
 * 
 * @author mauriciobejaranorivera
 *
 */
@Entity
@Table(name = "venta_grupo_servicio", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = {
		"id", "id_compania" }))
public class VentaGrupoServicios implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Size(max = 255)
	@Column(name = "descripcion", nullable = false)
	private String descripcion;
	
	// AC , IN , RM
	@Size(max = 2)
	@Column(name = "estado", nullable = false)
	private String estado;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_compania", nullable = false)
	private Compania compania;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_sucursal", nullable = false)
	private Sucursal sucursal;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_cuenta_ingreso", nullable=false)
	private PlanCuenta cuentaIngreso;
	
	@Column(name = "fecha_modificacion", nullable = true)
	private Date fechaModificacion;
	
	@Column(name = "fecha_registro", nullable = false)
	private Date fechaRegistro;
	
	@Column(name = "usuario_registro", nullable = false)
	private String usuarioRegistro;

	public VentaGrupoServicios() {
		super();
		this.id = 0;
		this.descripcion = "";
		this.estado = "AC";
		this.cuentaIngreso = new PlanCuenta();
		this.compania = new Compania();
		this.sucursal = new Sucursal();
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
			if (!(obj instanceof VentaGrupoServicios)) {
				return false;
			} else {
				if (((VentaGrupoServicios) obj).id == this.id) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

	public PlanCuenta getCuentaIngreso() {
		return cuentaIngreso;
	}

	public void setCuentaIngreso(PlanCuenta cuentaIngreso) {
		this.cuentaIngreso = cuentaIngreso;
	}

}
