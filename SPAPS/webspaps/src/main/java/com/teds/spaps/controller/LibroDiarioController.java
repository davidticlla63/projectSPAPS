package com.teds.spaps.controller;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import com.teds.spaps.interfaces.dao.ILineaContableDao;
import com.teds.spaps.model.LineaContable;
import com.teds.spaps.model.MonedaEmpresa;
import com.teds.spaps.session.SessionContable;
import com.teds.spaps.util.SessionMain;
import com.teds.spaps.util.DateUtility;
import com.teds.spaps.util.EDLibroDiario;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.ReportUtil;

/**
 * 
 * @author mauriciobejaranorivera
 *
 */
@ManagedBean(name = "libroDiarioController")
@ViewScoped
public class LibroDiarioController implements Serializable {

	private static final long serialVersionUID = -4058327498353920610L;

	//DAO
	private @Inject SessionMain sessionMain;
	private @Inject ILineaContableDao lineaContableDao;
	private @Inject SessionContable sessionContable;

	//VAR
	private String currentPage;
	private Date fechaIni;
	private Date fechaFin;
	private String filtroCuenta;
	private String filtroFecha;
	private String mes;
	private double totalDebeNacional;
	private double totalHaberNacional;
	private double totalDebeExtranjero;
	private double totalHaberExtranjero;
	private String urlLibroDiario;

	//LIST
	private List<LineaContable> listLineaContable ;
	private List<EDLibroDiario> listLibroDiario ;
	private List<String> listComparacion;

	//OBJECT
	private MonedaEmpresa monedaEmpresa;

	//treenode

	//STATE
	private boolean crear;
	private boolean registrar;
	private boolean seleccionado;
	private boolean verReporte;

	@PostConstruct
	public void init() {
		System.out.println("init libroDiario Controller");
		loadDefault();
	}

	public void loadDefault(){
		mes = String.valueOf(DateUtility.obtenerMesNumeral(new Date()));
		filtroCuenta = "TODAS";
		filtroFecha = "FECHAS";
		currentPage = "/pages/contabilidad/report/libro_diario/list.xhtml";
		monedaEmpresa = sessionContable.getListMonedaEmpresa().get(0);
		urlLibroDiario = "";
		setCrear(true);
		setRegistrar(false) ;
		setSeleccionado(false);
		
		totalDebeNacional = 0;
		totalHaberNacional = 0;
		totalDebeExtranjero = 0;
		totalHaberExtranjero = 0;

		listLibroDiario = new ArrayList<>();
		listComparacion = new ArrayList<>();

		fechaIni  = DateUtility.getPrimerDiaDelMes();
		fechaFin = new Date();

		//listLineaContable = lineaContableDao.obtenerEntreFechasPorEmpresa(fechaIni,fechaFin,sessionMain.getEmpresaLogin());
		//if(listLineaContable.size()==0){
		//	FacesUtil.infoMessage("Verificación","No se encontraron Transacciones");
		//}
	}

	// ------------ ACTION -----------

	public void onRowSelect(SelectEvent event){

	}

	public void changeOneMenuMonedaEmpresa(){
		for(MonedaEmpresa me: sessionContable.getListMonedaEmpresa()){
			if(me.getId().equals(monedaEmpresa.getId())){
				monedaEmpresa = me;
			}
		}
	}
	
	public void cancelarReporte(){
		setCrear(true);
		setRegistrar(false) ;
		setSeleccionado(false);
		setVerReporte(false);
		currentPage = "/pages/contabilidad/report/libro_diario/list.xhtml";
	}
	
	public void generarReporte(){
		setCrear(false);
		setRegistrar(false) ;
		setSeleccionado(false);
		verReporte = true;
		currentPage = "/pages/contabilidad/report/libro_diario/report.xhtml";
		Map<String, String> params = new HashMap<String, String>();
		params.put("pIdMonedaEmpresa",String.valueOf(monedaEmpresa.getId()));
		params.put("pIdEmpresa",String.valueOf(sessionMain.getEmpresaLogin().getId()));
		params.put("pIdGestion",String.valueOf(sessionMain.getGestionLogin().getId()));
		params.put("pUsuario", sessionMain.getUsuarioLogin().getLogin());
		params.put("pFechaInicio",String.valueOf(DateUtility.obtenerFormatoYYYYMMDD(fechaIni)));
		params.put("pFechaFin",String.valueOf(DateUtility.obtenerFormatoYYYYMMDD(fechaFin)));
		urlLibroDiario = ReportUtil.buildUrl("ReportLibroDiario", params);
	}

	public boolean verificarTipoMoneda(String tipo){
		for(MonedaEmpresa me: sessionContable.getListMonedaEmpresa()){
			if(me.getId().equals(monedaEmpresa.getId())){
				if(me.getTipo().equals(tipo)){
					return true;
				}
			}
		}
		return false;
	}

	public String getTotalDebe() {
		System.out.println("debe");
		changeOneMenuMonedaEmpresa();
		if(monedaEmpresa.getTipo().equals("NACIONAL")){
			return new DecimalFormat("###,###.###").format(totalDebeNacional);
		}else{
			return new DecimalFormat("###,###.###").format(totalDebeExtranjero);
		}
	}

