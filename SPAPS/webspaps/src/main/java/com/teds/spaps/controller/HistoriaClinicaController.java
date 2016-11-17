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
import javax.servlet.http.HttpServletRequest;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.DualListModel;

import com.teds.spaps.interfaces.dao.IAntecedenteDao;
import com.teds.spaps.interfaces.dao.IDetalleAntecedenteDao;
import com.teds.spaps.interfaces.dao.IDetalleAntecedenteEspecialidadDao;
import com.teds.spaps.interfaces.dao.IEnfermedadDao;
import com.teds.spaps.interfaces.dao.IEspecialidadDao;
import com.teds.spaps.interfaces.dao.IHistoriaClinicaDao;
import com.teds.spaps.interfaces.dao.IIndiceDao;
import com.teds.spaps.interfaces.dao.IMotivoDao;
import com.teds.spaps.interfaces.dao.IPacienteDao;
import com.teds.spaps.interfaces.dao.IPersonalDao;
import com.teds.spaps.model.Antecedente;
import com.teds.spaps.model.DetalleAntecedente;
import com.teds.spaps.model.DetalleAntecedenteEspecialidad;
import com.teds.spaps.model.Enfermedad;
import com.teds.spaps.model.Especialidad;
import com.teds.spaps.model.HistoriaClinica;
import com.teds.spaps.model.Indice;
import com.teds.spaps.model.Motivo;
import com.teds.spaps.model.Paciente;
import com.teds.spaps.model.Personal;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.Usuario;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;
import com.teds.spaps.util.Time;

/**
 * 
 * @author mauriciobejaranorivera
 *
 */
@ManagedBean(name = "historiaMedicaController")
@ViewScoped
public class HistoriaClinicaController implements Serializable {

	private static final long serialVersionUID = 1L;

	/******* DAO **********/
	private @Inject IHistoriaClinicaDao historiaClinicaDao;
	private @Inject SessionMain sessionMain;
	private @Inject IPacienteDao pacienteDao;
	private @Inject IPersonalDao personalDao;
	private @Inject IMotivoDao motivoDao;
	private @Inject IEnfermedadDao enfermedadDao;
	private @Inject IAntecedenteDao antecedenteDao;
	private @Inject IEspecialidadDao especialidadDao;
	private @Inject IDetalleAntecedenteEspecialidadDao detalleAntecedenteEspecialidadDao;
	private @Inject IDetalleAntecedenteDao detalleAntecedenteDao;
	private @Inject IIndiceDao indiceDao;

	/******* OBJECT **********/
	private HistoriaClinica historiaClinica;
	private HistoriaClinica historiaMedicaSelected;
	private Sucursal sucursal;
	private Usuario usuario;
	private Paciente selectedPaciente;
	private Personal selectedMedico;
	private Motivo motivo;
	private Motivo motivoSelected;
	private Enfermedad enfermedad;
	private Enfermedad enfermedadSelected;
	private Antecedente antecedente;
	private Antecedente antecedenteSelected;
	private Especialidad especialidad;
	private DetalleAntecedente detalleAntecedente;
	private DetalleAntecedente detalleAntecedenteSelected;

	/******* LIST **********/
	private List<HistoriaClinica> listaHistoriaMedica;
	private List<HistoriaClinica> listaHistoriaMedicas = new ArrayList<HistoriaClinica>();
	private List<Paciente> listaPacientes = new ArrayList<Paciente>();
	private List<Personal> listaMedicos = new ArrayList<Personal>();
	private List<Motivo> listaMotivos = new ArrayList<Motivo>();
	private List<Enfermedad> listaEnfermedades = new ArrayList<Enfermedad>();
	private List<Antecedente> listaAntecedentes = new ArrayList<Antecedente>();
	private List<Especialidad> listaEspecialidades = new ArrayList<Especialidad>();
	private DualListModel<String> listaAntecedentesDual;
	private List<String> listaAntecedentesDualSource = new ArrayList<String>();
	private List<String> listaAntecedentesDualTarget = new ArrayList<String>();
	private List<DetalleAntecedente> listaDetalleAntecedentes = new ArrayList<DetalleAntecedente>();

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;
	private boolean seleccionado = false;
	private boolean motivoSeleccionado = false;
	private boolean enfermedadSeleccionado = false;
	private boolean antecedenteSeleccionado = false;
	private int tabIndex = 0;

