/**
 * 
 */
package com.teds.spaps.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.teds.spaps.enums.Sexo;
import com.teds.spaps.interfaces.dao.IBarrioDao;
import com.teds.spaps.interfaces.dao.ICargoTrabajoDao;
import com.teds.spaps.interfaces.dao.ICompaniaDao;
import com.teds.spaps.interfaces.dao.IDepartamentoDao;
import com.teds.spaps.interfaces.dao.IDocumentoIdentidadDao;
import com.teds.spaps.interfaces.dao.IEstadoCivilDao;
import com.teds.spaps.interfaces.dao.IGrupoFamiliarDao;
import com.teds.spaps.interfaces.dao.IGrupoSanguineoDao;
import com.teds.spaps.interfaces.dao.IIdentificacionIndividuoDao;
import com.teds.spaps.interfaces.dao.IPacienteDao;
import com.teds.spaps.interfaces.dao.IPacienteEmpresaDao;
import com.teds.spaps.interfaces.dao.IPaisDao;
import com.teds.spaps.interfaces.dao.IParamSistemaIndicesDao;
import com.teds.spaps.interfaces.dao.IParentescoDao;
import com.teds.spaps.interfaces.dao.IPlanSeguroDao;
import com.teds.spaps.interfaces.dao.IPlanesSeguroDao;
import com.teds.spaps.interfaces.dao.IProvinciaDao;
import com.teds.spaps.interfaces.dao.IRazaDao;
import com.teds.spaps.interfaces.dao.ISeguroDao;
import com.teds.spaps.interfaces.dao.IUnidadVecinalDao;
import com.teds.spaps.model.Barrio;
import com.teds.spaps.model.Cargo;
import com.teds.spaps.model.CargoTrabajo;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Departamento;
import com.teds.spaps.model.DocumentoIdentidad;
import com.teds.spaps.model.Empresa;
import com.teds.spaps.model.EstadoCivil;
import com.teds.spaps.model.GrupoFamiliar;
import com.teds.spaps.model.GrupoSanguineo;
import com.teds.spaps.model.IdentificacionPaciente;
import com.teds.spaps.model.NivelAcademico;
import com.teds.spaps.model.Paciente;
import com.teds.spaps.model.PacienteEmpresa;
import com.teds.spaps.model.Pais;
import com.teds.spaps.model.ParamSistemaIndices;
import com.teds.spaps.model.Parentesco;
import com.teds.spaps.model.PlanSeguro;
import com.teds.spaps.model.PlanesSeguro;
import com.teds.spaps.model.Provincia;
import com.teds.spaps.model.Raza;
import com.teds.spaps.model.Seguro;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.UnidadVecinal;
import com.teds.spaps.model.Usuario;
import com.teds.spaps.structore.EDVistaPaciente;
import com.teds.spaps.util.DateUtility;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;
import com.teds.spaps.util.Time;

/**
 * @author ANITA
 *
 */
