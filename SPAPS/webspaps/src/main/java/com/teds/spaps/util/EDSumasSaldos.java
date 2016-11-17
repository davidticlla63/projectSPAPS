package com.teds.spaps.util;

/**
 * @author mauriciobejaranorivera
 *
 */
public class EDSumasSaldos {

	private Integer idPlanCuenta;
	
	private String codigoPlanCuenta;
	
	private String nombrePlanCuenta;
	
	private String clasePlanCuenta;
	
	private Integer idTipoCuenta;
	
	private String tipoCuenta;

	private double debeSumaNacional;

	private double haberSumaNacional;

	private double deudorSaldoNacional;

	private double acreedorSaldoNacional;

	private double debeSumaExtranjero;

	private double haberSumaExtranjero;

	private double deudorSaldoExtranjero;

	private double acreedorSaldoExtranjero;

	public EDSumasSaldos() {
		super();
	}
	
	public EDSumasSaldos(Integer idPlanCuenta, String codigoPlanCuenta,
			String nombrePlanCuenta, String clasePlanCuenta,
			Integer idTipoCuenta, String tipoCuenta, double debeSumaNacional,
			double haberSumaNacional, double deudorSaldoNacional,
			double acreedorSaldoNacional, double debeSumaExtranjero,
			double haberSumaExtranjero, double deudorSaldoExtranjero,
			double acreedorSaldoExtranjero) {
		super();
		this.idPlanCuenta = idPlanCuenta;
		this.codigoPlanCuenta = codigoPlanCuenta;
		this.nombrePlanCuenta = nombrePlanCuenta;
		this.clasePlanCuenta = clasePlanCuenta;
		this.idTipoCuenta = idTipoCuenta;
		this.tipoCuenta = tipoCuenta;
		this.debeSumaNacional = debeSumaNacional;
		this.haberSumaNacional = haberSumaNacional;
		this.deudorSaldoNacional = deudorSaldoNacional;
		this.acreedorSaldoNacional = acreedorSaldoNacional;
		this.debeSumaExtranjero = debeSumaExtranjero;
		this.haberSumaExtranjero = haberSumaExtranjero;
		this.deudorSaldoExtranjero = deudorSaldoExtranjero;
		this.acreedorSaldoExtranjero = acreedorSaldoExtranjero;
	}

	public double getDebeSumaNacional() {
		return debeSumaNacional;
	}

	public void setDebeSumaNacional(double debeSumaNacional) {
		this.debeSumaNacional = debeSumaNacional;
	}

	public double getHaberSumaNacional() {
		return haberSumaNacional;
	}

	public void setHaberSumaNacional(double haberSumaNacional) {
		this.haberSumaNacional = haberSumaNacional;
	}

	public double getDeudorSaldoNacional() {
		return deudorSaldoNacional;
	}

	public void setDeudorSaldoNacional(double deudorSaldoNacional) {
		this.deudorSaldoNacional = deudorSaldoNacional;
	}

	public double getAcreedorSaldoNacional() {
		return acreedorSaldoNacional;
	}

	public void setAcreedorSaldoNacional(double acreedorSaldoNacional) {
		this.acreedorSaldoNacional = acreedorSaldoNacional;
	}

	public double getDebeSumaExtranjero() {
		return debeSumaExtranjero;
	}

	public void setDebeSumaExtranjero(double debeSumaExtranjero) {
		this.debeSumaExtranjero = debeSumaExtranjero;
	}

	public double getHaberSumaExtranjero() {
		return haberSumaExtranjero;
	}

	public void setHaberSumaExtranjero(double haberSumaExtranjero) {
		this.haberSumaExtranjero = haberSumaExtranjero;
	}

	public double getDeudorSaldoExtranjero() {
		return deudorSaldoExtranjero;
	}

	public void setDeudorSaldoExtranjero(double deudorSaldoExtranjero) {
		this.deudorSaldoExtranjero = deudorSaldoExtranjero;
	}

	public double getAcreedorSaldoExtranjero() {
		return acreedorSaldoExtranjero;
	}

	public void setAcreedorSaldoExtranjero(double acreedorSaldoExtranjero) {
		this.acreedorSaldoExtranjero = acreedorSaldoExtranjero;
	}

	public Integer getIdPlanCuenta() {
		return idPlanCuenta;
	}

	public void setIdPlanCuenta(Integer idPlanCuenta) {
		this.idPlanCuenta = idPlanCuenta;
	}

	public String getCodigoPlanCuenta() {
		return codigoPlanCuenta;
	}

	public void setCodigoPlanCuenta(String codigoPlanCuenta) {
		this.codigoPlanCuenta = codigoPlanCuenta;
	}

	public String getNombrePlanCuenta() {
		return nombrePlanCuenta;
	}

	public void setNombrePlanCuenta(String nombrePlanCuenta) {
		this.nombrePlanCuenta = nombrePlanCuenta;
	}

	public String getClasePlanCuenta() {
		return clasePlanCuenta;
	}

	public void setClasePlanCuenta(String clasePlanCuenta) {
		this.clasePlanCuenta = clasePlanCuenta;
	}

	public Integer getIdTipoCuenta() {
		return idTipoCuenta;
	}

	public void setIdTipoCuenta(Integer idTipoCuenta) {
		this.idTipoCuenta = idTipoCuenta;
	}

	public String getTipoCuenta() {
		return tipoCuenta;
	}

	public void setTipoCuenta(String tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}



}
