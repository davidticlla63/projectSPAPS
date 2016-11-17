/**
 * 
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

import com.teds.spaps.interfaces.dao.IUnidadVecinalDao;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.UnidadVecinal;
import com.teds.spaps.model.Usuario;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

@ManagedBean(name = "unidadVecinalController")
@ViewScoped
/**
 * @author ANITA
 *
 */
public class UnidadVecinalController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8031953384804673612L;

	/******* DAO **********/
	private @Inject IUnidadVecinalDao unidadVecinalDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private UnidadVecinal unidadVecinal;
	private UnidadVecinal unidadVecinalSelected;
	private Sucursal sucursal;
	private Usuario usuario;

	/******* LIST **********/
	private List<UnidadVecinal> listaUnidadVecinal;
	private String[] listaEstado = { "ACTIVO", "INACTIVO", "ELIMINADO" };

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;
	private String estado = "AC";

	@Inject
	Conversation conversation;

	/**
	 * 
	 */
	public UnidadVecinalController() {
	}

	public UnidadVecinal getUnidadVecinal() {
		return unidadVecinal;
	}

	public UnidadVecinal getUnidadVecinalSelected() {
		return unidadVecinalSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<UnidadVecinal> getListaUnidadVecinal() {
		return listaUnidadVecinal;
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

	public void setUnidadVecinal(UnidadVecinal unidadVecinal) {
		this.unidadVecinal = unidadVecinal;
	}

	public void setUnidadVecinalSelected(UnidadVecinal unidadVecinalSelected) {
		this.unidadVecinalSelected = unidadVecinalSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaUnidadVecinal(List<UnidadVecinal> listaUnidadVecinal) {
		this.listaUnidadVecinal = listaUnidadVecinal;
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

	public String[] getListaEstado() {
		return listaEstado;
	}

	public void setListaEstado(String[] listaEstado) {
		this.listaEstado = listaEstado;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void actionNuevo() {
		crear = false;
		modificar = false;
		registrar = true;
	}

	public void actionCancelar() {
		crear = true;
		registrar = false;
		unidadVecinal = new UnidadVecinal();
		unidadVecinalSelected = new UnidadVecinal();
	}

	@PostConstruct
	public void initNew() {
		unidadVecinal = new UnidadVecinal();
		unidadVecinalSelected = new UnidadVecinal();
		sucursal = sessionMain.PruebaSucursal();
		usuario = sessionMain.getUsuarioLogin();
		listaUnidadVecinal = unidadVecinalDao
				.obtenerUnidadVecinalOrdenAscPorId();
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
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
		unidadVecinal = unidadVecinalSelected;
		estado = unidadVecinal.getEstado();
		if (estado.equals("AC")) {
			setEstado("ACTIVO");
		} else {
			if (estado.equals("IN")) {
				setEstado("INACTIVO");
			} else {
				setEstado("ELIMINADO");
			}
		}
		FacesContext.getCurrentInstance().renderResponse();
		resetearFitrosTabla("form001:dataTableUnidadVecinal");
	}

	public void resetearFitrosTabla(String id) {
		DataTable table = (DataTable) FacesContext.getCurrentInstance()
				.getViewRoot().findComponent(id);
		table.setSelection(null);
		table.reset();
	}

	public void registrar() {
		try {
			if (unidadVecinal.getNumero().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				unidadVecinal.setSucursal(getSucursal());
				unidadVecinal.setCompania(getSucursal().getCompania());
				unidadVecinal.setEstado("AC");
				unidadVecinal.setFechaRegistro(new Date());
				unidadVecinal.setFechaModificacion(unidadVecinal
						.getFechaRegistro());
				unidadVecinal.setUsuarioRegistro(getUsuario().getLogin());
				unidadVecinalDao.registrar(unidadVecinal);
				initNew();
			}
		} catch (Exception e) {
			System.out.println("Error en registro de unidadVecinal: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			if (unidadVecinal.getNumero().trim().isEmpty()
					|| getEstado().trim().isEmpty() || getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			}
			if (getEstado().equals("ACTIVO") || getEstado().equals("AC")) {
				unidadVecinal.setEstado("AC");
			} else {
				if (getEstado().equals("INACTIVO") || getEstado().equals("IN")) {
					unidadVecinal.setEstado("IN");
				} else {
					if (getEstado().equals("ELIMINADO")
							|| getEstado().equals("RM")) {
						unidadVecinal.setEstado("RM");
					}
				}
			}
			unidadVecinal.setFechaModificacion(new Date());
			unidadVecinal.setUsuarioRegistro(getUsuario().getLogin());
			unidadVecinalDao.modificar(unidadVecinal);
			initNew();

		} catch (Exception e) {
			System.out.println("Error en modificacion de unidadVecinal: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			unidadVecinalDao.eliminar(unidadVecinal);
			initNew();
		} catch (Exception e) {
			System.out.println("Error en eliminacion de unidadVecinal: "
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

	public void endConversation() {
		if (!conversation.isTransient()) {
			conversation.end();
			System.out.println(">>>>>>>>>> CONVERSACION TERMINADA...");
		}
	}

}
