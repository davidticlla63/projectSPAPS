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

import com.teds.spaps.interfaces.dao.ISeguroDao;
import com.teds.spaps.model.Seguro;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

/**
 * @author ANITA
 *
 */
@ManagedBean(name = "seguroController")
@ViewScoped
public class SeguroController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -125210622622704977L;
	/******* DAO **********/
	private @Inject ISeguroDao seguroDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private Seguro seguro;
	private Seguro seguroSelected;
	private Sucursal sucursal;

	/******* LIST **********/
	private List<Seguro> listaSeguro;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;
	private boolean seleccionado = false;

	@PostConstruct
	public void initNew() {
		sucursal = sessionMain.PruebaSucursal();
		System.out.println("Sucursal : " + sucursal.getDescripcion());
		System.out.println("Compañia : "
				+ sucursal.getCompania().getDescripcion());
		defaultValues();
	}

	private void defaultValues() {
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
		seleccionado = false;
		seguro = new Seguro();
		seguroSelected = new Seguro();
		listaSeguro = seguroDao.obtenerPorCompania(sucursal.getCompania());
	}

	public void registrar() {
		try {
			System.out.println("Ingreso a registrar");

			if (seguro.getNombre().trim().isEmpty()
					|| seguro.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			}
			seguro.setCompania(sucursal.getCompania());
			seguro.setSucursal(sucursal);
			seguro.setFechaRegistro(new Date());
			seguro.setFechaModificacion(seguro.getFechaRegistro());
			seguro.setUsuarioRegistro(sessionMain.getUsuarioLogin().getLogin());

			Seguro r = seguroDao.registrar(seguro);
			if (r != null) {
				FacesUtil.infoMessage("Seguro registrado", r.toString());
				defaultValues();
			} else {
				FacesUtil.errorMessage("Error al registrar");
				defaultValues();
			}
		} catch (Exception e) {
			System.out
					.println("Error en registro de seguro: " + e.getMessage());
		}

	}

	public void actualizar() {
		try {
			System.out.println("Ingreso a actualizar");
			if (seguro.getNombre().trim().isEmpty()
					|| seguro.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				seguro.setCompania(getSucursal().getCompania());
				seguro.setSucursal(getSucursal());
				seguro.setFechaModificacion(new Date());
				seguro.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getLogin());
				Seguro r = seguroDao.modificar(seguro);
				if (r != null) {
					FacesUtil.infoMessage("Seguro actualizado", r.toString());
					defaultValues();
				} else {
					FacesUtil.errorMessage("Error al actualizar");
					defaultValues();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de seguro: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			if (seguroDao.eliminar(seguro)) {
				FacesUtil.infoMessage("Seguro Eliminado", seguro.toString());
				defaultValues();
			} else {
				FacesUtil.errorMessage("Error al eliminar");
				defaultValues();
			}
		} catch (Exception e) {
			System.out.println("Error en eliminacion de seguro: "
					+ e.getMessage());
		}
	}

	public void onRowSelect(SelectEvent event) {
		seguro = seguroSelected;
		crear = false;
		seleccionado = true;
		registrar = false;
	}

	public void actionNuevo() {
		System.out.println("Ingreso a actionNuevo...");
		crear = false;
		seleccionado = false;
		registrar = true;
		seguro = new Seguro();
		seguro.setFechaRegistro(new Date());
	}

	public void actionCancelar() {
		crear = true;
		seleccionado = false;
		registrar = false;
		seguro = new Seguro();
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
	public SeguroController() {
	}

	public Seguro getSeguro() {
		return seguro;
	}

	public Seguro getSeguroSelected() {
		return seguroSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<Seguro> getListaSeguro() {
		return listaSeguro;
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

	public void setSeguro(Seguro seguro) {
		this.seguro = seguro;
	}

	public void setSeguroSelected(Seguro seguroSelected) {
		this.seguroSelected = seguroSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaSeguro(List<Seguro> listaSeguro) {
		this.listaSeguro = listaSeguro;
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
