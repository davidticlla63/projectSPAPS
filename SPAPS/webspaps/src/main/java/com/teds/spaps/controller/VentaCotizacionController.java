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

import com.teds.spaps.interfaces.dao.IPacienteDao;
import com.teds.spaps.interfaces.dao.IParamSistemaIndicesDao;
import com.teds.spaps.interfaces.dao.IVentaCotizacionDao;
import com.teds.spaps.interfaces.dao.IVentaServiciosDao;
import com.teds.spaps.model.Paciente;
import com.teds.spaps.model.ParamSistemaIndices;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.VentaCotizacion;
import com.teds.spaps.model.VentaCotizacionDetalle;
import com.teds.spaps.model.VentaServicios;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;
import com.teds.spaps.util.Time;

/**
 * @author david
 *
 */
@ManagedBean(name = "ventaCotizacionController")
@ViewScoped
public class VentaCotizacionController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3482724224924336636L;
	/******* DAO **********/
	private @Inject IVentaCotizacionDao ventaCotizacionDao;
	private @Inject SessionMain sesionMain;
	private @Inject IVentaServiciosDao ventaServiciosDao;
	private @Inject IPacienteDao pacienteDao;
	private @Inject IParamSistemaIndicesDao sistemaIndicesDao;

	/******* OBJECT **********/
	private VentaCotizacion ventaCotizacion;
	private VentaCotizacion ventaCotizacionSelected;
	private Sucursal sucursal;
	private VentaServicios ventaServiciosSelected;
	private Paciente selectedPaciente;
	private VentaCotizacionDetalle cotizacionDetalleSelected;
	private VentaCotizacionDetalle cotizacionDetalle;

	/******* LIST **********/
	private List<VentaCotizacion> listaVentaCotizacion;
	private List<VentaCotizacionDetalle> listCotizacionDetalle = new ArrayList<VentaCotizacionDetalle>();
	private List<VentaServicios> listVentaServicios = new ArrayList<VentaServicios>();
	private List<Paciente> listPacientes = new ArrayList<Paciente>();

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;
	private boolean seleccionado = false;

	// URL PAGE
	private String currentPage = "/pages/ventas/cotizacion/list.xhtml";

	/**
	 * 
	 */
	public VentaCotizacionController() {
	}

	@PostConstruct
	public void initNew() {
		sucursal = sesionMain.PruebaSucursal();
		System.out.println("Sucursal : " + sucursal.getDescripcion());
		System.out.println("Compañia : "
				+ sucursal.getCompania().getDescripcion());
		selectedPaciente = new Paciente();
		ventaServiciosSelected = new VentaServicios();
		cotizacionDetalle = new VentaCotizacionDetalle();
		defaultValues();
	}

	private void defaultValues() {
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
		seleccionado = false;
		ventaCotizacion = new VentaCotizacion();
		listaVentaCotizacion = ventaCotizacionDao.obtenerPorCompania(sucursal
				.getCompania());
		currentPage = "/pages/ventas/cotizacion/list.xhtml";
	}

	public void registrar() {
		try {
			System.out.println("Ingreso a registrar");

			if (listCotizacionDetalle.size() == 0) {
				FacesUtil.infoMessage("VALIDACION", "No tiene ningun Detalle");
				return;
			}

			if (ventaCotizacion.getObservacion().trim().isEmpty()
					|| ventaCotizacion.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			}
			if (selectedPaciente.getId() == 0) {
				ventaCotizacion.setPaciente(null);
			} else {
				ventaCotizacion.setPaciente(selectedPaciente);
			}
			ventaCotizacion.setCompania(sucursal.getCompania());
			ventaCotizacion.setSucursal(sucursal);
			ventaCotizacion.setFechaRegistro(new Date());
			ventaCotizacion.setFechaModificacion(ventaCotizacion
					.getFechaRegistro());
			ventaCotizacion.setUsuarioRegistro(sesionMain.getUsuarioLogin()
					.getLogin());
			VentaCotizacion r = ventaCotizacionDao.registrar(ventaCotizacion,
					listCotizacionDetalle);
			if (r != null) {
				defaultValues();
			} else {
				defaultValues();
			}
		} catch (Exception e) {
			System.out.println("Error en registro de ventaCotizacion: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			System.out.println("Ingreso a actualizar");
			if (listCotizacionDetalle.size() == 0) {
				FacesUtil.infoMessage("VALIDACION", "No tiene ningun Detalle");
				return;
			}
			if (ventaCotizacion.getObservacion().trim().isEmpty()
					|| ventaCotizacion.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				if (selectedPaciente.getId() == 0) {
					ventaCotizacion.setPaciente(null);
				} else {
					ventaCotizacion.setPaciente(selectedPaciente);
				}
				ventaCotizacion.setCompania(getSucursal().getCompania());
				ventaCotizacion.setSucursal(getSucursal());
				ventaCotizacion.setFechaModificacion(new Date());
				ventaCotizacion.setUsuarioRegistro(sesionMain.getUsuarioLogin()
						.getLogin());
				VentaCotizacion r = ventaCotizacionDao.registrar(
						ventaCotizacion, listCotizacionDetalle);
				if (r != null) {
					defaultValues();
				} else {
					defaultValues();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de ventaCotizacion: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			if (ventaCotizacionDao.eliminar(ventaCotizacion)) {
				FacesUtil.infoMessage("VentaCotizacion Eliminado",
						ventaCotizacion.toString());
				defaultValues();
			} else {
				FacesUtil.errorMessage("Error al eliminar");
				defaultValues();
			}
		} catch (Exception e) {
			System.out.println("Error en eliminacion de ventaCotizacion: "
					+ e.getMessage());
		}
	}

	public void onRowSelect(SelectEvent event) {
		ventaCotizacion = ventaCotizacionSelected;
		crear = false;
		seleccionado = true;
		registrar = false;
		selectedPaciente = ventaCotizacion.getPaciente();
		listCotizacionDetalle = ventaCotizacion.getListCotizacionDetalles();
		currentPage = "/pages/ventas/cotizacion/edit.xhtml";
	}

	public void actionNuevo() {
		System.out.println("Ingreso a actionNuevo...");
		crear = false;
		seleccionado = false;
		registrar = true;
		selectedPaciente = new Paciente();
		ventaCotizacion = new VentaCotizacion();
		ParamSistemaIndices sistemaIndices = sistemaIndicesDao
				.obtenerPorCompania("CO", sucursal.getCompania());
		ventaCotizacion.setCodigo(sistemaIndices.getPrefijo()
				+ Time.rellendarCeros(sistemaIndices.getNumeroDigitos(),
						sistemaIndices.getCorrelativo()));
		ventaCotizacion.setFechaRegistro(new Date());
		currentPage = "/pages/ventas/cotizacion/edit.xhtml";
	}

	public void actionCancelar() {
		crear = true;
		seleccionado = false;
		registrar = false;
		ventaCotizacion = new VentaCotizacion();
		currentPage = "/pages/ventas/cotizacion/list.xhtml";
	}

	// ONCOMPLETETEXT TIPO EXAMEN
	public List<VentaServicios> completeVentaServicios(String query) {
		String upperQuery = query.toUpperCase();
		listVentaServicios = ventaServiciosDao.obtenerPorCompania(upperQuery,
				sucursal.getCompania());
		return listVentaServicios;
	}

	public void onRowSelectVentaServiciosClick(SelectEvent event) {
		System.out.println("Ingreso a onRowSelectPacienteClick");
		String nombre = event.getObject().toString();
		for (VentaServicios i : listVentaServicios) {
			if (i.getDescripcion().equals(nombre)) {
				ventaServiciosSelected = i;
				System.out.println("Tipo Servicio : "
						+ ventaServiciosSelected.getDescripcion());
				
				cotizacionDetalle.setPrecioUnitario(ventaServiciosSelected
						.getPrecioVenta());
				cotizacionDetalle.setCantidad(1);
				return;
			}
		}
	}

	// ONCOMPLETETEXT PACIENTE
	public List<Paciente> completePaciente(String query) {
		String upperQuery = query.toUpperCase();
		String[] protocolo = upperQuery.split(" ");
		if (protocolo.length > 0) {
			if (protocolo.length == 1) {
				listPacientes = pacienteDao.obtenerIndividuosPorNombreCompania(
						protocolo[0].trim(), "", "", sucursal.getCompania());
			}
			if (protocolo.length == 2) {
				listPacientes = pacienteDao.obtenerIndividuosPorNombreCompania(
						protocolo[0].trim(), protocolo[1].trim(), "",
						sucursal.getCompania());
			}
			if (protocolo.length == 3) {
				listPacientes = pacienteDao.obtenerIndividuosPorNombreCompania(
						protocolo[0].trim(), protocolo[1].trim(),
						protocolo[2].trim(), sucursal.getCompania());
			}
		}
		System.out.println("paciente : " + listPacientes.size());

		return listPacientes;
	}

	public void onRowSelectPacienteClick(SelectEvent event) {
		String nombre = event.getObject().toString();
		System.out.println("Ingreso a onRowSelectPacienteClick : " + nombre);
		String[] protocolo = nombre.split(" ");
		for (Paciente i : listPacientes) {
			if (i.getNombre().equals(protocolo[0])
					&& i.getApellidoPaterno().equals(protocolo[1])
					&& i.getApellidoMaterno().equals(protocolo[1])) {
				selectedPaciente = i;

				System.out.println(selectedPaciente.getNombre() + " "
						+ selectedPaciente.getApellidoPaterno() + " "
						+ selectedPaciente.getApellidoMaterno());

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

	public void onRowDeleteDetalle(VentaCotizacionDetalle labExamenDetalle) {
		listCotizacionDetalle.remove(labExamenDetalle);
	}

	public void agregarDetalle() {
		System.out.println("Ingreso a agregarDetalle");
		if (cotizacionDetalle.getPrecioSubTotal() > 0
			) {
			cotizacionDetalle.setFechaRegistro(new Date());
			cotizacionDetalle.setUsuarioRegistro(sesionMain.getUsuarioLogin()
					.getLogin());
			listCotizacionDetalle.add(cotizacionDetalle);
			cotizacionDetalle = new VentaCotizacionDetalle();
			ventaServiciosSelected = new VentaServicios();
		}
	}

	public boolean isSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

	public VentaCotizacion getVentaCotizacion() {
		return ventaCotizacion;
	}

	public VentaCotizacion getVentaCotizacionSelected() {
		return ventaCotizacionSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<VentaCotizacion> getListaVentaCotizacion() {
		return listaVentaCotizacion;
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

	public void setVentaCotizacion(VentaCotizacion ventaCotizacion) {
		this.ventaCotizacion = ventaCotizacion;
	}

	public void setVentaCotizacionSelected(
			VentaCotizacion ventaCotizacionSelected) {
		this.ventaCotizacionSelected = ventaCotizacionSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaVentaCotizacion(
			List<VentaCotizacion> listaVentaCotizacion) {
		this.listaVentaCotizacion = listaVentaCotizacion;
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
	 * @return the listCotizacionDetalle
	 */
	public List<VentaCotizacionDetalle> getListCotizacionDetalle() {
		return listCotizacionDetalle;
	}

	/**
	 * @param listCotizacionDetalle
	 *            the listCotizacionDetalle to set
	 */
	public void setListCotizacionDetalle(
			List<VentaCotizacionDetalle> listCotizacionDetalle) {
		this.listCotizacionDetalle = listCotizacionDetalle;
	}

	/**
	 * @return the listVentaServicios
	 */
	public List<VentaServicios> getListVentaServicios() {
		return listVentaServicios;
	}

	/**
	 * @param listVentaServicios
	 *            the listVentaServicios to set
	 */
	public void setListVentaServicios(List<VentaServicios> listVentaServicios) {
		this.listVentaServicios = listVentaServicios;
	}

	/**
	 * @return the ventaServiciosSelected
	 */
	public VentaServicios getVentaServiciosSelected() {
		return ventaServiciosSelected;
	}

	/**
	 * @param ventaServiciosSelected
	 *            the ventaServiciosSelected to set
	 */
	public void setVentaServiciosSelected(VentaServicios ventaServiciosSelected) {
		this.ventaServiciosSelected = ventaServiciosSelected;
	}

	/**
	 * @return the selectedPaciente
	 */
	public Paciente getSelectedPaciente() {
		return selectedPaciente;
	}

	/**
	 * @param selectedPaciente
	 *            the selectedPaciente to set
	 */
	public void setSelectedPaciente(Paciente selectedPaciente) {
		this.selectedPaciente = selectedPaciente;
	}

	/**
	 * @return the listPacientes
	 */
	public List<Paciente> getListPacientes() {
		return listPacientes;
	}

	/**
	 * @param listPacientes
	 *            the listPacientes to set
	 */
	public void setListPacientes(List<Paciente> listPacientes) {
		this.listPacientes = listPacientes;
	}

	/**
	 * @return the cotizacionDetalleSelected
	 */
	public VentaCotizacionDetalle getCotizacionDetalleSelected() {
		return cotizacionDetalleSelected;
	}

	/**
	 * @param cotizacionDetalleSelected
	 *            the cotizacionDetalleSelected to set
	 */
	public void setCotizacionDetalleSelected(
			VentaCotizacionDetalle cotizacionDetalleSelected) {
		this.cotizacionDetalleSelected = cotizacionDetalleSelected;
	}

	/**
	 * @return the cotizacionDetalle
	 */
	public VentaCotizacionDetalle getCotizacionDetalle() {
		return cotizacionDetalle;
	}

	/**
	 * @param cotizacionDetalle
	 *            the cotizacionDetalle to set
	 */
	public void setCotizacionDetalle(VentaCotizacionDetalle cotizacionDetalle) {
		this.cotizacionDetalle = cotizacionDetalle;
	}

}
