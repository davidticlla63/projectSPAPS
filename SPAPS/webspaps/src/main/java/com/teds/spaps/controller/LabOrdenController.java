/**
 * 
 */
package com.teds.spaps.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DualListModel;

import com.teds.spaps.interfaces.dao.IConsultaDao;
import com.teds.spaps.interfaces.dao.ILabExamenDao;
import com.teds.spaps.interfaces.dao.ILabGrupoExamenDao;
import com.teds.spaps.interfaces.dao.ILabOrdenDao;
import com.teds.spaps.interfaces.dao.ILabProgramacionOrdenDao;
import com.teds.spaps.interfaces.dao.IPacienteDao;
import com.teds.spaps.interfaces.dao.IParamSistemaIndicesDao;
import com.teds.spaps.interfaces.dao.IPersonalDao;
import com.teds.spaps.interfaces.dao.ISucursalDao;
import com.teds.spaps.model.LabExamen;
import com.teds.spaps.model.LabGrupoExamen;
import com.teds.spaps.model.LabOrden;
import com.teds.spaps.model.LabOrdenDetalle;
import com.teds.spaps.model.LabProgramacionOrden;
import com.teds.spaps.model.Paciente;
import com.teds.spaps.model.ParamSistemaIndices;
import com.teds.spaps.model.Personal;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.structore.EDOrdenLaboratorio;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;
import com.teds.spaps.util.Time;

/**
 * @author david
 *
 */
