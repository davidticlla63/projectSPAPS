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
import javax.faces.component.UISelectBoolean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DualListModel;

import com.teds.spaps.interfaces.dao.IDesgImagGrupoDao;
import com.teds.spaps.interfaces.dao.IDesgImagGrupoExamenDao;
import com.teds.spaps.interfaces.dao.IDesgLabGrupoDao;
import com.teds.spaps.interfaces.dao.IDesgLabGrupoExamenDao;
import com.teds.spaps.interfaces.dao.IDesgOrdenServicioDao;
import com.teds.spaps.interfaces.dao.IDesgOrdenServicioImagExamenDao;
import com.teds.spaps.interfaces.dao.IDesgOrdenServicioLabExamenDao;
import com.teds.spaps.interfaces.dao.IDesgOrdenServiciosDao;
import com.teds.spaps.interfaces.dao.IDesgServGrupoDao;
import com.teds.spaps.interfaces.dao.IDesgServGrupoServicioDao;
import com.teds.spaps.interfaces.dao.IEmpresaDao;
import com.teds.spaps.interfaces.dao.IIdentificacionIndividuoDao;
import com.teds.spaps.interfaces.dao.ILabExamenDao;
import com.teds.spaps.interfaces.dao.ILabExamenImagDao;
import com.teds.spaps.interfaces.dao.IPacienteDao;
import com.teds.spaps.interfaces.dao.IParamSistemaIndicesDao;
import com.teds.spaps.interfaces.dao.IPersonalDao;
import com.teds.spaps.interfaces.dao.IServicioDao;
import com.teds.spaps.interfaces.dao.ISucursalDao;
import com.teds.spaps.model.DesgImagGrupo;
import com.teds.spaps.model.DesgImagGrupoExamen;
import com.teds.spaps.model.DesgLabGrupo;
import com.teds.spaps.model.DesgLabGrupoExamen;
import com.teds.spaps.model.DesgOrdenServicio;
import com.teds.spaps.model.DesgOrdenServicioImagExamen;
import com.teds.spaps.model.DesgOrdenServicioLabExamen;
import com.teds.spaps.model.DesgOrdenServicios;
import com.teds.spaps.model.DesgServGrupo;
import com.teds.spaps.model.DesgServGrupoServicio;
import com.teds.spaps.model.Empresa;
import com.teds.spaps.model.IdentificacionPaciente;
import com.teds.spaps.model.LabExamen;
import com.teds.spaps.model.LabExamenImag;
import com.teds.spaps.model.LabGrupoExamen;
import com.teds.spaps.model.LabGrupoExamenImag;
import com.teds.spaps.model.Paciente;
import com.teds.spaps.model.ParamSistemaIndices;
import com.teds.spaps.model.Personal;
import com.teds.spaps.model.Servicio;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.Usuario;
import com.teds.spaps.structore.EDDesgOrdenImagExamen;
import com.teds.spaps.structore.EDDesgOrdenLabExamen;
import com.teds.spaps.structore.EDDesgOrdenServicio;
import com.teds.spaps.structore.EDOrdenImagenologia;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;
import com.teds.spaps.util.Time;

/**
 * @author ANITA
 *
 */
