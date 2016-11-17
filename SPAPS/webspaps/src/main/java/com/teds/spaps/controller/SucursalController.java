package com.teds.spaps.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import com.teds.spaps.interfaces.dao.IPaisDao;
import com.teds.spaps.interfaces.dao.ISucursalDao;
import com.teds.spaps.interfaces.dao.IUsuarioDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Pais;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.Usuario;
import com.teds.spaps.model.UsuarioSucursal;
import com.teds.spaps.util.SessionMain;

/**
 * @author mauriciobejaranorivera
 *
 */
@ManagedBean(name = "sucursalController")
@ViewScoped
public class SucursalController implements Serializable {

	private static final long serialVersionUID = 844192813700641693L;

	// DAO
	private @Inject ISucursalDao sucursalDao;
	private @Inject SessionMain sessionMain;
	private @Inject IPaisDao paisDao;
	private @Inject IUsuarioDao usuarioDao;

	// OBJECT
	private Sucursal sucursal;
	private Compania compania;
	private Pais pais = new Pais();

	// LIST
	private List<Sucursal> listaSucursal;
	private List<Pais> listPais = new ArrayList<Pais>();

	// VAR
	private String currentPage = "/pages/param/sucursal/list.xhtml";

	// ESTADOS
	private boolean nuevo;
	private boolean registrar;
	private boolean seleccionado;

	public SucursalController() {
	}

	@PostConstruct
	public void initNew() {
		compania = sessionMain.getCompania();
		defaultValues();
	}

	private void defaultValues() {
		nuevo = true;
		registrar = false;
		seleccionado = false;

		sucursal = new Sucursal();
		listaSucursal = sucursalDao.obtenerPorCompania(sessionMain
				.PruebaSucursal().getCompania());
		listPais = paisDao.obtenerPaisOrdenAscPorId();
	}

	public void selectedPais() {
		pais = paisDao.obtenerPais(pais.getId());
	}

	// ACTION

	public void onRowSelect(SelectEvent event) {
		nuevo = false;
		seleccionado = true;
		registrar = false;
		pais = sucursal.getCiudad().getPais();
		currentPage = "/pages/param/sucursal/edit.xhtml";
	}

	public void actionNuevo() {
		nuevo = false;
		seleccionado = false;
		registrar = true;

		currentPage = "/pages/param/sucursal/edit.xhtml";
	}

	public void actionCancelar() {
		nuevo = true;
		seleccionado = false;
		registrar = false;

		sucursal = new Sucursal();
		currentPage = "/pages/param/sucursal/list.xhtml";
	}

	// PRODCESO

	public void registrarSucursal() {
		sucursal.setCompania(compania);
		sucursal.setFechaRegistro(new Date());
		sucursal.setUsuarioRegistro(sessionMain.getUsuarioLogin().getLogin());
		sucursal.setTipo("N");// NORMAL
		Usuario usuario = usuarioDao.obtenerPorRol("ADMIN", compania);
		if (usuario != null) {
			sucursal.getListUsuarioSucursals().add(new UsuarioSucursal(usuario, sucursal, "AC", sucursal.getFechaRegistro(), sucursal.getUsuarioRegistro()));
		}
//		Usuario usuario1 = usuarioDao.obtenerPorRol("SUPER", compania);
//		if (usuario1 != null) {
//			sucursal.getListUsuarioSucursals().add(new UsuarioSucursal(usuario1, sucursal, "AC", sucursal.getFechaRegistro(), sucursal.getUsuarioRegistro()));
//		}
		Sucursal r = sucursalDao.registrar(sucursal);
		if (r != null) {
			defaultValues();
			currentPage = "/pages/param/sucursal/list.xhtml";
		}
	}

	public void modificarSucursal() {
		sucursal.setFechaModificacion(new Date());
		Sucursal r = sucursalDao.modificar(sucursal);
		if (r != null) {
			defaultValues();
			currentPage = "/pages/param/sucursal/list.xhtml";
		}
	}

	public void eliminarSucursal() {
		if (sucursalDao.eliminar(sucursal)) {
			defaultValues();
			currentPage = "/pages/param/sucursal/list.xhtml";
		}
	}

	// GET AND SETTER

	public boolean isSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public boolean isRegistrar() {
		return registrar;
	}

	public void setRegistrar(boolean registrar) {
		this.registrar = registrar;
	}

	public void setListaSucursal(List<Sucursal> listaSucursal) {
		this.listaSucursal = listaSucursal;
	}

	public List<Sucursal> getListaSucursal() {
		return listaSucursal;
	}

	public Compania getCompania() {
		return compania;
	}

	public void setCompania(Compania compania) {
		this.compania = compania;
	}

	public String getPage() {
		return currentPage;
	}

	public void setPage(String currentPage) {
		this.currentPage = currentPage;
	}

	public boolean isNuevo() {
		return nuevo;
	}

	public void setNuevo(boolean nuevo) {
		this.nuevo = nuevo;
	}

	/**
	 * @return the pais
	 */
	public Pais getPais() {
		return pais;
	}

	/**
	 * @param pais
	 *            the pais to set
	 */
	public void setPais(Pais pais) {
		this.pais = pais;
	}

	/**
	 * @return the listPais
	 */
	public List<Pais> getListPais() {
		return listPais;
	}

	/**
	 * @param listPais
	 *            the listPais to set
	 */
	public void setListPais(List<Pais> listPais) {
		this.listPais = listPais;
	}

}
