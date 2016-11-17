/**
 * 
 */
package com.teds.spaps.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.SelectEvent;

import com.teds.spaps.interfaces.dao.INombreTareaDao;
import com.teds.spaps.model.NombreTarea;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.Usuario;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

@ManagedBean(name = "nombreTareaController")
@ViewScoped
public class NombreTareaController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5213639818616175909L;
	/******* DAO **********/
	private @Inject INombreTareaDao nombreTareaDao;
	private @Inject SessionMain sessionMain;
	@Inject
	private Logger log;

	/******* OBJECT **********/
	private NombreTarea nombreTarea;
	private NombreTarea nombreTareaSelected;
	private Sucursal sucursalLogin;
	private Usuario usuarioLogin;

	/******* LIST **********/
	private List<NombreTarea> listaNombreTarea;
	private String[] listaEstado = { "ACTIVO", "INACTIVO", "ELIMINADO" };

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;
	private String estado = "AC";

	// columnas
	private String tipoColumnRegistro = "col-md-4"; // 4
	private String tipoColumnTable = "col-md-12"; // 8

	@Inject
	Conversation conversation;

	/**
	 * 
	 */
	public NombreTareaController() {
	}

	public NombreTarea getNombreTarea() {
		return nombreTarea;
	}

	public void setNombreTarea(NombreTarea nombreTarea) {
		this.nombreTarea = nombreTarea;
	}

	public NombreTarea getNombreTareaSelected() {
		return nombreTareaSelected;
	}

	public void setNombreTareaSelected(NombreTarea nombreTareaSelected) {
		this.nombreTareaSelected = nombreTareaSelected;
	}

	public List<NombreTarea> getListaNombreTarea() {
		return listaNombreTarea;
	}

	public void setListaNombreTarea(List<NombreTarea> listaNombreTarea) {
		this.listaNombreTarea = listaNombreTarea;
	}

	public boolean isModificar() {
		return modificar;
	}

	public void setModificar(boolean modificar) {
		this.modificar = modificar;
	}

	public boolean isRegistrar() {
		return registrar;
	}

	public void setRegistrar(boolean registrar) {
		this.registrar = registrar;
	}

	public boolean isCrear() {
		return crear;
	}

	public void setCrear(boolean crear) {
		this.crear = crear;
	}

	public Sucursal getSucursalLogin() {
		return sucursalLogin;
	}

	public void setSucursalLogin(Sucursal sucursalLogin) {
		this.sucursalLogin = sucursalLogin;
	}

	public Usuario getUsuarioLogin() {
		return usuarioLogin;
	}

	public void setUsuarioLogin(Usuario usuarioLogin) {
		this.usuarioLogin = usuarioLogin;
	}

	public String[] getListaEstado() {
		return listaEstado;
	}

	public void setListaEstado(String[] listaEstado) {
		this.listaEstado = listaEstado;
	}

	public String getTipoColumnRegistro() {
		return tipoColumnRegistro;
	}

	public String getTipoColumnTable() {
		return tipoColumnTable;
	}

	public void setTipoColumnRegistro(String tipoColumnRegistro) {
		this.tipoColumnRegistro = tipoColumnRegistro;
	}

	public void setTipoColumnTable(String tipoColumnTable) {
		this.tipoColumnTable = tipoColumnTable;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@PostConstruct
	public void initNew() {
		initConversation();
		nombreTarea = new NombreTarea();
		nombreTareaSelected = new NombreTarea();
		usuarioLogin = sessionMain.getUsuarioLogin();
		sucursalLogin = sessionMain.PruebaSucursal();
		listaNombreTarea = nombreTareaDao.obtenerPorSucursal(sucursalLogin);
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
	}

	public void actionNuevo() {
		crear = false;
		modificar = false;
		registrar = true;
	}

	public void Init() {
		initConversation();
		nombreTarea = new NombreTarea();
		nombreTareaSelected = new NombreTarea();
		listaNombreTarea = nombreTareaDao.obtenerNombreTareaOrdenAscPorId();
		// sucursalLogin = sessionMain.getSucursalLogin();
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
	}

	public void registrar() {
		if (nombreTareaDao.verificarRepetido(nombreTarea,
				sucursalLogin.getCompania())) {
			FacesUtil.infoMessage("VALIDACIÓN", "Ya existe el registro.");
		} else {
			try {
				log.info("entro a registrar");
				System.out.println("entro a registrar");
				if (nombreTarea.getNombre().trim().isEmpty()
						|| getSucursalLogin() == null
						|| getSucursalLogin().getCompania() == null) {
					FacesUtil.infoMessage("VALIDACION",
							"No puede haber campos vacíos");
					return;
				} else {
					nombreTarea.setSucursal(getSucursalLogin());
					nombreTarea.setCompania(getSucursalLogin().getCompania());
					nombreTarea
							.setUsuarioRegistro(getUsuarioLogin().getLogin());
					nombreTarea.setFechaRegistro(new Date());
					nombreTarea.setFechaModificacion(nombreTarea
							.getFechaRegistro());
					nombreTarea.setEstado("AC");
					nombreTareaDao.registrar(nombreTarea);
					initNew();
				}
			} catch (Exception e) {
				System.out.println("Error en registro de nombreTarea: "
						+ e.getMessage());
			}
		}
	}

	public void cambiarAspecto() {
		crear = false;
		registrar = true;
		modificar = false;
	}

	public void actualizar() {
		if (nombreTareaDao.verificarRepetidoUpdate(nombreTarea,
				sucursalLogin.getCompania())) {
			FacesUtil.infoMessage("VALIDACIÓN", "Ya existe el registro.");
		} else {
			try {
				if (nombreTarea.getNombre().trim().isEmpty()
						|| nombreTarea.getEstado().trim().isEmpty()) {
					FacesUtil.infoMessage("VALIDACION",
							"No puede haber campos vacíos");
					return;
				} else {
					nombreTarea.setFechaModificacion(new Date());
					nombreTarea
							.setUsuarioRegistro(getUsuarioLogin().getLogin());
					if (getEstado().equals("ACTIVO")
							|| getEstado().equals("AC")) {
						nombreTarea.setEstado("AC");
					} else {
						if (getEstado().equals("INACTIVO")
								|| getEstado().equals("IN")) {
							nombreTarea.setEstado("IN");
						} else {
							if (getEstado().equals("ELIMINADO")
									|| getEstado().equals("RM")) {
								nombreTarea.setEstado("RM");
							}
						}
					}
					nombreTareaDao.modificar(nombreTarea);
					initNew();
				}
			} catch (Exception e) {
				System.out.println("Error en modificacion de nombreTarea: "
						+ e.getMessage());
			}
		}
	}

	public void eliminar() {
		try {
			nombreTareaDao.eliminar(nombreTarea);
			initNew();
		} catch (Exception e) {
			System.out.println("Error en eliminacion de nombreTarea: "
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

	public void onRowSelect(SelectEvent event) {
		crear = false;
		registrar = false;
		modificar = true;
		tipoColumnTable = "col-md-8";
		FacesContext.getCurrentInstance().renderResponse();
		resetearFitrosTabla("form001:singleDT");
	}

	public void resetearFitrosTabla(String id) {
		DataTable table = (DataTable) FacesContext.getCurrentInstance()
				.getViewRoot().findComponent(id);
		table.setSelection(null);
		table.reset();
	}

}