@ManagedBean(name = "desgOrdenServicioController")
@ViewScoped
public class DesgOrdenServicioController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6336508630298721212L;
	/******* DAO **********/
	private @Inject IDesgOrdenServicioDao desgOrdenServicioDao;
	private @Inject SessionMain sessionMain;
	private @Inject IPacienteDao pacienteDao;
	private @Inject IIdentificacionIndividuoDao identificacionIndividuoDao;
	private @Inject IPersonalDao personalDao;
	private @Inject ISucursalDao sucursalDao;
	private @Inject IEmpresaDao empresaDao;
	private @Inject IParamSistemaIndicesDao sistemaIndicesDao;
	private @Inject ILabExamenImagDao examenImagDao;
	private @Inject ILabExamenDao examenDao;
	private @Inject IDesgLabGrupoDao desgLabGrupoDao;
	private @Inject IDesgImagGrupoDao desgImagGrupoDao;
	private @Inject IDesgServGrupoDao desgServGrupoDao;
	private @Inject IDesgLabGrupoExamenDao desgLabGrupoExamenDao;
	private @Inject IDesgImagGrupoExamenDao desgImagGrupoExamenDao;
	private @Inject IDesgServGrupoServicioDao desgServGrupoServicioDao;
	private @Inject IServicioDao servicioDao;
	private @Inject IDesgOrdenServicioLabExamenDao desgOrdenServicioLabExamenDao;
	private @Inject IDesgOrdenServicioImagExamenDao desgOrdenServicioImagExamenDao;
	private @Inject IDesgOrdenServiciosDao desgOrdenServiciosDao;

	/******* OBJECT **********/
	private DesgOrdenServicio desgOrdenServicio;
	private DesgOrdenServicio desgOrdenServicioSelected;
	private Sucursal sucursal;
	private Usuario usuario;
	private Paciente paciente;
	private Personal medicoAuditor;
	private LabGrupoExamen grupoExamen;
	private LabGrupoExamenImag grupoExamenImag;
	private Servicio servicio;
	private ParamSistemaIndices sistemaIndices;

	/******* LIST **********/
	private List<DesgOrdenServicio> listaDesgOrdenServicio;
	public static List<Paciente> listaPacientes;
	public static List<Personal> medicos;
	public static List<Sucursal> sucursales;
	public static List<Empresa> empresas;
	private List<LabExamen> listaExamenDualSource;
	private List<LabExamen> listaExamenDualTarget;
	private DualListModel<LabExamen> listaExamenModel;
	private List<Servicio> listaServicioDualSource;
	private List<Servicio> listaServicioDualTarget;
	private DualListModel<Servicio> listaServicioModel;
	private List<LabExamen> listExamens;
	private List<LabExamenImag> listaExamenImagDualSource;
	private List<LabExamenImag> listaExamenImagDualTarget;
	private DualListModel<LabExamenImag> listaExamenImagModel;
	private List<EDOrdenImagenologia> listEdOrdenImagenologias;
	private List<LabExamenImag> listExamensImag;
	private List<EDDesgOrdenLabExamen> listaEDesgLabGrupo;
	private List<EDDesgOrdenImagExamen> listaEDesgImagGrupo;
	private List<EDDesgOrdenServicio> listaEDesgServGrupo;
	private List<DesgLabGrupo> listaLabGrupo;
	private List<DesgImagGrupo> listaImagGrupo;
	private List<DesgServGrupo> listaServGrupo;
	private List<DesgOrdenServicioLabExamen> labExamenes;
	private List<DesgOrdenServicioImagExamen> imagExamenes;
	private List<DesgOrdenServicios> servicios;
	private List<DesgOrdenServicioLabExamen> labExamenesBackup;
	private List<DesgOrdenServicioImagExamen> imagExamenesBackup;
	private List<DesgOrdenServicios> serviciosBackup;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;
	private boolean ordenSeleccionada = false;
	private boolean seeEstudio = false;
	private boolean resetTablesS = true;
	private String tipoBusqueda = "NP";
	private String pagina = "";
	private String currentPage1 = "/pages/desgravamen/desgravamen_laboratorios.xhtml";
	private String busqueda;
	private boolean check;

	@Inject
	Conversation conversation;

	@PostConstruct
	public void initNew() {
		desgOrdenServicio = new DesgOrdenServicio();
		desgOrdenServicioSelected = new DesgOrdenServicio();
		usuario = sessionMain.getUsuarioLogin();
		sucursal = sessionMain.PruebaSucursal();
		paciente = new Paciente();
		medicoAuditor = new Personal();
		listaDesgOrdenServicio = desgOrdenServicioDao.obtenerAll();
		listaPacientes = new ArrayList<Paciente>();
		medicos = new ArrayList<Personal>();
		sucursales = new ArrayList<Sucursal>();
		empresas = new ArrayList<Empresa>();
		modificar = false;
		registrar = false;
		crear = true;
		ordenSeleccionada = false;
		seeEstudio = false;
		resetTablesS = true;
		tipoBusqueda = "NP";
		sistemaIndices = sistemaIndicesDao.obtenerPorCompania("OS",
				sucursal.getCompania());
		desgOrdenServicio.setCodigo(sistemaIndices.getPrefijo()
				+ Time.rellendarCeros(sistemaIndices.getNumeroDigitos(),
						sistemaIndices.getCorrelativo()));
		resetEstudio();
		resetImageneologia();
		resetServicio();
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

	public void onKeyUpQueryExamenesImag() {
		String upperQuery = busqueda.toUpperCase();
		listExamensImag = examenImagDao.obtenerPorCompania(upperQuery,
				sucursal.getCompania());
		listaExamenImagDualSource = new ArrayList<LabExamenImag>();
		listaExamenImagDualSource = listExamensImag;
		listaExamenImagModel = new DualListModel<LabExamenImag>(
				listaExamenImagDualSource, listaExamenImagDualTarget);
	}

	public void onKeyUpQueryServicio() {
		String upperQuery = busqueda.toUpperCase();
		List<Servicio> servicios = servicioDao.obtenerPorCompania(upperQuery,
				sucursal.getCompania());
		listaServicioDualSource = new ArrayList<Servicio>();
		listaServicioDualSource = servicios;
		listaServicioModel = new DualListModel<Servicio>(
				listaServicioDualSource, listaServicioDualTarget);
	}

	public void onSelectTipoExamenImag() {
		listaExamenImagDualSource = new ArrayList<LabExamenImag>();
		DesgImagGrupo auxiliar = desgImagGrupoDao
				.obtenerDesgImagGrupo(grupoExamenImag.getId());
		List<LabExamenImag> labExamen = desgImagGrupoExamenDao
				.obtenerExamenesPorGrupo(auxiliar);
		listaExamenImagDualSource = labExamen;
		listaExamenImagModel = new DualListModel<LabExamenImag>(
				listaExamenImagDualSource, listaExamenImagDualTarget);
	}

	public void onSelectTipoExamen() {
		listaExamenDualSource = new ArrayList<LabExamen>();
		DesgLabGrupo auxiliar = desgLabGrupoDao.obtenerDesgLabGrupo(grupoExamen
				.getId());
		List<LabExamen> labExamen = desgLabGrupoExamenDao
				.obtenerExamenesPorGrupo(auxiliar);
		listaExamenDualSource = labExamen;
		listaExamenModel = new DualListModel<LabExamen>(listaExamenDualSource,
				listaExamenDualTarget);
	}

	public void onSelectServicio() {
		listaServicioDualSource = new ArrayList<Servicio>();
		DesgServGrupo auxiliar = desgServGrupoDao.obtenerDesgServGrupo(servicio
				.getId());
		List<Servicio> labExamen = desgServGrupoServicioDao
				.obtenerServiciosPorGrupo(auxiliar);
		listaServicioDualSource = labExamen;
		listaServicioModel = new DualListModel<Servicio>(
				listaServicioDualSource, listaServicioDualTarget);
	}

	private boolean existeExamen(LabExamen examen) {
		for (DesgOrdenServicioLabExamen labOrdenDetalle : labExamenes) {
			if (labOrdenDetalle.getExamen().equals(examen)) {
				return true;
			}
		}
		return false;
	}

	private boolean existeExamenImag(LabExamenImag examen) {
		for (DesgOrdenServicioImagExamen labOrdenDetalle : imagExamenes) {
			if (labOrdenDetalle.getExamen().equals(examen)) {
				return true;
			}
		}
		return false;
	}

	private boolean existeServicio(Servicio servicio) {
		for (DesgOrdenServicios labOrdenDetalle : servicios) {
			if (labOrdenDetalle.getServicio().equals(servicio)) {
				return true;
			}
		}
		return false;
	}

	/*
	 * private void rellenarDetalle() { listOrdenDetalles.clear(); for
	 * (EDDesgOrdenLabExamen edOrdenLaboratorio : listaEDesgGrupo) { for
	 * (DesgLabGrupoExamen labExamen : edOrdenLaboratorio.getListaExamen()) { if
	 * (labExamen.getExamen().isChecker()) { LabOrdenDetalle detalle = new
	 * LabOrdenDetalle(); detalle.setDescripcion(labExamen.getDescripcion());
	 * detalle.setCompania(sucursal.getCompania());
	 * detalle.setSucursal(sucursal); detalle.setFechaRegistro(new Date());
	 * detalle.setParametros("");
	 * detalle.setUsuarioRegistro(sessionMain.getUsuarioLogin() .getLogin());
	 * detalle.setLabExamen(labExamen); listOrdenDetalles.add(detalle); } } } }
	 */

	private void activarDetalle() {
		int i = 0;
		listaExamenDualSource = new ArrayList<LabExamen>();
		listaExamenDualTarget = new ArrayList<LabExamen>();
		listaExamenModel = new DualListModel<>(listaExamenDualSource,
				listaExamenDualTarget);
		listaExamenImagDualSource = new ArrayList<LabExamenImag>();
		listaExamenImagDualTarget = new ArrayList<LabExamenImag>();
		listaExamenImagModel = new DualListModel<>(listaExamenImagDualSource,
				listaExamenImagDualTarget);
		listaServicioDualSource = new ArrayList<Servicio>();
		listaServicioDualTarget = new ArrayList<Servicio>();
		listaServicioModel = new DualListModel<>(listaServicioDualSource,
				listaServicioDualTarget);
		for (DesgOrdenServicioLabExamen ordenDetalle : labExamenes) {
			boolean encontrado = false;
			for (EDDesgOrdenLabExamen edOrdenLaboratorio : listaEDesgLabGrupo) {
				for (DesgLabGrupoExamen labExamen : edOrdenLaboratorio
						.getListaExamen()) {
					if (labExamen.getExamen().equals(ordenDetalle.getExamen())) {
						i++;
						System.out.println("contador de detalle de examen = "
								+ i);
						labExamen.getExamen().setChecker(true);
						System.out.println("examen "
								+ labExamen.getExamen().getDescripcion()
								+ " esta " + labExamen.getExamen().isChecker());
						encontrado = true;
					}
				}
			}
			if (!encontrado) {
				i++;
				System.out.println("contador de detalle de examen = " + i);
				listaExamenModel.getTarget().add(ordenDetalle.getExamen());
				encontrado = false;
			}
		}
		listaExamenDualTarget = listaExamenModel.getTarget();
		System.out.println("target examenlab = "
				+ listaExamenModel.getTarget().size());
		i = 0;
		for (DesgOrdenServicioImagExamen ordenDetalle : imagExamenes) {
			boolean encontrado = false;
			for (EDDesgOrdenImagExamen edOrdenLaboratorio : listaEDesgImagGrupo) {
				for (DesgImagGrupoExamen labExamen : edOrdenLaboratorio
						.getListaExamen()) {
					if (labExamen.getExamen().equals(ordenDetalle.getExamen())) {
						i++;
						System.out
								.println("contador de detalle de examenimag = "
										+ i);
						labExamen.getExamen().setChecker(true);
						System.out.println("examenimag "
								+ labExamen.getExamen().getDescripcion()
								+ " esta " + labExamen.getExamen().isChecker());
						encontrado = true;
					}
				}
			}
			if (!encontrado) {
				i++;
				System.out.println("contador de detalle de examenimag = " + i);
				listaExamenImagModel.getTarget().add(ordenDetalle.getExamen());
				encontrado = false;
			}
		}
		listaExamenImagDualTarget = listaExamenImagModel.getTarget();
		System.out.println("target examenimag = "
				+ listaExamenImagModel.getTarget().size());
		i = 0;
		for (DesgOrdenServicios ordenDetalle : servicios) {
			boolean encontrado = false;
			for (EDDesgOrdenServicio edOrdenLaboratorio : listaEDesgServGrupo) {
				for (DesgServGrupoServicio labExamen : edOrdenLaboratorio
						.getServicios()) {
					if (labExamen.getServicio().equals(
							ordenDetalle.getServicio())) {
						i++;
						System.out
								.println("contador de detalle de servicios = "
										+ i);
						labExamen.getServicio().setChecker(true);
						System.out.println("servicio "
								+ labExamen.getServicio().getDescripcion()
								+ " esta "
								+ labExamen.getServicio().isChecker());
						encontrado = true;
					}
				}
			}
			if (!encontrado) {
				i++;
				System.out.println("contador de detalle de servicios = " + i);
				listaServicioModel.getTarget().add(ordenDetalle.getServicio());
				encontrado = false;
			}
		}
		listaServicioDualTarget = listaServicioModel.getTarget();
		System.out.println("target servicios = "
				+ listaServicioModel.getTarget().size());
	}

	private void fillListas() {
		for (EDDesgOrdenLabExamen edDesgOrdenLabExamen : listaEDesgLabGrupo) {
			for (DesgLabGrupoExamen desgLabGrupoExamen : edDesgOrdenLabExamen
					.getListaExamen()) {
				if (desgLabGrupoExamen.getExamen().isChecker()
						&& !existeExamen(desgLabGrupoExamen.getExamen())) {
					DesgOrdenServicioLabExamen desgOrdenServicioLabExamen = new DesgOrdenServicioLabExamen();
					desgOrdenServicioLabExamen.setExamen(desgLabGrupoExamen
							.getExamen());
					labExamenes.add(desgOrdenServicioLabExamen);
				}
			}
		}
		for (LabExamen empresa : listaExamenModel.getTarget()) {
			if (!existeExamen(empresa)) {
				DesgOrdenServicioLabExamen desgOrdenServicioLabExamen = new DesgOrdenServicioLabExamen();
				desgOrdenServicioLabExamen.setExamen(empresa);
				labExamenes.add(desgOrdenServicioLabExamen);
			}
		}
		for (EDDesgOrdenImagExamen edDesgOrdenLabExamen : listaEDesgImagGrupo) {
			for (DesgImagGrupoExamen desgLabGrupoExamen : edDesgOrdenLabExamen
					.getListaExamen()) {
				if (desgLabGrupoExamen.getExamen().isChecker()
						&& !existeExamenImag(desgLabGrupoExamen.getExamen())) {
					DesgOrdenServicioImagExamen desgOrdenServicioImagExamen = new DesgOrdenServicioImagExamen();
					desgOrdenServicioImagExamen.setExamen(desgLabGrupoExamen
							.getExamen());
					imagExamenes.add(desgOrdenServicioImagExamen);
				}
			}
		}
		for (LabExamenImag empresa : listaExamenImagModel.getTarget()) {
			if (!existeExamenImag(empresa)) {
				DesgOrdenServicioImagExamen desgOrdenServicioLabExamen = new DesgOrdenServicioImagExamen();
				desgOrdenServicioLabExamen.setExamen(empresa);
				imagExamenes.add(desgOrdenServicioLabExamen);
			}
		}
		for (EDDesgOrdenServicio edDesgOrdenLabExamen : listaEDesgServGrupo) {
			for (DesgServGrupoServicio desgLabGrupoExamen : edDesgOrdenLabExamen
					.getServicios()) {
				if (desgLabGrupoExamen.getServicio().isChecker()
						&& !existeServicio(desgLabGrupoExamen.getServicio())) {
					DesgOrdenServicios desgOrdenServicioLabExamen = new DesgOrdenServicios();
					desgOrdenServicioLabExamen.setServicio(desgLabGrupoExamen
							.getServicio());
					servicios.add(desgOrdenServicioLabExamen);
				}
			}
		}
		for (Servicio empresa : listaServicioModel.getTarget()) {
			if (!existeServicio(empresa)) {
				DesgOrdenServicios desgOrdenServicioLabExamen = new DesgOrdenServicios();
				desgOrdenServicioLabExamen.setServicio(empresa);
				servicios.add(desgOrdenServicioLabExamen);
			}
		}
	}

	public void nuevoEstudio() {
		seeEstudio = true;
		busqueda = "";
		tipoBusqueda = "NP";
		if (resetTablesS) {
			resetEstudio();
			resetImageneologia();
			resetServicio();
			resetTablesS = false;
		}
	}

	public void resetEstudio() {
		if (!ordenSeleccionada) {
			listaExamenDualSource = new ArrayList<LabExamen>();
			listaExamenDualTarget = new ArrayList<LabExamen>();
			listaExamenModel = new DualListModel<>(listaExamenDualSource,
					listaExamenDualTarget);
			labExamenes = new ArrayList<DesgOrdenServicioLabExamen>();
			listaEDesgLabGrupo = desgLabGrupoDao.obtenerEDGrupos(sucursal);
		}

		grupoExamen = new LabGrupoExamen();

		listaLabGrupo = desgLabGrupoDao.obtenerPorSucursal(sucursal);

		busqueda = "";
		tipoBusqueda = "NP";

		listExamens = new ArrayList<LabExamen>();
	}

	public void cancelarEstudio() {
		ordenSeleccionada = false;
		resetEstudio();
	}

	public void nuevoImageneologia() {
		seeEstudio = true;
		busqueda = "";
		tipoBusqueda = "NP";
		if (resetTablesS)
			resetImageneologia();
	}

	public void resetImageneologia() {
		if (!ordenSeleccionada) {
			listaExamenImagDualSource = new ArrayList<LabExamenImag>();
			listaExamenImagDualTarget = new ArrayList<LabExamenImag>();
			listaExamenImagModel = new DualListModel<>(
					listaExamenImagDualSource, listaExamenImagDualTarget);
			imagExamenes = new ArrayList<DesgOrdenServicioImagExamen>();
			listaEDesgImagGrupo = desgImagGrupoDao.obtenerEDGrupos(sucursal);
		}

		grupoExamenImag = new LabGrupoExamenImag();

		listaImagGrupo = desgImagGrupoDao.obtenerPorSucursal(sucursal);

		busqueda = "";
		tipoBusqueda = "NP";

		listExamensImag = new ArrayList<LabExamenImag>();
	}

	public void cancelarImageneologia() {
		ordenSeleccionada = false;
		resetEstudio();
	}

	public void nuevoServicio() {
		seeEstudio = true;
		busqueda = "";
		tipoBusqueda = "NP";
		if (resetTablesS)
			resetServicio();
	}

	public void resetServicio() {
		if (!ordenSeleccionada) {
			listaServicioDualSource = new ArrayList<Servicio>();
			listaServicioDualTarget = new ArrayList<Servicio>();
			listaServicioModel = new DualListModel<>(listaServicioDualSource,
					listaServicioDualTarget);
			servicios = new ArrayList<DesgOrdenServicios>();
			listaEDesgServGrupo = desgServGrupoDao.obtenerEDGrupos(sucursal);
		}

		servicio = new Servicio();

		listaServGrupo = desgServGrupoDao.obtenerPorSucursal(sucursal);

		busqueda = "";
		tipoBusqueda = "NP";

		listExamens = new ArrayList<LabExamen>();
	}

	public void cancelarServicio() {
		ordenSeleccionada = false;
		resetServicio();
	}

	private EDDesgOrdenLabExamen obtenerEDGrupoExamen(int id) {
		for (EDDesgOrdenLabExamen edDesgOrdenLabExamen : listaEDesgLabGrupo) {
			if (edDesgOrdenLabExamen.getGrupoExamen().getId().intValue() == id)
				return edDesgOrdenLabExamen;
		}
		return null;
	}

	private void selectAllExamen(int id) {
		EDDesgOrdenLabExamen ordenLabExamen = obtenerEDGrupoExamen(id);
		for (DesgLabGrupoExamen desgLabGrupoExamen : ordenLabExamen
				.getListaExamen()) {
			desgLabGrupoExamen.getExamen().setChecker(true);
		}
	}

	private void UnSelectAllExamen(int id) {
		EDDesgOrdenLabExamen ordenLabExamen = obtenerEDGrupoExamen(id);
		for (DesgLabGrupoExamen desgLabGrupoExamen : ordenLabExamen
				.getListaExamen()) {
			desgLabGrupoExamen.getExamen().setChecker(false);
		}
	}

	public void checkBoxListenerExamen(UISelectBoolean checkbox,
			DesgLabGrupo supplier) {
		boolean selected = checkbox.isSelected();
		if (selected) {
			selectAllExamen(supplier.getId());
		} else {
			UnSelectAllExamen(supplier.getId());
		}
	}

	private EDDesgOrdenImagExamen obtenerEDGrupoImagExamen(int id) {
		for (EDDesgOrdenImagExamen edDesgOrdenLabExamen : listaEDesgImagGrupo) {
			if (edDesgOrdenLabExamen.getGrupoExamen().getId().intValue() == id)
				return edDesgOrdenLabExamen;
		}
		return null;
	}

	private void selectAllImageneologia(int id) {
		EDDesgOrdenImagExamen ordenLabExamen = obtenerEDGrupoImagExamen(id);
		for (DesgImagGrupoExamen desgLabGrupoExamen : ordenLabExamen
				.getListaExamen()) {
			desgLabGrupoExamen.getExamen().setChecker(true);
		}
	}

	private void UnSelectAllImageneologia(int id) {
		EDDesgOrdenImagExamen ordenLabExamen = obtenerEDGrupoImagExamen(id);
		for (DesgImagGrupoExamen desgLabGrupoExamen : ordenLabExamen
				.getListaExamen()) {
			desgLabGrupoExamen.getExamen().setChecker(false);
		}
	}

	public void checkBoxListenerImageneologia(UISelectBoolean checkbox,
			DesgImagGrupo supplier) {
		boolean selected = checkbox.isSelected();
		if (selected) {
			selectAllImageneologia(supplier.getId());
		} else {
			UnSelectAllImageneologia(supplier.getId());
		}
	}

	private EDDesgOrdenServicio obtenerEDGrupoServicio(int id) {
		for (EDDesgOrdenServicio edDesgOrdenLabExamen : listaEDesgServGrupo) {
			if (edDesgOrdenLabExamen.getGrupoServicio().getId().intValue() == id)
				return edDesgOrdenLabExamen;
		}
		return null;
	}

	private void selectAllServicio(int id) {
		EDDesgOrdenServicio ordenLabExamen = obtenerEDGrupoServicio(id);
		for (DesgServGrupoServicio desgLabGrupoExamen : ordenLabExamen
				.getServicios()) {
			desgLabGrupoExamen.getServicio().setChecker(true);
		}
	}

	private void UnSelectAllServicio(int id) {
		EDDesgOrdenServicio ordenLabExamen = obtenerEDGrupoServicio(id);
		for (DesgServGrupoServicio desgLabGrupoExamen : ordenLabExamen
				.getServicios()) {
			desgLabGrupoExamen.getServicio().setChecker(false);
		}
	}

	public void checkBoxListenerServicio(UISelectBoolean checkbox,
			DesgServGrupo supplier) {
		boolean selected = checkbox.isSelected();
		if (selected) {
			selectAllServicio(supplier.getId());
		} else {
			UnSelectAllServicio(supplier.getId());
		}
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

	public List<Personal> onCompletePersonal(String query) {
		String upperQuery = query.toUpperCase().trim();
		medicos = personalDao.obtenerPorNombreSucursal(upperQuery, upperQuery,
				upperQuery, sucursal);
		return medicos;
	}

	public List<Sucursal> onCompleteSucursalDestino(String query) {
		String upperQuery = query.toUpperCase().trim();
		sucursales = sucursalDao.obtenerPorCompaniaAutoComplete(upperQuery,
				sucursal.getCompania());
		return sucursales;
	}

	public List<Empresa> onCompleteEntidad(String query) {
		String upperQuery = query.toUpperCase().trim();
		empresas = empresaDao.obtenerPorSucursalAutoComplete(upperQuery,
				sucursal);
		return empresas;
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
		desgOrdenServicio.setCliente((Paciente) event.getObject());
	}

	public void onSelectPersonalClick(SelectEvent event) {
		desgOrdenServicio.setMedicoAuditor((Personal) event.getObject());
	}

	public void onSelectSucursalDestinoClick(SelectEvent event) {
		desgOrdenServicio.setDestino((Sucursal) event.getObject());
	}

	public void onSelectEntidadClick(SelectEvent event) {
		desgOrdenServicio.setEmpresa((Empresa) event.getObject());
	}

	public void onSelectPagina() {
		currentPage1 = "/pages/desgravamen/" + pagina + ".xhtml";
	}

	public void actionNuevo() {
		System.out.println("Ingreso a actionNuevo...");
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
		ordenSeleccionada = true;
		labExamenes = desgOrdenServicioLabExamenDao
				.obtenerPorOrden(desgOrdenServicio);
		labExamenesBackup = desgOrdenServicioLabExamenDao
				.obtenerPorOrden(desgOrdenServicio);
		imagExamenes = desgOrdenServicioImagExamenDao
				.obtenerPorOrden(desgOrdenServicio);
		imagExamenesBackup = desgOrdenServicioImagExamenDao
				.obtenerPorOrden(desgOrdenServicio);
		servicios = desgOrdenServiciosDao.obtenerPorOrden(desgOrdenServicio);
		serviciosBackup = desgOrdenServiciosDao
				.obtenerPorOrden(desgOrdenServicio);
		activarDetalle();
		FacesContext.getCurrentInstance().renderResponse();
		resetearFitrosTabla("form001:singleDT");
		// FacesUtil.resetComponent("form001:body");
		// FacesUtil.updateComponent("form001:containerPages");
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
			if (getSucursal() == null || getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				if (desgOrdenServicio.getCodigo().isEmpty()) {
					FacesUtil
							.errorMessage("El codigo es un campo obligatorio.");
					return;
				}
				if (desgOrdenServicio.getCliente() == null
						|| desgOrdenServicio.getCliente().getId() == 0) {
					FacesUtil
							.errorMessage("El paciente es un campo obligatorio.");
					return;
				}
				if (desgOrdenServicio.getMedicoAuditor() == null
						|| desgOrdenServicio.getMedicoAuditor().getId() == 0) {
					FacesUtil
							.errorMessage("El medico auditor es un campo obligatorio.");
					return;
				}
				if (desgOrdenServicio.getEmpresa() == null
						|| desgOrdenServicio.getEmpresa().getId() == 0) {
					FacesUtil
							.errorMessage("La entidad es un campo obligatorio.");
					return;
				}
				if (desgOrdenServicio.getDestino() == null
						|| desgOrdenServicio.getDestino().getId() == 0) {
					FacesUtil
							.errorMessage("La sucursal destino es un campo obligatorio.");
					return;
				}
				fillListas();
				desgOrdenServicio.setEstado("PE");
				desgOrdenServicio.setSucursal(getSucursal());
				desgOrdenServicio.setCompania(getSucursal().getCompania());
				desgOrdenServicio.setFechaRegistro(new Date());
				desgOrdenServicio.setFechaModificacion(desgOrdenServicio
						.getFechaRegistro());
				desgOrdenServicio.setUsuarioRegistro(getUsuario().getLogin());
				desgOrdenServicio = desgOrdenServicioDao
						.registrar(desgOrdenServicio, labExamenes,
								imagExamenes, servicios);
				initNew();
			}
		} catch (Exception e) {
			System.out.println("Error en registro de desgOrdenServicio: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			if (desgOrdenServicio.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				if (desgOrdenServicio.getCodigo().isEmpty()) {
					FacesUtil
							.errorMessage("El codigo es un campo obligatorio.");
					return;
				}
				if (desgOrdenServicio.getCliente() == null
						|| desgOrdenServicio.getCliente().getId() == 0) {
					FacesUtil
							.errorMessage("El paciente es un campo obligatorio.");
					return;
				}
				if (desgOrdenServicio.getMedicoAuditor() == null
						|| desgOrdenServicio.getMedicoAuditor().getId() == 0) {
					FacesUtil
							.errorMessage("El medico auditor es un campo obligatorio.");
					return;
				}
				if (desgOrdenServicio.getEmpresa() == null
						|| desgOrdenServicio.getEmpresa().getId() == 0) {
					FacesUtil
							.errorMessage("La entidad es un campo obligatorio.");
					return;
				}
				if (desgOrdenServicio.getDestino() == null
						|| desgOrdenServicio.getDestino().getId() == 0) {
					FacesUtil
							.errorMessage("La sucursal destino es un campo obligatorio.");
					return;
				}
				labExamenes.clear();
				imagExamenes.clear();
				servicios.clear();
				fillListas();
				desgOrdenServicio.setFechaModificacion(new Date());
				desgOrdenServicio.setUsuarioRegistro(sessionMain
						.getUsuarioLogin().getLogin());
				desgOrdenServicioDao.modificar(desgOrdenServicio,
						labExamenesBackup, labExamenes, imagExamenesBackup,
						imagExamenes, serviciosBackup, servicios);
				initNew();
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de desgOrdenServicio: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			desgOrdenServicioDao.eliminar(getDesgOrdenServicio(), labExamenes,
					imagExamenes, servicios);
			initNew();
		} catch (Exception e) {
			System.out.println("Error en eliminacion de desgOrdenServicio: "
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

	public String getTipoBusqueda() {
		return tipoBusqueda;
	}

	public void setTipoBusqueda(String tipoBusqueda) {
		this.tipoBusqueda = tipoBusqueda;
	}

	/**
	 * 
	 */
	public DesgOrdenServicioController() {
	}

	public DesgOrdenServicio getDesgOrdenServicio() {
		return desgOrdenServicio;
	}

	public DesgOrdenServicio getDesgOrdenServicioSelected() {
		return desgOrdenServicioSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<DesgOrdenServicio> getListaDesgOrdenServicio() {
		return listaDesgOrdenServicio;
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

	public void setDesgOrdenServicio(DesgOrdenServicio desgOrdenServicio) {
		this.desgOrdenServicio = desgOrdenServicio;
	}

	public void setDesgOrdenServicioSelected(
			DesgOrdenServicio desgOrdenServicioSelected) {
		this.desgOrdenServicioSelected = desgOrdenServicioSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaDesgOrdenServicio(
			List<DesgOrdenServicio> listaDesgOrdenServicio) {
		this.listaDesgOrdenServicio = listaDesgOrdenServicio;
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

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public List<Paciente> getListaPacientes() {
		return listaPacientes;
	}

	public void setListaPacientes(List<Paciente> listaPacientes) {
		DesgOrdenServicioController.listaPacientes = listaPacientes;
	}

	public static List<Personal> getMedicos() {
		return medicos;
	}

	public static void setMedicos(List<Personal> medicos) {
		DesgOrdenServicioController.medicos = medicos;
	}

	public Personal getMedicoAuditor() {
		return medicoAuditor;
	}

	public void setMedicoAuditor(Personal medicoAuditor) {
		this.medicoAuditor = medicoAuditor;
	}

	public static List<Sucursal> getSucursales() {
		return sucursales;
	}

	public static void setSucursales(List<Sucursal> sucursales) {
		DesgOrdenServicioController.sucursales = sucursales;
	}

	public static List<Empresa> getEmpresas() {
		return empresas;
	}

	public static void setEmpresas(List<Empresa> empresas) {
		DesgOrdenServicioController.empresas = empresas;
	}

	public String getPagina() {
		return pagina;
	}

	public String getCurrentPage1() {
		return currentPage1;
	}

	public void setPagina(String pagina) {
		this.pagina = pagina;
	}

	public void setCurrentPage1(String currentPage1) {
		this.currentPage1 = currentPage1;
	}

	public boolean isOrdenSeleccionada() {
		return ordenSeleccionada;
	}

	public void setOrdenSeleccionada(boolean ordenSeleccionada) {
		this.ordenSeleccionada = ordenSeleccionada;
	}

	public boolean isSeeEstudio() {
		return seeEstudio;
	}

	public void setSeeEstudio(boolean seeEstudio) {
		this.seeEstudio = seeEstudio;
	}

	public LabGrupoExamen getGrupoExamen() {
		return grupoExamen;
	}

	public LabGrupoExamenImag getGrupoExamenImag() {
		return grupoExamenImag;
	}

	public List<LabExamen> getListaExamenDualSource() {
		return listaExamenDualSource;
	}

	public List<LabExamen> getListaExamenDualTarget() {
		return listaExamenDualTarget;
	}

	public DualListModel<LabExamen> getListaExamenModel() {
		return listaExamenModel;
	}

	public List<LabExamen> getListExamens() {
		return listExamens;
	}

	public List<LabExamenImag> getListaExamenImagDualSource() {
		return listaExamenImagDualSource;
	}

	public List<LabExamenImag> getListaExamenImagDualTarget() {
		return listaExamenImagDualTarget;
	}

	public DualListModel<LabExamenImag> getListaExamenImagModel() {
		return listaExamenImagModel;
	}

	public List<EDOrdenImagenologia> getListEdOrdenImagenologias() {
		return listEdOrdenImagenologias;
	}

	public List<LabExamenImag> getListExamensImag() {
		return listExamensImag;
	}

	public String getBusqueda() {
		return busqueda;
	}

	public void setGrupoExamen(LabGrupoExamen grupoExamen) {
		this.grupoExamen = grupoExamen;
	}

	public void setGrupoExamenImag(LabGrupoExamenImag grupoExamenImag) {
		this.grupoExamenImag = grupoExamenImag;
	}

	public void setListaExamenDualSource(List<LabExamen> listaExamenDualSource) {
		this.listaExamenDualSource = listaExamenDualSource;
	}

	public void setListaExamenDualTarget(List<LabExamen> listaExamenDualTarget) {
		this.listaExamenDualTarget = listaExamenDualTarget;
	}

	public void setListaExamenModel(DualListModel<LabExamen> listaExamenModel) {
		this.listaExamenModel = listaExamenModel;
	}

	public void setListExamens(List<LabExamen> listExamens) {
		this.listExamens = listExamens;
	}

	public void setListaExamenImagDualSource(
			List<LabExamenImag> listaExamenImagDualSource) {
		this.listaExamenImagDualSource = listaExamenImagDualSource;
	}

	public void setListaExamenImagDualTarget(
			List<LabExamenImag> listaExamenImagDualTarget) {
		this.listaExamenImagDualTarget = listaExamenImagDualTarget;
	}

	public void setListaExamenImagModel(
			DualListModel<LabExamenImag> listaExamenImagModel) {
		this.listaExamenImagModel = listaExamenImagModel;
	}

	public void setListEdOrdenImagenologias(
			List<EDOrdenImagenologia> listEdOrdenImagenologias) {
		this.listEdOrdenImagenologias = listEdOrdenImagenologias;
	}

	public void setListExamensImag(List<LabExamenImag> listExamensImag) {
		this.listExamensImag = listExamensImag;
	}

	public void setBusqueda(String busqueda) {
		this.busqueda = busqueda;
	}

	public List<EDDesgOrdenLabExamen> getListaEDesgGrupo() {
		return listaEDesgLabGrupo;
	}

	public void setListaEDesgGrupo(List<EDDesgOrdenLabExamen> listaEDesgGrupo) {
		this.listaEDesgLabGrupo = listaEDesgGrupo;
	}

	public List<EDDesgOrdenImagExamen> getListaEDesgImagGrupo() {
		return listaEDesgImagGrupo;
	}

	public void setListaEDesgImagGrupo(
			List<EDDesgOrdenImagExamen> listaEDesgImagGrupo) {
		this.listaEDesgImagGrupo = listaEDesgImagGrupo;
	}

	/**
	 * @return the listaEDesgServGrupo
	 */
	public List<EDDesgOrdenServicio> getListaEDesgServGrupo() {
		return listaEDesgServGrupo;
	}

	/**
	 * @param listaEDesgServGrupo
	 *            the listaEDesgServGrupo to set
	 */
	public void setListaEDesgServGrupo(
			List<EDDesgOrdenServicio> listaEDesgServGrupo) {
		this.listaEDesgServGrupo = listaEDesgServGrupo;
	}

	/**
	 * @return the listaServicioDualSource
	 */
	public List<Servicio> getListaServicioDualSource() {
		return listaServicioDualSource;
	}

	/**
	 * @param listaServicioDualSource
	 *            the listaServicioDualSource to set
	 */
	public void setListaServicioDualSource(
			List<Servicio> listaServicioDualSource) {
		this.listaServicioDualSource = listaServicioDualSource;
	}

	/**
	 * @return the listaServicioDualTarget
	 */
	public List<Servicio> getListaServicioDualTarget() {
		return listaServicioDualTarget;
	}

	/**
	 * @param listaServicioDualTarget
	 *            the listaServicioDualTarget to set
	 */
	public void setListaServicioDualTarget(
			List<Servicio> listaServicioDualTarget) {
		this.listaServicioDualTarget = listaServicioDualTarget;
	}

	/**
	 * @return the listaServicioModel
	 */
	public DualListModel<Servicio> getListaServicioModel() {
		return listaServicioModel;
	}

	/**
	 * @param listaServicioModel
	 *            the listaServicioModel to set
	 */
	public void setListaServicioModel(DualListModel<Servicio> listaServicioModel) {
		this.listaServicioModel = listaServicioModel;
	}

	/**
	 * @return the listaEDesgLabGrupo
	 */
	public List<EDDesgOrdenLabExamen> getListaEDesgLabGrupo() {
		return listaEDesgLabGrupo;
	}

	/**
	 * @param listaEDesgLabGrupo
	 *            the listaEDesgLabGrupo to set
	 */
	public void setListaEDesgLabGrupo(
			List<EDDesgOrdenLabExamen> listaEDesgLabGrupo) {
		this.listaEDesgLabGrupo = listaEDesgLabGrupo;
	}

	public boolean isResetTablesS() {
		return resetTablesS;
	}

	public void setResetTablesS(boolean resetTablesS) {
		this.resetTablesS = resetTablesS;
	}

	/**
	 * @return the listaLabGrupo
	 */
	public List<DesgLabGrupo> getListaLabGrupo() {
		return listaLabGrupo;
	}

	/**
	 * @param listaLabGrupo
	 *            the listaLabGrupo to set
	 */
	public void setListaLabGrupo(List<DesgLabGrupo> listaLabGrupo) {
		this.listaLabGrupo = listaLabGrupo;
	}

	/**
	 * @return the listaImagGrupo
	 */
	public List<DesgImagGrupo> getListaImagGrupo() {
		return listaImagGrupo;
	}

	/**
	 * @param listaImagGrupo
	 *            the listaImagGrupo to set
	 */
	public void setListaImagGrupo(List<DesgImagGrupo> listaImagGrupo) {
		this.listaImagGrupo = listaImagGrupo;
	}

	/**
	 * @return the listaServGrupo
	 */
	public List<DesgServGrupo> getListaServGrupo() {
		return listaServGrupo;
	}

	/**
	 * @param listaServGrupo
	 *            the listaServGrupo to set
	 */
	public void setListaServGrupo(List<DesgServGrupo> listaServGrupo) {
		this.listaServGrupo = listaServGrupo;
	}

	/**
	 * @return the servicio
	 */
	public Servicio getServicio() {
		return servicio;
	}

	/**
	 * @param servicio
	 *            the servicio to set
	 */
	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}

	public List<DesgOrdenServicioLabExamen> getLabExamenes() {
		return labExamenes;
	}

	public List<DesgOrdenServicioImagExamen> getImagExamenes() {
		return imagExamenes;
	}

	public List<DesgOrdenServicios> getServicios() {
		return servicios;
	}

	public void setLabExamenes(List<DesgOrdenServicioLabExamen> labExamenes) {
		this.labExamenes = labExamenes;
	}

	public void setImagExamenes(List<DesgOrdenServicioImagExamen> imagExamenes) {
		this.imagExamenes = imagExamenes;
	}

	public void setServicios(List<DesgOrdenServicios> servicios) {
		this.servicios = servicios;
	}

	public List<DesgOrdenServicioLabExamen> getLabExamenesBackup() {
		return labExamenesBackup;
	}

	public List<DesgOrdenServicioImagExamen> getImagExamenesBackup() {
		return imagExamenesBackup;
	}

	public List<DesgOrdenServicios> getServiciosBackup() {
		return serviciosBackup;
	}

	public void setLabExamenesBackup(
			List<DesgOrdenServicioLabExamen> labExamenesBackup) {
		this.labExamenesBackup = labExamenesBackup;
	}

	public void setImagExamenesBackup(
			List<DesgOrdenServicioImagExamen> imagExamenesBackup) {
		this.imagExamenesBackup = imagExamenesBackup;
	}

	public void setServiciosBackup(List<DesgOrdenServicios> serviciosBackup) {
		this.serviciosBackup = serviciosBackup;
	}

	/**
	 * @return the sistemaIndices
	 */
	public ParamSistemaIndices getSistemaIndices() {
		return sistemaIndices;
	}

	/**
	 * @param sistemaIndices
	 *            the sistemaIndices to set
	 */
	public void setSistemaIndices(ParamSistemaIndices sistemaIndices) {
		this.sistemaIndices = sistemaIndices;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

}
