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

import com.teds.spaps.interfaces.dao.ILabGrupoExamenDao;
import com.teds.spaps.model.LabGrupoExamen;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

/**
 * @author ANITA
 *
 */
@ManagedBean(name = "labGrupoExamenController")
@ViewScoped
public class LabGrupoExamenController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -125210622622704977L;
	/******* DAO **********/
	private @Inject ILabGrupoExamenDao labGrupoDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private LabGrupoExamen labGrupo;
	private LabGrupoExamen labGrupoExamenSelected;
	private Sucursal sucursal;

	/******* LIST **********/
	private List<LabGrupoExamen> listaLabGrupoExamen;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;
	private boolean seleccionado = false;
	
	//URL PAGE
	private String currentPage = "/pages/laboratorio/grupo_examen/list.xhtml";


	/**
	 * 
	 */
	public LabGrupoExamenController() {
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
		labGrupo = new LabGrupoExamen();
		labGrupoExamenSelected = new LabGrupoExamen();
		listaLabGrupoExamen = labGrupoDao.obtenerPorCompania(sucursal
				.getCompania());
		currentPage = "/pages/laboratorio/grupo_examen/list.xhtml";
	}

	public void registrar() {
		try {
			System.out.println("Ingreso a registrar");

			if (labGrupo.getDescripcion().trim().isEmpty()
					|| labGrupo.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			}
			labGrupo.setCompania(sucursal.getCompania());
			labGrupo.setSucursal(sucursal);
			labGrupo.setFechaRegistro(new Date());
			labGrupo
					.setFechaModificacion(labGrupo.getFechaRegistro());
			labGrupo.setUsuarioRegistro(sessionMain.getUsuarioLogin()
					.getLogin());

			LabGrupoExamen r = labGrupoDao.registrar(labGrupo);
			if (r != null) {
				defaultValues();
			} else {
				defaultValues();
			}
		} catch (Exception e) {
			System.out.println("Error en registro de labGrupoExamen: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			System.out.println("Ingreso a actualizar");
			if (labGrupo.getDescripcion().trim().isEmpty()
					|| labGrupo.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				labGrupo.setCompania(getSucursal().getCompania());
				labGrupo.setSucursal(getSucursal());
				labGrupo.setFechaModificacion(new Date());
				labGrupo.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getLogin());
				LabGrupoExamen r = labGrupoDao.modificar(labGrupo);
				if (r != null) {
					defaultValues();
				} else {
					defaultValues();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de labGrupoExamen: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			if (labGrupoDao.eliminar(labGrupo)) {
				FacesUtil.infoMessage("LabGrupoExamen Eliminado",
						labGrupo.toString());
				defaultValues();
			} else {
				FacesUtil.errorMessage("Error al eliminar");
				defaultValues();
			}
		} catch (Exception e) {
			System.out.println("Error en eliminacion de labGrupoExamen: "
					+ e.getMessage());
		}
	}

	public void onRowSelect(SelectEvent event) {
		labGrupo = labGrupoExamenSelected;
		crear = false;
		seleccionado = true;
		registrar = false;
		currentPage = "/pages/laboratorio/grupo_examen/edit.xhtml";
	}

	public void actionNuevo() {
		System.out.println("Ingreso a actionNuevo...");
		crear = false;
		seleccionado = false;
		registrar = true;
		labGrupo = new LabGrupoExamen();
		labGrupo.setFechaRegistro(new Date());
		currentPage = "/pages/laboratorio/grupo_examen/edit.xhtml";
	}

	public void actionCancelar() {
		crear = true;
		seleccionado = false;
		registrar = false;
		labGrupo = new LabGrupoExamen();
		currentPage = "/pages/laboratorio/grupo_examen/list.xhtml";
	}

	public boolean isSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

	public LabGrupoExamen getLabGrupoExamen() {
		return labGrupo;
	}

	public LabGrupoExamen getLabGrupoExamenSelected() {
		return labGrupoExamenSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<LabGrupoExamen> getListaLabGrupoExamen() {
		return listaLabGrupoExamen;
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

	public void setLabGrupoExamen(LabGrupoExamen labGrupo) {
		this.labGrupo = labGrupo;
	}

	public void setLabGrupoExamenSelected(LabGrupoExamen labGrupoExamenSelected) {
		this.labGrupoExamenSelected = labGrupoExamenSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaLabGrupoExamen(List<LabGrupoExamen> listaLabGrupoExamen) {
		this.listaLabGrupoExamen = listaLabGrupoExamen;
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
