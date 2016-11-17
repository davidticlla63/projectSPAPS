/**
 * 
 */
package com.teds.spaps.controller;

import java.io.Serializable;
import java.util.ArrayList;
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

import com.teds.spaps.interfaces.dao.IDocumentoIdentidadDao;
import com.teds.spaps.model.DocumentoIdentidad;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.Usuario;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

@ManagedBean(name = "documentoIdentidadController")
@ViewScoped
/**
 * @author Cinthia Zabala
 *
 */
public class DocumentoIdentidadController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6031966114388889680L;

	/******* DAO **********/
	private @Inject IDocumentoIdentidadDao documentoIdentidadDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private DocumentoIdentidad documentoIdentidad;
	private DocumentoIdentidad documentoIdentidadSelected;
	private Sucursal sucursalLogin;
	private Usuario usuario;

	/******* LIST **********/
	private List<DocumentoIdentidad> listaDocumentoIdentidad = new ArrayList<DocumentoIdentidad>();
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
	public DocumentoIdentidadController() {
	}

	public DocumentoIdentidad getDocumentoIdentidad() {
		return documentoIdentidad;
	}

	public void setDocumentoIdentidad(DocumentoIdentidad documentoIdentidad) {
		this.documentoIdentidad = documentoIdentidad;
	}

	public DocumentoIdentidad getDocumentoIdentidadSelected() {
		return documentoIdentidadSelected;
	}

	public void setDocumentoIdentidadSelected(
			DocumentoIdentidad documentoIdentidadSelected) {
		this.documentoIdentidadSelected = documentoIdentidadSelected;
	}

	public List<DocumentoIdentidad> getListaDocumentoIdentidad() {
		return listaDocumentoIdentidad;
	}

	public void setListaDocumentoIdentidad(
			List<DocumentoIdentidad> listaDocumentoIdentidad) {
		this.listaDocumentoIdentidad = listaDocumentoIdentidad;
	}

	public boolean isModificar() {
		return modificar;
	}

	public void setModificar(boolean modificar) {
		this.modificar = modificar;
	}

	public boolean isRegistrar() {
		return registrar;
	}

	public void setRegistrar(boolean registrar) {
		this.registrar = registrar;
	}

	public boolean isCrear() {
		return crear;
	}

	public void setCrear(boolean crear) {
		this.crear = crear;
	}

	public Sucursal getSucursalLogin() {
		return sucursalLogin;
	}

	public void setSucursalLogin(Sucursal sucursalLogin) {
		this.sucursalLogin = sucursalLogin;
	}

	public String[] getListaEstado() {
		return listaEstado;
	}

	public String getEstado() {
		return estado;
	}

	public void setListaEstado(String[] listaEstado) {
		this.listaEstado = listaEstado;
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

	@PostConstruct
	public void initNew() {
		usuario = sessionMain.getUsuarioLogin();
		sucursalLogin = sessionMain.PruebaSucursal();
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
		documentoIdentidad = new DocumentoIdentidad();
		documentoIdentidadSelected = new DocumentoIdentidad();
		listaDocumentoIdentidad = documentoIdentidadDao
				.obtenerPorCompania(sucursalLogin.getCompania());
	}

	public void Init() {
		documentoIdentidad = new DocumentoIdentidad();
		documentoIdentidadSelected = new DocumentoIdentidad();
		listaDocumentoIdentidad = documentoIdentidadDao
				.obtenerDocumentoIdentidadOrdenAscPorId();
		// sucursalLogin = sessionMain.getSucursalLogin();
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
		// documentoIdentidad = documentoIdentidadSelected;
		// estado = documentoIdentidad.getEstado();
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
		if (documentoIdentidadDao.verificarRepetido(documentoIdentidad,
				sucursalLogin.getCompania())) {
			FacesUtil.infoMessage("VALIDACIÓN", "Ya existe el registro.");
		} else {
			try {
				if (documentoIdentidad.getNombre().trim().isEmpty()
						|| documentoIdentidad.getSigla().trim().isEmpty()
						|| getSucursalLogin() == null
						|| getSucursalLogin().getCompania() == null) {
					FacesUtil.infoMessage("VALIDACION",
							"No puede haber campos vacíos");
					return;
				} else {
					documentoIdentidad.setSucursal(getSucursalLogin());
					documentoIdentidad.setCompania(getSucursalLogin()
							.getCompania());
					documentoIdentidad.setFechaRegistro(new Date());
					documentoIdentidad.setUsuarioRegistro(sessionMain
							.getUsuarioLogin().getLogin());
					documentoIdentidad.setEstado("AC");
					documentoIdentidad.setFechaRegistro(new Date());
					documentoIdentidad.setFechaModificacion(documentoIdentidad
							.getFechaRegistro());
					documentoIdentidadDao.registrar(documentoIdentidad);
					initNew();
				}
			} catch (Exception e) {
				System.out.println("Error en registro de documentoIdentidad: "
						+ e.getMessage());
			}
		}
	}

	public void actualizar() {
		if (documentoIdentidadDao.verificarRepetidoUpdate(documentoIdentidad,
				sucursalLogin.getCompania())) {
			FacesUtil.infoMessage("VALIDACIÓN", "Ya existe el registro.");
		} else {
			try {
				if (documentoIdentidad.getNombre().trim().isEmpty()
						|| documentoIdentidad.getSigla().trim().isEmpty()
						|| getEstado().trim().isEmpty()) {
					FacesUtil.infoMessage("VALIDACION",
							"No puede haber campos vacíos");
					return;
				} else {
					documentoIdentidad.setFechaModificacion(new Date());
					documentoIdentidad.setUsuarioRegistro(getUsuario()
							.getLogin());
					if (getEstado().equals("ACTIVO")
							|| getEstado().equals("AC")) {
						documentoIdentidad.setEstado("AC");
					} else {
						if (getEstado().equals("INACTIVO")
								|| getEstado().equals("IN")) {
							documentoIdentidad.setEstado("IN");
						} else {
							if (getEstado().equals("ELIMINADO")
									|| getEstado().equals("RM")) {
								documentoIdentidad.setEstado("RM");
							}
						}
					}
					documentoIdentidadDao.modificar(documentoIdentidad);
					initNew();
				}
			} catch (Exception e) {
				System.out
						.println("Error en modificacion de documentoIdentidad: "
								+ e.getMessage());
			}
		}
	}

	public void eliminar() {
		try {
			documentoIdentidadDao.eliminar(documentoIdentidad);
			initNew();
		} catch (Exception e) {
			System.out.println("Error en eliminacion de documentoIdentidad: "
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
