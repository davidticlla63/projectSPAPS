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
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import com.teds.spaps.interfaces.dao.ICitaDao;
import com.teds.spaps.interfaces.dao.ICompaniaDao;
import com.teds.spaps.interfaces.dao.IConsultaDao;
import com.teds.spaps.interfaces.dao.IDesgOrdenServicioDao;
import com.teds.spaps.interfaces.dao.IIdentificacionIndividuoDao;
import com.teds.spaps.interfaces.dao.IPacienteDao;
import com.teds.spaps.interfaces.dao.IPacienteEmpresaDao;
import com.teds.spaps.interfaces.dao.IPersonalDao;
import com.teds.spaps.interfaces.dao.IPlanSeguroDao;
import com.teds.spaps.interfaces.dao.IPlanesSeguroDao;
import com.teds.spaps.model.Cita;
import com.teds.spaps.model.Consulta;
import com.teds.spaps.model.DesgOrdenServicio;
import com.teds.spaps.model.IdentificacionPaciente;
import com.teds.spaps.model.Paciente;
import com.teds.spaps.model.PacienteEmpresa;
import com.teds.spaps.model.Personal;
import com.teds.spaps.model.PlanesSeguro;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;
import com.teds.spaps.util.Time;

/**
 * 
 * @author mauriciobejaranorivera
 *
 */
@ManagedBean(name = "citas2Controller")
@ViewScoped
public class Citas2Controller implements Serializable {

	private static final long serialVersionUID = 1L;

	/******* DAO **********/
	private @Inject ICitaDao citaDao;
	private @Inject SessionMain sessionMain;
	private @Inject IPacienteDao pacienteDao;
	private @Inject IPersonalDao personalDao;
	private @Inject IConsultaDao consultaDao;
	private @Inject IPlanesSeguroDao planesSeguroDao;
	private @Inject IPlanSeguroDao planSeguroDao;
	private @Inject IIdentificacionIndividuoDao identificacionIndividuoDao;
	private @Inject IPacienteEmpresaDao pacienteEmpresaDao;
	private @Inject IDesgOrdenServicioDao desgOrdenServicioDao;
	private @Inject ICompaniaDao companiaDao;

	/******* OBJECT **********/
	private Cita cita;
	private Cita citaSelected;
	private Sucursal sucursal;
	private Paciente selectedIndividuo;
	private Personal personal;
	private Consulta consulta;

	/******* LIST **********/
	private List<Cita> listaCita;
	public static List<Paciente> listaPacientes = new ArrayList<Paciente>();
	private List<PlanesSeguro> planesSeguros = new ArrayList<PlanesSeguro>();
	private List<PacienteEmpresa> empresas = new ArrayList<PacienteEmpresa>();
	private List<DesgOrdenServicio> ordenesServicios = new ArrayList<DesgOrdenServicio>();

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;
	private boolean seleccionado = false;

	// VAR
	private String currentPage = "/pages/citas/gestion_citas/list.xhtml";
	private String tipoBusqueda = "NP";
	private String tipoAtencion;

	/**** MODELO DE AGENDAS ****/
	private ScheduleModel eventModel;
	// private ScheduleModel lazyEventModel;
	private ScheduleEvent event = new DefaultScheduleEvent();

	@Inject
	private FacesContext facesContext;
	private HttpServletRequest request;

	@PostConstruct
	public void initNew() {
		sucursal = sessionMain.PruebaSucursal();

		System.out.println("Sucursal : " + sucursal.getDescripcion());
		System.out.println("Compañia : "
				+ sucursal.getCompania().getDescripcion());
		defaultValues();

	}

	public void initScrendule() {
		System.out.println("Ingreso a initScrendule");
		eventModel = new DefaultScheduleModel();
		for (Cita cita : listaCita) {
			if (!cita.getEstado().equals("RM")) {
				String color = colorStyleClass(cita.getEstadoAtencion()
						.toUpperCase());
				System.out.println("Color : " + color);

				DefaultScheduleEvent scheduleEvent = new DefaultScheduleEvent(
						cita.getId() + ":" + cita.getIndividuo().getNombre()
								+ " "
								+ cita.getIndividuo().getApellidoPaterno()
								+ " "
								+ cita.getIndividuo().getApellidoMaterno(),
						cita.getHora(), cita.getHoraEspera(), color);
				scheduleEvent.setData(cita);
				eventModel.addEvent(scheduleEvent);
				System.out.println(cita.getId() + ":" + cita.getMotivo());

			}
		}
	}

	public void cargarSeguros(ValueChangeEvent changeEvent) {
		String tipoAtencion1 = changeEvent.getNewValue().toString();
		System.out.println("entro a cargar seguros con " + tipoAtencion);
		if (tipoAtencion1.equals("S")) {
			consulta.setTipoAtencion(tipoAtencion);
			planesSeguros = planesSeguroDao
					.obtenerPorIndividuo(selectedIndividuo);
		}
		if (tipoAtencion1.equals("E")) {
			consulta.setTipoAtencion(tipoAtencion);
			empresas = pacienteEmpresaDao.obtenerPorPaciente(selectedIndividuo);
			ordenesServicios = desgOrdenServicioDao.obtenerPorClienteAprobadas(
					selectedIndividuo, sucursal);
		}
		if (tipoAtencion1.equals("P")) {
			consulta.setTipoAtencion(tipoAtencion);
		}
		tipoAtencion = tipoAtencion1;
	}

