package com.teds.spaps.controller;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import com.teds.spaps.interfaces.dao.ISumasSaldosDao;
import com.teds.spaps.util.SessionMain;
import com.teds.spaps.util.EDSumasSaldos;
import com.teds.spaps.util.FacesUtil;

/**
 * 
 * @author mauriciobejaranorivera
 *
 */
@ManagedBean(name = "estadoResultadoController")
@ViewScoped
public class EstadoResultadoController implements Serializable {

	private static final long serialVersionUID = -4058327498353920610L;

	//DAO
	private @Inject SessionMain sessionMain;
	private @Inject ISumasSaldosDao sumasSaldosDao;

	//VAR
	private String currentPage;
	private Date fechaIni;
	private Date fecha;
	private Date fechaMax;
	private String filtroCuenta;
	private String filtroFecha;
	private String nivel;
	private double totalIngresosNacional;
	private double totalIngresosExtranjero;
	
	private double totalCostosNacional;
	private double totalCostosExtranjero;
	
	private double totalEgresosNacional;
	private double totalEgresosExtranjero;
	
	//LIST
	private List<EDSumasSaldos> listEstadoResultado ;
	private List<EDSumasSaldos> listEstadoResultadoCuenta ;

	//OBJECT

	//treenode

	//STATE
	private boolean crear;
	private boolean registrar;
	private boolean seleccionado;

	@PostConstruct
	public void init() {
		System.out.println("init sumasSaldosController");
		loadDefault();
	}

	public void loadDefault(){
		nivel = "CAPITULO";
		totalIngresosNacional  = 0;
		totalIngresosExtranjero = 0;
		
		totalCostosNacional = 0;
		totalCostosExtranjero = 0;
		
		totalEgresosNacional = 0;
		totalEgresosExtranjero = 0;
		
		filtroCuenta = "TODAS";
		filtroFecha = "FECHAS";
		currentPage = "/pages/contabilidad/report/est_res/list.xhtml";
		setCrear(true);
		setRegistrar(false) ;
		setSeleccionado(false);

		listEstadoResultado = new ArrayList<>();
		listEstadoResultadoCuenta = new ArrayList<>();

		fechaIni  = new Date();
		fecha  = new Date();
		fechaMax = new Date();
	}

	// ------------ ACTION -----------
	
	public void changeNivel(){
		System.out.println("Nivel: "+nivel);
		if(nivel.equals("CAPITULO")){
			
		}else if (nivel.equals("CUENTA")){
			
		}
	}

	public void onRowSelect(SelectEvent event){

	}

	
	public void generarConsulta(){
		listEstadoResultado = new ArrayList<>();
		listEstadoResultadoCuenta = new ArrayList<>();
		//agregando a nivel:capitulo
		listEstadoResultado = sumasSaldosDao.obtenerEstadoResultadoEntreFechas(fechaIni,fecha, sessionMain.getEmpresaLogin());
		//agregando a nivel:cuenta
		EDSumasSaldos ingreso = new EDSumasSaldos();
		ingreso.setAcreedorSaldoExtranjero(-1);
		ingreso.setAcreedorSaldoNacional(-1);
		ingreso.setClasePlanCuenta("");
		ingreso.setCodigoPlanCuenta("INGRESO");
		ingreso.setNombrePlanCuenta("INGRESO");
		ingreso.setDebeSumaExtranjero(-1);
		ingreso.setDebeSumaNacional(-1);
		ingreso.setDeudorSaldoExtranjero(-1);
		ingreso.setDeudorSaldoNacional(-1);
		ingreso.setHaberSumaExtranjero(-1);
		ingreso.setHaberSumaNacional(-1);
		ingreso.setIdPlanCuenta(-1);
		ingreso.setIdTipoCuenta(-1);
		listEstadoResultadoCuenta.add(ingreso);
		EDSumasSaldos costo = new EDSumasSaldos();
		costo.setAcreedorSaldoExtranjero(-1);
		costo.setAcreedorSaldoNacional(-1);
		costo.setClasePlanCuenta("");
		costo.setCodigoPlanCuenta("COSTOS");
		costo.setNombrePlanCuenta("COSTOS");
		costo.setDebeSumaExtranjero(-1);
		costo.setDebeSumaNacional(-1);
		costo.setDeudorSaldoExtranjero(-1);
		costo.setDeudorSaldoNacional(-1);
		costo.setHaberSumaExtranjero(-1);
		costo.setHaberSumaNacional(-1);
		costo.setIdPlanCuenta(-1);
		costo.setIdTipoCuenta(-1);
		listEstadoResultadoCuenta.add(costo);
		EDSumasSaldos egreso = new EDSumasSaldos();
		egreso.setAcreedorSaldoExtranjero(-1);
		egreso.setAcreedorSaldoNacional(-1);
		egreso.setClasePlanCuenta("");
		egreso.setCodigoPlanCuenta("EGRESO");
		egreso.setNombrePlanCuenta("EGRESO");
		egreso.setDebeSumaExtranjero(-1);
		egreso.setDebeSumaNacional(-1);
		egreso.setDeudorSaldoExtranjero(-1);
		egreso.setDeudorSaldoNacional(-1);
		egreso.setHaberSumaExtranjero(-1);
		egreso.setHaberSumaNacional(-1);
		egreso.setIdPlanCuenta(-1);
		egreso.setIdTipoCuenta(-1);
		listEstadoResultadoCuenta.add(egreso);
		if(listEstadoResultado.size()==0){
			FacesUtil.infoMessage("Verificación", "No se encontro transacciones.");
		}

	}

