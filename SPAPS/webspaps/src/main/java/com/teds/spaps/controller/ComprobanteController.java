package com.teds.spaps.controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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
import org.primefaces.model.TreeNode;

import com.teds.spaps.util.ReportUtil;
import com.teds.spaps.interfaces.dao.IComprobanteDao;
import com.teds.spaps.model.CentroCosto;
import com.teds.spaps.model.Comprobante;
import com.teds.spaps.model.GrupoImpuesto;
import com.teds.spaps.model.LineaContable;
import com.teds.spaps.model.MonedaEmpresa;
import com.teds.spaps.model.ParametroEmpresa;
import com.teds.spaps.model.PlanCuenta;
import com.teds.spaps.model.TipoCambio;
import com.teds.spaps.model.TipoComprobante;
import com.teds.spaps.session.SessionContable;
import com.teds.spaps.util.SessionMain;
import com.teds.spaps.util.EDAsiento;
import com.teds.spaps.util.FacesUtil;

/**
 * 
 * @author mauriciobejaranorivera
 *
 */
@ManagedBean(name = "comprobanteController")
@ViewScoped
public class ComprobanteController implements Serializable {

	private static final long serialVersionUID = -4058327498353920610L;

	//DAO
	private @Inject IComprobanteDao comprobanteDao;
	private @Inject SessionMain sessionMain;
	private @Inject SessionContable sessionContable;

	//VAR
	private String infoDialog;
	private String tituloPanel;
	private String urlComprobante;
	private String currentPage;
	private String currentTipoComprobante;

	//LIST
	private List<Comprobante> listComprobante;
	private List<LineaContable> listLineaContable ;
	private List<PlanCuenta> listCuentasAuxiliares;
	private List<CentroCosto> listCentroCosto;
	private String[] arrayNombrePersona = {"Recibido De:","Pagado A:","Concepto de:","Concepto de:"};
	private List<EDAsiento> listEDAsiento;
	private List<GrupoImpuesto> listGrupoImpuesto;
	private Double[] arrayMonto = {0d,0d,0d,0d,0d,0d,0d,0d,0d,0d};
	private List<Double> listMontoNacional;

	//OBJECT
	private TipoComprobante tipoComprobante;
	private Comprobante comprobante;
	private LineaContable lineaContable;
	private PlanCuenta planCuenta;
	private TipoCambio tipoCambio;
	private MonedaEmpresa monedaEmpresa;
	private ParametroEmpresa parametroEmpresa;
	private EDAsiento eDAsiento;
	//private Sucursal sucursal;

	//treenode
	private TreeNode selectedNodeCentroCosto;
	private TreeNode selectedNodeCuenta;

	//STATE
	private boolean crear;
	private boolean registrar;
	private boolean seleccionado;
	private boolean verReporte;

	@PostConstruct
	public void init() {
		System.out.println("init comprobanteController");
		loadDefault();
	}

	public void loadDefault(){
		currentPage = "/pages/contabilidad/comprobante/list.xhtml";
		currentTipoComprobante = "/pages/contabilidad/comprobante/container_ingreso.xhtml";
		setCrear(true);
		setRegistrar(false) ;
		setSeleccionado(false);
		verReporte = false;

		listComprobante = comprobanteDao.obtenerPorEmpresaGesstion(sessionMain.getEmpresaLogin(), sessionMain.getGestionLogin());
	}

	// ------------ ACTION -----------

	public void onRowSelect(SelectEvent event){

	}
	
	//ACtion que actualiza la segunda glosa, siempre y cuando este vacia
	public void actualizarGlosa(){
		if(eDAsiento.getGlosa().isEmpty()){
			String glosa = comprobante.getGlosa().length()>50?comprobante.getGlosa().substring(0, 50):comprobante.getGlosa(); 
			eDAsiento.setGlosa(glosa);
		}
	}

