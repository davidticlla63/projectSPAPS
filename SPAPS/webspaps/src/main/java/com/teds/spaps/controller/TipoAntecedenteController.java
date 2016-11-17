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

import com.teds.spaps.interfaces.dao.ITipoAntecedenteDao;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.TipoAntecedente;
import com.teds.spaps.model.Usuario;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

/**
 * @author ANITA
 *
 */
@ManagedBean(name = "tipoAntecedenteController")
@ViewScoped
public class TipoAntecedenteController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9090899727198861708L;
	/******* DAO **********/
	private @Inject ITipoAntecedenteDao tipoAntecedenteDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private TipoAntecedente tipoAntecedente;
	private TipoAntecedente tipoAntecedenteSelected;
	private Sucursal sucursal;
	private Usuario usuario;

	/******* LIST **********/
	private List<TipoAntecedente> listaTipoAntecedente;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;

	@Inject
	Conversation conversation;

	/**
	 * 
	 */
	public TipoAntecedenteController() {
	}

	public TipoAntecedente getTipoAntecedente() {
		return tipoAntecedente;
	}

	public TipoAntecedente getTipoAntecedenteSelected() {
		return tipoAntecedenteSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<TipoAntecedente> getListaTipoAntecedente() {
		return listaTipoAntecedente;
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

	public void setTipoAntecedente(TipoAntecedente tipoAntecedente) {
		this.tipoAntecedente = tipoAntecedente;
	}

	public void setTipoAntecedenteSelected(
			TipoAntecedente tipoAntecedenteSelected) {
		this.tipoAntecedenteSelected = tipoAntecedenteSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaTipoAntecedente(
			List<TipoAntecedente> listaTipoAntecedente) {
		this.listaTipoAntecedente = listaTipoAntecedente;
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
		tipoAntecedente = new TipoAntecedente();
		tipoAntecedenteSelected = new TipoAntecedente();
		usuario = sessionMain.getUsuarioLogin();
		sucursal = sessionMain.PruebaSucursal();
		listaTipoAntecedente = tipoAntecedenteDao
				.obtenerTipoAntecedenteOrdenAscPorId();
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
			if (tipoAntecedente.getDescripcion().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				tipoAntecedente.setEstado("AC");
				tipoAntecedente.setSucursal(getSucursal());
				tipoAntecedente.setCompania(getSucursal().getCompania());
				tipoAntecedente.setFechaRegistro(new Date());
				tipoAntecedente.setFechaModificacion(tipoAntecedente
						.getFechaRegistro());
				tipoAntecedente.setUsuarioRegistro(getUsuario().getLogin());
				tipoAntecedenteDao.registrar(tipoAntecedente);
				initNew();
			}
		} catch (Exception e) {
			System.out.println("Error en registro de tipoAntecedente: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			if (tipoAntecedente.getDescripcion().trim().isEmpty()
					|| tipoAntecedente.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				tipoAntecedente.setFechaModificacion(new Date());
				tipoAntecedente.setUsuarioRegistro(sessionMain
						.getUsuarioLogin().getLogin());
				tipoAntecedenteDao.modificar(tipoAntecedente);
				initNew();
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de tipoAntecedente: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			tipoAntecedenteDao.eliminar(getTipoAntecedente());
			initNew();
		} catch (Exception e) {
			System.out.println("Error en eliminacion de tipoAntecedente: "
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
