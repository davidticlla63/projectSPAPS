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
 * 
 * @author mauriciobejaranorivera
 *
 */
@Entity
@Table(name = "sucursal", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = {
		"id", "nombre", "id_compania" }))
public class Sucursal implements Serializable {

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
	@Column(name = "direccion", nullable = true)
	private String direccion;

	@Size(max = 255)
	@Column(name = "telefono", nullable = true)
	private String telefono;

	@Size(max = 2)
	// P=Principal , N=Normal
	@Column(name = "tipo", nullable = false)
	private String tipo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_compania", nullable = false)
	private Compania compania;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_ciudad", nullable = false)
	private Ciudad ciudad;

	@Size(max = 2)
	// AC , IN , RM
	@Column(name = "estado", nullable = false)
	private String estado;

	@Column(name = "fecha_modificacion", nullable = true)
	private Date fechaModificacion;

	@Column(name = "fecha_registro", nullable = false)
	private Date fechaRegistro;

	@Column(name = "usuario_registro", nullable = false)
	private String usuarioRegistro;
	
	@OneToMany(mappedBy = "sucursal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<UsuarioSucursal> listUsuarioSucursals= new ArrayList<UsuarioSucursal>();

	public Sucursal() {
		super();
		this.id = 0;
		this.descripcion = "";
		this.direccion = "";
		this.telefono = "";
		this.estado = "AC";
		this.compania = new Compania();
		this.ciudad = new Ciudad();
		this.usuarioRegistro = "";
	}

	@Override
	public String toString() {
		return this.nombre;
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
			if (!(obj instanceof Sucursal)) {
				return false;
			} else {
				if (((Sucursal) obj).id == this.id) {
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

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Compania getCompania() {
		return compania;
	}

	public String getEstado() {
		return estado;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public String getUsuarioRegistro() {
		return usuarioRegistro;
	}

	public void setCompania(Compania compania) {
		this.compania = compania;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public void setUsuarioRegistro(String usuarioRegistro) {
		this.usuarioRegistro = usuarioRegistro;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the ciudad
	 */
	public Ciudad getCiudad() {
		return ciudad;
	}

	/**
	 * @param ciudad
	 *            the ciudad to set
	 */
	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}

	/**
	 * @return the listUsuarioSucursals
	 */
	public List<UsuarioSucursal> getListUsuarioSucursals() {
		return listUsuarioSucursals;
	}

	/**
	 * @param listUsuarioSucursals the listUsuarioSucursals to set
	 */
	public void setListUsuarioSucursals(List<UsuarioSucursal> listUsuarioSucursals) {
		this.listUsuarioSucursals = listUsuarioSucursals;
	}

}
