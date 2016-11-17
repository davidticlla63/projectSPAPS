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

import com.teds.spaps.interfaces.dao.ICargoTrabajoDao;
import com.teds.spaps.interfaces.dao.IEmpresaTrabajoDao;
import com.teds.spaps.model.CargoTrabajo;
import com.teds.spaps.model.EmpresaTrabajo;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

/**
 * @author ANITA
 *
 */
@ManagedBean(name = "cargoTrabajoController")
@ViewScoped
public class CargoTrabajoController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -996346986597770718L;
	/******* DAO **********/
	private @Inject ICargoTrabajoDao cargoTrabajoDao;
	private @Inject SessionMain sessionMain;
	private @Inject IEmpresaTrabajoDao empresaDao;

	/******* OBJECT **********/
	private CargoTrabajo cargoTrabajo;
	private CargoTrabajo cargoTrabajoSelected;
	private Sucursal sucursal;
	private EmpresaTrabajo selectedEmpresa;

	/******* LIST **********/
	private List<CargoTrabajo> listaCargoTrabajo;
	private List<EmpresaTrabajo> listaEmpresa = new ArrayList<EmpresaTrabajo>();

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;
	private boolean seleccionado = false;

	/**
	 * 
	 */
	public CargoTrabajoController() {
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
		cargoTrabajo = new CargoTrabajo();
		cargoTrabajoSelected = new CargoTrabajo();
		listaCargoTrabajo = cargoTrabajoDao.obtenerPorCompania(sucursal
				.getCompania());

	}

	public void registrar() {
		try {
			System.out.println("Ingreso a registrar");

			if (cargoTrabajo.getDescripcion().trim().isEmpty()
					|| cargoTrabajo.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			}
			if (cargoTrabajoDao.verificarRepetido(cargoTrabajo, sucursal)) {
				FacesUtil.errorMessage("Ya existe el cargo para esa empresa.");
			} else {
				cargoTrabajo.setCompania(sucursal.getCompania());
				cargoTrabajo.setSucursal(sucursal);
				cargoTrabajo.setFechaRegistro(new Date());
				cargoTrabajo.setFechaModificacion(cargoTrabajo
						.getFechaRegistro());
				cargoTrabajo.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getLogin());
				cargoTrabajo.setEmpresaTrabajo(selectedEmpresa);
				CargoTrabajo r = cargoTrabajoDao.registrar(cargoTrabajo);
				if (r != null) {
					FacesUtil.infoMessage("CargoTrabajo registrado",
							r.toString());
					defaultValues();
				} else {
					FacesUtil.errorMessage("Error al registrar");
					defaultValues();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en registro de cargoTrabajo: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			System.out.println("Ingreso a actualizar");
			if (cargoTrabajo.getDescripcion().trim().isEmpty()
					|| cargoTrabajo.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				if (cargoTrabajoDao.verificarRepetidoUpdate(cargoTrabajo,
						sucursal)) {
					FacesUtil
							.errorMessage("Ya existe el cargo para esa empresa.");
				} else {
					cargoTrabajo.setCompania(getSucursal().getCompania());
					cargoTrabajo.setSucursal(getSucursal());
					cargoTrabajo.setFechaModificacion(new Date());
					cargoTrabajo.setUsuarioRegistro(sessionMain
							.getUsuarioLogin().getLogin());
					cargoTrabajo.setEmpresaTrabajo(selectedEmpresa);
					CargoTrabajo r = cargoTrabajoDao.modificar(cargoTrabajo);
					if (r != null) {
						FacesUtil.infoMessage("CargoTrabajo actualizado",
								r.toString());
						defaultValues();
					} else {
						FacesUtil.errorMessage("Error al actualizar");
						defaultValues();
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de cargoTrabajo: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			if (cargoTrabajoDao.eliminar(cargoTrabajo)) {
				FacesUtil.infoMessage("CargoTrabajo Eliminado",
						cargoTrabajo.toString());
				defaultValues();
			} else {
				FacesUtil.errorMessage("Error al eliminar");
				defaultValues();
			}
		} catch (Exception e) {
			System.out.println("Error en eliminacion de cargoTrabajo: "
					+ e.getMessage());
		}
	}

	public void onRowSelect(SelectEvent event) {
		cargoTrabajo = cargoTrabajoSelected;
		crear = false;
		seleccionado = true;
		registrar = false;
		selectedEmpresa = cargoTrabajo.getEmpresaTrabajo();
	}

	public void actionNuevo() {
		System.out.println("Ingreso a actionNuevo...");
		crear = false;
		seleccionado = false;
		registrar = true;
		cargoTrabajo = new CargoTrabajo();
		cargoTrabajo.setFechaRegistro(new Date());
		selectedEmpresa = new EmpresaTrabajo();
	}

	public void actionCancelar() {
		System.out.println("Ingreso a actionCancelar..");
		crear = true;
		seleccionado = false;
		registrar = false;
		cargoTrabajo = new CargoTrabajo();
		selectedEmpresa = new EmpresaTrabajo();
	}

	// ONCOMPLETETEXT PAIS
	public List<EmpresaTrabajo> completeEmpresa(String query) {
		String upperQuery = query.toUpperCase();
		List<EmpresaTrabajo> results = new ArrayList<EmpresaTrabajo>();
		listaEmpresa = empresaDao.obtenerPorSucursalAutoComplete(upperQuery,
				sucursal);
		for (EmpresaTrabajo i : listaEmpresa) {
			results.add(i);
		}
		return results;
	}

	public void onRowSelectEmpresaClick(SelectEvent event) {
		String nombre = event.getObject().toString();
		for (EmpresaTrabajo i : listaEmpresa) {
			if (i.getDescripcion().equals(nombre)) {
				selectedEmpresa = i;
				return;
			}
		}
	}

	public boolean isSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

	public List<EmpresaTrabajo> getListaEmpresa() {
		return listaEmpresa;
	}

	public void setListaEmpresa(List<EmpresaTrabajo> listaEmpresa) {
		this.listaEmpresa = listaEmpresa;
	}

	public EmpresaTrabajo getSelectedEmpresa() {
		return selectedEmpresa;
	}

	public void setSelectedEmpresa(EmpresaTrabajo selectedEmpresa) {
		this.selectedEmpresa = selectedEmpresa;
	}

	public CargoTrabajo getCargoTrabajo() {
		return cargoTrabajo;
	}

	public CargoTrabajo getCargoTrabajoSelected() {
		return cargoTrabajoSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<CargoTrabajo> getListaCargoTrabajo() {
		return listaCargoTrabajo;
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

	public void setCargoTrabajo(CargoTrabajo cargoTrabajo) {
		this.cargoTrabajo = cargoTrabajo;
	}

	public void setCargoTrabajoSelected(CargoTrabajo cargoTrabajoSelected) {
		this.cargoTrabajoSelected = cargoTrabajoSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaCargoTrabajo(List<CargoTrabajo> listaCargoTrabajo) {
		this.listaCargoTrabajo = listaCargoTrabajo;
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
