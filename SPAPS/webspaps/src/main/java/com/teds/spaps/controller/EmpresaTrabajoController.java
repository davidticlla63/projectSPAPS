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

import com.teds.spaps.interfaces.dao.IEmpresaTrabajoDao;
import com.teds.spaps.model.EmpresaTrabajo;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

/**
 * @author ANITA
 *
 */
@ManagedBean(name = "empresaTrabajoController")
@ViewScoped
public class EmpresaTrabajoController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -470296293510720674L;
	/******* DAO **********/
	private @Inject IEmpresaTrabajoDao empresaTrabajoDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private EmpresaTrabajo empresaTrabajo;
	private EmpresaTrabajo empresaTrabajoSelected;
	private Sucursal sucursal;

	/******* LIST **********/
	private List<EmpresaTrabajo> listaEmpresaTrabajo;

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
		empresaTrabajo = new EmpresaTrabajo();
		empresaTrabajoSelected = new EmpresaTrabajo();
		listaEmpresaTrabajo = empresaTrabajoDao.obtenerPorCompania(sucursal
				.getCompania());
	}

	public void registrar() {
		try {
			System.out.println("Ingreso a registrar");

			if (empresaTrabajo.getDescripcion().trim().isEmpty()
					|| empresaTrabajo.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			}
			if (empresaTrabajoDao.verificarRepetido(empresaTrabajo, sucursal)) {
				FacesUtil.errorMessage("La empresa ya existe.");
			} else {
				empresaTrabajo.setCompania(sucursal.getCompania());
				empresaTrabajo.setSucursal(sucursal);
				empresaTrabajo.setFechaRegistro(new Date());
				empresaTrabajo.setFechaModificacion(empresaTrabajo
						.getFechaRegistro());
				empresaTrabajo.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getLogin());

				EmpresaTrabajo r = empresaTrabajoDao.registrar(empresaTrabajo);
				if (r != null) {
					FacesUtil.infoMessage("EmpresaTrabajo registrado",
							r.toString());
					defaultValues();
				} else {
					FacesUtil.errorMessage("Error al registrar");
					defaultValues();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en registro de empresaTrabajo: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			System.out.println("Ingreso a actualizar");
			if (empresaTrabajo.getDescripcion().trim().isEmpty()
					|| empresaTrabajo.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				if (empresaTrabajoDao.verificarRepetidoUpdate(empresaTrabajo,
						sucursal)) {
					FacesUtil.errorMessage("Ya existe la empresa");
				} else {
					empresaTrabajo.setCompania(getSucursal().getCompania());
					empresaTrabajo.setSucursal(getSucursal());
					empresaTrabajo.setFechaModificacion(new Date());
					empresaTrabajo.setUsuarioRegistro(sessionMain
							.getUsuarioLogin().getLogin());
					EmpresaTrabajo r = empresaTrabajoDao
							.modificar(empresaTrabajo);
					if (r != null) {
						FacesUtil.infoMessage("EmpresaTrabajo actualizado",
								r.toString());
						defaultValues();
					} else {
						FacesUtil.errorMessage("Error al actualizar");
						defaultValues();
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de empresaTrabajo: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			if (empresaTrabajoDao.eliminar(empresaTrabajo)) {
				FacesUtil.infoMessage("EmpresaTrabajo Eliminado",
						empresaTrabajo.toString());
				defaultValues();
			} else {
				FacesUtil.errorMessage("Error al eliminar");
				defaultValues();
			}
		} catch (Exception e) {
			System.out.println("Error en eliminacion de empresaTrabajo: "
					+ e.getMessage());
		}
	}

	public void onRowSelect(SelectEvent event) {
		empresaTrabajo = empresaTrabajoSelected;
		crear = false;
		seleccionado = true;
		registrar = false;
	}

	public void actionNuevo() {
		System.out.println("Ingreso a actionNuevo...");
		crear = false;
		seleccionado = false;
		registrar = true;
		empresaTrabajo = new EmpresaTrabajo();
		empresaTrabajo.setFechaRegistro(new Date());
	}

	public void actionCancelar() {
		crear = true;
		seleccionado = false;
		registrar = false;
		empresaTrabajo = new EmpresaTrabajo();
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
	public EmpresaTrabajoController() {
	}

	public EmpresaTrabajo getEmpresaTrabajo() {
		return empresaTrabajo;
	}

	public EmpresaTrabajo getEmpresaTrabajoSelected() {
		return empresaTrabajoSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<EmpresaTrabajo> getListaEmpresaTrabajo() {
		return listaEmpresaTrabajo;
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

	public void setEmpresaTrabajo(EmpresaTrabajo empresaTrabajo) {
		this.empresaTrabajo = empresaTrabajo;
	}

	public void setEmpresaTrabajoSelected(EmpresaTrabajo empresaTrabajoSelected) {
		this.empresaTrabajoSelected = empresaTrabajoSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaEmpresaTrabajo(List<EmpresaTrabajo> listaEmpresaTrabajo) {
		this.listaEmpresaTrabajo = listaEmpresaTrabajo;
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