	public void selectSeguro(ValueChangeEvent event) {
		int id = Integer.parseInt(event.getNewValue().toString());
		consulta.setPlanSeguro(planSeguroDao.obtenerPlanSeguro(id));
	}

	public void selectEmpresa(ValueChangeEvent event) {
		int id = Integer.parseInt(event.getNewValue().toString());
		consulta.setEmpresa(companiaDao.obtenerCompania(id));
	}

	public void selectOrdenServicio(ValueChangeEvent event) {
		int id = Integer.parseInt(event.getNewValue().toString());
		consulta.setOrdenServicio(desgOrdenServicioDao
				.obtenerDesgOrdenServicio(id));
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
		consulta = new Consulta();
		tipoBusqueda = "NP";
		tipoAtencion = "";
		seleccionado = false;
		cita = new Cita();
		citaSelected = new Cita();
		selectedIndividuo = new Paciente();
		List<Personal> listPersonals = new ArrayList<Personal>();
		listPersonals = personalDao.obtenerPorUsuario(sessionMain
				.getUsuarioLogin());
		if (listPersonals.size() == 1) {
			personal = listPersonals.get(0);
			Date fechaActual = new Date();
			Date fechaini = Time.sumarRestarDiasFecha(fechaActual, -15);
			Date fechafin = Time.sumarRestarDiasFecha(fechaActual, 15);
			// listaCita =
			// citaDao.obtenerPorCitasEntreDosFechas(sessionMain.getUsuarioLogin().getLogin(),
			// sucursal, fechaini, fechafin);
			listaCita = citaDao.obtenerPorCitasEntreDosFechasPorMedicos(personal,
					sucursal, fechaini, fechafin);
			planesSeguros = new ArrayList<PlanesSeguro>();

			initScrendule();
			System.out.println("listaCita : " + listaCita.size());
			currentPage = "/pages/citas/gestion_citas/list.xhtml";
		}
		
	}

	public List<Paciente> onComplete(String query) {
		String upperQuery = query.toUpperCase().trim();
		if (tipoBusqueda.equals("NP")) {
			listaPacientes = pacienteDao.obtenerIndividuosPorNombreSucursal(
					upperQuery, upperQuery, upperQuery, sucursal);
		}
		if (tipoBusqueda.equals("CP"))
			listaPacientes = pacienteDao.obtenerIndividuosPorCodigo(upperQuery,
					sucursal.getCompania());
		if (tipoBusqueda.equals("CH"))
			listaPacientes = pacienteDao.obtenerPorCodigoHC(upperQuery,
					sucursal.getCompania());
		if (tipoBusqueda.equals("DI")) {
			List<IdentificacionPaciente> auxiliar = identificacionIndividuoDao
					.obtenerPorCompaniaAutoComplete(upperQuery,
							sucursal.getCompania());
			System.out.println("DIs = " + auxiliar.size());
			listaPacientes = getPacientesDI(auxiliar);
		}
		System.out.println("lista de pacientes = " + listaPacientes.size());
		return listaPacientes;
	}

	private List<Paciente> getPacientesDI(
			List<IdentificacionPaciente> identificacionPacientes) {
		List<Paciente> pacientes = new ArrayList<>();
		for (IdentificacionPaciente identificacionPaciente : identificacionPacientes) {
			pacientes.add(identificacionPaciente.getIndividuo());
		}
		return pacientes;
	}

	public void onSelectPacienteClick(SelectEvent event) {
		selectedIndividuo = (Paciente) event.getObject();
		tipoAtencion = selectedIndividuo.getTipoAfiliacion();
	}

	public void registrar() {
		try {
			System.out.println("Ingreso a registrar");

			if (selectedIndividuo.getId().intValue() == 0
					|| selectedIndividuo == null) {
				FacesUtil.infoMessage("VALIDACION", "Seleccione El Paciente");
				return;
			}
			if (consultaDao.tieneConsultaPaciente(selectedIndividuo,
					sucursal.getCompania())) {
				consulta = consultaDao.obtenerConsultaActualDePaciente(
						selectedIndividuo, sucursal.getCompania());
			} else {
				if (cita.getClase().equals("CO")) {
					consulta.setReconsulta(0);
					consulta.setTipoConsulta(cita.getClase());
				}
				if (cita.getClase().equals("RC")) {
					Consulta reconsulta = consultaDao.obtenerReConsulta(
							selectedIndividuo, sucursal.getCompania());
					consulta.setReconsulta(reconsulta.getId());
					consulta.setTipoConsulta(cita.getClase());
				}
				consulta.setCompania(sucursal.getCompania());
				consulta.setEstado("AT");
				consulta.setFechaRegistro(new Date());
				consulta.setFechaModificacion(consulta.getFechaRegistro());
				consulta.setPaciente(selectedIndividuo);
				consulta.setSucursal(sucursal);
				consulta.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getLogin());
				consulta = consultaDao.registrar(consulta);
			}
			if (consulta != null)
				sessionMain.setConsulta(consulta);
			cita.setHoraEspera(Time.sumMinutes(cita.getHora(), 15));
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
			cita.setHoraEspera(Time.sumMinutes(cita.getHora(), 15));
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
		currentPage = "/pages/citas/gestion_citas/edit.xhtml";
	}

