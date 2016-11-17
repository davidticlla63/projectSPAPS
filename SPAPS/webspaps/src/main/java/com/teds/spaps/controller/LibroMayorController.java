package com.teds.spaps.controller;

import java.io.Serializable;
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

import com.teds.spaps.interfaces.dao.IComprobanteDao;
import com.teds.spaps.interfaces.dao.ILineaContableDao;
import com.teds.spaps.model.Comprobante;
import com.teds.spaps.model.LineaContable;
import com.teds.spaps.model.PlanCuenta;
import com.teds.spaps.session.SessionContable;
import com.teds.spaps.util.SessionMain;
import com.teds.spaps.util.DateUtility;
import com.teds.spaps.util.EDLibroMayor;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.ReportUtil;

/**
 * 
 * @author mauriciobejaranorivera
 *
 */
@ManagedBean(name = "libroMayorController")
@ViewScoped
public class LibroMayorController implements Serializable {

	private static final long serialVersionUID = -4058327498353920610L;

	//DAO
	private @Inject SessionContable sessionContable;
	private @Inject SessionMain sessionMain;
	private @Inject IComprobanteDao comprobanteDao;
	private @Inject ILineaContableDao lineaContableDao;

	//VAR
	private String currentPage;
	private Date fechaIni;
	private Date fechaFin;
	private EDLibroMayor edLibroMayor;
	private double saldoNacional;
	private double saldoExtranjero;
	private String filtroCuenta;
	private String filtroFecha;
	private double totalDebeNacional;
	private double totalDebeExtranjero;
	private double totalHaberNacional;
	private double totalHaberExtranjero;
	private double totalSaldoNacional;
	private double totalSaldoExtranjero;
	private String urlLibroMayor;

	//LIST
	private List<EDLibroMayor> listMayores;

	//OBJECT
	private PlanCuenta planCuentaInicial;
	private PlanCuenta planCuentaFinal;
	private PlanCuenta planCuenta;

	//STATE
	private boolean crear;
	private boolean registrar;
	private boolean seleccionado;
	private boolean habilitarPaginadorIzq;
	private boolean habilitarPaginadorDer;
	private boolean verReporte;

	@PostConstruct
	public void init() {
		System.out.println("init libroMayor");
		loadDefault();
	}

	public void loadDefault(){
		currentPage = "/pages/contabilidad/report/libro_mayor/list.xhtml";
		urlLibroMayor = "";
		saldoNacional = 0;
		saldoExtranjero = 0;
		totalDebeNacional = 0;
		totalDebeExtranjero = 0;
		totalHaberNacional = 0;
		totalHaberExtranjero = 0;

		totalSaldoNacional = 0;
		totalSaldoExtranjero = 0;

		filtroCuenta = "TODAS";
		filtroFecha = "FECHAS";

		habilitarPaginadorIzq = false;
		habilitarPaginadorDer = false;
		verReporte = false;	

		setCrear(true);
		setRegistrar(false) ;
		setSeleccionado(false);

		planCuentaInicial = obtenerPrimerCuenta();
		planCuentaFinal = obtenerUltimaCuenta();
		planCuenta = planCuentaInicial;

		listMayores = new ArrayList<EDLibroMayor>();

		fechaIni  = DateUtility.getPrimerDiaDelMes();
		fechaFin = new Date();
	}

	// ------------ ACTION -----------

	public void changeFilterCuenta(){
		if(filtroCuenta.equals("TODAS")){
			//planCuentaInicial = obtenerPrimerCuenta();
			//planCuentaFinal = obtenerUltimaCuenta();
			//planCuenta = planCuentaInicial;
		}else if (filtroCuenta.equals("SELECCION")){
			//planCuenta = new PlanCuenta();
			//planCuentaInicial = new PlanCuenta();
			//planCuentaFinal = new PlanCuenta();
		}else if(filtroCuenta.equals("RANGO")){
			//planCuenta = new PlanCuenta();
			//planCuentaInicial = new PlanCuenta();
			//planCuentaFinal = new PlanCuenta();
		}
	}

	public void onRowSelect(SelectEvent event){

	}

