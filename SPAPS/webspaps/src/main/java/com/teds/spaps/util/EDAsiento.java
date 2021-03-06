package com.teds.spaps.util;

import com.teds.spaps.model.CentroCosto;
import com.teds.spaps.model.PlanCuenta;


public class EDAsiento {
	private int id;
	private Integer idAsientoContable;
	private PlanCuenta cuenta;
	private String glosa;
	private CentroCosto centroCosto;
	private double haberNacional;
	private double debeNacional;
	private double haberExtranjero;
	private double debeExtranjero;
	private String numeroFactura;
	private String numeroCheque;

	public EDAsiento(){
		super();
		glosa = "";
	}

	/**
	 * 
	 * @param id
	 * @param cuenta
	 * @param glosa
	 * @param centroCosto
	 * @param haberNacional
	 * @param debeNacional
	 * @param haberExtranjero
	 * @param debeExtranjero
	 * @param idAsientoContable
	 * @param numeroFactura
	 * @param numeroCheque
	 */
	public EDAsiento(int id, PlanCuenta cuenta, String glosa,
			CentroCosto centroCosto, double haberNacional, double debeNacional,
			double haberExtranjero, double debeExtranjero,Integer idAsientoContable,String numeroFactura,String numeroCheque) {
		super();
		this.id = id;
		this.cuenta = cuenta;
		this.glosa = glosa;
		this.centroCosto = centroCosto;
		this.setHaberNacional(haberNacional);
		this.setDebeNacional(debeNacional);
		this.setHaberExtranjero(haberExtranjero);
		this.setDebeExtranjero(debeExtranjero);
		this.idAsientoContable = idAsientoContable;
		this.numeroFactura = numeroFactura;
		this.numeroCheque = numeroCheque;
	}

	public int getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public PlanCuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(PlanCuenta cuenta) {
		this.cuenta = cuenta;
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
	
	@Override
	public String toString(){
		return String.valueOf(id) ;
	}

	public Integer getIdAsientoContable() {
		return idAsientoContable;
	}

	public void setIdAsientoContable(Integer idAsientoContable) {
		this.idAsientoContable = idAsientoContable;
	}

	public String getNumeroFactura() {
		return numeroFactura;
	}

	public void setNumeroFactura(String numeroFactura) {
		this.numeroFactura = numeroFactura;
	}

	public String getNumeroCheque() {
		return numeroCheque;
	}

	public void setNumeroCheque(String numeroCheque) {
		this.numeroCheque = numeroCheque;
	}

}
