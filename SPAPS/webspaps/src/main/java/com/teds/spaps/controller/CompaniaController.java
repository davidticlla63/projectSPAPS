/**
 * 
 */
package com.teds.spaps.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import com.teds.spaps.interfaces.dao.ICompaniaDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

/**
 * @author ANITA
 *
 */
@ManagedBean(name = "companiaController")
@ViewScoped
public class CompaniaController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -125210622622704977L;
	/******* DAO **********/
	private @Inject ICompaniaDao companiaDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private Compania compania;
	private Compania companiaSelected;

	/******* LIST **********/
	private List<Compania> listaCompania;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;
	private boolean seleccionado = false;

	@PostConstruct
	public void initNew() {
		defaultValues();
	}

	private void defaultValues() {
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
		seleccionado = false;
		compania = new Compania();
		companiaSelected = new Compania();
		listaCompania = companiaDao.obtenerAllActivos();
	}

	public void registrar() {
		try {
			System.out.println("Ingreso a registrar");

			if (compania.getDescripcion().trim().isEmpty()
					|| compania.getEstado().trim().isEmpty()) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			}
			compania.setFechaRegistro(new Date());
			compania.setFechaModificacion(compania.getFechaRegistro());
			compania.setUsuarioRegistro(sessionMain.getUsuarioLogin()
					.getLogin());

			Compania r = companiaDao.registrar(compania);
			if (r != null) {
				FacesUtil.infoMessage("Compania registrado", r.toString());
				defaultValues();
			} else {
				FacesUtil.errorMessage("Error al registrar");
				defaultValues();
			}
		} catch (Exception e) {
			System.out.println("Error en registro de compania: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			System.out.println("Ingreso a actualizar");
			if (compania.getDescripcion().trim().isEmpty()
					|| compania.getEstado().trim().isEmpty()) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				compania.setFechaModificacion(new Date());
				compania.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getLogin());
				Compania r = companiaDao.modificar(compania);
				if (r != null) {
					FacesUtil.infoMessage("Compania actualizado", r.toString());
					defaultValues();
				} else {
					FacesUtil.errorMessage("Error al actualizar");
					defaultValues();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de compania: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			if (companiaDao.eliminar(compania)) {
				FacesUtil
						.infoMessage("Compania Eliminado", compania.toString());
				defaultValues();
			} else {
				FacesUtil.errorMessage("Error al eliminar");
				defaultValues();
			}
		} catch (Exception e) {
			System.out.println("Error en eliminacion de compania: "
					+ e.getMessage());
		}
	}

	public void onRowSelect(SelectEvent event) {
		compania = companiaSelected;
		crear = false;
		seleccionado = true;
		registrar = false;
	}

	public void actionNuevo() {
		System.out.println("Ingreso a actionNuevo...");
		crear = false;
		seleccionado = false;
		registrar = true;
		compania = new Compania();
		compania.setFechaRegistro(new Date());
	}

	public void actionCancelar() {
		crear = true;
		seleccionado = false;
		registrar = false;
		compania = new Compania();
	}

	public boolean isSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

	/**
	 * 
	 */
	public CompaniaController() {
	}

	public Compania getCompania() {
		return compania;
	}

	public Compania getCompaniaSelected() {
		return companiaSelected;
	}

	public List<Compania> getListaCompania() {
		return listaCompania;
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

	public void setCompania(Compania compania) {
		this.compania = compania;
	}

	public void setCompaniaSelected(Compania companiaSelected) {
		this.companiaSelected = companiaSelected;
	}

	public void setListaCompania(List<Compania> listaCompania) {
		this.listaCompania = listaCompania;
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

}
