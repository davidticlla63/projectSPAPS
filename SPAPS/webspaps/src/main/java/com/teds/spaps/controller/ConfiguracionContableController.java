package com.teds.spaps.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.TreeNode;

import com.teds.spaps.interfaces.dao.IConfiguracionContableDao;
import com.teds.spaps.interfaces.dao.IPlanCuentaDao;
import com.teds.spaps.model.ConfiguracionContable;
import com.teds.spaps.model.ConfiguracionContableCompra;
import com.teds.spaps.model.ConfiguracionContableVenta;
import com.teds.spaps.model.ParametroEmpresa;
import com.teds.spaps.model.PlanCuenta;
import com.teds.spaps.model.TipoCuenta;
import com.teds.spaps.session.SessionContable;
import com.teds.spaps.util.SessionMain;
import com.teds.spaps.util.EDNivel;
import com.teds.spaps.util.EDPlanCuenta;
import com.teds.spaps.util.FacesUtil;


/**
 * 
 * @author mauriciobejaranorivera
 *
 */
@ManagedBean(name = "configuracionContableController")
@ViewScoped
public class ConfiguracionContableController implements Serializable {

	private static final long serialVersionUID = -4058327498353920610L;

	//DAO
	private @Inject IPlanCuentaDao planCuentaDao;
	private @Inject SessionMain sessionMain;
	private @Inject SessionContable sessionContable; 
	private @Inject IConfiguracionContableDao configuracionContableDao;

	//VAR
	private String currentPage;
	private String currentCuenta;
	private String[] arrayNivel = {"","PRIMER NIVEL","SEGUNDO NIVEL","TERCER NIVEL","CUARTO NIVEL","QUINTO NIVEL","SEXTO NIVEL","SEPTIMO NIVEL","OCTAVO NIVEL","NOVENO NIVEL"};

	//LIST
	private List<PlanCuenta> listPlanCuenta;
	private List<EDNivel> listNivel;
	private List<TipoCuenta> listTipoCuenta;

	//OBJECT
	private TreeNode selectedNode;
	private PlanCuenta planCuenta;
	private ConfiguracionContable configuracionContable;
	private ConfiguracionContableCompra configuracionContableCompra;
	private ConfiguracionContableVenta configuracionContableVenta;
	private ParametroEmpresa parametroEmpresa;

	@PostConstruct
	public void initNewComprobante() {
		loadDefault();
	}

	public void loadDefault(){
		currentPage = "/pages/contabilidad/config/edit.xhtml";
		currentCuenta = "none";
		planCuenta = new PlanCuenta();
		listNivel= new ArrayList<>();
		listTipoCuenta = sessionContable.getListTipoCuenta();
		configuracionContable = configuracionContableDao.obtenerPorEmpresac(sessionMain.getEmpresaLogin());
		parametroEmpresa = sessionMain.getParametroEmpresa();
		String codificacion = parametroEmpresa.getCodificacionEstandar();
		int cantNivel = parametroEmpresa.getNivelMaximo();
		int cont = 1;
		while(cantNivel>0){
			EDNivel nivel = new EDNivel();
			nivel.setId(String.valueOf(cont));
			nivel.setNombre(arrayNivel[cont]);
			nivel.setTamanio(obtenerTamanioCodigoPorNivel(codificacion,cont));
			listNivel.add(nivel);
			cont = cont + 1;
			cantNivel = cantNivel - 1;
		}
	}

	/**
	 *   | 1  |  2  |  3   | -> Niveles
	 * Ej  9  . 99  . 9999 .  -> obtenerTamanioCodigoPorNivel(9.99.9999,999999,3) = 4
	 * @return
	 */
	private int obtenerTamanioCodigoPorNivel(String codificacion,int nivel){
		List<String> list = new ArrayList<>();
		int ant = 0,length = codificacion.length()-1;
		for(int index = 0; length >= 0 ; index++){
			String letra = String.valueOf(codificacion.charAt(index));
			if(letra.equals(".")){
				String x = codificacion.substring(ant, index+1);
				list.add(x.replace(".", "")); ant = index;
			} length = length - 1;
		}
		list.add(codificacion.substring(ant,codificacion.length()).replace(".", ""));
		return list.get(nivel-1).length();
	}

	// ACTION EVENT

	public void loadDialogCuenta(String currentCuenta){
		System.out.println("currentCuenta: "+currentCuenta);
		this.currentCuenta = currentCuenta;
		FacesUtil.showDialog("dlgPlanCuenta");
	}

