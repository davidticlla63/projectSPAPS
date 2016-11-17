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
import com.teds.spaps.model.Departamento;
import com.teds.spaps.model.Pais;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

/**
 * @author ANITA
 *
 */
@ManagedBean(name = "departamentoController")
@ViewScoped
public class DepartamentoController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 844192813700641693L;
	/******* DAO **********/
	private @Inject IDepartamentoDao departamentoDao;
	private @Inject SessionMain sessionMain;
	private @Inject IPaisDao paisDao;

	/******* OBJECT **********/
	private Departamento departamento;
	private Departamento departamentoSelected;
	private Sucursal sucursal;
	private Pais selectedPais;

	/******* LIST **********/
	private List<Departamento> listaDepartamento;
	private List<Pais> listaPais = new ArrayList<Pais>();

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;
	private boolean seleccionado = false;

	/**
	 * 
	 */
	public DepartamentoController() {
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
		departamento = new Departamento();
		departamentoSelected = new Departamento();
		listaDepartamento = departamentoDao.obtenerPorCompania(sucursal
				.getCompania());

	}

	public void registrar() {
		try {
			System.out.println("Ingreso a registrar");

			if (departamento.getNombre().trim().isEmpty()
					|| departamento.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			}
			departamento.setCompania(sucursal.getCompania());
			departamento.setSucursal(sucursal);
			departamento.setFechaRegistro(new Date());
			departamento.setFechaModificacion(departamento.getFechaRegistro());
			departamento.setUsuarioRegistro(sessionMain.getUsuarioLogin()
					.getLogin());
			departamento.setPais(selectedPais);
			Departamento r = departamentoDao.registrar(departamento);
			if (r != null) {
				FacesUtil.infoMessage("Departamento registrado", r.toString());
				defaultValues();
			} else {
				FacesUtil.errorMessage("Error al registrar");
				defaultValues();
			}
		} catch (Exception e) {
			System.out.println("Error en registro de departamento: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			System.out.println("Ingreso a actualizar");
			if (departamento.getNombre().trim().isEmpty()
					|| departamento.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				departamento.setCompania(getSucursal().getCompania());
				departamento.setSucursal(getSucursal());
				departamento.setFechaModificacion(new Date());
				departamento.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getLogin());
				departamento.setPais(selectedPais);
				Departamento r = departamentoDao.modificar(departamento);
				if (r != null) {
					FacesUtil.infoMessage("Departamento actualizado",
							r.toString());
					defaultValues();
				} else {
					FacesUtil.errorMessage("Error al actualizar");
					defaultValues();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de departamento: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			if (departamentoDao.eliminar(departamento)) {
				FacesUtil.infoMessage("Departamento Eliminado",
						departamento.toString());
				defaultValues();
			} else {
				FacesUtil.errorMessage("Error al eliminar");
				defaultValues();
			}
		} catch (Exception e) {
			System.out.println("Error en eliminacion de departamento: "
					+ e.getMessage());
		}
	}

	public void onRowSelect(SelectEvent event) {
		departamento = departamentoSelected;
		crear = false;
		seleccionado = true;
		registrar = false;
		selectedPais = departamento.getPais();
	}

	public void actionNuevo() {
		System.out.println("Ingreso a actionNuevo...");
		crear = false;
		seleccionado = false;
		registrar = true;
		departamento = new Departamento();
		departamento.setFechaRegistro(new Date());
		selectedPais = new Pais();
	}

	public void actionCancelar() {
		System.out.println("Ingreso a actionCancelar..");
		crear = true;
		seleccionado = false;
		registrar = false;
		departamento = new Departamento();
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

	public Departamento getDepartamento() {
		return departamento;
	}

	public Departamento getDepartamentoSelected() {
		return departamentoSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<Departamento> getListaDepartamento() {
		return listaDepartamento;
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

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public void setDepartamentoSelected(Departamento departamentoSelected) {
		this.departamentoSelected = departamentoSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaDepartamento(List<Departamento> listaDepartamento) {
		this.listaDepartamento = listaDepartamento;
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
