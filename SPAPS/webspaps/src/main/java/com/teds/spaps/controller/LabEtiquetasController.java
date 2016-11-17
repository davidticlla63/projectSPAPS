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

import com.teds.spaps.interfaces.dao.ILabEtiquetasDao;
import com.teds.spaps.model.LabEtiquetas;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

/**
 * @author david
 *
 */
@ManagedBean(name = "labEtiquetasController")
@ViewScoped
public class LabEtiquetasController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -125210622622704977L;
	/******* DAO **********/
	private @Inject ILabEtiquetasDao labEtiquetasDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private LabEtiquetas labEtiquetas;
	private LabEtiquetas labEtiquetasSelected;
	private Sucursal sucursal;

	/******* LIST **********/
	private List<LabEtiquetas> listaLabEtiquetas;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;
	private boolean seleccionado = false;

	// URL PAGE
	private String currentPage = "/pages/laboratorio/etiquetas/list.xhtml";

	/**
	 * 
	 */
	public LabEtiquetasController() {
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
		labEtiquetas = new LabEtiquetas();
		labEtiquetasSelected = new LabEtiquetas();
		listaLabEtiquetas = labEtiquetasDao.obtenerPorCompania(sucursal
				.getCompania());
		currentPage = "/pages/laboratorio/etiquetas/list.xhtml";
	}

	public void registrar() {
		try {
			System.out.println("Ingreso a registrar");

			if (labEtiquetas.getDescripcion().trim().isEmpty()
					|| labEtiquetas.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			}
			labEtiquetas.setCompania(sucursal.getCompania());
			labEtiquetas.setFechaRegistro(new Date());
			labEtiquetas.setFechaModificacion(labEtiquetas.getFechaRegistro());
			labEtiquetas.setUsuarioRegistro(sessionMain.getUsuarioLogin()
					.getLogin());

			LabEtiquetas r = labEtiquetasDao.registrar(labEtiquetas);
			if (r != null) {
				defaultValues();
			} else {
				defaultValues();
			}
		} catch (Exception e) {
			System.out.println("Error en registro de labEtiquetas: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			System.out.println("Ingreso a actualizar");
			if (labEtiquetas.getDescripcion().trim().isEmpty()
					|| labEtiquetas.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				labEtiquetas.setCompania(getSucursal().getCompania());
				labEtiquetas.setFechaModificacion(new Date());
				labEtiquetas.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getLogin());
				LabEtiquetas r = labEtiquetasDao.modificar(labEtiquetas);
				if (r != null) {
					defaultValues();
				} else {
					defaultValues();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de labEtiquetas: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			if (labEtiquetasDao.eliminar(labEtiquetas)) {
				FacesUtil.infoMessage("LabEtiquetas Eliminado",
						labEtiquetas.toString());
				defaultValues();
			} else {
				FacesUtil.errorMessage("Error al eliminar");
				defaultValues();
			}
		} catch (Exception e) {
			System.out.println("Error en eliminacion de labEtiquetas: "
					+ e.getMessage());
		}
	}

	public void onRowSelect(SelectEvent event) {
		labEtiquetas = labEtiquetasSelected;
		crear = false;
		seleccionado = true;
		registrar = false;
		currentPage = "/pages/laboratorio/etiquetas/edit.xhtml";
	}

	public void actionNuevo() {
		System.out.println("Ingreso a actionNuevo...");
		crear = false;
		seleccionado = false;
		registrar = true;
		labEtiquetas = new LabEtiquetas();
		labEtiquetas.setFechaRegistro(new Date());
		currentPage = "/pages/laboratorio/etiquetas/edit.xhtml";
	}

	public void actionCancelar() {
		crear = true;
		seleccionado = false;
		registrar = false;
		labEtiquetas = new LabEtiquetas();
		currentPage = "/pages/laboratorio/etiquetas/list.xhtml";
	}

	public boolean isSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

	public LabEtiquetas getLabEtiquetas() {
		return labEtiquetas;
	}

	public LabEtiquetas getLabEtiquetasSelected() {
		return labEtiquetasSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<LabEtiquetas> getListaLabEtiquetas() {
		return listaLabEtiquetas;
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

	public void setLabEtiquetas(LabEtiquetas labEtiquetas) {
		this.labEtiquetas = labEtiquetas;
	}

	public void setLabEtiquetasSelected(LabEtiquetas labEtiquetasSelected) {
		this.labEtiquetasSelected = labEtiquetasSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaLabEtiquetas(List<LabEtiquetas> listaLabEtiquetas) {
		this.listaLabEtiquetas = listaLabEtiquetas;
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