	public void onItemSelectCuentaInicial(SelectEvent event) {
		planCuentaInicial = obtenerPlanCuentaPorDescripcion(((PlanCuenta) event.getObject()).getDescripcion());
	}

	public void onItemSelectCuentaFinal(SelectEvent event) {
		planCuentaFinal = obtenerPlanCuentaPorDescripcion(((PlanCuenta) event.getObject()).getDescripcion());
	}

	public PlanCuenta obtenerPlanCuentaPorDescripcion(String descripcion){
		for(PlanCuenta pc: sessionContable.getListPlanCuentaAuxiliar()){
			if(pc.getDescripcion().equals(descripcion)){
				return pc;
			}
			//if(pc.getId().equals(id)){
			//	return pc;
			//}
		}
		return null;
	}

	// -----------  PROCESS ----

	public PlanCuenta obtenerPrimerCuenta(){
		//verificar que no obtenga cuentas eliminadas
		if(sessionContable.getListPlanCuentaAuxiliarActivas().size()==0){
			return new PlanCuenta();
		}
		PlanCuenta aux = sessionContable.getListPlanCuentaAuxiliarActivas().get(0);
		Integer codAux = Integer.valueOf(aux.getCodigo());
		for(PlanCuenta pc: sessionContable.getListPlanCuentaAuxiliar()){
			Integer codigo = Integer.valueOf(pc.getCodigo());
			if(codigo<codAux){
				codAux = codigo;
				aux = pc;
			}
		}
		return aux;
	}

	public PlanCuenta obtenerUltimaCuenta(){
		//verificar que no obtenga cuentas eliminadas
		if(sessionContable.getListPlanCuentaAuxiliarActivas().size()==0){
			return new PlanCuenta();
		}
		PlanCuenta aux = sessionContable.getListPlanCuentaAuxiliar().get(0);
		Integer codAux = Integer.valueOf(aux.getCodigo());
		for(PlanCuenta pc: sessionContable.getListPlanCuentaAuxiliar()){
			Integer codigo = Integer.valueOf(pc.getCodigo());
			if(codigo>codAux){
				codAux = codigo;
				aux = pc;
			}
		}
		return aux;
	}

	public void generarConsulta(){
		loadConsulta(true);
	}
	
	public void generarReporte(){
		setCrear(false);
		setRegistrar(false) ;
		setSeleccionado(false);
		verReporte = true;
		currentPage = "/pages/contabilidad/report/libro_mayor/report.xhtml";
		Map<String,String> map1 = new HashMap<>();
		map1.put("pUsuario", sessionMain.getUsuarioLogin().getNombre());
		map1.put("pFechaInicio",DateUtility.obtenerFormatoYYYYMMDD(fechaIni));
		map1.put("pFechaFin",DateUtility.obtenerFormatoYYYYMMDD(fechaFin));
		map1.put("pGestion",String.valueOf(sessionMain.getGestionLogin().getId()));
		map1.put("pIdEmpresa",String.valueOf(sessionMain.getEmpresaLogin().getId()));
		map1.put("pIdPlanCuenta", String.valueOf(planCuenta.getId()));
		//---URL COMPROBANTE
		urlLibroMayor = ReportUtil.buildUrl("ReportLibroMayor",map1);
	}
	
