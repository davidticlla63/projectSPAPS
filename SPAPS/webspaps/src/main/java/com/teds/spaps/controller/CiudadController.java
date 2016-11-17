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

import com.teds.spaps.interfaces.dao.ICiudadDao;
import com.teds.spaps.interfaces.dao.IPaisDao;
import com.teds.spaps.model.Ciudad;
import com.teds.spaps.model.Pais;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

/**
 * @author ANITA
 *
 */
@ManagedBean(name = "ciudadController")
@ViewScoped
public class CiudadController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -125210622622704977L;
	/******* DAO **********/
	private @Inject ICiudadDao ciudadDao;
	private @Inject SessionMain sessionMain;
	private @Inject IPaisDao paisDao;

	/******* OBJECT **********/
	private Ciudad ciudad;
	private Ciudad ciudadSelected;
	private Sucursal sucursal;
	private Pais selectedPais;

	/******* LIST **********/
	private List<Ciudad> listaCiudad;
	private List<Pais> listaPais = new ArrayList<Pais>();

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;
	private boolean seleccionado = false;
	
	//URL PAGE
	private String currentPage = "/pages/admision/ciudad/list.xhtml";


	/**
	 * 
	 */
	public CiudadController() {
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
		ciudad = new Ciudad();
		ciudadSelected = new Ciudad();
		listaCiudad = ciudadDao.obtenerPorCompania(sucursal
				.getCompania());
		currentPage = "/pages/admision/ciudad/list.xhtml";
	}

	public void registrar() {
		try {
			System.out.println("Ingreso a registrar");

			if (ciudad.getNombre().trim().isEmpty()
					|| ciudad.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			}
			//ciudad.setCompania(sucursal.getCompania());
			ciudad.setFechaRegistro(new Date());
			ciudad.setPais(selectedPais);
			ciudad
					.setFechaModificacion(ciudad.getFechaRegistro());
			ciudad.setUsuarioRegistro(sessionMain.getUsuarioLogin()
					.getLogin());

			Ciudad r = ciudadDao.registrar(ciudad);
			if (r != null) {
				defaultValues();
			} else {
				defaultValues();
			}
		} catch (Exception e) {
			System.out.println("Error en registro de ciudad: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			System.out.println("Ingreso a actualizar");
			if (ciudad.getNombre().trim().isEmpty()
					|| ciudad.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				//ciudad.setCompania(getSucursal().getCompania());
				ciudad.setFechaModificacion(new Date());
				ciudad.setPais(selectedPais);
				ciudad.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getLogin());
				Ciudad r = ciudadDao.modificar(ciudad);
				if (r != null) {
					defaultValues();
				} else {
					defaultValues();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de ciudad: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			if (ciudadDao.eliminar(ciudad)) {
				FacesUtil.infoMessage("Ciudad Eliminado",
						ciudad.toString());
				defaultValues();
			} else {
				FacesUtil.errorMessage("Error al eliminar");
				defaultValues();
			}
		} catch (Exception e) {
			System.out.println("Error en eliminacion de ciudad: "
					+ e.getMessage());
		}
	}

	public void onRowSelect(SelectEvent event) {
		ciudad = ciudadSelected;
		crear = false;
		seleccionado = true;
		registrar = false;
		selectedPais=ciudad.getPais();
		currentPage = "/pages/admision/ciudad/edit.xhtml";
	}

	public void actionNuevo() {
		System.out.println("Ingreso a actionNuevo...");
		crear = false;
		seleccionado = false;
		registrar = true;
		ciudad = new Ciudad();
		selectedPais= new Pais();
		ciudad.setFechaRegistro(new Date());
		currentPage = "/pages/admision/ciudad/edit.xhtml";
	}

	public void actionCancelar() {
		crear = true;
		seleccionado = false;
		registrar = false;
		ciudad = new Ciudad();
		currentPage = "/pages/admision/ciudad/list.xhtml";
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
					setSelectedPais(i);
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

	public Ciudad getCiudad() {
		return ciudad;
	}

	public Ciudad getCiudadSelected() {
		return ciudadSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<Ciudad> getListaCiudad() {
		return listaCiudad;
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

	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}

	public void setCiudadSelected(Ciudad ciudadSelected) {
		this.ciudadSelected = ciudadSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaCiudad(List<Ciudad> listaCiudad) {
		this.listaCiudad = listaCiudad;
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
	 * @param currentPage the currentPage to set
	 */
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * @return the selectedPais
	 */
	public Pais getSelectedPais() {
		return selectedPais;
	}

	/**
	 * @param selectedPais the selectedPais to set
	 */
	public void setSelectedPais(Pais selectedPais) {
		this.selectedPais = selectedPais;
	}

}
