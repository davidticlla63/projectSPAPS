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
 * Entity implementation class for Entity: DetalleTipoDiagnostico
 *
 */
@Entity
@Table(name = "detalle_tipo_diagnostico", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class DetalleTipoDiagnostico implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3771543809079517560L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_tipo_diagnostico", nullable = false)
	private TipoDiagnostico tipoDiagnostico;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_diagnostico", nullable = false)
	private Diagnostico diagnostico;
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

	public DetalleTipoDiagnostico() {
		super();
		this.id = 0;
		this.tipoDiagnostico = new TipoDiagnostico();
		this.diagnostico = new Diagnostico();
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

	public TipoDiagnostico getTipoDiagnostico() {
		return tipoDiagnostico;
	}

	public void setTipoDiagnostico(TipoDiagnostico tipoDiagnostico) {
		this.tipoDiagnostico = tipoDiagnostico;
	}

	public Diagnostico getDiagnostico() {
		return diagnostico;
	}

	public void setDiagnostico(Diagnostico diagnostico) {
		this.diagnostico = diagnostico;
	}

	@Override
	public String toString() {
		return "Grupo = " + this.tipoDiagnostico.toString()
				+ ", Diagnostico = " + this.diagnostico.toString();
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
			if (!(obj instanceof DetalleTipoDiagnostico)) {
				return false;
			} else {
				if (((DetalleTipoDiagnostico) obj).id == this.id
						&& ((DetalleTipoDiagnostico) obj).tipoDiagnostico
								.equals(this.tipoDiagnostico)
						&& ((DetalleTipoDiagnostico) obj).diagnostico
								.equals(this.diagnostico)) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

}
