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
@ManagedBean(name = "balanceGeneralController")
@ViewScoped
public class BalanceGeneralController implements Serializable {

	private static final long serialVersionUID = -4058327498353920610L;

	//DAO
	private @Inject SessionMain sessionMain;
	private @Inject ISumasSaldosDao sumasSaldosDao;

	//VAR
	private String currentPage;
	private Date fecha;
	private Date fechaMax;
	private String filtroCuenta;
	private String filtroFecha;

	//LIST
	private List<EDSumasSaldos> listBalanceGeneral ;

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
		filtroCuenta = "TODAS";
		filtroFecha = "FECHAS";
		currentPage = "/pages/contabilidad/report/bal_gen/list.xhtml";
		setCrear(true);
		setRegistrar(false) ;
		setSeleccionado(false);

		listBalanceGeneral = new ArrayList<>();

		fecha  = new Date();
		fechaMax = new Date();
	}

	// ------------ ACTION -----------

	public void onRowSelect(SelectEvent event){

	}

	
	public void generarConsulta(){
		listBalanceGeneral = new ArrayList<>();
		//listBalanceGeneral = sumasSaldosDao.obtenerBalanceHastaFecha(fecha, sessionMain.getCompania());
		if(listBalanceGeneral.size()==0){
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

	public List<EDSumasSaldos> getListBalanceGeneral() {
		return listBalanceGeneral;
	}

	public void setListBalanceGeneral(List<EDSumasSaldos> listBalanceGeneral) {
		this.listBalanceGeneral = listBalanceGeneral;
	}

}
