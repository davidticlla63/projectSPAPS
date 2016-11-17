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

import com.teds.spaps.interfaces.dao.IPresentacionDao;
import com.teds.spaps.model.Presentacion;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.Usuario;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

/**
 * @author ANITA
 *
 */
@ManagedBean(name = "presentacionController")
@ViewScoped
public class PresentacionController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -363041033638571694L;
	/******* DAO **********/
	private @Inject IPresentacionDao presentacionDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private Presentacion presentacion;
	private Presentacion presentacionSelected;
	private Sucursal sucursal;
	private Usuario usuario;

	/******* LIST **********/
	private List<Presentacion> listaPresentacion;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;

	@Inject
	Conversation conversation;

	/**
	 * 
	 */
	public PresentacionController() {
	}

	public Presentacion getPresentacion() {
		return presentacion;
	}

	public Presentacion getPresentacionSelected() {
		return presentacionSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<Presentacion> getListaPresentacion() {
		return listaPresentacion;
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

	public void setPresentacion(Presentacion presentacion) {
		this.presentacion = presentacion;
	}

	public void setPresentacionSelected(Presentacion presentacionSelected) {
		this.presentacionSelected = presentacionSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaPresentacion(List<Presentacion> listaPresentacion) {
		this.listaPresentacion = listaPresentacion;
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
		presentacion = new Presentacion();
		presentacionSelected = new Presentacion();
		usuario = sessionMain.getUsuarioLogin();
		sucursal = sessionMain.PruebaSucursal();
		listaPresentacion = presentacionDao.obtenerPresentacionOrdenAscPorId();
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
			if (presentacionDao.verificarRepetido(presentacion,
					sucursal.getCompania())) {
				FacesUtil.errorMessage("Ya existe el registro.");
			} else {
				if (presentacion.getDescripcion().trim().isEmpty()
						|| getSucursal() == null
						|| getSucursal().getCompania() == null) {
					FacesUtil.infoMessage("VALIDACION",
							"No puede haber campos vacíos");
					return;
				} else {
					presentacion.setEstado("AC");
					presentacion.setSucursal(getSucursal());
					presentacion.setCompania(getSucursal().getCompania());
					presentacion.setFechaRegistro(new Date());
					presentacion.setFechaModificacion(presentacion
							.getFechaRegistro());
					presentacion.setUsuarioRegistro(getUsuario().getLogin());
					presentacionDao.registrar(presentacion);
					initNew();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en registro de presentacion: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			if (presentacionDao.verificarRepetidoUpdate(presentacion,
					sucursal.getCompania())) {
				FacesUtil.errorMessage("Ya existe el registro.");
			} else {
				if (presentacion.getDescripcion().trim().isEmpty()
						|| presentacion.getEstado().trim().isEmpty()
						|| getSucursal() == null
						|| getSucursal().getCompania() == null) {
					FacesUtil.infoMessage("VALIDACION",
							"No puede haber campos vacíos");
					return;
				} else {
					presentacion.setFechaModificacion(new Date());
					presentacion.setUsuarioRegistro(sessionMain
							.getUsuarioLogin().getLogin());
					presentacionDao.modificar(presentacion);
					initNew();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de presentacion: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			presentacionDao.eliminar(getPresentacion());
			initNew();
		} catch (Exception e) {
			System.out.println("Error en eliminacion de presentacion: "
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
