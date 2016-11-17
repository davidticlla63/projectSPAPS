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
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import com.teds.spaps.interfaces.dao.ILabEtiquetasDao;
import com.teds.spaps.interfaces.dao.ILabProgramacionOrdenDao;
import com.teds.spaps.interfaces.dao.IPersonalDao;
import com.teds.spaps.model.LabEtiquetas;
import com.teds.spaps.model.LabMuestras;
import com.teds.spaps.model.LabProgramacionOrden;
import com.teds.spaps.model.Personal;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

/**
 * @author ANITA
 *
 */
@ManagedBean(name = "labProgramacionOrdenController")
@ViewScoped
public class LabProgramacionOrdenController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -125210622622704977L;
	/******* DAO **********/
	private @Inject ILabProgramacionOrdenDao labProgramacionOrdenDao;
	private @Inject IPersonalDao personalDao;
	private @Inject SessionMain sessionMain;
	private @Inject  ILabEtiquetasDao etiquetasDao;

	/******* OBJECT **********/
	private LabProgramacionOrden labProgramacionOrden;
	private LabProgramacionOrden labProgramacionOrdenSelected;
	private LabMuestras selectedMuestra;
	private LabMuestras newMuestras;
	private Sucursal sucursal;
	private Personal medico;
	private LabEtiquetas etiquetas;

	/******* LIST **********/
	private List<LabProgramacionOrden> listaLabProgramacionOrden;
	private List<LabEtiquetas> listEtiquetas= new ArrayList<LabEtiquetas>();

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;
	private boolean seleccionado = false;
	private boolean nuevo = false;

	// URL PAGE
	private String currentPage = "/pages/laboratorio/toma_mustras/list.xhtml";

	private Date fecha = new Date();
	
	private Date fechaInicio= new Date();
	
	private Date fechaFin= new Date();

	/**
	 * 
	 */
	public LabProgramacionOrdenController() {
	}

	@PostConstruct
	public void initNew() {
		sucursal = sessionMain.PruebaSucursal();
		System.out.println("Sucursal : " + sucursal.getDescripcion());
		System.out.println("Compañia : "
				+ sucursal.getCompania().getDescripcion());
		List<Personal> listPersonals = new ArrayList<Personal>();
		listPersonals = personalDao.obtenerPorUsuario(sessionMain
				.getUsuarioLogin());
		if (listPersonals.size() == 1) {
			medico = listPersonals.get(0);
		}
		
		defaultValues();
	}

	private void defaultValues() {
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
		seleccionado = false;
		labProgramacionOrden = new LabProgramacionOrden();
		labProgramacionOrdenSelected = new LabProgramacionOrden();
		if (medico!=null) {
//			listaLabProgramacionOrden = labProgramacionOrdenDao.obtenerPorSucursal(
//					"AC", medico, sucursal);
			consultar();
		}
		currentPage = "/pages/laboratorio/toma_mustras/list.xhtml";
	}

	public void consultar() {
//		listaLabProgramacionOrden = labProgramacionOrdenDao.obtenerPorSucursal(
//				"AC", fecha, medico, sucursal);
		listaLabProgramacionOrden = labProgramacionOrdenDao.obtenerPorSucursalYFechas(fechaInicio, fechaFin, medico, sucursal);
	}

	public void registrar() {
		try {
			System.out.println("Ingreso a registrar");

			if (labProgramacionOrden.getObservacion().trim().isEmpty()
					|| labProgramacionOrden.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			}
			labProgramacionOrden.setSucursal(sucursal);
			labProgramacionOrden.setFechaRegistro(new Date());
			labProgramacionOrden.setFechaModificacion(labProgramacionOrden
					.getFechaRegistro());
			labProgramacionOrden.setUsuarioRegistro(sessionMain
					.getUsuarioLogin().getLogin());

			LabProgramacionOrden r = labProgramacionOrdenDao
					.registrar(labProgramacionOrden);
			if (r != null) {
				defaultValues();
			} else {
				defaultValues();
			}
		} catch (Exception e) {
			System.out.println("Error en registro de labProgramacionOrden: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			System.out.println("Ingreso a actualizar");
			if (labProgramacionOrden.getObservacion().trim().isEmpty()
					|| labProgramacionOrden.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				labProgramacionOrden.setSucursal(getSucursal());
				labProgramacionOrden.setFechaModificacion(new Date());
				labProgramacionOrden.setUsuarioRegistro(sessionMain
						.getUsuarioLogin().getLogin());
				LabProgramacionOrden r = labProgramacionOrdenDao
						.modificar(labProgramacionOrden);
				if (r != null) {
					defaultValues();
				} else {
					defaultValues();
				}
			}
		} catch (Exception e) {
			System.out
					.println("Error en modificacion de labProgramacionOrden: "
							+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			if (labProgramacionOrdenDao.eliminar(labProgramacionOrden)) {
				FacesUtil.infoMessage("LabProgramacionOrden Eliminado",
						labProgramacionOrden.toString());
				defaultValues();
			} else {
				FacesUtil.errorMessage("Error al eliminar");
				defaultValues();
			}
		} catch (Exception e) {
			System.out.println("Error en eliminacion de labProgramacionOrden: "
					+ e.getMessage());
		}
	}

	public void completarRegistro() {
		try {
			LabProgramacionOrden r = labProgramacionOrdenDao
					.completarRegistro(labProgramacionOrden);
			if (r != null) {
				defaultValues();
			} else {
				FacesUtil.errorMessage("Error al completarRegistro");
				defaultValues();
			}
		} catch (Exception e) {
			System.out.println("Error en eliminacion de labProgramacionOrden: "
					+ e.getMessage());
		}
	}

	public void onRowSelect(SelectEvent event) {
		labProgramacionOrden = labProgramacionOrdenSelected;
		crear = false;
		seleccionado = true;
		registrar = false;
		currentPage = "/pages/laboratorio/toma_mustras/edit.xhtml";
	}

	public void onRowSelectMuestra(SelectEvent event) {

	}

	public void actionNuevo() {
		System.out.println("Ingreso a actionNuevo...");
		crear = false;
		seleccionado = false;
		registrar = true;
		labProgramacionOrden = new LabProgramacionOrden();
		labProgramacionOrden.setFechaRegistro(new Date());
		currentPage = "/pages/laboratorio/toma_mustras/edit.xhtml";
	}

	public void actionCancelar() {
		crear = true;
		seleccionado = false;
		registrar = false;
		labProgramacionOrden = new LabProgramacionOrden();
		currentPage = "/pages/laboratorio/toma_mustras/list.xhtml";
	}

	public void actionModificarMuetra(LabMuestras labExamenDetalle1) {
		newMuestras = labExamenDetalle1;
		FacesUtil.updateComponent("formDlg001");
		FacesUtil.showDialog("dlgDialog");
		nuevo = false;
	}

	public void actionNuevoMuestra() {
		try {
			System.out.println("Ingreso a actionNuevoMuestra...");
			newMuestras = new LabMuestras();
			FacesUtil.updateComponent("formDlg001");
			FacesUtil.showDialog("dlgDialog");
			nuevo = true;
		} catch (Exception e) {
			System.err.println("Error en actionNuevoDetalleExamen : "
					+ e.getStackTrace());
		}
	}

	public void registrarOModificarMuestra() {
		if (newMuestras.getId().equals(0) && nuevo == true) {// nuevo
			newMuestras.setId(labProgramacionOrden.getListMuestras().size()
					* (-1));
			newMuestras.setUsuarioRegistro(sessionMain.getUsuarioLogin()
					.getLogin());
			newMuestras.setFechaRegistro(new Date());
			newMuestras.setProgramacionOrden(labProgramacionOrden);
			labProgramacionOrden.getListMuestras().add(newMuestras);
		} else {
			for (LabMuestras us : labProgramacionOrden.getListMuestras()) {
				if (us.equals(newMuestras)) {
					us = newMuestras;
				}
			}
		}
		FacesUtil.hideDialog("dlgDialog");
	}

	// ONCOMPLETETEXT GRUPO EXAMEN
		public List<LabEtiquetas> completeEtiqueta(String query) {
			String upperQuery = query.toUpperCase();
			listEtiquetas = etiquetasDao.obtenerPorCompania(upperQuery,
					sucursal.getCompania());
			return listEtiquetas;
		}

		public void onRowSelectEtiquetaClick(SelectEvent event) {
			System.out.println("Ingreso a onRowSelectPacienteClick");
			String nombre = event.getObject().toString();
			for (LabEtiquetas i : listEtiquetas) {
				if (i.getDescripcion().equals(nombre)) {
					newMuestras.setEtiquetas(i);
					System.out.println("Paciente : "
							+newMuestras.getEtiquetas().getDescripcion());
					return;
				}
			}
		}
	
	public void onRowDeleteMuestra(LabMuestras labExamenDetalle) {
		labProgramacionOrden.getListMuestras().remove(labExamenDetalle);
	}

	public boolean isSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

	public LabProgramacionOrden getLabProgramacionOrden() {
		return labProgramacionOrden;
	}

	public LabProgramacionOrden getLabProgramacionOrdenSelected() {
		return labProgramacionOrdenSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<LabProgramacionOrden> getListaLabProgramacionOrden() {
		return listaLabProgramacionOrden;
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

	public void setLabProgramacionOrden(
			LabProgramacionOrden labProgramacionOrden) {
		this.labProgramacionOrden = labProgramacionOrden;
	}

	public void setLabProgramacionOrdenSelected(
			LabProgramacionOrden labProgramacionOrdenSelected) {
		this.labProgramacionOrdenSelected = labProgramacionOrdenSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaLabProgramacionOrden(
			List<LabProgramacionOrden> listaLabProgramacionOrden) {
		this.listaLabProgramacionOrden = listaLabProgramacionOrden;
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
	 * @return the medico
	 */
	public Personal getMedico() {
		return medico;
	}

	/**
	 * @param medico
	 *            the medico to set
	 */
	public void setMedico(Personal medico) {
		this.medico = medico;
	}

	/**
	 * @return the selectedMuestra
	 */
	public LabMuestras getSelectedMuestra() {
		return selectedMuestra;
	}

	/**
	 * @param selectedMuestra
	 *            the selectedMuestra to set
	 */
	public void setSelectedMuestra(LabMuestras selectedMuestra) {
		this.selectedMuestra = selectedMuestra;
	}

	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha
	 *            the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the newMuestras
	 */
	public LabMuestras getNewMuestras() {
		return newMuestras;
	}

	/**
	 * @param newMuestras
	 *            the newMuestras to set
	 */
	public void setNewMuestras(LabMuestras newMuestras) {
		this.newMuestras = newMuestras;
	}

	/**
	 * @return the nuevo
	 */
	public boolean isNuevo() {
		return nuevo;
	}

	/**
	 * @param nuevo
	 *            the nuevo to set
	 */
	public void setNuevo(boolean nuevo) {
		this.nuevo = nuevo;
	}

	/**
	 * @return the listEtiquetas
	 */
	public List<LabEtiquetas> getListEtiquetas() {
		return listEtiquetas;
	}

	/**
	 * @param listEtiquetas the listEtiquetas to set
	 */
	public void setListEtiquetas(List<LabEtiquetas> listEtiquetas) {
		this.listEtiquetas = listEtiquetas;
	}

	/**
	 * @return the etiquetas
	 */
	public LabEtiquetas getEtiquetas() {
		return etiquetas;
	}

	/**
	 * @param etiquetas the etiquetas to set
	 */
	public void setEtiquetas(LabEtiquetas etiquetas) {
		this.etiquetas = etiquetas;
	}

	/**
	 * @return the fechaInicio
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * @param fechaInicio the fechaInicio to set
	 */
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * @return the fechaFinal
	 */
	public Date getFechaFin() {
		return fechaFin;
	}

	/**
	 * @param fechaFinal the fechaFinal to set
	 */
	public void setFechaFin(Date fechaFinal) {
		this.fechaFin = fechaFinal;
	}

}
