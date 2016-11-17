/**
 * @author ANITA
 */
package com.teds.spaps.controller;

import java.io.Serializable;
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

import com.teds.spaps.interfaces.dao.ITipoTareaDao;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.TipoTarea;
import com.teds.spaps.model.Usuario;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

/**
 * @author ANITA
 *
 */
@ManagedBean(name = "tipoTareaController")
@ViewScoped
public class TipoTareaController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1906217694496116805L;
	/******* DAO **********/
	private @Inject ITipoTareaDao tipoTareaDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private TipoTarea tipoTarea;
	private TipoTarea tipoTareaSelected;
	private Sucursal sucursal;
	private Usuario usuario;

	/******* LIST **********/
	private List<TipoTarea> listaTipoTarea;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;

	@Inject
	Conversation conversation;

	/**
	 * 
	 */
	public TipoTareaController() {
	}

	public TipoTarea getTipoTarea() {
		return tipoTarea;
	}

	public TipoTarea getTipoTareaSelected() {
		return tipoTareaSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<TipoTarea> getListaTipoTarea() {
		return listaTipoTarea;
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

	public void setTipoTarea(TipoTarea tipoTarea) {
		this.tipoTarea = tipoTarea;
	}

	public void setTipoTareaSelected(TipoTarea tipoTareaSelected) {
		this.tipoTareaSelected = tipoTareaSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaTipoTarea(List<TipoTarea> listaTipoTarea) {
		this.listaTipoTarea = listaTipoTarea;
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

	@PostConstruct
	public void initNew() {
		tipoTarea = new TipoTarea();
		tipoTareaSelected = new TipoTarea();
		usuario = sessionMain.getUsuarioLogin();
		sucursal = sessionMain.PruebaSucursal();
		listaTipoTarea = tipoTareaDao.obtenerTipoTareaOrdenAscPorId();
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
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
		/*
		 * if (estado.equals("AC")) { setEstado("ACTIVO"); } else { if
		 * (estado.equals("IN")) { setEstado("INACTIVO"); } else {
		 * setEstado("ELIMINADO"); } }
		 */
		FacesContext.getCurrentInstance().renderResponse();
		resetearFitrosTabla("form001:singleDT");
	}

	public void resetearFitrosTabla(String id) {
		DataTable table = (DataTable) FacesContext.getCurrentInstance()
				.getViewRoot().findComponent(id);
		table.setSelection(null);
		table.reset();
	}

	public void registrar() {
		try {
			if (tipoTareaDao.verificarRepetido(tipoTarea, sucursal)) {
				FacesUtil.errorMessage("Ya existe el tipo de tarea.");
			} else {
				if (tipoTarea.getNombre().trim().isEmpty()
						|| tipoTarea.getDescripcion().trim().isEmpty()
						|| getSucursal() == null
						|| getSucursal().getCompania() == null) {
					FacesUtil.infoMessage("VALIDACION",
							"No puede haber campos vacíos");
					return;
				} else {
					tipoTarea.setEstado("AC");
					tipoTarea.setSucursal(getSucursal());
					tipoTarea.setCompania(getSucursal().getCompania());
					tipoTarea.setFechaRegistro(new Date());
					tipoTarea
							.setFechaModificacion(tipoTarea.getFechaRegistro());
					tipoTarea.setUsuarioRegistro(getUsuario().getLogin());
					tipoTareaDao.registrar(tipoTarea);
					initNew();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en registro de tipoTarea: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			if (tipoTareaDao.verificarRepetidoUpdate(tipoTarea, sucursal)) {
				FacesUtil.errorMessage("Ya existe el tipo de tarea.");
			} else {
				if (tipoTarea.getNombre().trim().isEmpty()
						|| tipoTarea.getDescripcion().trim().isEmpty()
						|| tipoTarea.getEstado().trim().isEmpty()
						|| getSucursal() == null
						|| getSucursal().getCompania() == null) {
					FacesUtil.infoMessage("VALIDACION",
							"No puede haber campos vacíos");
					return;
				} else {
					tipoTarea.setFechaModificacion(new Date());
					tipoTarea.setUsuarioRegistro(sessionMain.getUsuarioLogin()
							.getLogin());
					tipoTareaDao.modificar(tipoTarea);
					initNew();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de tipoTarea: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			tipoTareaDao.eliminar(getTipoTarea());
			initNew();
		} catch (Exception e) {
			System.out.println("Error en eliminacion de tipoTarea: "
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

}
