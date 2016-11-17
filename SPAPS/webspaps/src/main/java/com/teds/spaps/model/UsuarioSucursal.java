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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

/**
 * 
 * @author mauriciobejaranorivera
 *
 */
@Entity
@Table(name = "usuario_sucursal", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
@NamedQueries({
		@NamedQuery(name = "UsuarioSucursal.findAllByCompania", query = "SELECT em FROM UsuarioSucursal em WHERE em.sucursal.compania.id = :idCompania"),
		@NamedQuery(name = "UsuarioSucursal.findAllByUsuario", query = "SELECT em FROM UsuarioSucursal em WHERE em.usuario.id = :idUsuario") })
public class UsuarioSucursal implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_usuario", nullable = false)
	private Usuario usuario;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_sucursal", nullable = false)
	private Sucursal sucursal;

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

	public UsuarioSucursal() {
		super();
		this.id = 0;
		this.usuario = new Usuario();
		this.sucursal = new Sucursal();
		this.estado = "AC";
		this.usuarioRegistro = "";
	}
	

	/**
	 * @param usuario
	 * @param sucursal
	 * @param estado
	 * @param fechaRegistro
	 * @param usuarioRegistro
	 */
	public UsuarioSucursal(Usuario usuario, Sucursal sucursal, String estado,
			Date fechaRegistro, String usuarioRegistro) {
		super();
		this.usuario = usuario;
		this.sucursal = sucursal;
		this.estado = estado;
		this.fechaRegistro = fechaRegistro;
		this.usuarioRegistro = usuarioRegistro;
	}


	@Override
	public String toString() {
		return "UsuarioSucursal [id=" + id + ", usuario=" + usuario
				+ ", sucursal=" + sucursal + ", estado=" + estado
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
			if (!(obj instanceof UsuarioSucursal)) {
				return false;
			} else {
				if (((UsuarioSucursal) obj).id == this.id) {
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

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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

}