@ManagedBean(name = "pacienteController")
@SessionScoped
public class PacienteController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1967852320159778508L;
	/******* DAO **********/
	private @Inject IPacienteDao pacienteDao;
	private @Inject IDocumentoIdentidadDao documentoIdentidadDao;
	private @Inject IGrupoSanguineoDao grupoSanguineoDao;
	private @Inject IRazaDao razaDao;
	private @Inject IGrupoFamiliarDao grupoFamiliarDao;
	private @Inject IParentescoDao parentescoDao;
	// private @Inject ICargoDao cargoDao;
	private @Inject IPlanSeguroDao planSeguroDao;
	private @Inject SessionMain sessionMain;
	private @Inject IPaisDao paisDao;
	private @Inject IDepartamentoDao departamentoDao;
	private @Inject IProvinciaDao provinciaDao;
	private @Inject IBarrioDao barrioDao;
	private @Inject IUnidadVecinalDao unidadVecinalDao;
	private @Inject IPlanesSeguroDao planesSeguroDao;
	private @Inject ISeguroDao seguroDao;
	private @Inject IIdentificacionIndividuoDao identificacionIndividuoDao;
	private @Inject IEstadoCivilDao estadoCivilDao;
	private @Inject IPacienteEmpresaDao pacienteEmpresaDao;
	private @Inject ICargoTrabajoDao cargoTrabajoDao;
	private @Inject IParamSistemaIndicesDao sistemaIndicesDao;
	private @Inject ICompaniaDao companiaDao;

	/******* OBJECT **********/
	private Paciente paciente;
	private Paciente pacienteSelected;
	private Sucursal sucursal;
	private Raza raza;
	private GrupoSanguineo grupoSanguineo;
	private GrupoFamiliar grupoFamiliar;
	private UnidadVecinal unidadVecinal;
	private Cargo cargo;
	private Barrio barrio;
	private Parentesco parentesco;
	private NivelAcademico nivelAcademico;
	private Provincia provincia;
	private Usuario usuario;
	private DocumentoIdentidad documentoIdentidad;
	private Compania empresa;
	private PlanSeguro planSeguro;
	private Seguro seguro;
	private Pais pais;
	private Departamento departamento;
	private EDVistaPaciente vistaPaciente;
	private StreamedContent content;
	private ParamSistemaIndices sistemaIndices;

	/******* LIST **********/
	private List<Paciente> listaIndividuo;
	private List<PlanesSeguro> planesSeguro;
	public static List<DocumentoIdentidad> listaDocumentoIdentidad;
	private List<GrupoSanguineo> listaGrupoSanguineo;
	private List<GrupoFamiliar> listaGrupoFamiliar;
	private List<Parentesco> listaParentesco;
	private List<Raza> listaRaza;
	private List<Empresa> listaEmpresa;
	private List<Cargo> listaCargo;
	private List<PlanSeguro> listaPlanSeguro;
	private List<PlanSeguro> listaPlanSeguroSelected;
	private List<Pais> listaPais;
	private List<Departamento> listaDepartamento;
	private List<Provincia> listaProvincia;
	private List<Barrio> listaBarrio;
	private List<UnidadVecinal> listaUnidadVecinal;
	private List<PlanesSeguro> listaPlanesSeguros;
	private List<PlanesSeguro> listaPlanesSegurosBackup;
	private List<IdentificacionPaciente> listaIdentificacionIndividuo;
	private List<IdentificacionPaciente> listaIdentificacionIndividuoBackup;
	private List<EDVistaPaciente> vistaPacientes;
	public static List<Paciente> listaPacientes;
	private List<EstadoCivil> listaEstadoCivil;
	private List<PacienteEmpresa> empresas;
	private List<PacienteEmpresa> empresasBackup;
	public static List<Compania> listaEmpresas;
	public static List<CargoTrabajo> cargoTrabajos;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;
	private String estado = "AC";
	private String tipoAfiliacion = "P";// P = particular, S = Seguro
	private boolean titular = false;// false=S, true=N
	private int counter = 0;
	private String telefono = "";
	private String nombreGrupoFamiliar = "";
	private String nombreGrupoSanguineo = "";
	private String nombreRaza = "";
	private String nombrePais = "";
	private String nombreDepartamento = "";
	private String nombreProvincia = "";
	private String nombreBarrio = "";
	private String numeroUV = "";
	private String nombreParentesco = "";
	private String tipoBusqueda = "";
	private String nombreEstadoCivil = "";

	@Inject
	Conversation conversation;

	/**
	 * 
	 */
	public PacienteController() {
	}

	@PostConstruct
	public void initNew() {
		vistaPaciente = new EDVistaPaciente();
		paciente = new Paciente();
		paciente.setEstadoCivil(estadoCivilDao.obtenerEstadoCivil(1));
		nombreEstadoCivil = paciente.getEstadoCivil().getDescripcion();
		paciente.setTipoAfiliacion("P");
		paciente.setTitular("");
		pacienteSelected = new Paciente();
		grupoFamiliar = new GrupoFamiliar();
		parentesco = new Parentesco();
		usuario = sessionMain.getUsuarioLogin();
		sucursal = sessionMain.PruebaSucursal();
		sistemaIndices = sistemaIndicesDao.obtenerPorCompania("AD",
				sucursal.getCompania());
		paciente.setCodigo(sistemaIndices.getPrefijo()
				+ Time.rellendarCeros(sistemaIndices.getNumeroDigitos(),
						sistemaIndices.getCorrelativo()));
		if (usuario.getRol().getNombre().trim()
				.equalsIgnoreCase("SUPER ADMINISTRADOR")) {
			listaIndividuo = pacienteDao.obtenerPorCompania(sucursal
					.getCompania());
		} else {
			listaIndividuo = pacienteDao.obtenerPorSucursal(sucursal);
		}
		planesSeguro = new ArrayList<PlanesSeguro>();
		listaGrupoFamiliar = new ArrayList<GrupoFamiliar>();
		listaParentesco = new ArrayList<Parentesco>();
		empresa = new Compania();
		cargo = new Cargo();
		listaEmpresa = new ArrayList<Empresa>();
		listaCargo = new ArrayList<Cargo>();
		planSeguro = new PlanSeguro();
		listaPlanSeguro = new ArrayList<PlanSeguro>();
		listaPlanSeguroSelected = new ArrayList<PlanSeguro>();
		seguro = new Seguro();
		listaBarrio = new ArrayList<Barrio>();
		listaUnidadVecinal = new ArrayList<UnidadVecinal>();
		listaPlanesSeguros = new ArrayList<PlanesSeguro>();
		listaPlanesSegurosBackup = new ArrayList<PlanesSeguro>();
		listaIdentificacionIndividuo = new ArrayList<IdentificacionPaciente>();
		listaPacientes = new ArrayList<Paciente>();
		listaEstadoCivil = new ArrayList<EstadoCivil>();
		listaEmpresas = new ArrayList<Compania>();
		cargoTrabajos = new ArrayList<CargoTrabajo>();
		empresas = new ArrayList<PacienteEmpresa>();
		empresasBackup = new ArrayList<PacienteEmpresa>();
		vistaPacientes = new ArrayList<EDVistaPaciente>();
		vistaPacientes = cargarDetalle(listaIndividuo);
		counter = 0;
		telefono = "";
		nombreGrupoFamiliar = "";
		nombreGrupoSanguineo = "";
		nombreRaza = "";
		nombrePais = "";
		nombreDepartamento = "";
		nombreProvincia = "";
		nombreBarrio = "";
		numeroUV = "";
		nombreParentesco = "";
		documentoIdentidad = new DocumentoIdentidad();
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
	}

	private byte[] toByteArrayUsingJava(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int reads = is.read();
		while (reads != -1) {
			baos.write(reads);
			reads = is.read();
		}
		return baos.toByteArray();
	}

	public void handleFileUpload(FileUploadEvent event) {
		FacesUtil.infoMessage("Correcto", event.getFile().getFileName()
				+ " fue cargado.");
		try {
			InputStream molde = event.getFile().getInputstream();
			byte[] image = toByteArrayUsingJava(molde);
			FacesUtil.setSessionAttribute("imagePersonal", image);
		} catch (Exception e) {
			FacesUtil.errorMessage("No se pudo subir la imagen.");
		}
	}

	public List<CargoTrabajo> onCompleteCargoTrabajo(String query) {
		String upperQuery = query.toUpperCase().trim();
		cargoTrabajos = cargoTrabajoDao.obtenerPorCompaniaAutoComplete(
				upperQuery, sucursal.getCompania());
		return cargoTrabajos;
	}

	public void onSelectCargoTrabajo(SelectEvent event) {
		paciente.setCargo((CargoTrabajo) event.getObject());
	}

	public List<Paciente> onCompleteFind(String query) {
		String upperQuery = query.toUpperCase().trim();
		if (tipoBusqueda.equals("NP")) {
			listaPacientes = pacienteDao.obtenerIndividuosPorNombreCompania(
					upperQuery, upperQuery, upperQuery, sucursal.getCompania());
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
		listaIndividuo = listaPacientes;
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
		crear = false;
		registrar = false;
		modificar = true;
		paciente = (Paciente) event.getObject();
		nombreEstadoCivil = paciente.getEstadoCivil().getDescripcion();
		tipoAfiliacion = paciente.getTipoAfiliacion();
		listaIndividuo.clear();
		listaIndividuo.add(paciente);
		listaPlanesSeguros = new ArrayList<PlanesSeguro>();
		listaPlanesSeguros = planesSeguroDao.obtenerPorIndividuo(getPaciente());
		listaPlanesSegurosBackup = planesSeguroDao
				.obtenerPorIndividuo(getPaciente());
		listaPlanSeguroSelected = new ArrayList<PlanSeguro>();
		listaIdentificacionIndividuo = identificacionIndividuoDao
				.obtenerPorIndividuo(paciente);
		listaIdentificacionIndividuoBackup = identificacionIndividuoDao
				.obtenerPorIndividuo(paciente);
		empresas = pacienteEmpresaDao.obtenerPorPaciente(paciente);
		empresasBackup = pacienteEmpresaDao.obtenerPorPaciente(paciente);
		if (paciente.getGrupoFamiliar() != null)
			if (paciente.getGrupoFamiliar().getId() != 0) {
				grupoFamiliar = paciente.getGrupoFamiliar();
				nombreGrupoFamiliar = grupoFamiliar.getNombre();
			} else {
				grupoFamiliar = new GrupoFamiliar();
			}
		if (paciente.getParentesco() != null)
			if (paciente.getParentesco().getId() != 0) {
				parentesco = paciente.getParentesco();
				nombreParentesco = parentesco.getDescripcion();
			} else {
				parentesco = new Parentesco();
			}
		if (paciente.getGrupoSanguineo() != null)
			if (paciente.getGrupoSanguineo().getId() != 0) {
				grupoSanguineo = paciente.getGrupoSanguineo();
				nombreGrupoSanguineo = grupoSanguineo.getDescripcion();
			} else {
				grupoSanguineo = new GrupoSanguineo();
			}
		if (paciente.getProvincia() != null)
			if (paciente.getProvincia().getId() != 0) {
				provincia = paciente.getProvincia();
				nombreProvincia = provincia.getNombre();
				departamento = provincia.getDepartamento();
				nombreDepartamento = departamento.getNombre();
				pais = departamento.getPais();
				nombrePais = pais.getNombre();
			} else {
				provincia = new Provincia();
				departamento = new Departamento();
				pais = new Pais();
			}
		if (paciente.getRaza() != null)
			if (paciente.getRaza().getId() != 0) {
				raza = paciente.getRaza();
				nombreRaza = raza.getNombre();
			} else {
				raza = new Raza();
			}
		if (paciente.getBarrio() != null)
			if (paciente.getBarrio().getId() != 0) {
				barrio = paciente.getBarrio();
				nombreBarrio = barrio.getNombre();
			} else {
				barrio = new Barrio();
			}
		if (paciente.getUnidadVecinal() != null)
			if (paciente.getUnidadVecinal().getId() != 0) {
				unidadVecinal = paciente.getUnidadVecinal();
				numeroUV = unidadVecinal.getNumero();
			} else {
				unidadVecinal = new UnidadVecinal();
			}
		FacesUtil.setSessionAttribute("imagePersonal", paciente.getImagen());
		FacesContext.getCurrentInstance().renderResponse();
		resetearFitrosTabla("form001:singleDT");
	}

	public List<EDVistaPaciente> cargarDetalle(List<Paciente> pacientes) {
		List<EDVistaPaciente> resultado = new ArrayList<EDVistaPaciente>();
		for (Paciente paciente1 : pacientes) {
			EDVistaPaciente vistaPaciente = new EDVistaPaciente();
			vistaPaciente.setPaciente(paciente1);
			List<IdentificacionPaciente> documentos = identificacionIndividuoDao
					.obtenerPorIndividuo(paciente1);
			for (IdentificacionPaciente identificacionPaciente : documentos) {
				if (identificacionPaciente.getDocumentoIdentidad().getNombre()
						.equalsIgnoreCase("Carnet de Identidad"))
					vistaPaciente.setDocumentoIdentidad(identificacionPaciente);
			}
			if (vistaPaciente.getDocumentoIdentidad().getId() == 0
					&& documentos.size() == 1)
				vistaPaciente.setDocumentoIdentidad(documentos.get(0));
			if (paciente1.getTipoAfiliacion().equals("S")) {
				vistaPaciente.setSeguros(planesSeguroDao
						.obtenerPorIndividuo(paciente1));
			} else {
				vistaPaciente.setSeguros(new ArrayList<PlanesSeguro>());
			}
			resultado.add(vistaPaciente);
		}
		return resultado;
	}

	public void selectTipoAfiliacion(ValueChangeEvent valueChangeEvent) {
		String newValor = valueChangeEvent.getNewValue().toString().trim();
		paciente.setTitular(newValor);
		if (!paciente.getTitular().equals("T")) {
			paciente.setGrupoFamiliar(new GrupoFamiliar());
			paciente.setParentesco(new Parentesco());
		}
	}

	public void controlTelefono() {
		if (!paciente.getTelefono().trim().isEmpty()) {
			counter++;
		}
		if (counter == 8) {
			paciente.setTelefono(paciente.getTelefono() + "-");
			telefono = "";
			System.out.println("telefono paciente + telefono vista = "
					+ paciente.getTelefono() + " " + telefono);
			counter = 0;
		}
	}

	public List<Barrio> onCompleteBarrio(String query) {
		String upperQuery = query.toUpperCase();
		if (usuario.getRol().getNombre().trim()
				.equalsIgnoreCase("SUPER ADMINISTRADOR")) {
			listaBarrio = barrioDao.obtenerPorCompaniaAutoComplete(upperQuery,
					sucursal.getCompania());
		} else {
			listaBarrio = barrioDao.obtenerPorSucursalAutoComplete(upperQuery,
					sucursal);
		}
		return listaBarrio;
	}

	public void onRowSelectBarrioClick(SelectEvent event) {
		String nombre = event.getObject().toString();
		for (Barrio i : listaBarrio) {
			if (i.getNombre().equals(nombre)) {
				barrio = i;
				FacesUtil
						.infoMessage("Barrio seleccionado:", barrio.toString());
				return;
			}
		}
	}

	public List<UnidadVecinal> onCompleteUnidadVecinal(String query) {
		String upperQuery = query.toUpperCase();
		if (usuario.getRol().getNombre().trim()
				.equalsIgnoreCase("SUPER ADMINISTRADOR")) {
			listaUnidadVecinal = unidadVecinalDao
					.obtenerPorCompaniaAutoComplete(upperQuery,
							sucursal.getCompania());
		} else {
			listaUnidadVecinal = unidadVecinalDao
					.obtenerPorSucursalAutoComplete(upperQuery, sucursal);
		}
		return listaUnidadVecinal;
	}

	public void onRowSelectUnidadVecinalClick(SelectEvent event) {
		String nombre = event.getObject().toString();
		for (UnidadVecinal i : listaUnidadVecinal) {
			if (i.getNumero().equals(nombre)) {
				unidadVecinal = i;
				FacesUtil.infoMessage("Unidad Vecinal seleccionada:",
						unidadVecinal.toString());
				return;
			}
		}
	}

	public List<DocumentoIdentidad> onCompleteDocumentoIdentidad(String query) {
		String upperQuery = query.toUpperCase();
		if (usuario.getRol().getNombre().trim()
				.equalsIgnoreCase("SUPER ADMINISTRADOR")) {
			listaDocumentoIdentidad = documentoIdentidadDao
					.obtenerPorCompaniaAutoComplete(upperQuery,
							sucursal.getCompania());
		} else {
			listaDocumentoIdentidad = documentoIdentidadDao
					.obtenerPorSucursalAutoComplete(upperQuery, sucursal);
		}
		return listaDocumentoIdentidad;
	}

	public void onRowSelectDocumentoIdentidadClick(SelectEvent event) {
		DocumentoIdentidad selected = (DocumentoIdentidad) event.getObject();
		IdentificacionPaciente identificacionPaciente = new IdentificacionPaciente();
		identificacionPaciente.setDocumentoIdentidad(selected);
		if (verificarIdentificacion(identificacionPaciente)) {
			FacesUtil
					.errorMessage("Ya existe el mismo documento de identidad asociado al paciente.");
		} else {
			listaIdentificacionIndividuo.add(identificacionPaciente);
		}
	}

	public List<GrupoSanguineo> onCompleteGrupoSanguineo(String query) {
		String upperQuery = query.toUpperCase();
		if (usuario.getRol().getNombre().trim()
				.equalsIgnoreCase("SUPER ADMINISTRADOR")) {
			listaGrupoSanguineo = grupoSanguineoDao
					.obtenerPorCompaniaAutoComplete(upperQuery,
							sucursal.getCompania());
		} else {
			listaGrupoSanguineo = grupoSanguineoDao
					.obtenerPorSucursalAutoComplete(upperQuery, sucursal);
		}
		return listaGrupoSanguineo;
	}

	public List<EstadoCivil> onCompleteEstadoCivil(String query) {
		String upperQuery = query.toUpperCase();
		if (usuario.getRol().getNombre().trim()
				.equalsIgnoreCase("SUPER ADMINISTRADOR")) {
			listaEstadoCivil = estadoCivilDao.obtenerPorCompaniaAutoComplete(
					upperQuery, sucursal.getCompania());
		} else {
			listaEstadoCivil = estadoCivilDao.obtenerPorSucursalAutoComplete(
					upperQuery, sucursal);
		}
		return listaEstadoCivil;
	}

	public void onRowSelectEstadoCivilClick(SelectEvent event) {
		String nombre = event.getObject().toString();
		for (EstadoCivil i : listaEstadoCivil) {
			if (i.getDescripcion().equals(nombre)) {
				paciente.setEstadoCivil(i);
				return;
			}
		}
	}

	public void onRowSelectGrupoSanguineoClick(SelectEvent event) {
		String nombre = event.getObject().toString();
		for (GrupoSanguineo i : listaGrupoSanguineo) {
			if (i.getDescripcion().equals(nombre)) {
				grupoSanguineo = i;
				FacesUtil.infoMessage("Grupo Sanguineo seleccionado:",
						grupoSanguineo.toString());
				return;
			}
		}
	}

	public List<PlanSeguro> onCompleteSeguro(String query) {
		String upperQuery = query.toUpperCase();
		if (usuario.getRol().getNombre().trim()
				.equalsIgnoreCase("SUPER ADMINISTRADOR")) {
			listaPlanSeguro = planSeguroDao.obtenerPorCompaniaAutoComplete(
					upperQuery, sucursal.getCompania());
		} else {
			listaPlanSeguro = planSeguroDao.obtenerPorSucursalAutoComplete(
					upperQuery, sucursal);
		}
		return listaPlanSeguro;
	}

	public void onRowSelectSeguroClick(SelectEvent event) {
		String nombre = event.getObject().toString().trim();
		String[] protocolo = nombre.split("-");
		String nombreSeguro = protocolo[0].trim();
		String nombrePlanSeguro = protocolo[1].trim();
		System.out.println("event = " + nombre);
		Seguro seguro1 = seguroDao.obtenerSeguroPorNombre(nombreSeguro);
		System.out.println("seguro = " + seguro1.getId() + " "
				+ seguro1.getNombre());
		System.out.println("planseguro = " + nombrePlanSeguro);
		PlanSeguro planSeguro1 = planSeguroDao.obtenerPlanSeguro(
				nombrePlanSeguro, seguro1);
		PlanesSeguro planesSeguro = new PlanesSeguro();
		planesSeguro.setPlanSeguro(planSeguro1);
		if (verificarSeguro(listaPlanesSeguros, planesSeguro)) {
			FacesUtil
					.errorMessage("El plan ya se encuentra asociado al paciente.");
		} else {
			listaPlanesSeguros.add(planesSeguro);
		}
		for (PlanesSeguro i : listaPlanesSeguros) {
			System.out.println("planseguro seleccionado = " + i.getId() + " "
					+ i.getPlanSeguro().getDescripcion());
		}

	}

	public List<Compania> onCompleteEmpresas(String query) {
		String upperQuery = query.toUpperCase();
		listaEmpresas = companiaDao.obtenerPorCompaniaAutoComplete(upperQuery);
		return listaEmpresas;
	}

	public void onRowSelectEmpresasClick(SelectEvent event) {
		Compania empresa = new Compania();
		empresa = (Compania) event.getObject();
		PacienteEmpresa pacienteEmpresa = new PacienteEmpresa();
		pacienteEmpresa.setEmpresa(empresa);
		if (verificarEmpresa(pacienteEmpresa)) {
			FacesUtil.infoMessage("VALIDACION",
					"La empresa ya se encuentra seleccionada.");
		} else {
			empresas.add(pacienteEmpresa);
		}
	}

	public void onRowDeleteEmpresas(PacienteEmpresa empresa) {
		empresas.remove(empresa);
	}

	public boolean verificarEmpresa(PacienteEmpresa pacienteEmpresa) {
		for (int i = 0; i < empresas.size(); i++) {
			if (empresas.get(i).getEmpresa()
					.equals(pacienteEmpresa.getEmpresa()))
				return true;
		}
		return false;
	}

	public void onRowDelete(PlanesSeguro planSeguroSelected) {

	}

	public boolean verificarSeguro(List<PlanesSeguro> planesSeguros,
			PlanesSeguro planesSeguro) {
		for (PlanesSeguro planesSeguro1 : planesSeguros) {
			if (planesSeguro1.getPlanSeguro().equals(
					planesSeguro.getPlanSeguro()))
				return true;
		}
		return false;
	}

	public void onRowEditSeguro(PlanesSeguro planesSeguro) {
	}

	public void onRowDeleteSeguro(PlanesSeguro planesSeguro) {
		listaPlanesSeguros.remove(planesSeguro);
	}

	public void agregarSeguros() {
		for (PlanSeguro i : listaPlanSeguroSelected) {
			System.out.println("planseguro seleccionado = " + i.getId() + " "
					+ i.getDescripcion());
		}
	}

	public List<GrupoFamiliar> onCompleteGrupoFamiliar(String query) {
		String upperQuery = query.toUpperCase();
		if (usuario.getRol().getNombre().trim()
				.equalsIgnoreCase("SUPER ADMINISTRADOR")) {
			listaGrupoFamiliar = grupoFamiliarDao
					.obtenerPorCompaniaAutoComplete(upperQuery,
							sucursal.getCompania());
		} else {
			listaGrupoFamiliar = grupoFamiliarDao
					.obtenerPorSucursalAutoComplete(upperQuery, sucursal);
		}
		return listaGrupoFamiliar;
	}

	public void onRowSelectGrupoFamiliarClick(SelectEvent event) {
		String nombre = event.getObject().toString().trim();
		grupoFamiliar = grupoFamiliarDao.obtenerGrupoFamiliar1(nombre);
		Paciente auxiliar = pacienteDao.obtenerTitularPorFamilia(grupoFamiliar
				.getId());
		listaPlanesSeguros = planesSeguroDao.obtenerPorIndividuo(auxiliar);
		System.out.println("grupo familiar = " + grupoFamiliar.getId());
	}

	public List<Raza> onCompleteRaza(String query) {
		String upperQuery = query.toUpperCase();
		if (usuario.getRol().getNombre().trim()
				.equalsIgnoreCase("SUPER ADMINISTRADOR")) {
			listaRaza = razaDao.obtenerPorCompaniaAutoComplete(upperQuery,
					sucursal.getCompania());
		} else {
			listaRaza = razaDao.obtenerPorSucursalAutoComplete(upperQuery,
					sucursal);
		}
		return listaRaza;
	}

	public void onRowSelectRazaClick(SelectEvent event) {
		String nombre = event.getObject().toString();
		for (Raza i : listaRaza) {
			if (i.getNombre().equals(nombre)) {
				raza = i;
				FacesUtil.infoMessage("Raza seleccionada:", raza.toString());
				return;
			}
		}
	}

	public List<Parentesco> onCompleteParentesco(String query) {
		String upperQuery = query.toUpperCase();
		if (usuario.getRol().getNombre().trim()
				.equalsIgnoreCase("SUPER ADMINISTRADOR")) {
			listaParentesco = parentescoDao.obtenerPorCompaniaAutoComplete(
					upperQuery, sucursal.getCompania());
		} else {
			listaParentesco = parentescoDao.obtenerPorSucursalAutoComplete(
					upperQuery, sucursal);
		}
		return listaParentesco;
	}

	public void onRowSelectParentescoClick(SelectEvent event) {
		String nombre = event.getObject().toString();
		parentesco = parentescoDao.obtenerParentesco(nombre,
				sucursal.getCompania());
		System.out.println("parentesco = " + parentesco.getId());
	}

	public List<Pais> onCompletePais(String query) {
		String upperQuery = query.toUpperCase();
		if (usuario.getRol().getNombre().trim()
				.equalsIgnoreCase("SUPER ADMINISTRADOR")) {
			listaPais = paisDao.obtenerPorCompaniaAutoComplete(upperQuery,
					sucursal.getCompania());
		} else {
			listaPais = paisDao.obtenerPorSucursalAutoComplete(upperQuery,
					sucursal);
		}
		return listaPais;
	}

	public void onRowSelectPaisClick(SelectEvent event) {
		String nombre = event.getObject().toString();
		for (Pais i : listaPais) {
			if (i.getNombre().equals(nombre)) {
				pais = i;
				FacesUtil.infoMessage("Pais seleccionado:", pais.toString());
				return;
			}
		}
	}

	// ONCOMPLETETEXT DEPARTAMENTO
	public List<Departamento> onCompleteDepartamento(String query) {
		String upperQuery = query.toUpperCase();
		if (pais.getId().intValue() == 0) {
			FacesUtil.infoMessage("Error", "Seleccione el Pais");
			return null;
		}
		if (usuario.getRol().getNombre().trim()
				.equalsIgnoreCase("SUPER ADMINISTRADOR")) {
			listaDepartamento = departamentoDao
					.obtenerPorPaisCompaniaAutoComplete(upperQuery, pais,
							sucursal.getCompania());
		} else {
			listaDepartamento = departamentoDao
					.obtenerPorPaisSucursalAutoComplete(upperQuery, pais,
							sucursal);
		}
		return listaDepartamento;
	}

	public void onRowSelectDepartamentoClick(SelectEvent event) {
		String nombre = event.getObject().toString();
		for (Departamento i : listaDepartamento) {
			if (i.getNombre().equals(nombre)) {
				departamento = i;
				FacesUtil.infoMessage("Departamento seleccionado:",
						departamento.toString());
				return;
			}
		}
	}

	public List<Provincia> onCompleteProvincia(String query) {
		String upperQuery = query.toUpperCase();
		if (departamento.getId().intValue() == 0) {
			FacesUtil.infoMessage("Error", "Seleccione el Departamento");
			return null;
		}
		if (usuario.getRol().getNombre().trim()
				.equalsIgnoreCase("SUPER ADMINISTRADOR")) {
			listaProvincia = provinciaDao
					.obtenerPorDepartamentoCompaniaAutoComplete(upperQuery,
							departamento, sucursal.getCompania());
		} else {
			listaProvincia = provinciaDao
					.obtenerPorDepartamentoSucursalAutoComplete(upperQuery,
							departamento, sucursal);
		}
		return listaProvincia;
	}

	public void onRowSelectProvinciaClick(SelectEvent event) {
		String nombre = event.getObject().toString();
		for (Provincia i : listaProvincia) {
			if (i.getNombre().equals(nombre)) {
				provincia = i;
				FacesUtil.infoMessage("Provincia seleccionada:",
						provincia.toString());
				return;
			}
		}
	}

	public void deleteDI(IdentificacionPaciente identificacionPaciente) {
		if (listaIdentificacionIndividuo.size() > 0) {
			for (int i = 0; i < listaIdentificacionIndividuo.size(); i++) {
				IdentificacionPaciente auxiliar = listaIdentificacionIndividuo
						.get(i);
				if (auxiliar.getDocumentoIdentidad().equals(
						identificacionPaciente.getDocumentoIdentidad())) {
					listaIdentificacionIndividuo.remove(i);
				}
			}
			FacesContext.getCurrentInstance().renderResponse();
			resetearFitrosTabla("form001:tabView:formDocumentos:documentos");
		}
	}

	public void onRowEditDI(IdentificacionPaciente identificacionPaciente) {
		if (identificacionIndividuoDao.verificarDocumento(
				sucursal.getCompania(), identificacionPaciente)) {
			FacesUtil
					.fatalMessage("El documento de identidad ya se encuentra registrado.");
			listaIdentificacionIndividuo.remove(identificacionPaciente);
			// deleteDI(identificacionIndividuo);
		}
	}

	public void onRowDeleteDocumento(
			IdentificacionPaciente identificacionPaciente) {
		listaIdentificacionIndividuo.remove(identificacionPaciente);
	}

	public void onRowDeleteDI(IdentificacionPaciente identificacionPaciente) {

	}

	public void actionCancelar() {
		System.out.println("entro a cancelar");
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
		System.out.println("crear " + isCrear());
		System.out.println("modificar " + isModificar());
		System.out.println("registrar " + isRegistrar());
		FacesUtil.removeSessionAttribute("imagePersonal");
		paciente = new Paciente();
		pacienteSelected = new Paciente();
		initNew();
	}

	public void actionNuevo() {
		System.out.println("entro a nuevo");
		setCrear(false);
		setModificar(false);
		setRegistrar(true);
		System.out.println("crear " + isCrear());
		System.out.println("modificar " + isModificar());
		System.out.println("registrar " + isRegistrar());
		paciente = new Paciente();
		pacienteSelected = new Paciente();
		paciente.setEstadoCivil(estadoCivilDao.obtenerEstadoCivil(1));
		nombreEstadoCivil = paciente.getEstadoCivil().getDescripcion();
		paciente.setTipoAfiliacion("P");
		paciente.setTitular("");
		setContent(imageDefault(paciente));
	}

	private StreamedContent imageDefault(Paciente paciente) {
		String url = FacesContext.getCurrentInstance().getExternalContext()
				.getRealPath("/");
		System.out.println("url = " + url);
		String path = "";
		if (paciente.getSexo().toString().trim().equalsIgnoreCase("Masculino")) {
			path = url + "resources/paciente_default.png";
		} else {
			path = url + "resources/usuaria_default.png";
		}
		try {
			content = new DefaultStreamedContent(new FileInputStream(path),
					"image/png");
			return content;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return new DefaultStreamedContent();
		}
	}

	public void selectSexo(ValueChangeEvent changeEvent) {
		Sexo valor = (Sexo) changeEvent.getNewValue();
		paciente.setSexo(valor);
	}

	public void onRowSelect(SelectEvent event) {
		crear = false;
		registrar = false;
		modificar = true;
		paciente = vistaPaciente.getPaciente();
		FacesUtil.setSessionAttribute("imagePersonal", paciente.getImagen());
		nombreEstadoCivil = paciente.getEstadoCivil().getDescripcion();
		tipoAfiliacion = paciente.getTipoAfiliacion();
		listaPlanesSeguros = new ArrayList<PlanesSeguro>();
		listaPlanesSeguros = planesSeguroDao.obtenerPorIndividuo(getPaciente());
		listaPlanesSegurosBackup = planesSeguroDao
				.obtenerPorIndividuo(getPaciente());
		listaPlanSeguroSelected = new ArrayList<PlanSeguro>();
		listaIdentificacionIndividuo = identificacionIndividuoDao
				.obtenerPorIndividuo(paciente);
		listaIdentificacionIndividuoBackup = identificacionIndividuoDao
				.obtenerPorIndividuo(paciente);
		empresas = pacienteEmpresaDao.obtenerPorPaciente(paciente);
		empresasBackup = pacienteEmpresaDao.obtenerPorPaciente(paciente);
		if (paciente.getGrupoFamiliar() != null) {
			if (paciente.getGrupoFamiliar().getId() > 0) {
				grupoFamiliar = paciente.getGrupoFamiliar();
				nombreGrupoFamiliar = grupoFamiliar.getNombre();
			} else {
				grupoFamiliar = new GrupoFamiliar();
				nombreGrupoFamiliar = "";
			}
		} else {
			grupoFamiliar = new GrupoFamiliar();
			nombreGrupoFamiliar = "";
		}
		if (paciente.getParentesco() != null) {
			if (paciente.getParentesco().getId() > 0) {
				parentesco = paciente.getParentesco();
				nombreParentesco = parentesco.getDescripcion();
			} else {
				parentesco = new Parentesco();
				nombreParentesco = "";
			}
		} else {
			parentesco = new Parentesco();
			nombreParentesco = "";
		}
		if (paciente.getGrupoSanguineo() != null) {
			if (paciente.getGrupoSanguineo().getId() > 0) {
				grupoSanguineo = paciente.getGrupoSanguineo();
				nombreGrupoSanguineo = grupoSanguineo.getDescripcion();
			}
		} else {
			grupoSanguineo = new GrupoSanguineo();
			nombreGrupoSanguineo = "";
		}
		if (paciente.getProvincia() != null) {
			if (paciente.getProvincia().getId() != 0) {
				provincia = paciente.getProvincia();
				nombreProvincia = provincia.getNombre();
				departamento = provincia.getDepartamento();
				nombreDepartamento = departamento.getNombre();
				pais = departamento.getPais();
				nombrePais = pais.getNombre();
			}
		} else {
			provincia = new Provincia();
			departamento = new Departamento();
			pais = new Pais();
			nombreProvincia = "";
			nombreDepartamento = "";
			nombrePais = "";
		}
		if (paciente.getRaza() != null) {
			if (paciente.getRaza().getId() != 0) {
				raza = paciente.getRaza();
				nombreRaza = raza.getNombre();
			}
		} else {
			raza = new Raza();
			nombreRaza = "";
		}
		if (paciente.getBarrio() != null) {
			if (paciente.getBarrio().getId() != 0) {
				barrio = paciente.getBarrio();
				nombreBarrio = barrio.getNombre();
			}
		} else {
			barrio = new Barrio();
			nombreBarrio = "";
		}
		if (paciente.getUnidadVecinal() != null) {
			if (paciente.getUnidadVecinal().getId() != 0) {
				unidadVecinal = paciente.getUnidadVecinal();
				numeroUV = unidadVecinal.getNumero();
			}
		} else {
			unidadVecinal = new UnidadVecinal();
			numeroUV = "";
		}
		FacesContext.getCurrentInstance().renderResponse();
		resetearFitrosTabla("form001:singleDT");
	}

	public void resetearFitrosTabla(String id) {
		DataTable table = (DataTable) FacesContext.getCurrentInstance()
				.getViewRoot().findComponent(id);
		table.setSelection(null);
		table.reset();
	}

	public boolean verificarIdentificacion(
			IdentificacionPaciente identificacionPaciente) {
		for (int i = 0; i < listaIdentificacionIndividuo.size(); i++) {
			if (listaIdentificacionIndividuo.get(i).getDocumentoIdentidad()
					.equals(identificacionPaciente.getDocumentoIdentidad()))
				return true;
		}
		return false;
	}

	public boolean verificarIdentificacionDescripcion() {
		for (int i = 0; i < listaIdentificacionIndividuo.size(); i++) {
			if (listaIdentificacionIndividuo.get(i).getDescripcion().isEmpty())
				return true;
		}
		return false;
	}

	public boolean verificarSeguroDescripcion() {
		for (int i = 0; i < listaPlanesSeguros.size(); i++) {
			if (listaPlanesSeguros.get(i).getCodigoAsegurado().isEmpty())
				return true;
		}
		return false;
	}

	public void calcularEdad() {
		paciente.setEdad(DateUtility.calcularEdad(paciente.getFechaNacimiento()));
	}

	public void registrar1() {
		paciente = new Paciente();
		paciente.setApellidoMaterno("Perez");
		paciente.setApellidoPaterno("Perez");
		paciente.setCodigo("11223");
		paciente.setCompania(sucursal.getCompania());
		paciente.setEdad(12);
		paciente.setEstado("AC");
		paciente.setFechaIngreso(new Date());
		paciente.setFechaModificacion(new Date());
		paciente.setFechaNacimiento(new Date());
		paciente.setFechaRegistro(new Date());
		paciente.setNombre("Cesar");
		paciente.setSexo(Sexo.MASCULINO);
		paciente.setSucursal(sucursal);
		paciente.setTipoAfiliacion("P");
		paciente.setUsuarioRegistro("admin");
		pacienteDao.registrar(paciente);
	}

	public void registrar() {
		try {
			if (paciente.getNombre().trim().isEmpty()
					|| paciente.getApellidoPaterno().trim().isEmpty()
					|| paciente.getSexo() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				if (listaIdentificacionIndividuo.isEmpty()) {
					FacesUtil
							.fatalMessage("Debe agregar al menos un documento de identidad para su registro.");
					return;
				} else {
					if (verificarIdentificacionDescripcion()) {
						FacesUtil
								.fatalMessage("Debe ingresar la descripción del documento de identidad para su registro.");
						return;
					} else {
						/*
						 * if (tipoAfiliacion) {
						 * paciente.setTipoAfiliacion("S"); } else {
						 * paciente.setTipoAfiliacion("P"); }
						 */
						if (paciente.getTipoAfiliacion().equals("S"))
							if (listaPlanesSeguros.isEmpty()) {
								FacesUtil
										.fatalMessage("Debe agregar al menos un seguro al paciente por favor.");
								return;
							} else {
								if (verificarSeguroDescripcion()) {
									FacesUtil
											.fatalMessage("Debe ingresar el codigo de asegurado del paciente por favor.");
									return;
								}
							}
						if (paciente.getTipoAfiliacion().equals("E"))
							if (empresas.isEmpty()) {
								FacesUtil
										.fatalMessage("Debe agregar al menos una empresa al paciente por favor.");
								return;
							}
						if (grupoFamiliar != null)
							if (grupoFamiliar.getId() > 0)
								paciente.setGrupoFamiliar(grupoFamiliar);
						if (parentesco != null)
							if (parentesco.getId() > 0)
								paciente.setParentesco(parentesco);
						if (!(grupoSanguineo == null))
							if (grupoSanguineo.getId() > 0)
								paciente.setGrupoSanguineo(grupoSanguineo);
						if (!(raza == null))
							if (raza.getId() > 0)
								paciente.setRaza(raza);
						if (!(provincia == null))
							if (provincia.getId() > 0)
								paciente.setProvincia(provincia);
						if (!(barrio == null))
							if (barrio.getId() > 0)
								paciente.setBarrio(barrio);
							else
								paciente.setBarrio(null);
						if (!(unidadVecinal == null))
							if (unidadVecinal.getId() > 0)
								paciente.setUnidadVecinal(unidadVecinal);
						paciente.setSucursal(getSucursal());
						paciente.setCompania(getSucursal().getCompania());
						paciente.setFechaRegistro(new Date());
						paciente.setFechaModificacion(paciente
								.getFechaRegistro());
						paciente.setFechaIngreso(paciente.getFechaRegistro());
						paciente.setUsuarioRegistro(getUsuario().getLogin());
						paciente.setEstado("AC");
						byte[] image = (byte[]) FacesUtil
								.getSessionAttribute("imagePersonal");
						if (image != null)
							paciente.setImagen(image);
						else {
							System.out.println("sexo = " + paciente.getSexo());
							StreamedContent streamedContent = imageDefault(paciente);
							InputStream inputStream = streamedContent
									.getStream();
							byte[] bs = IOUtils.toByteArray(inputStream);
							paciente.setImagen(bs);
						}
						paciente = pacienteDao.registrar(paciente,
								listaPlanesSeguros, getGrupoFamiliar(),
								getParentesco(), listaIdentificacionIndividuo,
								empresas);
						initNew();
						FacesUtil.removeSessionAttribute("imagePersonal");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error en registro de individuo: "
					+ e.getMessage());
		}
	}

	public void actualizar() {
		try {
			if (paciente.getCodigo().trim().isEmpty()
					|| paciente.getNombre().trim().isEmpty()
					|| paciente.getApellidoPaterno().trim().isEmpty()
					|| paciente.getSexo() == null
					|| paciente.getEstado().trim().isEmpty()) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				if (listaIdentificacionIndividuo.isEmpty()) {
					FacesUtil
							.fatalMessage("Debe agregar al menos un documento de identidad para su registro.");
					return;
				} else {
					if (verificarIdentificacionDescripcion()) {
						FacesUtil
								.fatalMessage("Debe ingresar la descripción del documento de identidad para su registro.");
						return;
					} else {
						if (paciente.getTipoAfiliacion().equals("S"))
							if (listaPlanesSeguros.isEmpty()) {
								FacesUtil
										.fatalMessage("Debe agregar al menos un seguro al paciente por favor.");
								return;
							} else {
								if (verificarSeguroDescripcion()) {
									FacesUtil
											.fatalMessage("Debe ingresar el codigo de asegurado del paciente por favor.");
									return;
								}
							}
						if (paciente.getTipoAfiliacion().equals("E"))
							if (empresas.isEmpty()) {
								FacesUtil
										.fatalMessage("Debe agregar al menos una empresa al paciente por favor.");
								return;
							}
						if (paciente.getTipoAfiliacion().equals("P")) {
							grupoFamiliar = new GrupoFamiliar();
							parentesco = new Parentesco();
						}
						if (grupoFamiliar != null)
							if (grupoFamiliar.getId() > 0)
								paciente.setGrupoFamiliar(grupoFamiliar);
						if (parentesco != null)
							if (parentesco.getId() > 0)
								paciente.setParentesco(parentesco);
						System.out.println("grupo famimliar = "
								+ getGrupoFamiliar().getId());
						System.out.println("parentesco = "
								+ getParentesco().getId());
						if (!(grupoSanguineo == null))
							if (grupoSanguineo.getId() > 0)
								paciente.setGrupoSanguineo(grupoSanguineo);
						if (!(raza == null))
							if (raza.getId() > 0)
								paciente.setRaza(raza);
						if (!(provincia == null))
							if (provincia.getId() > 0)
								paciente.setProvincia(provincia);
						if (!(barrio == null))
							if (barrio.getId() > 0)
								paciente.setBarrio(barrio);
							else
								paciente.setBarrio(null);
						if (!(unidadVecinal == null))
							if (unidadVecinal.getId() > 0)
								paciente.setUnidadVecinal(unidadVecinal);
						paciente.setFechaModificacion(new Date());
						paciente.setUsuarioRegistro(getUsuario().getLogin());
						byte[] image = (byte[]) FacesUtil
								.getSessionAttribute("imagePersonal");
						if (image != null)
							paciente.setImagen(image);
						pacienteDao.modificar(paciente,
								listaPlanesSegurosBackup, listaPlanesSeguros,
								getGrupoFamiliar(), getParentesco(),
								listaIdentificacionIndividuoBackup,
								listaIdentificacionIndividuo, empresasBackup,
								empresas);
						initNew();
						FacesUtil.removeSessionAttribute("imagePersonal");
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de individuo: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			pacienteDao.eliminar(paciente, listaPlanesSeguros,
					listaIdentificacionIndividuo, empresas);
			initNew();
		} catch (Exception e) {
			System.out.println("Error en eliminacion de individuo: "
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

	public void endConversation() {
		if (!conversation.isTransient()) {
			conversation.end();
			System.out.println(">>>>>>>>>> CONVERSACION TERMINADA...");
		}
	}

	public String getNombreGrupoFamiliar() {
		return nombreGrupoFamiliar;
	}

	public void setNombreGrupoFamiliar(String nombreGrupoFamiliar) {
		this.nombreGrupoFamiliar = nombreGrupoFamiliar;
	}

	/**
	 * @return the nombreGrupoSanguineo
	 */
	public String getNombreGrupoSanguineo() {
		return nombreGrupoSanguineo;
	}

	/**
	 * @param nombreGrupoSanguineo
	 *            the nombreGrupoSanguineo to set
	 */
	public void setNombreGrupoSanguineo(String nombreGrupoSanguineo) {
		this.nombreGrupoSanguineo = nombreGrupoSanguineo;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public Paciente getPacienteSelected() {
		return pacienteSelected;
	}

	public void setPacienteSelected(Paciente pacienteSelected) {
		this.pacienteSelected = pacienteSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<Paciente> getListaIndividuo() {
		return listaIndividuo;
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

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaIndividuo(List<Paciente> listaIndividuo) {
		this.listaIndividuo = listaIndividuo;
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

	public Raza getRaza() {
		return raza;
	}

	public GrupoSanguineo getGrupoSanguineo() {
		return grupoSanguineo;
	}

	public GrupoFamiliar getGrupoFamiliar() {
		return grupoFamiliar;
	}

	public UnidadVecinal getUnidadVecinal() {
		return unidadVecinal;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public Barrio getBarrio() {
		return barrio;
	}

	public Parentesco getParentesco() {
		return parentesco;
	}

	public NivelAcademico getNivelAcademico() {
		return nivelAcademico;
	}

	public Provincia getProvincia() {
		return provincia;
	}

	public void setRaza(Raza raza) {
		this.raza = raza;
	}

	public void setGrupoSanguineo(GrupoSanguineo grupoSanguineo) {
		this.grupoSanguineo = grupoSanguineo;
	}

	public void setGrupoFamiliar(GrupoFamiliar grupoFamiliar) {
		this.grupoFamiliar = grupoFamiliar;
	}

	public void setUnidadVecinal(UnidadVecinal unidadVecinal) {
		this.unidadVecinal = unidadVecinal;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public void setBarrio(Barrio barrio) {
		this.barrio = barrio;
	}

	public void setParentesco(Parentesco parentesco) {
		this.parentesco = parentesco;
	}

	public void setNivelAcademico(NivelAcademico nivelAcademico) {
		this.nivelAcademico = nivelAcademico;
	}

	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}

	public List<PlanesSeguro> getPlanesSeguro() {
		return planesSeguro;
	}

	public void setPlanesSeguro(List<PlanesSeguro> planesSeguro) {
		this.planesSeguro = planesSeguro;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getTipoAfiliacion() {
		return tipoAfiliacion;
	}

	public void setTipoAfiliacion(String tipoAfiliacion) {
		this.tipoAfiliacion = tipoAfiliacion;
	}

	/**
	 * @return the listaDocumentoIdentidad
	 */
	public List<DocumentoIdentidad> getListaDocumentoIdentidad() {
		return listaDocumentoIdentidad;
	}

	/**
	 * @param listaDocumentoIdentidad
	 *            the listaDocumentoIdentidad to set
	 */
	public void setListaDocumentoIdentidad(
			List<DocumentoIdentidad> listaDocumentoIdentidad) {
		PacienteController.listaDocumentoIdentidad = listaDocumentoIdentidad;
	}

	/**
	 * @return the documentoIdentidad
	 */
	public DocumentoIdentidad getDocumentoIdentidad() {
		return documentoIdentidad;
	}

	/**
	 * @param documentoIdentidad
	 *            the documentoIdentidad to set
	 */
	public void setDocumentoIdentidad(DocumentoIdentidad documentoIdentidad) {
		this.documentoIdentidad = documentoIdentidad;
	}

	/**
	 * @return the listaGrupoSanguineo
	 */
	public List<GrupoSanguineo> getListaGrupoSanguineo() {
		return listaGrupoSanguineo;
	}

	/**
	 * @param listaGrupoSanguineo
	 *            the listaGrupoSanguineo to set
	 */
	public void setListaGrupoSanguineo(List<GrupoSanguineo> listaGrupoSanguineo) {
		this.listaGrupoSanguineo = listaGrupoSanguineo;
	}

	/**
	 * @return the listaRaza
	 */
	public List<Raza> getListaRaza() {
		return listaRaza;
	}

	/**
	 * @param listaRaza
	 *            the listaRaza to set
	 */
	public void setListaRaza(List<Raza> listaRaza) {
		this.listaRaza = listaRaza;
	}

	public boolean isTitular() {
		return titular;
	}

	public void setTitular(boolean titular) {
		this.titular = titular;
	}

	public List<GrupoFamiliar> getListaGrupoFamiliar() {
		return listaGrupoFamiliar;
	}

	public void setListaGrupoFamiliar(List<GrupoFamiliar> listaGrupoFamiliar) {
		this.listaGrupoFamiliar = listaGrupoFamiliar;
	}

	public List<Parentesco> getListaParentesco() {
		return listaParentesco;
	}

	public void setListaParentesco(List<Parentesco> listaParentesco) {
		this.listaParentesco = listaParentesco;
	}

	public Compania getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Compania empresa) {
		this.empresa = empresa;
	}

	public List<Empresa> getListaEmpresa() {
		return listaEmpresa;
	}

	public void setListaEmpresa(List<Empresa> listaEmpresa) {
		this.listaEmpresa = listaEmpresa;
	}

	public List<Cargo> getListaCargo() {
		return listaCargo;
	}

	public void setListaCargo(List<Cargo> listaCargo) {
		this.listaCargo = listaCargo;
	}

	public PlanSeguro getPlanSeguro() {
		return planSeguro;
	}

	public void setPlanSeguro(PlanSeguro planSeguro) {
		this.planSeguro = planSeguro;
	}

	public List<PlanSeguro> getListaPlanSeguro() {
		return listaPlanSeguro;
	}

	public void setListaPlanSeguro(List<PlanSeguro> listaPlanSeguro) {
		this.listaPlanSeguro = listaPlanSeguro;
	}

	public List<PlanSeguro> getListaPlanSeguroSelected() {
		return listaPlanSeguroSelected;
	}

	public void setListaPlanSeguroSelected(
			List<PlanSeguro> listaPlanSeguroSelected) {
		this.listaPlanSeguroSelected = listaPlanSeguroSelected;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public List<Pais> getListaPais() {
		return listaPais;
	}

	public void setListaPais(List<Pais> listaPais) {
		this.listaPais = listaPais;
	}

	public List<Departamento> getListaDepartamento() {
		return listaDepartamento;
	}

	public void setListaDepartamento(List<Departamento> listaDepartamento) {
		this.listaDepartamento = listaDepartamento;
	}

	public List<Provincia> getListaProvincia() {
		return listaProvincia;
	}

	public void setListaProvincia(List<Provincia> listaProvincia) {
		this.listaProvincia = listaProvincia;
	}

	public Seguro getSeguro() {
		return seguro;
	}

	public void setSeguro(Seguro seguro) {
		this.seguro = seguro;
	}

	public List<Barrio> getListaBarrio() {
		return listaBarrio;
	}

	public void setListaBarrio(List<Barrio> listaBarrio) {
		this.listaBarrio = listaBarrio;
	}

	public List<UnidadVecinal> getListaUnidadVecinal() {
		return listaUnidadVecinal;
	}

	public void setListaUnidadVecinal(List<UnidadVecinal> listaUnidadVecinal) {
		this.listaUnidadVecinal = listaUnidadVecinal;
	}

	public List<PlanesSeguro> getListaPlanesSeguros() {
		return listaPlanesSeguros;
	}

	public void setListaPlanesSeguros(List<PlanesSeguro> listaPlanesSeguros) {
		this.listaPlanesSeguros = listaPlanesSeguros;
	}

	public List<IdentificacionPaciente> getListaIdentificacionIndividuo() {
		return listaIdentificacionIndividuo;
	}

	public void setListaIdentificacionIndividuo(
			List<IdentificacionPaciente> listaIdentificacionIndividuo) {
		this.listaIdentificacionIndividuo = listaIdentificacionIndividuo;
	}

	public List<PlanesSeguro> getListaPlanesSegurosBackup() {
		return listaPlanesSegurosBackup;
	}

	public void setListaPlanesSegurosBackup(
			List<PlanesSeguro> listaPlanesSegurosBackup) {
		this.listaPlanesSegurosBackup = listaPlanesSegurosBackup;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * @return the nombreRaza
	 */
	public String getNombreRaza() {
		return nombreRaza;
	}

	/**
	 * @param nombreRaza
	 *            the nombreRaza to set
	 */
	public void setNombreRaza(String nombreRaza) {
		this.nombreRaza = nombreRaza;
	}

	/**
	 * @return the nombrePais
	 */
	public String getNombrePais() {
		return nombrePais;
	}

	/**
	 * @param nombrePais
	 *            the nombrePais to set
	 */
	public void setNombrePais(String nombrePais) {
		this.nombrePais = nombrePais;
	}

	/**
	 * @return the nombreDepartamento
	 */
	public String getNombreDepartamento() {
		return nombreDepartamento;
	}

	/**
	 * @param nombreDepartamento
	 *            the nombreDepartamento to set
	 */
	public void setNombreDepartamento(String nombreDepartamento) {
		this.nombreDepartamento = nombreDepartamento;
	}

	/**
	 * @return the nombreProvincia
	 */
	public String getNombreProvincia() {
		return nombreProvincia;
	}

	/**
	 * @param nombreProvincia
	 *            the nombreProvincia to set
	 */
	public void setNombreProvincia(String nombreProvincia) {
		this.nombreProvincia = nombreProvincia;
	}

	/**
	 * @return the nombreBarrio
	 */
	public String getNombreBarrio() {
		return nombreBarrio;
	}

	/**
	 * @param nombreBarrio
	 *            the nombreBarrio to set
	 */
	public void setNombreBarrio(String nombreBarrio) {
		this.nombreBarrio = nombreBarrio;
	}

	/**
	 * @return the numeroUV
	 */
	public String getNumeroUV() {
		return numeroUV;
	}

	/**
	 * @param numeroUV
	 *            the numeroUV to set
	 */
	public void setNumeroUV(String numeroUV) {
		this.numeroUV = numeroUV;
	}

	/**
	 * @return the nombreParentesco
	 */
	public String getNombreParentesco() {
		return nombreParentesco;
	}

	/**
	 * @param nombreParentesco
	 *            the nombreParentesco to set
	 */
	public void setNombreParentesco(String nombreParentesco) {
		this.nombreParentesco = nombreParentesco;
	}

	/**
	 * @return the listaIdentificacionIndividuoBackup
	 */
	public List<IdentificacionPaciente> getListaIdentificacionIndividuoBackup() {
		return listaIdentificacionIndividuoBackup;
	}

	/**
	 * @param listaIdentificacionIndividuoBackup
	 *            the listaIdentificacionIndividuoBackup to set
	 */
	public void setListaIdentificacionIndividuoBackup(
			List<IdentificacionPaciente> listaIdentificacionIndividuoBackup) {
		this.listaIdentificacionIndividuoBackup = listaIdentificacionIndividuoBackup;
	}

	public List<EDVistaPaciente> getVistaPacientes() {
		return vistaPacientes;
	}

	public void setVistaPacientes(List<EDVistaPaciente> vistaPacientes) {
		this.vistaPacientes = vistaPacientes;
	}

	public EDVistaPaciente getVistaPaciente() {
		return vistaPaciente;
	}

	public void setVistaPaciente(EDVistaPaciente vistaPaciente) {
		this.vistaPaciente = vistaPaciente;
	}

	/**
	 * @return the listaPacientes
	 */
	public static List<Paciente> getListaPacientes() {
		return listaPacientes;
	}

	/**
	 * @param listaPacientes
	 *            the listaPacientes to set
	 */
	public static void setListaPacientes(List<Paciente> listaPacientes) {
		PacienteController.listaPacientes = listaPacientes;
	}

	/**
	 * @return the tipoBusqueda
	 */
	public String getTipoBusqueda() {
		return tipoBusqueda;
	}

	/**
	 * @param tipoBusqueda
	 *            the tipoBusqueda to set
	 */
	public void setTipoBusqueda(String tipoBusqueda) {
		this.tipoBusqueda = tipoBusqueda;
	}

	public String getNombreEstadoCivil() {
		return nombreEstadoCivil;
	}

	public void setNombreEstadoCivil(String nombreEstadoCivil) {
		this.nombreEstadoCivil = nombreEstadoCivil;
	}

	public List<EstadoCivil> getListaEstadoCivil() {
		return listaEstadoCivil;
	}

	public void setListaEstadoCivil(List<EstadoCivil> listaEstadoCivil) {
		this.listaEstadoCivil = listaEstadoCivil;
	}

	public List<Compania> getListaEmpresas() {
		return listaEmpresas;
	}

	public void setListaEmpresas(List<Compania> listaEmpresas) {
		PacienteController.listaEmpresas = listaEmpresas;
	}

	public List<PacienteEmpresa> getEmpresasBackup() {
		return empresasBackup;
	}

	public void setEmpresasBackup(List<PacienteEmpresa> empresasBackup) {
		this.empresasBackup = empresasBackup;
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
	 * @return the cargoTrabajos
	 */
	public List<CargoTrabajo> getCargoTrabajos() {
		return cargoTrabajos;
	}

	/**
	 * @param cargoTrabajos
	 *            the cargoTrabajos to set
	 */
	public void setCargoTrabajos(List<CargoTrabajo> cargoTrabajos) {
		PacienteController.cargoTrabajos = cargoTrabajos;
	}

	/**
	 * @return the content
	 */
	public StreamedContent getContent() {
		FacesContext context = FacesContext.getCurrentInstance();

		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			// So, we're rendering the HTML. Return a stub StreamedContent so
			// that it will generate right URL.
			return new DefaultStreamedContent();
		} else {
			// So, browser is requesting the image. Return a real
			// StreamedContent with the image bytes.
			String id = context.getExternalContext().getRequestParameterMap()
					.get("param");
			Paciente paciente = pacienteDao.obtenerIndividuo(Integer
					.valueOf(id));
			if (paciente != null) {
				return new DefaultStreamedContent(new ByteArrayInputStream(
						paciente.getImagen()));
			} else {
				return new DefaultStreamedContent();
			}
		}
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(StreamedContent content) {
		this.content = content;
	}

	public ParamSistemaIndices getSistemaIndices() {
		return sistemaIndices;
	}

	public void setSistemaIndices(ParamSistemaIndices sistemaIndices) {
		this.sistemaIndices = sistemaIndices;
	}

}
