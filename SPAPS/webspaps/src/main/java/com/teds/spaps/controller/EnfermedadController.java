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

import com.teds.spaps.interfaces.dao.IEnfermedadDao;
import com.teds.spaps.model.Enfermedad;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.Usuario;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

/**
 * @author ANITA
 *
 */
@ManagedBean(name = "enfermedadController")
@ViewScoped
public class EnfermedadController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1682168731519850189L;
	/******* DAO **********/
	private @Inject IEnfermedadDao enfermedadDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private Enfermedad enfermedad;
	private Enfermedad enfermedadSelected;
	private Sucursal sucursal;
	private Usuario usuario;

	/******* LIST **********/
	private List<Enfermedad> listaEnfermedad;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;

	@Inject
	Conversation conversation;

	/**
	 * 
	 */
	public EnfermedadController() {
	}

	public Enfermedad getEnfermedad() {
		return enfermedad;
	}

	public Enfermedad getEnfermedadSelected() {
		return enfermedadSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<Enfermedad> getListaEnfermedad() {
		return listaEnfermedad;
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

	public void setEnfermedad(Enfermedad enfermedad) {
		this.enfermedad = enfermedad;
	}

	public void setEnfermedadSelected(Enfermedad enfermedadSelected) {
		this.enfermedadSelected = enfermedadSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaEnfermedad(List<Enfermedad> listaEnfermedad) {
		this.listaEnfermedad = listaEnfermedad;
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
		enfermedad = new Enfermedad();
		enfermedadSelected = new Enfermedad();
		usuario = sessionMain.getUsuarioLogin();
		sucursal = sessionMain.PruebaSucursal();
		listaEnfermedad = enfermedadDao.obtenerEnfermedadOrdenAscPorId();
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
			if (enfermedad.getNombre().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				enfermedad.setEstado("AC");
				enfermedad.setSucursal(getSucursal());
				enfermedad.setCompania(getSucursal().getCompania());
				enfermedad.setFechaRegistro(new Date());
				enfermedad.setFechaModificacion(enfermedad.getFechaRegistro());
				enfermedad.setUsuarioRegistro(getUsuario().getLogin());
				enfermedadDao.registrar(enfermedad);
				initNew();
			}
		} catch (Exception e) {
			System.out.println("Error en registro de enfermedad: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			if (enfermedad.getNombre().trim().isEmpty()
					|| enfermedad.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				enfermedad.setFechaModificacion(new Date());
				enfermedad.setUsuarioRegistro(getUsuario().getLogin());
				enfermedadDao.modificar(enfermedad);
				initNew();
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de enfermedad: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			enfermedadDao.eliminar(getEnfermedad());
			initNew();
		} catch (Exception e) {
			System.out.println("Error en eliminacion de enfermedad: "
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
