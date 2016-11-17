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
 * Entity implementation class for Entity: Transferencia
 *
 */
@Entity
@Table(name = "transferencia", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Transferencia implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1788877961799848467L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Size(max = 255)
	@Column(name = "motivo", nullable = false)
	private String motivo;
	@Size(max = 255)
	@Column(name = "tratamiento", nullable = false)
	private String tratamiento;
	@Size(max = 255)
	@Column(name = "examenes", nullable = false)
	private String examenes;
	@Size(max = 255)
	@Column(name = "impresion_diagnostica", nullable = false)
	private String impresionDiagnostica;
	@Size(max = 2)
	@Column(name = "estado", nullable = false)
	// II, AC , IN , RM
	private String estado;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_consulta", nullable = false)
	private Consulta consulta;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_medico_emisor", nullable = false)
	private Personal medicoEmisor;
	@Size(max = 255)
	@JoinColumn(name = "medico_receptor", nullable = false)
	private String medicoReceptor;
	@Size(max = 255)
	@JoinColumn(name = "especialidad_medico_receptor", nullable = false)
	private String especialidadMedicoReceptor;
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

	public Transferencia() {
		super();
		this.id = 0;
		this.motivo = "";
		this.tratamiento = "";
		this.examenes = "";
		this.impresionDiagnostica = "";
		this.estado = "AC";
		this.compania = new Compania();
		this.sucursal = new Sucursal();
		this.usuarioRegistro = "";
		this.historiaClinica = new HistoriaClinica();
		this.medicoEmisor = new Personal();
		this.medicoReceptor = "";
		this.especialidadMedicoReceptor = "";
		this.consulta = new Consulta();
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMotivo() {
		return this.motivo;
	}

	public void setMotivo(String mmotivo) {
		this.motivo = mmotivo;
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

	public String getTratamiento() {
		return tratamiento;
	}

	public String getExamenes() {
		return examenes;
	}

	public String getImpresionDiagnostica() {
		return impresionDiagnostica;
	}

	public void setTratamiento(String tratamiento) {
		this.tratamiento = tratamiento;
	}

	public void setExamenes(String examenes) {
		this.examenes = examenes;
	}

	public void setImpresionDiagnostica(String impresionDiagnostica) {
		this.impresionDiagnostica = impresionDiagnostica;
	}

	public HistoriaClinica getHistoriaClinica() {
		return historiaClinica;
	}

	public void setHistoriaClinica(HistoriaClinica historiaClinica) {
		this.historiaClinica = historiaClinica;
	}

	public Personal getMedicoEmisor() {
		return medicoEmisor;
	}

	public void setMedicoEmisor(Personal medicoEmisor) {
		this.medicoEmisor = medicoEmisor;
	}

	public String getMedicoReceptor() {
		return medicoReceptor;
	}

	public String getEspecialidadMedicoReceptor() {
		return especialidadMedicoReceptor;
	}

	public void setMedicoReceptor(String medicoReceptor) {
		this.medicoReceptor = medicoReceptor;
	}

	public void setEspecialidadMedicoReceptor(String especialidadMedicoReceptor) {
		this.especialidadMedicoReceptor = especialidadMedicoReceptor;
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
	public String toString() {
		return this.motivo;
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
			if (!(obj instanceof Transferencia)) {
				return false;
			} else {
				if (((Transferencia) obj).id == this.id) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

}
