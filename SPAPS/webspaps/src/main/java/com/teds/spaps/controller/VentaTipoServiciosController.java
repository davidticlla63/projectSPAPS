/**
 * 
 */
package com.teds.spaps.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import com.teds.spaps.interfaces.dao.IVentaTipoServiciosDao;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.VentaTipoServicios;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

/**
 * @author ANITA
 *
 */
@ManagedBean(name = "ventaTipoServiciosController")
@ViewScoped
public class VentaTipoServiciosController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -125210622622704977L;
	/******* DAO **********/
	private @Inject IVentaTipoServiciosDao ventaTipoServiciosDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private VentaTipoServicios ventaTipoServicios;
	private VentaTipoServicios ventaTipoServiciosSelected;
	private Sucursal sucursal;

	/******* LIST **********/
	private List<VentaTipoServicios> listaVentaTipoServicios;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;
	private boolean seleccionado = false;
	
	//URL PAGE
	private String currentPage = "/pages/ventas/tipo_servicios/list.xhtml";


	/**
	 * 
	 */
	public VentaTipoServiciosController() {
	}

	@PostConstruct
	public void initNew() {
		sucursal = sessionMain.PruebaSucursal();
		System.out.println("Sucursal : " + sucursal.getDescripcion());
		System.out.println("Compañia : "
				+ sucursal.getCompania().getDescripcion());
		defaultValues();
	}

	private void defaultValues() {
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
		seleccionado = false;
		ventaTipoServicios = new VentaTipoServicios();
		ventaTipoServiciosSelected = new VentaTipoServicios();
		listaVentaTipoServicios = ventaTipoServiciosDao.obtenerPorCompania(sucursal
				.getCompania());
		currentPage = "/pages/ventas/tipo_servicios/list.xhtml";
	}

	public void registrar() {
		try {
			System.out.println("Ingreso a registrar");

			if (ventaTipoServicios.getDescripcion().trim().isEmpty()
					|| ventaTipoServicios.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			}
			ventaTipoServicios.setCompania(sucursal.getCompania());
			ventaTipoServicios.setSucursal(sucursal);
			ventaTipoServicios.setFechaRegistro(new Date());
			ventaTipoServicios
					.setFechaModificacion(ventaTipoServicios.getFechaRegistro());
			ventaTipoServicios.setUsuarioRegistro(sessionMain.getUsuarioLogin()
					.getLogin());

			VentaTipoServicios r = ventaTipoServiciosDao.registrar(ventaTipoServicios);
			if (r != null) {
				defaultValues();
			} else {
				defaultValues();
			}
		} catch (Exception e) {
			System.out.println("Error en registro de ventaTipoServicios: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			System.out.println("Ingreso a actualizar");
			if (ventaTipoServicios.getDescripcion().trim().isEmpty()
					|| ventaTipoServicios.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				ventaTipoServicios.setCompania(getSucursal().getCompania());
				ventaTipoServicios.setSucursal(getSucursal());
				ventaTipoServicios.setFechaModificacion(new Date());
				ventaTipoServicios.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getLogin());
				VentaTipoServicios r = ventaTipoServiciosDao.modificar(ventaTipoServicios);
				if (r != null) {
					defaultValues();
				} else {
					defaultValues();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de ventaTipoServicios: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			if (ventaTipoServiciosDao.eliminar(ventaTipoServicios)) {
				FacesUtil.infoMessage("VentaTipoServicios Eliminado",
						ventaTipoServicios.toString());
				defaultValues();
			} else {
				FacesUtil.errorMessage("Error al eliminar");
				defaultValues();
			}
		} catch (Exception e) {
			System.out.println("Error en eliminacion de ventaTipoServicios: "
					+ e.getMessage());
		}
	}

	public void onRowSelect(SelectEvent event) {
		ventaTipoServicios = ventaTipoServiciosSelected;
		crear = false;
		seleccionado = true;
		registrar = false;
		currentPage = "/pages/ventas/tipo_servicios/edit.xhtml";
	}

	public void actionNuevo() {
		System.out.println("Ingreso a actionNuevo...");
		crear = false;
		seleccionado = false;
		registrar = true;
		ventaTipoServicios = new VentaTipoServicios();
		ventaTipoServicios.setFechaRegistro(new Date());
		currentPage = "/pages/ventas/tipo_servicios/edit.xhtml";
	}

	public void actionCancelar() {
		crear = true;
		seleccionado = false;
		registrar = false;
		ventaTipoServicios = new VentaTipoServicios();
		currentPage = "/pages/ventas/tipo_servicios/list.xhtml";
	}

	public boolean isSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

	public VentaTipoServicios getVentaTipoServicios() {
		return ventaTipoServicios;
	}

	public VentaTipoServicios getVentaTipoServiciosSelected() {
		return ventaTipoServiciosSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<VentaTipoServicios> getListaVentaTipoServicios() {
		return listaVentaTipoServicios;
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

	public void setVentaTipoServicios(VentaTipoServicios ventaTipoServicios) {
		this.ventaTipoServicios = ventaTipoServicios;
	}

	public void setVentaTipoServiciosSelected(VentaTipoServicios ventaTipoServiciosSelected) {
		this.ventaTipoServiciosSelected = ventaTipoServiciosSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaVentaTipoServicios(List<VentaTipoServicios> listaVentaTipoServicios) {
		this.listaVentaTipoServicios = listaVentaTipoServicios;
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

	/**
	 * @return the currentPage
	 */
	public String getCurrentPage() {
		return currentPage;
	}

	/**
	 * @param currentPage the currentPage to set
	 */
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

}
