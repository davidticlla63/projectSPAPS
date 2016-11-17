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
@Table(name = "configuracion_contable", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = {"id_empresa"}))
public class ConfiguracionContable implements Serializable {

	private static final long serialVersionUID = -8812447129953055293L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	//COMPRAS Y VENTAS
	@Column(name="porcentaje_iva", nullable=false )
	private double porcentajeIva;

	@Column(name="porcentaje_it", nullable=false )
	private double porcentajeIt;

	@ManyToOne(fetch=FetchType.EAGER, optional=false)
	@JoinColumn(name="id_empresa", nullable=false)
	private Empresa empresa;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="cuenta_debito_fiscal", nullable=true)
	private PlanCuenta cuentaDebitoFiscal;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="cuenta_credito_fiscal", nullable=true)
	private PlanCuenta cuentaCreditoFiscal;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="cuenta_credito_fiscal_no_deducible", nullable=true)
	private PlanCuenta cuentaCreditoFiscalNoDeducible;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="cuenta_credito_fiscal_transitorio", nullable=true)
	private PlanCuenta cuentaCreditoFiscalTransitorio;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="cuenta_impuesto_transacciones", nullable=true)
	private PlanCuenta cuentaImpuestoTransacciones;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="cuenta_gasto_impuesto_transacciones", nullable=true)
	private PlanCuenta cuentaGastoImpuestoTransacciones;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="cuenta_debito_fiscal_transitorio", nullable=true)
	private PlanCuenta cuentaDebitoFiscalTransitorio;

	//CUENTAS DE AJUSTE
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="cuenta_inflacion", nullable=true)
	private PlanCuenta cuentaInflacion;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="cuenta_ajuste_correcion_monetaria", nullable=true)
	private PlanCuenta cuentaAjusteCorrecionMonetaria;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="cuenta_diferencia_cambio", nullable=true)
	private PlanCuenta cuentaDiferenciaCambio;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="cuenta_ajuste_capital", nullable=true)
	private PlanCuenta cuentaAjusteCapital;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="cuenta_ajuste_reservas_patrimoniales", nullable=true)
	private PlanCuenta cuentaAjusteReservasPatrimoniales;
	
	//GESTION CONTABLE
	@Column(name="proceso_ajuste", nullable=false )
	private String procesoAjuste; //{ CD = Comprobante Por Dia | CM = Comprobante por Mes }

	@Size(max = 2) //AC , IN
	private String estado;

	@Column(name="fecha_modificacion",nullable=true )
	private Date fechaModificacion;

	@Column(name="fecha_registro",nullable=false )
	private Date fechaRegistro;

	@Column(name="usuario_registro",nullable=false )
	private String UsuarioRegistro;

	public ConfiguracionContable() {
		super();
		this.id = 0;
		this.porcentajeIva = 13;
		this.porcentajeIt = 3;
		this.estado = "AC";
		this.procesoAjuste = "CM";
	}

	@Override
	public String toString() {
		return ""+id ;
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
			if(!(obj instanceof ConfiguracionContable)){
				return false;
			}else{
				if(((ConfiguracionContable)obj).id==this.id){
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

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getUsuarioRegistro() {
		return UsuarioRegistro;
	}

	public void setUsuarioRegistro(String usuarioRegistro) {
		UsuarioRegistro = usuarioRegistro;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public double getPorcentajeIva() {
		return porcentajeIva;
	}

	public void setPorcentajeIva(double porcentajeIva) {
		this.porcentajeIva = porcentajeIva;
	}

	public double getPorcentajeIt() {
		return porcentajeIt;
	}

	public void setPorcentajeIt(double porcentajeIt) {
		this.porcentajeIt = porcentajeIt;
	}

	public PlanCuenta getCuentaDebitoFiscal() {
		return cuentaDebitoFiscal;
	}

	public void setCuentaDebitoFiscal(PlanCuenta cuentaDebitoFiscal) {
		this.cuentaDebitoFiscal = cuentaDebitoFiscal;
	}

	public PlanCuenta getCuentaCreditoFiscal() {
		return cuentaCreditoFiscal;
	}

	public void setCuentaCreditoFiscal(PlanCuenta cuentaCreditoFiscal) {
		this.cuentaCreditoFiscal = cuentaCreditoFiscal;
	}

	public PlanCuenta getCuentaCreditoFiscalNoDeducible() {
		return cuentaCreditoFiscalNoDeducible;
	}

	public void setCuentaCreditoFiscalNoDeducible(
			PlanCuenta cuentaCreditoFiscalNoDeducible) {
		this.cuentaCreditoFiscalNoDeducible = cuentaCreditoFiscalNoDeducible;
	}

	public PlanCuenta getCuentaCreditoFiscalTransitorio() {
		return cuentaCreditoFiscalTransitorio;
	}

	public void setCuentaCreditoFiscalTransitorio(
			PlanCuenta cuentaCreditoFiscalTransitorio) {
		this.cuentaCreditoFiscalTransitorio = cuentaCreditoFiscalTransitorio;
	}

	public PlanCuenta getCuentaImpuestoTransacciones() {
		return cuentaImpuestoTransacciones;
	}

	public void setCuentaImpuestoTransacciones(
			PlanCuenta cuentaImpuestoTransacciones) {
		this.cuentaImpuestoTransacciones = cuentaImpuestoTransacciones;
	}

	public PlanCuenta getCuentaGastoImpuestoTransacciones() {
		return cuentaGastoImpuestoTransacciones;
	}

	public void setCuentaGastoImpuestoTransacciones(
			PlanCuenta cuentaGastoImpuestoTransacciones) {
		this.cuentaGastoImpuestoTransacciones = cuentaGastoImpuestoTransacciones;
	}

	public PlanCuenta getCuentaDebitoFiscalTransitorio() {
		return cuentaDebitoFiscalTransitorio;
	}

	public void setCuentaDebitoFiscalTransitorio(
			PlanCuenta cuentaDebitoFiscalTransitorio) {
		this.cuentaDebitoFiscalTransitorio = cuentaDebitoFiscalTransitorio;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public PlanCuenta getCuentaInflacion() {
		return cuentaInflacion;
	}

	public void setCuentaInflacion(PlanCuenta cuentaInflacion) {
		this.cuentaInflacion = cuentaInflacion;
	}

	public PlanCuenta getCuentaAjusteCorrecionMonetaria() {
		return cuentaAjusteCorrecionMonetaria;
	}

	public void setCuentaAjusteCorrecionMonetaria(
			PlanCuenta cuentaAjusteCorrecionMonetaria) {
		this.cuentaAjusteCorrecionMonetaria = cuentaAjusteCorrecionMonetaria;
	}

	public PlanCuenta getCuentaDiferenciaCambio() {
		return cuentaDiferenciaCambio;
	}

	public void setCuentaDiferenciaCambio(PlanCuenta cuentaDiferenciaCambio) {
		this.cuentaDiferenciaCambio = cuentaDiferenciaCambio;
	}

	public PlanCuenta getCuentaAjusteCapital() {
		return cuentaAjusteCapital;
	}

	public void setCuentaAjusteCapital(PlanCuenta cuentaAjusteCapital) {
		this.cuentaAjusteCapital = cuentaAjusteCapital;
	}

	public PlanCuenta getCuentaAjusteReservasPatrimoniales() {
		return cuentaAjusteReservasPatrimoniales;
	}

	public void setCuentaAjusteReservasPatrimoniales(
			PlanCuenta cuentaAjusteReservasPatrimoniales) {
		this.cuentaAjusteReservasPatrimoniales = cuentaAjusteReservasPatrimoniales;
	}

	public String getProcesoAjuste() {
		return procesoAjuste;
	}

	public void setProcesoAjuste(String procesoAjuste) {
		this.procesoAjuste = procesoAjuste;
	}


}


