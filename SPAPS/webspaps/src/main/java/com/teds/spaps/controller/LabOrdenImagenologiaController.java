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
import com.teds.spaps.interfaces.dao.ILabExamenImagDao;
import com.teds.spaps.interfaces.dao.ILabGrupoExamenImagDao;
import com.teds.spaps.interfaces.dao.ILabOrdenImagDao;
import com.teds.spaps.interfaces.dao.ILabProgramacionOrdenDao;
import com.teds.spaps.interfaces.dao.IPacienteDao;
import com.teds.spaps.interfaces.dao.IParamSistemaIndicesDao;
import com.teds.spaps.interfaces.dao.IPersonalDao;
import com.teds.spaps.interfaces.dao.ISucursalDao;
import com.teds.spaps.model.Consulta;
import com.teds.spaps.model.LabExamenImag;
import com.teds.spaps.model.LabGrupoExamenImag;
import com.teds.spaps.model.LabOrdenDetalle;
import com.teds.spaps.model.LabOrdenDetalleImag;
import com.teds.spaps.model.LabOrdenImag;
import com.teds.spaps.model.LabProgramacionOrden;
import com.teds.spaps.model.Paciente;
import com.teds.spaps.model.ParamSistemaIndices;
import com.teds.spaps.model.Personal;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.structore.EDOrdenImagenologia;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;
import com.teds.spaps.util.Time;

/**
 * @author david
 *
 */
