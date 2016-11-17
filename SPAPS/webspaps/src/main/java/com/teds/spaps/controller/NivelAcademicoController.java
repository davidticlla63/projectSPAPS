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

import com.teds.spaps.interfaces.dao.INivelAcademicoDao;
import com.teds.spaps.model.NivelAcademico;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.Usuario;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

@ManagedBean(name = "nivelAcademicoController")
@ViewScoped
/**
 * @author ANITA
 *
 */
public class NivelAcademicoController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1510151773858646733L;

	/******* DAO **********/
	private @Inject INivelAcademicoDao nivelAcademicoDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private NivelAcademico nivelAcademico;
	private NivelAcademico nivelAcademicoSelected;
	private Usuario usuario;
	private Sucursal sucursal;

	/******* LIST **********/
	private List<NivelAcademico> listaNivelAcademico;
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
	public NivelAcademicoController() {
	}

	public NivelAcademico getNivelAcademico() {
		return nivelAcademico;
	}

	public NivelAcademico getNivelAcademicoSelected() {
		return nivelAcademicoSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<NivelAcademico> getListaNivelAcademico() {
		return listaNivelAcademico;
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

	public void setNivelAcademico(NivelAcademico nivelAcademico) {
		this.nivelAcademico = nivelAcademico;
	}

	public void setNivelAcademicoSelected(NivelAcademico nivelAcademicoSelected) {
		this.nivelAcademicoSelected = nivelAcademicoSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaNivelAcademico(List<NivelAcademico> listaNivelAcademico) {
		this.listaNivelAcademico = listaNivelAcademico;
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

	public void actionNuevo() {
		crear = false;
		modificar = false;
		registrar = true;
	}

	public void actionCancelar() {
		crear = true;
		registrar = false;
		nivelAcademico = new NivelAcademico();
		nivelAcademicoSelected = new NivelAcademico();
	}

	@PostConstruct
	public void initNew() {
		nivelAcademico = new NivelAcademico();
		nivelAcademicoSelected = new NivelAcademico();
		listaNivelAcademico = nivelAcademicoDao
				.obtenerNivelAcademicoOrdenAscPorId();
		usuario = sessionMain.getUsuarioLogin();
		sucursal = sessionMain.PruebaSucursal();
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
		nivelAcademico = nivelAcademicoSelected;
		estado = nivelAcademico.getEstado();
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
		resetearFitrosTabla("formTableNivelAcademico:dataTableNivelAcademico");
	}

	public void resetearFitrosTabla(String id) {
		DataTable table = (DataTable) FacesContext.getCurrentInstance()
				.getViewRoot().findComponent(id);
		table.setSelection(null);
		table.reset();
	}

	public void registrar() {
		try {
			if (nivelAcademico.getDescripcion().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				nivelAcademico.setSucursal(getSucursal());
				nivelAcademico.setCompania(getSucursal().getCompania());
				nivelAcademico.setFechaRegistro(new Date());
				nivelAcademico.setFechaModificacion(nivelAcademico
						.getFechaRegistro());
				nivelAcademico.setUsuarioRegistro(getUsuario().getLogin());
				nivelAcademicoDao.registrar(nivelAcademico);
				initNew();
			}
		} catch (Exception e) {
			System.out.println("Error en registro de nivelAcademico: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			if (nivelAcademico.getDescripcion().trim().isEmpty()
					|| getEstado().trim().isEmpty() || getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				nivelAcademico.setFechaModificacion(new Date());
				nivelAcademico.setUsuarioRegistro(getUsuario().getLogin());
				if (getEstado().equals("ACTIVO") || getEstado().equals("AC")) {
					nivelAcademico.setEstado("AC");
				} else {
					if (getEstado().equals("INACTIVO")
							|| getEstado().equals("IN")) {
						nivelAcademico.setEstado("IN");
					} else {
						if (getEstado().equals("ELIMINADO")
								|| getEstado().equals("RM")) {
							nivelAcademico.setEstado("RM");
						}
					}
				}
				nivelAcademicoDao.modificar(nivelAcademico);
				initNew();
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de nivelAcademico: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			nivelAcademicoDao.eliminar(nivelAcademico);
			initNew();
		} catch (Exception e) {
			System.out.println("Error en eliminacion de nivelAcademico: "
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
