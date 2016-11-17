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

import com.teds.spaps.interfaces.dao.ILabOrdenDao;
import com.teds.spaps.interfaces.dao.IParamSistemaIndicesDao;
import com.teds.spaps.interfaces.dao.IPersonalDao;
import com.teds.spaps.model.LabOrden;
import com.teds.spaps.model.LabOrdenDetalle;
import com.teds.spaps.model.LabOrdenSubDetalle;
import com.teds.spaps.model.Paciente;
import com.teds.spaps.model.ParamSistemaIndices;
import com.teds.spaps.model.Personal;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;
import com.teds.spaps.util.Time;

/**
 * @author ANITA
 *
 */
@ManagedBean(name = "labOrdenResultadosController")
@ViewScoped
public class LabOrdenResultadosController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -125210622622704977L;

	private @Inject FacesContext facesContext;

	/**************** REPORTES ************/

	/******* DAO **********/
	private @Inject ILabOrdenDao labOrdenDao;
	private @Inject SessionMain sessionMain;
	private @Inject IParamSistemaIndicesDao sistemaIndicesDao;
	private @Inject IPersonalDao personalDao;

	/******* OBJECT **********/
	private LabOrden labOrden;
	private LabOrden labOrdenSelected;
	private Sucursal sucursal;
	private Paciente selectedPaciente;
	private Personal selectedMedico;
	private LabOrdenDetalle newExamenDetalle;
	private LabOrdenDetalle selectedDetalleImag;
	private Personal medico;

	/******* LIST **********/
	private List<LabOrden> listaLabOrden;
	private List<LabOrdenDetalle> listOrdenDetalles = new ArrayList<LabOrdenDetalle>();

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;
	private boolean seleccionado = false;

	// BUSQUEDAS
	private String busqueda;
	
	private Date fechaInicio= new Date();
	private Date fechaFin= new Date(); 

	// URL PAGE
	private String currentPage = "/pages/laboratorio/resultados/list.xhtml";

	private Date fecha = new Date();

	/**
	 * 
	 */
	public LabOrdenResultadosController() {
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

	public void consultar() {
		listaLabOrden= labOrdenDao.obtenerProFechasYEstado(sucursal, fechaInicio, fechaFin, "PR");
	}

	private void defaultValues() {
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
		seleccionado = false;

		labOrden = new LabOrden();
		labOrdenSelected = new LabOrden();
		listOrdenDetalles.clear();
		if (medico != null) {
			listaLabOrden = labOrdenDao.obtenerPorSucursal("PR", medico,
					sucursal);
		} else {
			listaLabOrden = labOrdenDao
					.obtenerPorCompaniaOrdenadoFechaRegistroRow50(sucursal
							.getCompania());
		}

		currentPage = "/pages/laboratorio/resultados/list.xhtml";
	}

	public void imprimirresultado(LabOrdenSubDetalle detalle) {
		System.out.println("Resultado : " + detalle.getResultado());
	}

	public void actualizar() {
		try {
			System.out.println("Ingreso a actualizar");

			labOrden.setFechaModificacion(new Date());
			LabOrden r = labOrdenDao.registrarResultados(labOrden,
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
			// configuracionFtp = configuracionFtpDao
			// .obtenerConfiguracionFtpActivo(sucursal.getCompania(), "AC");
			// newExamenDetalle.setPathImagen(FtpUtil.enviarArchivos(
			// configuracionFtp.getUsuario(), configuracionFtp.getPassword(),
			// configuracionFtp.getIp(), uploadBean.getFile()));
			LabOrden r = labOrdenDao
					.registrarResultadosDetalle(newExamenDetalle);
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

		currentPage = "/pages/laboratorio/resultados/edit.xhtml";
	}

	public void onRowSelectDetalle(SelectEvent event) {
		newExamenDetalle = selectedDetalleImag;

		currentPage = "/pages/laboratorio/resultados/edit_examen.xhtml";
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

		ParamSistemaIndices sistemaIndices = sistemaIndicesDao
				.obtenerPorCompania("OL", sucursal.getCompania());
		labOrden.setCodigo(sistemaIndices.getPrefijo()
				+ Time.rellendarCeros(sistemaIndices.getNumeroDigitos(),
						sistemaIndices.getCorrelativo()));

		currentPage = "/pages/laboratorio/resultados/edit.xhtml";
	}

	public void actionCancelar() {
		crear = true;
		seleccionado = false;
		registrar = false;
		labOrden = new LabOrden();
		currentPage = "/pages/laboratorio/resultados/list.xhtml";
	}

	public void resetearFitrosTabla(String id) {
		DataTable table = (DataTable) FacesContext.getCurrentInstance()
				.getViewRoot().findComponent(id);
		table.setSelection(null);
		table.reset();
	}

	public void actionMdificarDetalle(LabOrdenDetalle labOrdenDetalle1) {

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
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ID_ORDEN", labOrden.getId());
			map.put("LOGO", URL_SERVLET_LOGO);
			map.put("SUBREPORT_DIR", urlPath+"resources/report/inventario/");
			map.put("REPORT_LOCALE", new Locale("en", "US"));
			String reportPath = urlPath+"resources/report/laboratorio/reporteOrdenExamenResultados.jasper";
			HttpServletRequest request1 = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();       
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
	 * @return the newExamenDetalle
	 */
	public LabOrdenDetalle getNewExamenDetalle() {
		return newExamenDetalle;
	}

	/**
	 * @param newExamenDetalle
	 *            the newExamenDetalle to set
	 */
	public void setNewExamenDetalle(LabOrdenDetalle newExamenDetalle) {
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
	public LabOrdenDetalle getSelectedDetalleImag() {
		return selectedDetalleImag;
	}

	/**
	 * @param selectedDetalleImag
	 *            the selectedDetalleImag to set
	 */
	public void setSelectedDetalleImag(LabOrdenDetalle selectedDetalleImag) {
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
	 * @param fechaInicio the fechaInicio to set
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
	 * @param fechaFin the fechaFin to set
	 */
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

}
