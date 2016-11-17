package com.teds.spaps.model;

import java.io.Serializable;
import java.util.Date;

import javax.faces.model.SelectItem;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import com.teds.spaps.enums.Sexo;

/**
 * Entity implementation class for Entity: GrupoFamiliar1
 *
 */
@Entity
@Table(name = "grupo_familiar", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class GrupoFamiliar implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Size(max = 20)
	@Column(name = "codigo", nullable = false)
	private String codigo;
	@Size(max = 200)
	@Column(name = "nombre", nullable = false)
	private String nombre;
	// @Size(max = 10)
	// @Column(name = "sexo", nullable = false)
	// private String sexo;
	@Enumerated(EnumType.STRING)
	private Sexo sexo;// HOMBRE, MUJER, A= AMBOS, F= FEMENINO, M=
						// MASCULINO
	@Size(max = 2)
	@Column(name = "estado", nullable = false)
	private String estado;
	@Column(name = "fecha_registro", nullable = false)
	private Date fechaRegistro;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_compania", nullable = false)
	private Compania compania;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_sucursal", nullable = false)
	private Sucursal sucursal;
	@Column(name = "fecha_modificacion", nullable = false)
	private Date fechaModificacion;
	@Column(name = "usuario_registro", nullable = false)
	private String usuarioRegistro;
	private static final long serialVersionUID = 1L;

	public GrupoFamiliar() {
		super();
		this.id = 0;
		this.codigo = "";
		this.nombre = "";
		this.sexo = Sexo.MASCULINO;
		this.estado = "AC";
		this.compania = new Compania();
		this.sucursal = new Sucursal();
		this.usuarioRegistro = "";
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Sexo getSexo() {
		return this.sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Date getFechaModificacion() {
		return this.fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public String getUsuarioRegistro() {
		return this.usuarioRegistro;
	}

	public void setUsuarioRegistro(String usuarioRegistro) {
		this.usuarioRegistro = usuarioRegistro;
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

	@Override
	public String toString() {
		return getNombre();
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
			if (!(obj instanceof GrupoFamiliar)) {
				return false;
			} else {
				if (((GrupoFamiliar) obj).id == this.id) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

	public SelectItem[] getSexos() {
		SelectItem[] items = new SelectItem[2];
		int i = 0;
		for (Sexo t : Sexo.values()) {
			if (t == Sexo.MASCULINO || t == Sexo.FEMENINO) {
				items[i++] = new SelectItem(t, t.getLabel());

			}
		}
		return items;
	}

}