	public void cargarSeleccionCuenta(){
		System.out.println("select: "+planCuenta.getId()+" | selectedNode: "+selectedNode);
		if(planCuenta.getId().equals(0) && (selectedNode==null) ){
			FacesUtil.infoMessage("Verificación","Seleccione una cuenta");
			return;
		}
		if(planCuenta.getId().equals(0)){
			EDPlanCuenta ed = (EDPlanCuenta) selectedNode.getData();
			planCuenta = ed.getPc();
		}
		switch (currentCuenta) {
		case "cuentaDebitoFiscal":
			configuracionContable.setCuentaDebitoFiscal(planCuenta);
			break;
		case "cuentaCreditoFiscal":
			configuracionContable.setCuentaCreditoFiscal(planCuenta);
			break;
		case "cuentaCreditoFiscalNoDeducible":
			configuracionContable.setCuentaCreditoFiscalNoDeducible(planCuenta);
			break;
		case "cuentaCreditoFiscalTransitorio":
			configuracionContable.setCuentaCreditoFiscalTransitorio(planCuenta);
			break;
		case "cuentaImpuestoTransacciones":
			configuracionContable.setCuentaImpuestoTransacciones(planCuenta);
			break;
		case "cuentaGastoImpuestoTransacciones":
			configuracionContable.setCuentaGastoImpuestoTransacciones(planCuenta);
			break;
		case "cuentaDebitoFiscalTransitorio":
			configuracionContable.setCuentaDebitoFiscalTransitorio(planCuenta);
			break;
		case "cuentaInflacion":
			configuracionContable.setCuentaInflacion(planCuenta);
			break;
		case "cuentaAjusteCorrecionMonetaria":
			configuracionContable.setCuentaAjusteCorrecionMonetaria(planCuenta);
			break;
		case "cuentaDiferenciaCambio":
			configuracionContable.setCuentaDiferenciaCambio(planCuenta);
			break;
		case "cuentaAjusteCapital":
			configuracionContable.setCuentaAjusteCapital(planCuenta);
			break;
		case "cuentaAjusteReservasPatrimoniales":
			configuracionContable.setCuentaAjusteReservasPatrimoniales(planCuenta);
			break;

		default:
			break;
		}
		planCuenta = new PlanCuenta();
		selectedNode = null;
		FacesUtil.hideDialog("dlgPlanCuenta");
	}

	// PROCESS

	public void registrarConfiguracionContable(){
		boolean sw = configuracionContableDao.modificar(configuracionContable);
		if(sw){
			loadDefault();
		}
	}

	//------------oncomplete------------

	// ONCOMPLETETEXT Banco Nacional
	public List<PlanCuenta> completeCuentaAuxiliar(String query) {
		listPlanCuenta = new ArrayList<PlanCuenta>();//reset
		List<PlanCuenta> results = new ArrayList<PlanCuenta>();
		listPlanCuenta = planCuentaDao.obtenerPlanCuentaAuxiliarPorDescripcionConsulta(query,sessionMain.getEmpresaLogin());
		for(PlanCuenta i : listPlanCuenta) {
			if(i.getDescripcion().toUpperCase().startsWith(query.toUpperCase())){
				results.add(i);
			}
		}         
		return results;
	}

	public void onRowSelectCuentaCreditoFiscalAuxiliarClick(SelectEvent event) {
		String nombre =  event.getObject().toString();
		for(PlanCuenta i : listPlanCuenta){
			if(i.getDescripcion().equals(nombre)){
				//cuentaCreditoFiscal = i;
				configuracionContable.setCuentaCreditoFiscal(i);
				return;
			}
		}
	}
	/**
	 *  cuentaCreditoFiscal);
			 cuentaCreditoFiscalNoDeducible);

			 cuentaCreditoFiscalTransitorio);
			 cuentaDebitoFiscal);
			 cuentaDebitoFiscalTransitorio);
			 cuentaGastoImpuestoTransacciones
			 cuentaImpuestoTransacciones
	 */
	public void onRowSelectCuentaCreditoFiscalNoDeducibleAuxiliarClick(SelectEvent event) {
		String nombre =  event.getObject().toString();
		for(PlanCuenta i : listPlanCuenta){
			if(i.getDescripcion().equals(nombre)){
				//cuentaCreditoFiscalNoDeducible = i;
				configuracionContable.setCuentaCreditoFiscalNoDeducible(i);
				return;
			}
		}
	}

