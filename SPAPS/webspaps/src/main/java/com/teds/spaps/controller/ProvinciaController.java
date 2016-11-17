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

import com.teds.spaps.interfaces.dao.IDepartamentoDao;
import com.teds.spaps.interfaces.dao.IPaisDao;
import com.teds.spaps.interfaces.dao.IProvinciaDao;
import com.teds.spaps.model.Departamento;
import com.teds.spaps.model.Pais;
import com.teds.spaps.model.Provincia;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

/**
 * @author ANITA
 *
 */
@ManagedBean(name = "provinciaController")
@ViewScoped
public class ProvinciaController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 844192813700641693L;
	/******* DAO **********/
	private @Inject IProvinciaDao provinciaDao;
	private @Inject SessionMain sessionMain;
	private @Inject IPaisDao paisDao;
	private @Inject IDepartamentoDao departamentoDao;

	/******* OBJECT **********/
	private Provincia provincia;
	private Provincia provinciaSelected;
	private Sucursal sucursal;
	private Departamento selectedDepartamento;
	private Pais selectedPais;

	/******* LIST **********/
	private List<Provincia> listaProvincia;
	private List<Pais> listaPais = new ArrayList<Pais>();
	private List<Departamento> listaDepartamentos = new ArrayList<Departamento>();
	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;
	private boolean seleccionado = false;

	/**
	 * 
	 */
	public ProvinciaController() {
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
		provincia = new Provincia();
		provinciaSelected = new Provincia();
		listaProvincia = provinciaDao
				.obtenerPorCompania(sucursal.getCompania());

	}

	public void registrar() {
		try {
			System.out.println("Ingreso a registrar");

			if (provincia.getNombre().trim().isEmpty()
					|| provincia.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			}
			provincia.setCompania(sucursal.getCompania());
			provincia.setSucursal(sucursal);
			provincia.setFechaRegistro(new Date());
			provincia.setFechaModificacion(provincia.getFechaRegistro());
			provincia.setUsuarioRegistro(sessionMain.getUsuarioLogin()
					.getLogin());
			provincia.setDepartamento(selectedDepartamento);
			Provincia r = provinciaDao.registrar(provincia);
			if (r != null) {
				FacesUtil.infoMessage("Provincia registrado", r.toString());
				defaultValues();
			} else {
				FacesUtil.errorMessage("Error al registrar");
				defaultValues();
			}
		} catch (Exception e) {
			System.out.println("Error en registro de provincia: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			System.out.println("Ingreso a actualizar");
			if (provincia.getNombre().trim().isEmpty()
					|| provincia.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				provincia.setCompania(getSucursal().getCompania());
				provincia.setSucursal(getSucursal());
				provincia.setFechaModificacion(new Date());
				provincia.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getLogin());
				provincia.setDepartamento(selectedDepartamento);
				Provincia r = provinciaDao.modificar(provincia);
				if (r != null) {
					FacesUtil
							.infoMessage("Provincia actualizado", r.toString());
					defaultValues();
				} else {
					FacesUtil.errorMessage("Error al actualizar");
					defaultValues();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de provincia: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			if (provinciaDao.eliminar(provincia)) {
				FacesUtil.infoMessage("Provincia Eliminado",
						provincia.toString());
				defaultValues();
			} else {
				FacesUtil.errorMessage("Error al eliminar");
				defaultValues();
			}
		} catch (Exception e) {
			System.out.println("Error en eliminacion de provincia: "
					+ e.getMessage());
		}
	}

	public void onRowSelect(SelectEvent event) {
		provincia = provinciaSelected;
		crear = false;
		seleccionado = true;
		registrar = false;
		selectedDepartamento = provincia.getDepartamento();
		selectedPais = selectedDepartamento.getPais();
	}

	public void actionNuevo() {
		System.out.println("Ingreso a actionNuevo...");
		crear = false;
		seleccionado = false;
		registrar = true;
		provincia = new Provincia();
		provincia.setFechaRegistro(new Date());
		selectedPais = new Pais();
		selectedDepartamento = new Departamento();
	}

	public void actionCancelar() {
		System.out.println("Ingreso a actionCancelar..");
		crear = true;
		seleccionado = false;
		registrar = false;
		provincia = new Provincia();
		selectedPais = new Pais();
	}

	// ONCOMPLETETEXT PAIS
	public List<Pais> completePais(String query) {
		String upperQuery = query.toUpperCase();
		List<Pais> results = new ArrayList<Pais>();
		listaPais = paisDao.obtenerPorCompaniaAutoComplete(upperQuery,
				sucursal.getCompania());
		for (Pais i : listaPais) {
			results.add(i);
		}
		return results;
	}

	public void onRowSelectPaisClick(SelectEvent event) {
		String nombre = event.getObject().toString();
		for (Pais i : listaPais) {
			if (i.getNombre().equals(nombre)) {
				selectedPais = i;
				return;
			}
		}
	}

	// ONCOMPLETETEXT DEPARTAMENTO
	public List<Departamento> completeDepartamento(String query) {
		String upperQuery = query.toUpperCase();
		List<Departamento> results = new ArrayList<Departamento>();
		if (selectedPais.getId().intValue() == 0) {
			FacesUtil.infoMessage("Error", "Seleccione el Pais");
			return null;
		}
		listaDepartamentos = departamentoDao.obtenerPorPais(upperQuery,
				selectedPais);
		for (Departamento i : listaDepartamentos) {
			results.add(i);
		}
		return results;
	}

	public void onRowSelectDepartamentoClick(SelectEvent event) {
		String nombre = event.getObject().toString();
		for (Departamento i : listaDepartamentos) {
			if (i.getNombre().equals(nombre)) {
				selectedDepartamento = i;
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

	public List<Pais> getListaPais() {
		return listaPais;
	}

	public void setListaPais(List<Pais> listaPais) {
		this.listaPais = listaPais;
	}

	public Pais getSelectedPais() {
		return selectedPais;
	}

	public void setSelectedPais(Pais selectedPais) {
		this.selectedPais = selectedPais;
	}

	public Provincia getProvincia() {
		return provincia;
	}

	public Provincia getProvinciaSelected() {
		return provinciaSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<Provincia> getListaProvincia() {
		return listaProvincia;
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

	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}

	public void setProvinciaSelected(Provincia provinciaSelected) {
		this.provinciaSelected = provinciaSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaProvincia(List<Provincia> listaProvincia) {
		this.listaProvincia = listaProvincia;
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

	public Departamento getSelectedDepartamento() {
		return selectedDepartamento;
	}

	public void setSelectedDepartamento(Departamento selectedDepartamento) {
		this.selectedDepartamento = selectedDepartamento;
	}

	public List<Departamento> getListaDepartamentos() {
		return listaDepartamentos;
	}

	public void setListaDepartamentos(List<Departamento> listadeDepartamentos) {
		this.listaDepartamentos = listadeDepartamentos;
	}

}