	// VAR
	private String currentPage = "/pages/historias_clinicas/registro_historias_clinicas/list.xhtml";
	private String pagina = "";
	private String currentPage1 = "/pages/historias_clinicas/registro_detalle_historias_clinicas/motivos.xhtml";

	@Inject
	private FacesContext facesContext;
	private HttpServletRequest request;

	@PostConstruct
	public void initNew() {
		usuario = sessionMain.getUsuarioLogin();
		sucursal = sessionMain.PruebaSucursal();
		System.out.println("Sucursal : " + sucursal.getDescripcion());
		System.out.println("Compañia : "
				+ sucursal.getCompania().getDescripcion());
		defaultValues();
	}

	private void defaultValues() {
		// pagina = "";
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
		especialidad = new Especialidad();
		seleccionado = false;
		enfermedadSeleccionado = false;
		antecedenteSeleccionado = false;
		motivoSeleccionado = false;
		motivo = new Motivo();
		motivoSelected = new Motivo();
		enfermedad = new Enfermedad();
		enfermedadSelected = new Enfermedad();
		antecedente = new Antecedente();
		antecedenteSelected = new Antecedente();
		// historiaClinica = historiaClinicaDao.obtenerHistoriaMedica(1);
		historiaMedicaSelected = new HistoriaClinica();
		selectedMedico = new Personal();
		selectedPaciente = new Paciente();
		// listaDetalleAntecedentes = detalleAntecedenteDao
		// .obtenerPorHM(historiaClinica);
		// listaAntecedentesDualSource = new ArrayList<String>();
		// listaAntecedentesDualTarget = new ArrayList<String>();
		// listaAntecedentesDual = new DualListModel<>(
		// listaAntecedentesDualSource, listaAntecedentesDualTarget);
		// listaEspecialidades =
		// especialidadDao.obtenerPorSucursal(getSucursal());
		// listaAntecedentes = antecedenteDao.obtenerPorSucursal(getSucursal());
		listaHistoriaMedica = historiaClinicaDao.obtenerPorCompania(sucursal
				.getCompania());
		// listaMotivos = motivoDao.obtenerPorHistoriaClinica(historiaClinica);
		// listaEnfermedades = enfermedadDao
		// .obtenerPorHistoriaClinica(historiaClinica);
		// currentPage =
		// "/pages/historias_clinicas/registro_historias_clinicas/list.xhtml";
		obtenerVariableExternos();
	}

	private void obtenerVariableExternos() {
		try {
			System.out.println("Ingreso a obtenerVariableExternos");
			request = (HttpServletRequest) facesContext.getExternalContext()
					.getRequest();
			List<Personal> listPersonals = new ArrayList<Personal>();
			listPersonals = personalDao.obtenerPorUsuario(sessionMain
					.getUsuarioLogin());
			if (listPersonals.size() == 1) {
				selectedMedico = listPersonals.get(0);
			}

			String idPaciente = request.getParameter("idPaciente");
			System.out.println("Paciente : " + idPaciente);
			if (idPaciente != null) {
				selectedPaciente = pacienteDao.obtenerIndividuo(new Integer(
						idPaciente));
				actionNuevo();
				Indice indice = indiceDao.obtenerIndice(sucursal.getCompania());
				Integer codigo = indice.getHistoriaClinica() + 1;
				System.out.println("Codigo : " + codigo);
				indice.setHistoriaClinica(codigo);
				indiceDao.modificar(indice);
				historiaClinica.setCorrelativo(codigo);
				historiaClinica.setCodigo("HC00000" + codigo);
				historiaClinica.setLugar(sucursal.getNombre());
				if (selectedPaciente.getFechaNacimiento() != null) {
					selectedPaciente.setEdad(Time.calcularEdad(selectedPaciente
							.getFechaNacimiento()));
				}
				currentPage = "/pages/historias_clinicas/registro_historias_clinicas/edit.xhtml";
			} else {
				currentPage = "/pages/historias_clinicas/registro_historias_clinicas/list.xhtml";
			}
		} catch (Exception e) {
			System.err.println("Error en obtenerVariableExternos : "
					+ e.getLocalizedMessage());
		}
	}