	public void actionNuevo() {
		System.out.println("Ingreso a actionNuevo...");
		crear = false;
		seleccionado = false;
		registrar = true;
		cita = new Cita();
		cita.setFechaRegistro(new Date());

		currentPage = "/pages/citas/gestion_citas/edit.xhtml";
	}

	public void actionCancelar() {
		crear = true;
		seleccionado = false;
		registrar = false;
		cita = new Cita();
		currentPage = "/pages/citas/gestion_citas/list.xhtml";
	}

	public void actionModificar(Integer id) {
		crear = false;
		seleccionado = true;
		registrar = false;
		currentPage = "/pages/citas/gestion_citas/edit.xhtml";
	}

	// ONCOMPLETETEXT PAIS
	public List<Paciente> completePaciente(String query) {
		String upperQuery = query.toUpperCase();
		// if (upperQuery.trim().length() == 0) {
		// FacesUtil.infoMessage("VALIDACION",
		// "Escriba el Nombre del Paciente");
		// return null;
		// }
		listaPacientes = pacienteDao.obtenerIndividuosPorNombreSucursal(
				upperQuery, upperQuery, upperQuery, sucursal);
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
		System.out.println("Cita : ");
		selectedIndividuo = cita.getIndividuo();
		crear = false;
		seleccionado = true;
		registrar = false;
		FacesUtil.showDialog("eventDialog");
		// currentPage = "/pages/citas/gestion_citas/edit.xhtml";
	}

	public void currentEdit() {
		System.out.println("Ingreso a currentEdit");
		crear = false;
		seleccionado = true;
		registrar = false;
		FacesUtil.hideDialog("eventDialog");
		currentPage = "/pages/citas/gestion_citas/edit.xhtml";
	}

	public void redireccionar() {
		try {
			System.out.println("Ingreso a redireccionar");
			String navigateString;
			if (selectedIndividuo.getCodigoHc() == null
					|| selectedIndividuo.getCodigoHc().trim().length() == 0) {
				// CREACION DE HOJA MEDICA
				navigateString = "/pages/historias_clinicas/registro_historias_clinicas/index.xhtml?idPaciente="
						+ selectedIndividuo.getId();
			} else {
				// CREACION DE HISTORIA CLINICA
				navigateString = "/pages/historias_clinicas/registro_detalle_historias_clinicas/historia_medica.xhtml?idPaciente="
						+ selectedIndividuo.getId();
			}
			FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap()
					.put("historiaClinicaDetalleController", null);
			request = (HttpServletRequest) facesContext.getExternalContext()
					.getRequest();
			System.out.println(request.getContextPath() + navigateString);
			facesContext.getExternalContext().redirect(
					request.getContextPath() + navigateString);

		} catch (Exception e) {
			System.out.println("Error en registro de paciente: "
					+ e.getMessage());
		}

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
		currentPage = "/pages/citas/gestion_citas/edit.xhtml";
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
		Citas2Controller.listaPacientes = listaPacientes;
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

	public Consulta getConsulta() {
		return consulta;
	}

	public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
	}

	/**
	 * @return the planesSeguros
	 */
	public List<PlanesSeguro> getPlanesSeguros() {
		return planesSeguros;
	}

	/**
	 * @param planesSeguros
	 *            the planesSeguros to set
	 */
	public void setPlanesSeguros(List<PlanesSeguro> planesSeguros) {
		this.planesSeguros = planesSeguros;
	}

	public String getTipoBusqueda() {
		return tipoBusqueda;
	}

	public void setTipoBusqueda(String tipoBusqueda) {
		this.tipoBusqueda = tipoBusqueda;
	}

	/**
	 * @return the tipoAtenciion
	 */
	public String getTipoAtencion() {
		return tipoAtencion;
	}

	/**
	 * @param tipoAtenciion
	 *            the tipoAtenciion to set
	 */
	public void setTipoAtencion(String tipoAtenciion) {
		this.tipoAtencion = tipoAtenciion;
	}

	/**
	 * @return the empresas
	 */
	public List<PacienteEmpresa> getEmpresas() {
		return empresas;
	}

	/**
	 * @param empresas
	 *            the empresas to set
	 */
	public void setEmpresas(List<PacienteEmpresa> empresas) {
		this.empresas = empresas;
	}

	/**
	 * @return the ordenesServicios
	 */
	public List<DesgOrdenServicio> getOrdenesServicios() {
		return ordenesServicios;
	}

	/**
	 * @param ordenesServicios
	 *            the ordenesServicios to set
	 */
	public void setOrdenesServicios(List<DesgOrdenServicio> ordenesServicios) {
		this.ordenesServicios = ordenesServicios;
	}

}
