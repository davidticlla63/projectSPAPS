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

import com.teds.spaps.interfaces.dao.IBarrioDao;
import com.teds.spaps.model.Barrio;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.Usuario;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

@ManagedBean(name = "barrioController")
@ViewScoped
/**
 * @author ANITA
 *
 */
public class BarrioController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -464680502319771570L;

	/******* DAO **********/
	private @Inject IBarrioDao barrioDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private Barrio barrio;
	private Barrio barrioSelected;
	private Sucursal sucursal;
	private Usuario usuario;

	/******* LIST **********/
	private List<Barrio> listaBarrio;
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
	public BarrioController() {
	}

	public Barrio getBarrio() {
		return barrio;
	}

	public Barrio getBarrioSelected() {
		return barrioSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<Barrio> getListaBarrio() {
		return listaBarrio;
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

	public void setBarrio(Barrio barrio) {
		this.barrio = barrio;
	}

	public void setBarrioSelected(Barrio barrioSelected) {
		this.barrioSelected = barrioSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaBarrio(List<Barrio> listaBarrio) {
		this.listaBarrio = listaBarrio;
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
		barrio = new Barrio();
		barrioSelected = new Barrio();
		usuario = sessionMain.getUsuarioLogin();
		sucursal = sessionMain.PruebaSucursal();
		listaBarrio = barrioDao.obtenerPorCompania(sucursal.getCompania());
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
		barrio = barrioSelected;
		estado = barrio.getEstado();
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
		if (barrioDao.verificarRepetido(barrio, sucursal.getCompania())) {
			FacesUtil.infoMessage("VALIDACIÓN",
					"El barrio ya se encuentra registrado.");
		} else {
			try {
				if (barrio.getNombre().trim().isEmpty()
						|| getSucursal() == null
						|| getSucursal().getCompania() == null) {
					FacesUtil.infoMessage("VALIDACION",
							"No puede haber campos vacíos");
					return;
				} else {
					barrio.setEstado("AC");
					barrio.setSucursal(getSucursal());
					barrio.setCompania(getSucursal().getCompania());
					barrio.setFechaRegistro(new Date());
					barrio.setFechaModificacion(barrio.getFechaRegistro());
					barrio.setUsuarioRegistro(getUsuario().getLogin());
					barrioDao.registrar(barrio);
					initNew();
				}
			} catch (Exception e) {
				System.out.println("Error en registro de barrio: "
						+ e.getMessage());
			}
		}
	}

	public void actualizar() {
		if (barrioDao.verificarRepetidoUpdate(barrio, sucursal.getCompania())) {
			FacesUtil.infoMessage("VALIDACIÓN",
					"El barrio ya se encuentra registrado.");
		} else {
			try {
				if (barrio.getNombre().trim().isEmpty()
						|| getEstado().trim().isEmpty()
						|| getSucursal() == null
						|| getSucursal().getCompania() == null) {
					FacesUtil.infoMessage("VALIDACION",
							"No puede haber campos vacíos");
					return;
				} else {
					if (getEstado().equals("ACTIVO")
							|| getEstado().equals("AC")) {
						barrio.setEstado("AC");
					} else {
						if (getEstado().equals("INACTIVO")
								|| getEstado().equals("IN")) {
							barrio.setEstado("IN");
						} else {
							if (getEstado().equals("ELIMINADO")
									|| getEstado().equals("RM")) {
								barrio.setEstado("RM");
							}
						}
					}
					barrio.setFechaModificacion(new Date());
					barrio.setUsuarioRegistro(sessionMain.getUsuarioLogin()
							.getLogin());
					barrioDao.modificar(barrio);
					initNew();
				}
			} catch (Exception e) {
				System.out.println("Error en modificacion de barrio: "
						+ e.getMessage());
			}
		}
	}

	public void eliminar() {
		try {
			barrioDao.eliminar(getBarrio());
			initNew();
		} catch (Exception e) {
			System.out.println("Error en eliminacion de barrio: "
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