	public void resetearFitrosTabla(String id) {
		DataTable table = (DataTable) FacesContext.getCurrentInstance()
				.getViewRoot().findComponent(id);
		table.setSelection(null);
		table.reset();
	}

	public void onRowSelectMotivos() {
		motivoSeleccionado = true;
		motivo = motivoSelected;
		FacesContext.getCurrentInstance().renderResponse();
		resetearFitrosTabla("form001:singleDT");
		FacesUtil.updateComponent("formDlg001");
		FacesUtil.showDialog("dlgMotivo");
	}

	public void onRowSelectEnfermedades() {
		enfermedadSeleccionado = true;
		enfermedad = enfermedadSelected;
		FacesContext.getCurrentInstance().renderResponse();
		resetearFitrosTabla("form001:singleDT");
		FacesUtil.updateComponent("formDlg001");
		FacesUtil.showDialog("dlgEnfermedad");
	}

	public void onRowSelectAntecedentes() {
		antecedenteSeleccionado = true;
		antecedente = antecedenteSelected;
		FacesContext.getCurrentInstance().renderResponse();
		resetearFitrosTabla("form001:singleDT");
		FacesUtil.updateComponent("formDlg001");
		FacesUtil.showDialog("dlgAntecedente");
	}

	public void onSelectEspecialidad() {
		listaAntecedentesDualSource = new ArrayList<String>();
		listaAntecedentesDualTarget = new ArrayList<String>();
		listaAntecedentesDual = new DualListModel<>(
				listaAntecedentesDualSource, listaAntecedentesDualTarget);
		Especialidad auxiliar = especialidadDao
				.obtenerEspecialidad(especialidad.getNombre());
		System.out.println("especialidadSelected" + auxiliar.getNombre());
		List<DetalleAntecedenteEspecialidad> detalle = detalleAntecedenteEspecialidadDao
				.obtenerPorEspecialidad(auxiliar);
		System.out.println("lista de detalleantecedenteespecialidad = "
				+ detalle.size());
		for (DetalleAntecedenteEspecialidad detalleAntecedenteEspecialidad : detalle) {
			listaAntecedentesDualSource.add(detalleAntecedenteEspecialidad
					.getAntecedente().getDescripcion());
		}
	}

	public boolean verificarRestriccion(String dato) {
		return dato.toLowerCase().contains("receta");
	}

	public boolean verificarHoraActualizar(Date fecha) {
		Date plazo = Time.sumHour(fecha, 24);
		return new Date().before(plazo);
	}

	public void registrarMotivo() {
		if (motivoSeleccionado) {// actualizar
			try {
				if (verificarHoraActualizar(motivo.getFechaRegistro())) {
					if (verificarRestriccion(motivo.getDescripcion())) {
						FacesUtil
								.errorMessage("En el registro de motivos no puede ingresar la palabra receta.");
					} else {
						if (motivo.getDescripcion().trim().isEmpty()
								|| motivo.getEstado().trim().isEmpty()
								|| getSucursal() == null
								|| getSucursal().getCompania() == null) {
							FacesUtil.infoMessage("VALIDACION",
									"No puede haber campos vacíos");
						} else {
							motivo.setFechaModificacion(new Date());
							motivo.setUsuarioRegistro(getUsuario().getLogin());
							motivoDao.modificar(motivo);
						}
					}
					initNew();
				} else {
					FacesUtil
							.errorMessage("Ya se cumplieron 24 horas a partir del registro del motivo, no puede editar el motivo.");
				}
			} catch (Exception e) {
				System.out.println("Error en modificacion de motivo: "
						+ e.getMessage());
			}
		} else {// registrar
			try {
				if (verificarRestriccion(motivo.getDescripcion())) {
					FacesUtil
							.errorMessage("En el registro de motivos no puede ingresar la palabra receta.");
				} else {
					if (motivo.getDescripcion().trim().isEmpty()
							|| getSucursal() == null
							|| getSucursal().getCompania() == null) {
						FacesUtil.infoMessage("VALIDACION",
								"No puede haber campos vacíos");
						return;
					} else {
						motivo.setEstado("AC");
						motivo.setSucursal(getSucursal());
						motivo.setCompania(getSucursal().getCompania());
						motivo.setFechaRegistro(new Date());
						motivo.setFechaModificacion(motivo.getFechaRegistro());
						motivo.setUsuarioRegistro(getUsuario().getLogin());
						motivo.setHistoriaClinica(historiaClinica);
						motivoDao.registrar(motivo);
						initNew();
					}
				}
			} catch (Exception e) {
				System.out.println("Error en registro de motivo: "
						+ e.getMessage());
			}
		}
	}

