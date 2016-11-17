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

import com.teds.spaps.interfaces.dao.IConfiguracionContableVentaDao;
import com.teds.spaps.interfaces.dao.IPlanCuentaDao;
import com.teds.spaps.model.ConfiguracionContableVenta;
import com.teds.spaps.model.PlanCuenta;
import com.teds.spaps.session.SessionContable;
import com.teds.spaps.util.EDPlanCuenta;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

/**
 * 
 * @author mauriciobejaranorivera
 *
 */
@ManagedBean(name = "configuracionVentaController")
@ViewScoped
public class ConfiguracionVentaController implements Serializable {

	private static final long serialVersionUID = -4058327498353920610L;

	//DAO
	private @Inject IPlanCuentaDao planCuentaDao;
	private @Inject IConfiguracionContableVentaDao configuracionContableVentaDao;
	private @Inject SessionMain sessionMain;
	private @Inject SessionContable sessionContable;

	//VAR
	private String currentPage;
	private String currentCuenta;

	//LIST
	private List<PlanCuenta> listPlanCuenta;

	//OBJECT
	private ConfiguracionContableVenta configuracionContableVenta;
	private TreeNode selectedNode;
	private PlanCuenta planCuenta;

	@PostConstruct
	public void initNewComprobante() {
		loadDefault();
	}

	public void loadDefault(){
		currentPage = "/pages/ventas/param/edit.xhtml";
		configuracionContableVenta = configuracionContableVentaDao.obtenerPorEmpresa(sessionMain.getEmpresaLogin());
	}

	// ACTION EVENT
	
	public void loadDialogCuenta(String currentCuenta){
		System.out.println("currentCuenta: "+currentCuenta);
		this.setCurrentCuenta(currentCuenta);
		FacesUtil.showDialog("dlgPlanCuenta");
	}


	// PROCESS


	//------------oncomplete------------

	// ONCOMPLETETEXT
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

	public void onRowSelectCuentaBancoNacional(SelectEvent event) {
		String nombre =  event.getObject().toString();
		for(PlanCuenta i : listPlanCuenta){
			if(i.getDescripcion().equals(nombre)){
				configuracionContableVenta.setCuentaBancoNacional(i);
				return;
			}
		}
	}
	
	public void onRowSelectCuentaBancoExtranjero(SelectEvent event) {
		String nombre =  event.getObject().toString();
		for(PlanCuenta i : listPlanCuenta){
			if(i.getDescripcion().equals(nombre)){
				configuracionContableVenta.setCuentaBancoExtranjero(i);
				return;
			}
		}
	}
	
	public void onRowSelectCuentaIngresoVenta(SelectEvent event) {
		String nombre =  event.getObject().toString();
		for(PlanCuenta i : listPlanCuenta){
			if(i.getDescripcion().equals(nombre)){
				configuracionContableVenta.setCuentaIngresoPorVenta(i);
				return;
			}
		}
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
		case "cuentaBancoNacional":
			configuracionContableVenta.setCuentaBancoNacional(planCuenta);
			break;
		case "cuentaBancoExtranjera":
			configuracionContableVenta.setCuentaBancoExtranjero(planCuenta);
			break;
		case "cuentaIngresoVenta":
			configuracionContableVenta.setCuentaIngresoPorVenta(planCuenta);
			break;
		}
	}


	// GET AND SET

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

	public String getCurrentCuenta() {
		return currentCuenta;
	}

	public void setCurrentCuenta(String currentCuenta) {
		this.currentCuenta = currentCuenta;
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
}
