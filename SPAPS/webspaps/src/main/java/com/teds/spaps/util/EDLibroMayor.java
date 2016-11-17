/**
 * 
 */
package com.teds.spaps.util;

import com.teds.spaps.model.Comprobante;

/**
 * @author mauriciobejaranorivera
 *
 */
public class EDLibroMayor {

	private Integer id;
	
	private String dato;
	
	private Comprobante comprobante;
	
	private double debeNacional;
	
	private double debeExtranjero;
	
	private double haberNacional;
	
	private double haberExtranjero;
	
	private double saldoNacional;
	
	private double saldoExtranjero;
	
	public EDLibroMayor(Integer id, String dato,Comprobante comprobante,double debeNacional,double debeExtranjero,double haberNacional,double haberExtranjero, double saldoNacional,double saldoExtranjero) {
		super();
		this.id = id;
		this.dato = dato;
		this.comprobante = comprobante;
		this.debeNacional = debeNacional;
		this.debeExtranjero = debeExtranjero;
		this.haberNacional = haberNacional;
		this.haberExtranjero = haberExtranjero;
		this.saldoNacional = saldoNacional;
		this.saldoExtranjero = saldoExtranjero;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getDato() {
		return dato;
	}
	
	public void setDato(String dato) {
		this.dato = dato;
	}

	public double getSaldoNacional() {
		return saldoNacional;
	}

	public void setSaldoNacional(double saldoNacional) {
		this.saldoNacional = saldoNacional;
	}

	public double getSaldoExtranjero() {
		return saldoExtranjero;
	}

	public void setSaldoExtranjero(double saldoExtranjero) {
		this.saldoExtranjero = saldoExtranjero;
	}

	public double getDebeNacional() {
		return debeNacional;
	}

	public void setDebeNacional(double debeNacional) {
		this.debeNacional = debeNacional;
	}

	public double getDebeExtranjero() {
		return debeExtranjero;
	}

	public void setDebeExtranjero(double debeExtranjero) {
		this.debeExtranjero = debeExtranjero;
	}

	public double getHaberNacional() {
		return haberNacional;
	}

	public void setHaberNacional(double haberNacional) {
		this.haberNacional = haberNacional;
	}

	public double getHaberExtranjero() {
		return haberExtranjero;
	}

	public void setHaberExtranjero(double haberExtranjero) {
		this.haberExtranjero = haberExtranjero;
	}

	public Comprobante getComprobante() {
		return comprobante;
	}

	public void setComprobante(Comprobante comprobante) {
		this.comprobante = comprobante;
	}
	
	
}
