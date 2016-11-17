package com.teds.spaps.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.teds.spaps.interfaces.dao.IAntecedenteDao;
import com.teds.spaps.interfaces.dao.IConsultaDao;
import com.teds.spaps.interfaces.dao.IContrareferenciaDao;
import com.teds.spaps.interfaces.dao.IDesgOrdenServicioDao;
import com.teds.spaps.interfaces.dao.IDesgOrdenServicioImagExamenDao;
import com.teds.spaps.interfaces.dao.IDesgOrdenServicioLabExamenDao;
import com.teds.spaps.interfaces.dao.IDesgOrdenServiciosDao;
import com.teds.spaps.interfaces.dao.IDetalleAntecedenteDao;
import com.teds.spaps.interfaces.dao.IDetalleAntecedenteEspecialidadDao;
import com.teds.spaps.interfaces.dao.IDetalleDiagnosticoDao;
import com.teds.spaps.interfaces.dao.IDetalleDiagnosticoEspecialidadDao;
import com.teds.spaps.interfaces.dao.IDetalleMedicamentoDao;
import com.teds.spaps.interfaces.dao.IDetalleTipoAntecedenteDao;
import com.teds.spaps.interfaces.dao.IDetalleTipoDiagnosticoDao;
import com.teds.spaps.interfaces.dao.IDiagnosticoDao;
import com.teds.spaps.interfaces.dao.IDiagnosticoPresuntivoDao;
import com.teds.spaps.interfaces.dao.IEnfermedadDao;
import com.teds.spaps.interfaces.dao.IEspecialidadDao;
import com.teds.spaps.interfaces.dao.IEvolucionDao;
import com.teds.spaps.interfaces.dao.IExamenFisicoSegmentarioDao;
import com.teds.spaps.interfaces.dao.IExamenFisicoSignosVitalesDao;
import com.teds.spaps.interfaces.dao.IHistoriaClinicaDao;
import com.teds.spaps.interfaces.dao.ILabExamenDao;
import com.teds.spaps.interfaces.dao.ILabExamenImagDao;
import com.teds.spaps.interfaces.dao.ILabGrupoExamenDao;
import com.teds.spaps.interfaces.dao.ILabGrupoExamenImagDao;
import com.teds.spaps.interfaces.dao.ILabOrdenDao;
import com.teds.spaps.interfaces.dao.ILabOrdenImagDao;
import com.teds.spaps.interfaces.dao.ILabOrdenSubDetalleDao;
import com.teds.spaps.interfaces.dao.IMedicamentoDao;
import com.teds.spaps.interfaces.dao.IMotivoDao;
import com.teds.spaps.interfaces.dao.IPacienteDao;
import com.teds.spaps.interfaces.dao.IParamSistemaIndicesDao;
import com.teds.spaps.interfaces.dao.IPersonalDao;
import com.teds.spaps.interfaces.dao.ISucursalDao;
import com.teds.spaps.interfaces.dao.ITipoAntecedenteDao;
import com.teds.spaps.interfaces.dao.ITipoDiagnosticoDao;
import com.teds.spaps.interfaces.dao.ITransferenciaDao;
import com.teds.spaps.model.Antecedente;
import com.teds.spaps.model.Consulta;
import com.teds.spaps.model.Contrareferencia;
import com.teds.spaps.model.DesgOrdenServicio;
import com.teds.spaps.model.DetalleAntecedente;
import com.teds.spaps.model.DetalleAntecedenteEspecialidad;
import com.teds.spaps.model.DetalleDiagnostico;
import com.teds.spaps.model.DetalleDiagnosticoEspecialidad;
import com.teds.spaps.model.DetalleMedicamento;
import com.teds.spaps.model.DetalleTipoAntecedente;
import com.teds.spaps.model.DetalleTipoDiagnostico;
import com.teds.spaps.model.Diagnostico;
import com.teds.spaps.model.DiagnosticoPresuntivo;
import com.teds.spaps.model.Enfermedad;
import com.teds.spaps.model.Especialidad;
import com.teds.spaps.model.Evolucion;
import com.teds.spaps.model.ExamenFisicoSegmentario;
import com.teds.spaps.model.ExamenFisicoSignosVitales;
import com.teds.spaps.model.Frecuencia;
import com.teds.spaps.model.HCLabOrdenDocumentos;
import com.teds.spaps.model.HistoriaClinica;
import com.teds.spaps.model.LabExamen;
import com.teds.spaps.model.LabExamenImag;
import com.teds.spaps.model.LabGrupoExamen;
import com.teds.spaps.model.LabGrupoExamenImag;
import com.teds.spaps.model.LabOrden;
import com.teds.spaps.model.LabOrdenDetalle;
import com.teds.spaps.model.LabOrdenDetalleImag;
import com.teds.spaps.model.LabOrdenImag;
import com.teds.spaps.model.LabOrdenSubDetalle;
import com.teds.spaps.model.Medicamento;
import com.teds.spaps.model.Motivo;
import com.teds.spaps.model.Paciente;
import com.teds.spaps.model.ParamSistemaIndices;
import com.teds.spaps.model.Personal;
import com.teds.spaps.model.Presentacion;
import com.teds.spaps.model.Servicio;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.TipoAntecedente;
import com.teds.spaps.model.TipoDiagnostico;
import com.teds.spaps.model.Transferencia;
import com.teds.spaps.model.Usuario;
import com.teds.spaps.model.Via;
import com.teds.spaps.structore.EDDesgOrdenServicioHC;
import com.teds.spaps.structore.EDOrdenImagenologia;
import com.teds.spaps.structore.EDOrdenLaboratorio;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;
import com.teds.spaps.util.Time;

/**
 * 
 * @author mauriciobejaranorivera
 *
 */