	public void registrarEnfermedad() {
		if (enfermedadSeleccionado) {// actualizar
			if (verificarHoraActualizar(enfermedad.getFechaRegistro())) {
				try {
					if (enfermedad.getNombre().trim().isEmpty()
							|| enfermedad.getEstado().trim().isEmpty()
							|| getSucursal() == null
							|| getSucursal().getCompania() == null) {
						FacesUtil.infoMessage("VALIDACION",
								"No puede haber campos vacíos");
					} else {
						enfermedad.setFechaModificacion(new Date());
						enfermedad.setUsuarioRegistro(getUsuario().getLogin());
						enfermedadDao.modificar(enfermedad);
					}
				} catch (Exception e) {
					System.out.println("Error en modificacion de enfermedad: "
							+ e.getMessage());
				}
			} else {
				FacesUtil
						.errorMessage("Ya se cumplieron 24 horas a partir del registro del motivo, no puede editar el motivo.");
			}
			initNew();
		} else {// registrar
			try {
				if (enfermedad.getNombre().trim().isEmpty()
						|| getSucursal() == null
						|| getSucursal().getCompania() == null) {
					FacesUtil.infoMessage("VALIDACION",
							"No puede haber campos vacíos");
				} else {
					enfermedad.setEstado("AC");
					enfermedad.setSucursal(getSucursal());
					enfermedad.setCompania(getSucursal().getCompania());
					enfermedad.setFechaRegistro(new Date());
					enfermedad.setFechaModificacion(enfermedad
							.getFechaRegistro());
					enfermedad.setUsuarioRegistro(getUsuario().getLogin());
					enfermedad.setHistoriaClinica(historiaClinica);
					enfermedadDao.registrar(enfermedad);
				}
			} catch (Exception e) {
				System.out.println("Error en registro de enfermedad: "
						+ e.getMessage());
			}
			initNew();
		}
	}

	public void registrarAntecedente() {
		List<String> target = listaAntecedentesDual.getTarget();
		System.out.println("lista de antecedentes a meter en source = "
				+ listaAntecedentesDualSource.size());
		System.out.println("lista de antecedentes a meter = " + target.size());
		for (String antecedente : target) {
			Antecedente auxiliar = new Antecedente();
			auxiliar = antecedenteDao.obtenerAntecedente(antecedente);
			DetalleAntecedente detalleAntecedente = new DetalleAntecedente();
			detalleAntecedente.setAntecedente(auxiliar);
			detalleAntecedente.setHistoriaClinica(historiaClinica);
			detalleAntecedente.setEstado("AC");
			detalleAntecedente.setFechaRegistro(new Date());
			detalleAntecedente.setFechaModificacion(detalleAntecedente
					.getFechaRegistro());
			detalleAntecedente.setSucursal(getSucursal());
			detalleAntecedente.setCompania(getSucursal().getCompania());
			detalleAntecedente.setUsuarioRegistro(getUsuario().getLogin());
			listaDetalleAntecedentes.add(detalleAntecedente);
		}
		if (detalleAntecedenteDao.registrar(listaDetalleAntecedentes)) {
			FacesUtil.infoMessage("REGISTRO", "Registro exitoso");
		} else {
			FacesUtil.infoMessage("REGISTRO", "Error en el registro");
		}
		initNew();
	}