	public void onRowSelectCuentaCreditoFiscalTransitorioAuxiliarClick(SelectEvent event) {
		String nombre =  event.getObject().toString();
		for(PlanCuenta i : listPlanCuenta){
			if(i.getDescripcion().equals(nombre)){
				//cuentaCreditoFiscalTransitorio = i;
				configuracionContable.setCuentaDebitoFiscalTransitorio(i);
				return;
			}
		}
	}

	public void onRowSelectCuentaDebitoFiscalAuxiliarClick(SelectEvent event) {
		String nombre =  event.getObject().toString();
		for(PlanCuenta i : listPlanCuenta){
			if(i.getDescripcion().equals(nombre)){
				//cuentaDebitoFiscal = i;
				configuracionContable.setCuentaCreditoFiscal(i);
				return;
			}
		}
	}

	public void onRowSelectCuentaDebitoFiscalTransitorioAuxiliarClick(SelectEvent event) {
		String nombre =  event.getObject().toString();
		for(PlanCuenta i : listPlanCuenta){
			if(i.getDescripcion().equals(nombre)){
				//cuentaDebitoFiscalTransitorio = i;
				configuracionContable.setCuentaCreditoFiscalTransitorio(i);
				return;
			}
		}
	}

	public void onRowSelectCuentaGastoImpuestoTransaccionesAuxiliarClick(SelectEvent event) {
		String nombre =  event.getObject().toString();
		for(PlanCuenta i : listPlanCuenta){
			if(i.getDescripcion().equals(nombre)){
				//cuentaGastoImpuestoTransacciones = i;
				configuracionContable.setCuentaImpuestoTransacciones(i);
				return;
			}
		}
	}

	public void onRowSelectCuentaImpuestoTransaccionesAuxiliarClick(SelectEvent event) {
		String nombre =  event.getObject().toString();
		for(PlanCuenta i : listPlanCuenta){
			if(i.getDescripcion().equals(nombre)){
				//cuentaImpuestoTransacciones = i;
				configuracionContable.setCuentaImpuestoTransacciones(i);
				return;
			}
		}
	}

	// GET AND SET

	public ConfiguracionContable getConfiguracionContable() {
		return configuracionContable;
	}

	public void setConfiguracionContable(ConfiguracionContable configuracionContable) {
		this.configuracionContable = configuracionContable;
	}

	public ConfiguracionContableCompra getConfiguracionContableCompra() {
		return configuracionContableCompra;
	}

	public void setConfiguracionContableCompra(
			ConfiguracionContableCompra configuracionContableCompra) {
		this.configuracionContableCompra = configuracionContableCompra;
	}

	public ConfiguracionContableVenta getConfiguracionContableVenta() {
		return configuracionContableVenta;
	}

	public void setConfiguracionContableVenta(ConfiguracionContableVenta configuracionContableVenta) {
		this.configuracionContableVenta = configuracionContableVenta;
	}

	public String getPage() {
		return currentPage;
	}

	public void setPage(String page) {
		this.currentPage = page;
	}

	public PlanCuenta getPlanCuenta() {
		return planCuenta;
	}

	public void setPlanCuenta(PlanCuenta planCuenta) {
		this.planCuenta = planCuenta;
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	public String getCurrentCuenta() {
		return currentCuenta;
	}

	public void setCurrentCuenta(String currentCuenta) {
		this.currentCuenta = currentCuenta;
	}

	public ParametroEmpresa getParametroEmpresa() {
		return parametroEmpresa;
	}

	public void setParametroEmpresa(ParametroEmpresa parametroEmpresa) {
		this.parametroEmpresa = parametroEmpresa;
	}

	public List<EDNivel> getListNivel() {
		return listNivel;
	}

	public void setListNivel(List<EDNivel> listNivel) {
		this.listNivel = listNivel;
	}

	public String[] getArrayNivel() {
		return arrayNivel;
	}

	public void setArrayNivel(String[] arrayNivel) {
		this.arrayNivel = arrayNivel;
	}

	public List<TipoCuenta> getListTipoCuenta() {
		return listTipoCuenta;
	}

	public void setListTipoCuenta(List<TipoCuenta> listTipoCuenta) {
		this.listTipoCuenta = listTipoCuenta;
	}

}
