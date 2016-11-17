package com.teds.spaps.controller;

import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import com.teds.spaps.interfaces.dao.ICitaDao;
import com.teds.spaps.interfaces.dao.IEspecialidadDao;
import com.teds.spaps.interfaces.dao.IPacienteDao;
import com.teds.spaps.interfaces.dao.IPersonalDao;
import com.teds.spaps.interfaces.dao.ITipoAtencionDao;
import com.teds.spaps.model.Cita;
import com.teds.spaps.model.Especialidad;
import com.teds.spaps.model.Paciente;
import com.teds.spaps.model.Personal;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;
import com.teds.spaps.util.Time;

/**
 * 
 * @author david
 *
 */
@ManagedBean(name = "agendaController")
@ViewScoped
public class AgendaController implements Serializable {

	private static final long serialVersionUID = 1L;

	/******* DAO **********/
	private @Inject ICitaDao citaDao;
	private @Inject SessionMain sessionMain;
	private @Inject IPacienteDao pacienteDao;
	private @Inject ITipoAtencionDao tipoAtencionDao;
	private @Inject IPersonalDao personalDao;
	private @Inject IEspecialidadDao especialidadDao;

	/******* OBJECT **********/
	private Cita cita;
	private Cita citaSelected;
	private Sucursal sucursal;
	private Paciente selectedIndividuo;
	private Personal personal;
	private Personal selectedMedico;
	private Especialidad selectedEspecialidad;

	/******* LIST **********/
	private List<Cita> listaCita;
	public static List<Paciente> listaPacientes = new ArrayList<Paciente>();
	private List<Especialidad> listaEspecialidad = new ArrayList<Especialidad>();
	private List<Personal> listaMedicos = new ArrayList<Personal>();

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;
	private boolean seleccionado = false;
	private Date fechaActual = new Date();

	// VAR
	private String currentPage = "/pages/citas/gestion_agenda/list.xhtml";

	/**** MODELO DE AGENDAS ****/
	private ScheduleModel eventModel = new DefaultScheduleModel();
	// private ScheduleModel lazyEventModel;
	private ScheduleEvent event = new DefaultScheduleEvent();

	@PostConstruct
	public void initNew() {
		sucursal = sessionMain.PruebaSucursal();
		System.out.println("Sucursal : " + sucursal.getDescripcion());
		System.out.println("Compañia : "
				+ sucursal.getCompania().getDescripcion());
		defaultValues();
		selectedEspecialidad = new Especialidad();
		selectedMedico = new Personal();
	}

	public void initScrendule() {
		System.out.println("Ingreso a initScrendule");
		eventModel = new DefaultScheduleModel();
		for (Cita cita : listaCita) {
			String color = colorStyleClass(cita.getEstadoAtencion()
					.toUpperCase());
			System.out.println("Color : " + color);
			DefaultScheduleEvent scheduleEvent = new DefaultScheduleEvent(
					cita.getId() + ":" + cita.getMotivo() + ":"
							+ cita.getIndividuo().getNombre() + " "
							+ cita.getIndividuo().getApellidoPaterno() + " "
							+ cita.getIndividuo().getApellidoMaterno(),
					cita.getHora(), cita.getHoraEspera(), color);
			scheduleEvent.setData(cita);
			eventModel.addEvent(scheduleEvent);
			System.out.println(cita.getId() + ":" + cita.getMotivo());
		}
	}

	private String colorStyleClass(String estado) {
		System.out.println("estado:" + estado);
		switch (estado) {
		case "AC":
			return "nuevo";
		case "PN":
			return "pendiente";
		case "NA":
			return "no_atendido";
		case "PR":
			return "atendido";

		}
		return "";
	}

	private void defaultValues() {
		System.out.println("Ingreso a defaultValues");
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
		seleccionado = false;
		cita = new Cita();
		citaSelected = new Cita();
		selectedIndividuo = new Paciente();

		currentPage = "/pages/citas/gestion_agenda/list.xhtml";
	}

	public void consulta() {
		personal = selectedMedico;
		if (selectedEspecialidad.getId() == 0 || selectedEspecialidad == null) {
			FacesUtil.infoMessage("VALIDACION", "Seleccione Especiliadad");
			return;
		}
		if (personal.getId() == 0 || personal == null) {
			FacesUtil.infoMessage("VALIDACION", "Seleccione Medico");
			return;
		}
		listaCita = citaDao.obtenerPorCitasEntreDosFechasPorMedicos(personal,
				sucursal, fechaActual, fechaActual);

		initScrendule();
	}

	public void registrar() {
		try {
			System.out.println("Ingreso a registrar");

			if (selectedIndividuo.getId().intValue() == 0
					|| selectedIndividuo == null) {
				FacesUtil.infoMessage("VALIDACION", "Seleccione El Paciente");
				return;
			}
			cita.setHoraEspera(Time.sumMinutes(cita.getHora(), 30));
			cita.setIndividuo(selectedIndividuo);
			cita.setPersonal(personal);
			cita.setSucursal(sucursal);
			cita.setFechaRegistro(new Date());
			cita.setFechaModificacion(cita.getFechaRegistro());
			cita.setUsuarioRegistro(sessionMain.getUsuarioLogin().getLogin());

			Cita r = citaDao.registrar(cita);
			if (r != null) {
				defaultValues();
			} else {
				return;
			}
		} catch (Exception e) {
			System.out.println("Error en registro de cita: " + e.getMessage());
		}

	}

