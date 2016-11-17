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

import com.teds.spaps.interfaces.dao.IRrHhTurnoDao;
import com.teds.spaps.model.RrHhTurno;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

/**
 * @author ANITA
 *
 */
@ManagedBean(name = "rrHhTurnoController")
@ViewScoped
public class RrHhTurnoController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -125210622622704977L;
	/******* DAO **********/
	private @Inject IRrHhTurnoDao rrHhTurnoDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private RrHhTurno  rrHhTurno;
	private RrHhTurno rrHhTurnoExamenSelected;
	private Sucursal sucursal;

	/******* LIST **********/
	private List<RrHhTurno> listaRrHhTurno;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;
	private boolean seleccionado = false;
	
	//URL PAGE
	private String currentPage = "/pages/rr_hh/turno/list.xhtml";


	/**
	 * 
	 */
	public RrHhTurnoController() {
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
		rrHhTurno = new RrHhTurno();
		rrHhTurnoExamenSelected = new RrHhTurno();
		listaRrHhTurno = rrHhTurnoDao.obtenerPorCompania(sucursal
				.getCompania());
		currentPage = "/pages/rr_hh/turno/list.xhtml";
	}

	public void registrar() {
		try {
			System.out.println("Ingreso a registrar");

			if (rrHhTurno.getNombre().trim().isEmpty()
					|| rrHhTurno.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			}
			rrHhTurno.setCompania(sucursal.getCompania());
			rrHhTurno.setFechaRegistro(new Date());
			rrHhTurno
					.setFechaModificacion(rrHhTurno.getFechaRegistro());
			rrHhTurno.setUsuarioRegistro(sessionMain.getUsuarioLogin()
					.getLogin());

			RrHhTurno r = rrHhTurnoDao.registrar(rrHhTurno);
			if (r != null) {
				defaultValues();
			} else {
				defaultValues();
			}
		} catch (Exception e) {
			System.out.println("Error en registro de rrHhTurnoExamen: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			System.out.println("Ingreso a actualizar");
			if (rrHhTurno.getNombre().trim().isEmpty()
					|| rrHhTurno.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				rrHhTurno.setCompania(getSucursal().getCompania());
				rrHhTurno.setFechaModificacion(new Date());
				rrHhTurno.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getLogin());
				RrHhTurno r = rrHhTurnoDao.modificar(rrHhTurno);
				if (r != null) {
					defaultValues();
				} else {
					defaultValues();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de rrHhTurnoExamen: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			if (rrHhTurnoDao.eliminar(rrHhTurno)) {
				FacesUtil.infoMessage("RrHhTurno Eliminado",
						rrHhTurno.toString());
				defaultValues();
			} else {
				FacesUtil.errorMessage("Error al eliminar");
				defaultValues();
			}
		} catch (Exception e) {
			System.out.println("Error en eliminacion de rrHhTurnoExamen: "
					+ e.getMessage());
		}
	}

	public void onRowSelect(SelectEvent event) {
		rrHhTurno = rrHhTurnoExamenSelected;
		crear = false;
		seleccionado = true;
		registrar = false;
		currentPage = "/pages/rr_hh/turno/edit.xhtml";
	}

	public void actionNuevo() {
		System.out.println("Ingreso a actionNuevo...");
		crear = false;
		seleccionado = false;
		registrar = true;
		rrHhTurno = new RrHhTurno();
		rrHhTurno.setFechaRegistro(new Date());
		currentPage = "/pages/rr_hh/turno/edit.xhtml";
	}

	public void actionCancelar() {
		crear = true;
		seleccionado = false;
		registrar = false;
		rrHhTurno = new RrHhTurno();
		currentPage = "/pages/rr_hh/turno/list.xhtml";
	}

	public boolean isSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

	public RrHhTurno getRrHhTurno() {
		return rrHhTurno;
	}

	public RrHhTurno getRrHhTurnoSelected() {
		return rrHhTurnoExamenSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<RrHhTurno> getListaRrHhTurno() {
		return listaRrHhTurno;
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

	public void setRrHhTurno(RrHhTurno rrHhTurno) {
		this.rrHhTurno = rrHhTurno;
	}

	public void setRrHhTurnoSelected(RrHhTurno rrHhTurnoExamenSelected) {
		this.rrHhTurnoExamenSelected = rrHhTurnoExamenSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaRrHhTurno(List<RrHhTurno> listaRrHhTurno) {
		this.listaRrHhTurno = listaRrHhTurno;
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
