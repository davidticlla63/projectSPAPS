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
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.SelectEvent;

import com.teds.spaps.interfaces.dao.IDesgOrdenServicioDao;
import com.teds.spaps.interfaces.dao.IDesgOrdenServicioImagExamenDao;
import com.teds.spaps.interfaces.dao.IDesgOrdenServicioLabExamenDao;
import com.teds.spaps.interfaces.dao.ILabOrdenDao;
import com.teds.spaps.interfaces.dao.ILabOrdenImagDao;
import com.teds.spaps.interfaces.dao.IMensajeDao;
import com.teds.spaps.interfaces.dao.INombreTareaDao;
import com.teds.spaps.interfaces.dao.IPersonalDao;
import com.teds.spaps.interfaces.dao.ITareaDao;
import com.teds.spaps.interfaces.dao.ITipoTareaDao;
import com.teds.spaps.model.DesgOrdenServicio;
import com.teds.spaps.model.DesgOrdenServicioImagExamen;
import com.teds.spaps.model.DesgOrdenServicioLabExamen;
import com.teds.spaps.model.LabOrden;
import com.teds.spaps.model.LabOrdenDetalle;
import com.teds.spaps.model.LabOrdenDetalleImag;
import com.teds.spaps.model.LabOrdenImag;
import com.teds.spaps.model.Mensaje;
import com.teds.spaps.model.NombreTarea;
import com.teds.spaps.model.Personal;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.Tarea;
import com.teds.spaps.model.TipoTarea;
import com.teds.spaps.model.Usuario;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

/**
 * @author ANITA
 *
 */