	public void actualizar() {
		try {
			System.out.println("Ingreso a actualizar");

			if (selectedIndividuo.getId().intValue() == 0
					|| selectedIndividuo == null) {
				FacesUtil.infoMessage("VALIDACION", "Seleccione El Paciente");
				return;
			}
			cita.setHoraEspera(Time.sumMinutes(cita.getHora(), 30));
			cita.setIndividuo(selectedIndividuo);
			cita.setPersonal(personal);
			cita.setSucursal(getSucursal());
			cita.setFechaModificacion(new Date());
			cita.setUsuarioRegistro(sessionMain.getUsuarioLogin().getLogin());
			Cita r = citaDao.modificar(cita);
			if (r != null) {
				defaultValues();
			} else {
				return;
			}

		} catch (Exception e) {
			System.out.println("Error en modificacion de cita: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			if (citaDao.eliminar(cita)) {
				defaultValues();
			} else {
				return;
			}
		} catch (Exception e) {
			System.out.println("Error en eliminacion de cita: "
					+ e.getMessage());
		}
	}

	public void onRowSelect(SelectEvent event) {
		cita = citaSelected;
		crear = false;
		seleccionado = true;
		registrar = false;
		selectedIndividuo = cita.getIndividuo();
		currentPage = "/pages/citas/gestion_agenda/edit.xhtml";
	}

	public void actionNuevo() {
		System.out.println("Ingreso a actionNuevo...");
		crear = false;
		seleccionado = false;
		registrar = true;
		cita = new Cita();
		cita.setFechaRegistro(new Date());

		currentPage = "/pages/citas/gestion_agenda/edit.xhtml";
	}

	public void actionCancelar() {
		crear = true;
		seleccionado = false;
		registrar = false;
		cita = new Cita();
		currentPage = "/pages/citas/gestion_agenda/list.xhtml";
	}

	public void actionModificar(Integer id) {
		crear = false;
		seleccionado = true;
		registrar = false;
		currentPage = "/pages/citas/gestion_agenda/edit.xhtml";
	}

	// ONCOMPLETETEXT PAIS
	public List<Paciente> completePaciente(String query) {
		String upperQuery = query.toUpperCase();
		// if (upperQuery.trim().length() == 0) {
		// FacesUtil.infoMessage("VALIDACION",
		// "Escriba el Nombre del Paciente");
		// return null;
		// }
		listaPacientes = pacienteDao.obtenerPorSucursal(upperQuery, sucursal);
		return listaPacientes;
	}

	public void onRowSelectPacienteClick(SelectEvent event) {
		System.out.println("Ingreso a onRowSelectPacienteClick");
		String nombre = event.getObject().toString();
		for (Paciente i : listaPacientes) {
			if (i.getNombre().equals(nombre)) {
				selectedIndividuo = i;
				System.out.println("Paciente : "
						+ selectedIndividuo.getNombre() + " "
						+ selectedIndividuo.getApellidoPaterno() + " "
						+ selectedIndividuo.getApellidoMaterno());
				return;
			}
		}
	}

	public boolean isSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

	/**
	 * @param lazyEventModel
	 *            the lazyEventModel to set
	 */
	// public void setLazyEventModel(ScheduleModel lazyEventModel) {
	// this.lazyEventModel = lazyEventModel;
	// }

	public Date getRandomDate(Date base) {
		Calendar date = Calendar.getInstance();
		date.setTime(base);
		date.add(Calendar.DATE, ((int) (Math.random() * 30)) + 1); // set random
																	// day of
																	// month

		return date.getTime();
	}

