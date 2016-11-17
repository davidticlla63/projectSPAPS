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

import com.teds.spaps.interfaces.dao.IFrecuenciaDao;
import com.teds.spaps.model.Frecuencia;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.Usuario;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

/**
 * @author ANITA
 *
 */
@ManagedBean(name = "frecuenciaController")
@ViewScoped
public class FrecuenciaController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2807434315789870216L;
	/******* DAO **********/
	private @Inject IFrecuenciaDao frecuenciaDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private Frecuencia frecuencia;
	private Frecuencia frecuenciaSelected;
	private Sucursal sucursal;
	private Usuario usuario;

	/******* LIST **********/
	private List<Frecuencia> listaFrecuencia;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;

	@Inject
	Conversation conversation;

	/**
	 * 
	 */
	public FrecuenciaController() {
	}

	public Frecuencia getFrecuencia() {
		return frecuencia;
	}

	public Frecuencia getFrecuenciaSelected() {
		return frecuenciaSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<Frecuencia> getListaFrecuencia() {
		return listaFrecuencia;
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

	public void setFrecuencia(Frecuencia frecuencia) {
		this.frecuencia = frecuencia;
	}

	public void setFrecuenciaSelected(Frecuencia frecuenciaSelected) {
		this.frecuenciaSelected = frecuenciaSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaFrecuencia(List<Frecuencia> listaFrecuencia) {
		this.listaFrecuencia = listaFrecuencia;
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
		frecuencia = new Frecuencia();
		frecuenciaSelected = new Frecuencia();
		usuario = sessionMain.getUsuarioLogin();
		sucursal = sessionMain.PruebaSucursal();
		listaFrecuencia = frecuenciaDao.obtenerFrecuenciaOrdenAscPorId();
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

	public boolean verificarRestriccion(String dato) {
		return dato.toLowerCase().contains("receta");
	}

	public void registrar() {
		try {
			if (frecuenciaDao.verificarRepetido(frecuencia,
					sucursal.getCompania())) {
				FacesUtil.errorMessage("Ya existe el registro.");
			} else {
				if (frecuencia.getDescripcion().trim().isEmpty()
						|| getSucursal() == null
						|| getSucursal().getCompania() == null) {
					FacesUtil.infoMessage("VALIDACION",
							"No puede haber campos vacíos");
					return;
				} else {
					frecuencia.setEstado("AC");
					frecuencia.setSucursal(getSucursal());
					frecuencia.setCompania(getSucursal().getCompania());
					frecuencia.setFechaRegistro(new Date());
					frecuencia.setFechaModificacion(frecuencia
							.getFechaRegistro());
					frecuencia.setUsuarioRegistro(getUsuario().getLogin());
					frecuenciaDao.registrar(frecuencia);
					initNew();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en registro de frecuencia: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			if (frecuenciaDao.verificarRepetidoUpdate(frecuencia,
					sucursal.getCompania())) {
				FacesUtil.errorMessage("Ya existe el registro.");
			} else {
				if (frecuencia.getDescripcion().trim().isEmpty()
						|| frecuencia.getEstado().trim().isEmpty()
						|| getSucursal() == null
						|| getSucursal().getCompania() == null) {
					FacesUtil.infoMessage("VALIDACION",
							"No puede haber campos vacíos");
					return;
				} else {
					frecuencia.setFechaModificacion(new Date());
					frecuencia.setUsuarioRegistro(sessionMain.getUsuarioLogin()
							.getLogin());
					frecuenciaDao.modificar(frecuencia);
					initNew();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de frecuencia: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			frecuenciaDao.eliminar(getFrecuencia());
			initNew();
		} catch (Exception e) {
			System.out.println("Error en eliminacion de frecuencia: "
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
