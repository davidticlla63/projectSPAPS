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

import com.teds.spaps.interfaces.dao.ITipoDiagnosticoDao;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.TipoDiagnostico;
import com.teds.spaps.model.Usuario;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

/**
 * @author ANITA
 *
 */
@ManagedBean(name = "tipoDiagnosticoController")
@ViewScoped
public class TipoDiagnosticoController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3775182051526544337L;
	/******* DAO **********/
	private @Inject ITipoDiagnosticoDao tipoDiagnosticoDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private TipoDiagnostico tipoDiagnostico;
	private TipoDiagnostico tipoDiagnosticoSelected;
	private Sucursal sucursal;
	private Usuario usuario;

	/******* LIST **********/
	private List<TipoDiagnostico> listaTipoDiagnostico;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;

	@Inject
	Conversation conversation;

	/**
	 * 
	 */
	public TipoDiagnosticoController() {
	}

	public TipoDiagnostico getTipoDiagnostico() {
		return tipoDiagnostico;
	}

	public TipoDiagnostico getTipoDiagnosticoSelected() {
		return tipoDiagnosticoSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<TipoDiagnostico> getListaTipoDiagnostico() {
		return listaTipoDiagnostico;
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

	public void setTipoDiagnostico(TipoDiagnostico tipoDiagnostico) {
		this.tipoDiagnostico = tipoDiagnostico;
	}

	public void setTipoDiagnosticoSelected(
			TipoDiagnostico tipoDiagnosticoSelected) {
		this.tipoDiagnosticoSelected = tipoDiagnosticoSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaTipoDiagnostico(
			List<TipoDiagnostico> listaTipoDiagnostico) {
		this.listaTipoDiagnostico = listaTipoDiagnostico;
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
		tipoDiagnostico = new TipoDiagnostico();
		tipoDiagnosticoSelected = new TipoDiagnostico();
		usuario = sessionMain.getUsuarioLogin();
		sucursal = sessionMain.PruebaSucursal();
		listaTipoDiagnostico = tipoDiagnosticoDao
				.obtenerTipoDiagnosticoOrdenAscPorId();
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
			if (tipoDiagnostico.getDescripcion().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				tipoDiagnostico.setEstado("AC");
				tipoDiagnostico.setSucursal(getSucursal());
				tipoDiagnostico.setCompania(getSucursal().getCompania());
				tipoDiagnostico.setFechaRegistro(new Date());
				tipoDiagnostico.setFechaModificacion(tipoDiagnostico
						.getFechaRegistro());
				tipoDiagnostico.setUsuarioRegistro(getUsuario().getLogin());
				tipoDiagnosticoDao.registrar(tipoDiagnostico);
				initNew();
			}
		} catch (Exception e) {
			System.out.println("Error en registro de tipoDiagnostico: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			if (tipoDiagnostico.getDescripcion().trim().isEmpty()
					|| tipoDiagnostico.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				tipoDiagnostico.setFechaModificacion(new Date());
				tipoDiagnostico.setUsuarioRegistro(sessionMain
						.getUsuarioLogin().getLogin());
				tipoDiagnosticoDao.modificar(tipoDiagnostico);
				initNew();
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de tipoDiagnostico: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			tipoDiagnosticoDao.eliminar(getTipoDiagnostico());
			initNew();
		} catch (Exception e) {
			System.out.println("Error en eliminacion de tipoDiagnostico: "
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
