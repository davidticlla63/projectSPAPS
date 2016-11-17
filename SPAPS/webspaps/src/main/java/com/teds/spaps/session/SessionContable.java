package com.teds.spaps.session;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.teds.spaps.interfaces.dao.ICentroCostoDao;
import com.teds.spaps.interfaces.dao.IMonedaEmpresaDao;
import com.teds.spaps.interfaces.dao.IPlanCuentaDao;
import com.teds.spaps.interfaces.dao.ITipoComprobanteDao;
import com.teds.spaps.interfaces.dao.ITipoCuentaDao;
import com.teds.spaps.model.CentroCosto;
import com.teds.spaps.model.MonedaEmpresa;
import com.teds.spaps.model.PlanCuenta;
import com.teds.spaps.model.TipoComprobante;
import com.teds.spaps.model.TipoCuenta;
import com.teds.spaps.util.SessionMain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class SessionContable, persistent data contabilidad.
 * 
 * @author mauriciobejaranorivera
 *
 */

@Named
@SessionScoped
public class SessionContable implements Serializable {

	private static final long serialVersionUID = 1L;

	// DAO
	private @Inject SessionMain sessionMain;
	private @Inject ITipoComprobanteDao tipoComprobanteDao;
	private @Inject IPlanCuentaDao planCuentaDao;
	private @Inject IMonedaEmpresaDao monedaEmpresaDao;
	private @Inject ICentroCostoDao centroCostoDao;
	private @Inject ITipoCuentaDao tipoCuentaDao;
	
	// OBJECT

	// LIST
	private List<PlanCuenta> listPlanCuenta;
	private List<CentroCosto> listCentroCosto;
	private List<MonedaEmpresa> listMonedaEmpresa;
	private List<TipoComprobante> listTipoComprobante;
	private List<TipoCuenta> listTipoCuenta;

	@PostConstruct
	public void init() {
		System.out.println("----- init SessionContable() --------");
		
		listPlanCuenta = new ArrayList<>();
		listMonedaEmpresa = new ArrayList<>();
		listTipoComprobante = new ArrayList<>();
		listCentroCosto = new ArrayList<>();
		listTipoCuenta = new ArrayList<>();
	}

	
	//--------------- PLAN CUENTA --------------

	public List<PlanCuenta> getListPlanCuenta() {
		if(listPlanCuenta.size()==0){
			listPlanCuenta = planCuentaDao.obtenerTodosActivosEInactivosPorEmpresa(sessionMain.getEmpresaLogin());
		}
		return listPlanCuenta;
	}

	public void setListPlanCuenta(List<PlanCuenta> listPlanCuenta) {
		this.listPlanCuenta = listPlanCuenta;
	}
	
	public void actualizarCuenta(PlanCuenta planCuenta){
		for(PlanCuenta pc : this.getListPlanCuenta()){
			if(pc.equals(planCuenta)){
				pc = planCuenta;
			}
		}
	}

	public void eliminarCuenta(PlanCuenta planCuenta){
		this.getListPlanCuenta().remove(planCuenta);
	}
	
	/**
	 * obtiene lista cuentas auxiliares por empresa
	 * @return List<PlanCuenta>
	 * @param Empresa empresa
	 */
	public List<PlanCuenta> getListPlanCuentaAuxiliar() {
		List<PlanCuenta> results = new ArrayList<PlanCuenta>();
		for(PlanCuenta pc : getListPlanCuenta()) {
			if(pc.getClase().equals("AUXILIAR") ){
				results.add(pc);
			}
		}
		return results;
	}
	
	/**
	 * obtiene lista cuentas auxiliares por empresa
	 * @return List<PlanCuenta>
	 * @param Empresa empresa
	 */
	public List<PlanCuenta> getListPlanCuentaAuxiliarActivas() {
		List<PlanCuenta> results = new ArrayList<PlanCuenta>();
		for(PlanCuenta pc : getListPlanCuenta()) {
			if(pc.getClase().equals("AUXILIAR") && pc.getEstado().equals("AC") ){
				results.add(pc);
			}
		}
		return results;
	}
	
	public List<CentroCosto> getListCentroCosto() {
		if(listCentroCosto.size()==0){
			listCentroCosto = centroCostoDao.obtenerTodosPorEmpresa(sessionMain.getEmpresaLogin());
		}
		return listCentroCosto;
	}

	public void setListCentroCosto(List<CentroCosto> listCentroCosto) {
		this.listCentroCosto = listCentroCosto;
	}
	
	public List<MonedaEmpresa> getListMonedaEmpresa() {
		if(listMonedaEmpresa.size()==0){
			listMonedaEmpresa = monedaEmpresaDao.obtenerTodasPorEmpresa(sessionMain.getEmpresaLogin());
		}
		return listMonedaEmpresa;
	}

	public void setListMonedaEmpresa(List<MonedaEmpresa> listMonedaEmpresa) {
		this.listMonedaEmpresa = listMonedaEmpresa;
	}
	
	public List<TipoComprobante> getListTipoComprobante() {
		if(listTipoComprobante.size()==0){
			listTipoComprobante = tipoComprobanteDao.obtenerOrdenAscPorId();
		}
		return listTipoComprobante;
	}

	public void setListTipoComprobante(List<TipoComprobante> listTipoComprobante) {
		this.listTipoComprobante = listTipoComprobante;
	}


	public List<TipoCuenta> getListTipoCuenta() {
		if(listTipoCuenta.size()==0){
			listTipoCuenta = tipoCuentaDao.obtenerPorEmpresaYGesstion(sessionMain.getEmpresaLogin(),sessionMain.getGestionLogin());
		}
		return listTipoCuenta;
	}


	public void setListTipoCuenta(List<TipoCuenta> listTipoCuenta) {
		this.listTipoCuenta = listTipoCuenta;
	}
	
}
