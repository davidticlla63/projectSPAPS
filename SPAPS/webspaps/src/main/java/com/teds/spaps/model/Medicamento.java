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
 * Entity implementation class for Entity: Medicamento
 *
 */
@Entity
@Table(name = "medicamento", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Medicamento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5315063008319641475L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Size(max = 255)
	@Column(name = "nombre_generico", nullable = false)
	private String nombreGenerico;
	@Size(max = 255)
	@Column(name = "nombre_comercial", nullable = false)
	private String nombreComercial;
	@Size(max = 255)
	@Column(name = "presentacion", nullable = false)
	private String presentacion;
	@Size(max = 255)
	@Column(name = "indicaciones", nullable = true, insertable = true, updatable = true)
	private String indicaciones;
	@Size(max = 255)
	@Column(name = "contra_indicaciones", nullable = true, insertable = true, updatable = true)
	private String contraIndicaciones;
	@Size(max = 255)
	@Column(name = "sobre_dosis", nullable = true, insertable = true, updatable = true)
	private String sobreDosis;
	@Size(max = 255)
	@Column(name = "reacciones_adversas", nullable = true, insertable = true, updatable = true)
	private String reaccionesAdversas;
	@Size(max = 255)
	@Column(name = "observaciones", nullable = true, insertable = true, updatable = true)
	private String observaciones;
	@Size(max = 2)
	@Column(name = "estado", nullable = false)
	// AC , IN , RM
	private String estado;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_compania", nullable = false)
	private Compania compania;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_sucursal", nullable = false)
	private Sucursal sucursal;
	@Column(name = "fecha_modificacion", nullable = false)
	private Date fechaModificacion;
	@Column(name = "fecha_registro", nullable = false)
	private Date fechaRegistro;
	@Column(name = "usuario_registro", nullable = false)
	private String usuarioRegistro;

	public Medicamento() {
		super();
		this.id = 0;
		this.nombreGenerico = "";
		this.nombreComercial = "";
		this.presentacion = "";
		this.indicaciones = "";
		this.contraIndicaciones = "";
		this.observaciones = "";
		this.reaccionesAdversas = "";
		this.sobreDosis = "";
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

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getUsuarioRegistro() {
		return usuarioRegistro;
	}

	public void setUsuarioRegistro(String usuarioRegistro) {
		this.usuarioRegistro = usuarioRegistro;
	}

	public String getIndicaciones() {
		return indicaciones;
	}

	public void setIndicaciones(String indicaciones) {
		this.indicaciones = indicaciones;
	}

	public String getContraIndicaciones() {
		return contraIndicaciones;
	}

	public void setContraIndicaciones(String contraIndicaciones) {
		this.contraIndicaciones = contraIndicaciones;
	}

	public String getSobreDosis() {
		return sobreDosis;
	}

	public void setSobreDosis(String sobreDosis) {
		this.sobreDosis = sobreDosis;
	}

	public String getReaccionesAdversas() {
		return reaccionesAdversas;
	}

	public void setReaccionesAdversas(String reaccionesAdversas) {
		this.reaccionesAdversas = reaccionesAdversas;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getNombreGenerico() {
		return nombreGenerico;
	}

	public String getNombreComercial() {
		return nombreComercial;
	}

	public void setNombreGenerico(String nombreGenerico) {
		this.nombreGenerico = nombreGenerico;
	}

	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}

	public String getPresentacion() {
		return presentacion;
	}

	public void setPresentacion(String presentacion) {
		this.presentacion = presentacion;
	}

	@Override
	public String toString() {
		return this.nombreGenerico;
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
			if (!(obj instanceof Medicamento)) {
				return false;
			} else {
				if (((Medicamento) obj).id == this.id
						&& ((Medicamento) obj).nombreGenerico
								.equals(this.nombreGenerico)) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

}
