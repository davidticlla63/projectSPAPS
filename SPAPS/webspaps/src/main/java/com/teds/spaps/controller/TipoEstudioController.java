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

import com.teds.spaps.interfaces.dao.ITipoEstudioDao;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.TipoEstudio;
import com.teds.spaps.model.Usuario;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

/**
 * @author ANITA
 *
 */
@ManagedBean(name = "tipoEstudioController")
@ViewScoped
public class TipoEstudioController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1583737613725678296L;
	/******* DAO **********/
	private @Inject ITipoEstudioDao tipoEstudioDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private TipoEstudio tipoEstudio;
	private TipoEstudio tipoEstudioSelected;
	private Sucursal sucursal;
	private Usuario usuario;

	/******* LIST **********/
	private List<TipoEstudio> listaTipoEstudio;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;

	@Inject
	Conversation conversation;

	/**
	 * 
	 */
	public TipoEstudioController() {
	}

	public TipoEstudio getTipoEstudio() {
		return tipoEstudio;
	}

	public TipoEstudio getTipoEstudioSelected() {
		return tipoEstudioSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<TipoEstudio> getListaTipoEstudio() {
		return listaTipoEstudio;
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

	public void setTipoEstudio(TipoEstudio tipoEstudio) {
		this.tipoEstudio = tipoEstudio;
	}

	public void setTipoEstudioSelected(TipoEstudio tipoEstudioSelected) {
		this.tipoEstudioSelected = tipoEstudioSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaTipoEstudio(List<TipoEstudio> listaTipoEstudio) {
		this.listaTipoEstudio = listaTipoEstudio;
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
		tipoEstudio = new TipoEstudio();
		tipoEstudioSelected = new TipoEstudio();
		usuario = sessionMain.getUsuarioLogin();
		sucursal = sessionMain.PruebaSucursal();
		listaTipoEstudio = tipoEstudioDao.obtenerTipoEstudioOrdenAscPorId();
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
			if (tipoEstudio.getDescripcion().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				tipoEstudio.setEstado("AC");
				tipoEstudio.setSucursal(getSucursal());
				tipoEstudio.setCompania(getSucursal().getCompania());
				tipoEstudio.setFechaRegistro(new Date());
				tipoEstudio
						.setFechaModificacion(tipoEstudio.getFechaRegistro());
				tipoEstudio.setUsuarioRegistro(getUsuario().getLogin());
				tipoEstudioDao.registrar(tipoEstudio);
				initNew();
			}
		} catch (Exception e) {
			System.out.println("Error en registro de tipoEstudio: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			if (tipoEstudio.getDescripcion().trim().isEmpty()
					|| tipoEstudio.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				tipoEstudio.setFechaModificacion(new Date());
				tipoEstudio.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getLogin());
				tipoEstudioDao.modificar(tipoEstudio);
				initNew();
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de tipoEstudio: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			tipoEstudioDao.eliminar(getTipoEstudio());
			initNew();
		} catch (Exception e) {
			System.out.println("Error en eliminacion de tipoEstudio: "
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
