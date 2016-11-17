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

import com.teds.spaps.interfaces.dao.ITipoAtencionDao;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.TipoAtencion;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

/**
 * 
 * @author mauriciobejaranorivera
 *
 */
@ManagedBean(name = "tipoAtencionController")
@ViewScoped
public class TipoAtencionController implements Serializable {

	private static final long serialVersionUID = 1L;

	/******* DAO **********/
	private @Inject ITipoAtencionDao tipoAtencionDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private TipoAtencion tipoAtencion;
	private TipoAtencion tipoAtencionSelected;
	private Sucursal sucursal;

	/******* LIST **********/
	private List<TipoAtencion> listaTipoAtencion;
	private List<TipoAtencion> listaTipoAtencions = new ArrayList<TipoAtencion>();

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;
	private boolean seleccionado = false;

	// VAR
	private String currentPage = "/pages/citas/tipo_atencion/list.xhtml";

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
		tipoAtencion = new TipoAtencion();
		tipoAtencionSelected = new TipoAtencion();
		listaTipoAtencion = tipoAtencionDao.obtenerPorCompania(sucursal
				.getCompania());
		currentPage = "/pages/citas/tipo_atencion/list.xhtml";
	}

	public void registrar() {
		try {
			System.out.println("Ingreso a registrar");

			if (tipoAtencion.getNombre().trim().isEmpty()
					|| tipoAtencion.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			}
			tipoAtencion.setCompania(sucursal.getCompania());
			tipoAtencion.setFechaRegistro(new Date());
			tipoAtencion.setFechaModificacion(tipoAtencion.getFechaRegistro());
			tipoAtencion.setUsuarioRegistro(sessionMain.getUsuarioLogin()
					.getLogin());

			TipoAtencion r = tipoAtencionDao.registrar(tipoAtencion);
			if (r != null) {
				defaultValues();
			} else {
				return;
			}
		} catch (Exception e) {
			System.out.println("Error en registro de tipoAtencion: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			System.out.println("Ingreso a actualizar");
			if (tipoAtencion.getNombre().trim().isEmpty()
					|| tipoAtencion.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				tipoAtencion.setCompania(sucursal.getCompania());
				tipoAtencion.setFechaModificacion(new Date());
				tipoAtencion.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getLogin());
				TipoAtencion r = tipoAtencionDao.modificar(tipoAtencion);
				if (r != null) {
					defaultValues();
				} else {
					return;
				}
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de tipoAtencion: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			if (tipoAtencionDao.eliminar(tipoAtencion)) {
				FacesUtil.infoMessage("TipoAtencion Eliminado",
						tipoAtencion.toString());
				defaultValues();
			} else {
				FacesUtil.errorMessage("Error al eliminar");
				defaultValues();
			}
		} catch (Exception e) {
			System.out.println("Error en eliminacion de tipoAtencion: "
					+ e.getMessage());
		}
	}

	public void onRowSelect(SelectEvent event) {
		tipoAtencion = tipoAtencionSelected;
		crear = false;
		seleccionado = true;
		registrar = false;
		currentPage = "/pages/citas/tipo_atencion/edit.xhtml";
	}

	public void actionNuevo() {
		System.out.println("Ingreso a actionNuevo...");
		crear = false;
		seleccionado = false;
		registrar = true;
		tipoAtencion = new TipoAtencion();
		tipoAtencion.setFechaRegistro(new Date());
		currentPage = "/pages/citas/tipo_atencion/edit.xhtml";
	}

	public void actionCancelar() {
		crear = true;
		seleccionado = false;
		registrar = false;
		tipoAtencion = new TipoAtencion();
		currentPage = "/pages/citas/tipo_atencion/list.xhtml";
	}

	public void actionModificar(Integer id) {
		crear = false;
		seleccionado = true;
		registrar = false;
		currentPage = "/pages/citas/tipo_atencion/edit.xhtml";
	}

	public boolean isSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

	/**
		 * 
		 */

	public TipoAtencion getTipoAtencion() {
		return tipoAtencion;
	}

	public TipoAtencion getTipoAtencionSelected() {
		return tipoAtencionSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<TipoAtencion> getListaTipoAtencion() {
		return listaTipoAtencion;
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

	public void setTipoAtencion(TipoAtencion tipoAtencion) {
		this.tipoAtencion = tipoAtencion;
	}

	public void setTipoAtencionSelected(TipoAtencion tipoAtencionSelected) {
		this.tipoAtencionSelected = tipoAtencionSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaTipoAtencion(List<TipoAtencion> listaTipoAtencion) {
		this.listaTipoAtencion = listaTipoAtencion;
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
	 * @param currentPage
	 *            the currentPage to set
	 */
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * @return the listaTipoAtencions
	 */
	public List<TipoAtencion> getListaTipoAtencions() {
		return listaTipoAtencions;
	}

	/**
	 * @param listaTipoAtencions
	 *            the listaTipoAtencions to set
	 */
	public void setListaTipoAtencions(List<TipoAtencion> listaTipoAtencions) {
		this.listaTipoAtencions = listaTipoAtencions;
	}

}
