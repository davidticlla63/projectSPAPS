package com.teds.spaps.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

/**
 * Entity implementation class for Entity: Cargo
 *
 */
@Entity
@Table(name = "lab_examen_imag", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class LabExamenImag implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Size(max = 255)
	@Column(name = "descripcion", nullable = false)
	private String descripcion;

	@Column(name = "visualizar", nullable = false)
	private boolean visualizar = false;

	private boolean checker = false;


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

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_grupo_examen", nullable = false)
	private LabGrupoExamenImag grupoExamen;

	@Column(name = "fecha_modificacion", nullable = true)
	private Date fechaModificacion;

	@Column(name = "fecha_registro", nullable = false)
	private Date fechaRegistro;
	@Column(name = "usuario_registro", nullable = false)
	private String usuarioRegistro;
	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "labExamen", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<LabExamenDetalleImag> listExamenDetalle= new ArrayList<LabExamenDetalleImag>();

	public LabExamenImag() {
		super();
		this.id = 0;
		this.descripcion = "";
		this.estado = "AC";
		this.compania = new Compania();
		this.sucursal = new Sucursal();
		this.usuarioRegistro = "";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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
		return getDescripcion();
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
			if (!(obj instanceof LabExamenImag)) {
				return false;
			} else {
				if (((LabExamenImag) obj).id == this.id) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

	/**
	 * @return the grupoExamen
	 */
	public LabGrupoExamenImag getGrupoExamen() {
		return grupoExamen;
	}

	/**
	 * @param grupoExamen
	 *            the grupoExamen to set
	 */
	public void setGrupoExamen(LabGrupoExamenImag grupoExamen) {
		this.grupoExamen = grupoExamen;
	}

	/**
	 * @return the visualizar
	 */
	public boolean isVisualizar() {
		return visualizar;
	}

	/**
	 * @param visualizar
	 *            the visualizar to set
	 */
	public void setVisualizar(boolean visualizar) {
		this.visualizar = visualizar;
	}

	/**
	 * @return the checker
	 */
	public boolean isChecker() {
		return checker;
	}

	/**
	 * @param checker
	 *            the checker to set
	 */
	public void setChecker(boolean checker) {
		this.checker = checker;
	}

	/**
	 * @return the listExamenDetalle
	 */
	public List<LabExamenDetalleImag> getListExamenDetalle() {
		return listExamenDetalle;
	}

	/**
	 * @param listExamenDetalle
	 *            the listExamenDetalle to set
	 */
	public void setListExamenDetalle(List<LabExamenDetalleImag> listExamenDetalle) {
		this.listExamenDetalle = listExamenDetalle;
	}

	public void addDetalle(LabExamenDetalleImag examenDetalle) {
		this.listExamenDetalle.add(examenDetalle);
	}

	
}
