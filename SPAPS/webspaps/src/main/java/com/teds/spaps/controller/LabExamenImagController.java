/**
 * 
 */
package com.teds.spaps.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.SelectEvent;

import com.teds.spaps.interfaces.dao.ICiudadDao;
import com.teds.spaps.interfaces.dao.ILabExamenImagDao;
import com.teds.spaps.interfaces.dao.ILabGrupoExamenImagDao;
import com.teds.spaps.model.Ciudad;
import com.teds.spaps.model.LabExamenDetalleImag;
import com.teds.spaps.model.LabExamenImag;
import com.teds.spaps.model.LabGrupoExamenImag;
import com.teds.spaps.model.LabValoresRef;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

/**
 * @author ANITA
 *
 */
@ManagedBean(name = "labExamenImagController")
@ViewScoped
public class LabExamenImagController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6693305123755133558L;
	/******* DAO **********/
	private @Inject ILabExamenImagDao labExamenDao;
	private @Inject SessionMain sessionMain;
	private @Inject ILabGrupoExamenImagDao grupoExamenDao;
	private @Inject ICiudadDao ciudadDao;

	/******* OBJECT **********/
	private LabExamenImag labExamen;
	private LabExamenImag labExamenSelected;
	private Sucursal sucursal;
	private LabGrupoExamenImag selectedGrupoExamen;
	private LabExamenDetalleImag newExamenDetalle;
	private LabValoresRef valoresRef;
	private LabExamenDetalleImag selectedExamenDetalle;

	/******* LIST **********/
	private List<LabExamenImag> listaLabExamen;
	private List<LabGrupoExamenImag> listGrupoExamens = new ArrayList<LabGrupoExamenImag>();
	private List<LabExamenDetalleImag> listExamenDetalles = new ArrayList<LabExamenDetalleImag>();
	private List<Ciudad> listCiudad = new ArrayList<Ciudad>();

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;
	private boolean seleccionado = false;

	// URL PAGE
	private String currentPage = "/pages/laboratorio/examen_imagenologia/list.xhtml";

	/**
	 * 
	 */
	public LabExamenImagController() {
	}

	@PostConstruct
	public void initNew() {
		sucursal = sessionMain.PruebaSucursal();
		System.out.println("Sucursal : " + sucursal.getDescripcion());
		System.out.println("Compañia : "
				+ sucursal.getCompania().getDescripcion());
		listCiudad = ciudadDao.obtenerAllActivos();
		//listCiudad = ciudadDao.obtenerPorCompania(sucursal.getCompania());
		valoresRef = new LabValoresRef();
		defaultValues();
	}

	private void defaultValues() {
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
		seleccionado = false;
		selectedGrupoExamen = new LabGrupoExamenImag();
		labExamen = new LabExamenImag();
		labExamenSelected = new LabExamenImag();
		listExamenDetalles.clear();
		listaLabExamen = labExamenDao
				.obtenerPorCompania(sucursal.getCompania());
		currentPage = "/pages/laboratorio/examen_imagenologia/list.xhtml";
	}

	public void registrar() {
		try {
			System.out.println("Ingreso a registrar");

			if (selectedGrupoExamen.getId().intValue() == 0
					|| selectedGrupoExamen == null || getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION", "Seleccione Grupo Examen");
				return;
			}

			if (labExamen.getDescripcion().trim().isEmpty()
					|| labExamen.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			}
			labExamen.setGrupoExamen(selectedGrupoExamen);
			labExamen.setCompania(sucursal.getCompania());
			labExamen.setSucursal(sucursal);
			labExamen.setFechaRegistro(new Date());
			labExamen.setFechaModificacion(labExamen.getFechaRegistro());
			labExamen.setUsuarioRegistro(sessionMain.getUsuarioLogin()
					.getLogin());

			LabExamenImag r = labExamenDao.registrar(labExamen, listExamenDetalles);
			if (r != null) {
				defaultValues();
			} else {
				defaultValues();
			}
		} catch (Exception e) {
			System.out.println("Error en registro de labExamen: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			System.out.println("Ingreso a actualizar");
			if (labExamen.getDescripcion().trim().isEmpty()
					|| labExamen.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {

				labExamen.setGrupoExamen(selectedGrupoExamen);
				labExamen.setCompania(getSucursal().getCompania());
				labExamen.setSucursal(getSucursal());
				labExamen.setFechaModificacion(new Date());
				labExamen.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getLogin());
				LabExamenImag r = labExamenDao.modificar(labExamen,
						listExamenDetalles);
				if (r != null) {
					defaultValues();
				} else {
					defaultValues();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de labExamen: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			if (labExamenDao.eliminar(labExamen)) {
				FacesUtil.infoMessage("LabExamen Eliminado",
						labExamen.toString());
				defaultValues();
			} else {
				FacesUtil.errorMessage("Error al eliminar");
				defaultValues();
			}
		} catch (Exception e) {
			System.out.println("Error en eliminacion de labExamen: "
					+ e.getMessage());
		}
	}

	public void onRowSelect(SelectEvent event) {
		labExamen = labExamenSelected;
		crear = false;
		seleccionado = true;
		registrar = false;
		selectedGrupoExamen = labExamen.getGrupoExamen();
		listExamenDetalles = labExamen.getListExamenDetalle();
		// listExamenDetalles = examenDetalleDao.obtenerPorExamen(labExamen);
		currentPage = "/pages/laboratorio/examen_imagenologia/edit.xhtml";
	}

	public void actionNuevo() {
		System.out.println("Ingreso a actionNuevo...");
		crear = false;
		seleccionado = false;
		registrar = true;
		labExamen = new LabExamenImag();
		labExamen.setFechaRegistro(new Date());
		currentPage = "/pages/laboratorio/examen_imagenologia/edit.xhtml";
	}

	public void actionCancelar() {
		crear = true;
		seleccionado = false;
		registrar = false;
		labExamen = new LabExamenImag();
		currentPage = "/pages/laboratorio/examen_imagenologia/list.xhtml";
	}

	// ONCOMPLETETEXT GRUPO EXAMEN
	public List<LabGrupoExamenImag> completeGrupoExamen(String query) {
		String upperQuery = query.toUpperCase();
		// if (upperQuery.trim().length() == 0) {
		// FacesUtil.infoMessage("VALIDACION",
		// "Escriba el Nombre del Paciente");
		// return null;
		// }
		listGrupoExamens = grupoExamenDao.obtenerPorCompania(upperQuery,
				sucursal.getCompania());
		return listGrupoExamens;
	}

	public void onRowSelectGrupoExamenClick(SelectEvent event) {
		System.out.println("Ingreso a onRowSelectPacienteClick");
		String nombre = event.getObject().toString();
		for (LabGrupoExamenImag i : listGrupoExamens) {
			if (i.getDescripcion().equals(nombre)) {
				selectedGrupoExamen = i;
				System.out.println("Paciente : "
						+ selectedGrupoExamen.getDescripcion());
				return;
			}
		}
	}

	public void resetearFitrosTabla(String id) {
		DataTable table = (DataTable) FacesContext.getCurrentInstance()
				.getViewRoot().findComponent(id);
		table.setSelection(null);
		table.reset();
	}

	public void onRowDeleteDI(LabExamenDetalleImag labExamenDetalle) {
		if (listExamenDetalles.size() > 0) {
			for (int i = 0; i < listExamenDetalles.size(); i++) {
				LabExamenDetalleImag auxiliar = listExamenDetalles.get(i);
				if (auxiliar.getDescripcion().equals(
						labExamenDetalle.getDescripcion())) {
					listExamenDetalles.remove(i);
				}
			}
			FacesContext.getCurrentInstance().renderResponse();
			resetearFitrosTabla("form001:formDocumentos:documentos");
		}
	}

	public void onRowEditDI(LabExamenDetalleImag labExamenDetalle) {
		if (verificarDetalle(labExamenDetalle)) {
			FacesUtil.fatalMessage("Ya se encuentra registrado.");
			listExamenDetalles.remove(labExamenDetalle);
			// deleteDI(identificacionIndividuo);
		}
	}

	private boolean verificarDetalle(LabExamenDetalleImag labExamenDetalle1) {
		for (LabExamenDetalleImag labExamenDetalle : listExamenDetalles) {
			if (labExamenDetalle.getDescripcion().equals(
					labExamenDetalle1.getDescripcion())
					|| labExamenDetalle.getParametros().equals(
							labExamenDetalle1.getParametros())) {
				return true;
			}
		}
		return false;
	}

	public void actionMdificarDetalle(LabExamenDetalleImag labExamenDetalle1) {

		newExamenDetalle = labExamenDetalle1;
		FacesUtil.updateComponent("formDlg001");
		FacesUtil.showDialog("dlgDialog");
	}

	public void actionMdificarSubDetalle(LabValoresRef labExamenDetalle1) {

		valoresRef = labExamenDetalle1;
		FacesUtil.updateComponent("formDlg002");
		FacesUtil.showDialog("dlgDialogRef");
	}

	public void actionNuevoDetalleExamen() {
		try {
			System.out.println("Ingreso a actionNuevoDetalleExamen...");
			newExamenDetalle = new LabExamenDetalleImag();
			FacesUtil.updateComponent("formDlg001");
			FacesUtil.showDialog("dlgDialog");

		} catch (Exception e) {
			System.err.println("Error en actionNuevoDetalleExamen : "
					+ e.getStackTrace());
		}
	}

	public void actionNuevoSubDetalleExamen(LabExamenDetalleImag labExamenDetalle) {
		try {
			System.out.println("Ingreso a actionNuevoSubDetalleExamen...");
			selectedExamenDetalle = labExamenDetalle;
			valoresRef = new LabValoresRef();
			FacesUtil.updateComponent("formDlg002");
			FacesUtil.showDialog("dlgDialogRef");

		} catch (Exception e) {
			System.err.println("Error en actionNuevoSubDetalleExamen : "
					+ e.getStackTrace());
		}
	}

	public void registrarOModificarDetalle() {
		if (newExamenDetalle.getId().equals(0)) {// nuevo

			newExamenDetalle.setId(listExamenDetalles.size() * (-1));
			newExamenDetalle.setUsuarioRegistro(sessionMain.getUsuarioLogin()
					.getLogin());
			newExamenDetalle.setFechaRegistro(new Date());
			newExamenDetalle.setCompania(sucursal.getCompania());
			newExamenDetalle.setSucursal(sucursal);
			newExamenDetalle.setCorrelativo(listExamenDetalles.size() + 1);
			listExamenDetalles.add(newExamenDetalle);
		} else {
			for (LabExamenDetalleImag us : listExamenDetalles) {
				if (us.equals(newExamenDetalle)) {
					us = newExamenDetalle;
				}
			}
		}
		FacesUtil.hideDialog("dlgDialog");
	}

	

	public void onRowDeleteDetalle(LabExamenDetalleImag labExamenDetalle) {
		listExamenDetalles.remove(labExamenDetalle);
	}

	

	public boolean isSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

	public LabExamenImag getLabExamen() {
		return labExamen;
	}

	public LabExamenImag getLabExamenSelected() {
		return labExamenSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<LabExamenImag> getListaLabExamen() {
		return listaLabExamen;
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

	public void setLabExamen(LabExamenImag labExamen) {
		this.labExamen = labExamen;
	}

	public void setLabExamenSelected(LabExamenImag labExamenSelected) {
		this.labExamenSelected = labExamenSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaLabExamen(List<LabExamenImag> listaLabExamen) {
		this.listaLabExamen = listaLabExamen;
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
	 * @return the selectedGrupoExamen
	 */
	public LabGrupoExamenImag getSelectedGrupoExamen() {
		return selectedGrupoExamen;
	}

	/**
	 * @param selectedGrupoExamen
	 *            the selectedGrupoExamen to set
	 */
	public void setSelectedGrupoExamen(LabGrupoExamenImag selectedGrupoExamen) {
		this.selectedGrupoExamen = selectedGrupoExamen;
	}

	/**
	 * @return the listGrupoExamens
	 */
	public List<LabGrupoExamenImag> getListGrupoExamens() {
		return listGrupoExamens;
	}

	/**
	 * @param listGrupoExamens
	 *            the listGrupoExamens to set
	 */
	public void setListGrupoExamens(List<LabGrupoExamenImag> listGrupoExamens) {
		this.listGrupoExamens = listGrupoExamens;
	}

	/**
	 * @return the listExamenDetalles
	 */
	public List<LabExamenDetalleImag> getListExamenDetalles() {
		return listExamenDetalles;
	}

	/**
	 * @param listExamenDetalles
	 *            the listExamenDetalles to set
	 */
	public void setListExamenDetalles(List<LabExamenDetalleImag> listExamenDetalles) {
		this.listExamenDetalles = listExamenDetalles;
	}

	/**
	 * @return the newExamenDetalle
	 */
	public LabExamenDetalleImag getNewExamenDetalle() {
		return newExamenDetalle;
	}

	/**
	 * @param newExamenDetalle
	 *            the newExamenDetalle to set
	 */
	public void setNewExamenDetalle(LabExamenDetalleImag newExamenDetalle) {
		this.newExamenDetalle = newExamenDetalle;
	}

	/**
	 * @return the valoresRef
	 */
	public LabValoresRef getValoresRef() {
		return valoresRef;
	}

	/**
	 * @param valoresRef
	 *            the valoresRef to set
	 */
	public void setValoresRef(LabValoresRef valoresRef) {
		this.valoresRef = valoresRef;
	}

	/**
	 * @return the selectedExamenDetalle
	 */
	public LabExamenDetalleImag getSelectedExamenDetalle() {
		return selectedExamenDetalle;
	}

	/**
	 * @param selectedExamenDetalle
	 *            the selectedExamenDetalle to set
	 */
	public void setSelectedExamenDetalle(LabExamenDetalleImag selectedExamenDetalle) {
		this.selectedExamenDetalle = selectedExamenDetalle;
	}

	/**
	 * @return the listCiudad
	 */
	public List<Ciudad> getListCiudad() {
		return listCiudad;
	}

	/**
	 * @param listCiudad
	 *            the listCiudad to set
	 */
	public void setListCiudad(List<Ciudad> listCiudad) {
		this.listCiudad = listCiudad;
	}

}
