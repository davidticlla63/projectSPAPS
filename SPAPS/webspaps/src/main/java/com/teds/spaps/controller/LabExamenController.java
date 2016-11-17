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

import com.teds.spaps.enums.TipoValor;
import com.teds.spaps.interfaces.dao.ICiudadDao;
import com.teds.spaps.interfaces.dao.ILabExamenDao;
import com.teds.spaps.interfaces.dao.ILabGrupoExamenDao;
import com.teds.spaps.interfaces.dao.ILabIndicacionesDao;
import com.teds.spaps.model.Ciudad;
import com.teds.spaps.model.LabExamen;
import com.teds.spaps.model.LabExamenDetalle;
import com.teds.spaps.model.LabExamenIndicaciones;
import com.teds.spaps.model.LabGrupoExamen;
import com.teds.spaps.model.LabIndicaciones;
import com.teds.spaps.model.LabValoresRef;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

/**
 * @author ANITA
 *
 */
@ManagedBean(name = "labExamenController")
@ViewScoped
public class LabExamenController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6693305123755133558L;
	/******* DAO **********/
	private @Inject ILabExamenDao labExamenDao;
	private @Inject SessionMain sessionMain;
	private @Inject ILabGrupoExamenDao grupoExamenDao;
	private @Inject ICiudadDao ciudadDao;
	private @Inject ILabIndicacionesDao indicacionesDao;

	/******* OBJECT **********/
	private LabExamen labExamen;
	private LabExamen labExamenSelected;
	private Sucursal sucursal;
	private LabGrupoExamen selectedGrupoExamen;
	private LabExamenDetalle newExamenDetalle;
	private LabValoresRef valoresRef;
	private LabExamenDetalle selectedExamenDetalle;

	/******* LIST **********/
	private List<LabExamen> listaLabExamen;
	private List<LabGrupoExamen> listGrupoExamens = new ArrayList<LabGrupoExamen>();
	private List<LabExamenDetalle> listExamenDetalles = new ArrayList<LabExamenDetalle>();
	private List<Ciudad> listCiudad = new ArrayList<Ciudad>();
	private List<LabIndicaciones> listIndicaciones = new ArrayList<LabIndicaciones>();

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;
	private boolean seleccionado = false;

	private String indicacion;

	// URL PAGE
	private String currentPage = "/pages/laboratorio/examen/list.xhtml";

	/**
	 * 
	 */
	public LabExamenController() {
	}

	@PostConstruct
	public void initNew() {
		sucursal = sessionMain.PruebaSucursal();
		System.out.println("Sucursal : " + sucursal.getDescripcion());
		System.out.println("Compañia : "
				+ sucursal.getCompania().getDescripcion());
		listCiudad = ciudadDao.obtenerAllActivos();
		valoresRef = new LabValoresRef();
		defaultValues();
	}

	private void defaultValues() {
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
		seleccionado = false;
		selectedGrupoExamen = new LabGrupoExamen();
		labExamen = new LabExamen();
		labExamenSelected = new LabExamen();
		listExamenDetalles.clear();
		listaLabExamen = labExamenDao
				.obtenerPorCompania(sucursal.getCompania());
		setListIndicaciones(indicacionesDao.obtenerPorCompania(sucursal
				.getCompania()));
		currentPage = "/pages/laboratorio/examen/list.xhtml";
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

			LabExamen r = labExamenDao.registrar(labExamen, listExamenDetalles);
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
				LabExamen r = labExamenDao.modificar(labExamen,
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
		currentPage = "/pages/laboratorio/examen/edit.xhtml";
	}

	public void actionNuevo() {
		System.out.println("Ingreso a actionNuevo...");
		crear = false;
		seleccionado = false;
		registrar = true;
		labExamen = new LabExamen();
		labExamen.setFechaRegistro(new Date());
		currentPage = "/pages/laboratorio/examen/edit.xhtml";
	}

	public void actionCancelar() {
		crear = true;
		seleccionado = false;
		registrar = false;
		labExamen = new LabExamen();
		currentPage = "/pages/laboratorio/examen/list.xhtml";
	}

	// ONCOMPLETETEXT GRUPO EXAMEN
	public List<LabGrupoExamen> completeGrupoExamen(String query) {
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
		for (LabGrupoExamen i : listGrupoExamens) {
			if (i.getDescripcion().equals(nombre)) {
				selectedGrupoExamen = i;
				System.out.println("Paciente : "
						+ selectedGrupoExamen.getDescripcion());
				return;
			}
		}
	}

	// ONCOMPLETETEXT GRUPO EXAMEN
	public List<LabIndicaciones> completeIndicaciones(String query) {
		String upperQuery = query.toUpperCase();
		listIndicaciones = indicacionesDao.obtenerPorCompania(upperQuery,
				sucursal.getCompania());
		// List<LabIndicaciones> list= new ArrayList<LabIndicaciones>();
		// for (LabIndicaciones labIndicaciones : listIndicaciones) {
		// if (labIndicaciones.getDescripcion().startsWith(upperQuery)) {
		// list.add(labIndicaciones);
		// }
		// }
		return listIndicaciones;
	}

	public void onRowSelectIndicacionesClick(SelectEvent event) {
		System.out.println("Ingreso a onRowSelectIndicacionesClick");
		String nombre = event.getObject().toString();
		for (LabIndicaciones i : listIndicaciones) {
			if (i.getDescripcion().equals(nombre)) {
				LabExamenIndicaciones examenIndicaciones = new LabExamenIndicaciones();
				examenIndicaciones.setExamen(labExamen);
				examenIndicaciones.setIndicaciones(i);
				examenIndicaciones.setFechaModificacion(new Date());
				examenIndicaciones.setFechaRegistro(new Date());
				examenIndicaciones.setUsuarioRegistro(sessionMain
						.getUsuarioLogin().getLogin());

				labExamen.getListIndicaciones().add(examenIndicaciones);
				System.out.println("Indicacion : " + i.getDescripcion());
				this.indicacion = "";
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

	public void onRowDeleteDI(LabExamenDetalle labExamenDetalle) {
		if (listExamenDetalles.size() > 0) {
			for (int i = 0; i < listExamenDetalles.size(); i++) {
				LabExamenDetalle auxiliar = listExamenDetalles.get(i);
				if (auxiliar.getDescripcion().equals(
						labExamenDetalle.getDescripcion())) {
					listExamenDetalles.remove(i);
				}
			}
			FacesContext.getCurrentInstance().renderResponse();
			resetearFitrosTabla("form001:formDocumentos:documentos");
		}
	}

	public void onRowEditDI(LabExamenDetalle labExamenDetalle) {
		if (verificarDetalle(labExamenDetalle)) {
			FacesUtil.fatalMessage("Ya se encuentra registrado.");
			listExamenDetalles.remove(labExamenDetalle);
			// deleteDI(identificacionIndividuo);
		}
	}

	private boolean verificarDetalle(LabExamenDetalle labExamenDetalle1) {
		for (LabExamenDetalle labExamenDetalle : listExamenDetalles) {
			if (labExamenDetalle.getDescripcion().equals(
					labExamenDetalle1.getDescripcion())
					|| labExamenDetalle.getParametros().equals(
							labExamenDetalle1.getParametros())) {
				return true;
			}
		}
		return false;
	}

	public void actionMdificarDetalle(LabExamenDetalle labExamenDetalle1) {

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
			newExamenDetalle = new LabExamenDetalle();
			FacesUtil.updateComponent("formDlg001");
			FacesUtil.showDialog("dlgDialog");

		} catch (Exception e) {
			System.err.println("Error en actionNuevoDetalleExamen : "
					+ e.getStackTrace());
		}
	}
	
	public void obtenerCiudad(LabValoresRef valoresRef){
		Ciudad ciudad= ciudadDao.obtenerCiudad(valoresRef.getCiudad().getId());
		valoresRef.setCiudad(ciudad);
	}

	public void actionNuevoSubDetalleExamen(LabExamenDetalle labExamenDetalle) {
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
			for (LabExamenDetalle us : listExamenDetalles) {
				if (us.equals(newExamenDetalle)) {
					us = newExamenDetalle;
				}
			}
		}
		FacesUtil.hideDialog("dlgDialog");
	}

	public void onRowDeleteIndicacion(LabExamenIndicaciones examenIndicaciones) {
		labExamen.getListIndicaciones().remove(examenIndicaciones);
	}

	public void agregarRegistro(LabExamenDetalle detalle) {
		for (LabExamenDetalle us : listExamenDetalles) {
			if (us.equals(detalle)) {
				detalle.getListValoresReferencia().add(new LabValoresRef());
				us = detalle;
				return;
			}
		}
	}

	public void guardarRegistro(LabValoresRef detalle) {
		if (detalle.getParametros().equals(null)||detalle.getParametros().trim().isEmpty()) {
			FacesUtil.infoMessage("VALIDACION",
					"Rellenar Parametros");
			return;
		}
		if (detalle.getValor()==0 && detalle.getExamenDetalle().getTipoValor()== TipoValor.NUMERICO) {
			FacesUtil.infoMessage("VALIDACION",
					"Rellenar Valor");
			return;
		}
		if (detalle.getTextoMultiple().equals("") && detalle.getExamenDetalle().getTipoValor()== TipoValor.MULTIPLE) {
			FacesUtil.infoMessage("VALIDACION",
					"Rellenar Text Multiple");
			return;
		}
//		if (detalle.getTextoMultiple().equals(null) && detalle.getExamenDetalle().getTipoValor()== TipoValor.MULTIPLE) {
//			FacesUtil.infoMessage("VALIDACION",
//					"Rellenar Parametros");
//			return;
//		}
		for (LabExamenDetalle us : listExamenDetalles) {
			for (LabValoresRef labValoresRef : us.getListValoresReferencia()) {
				if (labValoresRef.equals(detalle)) {
					detalle.setEdicion(false);						
					labValoresRef = detalle;
					return;
				}
			}
		}
	}

	public void editarRegistro(LabValoresRef detalle) {
		for (LabExamenDetalle us : listExamenDetalles) {
			for (LabValoresRef labValoresRef : us.getListValoresReferencia()) {
				if (labValoresRef.equals(detalle)) {
					detalle.setEdicion(true);
					labValoresRef = detalle;
					return;
				}
			}
		}
	}

	public void onRowDeleteSub(LabExamenDetalle detalle,
			LabValoresRef labExamenDetalle) {
		detalle.getListValoresReferencia().remove(labExamenDetalle);
		for (LabExamenDetalle us : listExamenDetalles) {
			if (us.equals(detalle)) {
				us = detalle;
				return;
			}
		}
	}

	public void registrarOModificarSubDetalle() {
		if (valoresRef.getId().equals(0)) {// nuevo
			if (valoresRef.getCiudad().equals(0)
					|| valoresRef.getCiudad() == null) {
				FacesUtil.infoMessage("Seleccionar ", "Ciudad");
				return;
			}
			valoresRef.setId(listExamenDetalles.size() * (-1));
			valoresRef.setUsuarioRegistro(sessionMain.getUsuarioLogin()
					.getLogin());
			valoresRef.setFechaRegistro(new Date());
			valoresRef.setCiudad(ciudadDao.obtenerCiudad(valoresRef.getCiudad()
					.getId()));
			valoresRef.setCorrelativo(selectedExamenDetalle
					.getListValoresReferencia().size() + 1);
			selectedExamenDetalle.getListValoresReferencia().add(valoresRef);

			for (LabExamenDetalle us : listExamenDetalles) {
				if (us.equals(selectedExamenDetalle)) {
					us = selectedExamenDetalle;
				}
			}
		} else {
			valoresRef.setFechaModificacion(new Date());
			valoresRef.setCiudad(ciudadDao.obtenerCiudad(valoresRef.getCiudad()
					.getId()));
			valoresRef.setCorrelativo(selectedExamenDetalle
					.getListValoresReferencia().size() + 1);
			selectedExamenDetalle.getListValoresReferencia().add(valoresRef);
			for (LabExamenDetalle us : listExamenDetalles) {
				if (us.equals(selectedExamenDetalle)) {
					us = selectedExamenDetalle;
				}
			}
		}
		FacesUtil.hideDialog("dlgDialogRef");
	}

	public void onRowDeleteDetalle(LabExamenDetalle labExamenDetalle) {
		listExamenDetalles.remove(labExamenDetalle);
	}

	public void onRowDeleteSubDetalle(LabValoresRef labExamenDetalle) {
		selectedExamenDetalle.getListValoresReferencia().remove(
				labExamenDetalle);
		for (LabExamenDetalle us : listExamenDetalles) {
			if (us.equals(selectedExamenDetalle)) {
				us = selectedExamenDetalle;
			}
		}
	}

	public boolean isSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

	public LabExamen getLabExamen() {
		return labExamen;
	}

	public LabExamen getLabExamenSelected() {
		return labExamenSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<LabExamen> getListaLabExamen() {
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

	public void setLabExamen(LabExamen labExamen) {
		this.labExamen = labExamen;
	}

	public void setLabExamenSelected(LabExamen labExamenSelected) {
		this.labExamenSelected = labExamenSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaLabExamen(List<LabExamen> listaLabExamen) {
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
	public LabGrupoExamen getSelectedGrupoExamen() {
		return selectedGrupoExamen;
	}

	/**
	 * @param selectedGrupoExamen
	 *            the selectedGrupoExamen to set
	 */
	public void setSelectedGrupoExamen(LabGrupoExamen selectedGrupoExamen) {
		this.selectedGrupoExamen = selectedGrupoExamen;
	}

	/**
	 * @return the listGrupoExamens
	 */
	public List<LabGrupoExamen> getListGrupoExamens() {
		return listGrupoExamens;
	}

	/**
	 * @param listGrupoExamens
	 *            the listGrupoExamens to set
	 */
	public void setListGrupoExamens(List<LabGrupoExamen> listGrupoExamens) {
		this.listGrupoExamens = listGrupoExamens;
	}

	/**
	 * @return the listExamenDetalles
	 */
	public List<LabExamenDetalle> getListExamenDetalles() {
		return listExamenDetalles;
	}

	/**
	 * @param listExamenDetalles
	 *            the listExamenDetalles to set
	 */
	public void setListExamenDetalles(List<LabExamenDetalle> listExamenDetalles) {
		this.listExamenDetalles = listExamenDetalles;
	}

	/**
	 * @return the newExamenDetalle
	 */
	public LabExamenDetalle getNewExamenDetalle() {
		return newExamenDetalle;
	}

	/**
	 * @param newExamenDetalle
	 *            the newExamenDetalle to set
	 */
	public void setNewExamenDetalle(LabExamenDetalle newExamenDetalle) {
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
	public LabExamenDetalle getSelectedExamenDetalle() {
		return selectedExamenDetalle;
	}

	/**
	 * @param selectedExamenDetalle
	 *            the selectedExamenDetalle to set
	 */
	public void setSelectedExamenDetalle(LabExamenDetalle selectedExamenDetalle) {
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

	/**
	 * @return the listIndicaciones
	 */
	public List<LabIndicaciones> getListIndicaciones() {
		return listIndicaciones;
	}

	/**
	 * @param listIndicaciones
	 *            the listIndicaciones to set
	 */
	public void setListIndicaciones(List<LabIndicaciones> listIndicaciones) {
		this.listIndicaciones = listIndicaciones;
	}

	/**
	 * @return the indicacion
	 */
	public String getIndicacion() {
		return indicacion;
	}

	/**
	 * @param indicacion
	 *            the indicacion to set
	 */
	public void setIndicacion(String indicacion) {
		this.indicacion = indicacion;
	}

}