@ManagedBean(name = "desgOrdenServicioAprobacionController")
@ViewScoped
public class DesgOrdenServicioAprobacionController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3190666074292467999L;
	/******* DAO **********/
	private @Inject IDesgOrdenServicioDao desgOrdenServicioDao;
	private @Inject SessionMain sessionMain;
	private @Inject ITipoTareaDao tipoTareaDao;
	private @Inject ITareaDao tareaDao;
	private @Inject ILabOrdenDao labOrdenDao;
	private @Inject ILabOrdenImagDao labOrdenImagDao;
	private @Inject IDesgOrdenServicioLabExamenDao desgOrdenServicioLabExamenDao;
	private @Inject IDesgOrdenServicioImagExamenDao desgOrdenServicioImagExamenDao;
	private @Inject INombreTareaDao nombreTareaDao;
	private @Inject IMensajeDao mensajeDao;
	private @Inject IPersonalDao personalDao;
	// private @Inject IDesgOrdenServiciosDao desgOrdenServiciosDao;

	/******* OBJECT **********/
	private DesgOrdenServicio desgOrdenServicio;
	private DesgOrdenServicio desgOrdenServicioSelected;
	private Sucursal sucursal;
	private Usuario usuario;
	private Tarea tarea;
	private Mensaje mensaje;

	/******* LIST **********/
	private List<DesgOrdenServicio> listaDesgOrdenServicio;
	private List<DesgOrdenServicioLabExamen> labExamenes;
	private List<DesgOrdenServicioImagExamen> imgExamenes;
	public static List<NombreTarea> nombreTareas;
	public static List<TipoTarea> tipoTareas;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;
	private boolean ordenSeleccionada = false;
	private boolean seeEstudio = false;
	private boolean resetTablesS = true;
	private String tipoBusqueda = "";
	private String pagina = "";
	private String currentPage1 = "/pages/desgravamen/desgravamen_laboratorios.xhtml";
	private String busqueda;

	@Inject
	Conversation conversation;

	@PostConstruct
	public void initNew() {
		desgOrdenServicio = new DesgOrdenServicio();
		desgOrdenServicioSelected = new DesgOrdenServicio();
		usuario = sessionMain.getUsuarioLogin();
		sucursal = sessionMain.PruebaSucursal();
		tarea = new Tarea();
		tarea.setTipoTarea(tipoTareaDao.obtenerTipoTarea(1));
		listaDesgOrdenServicio = desgOrdenServicioDao.obtenerAll();
		labExamenes = new ArrayList<DesgOrdenServicioLabExamen>();
		imgExamenes = new ArrayList<DesgOrdenServicioImagExamen>();
		modificar = false;
		registrar = false;
		crear = true;
		ordenSeleccionada = false;
		seeEstudio = false;
		resetTablesS = true;
		tipoBusqueda = "";
		mensaje = new Mensaje();
	}

	public List<TipoTarea> onCompleteTipoTarea(String query) {
		String upperQuery = query.toUpperCase().trim();
		tipoTareas = tipoTareaDao.obtenerPorSucursalAutoComplete(upperQuery,
				sucursal);
		return tipoTareas;
	}

	public void onSelectTipoTareaClick(SelectEvent event) {
		tarea.setTipoTarea((TipoTarea) event.getObject());
	}

	public List<NombreTarea> onCompleteNombreTarea(String query) {
		String upperQuery = query.toUpperCase().trim();
		nombreTareas = nombreTareaDao.obtenerPorSucursalAutoComplete(
				upperQuery, sucursal);
		return nombreTareas;
	}

	public void onSelectNombreTareaClick(SelectEvent event) {
		tarea.setNombreTarea((NombreTarea) event.getObject());
		System.out.println("tipo de tarea seleccionada = "
				+ tarea.getTipoTarea().getNombre());
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
		if (desgOrdenServicio.getEstado().equals("PE")) {
			FacesUtil.updateComponent("formDlg001");
			FacesUtil.showDialog("dlgTarea");
		} else
			initNew();
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

	public LabOrden generarOrdenLaboratorio() {
		LabOrden orden = new LabOrden();
		orden.setCompania(desgOrdenServicio.getCompania());
		orden.setDescripcion("");
		orden.setEspecialidad("");
		orden.setEstado("AC");
		orden.setFecha(desgOrdenServicio.getFechaRegistro());
		orden.setFechaRegistro(desgOrdenServicio.getFechaRegistro());
		orden.setFechaModificacion(desgOrdenServicio.getFechaModificacion());
		orden.setMedico(desgOrdenServicio.getMedicoAuditor());
		orden.setHistoriaClinica(null);
		orden.setNombreMedico("");
		orden.setPaciente(desgOrdenServicio.getCliente());
		orden.setSucursal(desgOrdenServicio.getSucursal());
		orden.setUsuarioRegistro(desgOrdenServicio.getUsuarioRegistro());
		List<LabOrdenDetalle> detalles = new ArrayList<LabOrdenDetalle>();
		List<DesgOrdenServicioLabExamen> ordenServicioLabExamens = desgOrdenServicioLabExamenDao
				.obtenerPorOrdenHC(desgOrdenServicio);
		for (DesgOrdenServicioLabExamen ordenServicioLabExamen : ordenServicioLabExamens) {
			LabOrdenDetalle ordenDetalle = new LabOrdenDetalle();
			ordenDetalle.setCompania(desgOrdenServicio.getCompania());
			ordenDetalle.setDescripcion(ordenServicioLabExamen.getExamen()
					.getDescripcion());
			ordenDetalle.setEstado("AC");
			ordenDetalle.setFechaModificacion(desgOrdenServicio
					.getFechaModificacion());
			ordenDetalle.setFechaRegistro(desgOrdenServicio.getFechaRegistro());
			ordenDetalle.setLabExamen(ordenServicioLabExamen.getExamen());
			ordenDetalle.setParametros("");
			ordenDetalle.setSucursal(desgOrdenServicio.getSucursal());
			ordenDetalle.setUsuarioRegistro(desgOrdenServicio
					.getUsuarioRegistro());
			detalles.add(ordenDetalle);
		}
		orden = labOrdenDao.registrar(orden, detalles);
		return orden;
	}

	public LabOrdenImag generarOrdenImageneologia() {
		LabOrdenImag orden = new LabOrdenImag();
		orden.setCompania(desgOrdenServicio.getCompania());
		orden.setDescripcion("");
		orden.setEspecialidad("");
		orden.setEstado("AC");
		orden.setFecha(desgOrdenServicio.getFechaRegistro());
		orden.setFechaRegistro(desgOrdenServicio.getFechaRegistro());
		orden.setFechaModificacion(desgOrdenServicio.getFechaModificacion());
		orden.setMedico(desgOrdenServicio.getMedicoAuditor());
		orden.setHistoriaClinica(null);
		orden.setNombreMedico("");
		orden.setPaciente(desgOrdenServicio.getCliente());
		orden.setSucursal(desgOrdenServicio.getSucursal());
		orden.setUsuarioRegistro(desgOrdenServicio.getUsuarioRegistro());
		List<LabOrdenDetalleImag> detalles = new ArrayList<LabOrdenDetalleImag>();
		List<DesgOrdenServicioImagExamen> ordenServicioLabExamens = desgOrdenServicioImagExamenDao
				.obtenerPorOrdenHC(desgOrdenServicio);
		for (DesgOrdenServicioImagExamen ordenServicioLabExamen : ordenServicioLabExamens) {
			LabOrdenDetalleImag ordenDetalle = new LabOrdenDetalleImag();
			ordenDetalle.setCompania(desgOrdenServicio.getCompania());
			ordenDetalle.setDescripcion(ordenServicioLabExamen.getExamen()
					.getDescripcion());
			ordenDetalle.setEstado("AC");
			ordenDetalle.setFechaModificacion(desgOrdenServicio
					.getFechaModificacion());
			ordenDetalle.setFechaRegistro(desgOrdenServicio.getFechaRegistro());
			ordenDetalle.setLabExamen(ordenServicioLabExamen.getExamen());
			ordenDetalle.setParametros("");
			ordenDetalle.setSucursal(desgOrdenServicio.getSucursal());
			ordenDetalle.setUsuarioRegistro(desgOrdenServicio
					.getUsuarioRegistro());
			detalles.add(ordenDetalle);
		}
		orden = labOrdenImagDao.registrar(orden, detalles);
		return orden;
	}

	public void registrar() {
		try {
			if (getSucursal() == null || getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				if (mensaje.getMensaje().isEmpty()) {
					FacesUtil.errorMessage("No puede enviar un mensaje vacio.");
					return;
				}
				desgOrdenServicio.setEstado("AE");
				desgOrdenServicio = desgOrdenServicioDao
						.modificar(desgOrdenServicio);
				if (desgOrdenServicio != null) {
					LabOrden ordenLab = generarOrdenLaboratorio();
					LabOrdenImag ordenImag = generarOrdenImageneologia();
					desgOrdenServicio.setOrdenLab(ordenLab);
					desgOrdenServicio.setOrdenImag(ordenImag);
					desgOrdenServicio = desgOrdenServicioDao
							.modificar(desgOrdenServicio);
					tarea.setCompania(sucursal.getCompania());
					tarea.setEstado("AC");
					tarea.setFechaRegistro(new Date());
					tarea.setFechaModificacion(tarea.getFechaRegistro());
					tarea.setOrdenServicio(desgOrdenServicio);
					tarea.setPersonal(desgOrdenServicio.getMedicoAuditor());
					tarea.setSucursal(sucursal);
					tarea.setUsuarioRegistro(usuario.getLogin());
					tarea = tareaDao.registrar(tarea);
					mensaje.setCompania(tarea.getCompania());
					Personal personal = personalDao.obtenerPorUsuario(usuario,
							sucursal);
					mensaje.setEmisor(personal);
					mensaje.setEstado("AC");
					mensaje.setFechaRegistro(new Date());
					mensaje.setFechaModificacion(mensaje.getFechaRegistro());
					mensaje.setReceptor(desgOrdenServicio.getMedicoAuditor());
					mensaje.setSucursal(sucursal);
					mensaje.setTarea(tarea);
					mensaje.setUsuarioRegistro(usuario.getLogin());
					mensaje = mensajeDao.registrar(mensaje);
					if (mensaje != null) {
						FacesUtil.infoMessage(
								"Aprobacion de Orden de Servicio",
								"Orden de Servicio aprobada");
					}
				}
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
				initNew();
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de desgOrdenServicio: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {

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
	public DesgOrdenServicioAprobacionController() {
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

	public String getBusqueda() {
		return busqueda;
	}

	public void setBusqueda(String busqueda) {
		this.busqueda = busqueda;
	}

	public boolean isResetTablesS() {
		return resetTablesS;
	}

	public void setResetTablesS(boolean resetTablesS) {
		this.resetTablesS = resetTablesS;
	}

	public Tarea getTarea() {
		return tarea;
	}

	public void setTarea(Tarea tarea) {
		this.tarea = tarea;
	}

	public List<DesgOrdenServicioLabExamen> getLabExamenes() {
		return labExamenes;
	}

	public List<DesgOrdenServicioImagExamen> getImgExamenes() {
		return imgExamenes;
	}

	public void setLabExamenes(List<DesgOrdenServicioLabExamen> labExamenes) {
		this.labExamenes = labExamenes;
	}

	public void setImgExamenes(List<DesgOrdenServicioImagExamen> imgExamenes) {
		this.imgExamenes = imgExamenes;
	}

	/**
	 * @return the nombreTareas
	 */
	public static List<NombreTarea> getNombreTareas() {
		return nombreTareas;
	}

	/**
	 * @param nombreTareas
	 *            the nombreTareas to set
	 */
	public static void setNombreTareas(List<NombreTarea> nombreTareas) {
		DesgOrdenServicioAprobacionController.nombreTareas = nombreTareas;
	}

	/**
	 * @return the tipoTareas
	 */
	public static List<TipoTarea> getTipoTareas() {
		return tipoTareas;
	}

	/**
	 * @param tipoTareas
	 *            the tipoTareas to set
	 */
	public static void setTipoTareas(List<TipoTarea> tipoTareas) {
		DesgOrdenServicioAprobacionController.tipoTareas = tipoTareas;
	}

	/**
	 * @return the mensaje
	 */
	public Mensaje getMensaje() {
		return mensaje;
	}

	/**
	 * @param mensaje
	 *            the mensaje to set
	 */
	public void setMensaje(Mensaje mensaje) {
		this.mensaje = mensaje;
	}

}
