package com.teds.spaps.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

/**
 * 
 * @author mauriciobejaranorivera
 *
 */
@Entity
@Table(name = "compania", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = {
		"id", "nombre" }))
public class Compania implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Size(max = 255)
	@Column(name = "nombre", nullable = false)
	private String nombre;

	@Size(max = 255)
	@Column(name = "descripcion", nullable = false)
	private String descripcion;

	@Size(max = 255)
	@Column(name = "direccion", nullable = false)
	private String direccion;

	@Size(max = 30)
	@Column(name = "nit", nullable = false)
	private String nit;

	@Size(max = 255)
	@Column(name = "telefono", nullable = false)
	private String telefono;

	@Size(max = 2)// P=Principal , N=Normal
	@Column(name = "tipo", nullable = false)
	private String tipo;

	// REPRESENTANTE
	@Size(max = 255)
	@Column(name = "ci_representante", nullable = false)
	private String ciRepresentante;

	@Size(max = 255)
	@Column(name = "nombre_representante", nullable = false)
	private String nombreRepresentante;

	@Size(max = 255)
	@Column(name = "telefono_representante", nullable = false)
	private String telefonoRepresentante;

	@Column(name = "logo", nullable = true)
	private byte[] logo;
	
	//aqui el listado que apunta a sucursal
	@OneToMany(mappedBy="compania", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<Sucursal> listSucursal;

	@Size(max = 2)// AC , IN , RM
	@Column(name = "estado", nullable = false)
	private String estado;

	@Column(name = "fecha_modificacion", nullable = true)
	private Date fechaModificacion;

	@Column(name = "fecha_registro", nullable = false)
	private Date fechaRegistro;

	@Column(name = "usuario_registro", nullable = false)
	private String usuarioRegistro;

	public Compania() {
		super();
		this.id = 0;
		this.descripcion = "";
		this.direccion = "";
		this.nit = "";
		this.telefono = "";
		this.estado = "AC";
		this.usuarioRegistro = "";
		this.tipo = "N";
	}

	@Override
	public String toString() {
		return descripcion;
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
			if (!(obj instanceof Compania)) {
				return false;
			} else {
				if (((Compania) obj).id == this.id) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getNit() {
		return this.nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
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

	public byte[] getLogo() {
		return logo;
	}

	public void setLogo(byte[] logo) {
		this.logo = logo;
	}

	public String getNombreRepresentante() {
		return nombreRepresentante;
	}

	public void setNombreRepresentante(String nombreRepresentante) {
		this.nombreRepresentante = nombreRepresentante;
	}

	public String getTelefonoRepresentante() {
		return telefonoRepresentante;
	}

	public void setTelefonoRepresentante(String telefonoRepresentante) {
		this.telefonoRepresentante = telefonoRepresentante;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCiRepresentante() {
		return ciRepresentante;
	}

	public void setCiRepresentante(String ciRepresentante) {
		this.ciRepresentante = ciRepresentante;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public List<Sucursal> getListSucursal() {
		return listSucursal;
	}

	public void setListSucursal(List<Sucursal> listSucursal) {
		this.listSucursal = listSucursal;
	}

}
