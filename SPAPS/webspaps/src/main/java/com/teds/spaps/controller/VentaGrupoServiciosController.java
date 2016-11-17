package com.teds.spaps.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import com.teds.spaps.interfaces.dao.IPlanCuentaDao;
import com.teds.spaps.interfaces.dao.IVentaGrupoServiciosDao;
import com.teds.spaps.model.PlanCuenta;
import com.teds.spaps.model.VentaGrupoServicios;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.session.SessionContable;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

/**
 * 
 * @author mauriciobejaranorivera
 *
 */
@ManagedBean(name = "ventaGrupoServiciosController")
@ViewScoped
public class VentaGrupoServiciosController implements Serializable {

	private static final long serialVersionUID = -125210622622704977L;

	//DAO
	private @Inject IVentaGrupoServiciosDao ventaGrupoServiciosDao;
	private @Inject IPlanCuentaDao planCuentaDao;
	private @Inject SessionMain sessionMain;
	private @Inject SessionContable sessionContable;

	// OBJECT
	private VentaGrupoServicios ventaGrupoServicios;
	private VentaGrupoServicios ventaGrupoServiciosSelected;
	private Sucursal sucursal;

	// LIST
	private List<VentaGrupoServicios> listaVentaGrupoServicios;
	private List<PlanCuenta> listPlanCuenta;

	// ESTADOS
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;
	private boolean seleccionado = false;

	//URL PAGE
	private String currentPage = "/pages/ventas/grupo_servicios/list.xhtml";

	@PostConstruct
	public void initNew() {
		sucursal = sessionMain.PruebaSucursal();
		defaultValues();
	}

	private void defaultValues() {
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
		seleccionado = false;

		listPlanCuenta = new ArrayList<>();

		ventaGrupoServicios = new VentaGrupoServicios();
		ventaGrupoServiciosSelected = new VentaGrupoServicios();
		listaVentaGrupoServicios = ventaGrupoServiciosDao.obtenerPorCompania(sucursal
				.getCompania());
		currentPage = "/pages/ventas/grupo_servicios/list.xhtml";
	}

	public void registrar() {
		if (ventaGrupoServicios.getDescripcion().trim().isEmpty()
				|| ventaGrupoServicios.getEstado().trim().isEmpty()
				|| getSucursal() == null
				|| getSucursal().getCompania() == null) {
			FacesUtil.infoMessage("VALIDACION",
					"No puede haber campos vacíos");
			return;
		}
		ventaGrupoServicios.setCompania(sucursal.getCompania());
		ventaGrupoServicios.setSucursal(sucursal);
		ventaGrupoServicios.setFechaRegistro(new Date());
		ventaGrupoServicios.setFechaModificacion(ventaGrupoServicios.getFechaRegistro());
		ventaGrupoServicios.setUsuarioRegistro(sessionMain.getUsuarioLogin().getLogin());
		VentaGrupoServicios r = ventaGrupoServiciosDao.registrar(ventaGrupoServicios);
		if (r != null) {
			defaultValues();
		}
	}

	public void actualizar() {
		if (ventaGrupoServicios.getDescripcion().trim().isEmpty()
				|| ventaGrupoServicios.getEstado().trim().isEmpty()
				|| getSucursal() == null
				|| getSucursal().getCompania() == null) {
			FacesUtil.infoMessage("VALIDACION",
					"No puede haber campos vacíos");
			return;
		} else {
			ventaGrupoServicios.setCompania(getSucursal().getCompania());
			ventaGrupoServicios.setSucursal(getSucursal());
			ventaGrupoServicios.setFechaModificacion(new Date());
			ventaGrupoServicios.setUsuarioRegistro(sessionMain.getUsuarioLogin().getLogin());
			VentaGrupoServicios r = ventaGrupoServiciosDao.modificar(ventaGrupoServicios);
			if (r != null) {
				defaultValues();
			} 
		}
	}

	public void eliminar() {
		if (ventaGrupoServiciosDao.eliminar(ventaGrupoServicios)) {
			defaultValues();
		} 
	}

	public void onRowSelect(SelectEvent event) {
		ventaGrupoServicios = ventaGrupoServiciosSelected;
		crear = false;
		seleccionado = true;
		registrar = false;
		currentPage = "/pages/ventas/grupo_servicios/edit.xhtml";
	}

	public void actionNuevo() {
		System.out.println("Ingreso a actionNuevo...");
		crear = false;
		seleccionado = false;
		registrar = true;
		ventaGrupoServicios = new VentaGrupoServicios();
		ventaGrupoServicios.setFechaRegistro(new Date());
		currentPage = "/pages/ventas/grupo_servicios/edit.xhtml";
	}

	public void actionCancelar() {
		crear = true;
		seleccionado = false;
		registrar = false;
		ventaGrupoServicios = new VentaGrupoServicios();
		currentPage = "/pages/ventas/grupo_servicios/list.xhtml";
	}

	// ONCOMPLETETEXT CUENTA
	public List<PlanCuenta> completeCuentaIngreso(String query) {
		for(PlanCuenta i : sessionContable.getListPlanCuentaAuxiliar()) {
			if(i.getDescripcion().toUpperCase().startsWith(query.toUpperCase())){
				listPlanCuenta.add(i);
			}
		}   
		System.out.println("listPlanCuenta.size(): "+listPlanCuenta.size()+"   -    query: "+query);
		return listPlanCuenta;
	}

	public void onRowSelectCuentaClick(SelectEvent event) {
		String nombre = event.getObject().toString();
		for (PlanCuenta i : listPlanCuenta) {
			if (i.getDescripcion().equals(nombre)) {
				ventaGrupoServicios.setCuentaIngreso(i);
				return;
			}
		}
	}

	//get and set

	public boolean isSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

	public VentaGrupoServicios getVentaGrupoServicios() {
		return ventaGrupoServicios;
	}

	public VentaGrupoServicios getVentaGrupoServiciosSelected() {
		return ventaGrupoServiciosSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<VentaGrupoServicios> getListaVentaGrupoServicios() {
		return listaVentaGrupoServicios;
	}

	public boolean isModificar() {
		return modificar;
	}

	public boolean isRegistrar() {
		return registrar;
	}

	public boolean isCrear() {
		return crear;
	}

	public void setVentaGrupoServicios(VentaGrupoServicios ventaGrupoServicios) {
		this.ventaGrupoServicios = ventaGrupoServicios;
	}

	public void setVentaGrupoServiciosSelected(VentaGrupoServicios ventaGrupoServiciosSelected) {
		this.ventaGrupoServiciosSelected = ventaGrupoServiciosSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaVentaGrupoServicios(List<VentaGrupoServicios> listaVentaGrupoServicios) {
		this.listaVentaGrupoServicios = listaVentaGrupoServicios;
	}

	public void setModificar(boolean modificar) {
		this.modificar = modificar;
	}

	public void setRegistrar(boolean registrar) {
		this.registrar = registrar;
	}

	public void setCrear(boolean crear) {
		this.crear = crear;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	public List<PlanCuenta> getListPlanCuenta() {
		return listPlanCuenta;
	}

	public void setListPlanCuenta(List<PlanCuenta> listPlanCuenta) {
		this.listPlanCuenta = listPlanCuenta;
	}

}
