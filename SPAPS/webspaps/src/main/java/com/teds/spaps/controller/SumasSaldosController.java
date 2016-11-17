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

import com.teds.spaps.interfaces.dao.ISumasSaldosDao;
import com.teds.spaps.model.MonedaEmpresa;
import com.teds.spaps.session.SessionContable;
import com.teds.spaps.util.SessionMain;
import com.teds.spaps.util.DateUtility;
import com.teds.spaps.util.EDSumasSaldos;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.ReportUtil;

/**
 * 
 * @author mauriciobejaranorivera
 *
 */
@ManagedBean(name = "sumasSaldosController")
@ViewScoped
public class SumasSaldosController implements Serializable {

	private static final long serialVersionUID = -4058327498353920610L;

	//DAO
	private @Inject SessionMain sessionMain;
	private @Inject ISumasSaldosDao sumasSaldosDao;
	private @Inject SessionContable sessionContable;

	//VAR
	private String currentPage;
	private Date fecha;
	private Date fechaMax;
	private String filtroCuenta;
	private String filtroFecha;
	private String urlSumaSaldo;

	//LIST
	private List<EDSumasSaldos> listSumasSaldos ;

	//OBJECT
	private MonedaEmpresa monedaEmpresa;


	//STATE
	private boolean crear;
	private boolean registrar;
	private boolean seleccionado;
	private boolean verReporte;

	@PostConstruct
	public void init() {
		System.out.println("init sumasSaldosController");
		loadDefault();
	}

	public void loadDefault(){
		urlSumaSaldo = "";
		verReporte= false;
		filtroCuenta = "TODAS";
		filtroFecha = "FECHAS";
		currentPage = "/pages/contabilidad/report/sum_sald/list.xhtml";
		setCrear(true);
		setRegistrar(false) ;
		setSeleccionado(false);
		monedaEmpresa = sessionContable.getListMonedaEmpresa().get(0);
		listSumasSaldos = new ArrayList<>();

		fecha  = new Date();
		fechaMax = new Date();
	}

	// ------------ ACTION -----------

	public void onRowSelect(SelectEvent event){

	}

	public void cancelarReporte(){
		setCrear(true);
		setRegistrar(false) ;
		setSeleccionado(false);
		setVerReporte(false);
		currentPage = "/pages/contabilidad/report/sum_sald/list.xhtml";
	}
	
	public void generarReporte(){
		setCrear(false);
		setRegistrar(false) ;
		setSeleccionado(false);
		verReporte = true;
		currentPage = "/pages/contabilidad/report/sum_sald/report.xhtml";
		changeOneMenuMonedaEmpresa();
		Map<String, String> params = new HashMap<String, String>();
		params.put("pIdEmpresa",String.valueOf(sessionMain.getEmpresaLogin().getId()));
		params.put("pUsuario", sessionMain.getUsuarioLogin().getLogin());
		params.put("pFechaFin",String.valueOf(DateUtility.obtenerFormatoYYYYMMDD(fecha)));
		params.put("pTitulo","Titulo");
		params.put("pTipoMoneda",monedaEmpresa.getTipo());
		params.put("pSimboloMoneda",monedaEmpresa.getSimbolo());
		System.out.println("- OK - maps: "+params);
		urlSumaSaldo = ReportUtil.buildUrl("ReportSumasSaldos", params);
		
	}
	
	public void generarConsulta(){
		listSumasSaldos = new ArrayList<>();
		listSumasSaldos = sumasSaldosDao.obtenerTodosHastaFecha(fecha, sessionMain.getEmpresaLogin());
		if(listSumasSaldos.size()==0){
			FacesUtil.infoMessage("Verificación", "No se encontro transacciones.");
		}else{
			seleccionado = true;
		}
	}
	
	public void changeOneMenuMonedaEmpresa(){
		for(MonedaEmpresa me: sessionContable.getListMonedaEmpresa()){
			if(me.getId().equals(monedaEmpresa.getId())){
				monedaEmpresa = me;
			}
		}
	}

	public String getTotalDebe() {
        double total = 0;
        return new DecimalFormat("###,###.###").format(total);
    }
	
	public String getTotalHaber() {
		double total = 0;
        return new DecimalFormat("###,###.###").format(total);
    }
	
	public String getTotalDeudor() {
		double total = 0;
        return new DecimalFormat("###,###.###").format(total);
    }
	
	public String getTotalAcreedor() {
		double total = 0;
        return new DecimalFormat("###,###.###").format(total);
    }

	// ------------ GET AND SET -----------

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

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Date getFechaMax() {
		return fechaMax;
	}

	public void setFechaMax(Date fechaMax) {
		this.fechaMax = fechaMax;
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

	public List<EDSumasSaldos> getListSumasSaldos() {
		return listSumasSaldos;
	}

	public void setListSumasSaldos(List<EDSumasSaldos> listSumasSaldos) {
		this.listSumasSaldos = listSumasSaldos;
	}

	public boolean isVerReporte() {
		return verReporte;
	}

	public void setVerReporte(boolean verReporte) {
		this.verReporte = verReporte;
	}

	public String getUrlSumaSaldo() {
		return urlSumaSaldo;
	}

	public void setUrlSumaSaldo(String urlSumaSaldo) {
		this.urlSumaSaldo = urlSumaSaldo;
	}

	public MonedaEmpresa getMonedaEmpresa() {
		return monedaEmpresa;
	}

	public void setMonedaEmpresa(MonedaEmpresa monedaEmpresa) {
		this.monedaEmpresa = monedaEmpresa;
	}

}