	/**
	 * 
	 * @param executePlanCuenta swicht permite la captura de la cuenta inicial,
	 * esto sucede cuando el usuario genera la consulta la primera vez entonces es true
	 * 
	 */
	private void loadConsulta(boolean executePlanCuenta){
		System.out.println("generarConsulta()");
		//validacion
		if(filtroCuenta.equals("SELECCION") || filtroCuenta.equals("RANGO")){
			if(planCuentaInicial.getDescripcion()==null || planCuentaFinal.getDescripcion()==null){
				FacesUtil.infoMessage("Verificación","Seleccione las cuentas");
				return;
			}
			if(executePlanCuenta){
				planCuenta = obtenerPlanCuentaPorDescripcion(planCuentaInicial.getDescripcion());
			}
		} else if(filtroFecha.equals("FECHAS")){
			int valueDate = DateUtility.diferenciasDeFechas(fechaIni, fechaFin); //verificar el orden de las fechas
			if(valueDate<0){
				FacesUtil.infoMessage("Fechas Incorrectas.","");
				return;
			}
		}
		listMayores = new ArrayList<>();
		List<Comprobante>  listComprobante=  comprobanteDao.obtenerEntreFechasPorCuenta(fechaIni, fechaFin,planCuenta, sessionMain.getEmpresaLogin());

		if(listComprobante.size()==0){
			FacesUtil.infoMessage("Verificación", "Sin Transacciones.");
		}
		//---------
		//aqui obtener el saldo anterior a la fechaIni
		saldoNacional = 0;
		saldoExtranjero = 0;
		List<Comprobante>  listComprobanteAnteriores =  comprobanteDao.obtenerMenoresAFechaPorCuenta(fechaIni,planCuenta, sessionMain.getEmpresaLogin());
		EDLibroMayor mayorAnteriores = new EDLibroMayor(0, "data",null, 0, 0, 0, 0, saldoNacional, saldoExtranjero);
		for(Comprobante comprobante: listComprobanteAnteriores){
			List<LineaContable> listLineaContableAnteriores = lineaContableDao.obtenerPorComprobanteYCuenta(comprobante,planCuenta);
			for(LineaContable linea: listLineaContableAnteriores){
				EDLibroMayor mayor = generarLibroMayor(comprobante,linea, mayorAnteriores);
				saldoNacional = saldoNacional - mayor.getSaldoNacional();
				saldoExtranjero = saldoExtranjero - mayor.getSaldoExtranjero();
			}
		}
		//---------
		EDLibroMayor mayorAnterior = new EDLibroMayor(0, "data",null, 0, 0, 0, 0, saldoNacional, saldoExtranjero);
		for(Comprobante comprobante: listComprobante){
			List<LineaContable> listLineaContable = lineaContableDao.obtenerPorComprobanteYCuenta(comprobante,planCuenta);
			for(LineaContable linea: listLineaContable){
				EDLibroMayor mayor = generarLibroMayor(comprobante,linea, mayorAnterior);
				listMayores.add(mayor);
				mayorAnterior = mayor;
			}
		}
		calcularTotales();
		//verificar los botones si se habilitan si hay mas cuentas hacia atras
		verificacionCuentaParaButtonAnterior();

		//verificar los botones si se habilitan si hay mas cuentas hacia adelante
		verificacionCuentaParaButtonSiguiente();
	}

	private EDLibroMayor generarLibroMayor(Comprobante comprobante,LineaContable linea,EDLibroMayor mayorAnterior){
		//FORMULA
		// DEBE - HABER = SALDO + SALDO ANTERIOR 
		Integer id = 0 ;
		double debeNacional = linea.getDebeNacional();
		double debeExtranjero = linea.getDebeExtranjero();
		double haberNacional = linea.getHaberNacional();
		double haberExtranjero = linea.getHaberExtranjero();
		double saldoNacional = (debeNacional - haberNacional) + mayorAnterior.getSaldoNacional();
		double saldoExtranjero = (debeExtranjero - haberExtranjero) + mayorAnterior.getSaldoExtranjero();
		EDLibroMayor edLibroMayor = new EDLibroMayor(id , "data",comprobante,debeNacional,debeExtranjero,haberNacional,haberExtranjero, saldoNacional,saldoExtranjero);
		return edLibroMayor;
	}

	private void calcularTotales(){
		totalDebeNacional = 0;
		totalDebeExtranjero = 0;
		totalHaberNacional = 0;
		totalHaberExtranjero = 0;
		for(EDLibroMayor ed: listMayores){
			totalDebeNacional = totalDebeNacional + ed.getDebeNacional();
			totalDebeExtranjero = totalDebeExtranjero + ed.getDebeNacional();
			totalHaberNacional = totalHaberNacional + ed.getDebeNacional();
			totalHaberExtranjero = totalHaberExtranjero + ed.getDebeNacional();
		}
	}