	public void registrarHistoriaClinica() {
		try {
			System.out.println("Ingreso a registrar");

			if (historiaClinica.getDescripcion().trim().isEmpty()
					|| historiaClinica.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			}
			if (selectedMedico.getId() == 0 || selectedMedico == null) {
				FacesUtil.infoMessage("VALIDACION", "Seleccione el Medico");
				return;
			}
			if (selectedPaciente.getId() == 0 || selectedPaciente == null) {
				FacesUtil.infoMessage("VALIDACION", "Seleccione el Paciente");
				return;
			}

			historiaClinica.setMedico(selectedMedico);
			historiaClinica.setEspecialidad(selectedMedico.obtenerEspecialidadActiva()
					.getNombre());
			historiaClinica.setPaciente(selectedPaciente);
			historiaClinica.setCompania(sucursal.getCompania());
			historiaClinica.setSucursal(sucursal);
			historiaClinica.setFechaRegistro(new Date());
			historiaClinica.setFechaModificacion(historiaClinica
					.getFechaRegistro());
			historiaClinica.setUsuarioRegistro(usuario.getLogin());

			HistoriaClinica r = historiaClinicaDao.registrar(historiaClinica);
			if (r != null) {
				redireccionar();
				defaultValues();
			} else {
				return;
			}
		} catch (Exception e) {
			System.out.println("Error en registro de historiaMedica: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			System.out.println("Ingreso a actualizar");

			if (historiaClinica.getDescripcion().trim().isEmpty()
					|| historiaClinica.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			}
			if (selectedMedico.getId() == 0 || selectedMedico == null) {
				FacesUtil.infoMessage("VALIDACION", "Seleccione el Medico");
				return;
			}
			if (selectedPaciente.getId() == 0 || selectedPaciente == null) {
				FacesUtil.infoMessage("VALIDACION", "Seleccione el Paciente");
				return;
			}
			historiaClinica.setCompania(sucursal.getCompania());
			historiaClinica.setSucursal(sucursal);
			historiaClinica.setFechaModificacion(new Date());
			historiaClinica.setUsuarioRegistro(usuario.getLogin());
			HistoriaClinica r = historiaClinicaDao.modificar(historiaClinica);
			if (r != null) {
				defaultValues();
			} else {
				return;
			}

		} catch (Exception e) {
			System.out.println("Error en modificacion de historiaMedica: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			if (historiaClinicaDao.eliminar(historiaClinica)) {
				FacesUtil.infoMessage("HistoriaMedica Eliminado",
						historiaClinica.toString());
				defaultValues();
			} else {
				FacesUtil.errorMessage("Error al eliminar");
				defaultValues();
			}
		} catch (Exception e) {
			System.out.println("Error en eliminacion de historiaMedica: "
					+ e.getMessage());
		}
	}

	public void redireccionar() {
		try {
			System.out.println("Ingreso a redireccionar");
			String navigateString;
			selectedPaciente = pacienteDao.obtenerIndividuo(selectedPaciente
					.getId());
			if (selectedPaciente.getCodigoHc() == null
					|| selectedPaciente.getCodigoHc().trim().length() == 0) {
				// CREACION DE HOJA MEDICA
				navigateString = "/pages/historias_clinicas/registro_historias_clinicas/index.xhtml?idPaciente="
						+ selectedPaciente.getId();
			} else {
				// CREACION DE HISTORIA CLINICA
				navigateString = "/pages/historias_clinicas/registro_detalle_historias_clinicas/historia_medica.xhtml?idPaciente="
						+ selectedPaciente.getId();
			}
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

	public void onTabChange(TabChangeEvent event) {
		tabIndex = 0;
		FacesUtil.infoMessage("Información de tab", "Active Tab: "
				+ event.getTab().getId());
	}

	public void onRowSelect(SelectEvent event) {
		historiaClinica = historiaMedicaSelected;
		crear = false;
		seleccionado = true;
		registrar = false;
		selectedMedico = historiaClinica.getMedico();
		selectedPaciente = historiaClinica.getPaciente();
		currentPage = "/pages/historias_clinicas/registro_historias_clinicas/edit.xhtml";
	}

	public void actionNuevo() {
		System.out.println("Ingreso a actionNuevo...");
		crear = false;
		seleccionado = false;
		registrar = true;
		historiaClinica = new HistoriaClinica();
		historiaClinica.setFechaRegistro(new Date());
		currentPage = "/pages/historias_clinicas/registro_historias_clinicas/edit.xhtml";
	}

	public void actionCancelar() {
		crear = true;
		seleccionado = false;
		registrar = false;
		historiaClinica = new HistoriaClinica();
		currentPage = "/pages/historias_clinicas/registro_historias_clinicas/list.xhtml";
	}

	public void actionModificar(Integer id) {
		crear = false;
		seleccionado = true;
		registrar = false;
		currentPage = "/pages/historias_clinicas/registro_historias_clinicas/edit.xhtml";
	}

	// ONCOMPLETETEXT PACIENTE
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
				selectedPaciente = i;
				System.out.println("Paciente : " + selectedPaciente.getNombre()
						+ " " + selectedPaciente.getApellidoPaterno() + " "
						+ selectedPaciente.getApellidoMaterno());
				Integer codigo = historiaClinicaDao.findActiveCodigoMax(
						selectedPaciente, sucursal.getCompania());
				System.out.println("Codigo : " + codigo);
				historiaClinica.setCorrelativo(codigo);
				historiaClinica.setCodigo("HC00000" + codigo);
				return;
			}
		}
	}

