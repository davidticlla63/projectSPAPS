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
 * Entity implementation class for Entity: Cargo
 *
 */
@Entity
@Table(name = "lab_muestras", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = { "id" }))
public class LabMuestras implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Size(max = 255)
	@Column(name = "encargado", nullable = false)
	private String encargado;// BIOQUIMICO

	@Column(name = "tipo_muestra", nullable = true)
	private String tipoMuestra; // SUEROS, ORINA,ACES, ETC

	@Column(name = "codigo_tubos", nullable = true)
	private String codigoTubos;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_programacion", nullable = false)
	private LabProgramacionOrden programacionOrden;
	
	
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "id_etuiquetas", nullable = true)
	private LabEtiquetas etiquetas;

	@Column(name = "fecha_modificacion", nullable = true)
	private Date fechaModificacion;
	@Column(name = "fecha_registro", nullable = false)
	private Date fechaRegistro;
	@Column(name = "usuario_registro", nullable = false)
	private String usuarioRegistro;
	private static final long serialVersionUID = 1L;

	public LabMuestras() {
		super();
		this.id = 0;
		this.setEncargado("");
		this.etiquetas= new LabEtiquetas();
		this.usuarioRegistro = "";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	@Override
	public String toString() {
		return getTipoMuestra();
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
			if (!(obj instanceof LabMuestras)) {
				return false;
			} else {
				if (((LabMuestras) obj).id == this.id) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

	/**
	 * @return the encargado
	 */
	public String getEncargado() {
		return encargado;
	}

	/**
	 * @param encargado
	 *            the encargado to set
	 */
	public void setEncargado(String encargado) {
		this.encargado = encargado;
	}

	/**
	 * @return the codigoTubos
	 */
	public String getCodigoTubos() {
		return codigoTubos;
	}

	/**
	 * @param codigoTubos
	 *            the codigoTubos to set
	 */
	public void setCodigoTubos(String codigoTubos) {
		this.codigoTubos = codigoTubos;
	}

	/**
	 * @return the tipoMuestra
	 */
	public String getTipoMuestra() {
		return tipoMuestra;
	}

	/**
	 * @param tipoMuestra
	 *            the tipoMuestra to set
	 */
	public void setTipoMuestra(String tipoMuestra) {
		this.tipoMuestra = tipoMuestra;
	}

	/**
	 * @return the programacionOrden
	 */
	public LabProgramacionOrden getProgramacionOrden() {
		return programacionOrden;
	}

	/**
	 * @param programacionOrden
	 *            the programacionOrden to set
	 */
	public void setProgramacionOrden(LabProgramacionOrden programacionOrden) {
		this.programacionOrden = programacionOrden;
	}

	/**
	 * @return the etiquetas
	 */
	public LabEtiquetas getEtiquetas() {
		return etiquetas;
	}

	/**
	 * @param etiquetas the etiquetas to set
	 */
	public void setEtiquetas(LabEtiquetas etiquetas) {
		this.etiquetas = etiquetas;
	}

}
