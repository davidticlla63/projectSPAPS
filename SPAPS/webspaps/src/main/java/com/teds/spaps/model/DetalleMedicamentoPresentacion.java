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
 * Entity implementation class for Entity: DetalleMedicamento
 *
 */
@Entity
@Table(name = "detalle_medicamento_presentacion", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class DetalleMedicamentoPresentacion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 37669845674519100L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_medicamento", nullable = false)
	private Medicamento medicamento;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_presentacion", nullable = false)
	private Presentacion presentacion;
	@Size(max = 2)
	@Column(name = "estado", nullable = false)
	// AC , IN , RM
	private String estado;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_compania", nullable = false)
	private Compania compania;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_sucursal", nullable = false)
	private Sucursal sucursalRegistro;
	@Column(name = "fecha_modificacion", nullable = false)
	private Date fechaModificacion;
	@Column(name = "fecha_registro", nullable = false)
	private Date fechaRegistro;
	@Column(name = "usuario_registro", nullable = false)
	private String usuarioRegistro;

	public DetalleMedicamentoPresentacion() {
		super();
		this.id = 0;
		this.medicamento = new Medicamento();
		this.presentacion = new Presentacion();
		this.estado = "AC";
		this.compania = new Compania();
		this.presentacion = new Presentacion();
		this.sucursalRegistro = new Sucursal();
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

	public Sucursal getSucursalRegistro() {
		return sucursalRegistro;
	}

	public void setSucursalRegistro(Sucursal sucursal) {
		this.sucursalRegistro = sucursal;
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

	public Medicamento getMedicamento() {
		return medicamento;
	}

	public void setMedicamento(Medicamento medicamento) {
		this.medicamento = medicamento;
	}

	public Presentacion getPresentacion() {
		return presentacion;
	}

	public void setPresentacion(Presentacion presentacion) {
		this.presentacion = presentacion;
	}

	@Override
	public String toString() {
		return "Medicamento = " + this.medicamento.getNombreGenerico()
				+ ", Presentacion = " + this.presentacion.getDescripcion();
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
			if (!(obj instanceof DetalleMedicamentoPresentacion)) {
				return false;
			} else {
				if (((DetalleMedicamentoPresentacion) obj).id == this.id
						&& ((DetalleMedicamentoPresentacion) obj).medicamento
								.equals(this.medicamento)
						&& ((DetalleMedicamentoPresentacion) obj).presentacion
								.equals(this.presentacion)) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

}
