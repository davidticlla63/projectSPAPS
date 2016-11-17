package com.teds.spaps.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;
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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import com.teds.spaps.enums.TipoValor;

/**
 * Entity implementation class for Entity: Cargo
 *
 */
@Entity
@Table(name = "lab_orden_sub_detalle", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class LabOrdenSubDetalle implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Size(max = 255)
	@Column(name = "descripcion", nullable = false)
	private String descripcion;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_valor", nullable = true)
	private TipoValor tipoValor;

	@Size(max = 255)
	@Column(name = "parametros", nullable = false)
	private String parametros;

	private boolean completado = false;

	@Column(name = "fuera_rango", nullable = false)
	private boolean fueraRango = false;

	@Column(name = "resultado", nullable = true, length = 255)
	private String resultado = "";

	@Column(name = "valor_inicial", nullable = false)
	private double valorInicial = 0;

	@Column(name = "valor_final", nullable = false)
	private double valorFinal = 0;

	@Column(name = "valor", nullable = true)
	private double valor = 0;

	@Column(name = "texto_multiple", nullable = true)
	private String textoMultiple;// ARREGLO DE RESPUESTAS, SEPARAR CON DOS
									// PUNTOS (:)

	@Column(name = "observacion", nullable = true, length = 255)
	private String observacion;

	@Column(name = "label", nullable = false)
	private boolean label = false;

	@Column(name = "unidad_medida", nullable = false)
	private String unidaMedida;

	@Size(max = 2)
	@Column(name = "estado", nullable = false)
	// AC , IN , RM
	private String estado;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_orden", nullable = false)
	private LabOrden orden;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_compania", nullable = false)
	private Compania compania;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_sucursal", nullable = false)
	private Sucursal sucursal;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_orden_detalle", nullable = false)
	private LabOrdenDetalle ordenDetalle;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "id_valor_referencia", nullable = true)
	private LabValoresRef valoresRef;

	@Column(name = "fecha_modificacion", nullable = true)
	private Date fechaModificacion;
	@Column(name = "fecha_registro", nullable = false)
	private Date fechaRegistro;
	@Column(name = "usuario_registro", nullable = false)
	private String usuarioRegistro;
	private static final long serialVersionUID = 1L;

	public LabOrdenSubDetalle() {
		super();
		this.id = 0;
		this.descripcion = "";
		this.estado = "AC";
		this.usuarioRegistro = "";
		this.fechaRegistro = new Date();
		this.label = false;
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
			if (!(obj instanceof LabOrdenSubDetalle)) {
				return false;
			} else {
				if (((LabOrdenSubDetalle) obj).id == this.id) {
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
	 * @return the ordenDetalle
	 */
	public LabOrdenDetalle getOrdenDetalle() {
		return ordenDetalle;
	}

	/**
	 * @param ordenDetalle
	 *            the ordenDetalle to set
	 */
	public void setOrdenDetalle(LabOrdenDetalle ordenDetalle) {
		this.ordenDetalle = ordenDetalle;
	}

	/**
	 * @return the resultado
	 */
	public String getResultado() {
		return resultado;
	}

	/**
	 * @param resultado
	 *            the resultado to set
	 */
	public void setResultado(String resultado) {
		this.resultado = resultado;
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
	 * @return the orden
	 */
	public LabOrden getOrden() {
		return orden;
	}

	/**
	 * @param orden
	 *            the orden to set
	 */
	public void setOrden(LabOrden orden) {
		this.orden = orden;
	}

	/**
	 * @return the valoresRef
	 */
	public LabValoresRef getValoresRef() {
		return valoresRef;
	}

	/**
	 * @param valoresRef
	 *            the valoresRef to set
	 */
	public void setValoresRef(LabValoresRef valoresRef) {
		this.valoresRef = valoresRef;
	}

	/**
	 * @return the valorInicial
	 */
	public double getValorInicial() {
		return valorInicial;
	}

	/**
	 * @param valorInicial
	 *            the valorInicial to set
	 */
	public void setValorInicial(double valorInicial) {
		this.valorInicial = valorInicial;
	}

	/**
	 * @return the valorFinal
	 */
	public double getValorFinal() {
		return valorFinal;
	}

	/**
	 * @param valorFinal
	 *            the valorFinal to set
	 */
	public void setValorFinal(double valorFinal) {
		this.valorFinal = valorFinal;
	}

	/**
	 * @return the completado
	 */
	public boolean isCompletado() {
		this.completado = this.resultado.trim().length() > 0;
		// this.ordenDetalle.getPorcentaje();
		return this.completado;
	}

	/**
	 * @param completado
	 *            the completado to set
	 */
	public void setCompletado(boolean completado) {
		this.completado = completado;
	}

	/**
	 * @return the fueraRango
	 */
	public boolean isFueraRango() {
		int valor = -1;
		if (tipoValor == TipoValor.MULTIPLE) {
			return false;
		}
		if (tipoValor == TipoValor.TEXTO) {
			return false;
		}
		if (tipoValor == TipoValor.RANGOS) {
			if (this.resultado.trim().length() > 0) {
				if (isNumeric(this.resultado)) {
					valor = Integer.parseInt(this.resultado);
				} else {
					this.setResultado("");
					return false;
				}
			}
			this.fueraRango = (valorFinal >= valor && valorInicial <= valor);
			// System.out.println(valorInicial + " " + valor + " " + valorFinal
			// + " "
			// + fueraRango);

			return fueraRango;
		}
		return false;

	}

	private static boolean isNumeric(String cadena) {
		try {
			Integer.parseInt(cadena);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	/**
	 * @param fueraRango
	 *            the fueraRango to set
	 */
	public void setFueraRango(boolean fueraRango) {
		this.fueraRango = fueraRango;
	}

	/**
	 * @return the valor
	 */
	public double getValor() {
		return valor;
	}

	/**
	 * @param valor
	 *            the valor to set
	 */
	public void setValor(double valor) {
		this.valor = valor;
	}

	/**
	 * @return the textoMultiple
	 */
	public String getTextoMultiple() {
		return textoMultiple;
	}

	/**
	 * @param textoMultiple
	 *            the textoMultiple to set
	 */
	public void setTextoMultiple(String textoMultiple) {
		this.textoMultiple = textoMultiple;
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

	public SelectItem[] obtenerTextoMultiples() {
		System.out.println(textoMultiple);
		String[] text = textoMultiple.split(":");
		SelectItem[] items = new SelectItem[text.length];
		int i = 0;
		for (String t : text) {
			items[i++] = new SelectItem(t, t);
		}
		return items;
	}

}
