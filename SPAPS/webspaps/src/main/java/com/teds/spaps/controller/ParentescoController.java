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

import com.teds.spaps.interfaces.dao.IParentescoDao;
import com.teds.spaps.model.Parentesco;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.Usuario;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

@ManagedBean(name = "parentescoController")
@ViewScoped
/**
 * @author ANITA
 *
 */
public class ParentescoController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 178292646123052040L;

	/******* DAO **********/
	private @Inject IParentescoDao parentescoDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private Parentesco parentesco;
	private Parentesco parentescoSelected;
	private Sucursal sucursal;
	private Usuario usuario;

	/******* LIST **********/
	private List<Parentesco> listaParentesco;
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
	public ParentescoController() {
	}

	public Parentesco getParentesco() {
		return parentesco;
	}

	public Parentesco getParentescoSelected() {
		return parentescoSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<Parentesco> getListaParentesco() {
		return listaParentesco;
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

	public void setParentesco(Parentesco parentesco) {
		this.parentesco = parentesco;
	}

	public void setParentescoSelected(Parentesco parentescoSelected) {
		this.parentescoSelected = parentescoSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaParentesco(List<Parentesco> listaParentesco) {
		this.listaParentesco = listaParentesco;
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

	@PostConstruct
	public void initNew() {
		parentesco = new Parentesco();
		parentescoSelected = new Parentesco();
		usuario = sessionMain.getUsuarioLogin();
		sucursal = sessionMain.PruebaSucursal();
		listaParentesco = parentescoDao.obtenerParentescoOrdenAscPorId();
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
	}

	public void cambiarAspecto() {
		System.out.println("entro a cambiar aspecto");
		crear = false;
		registrar = true;
		modificar = false;
	}

	public void onRowSelect(SelectEvent event) {
		crear = false;
		registrar = false;
		modificar = true;
		// parentesco = parentescoSelected;
		estado = parentesco.getEstado();
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
			if (parentesco.getDescripcion().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				parentesco.setEstado("AC");
				parentesco.setSucursal(getSucursal());
				parentesco.setCompania(getSucursal().getCompania());
				parentesco.setFechaRegistro(new Date());
				parentesco.setFechaModificacion(parentesco.getFechaRegistro());
				parentesco.setUsuarioRegistro(getUsuario().getLogin());
				parentescoDao.registrar(parentesco);
				initNew();
			}
		} catch (Exception e) {
			System.out.println("Error en registro de parentesco: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			if (parentesco.getDescripcion().trim().isEmpty()
					|| getEstado().trim().isEmpty() || getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				if (getEstado().equals("ACTIVO") || getEstado().equals("AC")) {
					parentesco.setEstado("AC");
				} else {
					if (getEstado().equals("INACTIVO")
							|| getEstado().equals("IN")) {
						parentesco.setEstado("IN");
					} else {
						if (getEstado().equals("ELIMINADO")
								|| getEstado().equals("RM")) {
							parentesco.setEstado("RM");
						}
					}
				}
				parentesco.setFechaModificacion(new Date());
				parentesco.setUsuarioRegistro(getUsuario().getLogin());
				parentescoDao.modificar(parentesco);
				initNew();
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de parentesco: "
					+ e.getMessage());
		}
	}

	public void eliminar() {
		try {
			parentescoDao.eliminar(parentesco);
			initNew();
		} catch (Exception e) {
			System.out.println("Error en eliminacion de parentesco: "
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
