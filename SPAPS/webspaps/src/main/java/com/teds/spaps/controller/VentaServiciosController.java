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

import com.teds.spaps.interfaces.dao.IEmpresaDao;
import com.teds.spaps.interfaces.dao.ISeguroDao;
import com.teds.spaps.interfaces.dao.IVentaGrupoServiciosDao;
import com.teds.spaps.interfaces.dao.IVentaServiciosDao;
import com.teds.spaps.interfaces.dao.IVentaTipoServiciosDao;
import com.teds.spaps.model.Empresa;
import com.teds.spaps.model.Seguro;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.VentaGrupoServicios;
import com.teds.spaps.model.VentaServicios;
import com.teds.spaps.model.VentaServiciosEmpresa;
import com.teds.spaps.model.VentaServiciosSeguros;
import com.teds.spaps.model.VentaTipoServicios;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

/**
 * @author ANITA
 *
 */
@ManagedBean(name = "ventaServiciosController")
@ViewScoped
public class VentaServiciosController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6693305123755133558L;
	/******* DAO **********/
	private @Inject IVentaServiciosDao ventaServiciosDao;
	private @Inject SessionMain sesionMain;
	private @Inject IVentaTipoServiciosDao tipoExamenDao;
	private @Inject IVentaGrupoServiciosDao grupoExamenDao;
	private @Inject ISeguroDao seguroDao;
	private @Inject IEmpresaDao empresaDao;

	/******* OBJECT **********/
	private VentaServicios ventaServicios;
	private VentaServicios ventaServiciosSelected;
	private Sucursal sucursal;
	private VentaTipoServicios selectedTipoServicios;
	private VentaGrupoServicios selectedGrupoServicios;
	private VentaServiciosEmpresa serviciosEmpresa;
	private VentaServiciosSeguros serviciosSeguros;

	/******* LIST **********/
	private List<VentaServicios> listaVentaServicios;
	private List<VentaGrupoServicios> listGrupoServicios = new ArrayList<VentaGrupoServicios>();
	private List<VentaTipoServicios> listTipoServicios = new ArrayList<VentaTipoServicios>();

	private List<VentaServiciosEmpresa> listaServiciosEmpresas = new ArrayList<VentaServiciosEmpresa>();
	private List<VentaServiciosSeguros> listServiciosSeguros = new ArrayList<VentaServiciosSeguros>();

	private List<Seguro> listSeguros = new ArrayList<Seguro>();
	private List<Empresa> listEmpresas = new ArrayList<Empresa>();

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;
	private boolean seleccionado = false;

	// URL PAGE
	private String currentPage = "/pages/ventas/servicios/list.xhtml";

	/**
	 * 
	 */
	public VentaServiciosController() {
	}

	@PostConstruct
	public void initNew() {
		sucursal = sesionMain.PruebaSucursal();
		System.out.println("Sucursal : " + sucursal.getDescripcion());
		System.out.println("Compañia : "
				+ sucursal.getCompania().getDescripcion());
		serviciosSeguros = new VentaServiciosSeguros();
		serviciosEmpresa = new VentaServiciosEmpresa();
		defaultValues();
	}

	private void defaultValues() {
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
		seleccionado = false;
		selectedGrupoServicios = new VentaGrupoServicios();
		selectedTipoServicios = new VentaTipoServicios();
		ventaServicios = new VentaServicios();
		ventaServiciosSelected = new VentaServicios();
		listaVentaServicios = ventaServiciosDao.obtenerPorCompania(sucursal
				.getCompania());
		listEmpresas = empresaDao.obtenerPorCompania(sucursal.getCompania());
		listSeguros = seguroDao.obtenerPorCompania(sucursal.getCompania());
		currentPage = "/pages/ventas/servicios/list.xhtml";
	}

	public void registrar() {
		try {
			System.out.println("Ingreso a registrar");

			if (selectedGrupoServicios.getId().intValue() == 0
					|| selectedGrupoServicios == null || getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION", "Seleccione Grupo Examen");
				return;
			}
			if (selectedTipoServicios.getId().intValue() == 0
					|| selectedTipoServicios == null || getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION", "Seleccione Tipo Examen");
				return;
			}
			if (ventaServicios.getDescripcion().trim().isEmpty()
					|| ventaServicios.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			}
			ventaServicios.setTipoServicios(selectedTipoServicios);
			ventaServicios.setGrupoServicios(selectedGrupoServicios);
			ventaServicios.setCompania(sucursal.getCompania());
			ventaServicios.setSucursal(sucursal);
			ventaServicios.setFechaRegistro(new Date());
			ventaServicios.setFechaModificacion(ventaServicios
					.getFechaRegistro());
			ventaServicios.setUsuarioRegistro(sesionMain.getUsuarioLogin()
					.getLogin());

			VentaServicios r = ventaServiciosDao.registrar(ventaServicios,
					listaServiciosEmpresas, listServiciosSeguros);
			if (r != null) {
				defaultValues();
			} else {
				defaultValues();
			}
		} catch (Exception e) {
			System.out.println("Error en registro de ventaServicios: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			System.out.println("Ingreso a actualizar");
			if (ventaServicios.getDescripcion().trim().isEmpty()
					|| ventaServicios.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {

				ventaServicios.setTipoServicios(selectedTipoServicios);
				ventaServicios.setGrupoServicios(selectedGrupoServicios);
				ventaServicios.setCompania(getSucursal().getCompania());
				ventaServicios.setSucursal(getSucursal());
				ventaServicios.setFechaModificacion(new Date());
				ventaServicios.setUsuarioRegistro(sesionMain.getUsuarioLogin()
						.getLogin());
				VentaServicios r = ventaServiciosDao.modificar(ventaServicios,
						listaServiciosEmpresas, listServiciosSeguros);
				if (r != null) {
					defaultValues();
				} else {
					defaultValues();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de ventaServicios: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			if (ventaServiciosDao.eliminar(ventaServicios)) {
				FacesUtil.infoMessage("VentaServicios Eliminado",
						ventaServicios.toString());
				defaultValues();
			} else {
				FacesUtil.errorMessage("Error al eliminar");
				defaultValues();
			}
		} catch (Exception e) {
			System.out.println("Error en eliminacion de ventaServicios: "
					+ e.getMessage());
		}
	}

	public void onRowSelect(SelectEvent event) {
		ventaServicios = ventaServiciosSelected;
		crear = false;
		seleccionado = true;
		registrar = false;
		selectedGrupoServicios = ventaServicios.getGrupoServicios();
		selectedTipoServicios = ventaServicios.getTipoServicios();
		listaServiciosEmpresas = ventaServicios.getListServiciosEmpresas();
		listServiciosSeguros = ventaServicios.getListServiciosSeguros();
		// listExamenDetalles =
		// serviciosDetalleDao.obtenerPorExamen(ventaServicios);
		currentPage = "/pages/ventas/servicios/edit.xhtml";
	}

	public void actionNuevo() {
		System.out.println("Ingreso a actionNuevo...");
		crear = false;
		seleccionado = false;
		registrar = true;
		ventaServicios = new VentaServicios();
		ventaServicios.setFechaRegistro(new Date());
		currentPage = "/pages/ventas/servicios/edit.xhtml";
	}

	public void actionCancelar() {
		crear = true;
		seleccionado = false;
		registrar = false;
		ventaServicios = new VentaServicios();
		currentPage = "/pages/ventas/servicios/list.xhtml";
	}
	
	// ONCOMPLETETEXT TIPO EXAMEN
	public List<VentaTipoServicios> completeTipoServicios(String query) {
		String upperQuery = query.toUpperCase();
		// if (upperQuery.trim().length() == 0) {
		// FacesUtil.infoMessage("VALIDACION",
		// "Escriba el Nombre del Paciente");
		// return null;
		// }
		listTipoServicios = tipoExamenDao.obtenerPorCompania(upperQuery,
				sucursal.getCompania());
		return listTipoServicios;
	}

	public void onRowSelectTipoServiciosClick(SelectEvent event) {
		System.out.println("Ingreso a onRowSelectPacienteClick");
		String nombre = event.getObject().toString();
		for (VentaTipoServicios i : listTipoServicios) {
			if (i.getDescripcion().equals(nombre)) {
				selectedTipoServicios = i;
				System.out.println("Tipo Examen : "
						+ selectedTipoServicios.getDescripcion());
				return;
			}
		}
	}

	// ONCOMPLETETEXT GRUPO EXAMEN
	public List<VentaGrupoServicios> completeGrupoServicios(String query) {
		String upperQuery = query.toUpperCase();
		listGrupoServicios = grupoExamenDao.obtenerPorCompania(upperQuery,
				sucursal.getCompania());
		return listGrupoServicios;
	}

	public void onRowSelectGrupoServiciosClick(SelectEvent event) {
		System.out.println("Ingreso a onRowSelectPacienteClick");
		String nombre = event.getObject().toString();
		for (VentaGrupoServicios i : listGrupoServicios) {
			if (i.getDescripcion().equals(nombre)) {
				selectedGrupoServicios = i;
				System.out.println("Paciente : "
						+ selectedGrupoServicios.getDescripcion());
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

	// DIALOGOS

	public void actionNuevoServicioSeguros() {
		try {
			System.out.println("Ingreso a actionNuevoServicioSeguros...");
			serviciosSeguros = new VentaServiciosSeguros();
			FacesUtil.updateComponent("formDlg001");
			FacesUtil.showDialog("dlgDialog");

		} catch (Exception e) {
			System.err.println("Error en actionNuevoServicioSeguros : "
					+ e.getStackTrace());
		}
	}

	public void actionMdificarServicioSeguro(
			VentaServiciosSeguros labExamenDetalle1) {

		serviciosSeguros = labExamenDetalle1;
		FacesUtil.updateComponent("formDlg001");
		FacesUtil.showDialog("dlgDialog");
	}

	public void registrarOModificarServicioSeguro() {
		if (serviciosSeguros.getId().equals(0)) {// nuevo
			serviciosSeguros.setSeguro(seguroDao.obtenerSeguro(new Integer(
					serviciosSeguros.getSeguro().getId())));
			serviciosSeguros.setId(listServiciosSeguros.size() * (-1));
			serviciosSeguros.setUsuarioRegistro(sesionMain.getUsuarioLogin()
					.getLogin());
			serviciosSeguros.setFechaRegistro(new Date());
			serviciosSeguros.setCompania(sucursal.getCompania());
			serviciosSeguros.setSucursal(sucursal);
			listServiciosSeguros.add(serviciosSeguros);
		} else {
			for (VentaServiciosSeguros us : listServiciosSeguros) {
				if (us.equals(serviciosSeguros)) {
					us = serviciosSeguros;
				}
			}
		}
		FacesUtil.hideDialog("dlgDialog");
	}

	public void onRowDeleteServicioSeguro(VentaServiciosSeguros labExamenDetalle) {
		listServiciosSeguros.remove(labExamenDetalle);
	}

	public void actionNuevoServicioEmpresa() {
		try {
			System.out.println("Ingreso a actionNuevoServicioEmpresa...");
			serviciosEmpresa = new VentaServiciosEmpresa();
			FacesUtil.updateComponent("formDlg001");
			FacesUtil.showDialog("dlgDialogEmpresa");

		} catch (Exception e) {
			System.err.println("Error en actionNuevoServicioEmpresa : "
					+ e.getStackTrace());
		}
	}

	public void actionMdificarServicioEmpresa(
			VentaServiciosEmpresa labExamenDetalle1) {

		serviciosEmpresa = labExamenDetalle1;
		FacesUtil.updateComponent("formDlg002");
		FacesUtil.showDialog("dlgDialogEmpresa");
	}

	public void registrarOModificarServicioEmpresa() {
		if (serviciosEmpresa.getId().equals(0)) {// nuevo
			serviciosEmpresa.setEmpresa(empresaDao.obtenerEmpresa(new Integer(
					serviciosEmpresa.getEmpresa().getId())));
			serviciosEmpresa.setId(listaServiciosEmpresas.size() * (-1));
			serviciosEmpresa.setUsuarioRegistro(sesionMain.getUsuarioLogin()
					.getLogin());
			serviciosEmpresa.setFechaRegistro(new Date());
			serviciosEmpresa.setCompania(sucursal.getCompania());
			serviciosEmpresa.setSucursal(sucursal);
			listaServiciosEmpresas.add(serviciosEmpresa);
		} else {
			for (VentaServiciosEmpresa us : listaServiciosEmpresas) {
				if (us.equals(serviciosEmpresa)) {
					us = serviciosEmpresa;
				}
			}
		}
		FacesUtil.hideDialog("dlgDialogEmpresa");
	}

	public void onRowDeleteServicioEmpresa(
			VentaServiciosEmpresa labExamenDetalle) {
		listaServiciosEmpresas.remove(labExamenDetalle);
	}

	public boolean isSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

	public VentaServicios getVentaServicios() {
		return ventaServicios;
	}

	public VentaServicios getVentaServiciosSelected() {
		return ventaServiciosSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<VentaServicios> getListaVentaServicios() {
		return listaVentaServicios;
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

	public void setVentaServicios(VentaServicios ventaServicios) {
		this.ventaServicios = ventaServicios;
	}

	public void setVentaServiciosSelected(VentaServicios ventaServiciosSelected) {
		this.ventaServiciosSelected = ventaServiciosSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaVentaServicios(List<VentaServicios> listaVentaServicios) {
		this.listaVentaServicios = listaVentaServicios;
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
	 * @return the selectedTipoServicios
	 */
	public VentaTipoServicios getSelectedTipoServicios() {
		return selectedTipoServicios;
	}

	/**
	 * @param selectedTipoServicios
	 *            the selectedTipoServicios to set
	 */
	public void setSelectedTipoServicios(
			VentaTipoServicios selectedTipoServicios) {
		this.selectedTipoServicios = selectedTipoServicios;
	}

	/**
	 * @return the selectedGrupoServicios
	 */
	public VentaGrupoServicios getSelectedGrupoServicios() {
		return selectedGrupoServicios;
	}

	/**
	 * @param selectedGrupoServicios
	 *            the selectedGrupoServicios to set
	 */
	public void setSelectedGrupoServicios(
			VentaGrupoServicios selectedGrupoServicios) {
		this.selectedGrupoServicios = selectedGrupoServicios;
	}

	/**
	 * @return the listGrupoServicios
	 */
	public List<VentaGrupoServicios> getListGrupoServicios() {
		return listGrupoServicios;
	}

	/**
	 * @param listGrupoServicios
	 *            the listGrupoServicios to set
	 */
	public void setListGrupoServicios(
			List<VentaGrupoServicios> listGrupoServicios) {
		this.listGrupoServicios = listGrupoServicios;
	}

	/**
	 * @return the listTipoServicios
	 */
	public List<VentaTipoServicios> getListTipoServicios() {
		return listTipoServicios;
	}

	/**
	 * @param listTipoServicios
	 *            the listTipoServicios to set
	 */
	public void setListTipoServicios(List<VentaTipoServicios> listTipoServicios) {
		this.listTipoServicios = listTipoServicios;
	}

	/**
	 * @return the listaServiciosEmpresas
	 */
	public List<VentaServiciosEmpresa> getListaServiciosEmpresas() {
		return listaServiciosEmpresas;
	}

	/**
	 * @param listaServiciosEmpresas
	 *            the listaServiciosEmpresas to set
	 */
	public void setListaServiciosEmpresas(
			List<VentaServiciosEmpresa> listaServiciosEmpresas) {
		this.listaServiciosEmpresas = listaServiciosEmpresas;
	}

	/**
	 * @return the listServiciosSeguros
	 */
	public List<VentaServiciosSeguros> getListServiciosSeguros() {
		return listServiciosSeguros;
	}

	/**
	 * @param listServiciosSeguros
	 *            the listServiciosSeguros to set
	 */
	public void setListServiciosSeguros(
			List<VentaServiciosSeguros> listServiciosSeguros) {
		this.listServiciosSeguros = listServiciosSeguros;
	}

	/**
	 * @return the serviciosEmpresa
	 */
	public VentaServiciosEmpresa getServiciosEmpresa() {
		return serviciosEmpresa;
	}

	/**
	 * @param serviciosEmpresa
	 *            the serviciosEmpresa to set
	 */
	public void setServiciosEmpresa(VentaServiciosEmpresa serviciosEmpresa) {
		this.serviciosEmpresa = serviciosEmpresa;
	}

	/**
	 * @return the serviciosSeguros
	 */
	public VentaServiciosSeguros getServiciosSeguros() {
		return serviciosSeguros;
	}

	/**
	 * @param serviciosSeguros
	 *            the serviciosSeguros to set
	 */
	public void setServiciosSeguros(VentaServiciosSeguros serviciosSeguros) {
		this.serviciosSeguros = serviciosSeguros;
	}

	/**
	 * @return the listSeguros
	 */
	public List<Seguro> getListSeguros() {
		return listSeguros;
	}

	/**
	 * @param listSeguros
	 *            the listSeguros to set
	 */
	public void setListSeguros(List<Seguro> listSeguros) {
		this.listSeguros = listSeguros;
	}

	/**
	 * @return the listEmpresas
	 */
	public List<Empresa> getListEmpresas() {
		return listEmpresas;
	}

	/**
	 * @param listEmpresas
	 *            the listEmpresas to set
	 */
	public void setListEmpresas(List<Empresa> listEmpresas) {
		this.listEmpresas = listEmpresas;
	}

}