	public void actionNuevo(){
		currentPage = "/pages/contabilidad/comprobante/edit.xhtml";
		tituloPanel = "Comprobante";

		crear = false;
		seleccionado = false;
		registrar = true;

		eDAsiento = new EDAsiento();
		planCuenta = new PlanCuenta();

		listMontoNacional =new ArrayList<Double>();
		monedaEmpresa = new MonedaEmpresa();
		monedaEmpresa = sessionContable.getListMonedaEmpresa().get(0);
		lineaContable = new LineaContable();

		tipoCambio = new TipoCambio();
		tipoCambio.setUnidad(6.96);

		tipoComprobante = new TipoComprobante();
		tipoComprobante = sessionContable.getListTipoComprobante().get(0);

		//sucursal = sessionMain.getListSucursal().get(0);
		comprobante = new Comprobante();
		comprobante.setFecha(new Date());
		comprobante.setTipoCambio(tipoCambio.getUnidad());
		comprobante.setNumero(comprobanteDao.obtenerNumeroComprobante(comprobante.getFecha(),sessionMain.getEmpresaLogin(),tipoComprobante));
		comprobante.setCorrelativo(getPatternCorrelativo(comprobante.getNumero()));
		comprobante.setEmpresa(sessionMain.getEmpresaLogin());
		comprobante.setEstado("AC");
		comprobante.setGestion(sessionMain.getGestionLogin());
		comprobante.setSucursal(sessionMain.getListSucursal().get(0));

		listLineaContable = new ArrayList<>();
		listEDAsiento = new ArrayList<>();
		listGrupoImpuesto = new ArrayList<>();

		listCentroCosto = sessionContable.getListCentroCosto();
		listCuentasAuxiliares = sessionContable.getListPlanCuentaAuxiliar();

		//verificar si la empresa utilizara centro de costo
		parametroEmpresa = sessionMain.getParametroEmpresa();
		if(parametroEmpresa.isCentroCosto()){
			setListCentroCosto(sessionContable.getListCentroCosto());
		}
	}

	public void changeOneMenuMonedaEmpresa(){
		for(MonedaEmpresa me: sessionContable.getListMonedaEmpresa()){
			if(me.getId().equals(monedaEmpresa.getId())){
				monedaEmpresa = me;
			}
		}
	}

	public void changeOneMenuTipoComprobante(){
		for(TipoComprobante tc: sessionContable.getListTipoComprobante()){
			if(tc.getId().equals(tipoComprobante.getId())){
				tipoComprobante = tc;
				//generar correlativo
				comprobante.setNumero(comprobanteDao.obtenerNumeroComprobante(comprobante.getFecha(),sessionMain.getEmpresaLogin(),tipoComprobante));
				comprobante.setCorrelativo(getPatternCorrelativo(comprobante.getNumero()));
			}
		}
	}

	public void actionCancelar(){
		seleccionado = false;
		crear = true;
		registrar = false;
		verReporte = false;
		currentPage = "/pages/contabilidad/comprobante/list.xhtml";
	}

	public void actionModificar(){

	}

	private String getPatternCorrelativo(int comprobante){
		// pather = "1508-000001";
		StringBuilder build = new StringBuilder();
		Date fecha = new Date(); 
		String year = new SimpleDateFormat("yy").format(fecha);
		String mes = new SimpleDateFormat("MM").format(fecha);
		return build.append(tipoComprobante.getSigla()).append(year).append(mes).append("-").append(String.format("%06d", comprobante)).toString();
	}

	public void onItemSelectCuenta(SelectEvent event) {
		planCuenta = obtenerPlanCuentaPorDescripcion(((PlanCuenta) event.getObject()).getDescripcion());
		eDAsiento.setCuenta(planCuenta);
	}

	public void agregarCuentaADetalle(){
		//validacion
		if(eDAsiento.getCuenta().getId().equals(0)){
			FacesUtil.infoMessage("Verificación", "Seleccione una cuenta");
		}
		//if(eDAsiento.getDebeNacional()==0 || eDAsiento.getHaberNacional()==0){
		//	FacesUtil.infoMessage("Verificación", "Montos no válidos");
		//}
		listMontoNacional.add(0, eDAsiento.getDebeNacional()==0?eDAsiento.getHaberNacional():eDAsiento.getDebeNacional());
		int cont = listEDAsiento.size()+1;
		eDAsiento.setId(cont);
		//calcular tipo de cambio
		if(verificarTipoMoneda("EXTRANJERA")){
			eDAsiento.setDebeNacional(eDAsiento.getDebeExtranjero() * tipoCambio.getUnidad());
			eDAsiento.setHaberNacional(eDAsiento.getHaberExtranjero() * tipoCambio.getUnidad());
		}else{
			eDAsiento.setDebeExtranjero(eDAsiento.getDebeNacional() / tipoCambio.getUnidad());
			eDAsiento.setHaberExtranjero(eDAsiento.getHaberNacional() / tipoCambio.getUnidad());
		}
		eDAsiento.setCentroCosto(null);//ppor el momento null
		listEDAsiento.add(eDAsiento);
		String glosa = eDAsiento.getGlosa();
		eDAsiento = new EDAsiento();
		eDAsiento.setGlosa(glosa);
		planCuenta = new PlanCuenta();
		calcularTotalComprobante();
	}

