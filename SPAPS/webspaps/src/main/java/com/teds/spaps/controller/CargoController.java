/**
 * 
 */
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

import com.teds.spaps.interfaces.dao.ICargoDao;
import com.teds.spaps.interfaces.dao.IEmpresaDao;
import com.teds.spaps.model.Cargo;
import com.teds.spaps.model.Empresa;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

/**
 * @author ANITA
 *
 */
@ManagedBean(name = "cargoController")
@ViewScoped
public class CargoController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 844192813700641693L;
	/******* DAO **********/
	private @Inject ICargoDao cargoDao;
	private @Inject SessionMain sessionMain;
	private @Inject IEmpresaDao empresaDao;

	/******* OBJECT **********/
	private Cargo cargo;
	private Cargo cargoSelected;
	private Sucursal sucursal;
//	private Empresa selectedEmpresa;

	/******* LIST **********/
	private List<Cargo> listaCargo;
//	private List<Empresa> listaEmpresa = new ArrayList<Empresa>();

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;
	private boolean seleccionado = false;

	/**
	 * 
	 */
	public CargoController() {
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
		cargo = new Cargo();
		cargoSelected = new Cargo();
		listaCargo = cargoDao.obtenerPorCompania(sucursal.getCompania());

	}

	public void registrar() {
		try {
			System.out.println("Ingreso a registrar");

			if (cargo.getDescripcion().trim().isEmpty()
					|| cargo.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			}
			cargo.setCompania(sucursal.getCompania());
//			cargo.setSucursal(sucursal);
			cargo.setFechaRegistro(new Date());
			cargo.setFechaModificacion(cargo.getFechaRegistro());
			cargo.setUsuarioRegistro(sessionMain.getUsuarioLogin().getLogin());
//			cargo.setEmpresa(selectedEmpresa);
			Cargo r = cargoDao.registrar(cargo);
			if (r != null) {
				FacesUtil.infoMessage("Cargo registrado", r.toString());
				defaultValues();
			} else {
				FacesUtil.errorMessage("Error al registrar");
				defaultValues();
			}
		} catch (Exception e) {
			System.out.println("Error en registro de cargo: " + e.getMessage());
		}

	}

	public void actualizar() {
		try {
			System.out.println("Ingreso a actualizar");
			if (cargo.getDescripcion().trim().isEmpty()
					|| cargo.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				cargo.setCompania(getSucursal().getCompania());
//				cargo.setSucursal(getSucursal());
				cargo.setFechaModificacion(new Date());
				cargo.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getLogin());
//				cargo.setEmpresa(selectedEmpresa);
				Cargo r = cargoDao.modificar(cargo);
				if (r != null) {
					FacesUtil.infoMessage("Cargo actualizado", r.toString());
					defaultValues();
				} else {
					FacesUtil.errorMessage("Error al actualizar");
					defaultValues();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de cargo: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			if (cargoDao.eliminar(cargo)) {
				FacesUtil.infoMessage("Cargo Eliminado", cargo.toString());
				defaultValues();
			} else {
				FacesUtil.errorMessage("Error al eliminar");
				defaultValues();
			}
		} catch (Exception e) {
			System.out.println("Error en eliminacion de cargo: "
					+ e.getMessage());
		}
	}

	public void onRowSelect(SelectEvent event) {
		cargo = cargoSelected;
		crear = false;
		seleccionado = true;
		registrar = false;
//		selectedEmpresa = cargo.getEmpresa();
	}

	public void actionNuevo() {
		System.out.println("Ingreso a actionNuevo...");
		crear = false;
		seleccionado = false;
		registrar = true;
		cargo = new Cargo();
		cargo.setFechaRegistro(new Date());
//		selectedEmpresa = new Empresa();
	}

	public void actionCancelar() {
		System.out.println("Ingreso a actionCancelar..");
		crear = true;
		seleccionado = false;
		registrar = false;
		cargo = new Cargo();
//		selectedEmpresa = new Empresa();
	}

	// ONCOMPLETETEXT PAIS
//	public List<Empresa> completeEmpresa(String query) {
//		String upperQuery = query.toUpperCase();
//		List<Empresa> results = new ArrayList<Empresa>();
//		listaEmpresa = empresaDao.obtenerPorCompania(upperQuery,
//				sucursal.getCompania());
//		for (Empresa i : listaEmpresa) {
//			results.add(i);
//		}
//		return results;
//	}
//
//	public void onRowSelectEmpresaClick(SelectEvent event) {
//		String nombre = event.getObject().toString();
//		for (Empresa i : listaEmpresa) {
//			if (i.getDescripcion().equals(nombre)) {
//				selectedEmpresa = i;
//				return;
//			}
//		}
//	}

	public boolean isSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

//	public List<Empresa> getListaEmpresa() {
//		return listaEmpresa;
//	}
//
//	public void setListaEmpresa(List<Empresa> listaEmpresa) {
//		this.listaEmpresa = listaEmpresa;
//	}
//
//	public Empresa getSelectedEmpresa() {
//		return selectedEmpresa;
//	}
//
//	public void setSelectedEmpresa(Empresa selectedEmpresa) {
//		this.selectedEmpresa = selectedEmpresa;
//	}

	public Cargo getCargo() {
		return cargo;
	}

	public Cargo getCargoSelected() {
		return cargoSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<Cargo> getListaCargo() {
		return listaCargo;
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

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public void setCargoSelected(Cargo cargoSelected) {
		this.cargoSelected = cargoSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaCargo(List<Cargo> listaCargo) {
		this.listaCargo = listaCargo;
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
