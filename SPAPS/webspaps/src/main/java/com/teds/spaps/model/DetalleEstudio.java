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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

/**
 * Entity implementation class for Entity: DetalleEstudio
 *
 */
@Entity
@Table(name = "detalle_estudio", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class DetalleEstudio implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4021684671074059769L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Lob
	@Column
	private byte[] archivo;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_estudio", nullable = false)
	private Estudio estudio;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_historia_medica", nullable = false)
	private HistoriaClinica historiaClinica;
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

	public DetalleEstudio() {
		super();
		this.id = 0;
		this.estudio = new Estudio();
		this.historiaClinica = new HistoriaClinica();
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

	public Estudio getEstudio() {
		return estudio;
	}

	public void setEstudio(Estudio estudio) {
		this.estudio = estudio;
	}

	public HistoriaClinica getHistoriaMedica() {
		return historiaClinica;
	}

	public void setHistoriaMedica(HistoriaClinica historiaClinica) {
		this.historiaClinica = historiaClinica;
	}

	@Override
	public String toString() {
		return "Grupo = " + this.estudio.toString() + ", Historia Medica = "
				+ this.historiaClinica.getCodigo();
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
			if (!(obj instanceof DetalleEstudio)) {
				return false;
			} else {
				if (((DetalleEstudio) obj).id == this.id
						&& ((DetalleEstudio) obj).estudio.equals(this.estudio)
						&& ((DetalleEstudio) obj).historiaClinica
								.equals(this.historiaClinica)) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

}