@ManagedBean(name = "labOrdenImagenologiaController")
@ViewScoped
public class LabOrdenImagenologiaController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5904739344015839892L;

	private @Inject FacesContext facesContext;

	/******* DAO **********/
	private @Inject ILabOrdenImagDao labOrdenDao;
	private @Inject SessionMain sessionMain;
	private @Inject ILabGrupoExamenImagDao grupoExamenDao;
	private @Inject IPacienteDao pacienteDao;
	private @Inject IPersonalDao personalDao;
	private @Inject ISucursalDao sucursalDao;
	private @Inject ILabExamenImagDao examenDao;
	private @Inject IParamSistemaIndicesDao sistemaIndicesDao;
	private @Inject IConsultaDao consultaDao;
	private @Inject ILabProgramacionOrdenDao programacionOrdenDao;

	/******* OBJECT **********/
	private LabOrdenImag labOrden;
	private LabOrdenImag labOrdenSelected;
	private Sucursal sucursal;
	private Paciente selectedPaciente;
	private Personal selectedMedico;
	private Personal selectedMedicoProgamacion;
	// private LabTipoExamenImag tipoExamen;
	private LabGrupoExamenImag grupoExamen;
	private Consulta consulta;
	private LabProgramacionOrden labProgramacionOrden;

	/******* LIST **********/
	private List<LabOrdenImag> listaLabOrden;
	private List<LabGrupoExamenImag> listGrupoExamens = new ArrayList<LabGrupoExamenImag>();
	private List<LabExamenImag> listExamens = new ArrayList<LabExamenImag>();
	private List<LabOrdenDetalleImag> listOrdenDetalles = new ArrayList<LabOrdenDetalleImag>();
	public static List<Paciente> listaPacientes = new ArrayList<Paciente>();
	public static List<Personal> listaPersonal = new ArrayList<Personal>();
	private List<Sucursal> listaSucursal = new ArrayList<Sucursal>();
	private List<EDOrdenImagenologia> listEdOrdenImagenologias = new ArrayList<EDOrdenImagenologia>();
	private DualListModel<LabExamenImag> listaExamenModel;
	private List<LabExamenImag> listaExamenDualSource = new ArrayList<LabExamenImag>();
	private List<LabExamenImag> listaExamenDualTarget = new ArrayList<LabExamenImag>();

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
	private String currentPage = "/pages/laboratorio/ordenes_imagenologia/list.xhtml";

	/**
	 * 
	 */
	public LabOrdenImagenologiaController() {
	}

	@PostConstruct
	public void initNew() {
		sucursal = sessionMain.PruebaSucursal();
		System.out.println("Sucursal : " + sucursal.getDescripcion());
		System.out.println("Compañia : "
				+ sucursal.getCompania().getDescripcion());
		listaSucursal = sucursalDao.obtenerPorCompania(sucursal.getCompania());
		grupoExamen = new LabGrupoExamenImag();
		// List<LabTipoExamenImag> listExamens =
		// tipoExamenDao.obtenerPorCompania(
		// "IMAGENOLOGIA", sucursal.getCompania());
		// if (listExamens.size() > 0) {
		// tipoExamen = listExamens.get(0);
		// System.out.println(tipoExamen.getDescripcion());
		// }

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
		// } else {
		// FacesUtil.infoMessage("Consulta",
		// "Por favor debe crear una consulta antes");
		// }
	}

	private void defaultValues() {
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
		seleccionado = false;
		aprovado = false;
		selectedPaciente = new Paciente();
		selectedMedico = new Personal();
		labOrden = new LabOrdenImag();
		labOrdenSelected = new LabOrdenImag();
		listOrdenDetalles.clear();
		listaExamenDualSource = new ArrayList<LabExamenImag>();
		listaExamenDualTarget = new ArrayList<LabExamenImag>();
		listaExamenModel = new DualListModel<>(listaExamenDualSource,
				listaExamenDualTarget);
		listEdOrdenImagenologias = examenDao.obtenerAllStructureOrden(sucursal
				.getCompania());
		listaLabOrden = labOrdenDao
				.obtenerPorCompaniaOrdenadoFechaRegistroRow50(sucursal
						.getCompania());
		consulta();
		currentPage = "/pages/laboratorio/ordenes_imagenologia/list.xhtml";
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
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
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
			for (LabExamenImag examen : listaExamenModel.getTarget()) {
				if (!existeExamen(examen, listOrdenDetalles)) {
					LabOrdenDetalleImag detalle = new LabOrdenDetalleImag();
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
			LabOrdenImag r = labOrdenDao.registrar(labOrden, listOrdenDetalles);
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
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
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
			rellenarDetalle();
			// labOrden.setConsulta(consulta);
			labOrden.setPaciente(selectedPaciente);

			labOrden.setCompania(sucursal.getCompania());
			labOrden.setSucursal(sucursal);
			labOrden.setFechaModificacion(labOrden.getFechaRegistro());
			labOrden.setUsuarioRegistro(sessionMain.getUsuarioLogin()
					.getLogin());
			labOrden.setHistoriaClinica(null);
			for (LabExamenImag examen : listaExamenModel.getTarget()) {
				if (!existeExamen(examen, listOrdenDetalles)) {
					LabOrdenDetalleImag detalle = new LabOrdenDetalleImag();
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
			LabOrdenImag r = labOrdenDao.modificar(labOrden, listOrdenDetalles);
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
		labProgramacionOrden.setOrdenImag(labOrden);
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
			labProgramacionOrden.setTipoOrden("IMAGENOLOGIA");
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

	private boolean existeExamen(LabExamenImag examen,
			List<LabOrdenDetalleImag> listOrdenDetalle) {
		for (LabOrdenDetalleImag labOrdenDetalle : listOrdenDetalle) {
			if (labOrdenDetalle.getLabExamen().getId().intValue() == examen
					.getId().intValue()) {
				return true;
			}
		}
		return false;
	}

	private void rellenarDetalle() {
		listOrdenDetalles.clear();
		for (EDOrdenImagenologia edOrdenImagenologia : listEdOrdenImagenologias) {
			for (LabExamenImag labExamen : edOrdenImagenologia.getListaExamen()) {
				if (labExamen.isChecker()) {
					LabOrdenDetalleImag detalle = new LabOrdenDetalleImag();
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
		for (LabOrdenDetalleImag ordenDetalle : listOrdenDetalles) {
			boolean encontrado = false;
			for (EDOrdenImagenologia edOrdenImagenologia : listEdOrdenImagenologias) {
				for (LabExamenImag labExamen : edOrdenImagenologia
						.getListaExamen()) {
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
			setAprovado(true);
		}

		selectedPaciente = labOrden.getPaciente();
		listOrdenDetalles = labOrden.getListOrdenDetalle();
		activarDetalle();
		currentPage = "/pages/laboratorio/ordenes_imagenologia/edit.xhtml";
	}

	public void actionNuevo() {
		System.out.println("Ingreso a actionNuevo...");
		crear = false;
		seleccionado = false;
		registrar = true;
		labOrden = new LabOrdenImag();
		labOrden.setFechaRegistro(new Date());
		selectedMedico = new Personal();
		selectedPaciente = new Paciente();
		listaExamenDualSource = new ArrayList<LabExamenImag>();
		listaExamenDualTarget = new ArrayList<LabExamenImag>();
		listaExamenModel = new DualListModel<>(listaExamenDualSource,
				listaExamenDualTarget);
		ParamSistemaIndices sistemaIndices = sistemaIndicesDao
				.obtenerPorCompania("OI", sucursal.getCompania());
		labOrden.setCodigo(sistemaIndices.getPrefijo()
				+ Time.rellendarCeros(sistemaIndices.getNumeroDigitos(),
						sistemaIndices.getCorrelativo()));

		currentPage = "/pages/laboratorio/ordenes_imagenologia/edit.xhtml";
	}

	public void actionCancelar() {
		crear = true;
		seleccionado = false;
		registrar = false;
		labOrden = new LabOrdenImag();
		currentPage = "/pages/laboratorio/ordenes_imagenologia/list.xhtml";
	}

	// ONCOMPLETETEXT GRUPO EXAMEN
	public List<LabGrupoExamenImag> completeGrupoExamen(String query) {
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
				LabOrdenDetalleImag auxiliar = listOrdenDetalles.get(i);
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
		for (LabOrdenDetalleImag labOrdenDetalle : listOrdenDetalles) {
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

		// listaPacientes = pacienteDao.obtenerPorCompania(upperQuery,
		// sucursal.getCompania());
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
//				consulta = sessionMain.getConsulta();
//				if ((consulta.equals(null) || consulta.getId() == 0)
//						&& consultaDao.tieneConsultaPaciente(selectedPaciente,
//								sucursal.getCompania())) {
//					consulta = consultaDao.obtenerConsultaActualDePaciente(
//							selectedPaciente, sucursal.getCompania());
//					sessionMain.setConsulta(consulta);
//				} else {
//					FacesUtil.infoMessage("Consulta",
//							"Por favor debe crear una consulta antes");
//				}
//				return;
//			}
//		}
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
		listaExamenDualSource = new ArrayList<LabExamenImag>();
		listaExamenDualSource = listExamens;
		listaExamenModel = new DualListModel<LabExamenImag>(
				listaExamenDualSource, listaExamenDualTarget);
	}

	public void onSelectTipoExamen() {
		listaExamenDualSource = new ArrayList<LabExamenImag>();
		LabGrupoExamenImag auxiliar = grupoExamenDao
				.obtenerLabGrupoExamenImag(grupoExamen.getId());
		List<LabExamenImag> labExamen = examenDao
				.obtenerExamenesPorGrupoExamen(auxiliar);
		listaExamenDualSource = labExamen;
		listaExamenModel = new DualListModel<LabExamenImag>(
				listaExamenDualSource, listaExamenDualTarget);

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
					+ "resources/report/laboratorio/reporteOrdenExamenImag.jasper";
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
			builder.append(((LabExamenImag) item).getDescripcion()).append(
					"<br />");
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

	public LabOrdenImag getLabOrden() {
		return labOrden;
	}

	public LabOrdenImag getLabOrdenSelected() {
		return labOrdenSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<LabOrdenImag> getListaLabOrden() {
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

	public void setLabOrden(LabOrdenImag labOrden) {
		this.labOrden = labOrden;
	}

	public void setLabOrdenSelected(LabOrdenImag labOrdenSelected) {
		this.labOrdenSelected = labOrdenSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaLabOrden(List<LabOrdenImag> listaLabOrden) {
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
	public List<LabGrupoExamenImag> getListGrupoExamens() {
		return listGrupoExamens;
	}

	/**
	 * @param listGrupoExamens
	 *            the listGrupoExamens to set
	 */
	public void setListGrupoExamens(List<LabGrupoExamenImag> listGrupoExamens) {
		this.listGrupoExamens = listGrupoExamens;
	}

	/**
	 * @return the listOrdenDetalles
	 */
	public List<LabOrdenDetalleImag> getListExamenDetalles() {
		return listOrdenDetalles;
	}

	/**
	 * @param listOrdenDetalles
	 *            the listOrdenDetalles to set
	 */
	public void setListExamenDetalles(
			List<LabOrdenDetalleImag> listOrdenDetalles) {
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
	 * @return the listEdOrdenImagenologias
	 */
	public List<EDOrdenImagenologia> getListEdOrdenImagenologias() {
		return listEdOrdenImagenologias;
	}

	/**
	 * @param listEdOrdenImagenologias
	 *            the listEdOrdenImagenologias to set
	 */
	public void setListEdOrdenImagenologias(
			List<EDOrdenImagenologia> listEdOrdenImagenologias) {
		this.listEdOrdenImagenologias = listEdOrdenImagenologias;
	}

	/**
	 * @return the listOrdenDetalles
	 */
	public List<LabOrdenDetalleImag> getListOrdenDetalles() {
		return listOrdenDetalles;
	}

	/**
	 * @param listOrdenDetalles
	 *            the listOrdenDetalles to set
	 */
	public void setListOrdenDetalles(List<LabOrdenDetalleImag> listOrdenDetalles) {
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
	public DualListModel<LabExamenImag> getListaExamenModel() {
		return listaExamenModel;
	}

	/**
	 * @param listaExamenModel
	 *            the listaExamenModel to set
	 */
	public void setListaExamenModel(
			DualListModel<LabExamenImag> listaExamenModel) {
		this.listaExamenModel = listaExamenModel;
	}

	/**
	 * @return the listaExamenDualSource
	 */
	public List<LabExamenImag> getListaExamenDualSource() {
		return listaExamenDualSource;
	}

	/**
	 * @param listaExamenDualSource
	 *            the listaExamenDualSource to set
	 */
	public void setListaExamenDualSource(
			List<LabExamenImag> listaExamenDualSource) {
		this.listaExamenDualSource = listaExamenDualSource;
	}

	/**
	 * @return the listaExamenDualTarget
	 */
	public List<LabExamenImag> getListaExamenDualTarget() {
		return listaExamenDualTarget;
	}

	/**
	 * @param listaExamenDualTarget
	 *            the listaExamenDualTarget to set
	 */
	public void setListaExamenDualTarget(
			List<LabExamenImag> listaExamenDualTarget) {
		this.listaExamenDualTarget = listaExamenDualTarget;
	}

	/**
	 * @return the listExamens
	 */
	public List<LabExamenImag> getListExamens() {
		return listExamens;
	}

	/**
	 * @param listExamens
	 *            the listExamens to set
	 */
	public void setListExamens(List<LabExamenImag> listExamens) {
		this.listExamens = listExamens;
	}

	public Consulta getConsulta() {
		return consulta;
	}

	public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
	}

	/**
	 * @return the grupoExamen
	 */
	public LabGrupoExamenImag getGrupoExamen() {
		return grupoExamen;
	}

	/**
	 * @param grupoExamen
	 *            the grupoExamen to set
	 */
	public void setGrupoExamen(LabGrupoExamenImag grupoExamen) {
		this.grupoExamen = grupoExamen;
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
