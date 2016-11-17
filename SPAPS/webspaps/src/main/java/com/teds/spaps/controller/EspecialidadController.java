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

import com.teds.spaps.interfaces.dao.IEspecialidadDao;
import com.teds.spaps.model.Especialidad;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

/**
 * @author ANITA
 *
 */
@ManagedBean(name = "especialidadController")
@ViewScoped
public class EspecialidadController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -125210622622704977L;
	/******* DAO **********/
	private @Inject IEspecialidadDao especialidadDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private Especialidad especialidad;
	private Especialidad especialidadSelected;
	private Sucursal sucursal;

	/******* LIST **********/
	private List<Especialidad> listaEspecialidad;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;
	private boolean seleccionado = false;

	/**
	 * 
	 */
	public EspecialidadController() {
	}

	public Especialidad getEspecialidad() {
		return especialidad;
	}

	public Especialidad getEspecialidadSelected() {
		return especialidadSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<Especialidad> getListaEspecialidad() {
		return listaEspecialidad;
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

	public void setEspecialidad(Especialidad especialidad) {
		this.especialidad = especialidad;
	}

	public void setEspecialidadSelected(Especialidad especialidadSelected) {
		this.especialidadSelected = especialidadSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaEspecialidad(List<Especialidad> listaEspecialidad) {
		this.listaEspecialidad = listaEspecialidad;
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
		especialidad = new Especialidad();
		especialidadSelected = new Especialidad();
		listaEspecialidad = especialidadDao.obtenerPorCompania(sucursal
				.getCompania());
	}

	public void registrar() {
		try {
			System.out.println("Ingreso a registrar");

			if (especialidad.getNombre().trim().isEmpty()
					|| especialidad.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			}
			especialidad.setCompania(sucursal.getCompania());
			especialidad.setSucursal(sucursal);
			especialidad.setFechaRegistro(new Date());
			especialidad.setFechaModificacion(especialidad.getFechaRegistro());
			especialidad.setUsuarioRegistro(sessionMain.getUsuarioLogin()
					.getLogin());

			Especialidad r = especialidadDao.registrar(especialidad);
			if (r != null) {
				FacesUtil.infoMessage("Especialidad registrado", r.toString());
				defaultValues();
			} else {
				FacesUtil.errorMessage("Error al registrar");
				defaultValues();
			}
		} catch (Exception e) {
			System.out.println("Error en registro de especialidad: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			System.out.println("Ingreso a actualizar");
			if (especialidad.getNombre().trim().isEmpty()
					|| especialidad.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				especialidad.setCompania(getSucursal().getCompania());
				especialidad.setSucursal(getSucursal());
				especialidad.setFechaModificacion(new Date());
				especialidad.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getLogin());
				Especialidad r = especialidadDao.modificar(especialidad);
				if (r != null) {
					FacesUtil.infoMessage("Especialidad actualizado",
							r.toString());
					defaultValues();
				} else {
					FacesUtil.errorMessage("Error al actualizar");
					defaultValues();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de especialidad: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			if (especialidadDao.eliminar(especialidad)) {
				FacesUtil.infoMessage("Especialidad Eliminado",
						especialidad.toString());
				defaultValues();
			} else {
				FacesUtil.errorMessage("Error al eliminar");
				defaultValues();
			}
		} catch (Exception e) {
			System.out.println("Error en eliminacion de especialidad: "
					+ e.getMessage());
		}
	}

	public void onRowSelect(SelectEvent event) {
		especialidad = especialidadSelected;
		crear = false;
		seleccionado = true;
		registrar = false;
	}

	public void actionNuevo() {
		System.out.println("Ingreso a actionNuevo...");
		crear = false;
		seleccionado = false;
		registrar = true;
		especialidad = new Especialidad();
		especialidad.setFechaRegistro(new Date());
	}

	public void actionCancelar() {
		crear = true;
		seleccionado = false;
		registrar = false;
		especialidad = new Especialidad();
	}

	public boolean isSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

}
