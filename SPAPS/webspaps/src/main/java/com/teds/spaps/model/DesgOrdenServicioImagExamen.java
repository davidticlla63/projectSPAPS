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
 * Entity implementation class for Entity: DesgOrdenServicios
 *
 */
@Entity
@Table(name = "desg_orden_servicio_imag_examen", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = {
		"id", "id_orden_servicio", "id_examen_imageneologia" }))
public class DesgOrdenServicioImagExamen implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3891442830694489562L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_orden_servicio", nullable = false)
	private DesgOrdenServicio ordenServicio;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_examen_imageneologia", nullable = false)
	private LabExamenImag examen;
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

	public DesgOrdenServicioImagExamen() {
		super();
		this.id = 0;
		this.ordenServicio = new DesgOrdenServicio();
		this.examen = new LabExamenImag();
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

	/**
	 * @return the ordenServicio
	 */
	public DesgOrdenServicio getOrdenServicio() {
		return ordenServicio;
	}

	/**
	 * @param ordenServicio
	 *            the ordenServicio to set
	 */
	public void setOrdenServicio(DesgOrdenServicio ordenServicio) {
		this.ordenServicio = ordenServicio;
	}

	public LabExamenImag getExamen() {
		return examen;
	}

	public void setExamen(LabExamenImag examen) {
		this.examen = examen;
	}

	@Override
	public String toString() {
		return this.id.toString();
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
			if (!(obj instanceof DesgOrdenServicioImagExamen)) {
				return false;
			} else {
				if (((DesgOrdenServicioImagExamen) obj).id == this.id
						&& ((DesgOrdenServicioImagExamen) obj).ordenServicio
								.equals(this.ordenServicio)
						&& ((DesgOrdenServicioImagExamen) obj).examen
								.equals(this.examen)) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

}