@ManagedBean(name = "historiaClinicaDetalleController")
@SessionScoped
public class HistoriaClinicaDetalleController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5557112566613590119L;
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
	private @Inject IDetalleDiagnosticoDao detalleDiagnosticoDao;
	private @Inject ITipoDiagnosticoDao tipoDiagnosticoDao;
	private @Inject IDetalleTipoDiagnosticoDao detalleTipoDiagnosticoDao;
	private @Inject IDiagnosticoDao diagnosticoDao;
	private @Inject IDetalleDiagnosticoEspecialidadDao detalleDiagnosticoEspecialidadDao;
	private @Inject ITipoAntecedenteDao tipoAntecedenteDao;
	private @Inject IDetalleTipoAntecedenteDao detalleTipoAntecedenteDao;
	private @Inject ISucursalDao sucursalDao;
	private @Inject IDetalleMedicamentoDao detalleMedicamentoDao;
	private @Inject IMedicamentoDao medicamentoDao;
	private @Inject ITransferenciaDao transferenciaDao;
	private @Inject IEvolucionDao evolucionDao;
	private @Inject IContrareferenciaDao contrareferenciaDao;
	private @Inject IExamenFisicoSegmentarioDao examenFisicoSegmentarioDao;
	private @Inject IExamenFisicoSignosVitalesDao examenFisicoSignosVitalesDao;
	private @Inject ILabOrdenDao labOrdenDao;
	private @Inject ILabOrdenSubDetalleDao labOrdenSubDetalleDao;
	private @Inject IParamSistemaIndicesDao sistemaIndicesDao;
	private @Inject ILabExamenDao examenDao;
	private @Inject IConsultaDao consultaDao;
	private @Inject ILabGrupoExamenDao grupoExamenDao;
	private @Inject IDiagnosticoPresuntivoDao diagnosticoPresuntivoDao;
	private @Inject ILabOrdenImagDao labOrdenImagDao;
	private @Inject ILabGrupoExamenImagDao grupoExamenImagDao;
	private @Inject ILabExamenImagDao examenImagDao;
	private @Inject IDesgOrdenServicioDao desgOrdenServicioDao;
	private @Inject IDesgOrdenServicioLabExamenDao desgOrdenServicioLabExamenDao;
	private @Inject IDesgOrdenServicioImagExamenDao desgOrdenServicioImagExamenDao;
	private @Inject IDesgOrdenServiciosDao desgOrdenServiciosDao;

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
	private ExamenFisicoSignosVitales examenFisicoSignosVitales;
	private ExamenFisicoSignosVitales examenFisicoSignosVitalesSelected;
	private ExamenFisicoSegmentario examenFisico;
	private ExamenFisicoSegmentario examenFisicoSelected;
	private Diagnostico diagnostico;
	private Diagnostico diagnosticoDetalle;
	private Diagnostico diagnosticoDetalleSelected;
	private TipoDiagnostico tipoDiagnostico;
	private DetalleDiagnostico detalleDiagnostico;
	private DetalleDiagnostico detalleDiagnosticoSelected;
	private Antecedente antecedenteDetalle;
	private Antecedente antecedenteDetalleSelected;
	private TipoAntecedente tipoAntecedente;
	private DetalleMedicamento detalleMedicamento;
	private DetalleMedicamento detalleMedicamentoSelected;
	private Sucursal sucursalSelected;
	private Frecuencia frecuencia;
	private Via via;
	private Presentacion presentacion;
	private Transferencia transferencia;
	private Transferencia transferenciaSelected;
	private Evolucion evolucion;
	private Evolucion evolucionSelected;
	private Contrareferencia contrareferencia;
	private Contrareferencia contrareferenciaSelected;
	private Transferencia transferenciaToContra;
	private LabOrden orden;
	private LabOrden ordenSelected;
	private Medicamento medicamento;
	private Consulta consulta;
	private LabGrupoExamen grupoExamen;
	private DiagnosticoPresuntivo diagnosticoPresuntivo;
	private DiagnosticoPresuntivo diagnosticoPresuntivoSelected;
	private LabOrdenImag labOrden;
	private LabOrdenImag labOrdenSelected;
	// private LabTipoExamenImag tipoExamen;
	private LabGrupoExamenImag grupoExamenImag;
	private DesgOrdenServicio ordenServicio;
	private EDDesgOrdenServicioHC vistaOrdenServicioSelected;
	@SuppressWarnings("unused")
	private StreamedContent content;

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
	private List<ExamenFisicoSignosVitales> listaExamenFisicos = new ArrayList<ExamenFisicoSignosVitales>();
	private List<DetalleDiagnostico> listaDetalleDiagnosticos = new ArrayList<DetalleDiagnostico>();
	private List<TipoDiagnostico> listaTipoDiagnosticos = new ArrayList<TipoDiagnostico>();
	private DualListModel<String> listaDiagnosticosDual;
	private List<String> listaDiagnosticosDualSource = new ArrayList<String>();
	private List<String> listaDiagnosticosDualTarget = new ArrayList<String>();
	private List<Diagnostico> listaDiagnosticos = new ArrayList<Diagnostico>();
	private List<Especialidad> listaEspecialidadDiagnosticos = new ArrayList<Especialidad>();
	private List<TipoAntecedente> listaTipoAntecedentes = new ArrayList<TipoAntecedente>();
	private List<Sucursal> listaSucursales = new ArrayList<Sucursal>();
	private List<DetalleMedicamento> listaDetalleMedicamentos = new ArrayList<DetalleMedicamento>();
	private List<Frecuencia> listaFrecuencias = new ArrayList<Frecuencia>();
	private List<Via> listaVias = new ArrayList<Via>();
	private List<Presentacion> listaPresentaciones = new ArrayList<Presentacion>();
	private List<Medicamento> listaMedicamentos = new ArrayList<Medicamento>();
	private List<Transferencia> listaTransferencias = new ArrayList<Transferencia>();
	private List<DetalleMedicamento> listaUltimaReceta = new ArrayList<DetalleMedicamento>();
	private List<Evolucion> listaEvoluciones = new ArrayList<Evolucion>();
	private List<Contrareferencia> listaContrareferencias = new ArrayList<Contrareferencia>();
	private List<ExamenFisicoSegmentario> listaExaamenFisicos = new ArrayList<ExamenFisicoSegmentario>();
	private List<LabOrden> listaOrdenes = new ArrayList<LabOrden>();
	private List<LabOrdenImag> listaOrdenesImag = new ArrayList<LabOrdenImag>();
	private List<LabOrdenDetalle> listaDetalleOrden = new ArrayList<LabOrdenDetalle>();
	private List<LabExamen> listaExamenDualSource = new ArrayList<LabExamen>();
	private List<LabExamen> listaExamenDualTarget = new ArrayList<LabExamen>();
	private DualListModel<LabExamen> listaExamenModel = new DualListModel<>(
			listaExamenDualSource, listaExamenDualTarget);
	private List<EDOrdenLaboratorio> listEdOrdenLaboratorios = new ArrayList<EDOrdenLaboratorio>();
	private List<LabExamen> listExamens = new ArrayList<LabExamen>();
	private List<LabOrden> listaLabOrden = new ArrayList<LabOrden>();
	private List<LabOrdenDetalle> listOrdenDetalles = new ArrayList<LabOrdenDetalle>();
	private List<LabGrupoExamen> listGrupoExamens;
	private List<DiagnosticoPresuntivo> listaDiagnosticosPresuntivos;
	private List<LabOrdenImag> listaLabOrdenImag;
	private List<LabGrupoExamenImag> listGrupoExamensImag = new ArrayList<LabGrupoExamenImag>();
	private List<LabExamenImag> listExamensImag = new ArrayList<LabExamenImag>();
	private List<LabOrdenDetalleImag> listOrdenDetallesImag = new ArrayList<LabOrdenDetalleImag>();
	private List<EDOrdenImagenologia> listEdOrdenImagenologias = new ArrayList<EDOrdenImagenologia>();
	private DualListModel<LabExamenImag> listaExamenImagModel;
	private List<LabExamenImag> listaExamenImagDualSource = new ArrayList<LabExamenImag>();
	private List<LabExamenImag> listaExamenImagDualTarget = new ArrayList<LabExamenImag>();
	private List<DesgOrdenServicio> ordenesServicio;
	private List<EDDesgOrdenServicioHC> listaVistaOrdenServicioSelected;
	private List<DesgOrdenServicio> ordenServicios;
	private List<Especialidad> listaEspecialidad = new ArrayList<Especialidad>();
	public static List<HCLabOrdenDocumentos> documentos;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;
	private boolean seleccionado = false;
	private boolean motivoSeleccionado = false;
	private boolean enfermedadSeleccionado = false;
	private boolean antecedenteSeleccionado = false;
	private boolean examenFisicoSeleccionado = false;
	private boolean detalleDiagnosticoSeleccionado = false;
	private boolean detalleAntecedenteSeleccionado = false;
	private String comentario = "";
	private String query = "";
	private boolean medico = true;
	private boolean tipoBusqueda = true;
	private boolean detalleMedicamentoSeleccionado = false;
	private boolean crearReceta = true;
	private Integer receta = 0;
	private boolean transferenciaSeleccionada = false;
	private boolean evolucionSeleccionada = false;
	private boolean banderaTransferencia = true;
	private boolean contrareferenciaSeleccionada = false;
	private boolean ordenSeleccionada = false;
	private String descripcionAntecedente = "";
	private String descripcionDiagnostico = "";
	private String busqueda = "";
	private String busquedaImag = "";
	private Date fechaOrden;
	private boolean seeEstudio = false;
	private String banderaEstudio = "LAB";
	private boolean diagnosticoPresuntivoSeleccionado = false;
	private boolean banderaDiagnostico = true;

	// VAR
	private String currentPage = "/pages/historias_clinicas/registro_historias_clinicas/list.xhtml";
	private String pagina = "";
	private String currentPage1 = "/pages/historias_clinicas/registro_detalle_historias_clinicas/antecedentes.xhtml";

	@Inject
	private FacesContext facesContext;
	private HttpServletRequest request;

	private StreamedContent streamPdf;

	@PostConstruct
	public void initNew() {
		usuario = sessionMain.getUsuarioLogin();
		sucursal = sessionMain.PruebaSucursal();
		System.out.println("Sucursal : " + sucursal.getDescripcion());
		System.out.println("Compañia : "
				+ sucursal.getCompania().getDescripcion());
		selectedMedico = new Personal();
		selectedPaciente = new Paciente();
		currentPage1 = "/pages/historias_clinicas/registro_detalle_historias_clinicas/antecedentes.xhtml";
		defaultValues();
		consulta = sessionMain.getConsulta();
		if ((!consulta.equals(null) || consulta.getId() > 0)
				&& consultaDao.tieneConsultaPaciente(selectedPaciente,
						sucursal.getCompania())) {
			consulta = consultaDao.obtenerConsultaActualDePaciente(
					selectedPaciente, sucursal.getCompania());
			sessionMain.setConsulta(consulta);
		} else {
			FacesUtil.infoMessage("Consulta",
					"Por favor debe crear una consulta antes");
		}
	}

	private void defaultValues() {
		// pagina = "";
		setCrear(true);
		setModificar(false);
		setRegistrar(false);

		query = "";
		seleccionado = false;
		comentario = "";

		historiaMedicaSelected = new HistoriaClinica();

		obtenerVariableExternos();
		historiaClinica = historiaClinicaDao
				.obtenerPorPaciente(selectedPaciente);

		listaHistoriaMedica = historiaClinicaDao.obtenerPorCompania(sucursal
				.getCompania());

		medico = selectedMedico.getCargo().getDescripcion()
				.equalsIgnoreCase("medico");
	}

	public void handleFileUpload(FileUploadEvent event) {
		FacesMessage msg = new FacesMessage("Succesful", event.getFile()
				.getFileName());
		try {
			FacesContext contexto = FacesContext.getCurrentInstance();
			String ruta = getRutaParam(contexto);
			System.out.println("ruta : " + ruta);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			UploadedFile file = event.getFile();
			InputStream molde = file.getInputstream();
			byte[] image = toByteArrayUsingJava(molde);
			FacesUtil.setSessionAttribute("imageProducto", image);
			HCLabOrdenDocumentos documento = new HCLabOrdenDocumentos();
			documento.setArchivo(image);
			documento.setNombreArchivo(file.getFileName());
			documentos.add(documento);
			System.out.println("documento id = " + documento.getId());
			System.out.println("documento archivo = "
					+ documento.getArchivo().toString());
			// listArchivos.add(new EDFiles(file, "", false));
		} catch (Exception e) {
			FacesUtil.errorMessage("No se pudo subir la imagen.");
		}
	}

	public String getRutaParam(FacesContext contexto) {
		Map<String, String> params = contexto.getExternalContext()
				.getRequestParameterMap();
		return params.get("ruta");
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

	private static File stream2file(InputStream in) throws IOException {

		final File tempFile = File.createTempFile("Reporte", ".pdf");
		tempFile.deleteOnExit();

		try (FileOutputStream out = new FileOutputStream(tempFile)) {
			IOUtils.copy(in, out);
		}

		return tempFile;
	}

	private void armarUrl(String dir, Integer parametro) {
		try {
			HttpServletRequest request = (HttpServletRequest) facesContext
					.getExternalContext().getRequest();
			String urlPath = request.getRequestURL().toString();
			urlPath = urlPath.substring(0, urlPath.length()
					- request.getRequestURI().length())
					+ request.getContextPath() + "/";

			String url1 = urlPath + dir + parametro;
			sessionMain.setUrl(url1);
			System.out.println("getURL() -> " + url1);
			FacesUtil.updateComponent("modalVistaPrevia");
			FacesUtil.showDialog("dlgVistaPrevia");
		} catch (Exception e) {
		}
	}

	public StreamedContent getStreamPdf() {
		try {
			HttpServletRequest request = (HttpServletRequest) facesContext
					.getExternalContext().getRequest();
			String urlPath = request.getRequestURL().toString();
			urlPath = urlPath.substring(0, urlPath.length()
					- request.getRequestURI().length())
					+ request.getContextPath() + "/";

			String url1 = urlPath + "ReporteRecetaHC?receta=" + receta;
			sessionMain.setUrl(url1);
			System.out.println("getURL() -> " + url1);
			URL url2 = new URL(url1);

			// Read the PDF from the URL and save to a local file
			InputStream is1 = url2.openStream();
			File f = stream2file(is1);
			InputStream stream = new FileInputStream(f);
			streamPdf = new DefaultStreamedContent(stream, "application/pdf",
					"LibroVentasNotariado.pdf");
			crearReceta = true;
			actionNuevaReceta();
			return streamPdf;
		} catch (Exception e) {
			return null;
		}
	}

	public void setStreamPdf(StreamedContent streamPdf) {
		this.streamPdf = streamPdf;
	}

	public void cerrarConsulta() {
		if (consulta == null || !consulta.getEstado().equals("AT")) {
			FacesUtil.errorMessage("No tiene ninguna consulta activa.");
		} else {
			if (consulta.getEstado().equals("AT")) {
				consulta.setEstado("AC");
				consulta = consultaDao.modificar(consulta);
				sessionMain.setConsulta(new Consulta());
			} else {

			}
		}
	}

	public void nuevoEstudio() {
		seeEstudio = true;
		resetEstudio();
	}

	public void resetEstudio() {
		if (!ordenSeleccionada) {
			orden = new LabOrden();
			ordenSelected = new LabOrden();
			ParamSistemaIndices sistemaIndices = sistemaIndicesDao
					.obtenerPorCompania("OL", sucursal.getCompania());
			orden.setCodigo(sistemaIndices.getPrefijo()
					+ Time.rellendarCeros(sistemaIndices.getNumeroDigitos(),
							sistemaIndices.getCorrelativo()));
			fechaOrden = null;
			listOrdenDetalles = new ArrayList<LabOrdenDetalle>();
			listaExamenDualSource = new ArrayList<LabExamen>();
			listaExamenDualTarget = new ArrayList<LabExamen>();
			listaExamenModel = new DualListModel<>(listaExamenDualSource,
					listaExamenDualTarget);
		}

		documentos = new ArrayList<HCLabOrdenDocumentos>();

		grupoExamen = new LabGrupoExamen();

		// if (banderaEstudio) {
		// tipoExamen = tipoExamenDao.obtenerPorCompania("LABORATORIO",
		// sucursal.getCompania()).get(0);
		// } else {
		// tipoExamen = tipoExamenDao.obtenerPorCompania("IMAGENOLOGIA",
		// sucursal.getCompania()).get(0);
		// }

		listGrupoExamens = grupoExamenDao.obtenerPorCompania(sucursal
				.getCompania());

		busqueda = "";

		listaOrdenes = labOrdenDao.obtenerDePaciente(selectedPaciente,
				sucursal.getCompania(), sucursal);
		listaDetalleOrden = new ArrayList<LabOrdenDetalle>();

		listaSucursales = sucursalDao
				.obtenerPorCompania(sucursal.getCompania());
		listEdOrdenLaboratorios = examenDao.obtenerAllStructureOrden(sucursal
				.getCompania());
		listExamens = new ArrayList<LabExamen>();
		// listTipoExamens = tipoExamenDao.obtenerPorCompania(sucursal
		// .getCompania());
		listaLabOrden = new ArrayList<LabOrden>();
		listaLabOrden = labOrdenDao.obtenerParaHC(selectedPaciente,
				historiaClinica, sucursal.getCompania());

	}

	public void cancelarEstudio() {
		ordenSeleccionada = false;
		resetEstudio();
	}

	public void nuevoImageneologia() {
		seeEstudio = true;
		resetImageneologia();
	}

	public void resetImageneologia() {
		if (!ordenSeleccionada) {
			labOrden = new LabOrdenImag();
			labOrdenSelected = new LabOrdenImag();
			ParamSistemaIndices sistemaIndices = sistemaIndicesDao
					.obtenerPorCompania("OL", sucursal.getCompania());
			labOrden.setCodigo(sistemaIndices.getPrefijo()
					+ Time.rellendarCeros(sistemaIndices.getNumeroDigitos(),
							sistemaIndices.getCorrelativo()));
			fechaOrden = null;
			listOrdenDetallesImag = new ArrayList<LabOrdenDetalleImag>();
			listaExamenImagDualSource = new ArrayList<LabExamenImag>();
			listaExamenImagDualTarget = new ArrayList<LabExamenImag>();
			listaExamenImagModel = new DualListModel<>(
					listaExamenImagDualSource, listaExamenImagDualTarget);
			System.out.println("lista de picklist = "
					+ listaExamenImagModel.getSource().size() + " "
					+ listaExamenImagModel.getTarget().size());
		}

		grupoExamenImag = new LabGrupoExamenImag();

		// if (banderaEstudio) {
		// tipoExamen = tipoExamenDao.obtenerPorCompania("LABORATORIO",
		// sucursal.getCompania()).get(0);
		// } else {
		// tipoExamen = tipoExamenDao.obtenerPorCompania("IMAGENOLOGIA",
		// sucursal.getCompania()).get(0);
		// }

		listGrupoExamensImag = grupoExamenImagDao.obtenerPorCompania(sucursal
				.getCompania());

		busqueda = "";

		listaOrdenesImag = labOrdenImagDao.obtenerDePaciente(selectedPaciente,
				sucursal.getCompania(), sucursal);
		listOrdenDetallesImag = new ArrayList<LabOrdenDetalleImag>();

		listaSucursales = sucursalDao
				.obtenerPorCompania(sucursal.getCompania());
		listEdOrdenImagenologias = examenImagDao
				.obtenerAllStructureOrden(sucursal.getCompania());
		listExamensImag = new ArrayList<LabExamenImag>();
		// listTipoExamens = tipoExamenDao.obtenerPorCompania(sucursal
		// .getCompania());
		listaLabOrdenImag = new ArrayList<LabOrdenImag>();
		listaLabOrdenImag = labOrdenImagDao.obtenerParaHC(selectedPaciente,
				historiaClinica, sucursal.getCompania());

	}

	public void cancelarImageneologia() {
		ordenSeleccionada = false;
		resetEstudio();
	}

	public void cancelarOrdenServicio() {
		ordenSeleccionada = false;
		resetOrdenServicio();
	}

	public void resetOrdenServicio() {
		ordenServicio = new DesgOrdenServicio();
		ordenesServicio = new ArrayList<DesgOrdenServicio>();
		ordenesServicio = desgOrdenServicioDao.obtenerPorHC(historiaClinica,
				sucursal);
		ordenServicios = desgOrdenServicioDao.obtenerPorClienteAprobadas(
				selectedPaciente, sucursal);
		vistaOrdenServicioSelected = new EDDesgOrdenServicioHC();
		listaVistaOrdenServicioSelected = new ArrayList<EDDesgOrdenServicioHC>();
	}

	public void nuevoOrdenServicio() {
		seeEstudio = true;
		ordenServicio = new DesgOrdenServicio();
		ordenesServicio = new ArrayList<DesgOrdenServicio>();
		ordenServicios = new ArrayList<DesgOrdenServicio>();
		ordenesServicio = desgOrdenServicioDao.obtenerPorHC(historiaClinica,
				sucursal);
		ordenServicios = desgOrdenServicioDao.obtenerPorClienteAprobadas(
				selectedPaciente, sucursal);
	}

	public void cargarListasOrdenServicio() {
		vistaOrdenServicioSelected.setLabExamenes(desgOrdenServicioLabExamenDao
				.obtenerPorOrdenHC(ordenServicio));
		System.out.println("labexamaenes de orden de servicio = "
				+ vistaOrdenServicioSelected.getLabExamenes().size());
		vistaOrdenServicioSelected
				.setImgExamenes(desgOrdenServicioImagExamenDao
						.obtenerPorOrdenHC(ordenServicio));
		System.out.println("imagexamenes de orden de servicio = "
				+ vistaOrdenServicioSelected.getImgExamenes());
		vistaOrdenServicioSelected.setServicios(desgOrdenServiciosDao
				.obtenerPorOrdenHC(ordenServicio));
		System.out.println("servicios de orden de servicio = "
				+ vistaOrdenServicioSelected.getServicios().size());
	}

	public void selectOrdenServicio(ValueChangeEvent event) {
		if (!event.getNewValue().equals(null)) {
			int id = Integer.parseInt(event.getNewValue().toString());
			ordenServicio = desgOrdenServicioDao.obtenerDesgOrdenServicio(id);
			vistaOrdenServicioSelected = new EDDesgOrdenServicioHC();
			vistaOrdenServicioSelected.setOrdenServicio(ordenServicio);
			cargarListasOrdenServicio();
			listaVistaOrdenServicioSelected = new ArrayList<EDDesgOrdenServicioHC>();
			listaVistaOrdenServicioSelected.add(vistaOrdenServicioSelected);
		}
	}

	public void onSelectOrden() {
		orden = labOrdenDao.obtenerLabOrden(orden.getId());
		listaDetalleOrden = orden.getListOrdenDetalle();
		System.out.println("lista de examenes = "
				+ orden.getListOrdenDetalle().size());
		System.out.println("lista de parametros = "
				+ orden.getListOrdenDetalle().get(0).getListOrdenSubDetalle()
						.size());
	}

	public void onRowEditP(LabOrdenSubDetalle subDetalle) {
		if (subDetalle.getResultado().trim().isEmpty()) {
			FacesUtil.errorMessage("Debe ingresar el resultado del examen.");
		} else {
			labOrdenSubDetalleDao.modificar(subDetalle);
		}
	}

	public void onRowDeleteOrden(LabOrden orden) {
		labOrdenDao.eliminarOrden(orden);
		resetEstudio();
	}

	public void onRowDeleteP(LabOrdenSubDetalle subDetalle) {
		onRowEditP(subDetalle);
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
		String upperQuery = busquedaImag.toUpperCase();
		listExamensImag = examenImagDao.obtenerPorCompania(upperQuery,
				sucursal.getCompania());
		listaExamenImagDualSource = new ArrayList<LabExamenImag>();
		listaExamenImagDualSource = listExamensImag;
		listaExamenImagModel = new DualListModel<LabExamenImag>(
				listaExamenImagDualSource, listaExamenImagDualTarget);
	}

	public void onSelectTipoExamenImag() {
		listaExamenImagDualSource = new ArrayList<LabExamenImag>();
		LabGrupoExamenImag auxiliar = grupoExamenImagDao
				.obtenerLabGrupoExamenImag(grupoExamenImag.getId());
		List<LabExamenImag> labExamen = examenImagDao
				.obtenerPorGrupoExamen(auxiliar);
		listaExamenImagDualSource = labExamen;
		listaExamenImagModel = new DualListModel<LabExamenImag>(
				listaExamenImagDualSource, listaExamenImagDualTarget);
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

	public void onTransferExamen(TransferEvent event) {
		StringBuilder builder = new StringBuilder();
		for (Object item : event.getItems()) {
			if (item instanceof LabExamen) {
				builder.append(((LabExamen) item).getDescripcion()).append(
						"<br />");
			} else {
				if (item instanceof LabExamenImag) {
					builder.append(((LabExamenImag) item).getDescripcion())
							.append("<br />");
				} else {
					builder.append(((Servicio) item).getDescripcion()).append(
							"<br />");
				}
			}
		}

		FacesMessage msg = new FacesMessage();
		msg.setSeverity(FacesMessage.SEVERITY_INFO);
		msg.setSummary("Items Transferred");
		msg.setDetail(builder.toString());

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onSelectExamen(SelectEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Item Selected", event.getObject().toString()));
	}

	public void onUnselectExamen(UnselectEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Item Unselected", event.getObject().toString()));
	}

	public void onReorderExamen() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"List Reordered", null));
	}

	public boolean verificarResultadosEstudio() {
		for (LabOrdenDetalle detalle : orden.getListOrdenDetalle()) {
			for (LabOrdenSubDetalle subDetalle : detalle
					.getListOrdenSubDetalle()) {
				if (subDetalle.getResultado() == null
						|| subDetalle.getResultado().trim().isEmpty()) {
					return false;
				}
			}
		}
		return true;
	}

	public void resetExamenFisico() {
		if (!examenFisicoSeleccionado) {
			examenFisico = new ExamenFisicoSegmentario();
			examenFisicoSelected = new ExamenFisicoSegmentario();
			examenFisicoSignosVitales = new ExamenFisicoSignosVitales();
			examenFisicoSignosVitalesSelected = new ExamenFisicoSignosVitales();
		}
		if (examenFisicoSignosVitalesDao.verificarExamenEnfermera(
				historiaClinica, sucursal.getCompania())) {
			examenFisicoSignosVitales = examenFisicoSignosVitalesDao
					.obtenerExamenEnfermera(historiaClinica,
							sucursal.getCompania());
			examenFisico.setAltura(examenFisicoSignosVitales.getAltura());
			examenFisico
					.setClasificacionIndiceMasaCorporal(examenFisicoSignosVitales
							.getClasificacionIndiceMasaCorporal());
			examenFisico
					.setClasificacionPresionArterial(examenFisicoSignosVitales
							.getClasificacionPresionArterial());
			examenFisico.setFrecuenciaCardiaca(examenFisicoSignosVitales
					.getFrecuenciaCardiaca());
			examenFisico.setIndiceMasaCorporal(examenFisicoSignosVitales
					.getIndiceMasaCorporal());
			examenFisico.setPb(examenFisicoSignosVitales.getPb());
			examenFisico.setPc(examenFisicoSignosVitales.getPc());
			examenFisico.setPeso(examenFisicoSignosVitales.getPeso());
			examenFisico.setPresionDiastolica(examenFisicoSignosVitales
					.getPresionDiastolica());
			examenFisico.setPresionSistolica(examenFisicoSignosVitales
					.getPresionSistolica());
			examenFisico.setPulso(examenFisicoSignosVitales.getPulso());
			examenFisico.setTemperatura(examenFisicoSignosVitales
					.getTemperatura());
		}
		listaExaamenFisicos = examenFisicoSegmentarioDao
				.obtenerPorHistoriaClinica(historiaClinica);
	}

	public void cancelarExamenFisico() {
		examenFisicoSeleccionado = false;
		resetExamenFisico();
	}

	public void resetEvolucion() {
		if (!evolucionSeleccionada) {
			evolucion = new Evolucion();
			evolucionSelected = new Evolucion();
			evolucionSeleccionada = false;
		}
		diagnosticoPresuntivo = new DiagnosticoPresuntivo();
		if (diagnosticoPresuntivoDao.verificarParaEvolucion(historiaClinica,
				sucursal.getCompania(), consulta))
			diagnosticoPresuntivo = diagnosticoPresuntivoDao
					.obtenerParaEvolucion(historiaClinica,
							sucursal.getCompania(), consulta);
		listaEvoluciones = new ArrayList<Evolucion>();
		if (evolucionDao
				.verificarPorHM(historiaClinica, sucursal.getCompania()))
			listaEvoluciones = evolucionDao.obtenerPorHM(historiaClinica,
					sucursal.getCompania());
		listaUltimaReceta = new ArrayList<DetalleMedicamento>();
		cargarUltimaReceta();
	}

	public void cancelarEvolucion() {
		evolucionSeleccionada = false;
		resetEvolucion();
	}

	public void resetDiagnostico() {
		if (!detalleDiagnosticoSeleccionado) {
			diagnostico = new Diagnostico();
			tipoDiagnostico = new TipoDiagnostico();
			detalleDiagnostico = new DetalleDiagnostico();
			detalleDiagnosticoSelected = new DetalleDiagnostico();
			diagnosticoDetalle = new Diagnostico();
			diagnosticoDetalleSelected = new Diagnostico();
			especialidad = new Especialidad();
			detalleDiagnosticoSeleccionado = false;
			query = "";
			descripcionDiagnostico = "";
		}

		listaDetalleDiagnosticos = new ArrayList<DetalleDiagnostico>();
		listaTipoDiagnosticos = new ArrayList<TipoDiagnostico>();
		listaDiagnosticos = new ArrayList<Diagnostico>();
		listaEspecialidadDiagnosticos = new ArrayList<Especialidad>();
		listaDiagnosticosDualSource = new ArrayList<String>();
		listaDiagnosticosDualTarget = new ArrayList<String>();
		listaDiagnosticosDual = new DualListModel<>(
				listaDiagnosticosDualSource, listaDiagnosticosDualTarget);
		listaTipoDiagnosticos = tipoDiagnosticoDao.obtenerPorSucursal(sucursal);

		listaEspecialidadDiagnosticos = especialidadDao
				.obtenerPorCompania(sucursal.getCompania());
		listaDetalleDiagnosticos = detalleDiagnosticoDao.obtenerPorHC(
				historiaClinica, sucursal.getCompania());
		listaDiagnosticos = diagnosticoDao.obtenerPorCompania(sucursal
				.getCompania());
		listaEspecialidades = especialidadDao.obtenerPorCompania(sucursal
				.getCompania());
	}

	public void cancelarDiagnostico() {
		detalleDiagnosticoSeleccionado = false;
		resetDiagnostico();
	}

	public void resetEnfermedad() {
		if (!enfermedadSeleccionado) {
			enfermedad = new Enfermedad();
			enfermedadSelected = new Enfermedad();
			enfermedadSeleccionado = false;
		}
		listaEnfermedades = enfermedadDao.obtenerPorHC(historiaClinica,
				sucursal.getCompania());
	}

	public void cancelarEnfermedad() {
		enfermedadSeleccionado = false;
		resetEnfermedad();
	}

	public void resetAntecedente() {
		listaDetalleAntecedentes = detalleAntecedenteDao.obtenerPorHC(
				historiaClinica, sucursal.getCompania());
		listaAntecedentesDualSource = new ArrayList<String>();
		listaAntecedentesDualTarget = new ArrayList<String>();
		listaAntecedentesDual = new DualListModel<>(
				listaAntecedentesDualSource, listaAntecedentesDualTarget);
		listaAntecedentes = antecedenteDao.obtenerPorCompania(sucursal
				.getCompania());
		listaTipoAntecedentes = new ArrayList<TipoAntecedente>();
		listaTipoAntecedentes = tipoAntecedenteDao.obtenerPorCompania(sucursal
				.getCompania());
		listaEspecialidades = especialidadDao.obtenerPorCompania(sucursal
				.getCompania());

		if (!detalleAntecedenteSeleccionado) {
			detalleAntecedenteSeleccionado = false;
			antecedenteSeleccionado = false;
			antecedenteDetalle = new Antecedente();
			antecedenteDetalleSelected = new Antecedente();
			antecedente = new Antecedente();
			antecedenteSelected = new Antecedente();
			tipoAntecedente = new TipoAntecedente();
			especialidad = new Especialidad();
			query = "";
			descripcionAntecedente = "";
		}
	}

	public void cancelarAntecedente() {
		antecedenteSeleccionado = false;
		detalleAntecedenteSeleccionado = false;
		resetAntecedente();
	}

	public void resetMotivo() {
		listaMotivos = motivoDao.obtenerPorHC(historiaClinica,
				sucursal.getCompania());
		if (!motivoSeleccionado) {
			motivo = new Motivo();
			motivoSelected = new Motivo();
			motivoSeleccionado = false;
		}
	}

	public void cancelarMotivo() {
		motivoSeleccionado = false;
		resetMotivo();
	}

	public void resetDiagnosticoPresuntivo() {
		listaDiagnosticosPresuntivos = diagnosticoPresuntivoDao.obtenerPorHC(
				historiaClinica, sucursal.getCompania());
		if (!diagnosticoPresuntivoSeleccionado) {
			diagnosticoPresuntivo = new DiagnosticoPresuntivo();
			diagnosticoPresuntivoSelected = new DiagnosticoPresuntivo();
			diagnosticoPresuntivoSeleccionado = false;
		}
	}

	public void cancelarDiagnosticoPresuntivo() {
		diagnosticoPresuntivoSeleccionado = false;
		resetDiagnosticoPresuntivo();
	}

	public void resetContrareferencia() {
		transferenciaSeleccionada = false;

		if (!contrareferenciaSeleccionada) {
			contrareferencia = new Contrareferencia();
			contrareferenciaSelected = new Contrareferencia();
			contrareferenciaSeleccionada = false;
		}
		listaContrareferencias = contrareferenciaDao.obtenerPorHC(
				historiaClinica, sucursal.getCompania());
		if (transferenciaDao.verificarTransferencia(historiaClinica,
				sucursal.getCompania())) {
			transferenciaToContra = transferenciaDao
					.obtenerTransferenciaParaContra(historiaClinica,
							sucursal.getCompania());
			contrareferencia.setMedicoEmisor(transferenciaToContra
					.getMedicoReceptor());
			contrareferencia.setEspecialidadMedicoEmisor(transferenciaToContra
					.getEspecialidadMedicoReceptor());
			contrareferencia.setMedicoReceptor(transferenciaToContra
					.getMedicoEmisor());
			contrareferencia.setTransferencia(transferenciaToContra);
		}
	}

	public void cancelarContrareferencia() {
		contrareferenciaSeleccionada = false;
		resetContrareferencia();
	}

	public void resetIndicacion() {
		if (!detalleMedicamentoSeleccionado) {
			detalleMedicamentoSeleccionado = false;
			sucursalSelected = new Sucursal();
			tipoBusqueda = true;
			query = "";
			detalleMedicamento = new DetalleMedicamento();
			detalleMedicamentoSelected = new DetalleMedicamento();
			medicamento = new Medicamento();
		}

		listaMedicamentos = new ArrayList<Medicamento>();
		listaUltimaReceta = new ArrayList<DetalleMedicamento>();
		listaUltimaReceta = detalleMedicamentoDao.obtenerUltimaReceta(sucursal
				.getCompania());
		listaSucursales = sucursalDao
				.obtenerPorCompania(sucursal.getCompania());
		listaDetalleMedicamentos = detalleMedicamentoDao.obtenerPorHC(
				historiaClinica, sucursal.getCompania());

		actionNuevaReceta();
	}

	public void cancelarIndicacion() {
		detalleMedicamentoSeleccionado = false;
		resetIndicacion();
	}

	public void resetTransferencia() {
		if (!transferenciaSeleccionada) {
			transferencia = new Transferencia();
			transferenciaSelected = new Transferencia();
			transferenciaSeleccionada = false;
		}

		listaTransferencias = new ArrayList<Transferencia>();
		listaTransferencias = transferenciaDao.obtenerPorHC(historiaClinica,
				sucursal.getCompania());
		listaEspecialidad = new ArrayList<Especialidad>();
		banderaTransferencia = true;
	}

	public void cancelarTransferencia() {
		transferenciaSeleccionada = false;
		resetTransferencia();
	}

	public void cargarUltimaReceta() {
		listaUltimaReceta = detalleMedicamentoDao.obtenerUltimaReceta(sucursal
				.getCompania());
	}

	public List<Especialidad> completeEspecialidad(String query) {
		String upperQuery = query.toUpperCase();
		listaEspecialidad = especialidadDao.obtenerPorCompania(upperQuery,
				sucursal.getCompania());
		return listaEspecialidad;
	}

	public void onRowSelectEspecialidadClick(SelectEvent event) {
		String nombre = event.getObject().toString();
		transferencia.setEspecialidadMedicoReceptor(nombre);
	}

	public void onKeyUpQueryDiagnostico() {
		String upperQuery = query.toUpperCase();
		listaDiagnosticos = diagnosticoDao.obtenerPorCompaniaAutoComplete(
				upperQuery, sucursal.getCompania());
		System.out.println("lista autocomplete = " + listaDiagnosticos.size());
		listaDiagnosticosDualSource = new ArrayList<String>();
		listaDiagnosticosDualTarget = new ArrayList<String>();
		listaDiagnosticosDual = new DualListModel<>(
				listaDiagnosticosDualSource, listaDiagnosticosDualTarget);
		for (Diagnostico detalleTipoDiagnostico : listaDiagnosticos) {
			listaDiagnosticosDualSource.add(detalleTipoDiagnostico
					.getDescripcion());
		}
	}

	public void onKeyUpQueryAntecedente() {
		String upperQuery = query.toUpperCase();
		listaAntecedentes = antecedenteDao.obtenerPorCompania(upperQuery,
				sucursal.getCompania());
		System.out.println("lista autocomplete = " + listaDiagnosticos.size());
		listaAntecedentesDualSource = new ArrayList<String>();
		listaAntecedentesDualTarget = new ArrayList<String>();
		listaAntecedentesDual = new DualListModel<>(
				listaAntecedentesDualSource, listaAntecedentesDualTarget);
		for (Antecedente antecedente : listaAntecedentes) {
			listaAntecedentesDualSource.add(antecedente.getDescripcion());
		}
	}

	public void onRowSelectTransferencia() {
		crear = false;
		registrar = false;
		modificar = true;
		transferenciaSeleccionada = true;
		transferencia = transferenciaSelected;
	}

	public void onRowSelectLabOrden() {
		crear = false;
		registrar = false;
		modificar = true;
		ordenSeleccionada = true;
		orden = ordenSelected;
	}

	public void onRowSelectContrareferencia() {
		crear = false;
		registrar = false;
		modificar = true;
		contrareferenciaSeleccionada = true;
		contrareferencia = contrareferenciaSelected;
	}

	public void onRowSelectEvolucion() {
		crear = false;
		registrar = false;
		modificar = true;
		evolucionSeleccionada = true;
		evolucion = evolucionSelected;
	}

	public void onRowDeleteTransferencia(Transferencia transferencia) {
		if (verificarHoraActualizar(transferencia.getFechaRegistro())) {
			transferenciaDao.eliminar(transferencia);
			listaTransferencias.remove(transferencia);
		} else {
			FacesUtil
					.errorMessage("Ya se cumplieron las 24 horas a partir del registro de la transferencia, no puede eliminar.");
		}
	}

	public void onRowDeleteContrareferencia(Contrareferencia contrareferencia) {
		if (verificarHoraActualizar(contrareferencia.getFechaRegistro())) {
			contrareferenciaDao.eliminar(contrareferencia);
			listaContrareferencias.remove(contrareferencia);
		} else {
			FacesUtil
					.errorMessage("Ya se cumplieron las 24 horas a partir del registro de la contrareferencia, no puede eliminar.");
		}
	}

	public void onRowDeleteEvolucion(Evolucion evolucion) {
		if (verificarHoraActualizar(evolucion.getFechaRegistro())) {
			evolucionDao.eliminar(evolucion);
			listaEvoluciones.remove(evolucion);
		} else {
			FacesUtil
					.errorMessage("Ya se cumplieron las 24 horas a partir del registro de la evolución, no puede eliminar.");
		}
	}

	public void onRowSelectDiagnostico(SelectEvent event) {
		crear = false;
		registrar = false;
		modificar = true;
		detalleDiagnosticoSeleccionado = true;
		detalleDiagnostico = detalleDiagnosticoSelected;
		diagnosticoDetalle = detalleDiagnosticoSelected.getDiagnostico();
		comentario = detalleDiagnostico.getDiagnostico().getDescripcion();
		System.out.println("evento = " + event.getClass().getName().toString());
		System.out.println("diagnostico = "
				+ detalleDiagnostico.getDiagnostico().getDescripcion() + " "
				+ detalleDiagnostico.getComentario() + " "
				+ detalleDiagnostico.getFechaRegistro());
		System.out.println("diagnostico = "
				+ detalleDiagnosticoSelected.getDiagnostico().getDescripcion()
				+ " " + detalleDiagnosticoSelected.getComentario() + " "
				+ detalleDiagnosticoSelected.getFechaRegistro());
		// RequestContext.getCurrentInstance().reset("form001");
		// FacesUtil.updateComponent("formDlg002");
		// FacesUtil.showDialog("dlgDiagnosticoUpdate");
		FacesContext.getCurrentInstance().renderResponse();
		resetearFitrosTabla("form001:singleDT");
	}

	public void onRowSelectAntecedente(SelectEvent event) {
		crear = false;
		registrar = false;
		modificar = true;
		detalleAntecedenteSeleccionado = true;
		detalleAntecedente = detalleAntecedenteSelected;
		antecedenteDetalle = detalleAntecedente.getAntecedente();
		FacesContext.getCurrentInstance().renderResponse();
		resetearFitrosTabla("form001:singleDT");
	}

	public void onRowEdit(DetalleAntecedente detalleAntecedente) {
		if (detalleAntecedente.getDetalle().trim().isEmpty()) {
			FacesUtil.errorMessage("No deben existir campos vacios.");
		} else {
			detalleAntecedente.setFechaModificacion(new Date());
			detalleAntecedente.setUsuarioRegistro(usuario.getLogin());
			detalleAntecedenteDao.modificar(detalleAntecedente);
		}
	}

	public void onRowCancel(DetalleAntecedente detalleAntecedente) {
		if (detalleAntecedente.getDetalle().trim().isEmpty()) {
			FacesUtil.errorMessage("No deben existir campos vacios.");
		} else {
			detalleAntecedente.setFechaModificacion(new Date());
			detalleAntecedente.setUsuarioRegistro(usuario.getLogin());
			detalleAntecedenteDao.modificar(detalleAntecedente);
		}
	}

	public void onRowEditDiagnostico(DetalleDiagnostico detalleDiagnostico) {
		System.out.println("detalle diagnostico = "
				+ getDiagnostico().getDescripcion());
		if (detalleDiagnostico.getComentario().trim().isEmpty()) {
			FacesUtil.errorMessage("No deben existir campos vacios.");
		} else {
			detalleDiagnostico.setFechaModificacion(new Date());
			detalleDiagnostico.setUsuarioRegistro(usuario.getLogin());
			detalleDiagnosticoDao.modificar(detalleDiagnostico);
		}
	}

	public void onRowCancelDiagnostico(DetalleDiagnostico detalleDiagnostico) {
		if (detalleDiagnostico.getComentario().trim().isEmpty()) {
			FacesUtil.errorMessage("No deben existir campos vacios.");
		} else {
			detalleDiagnostico.setFechaModificacion(new Date());
			detalleDiagnostico.setUsuarioRegistro(usuario.getLogin());
			detalleDiagnosticoDao.modificar(detalleDiagnostico);
		}
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
				if (selectedPaciente.getFechaNacimiento() != null) {
					selectedPaciente.setEdad(Time.calcularEdad(selectedPaciente
							.getFechaNacimiento()));
				}

			} else {
			}
		} catch (Exception e) {
			System.err.println("Error en obtenerVariableExternos : "
					+ e.getLocalizedMessage());
		}
	}

	public void calcularPA() {
		if (examenFisico.getPresionSistolica() == 0) {
			FacesUtil
					.errorMessage("Debe ingresar la presión sistólica para la clasificación respectiva.");
			return;
		} else {
			if (examenFisico.getPresionDiastolica() == 0) {
				FacesUtil
						.errorMessage("Debe ingresar la presión diastólica para la clasificación respectiva.");
				return;
			} else {
				if (examenFisico.getPresionSistolica() < 120
						&& examenFisico.getPresionDiastolica() < 80)
					examenFisico.setClasificacionPresionArterial("Óptima.");
				if (examenFisico.getPresionSistolica() >= 120
						&& examenFisico.getPresionSistolica() <= 139
						|| examenFisico.getPresionDiastolica() >= 80
						&& examenFisico.getPresionDiastolica() <= 89)
					examenFisico
							.setClasificacionPresionArterial("Prehipertensión.");
				if (examenFisico.getPresionSistolica() < 130
						&& examenFisico.getPresionDiastolica() < 85)
					examenFisico.setClasificacionPresionArterial("Normal.");
				if (examenFisico.getPresionSistolica() >= 130
						&& examenFisico.getPresionSistolica() <= 139
						|| examenFisico.getPresionDiastolica() >= 85
						&& examenFisico.getPresionDiastolica() <= 89)
					examenFisico
							.setClasificacionPresionArterial("Normal Alta.");
				if (examenFisico.getPresionSistolica() >= 140
						&& examenFisico.getPresionSistolica() <= 159
						|| examenFisico.getPresionDiastolica() >= 90
						&& examenFisico.getPresionDiastolica() <= 99)
					examenFisico
							.setClasificacionPresionArterial("Hipertensión grado 1.");
				if (examenFisico.getPresionSistolica() >= 160
						&& examenFisico.getPresionSistolica() <= 179
						|| examenFisico.getPresionDiastolica() >= 100
						&& examenFisico.getPresionDiastolica() <= 109)
					examenFisico
							.setClasificacionPresionArterial("Hipertensión grado 2.");
				if (examenFisico.getPresionSistolica() >= 180
						|| examenFisico.getPresionDiastolica() >= 110)
					examenFisico
							.setClasificacionPresionArterial("Hipertensión grado 3.");
			}
		}
	}

	public void calcularIMC() {
		if (examenFisico.getPeso() == 0) {
			FacesUtil
					.errorMessage("Debe ingresar el peso para la clasificación respectiva del IMC.");
			return;
		} else {
			if (examenFisico.getAltura() == 0) {
				FacesUtil
						.errorMessage("Debe ingresar la altura para la clasificación respectiva del IMC.");
				return;
			} else {
				System.out.println("valor = " + examenFisico.getAltura());
				float resultado = (float) (examenFisico.getPeso() / Math.pow(
						examenFisico.getAltura(), 2));
				System.out.println("peso = "
						+ Float.toString(examenFisico.getPeso()));
				System.out.println("altura = "
						+ Float.toString(examenFisico.getAltura()));
				System.out.println("imc = " + Float.toString(resultado));
				examenFisico.setIndiceMasaCorporal(resultado);
				if (resultado < 16)
					examenFisico
							.setClasificacionIndiceMasaCorporal("Delgadez severa.");
				else if (resultado >= 16 && resultado <= 16.99)
					examenFisico
							.setClasificacionIndiceMasaCorporal("Delgadez moderada.");
				else if (resultado >= 17 && resultado <= 18.49)
					examenFisico
							.setClasificacionIndiceMasaCorporal("Delgadez no muy pronunciada.");
				else if (resultado >= 18.5 && resultado <= 24.99)
					examenFisico.setClasificacionIndiceMasaCorporal("Normal");
				else if (resultado >= 25 && resultado <= 27.49)
					examenFisicoSignosVitales
							.setClasificacionIndiceMasaCorporal("Sobrepeso");
				else if (resultado >= 27.5 && resultado <= 29.99)
					examenFisico
							.setClasificacionIndiceMasaCorporal("Preobesidad.");
				else if (resultado >= 30 && resultado <= 34.99)
					examenFisico
							.setClasificacionIndiceMasaCorporal("Obesidad grado I.");
				else if (resultado >= 35 && resultado <= 39.99)
					examenFisico
							.setClasificacionIndiceMasaCorporal("Obesidad grado II.");
				else if (resultado >= 40)
					examenFisico
							.setClasificacionIndiceMasaCorporal("Obesidad grado III.");
				System.out.println("clase imc = "
						+ examenFisico.getClasificacionIndiceMasaCorporal());
			}
		}
	}

	public void onSelectSucursal() {
		sucursalSelected = sucursalDao.obtenerSucursal(sucursalSelected
				.getNombre());
	}

	/*
	 * public void onSelectFrecuencia() {
	 * detalleMedicamento.setFrecuencia(frecuenciaDao.obtenerFrecuencia(
	 * detalleMedicamento.getFrecuencia().getDescripcion(),
	 * sucursal.getCompania())); }
	 */

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

	public void onRowSelectDiagnosticoPresuntivos() {
		diagnosticoPresuntivoSeleccionado = true;
		diagnosticoPresuntivo = diagnosticoPresuntivoSelected;
		FacesContext.getCurrentInstance().renderResponse();
		resetearFitrosTabla("form001:singleDT");
		FacesUtil.updateComponent("formDlg001");
		FacesUtil.showDialog("dlgDiagnosticoPresuntivo");
	}

	public void onRowSelectExamenFisico() {
		examenFisicoSeleccionado = true;
		examenFisico = examenFisicoSelected;
		FacesContext.getCurrentInstance().renderResponse();
		resetearFitrosTabla("form001:singleDT");
		FacesUtil.updateComponent("formDlg001");
		FacesUtil.showDialog("dlgExamenFisico");
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

	public void onRowDeleteDiagnostico(DetalleDiagnostico detalleDiagnostico) {
		if (verificarHoraActualizar(detalleDiagnostico.getFechaRegistro())) {
			detalleDiagnosticoDao.eliminar(detalleDiagnostico);
			resetDiagnostico();
			// listaDetalleDiagnosticos.remove(detalleDiagnostico);
		} else {
			FacesUtil
					.errorMessage("Ya se cumplieron 24 horas a partir del registro del diagnostico, no puede editar el motivo.");
		}
	}

	public void onRowDeleteAntecedente(DetalleAntecedente detalleAntecedente) {
		if (verificarHoraActualizar(detalleAntecedente.getFechaRegistro())) {
			detalleAntecedenteDao.eliminar(detalleAntecedente);
			resetAntecedente();
			// listaDetalleDiagnosticos.remove(detalleDiagnostico);
		} else {
			FacesUtil
					.errorMessage("Ya se cumplieron 24 horas a partir del registro del antecedente, no puede editar el motivo.");
		}
	}

	public void onRowDeleteExamenFisico(
			ExamenFisicoSegmentario examenFisicoSegmentario) {
		if (verificarHoraActualizar(examenFisicoSegmentario.getFechaRegistro())) {
			examenFisicoSegmentarioDao.eliminar(examenFisicoSegmentario);
			resetExamenFisico();
			// listaDetalleDiagnosticos.remove(detalleDiagnostico);
		} else {
			FacesUtil
					.errorMessage("Ya se cumplieron 24 horas a partir del registro del exámen físico, no puede editar el motivo.");
		}
	}

	public void onRowDeleteMotivo(Motivo motivo) {
		if (verificarHoraActualizar(motivo.getFechaRegistro())) {
			motivoDao.eliminar(motivo);
			resetMotivo();
			// listaDetalleDiagnosticos.remove(detalleDiagnostico);
		} else {
			FacesUtil
					.errorMessage("Ya se cumplieron 24 horas a partir del registro del motivo, no puede editar el motivo.");
		}
	}

	public void onRowDeleteDiagnosticoPresuntivo(
			DiagnosticoPresuntivo diagnosticoPresuntivo) {
		if (verificarHoraActualizar(diagnosticoPresuntivo.getFechaRegistro())) {
			diagnosticoPresuntivoDao.eliminar(diagnosticoPresuntivo);
			resetDiagnosticoPresuntivo();
			// listaDetalleDiagnosticos.remove(detalleDiagnostico);
		} else {
			FacesUtil
					.errorMessage("Ya se cumplieron 24 horas a partir del registro del diagnostico, no puede editar el diagnostico.");
		}
	}

	public void onRowDeleteEnfermedad(Enfermedad enfermedad) {
		if (verificarHoraActualizar(enfermedad.getFechaRegistro())) {
			enfermedadDao.eliminar(enfermedad);
			resetEnfermedad();
			// listaDetalleDiagnosticos.remove(detalleDiagnostico);
		} else {
			FacesUtil
					.errorMessage("Ya se cumplieron 24 horas a partir del registro de la enfermedad, no puede editar el motivo.");
		}
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

	public void onSelectEspecialidadTransferencia() {
	}

	public void onSelectTipoDiagnostico() {
		listaDiagnosticosDualSource = new ArrayList<String>();
		listaDiagnosticosDualTarget = new ArrayList<String>();
		listaDiagnosticosDual = new DualListModel<>(
				listaDiagnosticosDualSource, listaDiagnosticosDualTarget);
		TipoDiagnostico auxiliar = tipoDiagnosticoDao
				.obtenerTipoDiagnostico(tipoDiagnostico.getNombre());
		System.out.println("tipodiagnostico = " + auxiliar.getNombre() + " "
				+ auxiliar.getDescripcion());
		List<DetalleTipoDiagnostico> detalle = detalleTipoDiagnosticoDao
				.obtenerPorTipoDiagnostico(auxiliar);
		System.out.println("lista de detalletipodiagnostico = "
				+ detalle.size());
		for (DetalleTipoDiagnostico detalleTipoDiagnostico : detalle) {
			listaDiagnosticosDualSource.add(detalleTipoDiagnostico
					.getDiagnostico().getDescripcion());
		}
	}

	public void onSelectTipoAntecedente() {
		listaAntecedentesDualSource = new ArrayList<String>();
		listaAntecedentesDualTarget = new ArrayList<String>();
		listaAntecedentesDual = new DualListModel<>(
				listaAntecedentesDualSource, listaAntecedentesDualTarget);
		TipoAntecedente auxiliar = tipoAntecedenteDao
				.obtenerTipoAntecedente(tipoAntecedente.getNombre());
		System.out.println("tipoantecedente = " + auxiliar.getNombre() + " "
				+ auxiliar.getDescripcion());
		List<DetalleTipoAntecedente> detalle = detalleTipoAntecedenteDao
				.obtenerPorTipoAntecedente(auxiliar);
		System.out.println("lista de detalletipoantecedente = "
				+ detalle.size());
		for (DetalleTipoAntecedente detalleTipoAntecedente : detalle) {
			listaAntecedentesDualSource.add(detalleTipoAntecedente
					.getAntecedente().getDescripcion());
		}
	}

	public void onSelectEspecialidadDiagnostico() {
		listaDiagnosticosDualSource = new ArrayList<String>();
		listaDiagnosticosDualTarget = new ArrayList<String>();
		listaDiagnosticosDual = new DualListModel<>(
				listaDiagnosticosDualSource, listaDiagnosticosDualTarget);
		Especialidad auxiliar = especialidadDao
				.obtenerEspecialidad(especialidad.getNombre());
		System.out.println("especialidad = " + auxiliar.getNombre());
		List<DetalleDiagnosticoEspecialidad> detalle = detalleDiagnosticoEspecialidadDao
				.obtenerPorEspecialidad(auxiliar);
		System.out.println("lista de detalleDiagnosticoEspecialidad = "
				+ detalle.size());
		for (DetalleDiagnosticoEspecialidad detalleDiagnosticoEspecialidad : detalle) {
			listaDiagnosticosDualSource.add(detalleDiagnosticoEspecialidad
					.getDiagnostico().getDescripcion());
		}
	}

	public void onSelectDiagnosticoDetalle() {
		System.out.println("diagnosticoDetalle = "
				+ diagnosticoDetalle.getDescripcion());
		diagnosticoDetalleSelected = diagnosticoDao
				.obtenerDiagnostico(diagnosticoDetalle.getDescripcion());
	}

	public void onSelectAntecedenteDetalle() {
		System.out.println("antecedenteDetalle = "
				+ antecedenteDetalle.getDescripcion());
		antecedenteDetalleSelected = antecedenteDao
				.obtenerAntecedente(antecedenteDetalle.getDescripcion());
	}

	public void onChangeDiagnostico(DetalleDiagnostico detalleDiagnostico) {
		System.out.println("detalle diagnostico = "
				+ detalleDiagnostico.getDiagnostico().getDescripcion());
	}

	public boolean verificarRestriccion(String dato) {
		return dato.toLowerCase().contains("receta");
	}

	public boolean verificarHoraActualizar(Date fecha) {
		Date plazo = Time.sumHour(fecha, 24);
		return new Date().before(plazo);
	}

	public List<Diagnostico> completeDiagnostico(String query) {
		String upperQuery = query.toUpperCase();
		listaDiagnosticos = diagnosticoDao.obtenerPorCompaniaAutoComplete(
				upperQuery, sucursal.getCompania());
		System.out.println("lista autocomplete = " + listaDiagnosticos.size());
		listaDiagnosticosDualSource = new ArrayList<String>();
		listaDiagnosticosDualTarget = new ArrayList<String>();
		listaDiagnosticosDual = new DualListModel<>(
				listaDiagnosticosDualSource, listaDiagnosticosDualTarget);
		for (Diagnostico detalleTipoDiagnostico : listaDiagnosticos) {
			listaDiagnosticosDualSource.add(detalleTipoDiagnostico
					.getDescripcion());
		}
		return listaDiagnosticos;
	}

	public List<Medicamento> onCompleteMedicamento(String query) {
		String upperQuery = query.toUpperCase();
		if (tipoBusqueda) {
			listaMedicamentos = medicamentoDao.obtenerAutoCompleteNG(
					upperQuery, sucursal.getCompania());
		} else {
			listaMedicamentos = medicamentoDao.obtenerAutoCompleteNC(
					upperQuery, sucursal.getCompania());
		}
		return listaMedicamentos;
	}

	public void onSelectMedicamentoClick(SelectEvent event) {
		String consulta = event.getObject().toString();
		System.out.println("seleccion medicamento = " + consulta);
		String[] protocolo = consulta.split("-");
		String nombreGenerico = protocolo[0];
		String presentacion = protocolo[1];
		Medicamento auxiliar = new Medicamento();
		if (tipoBusqueda) {
			auxiliar = medicamentoDao.obtenerMedicamentoNG(
					nombreGenerico.trim(), presentacion.trim(),
					sucursal.getCompania());
		} else {
			auxiliar = medicamentoDao.obtenerMedicamentoNG(
					nombreGenerico.trim(), presentacion.trim(),
					sucursal.getCompania());
		}
		System.out.println("medicamentoSelected = "
				+ auxiliar.getNombreGenerico() + " "
				+ auxiliar.getNombreComercial() + " "
				+ auxiliar.getPresentacion());
		detalleMedicamento = new DetalleMedicamento();
		detalleMedicamento.setMedicamento(auxiliar);
	}

	public void onRowSelectDiagnosticoClick(AjaxBehaviorEvent event) {
		String upperQuery = event.toString().toUpperCase();
		System.out.println("query = " + upperQuery);
		listaDiagnosticos = diagnosticoDao.obtenerPorCompaniaAutoComplete(
				upperQuery, sucursal.getCompania());
		System.out.println("lista autocomplete = " + listaDiagnosticos.size());
		listaDiagnosticosDualSource = new ArrayList<String>();
		listaDiagnosticosDualTarget = new ArrayList<String>();
		listaDiagnosticosDual = new DualListModel<>(
				listaDiagnosticosDualSource, listaDiagnosticosDualTarget);
		for (Diagnostico detalleTipoDiagnostico : listaDiagnosticos) {
			listaDiagnosticosDualSource.add(detalleTipoDiagnostico
					.getDescripcion());
		}
		// String nombre = event.getObject().toString();
		// Diagnostico t = diagnosticoDao.obtenerDiagnostico(nombre);
		// listaDiagnosticosDualTarget.add(t.getDescripcion());
	}

	public void onRowSelectMedicamento() {
		crear = false;
		registrar = false;
		modificar = true;
		detalleMedicamentoSeleccionado = true;
		detalleMedicamento = new DetalleMedicamento();
		receta = detalleMedicamentoSelected.getReceta();
		detalleMedicamento.setAdministracion(detalleMedicamentoSelected
				.getAdministracion());
		detalleMedicamento
				.setCantidad(detalleMedicamentoSelected.getCantidad());
		query = detalleMedicamentoSelected.getMedicamento().getNombreGenerico()
				+ " - "
				+ detalleMedicamentoSelected.getMedicamento().getPresentacion();
	}

	public void onRowDeleteMedicamento(DetalleMedicamento detalleMedicamento) {
		if (verificarHoraActualizar(detalleMedicamento.getFechaRegistro())) {
			detalleMedicamentoDao.eliminar(detalleMedicamento);
			listaDetalleMedicamentos.remove(detalleMedicamento);
			resetIndicacion();
		} else {
			FacesUtil
					.errorMessage("Ya se cumplieron las 24 horas no puede eliminar el medicamento");
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

	private boolean existeExamenImag(LabExamenImag examen,
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
		for (EDOrdenLaboratorio edOrdenLaboratorio : listEdOrdenLaboratorios) {
			for (LabExamen labExamen : edOrdenLaboratorio.getListaExamen()) {
				if (labExamen.isChecker()) {
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

	private void rellenarDetalleImag() {
		listOrdenDetallesImag.clear();
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
					listOrdenDetallesImag.add(detalle);
				}
			}
		}
	}

	public void onFecha() {
		System.out.println("maldita fecha = " + orden.getFecha());
	}

	private void activarDetalle() {
		int i = 0;
		for (LabOrdenDetalle ordenDetalle : listOrdenDetalles) {
			boolean encontrado = false;
			for (EDOrdenLaboratorio edOrdenLaboratorio : listEdOrdenLaboratorios) {
				for (LabExamen labExamen : edOrdenLaboratorio.getListaExamen()) {
					if (labExamen.getId().intValue() == ordenDetalle
							.getLabExamen().getId().intValue()) {
						i++;
						System.out.println("contador de detalle de orden = "
								+ i);
						labExamen.setChecker(true);
						encontrado = true;
					}
				}
			}
			if (!encontrado) {
				i++;
				System.out.println("contador de detalle de orden = " + i);
				listaExamenModel.getTarget().add(ordenDetalle.getLabExamen());
				encontrado = false;
			}
		}
		listaExamenDualTarget = listaExamenModel.getTarget();
	}

	public void onRowSelectEstudio(SelectEvent event) {
		orden = ordenSelected;
		seeEstudio = true;
		ordenSeleccionada = true;
		crear = false;
		seleccionado = true;
		registrar = false;
		selectedMedico = orden.getMedico();
		selectedPaciente = orden.getPaciente();
		listOrdenDetalles = orden.getListOrdenDetalle();
		activarDetalle();
	}

	public void registrarOrdenServicio() {
		try {
			if (ordenServicio != null) {
				ordenServicio.setHistoriaClinica(historiaClinica);
				ordenServicio = desgOrdenServicioDao.modificar(ordenServicio);
				resetOrdenServicio();
			} else {
				FacesUtil
						.errorMessage("Debe seleccionar una orden de servicio.");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void registrarEstudio() {
		if (ordenSeleccionada) {// actualiza
			if (verificarHoraActualizar(orden.getFechaRegistro())) {
				try {
					System.out.println("Ingreso a actualizar");
					if (orden.getDescripcion().trim().isEmpty()
							|| orden.getEstado().trim().isEmpty()
							|| getSucursal() == null
							|| getSucursal().getCompania() == null) {
						FacesUtil.infoMessage("VALIDACION",
								"No puede haber campos vacíos");
						return;
					} else {
						if (selectedPaciente.getId().intValue() == 0
								|| selectedPaciente == null
								|| getSucursal() == null
								|| getSucursal().getCompania() == null) {
							FacesUtil.infoMessage("VALIDACION",
									"Seleccione Grupo Examen");
							return;
						}
						if (selectedMedico.getId().intValue() == 0
								|| selectedMedico == null
								|| getSucursal() == null
								|| getSucursal().getCompania() == null) {
							FacesUtil.infoMessage("VALIDACION",
									"Seleccione Tipo Examen");
							return;
						}
						rellenarDetalle();
						orden.setPaciente(selectedPaciente);
						orden.setMedico(selectedMedico);
						orden.setEspecialidad(selectedMedico
								.obtenerEspecialidadActiva().getNombre());
						orden.setCompania(getSucursal().getCompania());
						orden.setSucursal(getSucursal());
						orden.setFechaModificacion(new Date());
						orden.setUsuarioRegistro(sessionMain.getUsuarioLogin()
								.getLogin());
						for (LabExamen examen : listaExamenModel.getTarget()) {
							if (!existeExamen(examen, listOrdenDetalles)) {
								LabOrdenDetalle detalle = new LabOrdenDetalle();
								detalle.setDescripcion(examen.getDescripcion());
								detalle.setCompania(sucursal.getCompania());
								detalle.setSucursal(sucursal);
								detalle.setFechaRegistro(new Date());
								detalle.setParametros("");
								detalle.setUsuarioRegistro(sessionMain
										.getUsuarioLogin().getLogin());
								detalle.setLabExamen(examen);
								listOrdenDetalles.add(detalle);
							}
						}
						if (listOrdenDetalles.size() == 0) {
							FacesUtil.infoMessage("VALIDACION",
									"No ha seleccionado ningun examen");
							return;
						}
						labOrdenDao.modificar(orden, listOrdenDetalles);
						ordenSeleccionada = false;
						seeEstudio = false;
						resetEstudio();
					}
				} catch (Exception e) {
					System.out.println("Error en modificacion de labOrden: "
							+ e.getMessage());
				}
			} else {
				FacesUtil
						.errorMessage("No puede editar la orden despues de 24 horas");
			}

		} else {// registra
			try {
				System.out.println("Ingreso a registrar");

				if (selectedPaciente.getId().intValue() == 0
						|| selectedPaciente == null || getSucursal() == null
						|| getSucursal().getCompania() == null) {
					FacesUtil.infoMessage("VALIDACION",
							"Seleccione Grupo Examen");
					return;
				}
				if (selectedMedico.getId().intValue() == 0
						|| selectedMedico == null || getSucursal() == null
						|| getSucursal().getCompania() == null) {
					FacesUtil.infoMessage("VALIDACION",
							"Seleccione Tipo Examen");
					return;
				}
				if (orden.getDescripcion().trim().isEmpty()
						|| orden.getEstado().trim().isEmpty()
						|| getSucursal() == null
						|| getSucursal().getCompania() == null) {
					FacesUtil.infoMessage("VALIDACION",
							"No puede haber campos vacíos");
					return;
				}
				rellenarDetalle();
				orden.setPaciente(selectedPaciente);
				orden.setMedico(selectedMedico);
				orden.setEspecialidad(selectedMedico
						.obtenerEspecialidadActiva().getNombre());
				orden.setCompania(sucursal.getCompania());
				orden.setSucursal(sucursal);
				orden.setFechaRegistro(new Date());
				orden.setHistoriaClinica(historiaClinica);
				orden.setFechaModificacion(orden.getFechaRegistro());
				orden.setUsuarioRegistro(usuario.getLogin());
				orden.setEstado("AC");
				orden.setConsulta(consulta);
				// orden.setFecha(fechaOrden);
				System.out.println("maldita fecha = " + orden.getFecha());
				for (LabExamen examen : listaExamenModel.getTarget()) {
					if (!existeExamen(examen, orden.getListOrdenDetalle())) {
						LabOrdenDetalle detalle = new LabOrdenDetalle();
						detalle.setDescripcion(examen.getDescripcion());
						detalle.setCompania(sucursal.getCompania());
						detalle.setSucursal(sucursal);
						detalle.setFechaRegistro(new Date());
						detalle.setFechaModificacion(detalle.getFechaRegistro());
						detalle.setParametros("");
						detalle.setUsuarioRegistro(usuario.getLogin());
						detalle.setLabExamen(examen);
						listOrdenDetalles.add(detalle);
					}
				}
				if (listOrdenDetalles.size() == 0) {
					FacesUtil.infoMessage("VALIDACION",
							"No ha seleccionado ningun examen");
					return;
				}
				orden = labOrdenDao.registrar(orden, listOrdenDetalles);
				armarUrl("ReportOrdenExamen?pIdOrden=", orden.getId());
				seeEstudio = false;
				resetEstudio();
			} catch (Exception e) {
				System.out.println("Error en registro de labOrden: "
						+ e.getMessage());
			}
		}
	}

	public void imprimirOrdenLab(LabOrden orden) {
		armarUrl("ReportOrdenExamen?pIdOrden=", orden.getId());
	}

	public void registrarImageneologia() {
		if (ordenSeleccionada) {// actualiza
			if (verificarHoraActualizar(labOrden.getFechaRegistro())) {
				try {
					System.out.println("Ingreso a actualizar");
					if (labOrden.getDescripcion().trim().isEmpty()
							|| labOrden.getEstado().trim().isEmpty()
							|| getSucursal() == null
							|| getSucursal().getCompania() == null) {
						FacesUtil.infoMessage("VALIDACION",
								"No puede haber campos vacíos");
						return;
					} else {
						if (selectedPaciente.getId().intValue() == 0
								|| selectedPaciente == null
								|| getSucursal() == null
								|| getSucursal().getCompania() == null) {
							FacesUtil.infoMessage("VALIDACION",
									"Seleccione Grupo Examen");
							return;
						}
						if (selectedMedico.getId().intValue() == 0
								|| selectedMedico == null
								|| getSucursal() == null
								|| getSucursal().getCompania() == null) {
							FacesUtil.infoMessage("VALIDACION",
									"Seleccione Tipo Examen");
							return;
						}
						rellenarDetalleImag();
						labOrden.setPaciente(selectedPaciente);
						labOrden.setMedico(selectedMedico);
						labOrden.setEspecialidad(selectedMedico
								.obtenerEspecialidadActiva().getNombre());
						labOrden.setCompania(getSucursal().getCompania());
						labOrden.setSucursal(getSucursal());
						labOrden.setFechaModificacion(new Date());
						labOrden.setUsuarioRegistro(sessionMain
								.getUsuarioLogin().getLogin());
						for (LabExamenImag examen : listaExamenImagModel
								.getTarget()) {
							if (!existeExamenImag(examen, listOrdenDetallesImag)) {
								LabOrdenDetalleImag detalle = new LabOrdenDetalleImag();
								detalle.setDescripcion(examen.getDescripcion());
								detalle.setCompania(sucursal.getCompania());
								detalle.setSucursal(sucursal);
								detalle.setFechaRegistro(new Date());
								detalle.setParametros("");
								detalle.setUsuarioRegistro(sessionMain
										.getUsuarioLogin().getLogin());
								detalle.setLabExamen(examen);
								listOrdenDetallesImag.add(detalle);
							}
						}
						if (listOrdenDetallesImag.size() == 0) {
							FacesUtil.infoMessage("VALIDACION",
									"No ha seleccionado ningun examen");
							return;
						}
						labOrdenImagDao.modificar(labOrden,
								listOrdenDetallesImag);
						ordenSeleccionada = false;
						seeEstudio = false;
						resetImageneologia();
					}
				} catch (Exception e) {
					System.out.println("Error en modificacion de labOrden: "
							+ e.getMessage());
				}
			} else {
				FacesUtil
						.errorMessage("No puede editar la orden despues de 24 horas");
			}

		} else {// registra
			try {
				System.out.println("Ingreso a registrar");

				if (selectedPaciente.getId().intValue() == 0
						|| selectedPaciente == null || getSucursal() == null
						|| getSucursal().getCompania() == null) {
					FacesUtil.infoMessage("VALIDACION",
							"Seleccione Grupo Examen");
					return;
				}
				if (selectedMedico.getId().intValue() == 0
						|| selectedMedico == null || getSucursal() == null
						|| getSucursal().getCompania() == null) {
					FacesUtil.infoMessage("VALIDACION",
							"Seleccione Tipo Examen");
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
				rellenarDetalleImag();
				labOrden.setPaciente(selectedPaciente);
				labOrden.setMedico(selectedMedico);
				labOrden.setEspecialidad(selectedMedico
						.obtenerEspecialidadActiva().getNombre());
				labOrden.setCompania(sucursal.getCompania());
				labOrden.setSucursal(sucursal);
				labOrden.setFechaRegistro(new Date());
				labOrden.setHistoriaClinica(historiaClinica);
				labOrden.setFechaModificacion(labOrden.getFechaRegistro());
				labOrden.setUsuarioRegistro(usuario.getLogin());
				labOrden.setEstado("AC");
				labOrden.setConsulta(consulta);
				// orden.setFecha(fechaOrden);
				System.out.println("maldita fecha = " + labOrden.getFecha());
				for (LabExamenImag examen : listaExamenImagModel.getTarget()) {
					if (!existeExamenImag(examen,
							labOrden.getListOrdenDetalle())) {
						LabOrdenDetalleImag detalle = new LabOrdenDetalleImag();
						detalle.setDescripcion(examen.getDescripcion());
						detalle.setCompania(sucursal.getCompania());
						detalle.setSucursal(sucursal);
						detalle.setFechaRegistro(new Date());
						detalle.setFechaModificacion(detalle.getFechaRegistro());
						detalle.setParametros("");
						detalle.setUsuarioRegistro(usuario.getLogin());
						detalle.setLabExamen(examen);
						listOrdenDetallesImag.add(detalle);
					}
				}
				if (listOrdenDetallesImag.size() == 0) {
					FacesUtil.infoMessage("VALIDACION",
							"No ha seleccionado ningun examen");
					return;
				}
				labOrden = labOrdenImagDao.registrar(labOrden,
						listOrdenDetallesImag);
				armarUrl("ReportOrdenExamenImagenologia?pIdOrden=",
						labOrden.getId());
				seeEstudio = false;
				resetImageneologia();
			} catch (Exception e) {
				System.out.println("Error en registro de labOrden: "
						+ e.getMessage());
			}
		}
	}

	public void imprimirOrdenImag(LabOrdenImag labOrdenImag) {
		armarUrl("ReportOrdenExamenImagenologia?pIdOrden=",
				labOrdenImag.getId());
	}

	public void registrarEvolucion() {
		if (evolucionSeleccionada) {// update
			if (verificarHoraActualizar(evolucion.getFechaRegistro())) {
				if (evolucion.getEvolucion().trim().isEmpty()
						|| evolucion.getObservacion().trim().isEmpty()) {
					FacesUtil.errorMessage("No pueden haber campos vacíos.");
				} else {
					evolucion.setMedico(selectedMedico);
					evolucion.setFechaModificacion(new Date());
					evolucion.setUsuarioRegistro(usuario.getLogin());
					evolucionDao.modificar(evolucion);
					evolucionSeleccionada = false;
				}
			} else {
				FacesUtil
						.errorMessage("Ya se cumplieron 24 horas a partir del registro de la evolución, no puede editar.");
			}
		} else {// registrar
			if (evolucion.getEvolucion().trim().isEmpty()
					|| evolucion.getObservacion().trim().isEmpty()) {
				FacesUtil.errorMessage("No pueden haber campos vacíos.");
			} else {
				evolucion.setConsulta(consulta);
				evolucion.setMedico(selectedMedico);
				evolucion.setEstado("AC");
				evolucion.setSucursal(sucursal);
				evolucion.setCompania(sucursal.getCompania());
				evolucion.setHistoriaClinica(historiaClinica);
				evolucion.setFechaRegistro(new Date());
				evolucion.setFechaModificacion(evolucion.getFechaRegistro());
				evolucion.setUsuarioRegistro(usuario.getLogin());
				evolucionDao.registrar(evolucion);
			}
		}
		resetEvolucion();
	}

	public void registrarContrareferencia() {
		if (contrareferenciaSeleccionada) {// update
			if (verificarHoraActualizar(contrareferencia.getFechaRegistro())) {
				if (contrareferencia.getImpresionDiagnostica().trim().isEmpty()
						|| contrareferencia.getConsultaExterna().trim()
								.isEmpty()
						|| contrareferencia.getConducta().trim().isEmpty()
						|| contrareferencia.getSala().trim().isEmpty()
						|| contrareferencia.getConsultaGuardia().trim()
								.isEmpty()) {
					FacesUtil.errorMessage("No pueden haber campos vacíos.");
				} else {
					contrareferencia.setMedicoReceptor(selectedMedico);
					contrareferencia.setFechaModificacion(new Date());
					contrareferencia.setUsuarioRegistro(usuario.getLogin());
					contrareferenciaDao.modificar(contrareferencia);
					contrareferenciaSeleccionada = false;
				}
			} else {
				FacesUtil
						.errorMessage("Ya se cumplieron 24 horas a partir del registro de la transferencia, no puede editar el motivo.");
			}
		} else {// registrar
			if (contrareferencia.getImpresionDiagnostica().trim().isEmpty()
					|| contrareferencia.getConsultaExterna().trim().isEmpty()
					|| contrareferencia.getConducta().trim().isEmpty()
					|| contrareferencia.getSala().trim().isEmpty()
					|| contrareferencia.getConsultaGuardia().trim().isEmpty()) {
				FacesUtil.errorMessage("No pueden haber campos vacíos.");
			} else {
				contrareferencia.setConsulta(consulta);
				contrareferencia.setMedicoReceptor(selectedMedico);
				contrareferencia.setEstado("AC");
				contrareferencia.setSucursal(sucursal);
				contrareferencia.setCompania(sucursal.getCompania());
				contrareferencia.setHistoriaClinica(historiaClinica);
				contrareferencia.setFechaRegistro(new Date());
				contrareferencia.setFechaModificacion(contrareferencia
						.getFechaRegistro());
				contrareferencia.setUsuarioRegistro(usuario.getLogin());
				contrareferencia = contrareferenciaDao
						.registrar(contrareferencia);
				armarUrl("ReporteContrareferenciaHC?idContrareferencia=",
						contrareferencia.getId());
			}
		}
		resetContrareferencia();
	}

	public void imprimirContraTransferencia() {
		armarUrl("ReporteContrareferenciaHC?idContrareferencia=",
				contrareferencia.getId());
	}

	public void registrarTransferencia() {
		if (transferenciaSeleccionada) {// update
			if (verificarHoraActualizar(transferencia.getFechaRegistro())) {
				if (transferencia.getImpresionDiagnostica().trim().isEmpty()
						|| transferencia.getExamenes().trim().isEmpty()
						|| transferencia.getMedicoReceptor().trim().isEmpty()
						|| transferencia.getMotivo().trim().isEmpty()
						|| transferencia.getTratamiento().trim().isEmpty()
						|| transferencia.getEspecialidadMedicoReceptor().trim()
								.isEmpty()) {
					FacesUtil.errorMessage("No pueden haber campos vacíos.");
				} else {
					transferencia.setMedicoEmisor(selectedMedico);
					transferencia.setFechaModificacion(new Date());
					transferencia.setUsuarioRegistro(usuario.getLogin());
					transferenciaDao.modificar(transferencia);
					transferenciaSeleccionada = false;
				}
			} else {
				FacesUtil
						.errorMessage("Ya se cumplieron 24 horas a partir del registro de la transferencia, no puede editar el motivo.");
			}
		} else {// registrar
			if (transferencia.getImpresionDiagnostica().trim().isEmpty()
					|| transferencia.getExamenes().trim().isEmpty()
					|| transferencia.getMedicoReceptor().trim().isEmpty()
					|| transferencia.getMotivo().trim().isEmpty()
					|| transferencia.getTratamiento().trim().isEmpty()
					|| transferencia.getEspecialidadMedicoReceptor().trim()
							.isEmpty()) {
				FacesUtil.errorMessage("No pueden haber campos vacíos.");
			} else {
				System.out
						.println("ingreso a registrar transferencia controller");
				transferencia.setConsulta(consulta);
				transferencia.setMedicoEmisor(selectedMedico);
				transferencia.setEstado("II");
				transferencia.setSucursal(sucursal);
				transferencia.setCompania(sucursal.getCompania());
				transferencia.setHistoriaClinica(historiaClinica);
				transferencia.setFechaRegistro(new Date());
				transferencia.setFechaModificacion(transferencia
						.getFechaRegistro());
				transferencia.setUsuarioRegistro(usuario.getLogin());
				transferencia = transferenciaDao.registrar(transferencia);
				armarUrl("ReporteTransferenciaHC?idTransferencia=",
						transferencia.getId());
			}
		}
		resetTransferencia();
	}

	public void imprimirTransferencia() {
		armarUrl("ReporteTransferenciaHC?idTransferencia=",
				transferencia.getId());
	}

	public void eliminarTransferencia() {
		transferenciaDao.eliminar(transferencia);
		defaultValues();
	}

	public void registrarMedicamentoNuevo() {
		medicamento.setCompania(sucursal.getCompania());
		medicamento.setEstado("AC");
		medicamento.setFechaRegistro(new Date());
		medicamento.setFechaModificacion(medicamento.getFechaRegistro());
		medicamento.setSucursal(sucursal);
		medicamento.setUsuarioRegistro(usuario.getLogin());
		medicamento = medicamentoDao.registrar(medicamento);
		detalleMedicamento = new DetalleMedicamento();
		detalleMedicamento.setMedicamento(medicamento);
		query = medicamento.getNombreGenerico() + " - "
				+ medicamento.getPresentacion();
		// FacesUtil.updateComponent("formDlg001");
		FacesUtil.hideDialog("dlgMedicamentoNuevo");
		;
	}

	public void registrarMedicamento() {
		if (detalleMedicamentoSeleccionado) {// update
			if (verificarHoraActualizar(detalleMedicamentoSelected
					.getFechaRegistro())) {
				if (detalleMedicamentoSelected.getMedicamento().getId() == 0
						|| detalleMedicamentoSelected.getAdministracion()
								.trim().isEmpty()
						|| detalleMedicamentoSelected.getCantidad() == 0) {
					FacesUtil.errorMessage("No pueden haber campos vacíos.");
				} else {
					if (detalleMedicamento.getMedicamento().getId() != 0)
						detalleMedicamentoSelected
								.setMedicamento(detalleMedicamento
										.getMedicamento());
					detalleMedicamentoSelected.setMedico(selectedMedico);
					detalleMedicamentoSelected.setCantidad(detalleMedicamento
							.getCantidad());
					detalleMedicamentoSelected
							.setAdministracion(detalleMedicamento
									.getAdministracion());
					detalleMedicamentoSelected.setFechaModificacion(new Date());
					detalleMedicamentoSelected.setUsuarioRegistro(usuario
							.getLogin());
					detalleMedicamentoDao.modificar(detalleMedicamentoSelected);
					detalleMedicamentoSeleccionado = false;
				}
			} else {
				FacesUtil
						.errorMessage("Ya se cumplieron las 24 horas no puede editar el medicamento.");
			}
		} else {// registrar
			if (detalleMedicamento.getMedicamento().getId() == 0
					|| detalleMedicamento.getAdministracion().trim().isEmpty()
					|| detalleMedicamento.getCantidad() == 0) {
				FacesUtil.errorMessage("No pueden haber campos vacíos.");
			} else {
				detalleMedicamento.setConsulta(consulta);
				detalleMedicamento.setMedico(selectedMedico);
				detalleMedicamento.setReceta(receta);
				detalleMedicamento.setEstado("AC");
				detalleMedicamento.setSucursal(sucursal);
				detalleMedicamento.setCompania(sucursal.getCompania());
				detalleMedicamento.setHistoriaClinica(historiaClinica);
				detalleMedicamento.setFechaRegistro(new Date());
				detalleMedicamento.setFechaModificacion(detalleMedicamento
						.getFechaRegistro());
				detalleMedicamento.setUsuarioRegistro(usuario.getLogin());
				detalleMedicamentoDao.registrar(detalleMedicamento);
			}
		}
		resetIndicacion();
	}

	public void registrarExamenFisico() {
		if (examenFisicoSeleccionado) {
			try {
				if (verificarHoraActualizar(examenFisico.getFechaRegistro())) {
					if (examenFisico.getAbdomen().trim().isEmpty()
							|| examenFisico.getAltura() == 0
							|| examenFisico.getCabeza().trim().isEmpty()
							|| examenFisico
									.getClasificacionIndiceMasaCorporal()
									.trim().isEmpty()
							|| examenFisico.getClasificacionPresionArterial()
									.trim().isEmpty()
							|| examenFisico.getCuello().trim().isEmpty()
							|| examenFisico.getExploracionGeneral().trim()
									.isEmpty()
							|| examenFisico.getFrecuenciaCardiaca() == 0
							|| examenFisico.getIndiceMasaCorporal() == 0
							|| examenFisico.getMiembros().trim().isEmpty()
							|| examenFisico.getPeso() == 0
							|| examenFisico.getPresionDiastolica() == 0
							|| examenFisico.getPresionSistolica() == 0
							|| examenFisico.getPulso() == 0
							|| examenFisico.getTemperatura() == 0
							|| examenFisico.getTorax().trim().isEmpty()
							|| getSucursal() == null
							|| getSucursal().getCompania() == null) {
						FacesUtil.infoMessage("VALIDACION",
								"No puede haber campos vacíos");
					} else {
						System.out.println("historia clinica = "
								+ examenFisico.getHistoriaClinica().getId());
						examenFisico.setMedico(selectedMedico);
						examenFisico.setFechaModificacion(new Date());
						examenFisico.setUsuarioRegistro(usuario.getLogin());
						examenFisicoSegmentarioDao.modificar(examenFisico);
					}
				} else {
					FacesUtil
							.errorMessage("Ya se cumplieron 24 horas a partir del registro del motivo, no puede editar el motivo.");
				}
				resetExamenFisico();
			} catch (Exception e) {
				System.out.println("Error en modificacion de motivo: "
						+ e.getMessage());
			}
		} else {
			if (examenFisico.getAbdomen().trim().isEmpty()
					|| examenFisico.getAltura() == 0
					|| examenFisico.getCabeza().trim().isEmpty()
					|| examenFisico.getClasificacionIndiceMasaCorporal().trim()
							.isEmpty()
					|| examenFisico.getClasificacionPresionArterial().trim()
							.isEmpty()
					|| examenFisico.getCuello().trim().isEmpty()
					|| examenFisico.getExploracionGeneral().trim().isEmpty()
					|| examenFisico.getFrecuenciaCardiaca() == 0
					|| examenFisico.getIndiceMasaCorporal() == 0
					|| examenFisico.getMiembros().trim().isEmpty()
					|| examenFisico.getPeso() == 0
					|| examenFisico.getPresionDiastolica() == 0
					|| examenFisico.getPresionSistolica() == 0
					|| examenFisico.getPulso() == 0
					|| examenFisico.getTemperatura() == 0
					|| examenFisico.getTorax().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
			} else {
				examenFisico.setConsulta(consulta);
				examenFisico.setMedico(selectedMedico);
				if (examenFisicoSignosVitales != null
						&& examenFisicoSignosVitales.getId() > 0)
					examenFisico.setExamenEnfermera(examenFisicoSignosVitales);
				else
					examenFisico.setExamenEnfermera(null);
				examenFisico.setHistoriaClinica(historiaClinica);
				examenFisico.setFechaRegistro(new Date());
				examenFisico.setFechaModificacion(examenFisico
						.getFechaRegistro());
				examenFisico.setSucursal(getSucursal());
				examenFisico.setCompania(getSucursal().getCompania());
				examenFisico.setUsuarioRegistro(usuario.getLogin());
				examenFisicoSegmentarioDao.registrar(examenFisico);
			}
			resetExamenFisico();
		}
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
							motivo.setMedico(selectedMedico);
							motivo.setFechaModificacion(new Date());
							motivo.setUsuarioRegistro(getUsuario().getLogin());
							motivoDao.modificar(motivo);
							motivoSeleccionado = false;
						}
					}
					resetMotivo();
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
						motivo.setConsulta(consulta);
						motivo.setMedico(selectedMedico);
						motivo.setEstado("AC");
						motivo.setSucursal(getSucursal());
						motivo.setCompania(getSucursal().getCompania());
						motivo.setFechaRegistro(new Date());
						motivo.setFechaModificacion(motivo.getFechaRegistro());
						motivo.setUsuarioRegistro(getUsuario().getLogin());
						motivo.setHistoriaClinica(historiaClinica);
						motivoDao.registrar(motivo);
						resetMotivo();
					}
				}
			} catch (Exception e) {
				System.out.println("Error en registro de motivo: "
						+ e.getMessage());
			}
		}
	}

	public void registrarDiagnosticoPresuntivo() {
		if (diagnosticoPresuntivoSeleccionado) {// actualizar
			try {
				if (verificarHoraActualizar(diagnosticoPresuntivo
						.getFechaRegistro())) {
					if (diagnosticoPresuntivo.getDescripcion().trim().isEmpty()
							|| diagnosticoPresuntivo.getEstado().trim()
									.isEmpty() || getSucursal() == null
							|| getSucursal().getCompania() == null) {
						FacesUtil.infoMessage("VALIDACION",
								"No puede haber campos vacíos");
					} else {
						diagnosticoPresuntivo.setMedico(selectedMedico);
						diagnosticoPresuntivo.setFechaModificacion(new Date());
						diagnosticoPresuntivo.setUsuarioRegistro(getUsuario()
								.getLogin());
						diagnosticoPresuntivoDao
								.modificar(diagnosticoPresuntivo);
						diagnosticoPresuntivoSeleccionado = false;
					}
					resetDiagnosticoPresuntivo();
				} else {
					FacesUtil
							.errorMessage("Ya se cumplieron 24 horas a partir del registro del diagnostico, no puede editar el diagnostico.");
				}
			} catch (Exception e) {
				System.out
						.println("Error en modificacion de diagnostico presuntivo: "
								+ e.getMessage());
			}
		} else {// registrar
			try {
				if (diagnosticoPresuntivo.getDescripcion().trim().isEmpty()
						|| getSucursal() == null
						|| getSucursal().getCompania() == null) {
					FacesUtil.infoMessage("VALIDACION",
							"No puede haber campos vacíos");
					return;
				} else {
					diagnosticoPresuntivo.setConsulta(consulta);
					diagnosticoPresuntivo.setMedico(selectedMedico);
					diagnosticoPresuntivo.setEstado("AC");
					diagnosticoPresuntivo.setSucursal(getSucursal());
					diagnosticoPresuntivo.setCompania(getSucursal()
							.getCompania());
					diagnosticoPresuntivo.setFechaRegistro(new Date());
					diagnosticoPresuntivo
							.setFechaModificacion(diagnosticoPresuntivo
									.getFechaRegistro());
					diagnosticoPresuntivo.setUsuarioRegistro(getUsuario()
							.getLogin());
					diagnosticoPresuntivo.setHistoriaClinica(historiaClinica);
					diagnosticoPresuntivoDao.registrar(diagnosticoPresuntivo);
					resetDiagnosticoPresuntivo();
				}
			} catch (Exception e) {
				System.out
						.println("Error en registro de diagnostico presuntivo: "
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
						enfermedad.setMedico(selectedMedico);
						enfermedad.setFechaModificacion(new Date());
						enfermedad.setUsuarioRegistro(getUsuario().getLogin());
						enfermedadDao.modificar(enfermedad);
						enfermedadSeleccionado = false;
					}
				} catch (Exception e) {
					System.out.println("Error en modificacion de enfermedad: "
							+ e.getMessage());
				}
			} else {
				FacesUtil
						.errorMessage("Ya se cumplieron 24 horas a partir del registro del motivo, no puede editar el motivo.");
			}
			resetEnfermedad();
		} else {// registrar
			try {
				if (enfermedad.getNombre().trim().isEmpty()
						|| getSucursal() == null
						|| getSucursal().getCompania() == null) {
					FacesUtil.infoMessage("VALIDACION",
							"No puede haber campos vacíos");
				} else {
					enfermedad.setConsulta(consulta);
					enfermedad.setMedico(selectedMedico);
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
			resetEnfermedad();
		}
	}

	public void registrarAntecedenteNuevo() {
		Antecedente nuevo = new Antecedente();
		nuevo.setDescripcion(descripcionAntecedente);
		nuevo.setCompania(sucursal.getCompania());
		nuevo.setEstado("AC");
		nuevo.setFechaRegistro(new Date());
		nuevo.setFechaModificacion(nuevo.getFechaRegistro());
		nuevo.setSucursal(sucursal);
		nuevo.setUsuarioRegistro(usuario.getLogin());
		nuevo = antecedenteDao.registrar(nuevo);
		DetalleAntecedente detalleAntecedente = new DetalleAntecedente();
		detalleAntecedente.setAntecedente(nuevo);
		if (detalleAntecedenteDao.verificarRepetido(historiaClinica,
				detalleAntecedente)) {
			FacesUtil.infoMessage("VALIDACIÓN", "Ya existe el antecedente.");
			resetAntecedente();
			return;
		}
		detalleAntecedente.setConsulta(consulta);
		detalleAntecedente.setMedico(selectedMedico);
		detalleAntecedente.setHistoriaClinica(historiaClinica);
		detalleAntecedente.setEstado("AC");
		detalleAntecedente.setFechaRegistro(new Date());
		detalleAntecedente.setFechaModificacion(detalleAntecedente
				.getFechaRegistro());
		detalleAntecedente.setSucursal(getSucursal());
		detalleAntecedente.setCompania(getSucursal().getCompania());
		detalleAntecedente.setUsuarioRegistro(getUsuario().getLogin());
		detalleAntecedenteDao.registrar(detalleAntecedente);
		FacesUtil.updateComponent("formDlg001");
		FacesUtil.showDialog("dlgAntecedente");
	}

	public void registrarAntecedente() {
		List<String> target = listaAntecedentesDual.getTarget();
		System.out.println("lista de antecedentes a meter en source = "
				+ listaAntecedentesDualSource.size());
		System.out.println("lista de antecedentes a meter = " + target.size());
		List<DetalleAntecedente> antecedentes = new ArrayList<DetalleAntecedente>();
		for (String antecedente : target) {
			Antecedente auxiliar = new Antecedente();
			auxiliar = antecedenteDao.obtenerAntecedente(antecedente);
			DetalleAntecedente detalleAntecedente = new DetalleAntecedente();
			detalleAntecedente.setAntecedente(auxiliar);
			if (detalleAntecedenteDao.verificarRepetido(historiaClinica,
					detalleAntecedente)) {
				FacesUtil
						.infoMessage("VALIDACIÓN", "Ya existe el antecedente.");
				resetAntecedente();
				return;
			}
			detalleAntecedente.setConsulta(consulta);
			detalleAntecedente.setMedico(selectedMedico);
			detalleAntecedente.setHistoriaClinica(historiaClinica);
			detalleAntecedente.setEstado("AC");
			detalleAntecedente.setFechaRegistro(new Date());
			detalleAntecedente.setFechaModificacion(detalleAntecedente
					.getFechaRegistro());
			detalleAntecedente.setSucursal(getSucursal());
			detalleAntecedente.setCompania(getSucursal().getCompania());
			detalleAntecedente.setUsuarioRegistro(getUsuario().getLogin());
			listaDetalleAntecedentes.add(detalleAntecedente);
			antecedentes.add(detalleAntecedente);
		}
		if (detalleAntecedenteDao.registrar(antecedentes)) {
			FacesUtil.infoMessage("REGISTRO", "Registro exitoso");
		} else {
			FacesUtil.infoMessage("REGISTRO", "Error en el registro");
		}
		resetAntecedente();
	}

	public void probando() {
		System.out.println("entro al método probando()");
	}

	public void actualizarDiagnostico() {
		System.out.println("entro a actualizar detalle diagnostico");
		System.out.println("diagnostico anterior = " + comentario);
		System.out.println("diagnostico = "
				+ detalleDiagnostico.getDiagnostico().getDescripcion() + " "
				+ detalleDiagnostico.getComentario() + " "
				+ detalleDiagnostico.getFechaRegistro());
		System.out.println("diagnosticoDetalleSelected = "
				+ diagnosticoDetalleSelected.getDescripcion() + " "
				+ diagnosticoDetalleSelected.getFechaRegistro());
		System.out.println("diagnosticoDetalle = "
				+ diagnosticoDetalle.getDescripcion() + " "
				+ diagnosticoDetalle.getFechaRegistro());
		if (verificarHoraActualizar(detalleDiagnostico.getFechaRegistro())) {
			if (detalleDiagnostico.getComentario().trim().isEmpty()
					|| detalleDiagnostico.getDiagnostico().getId() == 0) {
				FacesUtil.errorMessage("No pueden haber campos vacios.");
			} else {
				if (diagnosticoDetalleSelected.getId() != 0)
					detalleDiagnostico
							.setDiagnostico(diagnosticoDetalleSelected);
				System.out.println("diagnostico = "
						+ detalleDiagnostico.getDiagnostico().getDescripcion()
						+ " " + detalleDiagnostico.getComentario());
				detalleDiagnostico.setMedico(selectedMedico);
				detalleDiagnostico.setEstado("AC");
				detalleDiagnostico.setFechaModificacion(new Date());
				detalleDiagnostico.setUsuarioRegistro(usuario.getLogin());
				detalleDiagnosticoDao.modificar(detalleDiagnostico);
				detalleDiagnosticoSeleccionado = false;
			}
		} else {
			FacesUtil
					.errorMessage("Ya se cumplieron 24 horas a partir del registro del diagnostico, no puede editar el motivo.");
		}
		resetDiagnostico();
	}

	public void actualizarAntecedente() {
		System.out.println("entro a actualizar detalle antecedente");
		System.out.println("antecedente anterior = " + comentario);
		System.out.println("antecedente = "
				+ detalleAntecedente.getAntecedente().getDescripcion() + " "
				+ detalleAntecedente.getDetalle() + " "
				+ detalleAntecedente.getFechaRegistro());
		System.out.println("antecedenteDetalleSelected = "
				+ antecedenteDetalleSelected.getDescripcion() + " "
				+ antecedenteDetalleSelected.getFechaRegistro());
		System.out.println("antecedenteDetalle = "
				+ antecedenteDetalle.getDescripcion() + " "
				+ antecedenteDetalle.getFechaRegistro());
		if (verificarHoraActualizar(detalleAntecedente.getFechaRegistro())) {
			if (detalleAntecedente.getDetalle().trim().isEmpty()
					|| detalleAntecedente.getAntecedente().getId() == 0) {
				FacesUtil.errorMessage("No pueden haber campos vacios.");
			} else {
				if (antecedenteDetalleSelected.getId() != 0)
					detalleAntecedente
							.setAntecedente(antecedenteDetalleSelected);
				System.out.println("antecedente = "
						+ detalleAntecedente.getAntecedente().getDescripcion()
						+ " " + detalleAntecedente.getDetalle());
				detalleAntecedente.setMedico(selectedMedico);
				detalleAntecedente.setEstado("AC");
				detalleAntecedente.setFechaModificacion(new Date());
				detalleAntecedente.setUsuarioRegistro(usuario.getLogin());
				detalleAntecedenteDao.modificar(detalleAntecedente);
				detalleAntecedenteSeleccionado = false;
			}
		} else {
			FacesUtil
					.errorMessage("Ya se cumplieron 24 horas a partir del registro del antecedente, no puede editar el motivo.");
		}
		resetAntecedente();
	}

	public void registrarDiagnosticoNuevo() {
		Diagnostico nuevo = new Diagnostico();
		nuevo.setDescripcion(descripcionDiagnostico);
		nuevo.setCompania(sucursal.getCompania());
		nuevo.setEstado("AC");
		nuevo.setFechaRegistro(new Date());
		nuevo.setFechaModificacion(nuevo.getFechaRegistro());
		nuevo.setSucursal(sucursal);
		nuevo.setUsuarioRegistro(usuario.getLogin());
		nuevo = diagnosticoDao.registrar(nuevo);
		DetalleDiagnostico detalleDiagnostico = new DetalleDiagnostico();
		detalleDiagnostico.setDiagnostico(nuevo);
		if (detalleDiagnosticoDao.verificarRepetido(historiaClinica,
				detalleDiagnostico)) {
			FacesUtil.infoMessage("VALIDACIÓN", "Ya existe el diagnostico.");
			resetDiagnostico();
			return;
		}
		detalleDiagnostico.setConsulta(consulta);
		detalleDiagnostico.setMedico(selectedMedico);
		detalleDiagnostico.setHistoriaClinica(historiaClinica);
		detalleDiagnostico.setEstado("AC");
		detalleDiagnostico.setFechaRegistro(new Date());
		detalleDiagnostico.setFechaModificacion(detalleDiagnostico
				.getFechaRegistro());
		detalleDiagnostico.setSucursal(getSucursal());
		detalleDiagnostico.setCompania(getSucursal().getCompania());
		detalleDiagnostico.setUsuarioRegistro(getUsuario().getLogin());
		detalleDiagnosticoDao.registrar(detalleDiagnostico);
		FacesUtil.updateComponent("formDlg001");
		FacesUtil.showDialog("dlgDiagnostico");
	}

	public void registrarDiagnostico() {
		System.out.println("entro a registrar ");
		if (detalleDiagnosticoSeleccionado) {
			System.out.println("entro a registrar ");
		} else {
			List<String> target = listaDiagnosticosDual.getTarget();
			System.out.println("lista de diagnosticos a meter en source = "
					+ listaDiagnosticosDualSource.size());
			System.out.println("lista de diagnosticos a meter = "
					+ target.size());
			List<DetalleDiagnostico> diagnosticos = new ArrayList<DetalleDiagnostico>();
			for (String antecedente : target) {
				Diagnostico auxiliar = new Diagnostico();
				auxiliar = diagnosticoDao.obtenerDiagnostico(antecedente);
				DetalleDiagnostico detalleDiagnostico = new DetalleDiagnostico();
				detalleDiagnostico.setDiagnostico(auxiliar);
				/*
				 * if (detalleDiagnosticoDao.verificarRepetido(historiaClinica,
				 * detalleDiagnostico)) { FacesUtil .infoMessage("VALIDACIÓN",
				 * "Ya existe el diagnostico."); defaultValues(); return; }
				 */
				detalleDiagnostico.setConsulta(consulta);
				detalleDiagnostico.setMedico(selectedMedico);
				detalleDiagnostico.setHistoriaClinica(historiaClinica);
				detalleDiagnostico.setEstado("II");
				detalleDiagnostico.setFechaRegistro(new Date());
				detalleDiagnostico.setFechaModificacion(detalleDiagnostico
						.getFechaRegistro());
				detalleDiagnostico.setSucursal(getSucursal());
				detalleDiagnostico.setCompania(getSucursal().getCompania());
				detalleDiagnostico.setUsuarioRegistro(getUsuario().getLogin());
				listaDetalleDiagnosticos.add(detalleDiagnostico);
				diagnosticos.add(detalleDiagnostico);
			}
			if (detalleDiagnosticoDao.registrar(diagnosticos)) {
				FacesUtil.infoMessage("REGISTRO", "Registro exitoso");
			} else {
				FacesUtil.errorMessage("Error en el registro");
			}
		}
		resetDiagnostico();
	}

	public void registrar() {
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
			historiaClinica.setEspecialidad(selectedMedico
					.obtenerEspecialidadActiva().getNombre());
			historiaClinica.setPaciente(selectedPaciente);
			historiaClinica.setCompania(sucursal.getCompania());
			historiaClinica.setSucursal(sucursal);
			historiaClinica.setFechaRegistro(new Date());
			historiaClinica.setFechaModificacion(historiaClinica
					.getFechaRegistro());
			historiaClinica.setUsuarioRegistro(usuario.getLogin());

			HistoriaClinica r = historiaClinicaDao.registrar(historiaClinica);
			if (r != null) {
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

	public void onTabChange(TabChangeEvent event) {
		// tabIndex = 0;
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

	public void actionNuevoDiagnosticoPresuntivo() {
		diagnosticoPresuntivo = new DiagnosticoPresuntivo();
		FacesUtil.updateComponent("formDlg001");
		FacesUtil.showDialog("dlgDiagnosticoPresuntivo");
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

	public void actionNuevoDiagnostico() {
		diagnostico = new Diagnostico();
		FacesUtil.updateComponent("formDlg001");
		FacesUtil.showDialog("dlgDiagnostico");
	}

	public void actionNuevaReceta() {
		if (crearReceta)
			receta = detalleMedicamentoDao.obtenerMax(sucursal.getCompania());
		crearReceta = false;
	}

	public void actionNuevoExamenFisico() {
		setExamenFisico(new ExamenFisicoSegmentario());
		FacesUtil.updateComponent("formDlg001");
		FacesUtil.showDialog("dlgExamenFisico");
	}

	public void imprimir() {
		crearReceta = true;
		armarUrl("ReporteRecetaHC?receta=", receta);
		List<DetalleMedicamento> recetas = detalleMedicamentoDao
				.obtenerPorReceta(receta, historiaClinica,
						sucursal.getCompania());
		System.out.println("medicamentos en receta = " + recetas.size());
		actionNuevaReceta();
	}

	public void onSelectPagina() {
		currentPage1 = "/pages/historias_clinicas/registro_detalle_historias_clinicas/"
				+ pagina + ".xhtml";
	}

	// ONCOMPLETETEXT TIPO ATENCION
	// public List<TipoAtencion> completeTipoAtencion(String query) {
	// String upperQuery = query.toUpperCase();
	// listaTipoAtencions = tipoAtencionDao.obtenerPorCompania(upperQuery,
	// sucursal.getCompania());
	// return listaTipoAtencions;
	// }
	//
	// public void onRowSelectTipoAtencionClick(SelectEvent event) {
	// String nombre = event.getObject().toString();
	// for (TipoAtencion i : listaTipoAtencions) {
	// if (i.getNombre().equals(nombre)) {
	// selectedTipoAtencion = i;
	// return;
	// }
	// }
	// }

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

	public List<ExamenFisicoSignosVitales> getListaExamenFisicos() {
		return listaExamenFisicos;
	}

	public void setListaExamenFisicos(
			List<ExamenFisicoSignosVitales> listaExamenFisicos) {
		this.listaExamenFisicos = listaExamenFisicos;
	}

	public boolean isExamenFisicoSeleccionado() {
		return examenFisicoSeleccionado;
	}

	public void setExamenFisicoSeleccionado(boolean examenFisicoSeleccionado) {
		this.examenFisicoSeleccionado = examenFisicoSeleccionado;
	}

	public List<DetalleDiagnostico> getListaDetalleDiagnosticos() {
		return listaDetalleDiagnosticos;
	}

	public void setListaDetalleDiagnosticos(
			List<DetalleDiagnostico> listaDetalleDiagnosticos) {
		this.listaDetalleDiagnosticos = listaDetalleDiagnosticos;
	}

	public List<TipoDiagnostico> getListaTipoDiagnosticos() {
		return listaTipoDiagnosticos;
	}

	public void setListaTipoDiagnosticos(
			List<TipoDiagnostico> listaTipoDiagnosticos) {
		this.listaTipoDiagnosticos = listaTipoDiagnosticos;
	}

	public Diagnostico getDiagnostico() {
		return diagnostico;
	}

	public TipoDiagnostico getTipoDiagnostico() {
		return tipoDiagnostico;
	}

	public void setDiagnostico(Diagnostico diagnostico) {
		this.diagnostico = diagnostico;
	}

	public void setTipoDiagnostico(TipoDiagnostico tipoDiagnostico) {
		this.tipoDiagnostico = tipoDiagnostico;
	}

	public DualListModel<String> getListaDiagnosticosDual() {
		return listaDiagnosticosDual;
	}

	public List<String> getListaDiagnosticosDualSource() {
		return listaDiagnosticosDualSource;
	}

	public List<String> getListaDiagnosticosDualTarget() {
		return listaDiagnosticosDualTarget;
	}

	public void setListaDiagnosticosDual(
			DualListModel<String> listaDiagnosticosDual) {
		this.listaDiagnosticosDual = listaDiagnosticosDual;
	}

	public void setListaDiagnosticosDualSource(
			List<String> listaDiagnosticosDualSource) {
		this.listaDiagnosticosDualSource = listaDiagnosticosDualSource;
	}

	public void setListaDiagnosticosDualTarget(
			List<String> listaDiagnosticosDualTarget) {
		this.listaDiagnosticosDualTarget = listaDiagnosticosDualTarget;
	}

	public List<Diagnostico> getListaDiagnosticos() {
		return listaDiagnosticos;
	}

	public void setListaDiagnosticos(List<Diagnostico> listaDiagnosticos) {
		this.listaDiagnosticos = listaDiagnosticos;
	}

	public DetalleDiagnostico getDetalleDiagnostico() {
		return detalleDiagnostico;
	}

	public boolean isDetalleDiagnosticoSeleccionado() {
		return detalleDiagnosticoSeleccionado;
	}

	public void setDetalleDiagnostico(DetalleDiagnostico detalleDiagnostico) {
		this.detalleDiagnostico = detalleDiagnostico;
	}

	public void setDetalleDiagnosticoSeleccionado(
			boolean detalleDiagnosticoSeleccionado) {
		this.detalleDiagnosticoSeleccionado = detalleDiagnosticoSeleccionado;
	}

	public DetalleDiagnostico getDetalleDiagnosticoSelected() {
		return detalleDiagnosticoSelected;
	}

	public void setDetalleDiagnosticoSelected(
			DetalleDiagnostico detalleDiagnosticoSelected) {
		this.detalleDiagnosticoSelected = detalleDiagnosticoSelected;
	}

	public Diagnostico getDiagnosticoDetalle() {
		return diagnosticoDetalle;
	}

	public String getComentario() {
		return comentario;
	}

	public void setDiagnosticoDetalle(Diagnostico diagnosticoDetalle) {
		this.diagnosticoDetalle = diagnosticoDetalle;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Diagnostico getDiagnosticoDetalleSelected() {
		return diagnosticoDetalleSelected;
	}

	public void setDiagnosticoDetalleSelected(
			Diagnostico diagnosticoDetalleSelected) {
		this.diagnosticoDetalleSelected = diagnosticoDetalleSelected;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public boolean isDetalleAntecedenteSeleccionado() {
		return detalleAntecedenteSeleccionado;
	}

	public void setDetalleAntecedenteSeleccionado(
			boolean detalleAntecedenteSeleccionado) {
		this.detalleAntecedenteSeleccionado = detalleAntecedenteSeleccionado;
	}

	public Antecedente getAntecedenteDetalle() {
		return antecedenteDetalle;
	}

	public void setAntecedenteDetalle(Antecedente antecedenteDetalle) {
		this.antecedenteDetalle = antecedenteDetalle;
	}

	public Antecedente getAntecedenteDetalleSelected() {
		return antecedenteDetalleSelected;
	}

	public void setAntecedenteDetalleSelected(
			Antecedente antecedenteDetalleSelected) {
		this.antecedenteDetalleSelected = antecedenteDetalleSelected;
	}

	public List<Especialidad> getListaEspecialidadDiagnosticos() {
		return listaEspecialidadDiagnosticos;
	}

	public void setListaEspecialidadDiagnosticos(
			List<Especialidad> listaEspecialidadDiagnosticos) {
		this.listaEspecialidadDiagnosticos = listaEspecialidadDiagnosticos;
	}

	public boolean isMedico() {
		return medico;
	}

	public void setMedico(boolean medico) {
		this.medico = medico;
	}

	public TipoAntecedente getTipoAntecedente() {
		return tipoAntecedente;
	}

	public List<TipoAntecedente> getListaTipoAntecedentes() {
		return listaTipoAntecedentes;
	}

	public void setTipoAntecedente(TipoAntecedente tipoAntecedente) {
		this.tipoAntecedente = tipoAntecedente;
	}

	public void setListaTipoAntecedentes(
			List<TipoAntecedente> listaTipoAntecedentes) {
		this.listaTipoAntecedentes = listaTipoAntecedentes;
	}

	public DetalleMedicamento getDetalleMedicamento() {
		return detalleMedicamento;
	}

	public DetalleMedicamento getDetalleMedicamentoSelected() {
		return detalleMedicamentoSelected;
	}

	public Sucursal getSucursalSelected() {
		return sucursalSelected;
	}

	public List<Sucursal> getListaSucursales() {
		return listaSucursales;
	}

	public List<DetalleMedicamento> getListaDetalleMedicamentos() {
		return listaDetalleMedicamentos;
	}

	public void setDetalleMedicamento(DetalleMedicamento detalleMedicamento) {
		this.detalleMedicamento = detalleMedicamento;
	}

	public void setDetalleMedicamentoSelected(
			DetalleMedicamento detalleMedicamentoSelected) {
		this.detalleMedicamentoSelected = detalleMedicamentoSelected;
	}

	public void setSucursalSelected(Sucursal sucursalSelected) {
		this.sucursalSelected = sucursalSelected;
	}

	public void setListaSucursales(List<Sucursal> listaSucursales) {
		this.listaSucursales = listaSucursales;
	}

	public void setListaDetalleMedicamentos(
			List<DetalleMedicamento> listaDetalleMedicamentos) {
		this.listaDetalleMedicamentos = listaDetalleMedicamentos;
	}

	public Frecuencia getFrecuencia() {
		return frecuencia;
	}

	public Via getVia() {
		return via;
	}

	public Presentacion getPresentacion() {
		return presentacion;
	}

	public List<Frecuencia> getListaFrecuencias() {
		return listaFrecuencias;
	}

	public List<Via> getListaVias() {
		return listaVias;
	}

	public List<Presentacion> getListaPresentaciones() {
		return listaPresentaciones;
	}

	public void setFrecuencia(Frecuencia frecuencia) {
		this.frecuencia = frecuencia;
	}

	public void setVia(Via via) {
		this.via = via;
	}

	public void setPresentacion(Presentacion presentacion) {
		this.presentacion = presentacion;
	}

	public void setListaFrecuencias(List<Frecuencia> listaFrecuencias) {
		this.listaFrecuencias = listaFrecuencias;
	}

	public void setListaVias(List<Via> listaVias) {
		this.listaVias = listaVias;
	}

	public void setListaPresentaciones(List<Presentacion> listaPresentaciones) {
		this.listaPresentaciones = listaPresentaciones;
	}

	public List<Medicamento> getListaMedicamentos() {
		return listaMedicamentos;
	}

	public void setListaMedicamentos(List<Medicamento> listaMedicamentos) {
		this.listaMedicamentos = listaMedicamentos;
	}

	public boolean isTipoBusqueda() {
		return tipoBusqueda;
	}

	public void setTipoBusqueda(boolean tipoBusqueda) {
		this.tipoBusqueda = tipoBusqueda;
	}

	public boolean isDetalleMedicamentoSeleccionado() {
		return detalleMedicamentoSeleccionado;
	}

	public void setDetalleMedicamentoSeleccionado(
			boolean detalleMedicamentoSeleccionado) {
		this.detalleMedicamentoSeleccionado = detalleMedicamentoSeleccionado;
	}

	public boolean isCrearReceta() {
		return crearReceta;
	}

	public void setCrearReceta(boolean crearReceta) {
		this.crearReceta = crearReceta;
	}

	public Integer getReceta() {
		return receta;
	}

	public void setReceta(Integer receta) {
		this.receta = receta;
	}

	public Transferencia getTransferencia() {
		return transferencia;
	}

	public List<Transferencia> getListaTransferencias() {
		return listaTransferencias;
	}

	public boolean isTransferenciaSeleccionada() {
		return transferenciaSeleccionada;
	}

	public void setTransferencia(Transferencia transferencia) {
		this.transferencia = transferencia;
	}

	public void setListaTransferencias(List<Transferencia> listaTransferencias) {
		this.listaTransferencias = listaTransferencias;
	}

	public void setTransferenciaSeleccionada(boolean transferenciaSeleccionada) {
		this.transferenciaSeleccionada = transferenciaSeleccionada;
	}

	public Transferencia getTransferenciaSelected() {
		return transferenciaSelected;
	}

	public void setTransferenciaSelected(Transferencia transferenciaSelected) {
		this.transferenciaSelected = transferenciaSelected;
	}

	public Evolucion getEvolucion() {
		return evolucion;
	}

	public Evolucion getEvolucionSelected() {
		return evolucionSelected;
	}

	public List<DetalleMedicamento> getListaUltimaReceta() {
		return listaUltimaReceta;
	}

	public List<Evolucion> getListaEvoluciones() {
		return listaEvoluciones;
	}

	public boolean isEvolucionSeleccionada() {
		return evolucionSeleccionada;
	}

	public void setEvolucion(Evolucion evolucion) {
		this.evolucion = evolucion;
	}

	public void setEvolucionSelected(Evolucion evolucionSelected) {
		this.evolucionSelected = evolucionSelected;
	}

	public void setListaUltimaReceta(List<DetalleMedicamento> listaUltimaReceta) {
		this.listaUltimaReceta = listaUltimaReceta;
	}

	public void setListaEvoluciones(List<Evolucion> listaEvoluciones) {
		this.listaEvoluciones = listaEvoluciones;
	}

	public void setEvolucionSeleccionada(boolean evolucionSeleccionada) {
		this.evolucionSeleccionada = evolucionSeleccionada;
	}

	/**
	 * @return the banderaTransferencia
	 */
	public boolean isBanderaTransferencia() {
		return banderaTransferencia;
	}

	/**
	 * @param banderaTransferencia
	 *            the banderaTransferencia to set
	 */
	public void setBanderaTransferencia(boolean banderaTransferencia) {
		this.banderaTransferencia = banderaTransferencia;
	}

	public Contrareferencia getContrareferencia() {
		return contrareferencia;
	}

	public Contrareferencia getContrareferenciaSelected() {
		return contrareferenciaSelected;
	}

	public List<Contrareferencia> getListaContrareferencias() {
		return listaContrareferencias;
	}

	public boolean isContrareferenciaSeleccionada() {
		return contrareferenciaSeleccionada;
	}

	public void setContrareferencia(Contrareferencia contrareferencia) {
		this.contrareferencia = contrareferencia;
	}

	public void setContrareferenciaSelected(
			Contrareferencia contrareferenciaSelected) {
		this.contrareferenciaSelected = contrareferenciaSelected;
	}

	public void setListaContrareferencias(
			List<Contrareferencia> listaContrareferencias) {
		this.listaContrareferencias = listaContrareferencias;
	}

	public void setContrareferenciaSeleccionada(
			boolean contrareferenciaSeleccionada) {
		this.contrareferenciaSeleccionada = contrareferenciaSeleccionada;
	}

	public ExamenFisicoSignosVitales getExamenFisicoSignosVitales() {
		return examenFisicoSignosVitales;
	}

	public ExamenFisicoSignosVitales getExamenFisicoSignosVitalesSelected() {
		return examenFisicoSignosVitalesSelected;
	}

	public ExamenFisicoSegmentario getExamenFisico() {
		return examenFisico;
	}

	public ExamenFisicoSegmentario getExamenFisicoSelected() {
		return examenFisicoSelected;
	}

	public void setExamenFisicoSignosVitales(
			ExamenFisicoSignosVitales examenFisicoSignosVitales) {
		this.examenFisicoSignosVitales = examenFisicoSignosVitales;
	}

	public void setExamenFisicoSignosVitalesSelected(
			ExamenFisicoSignosVitales examenFisicoSignosVitalesSelected) {
		this.examenFisicoSignosVitalesSelected = examenFisicoSignosVitalesSelected;
	}

	public void setExamenFisico(ExamenFisicoSegmentario examenFisico) {
		this.examenFisico = examenFisico;
	}

	public void setExamenFisicoSelected(
			ExamenFisicoSegmentario examenFisicoSelected) {
		this.examenFisicoSelected = examenFisicoSelected;
	}

	public List<ExamenFisicoSegmentario> getListaExaamenFisicos() {
		return listaExaamenFisicos;
	}

	public void setListaExaamenFisicos(
			List<ExamenFisicoSegmentario> listaExaamenFisicos) {
		this.listaExaamenFisicos = listaExaamenFisicos;
	}

	public LabOrden getOrden() {
		return orden;
	}

	public void setOrden(LabOrden orden) {
		this.orden = orden;
	}

	public List<LabOrden> getListaOrdenes() {
		return listaOrdenes;
	}

	public void setListaOrdenes(List<LabOrden> listaOrdenes) {
		this.listaOrdenes = listaOrdenes;
	}

	public LabOrden getOrdenSelected() {
		return ordenSelected;
	}

	public void setOrdenSelected(LabOrden ordenSelected) {
		this.ordenSelected = ordenSelected;
	}

	public boolean isOrdenSeleccionada() {
		return ordenSeleccionada;
	}

	public void setOrdenSeleccionada(boolean ordenSeleccionada) {
		this.ordenSeleccionada = ordenSeleccionada;
	}

	public List<LabOrdenDetalle> getListaDetalleOrden() {
		return listaDetalleOrden;
	}

	public void setListaDetalleOrden(List<LabOrdenDetalle> listaDetalleOrden) {
		this.listaDetalleOrden = listaDetalleOrden;
	}

	/**
	 * @return the descripcionAntecedente
	 */
	public String getDescripcionAntecedente() {
		return descripcionAntecedente;
	}

	/**
	 * @param descripcionAntecedente
	 *            the descripcionAntecedente to set
	 */
	public void setDescripcionAntecedente(String descripcionAntecedente) {
		this.descripcionAntecedente = descripcionAntecedente;
	}

	/**
	 * @return the descripcionDiagnostico
	 */
	public String getDescripcionDiagnostico() {
		return descripcionDiagnostico;
	}

	/**
	 * @param descripcionDiagnostico
	 *            the descripcionDiagnostico to set
	 */
	public void setDescripcionDiagnostico(String descripcionDiagnostico) {
		this.descripcionDiagnostico = descripcionDiagnostico;
	}

	/**
	 * @return the medicamento
	 */
	public Medicamento getMedicamento() {
		return medicamento;
	}

	/**
	 * @param medicamento
	 *            the medicamento to set
	 */
	public void setMedicamento(Medicamento medicamento) {
		this.medicamento = medicamento;
	}

	/**
	 * @return the consulta
	 */
	public Consulta getConsulta() {
		return consulta;
	}

	/**
	 * @param consulta
	 *            the consulta to set
	 */
	public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
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

	public void setListaExamenDualSource(List<LabExamen> listaExamenDualSource) {
		this.listaExamenDualSource = listaExamenDualSource;
	}

	public void setListaExamenDualTarget(List<LabExamen> listaExamenDualTarget) {
		this.listaExamenDualTarget = listaExamenDualTarget;
	}

	public void setListaExamenModel(DualListModel<LabExamen> listaExamenModel) {
		this.listaExamenModel = listaExamenModel;
	}

	public List<EDOrdenLaboratorio> getListEdOrdenLaboratorios() {
		return listEdOrdenLaboratorios;
	}

	public void setListEdOrdenLaboratorios(
			List<EDOrdenLaboratorio> listEdOrdenLaboratorios) {
		this.listEdOrdenLaboratorios = listEdOrdenLaboratorios;
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

	/**
	 * @return the listaLabOrden
	 */
	public List<LabOrden> getListaLabOrden() {
		return listaLabOrden;
	}

	/**
	 * @param listaLabOrden
	 *            the listaLabOrden to set
	 */
	public void setListaLabOrden(List<LabOrden> listaLabOrden) {
		this.listaLabOrden = listaLabOrden;
	}

	public Date getFechaOrden() {
		return fechaOrden;
	}

	public void setFechaOrden(Date fechaOrden) {
		this.fechaOrden = fechaOrden;
	}

	public boolean isSeeEstudio() {
		return seeEstudio;
	}

	public void setSeeEstudio(boolean seeEstudio) {
		this.seeEstudio = seeEstudio;
	}

	public String getBanderaEstudio() {
		return banderaEstudio;
	}

	public void setBanderaEstudio(String banderaEstudio) {
		this.banderaEstudio = banderaEstudio;
	}

	public List<LabGrupoExamen> getListGrupoExamens() {
		return listGrupoExamens;
	}

	public void setListGrupoExamens(List<LabGrupoExamen> listGrupoExamens) {
		this.listGrupoExamens = listGrupoExamens;
	}

	public LabGrupoExamen getGrupoExamen() {
		return grupoExamen;
	}

	public void setGrupoExamen(LabGrupoExamen grupoExamen) {
		this.grupoExamen = grupoExamen;
	}

	/**
	 * @return the diagnosticoPresuntivo
	 */
	public DiagnosticoPresuntivo getDiagnosticoPresuntivo() {
		return diagnosticoPresuntivo;
	}

	/**
	 * @param diagnosticoPresuntivo
	 *            the diagnosticoPresuntivo to set
	 */
	public void setDiagnosticoPresuntivo(
			DiagnosticoPresuntivo diagnosticoPresuntivo) {
		this.diagnosticoPresuntivo = diagnosticoPresuntivo;
	}

	/**
	 * @return the diagnosticoPresuntivoSelected
	 */
	public DiagnosticoPresuntivo getDiagnosticoPresuntivoSelected() {
		return diagnosticoPresuntivoSelected;
	}

	/**
	 * @param diagnosticoPresuntivoSelected
	 *            the diagnosticoPresuntivoSelected to set
	 */
	public void setDiagnosticoPresuntivoSelected(
			DiagnosticoPresuntivo diagnosticoPresuntivoSelected) {
		this.diagnosticoPresuntivoSelected = diagnosticoPresuntivoSelected;
	}

	/**
	 * @return the listaDiagnosticosPresuntivos
	 */
	public List<DiagnosticoPresuntivo> getListaDiagnosticosPresuntivos() {
		return listaDiagnosticosPresuntivos;
	}

	/**
	 * @param listaDiagnosticosPresuntivos
	 *            the listaDiagnosticosPresuntivos to set
	 */
	public void setListaDiagnosticosPresuntivos(
			List<DiagnosticoPresuntivo> listaDiagnosticosPresuntivos) {
		this.listaDiagnosticosPresuntivos = listaDiagnosticosPresuntivos;
	}

	public boolean isBanderaDiagnostico() {
		return banderaDiagnostico;
	}

	public void setBanderaDiagnostico(boolean banderaDiagnostico) {
		this.banderaDiagnostico = banderaDiagnostico;
	}

	public Transferencia getTransferenciaToContra() {
		return transferenciaToContra;
	}

	public LabOrdenImag getLabOrden() {
		return labOrden;
	}

	public LabOrdenImag getLabOrdenSelected() {
		return labOrdenSelected;
	}

	public LabGrupoExamenImag getGrupoExamenImag() {
		return grupoExamenImag;
	}

	public List<LabOrdenDetalle> getListOrdenDetalles() {
		return listOrdenDetalles;
	}

	public List<LabOrdenImag> getListaLabOrdenImag() {
		return listaLabOrdenImag;
	}

	public List<LabGrupoExamenImag> getListGrupoExamensImag() {
		return listGrupoExamensImag;
	}

	public List<LabExamenImag> getListExamensImag() {
		return listExamensImag;
	}

	public List<LabOrdenDetalleImag> getListOrdenDetallesImag() {
		return listOrdenDetallesImag;
	}

	public List<EDOrdenImagenologia> getListEdOrdenImagenologias() {
		return listEdOrdenImagenologias;
	}

	public DualListModel<LabExamenImag> getListaExamenImagModel() {
		return listaExamenImagModel;
	}

	public List<LabExamenImag> getListaExamenImagDualTarget() {
		return listaExamenImagDualTarget;
	}

	public String getBusquedaImag() {
		return busquedaImag;
	}

	public void setTransferenciaToContra(Transferencia transferenciaToContra) {
		this.transferenciaToContra = transferenciaToContra;
	}

	public void setLabOrden(LabOrdenImag labOrden) {
		this.labOrden = labOrden;
	}

	public void setLabOrdenSelected(LabOrdenImag labOrdenSelected) {
		this.labOrdenSelected = labOrdenSelected;
	}

	public void setGrupoExamenImag(LabGrupoExamenImag grupoExamenImag) {
		this.grupoExamenImag = grupoExamenImag;
	}

	public void setListOrdenDetalles(List<LabOrdenDetalle> listOrdenDetalles) {
		this.listOrdenDetalles = listOrdenDetalles;
	}

	public void setListaLabOrdenImag(List<LabOrdenImag> listaLabOrdenImag) {
		this.listaLabOrdenImag = listaLabOrdenImag;
	}

	public void setListGrupoExamensImag(
			List<LabGrupoExamenImag> listGrupoExamensImag) {
		this.listGrupoExamensImag = listGrupoExamensImag;
	}

	public void setListExamensImag(List<LabExamenImag> listExamensImag) {
		this.listExamensImag = listExamensImag;
	}

	public void setListOrdenDetallesImag(
			List<LabOrdenDetalleImag> listOrdenDetallesImag) {
		this.listOrdenDetallesImag = listOrdenDetallesImag;
	}

	public void setListEdOrdenImagenologias(
			List<EDOrdenImagenologia> listEdOrdenImagenologias) {
		this.listEdOrdenImagenologias = listEdOrdenImagenologias;
	}

	public void setListaExamenImagModel(
			DualListModel<LabExamenImag> listaExamenImagModel) {
		this.listaExamenImagModel = listaExamenImagModel;
	}

	public void setListaExamenImagDualTarget(
			List<LabExamenImag> listaExamenImagDualTarget) {
		this.listaExamenImagDualTarget = listaExamenImagDualTarget;
	}

	public void setBusquedaImag(String busquedaImag) {
		this.busquedaImag = busquedaImag;
	}

	public List<LabExamenImag> getListaExamenImagDualSource() {
		return listaExamenImagDualSource;
	}

	public void setListaExamenImagDualSource(
			List<LabExamenImag> listaExamenImagDualSource) {
		this.listaExamenImagDualSource = listaExamenImagDualSource;
	}

	public List<LabOrdenImag> getListaOrdenesImag() {
		return listaOrdenesImag;
	}

	public void setListaOrdenesImag(List<LabOrdenImag> listaOrdenesImag) {
		this.listaOrdenesImag = listaOrdenesImag;
	}

	/**
	 * @return the ordenServicio
	 */
	public DesgOrdenServicio getOrdenServicio() {
		return ordenServicio;
	}

	/**
	 * @param ordenServicio
	 *            the ordenServicio to set
	 */
	public void setOrdenServicio(DesgOrdenServicio ordenServicio) {
		this.ordenServicio = ordenServicio;
	}

	/**
	 * @return the ordenesServicio
	 */
	public List<DesgOrdenServicio> getOrdenesServicio() {
		return ordenesServicio;
	}

	/**
	 * @param ordenesServicio
	 *            the ordenesServicio to set
	 */
	public void setOrdenesServicio(List<DesgOrdenServicio> ordenesServicio) {
		this.ordenesServicio = ordenesServicio;
	}

	public EDDesgOrdenServicioHC getVistaOrdenServicioSelected() {
		return vistaOrdenServicioSelected;
	}

	public void setVistaOrdenServicioSelected(
			EDDesgOrdenServicioHC vistaOrdenServicioSelected) {
		this.vistaOrdenServicioSelected = vistaOrdenServicioSelected;
	}

	public List<EDDesgOrdenServicioHC> getListaVistaOrdenServicioSelected() {
		return listaVistaOrdenServicioSelected;
	}

	public void setListaVistaOrdenServicioSelected(
			List<EDDesgOrdenServicioHC> listaVistaOrdenServicioSelected) {
		this.listaVistaOrdenServicioSelected = listaVistaOrdenServicioSelected;
	}

	/**
	 * @return the ordenServicios
	 */
	public List<DesgOrdenServicio> getOrdenServicios() {
		return ordenServicios;
	}

	/**
	 * @param ordenServicios
	 *            the ordenServicios to set
	 */
	public void setOrdenServicios(List<DesgOrdenServicio> ordenServicios) {
		this.ordenServicios = ordenServicios;
	}

	public List<Especialidad> getListaEspecialidad() {
		return listaEspecialidad;
	}

	public void setListaEspecialidad(List<Especialidad> listaEspecialidad) {
		this.listaEspecialidad = listaEspecialidad;
	}

	/**
	 * @return the documentos
	 */
	public List<HCLabOrdenDocumentos> getDocumentos() {
		return documentos;
	}

	/**
	 * @param documentos
	 *            the documentos to set
	 */
	public void setDocumentos(List<HCLabOrdenDocumentos> documentos) {
		HistoriaClinicaDetalleController.documentos = documentos;
	}

	private HCLabOrdenDocumentos getDocumento(String nombreArchivo) {
		for (HCLabOrdenDocumentos hcLabOrdenDocumentos : documentos) {
			if (hcLabOrdenDocumentos.getNombreArchivo().equals(nombreArchivo))
				return hcLabOrdenDocumentos;
		}
		return new HCLabOrdenDocumentos();
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
			System.out.println("nombre de archivo = " + id);
			HCLabOrdenDocumentos documento = getDocumento(id);
			if (documento != null) {
				return new DefaultStreamedContent(new ByteArrayInputStream(
						documento.getArchivo()));
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
}
