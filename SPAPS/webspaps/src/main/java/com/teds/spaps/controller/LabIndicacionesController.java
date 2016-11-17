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

import com.teds.spaps.interfaces.dao.ILabIndicacionesDao;
import com.teds.spaps.model.LabIndicaciones;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

/**
 * @author david
 *
 */
@ManagedBean(name = "labIndicacionesController")
@ViewScoped
public class LabIndicacionesController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -125210622622704977L;
	/******* DAO **********/
	private @Inject ILabIndicacionesDao labIndicacionesDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private LabIndicaciones labIndicaciones;
	private LabIndicaciones labIndicacionesSelected;
	private Sucursal sucursal;

	/******* LIST **********/
	private List<LabIndicaciones> listaLabIndicaciones;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;
	private boolean seleccionado = false;

	// URL PAGE
	private String currentPage = "/pages/laboratorio/indicaciones/list.xhtml";

	/**
	 * 
	 */
	public LabIndicacionesController() {
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
		labIndicaciones = new LabIndicaciones();
		labIndicacionesSelected = new LabIndicaciones();
		listaLabIndicaciones = labIndicacionesDao.obtenerPorCompania(sucursal
				.getCompania());
		currentPage = "/pages/laboratorio/indicaciones/list.xhtml";
	}

	public void registrar() {
		try {
			System.out.println("Ingreso a registrar");

			if (labIndicaciones.getDescripcion().trim().isEmpty()
					|| labIndicaciones.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			}
			labIndicaciones.setCompania(sucursal.getCompania());
			labIndicaciones.setFechaRegistro(new Date());
			labIndicaciones.setFechaModificacion(labIndicaciones.getFechaRegistro());
			labIndicaciones.setUsuarioRegistro(sessionMain.getUsuarioLogin()
					.getLogin());

			LabIndicaciones r = labIndicacionesDao.registrar(labIndicaciones);
			if (r != null) {
				defaultValues();
			} else {
				defaultValues();
			}
		} catch (Exception e) {
			System.out.println("Error en registro de labIndicaciones: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			System.out.println("Ingreso a actualizar");
			if (labIndicaciones.getDescripcion().trim().isEmpty()
					|| labIndicaciones.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				labIndicaciones.setCompania(getSucursal().getCompania());
				labIndicaciones.setFechaModificacion(new Date());
				labIndicaciones.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getLogin());
				LabIndicaciones r = labIndicacionesDao.modificar(labIndicaciones);
				if (r != null) {
					defaultValues();
				} else {
					defaultValues();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de labIndicaciones: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			if (labIndicacionesDao.eliminar(labIndicaciones)) {
				FacesUtil.infoMessage("LabIndicaciones Eliminado",
						labIndicaciones.toString());
				defaultValues();
			} else {
				FacesUtil.errorMessage("Error al eliminar");
				defaultValues();
			}
		} catch (Exception e) {
			System.out.println("Error en eliminacion de labIndicaciones: "
					+ e.getMessage());
		}
	}

	public void onRowSelect(SelectEvent event) {
		labIndicaciones = labIndicacionesSelected;
		crear = false;
		seleccionado = true;
		registrar = false;
		currentPage = "/pages/laboratorio/indicaciones/edit.xhtml";
	}

	public void actionNuevo() {
		System.out.println("Ingreso a actionNuevo...");
		crear = false;
		seleccionado = false;
		registrar = true;
		labIndicaciones = new LabIndicaciones();
		labIndicaciones.setFechaRegistro(new Date());
		currentPage = "/pages/laboratorio/indicaciones/edit.xhtml";
	}

	public void actionCancelar() {
		crear = true;
		seleccionado = false;
		registrar = false;
		labIndicaciones = new LabIndicaciones();
		currentPage = "/pages/laboratorio/indicaciones/list.xhtml";
	}

	public boolean isSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

	public LabIndicaciones getLabIndicaciones() {
		return labIndicaciones;
	}

	public LabIndicaciones getLabIndicacionesSelected() {
		return labIndicacionesSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<LabIndicaciones> getListaLabIndicaciones() {
		return listaLabIndicaciones;
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

	public void setLabIndicaciones(LabIndicaciones labIndicaciones) {
		this.labIndicaciones = labIndicaciones;
	}

	public void setLabIndicacionesSelected(LabIndicaciones labIndicacionesSelected) {
		this.labIndicacionesSelected = labIndicacionesSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaLabIndicaciones(List<LabIndicaciones> listaLabIndicaciones) {
		this.listaLabIndicaciones = listaLabIndicaciones;
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

}
