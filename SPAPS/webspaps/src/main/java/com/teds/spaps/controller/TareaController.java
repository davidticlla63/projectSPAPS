/**
 * @author ANITA
 */
package com.teds.spaps.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.SelectEvent;

import com.teds.spaps.interfaces.dao.IIdentificacionIndividuoDao;
import com.teds.spaps.interfaces.dao.INombreTareaDao;
import com.teds.spaps.interfaces.dao.IPacienteDao;
import com.teds.spaps.interfaces.dao.IPersonalDao;
import com.teds.spaps.interfaces.dao.ITareaDao;
import com.teds.spaps.interfaces.dao.ITipoTareaDao;
import com.teds.spaps.model.IdentificacionPaciente;
import com.teds.spaps.model.NombreTarea;
import com.teds.spaps.model.Paciente;
import com.teds.spaps.model.Personal;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.Tarea;
import com.teds.spaps.model.TipoTarea;
import com.teds.spaps.model.Usuario;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

/**
 * @author ANITA
 *
 */
@ManagedBean(name = "tareaController")
@ViewScoped
public class TareaController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2787255219892909409L;
	/******* DAO **********/
	private @Inject ITareaDao tareaDao;
	private @Inject SessionMain sessionMain;
	private @Inject IPacienteDao pacienteDao;
	private @Inject IIdentificacionIndividuoDao identificacionIndividuoDao;
	private @Inject IPersonalDao personalDao;
	private @Inject ITipoTareaDao tipoTareaDao;
	private @Inject INombreTareaDao nombreTareaDao;

	/******* OBJECT **********/
	private Tarea tarea;
	private Tarea tareaSelected;
	private Sucursal sucursal;
	private Usuario usuario;

	/******* LIST **********/
	private List<Tarea> listaTarea;
	public static List<NombreTarea> nombreTareas;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;
	private String tipoBusqueda = "NP";

	@Inject
	Conversation conversation;
	public static List<Paciente> listaPacientes;
	public static List<Personal> personales;
	public static List<TipoTarea> tipoTareas;

	/**
	 * 
	 */
	public TareaController() {
	}

	public Tarea getTarea() {
		return tarea;
	}

	public Tarea getTareaSelected() {
		return tareaSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<Tarea> getListaTarea() {
		return listaTarea;
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

	public void setTarea(Tarea tarea) {
		this.tarea = tarea;
	}

	public void setTareaSelected(Tarea tareaSelected) {
		this.tareaSelected = tareaSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaTarea(List<Tarea> listaTarea) {
		this.listaTarea = listaTarea;
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getTipoBusqueda() {
		return tipoBusqueda;
	}

	public void setTipoBusqueda(String tipoBusqueda) {
		this.tipoBusqueda = tipoBusqueda;
	}

	public static List<Personal> getPersonales() {
		return personales;
	}

	public static void setPersonales(List<Personal> personales) {
		TareaController.personales = personales;
	}

	/**
	 * @return the tipoTareas
	 */
	public static List<TipoTarea> getTipoTareas() {
		return tipoTareas;
	}

	/**
	 * @param tipoTareas
	 *            the tipoTareas to set
	 */
	public static void setTipoTareas(List<TipoTarea> tipoTareas) {
		TareaController.tipoTareas = tipoTareas;
	}

	/**
	 * @return the nombreTareas
	 */
	public static List<NombreTarea> getNombreTareas() {
		return nombreTareas;
	}

	/**
	 * @param nombreTareas
	 *            the nombreTareas to set
	 */
	public static void setNombreTareas(List<NombreTarea> nombreTareas) {
		TareaController.nombreTareas = nombreTareas;
	}

	@PostConstruct
	public void initNew() {
		tarea = new Tarea();
		tareaSelected = new Tarea();
		usuario = sessionMain.getUsuarioLogin();
		sucursal = sessionMain.PruebaSucursal();
		listaTarea = tareaDao.obtenerPorSucursal(sucursal);
		tipoBusqueda = "NP";
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
	}

	public List<NombreTarea> onCompleteNombreTarea(String query) {
		String upperQuery = query.toUpperCase().trim();
		nombreTareas = nombreTareaDao.obtenerPorSucursalAutoComplete(
				upperQuery, sucursal);
		return nombreTareas;
	}

	public void onSelectNombreTareaClick(SelectEvent event) {
		tarea.setNombreTarea((NombreTarea) event.getObject());
		System.out.println("tipo de tarea seleccionada = "
				+ tarea.getTipoTarea().getNombre());
	}

	private List<Paciente> getPacientesDI(
			List<IdentificacionPaciente> identificacionPacientes) {
		List<Paciente> pacientes = new ArrayList<>();
		for (IdentificacionPaciente identificacionPaciente : identificacionPacientes) {
			pacientes.add(identificacionPaciente.getIndividuo());
		}
		return pacientes;
	}

	public List<Personal> onCompletePersonal(String query) {
		String upperQuery = query.toUpperCase().trim();
		personales = personalDao.obtenerPorNombreSucursal(upperQuery,
				upperQuery, upperQuery, sucursal);
		return personales;
	}

	public List<TipoTarea> onCompleteTipoTarea(String query) {
		String upperQuery = query.toUpperCase().trim();
		tipoTareas = tipoTareaDao.obtenerPorSucursalAutoComplete(upperQuery,
				sucursal);
		return tipoTareas;
	}

	public List<Paciente> onCompleteFind(String query) {
		String upperQuery = query.toUpperCase().trim();
		if (tipoBusqueda.equals("NP")) {
			listaPacientes = pacienteDao.obtenerIndividuosPorNombreSucursal(
					upperQuery, upperQuery, upperQuery, sucursal);
		}
		if (tipoBusqueda.equals("CP"))
			listaPacientes = pacienteDao.obtenerIndividuosPorCodigo(upperQuery,
					sucursal.getCompania());
		if (tipoBusqueda.equals("DI")) {
			List<IdentificacionPaciente> auxiliar = identificacionIndividuoDao
					.obtenerPorCompaniaAutoComplete(upperQuery,
							sucursal.getCompania());
			System.out.println("DIs = " + auxiliar.size());
			listaPacientes = getPacientesDI(auxiliar);
		}
		return listaPacientes;
	}

	public void onSelectPacienteClick(SelectEvent event) {
		// tarea.setPaciente((Paciente) event.getObject());
	}

	public void onSelectPersonalClick(SelectEvent event) {
		tarea.setPersonal((Personal) event.getObject());
	}

	public void onSelectTipoTareaClick(SelectEvent event) {
		tarea.setTipoTarea((TipoTarea) event.getObject());
	}

	public void actionNuevo() {
		crear = false;
		modificar = false;
		registrar = true;
	}

	public void cambiarAspecto() {
		crear = false;
		registrar = true;
		modificar = false;
	}

	public void onRowSelect(SelectEvent event) {
		crear = false;
		registrar = false;
		modificar = true;
		/*
		 * if (estado.equals("AC")) { setEstado("ACTIVO"); } else { if
		 * (estado.equals("IN")) { setEstado("INACTIVO"); } else {
		 * setEstado("ELIMINADO"); } }
		 */
		FacesContext.getCurrentInstance().renderResponse();
		resetearFitrosTabla("form001:singleDT");
	}

	public void resetearFitrosTabla(String id) {
		DataTable table = (DataTable) FacesContext.getCurrentInstance()
				.getViewRoot().findComponent(id);
		table.setSelection(null);
		table.reset();
	}

	public boolean verificarRestriccion(String dato) {
		return dato.toLowerCase().contains("receta");
	}

	public void registrar() {
		try {
			if (tarea.getNombreTarea() == null || getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				tarea.setEstado("AC");
				tarea.setSucursal(getSucursal());
				tarea.setCompania(getSucursal().getCompania());
				tarea.setFechaRegistro(new Date());
				tarea.setFechaModificacion(tarea.getFechaRegistro());
				tarea.setUsuarioRegistro(getUsuario().getLogin());
				tareaDao.registrar(tarea);
				initNew();
			}
		} catch (Exception e) {
			System.out.println("Error en registro de tarea: " + e.getMessage());
		}
	}

	public void actualizar() {
		try {
			if (tarea.getNombreTarea() == null
					|| tarea.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				tarea.setFechaModificacion(new Date());
				tarea.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getLogin());
				tareaDao.modificar(tarea);
				initNew();
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de tarea: "
					+ e.getMessage());
		}
	}

	public void eliminar() {
		try {
			tareaDao.eliminar(getTarea());
			initNew();
		} catch (Exception e) {
			System.out.println("Error en eliminacion de tarea: "
					+ e.getMessage());
		}
	}

	public void initConversation() {
		if (!FacesContext.getCurrentInstance().isPostback()
				&& conversation.isTransient()) {
			conversation.begin();
			System.out.println(">>>>>>>>>> CONVERSACION INICIADA...");
		}
	}

	public String endConversation() {
		if (!conversation.isTransient()) {
			conversation.end();
			System.out.println(">>>>>>>>>> CONVERSACION TERMINADA...");
		}
		return "kardex_producto.xhtml?faces-redirect=true";
	}

}
