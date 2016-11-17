package com.teds.spaps.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

import com.teds.spaps.enums.TipoSeleccion;
import com.teds.spaps.enums.TipoValor;

/**
 * Entity implementation class for Entity: Cargo
 *
 */

@Entity
@Table(name = "lab_examen_detalle", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class LabExamenDetalle implements Serializable {

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_parametro", nullable = true)
	private TipoSeleccion tipoParametro;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_valor", nullable = true)
	private TipoValor tipoValor;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Size(max = 255)
	@Column(name = "descripcion", nullable = false)
	private String descripcion;

	private Integer correlativo;

	@Size(max = 255)
	@Column(name = "parametros", nullable = false)
	private String parametros = "";

	@Column(name = "unidad_medida", nullable = false)
	private String unidaMedida = "";

	private boolean label = false;

	private boolean valores = false;

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
	@JoinColumn(name = "id_examen", nullable = false)
	private LabExamen labExamen;

	@Column(name = "fecha_modificacion", nullable = true)
	private Date fechaModificacion;
	@Column(name = "fecha_registro", nullable = false)
	private Date fechaRegistro;
	@Column(name = "usuario_registro", nullable = false)
	private String usuarioRegistro;
	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "examenDetalle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<LabValoresRef> listValoresReferencia = new ArrayList<LabValoresRef>();

	public LabExamenDetalle() {
		super();
		this.id = 0;
		this.descripcion = "";
		this.estado = "AC";
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
			if (!(obj instanceof LabExamenDetalle)) {
				return false;
			} else {
				if (((LabExamenDetalle) obj).id == this.id) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

	/**
	 * @return the parametros
	 */
	public String getParametros() {
		return parametros;
	}

	/**
	 * @param parametros
	 *            the parametros to set
	 */
	public void setParametros(String parametros) {
		this.parametros = parametros;
	}

	/**
	 * @return the labExamen
	 */
	public LabExamen getLabExamen() {
		return labExamen;
	}

	/**
	 * @param labExamen
	 *            the labExamen to set
	 */
	public void setLabExamen(LabExamen labExamen) {
		this.labExamen = labExamen;
	}

	/**
	 * @return the listValoresReferencia
	 */
	public List<LabValoresRef> getListValoresReferencia() {
		return listValoresReferencia;
	}

	/**
	 * @param listValoresReferencia
	 *            the listValoresReferencia to set
	 */
	public void setListValoresReferencia(
			List<LabValoresRef> listValoresReferencia) {
		this.listValoresReferencia = listValoresReferencia;
	}

	// public void addSubDetalle(LabValoresRef labValoresRef) {
	// this.listValoresReferencia.add(labValoresRef);
	// }

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
	 * @return the label
	 */
	public boolean isLabel() {
		return label;
	}

	/**
	 * @param label
	 *            the label to set
	 */
	public void setLabel(boolean label) {
		this.label = label;
	}

	/**
	 * @return the valores
	 */
	public boolean isValores() {
		return valores;
	}

	/**
	 * @param valores
	 *            the valores to set
	 */
	public void setValores(boolean valores) {
		this.valores = valores;
	}

	/**
	 * @return the unidaMedida
	 */
	public String getUnidaMedida() {
		return unidaMedida;
	}

	/**
	 * @param unidaMedida
	 *            the unidaMedida to set
	 */
	public void setUnidaMedida(String unidaMedida) {
		this.unidaMedida = unidaMedida;
	}

	/**
	 * @return the tipoParametro
	 */
	public TipoSeleccion getTipoParametro() {
		return tipoParametro;
	}

	/**
	 * @param tipoParametro
	 *            the tipoParametro to set
	 */
	public void setTipoParametro(TipoSeleccion tipoParametro) {
		this.tipoParametro = tipoParametro;
	}

	public SelectItem[] getSeleccion() {
		SelectItem[] items = new SelectItem[TipoSeleccion.values().length];
		int i = 0;
		for (TipoSeleccion t : TipoSeleccion.values()) {
			items[i++] = new SelectItem(t, t.getLabel());
		}
		return items;
	}

	public SelectItem[] getTipoValors() {
		SelectItem[] items = new SelectItem[TipoValor.values().length];
		int i = 0;
		for (TipoValor t : TipoValor.values()) {
			items[i++] = new SelectItem(t, t.getLabel());
		}
		return items;
	}

	public static void main(String[] args) {
		LabExamenDetalle labValoresRef = new LabExamenDetalle();
		TipoSeleccion[] seleccions = TipoSeleccion.values();
		for (TipoSeleccion tipoSeleccion : seleccions) {
			System.out.println(tipoSeleccion.name());

		}
	}

	/**
	 * @return the tipoValor
	 */
	public TipoValor getTipoValor() {
		return tipoValor;
	}

	/**
	 * @param tipoValor
	 *            the tipoValor to set
	 */
	public void setTipoValor(TipoValor tipoValor) {
		this.tipoValor = tipoValor;
	}

}
