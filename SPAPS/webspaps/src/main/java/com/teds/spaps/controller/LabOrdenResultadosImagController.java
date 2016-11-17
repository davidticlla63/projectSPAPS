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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.SelectEvent;

import com.teds.spaps.interfaces.dao.IConfiguracionFtpDao;
import com.teds.spaps.interfaces.dao.ILabOrdenImagDao;
import com.teds.spaps.interfaces.dao.IParamSistemaIndicesDao;
import com.teds.spaps.interfaces.dao.IPersonalDao;
import com.teds.spaps.model.ConfiguracionFtp;
import com.teds.spaps.model.LabOrdenDetalleImag;
import com.teds.spaps.model.LabOrdenDocumentos;
import com.teds.spaps.model.LabOrdenImag;
import com.teds.spaps.model.Paciente;
import com.teds.spaps.model.ParamSistemaIndices;
import com.teds.spaps.model.Personal;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.structore.EDFiles;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.FileUploadBean;
import com.teds.spaps.util.FtpUtil;
import com.teds.spaps.util.SessionMain;
import com.teds.spaps.util.Time;

/**
 * @author ANITA
 *
 */
@ManagedBean(name = "labOrdenResultadosImagController")
@ViewScoped
public class LabOrdenResultadosImagController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7980998416050874064L;

	private @Inject FacesContext facesContext;

	private @Inject FileUploadBean uploadBean;

	/**************** REPORTES ************/

	/******* DAO **********/
	private @Inject ILabOrdenImagDao labOrdenDao;
	private @Inject SessionMain sessionMain;
	private @Inject IParamSistemaIndicesDao sistemaIndicesDao;
	private @Inject IPersonalDao personalDao;
	private @Inject IConfiguracionFtpDao configuracionFtpDao;// FTP DAO

	/******* OBJECT **********/
	private LabOrdenImag labOrden;
	private LabOrdenImag labOrdenSelected;
	private Sucursal sucursal;
	private Paciente selectedPaciente;
	private Personal selectedMedico;
	private LabOrdenDetalleImag newExamenDetalle;
	private LabOrdenDetalleImag selectedDetalleImag;
	private Personal medico;
	private ConfiguracionFtp configuracionFtp;
	/******* LIST **********/
	private List<LabOrdenImag> listaLabOrden;
	private List<LabOrdenDetalleImag> listOrdenDetalles = new ArrayList<LabOrdenDetalleImag>();

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;
	private boolean seleccionado = false;

	// BUSQUEDAS
	private String busqueda;

	// URL PAGE
	private String currentPage = "/pages/laboratorio/resultados_imagenologia/list.xhtml";

	private Date fecha = new Date();

	private Date fechaInicio = new Date();
	private Date fechaFin = new Date();

	/**
	 * 
	 */
	public LabOrdenResultadosImagController() {
	}

	@PostConstruct
	public void initNew() {
		sucursal = sessionMain.PruebaSucursal();
		System.out.println("Sucursal : " + sucursal.getDescripcion());
		System.out.println("Compañia : "
				+ sucursal.getCompania().getDescripcion());
		List<Personal> listPersonals = new ArrayList<Personal>();
		listPersonals = personalDao.obtenerPorUsuario(sessionMain
				.getUsuarioLogin());
		if (listPersonals.size() == 1) {
			medico = listPersonals.get(0);
		}
		defaultValues();
	}

	private void defaultValues() {
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
		seleccionado = false;

		labOrden = new LabOrdenImag();
		labOrdenSelected = new LabOrdenImag();
		listOrdenDetalles.clear();
		if (medico != null) {
			listaLabOrden = labOrdenDao.obtenerPorSucursal("PR", medico,
					sucursal);
		} else {
			listaLabOrden = labOrdenDao
					.obtenerPorCompaniaOrdenadoFechaRegistroRow50(sucursal
							.getCompania());
		}
		currentPage = "/pages/laboratorio/resultados_imagenologia/list.xhtml";
	}

	public void consultar() {
		listaLabOrden = labOrdenDao.obtenerProFechasYEstado(sucursal,
				fechaInicio, fechaFin, "PR");
	}

	public void actualizar() {
		try {
			System.out.println("Ingreso a actualizar");

			labOrden.setFechaModificacion(new Date());
			configuracionFtp = configuracionFtpDao
					.obtenerConfiguracionFtpActivo(sucursal.getCompania(), "AC");
			newExamenDetalle.getListOrdenDocumentos().clear();
			for (EDFiles files : uploadBean.listArchivos) {
//				if (files.isEnviado()) {
//					break;
//				}
				LabOrdenDocumentos documentos = new LabOrdenDocumentos("",
						newExamenDetalle, labOrden, new Date(), sessionMain
								.getUsuarioLogin().getLogin());
				if (files.isEnviado()) {
					documentos.setId(files.getId());
					documentos.setEstado("AC");
					documentos.setComentario(files.getComentario());
					documentos.setPathImagen(files.getPathImage());
					documentos.setFechaRegistro(files.getFecha());
				}else{
					documentos.setFile(files.getFile());
					documentos.setEstado("AC");
					documentos.setComentario(files.getComentario());
					documentos.setPathImagen(FtpUtil.enviarArchivos(
							configuracionFtp.getUsuario(),
							configuracionFtp.getPassword(),
							configuracionFtp.getIp(), files.getFile()));
				}

				newExamenDetalle.getListOrdenDocumentos().add(documentos);
			}
			// if (uploadBean.getFile() != null) {
			// newExamenDetalle.setPathImagen(FtpUtil.enviarArchivos(
			// configuracionFtp.getUsuario(),
			// configuracionFtp.getPassword(),
			// configuracionFtp.getIp(), uploadBean.getFile()));
			// }
			LabOrdenImag r = labOrdenDao.registrarResultados(labOrden,
					listOrdenDetalles);
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

	public void actualizarDetalle() {
		try {
			System.out.println("Ingreso a actualizar");

			labOrden.setFechaModificacion(new Date());
			configuracionFtp = configuracionFtpDao
					.obtenerConfiguracionFtpActivo(sucursal.getCompania(), "AC");
			newExamenDetalle.getListOrdenDocumentos().clear();
			for (EDFiles files : uploadBean.listArchivos) {
				
				LabOrdenDocumentos documentos = new LabOrdenDocumentos("",
						newExamenDetalle, labOrden, new Date(), sessionMain
								.getUsuarioLogin().getLogin());
				
				if (files.isEnviado()) {
					documentos.setId(files.getId());
					documentos.setEstado("AC");
					documentos.setComentario(files.getComentario());
					documentos.setPathImagen(files.getPathImage());
					documentos.setFechaRegistro(files.getFecha());
				}else{
					documentos.setFile(files.getFile());
					documentos.setEstado("AC");
					documentos.setComentario(files.getComentario());
					documentos.setPathImagen(FtpUtil.enviarArchivos(
							configuracionFtp.getUsuario(),
							configuracionFtp.getPassword(),
							configuracionFtp.getIp(), files.getFile()));
				}

				newExamenDetalle.getListOrdenDocumentos().add(documentos);
			}
			// if (uploadBean.getFile() != null) {
			// newExamenDetalle.setPathImagen(FtpUtil.enviarArchivos(
			// configuracionFtp.getUsuario(),
			// configuracionFtp.getPassword(),
			// configuracionFtp.getIp(), uploadBean.getFile()));
			// }
			LabOrdenImag r = labOrdenDao
					.registrarResultadosDetalle(newExamenDetalle);
			if (r != null) {
				defaultValues();
				uploadBean.listArchivos.clear();
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

	public void imprimir() {
		try {
			armarUrl();
		} catch (Exception e) {
			System.out.println("Error en eliminacion de labOrden: "
					+ e.getMessage());
		}
	}

	public void onRowSelect(SelectEvent event) {
		labOrden = labOrdenSelected;
		crear = false;
		seleccionado = true;
		registrar = false;
		if (labOrden.getTipoOrden().equals("INTERNO")) {
			selectedMedico = labOrden.getMedico();
		}
		selectedPaciente = labOrden.getPaciente();
		listOrdenDetalles = labOrden.getListOrdenDetalle();

		currentPage = "/pages/laboratorio/resultados_imagenologia/edit.xhtml";
	}

	public void onRowSelectDetalle(SelectEvent event) {

		newExamenDetalle = selectedDetalleImag;
		labOrden = newExamenDetalle.getOrden();
		if (labOrden.getTipoOrden().equals("INTERNO")) {
			selectedMedico = labOrden.getMedico();
		}
		selectedPaciente = labOrden.getPaciente();
		listOrdenDetalles = labOrden.getListOrdenDetalle();
		crear = false;
		seleccionado = true;
		registrar = false;
		uploadBean.listArchivos = new ArrayList<EDFiles>();
		configuracionFtp = configuracionFtpDao.obtenerConfiguracionFtpActivo(
				sucursal.getCompania(), "AC");
		for (LabOrdenDocumentos documentos : newExamenDetalle
				.getListOrdenDocumentos()) {
			EDFiles documento = new EDFiles(null, "", true);
			documento.setId(documentos.getId());
			documento.setEnviado(true);
			documento.setFecha(documentos.getFechaRegistro());
			documento.setNombre(documentos.getPathImagen());
			documento.setComentario(documentos.getComentario());
			documento.setPath("ftp://" + configuracionFtp.getUsuario() + ":"
					+ configuracionFtp.getPassword() + "@"
					+ configuracionFtp.getIp() + "/"
					+ documentos.getPathImagen());
			documento.setPathImage(documentos.getPathImagen());
			uploadBean.listArchivos.add(documento);
		}
		currentPage = "/pages/laboratorio/resultados_imagenologia/edit_examen.xhtml";
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

		ParamSistemaIndices sistemaIndices = sistemaIndicesDao
				.obtenerPorCompania("OL", sucursal.getCompania());
		labOrden.setCodigo(sistemaIndices.getPrefijo()
				+ Time.rellendarCeros(sistemaIndices.getNumeroDigitos(),
						sistemaIndices.getCorrelativo()));

		currentPage = "/pages/laboratorio/resultados_imagenologia/edit.xhtml";
	}

	public void actionCancelar() {
		crear = true;
		seleccionado = false;
		registrar = false;
		labOrden = new LabOrdenImag();
		currentPage = "/pages/laboratorio/resultados_imagenologia/list.xhtml";
	}

	public void resetearFitrosTabla(String id) {
		DataTable table = (DataTable) FacesContext.getCurrentInstance()
				.getViewRoot().findComponent(id);
		table.setSelection(null);
		table.reset();
	}

	public void actionMdificarDetalle(LabOrdenDetalleImag labOrdenDetalle1) {

		newExamenDetalle = labOrdenDetalle1;
		FacesUtil.updateComponent("formDlg001");
		FacesUtil.showDialog("dlgDialog");
	}

	// reporte

	private void armarUrl() {
		try {
			HttpServletRequest request = (HttpServletRequest) facesContext
					.getExternalContext().getRequest();
			String urlPath = request.getRequestURL().toString();
			urlPath = urlPath.substring(0, urlPath.length()
					- request.getRequestURI().length())
					+ request.getContextPath() + "/";

			String URL_SERVLET_LOGO = urlPath + "ServletImageLogo?id="
					+ labOrden.getCompania().getId() + "&type=EMPRESA";
			configuracionFtp = configuracionFtpDao.obtenerConfiguracionFtpActivo(
					sucursal.getCompania(), "AC");
			String URL="ftp://" + configuracionFtp.getUsuario() + ":"
					+ configuracionFtp.getPassword() + "@"
					+ configuracionFtp.getIp() + "/";//FTP DIRECCION
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ID_ORDEN", labOrden.getId());
			map.put("LOGO", URL_SERVLET_LOGO);
			map.put("URL", URL);
			map.put("SUBREPORT_DIR", urlPath + "resources/report/laboratorio/");
			map.put("REPORT_LOCALE", new Locale("en", "US"));
			String reportPath = urlPath
					+ "resources/report/laboratorio/reporteOrdenExamenResultadosImag.jasper";
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
	 * @return the newExamenDetalle
	 */
	public LabOrdenDetalleImag getNewExamenDetalle() {
		return newExamenDetalle;
	}

	/**
	 * @param newExamenDetalle
	 *            the newExamenDetalle to set
	 */
	public void setNewExamenDetalle(LabOrdenDetalleImag newExamenDetalle) {
		this.newExamenDetalle = newExamenDetalle;
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
	 * @return the medico
	 */
	public Personal getMedico() {
		return medico;
	}

	/**
	 * @param medico
	 *            the medico to set
	 */
	public void setMedico(Personal medico) {
		this.medico = medico;
	}

	/**
	 * @return the selectedDetalleImag
	 */
	public LabOrdenDetalleImag getSelectedDetalleImag() {
		return selectedDetalleImag;
	}

	/**
	 * @param selectedDetalleImag
	 *            the selectedDetalleImag to set
	 */
	public void setSelectedDetalleImag(LabOrdenDetalleImag selectedDetalleImag) {
		this.selectedDetalleImag = selectedDetalleImag;
	}

	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha
	 *            the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
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
