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

import com.teds.spaps.interfaces.dao.IMotivoDao;
import com.teds.spaps.model.Motivo;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.Usuario;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

/**
 * @author ANITA
 *
 */
@ManagedBean(name = "motivoController")
@ViewScoped
public class MotivoController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3470636703217420208L;
	/******* DAO **********/
	private @Inject IMotivoDao motivoDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private Motivo motivo;
	private Motivo motivoSelected;
	private Sucursal sucursal;
	private Usuario usuario;

	/******* LIST **********/
	private List<Motivo> listaMotivo;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;

	@Inject
	Conversation conversation;

	/**
	 * 
	 */
	public MotivoController() {
	}

	public Motivo getMotivo() {
		return motivo;
	}

	public Motivo getMotivoSelected() {
		return motivoSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<Motivo> getListaMotivo() {
		return listaMotivo;
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

	public void setMotivo(Motivo motivo) {
		this.motivo = motivo;
	}

	public void setMotivoSelected(Motivo motivoSelected) {
		this.motivoSelected = motivoSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaMotivo(List<Motivo> listaMotivo) {
		this.listaMotivo = listaMotivo;
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
		motivo = new Motivo();
		motivoSelected = new Motivo();
		usuario = sessionMain.getUsuarioLogin();
		sucursal = sessionMain.PruebaSucursal();
		listaMotivo = motivoDao.obtenerMotivoOrdenAscPorId();
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
			if (verificarRestriccion(motivo.getDescripcion())) {
				FacesUtil
						.errorMessage("En el registro de motivos no puede ingresar la palabra receta.");
			} else {
				if (motivo.getDescripcion().trim().isEmpty()
						|| getSucursal() == null
						|| getSucursal().getCompania() == null) {
					FacesUtil.infoMessage("VALIDACION",
							"No puede haber campos vacíos");
					return;
				} else {
					motivo.setEstado("AC");
					motivo.setSucursal(getSucursal());
					motivo.setCompania(getSucursal().getCompania());
					motivo.setFechaRegistro(new Date());
					motivo.setFechaModificacion(motivo.getFechaRegistro());
					motivo.setUsuarioRegistro(getUsuario().getLogin());
					motivoDao.registrar(motivo);
					initNew();
				}
			}
		} catch (Exception e) {
			System.out
					.println("Error en registro de motivo: " + e.getMessage());
		}

	}

	public void actualizar() {
		try {
			if (verificarRestriccion(motivo.getDescripcion())) {
				FacesUtil
						.errorMessage("En el registro de motivos no puede ingresar la palabra receta.");
			} else {
				if (motivo.getDescripcion().trim().isEmpty()
						|| motivo.getEstado().trim().isEmpty()
						|| getSucursal() == null
						|| getSucursal().getCompania() == null) {
					FacesUtil.infoMessage("VALIDACION",
							"No puede haber campos vacíos");
					return;
				} else {
					motivo.setFechaModificacion(new Date());
					motivo.setUsuarioRegistro(sessionMain.getUsuarioLogin()
							.getLogin());
					motivoDao.modificar(motivo);
					initNew();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de motivo: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			motivoDao.eliminar(getMotivo());
			initNew();
		} catch (Exception e) {
			System.out.println("Error en eliminacion de motivo: "
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
