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

import com.teds.spaps.interfaces.dao.ITipoExamenDao;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.TipoExamen;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

/**
 * @author ANITA
 *
 */
@ManagedBean(name = "tipoExamenController")
@ViewScoped
public class TipoExamenController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -125210622622704977L;
	/******* DAO **********/
	private @Inject ITipoExamenDao tipoExamenDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private TipoExamen tipoExamen;
	private TipoExamen tipoExamenSelected;
	private Sucursal sucursal;

	/******* LIST **********/
	private List<TipoExamen> listaTipoExamen;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;
	private boolean seleccionado = false;

	/**
	 * 
	 */
	public TipoExamenController() {
	}

	public TipoExamen getTipoExamen() {
		return tipoExamen;
	}

	public TipoExamen getTipoExamenSelected() {
		return tipoExamenSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<TipoExamen> getListaTipoExamen() {
		return listaTipoExamen;
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

	public void setTipoExamen(TipoExamen tipoExamen) {
		this.tipoExamen = tipoExamen;
	}

	public void setTipoExamenSelected(TipoExamen tipoExamenSelected) {
		this.tipoExamenSelected = tipoExamenSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaTipoExamen(List<TipoExamen> listaTipoExamen) {
		this.listaTipoExamen = listaTipoExamen;
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
		tipoExamen = new TipoExamen();
		tipoExamenSelected = new TipoExamen();
		listaTipoExamen = tipoExamenDao.obtenerPorSucursal(sucursal);
	}

	public void registrar() {
		try {
			System.out.println("Ingreso a registrar");

			if (tipoExamen.getNombre().trim().isEmpty()
					|| tipoExamen.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			}
			tipoExamen.setSucursal(sucursal);
			tipoExamen.setFechaRegistro(new Date());
			tipoExamen.setFechaModificacion(tipoExamen.getFechaRegistro());
			tipoExamen.setUsuarioRegistro(sessionMain.getUsuarioLogin()
					.getLogin());

			TipoExamen r = tipoExamenDao.registrar(tipoExamen);
			if (r != null) {
				FacesUtil.infoMessage("TipoExamen registrado", r.toString());
				defaultValues();
			} else {
				FacesUtil.errorMessage("Error al registrar");
				defaultValues();
			}
		} catch (Exception e) {
			System.out.println("Error en registro de tipoExamen: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			System.out.println("Ingreso a actualizar");
			if (tipoExamen.getNombre().trim().isEmpty()
					|| tipoExamen.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				tipoExamen.setSucursal(getSucursal());
				tipoExamen.setFechaModificacion(new Date());
				tipoExamen.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getLogin());
				TipoExamen r = tipoExamenDao.modificar(tipoExamen);
				if (r != null) {
					FacesUtil.infoMessage("TipoExamen actualizado",
							r.toString());
					defaultValues();
				} else {
					FacesUtil.errorMessage("Error al actualizar");
					defaultValues();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de tipoExamen: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			if (tipoExamenDao.eliminar(tipoExamen)) {
				FacesUtil.infoMessage("TipoExamen Eliminado",
						tipoExamen.toString());
				defaultValues();
			} else {
				FacesUtil.errorMessage("Error al eliminar");
				defaultValues();
			}
		} catch (Exception e) {
			System.out.println("Error en eliminacion de tipoExamen: "
					+ e.getMessage());
		}
	}

	public void onRowSelect(SelectEvent event) {
		tipoExamen = tipoExamenSelected;
		crear = false;
		seleccionado = true;
		registrar = false;
	}

	public void actionNuevo() {
		System.out.println("Ingreso a actionNuevo...");
		crear = false;
		seleccionado = false;
		registrar = true;
		tipoExamen = new TipoExamen();
		tipoExamen.setFechaRegistro(new Date());
	}

	public void actionCancelar() {
		crear = true;
		seleccionado = false;
		registrar = false;
		tipoExamen = new TipoExamen();
	}

	public boolean isSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

}
