package com.teds.spaps.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * 
 * @author mauriciobejaranorivera
 *
 */
@Entity
@Table(name = "linea_contable", schema = "public")
public class LineaContable implements Serializable {

	private static final long serialVersionUID = 7264798590170186972L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="haber_nacional",nullable=true )
	private double haberNacional;
	
	@Column(name="debe_nacional",nullable=true )
	private double debeNacional;
	
	@Column(name="haber_extranjero",nullable=true )
	private double haberExtranjero;
	
	@Column(name="debe_extranjero",nullable=true )
	private double debeExtranjero;
	
	private String glosa;
	
	@Temporal(TemporalType.DATE)
	@Column(name="fecha",nullable=true)
	private Date fecha;
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_centro_costo", nullable=true)
	private CentroCosto centroCosto;
	
	//@ManyToOne(fetch=FetchType.EAGER )
    //@JoinColumn(name="id_plan_cuenta", nullable=false)
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_plan_cuenta",nullable=false )
	private PlanCuenta planCuenta;
	
	@ManyToOne(fetch=FetchType.EAGER )
    @JoinColumn(name="id_comprobante", nullable=false)
	private Comprobante comprobante;
	
	@Size(max = 2) //AC , IN
	private String estado;
	
	@Column(name="usuario_registro",nullable=true )
	private String usuarioRegistro;
	
	@Column(name="fecha_registro",nullable=true )
	private Date fechaRegistro;
	
	@Column(name="fecha_modificacion",nullable=true )
	private Date fechaModificacion;
	
	public LineaContable() {
		super();
		this.id = 0;
	}
	
	public LineaContable(Integer id,  double haberNacional,
			double debeNacional, double haberExtranjero, double debeExtranjero,
			String glosa, CentroCosto centroCosto, PlanCuenta planCuenta) {
		super();
		this.id = id;
		this.haberNacional = haberNacional;
		this.debeNacional = debeNacional;
		this.haberExtranjero = haberExtranjero;
		this.debeExtranjero = debeExtranjero;
		this.glosa = glosa;
		this.centroCosto = centroCosto;
		this.planCuenta = planCuenta;
	}

	@Override
	public String toString() {
		return String.valueOf(id);
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj==null){
			return false;
		}else{
			if(!(obj instanceof LineaContable)){
				return false;
			}else{
				if(((LineaContable)obj).id==this.id){
					return true;
				}else{
					return false;
				}
			}
		}
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public double getHaberNacional() {
		return haberNacional;
	}

	public void setHaberNacional(double haberNacional) {
		this.haberNacional = haberNacional;
	}

	public double getDebeNacional() {
		return debeNacional;
	}

	public void setDebeNacional(double debeNacional) {
		this.debeNacional = debeNacional;
	}

	public double getHaberExtranjero() {
		return haberExtranjero;
	}

	public void setHaberExtranjero(double haberExtranjero) {
		this.haberExtranjero = haberExtranjero;
	}

	public double getDebeExtranjero() {
		return debeExtranjero;
	}

	public void setDebeExtranjero(double debeExtranjero) {
		this.debeExtranjero = debeExtranjero;
	}

	public String getGlosa() {
		return glosa;
	}

	public void setGlosa(String glosa) {
		this.glosa = glosa;
	}

	public CentroCosto getCentroCosto() {
		return centroCosto;
	}

	public void setCentroCosto(CentroCosto centroCosto) {
		this.centroCosto = centroCosto;
	}

	public PlanCuenta getPlanCuenta() {
		return planCuenta;
	}

	public void setPlanCuenta(PlanCuenta planCuenta) {
		this.planCuenta = planCuenta;
	}

	public Comprobante getComprobante() {
		return comprobante;
	}

	public void setComprobante(Comprobante comprobante) {
		this.comprobante = comprobante;
	}

	public String getUsuarioRegistro() {
		return usuarioRegistro;
	}

	public void setUsuarioRegistro(String usuarioRegistro) {
		this.usuarioRegistro = usuarioRegistro;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

}


