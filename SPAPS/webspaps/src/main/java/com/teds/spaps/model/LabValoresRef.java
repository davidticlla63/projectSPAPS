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

import com.teds.spaps.enums.Sexo;

/**
 * Entity implementation class for Entity: Cargo
 *
 */
@Entity
@Table(name = "lab_valores_referencia", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class LabValoresRef implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private boolean edicion;

	@Size(max = 255)
	@Column(name = "parametros", nullable = false)
	private String parametros;

	@Enumerated(EnumType.STRING)
	private Sexo sexo;// HOMBRE, MUJER, A= AMBOS, F= FEMENINO, M=
						// MASCULINO

	private String edad = "ADULTO"; // ADULTO,NIÑO

	@Column(name = "edad_inicial", nullable = true)
	private String edadInicial; // 20D =20 DIAS, 20A = 20 AÑOS

	@Column(name = "edad_final", nullable = true)
	private String edadFinal;// 20D =20 DIAS, 20A = 20 AÑOS

	@Column(name = "valor_inicial", nullable = true)
	private double valorInicial = 0;

	@Column(name = "valor_final", nullable = true)
	private double valorFinal = 0;

	@Column(name = "valor", nullable = true)
	private double valor = 0;

	@Column(name = "texto_multiple", nullable = true)
	private String textoMultiple = "";// ARREGLO DE RESPUESTAS, SEPARAR CON DOS
										// PUNTOS (:)

	@Size(max = 2)
	@Column(name = "estado", nullable = false)
	// AC , IN , RM
	private String estado;

	private Integer correlativo;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_examen_detalle", nullable = false)
	private LabExamenDetalle examenDetalle;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_examen", nullable = false)
	private LabExamen labExamen;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "id_ciudad", nullable = true)
	private Ciudad ciudad;

	@Column(name = "fecha_modificacion", nullable = true)
	private Date fechaModificacion;
	@Column(name = "fecha_registro", nullable = false)
	private Date fechaRegistro;
	@Column(name = "usuario_registro", nullable = false)
	private String usuarioRegistro;
	private static final long serialVersionUID = 1L;

	public LabValoresRef() {
		super();
		this.id = 0;
		this.estado = "AC";
		this.usuarioRegistro = "";
		this.ciudad = new Ciudad();
		this.edadInicial = "00";
		this.edadFinal = "200A";
		this.valorFinal = 0;
		this.valorInicial = 0;
		this.examenDetalle = new LabExamenDetalle();
		this.labExamen = new LabExamen();
		this.edicion = true;
		this.fechaRegistro = new Date();
	}

	public Integer getId() {
		return id;
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
		return getParametros();
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
			if (!(obj instanceof LabValoresRef)) {
				return false;
			} else {
				if (((LabValoresRef) obj).id == this.id) {
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
	 * @return the examenDetalle
	 */
	public LabExamenDetalle getExamenDetalle() {
		return examenDetalle;
	}

	/**
	 * @param examenDetalle
	 *            the examenDetalle to set
	 */
	public void setExamenDetalle(LabExamenDetalle examenDetalle) {
		this.examenDetalle = examenDetalle;
	}

	/**
	 * @return the ciudad
	 */
	public Ciudad getCiudad() {
		return ciudad;
	}

	/**
	 * @param ciudad
	 *            the ciudad to set
	 */
	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}

	/**
	 * @return the sexo
	 */
	public Sexo getSexo() {
		return sexo;
	}

	/**
	 * @param sexo
	 *            the sexo to set
	 */
	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	/**
	 * @return the edad
	 */
	public String getEdad() {
		return edad;
	}

	/**
	 * @param edad
	 *            the edad to set
	 */
	public void setEdad(String edad) {
		this.edad = edad;
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
	 * @return the edadInicial
	 */
	public String getEdadInicial() {
		return edadInicial;
	}

	/**
	 * @param edadInicial
	 *            the edadInicial to set
	 */
	public void setEdadInicial(String edadInicial) {
		this.edadInicial = edadInicial;
	}

	/**
	 * @return the edadFinal
	 */
	public String getEdadFinal() {
		return edadFinal;
	}

	/**
	 * @param edadFinal
	 *            the edadFinal to set
	 */
	public void setEdadFinal(String edadFinal) {
		this.edadFinal = edadFinal;
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
	 * @return the edicion
	 */
	public boolean isEdicion() {
		return edicion;
	}

	/**
	 * @param edicion
	 *            the edicion to set
	 */
	public void setEdicion(boolean edicion) {
		this.edicion = edicion;
	}

	public SelectItem[] getSexos() {
		SelectItem[] items = new SelectItem[Sexo.values().length];
		int i = 0;
		for (Sexo t : Sexo.values()) {
			items[i++] = new SelectItem(t, t.getLabel());
		}
		return items;
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

}