	public Date getInitialDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), Calendar.FEBRUARY,
				calendar.get(Calendar.DATE), 0, 0, 0);

		return calendar.getTime();
	}

	public ScheduleModel getEventModel() {
		return eventModel;
	}

	// public ScheduleModel getLazyEventModel() {
	// return lazyEventModel;
	// }

	public ScheduleEvent getEvent() {
		return event;
	}

	public void setEvent(ScheduleEvent event) {
		this.event = event;
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

		cita = (Cita) event.getData();
		System.out.println("Cita : " + cita.getMotivo());
		selectedIndividuo = cita.getIndividuo();
		crear = false;
		seleccionado = true;
		registrar = false;
		currentPage = "/pages/citas/gestion_agenda/edit.xhtml";
	}

	public void onDateSelect(SelectEvent selectEvent) {
		event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(),
				(Date) selectEvent.getObject());
		System.out.println("Ingreso a onDateSelect ");
		// cita = citaSelected;

		crear = false;
		seleccionado = false;
		registrar = true;
		cita = new Cita();
		cita.setHora(event.getStartDate());
		cita.setHoraEspera(event.getEndDate());
		cita.setFechaRegistro(new Date());
		currentPage = "/pages/citas/gestion_agenda/edit.xhtml";
	}

	// ONCOMPLETETEXT ESPECIALIDAD
	public List<Especialidad> completeEspecialidad(String query) {
		String upperQuery = query.toUpperCase();
		listaEspecialidad = especialidadDao.obtenerPorCompania(upperQuery,
				sucursal.getCompania());

		return listaEspecialidad;
	}

	public void onRowSelectEspecialidadClick(SelectEvent event) {
		String nombre = event.getObject().toString();
		for (Especialidad i : listaEspecialidad) {
			if (i.getNombre().equals(nombre)) {
				setSelectedEspecialidad(i);
				return;
			}
		}
	}

	// ONCOMPLETETEXT ESPECIALIDAD
	public List<Personal> completeMedico(String query) {
		String upperQuery = query.toUpperCase();
		listaMedicos = personalDao.obtenerPorSucursalAndEpecialidad(upperQuery,
				selectedEspecialidad, sucursal);
		return listaMedicos;
	}

	public void onRowSelectMedicoClick(SelectEvent event) {
		String nombre = event.getObject().toString();
		for (Personal i : listaMedicos) {
			if (i.getNombre().equals(nombre)) {
				setSelectedMedico(i);
				personal = selectedMedico;
				return;
			}
		}
	}

	public void onEventMove(ScheduleEntryMoveEvent event) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Event moved", "Day delta:" + event.getDayDelta()
						+ ", Minute delta:" + event.getMinuteDelta());

		addMessage(message);
	}

	public void onEventResize(ScheduleEntryResizeEvent event) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Event resized", "Day delta:" + event.getDayDelta()
						+ ", Minute delta:" + event.getMinuteDelta());

		addMessage(message);
	}

	private void addMessage(FacesMessage message) {
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	/**
		 * 
		 */

	public Cita getCita() {
		return cita;
	}

	public Cita getCitaSelected() {
		return citaSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<Cita> getListaCita() {
		return listaCita;
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

	public void setCita(Cita cita) {
		this.cita = cita;
	}

	public void setCitaSelected(Cita citaSelected) {
		this.citaSelected = citaSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaCita(List<Cita> listaCita) {
		this.listaCita = listaCita;
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
	 * @return the listaPacientes
	 */
	public List<Paciente> getListaPacientes() {
		return listaPacientes;
	}

	/**
	 * @param listaPacientes
	 *            the listaPacientes to set
	 */
	public void setListaPacientes(List<Paciente> listaPacientes) {
		this.listaPacientes = listaPacientes;
	}

	/**
	 * @return the selecttedIndividuo
	 */
	public Paciente getSelectedIndividuo() {
		return selectedIndividuo;
	}

	/**
	 * @param selecttedIndividuo
	 *            the selecttedIndividuo to set
	 */
	public void setSelectedIndividuo(Paciente selecttedIndividuo) {
		this.selectedIndividuo = selecttedIndividuo;
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
	 * @return the listaEspecialidad
	 */
	public List<Especialidad> getListaEspecialidad() {
		return listaEspecialidad;
	}

	/**
	 * @param listaEspecialidad
	 *            the listaEspecialidad to set
	 */
	public void setListaEspecialidad(List<Especialidad> listaEspecialidad) {
		this.listaEspecialidad = listaEspecialidad;
	}

	/**
	 * @return the selectedMedico
	 */
	public Personal getSelectedMedico() {
		return selectedMedico;
	}

	/**
	 * @param selectedMedico
	 *            the selectedMedico to set
	 */
	public void setSelectedMedico(Personal selectedMedico) {
		this.selectedMedico = selectedMedico;
	}

	/**
	 * @return the selectedEspecialidad
	 */
	public Especialidad getSelectedEspecialidad() {
		return selectedEspecialidad;
	}

	/**
	 * @param selectedEspecialidad
	 *            the selectedEspecialidad to set
	 */
	public void setSelectedEspecialidad(Especialidad selectedEspecialidad) {
		this.selectedEspecialidad = selectedEspecialidad;
	}

	/**
	 * @return the listaMedicos
	 */
	public List<Personal> getListaMedicos() {
		return listaMedicos;
	}

	/**
	 * @param listaMedicos
	 *            the listaMedicos to set
	 */
	public void setListaMedicos(List<Personal> listaMedicos) {
		this.listaMedicos = listaMedicos;
	}

	/**
	 * @return the fechaActual
	 */
	public Date getFechaActual() {
		return fechaActual;
	}

	/**
	 * @param fechaActual
	 *            the fechaActual to set
	 */
	public void setFechaActual(Date fechaActual) {
		this.fechaActual = fechaActual;
	}

	public ITipoAtencionDao getTipoAtencionDao() {
		return tipoAtencionDao;
	}

	public void setTipoAtencionDao(ITipoAtencionDao tipoAtencionDao) {
		this.tipoAtencionDao = tipoAtencionDao;
	}

}