	// ONCOMPLETETEXT PACIENTE
	public List<Personal> completeMedicos(String query) {
		String upperQuery = query.toUpperCase();
		listaMedicos = personalDao.obtenerPorCompania(upperQuery, sucursal);
		return listaMedicos;
	}

	public void onRowSelectMedicosClick(SelectEvent event) {
		System.out.println("Ingreso a onRowSelectPacienteClick");
		String nombre = event.getObject().toString();
		for (Personal i : listaMedicos) {
			if (i.getNombre().equals(nombre)) {
				selectedMedico = i;
				System.out.println("Paciente : " + selectedMedico.getNombre()
						+ " " + selectedMedico.getApellidoPaterno() + " "
						+ selectedMedico.getApellidoMaterno());
				return;
			}
		}
	}

	public void actionNuevoMotivo() {
		motivo = new Motivo();
		FacesUtil.updateComponent("formDlg001");
		FacesUtil.showDialog("dlgMotivo");
	}

	public void actionNuevoEnfermedad() {
		enfermedad = new Enfermedad();
		FacesUtil.updateComponent("formDlg001");
		FacesUtil.showDialog("dlgEnfermedad");
	}

	public void actionNuevoAntecedente() {
		antecedente = new Antecedente();
		FacesUtil.updateComponent("formDlg001");
		FacesUtil.showDialog("dlgAntecedente");
	}

	public void onSelectPagina() {
		currentPage1 = "/pages/historias_clinicas/registro_detalle_historias_clinicas/"
				+ pagina + ".xhtml";
	}

	public boolean isSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

	/**
		 * 
		 */

	public HistoriaClinica getHistoriaMedica() {
		return historiaClinica;
	}

