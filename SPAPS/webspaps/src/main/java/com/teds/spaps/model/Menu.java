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
@Table(name = "menu", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "nombre"))
public class Menu implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Size(max = 255)
	@Column(name = "nombre", nullable = false)
	private String nombre;

	@Size(max = 255)
	@Column(name = "ruta", nullable = false)
	private String ruta;

	@Size(max = 2)
	@Column(name = "flag_security", nullable = true)
	private String flagSecurity;

	@Size(max = 2)
	@Column(name = "flag_visible", nullable = true)
	private String flagVisible;// AC , IN , RM

	@Column(name = "menu_icono", nullable = true)
	private String menuIcono;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_menu_parent", nullable = true)
	private Menu menuParent;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_modulo", nullable = false)
	private Modulo modulo;

	@Size(max = 10)
	@Column(name = "tipo", nullable = false)
	private String tipo;

	@Size(max = 2)
	@Column(name = "estado", nullable = false)
	private String estado;// AC , IN , RM

	@Column(name = "fecha_modificacion", nullable = true)
	private Date fechaModificacion;

	@Column(name = "fecha_registro", nullable = false)
	private Date fechaRegistro;

	@Column(name = "usuario_registro", nullable = false)
	private String usuarioRegistro;

	public Menu() {
		super();
		this.id = 0;
		this.nombre = "";
		this.ruta = "";
		this.flagSecurity = "";
		this.flagVisible = "AC";
		this.menuIcono = "";
		this.menuParent = null;
		this.modulo = new Modulo();
		this.tipo = "";
		this.estado = "AC";
		this.usuarioRegistro = "";
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRuta() {
		return this.ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getFlagSecurity() {
		return flagSecurity;
	}

	public void setFlagSecurity(String flagSecurity) {
		this.flagSecurity = flagSecurity;
	}

	public String getFlagVisible() {
		return flagVisible;
	}

	public void setFlagVisible(String flagVisible) {
		this.flagVisible = flagVisible;
	}

	public Menu getMenuParent() {
		return menuParent;
	}

	public void setMenuParent(Menu menuParent) {
		this.menuParent = menuParent;
	}

	public Modulo getModulo() {
		return modulo;
	}

	public void setModulo(Modulo modulo) {
		this.modulo = modulo;
	}

	public String getMenuIcono() {
		return this.menuIcono;
	}

	public void setMenuIcono(String menu_icono) {
		this.menuIcono = menu_icono;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaModificacion() {
		return this.fechaModificacion;
	}

	public void setFechaModificacion(Date fecha_modificacion) {
		this.fechaModificacion = fecha_modificacion;
	}

	public Date getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Date fecha_registro) {
		this.fechaRegistro = fecha_registro;
	}

	public String getUsuarioRegistro() {
		return this.usuarioRegistro;
	}

	public void setUsuarioRegistro(String usuario_registro) {
		this.usuarioRegistro = usuario_registro;
	}

	@Override
	public String toString() {
		return nombre;
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
			if (!(obj instanceof Menu)) {
				return false;
			} else {
				if (((Menu) obj).id == this.id) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

}
