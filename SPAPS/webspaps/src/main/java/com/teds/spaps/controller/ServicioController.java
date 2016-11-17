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

import com.teds.spaps.interfaces.dao.IServicioDao;
import com.teds.spaps.model.Servicio;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.Usuario;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

/**
 * @author ANITA
 *
 */
@ManagedBean(name = "servicioController")
@ViewScoped
public class ServicioController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7583583979769096407L;
	/******* DAO **********/
	private @Inject IServicioDao servicioDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private Servicio servicio;
	private Servicio coberturaSelected;
	private Sucursal sucursal;
	private Usuario usuario;

	/******* LIST **********/
	private List<Servicio> listaCobertura;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;

	@Inject
	Conversation conversation;

	/**
	 * 
	 */
	public ServicioController() {
	}

	public Servicio getCobertura() {
		return servicio;
	}

	public Servicio getCoberturaSelected() {
		return coberturaSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<Servicio> getListaCobertura() {
		return listaCobertura;
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

	public void setCobertura(Servicio servicio) {
		this.servicio = servicio;
	}

	public void setCoberturaSelected(Servicio coberturaSelected) {
		this.coberturaSelected = coberturaSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaCobertura(List<Servicio> listaCobertura) {
		this.listaCobertura = listaCobertura;
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
		servicio = new Servicio();
		coberturaSelected = new Servicio();
		usuario = sessionMain.getUsuarioLogin();
		sucursal = sessionMain.PruebaSucursal();
		listaCobertura = servicioDao.obtenerPorSucursal(sucursal);
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
	}

	public void actionNuevo() {
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
		if (servicioDao.verificarRepetido(servicio, sucursal.getCompania())) {
			FacesUtil.infoMessage("VALIDACIÓN", "Ya existe el registro.");
		} else {
			try {
				if (verificarRestriccion(servicio.getDescripcion())) {
					FacesUtil
							.errorMessage("En el registro de coberturas no puede ingresar la palabra receta.");
				} else {
					if (servicio.getDescripcion().trim().isEmpty()
							|| getSucursal() == null
							|| getSucursal().getCompania() == null) {
						FacesUtil.infoMessage("VALIDACION",
								"No puede haber campos vacíos");
						return;
					} else {
						servicio.setEstado("AC");
						servicio.setSucursal(getSucursal());
						servicio.setCompania(getSucursal().getCompania());
						servicio.setFechaRegistro(new Date());
						servicio.setFechaModificacion(servicio
								.getFechaRegistro());
						servicio.setUsuarioRegistro(getUsuario().getLogin());
						servicioDao.registrar(servicio);
						initNew();
					}
				}
			} catch (Exception e) {
				System.out.println("Error en registro de cobertura: "
						+ e.getMessage());
			}
		}
	}

	public void actualizar() {
		if (servicioDao.verificarRepetidoUpdate(servicio,
				sucursal.getCompania())) {
			FacesUtil.infoMessage("VALIDACIÓN", "Ya existe el registro.");
		} else {
			try {
				if (verificarRestriccion(servicio.getDescripcion())) {
					FacesUtil
							.errorMessage("En el registro de coberturas no puede ingresar la palabra receta.");
				} else {
					if (servicio.getDescripcion().trim().isEmpty()
							|| servicio.getEstado().trim().isEmpty()
							|| getSucursal() == null
							|| getSucursal().getCompania() == null) {
						FacesUtil.infoMessage("VALIDACION",
								"No puede haber campos vacíos");
						return;
					} else {
						servicio.setFechaModificacion(new Date());
						servicio.setUsuarioRegistro(sessionMain
								.getUsuarioLogin().getLogin());
						servicioDao.modificar(servicio);
						initNew();
					}
				}
			} catch (Exception e) {
				System.out.println("Error en modificacion de cobertura: "
						+ e.getMessage());
			}
		}
	}

	public void eliminar() {
		try {
			servicioDao.eliminar(getCobertura());
			initNew();
		} catch (Exception e) {
			System.out.println("Error en eliminacion de cobertura: "
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