	public HistoriaClinica getHistoriaMedicaSelected() {
		return historiaMedicaSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<HistoriaClinica> getListaHistoriaMedica() {
		return listaHistoriaMedica;
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

	public void setHistoriaMedica(HistoriaClinica historiaClinica) {
		this.historiaClinica = historiaClinica;
	}

	public void setHistoriaMedicaSelected(HistoriaClinica historiaMedicaSelected) {
		this.historiaMedicaSelected = historiaMedicaSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaHistoriaMedica(List<HistoriaClinica> listaHistoriaMedica) {
		this.listaHistoriaMedica = listaHistoriaMedica;
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
	 * @return the listaHistoriaMedicas
	 */
	public List<HistoriaClinica> getListaHistoriaMedicas() {
		return listaHistoriaMedicas;
	}

	/**
	 * @param listaHistoriaMedicas
	 *            the listaHistoriaMedicas to set
	 */
	public void setListaHistoriaMedicas(
			List<HistoriaClinica> listaHistoriaMedicas) {
		this.listaHistoriaMedicas = listaHistoriaMedicas;
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public int getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
	}

	public Motivo getMotivo() {
		return motivo;
	}

	public void setMotivo(Motivo motivo) {
		this.motivo = motivo;
	}

	public String getPagina() {
		return pagina;
	}

	public void setPagina(String pagina) {
		this.pagina = pagina;
	}

	public HistoriaClinica getHistoriaClinica() {
		return historiaClinica;
	}

	public void setHistoriaClinica(HistoriaClinica historiaClinica) {
		this.historiaClinica = historiaClinica;
	}

	public List<Motivo> getListaMotivos() {
		return listaMotivos;
	}

	public void setListaMotivos(List<Motivo> listaMotivos) {
		this.listaMotivos = listaMotivos;
	}

	public Motivo getMotivoSelected() {
		return motivoSelected;
	}

	public void setMotivoSelected(Motivo motivoSelected) {
		this.motivoSelected = motivoSelected;
	}

	public boolean isMotivoSeleccionado() {
		return motivoSeleccionado;
	}

	public void setMotivoSeleccionado(boolean motivoSeleccionado) {
		this.motivoSeleccionado = motivoSeleccionado;
	}

	public Enfermedad getEnfermedad() {
		return enfermedad;
	}

	public void setEnfermedad(Enfermedad enfermedad) {
		this.enfermedad = enfermedad;
	}

	public Enfermedad getEnfermedadSelected() {
		return enfermedadSelected;
	}

	public void setEnfermedadSelected(Enfermedad enfermedadSelected) {
		this.enfermedadSelected = enfermedadSelected;
	}

	public List<Enfermedad> getListaEnfermedades() {
		return listaEnfermedades;
	}

	public void setListaEnfermedades(List<Enfermedad> listaEnfermedades) {
		this.listaEnfermedades = listaEnfermedades;
	}

	public boolean isEnfermedadSeleccionado() {
		return enfermedadSeleccionado;
	}

	public void setEnfermedadSeleccionado(boolean enfermedadSeleccionado) {
		this.enfermedadSeleccionado = enfermedadSeleccionado;
	}

	public Antecedente getAntecedente() {
		return antecedente;
	}

	public void setAntecedente(Antecedente antecedente) {
		this.antecedente = antecedente;
	}

	public Antecedente getAntecedenteSelected() {
		return antecedenteSelected;
	}

	public void setAntecedenteSelected(Antecedente antecedenteSelected) {
		this.antecedenteSelected = antecedenteSelected;
	}

	public List<Antecedente> getListaAntecedentes() {
		return listaAntecedentes;
	}

	public void setListaAntecedentes(List<Antecedente> listaAntecedentes) {
		this.listaAntecedentes = listaAntecedentes;
	}

	public boolean isAntecedenteSeleccionado() {
		return antecedenteSeleccionado;
	}

	public void setAntecedenteSeleccionado(boolean antecedenteSeleccionado) {
		this.antecedenteSeleccionado = antecedenteSeleccionado;
	}

	public Especialidad getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(Especialidad especialidad) {
		this.especialidad = especialidad;
	}

	public List<Especialidad> getListaEspecialidades() {
		return listaEspecialidades;
	}

	public void setListaEspecialidades(List<Especialidad> listaEspecialidades) {
		this.listaEspecialidades = listaEspecialidades;
	}

	public DualListModel<String> getListaAntecedentesDual() {
		return listaAntecedentesDual;
	}

	public void setListaAntecedentesDual(
			DualListModel<String> listaAntecedentesDual) {
		this.listaAntecedentesDual = listaAntecedentesDual;
	}

	public List<String> getListaAntecedentesDualSource() {
		return listaAntecedentesDualSource;
	}

	public void setListaAntecedentesDualSource(
			List<String> listaAntecedentesDualSource) {
		this.listaAntecedentesDualSource = listaAntecedentesDualSource;
	}

	public List<String> getListaAntecedentesDualTarget() {
		return listaAntecedentesDualTarget;
	}

	public void setListaAntecedentesDualTarget(
			List<String> listaAntecedentesDualTarget) {
		this.listaAntecedentesDualTarget = listaAntecedentesDualTarget;
	}

	public List<DetalleAntecedente> getListaDetalleAntecedentes() {
		return listaDetalleAntecedentes;
	}

	public void setListaDetalleAntecedentes(
			List<DetalleAntecedente> listaDetalleAntecedentes) {
		this.listaDetalleAntecedentes = listaDetalleAntecedentes;
	}

	public DetalleAntecedente getDetalleAntecedente() {
		return detalleAntecedente;
	}

	public void setDetalleAntecedente(DetalleAntecedente detalleAntecedente) {
		this.detalleAntecedente = detalleAntecedente;
	}

	public DetalleAntecedente getDetalleAntecedenteSelected() {
		return detalleAntecedenteSelected;
	}

	public void setDetalleAntecedenteSelected(
			DetalleAntecedente detalleAntecedenteSelected) {
		this.detalleAntecedenteSelected = detalleAntecedenteSelected;
	}

	/**
	 * @return the currentPage1
	 */
	public String getCurrentPage1() {
		return currentPage1;
	}

	/**
	 * @param currentPage1
	 *            the currentPage1 to set
	 */
	public void setCurrentPage1(String currentPage1) {
		this.currentPage1 = currentPage1;
	}
}
