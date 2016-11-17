/**
 * 
 */
package com.teds.spaps.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

import com.teds.spaps.interfaces.dao.ITesterFileDao;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.TesterFile;
import com.teds.spaps.model.Usuario;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

@ManagedBean(name = "testerController")
@ViewScoped
public class TesterController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -971286738405365006L;
	/******* DAO **********/
	private @Inject ITesterFileDao testerFileDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private TesterFile testerFile;
	private TesterFile testerFileSelected;
	private Sucursal sucursal;
	private Usuario usuario;
	private UploadedFile file;

	/******* LIST **********/
	private List<TesterFile> listaTesterFile;
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
	public TesterController() {
	}

	public TesterFile getTesterFile() {
		return testerFile;
	}

	public TesterFile getTesterFileSelected() {
		return testerFileSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<TesterFile> getListaTesterFile() {
		return listaTesterFile;
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

	public void setTesterFile(TesterFile testerFile) {
		this.testerFile = testerFile;
	}

	public void setTesterFileSelected(TesterFile testerFileSelected) {
		this.testerFileSelected = testerFileSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaTesterFile(List<TesterFile> listaTesterFile) {
		this.listaTesterFile = listaTesterFile;
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

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	@PostConstruct
	public void initNew() {
		testerFile = new TesterFile();
		testerFileSelected = new TesterFile();
		usuario = sessionMain.getUsuarioLogin();
		sucursal = sessionMain.PruebaSucursal();
		listaTesterFile = testerFileDao.obtenerTesterFileOrdenAscPorId();
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
	}

	public void prueba() {
		System.out.println("entro a prueba");
	}

	public void handleFileUpload(FileUploadEvent event) {
		setFile(event.getFile());
		System.out.println("file = " + getFile().getFileName() + ", "
				+ getFile().getSize());
		FacesUtil.infoMessage("Exito", event.getFile().getFileName()
				+ " is uploaded.");
	}

	@SuppressWarnings({ "unused", "resource" })
	public byte[] CargarFile() {
		try {
			File f = new File("D:/Imagen006.jpg"); // asociamos el archivo
													// fisico
			InputStream is = new FileInputStream(f); // lo abrimos. Lo
														// importante es que sea
														// un InputStream
			byte[] buffer = new byte[(int) f.length()]; // creamos el buffer
			int readers = is.read(buffer);
			return buffer;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} // leemos el archivo al buffer

	}

	public void actionNuevo() {
		System.out.println("Ingreso a actionNuevo...tester");
		System.out.println("user = " + getUsuario().toString());
		System.out.println("sucursal = " + getSucursal().toString());
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
		testerFile = testerFileSelected;
		// estado = testerFile.getEstado();
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

	public void add() {
		System.out.println("entro a registrar");
		testerFile.setFile(file.getContents());
		System.out.println("nombre de archivo = " + file.getFileName() + " "
				+ file.getSize());
		testerFileDao.registrar(testerFile);
		initNew();
		if (testerFile.getNombre().trim().isEmpty() || getSucursal() == null
				|| getSucursal().getCompania() == null) {
			FacesUtil.infoMessage("VALIDACION", "No puede haber campos vacíos");
			return;
		} else {
			System.out.println("entro a registrar");
			testerFile.setFile(file.getContents());
			System.out.println("nombre de archivo = " + file.getFileName()
					+ " " + file.getSize());
			testerFileDao.registrar(testerFile);
			initNew();
		}
	}

	public void registrar1() {
		try {
			if (testerFile.getNombre().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				System.out.println("entro a registrar");
				testerFile.setFile(file.getContents());
				System.out.println("nombre de archivo = " + file.getFileName()
						+ " " + file.getSize());
				testerFileDao.registrar(testerFile);
				initNew();
			}
		} catch (Exception e) {
			System.out.println("Error en registro de testerFile: "
					+ e.getMessage());
		}

	}

	/*
	 * public void actualizar() { try { if
	 * (testerFile.getNombre().trim().isEmpty() || getEstado().trim().isEmpty()
	 * || getSucursal() == null || getSucursal().getCompania() == null) {
	 * FacesUtil.infoMessage("VALIDACION", "No puede haber campos vacíos");
	 * return; } else { if (getEstado().equals("ACTIVO") ||
	 * getEstado().equals("AC")) { testerFile.setEstado("AC"); } else { if
	 * (getEstado().equals("INACTIVO") || getEstado().equals("IN")) {
	 * testerFile.setEstado("IN"); } else { if (getEstado().equals("ELIMINADO")
	 * || getEstado().equals("RM")) { testerFile.setEstado("RM"); } } }
	 * testerFile.setFechaModificacion(new Date());
	 * testerFile.setUsuarioRegistro(sessionMain.getUsuarioLogin() .getId());
	 * testerFileDao.modificar(testerFile); initNew(); } } catch (Exception e) {
	 * System.out.println("Error en modificacion de testerFile: " +
	 * e.getMessage()); }
	 * 
	 * }
	 */

	public void eliminar() {
		try {
			testerFileDao.eliminar(getTesterFile());
			initNew();
		} catch (Exception e) {
			System.out.println("Error en eliminacion de testerFile: "
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
