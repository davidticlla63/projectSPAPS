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
@Table(name = "examen_fisico_medico", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class ExamenFisicoSegmentario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5192703946344499634L;
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
	@Size(max = 255)
	@Column(name = "exploracion_general", nullable = false)
	private String exploracionGeneral;
	@Size(max = 255)
	@Column(name = "cabeza", nullable = false)
	private String cabeza;
	@Size(max = 255)
	@Column(name = "cuello", nullable = false)
	private String cuello;
	@Size(max = 255)
	@Column(name = "torax", nullable = false)
	private String torax;
	@Size(max = 255)
	@Column(name = "abdomen", nullable = false)
	private String abdomen;
	@Size(max = 255)
	@Column(name = "miembros", nullable = false)
	private String miembros;
	@Size(max = 255)
	@Column(name = "genitales", nullable = true, insertable = true, updatable = true)
	private String genitales;
	@Size(max = 2)
	@Column(name = "estado", nullable = false)
	// AC , IN , RM
	private String estado;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_consulta", nullable = false)
	private Consulta consulta;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_medico", nullable = false)
	private Personal medico;
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "id_examen_enfermera", nullable = true, insertable = true, updatable = true)
	private ExamenFisicoSignosVitales examenEnfermera;
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

	public ExamenFisicoSegmentario() {
		super();
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
		this.presionDiastolica = 0;
		this.presionSistolica = 0;
		this.id = 0;
		this.estado = "AC";
		this.compania = new Compania();
		this.sucursal = new Sucursal();
		this.usuarioRegistro = "";
		this.cabeza = "No se valora.";
		this.cuello = "No se valora.";
		this.abdomen = "No se valora.";
		this.exploracionGeneral = "";
		this.miembros = "No se valora.";
		this.torax = "No se valora.";
		this.genitales = "No se valora.";
		this.historiaClinica = new HistoriaClinica();
		this.examenEnfermera = null;
		this.medico = new Personal();
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

	public String getExploracionGeneral() {
		return exploracionGeneral;
	}

	public void setExploracionGeneral(String exploracionGeneral) {
		this.exploracionGeneral = exploracionGeneral;
	}

	public String getCabeza() {
		return cabeza;
	}

	public void setCabeza(String cabeza) {
		this.cabeza = cabeza;
	}

	public String getCuello() {
		return cuello;
	}

	public void setCuello(String cuello) {
		this.cuello = cuello;
	}

	public String getTorax() {
		return torax;
	}

	public void setTorax(String torax) {
		this.torax = torax;
	}

	public String getAbdomen() {
		return abdomen;
	}

	public void setAbdomen(String abdomen) {
		this.abdomen = abdomen;
	}

	public String getMiembros() {
		return miembros;
	}

	public void setMiembros(String miembros) {
		this.miembros = miembros;
	}

	public String getGenitales() {
		return genitales;
	}

	public void setGenitales(String genitales) {
		this.genitales = genitales;
	}

	public HistoriaClinica getHistoriaClinica() {
		return historiaClinica;
	}

	public void setHistoriaClinica(HistoriaClinica historiaClinica) {
		this.historiaClinica = historiaClinica;
	}

	/**
	 * @return the frecuenciaCardiaca
	 */
	public Integer getFrecuenciaCardiaca() {
		return frecuenciaCardiaca;
	}

	/**
	 * @param frecuenciaCardiaca
	 *            the frecuenciaCardiaca to set
	 */
	public void setFrecuenciaCardiaca(Integer frecuenciaCardiaca) {
		this.frecuenciaCardiaca = frecuenciaCardiaca;
	}

	/**
	 * @return the peso
	 */
	public float getPeso() {
		return peso;
	}

	/**
	 * @param peso
	 *            the peso to set
	 */
	public void setPeso(float peso) {
		this.peso = peso;
	}

	/**
	 * @return the pulso
	 */
	public Integer getPulso() {
		return pulso;
	}

	/**
	 * @param pulso
	 *            the pulso to set
	 */
	public void setPulso(Integer pulso) {
		this.pulso = pulso;
	}

	/**
	 * @return the altura
	 */
	public float getAltura() {
		return altura;
	}

	/**
	 * @param altura
	 *            the altura to set
	 */
	public void setAltura(float altura) {
		this.altura = altura;
	}

	/**
	 * @return the presionSistolica
	 */
	public float getPresionSistolica() {
		return presionSistolica;
	}

	/**
	 * @param presionSistolica
	 *            the presionSistolica to set
	 */
	public void setPresionSistolica(float presionSistolica) {
		this.presionSistolica = presionSistolica;
	}

	/**
	 * @return the presionDiastolica
	 */
	public float getPresionDiastolica() {
		return presionDiastolica;
	}

	/**
	 * @param presionDiastolica
	 *            the presionDiastolica to set
	 */
	public void setPresionDiastolica(float presionDiastolica) {
		this.presionDiastolica = presionDiastolica;
	}

	/**
	 * @return the temperatura
	 */
	public Integer getTemperatura() {
		return temperatura;
	}

	/**
	 * @param temperatura
	 *            the temperatura to set
	 */
	public void setTemperatura(Integer temperatura) {
		this.temperatura = temperatura;
	}

	/**
	 * @return the pb
	 */
	public float getPb() {
		return pb;
	}

	/**
	 * @param pb
	 *            the pb to set
	 */
	public void setPb(float pb) {
		this.pb = pb;
	}

	public float getPc() {
		return pc;
	}

	public void setPc(float pc) {
		this.pc = pc;
	}

	/**
	 * @return the indiceMasaCorporal
	 */
	public float getIndiceMasaCorporal() {
		return indiceMasaCorporal;
	}

	/**
	 * @param indiceMasaCorporal
	 *            the indiceMasaCorporal to set
	 */
	public void setIndiceMasaCorporal(float indiceMasaCorporal) {
		this.indiceMasaCorporal = indiceMasaCorporal;
	}

	/**
	 * @return the clasificacionPresionArterial
	 */
	public String getClasificacionPresionArterial() {
		return clasificacionPresionArterial;
	}

	/**
	 * @param clasificacionPresionArterial
	 *            the clasificacionPresionArterial to set
	 */
	public void setClasificacionPresionArterial(
			String clasificacionPresionArterial) {
		this.clasificacionPresionArterial = clasificacionPresionArterial;
	}

	/**
	 * @return the clasificacionIndiceMasaCorporal
	 */
	public String getClasificacionIndiceMasaCorporal() {
		return clasificacionIndiceMasaCorporal;
	}

	/**
	 * @param clasificacionIndiceMasaCorporal
	 *            the clasificacionIndiceMasaCorporal to set
	 */
	public void setClasificacionIndiceMasaCorporal(
			String clasificacionIndiceMasaCorporal) {
		this.clasificacionIndiceMasaCorporal = clasificacionIndiceMasaCorporal;
	}

	public ExamenFisicoSignosVitales getExamenEnfermera() {
		return examenEnfermera;
	}

	public void setExamenEnfermera(ExamenFisicoSignosVitales examenEnfermera) {
		this.examenEnfermera = examenEnfermera;
	}

	public Personal getMedico() {
		return medico;
	}

	public void setMedico(Personal medico) {
		this.medico = medico;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
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
			if (!(obj instanceof ExamenFisicoSegmentario)) {
				return false;
			} else {
				if (((ExamenFisicoSegmentario) obj).id == this.id) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

}
