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
 * Entity implementation class for Entity: Motivo
 *
 */
@Entity
@Table(name = "examen_fisico_enfermera", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class ExamenFisicoSignosVitales implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4275692519708404909L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "frecuencia_cardiaca", nullable = false)
	private Integer frecuenciaCardiaca;
	@Column(name = "peso", nullable = false)
	private float peso;
	@Column(name = "pulso", nullable = false)
	private Integer pulso;
	@Column(name = "altura", nullable = false)
	private float altura;
	@Column(name = "presion_sistolica", nullable = false)
	private float presionSistolica;
	@Column(name = "presion_diastolica", nullable = false)
	private float presionDiastolica;
	@Column(name = "temperatura", nullable = false)
	private Integer temperatura;
	@Column(name = "pb", nullable = true, insertable = true, updatable = true)
	private float pb;
	@Column(name = "pc", nullable = true, insertable = true, updatable = true)
	private float pc;
	@Column(name = "indice_masa_corporal", nullable = false)
	private float indiceMasaCorporal;
	@Size(max = 255)
	@Column(name = "clasificacion_presion_arterial", nullable = false)
	private String clasificacionPresionArterial;
	@Size(max = 255)
	@Column(name = "clasificacion_indice_masa_corporal", nullable = false)
	private String clasificacionIndiceMasaCorporal;
	@Size(max = 2)
	@Column(name = "estado", nullable = false)
	// AC , IN , RM
	private String estado;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_consulta", nullable = false)
	private Consulta consulta;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_enfermera", nullable = false)
	private Personal enfermera;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_historia_clinica", nullable = false)
	private HistoriaClinica historiaClinica;
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

	public ExamenFisicoSignosVitales() {
		super();
		this.id = 0;
		this.estado = "AC";
		this.compania = new Compania();
		this.sucursal = new Sucursal();
		this.usuarioRegistro = "";
		this.clasificacionIndiceMasaCorporal = "";
		this.clasificacionPresionArterial = "";
		this.altura = 0;
		this.peso = 0;
		this.pb = 0;
		this.pc = 0;
		this.pulso = 0;
		this.indiceMasaCorporal = 0;
		this.frecuenciaCardiaca = 0;
		this.temperatura = 0;
		this.pulso = 0;
		this.historiaClinica = new HistoriaClinica();
		this.presionDiastolica = 0;
		this.presionSistolica = 0;
		this.enfermera = new Personal();
		this.consulta = new Consulta();
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

	public Integer getFrecuenciaCardiaca() {
		return frecuenciaCardiaca;
	}

	public void setFrecuenciaCardiaca(Integer frecuenciaCardiaca) {
		this.frecuenciaCardiaca = frecuenciaCardiaca;
	}

	public float getPeso() {
		return peso;
	}

	public void setPeso(float peso) {
		this.peso = peso;
	}

	public Integer getPulso() {
		return pulso;
	}

	public void setPulso(Integer pulso) {
		this.pulso = pulso;
	}

	public float getAltura() {
		return altura;
	}

	public void setAltura(float altura) {
		this.altura = altura;
	}

	public Integer getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(Integer temperatura) {
		this.temperatura = temperatura;
	}

	public float getPb() {
		return pb;
	}

	public void setPb(float pb) {
		this.pb = pb;
	}

	public float getPc() {
		return pc;
	}

	public void setPc(float pc) {
		this.pc = pc;
	}

	public float getIndiceMasaCorporal() {
		return indiceMasaCorporal;
	}

	public void setIndiceMasaCorporal(float indiceMasaCorporal) {
		this.indiceMasaCorporal = indiceMasaCorporal;
	}

	public String getClasificacionPresionArterial() {
		return clasificacionPresionArterial;
	}

	public void setClasificacionPresionArterial(
			String clasificacionPresionArterial) {
		this.clasificacionPresionArterial = clasificacionPresionArterial;
	}

	public String getClasificacionIndiceMasaCorporal() {
		return clasificacionIndiceMasaCorporal;
	}

	public void setClasificacionIndiceMasaCorporal(
			String clasificacionIndiceMasaCorporal) {
		this.clasificacionIndiceMasaCorporal = clasificacionIndiceMasaCorporal;
	}

	public HistoriaClinica getHistoriaClinica() {
		return historiaClinica;
	}

	public void setHistoriaClinica(HistoriaClinica historiaClinica) {
		this.historiaClinica = historiaClinica;
	}

	public float getPresionSistolica() {
		return presionSistolica;
	}

	public void setPresionSistolica(float presionSistolica) {
		this.presionSistolica = presionSistolica;
	}

	public float getPresionDiastolica() {
		return presionDiastolica;
	}

	public void setPresionDiastolica(float presionDiastolica) {
		this.presionDiastolica = presionDiastolica;
	}

	public Personal getEnfermera() {
		return enfermera;
	}

	public void setEnfermera(Personal enfermera) {
		this.enfermera = enfermera;
	}

	/**
	 * @return the consulta
	 */
	public Consulta getConsulta() {
		return consulta;
	}

	/**
	 * @param consulta
	 *            the consulta to set
	 */
	public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
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
			if (!(obj instanceof ExamenFisicoSignosVitales)) {
				return false;
			} else {
				if (((ExamenFisicoSignosVitales) obj).id == this.id) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

}