	public void anteriorCuenta(){
		//obtener la anterior cuenta
		int actualCuenta = sessionContable.getListPlanCuentaAuxiliarActivas().indexOf(planCuenta);
		planCuenta = sessionContable.getListPlanCuentaAuxiliarActivas().get(actualCuenta-1);
		loadConsulta(false);
	}

	public void siguienteCuenta(){
		//obtener la siguiente cuenta
		int actualCuenta = sessionContable.getListPlanCuentaAuxiliarActivas().indexOf(planCuenta);
		planCuenta = sessionContable.getListPlanCuentaAuxiliarActivas().get(actualCuenta+1);
		loadConsulta(false);
	}

	private void verificacionCuentaParaButtonSiguiente(){
		int size = sessionContable.getListPlanCuentaAuxiliarActivas().size() - 1;
		int actualCuenta = sessionContable.getListPlanCuentaAuxiliarActivas().indexOf(planCuenta);
		habilitarPaginadorDer = (size - actualCuenta)>0;
	}

	private void verificacionCuentaParaButtonAnterior(){
		int actualCuenta = sessionContable.getListPlanCuentaAuxiliarActivas().indexOf(planCuenta);
		habilitarPaginadorIzq = actualCuenta>0;
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

	public List<EDLibroMayor> getListMayores() {
		return listMayores;
	}

	public void setListMayores(List<EDLibroMayor> listMayores) {
		this.listMayores = listMayores;
	}

	public EDLibroMayor getEdLibroMayor() {
		return edLibroMayor;
	}

	public void setEdLibroMayor(EDLibroMayor edLibroMayor) {
		this.edLibroMayor = edLibroMayor;
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

	public PlanCuenta getPlanCuentaInicial() {
		return planCuentaInicial;
	}

	public void setPlanCuentaInicial(PlanCuenta planCuentaInicial) {
		this.planCuentaInicial = planCuentaInicial;
	}

	public PlanCuenta getPlanCuentaFinal() {
		return planCuentaFinal;
	}

	public void setPlanCuentaFinal(PlanCuenta planCuentaFinal) {
		this.planCuentaFinal = planCuentaFinal;
	}

	public PlanCuenta getPlanCuenta() {
		return planCuenta;
	}

	public void setPlanCuenta(PlanCuenta planCuenta) {
		this.planCuenta = planCuenta;
	}

	public boolean isHabilitarPaginadorIzq() {
		return habilitarPaginadorIzq;
	}

	public void setHabilitarPaginadorIzq(boolean habilitarPaginadorIzq) {
		this.habilitarPaginadorIzq = habilitarPaginadorIzq;
	}

	public boolean isHabilitarPaginadorDer() {
		return habilitarPaginadorDer;
	}

	public void setHabilitarPaginadorDer(boolean habilitarPaginadorDer) {
		this.habilitarPaginadorDer = habilitarPaginadorDer;
	}

	public double getTotalDebeNacional() {
		return totalDebeNacional;
	}

	public void setTotalDebeNacional(double totalDebeNacional) {
		this.totalDebeNacional = totalDebeNacional;
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

	public double getTotalHaberNacional() {
		return totalHaberNacional;
	}

	public void setTotalHaberNacional(double totalHaberNacional) {
		this.totalHaberNacional = totalHaberNacional;
	}

	public double getTotalSaldoNacional() {
		return totalSaldoNacional;
	}

	public void setTotalSaldoNacional(double totalSaldoNacional) {
		this.totalSaldoNacional = totalSaldoNacional;
	}

	public double getTotalSaldoExtranjero() {
		return totalSaldoExtranjero;
	}

	public void setTotalSaldoExtranjero(double totalSaldoExtranjero) {
		this.totalSaldoExtranjero = totalSaldoExtranjero;
	}

	public String getUrlLibroMayor() {
		return urlLibroMayor;
	}

	public void setUrlLibroMayor(String urlLibroMayor) {
		this.urlLibroMayor = urlLibroMayor;
	}

	public boolean isVerReporte() {
		return verReporte;
	}

	public void setVerReporte(boolean verReporte) {
		this.verReporte = verReporte;
	}

}
