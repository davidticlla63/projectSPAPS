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

import com.teds.spaps.interfaces.dao.IPaisDao;
import com.teds.spaps.model.Pais;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

/**
 * @author ANITA
 *
 */
@ManagedBean(name = "paisController")
@ViewScoped
public class PaisController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -125210622622704977L;
	/******* DAO **********/
	private @Inject IPaisDao paisDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private Pais pais;
	private Pais paisSelected;
	private Sucursal sucursal;

	/******* LIST **********/
	private List<Pais> listaPais;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;
	private boolean seleccionado = false;

	/**
	 * 
	 */
	public PaisController() {
	}

	public Pais getPais() {
		return pais;
	}

	public Pais getPaisSelected() {
		return paisSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<Pais> getListaPais() {
		return listaPais;
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

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public void setPaisSelected(Pais paisSelected) {
		this.paisSelected = paisSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaPais(List<Pais> listaPais) {
		this.listaPais = listaPais;
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
		pais = new Pais();
		paisSelected = new Pais();
		listaPais = paisDao.obtenerPorCompania(sucursal.getCompania());
	}

	public void registrar() {
		try {
			System.out.println("Ingreso a registrar");

			if (pais.getNombre().trim().isEmpty()
					|| pais.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			}
			pais.setCompania(sucursal.getCompania());
			pais.setFechaRegistro(new Date());
			pais.setFechaModificacion(pais.getFechaRegistro());
			pais.setUsuarioRegistro(sessionMain.getUsuarioLogin().getLogin());

			Pais r = paisDao.registrar(pais);
			if (r != null) {
				defaultValues();
			} else {
				defaultValues();
			}
		} catch (Exception e) {
			System.out.println("Error en registro de pais: " + e.getMessage());
		}

	}

	public void actualizar() {
		try {
			System.out.println("Ingreso a actualizar");
			if (pais.getNombre().trim().isEmpty()
					|| pais.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				pais.setCompania(getSucursal().getCompania());
				pais.setFechaModificacion(new Date());
				pais.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getLogin());
				Pais r = paisDao.modificar(pais);
				if (r != null) {
					FacesUtil.infoMessage("Pais actualizado", r.toString());
					defaultValues();
				} else {
					FacesUtil.errorMessage("Error al actualizar");
					defaultValues();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de pais: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			if (paisDao.eliminar(pais)) {
				FacesUtil.infoMessage("Pais Eliminado", pais.toString());
				defaultValues();
			} else {
				FacesUtil.errorMessage("Error al eliminar");
				defaultValues();
			}
		} catch (Exception e) {
			System.out.println("Error en eliminacion de pais: "
					+ e.getMessage());
		}
	}

	public void onRowSelect(SelectEvent event) {
		pais = paisSelected;
		crear = false;
		seleccionado = true;
		registrar = false;
	}

	public void actionNuevo() {
		System.out.println("Ingreso a actionNuevo...");
		crear = false;
		seleccionado = false;
		registrar = true;
		pais = new Pais();
		pais.setFechaRegistro(new Date());
	}

	public void actionCancelar() {
		crear = true;
		seleccionado = false;
		registrar = false;
		pais = new Pais();
	}

	public boolean isSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

}
