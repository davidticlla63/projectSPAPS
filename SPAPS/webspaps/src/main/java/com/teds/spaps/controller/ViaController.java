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

import com.teds.spaps.interfaces.dao.IViaDao;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.Usuario;
import com.teds.spaps.model.Via;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

/**
 * @author ANITA
 *
 */
@ManagedBean(name = "viaController")
@ViewScoped
public class ViaController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4539263847596962866L;
	/******* DAO **********/
	private @Inject IViaDao viaDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private Via via;
	private Via viaSelected;
	private Sucursal sucursal;
	private Usuario usuario;

	/******* LIST **********/
	private List<Via> listaVia;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;

	@Inject
	Conversation conversation;

	/**
	 * 
	 */
	public ViaController() {
	}

	public Via getVia() {
		return via;
	}

	public Via getViaSelected() {
		return viaSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<Via> getListaVia() {
		return listaVia;
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

	public void setVia(Via via) {
		this.via = via;
	}

	public void setViaSelected(Via viaSelected) {
		this.viaSelected = viaSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaVia(List<Via> listaVia) {
		this.listaVia = listaVia;
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
		via = new Via();
		viaSelected = new Via();
		usuario = sessionMain.getUsuarioLogin();
		sucursal = sessionMain.PruebaSucursal();
		listaVia = viaDao.obtenerViaOrdenAscPorId();
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
			if (viaDao.verificarRepetido(via, sucursal.getCompania())) {
				FacesUtil.errorMessage("Ya existe el registro.");
			} else {
				if (via.getDescripcion().trim().isEmpty()
						|| getSucursal() == null
						|| getSucursal().getCompania() == null) {
					FacesUtil.infoMessage("VALIDACION",
							"No puede haber campos vacíos");
					return;
				} else {
					via.setEstado("AC");
					via.setSucursal(getSucursal());
					via.setCompania(getSucursal().getCompania());
					via.setFechaRegistro(new Date());
					via.setFechaModificacion(via.getFechaRegistro());
					via.setUsuarioRegistro(getUsuario().getLogin());
					viaDao.registrar(via);
					initNew();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en registro de via: " + e.getMessage());
		}

	}

	public void actualizar() {
		try {
			if (viaDao.verificarRepetidoUpdate(via, sucursal.getCompania())) {
				FacesUtil.errorMessage("Ya existe el registro.");
			} else {
				if (via.getDescripcion().trim().isEmpty()
						|| via.getEstado().trim().isEmpty()
						|| getSucursal() == null
						|| getSucursal().getCompania() == null) {
					FacesUtil.infoMessage("VALIDACION",
							"No puede haber campos vacíos");
					return;
				} else {
					via.setFechaModificacion(new Date());
					via.setUsuarioRegistro(sessionMain.getUsuarioLogin()
							.getLogin());
					viaDao.modificar(via);
					initNew();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de via: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			viaDao.eliminar(getVia());
			initNew();
		} catch (Exception e) {
			System.out
					.println("Error en eliminacion de via: " + e.getMessage());
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
