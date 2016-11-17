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
@Table(name = "contrareferencia", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Contrareferencia implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3402199555777120035L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Size(max = 255)
	@Column(name = "consulta_externa", nullable = false)
	private String consultaExterna;
	@Size(max = 255)
	@Column(name = "sala", nullable = false)
	private String sala;
	@Size(max = 255)
	@Column(name = "consulta_guardia", nullable = false)
	private String consultaGuardia;
	@Size(max = 255)
	@Column(name = "impresion_diagnostica", nullable = false)
	private String impresionDiagnostica;
	@Size(max = 255)
	@Column(name = "conducta", nullable = false)
	private String conducta;
	@Size(max = 2)
	@Column(name = "estado", nullable = false)
	// AC , IN , RM
	private String estado;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_consulta", nullable = false)
	private Consulta consulta;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_medico_receptor", nullable = false)
	private Personal medicoReceptor;
	@Size(max = 255)
	@JoinColumn(name = "medico_emisor", nullable = false)
	private String medicoEmisor;
	@Size(max = 255)
	@JoinColumn(name = "especialidad_medico_emisor", nullable = false)
	private String especialidadMedicoEmisor;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_transferencia", nullable = false)
	private Transferencia transferencia;
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

	public Contrareferencia() {
		super();
		this.id = 0;
		this.consultaExterna = "";
		this.sala = "";
		this.consultaGuardia = "";
		this.impresionDiagnostica = "";
		this.conducta = "";
		this.estado = "AC";
		this.compania = new Compania();
		this.sucursal = new Sucursal();
		this.usuarioRegistro = "";
		this.historiaClinica = new HistoriaClinica();
		this.medicoReceptor = new Personal();
		this.medicoEmisor = "";
		this.especialidadMedicoEmisor = "";
		this.transferencia = new Transferencia();
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

	public String getImpresionDiagnostica() {
		return impresionDiagnostica;
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

	public String getConsultaExterna() {
		return consultaExterna;
	}

	public String getSala() {
		return sala;
	}

	public String getConsultaGuardia() {
		return consultaGuardia;
	}

	public String getConducta() {
		return conducta;
	}

	public void setConsultaExterna(String consultaExterna) {
		this.consultaExterna = consultaExterna;
	}

	public void setSala(String sala) {
		this.sala = sala;
	}

	public void setConsultaGuardia(String consultaGuardia) {
		this.consultaGuardia = consultaGuardia;
	}

	public void setConducta(String conducta) {
		this.conducta = conducta;
	}

	public Personal getMedicoReceptor() {
		return medicoReceptor;
	}

	public String getMedicoEmisor() {
		return medicoEmisor;
	}

	public String getEspecialidadMedicoEmisor() {
		return especialidadMedicoEmisor;
	}

	public void setMedicoReceptor(Personal medicoReceptor) {
		this.medicoReceptor = medicoReceptor;
	}

	public void setMedicoEmisor(String medicoEmisor) {
		this.medicoEmisor = medicoEmisor;
	}

	public void setEspecialidadMedicoEmisor(String especialidadMedicoEmisor) {
		this.especialidadMedicoEmisor = especialidadMedicoEmisor;
	}

	public Transferencia getTransferencia() {
		return transferencia;
	}

	public void setTransferencia(Transferencia transferencia) {
		this.transferencia = transferencia;
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
		return this.consultaExterna;
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
			if (!(obj instanceof Contrareferencia)) {
				return false;
			} else {
				if (((Contrareferencia) obj).id == this.id) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

}
