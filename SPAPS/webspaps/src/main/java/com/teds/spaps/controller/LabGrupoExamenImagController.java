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

import com.teds.spaps.interfaces.dao.ILabGrupoExamenImagDao;
import com.teds.spaps.model.LabGrupoExamenImag;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

/**
 * @author ANITA
 *
 */
@ManagedBean(name = "labGrupoExamenImagController")
@ViewScoped
public class LabGrupoExamenImagController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -125210622622704977L;
	/******* DAO **********/
	private @Inject ILabGrupoExamenImagDao labGrupoDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private LabGrupoExamenImag labGrupo;
	private LabGrupoExamenImag labGrupoExamenImagSelected;
	private Sucursal sucursal;

	/******* LIST **********/
	private List<LabGrupoExamenImag> listaLabGrupoExamenImag;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;
	private boolean seleccionado = false;
	
	//URL PAGE
	private String currentPage = "/pages/laboratorio/grupo_examen_imagenologia/list.xhtml";


	/**
	 * 
	 */
	public LabGrupoExamenImagController() {
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
		labGrupo = new LabGrupoExamenImag();
		labGrupoExamenImagSelected = new LabGrupoExamenImag();
		listaLabGrupoExamenImag = labGrupoDao.obtenerPorCompania(sucursal
				.getCompania());
		currentPage = "/pages/laboratorio/grupo_examen_imagenologia/list.xhtml";
	}

	public void registrar() {
		try {
			System.out.println("Ingreso a registrar");

			if (labGrupo.getDescripcion().trim().isEmpty()
					|| labGrupo.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			}
			labGrupo.setCompania(sucursal.getCompania());
			labGrupo.setSucursal(sucursal);
			labGrupo.setFechaRegistro(new Date());
			labGrupo
					.setFechaModificacion(labGrupo.getFechaRegistro());
			labGrupo.setUsuarioRegistro(sessionMain.getUsuarioLogin()
					.getLogin());

			LabGrupoExamenImag r = labGrupoDao.registrar(labGrupo);
			if (r != null) {
				defaultValues();
			} else {
				defaultValues();
			}
		} catch (Exception e) {
			System.out.println("Error en registro de labGrupoExamenImag: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			System.out.println("Ingreso a actualizar");
			if (labGrupo.getDescripcion().trim().isEmpty()
					|| labGrupo.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				labGrupo.setCompania(getSucursal().getCompania());
				labGrupo.setSucursal(getSucursal());
				labGrupo.setFechaModificacion(new Date());
				labGrupo.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getLogin());
				LabGrupoExamenImag r = labGrupoDao.modificar(labGrupo);
				if (r != null) {
					defaultValues();
				} else {
					defaultValues();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de labGrupoExamenImag: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			if (labGrupoDao.eliminar(labGrupo)) {
				FacesUtil.infoMessage("LabGrupoExamenImag Eliminado",
						labGrupo.toString());
				defaultValues();
			} else {
				FacesUtil.errorMessage("Error al eliminar");
				defaultValues();
			}
		} catch (Exception e) {
			System.out.println("Error en eliminacion de labGrupoExamenImag: "
					+ e.getMessage());
		}
	}

	public void onRowSelect(SelectEvent event) {
		labGrupo = labGrupoExamenImagSelected;
		crear = false;
		seleccionado = true;
		registrar = false;
		currentPage = "/pages/laboratorio/grupo_examen_imagenologia/edit.xhtml";
	}

	public void actionNuevo() {
		System.out.println("Ingreso a actionNuevo...");
		crear = false;
		seleccionado = false;
		registrar = true;
		labGrupo = new LabGrupoExamenImag();
		labGrupo.setFechaRegistro(new Date());
		currentPage = "/pages/laboratorio/grupo_examen_imagenologia/edit.xhtml";
	}

	public void actionCancelar() {
		crear = true;
		seleccionado = false;
		registrar = false;
		labGrupo = new LabGrupoExamenImag();
		currentPage = "/pages/laboratorio/grupo_examen_imagenologia/list.xhtml";
	}

	public boolean isSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

	public LabGrupoExamenImag getLabGrupoExamenImag() {
		return labGrupo;
	}

	public LabGrupoExamenImag getLabGrupoExamenImagSelected() {
		return labGrupoExamenImagSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<LabGrupoExamenImag> getListaLabGrupoExamenImag() {
		return listaLabGrupoExamenImag;
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

	public void setLabGrupoExamenImag(LabGrupoExamenImag labGrupo) {
		this.labGrupo = labGrupo;
	}

	public void setLabGrupoExamenImagSelected(LabGrupoExamenImag labGrupoExamenImagSelected) {
		this.labGrupoExamenImagSelected = labGrupoExamenImagSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaLabGrupoExamenImag(List<LabGrupoExamenImag> listaLabGrupoExamenImag) {
		this.listaLabGrupoExamenImag = listaLabGrupoExamenImag;
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

}
