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

import com.teds.spaps.interfaces.dao.IEvolucionDao;
import com.teds.spaps.model.Evolucion;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.Usuario;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

/**
 * @author ANITA
 *
 */
@ManagedBean(name = "evolucionController")
@ViewScoped
public class EvolucionController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2880203035349039505L;
	/******* DAO **********/
	private @Inject IEvolucionDao evolucionDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private Evolucion evolucion;
	private Evolucion evolucionSelected;
	private Sucursal sucursal;
	private Usuario usuario;

	/******* LIST **********/
	private List<Evolucion> listaEvolucion;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;

	@Inject
	Conversation conversation;

	/**
	 * 
	 */
	public EvolucionController() {
	}

	public Evolucion getEvolucion() {
		return evolucion;
	}

	public Evolucion getEvolucionSelected() {
		return evolucionSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<Evolucion> getListaEvolucion() {
		return listaEvolucion;
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

	public void setEvolucion(Evolucion evolucion) {
		this.evolucion = evolucion;
	}

	public void setEvolucionSelected(Evolucion evolucionSelected) {
		this.evolucionSelected = evolucionSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaEvolucion(List<Evolucion> listaEvolucion) {
		this.listaEvolucion = listaEvolucion;
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
		evolucion = new Evolucion();
		evolucionSelected = new Evolucion();
		usuario = sessionMain.getUsuarioLogin();
		sucursal = sessionMain.PruebaSucursal();
		listaEvolucion = evolucionDao
				.obtenerPorCompania(sucursal.getCompania());
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
			if (evolucion.getEvolucion().trim().isEmpty()
					|| evolucion.getObservacion().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				evolucion.setEstado("AC");
				evolucion.setSucursal(getSucursal());
				evolucion.setCompania(getSucursal().getCompania());
				evolucion.setFechaRegistro(new Date());
				evolucion.setFechaModificacion(evolucion.getFechaRegistro());
				evolucion.setUsuarioRegistro(getUsuario().getLogin());
				evolucionDao.registrar(evolucion);
				initNew();
			}
		} catch (Exception e) {
			System.out.println("Error en registro de evolucion: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			if (evolucion.getEvolucion().trim().isEmpty()
					|| evolucion.getObservacion().trim().isEmpty()
					|| evolucion.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				evolucion.setFechaModificacion(new Date());
				evolucion.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getLogin());
				evolucionDao.modificar(evolucion);
				initNew();
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de evolucion: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			evolucionDao.eliminar(getEvolucion());
			initNew();
		} catch (Exception e) {
			System.out.println("Error en eliminacion de evolucion: "
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
