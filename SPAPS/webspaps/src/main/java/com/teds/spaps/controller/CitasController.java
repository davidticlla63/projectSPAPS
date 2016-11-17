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

import com.teds.spaps.interfaces.dao.ICitaDao;
import com.teds.spaps.model.Cita;
import com.teds.spaps.model.Paciente;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

/**
 * 
 * @author david
 *
 */
@ManagedBean(name = "citasController")
@ViewScoped
public class CitasController implements Serializable {

	private static final long serialVersionUID = 1L;

	/******* DAO **********/
	private @Inject ICitaDao citaDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private Cita cita;
	private Cita citaSelected;
	private Sucursal sucursal;
	private Paciente paciente= new Paciente();

	/******* LIST **********/
	private List<Cita> listaCita;
	private List<Cita> listaCitas = new ArrayList<Cita>();

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;
	private boolean seleccionado = false;

	// VAR
	private String currentPage = "/pages/citas/citas/list.xhtml";
	
	

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
		cita = new Cita();
		citaSelected = new Cita();
		listaCita = citaDao.obtenerPorSucursal(sucursal);
		currentPage = "/pages/citas/citas/list.xhtml";
	}

	public void registrar() {
		try {
			System.out.println("Ingreso a registrar");

			cita.setSucursal(sucursal);
			cita.setFechaRegistro(new Date());
			cita.setFechaModificacion(cita.getFechaRegistro());
			cita.setUsuarioRegistro(sessionMain.getUsuarioLogin()
					.getLogin());

			Cita r = citaDao.registrar(cita);
			if (r != null) {
				defaultValues();
			} else {
				return;
			}
		} catch (Exception e) {
			System.out.println("Error en registro de cita: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			System.out.println("Ingreso a actualizar");
			
				cita.setSucursal(sucursal);
				cita.setFechaModificacion(new Date());
				cita.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getLogin());
				Cita r = citaDao.modificar(cita);
				if (r != null) {
					defaultValues();
				} else {
					return;
				}
		} catch (Exception e) {
			System.out.println("Error en modificacion de cita: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			if (citaDao.eliminar(cita)) {
				FacesUtil.infoMessage("Cita Eliminado",
						cita.toString());
				defaultValues();
			} else {
				FacesUtil.errorMessage("Error al eliminar");
				defaultValues();
			}
		} catch (Exception e) {
			System.out.println("Error en eliminacion de cita: "
					+ e.getMessage());
		}
	}

	public void onRowSelect(SelectEvent event) {
		cita = citaSelected;
		crear = false;
		seleccionado = true;
		registrar = false;
		currentPage = "/pages/citas/citas/edit.xhtml";
	}

	public void actionNuevo() {
		System.out.println("Ingreso a actionNuevo...");
		crear = false;
		seleccionado = false;
		registrar = true;
		cita = new Cita();
		cita.setFechaRegistro(new Date());
		currentPage = "/pages/citas/citas/edit.xhtml";
	}

	public void actionCancelar() {
		crear = true;
		seleccionado = false;
		registrar = false;
		cita = new Cita();
		currentPage = "/pages/citas/citas/list.xhtml";
	}

	public void actionModificar(Integer id) {
		crear = false;
		seleccionado = true;
		registrar = false;
		currentPage = "/pages/citas/citas/edit.xhtml";
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

	public Cita getCita() {
		return cita;
	}

	public Cita getCitaSelected() {
		return citaSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<Cita> getListaCita() {
		return listaCita;
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

	public void setCita(Cita cita) {
		this.cita = cita;
	}

	public void setCitaSelected(Cita citaSelected) {
		this.citaSelected = citaSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaCita(List<Cita> listaCita) {
		this.listaCita = listaCita;
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

	/**
	 * @return the currentPage
	 */
	public String getCurrentPage() {
		return currentPage;
	}

	/**
	 * @param currentPage
	 *            the currentPage to set
	 */
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * @return the listaCitas
	 */
	public List<Cita> getListaCitas() {
		return listaCitas;
	}

	/**
	 * @param listaCitas
	 *            the listaCitas to set
	 */
	public void setListaCitas(List<Cita> listaCitas) {
		this.listaCitas = listaCitas;
	}

	/**
	 * @return the paciente
	 */
	public Paciente getPaciente() {
		return paciente;
	}

	/**
	 * @param paciente the paciente to set
	 */
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

}
