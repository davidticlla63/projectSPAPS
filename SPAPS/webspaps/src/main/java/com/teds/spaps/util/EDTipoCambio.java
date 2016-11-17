/**
 * 
 */
package com.teds.spaps.util;

import java.util.Date;

import com.teds.spaps.model.TipoCambio;
import com.teds.spaps.model.TipoCambioUfv;

/**
 * @author mauriciobejaranorivera
 *
 */
public class EDTipoCambio {
	private Integer id;

	private Date fecha;
	
	private TipoCambio tipoCambio;
	
	private TipoCambioUfv tipoCambioUfv;
	
	public EDTipoCambio() {
		super();
		this.id = 0;
		this.fecha = new Date();
		this.tipoCambio = new TipoCambio();
		this.tipoCambioUfv = new TipoCambioUfv();
	}
	
	public EDTipoCambio(Integer id,Date fecha, TipoCambio tipoCambio,
			TipoCambioUfv tipoCambioUfv) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.tipoCambio = tipoCambio;
		this.tipoCambioUfv = tipoCambioUfv;
	}

	public TipoCambio getTipoCambio() {
		return tipoCambio;
	}
	
	public void setTipoCambio(TipoCambio tipoCambio) {
		this.tipoCambio = tipoCambio;
	}
	
	public TipoCambioUfv getTipoCambioUfv() {
		return tipoCambioUfv;
	}
	
	public void setTipoCambioUfv(TipoCambioUfv tipoCambioUfv) {
		this.tipoCambioUfv = tipoCambioUfv;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	

}
