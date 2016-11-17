package com.teds.spaps.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import com.teds.spaps.interfaces.dao.IFacturaVentaDao;
import com.teds.spaps.model.FacturaVenta;
import com.teds.spaps.util.ReportUtil;

/**
 * 
 * @author mauriciobejaranorivera
 *
 */
@ManagedBean(name="facturacionVentaController")
@ViewScoped
public class FacturacionVentaController implements Serializable{

	private static final long serialVersionUID = 1L;

	//DAO
	private @Inject IFacturaVentaDao facturaVentaDao;

	//OBJECT 
	private FacturaVenta facturaVenta;

	//VAR
	private String currentPage = "/pages/ventas/facturacion/list.xhtml";
	private String urlReport;

	//LIST
	private List<FacturaVenta> listFacturaVenta;

	//STATE
	private boolean nuevo;
	private boolean registrar;
	private boolean seleccionado;
	private boolean verReporte;

	@PostConstruct
	public void init() {
		loadDefault();
	}

	public void loadDefault(){
		urlReport= "";
		verReporte = false;
		nuevo = true;
		seleccionado = false;
		registrar = false;
		facturaVenta = new FacturaVenta();
		listFacturaVenta = facturaVentaDao.obtenerTodasOrdenAscPorId();
	}

	//ACTION

	public void onRowSelect(){
		nuevo = false;
		seleccionado = true;
		registrar = false;
	}
	
	public void cargarReporte(){
		verReporte = true;
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", String.valueOf(facturaVenta.getId()));
		params.put("nroNit", "18923722");
		params.put("ciudad", "Santa Cruz");
		params.put("pais", "Bolivia");
		params.put("empresa", "MI EMPRESA");
		params.put("qrtext", "cualquierdigitoparageneracionqr");
		params.put("leyenda", "LEYENDA PRINCIPAL DE EMPRESA");
		//id,nroNit,ciudad,pais,empresa,qrtext,leyenda
		urlReport = ReportUtil.buildUrl("ReportFacturaVenta", params);
	}

	//SETTERS AND GETTERS

	public boolean isNuevo() {
		return nuevo;
	}

	public void setNuevo(boolean nuevo) {
		this.nuevo = nuevo;
	}

	public boolean isRegistrar() {
		return registrar;
	}

	public void setRegistrar(boolean registrar) {
		this.registrar = registrar;
	}

	public boolean isSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	public FacturaVenta getFacturaVenta() {
		return facturaVenta;
	}

	public void setFacturaVenta(FacturaVenta facturaVenta) {
		this.facturaVenta = facturaVenta;
	}

	public List<FacturaVenta> getListFacturaVenta() {
		return listFacturaVenta;
	}

	public void setListFacturaVenta(List<FacturaVenta> listFacturaVenta) {
		this.listFacturaVenta = listFacturaVenta;
	}

	public boolean isVerReporte() {
		return verReporte;
	}

	public void setVerReporte(boolean verReporte) {
		this.verReporte = verReporte;
	}

	public String getUrlReport() {
		return urlReport;
	}

	public void setUrlReport(String urlReport) {
		this.urlReport = urlReport;
	}


}
