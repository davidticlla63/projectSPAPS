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
 * Entity implementation class for Entity: Indice
 *
 */
@Entity
@Table(name = "param_sistema_indeces", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = {
		"id", "id_compania" }))
public class ParamSistemaIndices implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7164019509389154356L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "nombre_operacion", nullable = false, insertable = true, updatable = true)
	private String nombreOperacion;

	@Column(name = "key", nullable = false, insertable = true, updatable = true)
	private String key;

	@Column(name = "prefijo", nullable = true, insertable = true, updatable = true)
	private String prefijo;

	@Column(name = "numero_digitos", nullable = true, insertable = true, updatable = true)
	private Integer numeroDigitos = 0;

	@Column(name = "correlativo", nullable = false, insertable = true, updatable = true)
	private Integer correlativo = 0;

	@Size(max = 2)
	@Column(name = "estado", nullable = false)
	// AC , IN , RM
	private String estado;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_compania", nullable = false)
	private Compania compania;

	@Column(name = "fecha_modificacion", nullable = true)
	private Date fechaModificacion;

	@Column(name = "fecha_registro", nullable = false)
	private Date fechaRegistro;

	@Column(name = "usuario_registro", nullable = false)
	private String usuarioRegistro;

	public ParamSistemaIndices() {
		super();
		this.id = 0;
		this.numeroDigitos = 0;
		this.correlativo = 0;
		this.key = "";
		this.nombreOperacion = "";
		this.estado = "AC";
		this.compania = new Compania();
		this.usuarioRegistro = "";
	}

	/**
	 * @param id
	 * @param nombreOperacion
	 * @param key
	 * @param prefijo
	 * @param numeroCeros
	 * @param correlativo
	 * @param estado
	 * @param compania
	 * @param fechaRegistro
	 * @param usuarioRegistro
	 */
	public ParamSistemaIndices(String nombreOperacion, String key,
			String prefijo, Integer numeroCeros, Integer correlativo,
			String estado, Compania compania, Date fechaRegistro,
			String usuarioRegistro) {
		super();
		this.nombreOperacion = nombreOperacion;
		this.key = key;
		this.prefijo = prefijo;
		this.numeroDigitos = numeroCeros;
		this.correlativo = correlativo;
		this.estado = estado;
		this.compania = compania;
		this.fechaRegistro = fechaRegistro;
		this.usuarioRegistro = usuarioRegistro;
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
			if (!(obj instanceof ParamSistemaIndices)) {
				return false;
			} else {
				if (((ParamSistemaIndices) obj).id == this.id) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

	/**
	 * @return the nombreOperacion
	 */
	public String getNombreOperacion() {
		return nombreOperacion;
	}

	/**
	 * @param nombreOperacion
	 *            the nombreOperacion to set
	 */
	public void setNombreOperacion(String nombreOperacion) {
		this.nombreOperacion = nombreOperacion;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the prefijo
	 */
	public String getPrefijo() {
		return prefijo;
	}

	/**
	 * @param prefijo
	 *            the prefijo to set
	 */
	public void setPrefijo(String prefijo) {
		this.prefijo = prefijo;
	}

	/**
	 * @return the correlativo
	 */
	public Integer getCorrelativo() {
		return correlativo;
	}

	/**
	 * @param correlativo
	 *            the correlativo to set
	 */
	public void setCorrelativo(Integer correlativo) {
		this.correlativo = correlativo;
	}

	/**
	 * @return the numeroCeros
	 */
	public Integer getNumeroDigitos() {
		return numeroDigitos;
	}

	/**
	 * @param numeroCeros
	 *            the numeroCeros to set
	 */
	public void setNumeroDigitos(Integer numeroCeros) {
		this.numeroDigitos = numeroCeros;
	}

}
