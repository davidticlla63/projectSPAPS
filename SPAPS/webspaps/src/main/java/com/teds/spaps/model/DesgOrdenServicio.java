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
 * Entity implementation class for Entity: DesgOrdenServicio
 *
 */
@Entity
@Table(name = "desg_orden_servicio", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class DesgOrdenServicio implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3996230045537613107L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String codigo;
	@Column(name = "progreso", nullable = false)
	private Integer progreso;
	@Size(max = 2)
	@Column(name = "estado", nullable = false)
	// Actual Solicitada, Aprobada, Enviada, Realizada, No realizado
	private String estado;
	@Size(max = 1)
	@Column(name = "pago", nullable = false)
	private String pago;
	@Size(max = 255)
	@Column(name = "observacion", nullable = false)
	private String observacion;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_cliente", nullable = false)
	private Paciente cliente;
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "id_historia_clinica", nullable = true, insertable = true, updatable = true)
	private HistoriaClinica historiaClinica;
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "id_orden_laboratorio", nullable = true, insertable = true, updatable = true)
	private LabOrden ordenLab;
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "id_orden_imageneologia", nullable = true, insertable = true, updatable = true)
	private LabOrdenImag ordenImag;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_medico_auditor", nullable = false)
	private Personal medicoAuditor;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_empresa", nullable = false)
	private Empresa empresa;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_sucursal_destino", nullable = false)
	private Sucursal destino;
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

	public DesgOrdenServicio() {
		super();
		this.id = 0;
		this.cliente = new Paciente();
		this.estado = "AC";
		this.compania = new Compania();
		this.sucursal = new Sucursal();
		this.usuarioRegistro = "";
		this.codigo = "";
		this.destino = new Sucursal();
		this.medicoAuditor = new Personal();
		this.empresa = new Empresa();
		this.pago = "E";
		this.observacion = "";
		this.historiaClinica = null;
		this.ordenImag = null;
		this.ordenLab = null;
		this.progreso = 0;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEstado() {
		return this.estado;
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

	/**
	 * @return the cliente
	 */
	public Paciente getCliente() {
		return cliente;
	}

	/**
	 * @param cliente
	 *            the cliente to set
	 */
	public void setCliente(Paciente cliente) {
		this.cliente = cliente;
	}

	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Personal getMedicoAuditor() {
		return medicoAuditor;
	}

	public void setMedicoAuditor(Personal medicoAuditor) {
		this.medicoAuditor = medicoAuditor;
	}

	@Override
	public String toString() {
		return this.id.toString();
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Sucursal getDestino() {
		return destino;
	}

	public void setDestino(Sucursal destino) {
		this.destino = destino;
	}

	/**
	 * @return the pago
	 */
	public String getPago() {
		return pago;
	}

	/**
	 * @param pago
	 *            the pago to set
	 */
	public void setPago(String pago) {
		this.pago = pago;
	}

	/**
	 * @return the observacion
	 */
	public String getObservacion() {
		return observacion;
	}

	/**
	 * @param observacion
	 *            the observacion to set
	 */
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public HistoriaClinica getHistoriaClinica() {
		return historiaClinica;
	}

	public void setHistoriaClinica(HistoriaClinica historiaClinica) {
		this.historiaClinica = historiaClinica;
	}

	/**
	 * @return the ordenLab
	 */
	public LabOrden getOrdenLab() {
		return ordenLab;
	}

	/**
	 * @param ordenLab
	 *            the ordenLab to set
	 */
	public void setOrdenLab(LabOrden ordenLab) {
		this.ordenLab = ordenLab;
	}

	/**
	 * @return the ordenImag
	 */
	public LabOrdenImag getOrdenImag() {
		return ordenImag;
	}

	/**
	 * @param ordenImag
	 *            the ordenImag to set
	 */
	public void setOrdenImag(LabOrdenImag ordenImag) {
		this.ordenImag = ordenImag;
	}

	/**
	 * @return the progreso
	 */
	public Integer getProgreso() {
		return progreso;
	}

	/**
	 * @param progreso
	 *            the progreso to set
	 */
	public void setProgreso(Integer progreso) {
		this.progreso = progreso;
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
			if (!(obj instanceof DesgOrdenServicio)) {
				return false;
			} else {
				if (((DesgOrdenServicio) obj).id == this.id) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

}