	public void calcularTotalComprobante(){
		double totalDebeNacional = 0;
		double totalHaberNacional = 0;
		double totalDebeExtranjero = 0;
		double totalHaberExtranjero = 0;
		for(EDAsiento ed: listEDAsiento){
			totalDebeNacional = totalDebeNacional + ed.getDebeNacional();
			totalHaberNacional = totalHaberNacional + ed.getHaberNacional();
			totalDebeExtranjero = totalDebeExtranjero + ed.getDebeExtranjero();
			totalHaberExtranjero = totalHaberExtranjero + ed.getHaberExtranjero();
		}
		comprobante.setImporteTotalDebeNacional(totalDebeNacional);
		comprobante.setImporteTotalHaberNacional(totalHaberNacional);
		comprobante.setImporteTotalDebeExtranjero(totalDebeExtranjero);
		comprobante.setImporteTotalHaberExtranjero(totalHaberExtranjero);
	}

	public PlanCuenta obtenerPlanCuentaPorDescripcion(String descripcion){
		for(PlanCuenta pc: sessionContable.getListPlanCuentaAuxiliar()){
			if(pc.getDescripcion().equals(descripcion)){
				return pc;
			}
		}
		return null;
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

	public void printReport(){
		currentPage = "/pages/contabilidad/comprobante/report.xhtml";
		seleccionado = false;
		verReporte = true;
		cargarReporte(comprobante);
	}
	
	// ------------ PROCESS -----------

	public void registrarComprobante(){
		System.out.println("registrarComprobante");
		//sin cuentas agregadas
		if(listEDAsiento.size()==0){
			FacesUtil.errorMessage("Debe agregar cuentas.");
		}
		//comprobante desbalanceado
		if(comprobante.getImporteTotalDebeNacional()==0 && comprobante.getImporteTotalHaberNacional()==0){
			FacesUtil.errorMessage("Comprobante Desbalanceado");
		}
		//comprobante.setSucursal(sucursal);
		comprobante.setFechaRegistro(new Date());
		comprobante.setTipoComprobante(tipoComprobante);
		comprobante.setTipoMoneda(monedaEmpresa.getTipo());
		comprobante.setUsuarioRegistro(sessionMain.getUsuarioLogin().getLogin());
		Comprobante sw = comprobanteDao.registrar(comprobante, listEDAsiento);
		if(sw!=null){
			comprobante = new Comprobante();
			loadDefault();
			setCrear(false);
			setRegistrar(false) ;
			setSeleccionado(false);
			verReporte = true;
			currentPage = "/pages/contabilidad/comprobante/report.xhtml";
			cargarReporte(sw);
		}
	}

	public void modificarComprobante(){

	}

	public void anularComprobante(){

	}

	public void eliminarComprobante(){

	}
	
	//---REPORT
		private void cargarReporte(Comprobante comprobante){
			//setVerReporte(true);
			Map<String,String> map1 = new HashMap<>();
			map1.put("pUsuario", sessionMain.getUsuarioLogin().getNombre());
			map1.put("pIdComprobante", String.valueOf(comprobante.getId()));
			//---URL COMPROBANTE
			urlComprobante = ReportUtil.buildUrl("ReportComprobante",map1);
		}

	// ------------ GET AND SET -----------

	public String getInfoDialog() {
		return infoDialog;
	}

	public void setInfoDialog(String infoDialog) {
		this.infoDialog = infoDialog;
	}

	public String getTituloPanel() {
		return tituloPanel;
	}

	public void setTituloPanel(String tituloPanel) {
		this.tituloPanel = tituloPanel;
	}

	public String getUrlComprobante() {
		return urlComprobante;
	}

	public void setUrlComprobante(String urlComprobante) {
		this.urlComprobante = urlComprobante;
	}

	public List<LineaContable> getListLineaContable() {
		return listLineaContable;
	}

	public void setListLineaContable(List<LineaContable> listLineaContable) {
		this.listLineaContable = listLineaContable;
	}

	public List<PlanCuenta> getListCuentasAuxiliares() {
		return listCuentasAuxiliares;
	}

	public void setListCuentasAuxiliares(List<PlanCuenta> listCuentasAuxiliares) {
		this.listCuentasAuxiliares = listCuentasAuxiliares;
	}

	public List<CentroCosto> getListCentroCosto() {
		return listCentroCosto;
	}

	public void setListCentroCosto(List<CentroCosto> listCentroCosto) {
		this.listCentroCosto = listCentroCosto;
	}

	public String[] getArrayNombrePersona() {
		return arrayNombrePersona;
	}

	public void setArrayNombrePersona(String[] arrayNombrePersona) {
		this.arrayNombrePersona = arrayNombrePersona;
	}

	public List<EDAsiento> getListEDAsiento() {
		return listEDAsiento;
	}

	public void setListEDAsiento(List<EDAsiento> listEDAsiento) {
		this.listEDAsiento = listEDAsiento;
	}

	public List<GrupoImpuesto> getListGrupoImpuesto() {
		return listGrupoImpuesto;
	}

	public void setListGrupoImpuesto(List<GrupoImpuesto> listGrupoImpuesto) {
		this.listGrupoImpuesto = listGrupoImpuesto;
	}

	public Comprobante getComprobante() {
		return comprobante;
	}

	public void setComprobante(Comprobante comprobante) {
		this.comprobante = comprobante;
	}

	public LineaContable getLineaContable() {
		return lineaContable;
	}

	public void setLineaContable(LineaContable lineaContable) {
		this.lineaContable = lineaContable;
	}

	public PlanCuenta getPlanCuenta() {
		return planCuenta;
	}

	public void setPlanCuenta(PlanCuenta planCuenta) {
		this.planCuenta = planCuenta;
	}

	public TipoCambio getTipoCambio() {
		return tipoCambio;
	}

	public void setTipoCambio(TipoCambio tipoCambio) {
		this.tipoCambio = tipoCambio;
	}

	public MonedaEmpresa getMonedaEmpresa() {
		return monedaEmpresa;
	}

	public void setMonedaEmpresa(MonedaEmpresa monedaEmpresa) {
		this.monedaEmpresa = monedaEmpresa;
	}

	public EDAsiento geteDAsiento() {
		return eDAsiento;
	}

	public void seteDAsiento(EDAsiento eDAsiento) {
		this.eDAsiento = eDAsiento;
	}

	public TreeNode getSelectedNodeCentroCosto() {
		return selectedNodeCentroCosto;
	}

	public void setSelectedNodeCentroCosto(TreeNode selectedNodeCentroCosto) {
		this.selectedNodeCentroCosto = selectedNodeCentroCosto;
	}

	public TreeNode getSelectedNodeCuenta() {
		return selectedNodeCuenta;
	}

	public void setSelectedNodeCuenta(TreeNode selectedNodeCuenta) {
		this.selectedNodeCuenta = selectedNodeCuenta;
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

	public List<Comprobante> getListComprobante() {
		return listComprobante;
	}

	public void setListComprobante(List<Comprobante> listComprobante) {
		this.listComprobante = listComprobante;
	}

//	public Sucursal getSucursal(){
//		return sucursal;
//	}
//
//	public void setSucursal(Sucursal sucursal){
//		this.sucursal =  sucursal;
//	}

	public TipoComprobante getTipoComprobante() {
		return tipoComprobante;
	}

	public void setTipoComprobante(TipoComprobante tipoComprobante) {
		this.tipoComprobante = tipoComprobante;
	}

	public String getCurrentTipoComprobante() {
		return currentTipoComprobante;
	}

	public void setCurrentTipoComprobante(String currentTipoComprobante) {
		this.currentTipoComprobante = currentTipoComprobante;
	}

	public Double[] getArrayMonto() {
		return arrayMonto;
	}

	public void setArrayMonto(Double[] arrayMonto) {
		this.arrayMonto = arrayMonto;
	}

	public List<Double> getListMontoNacional() {
		return listMontoNacional;
	}

	public void setListMontoNacional(List<Double> listMontoNacional) {
		this.listMontoNacional = listMontoNacional;
	}

	public boolean isVerReporte() {
		return verReporte;
	}

	public void setVerReporte(boolean verReporte) {
		this.verReporte = verReporte;
	}
}
