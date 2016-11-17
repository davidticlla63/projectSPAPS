/**
 * @author ANITA
 */
package com.teds.spaps.controller;

import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import com.teds.spaps.interfaces.dao.IIdentificacionIndividuoDao;
import com.teds.spaps.interfaces.dao.IMensajeDao;
import com.teds.spaps.interfaces.dao.IPacienteDao;
import com.teds.spaps.interfaces.dao.IPersonalDao;
import com.teds.spaps.interfaces.dao.ITareaDao;
import com.teds.spaps.model.IdentificacionPaciente;
import com.teds.spaps.model.Mensaje;
import com.teds.spaps.model.Paciente;
import com.teds.spaps.model.Personal;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.Tarea;
import com.teds.spaps.model.Usuario;
import com.teds.spaps.structore.EDTarea;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;
import com.teds.spaps.util.Time;

/**
 * @author ANITA
 *
 */
@ManagedBean(name = "misTareaController")
@ViewScoped
public class MisTareaController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8530505501957475661L;
	/******* DAO **********/
	private @Inject ITareaDao tareaDao;
	private @Inject SessionMain sessionMain;
	private @Inject IPacienteDao pacienteDao;
	private @Inject IIdentificacionIndividuoDao identificacionIndividuoDao;
	private @Inject IPersonalDao personalDao;
	private @Inject IMensajeDao mensajeDao;

	/******* OBJECT **********/
	private Tarea tarea;
	private Tarea tareaSelected;
	private Sucursal sucursal;
	private Usuario usuario;
	private ScheduleModel eventModel;
	private ScheduleEvent event = new DefaultScheduleEvent();
	private Personal personal;
	private EDTarea edTarea;
	private Mensaje mensaje;

	/******* LIST **********/
	private List<Tarea> listaTarea;
	public static List<Paciente> listaPacientes;
	public static List<Personal> personales;
	private List<EDTarea> vistaTareas;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;
	private String tipoBusqueda = "NP";

	@Inject
	Conversation conversation;

	/**
	 * 
	 */
	public MisTareaController() {
	}

	@PostConstruct
	public void initNew() {
		tarea = new Tarea();
		tareaSelected = new Tarea();
		usuario = sessionMain.getUsuarioLogin();
		sucursal = sessionMain.PruebaSucursal();
		personal = personalDao.obtenerPorUsuario(usuario, sucursal);
		personales = new ArrayList<Personal>();
		// listaTarea = tareaDao.obtenerPorPersonal(personal, sucursal);
		listaTarea = tareaDao.obtenerPorCompania(sucursal.getCompania());
		System.out.println("tareas = " + listaTarea.size());
		tipoBusqueda = "NP";
		edTarea = new EDTarea();
		cargarView();
		// initSchedule();
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
		mensaje = new Mensaje();
	}

	public void enviarMensaje(EDTarea edTarea) {
		mensaje.setReceptor(edTarea.getTarea().getPersonal());
		Personal personal = personalDao.obtenerPorUsuario(usuario, sucursal);
		mensaje.setEmisor(personal);
		mensaje.setTarea(edTarea.getTarea());
		FacesUtil.updateComponent("formDlg001");
		FacesUtil.showDialog("dlgMensaje");
	}

	public void send() {
		try {
			if (getSucursal() == null || getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				if (mensaje.getMensaje().isEmpty()) {
					FacesUtil.errorMessage("No puede enviar un mensaje vacio.");
					return;
				}
				mensaje.setEstado("AC");
				mensaje.setSucursal(getSucursal());
				mensaje.setCompania(getSucursal().getCompania());
				mensaje.setFechaRegistro(new Date());
				mensaje.setFechaModificacion(mensaje.getFechaRegistro());
				mensaje.setUsuarioRegistro(getUsuario().getLogin());
				mensaje = mensajeDao.registrar(mensaje);
				FacesUtil.infoMessage("FELICIDADES",
						"Mensaje enviado correctamente.");
				initNew();
			}
		} catch (Exception e) {
			System.out.println("Error en registro de tarea: " + e.getMessage());
		}
	}

	public void cargarView() {
		vistaTareas = new ArrayList<EDTarea>();
		for (Tarea tarea : listaTarea) {
			EDTarea edTarea = new EDTarea();
			edTarea.setTarea(tarea);
			List<Mensaje> mensajes = mensajeDao
					.obtenerPorTarea(tarea, sucursal);
			edTarea.setMensajes(mensajes);
			vistaTareas.add(edTarea);
		}
	}

	public void initSchedule() {
		eventModel = new DefaultScheduleModel();
		for (Tarea tarea : listaTarea) {
			// String color =
			// colorStyleClass(cita.getEstadoAtencion().toUpperCase());
			DefaultScheduleEvent scheduleEvent;
			if (tarea.getOrdenServicio() == null) {
				scheduleEvent = new DefaultScheduleEvent(tarea.getTipoTarea()
						+ " : " + tarea.getNombreTarea().getNombre(),
						tarea.getFechaRegistro(), Time.sumMinutes(
								tarea.getFechaRegistro(), 15), "activo");
			} else {
				scheduleEvent = new DefaultScheduleEvent(tarea.getTipoTarea()
						+ " : "
						+ tarea.getNombreTarea().getNombre()
						+ " : "
						+ tarea.getOrdenServicio().getCodigo()
						+ " : "
						+ tarea.getOrdenServicio().getCliente().getNombre()
						+ " "
						+ tarea.getOrdenServicio().getCliente()
								.getApellidoPaterno()
						+ " "
						+ tarea.getOrdenServicio().getCliente()
								.getApellidoMaterno(),
						tarea.getFechaRegistro(), Time.sumMinutes(
								tarea.getFechaRegistro(), 15), "atendido");
			}
			scheduleEvent.setData(tarea);
			eventModel.addEvent(scheduleEvent);
		}
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

	public void addEvent(ActionEvent actionEvent) {
		if (event.getId() == null)
			eventModel.addEvent(event);
		else
			eventModel.updateEvent(event);

		event = new DefaultScheduleEvent();
	}

	public void onEventSelect(SelectEvent selectEvent) {
		event = (ScheduleEvent) selectEvent.getObject();
		System.out.println("Ingreso a onEventSelect ");
		tarea = new Tarea();
		tarea = (Tarea) event.getData();
		FacesUtil.showDialog("dlgTarea");

		/*
		 * cita = (Cita) event.getData(); System.out.println("Cita : " );
		 * selectedIndividuo = cita.getIndividuo(); crear = false; seleccionado
		 * = true; registrar = false; FacesUtil.showDialog("eventDialog");
		 */
		// currentPage = "/pages/citas/gestion_citas/edit.xhtml";
	}

	public void onDateSelect(SelectEvent selectEvent) {
		event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(),
				(Date) selectEvent.getObject());
		System.out.println("Ingreso a onDateSelect ");
		// cita = citaSelected;

		/*
		 * crear = false; seleccionado = false; registrar = true; cita = new
		 * Cita(); cita.setHora(event.getStartDate());
		 * cita.setHoraEspera(event.getEndDate()); cita.setFechaRegistro(new
		 * Date()); currentPage = "/pages/citas/gestion_citas/edit.xhtml";
		 */
	}

	@SuppressWarnings("unused")
	public void onEventMove(ScheduleEntryMoveEvent event) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Event moved", "Day delta:" + event.getDayDelta()
						+ ", Minute delta:" + event.getMinuteDelta());

		// addMessage(message);
	}

	@SuppressWarnings("unused")
	public void onEventResize(ScheduleEntryResizeEvent event) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Event resized", "Day delta:" + event.getDayDelta()
						+ ", Minute delta:" + event.getMinuteDelta());

		// addMessage(message);
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
		MisTareaController.personales = personales;
	}

	/**
	 * @return the personal
	 */
	public Personal getPersonal() {
		return personal;
	}

	/**
	 * @param personal
	 *            the personal to set
	 */
	public void setPersonal(Personal personal) {
		this.personal = personal;
	}

	/**
	 * @return the eventModel
	 */
	public ScheduleModel getEventModel() {
		return eventModel;
	}

	/**
	 * @param eventModel
	 *            the eventModel to set
	 */
	public void setEventModel(ScheduleModel eventModel) {
		this.eventModel = eventModel;
	}

	/**
	 * @return the event
	 */
	public ScheduleEvent getEvent() {
		return event;
	}

	/**
	 * @param event
	 *            the event to set
	 */
	public void setEvent(ScheduleEvent event) {
		this.event = event;
	}

	/**
	 * @return the vistaTareas
	 */
	public List<EDTarea> getVistaTareas() {
		return vistaTareas;
	}

	/**
	 * @param vistaTareas
	 *            the vistaTareas to set
	 */
	public void setVistaTareas(List<EDTarea> vistaTareas) {
		this.vistaTareas = vistaTareas;
	}

	/**
	 * @return the edTarea
	 */
	public EDTarea getEdTarea() {
		return edTarea;
	}

	/**
	 * @param edTarea
	 *            the edTarea to set
	 */
	public void setEdTarea(EDTarea edTarea) {
		this.edTarea = edTarea;
	}

	/**
	 * @return the mensaje
	 */
	public Mensaje getMensaje() {
		return mensaje;
	}

	/**
	 * @param mensaje
	 *            the mensaje to set
	 */
	public void setMensaje(Mensaje mensaje) {
		this.mensaje = mensaje;
	}

}