	public String getTotalHaber() {
		System.out.println("haber");
		changeOneMenuMonedaEmpresa();
		if(monedaEmpresa.getTipo().equals("NACIONAL")){
			return new DecimalFormat("###,###.###").format(totalHaberNacional);
		}else{
			return new DecimalFormat("###,###.###").format(totalHaberExtranjero);
		}
	}

	public boolean yaSeAgrego(String numeroComprobante){
		boolean sw = listComparacion.contains(numeroComprobante);
		if(!sw){listComparacion.add(numeroComprobante);}
		return sw;
	}

	public void generarConsulta(){
		listLibroDiario = new ArrayList<>();
		listComparacion = new ArrayList<>();
		if(filtroFecha.equals("FECHAS")){
			int valueDate = DateUtility.diferenciasDeFechas(fechaIni, fechaFin); //verificar el orden de las fechas
			if(valueDate<0){
				FacesUtil.infoMessage("Fechas Incorrectas.","");
				return;
			}
			listLineaContable = lineaContableDao.obtenerEntreFechasPorEmpresa(fechaIni,fechaFin,sessionMain.getEmpresaLogin());
		}else{
			listLineaContable = lineaContableDao.obtenerPorPeriodoYEmpresa(mes,"2016",sessionMain.getEmpresaLogin());
		}
		if(listLineaContable.size()==0){
			FacesUtil.infoMessage("Verificación","No se encontraron Transacciones");
		}else{
			seleccionado = true;
		}
		totalDebeNacional = 0;
		totalDebeExtranjero = 0;
		totalHaberNacional = 0;
		totalHaberExtranjero = 0;
		for(LineaContable lc: listLineaContable){
			EDLibroDiario ed = new EDLibroDiario(lc, yaSeAgrego(lc.getComprobante().getCorrelativo()));
			totalDebeNacional = totalDebeNacional + lc.getDebeNacional();
			totalDebeExtranjero = totalDebeExtranjero + lc.getDebeExtranjero();
			totalHaberNacional = totalHaberNacional + lc.getHaberNacional();
			totalHaberExtranjero = totalHaberExtranjero + lc.getHaberExtranjero();
			listLibroDiario.add(ed);
		}
	}


	// ------------ GET AND SET -----------

	public List<LineaContable> getListLineaContable() {
		return listLineaContable;
	}

	public void setListLineaContable(List<LineaContable> listLineaContable) {
		this.listLineaContable = listLineaContable;
	}

	public boolean isCrear() {
		return crear;
	}

	public void setCrear(boolean crear) {
		this.crear = crear;
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

	public String getPage() {
		return currentPage;
	}

	public void setPage(String currentPage) {
		this.currentPage = currentPage;
	}

	public Date getFechaIni() {
		return fechaIni;
	}

	public void setFechaIni(Date fechaIni) {
		this.fechaIni = fechaIni;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getFiltroCuenta() {
		return filtroCuenta;
	}

	public void setFiltroCuenta(String filtroCuenta) {
		this.filtroCuenta = filtroCuenta;
	}

	public String getFiltroFecha() {
		return filtroFecha;
	}

	public void setFiltroFecha(String filtroFecha) {
		this.filtroFecha = filtroFecha;
	}

	public List<EDLibroDiario> getListLibroDiario() {
		return listLibroDiario;
	}

	public void setListLibroDiario(List<EDLibroDiario> listLibroDiario) {
		this.listLibroDiario = listLibroDiario;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public MonedaEmpresa getMonedaEmpresa() {
		return monedaEmpresa;
	}

	public void setMonedaEmpresa(MonedaEmpresa monedaEmpresa) {
		this.monedaEmpresa = monedaEmpresa;
	}

	public double getTotalDebeNacional() {
		return totalDebeNacional;
	}

	public void setTotalDebeNacional(double totalDebeNacional) {
		this.totalDebeNacional = totalDebeNacional;
	}

	public double getTotalHaberNacional() {
		return totalHaberNacional;
	}

	public void setTotalHaberNacional(double totalHaberNacional) {
		this.totalHaberNacional = totalHaberNacional;
	}

	public double getTotalDebeExtranjero() {
		return totalDebeExtranjero;
	}

	public void setTotalDebeExtranjero(double totalDebeExtranjero) {
		this.totalDebeExtranjero = totalDebeExtranjero;
	}

	public double getTotalHaberExtranjero() {
		return totalHaberExtranjero;
	}

	public void setTotalHaberExtranjero(double totalHaberExtranjero) {
		this.totalHaberExtranjero = totalHaberExtranjero;
	}

	public String getUrlLibroDiario() {
		return urlLibroDiario;
	}

	public void setUrlLibroDiario(String urlLibroDiario) {
		this.urlLibroDiario = urlLibroDiario;
	}

	public boolean isVerReporte() {
		return verReporte;
	}

	public void setVerReporte(boolean verReporte) {
		this.verReporte = verReporte;
	}

}