@ManagedBean(name = "labOrdenController")
@ViewScoped
public class LabOrdenController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -125210622622704977L;

	private @Inject FacesContext facesContext;

	/******* DAO **********/
	private @Inject ILabOrdenDao labOrdenDao;
	private @Inject SessionMain sessionMain;
	private @Inject ILabGrupoExamenDao grupoExamenDao;
	private @Inject IPacienteDao pacienteDao;
	private @Inject IPersonalDao personalDao;
	private @Inject ISucursalDao sucursalDao;
	private @Inject ILabExamenDao examenDao;
	private @Inject IParamSistemaIndicesDao sistemaIndicesDao;
	private @Inject IConsultaDao consultaDao;
	private @Inject ILabProgramacionOrdenDao programacionOrdenDao;

	/******* OBJECT **********/
	private LabOrden labOrden;
	private LabOrden labOrdenSelected;
	private Sucursal sucursal;
	private Paciente selectedPaciente;
	private Personal selectedMedico;
	private Personal selectedMedicoProgamacion;
	private LabGrupoExamen grupoExamen;
	private LabProgramacionOrden labProgramacionOrden;

	/******* LIST **********/
	private List<LabOrden> listaLabOrden;
	private List<LabGrupoExamen> listGrupoExamens = new ArrayList<LabGrupoExamen>();
	private List<LabExamen> listExamens = new ArrayList<LabExamen>();
	private List<LabOrdenDetalle> listOrdenDetalles = new ArrayList<LabOrdenDetalle>();
	public static List<Paciente> listaPacientes = new ArrayList<Paciente>();
	public static List<Personal> listaPersonal = new ArrayList<Personal>();
	private List<Sucursal> listaSucursal = new ArrayList<Sucursal>();
	private List<EDOrdenLaboratorio> listEdOrdenLaboratorios = new ArrayList<EDOrdenLaboratorio>();
	private DualListModel<LabExamen> listaExamenModel;
	private List<LabExamen> listaExamenDualSource = new ArrayList<LabExamen>();
	private List<LabExamen> listaExamenDualTarget = new ArrayList<LabExamen>();

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;
	private boolean seleccionado = false;

	private boolean aprovado = false;

	// BUSQUEDAS
	private String busqueda;

	private Date fechaInicio = new Date();

	private Date fechaFin = new Date();

	// URL PAGE
	private String currentPage = "/pages/laboratorio/ordenes/list.xhtml";

	/**
	 * 
	 */
	public LabOrdenController() {
	}

	@PostConstruct
	public void initNew() {
		sucursal = sessionMain.PruebaSucursal();
		System.out.println("Sucursal : " + sucursal.getDescripcion());
		System.out.println("Compañia : "
				+ sucursal.getCompania().getDescripcion());
		listaSucursal = sucursalDao.obtenerPorCompania(sucursal.getCompania());
		grupoExamen = new LabGrupoExamen();
		listGrupoExamens = grupoExamenDao.obtenerPorCompania(sucursal
				.getCompania());
		selectedMedicoProgamacion = new Personal();
		defaultValues();
		// consulta = sessionMain.getConsulta();
		// if ((consulta.equals(null) || consulta.getId() == 0)
		// && consultaDao.tieneConsultaPaciente(selectedPaciente,
		// sucursal.getCompania())) {
		// consulta = consultaDao.obtenerConsultaActualDePaciente(
		// selectedPaciente, sucursal.getCompania());
		// sessionMain.setConsulta(consulta);
		// } else {
		// FacesUtil.infoMessage("Consulta",
		// "Por favor debe crear una consulta antes");
		// }
	}

	private void defaultValues() {
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
		aprovado = false;
		seleccionado = false;
		selectedPaciente = new Paciente();
		selectedMedico = new Personal();
		labOrden = new LabOrden();
		labOrdenSelected = new LabOrden();
		listOrdenDetalles.clear();
		listaExamenDualSource = new ArrayList<LabExamen>();
		listaExamenDualTarget = new ArrayList<LabExamen>();
		listaExamenModel = new DualListModel<>(listaExamenDualSource,
				listaExamenDualTarget);
		listEdOrdenLaboratorios = examenDao.obtenerAllStructureOrden(sucursal
				.getCompania());
		listaLabOrden = labOrdenDao
				.obtenerPorCompaniaOrdenadoFechaRegistroRow50(sucursal
						.getCompania());
		consulta();
		currentPage = "/pages/laboratorio/ordenes/list.xhtml";
	}

	public void consulta() {
		listaLabOrden = labOrdenDao.obtenerProFechas(sucursal, fechaInicio,
				fechaFin);
	}

	public void cerrarConsulta() {
		// if (consulta == null || !consulta.getEstado().equals("AT")) {
		// FacesUtil.errorMessage("No tiene ninguna consulta activa.");
		// } else {
		// if (consulta.getEstado().equals("AT")) {
		// consulta.setEstado("AC");
		// consulta = consultaDao.modificar(consulta);
		// sessionMain.setConsulta(new Consulta());
		// } else {
		//
		// }
		// }
	}

	public void registrar() {
		try {
			System.out.println("Ingreso a registrar");

			if (selectedPaciente.getId().intValue() == 0
					|| selectedPaciente == null || getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION", "Seleccione Paciente");
				return;
			}

			if (labOrden.getDescripcion().trim().isEmpty()
					|| labOrden.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION", "Revise Descripcion");
				return;
			}
			if (selectedMedico.obtenerEspecialidadActiva().getId() == 0) {
				FacesUtil.infoMessage("VALIDACION", "Revise Especialidad");
				return;
			}
			if (labOrden.getTipoOrden().equals("INTERNO")) {
				if (selectedMedico.getId().intValue() == 0
						|| selectedMedico == null || getSucursal() == null
						|| getSucursal().getCompania() == null) {
					FacesUtil.infoMessage("VALIDACION",
							"Seleccione Tipo Examen");
					return;
				}
				labOrden.setMedico(selectedMedico);

				labOrden.setEspecialidad(selectedMedico
						.obtenerEspecialidadActiva().getNombre());
			} else {
				labOrden.setMedico(null);
			}
			labOrden.setIndicaciones("");
			rellenarDetalle();
			// labOrden.setConsulta(consulta);
			labOrden.setPaciente(selectedPaciente);

			labOrden.setCompania(sucursal.getCompania());
			labOrden.setSucursal(sucursal);
			labOrden.setFechaRegistro(new Date());
			labOrden.setFechaModificacion(labOrden.getFechaRegistro());
			labOrden.setUsuarioRegistro(sessionMain.getUsuarioLogin()
					.getLogin());
			labOrden.setHistoriaClinica(null);
			labOrden.setEstado("PE");
			for (LabExamen examen : listaExamenModel.getTarget()) {
				if (!existeExamen(examen, listOrdenDetalles)) {
					labOrden.setIndicaciones(labOrden.getIndicaciones()+examen.obtenerIndicaciones()+"\n");
					LabOrdenDetalle detalle = new LabOrdenDetalle();
					detalle.setDescripcion(examen.getDescripcion());
					detalle.setCompania(sucursal.getCompania());
					detalle.setSucursal(sucursal);
					detalle.setFechaRegistro(new Date());
					detalle.setParametros("");
					detalle.setUsuarioRegistro(sessionMain.getUsuarioLogin()
							.getLogin());
					detalle.setLabExamen(examen);
					listOrdenDetalles.add(detalle);
				}
			}
			if (listOrdenDetalles.size() == 0) {
				FacesUtil.infoMessage("VALIDACION",
						"No ha seleccionado ningun examen");
				return;
			}
			LabOrden r = labOrdenDao.registrar(labOrden, listOrdenDetalles);
			if (r != null) {
				armarUrl();
				defaultValues();
			} else {
				defaultValues();
			}
		} catch (Exception e) {
			System.out.println("Error en registro de labOrden: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			System.out.println("Ingreso a actualizar");
			if (selectedPaciente.getId().intValue() == 0
					|| selectedPaciente == null || getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION", "Seleccione Paciente");
				return;
			}

			if (labOrden.getDescripcion().trim().isEmpty()
					|| labOrden.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION", "Revise Descripcion");
				return;
			}
			if (selectedMedico.obtenerEspecialidadActiva().getId() == 0) {
				FacesUtil.infoMessage("VALIDACION", "Revise Especialidad");
				return;
			}
			if (labOrden.getTipoOrden().equals("INTERNO")) {
				if (selectedMedico.getId().intValue() == 0
						|| selectedMedico == null || getSucursal() == null
						|| getSucursal().getCompania() == null) {
					FacesUtil.infoMessage("VALIDACION",
							"Seleccione Tipo Examen");
					return;
				}
				labOrden.setMedico(selectedMedico);

				labOrden.setEspecialidad(selectedMedico
						.obtenerEspecialidadActiva().getNombre());
			} else {
				labOrden.setMedico(null);
			}
			labOrden.setIndicaciones("");
			rellenarDetalle();
			// labOrden.setConsulta(consulta);
			labOrden.setPaciente(selectedPaciente);
			labOrden.setCompania(sucursal.getCompania());
			labOrden.setSucursal(sucursal);
			labOrden.setFechaModificacion(labOrden.getFechaRegistro());
			labOrden.setUsuarioRegistro(sessionMain.getUsuarioLogin()
					.getLogin());
			labOrden.setHistoriaClinica(null);
			for (LabExamen examen : listaExamenModel.getTarget()) {
				if (!existeExamen(examen, listOrdenDetalles)) {
					labOrden.setIndicaciones(labOrden.getIndicaciones()+examen.obtenerIndicaciones()+"\n");
					LabOrdenDetalle detalle = new LabOrdenDetalle();
					detalle.setDescripcion(examen.getDescripcion());
					detalle.setCompania(sucursal.getCompania());
					detalle.setSucursal(sucursal);
					detalle.setFechaRegistro(new Date());
					detalle.setParametros("");
					detalle.setUsuarioRegistro(sessionMain.getUsuarioLogin()
							.getLogin());
					detalle.setLabExamen(examen);
					listOrdenDetalles.add(detalle);
				}
			}
			if (listOrdenDetalles.size() == 0) {
				FacesUtil.infoMessage("VALIDACION",
						"No ha seleccionado ningun examen");
				return;
			}
			LabOrden r = labOrdenDao.modificar(labOrden, listOrdenDetalles);
			if (r != null) {
				defaultValues();
			} else {
				defaultValues();
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de labOrden: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			if (labOrdenDao.eliminar(labOrden)) {
				FacesUtil
						.infoMessage("LabOrden Eliminado", labOrden.toString());
				defaultValues();
			} else {
				FacesUtil.errorMessage("Error al eliminar");
				defaultValues();
			}
		} catch (Exception e) {
			System.out.println("Error en eliminacion de labOrden: "
					+ e.getMessage());
		}
	}
	
	

	// ONCOMPLETETEXT MEDICO
	public List<Personal> completeMedicoProgramadocion(String query) {
		String upperQuery = query.toUpperCase();
		listaPersonal = personalDao.obtenerPorSucursal(upperQuery, sucursal);
		return listaPersonal;
	}

	public void onRowSelectMedicoProgramadocionClick(SelectEvent event) {
		System.out.println("Ingreso a onRowSelectMedicoProgramadocionClick");
		String nombre[] = event.getObject().toString().split(" ");
		System.out.println(nombre.toString());
		for (Personal i : listaPersonal) {
			if (i.getNombre().equals(nombre[0])
					|| i.getApellidoPaterno().equals(nombre[1])
					|| i.getApellidoMaterno().equals(nombre[2])) {
				selectedMedicoProgamacion = i;
				System.out.println("Medico : "
						+ selectedMedicoProgamacion.getNombre() + " "
						+ selectedMedicoProgamacion.getApellidoPaterno() + " "
						+ selectedMedicoProgamacion.getApellidoMaterno());
				return;
			}
		}
	}

	public void crearProgramacion() {
		labProgramacionOrden = new LabProgramacionOrden();
		labProgramacionOrden.setOrden(labOrden);
		selectedMedicoProgamacion = new Personal();
		FacesUtil.updateComponent("formDlg001");
		FacesUtil.showDialog("dlgDialog1");
	}

	public void programar() {
		try {
			if (selectedMedicoProgamacion.getId().intValue() == 0
					|| selectedMedicoProgamacion == null) {
				FacesUtil
						.infoMessage("VALIDACION", "No ha seleccionado Medico");
				return;
			}
			labProgramacionOrden.setEspecialidad(selectedMedicoProgamacion
					.obtenerEspecialidadActiva().getNombre());
			labProgramacionOrden.setMedico(selectedMedicoProgamacion);
			labProgramacionOrden.setSucursal(sucursal);
			labProgramacionOrden.setFechaRegistro(new Date());
			labProgramacionOrden.setTipoOrden("LABORATORIO");
			labProgramacionOrden.setUsuarioRegistro(sessionMain
					.getUsuarioLogin().getLogin());
			LabProgramacionOrden r = programacionOrdenDao
					.registrar(labProgramacionOrden);
			if (r != null) {
				defaultValues();
				FacesUtil.hideDialog("dlgDialog1");
			} else {
				defaultValues();
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private boolean existeExamen(LabExamen examen,
			List<LabOrdenDetalle> listOrdenDetalle) {
		for (LabOrdenDetalle labOrdenDetalle : listOrdenDetalle) {
			if (labOrdenDetalle.getLabExamen().getId().intValue() == examen
					.getId().intValue()) {
				return true;
			}
		}
		return false;
	}

	private void rellenarDetalle() {
		listOrdenDetalles.clear();
		for (EDOrdenLaboratorio edOrdenLaboratorio : listEdOrdenLaboratorios) {
			for (LabExamen labExamen : edOrdenLaboratorio.getListaExamen()) {
				if (labExamen.isChecker()) {
					labOrden.setIndicaciones(labOrden.getIndicaciones()+labExamen.obtenerIndicaciones()+"\n");
					LabOrdenDetalle detalle = new LabOrdenDetalle();
					detalle.setDescripcion(labExamen.getDescripcion());
					detalle.setCompania(sucursal.getCompania());
					detalle.setSucursal(sucursal);
					detalle.setFechaRegistro(new Date());
					detalle.setParametros("");
					detalle.setUsuarioRegistro(sessionMain.getUsuarioLogin()
							.getLogin());
					detalle.setLabExamen(labExamen);
					listOrdenDetalles.add(detalle);
				}
			}
		}
	}

	private void activarDetalle() {
		for (LabOrdenDetalle ordenDetalle : listOrdenDetalles) {
			boolean encontrado = false;
			for (EDOrdenLaboratorio edOrdenLaboratorio : listEdOrdenLaboratorios) {
				for (LabExamen labExamen : edOrdenLaboratorio.getListaExamen()) {
					if (labExamen.getId().intValue() == ordenDetalle
							.getLabExamen().getId().intValue()) {
						labExamen.setChecker(true);
						encontrado = true;
					}
				}
			}
			if (!encontrado) {
				listaExamenModel.getTarget().add(ordenDetalle.getLabExamen());
				encontrado = false;
			}
		}
		listaExamenDualTarget = listaExamenModel.getTarget();
	}

	public void onRowSelect(SelectEvent event) {
		labOrden = labOrdenSelected;
		crear = false;
		seleccionado = true;
		registrar = false;
		if (labOrden.getTipoOrden().equals("INTERNO")) {
			selectedMedico = labOrden.getMedico();
		}
		if (labOrden.getEstado().equals("AP")) {
			aprovado = true;
		}

		selectedPaciente = labOrden.getPaciente();
		listOrdenDetalles = labOrden.getListOrdenDetalle();
		activarDetalle();
		// listOrdenDetalles.clear();
		// listOrdenDetalles= ordenesDetalleDao.obtenerPorExamen(labOrden);
		currentPage = "/pages/laboratorio/ordenes/edit.xhtml";
	}

	public void actionNuevo() {
		System.out.println("Ingreso a actionNuevo...");
		crear = false;
		seleccionado = false;
		registrar = true;
		labOrden = new LabOrden();
		labOrden.setFechaRegistro(new Date());
		selectedMedico = new Personal();
		selectedPaciente = new Paciente();
		listaExamenDualSource = new ArrayList<LabExamen>();
		listaExamenDualTarget = new ArrayList<LabExamen>();
		listaExamenModel = new DualListModel<>(listaExamenDualSource,
				listaExamenDualTarget);
		ParamSistemaIndices sistemaIndices = sistemaIndicesDao
				.obtenerPorCompania("OL", sucursal.getCompania());
		labOrden.setCodigo(sistemaIndices.getPrefijo()
				+ Time.rellendarCeros(sistemaIndices.getNumeroDigitos(),
						sistemaIndices.getCorrelativo()));
		listEdOrdenLaboratorios = examenDao.obtenerAllStructureOrden(sucursal
				.getCompania());

		currentPage = "/pages/laboratorio/ordenes/edit.xhtml";
	}

	public void actionCancelar() {
		crear = true;
		seleccionado = false;
		registrar = false;
		labOrden = new LabOrden();
		currentPage = "/pages/laboratorio/ordenes/list.xhtml";
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

	public void resetearFitrosTabla(String id) {
		DataTable table = (DataTable) FacesContext.getCurrentInstance()
				.getViewRoot().findComponent(id);
		table.setSelection(null);
		table.reset();
	}

	public void onRowDeleteDI(LabOrdenDetalle labOrdenDetalle) {
		if (listOrdenDetalles.size() > 0) {
			for (int i = 0; i < listOrdenDetalles.size(); i++) {
				LabOrdenDetalle auxiliar = listOrdenDetalles.get(i);
				if (auxiliar.getDescripcion().equals(
						labOrdenDetalle.getDescripcion())) {
					listOrdenDetalles.remove(i);
				}
			}
			FacesContext.getCurrentInstance().renderResponse();
			resetearFitrosTabla("form001:formDocumentos:documentos");
		}
	}

	public void onRowEditDI(LabOrdenDetalle labOrdenDetalle) {
		if (verificarDetalle(labOrdenDetalle)) {
			FacesUtil.fatalMessage("Ya se encuentra registrado.");
			listOrdenDetalles.remove(labOrdenDetalle);
			// deleteDI(identificacionIndividuo);
		}
	}

	private boolean verificarDetalle(LabOrdenDetalle labOrdenDetalle1) {
		for (LabOrdenDetalle labOrdenDetalle : listOrdenDetalles) {
			if (labOrdenDetalle.getDescripcion().equals(
					labOrdenDetalle1.getDescripcion())
					|| labOrdenDetalle.getParametros().equals(
							labOrdenDetalle1.getParametros())) {
				return true;
			}
		}
		return false;
	}

	// ONCOMPLETETEXT PACIENTE
	public List<Paciente> completePaciente(String query) {
		String upperQuery = query.toUpperCase();
		String[] protocolo = upperQuery.split(" ");
		if (protocolo.length > 0) {
			if (protocolo.length == 1) {
				listaPacientes = pacienteDao
						.obtenerIndividuosPorNombreCompania(
								protocolo[0].trim(), "", "",
								sucursal.getCompania());
			}
			if (protocolo.length == 2) {
				listaPacientes = pacienteDao
						.obtenerIndividuosPorNombreCompania(
								protocolo[0].trim(), protocolo[1].trim(), "",
								sucursal.getCompania());
			}
			if (protocolo.length == 3) {
				listaPacientes = pacienteDao
						.obtenerIndividuosPorNombreCompania(
								protocolo[0].trim(), protocolo[1].trim(),
								protocolo[2].trim(), sucursal.getCompania());
			}
		}
		System.out.println("paciente : " + listaPacientes.size());
		return listaPacientes;
	}

	public void onRowSelectPacienteClick(SelectEvent event) {
		System.out.println("Ingreso a onRowSelectPacienteClick");
		selectedPaciente = (Paciente) event.getObject();
//		String nombre = event.getObject().toString();
//		String[] protocolo = nombre.split(" ");
//		for (Paciente i : listaPacientes) {
//			if (i.getNombre().equals(protocolo[0])
//					&& i.getApellidoPaterno().equals(protocolo[1])
//					&& i.getApellidoMaterno().equals(protocolo[2])) {
//
//				selectedPaciente = i;
//				System.out.println(selectedPaciente.getNombre() + " "
//						+ selectedPaciente.getApellidoPaterno() + " "
//						+ selectedPaciente.getApellidoMaterno());
//
//				return;
//			}
//		}
		//selectedPaciente = new Paciente();
	}

	// ONCOMPLETETEXT MEDICO
	public List<Personal> completeMedico(String query) {
		String upperQuery = query.toUpperCase();

		listaPersonal = personalDao.obtenerPorSucursal(upperQuery, sucursal);
		return listaPersonal;
	}

	public void onRowSelectMedicoClick(SelectEvent event) {
		System.out.println("Ingreso a onRowSelectMedicoClick");
		selectedMedico = (Personal) event.getObject();
//		String nombre = event.getObject().toString();
//		for (Personal i : listaPersonal) {
//			if (i.getNombre().equals(nombre)) {
//				selectedMedico = i;
//				System.out.println("Medico : " + selectedMedico.getNombre()
//						+ " " + selectedMedico.getApellidoPaterno() + " "
//						+ selectedMedico.getApellidoMaterno());
//				return;
//			}
//		}
	}

	public void onKeyUpQueryExamenes() {
		String upperQuery = busqueda.toUpperCase();
		listExamens = examenDao.obtenerPorCompania(upperQuery,
				sucursal.getCompania());
		listaExamenDualSource = new ArrayList<LabExamen>();
		listaExamenDualSource = listExamens;
		listaExamenModel = new DualListModel<LabExamen>(listaExamenDualSource,
				listaExamenDualTarget);
	}

	public void onSelectTipoExamen() {
		listaExamenDualSource = new ArrayList<LabExamen>();
		LabGrupoExamen auxiliar = grupoExamenDao
				.obtenerLabGrupoExamen(grupoExamen.getId());
		List<LabExamen> labExamen = examenDao.obtenerPorGrupoExamen(auxiliar);
		listaExamenDualSource = labExamen;
		listaExamenModel = new DualListModel<LabExamen>(listaExamenDualSource,
				listaExamenDualTarget);
	}

	// reporte

	public void armarUrl() {
		try {
			HttpServletRequest request = (HttpServletRequest) facesContext
					.getExternalContext().getRequest();
			String urlPath = request.getRequestURL().toString();
			urlPath = urlPath.substring(0, urlPath.length()
					- request.getRequestURI().length())
					+ request.getContextPath() + "/";

			String URL_SERVLET_LOGO = urlPath + "ServletImageLogo?id="
					+ labOrden.getCompania().getId() + "&type=EMPRESA";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ID_ORDEN", labOrden.getId());
			map.put("LOGO", URL_SERVLET_LOGO);
			map.put("SUBREPORT_DIR", urlPath + "resources/report/inventario/");
			map.put("REPORT_LOCALE", new Locale("en", "US"));
			String reportPath = urlPath
					+ "resources/report/laboratorio/reporteOrdenExamen.jasper";
			HttpServletRequest request1 = (HttpServletRequest) FacesContext
					.getCurrentInstance().getExternalContext().getRequest();
			request1.getSession().setAttribute("parameter", map);
			request1.getSession().setAttribute("path", reportPath);

			String url = urlPath + "ReportPdfServlet";
			sessionMain.setUrl(url);
			System.out.println("getURL() -> " + url);
			FacesUtil.updateComponent("modalVistaPrevia");
			FacesUtil.showDialog("dlgVistaPrevia");
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void onTransfer(TransferEvent event) {
		StringBuilder builder = new StringBuilder();
		for (Object item : event.getItems()) {
			builder.append(((LabExamen) item).getDescripcion())
					.append("<br />");
		}

		FacesMessage msg = new FacesMessage();
		msg.setSeverity(FacesMessage.SEVERITY_INFO);
		msg.setSummary("Items Transferred");
		msg.setDetail(builder.toString());

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onSelect(SelectEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Item Selected", event.getObject().toString()));
	}

	public void onUnselect(UnselectEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Item Unselected", event.getObject().toString()));
	}

	public void onReorder() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"List Reordered", null));
	}

	public void onRowDeleteDetalle(LabOrdenDetalle labOrdenDetalle) {
		listOrdenDetalles.remove(labOrdenDetalle);
	}

	public boolean isSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

	public LabOrden getLabOrden() {
		return labOrden;
	}

	public LabOrden getLabOrdenSelected() {
		return labOrdenSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<LabOrden> getListaLabOrden() {
		return listaLabOrden;
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

	public void setLabOrden(LabOrden labOrden) {
		this.labOrden = labOrden;
	}

	public void setLabOrdenSelected(LabOrden labOrdenSelected) {
		this.labOrdenSelected = labOrdenSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaLabOrden(List<LabOrden> listaLabOrden) {
		this.listaLabOrden = listaLabOrden;
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
	 * @return the listOrdenDetalles
	 */
	public List<LabOrdenDetalle> getListExamenDetalles() {
		return listOrdenDetalles;
	}

	/**
	 * @param listOrdenDetalles
	 *            the listOrdenDetalles to set
	 */
	public void setListExamenDetalles(List<LabOrdenDetalle> listOrdenDetalles) {
		this.listOrdenDetalles = listOrdenDetalles;
	}

	/**
	 * @return the listaPersonal
	 */
	public List<Personal> getListaPersonal() {
		return listaPersonal;
	}

	/**
	 * @param listaPersonal
	 *            the listaPersonal to set
	 */
	public void setListaPersonal(List<Personal> listaPersonal) {
		this.listaPersonal = listaPersonal;
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
	 * @return the listaSucursal
	 */
	public List<Sucursal> getListaSucursal() {
		return listaSucursal;
	}

	/**
	 * @param listaSucursal
	 *            the listaSucursal to set
	 */
	public void setListaSucursal(List<Sucursal> listaSucursal) {
		this.listaSucursal = listaSucursal;
	}

	/**
	 * @return the listEdOrdenLaboratorios
	 */
	public List<EDOrdenLaboratorio> getListEdOrdenLaboratorios() {
		return listEdOrdenLaboratorios;
	}

	/**
	 * @param listEdOrdenLaboratorios
	 *            the listEdOrdenLaboratorios to set
	 */
	public void setListEdOrdenLaboratorios(
			List<EDOrdenLaboratorio> listEdOrdenLaboratorios) {
		this.listEdOrdenLaboratorios = listEdOrdenLaboratorios;
	}

	/**
	 * @return the listOrdenDetalles
	 */
	public List<LabOrdenDetalle> getListOrdenDetalles() {
		return listOrdenDetalles;
	}

	/**
	 * @param listOrdenDetalles
	 *            the listOrdenDetalles to set
	 */
	public void setListOrdenDetalles(List<LabOrdenDetalle> listOrdenDetalles) {
		this.listOrdenDetalles = listOrdenDetalles;
	}

	/**
	 * @return the busqueda
	 */
	public String getBusqueda() {
		return busqueda;
	}

	/**
	 * @param busqueda
	 *            the busqueda to set
	 */
	public void setBusqueda(String busqueda) {
		this.busqueda = busqueda;
	}

	/**
	 * @return the listaExamenModel
	 */
	public DualListModel<LabExamen> getListaExamenModel() {
		return listaExamenModel;
	}

	/**
	 * @param listaExamenModel
	 *            the listaExamenModel to set
	 */
	public void setListaExamenModel(DualListModel<LabExamen> listaExamenModel) {
		this.listaExamenModel = listaExamenModel;
	}

	/**
	 * @return the listaExamenDualSource
	 */
	public List<LabExamen> getListaExamenDualSource() {
		return listaExamenDualSource;
	}

	/**
	 * @param listaExamenDualSource
	 *            the listaExamenDualSource to set
	 */
	public void setListaExamenDualSource(List<LabExamen> listaExamenDualSource) {
		this.listaExamenDualSource = listaExamenDualSource;
	}

	/**
	 * @return the listaExamenDualTarget
	 */
	public List<LabExamen> getListaExamenDualTarget() {
		return listaExamenDualTarget;
	}

	/**
	 * @param listaExamenDualTarget
	 *            the listaExamenDualTarget to set
	 */
	public void setListaExamenDualTarget(List<LabExamen> listaExamenDualTarget) {
		this.listaExamenDualTarget = listaExamenDualTarget;
	}

	/**
	 * @return the listExamens
	 */
	public List<LabExamen> getListExamens() {
		return listExamens;
	}

	/**
	 * @param listExamens
	 *            the listExamens to set
	 */
	public void setListExamens(List<LabExamen> listExamens) {
		this.listExamens = listExamens;
	}

	// public Consulta getConsulta() {
	// return consulta;
	// }
	//
	// public void setConsulta(Consulta consulta) {
	// this.consulta = consulta;
	// }

	/**
	 * @return the grupoExamen
	 */
	public LabGrupoExamen getGrupoExamen() {
		return grupoExamen;
	}

	/**
	 * @param grupoExamen
	 *            the grupoExamen to set
	 */
	public void setGrupoExamen(LabGrupoExamen grupoExamen) {
		this.grupoExamen = grupoExamen;
	}

	/**
	 * @return the aprovado
	 */
	public boolean isAprovado() {
		return aprovado;
	}

	/**
	 * @param aprovado
	 *            the aprovado to set
	 */
	public void setAprovado(boolean aprovado) {
		this.aprovado = aprovado;
	}

	/**
	 * @return the labProgramacionOrden
	 */
	public LabProgramacionOrden getLabProgramacionOrden() {
		return labProgramacionOrden;
	}

	/**
	 * @param labProgramacionOrden
	 *            the labProgramacionOrden to set
	 */
	public void setLabProgramacionOrden(
			LabProgramacionOrden labProgramacionOrden) {
		this.labProgramacionOrden = labProgramacionOrden;
	}

	/**
	 * @return the selectedMedicoProgamacion
	 */
	public Personal getSelectedMedicoProgamacion() {
		return selectedMedicoProgamacion;
	}

	/**
	 * @param selectedMedicoProgamacion
	 *            the selectedMedicoProgamacion to set
	 */
	public void setSelectedMedicoProgamacion(Personal selectedMedicoProgamacion) {
		this.selectedMedicoProgamacion = selectedMedicoProgamacion;
	}

	/**
	 * @return the fechaInicio
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * @param fechaInicio
	 *            the fechaInicio to set
	 */
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * @return the fechaFin
	 */
	public Date getFechaFin() {
		return fechaFin;
	}

	/**
	 * @param fechaFin
	 *            the fechaFin to set
	 */
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

}
