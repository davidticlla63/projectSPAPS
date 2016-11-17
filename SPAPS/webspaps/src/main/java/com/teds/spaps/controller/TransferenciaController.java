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

import com.teds.spaps.interfaces.dao.ITransferenciaDao;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.Transferencia;
import com.teds.spaps.model.Usuario;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

/**
 * @author ANITA
 *
 */
@ManagedBean(name = "transferenciaController")
@ViewScoped
public class TransferenciaController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6500127563937547015L;
	/******* DAO **********/
	private @Inject ITransferenciaDao transferenciaDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private Transferencia transferencia;
	private Transferencia transferenciaSelected;
	private Sucursal sucursal;
	private Usuario usuario;

	/******* LIST **********/
	private List<Transferencia> listaTransferencia;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;

	@Inject
	Conversation conversation;

	/**
	 * 
	 */
	public TransferenciaController() {
	}

	public Transferencia getTransferencia() {
		return transferencia;
	}

	public Transferencia getTransferenciaSelected() {
		return transferenciaSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<Transferencia> getListaTransferencia() {
		return listaTransferencia;
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

	public void setTransferencia(Transferencia transferencia) {
		this.transferencia = transferencia;
	}

	public void setTransferenciaSelected(Transferencia transferenciaSelected) {
		this.transferenciaSelected = transferenciaSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaTransferencia(List<Transferencia> listaTransferencia) {
		this.listaTransferencia = listaTransferencia;
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
		transferencia = new Transferencia();
		transferenciaSelected = new Transferencia();
		usuario = sessionMain.getUsuarioLogin();
		sucursal = sessionMain.PruebaSucursal();
		listaTransferencia = transferenciaDao
				.obtenerTransferenciaOrdenAscPorId();
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
			if (transferencia.getMotivo().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				transferencia.setEstado("AC");
				transferencia.setSucursal(getSucursal());
				transferencia.setCompania(getSucursal().getCompania());
				transferencia.setFechaRegistro(new Date());
				transferencia.setFechaModificacion(transferencia
						.getFechaRegistro());
				transferencia.setUsuarioRegistro(getUsuario().getLogin());
				transferenciaDao.registrar(transferencia);
				initNew();
			}
		} catch (Exception e) {
			System.out.println("Error en registro de transferencia: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			if (transferencia.getMotivo().trim().isEmpty()
					|| transferencia.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				transferencia.setFechaModificacion(new Date());
				transferencia.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getLogin());
				transferenciaDao.modificar(transferencia);
				initNew();
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de transferencia: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			transferenciaDao.eliminar(getTransferencia());
			initNew();
		} catch (Exception e) {
			System.out.println("Error en eliminacion de transferencia: "
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
