package com.teds.spaps.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

/**
 * This class belongs to the security module.
 * 
 * @author mauriciobejaranorivera
 *
 */
@Entity
@Table(name = "usuario", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "login"))
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Size(max = 255)
	@Column(name = "nombre", nullable = false)
	private String nombre;

	@Size(max = 200)
	@Column(name = "email", nullable = false)
	private String email;

	@Size(max = 10)
	@Column(name = "login", nullable = false)
	private String login;

	@Size(max = 255)
	@Column(name = "password", nullable = false)
	private String password;

	@Size(max = 10)
	@Column(name = "tipo", nullable = false)
	private String tipo;

	@Size(max = 255)
	@Column(name = "pagina_inicio", nullable = false)
	private String paginaInicio;

	@Column(name = "peso_foto", nullable = true)
	private int pesoFoto;

	@Size(max = 2)
	@Column(name = "estado", nullable = false)
	private String estado;// AC , IN , RM

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_rol", nullable = false)
	private Rol rol;

	@Column(name = "fecha_modificacion", nullable = true)
	private Date fechaModificacion;

	@Column(name = "fecha_registro", nullable = false)
	private Date fechaRegistro;

	@Column(name = "usuario_registro", nullable = true)
	private String usuarioRegistro;

	@Column(name = "foto_perfil", nullable = true)
	private byte[] fotoPerfil;
	
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<UsuarioSucursal> listUsuarioSucursals= new ArrayList<UsuarioSucursal>();

	public Usuario() {
		super();
		this.id = 0;
		this.nombre = "";
		this.email = "";
		this.login = "";
		this.password = "";
		this.tipo = "";
		//this.paginaInicio = "/pages/dashboard.xhtml";		
		this.paginaInicio = "/pages/config/compania/index.xhtml";
		this.estado = "PN";
		this.rol = new Rol();
		this.usuarioRegistro = "";
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", email=" + email
				+ ", login=" + login + ", password=" + password + ", tipo="
				+ tipo + ", paginaInicio=" + paginaInicio + ", pesoFoto="
				+ pesoFoto + ", estado=" + estado + ", rol=" + rol
				+ ", fechaModificacion=" + fechaModificacion
				+ ", fechaRegistro=" + fechaRegistro + ", usuarioRegistro="
				+ usuarioRegistro + ", fotoPerfil="
				+ Arrays.toString(fotoPerfil) + "]";
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
			if (!(obj instanceof Usuario)) {
				return false;
			} else {
				if (((Usuario) obj).id == this.id) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

	public String getEstado() {
		return estado;
	}

	public Rol getRol() {
		return rol;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getState() {
		return estado;
	}

	public void setState(String state) {
		this.estado = state;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public byte[] getFotoPerfil() {
		return fotoPerfil;
	}

	public void setFotoPerfil(byte[] fotoPerfil) {
		this.fotoPerfil = fotoPerfil;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUsuarioRegistro() {
		return usuarioRegistro;
	}

	public void setUsuarioRegistro(String usuarioRegistro) {
		this.usuarioRegistro = usuarioRegistro;
	}

	public int getPesoFoto() {
		return pesoFoto;
	}

	public void setPesoFoto(int pesoFoto) {
		this.pesoFoto = pesoFoto;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public String getPaginaInicio() {
		return paginaInicio;
	}

	public void setPaginaInicio(String paginaInicio) {
		this.paginaInicio = paginaInicio;
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