	public String getTotalDebe() {
        int total = 0;
        return new DecimalFormat("###,###.###").format(total);
    }
	
	public String getTotalHaber() {
        int total = 0;
        return new DecimalFormat("###,###.###").format(total);
    }
	
	public String getTotalDeudor() {
        int total = 0;
        return new DecimalFormat("###,###.###").format(total);
    }
	
	public String getTotalAcreedor() {
        int total = 0;
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

	public List<EDSumasSaldos> getListEstadoResultado() {
		return listEstadoResultado;
	}

	public void setListEstadoResultado(List<EDSumasSaldos> listEstadoResultado) {
		this.listEstadoResultado = listEstadoResultado;
	}

	public Date getFechaIni() {
		return fechaIni;
	}

	public void setFechaIni(Date fechaIni) {
		this.fechaIni = fechaIni;
	}

	public double getTotalIngresosNacional() {
		return totalIngresosNacional;
	}

	public void setTotalIngresosNacional(double totalIngresosNacional) {
		this.totalIngresosNacional = totalIngresosNacional;
	}

	public double getTotalIngresosExtranjero() {
		return totalIngresosExtranjero;
	}

	public void setTotalIngresosExtranjero(double totalIngresosExtranjero) {
		this.totalIngresosExtranjero = totalIngresosExtranjero;
	}

	public double getTotalCostosNacional() {
		return totalCostosNacional;
	}

	public void setTotalCostosNacional(double totalCostosNacional) {
		this.totalCostosNacional = totalCostosNacional;
	}

	public double getTotalCostosExtranjero() {
		return totalCostosExtranjero;
	}

	public void setTotalCostosExtranjero(double totalCostosExtranjero) {
		this.totalCostosExtranjero = totalCostosExtranjero;
	}

	public double getTotalEgresosNacional() {
		return totalEgresosNacional;
	}

	public void setTotalEgresosNacional(double totalEgresosNacional) {
		this.totalEgresosNacional = totalEgresosNacional;
	}

	public double getTotalEgresosExtranjero() {
		return totalEgresosExtranjero;
	}

	public void setTotalEgresosExtranjero(double totalEgresosExtranjero) {
		this.totalEgresosExtranjero = totalEgresosExtranjero;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public List<EDSumasSaldos> getListEstadoResultadoCuenta() {
		return listEstadoResultadoCuenta;
	}

	public void setListEstadoResultadoCuenta(
			List<EDSumasSaldos> listEstadoResultadoCuenta) {
		this.listEstadoResultadoCuenta = listEstadoResultadoCuenta;
	}

}
