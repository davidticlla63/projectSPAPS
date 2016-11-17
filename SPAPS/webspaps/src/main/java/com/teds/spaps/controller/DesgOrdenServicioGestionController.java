/**
 * @author ANITA
 */
package com.teds.spaps.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.SelectEvent;

import com.teds.spaps.interfaces.dao.IDesgOrdenServicioDao;
import com.teds.spaps.model.DesgOrdenServicio;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.Usuario;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

/**
 * @author ANITA
 *
 */
@ManagedBean(name = "desgOrdenServicioGestionController")
@ViewScoped
public class DesgOrdenServicioGestionController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9185930552200126503L;
	/******* DAO **********/
	private @Inject IDesgOrdenServicioDao desgOrdenServicioDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private DesgOrdenServicio desgOrdenServicio;
	private DesgOrdenServicio desgOrdenServicioSelected;
	private Sucursal sucursal;
	private Usuario usuario;

	/******* LIST **********/
	private List<DesgOrdenServicio> listaDesgOrdenServicio;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;
	private boolean ordenSeleccionada = false;
	private boolean seeEstudio = false;
	private boolean resetTablesS = true;
	private String tipoBusqueda = "";
	private String pagina = "";
	private String currentPage1 = "/pages/desgravamen/desgravamen_laboratorios.xhtml";
	private String busqueda;

	@Inject
	Conversation conversation;

	@PostConstruct
	public void initNew() {
		desgOrdenServicio = new DesgOrdenServicio();
		desgOrdenServicioSelected = new DesgOrdenServicio();
		usuario = sessionMain.getUsuarioLogin();
		sucursal = sessionMain.PruebaSucursal();
		listaDesgOrdenServicio = desgOrdenServicioDao.obtenerAll();
		modificar = false;
		registrar = false;
		crear = true;
		ordenSeleccionada = false;
		seeEstudio = false;
		resetTablesS = true;
		tipoBusqueda = "";
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
		ordenSeleccionada = true;
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
			if (getSucursal() == null || getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {

				initNew();
			}
		} catch (Exception e) {
			System.out.println("Error en registro de desgOrdenServicio: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			if (desgOrdenServicio.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {

				initNew();
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de desgOrdenServicio: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {

			initNew();
		} catch (Exception e) {
			System.out.println("Error en eliminacion de desgOrdenServicio: "
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

	public String getTipoBusqueda() {
		return tipoBusqueda;
	}

	public void setTipoBusqueda(String tipoBusqueda) {
		this.tipoBusqueda = tipoBusqueda;
	}

	/**
	 * 
	 */
	public DesgOrdenServicioGestionController() {
	}

	public DesgOrdenServicio getDesgOrdenServicio() {
		return desgOrdenServicio;
	}

	public DesgOrdenServicio getDesgOrdenServicioSelected() {
		return desgOrdenServicioSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<DesgOrdenServicio> getListaDesgOrdenServicio() {
		return listaDesgOrdenServicio;
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

	public void setDesgOrdenServicio(DesgOrdenServicio desgOrdenServicio) {
		this.desgOrdenServicio = desgOrdenServicio;
	}

	public void setDesgOrdenServicioSelected(
			DesgOrdenServicio desgOrdenServicioSelected) {
		this.desgOrdenServicioSelected = desgOrdenServicioSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaDesgOrdenServicio(
			List<DesgOrdenServicio> listaDesgOrdenServicio) {
		this.listaDesgOrdenServicio = listaDesgOrdenServicio;
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

	public String getPagina() {
		return pagina;
	}

	public String getCurrentPage1() {
		return currentPage1;
	}

	public void setPagina(String pagina) {
		this.pagina = pagina;
	}

	public void setCurrentPage1(String currentPage1) {
		this.currentPage1 = currentPage1;
	}

	public boolean isOrdenSeleccionada() {
		return ordenSeleccionada;
	}

	public void setOrdenSeleccionada(boolean ordenSeleccionada) {
		this.ordenSeleccionada = ordenSeleccionada;
	}

	public boolean isSeeEstudio() {
		return seeEstudio;
	}

	public void setSeeEstudio(boolean seeEstudio) {
		this.seeEstudio = seeEstudio;
	}

	public String getBusqueda() {
		return busqueda;
	}

	public boolean isResetTablesS() {
		return resetTablesS;
	}

	public void setResetTablesS(boolean resetTablesS) {
		this.resetTablesS = resetTablesS;
	}

}
